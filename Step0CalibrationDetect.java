import java.io.File;
import java.util.*;
public class Step0CalibrationDetect 
{
	private LinkedList<s0> helium0;
	private LinkedList<s0> hydrogen0;
	
	
	private LinkedList<s0> heliumTemp;
	private LinkedList<s0> hydrogenTemp;
	
	private static int monthsWith30Days[]={4,6,9,11};
	private static int monthsWith31Days[]={1,3,5,7,8,10,12};
	private int monthWith1Month=2;
	
	
	
	
	private ArrayList<String> time;
	private ArrayList<Double> CoreHeaterPow;
	private ArrayList<Double> QPow;
	private ArrayList<Double> CoreOutH2;
	private ArrayList<Double> CoreOutHE;
	private ArrayList<Double> CoreInPressure;
	private ArrayList<Double> CoreReactorTemperature;
	private ArrayList<Double> HeatVector;
	
	Step0CalibrationDetect(String dates) throws Exception
	{
		/* So for the future, I want to take a string of file dates, calibrate it and break it and cycle to each of the dates and pop out a 
		LinkedList along the each way for hydrogen
		 */
		helium0= new LinkedList<>();
		hydrogen0= new LinkedList<>();
		
		CoreHeaterPow= new ArrayList<>();
		 QPow= new ArrayList<>();
		 time= new ArrayList<>();
		 CoreOutH2= new ArrayList<>();
		 CoreOutHE= new ArrayList<>();
		 CoreInPressure= new ArrayList<>();
		 CoreReactorTemperature= new ArrayList<>();
		 HeatVector= new ArrayList<>();
		 
		 
		String desiredDates[];
		Step1Calculation tempCalc1= new Step1Calculation();
		Step2and3Calc tempCalc23;
		String firstMonthDate[];
		String endMonthDate[];
		int monthDiff;
		int numOfDays=0;
		int daysIterated=0;
		desiredDates=dates.split("___");
		firstMonthDate=desiredDates[0].split("-");
		endMonthDate=desiredDates[1].split("-");
		monthDiff=Integer.parseInt(endMonthDate[0])-Integer.parseInt(firstMonthDate[0])+1;
		//System.out.println(" The Month Diff: "+monthDiff);
		String datefile;
		LinkedList<s0> tempLL;

		if(monthDiff==1)
		{
			int dayDiff=Integer.parseInt(endMonthDate[1])-Integer.parseInt(firstMonthDate[1]);
			for(int j=Integer.parseInt(firstMonthDate[1]);j<Integer.parseInt(firstMonthDate[1])+dayDiff;j++)
			{
				datefile="data/"+firstMonthDate[0]+"-"+j+"-"+firstMonthDate[2]+".csv";	
				//System.out.println("*********------------*********-----------********");
				//System.out.println("Opening file "+datefile);
				//System.out.println("*********------------*********-----------********");
				tempLL=getStep0forDate(datefile);
					if(tempLL.isEmpty()==false)
					{
					splitLLintoHandHE(tempLL);
					// But you need to include it in your code to go months without detecting step1
					}
				 
				
			}
		}
		
		else
		{
		//	System.out.println("[[[[[[[[[[[[[ELSE]]]]]]]]]]");
			System.out.println("Begin Month: "+firstMonthDate[0]+ " End Month "+endMonthDate[0]);
		for(int i=Integer.parseInt(firstMonthDate[0]);i<Integer.parseInt(firstMonthDate[0])+monthDiff;i++) // This controls the month
		{
			//This determines the num of Days
			System.out.println("Month : "+i);
			for(int j=0;j< monthsWith30Days.length;j++){
				if(monthsWith30Days[j]==i){
					numOfDays=30;
				}
			}
			for( int k=0;k<monthsWith31Days.length;k++){
				if(monthsWith31Days[k]==i){
					numOfDays=31;
				}
			}
			if(i==2){
				numOfDays=28;
			}
			
			
			
			
			
		//	System.out.println("Num of Days: "+numOfDays);
			//We determine the num of Days per month
			
			
			
				// First Month
			if(i==Integer.parseInt(firstMonthDate[0]))
			{
			//	System.out.println("First Month");
				for(int p=Integer.parseInt(firstMonthDate[1]);p<numOfDays+1;p++)
				{
					// Basically you need to go through the file until you find step1. Keep cycling through dates till the first step 1 is found
					datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
				//	System.out.println("*********------------*********-----------********");
				//	System.out.println("Opening file "+datefile);
				//	System.out.println("*********------------*********-----------********");

					tempLL=getStep0forDate(datefile);
					if(tempLL.isEmpty()==false)
					{
					splitLLintoHandHE(tempLL);
					// But you need to include it in your code to go months without detecting step1
					}
				if(p==numOfDays)
				{
					//System.out.println("Break");
					//System.out.println("i: "+i);
					i++;
					break;
				}
				}
			}
			//End month
			
			if(i==Integer.parseInt(endMonthDate[0]))
			{
				//		System.out.println("End Month");

				for(int p=1;p<Integer.parseInt(endMonthDate[1])+1;p++)
				{
					datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
					
					//System.out.println("*********------------*********-----------********");
					//System.out.println("Opening file "+datefile);
					//System.out.println("*********------------*********-----------********");
					tempLL=getStep0forDate(datefile);
					if(tempLL.isEmpty()==false)
					{
					splitLLintoHandHE(tempLL);
					// But you need to include it in your code to go months without detecting step1
					}
					
					
						
				}
			}
			
				
				
			else
				//Months in between ie go through all the days
			{
				for(int p=1;p<numOfDays+1;p++)
				{
			
					datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
					//System.out.println("Opening file "+datefile);
					tempLL=getStep0forDate(datefile);
					if(tempLL.isEmpty()==false)
					{
						splitLLintoHandHE(tempLL);
						// But you need to include it in your code to go months without detecting step1
					}


				}
			}
		}
		
		
		
		
		}
		shrinkLL();
	}
	
