/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.TiposDeAtributos;
import datos.dataStructure;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import particionado.AAUtils;

/**
 *
 * @author vicdejuan
 */
public class ClasificadorNaiveBayes extends Clasificador {
    
    private boolean Laplace;
    /*
    Clave -> Clase
    Valor -> MAP:
            Clave -> Atributo
            Valor -> MAP:
                    Clave -> µ|σ (continuo) ó atrb (nominal)
                    Valor -> valor (continuo) ó frec (nominal)
    
    */
    HashMap <String,HashMap<String, HashMap<String,Double>>> SMT;
    HashMap <String,Double> probs_hips;
    double total;

    ClasificadorNaiveBayes(Boolean laplace) {
        this.Laplace = laplace;
    }


    
    public boolean isLaplace() {
        return Laplace;
    }

    public HashMap<String, HashMap<String, HashMap<String,Double>>> getSMT() {
        return SMT;
    }

    @Override
    public void entrenamiento(Datos datosTrain) {
        probs_hips = new HashMap<>(datosTrain.getClases());
        SMT = new HashMap<>();
        double val;
        total = datosTrain.getNumDatos();
        for(String  actualClass : datosTrain.getClases().keySet()){
            for (HashMap<String,dataStructure> row : datosTrain.getDatos()){
                if (actualClass.equals(datosTrain.getClassFromRow(row))){
                    for (String atrb : datosTrain.getNomDatos()){
                    if (datosTrain.getTipoAtributos().get(atrb) == TiposDeAtributos.Nominal){
                            val = 1;  
                            AAUtils.updateSMT(SMT, actualClass, atrb, (String) row.get(atrb).getVal(), val);   
                        }
                        else{
                            val = (double) row.get(atrb).getVal();
                            AAUtils.updateSMT(SMT,actualClass,atrb,"esp",val,"var",0.0);
                     }
                   
                    }
                }
            }
        }
        
        // TODO: cálculo de varianza.
        // Dividir tooodos por el número total de datos. ¿Necesario? Es ineficiente...
        // TODO: revisar los divisores.
        for (Map.Entry<String, HashMap<String, HashMap<String,Double>>> clase : SMT.entrySet()){
            double todiv = datosTrain.getClases().get(clase.getKey());
            for (Map.Entry<String, HashMap<String,Double>> atrb : clase.getValue().entrySet())
                for (Map.Entry<String, Double> entry : atrb.getValue().entrySet()){
                    atrb.getValue().put(entry.getKey(), entry.getValue()/todiv);
                }
        }
            
                
    }

    
    
    @Override
    public HashMap<String, Double> clasifica(Datos datosTest) {
        HashMap<String,Double> toRet = new HashMap<>();
        HashMap<String,Double> toMax = new HashMap<>();
        for (HashMap<String, dataStructure> row : datosTest.getDatos()){
            toMax.clear();
            for (String clas : SMT.keySet()){
                toMax.put(clas, computa(clas,row));
            }
            
            AAUtils.AddOrCreate(toRet, Collections.max(
                toMax.entrySet(),
                new Comparator<Map.Entry<String,Double>>(){
                    @Override
                    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                        return o1.getValue() > o2.getValue()? 1:-1;
                    }
                }).getKey(), 1);
            
        }
        return toRet;
    }
    
    private double computa(String clas, HashMap<String,dataStructure> row){
        double producto = 1;
        double prob_hip = probs_hips.get(clas)/total;
        double x,mean,sd;
        for (Map.Entry<String, dataStructure> atrb : row.entrySet()){
            //if Continuo
            if (atrb.getValue().getTipoAtributo() == TiposDeAtributos.Continuo){
                x = (double) atrb.getValue().getVal();
                mean = SMT.get(clas).get(atrb.getKey()).get("esp");
                sd = SMT.get(clas).get(atrb.getKey()).get("var");
                producto *= distribucionNormal(x, mean, sd);
            }else{
                //TODO:
                producto *= 1;//SMT.get(clas).get(atrb.getKey()).get((String) atrb.getValue().getVal());
            }
        }
        return producto*prob_hip;
    }
    
    private static double distribucionNormal(double mean, double sd, double x){
        return phi(x, mean, sd);
    }
    
    // Distribución normal estandar.
    public static double phi(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    // return phi(x, mu, signma) = Normal de media mu y varianza sigma.
    public static double phi(double x, double mu, double sigma) {
        return phi((x - mu) / sigma) / sigma;
    }
    
}
