import java.util.*;
public class Step4Calculation 
{
	private String date;
	private ArrayList<S4> Step4Table;
	
	Step4Calculation(String date)
	{
		this.date=date;
		Step4Table= new ArrayList<>();
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
	
	
}
