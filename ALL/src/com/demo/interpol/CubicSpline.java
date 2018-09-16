package com.demo.interpol;


import java.util.Vector;

public class CubicSpline implements InterpolationMethod {
    private int i = 1, l = 0, n = 0, lim_left = 0, lim_right = 0;
    private float result = 0;
    private double[][] matrix;
    public Vector<Double> data;
    private boolean flag = false;
    @Override
    public double calculateResult(double t, Vector<Double> xx, Vector<Double> yy) {
        n = xx.size();
        lim_right = n - 5;
        matrix = new double[n - 2][n - 1];
        data = new Vector<Double>();
        data.add(2 * (xx.elementAt(i + 1) - xx.elementAt(i - 1)));
        data.add((xx.elementAt(i + 1) - xx.elementAt(i)));
        for (int z = 0; z < (n - 4); z++) {
            data.add(0.0);
        }
        //Movimiento1
        data.add(addFromElement(xx, yy));
        //Movimiento2
        inicioFun(xx, yy);
        for (int z = 0; z < (n - 4); z++) {
            data.add(0.0);
        }
        data.add((xx.elementAt(i) - xx.elementAt(i - 1)));
        data.add(2 * (xx.elementAt(i + 1) - xx.elementAt(i - 1)));
        data.add(addFromElement(xx, yy));
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < (i + 1); k++) {
                matrix[j][k] = data.elementAt(l);
                l++;
            }
        }
        return getFunction(t, xx, yy, Gauss(matrix, (n - 2)), n);
    }
    private double[] Gauss(double matrix[][], int n) {
        double d2x[] = new double[n + 2];
        int i, j, k;
        double pivot, zero;

        for (i = 0; i < n; i++) {
            pivot = matrix[i][i];
            for (j = i; j < (n + 1); j++) {
                matrix[i][j] = matrix[i][j] / pivot;
            }
            for (k = 0; k < n; k++) {
                if (k != i) {
                    zero = -matrix[k][i];
                    for (j = i; j < (n + 1); j++) {
                        matrix[k][j] = matrix[k][j] + (zero * matrix[i][j]);
                    }
                }
            }
        }
        d2x[0] = 0;
        for (int l = 0; l < (d2x.length - 2); l++) {
            d2x[l + 1] = matrix[l][n];
        }
        d2x[n + 1] = 0;
        return d2x;
    }
    private float getFunction(double t, Vector<Double> x, Vector<Double> fx, double d2x[], int n) {
        i = 1;
        try {
            while (i < n) {
                if ((t >= x.elementAt(i - 1)) && (t <= x.elementAt(i))) {
                    result = (float) (((d2x[i - 1] / (6 * (x.elementAt(i) - x.elementAt(i - 1)))) * Math.pow((x.elementAt(i) - t), 3)) +
                            ((d2x[i] / (6 * (x.elementAt(i) - x.elementAt(i - 1)))) * Math.pow((t - x.elementAt(i - 1)), 3)) +
                            ((fx.elementAt(i - 1) / (x.elementAt(i) - x.elementAt(i - 1)) - ((d2x[i - 1] * (x.elementAt(i) - x.elementAt(i - 1))) / 6)) * (x.elementAt(i) - t)) +
                            (((fx.elementAt(i) / (x.elementAt(i) - x.elementAt(i - 1))) - ((d2x[i] * (x.elementAt(i) - x.elementAt(i - 1))) / 6)) * (t - x.elementAt(i - 1))));
                    flag = true;
                }
                i++;
            }
            if (!flag)
                System.out.println("Fuera de rango");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }
    //Movimiento1
    private double addFromElement(Vector<Double> element1, Vector<Double> element2) {

        return (((6 / (element1.elementAt(i + 1) - element1.elementAt(i))) * (element2.elementAt(i + 1) - element2.elementAt(i))) +
                ((6 / (element1.elementAt(i) - element1.elementAt(i - 1))) * (element2.elementAt(i - 1) - element2.elementAt(i))));
    }
    //Movimiento2
    private void inicioFun(Vector<Double> element3, Vector<Double> element4) {
        for (i = 2; i < (n - 2); i++) {
            for (int z = 0; z < lim_left; z++) {
                data.add(0.0);
            }
            lim_left++;
            data.add((element3.elementAt(i) - element3.elementAt(i - 1)));
            data.add(2 * (element3.elementAt(i + 1) - element3.elementAt(i - 1)));
            data.add((element3.elementAt(i + 1) - element3.elementAt(i)));

            for (int z = 0; z < lim_right; z++) {
                data.add(0.0);
            }
            lim_right--;
            data.add(addFromElement(element3, element4));
        }
    }
    @Override
    public String toString() {
        return "Cubic Spline";
    }
}