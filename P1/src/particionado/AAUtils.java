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
    
    
}
