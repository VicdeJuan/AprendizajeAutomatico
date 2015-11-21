/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import java.util.ArrayList;
import java.util.Random;
import static particionado.EstrategiaParticionado.SEED;

/**
 *
 * @author vicdejuan
 */
public class Poblacion {

    double elitismo;
    int size;
    ArrayList<Individuo> individuos;
    int cruce;
    double probCruce;
    double probMutacion;
    
    public Poblacion(int n, int numReglas, int numAtrib,double pMut,double pCruc){
        individuos = new ArrayList<>(n);
        for (int i=0;i<n;i++)
            individuos.add(new Individuo(numReglas, numAtrib));
        size = n;
        probMutacion=pMut;
        probCruce=pCruc;
    }
    
    public void mutacion(){
        Random r = new Random(SEED);
        for (Individuo i: individuos)
            if (r.nextDouble() >= probMutacion)
                i.mutar();
    }
    
}
