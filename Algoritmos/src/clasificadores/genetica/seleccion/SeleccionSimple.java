package clasificadores.genetica.seleccion;

import java.util.ArrayList;
import clasificadores.genetica.Individuo;
import clasificadores.genetica.Poblacion;

public class SeleccionSimple implements Seleccion {

	@SuppressWarnings("unchecked")
	@Override
	public Poblacion seleccionar(Poblacion P) {
		Poblacion toret = new Poblacion(P);
		toret.setElitismo(P.getElitismo());
		ArrayList<Individuo> toadd = new ArrayList<>();
		for (Individuo i : P.getIndividuos())
			toadd.add(new Individuo(i));
		toret.setIndividuos(toadd);
		toret.setSize(toadd.size());
		
		return toret;
	}

}
