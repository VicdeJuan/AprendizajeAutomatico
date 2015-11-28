/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import clasificadores.genetica.reemplazos.Reemplazo;
import clasificadores.genetica.seleccion.Seleccion;
import comparadores.ComparadorFitness;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import Datos.Datos;

import static particionado.EstrategiaParticionado.SEED;


public class Poblacion {

    double elitismo;
    int size;
    ArrayList<Individuo> individuos;
 
    double probCruce;
    double probMutacion;
    int numReglas;
    int numAtributos;
    Reemplazo estrategiaReemplazo;
    Seleccion estrategiaSeleccion;
    boolean numReglasAleat;

    
    /**
     * Getters y setters
     */
    
    
    
	public void setNumReglas(int numReglas) {
		this.numReglas = numReglas;
	}

	public void setNumAtributos(int numAtributos) {
		this.numAtributos = numAtributos;
	}

	public int getNumReglas() {
		return numReglas;
	}

	public int getNumAtributos() {
		return numAtributos;
	}

	public double getElitismo() {
		return elitismo;
	}

	public int getSize() {
		return size;
	}

	public ArrayList<Individuo> getIndividuos() {
		return individuos;
	}


	public double getProbCruce() {
		return probCruce;
	}

	public double getProbMutacion() {
		return probMutacion;
	}

