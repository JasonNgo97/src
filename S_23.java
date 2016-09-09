import java.util.*;

public class S_23 
{
	private String index;
	private int beginIndex;
	private int endIndex;
	private double pulseParam;
	
	private double minimumPulse;
	private double maxPulse;

	private double maximumJacket;
	private double minimumJacket;
	
	private double LOther;
	private ArrayList<Double> PulsePower;
	private ArrayList<Double> JacketLoss;
	private int Temperature;
	private double meanPulse;
	private double meanJacket;
	private double maxJacket;
	private boolean helium;
	
	private int numTimesCompressed=0;
	
	S_23(double minPulse, double maxPulse, double minJacket, double maxJacket, int temperature, double meanJacket, double meanPulse, double pulseParam, boolean helium)
	{
		System.out.println(" This is the avg constructor. ");
		this.maximumJacket=maxJacket;
		//this.maximumPulse=maxPulse;
		this.maxPulse=maxPulse;
		
		this.minimumPulse=minPulse;
		this.minimumJacket=minJacket;
		this.Temperature=temperature;
		this.meanJacket=meanJacket;
		this.meanPulse=meanPulse;
		this.pulseParam=pulseParam;
		this.helium=helium;
		
		this.LOther=-1;
		this.PulsePower=null;
		this.JacketLoss=null;
		this.index=null;
		this.beginIndex=-1;
		this.endIndex=-1;
		System.out.println(" Temperature :"+ this.Temperature +"  Pulse Param: "+pulseParam);
		//System.out.println(" Average Pulse Power: "+meanPulse+" Average Jacket Loss: "+ meanJacket);
		System.out.println(" Min Pulse: "+minimumPulse+ " MAX Pulse: "+maxPulse +" Mean Pulse "+ this.meanPulse);
		
	}
	
	
	
	
	S_23(String index, double LOther, ArrayList<Double> QPow, ArrayList<Double> LCoolant, double pulseParam, GasLoss gLoss, ArrayList<Double> HeaterPow, ArrayList<Double> tempDiff, ArrayList<Double> LPM, boolean helium, int Temperature)
	{
		maxPulse=0;
		double minPulse=10000;
		maxJacket=0;
		System.out.println("Index: "+index);
		String holder[];
		holder=index.split("---");
        this.beginIndex=(Integer.parseInt(holder[0]));
        this.endIndex=Math.abs((Integer.parseInt(holder[1])));	
		this.helium=helium;
		this.LOther=LOther;
		this.pulseParam=pulseParam;
		this.index=index;
		this.Temperature=Temperature;
		double sum=0;
		double sum2=0;
		double temp=0;
		double tempHolder=0;
		PulsePower= new ArrayList<>();
		JacketLoss= new ArrayList<>();
		//This is for error checking
		if(QPow.size()!=LCoolant.size())
		{
			System.out.println("There is a  error.  The QPow and Lcoolant Arrays are the same size");
		}
		
		
		//Here I want to take in the arrayList and initialize the step2Calc
		//System.out.println(beginIndex+" QPow : "+QPow.get(0)+" LCoolant: "+LCoolant.get(0)+" LOther:" +this.LOther);
		//System.out.println(endIndex+" QPow : "+QPow.get(QPow.size()-1)+" LCoolant: "+LCoolant.get(QPow.size()-1)+" LOther:" +this.LOther);

		for(int i=0;i<QPow.size();i++)
		{
			//Lets skip the first 50 for step2 calc and step 3
			temp=QPow.get(i)-LCoolant.get(i)-this.LOther;
			if(temp>maxPulse)
			{
				maxPulse=temp;
			}
			if(temp<minPulse)
			{
				minPulse=temp;
			}
			//System.out.println(beginIndex+i+" QPow : "+QPow.get(i)+" LCoolant: "+LCoolant.get(i)+" LOther:" +this.LOther);
			tempHolder= HeaterPow.get(i)+ temp- gLoss.get(i);
			if(tempHolder>maxJacket)
			{
				maxJacket=tempHolder;
			}
			sum+=temp;
			sum2+=tempHolder;
			
			PulsePower.add(temp);
			JacketLoss.add(tempHolder);
		}
		meanPulse=sum/(QPow.size());
		this.minimumPulse=minPulse;
		meanJacket=sum2/(QPow.size());
		System.out.println("Pulse Param: "+pulseParam);
		System.out.println("Mean Pulse: "+meanPulse);
		System.out.println("Mean Jacket: "+meanJacket);
		System.out.println("MAX Pulse: "+maxPulse);
		System.out.println("MAX Jacket: "+maxJacket);

		// Now I want to break up the String index into begin and end
		
	}
	public void increaseNumTimesCompressedby1()
	{
		numTimesCompressed++;
	}
	public void setNumTimesCompressed(int numTimes)
	{
		this.numTimesCompressed=numTimes;
	}
	public void addOnNumTimesCompressed( int numTimes)
	{
		this.numTimesCompressed=this.numTimesCompressed+numTimes;
	}
	public int getNumTimesCompressed()
	{
		return this.numTimesCompressed;
	}
	
	public double getPulseParam()
	{
		return this.pulseParam;
	}
	public String getIndex()
	{
		return this.index;
	}
	public int getBeginIndex()
	{
		return this.beginIndex;
	}
	public String getGas()
	{
		String gas= new String();
		if(helium==true)
		{
			gas=" HELIUM ";
			return gas;
		}
		else
		{ 
			gas="HYDROGEN ";
			return gas;
			
		}
		
	}
	public int getEndIndex()
	{
		return this.endIndex;
	}
	public int getTemperature()
	{
		return this.Temperature;
	}
	public double getMeanPulse()
	{
		return this.meanPulse;
	}
	public double getMeanJacket()
	{
		return this.meanJacket;
	}
	public double getMaxPulse()
	{
		return this.maxPulse;
	}
	public double getMinPulse()
	{
		return this.minimumPulse;
	}
	public double getMaxJacket()
	{
		return this.maximumJacket;
	}
	public double getMinJacket()
	{
		return this.minimumJacket;
	}
	
	public void setTemperature( int temperature)
	{
		this.Temperature=temperature;
	}
	public void setMeanPulse( double meanPulse)
	{
		this.meanPulse=meanPulse;
		
	}
	public void setMeanJacket(double meanJacket)
	{
		this.meanJacket=meanJacket;
	}
	public void setJacketMin(double minJacket)
	{
		this.minimumJacket=minJacket;
	}
	public void setJacketMax(double maxJacket)
	{
		this.maximumJacket=maxJacket;
		
	}
	public void setMinPulse(double minPulse)
	{
		this.minimumPulse=minPulse;
	}
	public void setMaxPulse(double maxPulse)
	{
		this.maxPulse=maxPulse;
	}
	
}
