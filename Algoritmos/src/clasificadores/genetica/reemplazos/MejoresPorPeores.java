package clasificadores.genetica.reemplazos;

import clasificadores.genetica.Poblacion;

public class MejoresPorPeores extends ReemplazoAbstract {

	@Override
	public Poblacion Reemplazar(Poblacion progenitores, Poblacion vastagos) {
		Poblacion toret = new Poblacion(progenitores);
		toret.setElitismo(progenitores.getElitismo());

		toret = Poblacion.join(vastagos, progenitores);
		toret.crop(progenitores.getSize());
		
		return toret;
	}

}
