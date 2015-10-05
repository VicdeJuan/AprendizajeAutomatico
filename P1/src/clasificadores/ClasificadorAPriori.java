package clasificadores;

import datos.Datos;
import datos.dataStructure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import particionado.AAUtils;

public class ClasificadorAPriori extends Clasificador {
    
    private String ClaseMasRepetida;

    public String getClaseMasRepetida() {
        return ClaseMasRepetida;
    }
    
    
    
    @Override
    public void entrenamiento(Datos datostrain) {
        HashMap<String,Integer> classes = new HashMap();
        for (dataStructure[] row : datostrain.getDatos()) {
            String clase = datostrain.getClassFromRow(row);
            AAUtils.AddOrCreate(classes, clase, 1);
        }
        
        ClaseMasRepetida = Collections.max(
                classes.entrySet(),
                new Comparator<Entry<String,Integer>>(){
                    @Override
                    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                        return o1.getValue() > o2.getValue()? 1:-1;
                    }
                }).getKey();
        
        
    }

    @Override
    public HashMap<String,Integer> clasifica(Datos datos) {
        // Asigno la clase mayoritaria a todos los datos
        HashMap<String,Integer> toret = new HashMap<>();
        toret.put(ClaseMasRepetida,datos.getNumDatos());
            
        return toret;
    }
}
