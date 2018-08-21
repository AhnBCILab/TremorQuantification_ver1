package app;


import java.io.File;
import java.lang.Math.*;
import java.lang.Object;

/*
 * Class LowPassFilter
 *  @see http://en.wikipedia.
 *      org/wiki/Low-pass_filter
 */
public class LowPassFilter extends AbstractFilter {
	  double[] smoothed;
	  long    lastTime = 0;
	  float   timeConstant; 

	  public LowPassFilter(SampleProvider  source, float timeConstant) {
		super(source);
	    smoothed = new double[sampleSize];
	    this.timeConstant = timeConstant;
	  }

	  public void fetchSample(double[] dst, int off) {
	    super.fetchSample(dst, off);
	    if (lastTime == 0 || timeConstant == 0) {
	      for (int axis = 0; axis < sampleSize; axis++) {
	        smoothed[axis] = (dst[off + axis]);
	      }
	    }
	    else {
	      float dt = (float) ((System.currentTimeMillis() - lastTime) / 1000.0);
	      float a = dt / (timeConstant + dt);
	      for (int axis = 0; axis < sampleSize; axis++) {
	        smoothed[axis] = (1f - a) * smoothed[axis] + a * (dst[off + axis]);
	        dst[axis + off] = smoothed[axis];
	      }
	    }
	    lastTime = System.currentTimeMillis();
	    return;
	  }

	  public void setTimeConstant(float timeConstant) {
	    this.timeConstant = timeConstant;
	  }
 }