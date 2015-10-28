/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import particionado.EstrategiaParticionado;
import particionado.ValidacionCruzada;
import particionado.ValidacionSimple;

/**
 *
 * @author vicdejuan
 */
public class ClasificadorAPrioriTest {
    
    public ClasificadorAPrioriTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    Datos datos = Datos.cargaDeFichero("data/test.data");
    ClasificadorAPriori instance = new ClasificadorAPriori();
    /**
     * Test of entrenamiento method, of class ClasificadorAPriori.
     */
    @Test
    public void testEntrenamiento() {
        System.out.println("entrenamiento");
        
        instance.entrenamiento(datos);
        
        assertEquals(instance.getClaseMasRepetida(),"+");
    }

    /**
     * Test of clasifica method, of class ClasificadorAPriori.
     */
    @Test
    public void testClasifica() {
        System.out.println("clasifica");
        
        
        HashMap<String, Double> expResult = new HashMap<>();
        expResult.put("+", 6.0);
        instance.entrenamiento(datos);
        HashMap<String, Double> result = instance.clasifica(datos);
        assertEquals(expResult, result);
        
        
    }
    
    @Test
    public void testError() {
        System.out.println("error");
        instance.entrenamiento(datos);
        assertEquals(1.0/6,instance.error(datos, instance),0.0001);        
        
    }
    
    @Test
    public void testValidacion() {
        System.out.println("Validación");
        
        
        EstrategiaParticionado estC = new ValidacionCruzada(10);
        EstrategiaParticionado estS = new ValidacionSimple();
        ArrayList<Double> ValCru = Clasificador.validacion(estC, datos, instance);
        ArrayList<Double> ValSim = Clasificador.validacion(estS, datos, instance);
        assertEquals(1,ValSim.size());
        assertEquals(10,estC.getNumeroParticiones());
        assertEquals(estC.getNumeroParticiones(),ValCru.size());
        assertEquals(0.000001,ValSim.get(0),0.0001);        
        for (Double ValCru1 : ValCru) {
            assertEquals(0, ValCru1, 0.0001);        
        }
    }
    
    
}