	public Step0CalibrationDetect(String beginDate, String endDate) throws Exception
	{

		/* So for the future, I want to take a string of file dates, calibrate it and break it and cycle to each of the dates and pop out a 
		LinkedList along the each way for hydrogen
		 */
		helium0= new LinkedList<>();
		hydrogen0= new LinkedList<>();
		
		CoreHeaterPow= new ArrayList<>();
		 QPow= new ArrayList<>();
		 time= new ArrayList<>();
		 CoreOutH2= new ArrayList<>();
		 CoreOutHE= new ArrayList<>();
		 CoreInPressure= new ArrayList<>();
		 CoreReactorTemperature= new ArrayList<>();
		 HeatVector= new ArrayList<>();
		 
		 
		String desiredDates[]=  new String[2];
		Step1Calculation tempCalc1= new Step1Calculation();
		Step2and3Calc tempCalc23;
		String firstMonthDate[];
		String endMonthDate[];
		int monthDiff;
		int numOfDays=0;
		int daysIterated=0;
		desiredDates[0]=beginDate;
		desiredDates[1]=endDate;
		firstMonthDate=desiredDates[0].split("-");
		endMonthDate=desiredDates[1].split("-");
		monthDiff=Integer.parseInt(endMonthDate[0])-Integer.parseInt(firstMonthDate[0])+1;
		//System.out.println(" The Month Diff: "+monthDiff);
		String datefile;
		LinkedList<s0> tempLL;

		if(monthDiff==1)
		{
			int dayDiff=Integer.parseInt(endMonthDate[1])-Integer.parseInt(firstMonthDate[1]);
			for(int j=Integer.parseInt(firstMonthDate[1]);j<Integer.parseInt(firstMonthDate[1])+dayDiff;j++)
			{
				datefile="data/"+firstMonthDate[0]+"-"+j+"-"+firstMonthDate[2]+".csv";	
				//System.out.println("*********------------*********-----------********");
				//System.out.println("Opening file "+datefile);
				//System.out.println("*********------------*********-----------********");
				tempLL=getStep0forDate(datefile);
					if(tempLL.isEmpty()==false)
					{
					splitLLintoHandHE(tempLL);
					// But you need to include it in your code to go months without detecting step1
					}
				 
				
			}
		}
		
		else
		{
		//	System.out.println("[[[[[[[[[[[[[ELSE]]]]]]]]]]");
			System.out.println("Begin Month: "+firstMonthDate[0]+ " End Month "+endMonthDate[0]);
		for(int i=Integer.parseInt(firstMonthDate[0]);i<Integer.parseInt(firstMonthDate[0])+monthDiff;i++) // This controls the month
		{
			//This determines the num of Days
			System.out.println("Month : "+i);
			for(int j=0;j< monthsWith30Days.length;j++){
				if(monthsWith30Days[j]==i){
					numOfDays=30;
				}
			}
			for( int k=0;k<monthsWith31Days.length;k++){
				if(monthsWith31Days[k]==i){
					numOfDays=31;
				}
			}
			if(i==2){
				numOfDays=28;
			}
			
			
			
			
			
		//	System.out.println("Num of Days: "+numOfDays);
			//We determine the num of Days per month
			
			
			
				// First Month
			if(i==Integer.parseInt(firstMonthDate[0]))
			{
			//	System.out.println("First Month");
				for(int p=Integer.parseInt(firstMonthDate[1]);p<numOfDays+1;p++)
				{
					// Basically you need to go through the file until you find step1. Keep cycling through dates till the first step 1 is found
					datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
				//	System.out.println("*********------------*********-----------********");
				//	System.out.println("Opening file "+datefile);
				//	System.out.println("*********------------*********-----------********");

					tempLL=getStep0forDate(datefile);
					if(tempLL.isEmpty()==false)
					{
					splitLLintoHandHE(tempLL);
					// But you need to include it in your code to go months without detecting step1
					}
				if(p==numOfDays)
				{
					//System.out.println("Break");
					//System.out.println("i: "+i);
					i++;
					break;
				}
				}
			}
			//End month
			
			if(i==Integer.parseInt(endMonthDate[0]))
			{
				//		System.out.println("End Month");

				for(int p=1;p<Integer.parseInt(endMonthDate[1])+1;p++)
				{
					datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
					
					//System.out.println("*********------------*********-----------********");
					//System.out.println("Opening file "+datefile);
					//System.out.println("*********------------*********-----------********");
					tempLL=getStep0forDate(datefile);
					if(tempLL.isEmpty()==false)
					{
					splitLLintoHandHE(tempLL);
					// But you need to include it in your code to go months without detecting step1
					}
					
					
						
				}
			}
			
				
				
			else
				//Months in between ie go through all the days
			{
				for(int p=1;p<numOfDays+1;p++)
				{
			
					datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
					//System.out.println("Opening file "+datefile);
					tempLL=getStep0forDate(datefile);
					if(tempLL.isEmpty()==false)
					{
						splitLLintoHandHE(tempLL);
						// But you need to include it in your code to go months without detecting step1
					}


				}
			}
		}
		
		
		
		
		}
		shrinkLL();
	}
	public void splitLLintoHandHE(LinkedList<s0> LLtoBeSplit)
	{
		s0 temp;
		for(int i=0;i<LLtoBeSplit.size();i++)
		{
		temp=LLtoBeSplit.get(i);
		if(temp.getHelium()==false)
		{
		//	System.out.println("Hydrogen");
			hydrogen0.add(temp);
		}
		else
		{
			helium0.add(temp);
		}
	
		}
	}
	public double getCalibrationConstant(double Temperature, boolean helium)
	{
		sortHeliumLLandHydrogenLL();
		double calibrationConstant=-1;
		double Temperature0=Math.round(Temperature);
		int TemperatureFlat=(int)Temperature0;
		s0 temp;
		if(helium==true)
		{
		
		//	sortHeliumLL();
			
			for(int i=0;i<helium0.size();i++)
			{
				if(TemperatureFlat==helium0.get(i).getTemperature())
				{
					calibrationConstant=helium0.get(i).getHeatStep0();
					return calibrationConstant;
				}
				
			}
			
		}
		else // it is hydrogen
		{
			for(int j=0;j<hydrogen0.size();j++)
			{
				if(TemperatureFlat==hydrogen0.get(j).getTemperature())
				{
					calibrationConstant=hydrogen0.get(j).getHeatStep0();
					return calibrationConstant;
				}
				
			}
			
		}
		
		if(calibrationConstant==1)
		{
			System.out.println("Temperature Not Mapped Correctly");
		}
		return calibrationConstant;
		
		
		
	}
	
