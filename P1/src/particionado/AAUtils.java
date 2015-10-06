/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particionado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
    
}
