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
	private static FillNull fn;
	private static fitting fg;
	private static double[] result;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int srate = 250;// sampling rate
		double[][] resultx;
		double[][] resulty;

		
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
		int start = 1;
		List<Integer> slice = new ArrayList<Integer>();
		Dataslice ds = new Dataslice();
		slice = ds.Dataslice(n);
		int m = slice.size();
	    // fn = new FillNull(x, y, time);
		resultx = new double[m][5];
		resulty = new double[m][5];
		
        /* ******************************** FFT *****************************************/
		for(int k = 0 ; k < m; k++) {
			int length = (int) Math.pow(2, slice.get(k));
			Complex[] x = new Complex[length];		Complex[] y = new Complex[length]; 
			for (int i = 0; i < length; i++) {
				x[i] = new Complex(orgX.get(start+i), 0);
				y[i] = new Complex(orgY.get(start+i), 0);
			}

			start = start + length; 
			Complex[] fftx = ft.fft(x);        Complex[] ffty = ft.fft(y);
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
	
			FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			String s = Arrays.toString(absfftx);
			// Write objects to file
			o.writeObject(s);
			o.close();
			f.close();
	        
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
        
        double[] fitting = new double[2];
        fg = new fitting();
        fitting =  fg.fitting(orgX, orgY, time);
        System.out.println("fitting mean : "+ fitting[0] + "	fitting std : " + fitting[1]);
        
	}
}