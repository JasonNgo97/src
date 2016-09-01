import java.util.*;

public class LENR 
{
	private int startTimeStep1;
	private int endTimeStep1;
	
	private static int monthsWith30Days[]={4,6,9,11};
	private static int monthsWith31Days[]={1,3,5,7,8,10,12};
	private int monthWith1Month=2;
	
	private static Stack<Step1Calculation> Step1LongCalcStack;
	private static Stack<Step2and3Calc> Step2and3CalcStack;
	
	
	
	
	
	public static void main(String args[]) throws Exception
	{	
		
		String holder;;
		Scanner scan= new Scanner(System.in);
		Step2and3CalcStack= new Stack<>();
		Step1LongCalcStack= new Stack<>();
		String desiredDates[];
		Step1Calculation tempCalc1= new Step1Calculation();
		Step2and3Calc tempCalc23;
		String firstMonthDate[];
		String endMonthDate[];
		int monthDiff;
		System.out.print(" Enter your dates must start at 6-13-2016 (seperated by ___)---->");
		holder=scan.nextLine();
		Step0CalibrationDetect test0= new Step0CalibrationDetect(holder);

		desiredDates=holder.split("___");
		firstMonthDate=desiredDates[0].split("-");
		endMonthDate=desiredDates[1].split("-");
		monthDiff=Integer.parseInt(endMonthDate[0])-Integer.parseInt(firstMonthDate[0]);
		System.out.println(" The Month Diff: "+monthDiff);
		int numOfDays=0;
		int daysIterated=0;
		String datefile;
		boolean step1Found=false;
		boolean test=false;
		boolean test2=false;
		LinkedList<Step1Calculation> tempLL;
		int length;
		LENRCSVParser parse;
		if(monthDiff==0)   // Basically this is the first month
		{
		
			// The top maps the Month to number of days
			System.out.println("SINGLE MONTH");
			int i=Integer.parseInt(firstMonthDate[0]);
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
			System.out.println("Num of Days: "+numOfDays);
			boolean testStep1=false;

			for(int p=Integer.parseInt(firstMonthDate[1]);p<Integer.parseInt(endMonthDate[1])+1;p++)
			{
				// Basically you need to go through the file until you find step1. Keep cycling through dates till the first step 1 is found
				if(p==Integer.parseInt(endMonthDate[1]))
				{
					break;
				}
				datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
				System.out.println("Opening file "+datefile);
				// But you need to include it in your code to go months without detecting step1
				//This needs to be the first Step Found
				
				// Try to find the first step 1 to calibrate step 2
				if(step1Found==false)
				{
					System.out.println("Reinitiailizing Step 1 finder");
					parse= new LENRCSVParser(datefile,test0);
					System.out.println("Detecting Step 1 Interval");
					test=parse.detectStep1Intervals();
					System.out.println("Done Step 1 Detection");
					//
					System.out.println(" Step 1 Found : "+test);
					if(test==true)  // In this interval if it is found
					{
						// Basically if you find this interval, you want to pop it off the stack and lets say that it is calibrated every day.  You want to have the latest one on as temp
						step1Found=true;
						System.out.println("Calculation");
						parse.calculateStep1Intervals();
						System.out.println("Calculation Done");
						tempLL=parse.getStep1LL();
						tempCalc1=tempLL.get(tempLL.size()-1); // This is the last element of the LL
						//Hold your temp calc							Step1LongCalcStack.push(tempCalc1);
						test2=parse.detectStep2Intervals();
						//This is if step 2 is found on the same date
						if(test2==true)
						{
							parse.calculateStep2and3Intervals(tempCalc1);
							tempCalc23=parse.getTableCalc();
							System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
							tempCalc23.printTable23();
							// Basically you push the whole table to the stack
							Step2and3CalcStack.push(tempCalc23);
						}
						
					}
					else  // This is basically if step1 is not true
					{
						System.out.println("Step 1 is not found");
						System.out.println("Calibrating for next day");
					}			
			
				}
				else // Step 1 is found    tempCalc1== step 1 element
				{
					
					System.out.println(" Step 1 is calibrated. " );
					System.out.println(" S1 value: "+tempCalc1.getMean());
					parse= new LENRCSVParser(datefile,test0);
					testStep1=parse.detectStep1Intervals();
					if(testStep1==true)
					{
						
						tempLL=parse.getStep1LL();
						if(tempLL.size()>0)
						{
						tempCalc1=tempLL.get(tempLL.size()-1);
						System.out.println("Step 1 is found.  Recalibrating new Step 1");
						}
						test2=parse.detectStep2Intervals();
						if(test2==true)
						{
							parse.calculateStep2and3Intervals(tempCalc1);
						}
						else
						{
						System.out.println("Step 2 and 3 not found");	
						}
					}
					else
					{
						test2=parse.detectStep2Intervals();
						if(test2==true)
						{
							parse.calculateStep2and3Intervals(tempCalc1);
							tempCalc23=parse.getTableCalc();
							System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
							tempCalc23.printTable23();
							Step2and3CalcStack.push(tempCalc23);
						}
						else
						{
						System.out.println("Step 2 and 3 not found");	
						}
	
					}

				}
					

			}
		
		}
		
		
		
		
		
		else
		{
					// Month Matching
			for(int i=Integer.parseInt(firstMonthDate[0]);i<Integer.parseInt(firstMonthDate[0])+monthDiff;i++) // This controls the month
						{
							// Day Matching
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
							System.out.println("Num of Days: "+numOfDays);
						//We determine the num of Days per month
			
			
				// First Month
			if(i==Integer.parseInt(firstMonthDate[0]))
			{
				System.out.println("First Month");
				for(int p=Integer.parseInt(firstMonthDate[1]);p<numOfDays+1;p++)
				{
					// Basically you need to go through the file until you find step1. Keep cycling through dates till the first step 1 is found
					datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
					System.out.println("Opening file "+datefile);
					if (p==numOfDays)
					{
						i++;
					}
					// But you need to include it in your code to go months without detecting step1
					//This needs to be the first Step Found
					if(step1Found==false)
					{
						System.out.println("Reinitiailizing to search for Step1");
						parse= new LENRCSVParser(datefile,test0);
						System.out.println("Detecting Step 1 Interval");
						test=parse.detectStep1Intervals();
						System.out.println("Done Step 1 Detection");
						//
						System.out.println(" Step 1 Found : "+test);
						if(test==true && parse.argonExists==false && parse.hydrogenExists==false)  // In this interval if it is found
						{
							step1Found=true;
							//System.out.println("Calculation");
							parse.calculateStep1Intervals();
							System.out.println("Recalibrating Step 1 to "+parse.getStep1LL().get(parse.getStep1LL().size()-1).getMean());

							//System.out.println("Calculation Done");
							tempLL=parse.getStep1LL();
							tempCalc1=tempLL.get(tempLL.size()-1);  //Hold your temp calc							Step1LongCalcStack.push(tempCalc1);
							test2=parse.detectStep2Intervals();
							//This is if step 2 is found on the same date
							if(test2==true)
							{
								System.out.println("Step2 found :"+test2);
								parse.calculateStep2and3Intervals(tempCalc1);
								tempCalc23=parse.getTableCalc();
								System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
								tempCalc23.printTable23();
								// Basically you push the whole table to the stack
								Step2and3CalcStack.push(tempCalc23);
							}
							else
							{
								System.out.println(" Step 2 and 3 not  found");
							}
							
						}
						else  // This is basically if step1 is not true
						{
							System.out.println("Step 1 is not found");
							System.out.println("Still searching for next day");
						}
						
					}
					else   // Basically Step1Temp was already found
					{
						System.out.println("Step 1 First Found");
						parse= new LENRCSVParser(datefile,test0);
						test=parse.detectStep1Intervals();
						if(test==true && parse.argonExists==false && parse.hydrogenExists==false)  // In this interval if it is found  // BASICALLY TAKE IN ANOTHER LL AND UPDATE THE TEMP
						{
							System.out.println("Updating the Step1");

							parse.calculateStep1Intervals();
							System.out.println("Recalibrating Step 1 to "+parse.getStep1LL().get(parse.getStep1LL().size()-1).getMean());

							tempLL=parse.getStep1LL();
							tempCalc1=tempLL.get(tempLL.size()-1);  // ReInitialize the Step1Temp
							Step1LongCalcStack.push(tempCalc1);
							test2=parse.detectStep2Intervals();
							//step1Found=true;
							if(test2==true)
							{
								parse.calculateStep2and3Intervals(tempCalc1);
								tempCalc23=parse.getTableCalc();
								System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
								tempCalc23.printTable23();
								Step2and3CalcStack.push(tempCalc23);
							}
							else
							{
								System.out.println("Step 2 and 3 not found.");
							}
						}
						else
						{
							test2=parse.detectStep2Intervals();
							if(test2==true)
							{
								parse.calculateStep2and3Intervals(tempCalc1);
								tempCalc23=parse.getTableCalc();
								// Basically you push the whole table to the stack
								System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
								tempCalc23.printTable23();
								Step2and3CalcStack.push(tempCalc23);
								
							}
							else
							{
								System.out.println("Step 2 and 3 not found.");
							}
						}
							
						}
					//System.out.println("Date: "+datefile);
					daysIterated++;
					}
			}
			//End month
			
			if(i==Integer.parseInt(endMonthDate[0]))
			{
				System.out.println("End Month");

				for(int p=1;p<Integer.parseInt(endMonthDate[1])+1;p++)
				{
					datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
					if(step1Found==false)
					{
						System.out.println("Step1 found is not for endMonth"); //Optimally we don't want to have this statement print
						parse= new LENRCSVParser(datefile,test0);						
						test=parse.detectStep1Intervals();						
						//
						if(test==true && parse.argonExists==false && parse.hydrogenExists==false)  // In this interval if it is found
						{
							step1Found=true;
							System.out.println("Step 1 is found");
							parse.calculateStep1Intervals();
							System.out.println("Recalibrating Step 1 to "+parse.getStep1LL().get(parse.getStep1LL().size()-1).getMean());
							tempLL=parse.getStep1LL();
							tempCalc1=tempLL.get(tempLL.size()-1); 
							  // Initialize the Step1
							Step1LongCalcStack.push(tempCalc1);
							test2=parse.detectStep2Intervals();
							if(test2==true)
							{
								parse.calculateStep2and3Intervals(tempCalc1);
								tempCalc23=parse.getTableCalc();
								System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
								tempCalc23.printTable23();
								Step2and3CalcStack.push(tempCalc23);
							}
							else
							{
								System.out.println("Step 23 not found");
							}
							
						}
						
					}
					else
					{
						parse= new LENRCSVParser(datefile,test0);
						test=parse.detectStep1Intervals();
						System.out.println("Step 1 is true.");
						if(test==true && parse.argonExists==false)  // In this interval if it is found reinitialize
						{
							System.out.println("Testing for step 2");
							System.out.println("Reinitializing");
							parse.calculateStep1Intervals();
							tempLL=parse.getStep1LL();
							tempCalc1=tempLL.get(tempLL.size()-1);  // ReInitialize the Step1Temp
							Step1LongCalcStack.push(tempCalc1);
							test2=parse.detectStep2Intervals();
							//step1Found=true;
							if(test2==true)
							{
								parse.calculateStep2and3Intervals(tempCalc1);
								tempCalc23=parse.getTableCalc();
								System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
								tempCalc23.printTable23();
								Step2and3CalcStack.push(tempCalc23);
							
							}
							else
							{
								System.out.println("Step 23 not found");
							}
						}
					
						else
						{
							test2=parse.detectStep2Intervals();
							System.out.println("Testing for step 2");
							//step1Found=true;
							if(test2==true)
							{
								System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");

								parse.calculateStep2and3Intervals(tempCalc1);
								tempCalc23=parse.getTableCalc();
								System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
								tempCalc23.printTable23();
								Step2and3CalcStack.push(tempCalc23);
							
							}
							else
							{
								System.out.println("Step 23 not found");
							}
						}
					//System.out.println("Date: "+datefile);
					daysIterated++;
					}					

				}
			}
			
				
			
			
			
			
			
			
			
			
			
				
			else
				//Months in between ie go through all the days
			{
			for(int p=1;p<numOfDays+1;p++)
			{
			
			datefile="data/"+i+"-"+p+"-"+firstMonthDate[2]+".csv";	
			System.out.println(" Opening file: "+datefile);
			if(step1Found==false)
			{
				System.out.println("Step 1 has not been found");
				parse= new LENRCSVParser(datefile,test0);
				test=parse.detectStep1Intervals();
				
				//
				if(test==true && parse.argonExists==false)  // In this interval if it is found
				{
					System.out.println("Step 1 found");
					step1Found=true;
					parse.calculateStep1Intervals();
					System.out.println("Recalibrating Step 1 to "+parse.getStep1LL().get(parse.getStep1LL().size()-1).getMean());

					tempLL=parse.getStep1LL();
					tempCalc1=tempLL.get(tempLL.size()-1);  // Initialize the Step1
					Step1LongCalcStack.push(tempCalc1);
					test2=parse.detectStep2Intervals();
					if(test2==true)
					{
						parse.calculateStep2and3Intervals(tempCalc1);
						tempCalc23=parse.getTableCalc();
						System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
						tempCalc23.printTable23();
						Step2and3CalcStack.push(tempCalc23);
						
					}
					else{
						System.out.println(" Step 23 is not found");
					}
					
				}
				else
				{
					System.out.println("Step 1 not found.  Recalibrating.");
				}
				
			}
			// Step 1 is found
			else
			{
				parse= new LENRCSVParser(datefile,test0);
				
				test=parse.detectStep1Intervals();
				if(test==true && parse.argonExists==false)  // In this interval if it is found
				{
					System.out.println("Reinitialzing step 1 ");
					parse.calculateStep1Intervals();
					tempLL=parse.getStep1LL();
					tempCalc1=tempLL.get(tempLL.size()-1);  // ReInitialize the Step1Temp
					Step1LongCalcStack.push(tempCalc1);
					test2=parse.detectStep2Intervals();
					//step1Found=true;
					if(test2==true)
					{
						parse.calculateStep2and3Intervals(tempCalc1);
						tempCalc23=parse.getTableCalc();
						System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
						tempCalc23.printTable23();
						Step2and3CalcStack.push(tempCalc23);
					
					}
				}
				else
				{
					test2=parse.detectStep2Intervals();
					if(test2==true)
					{
						parse.calculateStep2and3Intervals(tempCalc1);
						tempCalc23=parse.getTableCalc();
						System.out.println(i+"-"+p+"-"+firstMonthDate[2]+ " Step 23 Table");
						tempCalc23.printTable23();
						Step2and3CalcStack.push(tempCalc23);
						
					}
					else
					{
						System.out.println("Step 2 and 3 not found.  Recalibrating");
					}
				
				}
			
			//System.out.println("Date: "+datefile);
			daysIterated++;
			}			

			}
			}
		}
		}
	
		/*
		System.out.println("Analyzing Data for data/6-14-2016.csv");
		parse= new LENRCSVParser("data/6-14-2016.csv",test0);
		ArrayList<String> times=(ArrayList<String>)parse.getTime();
		int size=parse.getSize();
		
	
		//parse.printPiTherm();
		boolean test3=parse.detectStep1Intervals();
		System.out.println("Detection :"+test3);
		parse.detectStep2Intervals();
		parse.calculateStep1Intervals();
		parse.printStep1LinkedList();
		//parse.calculateStep2and3Intervals();
		
		*/
		//parse.printStep1and2ArrayList();
		//System.out.println("****Time + Date*****");
		//for (int i=0;i<size;i++)
		//{
		//	System.out.println( i+") "+times.get(i));
		//}
		//System.out.println("**********************");
	
		}}
