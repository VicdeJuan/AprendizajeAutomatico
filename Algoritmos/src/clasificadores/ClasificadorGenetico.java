package clasificadores;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	int sizePoblacion,numReglas,numAtributos,generaciones;
	double pCruce,pMut,elit;
	boolean numReglasAleat,ordered;
	Reemplazo estrategiaReemplazo;
	Seleccion estrategiaSeleccion;
	String filename;
	
	public ClasificadorGenetico(int generaciones,int nPoblacion,int numReglas, boolean numReglasAleat, double pCruce, double pMut,double elit,Reemplazo reemplazoStrategy,Seleccion seleccionStrategy){
		this.generaciones = generaciones;
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
		filename = String.format("g%d_p%d_%s_%s.res",generaciones,sizePoblacion,estrategiaReemplazo,estrategiaSeleccion);

	}
	
	@Override
	public void entrenamiento(Datos datosTrain) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		
		

		Poblacion P = new Poblacion(sizePoblacion, numReglas, numAtributos, pMut, pCruce, estrategiaReemplazo, ordered, numReglasAleat,estrategiaSeleccion);
		P.setElitismo(elit);

		Poblacion Pprime;
		int i=0;


		double min,media,max;
		while(i < this.generaciones){
			System.out.print(String.format("Ronda %d -> ",i));
			P.calcularFitness(datosTrain);

			Pprime = P.getEstrategiaSeleccion().seleccionar(P);
			Pprime.mutacion();
			Pprime.cruceNPuntos(2);
			Pprime.calcularFitness(datosTrain);
			Pprime.OrdenarPorFitness();
			P = P.getEstrategiaReemplazo().Reemplazar(P, Pprime);
		

			min = P.getIndividuos().get(P.getSize()-1).getFitness();
			media = P.getMedia();
			max =  P.getIndividuos().get(0).getFitness();
			System.out.println(max);
			writer.println(String.format("Ronda %d: \n\tFitness maximo: %.3f \n\t Fitness medio: %.3f\n\tFitness minimo: %.3f",i,max,media,min));

			i++;
		}

		train_result = P.getIndividuos().get(0);
			

		print_end_train();
		writer.close();
	}
	
	
	
	private void print_end_train(){
		String toprint = "Finalizado proceso de train.\n   Fitness: ";
		toprint += train_result;
		toprint += String.format("\nGen: %d  - Pob: %d\n",this.generaciones,this.sizePoblacion);
		toprint += this.estrategiaReemplazo.toString() + this.estrategiaSeleccion.toString();
		
		System.out.println(toprint);
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		
		writer.println(toprint);
		writer.close();
	}

	@Override
	public ArrayList<String> clasifica(Datos datosTest) {
		ArrayList<String> toret = new ArrayList<>(datosTest.getNumDatos());
		for (ArrayList<String> row : datosTest.getDatos())
			toret.add(train_result.clasifica(row));

		return toret;
	}

}
