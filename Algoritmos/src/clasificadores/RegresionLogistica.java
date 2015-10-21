/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.dataStructure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vicdejuan
 */
public class RegresionLogistica extends Clasificador {

    ArrayList<Double> coef;
    
    @Override
    public void entrenamiento(Datos datosTrain) {
        // Los coeficientes serán los b_i tales que p(x) = (1,x) · b. Teniendo b n+1 dimensiones si hay n atributos. 
        // El numero de atributos INCLUYE la clase, por eso tiene n+1 de dimensión. 
        coef = new ArrayList<>(datosTrain.getNumAtributos());

        
 
    }

    @Override
    public HashMap<String, Double> clasifica(Datos datosTest) {
        HashMap<String,Double> toret = new HashMap<>();
        for (Map.Entry<String, Double> a : datosTest.getClases().entrySet()){
            String key = a.getKey();
            // Para la clase KEY iteramos por los campos.
            for (HashMap<String, dataStructure> line : datosTest.getDatos()){
                ArrayList<Double> values = Hash2ArrayList(line);
                try {
                    toret.put(key, escalarProd(values,coef));
                } catch (Exception ex) {
                    Logger.getLogger(RegresionLogistica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private double escalarProd(ArrayList <Double> a, ArrayList <Double> b) throws Exception{
        double acum = 0;
        if (a == null || b == null)
            return acum;
        if (a.isEmpty() || b.isEmpty())
            return acum;
        
        if (a.size() != b.size())
            throw new Exception("Las dimensiones de este producto escalar no son las mismas");
        
        for (int i = 0; i<a.size(); i++)
            acum += a.get(i)*b.get(i);
        
        return acum;
    }

    /**
     * Este método devuelve un array de doubles a partir de una línea de datos.
     * 
     * El ArrayList devuelto termina con un 1
     * 
     * TODO: 
     *  La línea de datos contiene valores continuos y discretos. Ahora mismo sólo está implementado con continuos.
     *   
     * 
     * @param line
     * @return 
     */
    private ArrayList<Double> Hash2ArrayList(HashMap<String, dataStructure> line) {
        // Para convertir los datos a un array y facilitar el producto escalar.
        ArrayList<Double> toret = new ArrayList<>();
        double val;
        
        for (String a : line.keySet()){
            if ("Class".equals(a))
                val = 1;
            else
                val = (Double) line.get(a).getVal();
            
            toret.add(val);
        }
            // Aquí habría que añadir un convertidor a continuo de los valores discretos.
            
        return toret;
    }
}
