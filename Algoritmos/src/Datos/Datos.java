package Datos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import particionado.Particion;

public class Datos {
	
	public enum TiposDeAtributos {Continuo, Nominal};
	
	//Partimos de un fichero .data con x columnas e y filas
	ArrayList<TiposDeAtributos> tipoAtributos; // el tipo de atirbuto de las priemras x-1 columnas (segunda fila del fichero)
	ArrayList<ArrayList<String>> datos; // las x columnas y las y-2 ultimas filas (quitamos la primera fila que son los nombres de los atirbutos y la segudna que es el tipo
	ArrayList<String> nombreAtributos; //EL nombre de cada atributo de las primeras x-1 columnas, la priemra fila del fichero
	ArrayList<Integer> numClases; //Numero de veces que aparece cada clase
	int num_datos; //numero de datos
	private ArrayList<String> clases; // ultima columna, las clases de cada dato
	private HashMap<String,ArrayList<String>> a; //relaciona nombre de atirbuto con su conjunto de posibles valores si es nominal

	
	public ArrayList<String> getNombreAtrs(){
		return this.nombreAtributos;
	}
	
	public Datos(int numDatos, ArrayList<TiposDeAtributos> tipos) {
		
		
		this.datos = new ArrayList<ArrayList<String>>();
		this.tipoAtributos = tipos;
		this.nombreAtributos = new ArrayList<String>();
		this.clases = new ArrayList<String>();
		this.num_datos = numDatos;		

		
	}
	
	public HashMap<String,ArrayList<String>>  getA(){
		return this.a;
	}
	
	public ArrayList<ArrayList<String>> getDatos(){
		return this.datos;
	}
	
	public ArrayList<String> getClases(){
		return this.clases;
	}
	
	
	
	public ArrayList<TiposDeAtributos> getTipoAtributos(){
		return this.tipoAtributos;
	}
	
        public Datos (ArrayList<ArrayList<String>> data, ArrayList<TiposDeAtributos> tipos, ArrayList<String> clases, ArrayList<String> NombreAtributos){
		this.datos = data;
		this.tipoAtributos = tipos;
		this.clases = clases;
		this.nombreAtributos = NombreAtributos;
		this.num_datos = data.size();
                
        }
	
	public Datos(){
	
		
	}
	

	public Datos extraeDatosTrain(Particion idx) {
		
		Datos datos = new Datos(idx.getIndicesTrain().size(),this.getTipoAtributos());
		for (Integer i : idx.getIndicesTrain())
			datos.datos.add(this.getDatos().get(i));
		
		
		datos.setNombAtr(this.nombreAtributos);
		datos.setA(this.getA());
		datos.setTipoAtr(this.getTipoAtributos());
		datos.obtenerClases();
		datos.calcularNumClases();
		
		return datos;
	}
	
	public void setTipoAtr(ArrayList<TiposDeAtributos> tipoAtr){
		this.tipoAtributos = tipoAtr;
	}
	
	public void setA( HashMap<String,ArrayList<String>> hash){
		this.a = hash;
	}
	
	public void setNombAtr(ArrayList<String> nombAt){
		this.nombreAtributos = nombAt;
	}

	public Datos extraeDatosTest(Particion idx) {
		
		Datos datos = new Datos(idx.getIndicesTest().size(),this.getTipoAtributos());
		//datos.datos = new ArrayList<String>();
		for (Integer i : idx.getIndicesTest())
			datos.datos.add(this.getDatos().get(i));
		
		
		datos.obtenerClases();
		datos.calcularNumClases();
		return datos;
		
	}
	
