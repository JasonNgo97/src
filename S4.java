import java.text.DecimalFormat;
import java.util.*;

public class S4 
{
	private String index;
	private int beginIndex;
	private int endIndex;
	
	
	private double PulseParam;
	private int Temperature;
	
	private double JacketLoss;
	
	private double PulsePowerMin;
	private double PulsePowerMax;
	private double PulsePowerMean;
	
	private ArrayList<Double> PHeater4;
	private GasLoss gLoss;
	private ArrayList<Double> minLENR;
	private ArrayList<Double> maxLENR;
	private ArrayList<Double> avgLENR;
	
	//This takes care of the middle
	private double MAXLENRavg;
	private double MINLENRavg;
	private double AVGLENRavg;
	
	private double MAXLENRmax;
	private double MINLENRmax;
	private double AVGLENRmax;
	
	private double MAXLENRmin;
	private double MINLENRmin;
	private double AVGLENRmin;
	
	private double MAXLENR;
	private double MINLENR;
	private double AVGLENR;
	
	S4(String index, S_23 paramObject, ArrayList<Double> PHeater4,GasLoss gl )
	{
		this.index=index;
		splitIndex();
		this.JacketLoss=paramObject.getMeanJacket();
		this.PulseParam=paramObject.getPulseParam();
		this.Temperature=paramObject.getTemperature();
		this.PulsePowerMin=paramObject.getMinPulse();
		this.PulsePowerMax=paramObject.getMaxPulse();;
		this.PulsePowerMean=paramObject.getMeanPulse();
		this.gLoss=gl;
		minLENR= new ArrayList<>();
		maxLENR= new ArrayList<>();
		avgLENR= new ArrayList<>();
		
		this.PHeater4=PHeater4;
		
		 MAXLENRavg=-1;
		 MINLENRavg=1000;
		 AVGLENRavg=0;
		
		 MAXLENRmax=-1;
		 MINLENRmax=1000;
		 AVGLENRmax=0;
		
	     MAXLENRmin=-1;
		 MINLENRmin=1000;
		 AVGLENRmin=0;
		
		 MAXLENR=-1;
		 MINLENR=1000;
		 AVGLENR=0;
		
		 calculateStep4();
	}
	public String toString()
	{
		String x="Index: "+index+"\n"+" Temperature: "+this.Temperature+"  Pulse Param: "+this.PulseParam +"\n"+" Avg LENR: "+this.AVGLENRavg+"\n"+" MAX LENR: "
	   +this.MAXLENR+"\n"+" MIN LENR: "+this.MINLENR;
		return x;
	}
	public double getPulseParam()
	{
		return this.PulseParam;
	}
	public double getPulsePowerMean()
	{
		DecimalFormat df = new DecimalFormat("#.00");
		Double pulseMean=this.PulsePowerMean;
		String jacketValue= df.format(pulseMean);
		pulseMean=Double.parseDouble(jacketValue);
		return pulseMean;
	}
	public double getPulsePowerMAX()
	{
		DecimalFormat df = new DecimalFormat("#.00");
		Double pulseMAX=this.PulsePowerMax;
		String jacketValue= df.format(pulseMAX);
		pulseMAX=Double.parseDouble(jacketValue);
		return pulseMAX;
	}
	public double getPulsePowerMIN()
	{
		DecimalFormat df = new DecimalFormat("#.00");
		Double pulseMin=this.PulsePowerMin;
		String jacketValue= df.format(pulseMin);
		pulseMin=Double.parseDouble(jacketValue);
		return pulseMin;
	}
	public double getJacket()
	{
		DecimalFormat df = new DecimalFormat("#.00");
		Double jacketL=this.JacketLoss;
		String jacketValue= df.format(jacketL);

		jacketL=Double.parseDouble(jacketValue);
		return jacketL;
	}
	public int getTemperature()
	{
		return this.Temperature;
	}
	public double PHeaterAvg()
	{
		double konstant=0;
		DecimalFormat df = new DecimalFormat("#.00");

		for(int j=0;j<PHeater4.size();j++)
		{
			konstant+=PHeater4.get(j);
		}
		konstant=konstant/PHeater4.size();
		String gValue= df.format(konstant);
		konstant=Double.parseDouble(gValue);
		return konstant;
	}
	