	public double getNearestCalibrationConstant(double Temperature, boolean helium)
	{
		sortHeliumLLandHydrogenLL();
		double temporaryTemperature=Math.round(Temperature);
		int tempTemperature=(int)temporaryTemperature;
		double avgCalb=0;
		if(helium==true)
		{
			
			//sortHeliumLL();
			
			//System.out.println("Inside Temp: "+tempTemperature);
			if(tempTemperature<helium0.get(0).getTemperature())
			{
				return helium0.get(0).getHeatStep0();
			}
			if(tempTemperature>500.0)
			{
				return helium0.get(helium0.size()-1).getHeatStep0(); // Return the last element of the list
			}
			for( int i=0;i<helium0.size();i++)
			{
				//System.out.println("Comparing Temperature to "+helium0.get(i).getTemperature());
				if(helium0.size()==1)
				{
					return helium0.get(0).getHeatStep0();
				}
				if(tempTemperature==helium0.get(i).getTemperature())
				{
					avgCalb=helium0.get(i).getHeatStep0();
					return avgCalb;
				}
				if(tempTemperature>helium0.get(i).getTemperature() && tempTemperature<helium0.get(i+1).getTemperature() )
				{
					avgCalb=(helium0.get(i).getHeatStep0()+helium0.get(i+1).getHeatStep0())/2;
					return avgCalb;
				}
			}
		}
		else
		{
			if(tempTemperature<hydrogen0.get(0).getTemperature())
			{
				return hydrogen0.get(0).getHeatStep0();
			}
			if(tempTemperature>500.0)
			{
				return hydrogen0.get(hydrogen0.size()-1).getHeatStep0(); // Return the last element of the list
			}
			for( int i=0;i<hydrogen0.size();i++)
			{
				if(hydrogen0.size()==1)
				{
					return hydrogen0.get(0).getHeatStep0();
				}
				if(tempTemperature==hydrogen0.get(i).getTemperature())
				{
					avgCalb=hydrogen0.get(i).getHeatStep0()+hydrogen0.get(i+1).getHeatStep0();
					return avgCalb;
				}
				if(tempTemperature>hydrogen0.get(i).getTemperature() && tempTemperature<hydrogen0.get(i+1).getTemperature() )
				{
					avgCalb=hydrogen0.get(i).getHeatStep0()+hydrogen0.get(i+1).getHeatStep0();
					avgCalb=avgCalb/2;
					return avgCalb;
				}
			}
			
		}
		if(avgCalb==0)
		{
		System.out.println("ERROR!");
		}
		return avgCalb;
		
		
		
		
		
	}

