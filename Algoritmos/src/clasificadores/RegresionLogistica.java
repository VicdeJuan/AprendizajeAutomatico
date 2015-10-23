/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.TiposDeAtributos;
import datos.dataStructure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import particionado.AAUtils;

/**
 *
 * @author vicdejuan
 */
public class RegresionLogistica extends Clasificador {

    HashMap<String,Double> coef;
    HashMap<String,Double> valoresParaNominales;
    private final String STR_DELIM = "_";

    
    @Override
    public void entrenamiento(Datos datosTrain) {
        // Los coeficientes serán los b_i tales que p(x) = (1,x) · b. Teniendo b n+1 dimensiones si hay n atributos. 
        // El numero de atributos INCLUYE la clase, por eso tiene n+1 de dimensión. 
        coef = new HashMap<>();
        valoresParaNominales = new HashMap<>();
        
        double terminoIndependiente = 0;
        
        coef.put("Class", terminoIndependiente);
        
        
 
    }

    
    @Override
    public HashMap<String, Double> clasifica(Datos datosTest) {
        HashMap<String,Double> retval = new HashMap<>();
        String clas_max;
        double val,val_max;
        for (HashMap<String, dataStructure> line : datosTest.getDatos()){
		clas_max = "";
		val_max = Double.NEGATIVE_INFINITY;
        	for (String clas : datosTest.getClases().keySet()){
                	val = escalarProdHash(convertHashFromDiscToCont(line), coef);
			val = 1/(1+Math.exp(-val));
	                if (val >= val_max){
        		        clas_max = clas;
		                val_max = val;
        	        }
            	}
         	AAUtils.AddOrCreate(retval, clas_max, 1);
        }
        return retval;
    }
    
    /**
     * Producto escalar entre 2 hashes coordenada a coordenada utilizando la clave para asegurar.
     * @param a :   Primer vector del argumento.
     * @param b :   segundo vector para multiplicar.
     *              Se recomienda el vector de pesos como segundo argumento.
     * @return
     */
    private double escalarProdHash(HashMap <String,Double> a, HashMap <String,Double> b){
        double acum = 0,debug1,debug2;
        if (a == null || b == null)
            return Double.NaN;
        if (a.isEmpty() || b.isEmpty() || (a.size() != b.size()))
            return Double.NaN;
             
        for ( String key : a.keySet()){
            if (!b.containsKey(key))
                return Double.NaN;
	    debug1 = a.get(key);
	    debug2 = b.get(key);
            acum += a.get(key)* b.get(key);
        }
        
        return acum;
    }
    
    /**
     * Convierte un hashmap con valores discretos a un hashmap de valores continuos.
     * @param line  :   Vector de atributos 
     * @return 
     */
    private HashMap<String, Double> convertHashFromDiscToCont(HashMap<String, dataStructure> line) {
        HashMap<String, Double> retval = new HashMap<>();
        double val = 0;
        for (Map.Entry<String, dataStructure> entry : line.entrySet()){
            if (entry.getValue().getTipoAtributo() == TiposDeAtributos.Continuo){
                val = (Double)entry.getValue().getVal();
            }else {
                /*Si es nominal, convertimos (aunque tal vez este caso no se de nunca)*/
                 val = convertCont(entry.getKey(),(String) entry.getValue().getVal());
            }
            retval.put(entry.getKey(), val);
        }
        
        return retval;
    }

    /**
     * Convierte un atributo nominal en un atributo continuo utilizando los pesos construidos en el entrenamiento.
     * 
     * @param atributo        : nombre del atributo que convertir
     * @param valorNominal    : valor nominal para ser convertido a double 
     * @return valor double correspondiente al valorNominal.
     */
    private double convertCont(String atributo, String valorNominal) {
            double val = 0;
        
            if ("Class".equals(atributo))
                val = 1;
            else{
                val = valoresParaNominales.get(atributo + STR_DELIM + valorNominal);
            }
            return val;
    }


	    /**
	     * Método necesario para los test.
	     * @param coeficientes 
	     */
	void setCoef(HashMap<String, Double> coeficientes) {
		coef = coeficientes;
	}

	public HashMap<String, Double> getCoef() {
		return coef;
	}

    

}
