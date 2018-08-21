package app;

public interface SampleProvider {
	public abstract int sampleSize();
	public abstract void fetchSample(double[] sample, int offset);
}
