package com.demo;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;
import java.util.StringTokenizer;
import com.demo.interpol.InterpolationMethod;

public class InterpolationHelper {
    private FileReader input;
    private BufferedReader bufRead;
    private StringTokenizer xy;
    private String pathFile;
    private double t;
    private Vector<Double> x, y;
    private InterpolationMethod interpolationMethod;
    public InterpolationHelper(String pathFile, double t, InterpolationMethod interpolationMethod) {
        this.pathFile = pathFile;
        this.t = t;
        this.interpolationMethod = interpolationMethod;
        x = new Vector<Double>();
        y = new Vector<Double>();
    }
    private void readFile() {
        try {
            input = new FileReader(pathFile);
            /* Filtra FileReader atraves de un Buffered para leer una linea a la vez */
            bufRead = new BufferedReader(input);
            // Lee la primera linea
            String line = bufRead.readLine();
            // Lee atraves del archivo una linea a la vez.
            while (line != null) {
                xy = new StringTokenizer(line, "\t");
                while (xy.hasMoreTokens()) {
                    x.addElement(Double.parseDouble(xy.nextToken()));
                    y.addElement(Double.parseDouble(xy.nextToken()));
                }
                line = bufRead.readLine();
            }
            bufRead.close();
        } catch (IOException e) {
            //Si otra excepcion es generada, Imprime un rastro de la pila del error
            e.printStackTrace();
        }
    }
    public void getResult() {
        if (interpolationMethod != null) {
            readFile();
            System.out.println(interpolationMethod.toString());
            System.out.println("x = " + t + ",  f(x) = " + interpolationMethod.calculateResult(t, x, y));
        } else {
            System.out.println("No esta definido en la interpolacion del metodo.");
        }
    }
}