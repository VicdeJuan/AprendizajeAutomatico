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
    
    public static void AddOrCreate(HashMap<String,Integer> h, String key, Integer value){
            if (!h.containsKey(key))
                h.put(key,1);
            else{
                h.put(key, h.get(key)+value);
            }
            
        }

    public static void AddOrCreateDoubleArray(HashMap<String,double[]> h, String key, int firstIdx, double firstVal, int secondIdx, double secondVal){
        double[] frecToAdd = new double[2];    
        if (!h.containsKey(key)){
            frecToAdd[firstIdx] = firstVal;
            frecToAdd[secondIdx] = secondVal;
        }
        else{
            frecToAdd = h.get(key);
            frecToAdd[firstIdx] = firstVal + h.get(key)[firstIdx];
            frecToAdd[secondIdx] = secondVal + h.get(key)[secondIdx];
        }
        h.put(key, frecToAdd);
    }

    public static void updateSMT(HashMap<String, HashMap<String, double[]>> SMT, String actualClass, String atrb, int firstIdx, double firstVal, int secondIdx, double secondVal) {
        HashMap<String, double[]> toAdd;
        
        if (!SMT.containsKey(actualClass)){    
            toAdd = new HashMap<>();
        } else{
            toAdd = SMT.get(actualClass);
            
        }
        AAUtils.AddOrCreateDoubleArray(toAdd, atrb, firstIdx, firstVal, secondIdx, secondVal);
        SMT.put(actualClass, toAdd);
        
    }
    
    
}
