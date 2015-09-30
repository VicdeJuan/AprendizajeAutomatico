package datos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import particionado.Particion;
public class Datos {
        final private int numDatos;
        
	ArrayList<TiposDeAtributos> tipoAtributos;
	ArrayList<dataStructure[]> datos;
        
        public int getNumDatos(){
            return numDatos;
        }
	public Datos(int numDatos, ArrayList<TiposDeAtributos> tipos, ArrayList<dataStructure[]> datos) {
            this.numDatos = numDatos;
            this.tipoAtributos = tipos;
            this.datos = datos;
            
	}
        
        private static ArrayList getSList(List src,ArrayList<Integer> indices){
            ArrayList toret = new ArrayList<>();
            
            for (int i : indices)
                toret.add(src.get(i));
            
            return toret;
                    
        }
        
        private Datos extraeDatosGen(Particion idx, boolean Train){
            ArrayList<dataStructure[]> d;
            if (Train)
                d = Datos.getSList(datos, idx.getIndicesTrain());
            else
                d = Datos.getSList(datos, idx.getIndicesTest());
            return new Datos(d.size(), tipoAtributos, d);
        }
        
	public Datos extraeDatosTrain(Particion idx) {
            return extraeDatosGen(idx,true);
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
                        double val;
                        
                        while ((sCurrentLine = br.readLine()) != null) {
                            j=0;
                            dataStructure[] add = new dataStructure[Atrb.size()];
                            
                            for (String str : Arrays.asList(sCurrentLine.split("\\s*,\\s*"))){
                                
                                if (Atrb.get(j) == TiposDeAtributos.Continuo){
                                    if ("?".equals(str)) val = Double.NaN;
                                    else val = Double.parseDouble(str);
                                    add[j] = new dataStructure(val,Atrb.get(j));
                                }
                                else
                                    add[j] = new dataStructure(str,Atrb.get(j));
                                j++;
                            }
                            i++;
                            toAdd.add(add);
			}
                        
                        return new Datos(nDatos, Atrb,toAdd);        
		} catch (IOException e) {
			e.printStackTrace();
                        return null;
		} 
                
                
	}
}