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
public class ChiCuadrado {
    private double col;
    private double valor;

    public ChiCuadrado(double col, double valor) {
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
