package datos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import particionado.Particion;
public class Datos {
        private int numDatos;
        private ArrayList<String> nombreDatos;
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
	public Datos extraeDatosTrain(Particion idx) {
                
		return null;
	}
	public Datos extraeDatosTest(Particion idx) {
		return null;
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