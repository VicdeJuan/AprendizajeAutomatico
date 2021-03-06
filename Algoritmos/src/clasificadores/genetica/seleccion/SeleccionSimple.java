package clasificadores.genetica.seleccion;

import java.util.ArrayList;
import clasificadores.genetica.Individuo;
import clasificadores.genetica.Poblacion;

public class SeleccionSimple implements Seleccion {


	@Override
	public Poblacion seleccionar(Poblacion P) {
		
		//Copy & Paste
		Poblacion p1 = P;
		Poblacion toret = new Poblacion(p1.getSize(),p1.getNumReglas(),p1.getNumAtributos(),p1.getProbMutacion(),p1.getProbCruce(),p1.getEstrategiaReemplazo(),true,p1.getNumReglasAleat(),p1.getEstrategiaSeleccion());

		
		toret.setElitismo(P.getElitismo());
		ArrayList<Individuo> toadd = new ArrayList<>(P.getSize());

		for (Individuo i : P.getIndividuos()){
			toadd.add(new Individuo(i));

		}
		
		
		toret.setIndividuos(toadd);
		toret.setSize(toadd.size());
		
		return toret;
	}
	
	@Override
	public String toString() {
		return "SeleccionSimple";
	}

}
