package clasificadores;

import java.util.ArrayList;
import java.util.Scanner;

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
		ArrayList<Double> errores;
		
		d = Datos.cargaDeFichero("wdbc.data");
		d = Datos.cargaDeFichero("heart-disease.data");
		d = Datos.cargaDeFichero("winequality.data");
		
		part =  new ValidacionSimple();
		
		//___________________ Apartado 2 ________________________
		System.out.println("__________________Apartado 2_________________");
		System.out.println("---------------------------------------------");
		d = Datos.cargaDeFichero("wdbc.data");
		c = new ClasificadorKNN(1);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc k=1:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(3);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc k=3:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(5);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc k=5:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(11);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc k=11:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(21);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc k=21:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(51);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc k=51:");
		for(Double err : errores){
			System.out.println(err);
		}

		
		
		System.out.println("---------------------------------------------");
		d = Datos.cargaDeFichero("heart-disease.data");
		c = new ClasificadorKNN(1);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("heart-disease k=1:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(3);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("heart-disease k=3:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(5);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("heart-disease k=5:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(11);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("heart-disease k=11:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(21);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("heart-disease k=21:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(51);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("heart-disease k=51:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		
		
		System.out.println("---------------------------------------------");
		d = Datos.cargaDeFichero("winequality.data");
		c = new ClasificadorKNN(1);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("winequality k=1:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(3);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("winequality k=3:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(5);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("winequality k=5:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(11);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("winequality k=11:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(21);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("winequality k=21:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorKNN(51);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("winequality k=51:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		
		
		//___________________ Apartado 3 ________________________
		System.out.println("__________________Apartado 3_________________");
		System.out.println("---------------------------------------------");
		d = Datos.cargaDeFichero("wdbc.data");
		c = new ClasificadorRegLog(1,0.5);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc epocas=1, cte Aprendizaje=0.5:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorRegLog(5,0.5);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc epocas=5, cte Aprendizaje=0.5:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorRegLog(20,0.5);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc epocas=20, cte Aprendizaje=0.5:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorRegLog(1,1);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc epocas=1, cte Aprendizaje=1:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorRegLog(5,1);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc epocas=5, cte Aprendizaje=1:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorRegLog(20,1);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc epocas=20, cte Aprendizaje=1:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorRegLog(1,1.5);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc epocas=1, cte Aprendizaje=1.5:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorRegLog(5,1.5);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc epocas=5, cte Aprendizaje=1.5:");
		for(Double err : errores){
			System.out.println(err);
		}
		
		System.out.println("---------------------------------------------");
		c = new ClasificadorRegLog(20,1.5);
		errores = Clasificador.validacion(part, d, c,porc,true);
		System.out.println("wdbc epocas=20, cte Aprendizaje=1.5:");
		for(Double err : errores){
			System.out.println(err);
		}
	}

}