	public static Datos cargaDeFichero(String nombreDeFichero) {
		
		Datos datos = null;
		ArrayList<String> clases = new ArrayList<String>();
		ArrayList<Integer> numClases = new ArrayList<Integer>();

		int numDatos;
		int indiceClase =0;
		//int cont=0;
		ArrayList<TiposDeAtributos> atributos = new ArrayList<TiposDeAtributos> ();

		HashMap<String,ArrayList<String>> a= new HashMap<String,ArrayList<String>>();
		
		
		BufferedReader br = null;

		try {

			String tiposAtributos, nombreAtributos;
			

			br = new BufferedReader(new FileReader(nombreDeFichero));

			numDatos = Integer.parseInt(br.readLine());
			nombreAtributos = br.readLine();
			List<String> LstNomAtr =  Arrays.asList(nombreAtributos.split(","));


			tiposAtributos = br.readLine();
			List<String> LstTpAtr = Arrays.asList(tiposAtributos.split(","));
			
			int cuenta;
			for (cuenta=0; cuenta<LstNomAtr.size()-1;cuenta++){
				a.put(LstNomAtr.get(cuenta), new ArrayList<String>());
			}
			indiceClase = cuenta;
			
			
			//for(String el: LstNomAtr){
			//	if(el.equals("Class"))
			//		indiceClase = cont;
			//	cont++;
			//}
			
			for (cuenta=0; cuenta<LstTpAtr.size()-1; cuenta++){
				if(LstTpAtr.get(cuenta).equals("Continuo"))
					atributos.add(TiposDeAtributos.Continuo);
				else atributos.add(TiposDeAtributos.Nominal);
			}
			atributos.add(TiposDeAtributos.Nominal);
			
			//for(String st : LstTpAtr){
			//	if (st.equals("Continuo"))
			//		atributos.add(TiposDeAtributos.Continuo);
			//	else atributos.add(TiposDeAtributos.Nominal);
			//}
			
			datos = new Datos(numDatos,atributos);
			
			for(String nombre : LstNomAtr)
				datos.nombreAtributos.add(nombre);
			
			int i=0,j=0;
			int contClm = 0;
			int numDatosAux = numDatos;
			
			
			
			while (i<numDatos) {
				contClm = 0;
				String prueba = br.readLine();
	
				List<String> Datos =  Arrays.asList(prueba.split(","));
				if(Datos.contains("?")){
					numDatosAux--;
					i++;
					continue;
				}
				datos.getDatos().add(new ArrayList<String>());
				for(String el : Datos){
					//datos.getDatos().add(new ArrayList<String>());
					datos.getDatos().get(j).add(el);
					if(Datos.indexOf(el)==indiceClase){
						if(!clases.contains(el)){
							clases.add(el);
							numClases.add(1);
						}else{
							numClases.set(clases.indexOf(el),numClases.get(clases.indexOf(el))+1);
						}
					}else
						if(atributos.get(Datos.indexOf(el)).equals(TiposDeAtributos.Nominal))
							for(String str : a.keySet()){
								if(str.equals(LstNomAtr.get(contClm))){
									if(!a.get(str).contains(el)){
										a.get(str).add(el);	
										a.put(str,a.get(str));
									}
									break;
								}
									
							}
								
								contClm++;
							}
				j++;
							
				i++;	
				}
				
			
			datos.clases = clases;
			datos.numClases = numClases;
			datos.a = a;
			datos.setNumDatos(numDatosAux);
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		}
		
		return datos;


	}
	
	public void imprimirDatos(){
		for(ArrayList<String> s : this.datos){
			for(String el : s)
				System.out.printf(el+"\t");
			System.out.printf("\n");
		}
			
	}
	
	public void setNumDatos(int numDatos){
		this.num_datos = numDatos;
	}
	
	public int obtNumCoinc(String clase, String atributo, String valor){
		
		int numCoin = 0;
		for(ArrayList<String> fila : this.datos)
			if(clase.equals(obtClaseFila(fila)))
				if(esIgualAtrAValor(fila,atributo,valor))
						numCoin ++;
	
		return numCoin;
			
		}
	
	
	public String obtClaseFila(ArrayList<String> fila){
		return fila.get(this.tipoAtributos.size()-1);
		
	}
	
	public int obtIndiceAtr(String atributo){
		int i = 0;
		for(String atr : this.nombreAtributos){
			if(atr.equals(atributo))
				return i;
			i++;
		}
		 return 0; 
	}
	
