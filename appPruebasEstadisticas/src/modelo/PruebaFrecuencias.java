/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class PruebaFrecuencias {

    private ArrayList<Double> muestra;
    private HashMap<Integer, ArrayList<ChiCuadrado>> tablaChi;
    private int k;
    private int n;
    private double FE; //frecuencia esperada
    private int[] FO;   //frecuencia observada
    private double alfa;
    private double estadisticoTeorico;
    private double estadistico;
    private double[] columnas = {0.001, 0.0025, 0.005, 0.01, 0.025, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5,
        0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 0.975, 0.99, 0.995, 0.9975, 0.999};

    public PruebaFrecuencias(String datoMuestra, int k, int alfa) {
        this.k = k;
        this.alfa = alfa;
        n=0;
        estadistico=0.0;
        tablaChi = new HashMap();
        muestra= new ArrayList();
        FO=new int[k];
        recuperarMuestra(datoMuestra);
        cargarDatosTabla("chi cuadrado1.txt", 0);
        cargarDatosTabla("chi cuadrado2.txt", 15);
        FE=(n+0.0)/k;
    }

    private void recuperarMuestra(String datoMuestra) {
        try {
            datoMuestra = datoMuestra.replace(".", ",");
            Scanner sc = new Scanner(datoMuestra);

            while (sc.hasNext()) {
                double numero = sc.nextDouble();
                //System.out.println(numero);
                muestra.add(numero);
                n++;
            }
            sc.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void cargarDatosTabla(String archivo, int indice) {
        try {
            File file = new File("src/tablas/" + archivo);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                linea = linea.replace(',', '.');
                String[] datos = linea.split(" ");
                int clave = Integer.parseInt(datos[0]);
                ArrayList<ChiCuadrado> listaAux;
                ChiCuadrado objAux;

                if (tablaChi.containsKey(clave)) {
                    listaAux = tablaChi.get(clave);
                } else {
                    listaAux = new ArrayList();
                }
                int contador = indice;
                for (int i = 1; i < datos.length; i++) {
                    objAux = new ChiCuadrado(columnas[contador], Double.parseDouble(datos[i]));
                    listaAux.add(objAux);
                    contador++;
                }
                tablaChi.put(clave, listaAux);

            }
            sc.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void calcularEstadisticoTeorico() {
        double col = alfa / 100;
        for (ChiCuadrado c : tablaChi.get(k - 1)) {
            if (c.getCol() == col) {
                estadisticoTeorico=c.getValor();
                //System.out.println(c.mostrarDatos());
                break;
            }
            //System.out.println(c.mostrarDatos());
        }
    }
    
    private void frecuenciaObservada(){
        for(double dato:muestra){
            for(int i=0;i<k;i++){
                if((dato<(i+1.0)/k)){
                    FO[i]++;
                    break;
                }
            }
        }
    }
    
    private void calcularEstadistico(){
        for(int i=0;i<k;i++){
            estadistico+=((FE-FO[i])*(FE-FO[i]))/FE;
        }
    }
    public boolean concluir(){
        frecuenciaObservada();
        calcularEstadistico();
        calcularEstadisticoTeorico();
        return estadistico<estadisticoTeorico;
    }
}