	public LinkedList<s0> getStep0forDate(String date) throws Exception
	{
		LinkedList<s0> dateHeatPow= new LinkedList<>();
		
		String fileDate=date;
		
		// Here is when the date 
		File file= new File(fileDate);
		Scanner scan= new Scanner(file);
		
		String garbage= scan.nextLine();
		String holder;
		String holdBreak[];
		//Initialize the LL
		CoreHeaterPow.clear();
		QPow.clear();
		time.clear();
		CoreOutH2.clear();
		CoreOutHE.clear();
		CoreInPressure.clear();
		CoreReactorTemperature.clear();
		HeatVector.clear();
		
		 
		//Here are the temp variables to act as holders
		double CoreHeatPowTemp=0;
		int size=0;
		double QPowTemp=0;
		double CoreOutH2Temp=0;
		double CoreOutHeTemp=0;
		double CoreInPressureTemp=0;
		double CoreReactorTemp=0;
		String timeTemp= new String();
		int arrayParse[]={1,2,24,36,37,59,77};
		int loc;
		double hold;
		while(scan.hasNextLine())
		{
			holder=scan.nextLine();
			holdBreak=holder.split(",");
			//Here we initialize the temp variables
			for(int i=0;i<arrayParse.length;i++)
			{
				loc=arrayParse[i];
				loc=loc-1;
				switch(loc)
				{
				case 0:
					timeTemp=holdBreak[loc];
					break;
				case 1:
					hold=Double.parseDouble(holdBreak[loc]);
					CoreReactorTemp=hold;
					break;
				case 23:
					hold=Double.parseDouble(holdBreak[loc]);
					CoreInPressureTemp=hold;
					//System.out.println(hold);
					break;
				case 35:
					hold=Double.parseDouble(holdBreak[loc]);
					CoreOutH2Temp=hold;
					break;
				case 36:
					hold=Double.parseDouble(holdBreak[loc]);
					CoreOutHeTemp=hold;
					break;
				case 58:
					hold=Double.parseDouble(holdBreak[loc]);
				//	System.out.println(hold);
					CoreHeatPowTemp=hold;
					break;
				case 76:
					hold=Double.parseDouble(holdBreak[loc]);
					QPowTemp=hold;
					break;
				}
				
			}
			size++;
			// Push the temp variables to ArrayList
			CoreHeaterPow.add(CoreHeatPowTemp);
			QPow.add(QPowTemp);
			CoreOutH2.add(CoreOutH2Temp);
			CoreOutHE.add(CoreOutHeTemp);
			CoreInPressure.add(CoreInPressureTemp);
			CoreReactorTemperature.add(CoreReactorTemp);
			time.add(timeTemp);
		}
		//Now everything is loaded
		
		// The necessity is that it must be in the same between 0.1 for 1 hr
		
		
		// Goal of here is to determine when q POW IS LESS than 0.5
		
		int beginIndex=0;
		int endIndex=0;
		double heatTemp=0;
		boolean begin=false;
		//System.out.println("Size :"+ size);
		for(int i=0;i<QPow.size();i++)
		{
			//System.out.println(QPow.get(i));
			
			if(begin==false && QPow.get(i)<0.1)
			{
				begin=true;
				beginIndex=i;
				
			}
			
			if(begin==true && QPow.get(i)>1 || begin==true && i==QPow.size()-1)
			{
				
				endIndex=i-1;
			/*	System.out.println(" Q POW OFF Interval Index:" + beginIndex+"---"+endIndex);
				System.out.println(" Begin Time: "+time.get(beginIndex)+"  End Time: "+time.get(endIndex));
				System.out.println("\t"+ "Begin Q Pow: "+ QPow.get(beginIndex));
				System.out.println("\t"+" End Q Pow: "+ QPow.get(endIndex));
				System.out.println("\t "+ "New Q Pow: "+ QPow.get(i));
				System.out.println("\t"+ " Core Heater Pow mid: "+CoreHeaterPow.get((beginIndex+endIndex)/2));
				System.out.println();
				*/
				if(endIndex-beginIndex>1080)
				{
					for(int j=beginIndex+1440;j<endIndex;j++)
					{
						heatTemp=CoreHeaterPow.get(j);
						//System.out.println(heatTemp);
						HeatVector.add(heatTemp);
					}
					heatTemp=0;
					for(int k=0;k<HeatVector.size();k++)
					{
						heatTemp+=HeatVector.get(k);
					}
					heatTemp=heatTemp/HeatVector.size();
					if(Math.abs(CoreReactorTemperature.get(beginIndex+200)-CoreReactorTemperature.get(endIndex))<3)
					{
						//System.out.println("Heater Calibration");
						//System.out.println("\t"+" Avg Heat Pow: "+heatTemp);
						//Need to get the rounding 
						//System.out.println("\t"+ "Core Temp: "+Math.round(CoreReactorTemperature.get(beginIndex+200)));
					int Temperature= (int) Math.round(CoreReactorTemperature.get(beginIndex+200));
					boolean helium=false;
						if(CoreOutH2.get(beginIndex+200)<1 && CoreOutHE.get(beginIndex+200)<1 || Double.isNaN(heatTemp))
						{
							//System.out.println("This is argon just ignore");
						}
						else
						{
							if(CoreOutH2.get(beginIndex+200)>1)
							{
								helium=false;
							}
							else
							{
								helium=true;
							}
							s0 value= new s0(Temperature,heatTemp,helium);
							//System.out.println(value.toString());
							dateHeatPow.add(value);
					//	System.out.println("LinkedList is adding");
						}
					}
				}
				begin=false;
			}
			
			
		}
		
		
		
		return dateHeatPow;
		
		
		
	}
	
