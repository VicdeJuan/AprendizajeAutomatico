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

/**
 *
 * @author vicdejuan
 */
public class Poblacion {

    double elitismo;
    int size;
    ArrayList<Individuo> individuos;
    int cruce;
    double probCruce;
    double probMutacion;
    int numReglas;
    int numAtributos;
    Reemplazo estrategiaReemplazo;
    Seleccion estrategiaSeleccion;
    boolean numReglasAleat;
    boolean fitnessSetted;
    
    /**
     * Getters y setters
     */
    
    
    
	public void setNumReglas(int numReglas) {
		this.numReglas = numReglas;
	}

	public boolean isFitnessSetted() {
		return fitnessSetted;
	}

	public void setFitnessSetted(boolean fitnessSetted) {
		this.fitnessSetted = fitnessSetted;
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

	public int getCruce() {
		return cruce;
	}

	public double getProbCruce() {
		return probCruce;
	}

	public double getProbMutacion() {
		return probMutacion;
	}

	public void setElitismo(double elitismo) {
		this.elitismo = elitismo;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setIndividuos(ArrayList<Individuo> individuos) {
		this.individuos = individuos;
	}

	public void setCruce(int cruce) {
		this.cruce = cruce;
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
    * Crea una nueva poblaci�n con los par�metros definidos.
    * @param n					Tama�o de la poblaci�n.
    * @param numReglas			N�mero de reglas de cada individuo.
    * @param numAtrib			N�mero de atributos de los datos con los que trabajamos.
    * @param pMut				Probabilidad de mutaci�n.
    * @param pCruc				Probabilidad de cruce.
    * @param reemplazoStrategy 	Objeto ya creado con una estrategia de reemplazo.
    * @param order				Si la Poblaci�n debe estar ordenada de acuerdo al fitness o no.
    * @param NumReglasRandom	False si cada regla puede tener un n�mero distinto de reglas.
    * @param seleccionStraregy	Objeto ya creado con una estrategia de selecci�n.
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
        setFitnessSetted(false);
    }
    
    /**
     * Crea una nueva poblaci�n SIN INDIVIDUOS manteniendo los atributos de la dada como argumento.
     * @param p	Poblaci�n con los datos que tomar como base.
     */

    public Poblacion(Poblacion p) {
    	size = p.size;
    	probMutacion = p.probMutacion;
		probCruce = p.probCruce;
		estrategiaReemplazo = p.estrategiaReemplazo;
		numReglasAleat = p.numReglasAleat;
		elitismo = p.elitismo;
		numAtributos = p.numAtributos;
		estrategiaSeleccion  = p.estrategiaSeleccion;
		setFitnessSetted(p.isFitnessSetted());
    }

	/**
     * Devuelve la combinaci�n de 2 poblaciones. La nueva poblaci�n NO estar�
     * 	ordenada. 
     * @param p1	población 1 a combinar
     * @param p2	Población 2 a combinar.
     * @return La uni�n de las poblaciones SI Y SOLO SI el n�mero de reglas, 
     * 		de atributos, la probabilidad de cruce y la probabilidad de 
     * 		mutaci�n coindicen. En caso de que alguna difiera, se devuelve 
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
	toret.setFitnessSetted(true);
	toret.setIndividuos(toadd);
	toret.OrdenarPorFitness();

	return toret;
    }
    
    /**
     * Calcula el fitness de cada individuo de la poblaci�n y almacena su fitness. 
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
		//System.out.println("pre_ind: "+this.getIndividuos().indexOf(Collections.max(this.getIndividuos(),new ComparadorFitness())));
		//Individuo imax = Collections.max(this.individuos,new ComparadorFitness());
		//System.out.println(" - Is repeated? "+(individuos.indexOf(imax) == individuos.lastIndexOf(imax)));
	    Collections.sort(this.individuos, new Comparator<Individuo>(){
	    	@Override
	    	public int compare (Individuo i1,Individuo i2){
	    		return Double.compare(i2.getFitness(), i1.getFitness());
	    	}	
	    });
	    
	    //System.out.println(" - post_id: "+this.getIndividuos().indexOf(Collections.max(this.getIndividuos(),new ComparadorFitness())));
	    //imax = Collections.max(this.individuos,new ComparadorFitness());
		//System.out.println(" - Is repeated? "+(individuos.indexOf(imax) == individuos.lastIndexOf(imax)));
	   
    }
    public void mutacion(){
        Random r = new Random(SEED);
        for (Individuo i: individuos)
            if (r.nextDouble() >= probMutacion)
                i.mutar();
    }
    
    /**
     * TODO: Vaya chapuza de m�todo.
     * @param nPuntos 
     */
    public void cruceNPuntos(int nPuntos){
	Individuo i1,i2;
	for (int i=0; i<size-size%2; i+=2){
		i1 = this.getIndividuos().get(i);
		i2 = this.getIndividuos().get(i+1);
		int n = Regla.getDivisor(i1.getReglas(), i2.getReglas(), nPuntos);
		
		Regla[] hijos = Regla.CruceNPuntos(i1.getReglas(), i2.getReglas(), nPuntos);
		
		Regla dst[] = new Regla[i1.getNumReglas()];
		System.arraycopy(hijos, 0, dst,0, n);
		i1.setReglas(dst);
		
		dst = new Regla[i2.getNumReglas()];
		System.arraycopy(hijos,n , dst,0, i2.getNumReglas());
		i2.setReglas(dst);
	}
	
    }

    public boolean getNumReglasAleat() {
        
        return this.numReglasAleat;
        
    }
}
