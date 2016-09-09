import java.util.*;
public class Step4Calculation 
{
	private String date;
	private ArrayList<S4> Step4Table;
	
	public ArrayList<S4> getStep4Table()
	{
		return this.Step4Table;
	}
	
	Step4Calculation(String date)
	{
		this.date=date;
		Step4Table= new ArrayList<>();
	}
	public int getSize()
	{
		return Step4Table.size();
	}
	public S4 get(int i)
	{
		return Step4Table.get(i);
	}
	public void addS4( S4 objectToBeAdded)
	{
		Step4Table.add(objectToBeAdded);
	}
	
	public void printStep4Table()
	{
		for(int i=0;i<Step4Table.size();i++)
		{
			System.out.println(Step4Table.get(i).toString());
		}
	}
	public boolean checkIndices(int index)
	{
		for(int i=0;i<Step4Table.size();i++)
		{
			if(index>=Step4Table.get(i).getBeginIndex() && index<Step4Table.get(i).getEndIndex())
			{
				return true;
			}
		}
		return false;
	}
	public double getMaxLENRPowerAtIndex(int index)
	{
		int indexGet=0;
		ArrayList<Double> tempList;
		double valueDesired=0;
		for(int i=0;i<Step4Table.size();i++)
		{
			if(index>=Step4Table.get(i).getBeginIndex() && index<Step4Table.get(i).getEndIndex())
			{
				indexGet=index-Step4Table.get(i).getBeginIndex();
				tempList=Step4Table.get(i).getMaxLENRList();
				valueDesired=tempList.get(indexGet);

				//System.out.println(index+ " & Begin Index "+Step4Table.get(i).getBeginIndex()+" End Index: "+Step4Table.get(i).getEndIndex() +" Index Get: "+indexGet+" Value: "+valueDesired);
				return valueDesired;
			}
		}
		System.out.println("There is an error.  Returning -1");
		return -1;
		
	}
	public double getMinLENRPowerAtIndex(int index)
	{
		int indexGet=0;
		ArrayList<Double> tempList;
		double valueDesired=0;
		for(int i=0;i<Step4Table.size();i++)
		{
			if(index>=Step4Table.get(i).getBeginIndex() && index<Step4Table.get(i).getEndIndex())
			{
				indexGet=index-Step4Table.get(i).getBeginIndex();
				tempList=Step4Table.get(i).getMinLENRList();
				valueDesired=tempList.get(indexGet);
				//System.out.println(index+ " & Begin Index "+Step4Table.get(i).getBeginIndex()+" End Index: "+Step4Table.get(i).getEndIndex() +" Index Get: "+indexGet+" Value: "+valueDesired);

				
				return valueDesired;
			}
		}
		System.out.println("There is an error.  Returning -1");
		return -1;
		
	}
	public double getAvgLENRPowerAtIndex(int index)
	{
		int indexGet=0;
		ArrayList<Double> tempList;
		double valueDesired=0;
		for(int i=0;i<Step4Table.size();i++)
		{
			if(index>=Step4Table.get(i).getBeginIndex() && index<Step4Table.get(i).getEndIndex())
			{
				indexGet=index-Step4Table.get(i).getBeginIndex();
				tempList=Step4Table.get(i).getAvgLENRList();
				valueDesired=tempList.get(indexGet);
				//System.out.println(index+ " & Begin Index "+Step4Table.get(i).getBeginIndex()+" End Index: "+Step4Table.get(i).getEndIndex() +" Index Get: "+indexGet+" Value: "+valueDesired);
			
				return valueDesired;
			}
		}
		System.out.println("There is an error.  Returning -1");
		return -1;
	
		
	}
	public double getAVGLENRavgPowerAtIndex(int index)
	{
		int indexGet=0;
		ArrayList<Double> tempList;
		double valueDesired=0;
		S4 temp= Step4Table.get(index);
		valueDesired=temp.getAVGLENR();
		
		return valueDesired;
	}
	
	
}
