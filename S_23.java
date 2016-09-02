import java.util.*;

public class S_23 
{
	private String index;
	private int beginIndex;
	private int endIndex;
	private double pulseParam;
	private double LOther;
	private ArrayList<Double> PulsePower;
	private ArrayList<Double> JacketLoss;
	private int Temperature;
	private double meanPulse;
	private double meanJacket;
	private boolean helium;
	
	S_23(String index, double LOther, ArrayList<Double> QPow, ArrayList<Double> LCoolant, double pulseParam, GasLoss gLoss, ArrayList<Double> HeaterPow, ArrayList<Double> tempDiff, ArrayList<Double> LPM, boolean helium, int Temperature)
	{
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
			//System.out.println(beginIndex+i+" QPow : "+QPow.get(i)+" LCoolant: "+LCoolant.get(i)+" LOther:" +this.LOther);
			tempHolder= HeaterPow.get(i)+ temp- gLoss.get(i);
			
			sum+=temp;
			sum2+=tempHolder;
			
			PulsePower.add(temp);
			JacketLoss.add(tempHolder);
		}
		meanPulse=sum/(QPow.size());
		meanJacket=sum2/(QPow.size());
		System.out.println("Mean Pulse: "+meanPulse);
		System.out.println("Mean Jacket: "+meanJacket);

		// Now I want to break up the String index into begin and end
		
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
	
}
