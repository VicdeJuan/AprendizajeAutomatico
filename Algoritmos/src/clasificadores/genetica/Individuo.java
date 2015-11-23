/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import java.util.ArrayList;
import java.util.Random;
import static particionado.EstrategiaParticionado.SEED;

/**
 *
 * @author vicdejuan
 */
public class Individuo {
	Regla[] reglas;
	int numAtributos;
	int numReglas;
        boolean numReglasAleat;
	public static final String DEFAULT_CLASS = "-";
	double fitness;

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
	 * @param rules		Reglas del individuo.
	 * @param nAtrib	Número de atrubutos.
	 * @param nReglas 	Número de reglas.
	 */
	public Individuo(Regla[] rules, int nAtrib, int nReglas,boolean numReglasRandom){
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
	 * @param numReglas Número de reglas para el individuo.
	 * @param numAtributos Número de atributos (no de bits) que tienen las reglas.
	 */	
	public Individuo(int numReglas,int numAtributos,boolean numReglasRandom){
            int nReglas;    
            if (numReglasRandom){
                nReglas = (int) Math.round(Math.random()*numReglas+1);
            }else{
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
	
	/**
	 * Devuelve una clase para la fila dada. En caso de no matchear con 
	 * 	ninguna regla de las del individuo, se devolverá la clase dada 
	 * 	por DEFAULT_CLASS
	 * @param row Fila que clasificar.
	 * @return 
	 */
	public String clasifica(ArrayList<String> row) {
		String retval = DEFAULT_CLASS;
		for (Regla regla : reglas) {
			if (null != regla.match(Regla.convert(row))) {
				retval = regla.get_class();
			}
		}
		return retval;
	}

	
	/**
	 * Función fitness del algoritmo genético.
	 * @param datos sobre los que calcular el fitness
	 * @return el porcentaje de aciertos cometidos
	 */
	public double fitness(ArrayList<ArrayList<String>> datos) {
		double acum = 0;
		int n = 0;
		for (ArrayList<String> row : datos){
			if (this.clasifica(row) == row.get(row.size()-1)) {
				acum++;
			}
			n++;
		}
		fitness = acum/n;
		return acum/n;
	}

    void mutar() {
        int idx = (int) Math.round(Math.random()*numReglas);
        
        reglas[idx].mutar();
        
    }


}