	public void shrinkLL()
	{
		/*
		 * 
		 * So basically, you need a method to detect the same numbers, take an average of the heater and shrink the LL
		 * 
		 */
		// This is the holder variable
		// For now, lets just take care of the helium LL
	
	
	//	System.out.println("The initial size of the Helium of the LL: "+helium0.size());
	//	System.out.println("********Initial*******");
	//	printHeliumLL();
		s0 tempHelium;
		double tempHelium0;
		double HeliumSize=0;
		double tempHeliumCal;
		ArrayList<Integer> removeArrayHelium= new ArrayList<>();
		for(int w=0;w<helium0.size();w++)
		{
			HeliumSize=0;
			tempHelium=helium0.get(w);
			tempHeliumCal=tempHelium.getHeatStep0();
			// THis is the inner loop
			for( int p=w+1;p<helium0.size();p++)
			{
				if(helium0.get(p).getTemperature()==tempHelium.getTemperature())
				{
					//This is a repeated element
					tempHeliumCal+=helium0.get(p).getHeatStep0();
					//System.out.println("Removing "+(p+1));
					removeArrayHelium.add(p);
					HeliumSize++;	
				}
			}
		//	hydrogen0.get(w).setCalibrationConstant(tempHydrogenCal/HydrogenSize);
			for( int o=0;o< removeArrayHelium.size();o++)
			{
				helium0.remove(removeArrayHelium.get(o)-o);
			}
			removeArrayHelium= new ArrayList<>();
			/*if( hydrogen0.get(w).getTemperature()==hydrogen0.get(w+1).getTemperature())
			{
				tempHydrogen=hydrogen0.get(0);
				tempHydrogen.setCalibrationConstant((hydrogen0.get(w).getHeatStep0() +hydrogen0.get(w+1).getHeatStep0())/2);
				helium0.remove(w+1);
			}*/
			if(HeliumSize>0)
			{
			//System.out.println("Repeating Helium Size: "+HeliumSize + " Calibration Constant" );
			helium0.get(w).setCalibrationConstant(tempHeliumCal/(HeliumSize+1));
		//	printHydrogenLL();
			}
		}
		
		
		
		
		
		//System.out.println("*******Afterwards Averaging******");
		System.out.println("****HELIUM***");
		printHeliumLL();
		//System.out.println("***********************");
		
		//System.out.println("The initial size of the Hydrogen of the LL: "+hydrogen0.size());
	//	System.out.println("********Initial*******");
		//printHydrogenLL();
		s0 tempHydrogen;
		double tempHydrogen0;
		double HydrogenSize=0;
		double tempHydrogenCal;
		ArrayList<Integer> removeArray= new ArrayList<>();
		for(int w=0;w<hydrogen0.size();w++)
		{
			HydrogenSize=0;
			tempHydrogen=hydrogen0.get(w);
			tempHydrogenCal=tempHydrogen.getHeatStep0();
			// THis is the inner loop
			for( int p=w+1;p<hydrogen0.size();p++)
			{
				if(hydrogen0.get(p).getTemperature()==tempHydrogen.getTemperature())
				{
					//This is a repeated element
					tempHydrogenCal+=hydrogen0.get(p).getHeatStep0();
					//System.out.println("Removing "+(p+1));
					removeArray.add(p);
					HydrogenSize++;	
				}
			}
		//	hydrogen0.get(w).setCalibrationConstant(tempHydrogenCal/HydrogenSize);
			for( int o=0;o< removeArray.size();o++)
			{
				hydrogen0.remove(removeArray.get(o)-o);
			}
			removeArray= new ArrayList<>();
			/*if( hydrogen0.get(w).getTemperature()==hydrogen0.get(w+1).getTemperature())
			{
				tempHydrogen=hydrogen0.get(0);
				tempHydrogen.setCalibrationConstant((hydrogen0.get(w).getHeatStep0() +hydrogen0.get(w+1).getHeatStep0())/2);
				helium0.remove(w+1);
			}*/
			if(HydrogenSize>0)
			{
			//System.out.println("Repeating Hydrogen Size: "+HydrogenSize + " Calibration Constant" );
			hydrogen0.get(w).setCalibrationConstant(tempHydrogenCal/(HydrogenSize+1));
		//	printHydrogenLL();
			}
		}
		//System.out.println("*******Afterwards Averaging******");
		System.out.println("***HYDROGEN****");
		printHydrogenLL();
		
		
		
		
	}
	public void sortHeliumLLandHydrogenLL()
	{
	//	private LinkedList<s0> helium0;
	//	private LinkedList<s0> hydrogen0;
		System.out.println("Before: ");
		printHeliumLL();
		printHydrogenLL();
		qqSortLL(true);
		qqSortLL(false);	
		System.out.println("After: ");

		printHeliumLL();
		printHydrogenLL();
		
		
	}
	
