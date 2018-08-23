package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {

	private static fft ft;
	private static FillNull fn ;
	private static fitting fg;
	private static Filter filter;
	private static double[] result;
    private static final int srate = 125;	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] resultx;		double[][] resulty;
		Complex[] x;			Complex[] y; 
        double[] fitting;
		
		filter = new Filter();
		
		/* read data - skip*/
		List<Double> orgX = new ArrayList<Double>();
		List<Double> orgY = new ArrayList<Double>();
		List<Double> time = new ArrayList<Double>();
        String csvFile = "/Users/Kwonda/Desktop/data.txt";
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\t+");
                orgX.add(Double.parseDouble(data[0]));
                orgY.add(Double.parseDouble(data[1]));
                time.add(Double.parseDouble(data[2]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        

        /* data setting - must do */
		int n = time.size();
		x = new Complex[n];		y = new Complex[n]; 
		List<Double> fiX = new ArrayList<Double>();
		List<Double> fiY = new ArrayList<Double>();
	        
		//pre-processing		
		
	    // data = fn.FillNull(x, y, time);	

		fiX = filter.LowPassFilter(filter.HighPassFilter(orgX,3,srate),15,srate);	
		fiY = filter.LowPassFilter(filter.HighPassFilter(orgY,3,srate),15,srate); 

		for (int i = 0; i < n; i++) {
			x[i] = new Complex(fiX.get(i), 0);
			y[i] = new Complex(fiY.get(i), 0);
		}
/*		
		FileOutputStream f = new FileOutputStream(new File("data.txt"));
		ObjectOutputStream o = new ObjectOutputStream(f);
		String s = Arrays.toString(x);
		o.writeObject(s);
		o.close();
		f.close();
	*/	
		
        //separate data 
		int start = 1;
		List<Integer> slice = new ArrayList<Integer>();
		Dataslice ds = new Dataslice();
		slice = ds.Dataslice(n);
		int m = slice.size();	

		resultx = new double[m][5];
		resulty = new double[m][5];
        /* ******************************** FFT *****************************************/
		for(int k = 0 ; k < m; k++) {
			
			int length = (int) Math.pow(2, slice.get(k));
			Complex[] xi = new Complex[length];			Complex[] yi= new Complex[length]; 
			for (int i = 0; i < length; i++) {
				xi[i] = x[start + i];
				yi[i] = y[start + i];
			}
			start = start + length; 
			Complex[] fftx = ft.fft(xi);        Complex[] ffty = ft.fft(yi);
			// change the data type: Complex ---> double 
        	double[] absfftx = new double[length/2]; 
        	double[] absffty = new double[length/2];   
        	
    		absfftx[0] = 2*fftx[0].abs()/length;
    		absffty[0] = 2*ffty[0].abs()/length;
    		
        	for(int i = 1; i < length/2; i++) {
        		absfftx[i] = 2*fftx[i].abs()/length;
        		absffty[i] = 2*ffty[i].abs()/length;
        	}
        	
        	double[] index = new double[length/2];
        	for(int j = 0; j < length/2 ; j++) {
        		index[j] = (double)srate*(double)j/(double)length;
        	}
        	
			resultx[k] = ft.analysis(absfftx, index);
			resulty[k] = ft.analysis(absffty, index); 
	        
	        
		}// end FFT calculation
		
		result = new double[5];
		for(int j = 0; j < 5 ; j++) {	
			result[j] = 0;
			for(int i = 0; i < m ; i++) {
				result[j] = (result[j] + resultx[i][j] + resulty[i][j]);
			}
			result[j] = result[j]/(2*m);
		}
	

        
        System.out.println("mean : " +result[0] + "	std : " +result[1] +
        				"	standard : " +  result[2]+ "	amp : " + result[3] + "	Hz : " + result[4]);
        /* ******************************** fitting *************************************/
        
        fitting = new double[2];
        fg = new fitting();
        fitting =  fg.fitting(orgX, orgY, time);
        System.out.println("fitting mean : "+ fitting[0] + "	fitting std : " + fitting[1]);
        
	}
}