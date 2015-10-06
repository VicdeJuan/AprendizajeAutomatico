package datos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import particionado.AAUtils;
import particionado.Particion;
public class Datos {
        final private int numDatos;
        
	HashMap<String,TiposDeAtributos> tipoAtributos;
	ArrayList<HashMap<String,dataStructure>> datos;
        HashMap<String,Integer> clases;
        ArrayList<String> nomDatos;
        public int getNumDatos(){
            return numDatos;
        }
	public Datos(int numDatos, HashMap<String,TiposDeAtributos> tipos, ArrayList<HashMap<String,dataStructure>> datos,HashMap<String,Integer> classes,ArrayList<String> nombreDatos) {
            this.numDatos = numDatos;
            this.tipoAtributos = tipos;
            this.datos = datos;
            this.clases = classes;
            this.nomDatos = nombreDatos;
            
	}
        public String getClassFromRow(HashMap<String,dataStructure> row){
            // la clase es el último atributo del array SIEMPRE.
            return (String) row.get("Class").getVal();
        }

        public ArrayList<String> getNomDatos() {
            return nomDatos;
        }
        
        
        
        public HashMap<String,TiposDeAtributos> getTipoAtributos() {
            return tipoAtributos;
        }

        public ArrayList<HashMap<String,dataStructure>> getDatos() {
            return datos;
        }

        public HashMap<String, Integer> getClases() {
            return clases;
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
                        ArrayList<String> nomDatos =  new ArrayList<>(Arrays.asList(sCurrentLine.split("\\s*,\\s*")));
                        // La tercera linea son los tipos de datos.
                        sCurrentLine = br.readLine();
                        HashMap<String,TiposDeAtributos> Atrb = new HashMap<>();
                        int i=0;
                        for (String str : Arrays.asList(sCurrentLine.split("\\s*,\\s*"))){
                            Atrb.put(nomDatos.get(i),TiposDeAtributos.valueOf(str));
                            i++;
                        }
                            
                                
                        
                        int j=0;
                        
                        // Empezamos a parsear los datos.
                        ArrayList<HashMap<String,dataStructure>> toAdd = new ArrayList<>(); 
                        HashMap<String,Integer> clases = new HashMap<>();
                        String clase = null;
                        double val;
                        boolean skip;
                        int jumpedRows = 0;
                        
                        while ((sCurrentLine = br.readLine()) != null) {
                            j=0;
                            HashMap<String,dataStructure> add = new HashMap<>();
                            skip = false;
                            for (String str : Arrays.asList(sCurrentLine.split("\\s*,\\s*"))){
                                if ("?".equals(str)) {
                                        skip = true;
                                        jumpedRows++;
                                        break;
                                }
                                
                                if (Atrb.get(nomDatos.get(j)) == TiposDeAtributos.Continuo){
                                    val = Double.parseDouble(str);
                                    add.put(nomDatos.get(j),new dataStructure(val,Atrb.get(nomDatos.get(j))));                                    
                                }
                                else
                                    add.put(nomDatos.get(j),new dataStructure(str,Atrb.get(nomDatos.get(j))));                                    
                                // Si hay interrogación en algún atributo, nos saltamos la fila como hacen los
                                // algoritmos de Weka y R.
                                j++;
                                //TODO: chapucilla...
                                //En la última iteración del bucle, str tendrá la clase.
                                clase = str;
                            }
                            
                            
                            if (!skip){
                                AAUtils.AddOrCreate(clases, clase, 1);
                                toAdd.add(add);
                            }                            
			}
                        
                        return new Datos(nDatos-jumpedRows, Atrb,toAdd,clases,nomDatos);        
		} catch (IOException e) {
			e.printStackTrace();
                        return null;
		} 
                
                
	}

 

    private Datos extraeDatosGen(Particion idx, boolean train) {
            ArrayList<HashMap<String,dataStructure>> values = new ArrayList<>();
            HashMap<String,Integer> clasesToRet = new HashMap<>();
            ArrayList<Integer> indices = null;
            if (train)
                indices = idx.getIndicesTrain();
            else
                indices = idx.getIndicesTest();
                
            
            for (int i : indices){
                HashMap<String,dataStructure> arr = datos.get(i);
                values.add(arr);
                AAUtils.AddOrCreate(clases, this.getClassFromRow(arr), 1);
            }
   
            return new Datos(indices.size(), tipoAtributos, values, clasesToRet,nomDatos);
    }
}