	public void qqSortLL(boolean helium)
	{
		if(helium==true)
		{
		if(helium0.size()==0 || helium0==null)
		{
			System.out.println("Empty or Null.  No need for sorting");
			return; // No need for sorting
		}
		int length=helium0.size();
		qqSort(0,length-1,helium); //This is the interval in which you want to sort
		}
		else
		{
			if(hydrogen0.size()==0 || hydrogen0==null)
			{
				System.out.println("Empty or Null.  No need for sorting");
				return; // No need for sorting
			}
			int length=hydrogen0.size();
			qqSort(0,length-1,helium); //This is the interval in which you want to sort
		}
	}
	
	
	 public void swapPos(int position1, int position2, boolean helium)
		{
		 	if(helium)
		 	{
			s0 copy1= helium0.get(position1);
			s0 copy2= helium0.get(position2);
			helium0.set(position2, copy1);
			helium0.set(position1, copy2);
		 	}
		 	else{
		 		s0 copy1= hydrogen0.get(position1);
				s0 copy2= hydrogen0.get(position2);
				hydrogen0.set(position2, copy1);
				hydrogen0.set(position1, copy2);
		 	}

			// The objective of this function is to swap LL
		}
	private void qqSort(int lowerIndex, int higherIndex,boolean helium)
	{
		if(helium==true)
		{
		int i=lowerIndex;
		int j=higherIndex;
		s0 pivot=helium0.get(lowerIndex+(higherIndex-lowerIndex)/2);  // This is your pivot LL.
		System.out.println(" Pivot: "+pivot.toString());
		while (i<=j)
		{
			while(helium0.get(i).getTemperature()<pivot.getTemperature())
			{
				i++;
			}
			while(helium0.get(j).getTemperature()>pivot.getTemperature())
			{
				j--;
			}
			if(i<=j)
			{
				swapPos(i,j,helium);
				i++;
				j--;
				
			}
		}
		if (lowerIndex < j)
		{
			qqSort(lowerIndex, j,helium);
		}
        if (i < higherIndex)
        {
        	qqSort(i, higherIndex,helium);
        }
		}
		else
		{
			int i=lowerIndex;
			int j=higherIndex;
			s0 pivot=hydrogen0.get(lowerIndex+(higherIndex-lowerIndex)/2);  // This is your pivot LL.
			System.out.println(" Pivot: "+pivot.toString());

			while (i<=j)
			{
				while(hydrogen0.get(i).getTemperature()<pivot.getTemperature())
				{
					i++;
				}
				while(hydrogen0.get(j).getTemperature()>pivot.getTemperature())
				{
					j--;
				}
				if(i<=j)
				{
					swapPos(i,j,helium);
					i++;
					j--;
					
				}
			}
			if (lowerIndex < j)
			{
				qqSort(lowerIndex, j,helium);
			}
	        if (i < higherIndex)
	        {
	        	qqSort(i, higherIndex,helium);
	        }
		}
	}
	
	
	
	public void printHeliumLL()
	{
		
		System.out.println("------HELIUM LL-------");
		for(int i=0;i<helium0.size();i++)
		{
			System.out.println(helium0.get(i).toString());
		}
	}
	public void printHydrogenLL()
	{
		s0 temp;
		System.out.println("------Hydrogen  LL------");
		for(int i=0;i<hydrogen0.size();i++)
		{
			System.out.println(hydrogen0.get(i).toString());
		}
	}

	



}
