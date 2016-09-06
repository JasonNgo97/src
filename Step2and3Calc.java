import java.util.*;
public class Step2and3Calc 
{
	private double LOther;
	private ArrayList<LinkedList<S_23>> Step23Table;
	//Each Arraylist has a linked List of trials who temperature and pulse parameters are the same
	Step2and3Calc(double LOther)
	{
		this.LOther=LOther;
		Step23Table= new ArrayList<>();
	}

	Step2and3Calc(double LOther, ArrayList<LinkedList<S_23>> Step23Table)
	{
		this.LOther=LOther;
		this.Step23Table=Step23Table;
	}
	/*    Here is how I want the data structure to look like 
	 * 						ArrayList
	 * 						|| 	||
	 * 					|||||||||||||||||		|||||||||||||||||			||||||||||||||||||	
	 * 					|Pulse: 150	   |=======>|Pulse: 150		|=========>	|  Pulse: 150	 |
	 * 	LinkedList====	|Mean :		   |		|Mean:			|			|  Mean:		 |
	 * 					Index:	  	   |=======>|Index:  		|=========> |  Index:		 |
	 * 					|||||||||||||||||		|||||||||||||||||			||||||||||||||||||
	 * 						||   ||
	 *					 |||||||||||||||||		  |||||||||||||||||			 |||||||||||||||||	
	 *					 |Pulse: 200	 |=======>|Pulse: 200	  |=========>|  Pulse: 200	 |
	 *					 |Mean :		 |		  |Mean:		  |			 |  Mean:		 |
	 * 					 |Index:	  	 |=======>|Index:  		  |=========>|  Index:		 |
	 * 					 |||||||||||||||||		  |||||||||||||||||			 |||||||||||||||||
	 *  					||   ||
	 * 					|||||||||||||||||		|||||||||||||||||			||||||||||||||||||	
	 *				    |Pulse: 250	   |=======>|Pulse: 250		|=========>	|  Pulse: 250	 |
	 * 					|Mean :		   |		|Mean:			|			|  Mean:		 |
	 *					| Index:	   |=======>|Index:  		|=========> |  Index:		 |
	 * 					|||||||||||||||||		|||||||||||||||||			||||||||||||||||||
	 *
	 *
	 *
	 *				In this sort of hash table, LOther is the same
	 *
	 */
	public void addTable(ArrayList<LinkedList<S_23>> Step23Table)
	{
		this.Step23Table=Step23Table;
	}
	
	public Object clone()
	{
		Step2and3Calc clone= new Step2and3Calc(LOther);
		clone.Step23Table=(ArrayList<LinkedList<S_23>>)Step23Table.clone();
		return clone;
	}
	
