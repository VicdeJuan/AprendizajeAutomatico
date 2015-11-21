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

    	
    
    public Poblacion(int n, int numReglas, int numAtrib,double pMut,double pCruc){
        individuos = new ArrayList<>(n);
        for (int i=0;i<n;i++)
            individuos.add(new Individuo(numReglas, numAtrib));
        size = n;
        probMutacion=pMut;
        probCruce=pCruc;
	estrategiaReemplazo = new ReemplazoTotal(0.03);
    }

    /**
     * Devuelve la combinación de 2 poblaciones
     * @param p1	población 1 a combinar
     * @param p2
     * @return La unión de las poblaciones SI Y SOLO SI el número de reglas, 
     * 		de atributos, la probabilidad de cruce y la probabilidad de 
     * 		mutación coindicen. En caso de que alguna difiera, se devuelve 
     * 		null.
     */
    public static Poblacion join (Poblacion p1,Poblacion p2){
	if ( !((p1.getNumAtributos() == p2.getNumAtributos()) && (p1.getNumReglas() == p2.getNumReglas()) && (p1.probCruce == p2.probCruce) &&	 (p1.probMutacion == p2.probMutacion))) {
		return null;
	}
	    
	Poblacion toret = new Poblacion(p1.size + p2.size, p1.getNumReglas() , p1.getNumAtributos(), p1.probMutacion, p1.probCruce);
	ArrayList<Individuo> toadd = new ArrayList<>();
	toadd.addAll(p1.getIndividuos());
	toadd.addAll(p2.getIndividuos());
	toret.setIndividuos(toadd);
	return toret;
    }
    
    public void mutacion(){
        Random r = new Random(SEED);
        for (Individuo i: individuos)
            if (r.nextDouble() >= probMutacion)
                i.mutar();
    }
    
}
