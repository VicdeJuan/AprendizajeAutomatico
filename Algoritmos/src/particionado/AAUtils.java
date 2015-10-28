/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particionado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vicdejuan
 */
public class AAUtils {
    public static ArrayList<Integer> seq(int num, boolean shuffle){
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i< num;i++)
            ints.add(i);
        
        if (shuffle)
            Collections.shuffle(ints);
        
        return ints;
    }
    
    public static void AddOrCreate(HashMap<String,Double> h, String key, double value){
            if (!h.containsKey(key))
                h.put(key,value);
            else{
                h.put(key, h.get(key)+value);
            }
            
        }

    
    public static void AddOrCreate(HashMap<String,Integer> h, String key, Integer value){
            if (!h.containsKey(key))
                h.put(key,value);
            else{
                h.put(key, h.get(key)+value);
            }
            
        }


    public static void updateSMT(HashMap<String, HashMap<String, HashMap<String,Double>>> SMT, String actualClass, String atrb, String firstIdx, double firstVal) {
     HashMap<String, HashMap<String,Double>> toAdd;
        
        if (!SMT.containsKey(actualClass)){    
            toAdd = new HashMap<>();
        } else{
            toAdd = SMT.get(actualClass);
            
        }
        
        if (toAdd.containsKey(atrb)){
            AAUtils.AddOrCreate(toAdd.get(atrb), firstIdx, firstVal);
            
        }else{
            HashMap <String,Double> pair = new HashMap<>();
            AAUtils.AddOrCreate(pair, firstIdx, firstVal);
            toAdd.put(atrb, pair);
        }
        SMT.put(actualClass, toAdd);        
        
    }
    
    public static void updateSMT(HashMap<String, HashMap<String, HashMap<String,Double>>> SMT, String actualClass, String atrb, String firstIdx, double firstVal, String secondIdx, double secondVal) {
        AAUtils.updateSMT(SMT, actualClass, atrb, firstIdx, firstVal);
        AAUtils.updateSMT(SMT, actualClass, atrb, secondIdx, secondVal);
    }

        /**
     * Producto escalar entre 2 hashes coordenada a coordenada utilizando la
     * clave para asegurar.
     *
     * @param a : Primer vector del argumento.
     * @param b : segundo vector para multiplicar. Se recomienda el vector de
     * pesos como segundo argumento.
     * @return
     */
    public static double escalarProdHash(HashMap<String, Double> a, HashMap<String, Double> b) {
        double acum = 0, debug1, debug2;
        if (a == null || b == null) {
            return Double.NaN;
        }
        if (a.isEmpty() || b.isEmpty() || (a.size() != b.size())) {
            return Double.NaN;
        }

        for (String key : a.keySet()) {
            if (!b.containsKey(key)) {
                return Double.NaN;
            }
            debug1 = a.get(key);
            debug2 = b.get(key);
            acum += a.get(key) * b.get(key);
        }

        return acum;
    }
    /**
     * 
     * @param v1
     * @param v2
     * @return 
     */
	public static HashMap<String, Double> restaHash(HashMap<String, Double> v1, HashMap<String, Double> v2) {
        HashMap<String, Double> toret = new HashMap<>();
        for (String key : v1.keySet()) {
            toret.put(key, v1.get(key) - v2.get(key));
        }

        return toret;
    }
/**
 * 
 * @param esc
 * @param vector
 * @return 
 */
    public static HashMap<String, Double> linearProdHash(double esc, HashMap<String, Double> vector) {
        HashMap<String, Double> toret = new HashMap<>();
        for (Map.Entry<String, Double> entry : vector.entrySet()) {
            toret.put(entry.getKey(), esc * entry.getValue());
        }
        return toret;
    }

	public static double restaCuadradoHash (HashMap<String,Double> v1, HashMap<String,Double> v2){
	
	return 0;
	}

}
