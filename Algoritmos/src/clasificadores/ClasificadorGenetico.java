package clasificadores;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
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
		filename = String.format("g%d_p%d_%s_%s_reg%d.res",generaciones,sizePoblacion,estrategiaReemplazo,estrategiaSeleccion,numReglas);

	}
	
	@Override
	public void entrenamiento(Datos datosTrain) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new PrintWriter(filename, "UTF-8"));
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
			Pprime.calcularFitness(datosTrain);
			Pprime.cruceNPuntos(1);
			Pprime.calcularFitness(datosTrain);
			Pprime.OrdenarPorFitness();
			P = P.getEstrategiaReemplazo().Reemplazar(P, Pprime);
			P.OrdenarPorFitness();

			min = Collections.min(P.getIndividuos(),new ComparadorFitness()).getFitness();
			media = P.getMedia();
			train_result = Collections.max(P.getIndividuos(),new ComparadorFitness());
			max = train_result.getFitness();

			System.out.println(String.format("%.3f - %d", max,P.getIndividuos().get(0).getReglas().length));
			
			try {
				writer.write(String.format("Ronda %d: \n\tFitness maximo: %.3f \n\t Fitness medio: %.3f\n\tFitness minimo: %.3f \n\tNum Reglas: %d\n",i,max,media,min,P.getIndividuos().get(0).getNumReglas()));
			} catch (IOException e) {
				e.printStackTrace();
			}

			i++;
		}

	
			

		String toprint = "Finalizado proceso de train.\n   Fitness: ";
		toprint += train_result;
		toprint += String.format("\nGen: %d  - Pob: %d\n",this.generaciones,this.sizePoblacion);
		toprint += this.estrategiaReemplazo.toString() + this.estrategiaSeleccion.toString();
		
		System.out.println(toprint);
		try {
			writer.write(toprint+"\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public ArrayList<String> clasifica(Datos datosTest) {
		ArrayList<String> toret = new ArrayList<>(datosTest.getNumDatos());
		for (ArrayList<String> row : datosTest.getDatos())
			toret.add(train_result.clasifica(row));

		return toret;
	}

}
