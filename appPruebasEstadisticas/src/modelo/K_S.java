/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Administrator
 */
public class K_S {
    private double col,valor;

    public K_S(double col, double valor) {
        this.col = col;
        this.valor = valor;
    }

    public double getCol() {
        return col;
    }

    public double getValor() {
        return valor;
    }
    
    public String mostrarDatos(){
        return col+"\t"+valor;
    }
    
}
