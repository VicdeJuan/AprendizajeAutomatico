package comparadores;
import java.util.ArrayList;
import java.util.Comparator;

import Datos.Datos;
	
	public class ComparadorEuclideo implements Comparator<ArrayList<String>> {

		ArrayList<String> point;
		Datos datos;

		public ComparadorEuclideo(ArrayList<String> point, Datos datos) {
			this.point = point;
			this.datos = datos;
		}

		private double distancia(ArrayList<String> p) {
			double dist=0;
			//DEBUG
			//System.out.println("Funcion distancia: point: "+point+" - p: "+p);
			for (int i=0; i < p.size()-1; i++){
				if(datos.isContinuos(datos.getNombreAtrs().get(i))){
					dist = dist + Math.pow(Double.parseDouble(p.get(i))-Double.parseDouble(point.get(i)), 2);
				}else{
					if(!(p.get(i).equals(point.get(i)))){
						dist = dist + 1/(datos.getA().get(datos.getNombreAtrs().get(i)).size());
					}
				}
			}
			return dist;
		}

		@Override
		public int compare(ArrayList<String> p1, ArrayList<String> p2) {
			double a = distancia(p1);
			double b = distancia(p2);
			//DEBUG
			//System.out.println("Funcion compare: p1: "+p1+" - p2: "+p2);
			//System.out.println("Comparamos "+a+" con "+b+": "+ Double.compare(a, b) );
			return Double.compare(a, b);
		}
	}

