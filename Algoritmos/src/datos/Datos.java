package datos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import particionado.AAUtils;
import particionado.Particion;
public class Datos {
        final private int numDatos;
        int numAtributos;
	HashMap<String,TiposDeAtributos> tipoAtributos;
	ArrayList<HashMap<String,dataStructure>> datos;
        HashMap<String,Double> clases;
        ArrayList<String> nomDatos;
	
        public int getNumDatos(){
            return numDatos;
        }
	public Datos(int numDatos, HashMap<String,TiposDeAtributos> tipos, ArrayList<HashMap<String,dataStructure>> datos,HashMap<String,Double> classes,ArrayList<String> nombreDatos ) {
            this.numDatos = numDatos;
            this.tipoAtributos = tipos;
            this.datos = datos;
            this.clases = new HashMap<>(classes);
            this.nomDatos = nombreDatos;
            numAtributos = tipos.size();            
	}

        public int getNumAtributos() {
            return numAtributos;
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

        public HashMap<String, Double> getClases() {
            return clases;
        }
        
        
        
	public Datos extraeDatosTrain(Particion idx) {
            return extraeDatosGen(idx, true);
	}
        
	public Datos extraeDatosTest(Particion idx) {
            return extraeDatosGen(idx,false);
	}
        
	public static Datos cargaDeFichero(String nombreDeFichero) {
            
		HashMap <String,Double> medias = new HashMap<>();
		HashMap <String,Double> varianzas = new HashMap<>();
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
                        HashMap<String,Double> clases = new HashMap<>();
                        String clase = null;
                        double val;
                        boolean skip;
                        int jumpedRows = 0;
                        
                        while ((sCurrentLine = br.readLine()) != null) {
                            j=0;
                            HashMap<String,dataStructure> add = new HashMap<>();
                            skip = false;
                            for (String str : Arrays.asList(sCurrentLine.split("\\s*,\\s*"))){
                                // Si hay interrogación en algún atributo, nos saltamos la fila como hacen los
                                // algoritmos de Weka y R.
                                if ("?".equals(str)) {
                                        skip = true;
                                        jumpedRows++;
                                        break;
                                }
                                
                                if (Atrb.get(nomDatos.get(j)) == TiposDeAtributos.Continuo){
                                    val = Double.parseDouble(str);
                                    add.put(nomDatos.get(j),new dataStructure(val,Atrb.get(nomDatos.get(j))));                                    
				    AAUtils.AddOrCreate(medias, nomDatos.get(j), val);
                                }
                                else
                                    add.put(nomDatos.get(j),new dataStructure(str,Atrb.get(nomDatos.get(j))));                                    
                                j++;
                                //TODO: chapucilla...
                                //En la última iteración del bucle, str tendrá la clase.
                                clase = str;
                            }
                            
                            
                            if (!skip){
                                AAUtils.AddOrCreate(clases, clase, 1.0);
                                toAdd.add(add);
                            }                            
			}

			ArrayList<HashMap<String,dataStructure>> toAdd_Normalizado = new ArrayList<>();
			HashMap<String, dataStructure> lineaNormalizada = new HashMap<>();
			/**
			 * Normalizamos los datos
			*/
			// Calculamos las medias:
			for (Map.Entry<String, Double> mu : medias.entrySet()){
				medias.put(mu.getKey(), mu.getValue()/(nDatos-jumpedRows));
			}
			
			// Calculamos las varianzas:
			HashMap<String,Double> var_aux = new HashMap<>();
			for (HashMap<String, dataStructure> line : toAdd){
				
				for (String  key : line.keySet()){
					dataStructure entry = line.get(key);
					if (line.get(key).getTipoAtributo() == TiposDeAtributos.Nominal)
						continue;
					val = (Double) entry.getVal();
					val = Math.pow(val - medias.get(key),2);
					AAUtils.AddOrCreate(var_aux, key, val);
				}
			}
			for (Map.Entry<String, Double> sig : var_aux.entrySet()){
				varianzas.put(sig.getKey(), sig.getValue()/(nDatos-jumpedRows));
			}	
			
			// Normalizamos en sí:
			
			
			double aux;
			for (i = 0; i < toAdd.size(); i++){
				HashMap<String, dataStructure> line = toAdd.get(i); 	
				lineaNormalizada.clear();
				for (Map.Entry<String, dataStructure> entry : line.entrySet()){
					if (entry.getValue().getTipoAtributo() == TiposDeAtributos.Nominal){
						lineaNormalizada.put(entry.getKey(),entry.getValue());
					}else{
						aux = (Double) entry.getValue().getVal();
						aux = aux - medias.get(entry.getKey());
						aux = aux / Math.sqrt(varianzas.get(entry.getKey())) ;
						lineaNormalizada.put(entry.getKey(), new dataStructure(aux, TiposDeAtributos.Continuo));
					}
				}	
				toAdd_Normalizado.add(new HashMap<>(lineaNormalizada));
			}
			/*
			Calculas las medias por si acaso:
			
			HashMap <String,Double> medias_aux = new HashMap<>();
			for (HashMap<String, dataStructure> line : toAdd_Normalizado)
			for (Map.Entry<String, dataStructure> entry : line.entrySet()){
				if (entry.getValue().getTipoAtributo() != TiposDeAtributos.Nominal)
					AAUtils.AddOrCreate(medias_aux, entry.getKey(), (Double) entry.getValue().getVal());
			}
			for (Map.Entry<String, Double> entry : medias_aux.entrySet()){
				medias.put(entry.getKey(), entry.getValue()/(nDatos-jumpedRows));
			}*/
                        return new Datos(nDatos-jumpedRows, Atrb,toAdd_Normalizado,clases,nomDatos);        
			
			
                        //return new Datos(nDatos-jumpedRows, Atrb,toAdd,clases,nomDatos);        
		} catch (IOException e) {
			e.printStackTrace();
                        return null;
		} 
                
                
	}

 

    private Datos extraeDatosGen(Particion idx, boolean train) {
            ArrayList<HashMap<String,dataStructure>> values = new ArrayList<>();
            HashMap<String,Double> clasesToRet = new HashMap<>();
            ArrayList<Integer> indices = null;
            if (train)
                indices = idx.getIndicesTrain();
            else
                indices = idx.getIndicesTest();
                
            
            for (int i : indices){
                HashMap<String,dataStructure> arr = datos.get(i);
                values.add(arr);
                AAUtils.AddOrCreate(clasesToRet, this.getClassFromRow(arr), 1);
            }
   
            return new Datos(indices.size(), tipoAtributos, values, clasesToRet,nomDatos);
    }
}
