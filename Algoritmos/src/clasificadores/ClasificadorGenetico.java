package clasificadores;

import java.util.ArrayList;
import java.util.Collections;

import Datos.Datos;
import clasificadores.genetica.Individuo;
import clasificadores.genetica.Poblacion;
import clasificadores.genetica.reemplazos.Reemplazo;
import clasificadores.genetica.reemplazos.ReemplazoTotal;
import clasificadores.genetica.seleccion.Seleccion;
import clasificadores.genetica.seleccion.SeleccionProporcionalFitness;
import comparadores.ComparadorFitness;

public class ClasificadorGenetico extends Clasificador {

	Individuo train_result;
	int sizePoblacion,numReglas,numAtributos;
	double pCruce,pMut,elit;
	boolean numReglasAleat,ordered;
	Reemplazo estrategiaReemplazo;
	Seleccion estrategiaSeleccion;
	
	
	public ClasificadorGenetico(int nPoblacion,int numReglas,boolean numReglasAleat, double pCruce, double pMut,double elit,Reemplazo reemplazoStrategy,Seleccion seleccionStrategy){
		sizePoblacion = nPoblacion;
		this.numReglas = numReglas;
		this.numReglasAleat = numReglasAleat;
		this.pCruce =pCruce;
		this.pMut = pMut;
		this.elit = elit;
		// Fijo para tic-tac-toe
		numAtributos = 6;
		estrategiaReemplazo = reemplazoStrategy;
		estrategiaSeleccion = seleccionStrategy;
		train_result = null;
	}
	
	@Override
	public void entrenamiento(Datos datosTrain) {
		Poblacion P = new Poblacion(sizePoblacion, numReglas, numAtributos, pMut, pCruce, estrategiaReemplazo, ordered, numReglasAleat,estrategiaSeleccion);
		Poblacion Pprime;
		int i=0;
		while(i < 10){
			P.calcularFitness(datosTrain);
			Pprime = P.getEstrategiaSeleccion().seleccionar(P);
			Pprime.mutacion();
			Pprime.cruceNPuntos(1);
			Pprime.calcularFitness(datosTrain);
			P = P.getEstrategiaReemplazo().Reemplazar(P, Pprime);
			i++;
		}
		if (P.isOrdered())
			train_result = P.getIndividuos().get(0);
		else
			train_result = Collections.max(P.getIndividuos(),new ComparadorFitness());
		
	}

	@Override
	public ArrayList<String> clasifica(Datos datosTest) {
		
		return null;
	}

}
