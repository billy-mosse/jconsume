package ar.uba.dc.analysis.common;

public interface SummaryWriter<T> {

	public void write(T summary);
	public void setMainClass(String mainClass);		
}
