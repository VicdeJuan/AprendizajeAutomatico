/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import static particionado.EstrategiaParticionado.SEED;


public class Regla {
	
	int size;
	long regla;
	private static final String clases[] = new String[]{"negative","positive"};;
	private static final HashMap<String,Long> nominal_to_bit = createNominal();
	Random r = new Random();
	
	private static HashMap<String,Long> createNominal(){
		HashMap<String,Long> nomin = new HashMap<>();
		
		nomin.put("o", 1L);
		nomin.put("b", 2L);
		nomin.put("x", 4L);
		return nomin;
	}
	
	/**
	 * Return the number of bits from a given number of attributes.
	 * @param n
	 * @return 
	 */
	public int getBitsFromSize(int n){
		return n*3-2;
	}
	
	/**
	 * Constructor. 
	 * Crea una regla ALEATORIA de tamaño n. Los bits que estén más a la 
	 * 	izquierda del n-ésimo deberán ser 0.
	 * @param n número de bits de la regla.
	 */
	public Regla(Integer n){
		// Asignación arbitraria de valores nominal es a números. Como sólo hay 3 posibles valores, necesitamos 2 bits, de ahí valores entre 0 y 3
		
		size = n;
		regla = ((long)(r.nextDouble()*(Math.pow(2,3*n)))-1);	
		
	}	

	public Regla(int n, long rule) {
		// Asignación arbitraria de valores nominal es a números. Como sólo hay 3 posibles valores, necesitamos 2 bits, de ahí valores entre 0 y 3
		
		size = n;
		this.regla = rule; 
	
	}
	
/**
	 * Devuelve un long con todos sus bits 0's, salvo el bit dado por index,
	 * 	que será un 1.
	 * @param index	índice del bit para cambiar.
	 * @return 
	 */
	private long just1at(int index){
		return (long) Math.pow(2, index);
	}
	/**
	 * Devuelve un long con sus bits a 1's salvo el bit dado por index,
	 * 	que será un 0.
	 * @param index
	 * @return 
	 */
	private long just0at(int index){
		return ~just1at(index);
	}
	/**
	 * Asigna a un bit un valor binario concreto.
	 * @param index índice que modificar.
	 * @param value valor binario que asignar.
	 */
	public void set(int index, boolean value){
		if (!value) {
			regla |= just1at(index);
		} else {
			regla &= just0at(index);	
		}
		
	}

	/**
	 * Cambia el valor del bit en la posición index.
	 * @param index índice del bit a cambiar
	 */
	public void mutar(int index){
		/* Si regla tiene un 1 en index, la comparación será cierta y  llamamos a set con True.
		Si la regla tiene un 0, la comparación será falsa y llamamos a set con False*/
		set(index,(regla & (just1at(index))) == just1at(index));
	}
        
        public void mutar(double prob){
        	for (int i=0;i<getBitsFromSize(this.size);i++){
        		if (r.nextDouble() <= prob)
        			mutar(i);
        	}
        }

	/**
	 * La clase se almacena en el último bit válido de la regla.
	 * @return Devuelve la clase que asigna esta regla.
	 */
	public String get_class(){
		//int idx = (int) (regla & just1at(size))>>size;
		int idx = (int) regla % 2;
		return clases[idx];
	}	

	/**
	 * Comprueba si la regla dada como argumento es clasificable. Esto es,
	 * 	si coinciden todos los cromosomas, salvo la clase.
	 * @param rule regla para comprobar coincidencia.
	 * @return La clase asignada en caso de coincidencia. Null en caso de no coincidencia.
	 */
	public String match(Regla rule){
		// Desplazamos 1 bit para ignorar las clases de cada regla, ya que queremos ver si 
		//	coinciden el resto de cromosomas.
		return rule.getRegla() >> 1 == this.getRegla()>>1 ? this.get_class() : null;
	}
	
	/**
	 * Convierte una fila de datos del ficherto tic-tac-toe en una regla.
	 * Método estático para poder ser invocado independientemente de la regla que tengamos.
	 * @param fila	datos de la fila.
	 * @return 	devuelve la regla creada acorde con los datos.
	 */
	public static Regla convert(ArrayList<String> fila){
		String val;
		int n = fila.size();
		long rule = 0L;
		for (int i=0; i<fila.size();i++){
			val = fila.get(i);
			if (nominal_to_bit.containsKey(val))
				rule = (rule << 3) | nominal_to_bit.get(val);
			else{
				if ((fila.get(fila.size()-1).equals(clases[1])) && (val.equals(clases[1]))){
					rule = (rule << 1) | 1;
				}else if ((fila.get(fila.size()-1).equals(clases[0])) && (val.equals(clases[0]))){
					rule = rule << 1;
				} else {
					System.out.println("Esto no deber�a ocurrir nunca!!");
				}
			}
		}
		
		return new Regla(n,rule);
	}
	
	
	public static Long getBitsFromStr(String val){
		if (nominal_to_bit.containsKey(val))
			return nominal_to_bit.get(val);
		else{
			if (val.equals(clases[1])){
				return 1L;
			}else if (val.equals(clases[0])){
				return 0L;
			} else {
				System.err.println("Esto no deber�a ocurrir nunca!!");
				return 0L;
			}
		}
		
	}

	
	private long getRegla() {
		return regla;
	}
	public static int[] generateIdxAddLast(int n,int lim,int last){
		int [] toret = new int[n+1];
		Random r = new Random(SEED);
		int i;
		for (i=0; i<n;i++){
			toret[i] = r.nextInt(lim);
		}
		toret[i] = last;
		Arrays.sort(toret);
		return toret;
		
	}
	/**
	 * Función auxiliar para el cruce en n puntos. Como hay que devolver 2 
	 * 	hijos en un único return, devolvemos todo en el mismo array, pero
	 * 	a partir de un cierto valor (el devuelto por esta función) empieza
	 * 	el segundo hijo.
	 * @param r1
	 * @param r2
	 * @param nPuntos
	 * @return 
	 */
	public static int getDivisor(Regla[]r1,Regla[]r2,int nPuntos){
		if (nPuntos%2 == 0) return r1.length;
		else return r2.length;
	}
	public static Regla[] CruceNPuntos (Regla[] r1 , Regla[] r2, int nPuntos){
		Regla[] toret = new Regla[r1.length + r2.length];
		/**
		 * El array de índices en los que cruzar.
		 * Queremos que caiga dentro de las 2 reglas, por eso ponemos 
		 * 	que los índices aleatorios (ordenados) estén entre 
		 * 	0 y el mínimo.
		 */
		int [] idx = generateIdxAddLast(nPuntos,Math.min(r1.length, r2.length),Math.max(r1.length, r2.length));
		int j=0,i=0;
		int n = getDivisor(r1, r2, nPuntos);
		/**
		 * Si hay un número par de cortes, entonces la línea divisoria 
		 * 	entre el hijo 1 y el hijo 2 devuelto será el tamaño de 
		 * 	la regla 2. Para entenderlo, sugiero hacer un dibujo
		 * 	con los cromosomas, etc
		 */
		for(;j<=nPuntos; j++){
			for(;i<idx[j];i++){
				if (j%2 == 0){
					if (i< r1.length) toret[i] = r1[i];
					if (i< r2.length) toret[i+n] = r2[i];
				}else{
					if (i< r2.length) toret[i] = r2[i];
					if (i< r1.length) toret[i+n] = r1[i];
				}
			}	
		}

		return toret;
	}

	public static String[] getClases() {
		
		return clases;
	}
}
