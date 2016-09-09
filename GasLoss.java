import java.util.*;

public class GasLoss 
{
	private String index;
	private String name;
	private int beginIndex;
	private int endIndex;
	private GasProp gas;
	private ArrayList<Double> gLoss;
	
	public GasLoss(ArrayList<Double> PowOut, ArrayList<Double> ThermPow, Double HeatCapacityCP , ArrayList<Double> ConvertedLPM, ArrayList<Double> tempCoreDiff, boolean helium, String index)
	{
		 ArrayList<Double> ArgonLoss= new ArrayList<>();
		 gLoss= new ArrayList<>();
		 Double holder=0.0;
		 // Calculating GLoss
		 //Initialize and Load the Argon
		 for(int i=0;i<PowOut.size();i++)
		 {
			 holder=PowOut.get(i)-ThermPow.get(i);
			 //System.out.println("Argon Loss: "+holder);
			 ArgonLoss.add(holder);
		 }
		
			 
			 
			 
		 if( helium==false)
		 {
			 ArrayList<Double> HydrogenLoss= new ArrayList<>();
			 for(int i=0;i<tempCoreDiff.size();i++)
			 {
				 holder=HeatCapacityCP*ConvertedLPM.get(i)*tempCoreDiff.get(i);
				// System.out.println(" Heat Capacity: "+HeatCapacityCP+ " Converted LPM: "+ConvertedLPM.get(i)+ " Temp Diff: "+ tempCoreDiff);
				// System.out.println("Hydrogen Heat Loss: "+holder);
				 HydrogenLoss.add(holder);
			 }
			 for(int i=0;i<PowOut.size();i++)
			 {
				holder=ArgonLoss.get(i)+HydrogenLoss.get(i);
		//		System.out.println(" Gas Loss: "+holder);
				gLoss.add(holder);
			 }			 
			 
		 }
		 else
		 {
			 ArrayList<Double> HeliumLoss= new ArrayList<>();
			 for(int i=0;i<tempCoreDiff.size();i++)
			 {
				 
				// System.out.println("Temp Core Diff :"+tempCoreDiff.get(i));
				 holder=HeatCapacityCP*ConvertedLPM.get(i)*tempCoreDiff.get(i);
				 //System.out.println(" Heat Capacity: "+HeatCapacityCP+ " Converted LPM: "+ConvertedLPM.get(i)+ " Temp Diff: "+ tempCoreDiff.get(i));

				//  System.out.println("Helium Heat Loss: "+holder);

				 HeliumLoss.add(holder);
			 }
			 for(int i=0;i<PowOut.size();i++)
			 {
				holder=ArgonLoss.get(i)+HeliumLoss.get(i);
			//	System.out.println("ArgonLoss +"+ArgonLoss.get(i)+ " Helium Loss "+HeliumLoss.get(i));
			//	System.out.println("Gas Loss: "+holder);
				gLoss.add(holder);
			 }	
		 }
		// Now break the index
		 String holder2[];
		 holder2=index.split("---");
	     this.beginIndex=(Integer.parseInt(holder2[0]));
	     this.endIndex=Math.abs((Integer.parseInt(holder2[1])));	
		 
		 
		 
		 
	}
	public int size()
	{
		return gLoss.size();
	}
	public Double get(int i)
	{
		return gLoss.get(i);
	}
}
