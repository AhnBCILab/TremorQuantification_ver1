package com.ahnbcilab.tremorquantification.functions;

import java.util.List;

/*
 * Calculate how closely the given line is drawn to match the given line.
 * input :  X, Y data of drawn spiral 
 * output : total distance/ mean / standard deviation
 * */

public class fitting {
	
	private int srate = 125;
	
	public static baseline bring(double[] x, double[] y, double[] t) {
		return new baseline(x , y, t);}
	
	public double[] fitting(List<Double> orgX, List<Double> orgY, List<Double> time){
		double[] result = new double[2];
		int n = time.size();
		double[] objX = new double[n] ;		double[] objY = new double[n]  ; 		double[] t = new double[n]  ; 
		baseline base = bring(objX, objY, t);
		base.setting(n);
		objX = base.getArray1();
		objY = base.getArray2();
		//double[] t = base.getArray3();
		
		/* (not yet) find starting point */
		
		/* calculate distance */
		Filter ft = new Filter();
		// data need to lowpass filter implemented at 4 kHz in Kotlin
		List<Double> dataX= ft.LowPassFilter(orgX, 4,srate);
		List<Double> dataY= ft.LowPassFilter(orgY, 4,srate); 
		
		double[] distance = new double[n];
		for (int i = 0 ; i < n; i++) {
			 distance[i] = (dataX.get(i) - objX[i]) +  (dataY.get(i) - objY[i]);
		 }
		Calculater cal = new Calculater();
		result[0] = cal.mean(distance);
		result[1]  = cal.sd(distance);
		
		return result;
		 }		 
	}


class baseline{
	private double[] baseX;
	private double[] baseY;
	private double[] t;
	private float min = 0 ;//baseline scope
	private double max = 4*Math.PI;
	
	public baseline() {}
	public baseline(double[] array1, double[] array2, double[] time) {
		this.baseX = array1;
		this.baseY = array2;
		this.t = time;
	}

	public double[] getArray1() {return baseX;}
	public double[] getArray2() {return baseY;}
	public double[] getArray3() {return t;}
	
	public baseline setting(int length)
	{
		this.t = new double[length];  
		for (int i = 0; i < length; i++){  
		        this.t[i] = min + i * (max - min) / (length - 1); 
		        this.baseX[i] = t[i]*Math.cos(2.5*t[i]);
		        this.baseY[i] = t[i]*Math.sin(2.5*t[i]);
		}  
		return new baseline(baseX, baseY, t);
	}
	
}