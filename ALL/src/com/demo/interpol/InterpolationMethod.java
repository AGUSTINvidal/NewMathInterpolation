package com.demo.interpol;

import java.util.Vector;

public interface InterpolationMethod {
	double calculateResult(double t, Vector<Double> xx, Vector<Double> yy);
}