/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import datos.TiposDeAtributos;

/**
 *
 * @author vicdejuan
 */
public class dataStructure {
    private double val;
    private String str;
    private TiposDeAtributos tipoAtributo;

    public TiposDeAtributos getTipoAtributo() {
        return tipoAtributo;
    }
    
    public dataStructure(double val, TiposDeAtributos atrb){
        this.val = val;
        tipoAtributo = atrb;
        this.str = null;
    }
    public dataStructure(String str, TiposDeAtributos atrb){
        this.str = str;
        tipoAtributo = atrb;
        this.val = Double.NaN;
    }
    
    /**
     * Devuelve el dato almacenado en la estructura, dependiendo del tipo de atributo que sea.
     * @return 
     */
    public Object getVal(){
        if (this.tipoAtributo == TiposDeAtributos.Nominal) 
            return str;
        else 
            return val;
    }

	public void setVal(double val) {
		this.val = val;
	}

}
