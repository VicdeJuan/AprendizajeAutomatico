/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.TiposDeAtributos;
import datos.dataStructure;
import java.util.HashMap;
import java.util.Map;
import particionado.AAUtils;

/**
 *
 * @author vicdejuan
 */
public class ClasificadorNaiveBayes extends Clasificador {
    
    private boolean Laplace;
    
    HashMap <String,HashMap<String, HashMap<String,Double>>> SMT;

    public boolean isLaplace() {
        return Laplace;
    }

    public HashMap<String, HashMap<String, HashMap<String,Double>>> getSMT() {
        return SMT;
    }
    
    
    
    
    private boolean IsNominal(Datos datos, String atrb){
        return datos.getTipoAtributos().get(atrb) == TiposDeAtributos.Nominal;
    }
    
    @Override
    public void entrenamiento(Datos datosTrain) {
        
        SMT = new HashMap<>();
        double val;
        
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
                            AAUtils.updateSMT(SMT,actualClass,atrb,"esp",val,"var",val*val);
                     }
                   
                    }
                }
            }
        }
        
        
        // Dividir tooodos por el número total de datos. ¿Necesario? Es ineficiente...
        for (Map.Entry<String, HashMap<String, HashMap<String,Double>>> clase : SMT.entrySet()){
            int todiv = datosTrain.getClases().get(clase.getKey());
            for (Map.Entry<String, HashMap<String,Double>> atrb : clase.getValue().entrySet())
                for (Map.Entry<String, Double> entry : atrb.getValue().entrySet()){
                    atrb.getValue().put(entry.getKey(), entry.getValue()/todiv);
                }
        }
            
                
    }

    
    
    @Override
    public HashMap<String, Integer> clasifica(Datos datosTest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
