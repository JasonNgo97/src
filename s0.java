
public class s0 
{
private int temperature;
private double calibrationConstant0;
private boolean helium;

s0(int temperature, double HeatCalibrationConstant, boolean helium)
{
	this.temperature=temperature;
	this.calibrationConstant0=HeatCalibrationConstant;
	this.helium=helium;
}

public void setCalibrationConstant(double calibrationConstant)
{
	this.calibrationConstant0=calibrationConstant;
}


public int getTemperature()
{
	return this.temperature;
	
}
public double getHeatStep0()
{
	return this.calibrationConstant0;
}
public boolean getHelium()
{
	return this.helium;
}
public String toString()
{
	String temp= new String();
	temp+=" Temperature: "+temperature+" Calibration Constant: "+calibrationConstant0;
	return temp;
}
}