	public void addArray(String index, ArrayList<Double> QPow, ArrayList<Double> H2MakeUpLPM, ArrayList<Double> tempDiff,
			ArrayList<Double> LCoolant2, double pulseParam, ArrayList<Double> H2, ArrayList<Double> HE, ArrayList<Double> PowOut, ArrayList<Double> HeaterPow,
			ArrayList<Double> JacketTemp, ArrayList<Double> JacketPressure, ArrayList<Double> CoreTempDiff, ArrayList<Double> CoreTemp, ArrayList<Double> CorePressure,GasProp hydrogen, GasProp helium, int CoreReactorTemperature)
	{
		//So basically what I want this to do is to scan the arrayList to see if there are any matching parameters
		
		// Core Gas Helium and Hydrogen Mapped Density
		// So you don't really need to organize the arraylist in order
		boolean found=false;
		int meanJacketTemp=0;
		int meanJacketPressure=0;
		int meanCoreTemp=0;
		int meanCorePressure=0;
		double mappedDensity=0;
		double mappedSpecificHeat=0;
		for(int i=0;i<JacketTemp.size();i++)
		{
			meanJacketTemp+=JacketTemp.get(i);
			//System.out.println(JacketTemp.get(i));
		}
		//System.out.println("JacketTemp before dividing by: "+JacketPressure.size()+" : "+ meanJacketTemp);
		meanJacketTemp=(int)meanJacketTemp/(JacketTemp.size());
		
		for(int i=0;i<JacketPressure.size();i++)
		{
			meanJacketPressure+=JacketPressure.get(i);
		//	System.out.println(JacketPressure.get(i));
		}
		//System.out.println("Jacket Pressure before dividing by "+JacketPressure.size()+ ": "+meanJacketPressure);
		meanJacketPressure=(int)meanJacketPressure/(JacketPressure.size());
		
		for(int i=0;i<CoreTemp.size();i++)
		{
			meanCoreTemp+=CoreTemp.get(i);
		}
		meanCoreTemp=(int)meanCoreTemp/(CoreTemp.size());
		for(int i=0;i<CorePressure.size();i++)
		{
			meanCorePressure+=CorePressure.get(i);
		}
		meanCorePressure=(int)(meanCorePressure/(CorePressure.size()));
		
		
		
		
		
		//Map the heat capacity and density through pressure and temperature mapping
		// Initialize the gas
		boolean heliumTest=false;
		for( int i=1;i<10;i++)
		{
			if(H2.get(i)>HE.get(i))
			{
				heliumTest=false;
			}
			else
			{
				heliumTest=true;
			
			}
		}
		//System.out.println("H2MakeUpLPM");
		//for(int i=0;i<H2MakeUpLPM.size();i++)
		//{
		//	System.out.println(H2MakeUpLPM.get(i));
		//}
		
		//System.out.println(" Jacket Temp :"+meanJacketTemp);
		//System.out.println(" Core Pressure: "+meanCorePressure);
		//System.out.println(" Core Temp: "+meanCoreTemp);
		if(heliumTest==false)
		{
			//System.out.println(" Hydrogen Gas");
			mappedDensity=hydrogen.getDensity(meanCoreTemp, meanCorePressure);
			mappedSpecificHeat=hydrogen.getHeatCapacity(meanCoreTemp, meanCorePressure);
			
		}
		else
		{
			//System.out.println( "Helium Gas");
			mappedDensity=helium.getDensity(meanCoreTemp, meanCorePressure);
			mappedSpecificHeat=helium.getHeatCapacity(meanCoreTemp, meanCorePressure);
		}
		// Convert the LPM
		//System.out.println("Got here.");
		ArrayList<Double> convertedLPM= new ArrayList<>();
		double LPMholder=0;
		//System.out.println("H2MakeUpLPM");
		//for(int i=0;i<H2MakeUpLPM.size();i++)
		//{
		//	System.out.println(H2MakeUpLPM.get(i));
		//}

		for(int i=0;i<H2MakeUpLPM.size();i++)
		{
			//System.out.println(H2MakeUpLPM.get(i));
		LPMholder=(H2MakeUpLPM.get(i)/(60*1000))*mappedDensity;
		//System.out.println("H2MakeUpLPM: "+ H2MakeUpLPM.get(i)+"  LPM Holder: "+LPMholder);
		//System.out.println(" Core Pressure: "+meanCorePressure +" Core Temp: "+meanCoreTemp);
		//System.out.println("H2MakeUp LPM : "+H2MakeUpLPM.get(i)+"   Converted LPM: "+LPMholder  +"  Mapped Density: "+mappedDensity);
		convertedLPM.add(LPMholder);
		}

		GasLoss gasL= new GasLoss(PowOut, LCoolant2,mappedSpecificHeat, convertedLPM,CoreTempDiff, heliumTest, index);
		
		//	S_23(String index, double LOther, ArrayList<Double> QPow, ArrayList<Double> LCoolant, double pulseParam, 
		//GasLoss gLoss, ArrayList<Double> HeaterPow, ArrayList<Double> tempDiff, ArrayList<Double> LPM)

		//	S_23(String index, double LOther, ArrayList<Double> QPow, ArrayList<Double> LCoolant, double pulseParam,
		//GasLoss gLoss, ArrayList<Double> HeaterPow, ArrayList<Double> tempDiff, ArrayList<Double> LPM)

		if(Step23Table.size()==0)
		{
			//This means that there are no arrays in the data structure
			//Here you create the first LinkedList
			LinkedList<S_23> holder= new LinkedList<>();   
			//Now you create the first element of the LL
			S_23 first= new S_23(index,this.LOther,QPow,LCoolant2,pulseParam,gasL,HeaterPow,CoreTempDiff,H2MakeUpLPM,heliumTest,CoreReactorTemperature);
			//Now you add this element to the LL
			if(first.getPulseParam()>10)
			{
			holder.add(first);
			Step23Table.add(holder);
			}
			return;			
		}
			
		// Search through the table to see if any of the LL has the same pulse parameters
		for(int i=0;i<Step23Table.size();i++)
		{
			//Here the idea is to scan the first element of the linkedlist
			if(Step23Table.get(i).get(0).getPulseParam()==pulseParam && Step23Table.get(i).get(0).getTemperature()==CoreReactorTemperature)
			{
				found=true; //Proper LL is found
				//Initialize the element
				S_23 elem= new S_23(index,this.LOther,QPow,LCoolant2,pulseParam,gasL,HeaterPow,tempDiff,H2MakeUpLPM,heliumTest,CoreReactorTemperature);
				if(elem.getPulseParam()>10)
				{
				//Add the element to the according LL
				Step23Table.get(i).add(elem);
				}
				return;				
			}
		}
		// The proper LL is not there
		if(found==false) 
		{
			//Initialize the LL
			LinkedList<S_23> holder= new LinkedList<>();
			S_23 first= new S_23(index,this.LOther,QPow,LCoolant2,pulseParam,gasL,HeaterPow,tempDiff,H2MakeUpLPM,heliumTest,CoreReactorTemperature);
			if(first.getPulseParam()>10)
			{
			holder.add(first);
			Step23Table.add(holder);
			}
			return;
			
		}
				
	}
	public void printTable23()
	{
		System.out.println("--------Step 2 Calculations---------");
		if(Step23Table.size()==0)
		{
			System.out.println("The table is null");
			return;
		}
		for(int i=0;i<Step23Table.size();i++)
			//This is pretty much the iteration through each of the LL in ArrayList
		{
			System.out.println(" The size is "+Step23Table.get(i).size());
			if(Step23Table.get(i).size()>0)
			{
			System.out.println(" LL with Pulse Param :"+ Step23Table.get(i).get(0).getPulseParam());
				for( int j=0;j< Step23Table.get(i).size();j++)
				//Basically this iterates through each element of the LL
			{
				System.out.println("\t"+" ("+j+") ");
				System.out.println(" Index: "+Step23Table.get(i).get(j).getIndex());
				System.out.println(" Mean Jacket : "+Step23Table.get(i).get(j).getMeanJacket());
				System.out.println(" Mean Pulse Power: "+Step23Table.get(i).get(j).getMeanPulse());
				System.out.println(" Gas: "+Step23Table.get(i).get(j).getGas());
				System.out.println(" Temperature: "+Step23Table.get(i).get(j).getTemperature());
				
			}
			}
			
		}
		System.out.println("------------------------------------");
	}
	public ArrayList<LinkedList<S_23>> getS23Table()
	{
		return this.Step23Table;
	}
	public double getLOther()
	{
		return this.LOther;
	}
	
	public LinkedList<S_23> getPulseParamTable(int pulseParameter)
	{
		LinkedList<S_23> temp;
		for(int i=0;i<Step23Table.size();i++)
		{
			temp=Step23Table.get(i);
			if((int)temp.get(0).getPulseParam()==pulseParameter)
			{
				return temp;
			}
			
		}
		return null;
	}
	/*public void addArray(String interval, ArrayList<Double> qPowHolder,
			ArrayList<Double> h2MakeUpLPMholder,
			ArrayList<Double> tempDiffholder, ArrayList<Double> lCoolantHolder,
			Double double1, ArrayList<Double> h2holder,
			ArrayList<Double> hEholder, ArrayList<Double> coreHeaterPowHolder,
			ArrayList<Double> jacketTempHolder, double jacketPressureTemp,
			GasProp hydrogen, GasProp helium) {
		// TODO Auto-generated method stub
		*/
	//}
	
	
	
	
}
