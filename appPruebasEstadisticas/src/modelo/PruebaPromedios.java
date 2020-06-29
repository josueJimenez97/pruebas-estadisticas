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
public class PruebaPromedios {
    private ArrayList<Double> muestra;
    private double nivelSignificancia;
    private int tamMuestra;
    private double mediaMuestral;
    private double estadisticoZo;
    private HashMap<Integer,ArrayList<Double>> tablaDistribucionN;
    private double estadisticoAlfaMedios;
    public PruebaPromedios(String muestraEn,double nivelSigificancia){
        this.muestra=new ArrayList();
        tablaDistribucionN=new HashMap<>();
        this.nivelSignificancia=nivelSigificancia;
        agregarDatosMuestra(muestraEn);
        cargarDatosTabla();
    }
    private void agregarDatosMuestra(String muestraEn){
        try {
            muestraEn=muestraEn.replace(".", ",");
            Scanner sc = new Scanner(muestraEn);

            while (sc.hasNext()) {
                double numero=sc.nextDouble();
                //System.out.println(numero);
                muestra.add(numero);
            }
            tamMuestra=muestra.size();
            sc.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void cargarDatosTabla(){
         try {
            File file = new File("src/tablas/distribucion normal.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                linea.replace('.',',');
                String[] datos= linea.split(" ");
                int clave=Integer.parseInt(datos[0]);
                ArrayList<Double> listaAux;
                if(tablaDistribucionN.containsKey(clave)){
                    listaAux=tablaDistribucionN.get(clave);
                }else{
                    listaAux=new ArrayList();
                }
                for(int i=1; i<datos.length;i++){
                    listaAux.add(Double.parseDouble(datos[i]));
                }
                tablaDistribucionN.put(clave, listaAux);
                
            }
            sc.close();
            double decimal=nivelSignificancia/100;
            int primerDecimal=Integer.parseInt((decimal+"").charAt(2)+"");
            int segundoDecimal=Integer.parseInt((decimal+"").charAt(3)+"");
            estadisticoAlfaMedios=tablaDistribucionN.get(primerDecimal).get(segundoDecimal);
             System.out.println(estadisticoAlfaMedios);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void calcularMediaMuestral(){
        double sumatoria=0;
        for(double d:muestra){
            sumatoria+=d;
        }
        mediaMuestral=sumatoria/tamMuestra;
    }
    
    private void calcularEstadisticoZo(){
        estadisticoZo=((0.5-mediaMuestral)*Math.sqrt(tamMuestra)/Math.sqrt(1.0/12.0));
        //estadisticoZo=((mediaMuestral-0.5)*Math.sqrt(tamMuestra)/Math.sqrt(1.0/12.0));
        
    }
    public boolean concluir(){
        calcularMediaMuestral();
        calcularEstadisticoZo();
        return estadisticoZo<estadisticoAlfaMedios;
    }
    
}