	public double GLossAVG()
	{
		DecimalFormat df = new DecimalFormat("#.00");

		double gLossAmount=0;
		for(int i=0;i<gLoss.size();i++)
		{
			gLossAmount+=gLoss.get(i);
		}
		gLossAmount=gLossAmount/gLoss.size();
		String gValue= df.format(gLossAmount);
		gLossAmount=Double.parseDouble(gValue);
		return gLossAmount;
	}
	private void calculateStep4()
	{
		double tempLENRmin;
		double tempLENRmax;
		double tempLENRavg;
		for(int i=0;i<PHeater4.size();i++)
		{
			tempLENRavg=this.JacketLoss-PHeater4.get(i)-PulsePowerMean+gLoss.get(i);
			tempLENRmax=this.JacketLoss-PHeater4.get(i)-PulsePowerMin+gLoss.get(i);
			tempLENRmin=this.JacketLoss-PHeater4.get(i)-PulsePowerMax+gLoss.get(i);
			
			if(tempLENRavg<MINLENR)
			{
				MINLENR=tempLENRavg;
			}
			if(tempLENRmax<MINLENR)
			{
				MINLENR=tempLENRmax;
			}
			if(tempLENRmin<MINLENR)
			{
				MINLENR=tempLENRmin;
			}
			
			
			
			minLENR.add(tempLENRmin);
			maxLENR.add(tempLENRmax);
			avgLENR.add(tempLENRavg);
			
			if(tempLENRavg>MAXLENRavg)
			{
				MAXLENRavg=tempLENRavg;
			}
			if(tempLENRavg<MINLENRavg)
			{
				MINLENRavg=tempLENRavg;
			}
			AVGLENRavg+=tempLENRavg;
			
			
			
			
			
			if(tempLENRmax>MAXLENRmax)
			{
				MAXLENRmax=tempLENRmax;
			}
			if(tempLENRmax<MINLENRmax)
			{
				MINLENRmax=tempLENRmax;
			}
			AVGLENRmax+=tempLENRmax;
			
			
			

			if(tempLENRmin>MAXLENRmin)
			{
				MAXLENRmin=tempLENRmax;
			}
			if(tempLENRmin<MINLENRmin)
			{
				MINLENRmin=tempLENRmax;
			}
			AVGLENRmin+=tempLENRmin;
		}
		
		AVGLENRavg=AVGLENRavg/PHeater4.size();
		AVGLENRmax=AVGLENRmax/PHeater4.size();
		AVGLENRmin=AVGLENRmin/PHeater4.size();
		
		
		if(MAXLENRavg>MAXLENRmax && MAXLENRavg>MAXLENRmin)
		{
			MAXLENR=MAXLENRavg;
		}
		
		if(MAXLENRavg<MAXLENRmax && MAXLENRmax>MAXLENRmin)
		{
			MAXLENR=MAXLENRmax;
		}
		
		if(MAXLENRavg<MAXLENRmin && MAXLENRmax<MAXLENRmin)
		{
			MAXLENR=MAXLENRmax;
		}
		
		
		
		
		

		
		AVGLENR=(AVGLENRavg+AVGLENRmin+AVGLENRmax)/3;
		
	}
	
	
	private void splitIndex()
	{
		String holder[];
		holder=index.split("---");
        this.beginIndex=(Integer.parseInt(holder[0]));
        this.endIndex=Math.abs((Integer.parseInt(holder[1])));
	}

	
	public ArrayList<Double> getMinLENRList()
	{
		return this.minLENR;
	}
	public ArrayList<Double> getMaxLENRList()
	{
		return this.maxLENR;
	}
	public ArrayList<Double> getAvgLENRList()
	{
		return this.avgLENR;
	}
	
	
	public double getMAXLENRavg()
	{
		return this.MAXLENRavg;
	}
	
	public double getMINLENRavg()
	{
		return this.MINLENRavg;
	}
	public double getAVGLENRavg()
	{
		return this.AVGLENRavg;
	}
	
	public int getBeginIndex()
	{
		return this.beginIndex;
	}
	public int getEndIndex()
	{
		return this.endIndex;
	}
	public double getMAXLENRmax()
	{
		return this.MAXLENRmax;
	}
	public double getMINLENRmax()
	{
		return this.MINLENRmax;
	}
	public double getAVGLENRmax()
	{
		return this.AVGLENRmax;
	}
	
	public double getMAXLENRmin(){
		return this.MAXLENRmin;
	}
	public double getMINLENRmin(){
		return this.MINLENRmin;
	}
	private double getAVGLENRmin()
	{
		return this.AVGLENRmin;
	};
	
	public double getMAXLENR(){
		return this.MAXLENR;
	};
	public double getMINLENR(){
		return this.MINLENR;
	};
	public double getAVGLENR(){
		return this.AVGLENR;
	};
}
