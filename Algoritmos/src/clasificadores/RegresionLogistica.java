/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.TiposDeAtributos;
import datos.dataStructure;
import java.util.HashMap;
import java.util.Map;
import particionado.AAUtils;

/**
 *
 * @author vicdejuan
 */
public class RegresionLogistica extends Clasificador {

    HashMap<String, Double> coef;
    HashMap<String, Double> valoresParaNominales;
    private final String STR_DELIM = "_";
    private double eta;
    int numEpoc;
    HashMap<String, Integer> tStr;
    String[] tArr;

    RegresionLogistica(int epocas, double eta) {
        this.eta = eta;
        numEpoc = epocas;
    }

    @Override
    public void entrenamiento(Datos datosTrain) {
        // Solo se trabaja clasificando 2 clases.
        int numClases = 2;
        // Los coeficientes serán los b_i tales que p(x) = (1,x) · b. Teniendo b n+1 dimensiones si hay n atributos.
        // El numero de atributos INCLUYE la clase, por eso tiene n+1 de dimensión.
        coef = new HashMap<>();
        tStr = new HashMap<>();
        tArr = new String[numClases];
        

		double val;
        HashMap<String, Double> coef_aux = new HashMap<>();

		//
        // Conversión de las clases a t_i = 0,1.
        // TODO: en caso de incluir regresión logística para más clases,  arreglar este bucle.
        //
       
        String clase;
        for (int i = 0; i < numClases; i++) {
            clase = datosTrain.getClases().keySet().toArray()[i].toString();
            tStr.put(clase, i);
            tArr[i] = clase;
        }

        // Inicializamos los valores de coef_aux aleatorios entre -1 y 1
        for (String atrb : datosTrain.getNomDatos()) {
            if (atrb.equals("Class")) {
                val = 1;
            } else // Valor entre -1 y 1
            {
                val = 2 * Math.random() - 1;
            }

            coef_aux.put(atrb, val);
        }
        // Bucle en si del calculo de los coeficientes.
        for (int iepoc = 1; iepoc < numEpoc; iepoc++) {
            for (HashMap<String, dataStructure> line : datosTrain.getDatos()) {
                val = escalarProdHash(convertHashFromDiscToCont(line), coef_aux);
                val = 1 / (1 + Math.exp(-val));
                // Actualizamos coef_aux
                coef_aux = restaHash(coef_aux, linearProdHash(eta * (val - tStr.get(datosTrain.getClassFromRow(line))), convertHashFromDiscToCont(line)));
            }
        }

        coef = coef_aux;

    }

    private HashMap<String, Double> restaHash(HashMap<String, Double> v1, HashMap<String, Double> v2) {
        HashMap<String, Double> toret = new HashMap<>();
        for (String key : v1.keySet()) {
            toret.put(key, v1.get(key) - v2.get(key));
        }

        return toret;
    }

    private HashMap<String, Double> linearProdHash(double esc, HashMap<String, Double> vector) {
        HashMap<String, Double> toret = new HashMap<>();
        for (Map.Entry<String, Double> entry : vector.entrySet()) {
            toret.put(entry.getKey(), esc * entry.getValue());
        }
        return toret;
    }

    @Override
    public HashMap<String, Double> clasifica(Datos datosTest) {
        HashMap<String, Double> retval = new HashMap<>();
        String clas_max;
        double val;
        for (HashMap<String, dataStructure> line : datosTest.getDatos()) {
            val = escalarProdHash(convertHashFromDiscToCont(line), coef);
            val = 1 / (1 + Math.exp(-val));
            /**
             * TODO: El 1 y el 0 aquí... lo he puesto a mano y es una chapuza.
             * Cuando lo veamos en clase sabré a qué clase corresponde >0.5 y a
             * cual no.
             */
            int index;
            if (val > 0.5) {
                index = 1;
            } else {
                index = 0;
            }

            clas_max = datosTest.getClases().keySet().toArray()[index].toString();

            AAUtils.AddOrCreate(retval, clas_max, 1);
        }
        /**
         * debería ser M-> 211 ; Es M-> 209 B-> 358 ; Es B-> 360
         */

        return retval;
    }

    /**
     * Producto escalar entre 2 hashes coordenada a coordenada utilizando la
     * clave para asegurar.
     *
     * @param a : Primer vector del argumento.
     * @param b : segundo vector para multiplicar. Se recomienda el vector de
     * pesos como segundo argumento.
     * @return
     */
    private double escalarProdHash(HashMap<String, Double> a, HashMap<String, Double> b) {
        double acum = 0, debug1, debug2;
        if (a == null || b == null) {
            return Double.NaN;
        }
        if (a.isEmpty() || b.isEmpty() || (a.size() != b.size())) {
            return Double.NaN;
        }

        for (String key : a.keySet()) {
            if (!b.containsKey(key)) {
                return Double.NaN;
            }
            debug1 = a.get(key);
            debug2 = b.get(key);
            acum += a.get(key) * b.get(key);
        }

        return acum;
    }

    /**
     * Convierte un hashmap con valores discretos a un hashmap de valores
     * continuos.
     *
     * @param line : Vector de atributos
     * @return
     */
    private HashMap<String, Double> convertHashFromDiscToCont(HashMap<String, dataStructure> line) {
        HashMap<String, Double> retval = new HashMap<>();
        double val = 0;
        for (Map.Entry<String, dataStructure> entry : line.entrySet()) {
            if (entry.getValue().getTipoAtributo() == TiposDeAtributos.Continuo) {
                val = (Double) entry.getValue().getVal();
            } else {
                /*Si es nominal, convertimos (aunque tal vez este caso no se de nunca)*/
                val = convertCont(entry.getKey(), (String) entry.getValue().getVal());
            }
            retval.put(entry.getKey(), val);
        }

        return retval;
    }

    /**
     * Convierte un atributo nominal en un atributo continuo utilizando los
     * pesos construidos en el entrenamiento.
     *
     * @param atributo : nombre del atributo que convertir
     * @param valorNominal : valor nominal para ser convertido a double
     * @return valor double correspondiente al valorNominal.
     */
    private double convertCont(String atributo, String valorNominal) {
        double val = 0;

        if ("Class".equals(atributo)) {
            val = 1;
        } else {
            val = valoresParaNominales.get(atributo + STR_DELIM + valorNominal);
        }
        return val;
    }

    /**
     * Método necesario para los test.
     *
     * @param coeficientes
     */
    void setCoef(HashMap<String, Double> coeficientes) {
        coef = coeficientes;
    }

    public HashMap<String, Double> getCoef() {
        return coef;
    }

}
