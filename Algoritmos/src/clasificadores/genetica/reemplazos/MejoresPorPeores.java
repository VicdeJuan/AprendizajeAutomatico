package clasificadores.genetica.reemplazos;

import clasificadores.genetica.Poblacion;

public class MejoresPorPeores extends ReemplazoAbstract {

	@Override
	public Poblacion Reemplazar(Poblacion progenitores, Poblacion vastagos) {
		//Copy & paste
		Poblacion p1=progenitores;
		Poblacion toret = new Poblacion(p1.getSize(),p1.getNumReglas(),p1.getNumAtributos(),p1.getProbMutacion(),p1.getProbCruce(),p1.getEstrategiaReemplazo(),true,p1.getNumReglasAleat(),p1.getEstrategiaSeleccion());

		toret.setElitismo(progenitores.getElitismo());

		toret = Poblacion.join(vastagos, progenitores);
		toret.crop(progenitores.getSize());
		toret.setElitismo(progenitores.getElitismo());
		return toret;
	}

	@Override
	public String toString() {
		return "Mejores por peores";
	}
}
