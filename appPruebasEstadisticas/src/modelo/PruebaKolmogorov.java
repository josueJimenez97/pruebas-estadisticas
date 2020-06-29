/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class PruebaKolmogorov {
    private ArrayList<Double> muestra;
    private HashMap<Integer,ArrayList<K_S>> tablaK_S;
    private double alfa;
    private int n;
    private double Do; //estadistico teorico
    private double Dmax; //estadistico calculado
    private double[] columnas={0.2,0.1,0.05,0.02,0.01};
    public PruebaKolmogorov(String datoMuestra,int alfa){
        this.alfa=alfa;
        n=0;
        Do=0.0;
        Dmax=0.0;
        muestra=new ArrayList();
        tablaK_S= new HashMap();
        recuperarMuestra(datoMuestra);
        cargarDatosTablaK_S();
        
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
            Collections.sort(muestra);
           

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
     private void cargarDatosTablaK_S(){
         try {
            File file = new File("src/tablas/kolmogorov.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                linea = linea.replace(',', '.');
                String[] datos = linea.split(" ");
                int clave = Integer.parseInt(datos[0]);
                ArrayList<K_S> listaAux;
                K_S objAux;

                if (tablaK_S.containsKey(clave)) {
                    listaAux = tablaK_S.get(clave);
                } else {
                    listaAux = new ArrayList();
                }
                int contador = 0;
                for (int i = 1; i < datos.length; i++) {
                    objAux = new K_S(columnas[contador], Double.parseDouble(datos[i]));
                    listaAux.add(objAux);
                    contador++;
                }
                tablaK_S.put(clave, listaAux);
            }
            sc.close();
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
     }
     
     private void calcularEstadistico(){
         double aux;
         for(int i=0;i<n;i++){
             aux=Math.abs(((i+1.0)/n)-muestra.get(i));
             if(aux>Dmax){
                 Dmax=aux;
             }
         }
     }
     private void calcularEstadisticoTeorico(){
         double a=alfa/100;
            for(K_S d: tablaK_S.get(n)){
                if(d.getCol()==a){
                    Do=d.getValor();
                    break;
                }
            }
     }
     public boolean concluir(){
         calcularEstadistico();
         calcularEstadisticoTeorico();
         //System.out.println(Do);
         //System.out.println(Dmax);
         return Dmax<Do;
     }
}
