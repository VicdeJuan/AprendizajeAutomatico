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
    
    HashMap <String,HashMap<String, double[]>> SMT;
    
    
    
    
    private boolean IsNominal(Datos datos, String atrb){
        return datos.getTipoAtributos().get(datos.getNomDatos().indexOf(atrb)) == TiposDeAtributos.Nominal;
    }
    
    @Override
    public void entrenamiento(Datos datosTrain) {
        
        SMT = new HashMap<>();
        double val;
        
        for (dataStructure[] row : datosTrain.getDatos()){
            for(String  actualClass : datosTrain.getClases().keySet()){
                for (String atrb : datosTrain.getNomDatos()){
                    if (this.IsNominal(datosTrain, atrb))
                        val = 1;
                    else
                        val = row.get(atrb);
                    AAUtils.updateSMT(SMT,actualClass,atrb,0,val,1,val*val);
                    
                }
            }
        }
        
    }

    @Override
    public HashMap<String, Integer> clasifica(Datos datosTest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
