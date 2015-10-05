/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        
        
        HashMap<String, Integer> expResult = new HashMap<String,Integer>();
        expResult.put("+", 7);
        instance.entrenamiento(datos);
        HashMap<String, Integer> result = instance.clasifica(datos);
        assertEquals(expResult, result);
        
        
    }
    
    @Test
    public void testError() {
        System.out.println("error");
        
        instance.entrenamiento(datos);
        assertEquals(0,instance.error(datos, instance),0.0001);        
        
    }
    
}
