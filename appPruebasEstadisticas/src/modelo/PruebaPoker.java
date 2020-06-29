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
public class PruebaPoker {
    private ArrayList<String> muestra;
    private HashMap<Integer, ArrayList<ChiCuadrado>> tablaChi;
    private double[] columnas = {0.001, 0.0025, 0.005, 0.01, 0.025, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5,
        0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 0.975, 0.99, 0.995, 0.9975, 0.999};
    private double[] FE;
    private double[] prob={0.3024,0.5040,0.1080,0.072,0.0090,0.00450,0.00010};
    private int[] FO;
    private int n;
    private double estadistico;
    private double estadisticoTeorico;
    private double alfa;
    
    public PruebaPoker(String datoMuestra,int alfa){
        this.alfa=(alfa+0.0)/100;
        estadistico=0.0;
        estadisticoTeorico=0.0;
        muestra=new ArrayList<>();
        tablaChi=new HashMap();
        FE =new double[7];
        FO =new int[7];
        cargarDatosTabla("chi cuadrado1.txt", 0);
        cargarDatosTabla("chi cuadrado2.txt", 15);
        recuperarMuestra(datoMuestra);
    }
    
    private void recuperarMuestra(String datoMuestra) {
        try {
            datoMuestra = datoMuestra.replace(".", ",");
            Scanner sc = new Scanner(datoMuestra);
            
            while (sc.hasNext()) {
                double numero = sc.nextDouble();
                String x=numero+"";
                //System.out.println(x.substring(2));
                muestra.add(x.substring(2));
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
    private void determinarFE(){
        for(int i=0;i<7;i++){
            FE[i]=n*prob[i];
        }
    }
    private void determinarFO(){
        for(String s: muestra){
            FO[getIndiceFO(s)]++;
        }
    }
    private int getIndiceFO(String s){
        int[] numeros=new int[10];
        boolean[] seleccionado=new boolean[5];
        for(int i=0;i<s.length();i++){
            if(!seleccionado[i]){
                char c=s.charAt(i);
                int cont=1;
                for(int j=i+1;j<s.length();j++){
                    if(c==s.charAt(j)){
                        cont++;
                        seleccionado[j]=true;
                    }
                }
                if(cont==5||cont==4){
                    cont++;
                }
                if(cont==1)
                    cont=0;
                if(cont==2)
                    cont=1;
                numeros[c-48]=cont;
            }
            
        }
        int sum=0;
        for(int i=0;i<10;i++){
            sum+=numeros[i];
        }
        return sum;
    }
    private void calcularEstadistico(){
        for(int i=0;i<7;i++){
            //System.out.println(FO[i]+" "+FE[i]);
            estadistico+=((FE[i]-FO[i])*(FE[i]-FO[i]))/FE[i];
        }
    }
    private void calcularEstadisticoTeorico(){
        for (ChiCuadrado c : tablaChi.get(6)){
            if (c.getCol() == alfa) {
                estadisticoTeorico=c.getValor();
                //System.out.println(c.mostrarDatos());
                break;
            }
            //System.out.println(c.mostrarDatos());
        }
    }
    
    public boolean concluir(){
        determinarFE();
        determinarFO();
        calcularEstadistico();
        calcularEstadisticoTeorico();
        //System.out.println(estadistico);
        //System.out.println(estadisticoTeorico);
        return estadistico<estadisticoTeorico;
    }

    
    
}
