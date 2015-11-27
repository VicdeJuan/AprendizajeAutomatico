/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import static particionado.EstrategiaParticionado.SEED;

/**
 *
 * @author vicdejuan
 */
public class Individuo {
	Regla[] reglas;
	Regla[] rmas;
	Regla[] rmenos;
	int numAtributos;
	int numReglas;
	boolean numReglasAleat;
	public static String DEFAULT_CLASS = "";
	double fitness;

	public void setDefault(String def){
		DEFAULT_CLASS = def;
	}
	public Regla[] getReglas() {
		return reglas;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * Crea un individuo con los parámetros dados.
	 * 
	 * @param rules
	 *            Reglas del individuo.
	 * @param nAtrib
	 *            Número de atrubutos.
	 * @param nReglas
	 *            Número de reglas.
	 */
	public Individuo(Regla[] rules, int nAtrib, int nReglas,
			boolean numReglasRandom) {
		reglas = rules;
		numAtributos = nAtrib;
		numReglas = nReglas;
		fitness = 0.0;
		numReglasAleat = numReglasRandom;
	}

	
	public int getNumAtributos() {
		return numAtributos;
	}

	public int getNumReglas() {
		return numReglas;
	}

	public static String getDEFAULT_CLASS() {
		return DEFAULT_CLASS;
	}

	public void setReglas(Regla[] reglas) {
		this.reglas = reglas;
	}

	/**
	 * Constructor con reglas aleatorias.
	 * 
	 * @param numReglas
	 *            Número de reglas para el individuo.
	 * @param numAtributos
	 *            Número de atributos (no de bits) que tienen las reglas.
	 */
	public Individuo(int numReglas, int numAtributos, boolean numReglasRandom) {
		int nReglas;
		Random r = new Random(SEED);
		if (numReglasRandom) {
			nReglas = r.nextInt(numReglas);
		} else {
			nReglas = numReglas;
		}
		reglas = new Regla[nReglas];
		for (int i = 0; i < nReglas; i++)
			reglas[i] = new Regla(numAtributos);
		this.numAtributos = numAtributos;
		this.numReglas = numReglas;
		fitness = 0.0;
		numReglasAleat = numReglasRandom;
	}

	public Individuo(Individuo individuo){
		this(individuo.numReglas,individuo.numAtributos,individuo.numReglasAleat);
		this.setFitness(individuo.fitness);
		reglas = new Regla[individuo.numReglas];
		for (int i = 0; i < individuo.numReglas; i++)
			reglas[i] = new Regla(numAtributos,individuo.getReglas()[i].regla);
		this.setReglas(reglas);
		this.ContruirRmasRmenos();
	}
	/**
	 *
	 *	Construimos los arrays de reglas que clasifican como + 
	 *		y el array de reglas que clasifican como - 
	 */
	public void ContruirRmasRmenos(){
		int masSize = 0;
		for (int i=0; i<this.reglas.length;i++){
			masSize += (this.reglas[i].regla % 2 == 1) ? 1 : 0;
		}
		rmas = new Regla[masSize];
		rmenos = new Regla[this.reglas.length - masSize];
		int ms=0,mn=0;
		for (int i=0; i<this.reglas.length;i++){
			if (this.reglas[i].regla % 2 == 1){
				rmas[ms] = this.reglas[i];
				ms++;
			}
			else{
				rmenos[mn] = this.reglas[i];
				mn++;
			}
		}
	}

	/**
	 * Devuelve una clase para la fila dada. En caso de no matchear con ninguna
	 * regla de las del individuo, se devolverá la clase dada por DEFAULT_CLASS
	 * 
	 * @param row
	 *            Fila que clasificar.
	 * @return
	 */
	public String clasifica(ArrayList<String> row) {
		//this.ContruirRmasRmenos();

		String retval = getDEFAULT_CLASS();
	
		boolean aux,esmas = true,esmenos = true;
		int desplazamiento;
		long bitsAtributoEnRegla;
		String atrb;
		
		// size-1 porque excluimos la regla de la fila para clasificar.
		for (int i = 0; i<row.size()-1;i++) {
			atrb = row.get(i);
			aux = false;
			for (Regla r : rmenos){
				if (aux) break;
				desplazamiento = (2*(row.size()-(i+1)-1)+1);
				bitsAtributoEnRegla = (r.regla >> desplazamiento) & 3L;
				if ((bitsAtributoEnRegla & Regla.getBitsFromStr(atrb)) == Regla.getBitsFromStr(atrb)){
					aux = true;
				}
			}
			esmenos &= aux;
		}
		if (esmenos)
			return Regla.getClases()[0];
		for (int i = 0; i<row.size()-1;i++) {
			atrb = row.get(i);
			aux = false;
			for (Regla r : rmas){
				if (aux) break;
				desplazamiento = (2*(row.size()-(i+1)-1)+1);
				bitsAtributoEnRegla = r.regla >> desplazamiento & 3L;
				if ((bitsAtributoEnRegla & Regla.getBitsFromStr(atrb)) == Regla.getBitsFromStr(atrb)){
					aux = true;
				}
			}
			esmas &= aux;
		}
		
		if (esmas)
			return Regla.getClases()[1];

		
		return retval;
	}

	/**
	 * Función fitness del algoritmo genético.
	 * 
	 * @param datos
	 *            sobre los que calcular el fitness
	 * @return el porcentaje de aciertos cometidos
	 */
	public double fitness(ArrayList<ArrayList<String>> datos) {
		this.ContruirRmasRmenos();
		double acum = 0;
		int n = 0;
		for (ArrayList<String> row : datos) {
			String a = row.get(row.size() - 1);
			if (this.clasifica(row).equals(row.get(row.size() - 1))) {
				acum++;
			}
			n++;
		}
		fitness = acum / n;
		//System.out.println("\tf: " + fitness);
		setFitness(fitness);
		return fitness;
	}

	public void mutar(double prob) {

		for (int idx = 0; idx<numReglas;idx++)
			reglas[idx].mutar(prob);

	}
	
	@Override
	public String toString() {
		String toret = "{";
		
		toret +=  String.format("[n�mero de reglas: %d] , ",this.numReglas);
		toret +=  String.format("[n�mero de Atributos %d] , ",this.numAtributos);
		toret +=  String.format("[fitness %f] ,  ",this.fitness);
		toret +=  "[default class: "+Individuo.getDEFAULT_CLASS() + "] ";
		toret += "}";
		
		return toret;
	}

}
