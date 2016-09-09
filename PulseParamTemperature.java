
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
	public String toString()
	{
		String x=" Temperature: "+this.Temperature+" Pulse Param: "+ this.PulseParam;
		return x;
	}
	public boolean equals(Object o)
	{
		if(!( o instanceof PulseParamTemperature))
		{
			return false;
		}
		else
		{
			PulseParamTemperature convertedObj= (PulseParamTemperature)o;
			if(convertedObj.getPulseParam()==this.PulseParam && convertedObj.getTemperature()== this.Temperature)
			{
				return true;
			}
		}
		return false;
	}

}
