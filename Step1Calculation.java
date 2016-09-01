import java.util.*;

public class Step1Calculation 
{
	public ArrayList<Double> Step1Calc;
	private String index;
	private String date;
	private double mean;
	private double SD;
	private double getMax;
	private int MaxIndex;
	private boolean isEmpty;
	private int beginIndex;
	private int endIndex;
	
	public boolean testEmpty()
	{
		if(Step1Calc.size()==0)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	Step1Calculation()
	{
		Step1Calc= new ArrayList<>();
	}
	Step1Calculation(String date)
	{
		Step1Calc= new ArrayList<>();
		this.date=date;
	}
	
	Step1Calculation(String index, String date)
	{
		//System.out.println(" Core Short Inside Constructor #2");
		//System.out.println("Date Short: "+date);
		String x= new String();
		Step1Calc= new ArrayList<>();
		this.index=index;
		this.date= date;
		setIndex();
		//System.out.println("Date "+ this.date);
	}
	
	public Object clone()
	{
		Step1Calculation clone= new Step1Calculation(this.index,this.date);
		clone.Step1Calc=(ArrayList<Double>)this.Step1Calc.clone();
		clone.mean=this.mean;
		clone.SD=this.SD;
		clone.getMax=this.getMax;
		clone.MaxIndex=this.MaxIndex;
		clone.isEmpty=this.isEmpty;
		clone.beginIndex=this.beginIndex;
		clone.endIndex=this.endIndex;
		return clone;
		
	}
	public String getDate(){
		return this.date;
	}
	public ArrayList<Double> getStep1Calc()
	{
		ArrayList<Double> clone=( ArrayList<Double>)Step1Calc.clone();
		return clone;
	}
	public void PiMinusLCool(double PiPower, double LCoolant )
	{
		double holder=PiPower-LCoolant;
		Step1Calc.add(holder);
				
	}
	
	public void addStep1Calc(double Step1Calc)
	{
		this.Step1Calc.add(Step1Calc);
	}
	
	public void CalculateMean()
	{
		double sum=0;
		for(int i=1; i<Step1Calc.size();i++)
		{
			sum+=Step1Calc.get(i);
		}
		mean=sum/Step1Calc.size();
		
	}
	public void setMean( double mean)
	{
		this.mean=mean;
	}
	public double getMean()
	{
		return mean;
	}
	public void CalculateSD()
	{
		
	}
	public double getMax()
	{
		double temp=0;
		for(int i=0;i< Step1Calc.size();i++)
		{
			if(temp<Step1Calc.get(i))
			{
				temp=Step1Calc.get(i);
				MaxIndex=i;
			}
		}
		
		getMax=temp;
		return this.getMax;
		
	}
	public int getMaxIndex()
	{
		return this.MaxIndex;
	}
	public String getIndex()
	{
		return this.index;
	}
	
	public void setIndex()
	{
		String holder[];
		holder=index.split("---");
        this.beginIndex=(Integer.parseInt(holder[0]));
        this.endIndex=Math.abs((Integer.parseInt(holder[1])));
  //      System.out.println("Begin Index :"+beginIndex);
    //    System.out.println("End Index  :"+endIndex);
   	}
	public int getBegin()
	{
		return this.beginIndex;
	}
	public int getEnd()
	{
		return this.endIndex;
	}
	public int getDay()
	{
		//System.out.println("Getting Day");
		//System.out.println("Date: "+date);
		String x[]=date.split("/");
		int day= Integer.parseInt(x[1]);
		//System.out.println("Day :"+day);
		
		return day;
	}

	

}
