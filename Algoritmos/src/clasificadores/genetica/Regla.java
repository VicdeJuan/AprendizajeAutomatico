/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author vicdejuan
 */
public class Regla {
	
	int size;
	long regla;
	private static final String clases[] = new String[]{"+", "-"};;
	private static final HashMap<String,Long> nominal_to_bit = createNominal();
	Random r = new Random();
	
	private static HashMap<String,Long> createNominal(){
		HashMap<String,Long> nomin = new HashMap<>();
		
		nomin.put("c", 1L);
		nomin.put("r", 2L);
		nomin.put("x", 0L);
		return nomin;
	}
	
	/**
	 * Return the number of bits from a given number of attributes.
	 * @param n
	 * @return 
	 */
	public int getBitsFromSize(int n){
		return n*2-1;
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
		regla = ((long)(r.nextDouble()*(Math.pow(2,n)))-1);	
		
	}	

	public Regla(int n, long rule) {
		// Asignación arbitraria de valores nominal es a números. Como sólo hay 3 posibles valores, necesitamos 2 bits, de ahí valores entre 0 y 3
		
		size = n;
		this.regla = rule; 
	
	}
	
// la máscara es 0...d...011111... donde 
	// 	d = 2*n-1, ya que cada atributo necesita 2 bits, salvo la clase que solo 1
	private long buildTicTacMask(Integer n){
		return (long) Math.pow(2, getBitsFromSize(n)+1)-1;
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
		// revisar el >> (ha sido triplazo)
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
				rule = rule << 2 | nominal_to_bit.get(val);
			else{
				if (fila.get(fila.size()-1) == clases[1] && val == clases[1]){
					rule = rule << 1 | 1;
				}else if (fila.get(fila.size()-1) == clases[0] && val == clases[0]){
					rule = rule << 1;
				} else {
					System.out.println("Esto no debería ocurrir nunca!!");
				}
			}
		}
		
		return new Regla(n,rule);
	}

	
	private long getRegla() {
		return regla;
	}
	

}
