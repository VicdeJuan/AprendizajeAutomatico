package clasificadores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import clasificadores.genetica.reemplazos.Reemplazo;
import clasificadores.genetica.reemplazos.ReemplazoTotal;
import clasificadores.genetica.seleccion.Seleccion;
import clasificadores.genetica.seleccion.SeleccionProporcionalFitness;
import Datos.Datos;
import particionado.EstrategiaParticionado;
import particionado.ValidacionCruzada;
import particionado.ValidacionSimple;

public class practica {

	public static void main(String[] args) {
			
		Datos d;
		EstrategiaParticionado part;
		Clasificador c;
		int porc = 66;
		ArrayList<Double> errores = new ArrayList<>();
		
		int nPoblacion,nReglas,generaciones;
		boolean numReglasAleat;
		double pCruce,pMut,elit;
		Reemplazo reempStrategy;
		Seleccion selecStrategy;
		
		d = Datos.cargaDeFichero("tic-tac-toe.data.txt");
		
		part =  new ValidacionSimple();
		//___________________ Apartado 2 ________________________
		/*
		a) Tamaño de población = 10 ; Generaciones = 100
		b) Tamaño de población = 10 ; Generaciones = 1000
		c) Tamaño de población = 100 ; Generaciones = 100
		d) Tamaño de población = 100 ; Generaciones = 1000
		e) Tamaño de población = 500 ; Generaciones = 100
		f) Tamaño de población = 500 ; Generaciones = 1000*/

		System.out.println("__________________Apartado 2_________________");
		System.out.println("---------------------------------------------");
		nPoblacion = 10;
		generaciones = 100;
		nReglas = 30;
		numReglasAleat = true;
		pCruce = 0.6;
		pMut = 0.001;
		elit = 0.05;
		reempStrategy = new ReemplazoTotal();
		selecStrategy = new SeleccionProporcionalFitness();
		c = new ClasificadorGenetico(generaciones,nPoblacion,nReglas,numReglasAleat,pCruce,pMut,elit,reempStrategy,selecStrategy);
		errores = Clasificador.validacion(part, d, c,porc,true);
		String msg = String.format("Valores: \n\t Población: %d \t Generaciones: %d \n\t Mismo número de reglas: %b \n\t Probabilidades:\n\t\t cruce: %.2f%%\t mutación: %.2f%%\n\t Elitismo %.2f %%\n\t Selección: %s\n\t Reemplazo: %s",nPoblacion,generaciones,numReglasAleat,pCruce*100,pMut*100,elit*100,reempStrategy,selecStrategy);
		System.out.println(msg);
		System.out.println("Errores obtenidos: " + errores);
		double media = 0;
		for (double e : errores)
			media += e;
		media /= errores.size();
		System.out.println(String.format("Min: %f.2 \t Max: %f.2 \t Media: %f.2",Collections.max(errores),Collections.min(errores),media));
		
		
		}

}
