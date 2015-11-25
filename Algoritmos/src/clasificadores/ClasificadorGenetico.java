package clasificadores;

import java.util.ArrayList;
import java.util.Arrays;
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
		numAtributos = 10;
		estrategiaReemplazo = reemplazoStrategy;
		estrategiaSeleccion = seleccionStrategy;
		train_result = null;
	}
	
	@Override
	public void entrenamiento(Datos datosTrain) {
		Poblacion P = new Poblacion(sizePoblacion, numReglas, numAtributos, pMut, pCruce, estrategiaReemplazo, ordered, numReglasAleat,estrategiaSeleccion);
		P.setElitismo(elit);
		Poblacion Pprime;
		int i=0;
		boolean debug = false;
		while(i < 30){
			if (debug) System.out.print(String.format("Ronda %d ->",i));
			P.calcularFitness(datosTrain);
			// Aquí ya está ordenado.
			Pprime = P.getEstrategiaSeleccion().seleccionar(P);
			Pprime.mutacion();
			Pprime.cruceNPuntos(1);
			Pprime.calcularFitness(datosTrain);
			Pprime.OrdenarPorFitness();
			if (debug) System.out.println(Collections.max(P.getIndividuos(),new ComparadorFitness()).getFitness() == P.getIndividuos().get((int) Math.round(Math.random()*(P.getSize()-2))).getFitness());
			P = P.getEstrategiaReemplazo().Reemplazar(P, Pprime);
			P.OrdenarPorFitness();
			i++;
			if (debug) System.out.println(Collections.max(P.getIndividuos(),new ComparadorFitness()) == P.getIndividuos().get(0));

			System.out.println(Collections.max(P.getIndividuos(),new ComparadorFitness()).getFitness());
		}
		if (P.isOrdered())
			train_result = P.getIndividuos().get(0);
		else
			train_result = Collections.max(P.getIndividuos(),new ComparadorFitness());
		
		print_end_train();
		
	}
	
	
	
	private void print_end_train(){
		String toprint = "Finalizado proceso de train.\n";
		toprint += train_result.toString();
		
		System.out.println(toprint);
		
	}

	@Override
	public ArrayList<String> clasifica(Datos datosTest) {
		ArrayList<String> toret = new ArrayList<>(datosTest.getNumDatos());
		for (ArrayList<String> row : datosTest.getDatos())
			toret.add(train_result.clasifica(row));
		
		return toret;
	}

}
