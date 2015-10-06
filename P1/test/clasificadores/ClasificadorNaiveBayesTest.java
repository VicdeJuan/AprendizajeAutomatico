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
public class ClasificadorNaiveBayesTest {
    
    public ClasificadorNaiveBayesTest() {
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

    /**
     * Test of entrenamiento method, of class ClasificadorNaiveBayes.
     */
    @Test
    public void testEntrenamiento() {
        System.out.println("entrenamiento");
        Datos datosTrain = Datos.cargaDeFichero("data/naiveTrain.data");
        assertNotNull(datosTrain);
        ClasificadorNaiveBayes instance = new ClasificadorNaiveBayes();
        instance.entrenamiento(datosTrain);
        HashMap<String, HashMap<String, HashMap<String,Double>>> SMTGot = instance.getSMT();
        HashMap<String, HashMap<String, HashMap<String,Double>>> SMTExpected = new HashMap<>() ;
        
        /***************************************
        Expected results.
                +) 
            A1: a=.4  b=.6
            A2: 33.718
            A3: 3.125
            A4: u=1
            A5: g=1
            A6: q=0.4, w=0.2 m=0.2 r=0.2

                -)
            A1: b=1
            A2: 27.83
            A3: 1.54
            A4: u=1 
            A5: g=1 
            A6: w=1
        *****************************************/

        // Mas!
        HashMap<String,HashMap<String,Double>> mas = new HashMap<>();
        HashMap<String,HashMap<String,Double>> menos = new HashMap<>();
        HashMap<String,Double> add ;

        add = new HashMap<>();
        add.put("a", 0.4);
        add.put("b", 0.6);
        mas.put("A1", add);

        add = new HashMap<>();
        add.put("esp",33.718); add.put("var",1315.7246200000002);
        mas.put("A2",add);
                

        add = new HashMap<>();
        add.put("esp",3.125); add.put("var",13.772764999999998);
        mas.put("A3",add);

                 

        add = new HashMap<>();
        add.put("u",1.0);
        mas.put("A4",add);        

        add = new HashMap<>();
        add.put("g",1.0);
        mas.put("A5",add);        

        add = new HashMap<>();
        add.put("q",0.4); add.put("w",0.2); add.put("m",0.2); add.put("r",0.2);
        mas.put("A6",add);

        add = new HashMap<>();
        add.put("+",1.0);
        mas.put("Class",add);
        
        // Menos!
        
        add = new HashMap<>();
        add.put("b",1.0);
        menos.put("A1",add);

        add = new HashMap<>();
        add.put("esp",27.83); add.put("var",774.5088999999999);
        menos.put("A2",add);

        add = new HashMap<>();
        add.put("esp",1.54);
        add.put("var",2.3716);
        menos.put("A3",add);

        add = new HashMap<>();
        add.put("u",1.0); 
        menos.put("A4",add);

        add = new HashMap<>();
        add.put("g",1.0); 
        menos.put("A5",add);

        add = new HashMap<>();
        add.put("w",1.0);
        menos.put("A6",add); 

        add = new HashMap<>();
        add.put("-",1.0);
        menos.put("Class",add);
        

        SMTExpected.put("+",mas);
        SMTExpected.put("-",menos);
        System.out.print("Exp:\n"+SMTExpected);
        System.out.print("\nGot:\n"+SMTGot);
        assertEquals(SMTExpected, SMTGot);
    }

    /**
     * Test of clasifica method, of class ClasificadorNaiveBayes.
     */
    @Test
    public void testClasifica() {
        System.out.println("clasifica");
        Datos datosTest = null;
        ClasificadorNaiveBayes instance = new ClasificadorNaiveBayes();
        HashMap<String, Integer> expResult = null;
        HashMap<String, Integer> result = instance.clasifica(datosTest);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