	public void setElitismo(double elitismo) {
		if (elitismo > 1){
			System.err.println("Elitismo no puede ser mayor que 1");
		}
		this.elitismo = elitismo;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setIndividuos(ArrayList<Individuo> individuos) {
		this.individuos = individuos;
	}



	public void setProbCruce(double probCruce) {
		this.probCruce = probCruce;
	}

	public void setProbMutacion(double probMutacion) {
		this.probMutacion = probMutacion;
	}

	public Reemplazo getEstrategiaReemplazo() {
		return estrategiaReemplazo;
	}

	public Seleccion getEstrategiaSeleccion() {
		return estrategiaSeleccion;
	}

	public void setEstrategiaSeleccion(Seleccion estrategiaSeleccion) {
		this.estrategiaSeleccion = estrategiaSeleccion;
	}

	public void setNumReglasAleat(boolean numReglasAleat) {
		this.numReglasAleat = numReglasAleat;
	}

	public void setEstrategiaReemplazo(Reemplazo estrategiaReemplazo) {
		this.estrategiaReemplazo = estrategiaReemplazo;
	}



	
    
   /**
    * Crea una nueva población con los parámetros definidos.
    * @param n					Tamaño de la poblaciún.
    * @param numReglas			Número de reglas de cada individuo.
    * @param numAtrib			Número de atributos de los datos con los que trabajamos.
    * @param pMut				Probabilidad de mutaciún.
    * @param pCruc				Probabilidad de cruce.
    * @param reemplazoStrategy 	Objeto ya creado con una estrategia de reemplazo.
    * @param order				Si la Población debe estar ordenada de acuerdo al fitness o no.
    * @param NumReglasRandom	False si cada regla puede tener un número distinto de reglas.
    * @param seleccionStraregy	Objeto ya creado con una estrategia de selección.
    */ 
    public Poblacion(int n, int numReglas, int numAtrib,double pMut,double pCruc, Reemplazo reemplazoStrategy,boolean order,boolean numReglasRandom, Seleccion seleccionStrategy){
        individuos = new ArrayList<>(n);
        for (int i=0;i<n;i++)
            individuos.add(new Individuo(numReglas, numAtrib,numReglasRandom));
        size = n;
        probMutacion=pMut;
        probCruce=pCruc;
        estrategiaReemplazo = reemplazoStrategy;

        numReglasAleat = numReglasRandom;
        estrategiaSeleccion = seleccionStrategy;

    }


	/**
     * Devuelve la combinación de 2 poblaciones. La nueva población se ordena. 
     * @param p1	población 1 a combinar
     * @param p2	Población 2 a combinar.
     * @return La unión de las poblaciones SI Y SOLO SI el número de reglas, 
     * 		de atributos, la probabilidad de cruce y la probabilidad de 
     * 		mutación coindicen. En caso de que alguna difiera, se devuelve 
     * 		null.
     */
    public static Poblacion join (Poblacion p1,Poblacion p2){
	if (p1 == null && p2 != null) 
		return p2;
	if (p2 == null && p1 != null)
		return p1;
	if ( !((p1.getNumAtributos() == p2.getNumAtributos()) && (p1.getNumReglas() == p2.getNumReglas()) && (p1.probCruce == p2.probCruce) &&	 (p1.probMutacion == p2.probMutacion))) {
		return null;
	}
	    
	Poblacion toret = new Poblacion(p1.size + p2.size, p1.getNumReglas() , p1.getNumAtributos(), p1.probMutacion, p1.probCruce,p1.getEstrategiaReemplazo(),false,p1.getNumReglasAleat() || p1.getNumReglasAleat(),p1.getEstrategiaSeleccion());
	ArrayList<Individuo> toadd = new ArrayList<>();
	
	toadd.addAll((ArrayList<Individuo>) p1.getIndividuos().clone());
	toadd.addAll((ArrayList<Individuo>) p2.getIndividuos().clone());

	toret.setIndividuos(toadd);
	toret.setSize(toadd.size());

	toret.setElitismo(p1.getElitismo());
	toret.OrdenarPorFitness();

	return toret;
    }
    
    /**
     * Calcula el fitness de cada individuo de la población y almacena su fitness. 
     * 		
     * 		(si isOrdered == true) entonces ordena la lista de individuos por fitness.
     * 
     * @param datos sobre los que calcular el fitness
     */
    public void calcularFitness(Datos datos){
    	for (Individuo i : individuos){
    		i.fitness(datos.getDatos());
    	}
    	
    	OrdenarPorFitness();
    	
    }

	public void OrdenarPorFitness(){

	    Collections.sort(this.individuos, new Comparator<Individuo>(){
	    	@Override
	    	public int compare (Individuo i1,Individuo i2){
	    		return Double.compare(i2.getFitness(), i1.getFitness());
	    	}	
	    });

    }
    public void mutacion(){
        for (Individuo i: individuos)
                i.mutar(probMutacion);
    }
    
    /**
     * TODO: Vaya chapuza de método.
     * @param nPuntos 
     */
    public void cruceNPuntos(int nPuntos){
	Individuo i1,i2;
	Random r = new Random(SEED);
	
	for (int i=0; i<size-size%2; i+=2){
		i1 = this.getIndividuos().get(i);
		i2 = this.getIndividuos().get(i+1);
		
		int n = Regla.getDivisor(i1.getReglas(), i2.getReglas(), nPuntos);
		
		Regla[] hijos = Regla.CruceNPuntos(i1.getReglas(), i2.getReglas(), nPuntos);
		
		Regla dst[] = new Regla[i1.getNumReglas()];
		System.arraycopy(hijos, 0, dst,0, n);
		if (r.nextDouble() < this.getProbCruce())
			i1.setReglas(dst);
		
		dst = new Regla[i2.getNumReglas()];
		System.arraycopy(hijos,n , dst,0, i2.getNumReglas());
		if (r.nextDouble() < this.getProbCruce())
			i2.setReglas(dst);
	}
	
    }

    public boolean getNumReglasAleat() {
        
        return this.numReglasAleat;
        
    }


	public double getMedia() {
		double mu = 0;
		for (Individuo i : individuos){
			mu += i.getFitness();
		}
		
		return mu/individuos.size();
	}

	public void crop(int newSize) {
		if (this.individuos.size() < newSize)
			return;
		else{
			for (int i = newSize;i<this.individuos.size();i++){
				this.individuos.remove(i);
			}
			this.setSize(newSize);
		}		

	}
}
