package clasificadores;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import clasificadores.genetica.reemplazos.MejoresPorPeores;
import clasificadores.genetica.reemplazos.Reemplazo;
import clasificadores.genetica.reemplazos.ReemplazoTotal;
import clasificadores.genetica.seleccion.Seleccion;
import clasificadores.genetica.seleccion.SeleccionGreedy;
import clasificadores.genetica.seleccion.SeleccionProporcionalFitness;
import clasificadores.genetica.seleccion.SeleccionSimple;
import Datos.Datos;
import particionado.EstrategiaParticionado;
import particionado.ValidacionCruzada;
import particionado.ValidacionSimple;

public class practica {

	public static void main(String[] args) {
			

		String filename ="Resultados";
		
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
		
		Integer[] Poblaciones = {10,100,500};
		Integer[] generaciones = {100,500};
		Reemplazo[] reemStrategies = {new ReemplazoTotal(),new MejoresPorPeores()};
		Seleccion[] selecStrategies = {new SeleccionProporcionalFitness(),new SeleccionGreedy(),new SeleccionSimple()};
		Integer[] reglas = {5,11,16,25};
		
		/*String msg = "Analisis importancia de reglas:\n";
		
		for (int nr : reglas){
			msg += "NumReglas : " + nr + "\n" + getMsgFromClasificar(true,101,101,nr,new SeleccionProporcionalFitness(),new MejoresPorPeores());
		}*/
		
		String msg = "\nApartado 2:\n";
		writer.write(msg);
		/*for (int np : Poblaciones)
			for (int ng : generaciones){
					msg = getMsgFromClasificar(false,ng, np, 11, new SeleccionProporcionalFitness(), new ReemplazoTotal());
					writer.write(msg);
					System.out.print(msg);
				}*/
						
		for (Reemplazo r : reemStrategies)
			for (Seleccion s : selecStrategies){
				msg = getMsgFromClasificar(false,73, 73, 11,s, r);
				writer.write(msg);
				System.out.print(msg);
			}
		
		
		
		
		
		writer.close();
		}
	
	private static String getMsgFromClasificar(boolean numRegAleat,int gen,int pob,int nReglas,Seleccion selecStrategy,Reemplazo reempStrategy){
		boolean numReglasAleat = numRegAleat;
		double pCruce = 0.6,pMut = 0.01,elit = 0.05;
		ArrayList<Double> errores = new ArrayList<>();
		Datos d;
		EstrategiaParticionado part;
		Clasificador c;
		int porc = 66;
		
		d = Datos.cargaDeFichero("tic-tac-toe.data.txt");
		part =  new ValidacionSimple();
		
		c = new ClasificadorGenetico(gen,pob,nReglas,numReglasAleat,pCruce,pMut,elit,reempStrategy,selecStrategy);
		errores = Clasificador.validacion(part, d, c,porc,true);
		String msg = String.format("\nValores: \n\t Poblacion: %d \t Generaciones: %d \n\t Probabilidades:\n\t\t cruce: %.2f%%\t mutacion: %.2f%%\n\t Elitismo %.2f %%\n\t Seleccion: %s\n\t Reemplazo: %s",pob,gen,pCruce*100,pMut*100,elit*100,reempStrategy,selecStrategy);
		msg  += "\nErrores obtenidos: " + errores;
		double media = 0;
		for (double e : errores)
			media += e;
		media /= errores.size();
		msg += String.format("\nMin: %f.3 \t Max: %f.3 \t Media: %f.3\n\n",Collections.min(errores),Collections.max(errores),media);
		return msg;
		

	}

}
