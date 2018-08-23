package app;

import java.util.ArrayList;
import java.util.List;

public class Filter {

	 /*
	  * https://en.wikipedia.org/wiki/High-pass_filter#Discrete-time%20realization
	  */
	 
	 public List<Double> HighPassFilter(List<Double> input, float cutoff, int srate){
		List<Double> output = new ArrayList<Double>();
		double dt = 1/(double)srate;
	    double RC 	 = 1/(2*cutoff*Math.PI);
	    double ALPHA = RC/( RC + dt ); 
	    double value;
	    output.add(input.get(0));
	       for (int i = 1; i < input.size(); i++) {
	    	   value = ALPHA * (input.get(i) - input.get(i-1) + output.get(i-1) );
	           output.add(value);
	       }

		if (input == null)
	           throw new NullPointerException("input and prev float arrays must be non-NULL");

		return output;
	 }

	 /*
	  * Time smoothing constant for low-pass filter 0 ¡Â ¥á ¡Â 1 
	  * http://en.wikipedia.org/wiki/Low-pass_filter#Discrete-time_realization
	  */
   public List<Double> LowPassFilter(List<Double> input, float cutoff, int srate){

	   List<Double> output = new ArrayList<Double>();
		double dt = 1/(double)srate;

       if (input == null)
           throw new NullPointerException("input and prev float arrays must be non-NULL");
    
       double RC	 = 1/(2*cutoff*Math.PI);
       double ALPHA  = dt/( RC + dt ); 
       double value;
     
       output.add(ALPHA * input.get(0));
       for (int i = 1; i < input.size(); i++) {
    	   value = output.get(i-1) + ALPHA * (input.get(i) - output.get(i-1));
           output.add(value);
       }

       return output;
   }
 
   
}
