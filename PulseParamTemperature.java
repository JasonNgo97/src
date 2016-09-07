
public class PulseParamTemperature 
{
	private double PulseParam;
	private int Temperature;
	
	PulseParamTemperature(double PulseParam, int Temperature)
	{
		this.PulseParam=PulseParam;
		this.Temperature=Temperature;
	}
	public double getPulseParam()
	{
		return this.PulseParam;
	}
	public int getTemperature()
	{
		return this.Temperature;
	}

}