	public boolean esIgualAtrAValor(ArrayList<String> fila,String atributo, String valor){
		
		return fila.get(obtIndiceAtr(atributo)).equals(valor) ? true :false;
		
	}
	
	
	public ArrayList<Integer> getNumClases(){
		return this.numClases;
	}
	
	
	public void calcularNumClases(){
		
		this.numClases = new ArrayList<Integer>(Collections.nCopies(this.getClases().size(), 0));
		for(ArrayList<String> fila : this.getDatos()){
			String clase = this.obtClaseFila(fila);
			int indiceClase = this.getClases().indexOf(clase);
			numClases.set(indiceClase,numClases.get(indiceClase)+1);
			
		}
	}
	
	
	public void obtenerClases(){
		for(ArrayList<String> fila : this.getDatos()){
			String clase = this.obtClaseFila(fila);
			if(!this.getClases().contains(clase))
				this.getClases().add(this.obtClaseFila(fila));
		}
		
	}
	
	public ArrayList<Double> probClases(){
		
		ArrayList<Double> probs = new ArrayList<Double>();
		for(int i=0;i<this.getClases().size();i++)
			probs.add((double)this.getNumClases().get(i)/this.num_datos);
		return probs;
	}
		
	
	
	
	public static int sumArrayElems(ArrayList<Integer> lista){
		
		int i;
		int sum = 0;
		for(i = 0; i < lista.size(); i++)
		    sum += lista.get(i);
		return sum;
	
	}
	
	
	public static double normalDistribution(double x, double mean, double var){
		
		return (1/(Math.pow(2 * var* Math.PI, 0.5))) * (Math.pow(Math.E,(- (Math.pow(x-mean,2))/(2*var)))) ;
				
	}
	
	public static double mediaDatos(Datos datos, String clase, String atributo){
		
		double total = 0;
		int cont = 0;
		for(ArrayList<String> fila: datos.getDatos()){
			if(datos.obtClaseFila(fila).equals(clase)){
				if(!fila.get(datos.getNombreAtrs().indexOf(atributo)).equals("?")){
					total += Double.parseDouble(fila.get(datos.getNombreAtrs().indexOf(atributo)));
					cont++;
				}
			}
				
		}
		
		return (double) total/cont;
		
	}
	
public static double media(Datos datos, String atributo){
		
		double total = 0;
		int cont = 0;
		for(ArrayList<String> fila: datos.getDatos()){
			if(!fila.get(datos.getNombreAtrs().indexOf(atributo)).equals("?")){
				total += Double.parseDouble(fila.get(datos.getNombreAtrs().indexOf(atributo)));
				cont++;
			}				
		}
		
		return (double) total/cont;
		
	}
	
	
	public static double VarDatos(Datos datos, String clase, String atributo, double media){
		
		double valor;
		double sumaCuadrados = 0;
		int cont = 0;
		for(ArrayList<String> fila: datos.getDatos()){
			if(datos.obtClaseFila(fila).equals(clase)){
				if(!fila.get(datos.getNombreAtrs().indexOf(atributo)).equals("?")){
					valor = Double.parseDouble(fila.get(datos.getNombreAtrs().indexOf(atributo)));
					sumaCuadrados += Math.pow(valor-media,2);
					cont++;
				}
			}
				
		}
		
		return (double) sumaCuadrados/cont;
	}
	
public static double varianza(Datos datos, String atributo, double media){
		
		double valor;
		double sumaCuadrados = 0;
		int cont = 0;
		for(ArrayList<String> fila: datos.getDatos()){
			if(!fila.get(datos.getNombreAtrs().indexOf(atributo)).equals("?")){
				valor = Double.parseDouble(fila.get(datos.getNombreAtrs().indexOf(atributo)));
				sumaCuadrados += Math.pow(valor-media,2);
				cont++;
			}				
		}
		
		return (double) sumaCuadrados/cont;
	}
	
	public boolean isContinuos(String atr){
		
		TiposDeAtributos tipoAtr = null;
		for(String at: this.getNombreAtrs()){
			if(at.equals(atr))
				tipoAtr = this.getTipoAtributos().get(this.getNombreAtrs().indexOf(at));
		}
		
		return tipoAtr == TiposDeAtributos.Continuo ? true : false;
		
	}
	
	public int getNumDatos(){
		return this.num_datos;
	}
	
	

}
