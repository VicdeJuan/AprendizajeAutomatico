package datos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import particionado.Particion;
public class Datos {
        final private int numDatos;
        
	ArrayList<TiposDeAtributos> tipoAtributos;
	ArrayList<dataStructure[]> datos;
        HashMap<String,Integer> clases;
        
        public int getNumDatos(){
            return numDatos;
        }
	public Datos(int numDatos, ArrayList<TiposDeAtributos> tipos, ArrayList<dataStructure[]> datos,HashMap<String,Integer> classes) {
            this.numDatos = numDatos;
            this.tipoAtributos = tipos;
            this.datos = datos;
            this.clases = classes;
            
	}
        public String getClass(dataStructure[] row){
            // la clase es el último atributo del array SIEMPRE.
            return (String) row[row.length-1].getVal();
        }

        public ArrayList<TiposDeAtributos> getTipoAtributos() {
            return tipoAtributos;
        }

        public ArrayList<dataStructure[]> getDatos() {
            return datos;
        }
        
	public Datos extraeDatosTrain(Particion idx) {
            return extraeDatosGen(idx, true);
	}
        
	public Datos extraeDatosTest(Particion idx) {
            return extraeDatosGen(idx,false);
	}
        
	public static Datos cargaDeFichero(String nombreDeFichero) {
            
            try (BufferedReader br = new BufferedReader(new FileReader(nombreDeFichero)))
		{
                        // La primera línea siempre será el número de datos.
			String sCurrentLine = br.readLine();
                        int nDatos = (int) Double.parseDouble(sCurrentLine);
                        // La segunda linea son los nombres de los datos.
                        sCurrentLine = br.readLine();
                        List<String> nomDatos =  Arrays.asList(sCurrentLine.split("\\s*,\\s*"));
                        // La tercera linea son los tipos de datos.
                        sCurrentLine = br.readLine();
                        ArrayList<TiposDeAtributos> Atrb = new ArrayList<TiposDeAtributos>();
                        
                        for (String str : Arrays.asList(sCurrentLine.split("\\s*,\\s*")))
                            Atrb.add(TiposDeAtributos.valueOf(str));
                        
                        int i=0,j=0;
                        
                        ArrayList<dataStructure[]> toAdd = new ArrayList<>(); 
                        HashMap<String,Integer> clases = new HashMap<>();
                        String clase = null;
                        double val;
                        boolean skip = false;
                        
                        while ((sCurrentLine = br.readLine()) != null) {
                            j=0;
                            dataStructure[] add = new dataStructure[Atrb.size()];
                            skip = false;
                            for (String str : Arrays.asList(sCurrentLine.split("\\s*,\\s*"))){
                                
                                if (Atrb.get(j) == TiposDeAtributos.Continuo){
                                    if ("?".equals(str)) {
                                        skip = true;
                                        break;
                                    }
                                    else{   
                                        val = Double.parseDouble(str);
                                        add[j] = new dataStructure(val,Atrb.get(j));
                                    }
                                    
                                }
                                else
                                    add[j] = new dataStructure(str,Atrb.get(j));
                                // Si hay interrogación en algún atributo, nos saltamos la fila como hacen los
                                // algoritmos de Weka y R.
                                j++;
                                //TODO: chapucilla...
                                //En la última iteración del bucle, str tendrá la clase.
                                clase = str;
                            }
                            
                            
                            if (!skip){
                                clases.put(clase, 1);
                                toAdd.add(add);
                            }
                                
                            i++;
                            
			}
                        
                        return new Datos(nDatos, Atrb,toAdd,clases);        
		} catch (IOException e) {
			e.printStackTrace();
                        return null;
		} 
                
                
	}

 

    private Datos extraeDatosGen(Particion idx, boolean train) {
            ArrayList<dataStructure[]> values = new ArrayList<>();
            HashMap<String,Integer> clasesToRet = new HashMap<>();
            ArrayList<Integer> indices = null;
            if (train)
                indices = idx.getIndicesTrain();
            else
                indices = idx.getIndicesTest();
                
            
            for (int i : indices){
                dataStructure[] arr = datos.get(i);
                values.add(arr);
                clasesToRet.put(this.getClass(arr),1);
            }
   
            return new Datos(indices.size(), tipoAtributos, values, clasesToRet);
    }
}