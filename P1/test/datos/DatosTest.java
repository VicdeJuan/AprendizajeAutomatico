/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import datos.TiposDeAtributos;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import particionado.Particion;

/**
 *
 * @author vicdejuan
 */
public class DatosTest {
    
    public DatosTest() {
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
     * Test of cargaDeFichero method, of class Datos.
     */
    @Test
    public void testCargaDeFichero() {
        System.out.println("cargaDeFichero");
        String nombreDeFichero = "data/test.data";
        ArrayList<TiposDeAtributos> Atrb;
        Atrb = new ArrayList<> ();
        
        Atrb.add(TiposDeAtributos.Nominal);
        Atrb.add(TiposDeAtributos.Continuo);
        Atrb.add(TiposDeAtributos.Continuo);
        Atrb.add(TiposDeAtributos.Nominal);
        Atrb.add(TiposDeAtributos.Nominal);
        Atrb.add(TiposDeAtributos.Nominal);
        Atrb.add(TiposDeAtributos.Nominal);
        Atrb.add(TiposDeAtributos.Continuo);
        Atrb.add(TiposDeAtributos.Nominal);
        Atrb.add(TiposDeAtributos.Nominal);
        Atrb.add(TiposDeAtributos.Continuo);
        Atrb.add(TiposDeAtributos.Nominal);
        Atrb.add(TiposDeAtributos.Nominal);
        Atrb.add(TiposDeAtributos.Continuo);
        Atrb.add(TiposDeAtributos.Continuo);
        Atrb.add(TiposDeAtributos.Nominal);
        
        Datos result = Datos.cargaDeFichero(nombreDeFichero);
        assertEquals(6, result.getNumDatos());
        assertEquals(result.datos.get(0).length, Atrb.size());
        assertEquals(result.tipoAtributos, Atrb);
    }
    
}
