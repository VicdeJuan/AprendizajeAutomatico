/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import datos.TiposDeAtributos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import particionado.AAUtils;
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
        HashMap<String,TiposDeAtributos> Atrb = new HashMap<> ();
        
        Atrb.put("A1",TiposDeAtributos.Nominal);
        Atrb.put("A2",TiposDeAtributos.Continuo);
        Atrb.put("A3",TiposDeAtributos.Continuo);
        Atrb.put("A4",TiposDeAtributos.Nominal);
        Atrb.put("A5",TiposDeAtributos.Nominal);
        Atrb.put("A6",TiposDeAtributos.Nominal);
        Atrb.put("A7",TiposDeAtributos.Nominal);
        Atrb.put("A8",TiposDeAtributos.Continuo);
        Atrb.put("A9",TiposDeAtributos.Nominal);
        Atrb.put("A10",TiposDeAtributos.Nominal);
        Atrb.put("A11",TiposDeAtributos.Continuo);
        Atrb.put("A12",TiposDeAtributos.Nominal);
        Atrb.put("A13",TiposDeAtributos.Nominal);
        Atrb.put("A14",TiposDeAtributos.Continuo);
        Atrb.put("A15",TiposDeAtributos.Continuo);
        Atrb.put("Class",TiposDeAtributos.Nominal);
            
        Datos result = Datos.cargaDeFichero(nombreDeFichero);
        assertEquals(6, result.getNumDatos());
        assertEquals(result.datos.get(0).size(), Atrb.size());
        assertEquals(result.tipoAtributos, Atrb);
        assertEquals(result.getTipoAtributos().get("A13"),TiposDeAtributos.Nominal);
    }
     @Test
    public void testCargaDeFicheroPesos() {
        System.out.println("cargaDeFichero");
        String nombreDeFichero = "data/pesos.data";
        HashMap<String,TiposDeAtributos> Atrb = new HashMap<> ();
        Datos result = Datos.cargaDeFichero(nombreDeFichero);
        
        Atrb.put("Class",TiposDeAtributos.Nominal);
        Atrb.put("height",TiposDeAtributos.Continuo);
        Atrb.put("weight",TiposDeAtributos.Continuo);
        Atrb.put("footsize",TiposDeAtributos.Continuo);
        assertEquals(8, result.getNumDatos());
        assertEquals(Atrb,result.getTipoAtributos());
    }
    @Test
    public void estanNormalizados() {

	    
        System.out.println("cargaDeFichero");
        String nombreDeFichero = "data/wdbc.data";
        HashMap<String,TiposDeAtributos> Atrb = new HashMap<> ();
        Datos result = Datos.cargaDeFichero(nombreDeFichero);
	HashMap<String,Double> medias = new HashMap<>();
	HashMap<String,Double> medias_aux = new HashMap<>();

	
	for (HashMap<String, dataStructure> line : result.getDatos())
		for (Map.Entry<String, dataStructure> entry : line.entrySet()){
			if (entry.getValue().getTipoAtributo() != TiposDeAtributos.Nominal)
				AAUtils.AddOrCreate(medias_aux, entry.getKey(), (Double) entry.getValue().getVal());
		}
	for (Map.Entry<String, Double> entry : medias_aux.entrySet()){
		medias.put(entry.getKey(), entry.getValue()/result.getNumDatos());
	}

	for (Double val : medias.values())
		assertEquals(val,0,0.001);
    }
}
