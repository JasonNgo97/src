import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class LENRCSVParser
{
    /*
     * For the constructor we plan to parse the file into
     * Numbers to be parsed:
     * 1:Time
     * 4:Jacket Out Gas
     * 5:Jacket In Gas,
     * 9 Term H2o in
     * 10 Term h2o out,
     * 12 Jack h2o out,
     * 27 Therm H2o,
     * 36 Core out H2
     * 37 Core out HE,
     * 59 Core Heater Power,
     * 61 Q supply voltage,
     * 65 Q pulse width,
     * 77 Qpow
     * That correspond to
     * Time, Jacket Out Gas, Jacket In Gas, Term h2o out, Jack h2o out, Term h2o, Core out h2, Core out He, Core heater Power, Q supply voltage
     */
    // Corresponding Data Members in ArrayList

    // Important THings to Quantify:  Where does the data go after the constructor?
    static private int size=0;
    //This is the total Data Members

    static private ArrayList<String> time;
    static private ArrayList<Double> JacketOutGas;//Temperature
    static private ArrayList<Double> JacketPressure;
    static private ArrayList<Double> H2MakeUpLPM;
    static private ArrayList<Double> JacketInGas;//Temperature
    static private ArrayList<Double> JackDiffTemp;
    static private ArrayList<Double> ThermH2oOut;
    static private ArrayList<Double> ThermH2oIn;
    static private ArrayList<Double>  JackH2OOut;
    static private ArrayList<Double> ThermH2O;
    static private ArrayList<Double> CoreOutH2;
    static private ArrayList<Double> CoreOutHE;
    static private ArrayList<Double> CoreOutArgon;

    static private ArrayList<Double> CoreReactorTemperature;
    static private ArrayList<Double> CoreInTemp;
    static private ArrayList<Double> CoreOutTemp;
    static private ArrayList<Double> CoreInPressure;
    static private ArrayList<Double> CoreTempDiff;

    static private ArrayList<Double> CoreHeaterPow;
    static private ArrayList<Double> QSupplyVoltage;
    static private ArrayList<Double> QPulseWidth;
    static private ArrayList<Double> QPow;
    static private ArrayList<Double> HeatDiff;
    static private ArrayList<Double> PThermH2O;
    static private ArrayList<Double> P_pitherm;
    static private ArrayList<Double> LCoolant;
    static private ArrayList<Double> PowOut;

    static private Stack<Step1Calculation> Step1Calc;
    static private Stack<Step2and3Calc> Step2Calc;
    static private LinkedList<Step1Calculation> Step1LongCalc;
    static private Step2and3Calc tableCalc;

    static private ArrayList<String> Step1Short;
    static private ArrayList<String> Step1ShortTime;
    static private ArrayList<String> Step1Long;
    static private ArrayList<String> Step1LongIndex;
    static private ArrayList<String> Step2Short;
    static private ArrayList<String> Step2ShortTime;
    static private ArrayList<String> Step2Long;
    static private ArrayList<String> Step2LongIndex;

    static private GasProp hydrogen;
    static private GasProp argon;
    static private GasProp helium;
    boolean argonExists;
    boolean hydrogenExists;
    static double zero;
    String date;// This is only for step1 
    
    static private Step0CalibrationDetect step0table;

    LENRCSVParser( String filename, Step0CalibrationDetect step0table )
    {
    			this.size=0;
    			argonExists=false;
    			hydrogenExists=false;
              File file = new File(filename);
              time= new ArrayList<>();
              this.step0table=step0table;
              JacketOutGas = new ArrayList<>();
              JacketInGas = new ArrayList<>();
              JackDiffTemp= new ArrayList<>();
              H2MakeUpLPM= new ArrayList<>();
              ThermH2oOut = new ArrayList<>();
              ThermH2oIn = new ArrayList<>();
              JacketPressure= new ArrayList<>();
              JackH2OOut = new ArrayList<>();
              ThermH2O = new ArrayList<>();
              CoreOutH2= new ArrayList<>();
              CoreOutHE= new ArrayList<>();
              CoreOutArgon= new ArrayList<>();
              CoreHeaterPow= new ArrayList<>();
              QSupplyVoltage= new ArrayList<>();
              QPulseWidth= new ArrayList<>();
              QPow= new ArrayList<>();
              HeatDiff= new ArrayList<>();
              PThermH2O= new ArrayList<>();
              P_pitherm= new ArrayList<>();
              LCoolant= new ArrayList<>();
              PowOut= new ArrayList<>();
              CoreReactorTemperature= new ArrayList<>();
              Step1Short= new ArrayList<>();
              Step1ShortTime= new ArrayList<>();
              Step1Long= new ArrayList<>();
              Step1LongIndex= new ArrayList<>();
              Step2Short= new ArrayList<>();
              Step2ShortTime= new ArrayList<>();
              Step2Long= new ArrayList<>();
              Step2LongIndex= new ArrayList<>();

              CoreInTemp= new ArrayList<>();
              CoreOutTemp= new ArrayList<>();
              CoreInPressure= new ArrayList<>();
              CoreTempDiff= new ArrayList<>();

              Step1Calc= new Stack<>();
              Step2Calc= new Stack<>();
              Step1LongCalc= new LinkedList<>();

              hydrogen= new GasProp("hydrogen H2");
              argon= new GasProp("argon Ar");
              helium= new GasProp("helium He");
              System.out.println("Initializing LENR");
        int parseArray[]={1,2,3,4,5,6,9,10,11,12,16,20,22,27,36,37,42,57,59,61,65,77,79};
        try
        {
            String temp;
            Scanner scan= new Scanner(file);
            String garbage= scan.nextLine();
            //String garbageSplit[]= garbage.split(",");
            //System.out.println(garbageSplit[9])
            //
            while (scan.hasNextLine())
            // So basically, the idea is to take in the next line and parse it through a function
            {
                //System.out.println("Inside");
                String ParseBreak= scan.nextLine();
            //    System.out.println(ParseBreak);
                String ParseSplit[]= ParseBreak.split(",");
                int loc=0;
                Double hold;
                for (int i=0; i<parseArray.length;i++)
                    {
                    loc=parseArray[i];
                    loc=loc-1;
                    //System.out.println("loc is "+ loc);
                    switch (loc)
                    {

                        case 0:
                            //System.out.println(ParseSplit[loc]);
                            time.add(ParseSplit[loc]);//  This throws an exception
                            temp=time.get(size);
                            //System.out.println("Date: "+temp);
                            //System.out.println("1:"+temp);
                            break;
                        case 1:
                            hold= Double.parseDouble(ParseSplit[loc]);
                            CoreReactorTemperature.add(hold);
                            //System.out.println("Core Reactor: "+hold);
                            break;
                        	
                        case 2:
                            hold= Double.parseDouble(ParseSplit[loc]);
                             CoreOutTemp.add(hold);
                             break;
                        case 3:
                            //System.out.println("3:" + ParseSplit[loc]);
                            hold= Double.parseDouble(ParseSplit[loc]);
                            //System.out.println("3(Double conversion)"+ hold);
                            JacketInGas.add(hold);
                            break;
                        case 4:
                            hold= Double.parseDouble(ParseSplit[loc]);
                            JacketOutGas.add(hold);
                            break;
                        case 5:
                            hold= Double.parseDouble(ParseSplit[loc]);
                            CoreInTemp.add(hold);
                          //  System.out.println("Core In Temp:" +hold);
                            break;

                        case 8:
                            hold= Double.parseDouble(ParseSplit[loc]);
                            //System.out.println(size+") ThermH2OIn : "+hold);
                            ThermH2oIn.add(hold);
                            hold=0.0;
                            break;
                        case 9:
                            hold= Double.parseDouble(ParseSplit[loc]);
                            hold= hold -0.06;
                            ThermH2oOut.add(hold);
                            //System.out.println(size+") ThermH2oOut : "+hold);
                            break;
                        case 11:
                            hold= Double.parseDouble(ParseSplit[loc]);
                            JackH2OOut.add(hold);
                            break;
                        case 15:
                            hold=Double.parseDouble(ParseSplit[loc]);
                            //System.out.println(hold);
                            H2MakeUpLPM.add(hold);
                            break;
                        case 19:
                            hold=Double.parseDouble(ParseSplit[loc]);
                             CoreInPressure.add(hold);
                             break;
                        case 21:
                            hold=Double.parseDouble(ParseSplit[loc]);
                            JacketPressure.add(hold);
                            break;
                        case 26:
                            hold= Double.parseDouble(ParseSplit[loc]);
                            ThermH2O.add(hold);
                            break;
                        case 35:
                            hold= Double.parseDouble(ParseSplit[loc]);
                            CoreOutH2.add(hold);
                        //    System.out.println(size+ ") H2: "+hold);
                            break;
                        case 36:
                            hold=Double.parseDouble(ParseSplit[loc]);
                        //    System.out.println(size+ ") HE: "+hold);
                            CoreOutHE.add(hold);
                            break;
                        case 41:
                            hold=Double.parseDouble(ParseSplit[loc]);
                            CoreOutArgon.add(hold);
                            break;
                        	
                        case 56:
                            hold=Double.parseDouble(ParseSplit[loc]);
                            PowOut.add(hold);
                            break;
                        case 58:
                            hold=Double.parseDouble(ParseSplit[loc]);
                            //System.out.println(hold);
                            CoreHeaterPow.add(hold);
                            break;
                        case 60:
                            hold=Double.parseDouble(ParseSplit[loc]);
                            QSupplyVoltage.add(hold);
                            break;
                        case 64:
                            hold=Double.parseDouble(ParseSplit[loc]);
                        //    System.out.println(size+ ") "+hold);
                            QPulseWidth.add(hold);
                            break;
                        case 76:
                            hold=Double.parseDouble(ParseSplit[loc]);
                            QPow.add(hold);
                            break;
                        case 78:
                            hold=Double.parseDouble(ParseSplit[loc]);
                            //System.out.println(hold);
                            LCoolant.add(hold);
                            break;

                }

            }
                //System.out.println(size);
                size++;

            //System.out.println(" Loading in the arrayList starting from "+time.get(0)+ " to "+ time.get(size-1)+ ".");
            //System.out.println(" The Total ArraySize is "+ size);
            }
            }
        catch(FileNotFoundException  e)
        {
            System.out.println("File is not found");
        }
        catch( Exception e)
        {
            System.out.println("Some other exception");
            System.out.println(e.getMessage());
        }
    //System.out.println("Before Jack Temp");
        String x=time.get(1);
        
        int index=-1;
        for(int i=0;i<x.length();i++)
        {
        	if(x.charAt(i)==' ')
        	{
        		index=i;
        		break;
        	}
         }
        //date= new String();
        for(int i=0;i<index;i++)
        {
        	date+=x.charAt(i);
        }
    initializeJackTempDiff();
    //System.out.println("After Jack Temp");
	initializePThermH2O();
	//System.out.println("After PTherm H2O");
	initializeCoreTempDiff();
	//System.out.println("After Core Temp Diff");
	initializeP_pitherm();
	initializeGasData();
	initializeHeatDiff();

    }
    public LinkedList<Step1Calculation> getStep1LL()
    {
    	LinkedList<Step1Calculation> Step1LongClone= (LinkedList<Step1Calculation>)Step1LongCalc.clone();
    	return Step1LongClone;
    }
    public Step2and3Calc getTableCalc()
    {
    	return tableCalc;
    }
    
    
    public void initializeGasData()
    {
        //Here you want to initialize the Hydrogen Gas
        String holder;
        int NumConv;
        int counter=0;
        int TemperatureHold=0;
        int PressureHold=0;
        double DensityHold=0;
        double HeatCapacityHold=0;

        double NumHolder;
        //So you use numHolder, then parse Int or parse Double to the gas properties
        int loc=0;
        String temp[];
        int columnMatter[]={0,1,2,5};
        File hydrogenFile= new File("data/Hydrogen Properties.csv");
        try
        {
            Scanner gasScan= new Scanner(hydrogenFile);

      //      System.out.println("Name :"+hydrogenFile.getName());
        //    System.out.println(" File can Read :"+ hydrogenFile.canRead());
            String garbage= gasScan.nextLine(); //The first line is garbage

            while (gasScan.hasNextLine())
            {
                holder=gasScan.nextLine();
                if(holder.isEmpty() || holder.equals(",,,,,"))
                {
                    // DO Nothing and GO to next line
                }
                else
                {
                  
                    temp=holder.split(",");
                   
                    for(int i=0;i<columnMatter.length;i++)
                    {
                        loc=columnMatter[i];
                        switch (loc)
                        {
                            case 0:
                                //This tells you temperature
                                NumHolder=Double.parseDouble(temp[loc]);
                                TemperatureHold=(int)NumHolder;
                                break;

                            case 1:
                                NumHolder=Double.parseDouble(temp[loc]);
                                PressureHold=(int)(NumHolder-14.7);
                                break;

                            case 2:
                                DensityHold=Double.parseDouble(temp[loc]);
                                break;

                            case 5:
                                HeatCapacityHold=Double.parseDouble(temp[loc]);
                                break;
                        }
                    }
                //System.out.println("\t"+" ("+counter+") ");
                hydrogen.addElem(TemperatureHold, PressureHold, DensityHold, HeatCapacityHold);
                counter++;
             //   System.out.println();
                }
            }
        }
        catch (Exception e)
        {
        //    System.out.println("There is an exception.");
        //    System.out.println(e.getMessage());
            counter=0;
        }
        //System.out.println("Done Loading in the Hydrogen Data.");

        File argonFile= new File("data/Argon Properties.csv");
        try
        {
            Scanner gasScan= new Scanner(argonFile);

           // System.out.println("Name :"+argonFile.getName());
          //  System.out.println(" File can Read :"+ argonFile.canRead());
            String garbage= gasScan.nextLine(); //The first line is garbage

            while (gasScan.hasNextLine())
            {
                holder=gasScan.nextLine();
                if(holder.isEmpty() || holder.equals(",,,,,"))
                {
                    // DO Nothing and GO to next line
                }
                else
                {
                    //System.out.println("Holder :"+holder);
                    //System.out.println("This is before the break line.");
                    temp=holder.split(",");
                    //System.out.println("Column Matter Length: "+columnMatter.length);
                    //So I want to break the temp Array and load into gasData for hydrogen
                    for(int i=0;i<columnMatter.length;i++)
                    {
                        loc=columnMatter[i];
                        switch (loc)
                        {
                            case 0:
                                //This tells you temperature
                                NumHolder=Double.parseDouble(temp[loc]);
                                TemperatureHold=(int)NumHolder;
                                break;

                            case 1:
                                NumHolder=Double.parseDouble(temp[loc]);
                                PressureHold=(int)(NumHolder-14.7);
                                break;

                            case 2:
                                DensityHold=Double.parseDouble(temp[loc]);
                                break;

                            case 5:
                                HeatCapacityHold=Double.parseDouble(temp[loc]);
                                break;
                        }
                    }
                //System.out.println("\t"+" ("+counter+") ");
                argon.addElem(TemperatureHold, PressureHold, DensityHold, HeatCapacityHold);
                counter++;
          //     System.out.println();
                }
            }
        }
        catch (Exception e)
        {
        //    System.out.println("There is an exception.");
        //    System.out.println(e.getMessage());
            counter=0;
        }
    //    System.out.println("Loading data for argon is complete.");
        File heliumFile= new File("data/Helium Properties.csv");
        try
        {
            Scanner gasScan= new Scanner(heliumFile);

        //    System.out.println("Name :"+heliumFile.getName());
      //      System.out.println(" File can Read :"+ heliumFile.canRead());
            String garbage= gasScan.nextLine(); //The first line is garbage

            while (gasScan.hasNextLine())
            {
                holder=gasScan.nextLine();
                if(holder.isEmpty() || holder.equals(",,,,,"))
                {
                    // DO Nothing and GO to next line
                }
                else
                {
                    //System.out.println("Holder :"+holder);
                    //System.out.println("This is before the break line.");
                    temp=holder.split(",");
                //    System.out.println("Column Matter Length: "+columnMatter.length);
                    //So I want to break the temp Array and load into gasData for hydrogen
                    for(int i=0;i<columnMatter.length;i++)
                    {
                        loc=columnMatter[i];
                        switch (loc)
                        {
                            case 0:
                                //This tells you temperature
                                NumHolder=Double.parseDouble(temp[loc]);
                                TemperatureHold=(int)NumHolder;
                                break;

                            case 1:
                                NumHolder=Double.parseDouble(temp[loc]);
                                PressureHold=(int)(NumHolder-14.7);
                                break;

                            case 2:
                                DensityHold=Double.parseDouble(temp[loc]);
                                break;

                            case 5:
                                HeatCapacityHold=Double.parseDouble(temp[loc]);
                                //System.out.println(HeatCapacityHold);
                                break;
                        }
                    }
                //System.out.println("\t"+" ("+counter+") ");
                helium.addElem(TemperatureHold, PressureHold, DensityHold, HeatCapacityHold);
                //helium.printGasHeatCapacity();
                counter++;
              //  System.out.println();
                }
            }
        }
        catch (Exception e)
        {
        //    System.out.println("There is an exception.");
        //    System.out.println(e.getMessage());
            counter=0;
        }
        //System.out.println("Loading data for helium is complete.");

        //System.out.println("-----Testing Gas Arrays -------");
        //hydrogen.printGasProp();
        //helium.printGasProp();
        //argon.printGasProp();
        /*hydrogen.printGasDensity();
        helium.printGasDensity();
        argon.printGasDensity();
        */
        /*
         hydrogen.printGasHeatCapacity();
           helium.printGasHeatCapacity();
        argon.printGasHeatCapacity();
        */

    }
    public void initializeJackTempDiff()
    {
        double temp=0;
        for(int i=0;i<JacketOutGas.size();i++)
        {
            temp=JacketInGas.get(i)-JacketOutGas.get(i);
            //System.out.println(temp);
            JackDiffTemp.add(temp);
        }
    }

    public void initializeHeatDiff()
    {
    	//step0table
    	double konstant=0;
        double hold=0;
        boolean helium=false;
        boolean argon=false;
        double Temperature=0;
        double tempHold;
        boolean foundKonstant=false;
        HeatDiff= new ArrayList<>();
    	//System.out.println(" Date: "+time.get(0));

        for (int i=0;i<size;i++)
        {
        	//But sometimes the thing is not stable 
        	// WHAT IF THE TEMPERATURE IS UNSTABLE IN THE MORNING?
        	//This arrayList is not going to be the right size
        	//System.out.println(" Date: "+time.get(i));
        	
        	//Trigger the argon
        	//System.out.println(" Date: "+time.get(0));

        	if(CoreOutHE.get(i)<70 && CoreOutH2.get(i)<80 && argon== false)
        	{
            	System.out.println(" Date: "+time.get(i));

        		System.out.println("In Argon");
        		argon=true;
        		argonExists=true;
            	System.out.println( "Helium Amount: "+CoreOutHE.get(i)+ "    Hydrogen  amount: "+CoreOutH2.get(i));
            	System.out.println("Temperature is "+Temperature);
            	konstant=step0table.getNearestCalibrationConstant(Temperature, helium); // Basically you initialize the konstant first
            	System.out.println("Heater / Step 0 Calibration: "+konstant);
            	
        	}
        	
        	
        	
        	if(i==0 && H2MakeUpLPM.get(0)<80)// Is it the first element
        	{
        		//System.out.println("HE Makeup: "+CoreOutHE.get(i));
        		//System.out.println("H2 Makeup: "+CoreOutH2.get(i));

        		Temperature=CoreReactorTemperature.get(i);	
        		helium=true;
        		
//        		System.out.println("Helium is "+helium);
  //      		System.out.println("Temperature is "+Temperature);
        		konstant=step0table.getNearestCalibrationConstant(Temperature, helium); // Basically you initialize the konstant first
            	zero=konstant;

        		System.out.println("Heater / Step 0 Calibration: "+konstant);
        	}
        	
        	if(i==0 && H2MakeUpLPM.get(0)>80)// Is it the first element
        	{
            	//System.out.println("H2 Makeup: "+CoreOutH2.get(i));
            	//System.out.println("HE Makeup: "+CoreOutHE.get(i));

        		hydrogenExists=true;
            	Temperature=CoreReactorTemperature.get(i);
            	helium=false;
            	System.out.println("Helium is "+helium);
            	System.out.println("Temperature is "+Temperature);
            	konstant=step0table.getNearestCalibrationConstant(Temperature, helium); // Basically you initialize the konstant first
            	zero=konstant;
            	System.out.println("Heater / Step 0 Calibration: "+konstant);

        	}
        	
        	
        	
        	
        	
        	
        	
    
        	// If the temperature changes or gas changes
        	// You need to make sure that it is stable  
        	if(Math.abs(Temperature-CoreReactorTemperature.get(i))>5  || H2MakeUpLPM.get(0)<80 && helium==false || H2MakeUpLPM.get(0)>80 && helium==true )
        	{
        		if(H2MakeUpLPM.get(0)<70 && helium==false && CoreOutHE.get(i)> 80)  // If Hydrogen becomes decrease below threshold and the helium is false, then switch
        		{
        			helium=true;
        			//Then remap your konsant
        			tempHold=step0table.getCalibrationConstant(Temperature,helium);
        			if(tempHold!=-1)
        			{
        				konstant=tempHold;
                    	zero=konstant;

        				System.out.println("Gas Change of helium to true");
        				System.out.println(" Helium is :"+CoreOutHE.get(i));
        				System.out.println("Temperature: "+Temperature);
        				System.out.println("Changing Step0 to: "+konstant);
        			}
        			else
        			{
        			//	System.out.println("Not Stable");
        			}
        		}
        		if(H2MakeUpLPM.get(0)>70 && helium==true)  //If hydrogen increase above a threshold and helium is true, then switch
        		{
        			helium=false;
        			tempHold=step0table.getCalibrationConstant(Temperature,helium);
        			if(tempHold!=-1)
        			{
        				konstant=tempHold;
                    	zero=konstant;

        				System.out.println("Gas Change of helium to false");

        				System.out.println("Changing Konstant to: "+konstant);

        			}
        			else
        			{
        			//	System.out.println("Not Stable");
        			}
        		}
        		if(Math.abs(Temperature-CoreReactorTemperature.get(i))>10)
        		{
        			Temperature=CoreReactorTemperature.get(i);
        			tempHold=step0table.getCalibrationConstant(CoreReactorTemperature.get(i), helium);
        			if(tempHold==-1)
        			{
        				//System.out.println("Not Stable");
        			// Basically continue because the temperature is not stablized
        			}
        			else
        			{
        				konstant=tempHold;
                    	zero=konstant;

        				System.out.println("Core Temperature changing to "+Temperature);
        				System.out.println("Changing Konstant to: "+konstant);

        			}
        		}
        	}
        	hold=konstant-CoreHeaterPow.get(i);
        	//System.out.println(" Core Heater Pow: "+CoreHeaterPow.get(i)+" Konstant: "+konstant);
            HeatDiff.add(hold);
            //System.out.println(i+") Heat Diff is "+HeatDiff.get(i));

        }
    }
    public void initializeCoreTempDiff()
    {
        double temp=0;
        for(int i=0;i<CoreInTemp.size();i++)
        {
            temp=CoreInTemp.get(i)- CoreOutTemp.get(i);
        //    System.out.println("Core Gas Temp Diff: "+temp);
            CoreTempDiff.add(temp);
        }
    }

    public void printPiTherm()
    {
        double holder;
        for (int i=0;i<size;i++)
        {
            holder=P_pitherm.get(i);
           // System.out.println(i+") "+ holder);
        }
    }

    //These are for the detection of Data Intervals that we push to the Queue
    public boolean detectStep1Intervals()
    {
        //These are temporary string holders
    	//System.out.println("In detection");
        String begintime= new String();
        String endtime= new String();
        String interval;
        Double holdDiff;
        String indexHolder= new String();
        String holder= new String();
        double pulseParam=0;
        boolean begin=false;
        int beginIndexInitial=-1;
        int endIndexFinal=-1;
        String BeginTimeInitial= new String();
        int numCalTimes=0;
        String holder2=new String();
        int beginIndex=-1;
        int endIndex=-1;
        boolean foundStep1=false;
        
        //System.out.println("Hello");
        // Here is the thing on the graph, you can't see it because it is negative
        for(int i=0;i<size-20;i++)

         
        {
            //
        	//System.out.println(i);
        	// tHE heat diff is 
        	//System.out.println(HeatDiff.get(i));
        	/**/
        	   if(P_pitherm.get(i)<5 && P_pitherm.get(i)>2 && P_pitherm.get(i+10)>2 && P_pitherm.get(i+10)<5 && HeatDiff.get(i)<2
                    && HeatDiff.get(i+10)<2 && begin==false ) 
        	 
        
           // if(P_pitherm.get(i)>2 &&  HeatDiff.get(i)<2
             //       && HeatDiff.get(i+10)<2 && begin==false ) //This tells you that the sequence has begin
            {
                //System.out.println("*****Step 1 Initialized*******");
                //This tells you that the sequence has begun
               //	System.out.println("Heat Diff: "+HeatDiff.get(i));
                //pulseParam=QPulseWidth.get(i);
                begin=true;
                beginIndex=i;
                begintime= time.get(beginIndex);
                foundStep1=true;
               // System.out.println("Pulse Param: "+pulseParam);
                //System.out.println("Begin Time: "+begintime+" Begin Index: "+beginIndex);
                //System.out.println(" P_pitherm: "+P_pitherm.get(i));
                //System.out.println(" HeatDiff: "+HeatDiff.get(i));
                //System.out.println(" HeatDiff +10: "+HeatDiff.get(i+10));
                if(numCalTimes==0)
                {
                    beginIndexInitial=i;
                    BeginTimeInitial=begintime;

                }
                numCalTimes+=1;
            }
            // Changing Pulse Parm
          

            if(P_pitherm.get(i)<0.5  && HeatDiff.get(i)<1 && begin==true )
            {
                endIndexFinal=i;
                int endIndexTemp;
                endIndexTemp=endIndex;
                endIndex=i;
                endtime= time.get(endIndexFinal);

                //System.out.println("*****Step 1 is completely done********");
                //System.out.println("End Time is "+endtime+" End Index: "+endIndex);
               // System.out.println(" Ppitherm: "+P_pitherm.get(i));
                //System.out.println(" Heat Diff: "+HeatDiff.get(i));
                if(endIndex-beginIndex<20)
                {
                	System.out.println("Interval is too small to add");
                	endIndex=endIndexTemp;
                	   begintime= new String();
                       endtime= new String();
                       beginIndex=0;
                       endIndex=0;
                       pulseParam=0;
                       begin=false;
                }
                else
                {
                	
                 holder=beginIndex+"----"+endIndex;
                Step1Short.add(holder);
                holder2=begintime+"----"+endtime;

                Step1ShortTime.add(holder2);
                //System.out.println("Index is "+holder);
                //System.out.println("Changing pulse param to "+QPulseWidth.get(i));

                begin=false;
                interval= BeginTimeInitial+"------"+endtime;
                //System.out.println("----------------------------------------------------------------------"
                //    + "-----------------------");
                //System.out.println("Interval: "+ interval);
                indexHolder= beginIndexInitial+"----"+endIndexFinal;
                interval+= " ||  Total Index: " +indexHolder;
                
                Step1Long.add(interval);
                Step1LongIndex.add(indexHolder);
                }

                //System.out.println("Index: "+indexHolder);
                //System.out.println(" Time Interval: "+ interval);
                //Step1.add(interval);

                //reset
                begintime= new String();
                endtime= new String();
                beginIndex=0;
                endIndex=0;
                pulseParam=0;
             //   return true;
            }
        }
        
        System.out.println("Step 1: "+foundStep1);
        return foundStep1;
    }
    public boolean detectStep2Intervals()
    {
    	System.out.println("Step 2 Date: "+date);
        int beginInitialIndex = 0;
        int endFinalIndex;
        boolean found2=false;
        double temp=0;
        int beginIndex=-1;
        int endIndex=-1;

        String beginTimeInitial= new String();
        String endTimeFinal= new String();

        String beginTime= new String();
        String endTime= new String();

        boolean begin=false;
        boolean end=false;

        double pulseParam=-1;

        int numCalTimes=0;

        String holderTime= new String();
        String holderIndex= new String();

        for(int i=0;i<size;i++) // Here we try to cycle through all the data points
        {
            // Calibration only runs during Helium
            if(CoreOutHE.get(i)>90)
            {
                //System.out.println("CalTime HE   "+size+") "+ CoreOutHE.get(i));
                //This is the threshold of step2
            	if(i<CoreInTemp.size()-60)
            	{
                if(HeatDiff.get(i)>1.1 && HeatDiff.get(i+50)>1.1 && P_pitherm.get(i)>5 && P_pitherm.get(i+10)>5 && P_pitherm.get(i+10)>0)
                {
                    //This is the first time
                    if(numCalTimes==0)
                    {
                        beginTimeInitial=time.get(i);
                        beginInitialIndex=i;
                        beginTime=time.get(i);
                        begin=true;
                        beginIndex=i;
                        pulseParam=QPulseWidth.get(i);
                        temp=CoreInTemp.get(i);
                        found2=true;
                        System.out.println("*****Step 2 Beginning*******");
                        System.out.println("Begin Time: "+beginTimeInitial);
                        System.out.println(" Pulse Param: "+pulseParam);
                        numCalTimes++;
                    }
                    if((QPulseWidth.get(i)!=pulseParam && numCalTimes>0) ||  (CoreInTemp.get(i)>(temp+10)&& numCalTimes>0) || (CoreInTemp.get(i)<(temp-10) && numCalTimes>0))
                    {
                        endIndex=i-1;
                        endTime=time.get(i);
                        System.out.println("----Step 2 Ended with Pulse Param: "+ pulseParam);
                        holderTime= beginTime+ " ---- "+ endTime;
                        holderIndex= beginIndex+ "----"+endIndex;
                        Step2Short.add(holderIndex);
                        Step2ShortTime.add(holderTime);
                        System.out.println(" Time: "+holderTime);
                        System.out.println(" Index: "+holderIndex);
                        //System.out.println("*****----Step 2 Initializing------******");
                        beginIndex=i;
                        beginTime=time.get(i);
                        temp=CoreInTemp.get(i);
                        //System.out.println("Begin Time: "+beginTime);
                        pulseParam=QPulseWidth.get(i);
                    }
                }}
                if(P_pitherm.get(i)<1  && HeatDiff.get(i)<1 && begin==true )
                {

                    System.out.println("*******Step 2*******DONE*****************");
                    end=true;
                    endFinalIndex=i;
                    endIndex=i;
                    endTime= time.get(endFinalIndex);
                    endTimeFinal=time.get(endFinalIndex);
                    numCalTimes=0;
                    holderIndex=beginIndex+"----"+endIndex;
                    holderTime=beginTime+"----"+endTime;
                    Step2Short.add(holderIndex);
                    Step2ShortTime.add(holderTime);

                begin=false;
                holderTime= "Time: "+beginTimeInitial+"------"+endTimeFinal;
                System.out.println("----------------------------------------------------------------------"
                   + "-----------------------");
                //System.out.println("Interval: "+ interval);
                holderIndex= beginInitialIndex+"----"+endFinalIndex;
                holderTime+=" ||  Index: " +holderIndex;
                //System.out.println("Adding Interval "+holderIndex);
                Step2Long.add(holderTime);
                Step2LongIndex.add(holderIndex);
                //System.out.println(holderTime);
                //System.out.println("Index: "+indexHolder);
                //System.out.println(" Time Interval: "+ interval);
                //Step1.add(interval);

                //reset
                beginTime= new String();
                endTime= new String();
                beginIndex=0;
                endIndex=0;
                pulseParam=0;
            }}
            }
        return found2;
        }

    public void calculateStep1Intervals()
    {
    	System.out.println("In step1 Calculation");
        int beginIndexL=0;
        int endIndexL=0;
        double holder=0;
        int beginIndex;
        int endIndex;
        Step1Calculation temp;
        String intervalHolder[];
        String dateHolder[];
        String dateTemp[];
        String date1= new String();
     //   System.out.println("----Step 1 Calculation----");
        for(int i=0;i<Step1ShortTime.size();i++)
        {
            intervalHolder=Step1Short.get(i).split("---");
            beginIndex=Integer.parseInt(intervalHolder[0]);
            dateHolder=time.get(beginIndex).split(" ");
            date1=dateHolder[0];
            endIndex=Math.abs(Integer.parseInt(intervalHolder[1]));
            temp=new Step1Calculation(Step1Short.get(i),date1);
            
        //        System.out.println("\t"+"("+i+")");
            System.out.println(i+") Calculating Step 1 for "+temp.getIndex());
            System.out.println("QPow :"+QPow.get(beginIndex)+ " LCoolant : "+LCoolant.get(beginIndex));
            for(int j=beginIndex;j<endIndex;j++)
            {
                temp.PiMinusLCool(QPow.get(j),LCoolant.get(j));
            }
            temp.CalculateMean();
           System.out.println(" Mean: "+temp.getMean());
           System.out.println("Max : "+ temp.getMax());
            //System.out.println("Max Index: "+ temp.getMaxIndex());
            Step1Calc.push(temp);
       //     System.out.println("Pushing");
        }

      Step1Calculation temporLong;
      Step1Calculation last= new Step1Calculation(date1);
      int time=0;
      double mean=0;
      double sum=0;

    //    System.out.println("The Stack is Empty (0V1) "+ Step1Calc.empty());

     try
     {
      while( Step1Calc.peek()!=null)
      {
         // System.out.println("hI");
          last=Step1Calc.pop();
          last.setIndex();
          if(time==0)
          {
            //  System.out.println("Print BeginL: "+last.getBegin());
              endIndexL=last.getEnd();
              time++;
          }
          if(time!=0)
          {
              sum+=last.getMean();
              time++;
          }
      }
     }

     //This means that the file is done popping
     catch (Exception e)
     {
          mean=sum/(time-1);
          beginIndexL=last.getBegin();
        //  System.out.println("-------Step 1 Long--------- ");
          String indexL= beginIndexL+"----"+endIndexL;
          System.out.println("L Index: "+indexL);
         
          dateTemp=date.split("ll");
          date=dateTemp[1];
          temporLong= new Step1Calculation(indexL,date);
          System.out.println("Step1 Long Index: "+temporLong.getIndex());
          temporLong.setMean(mean);
          Step1LongCalc.add(temporLong);
       //   System.out.println("Mean:  "+mean);
     }

    }
    //These are used for the testing of both queues
    public void calculateStep2and3Intervals(Step1Calculation past)
    {
    	System.out.println("Calculating Step 2 and 3 now***");
    	//System.out.println("Step 1 LONG SIZE: "+Step1LongCalc.size());
        double mean=0;
        String interval;
        int beginIndex=0;
        int endIndex;
        String holdShortInterval[];
        double Qtemp;
        double LCtemp;
        double H2MakeUpTemp;
        double tempJackDiffTemp;
        double tempCoreDiffTemp;
        double JacketPressureTemp;
        double JacketTemp;
        double PowHeatTemp;
        double H2Temp;
        double HETemp;
        double PowOutTemp;
        double CoreGasTemp;
        double CoreGasPressureTemp;
        
        double CoreTemperature;

        ArrayList<Double> QPowHolder;
        ArrayList<Double> H2MakeUpLPMholder;
        ArrayList<Double> HeaterPowHolder;
        ArrayList<Double> JacketTempDiffholder;
        ArrayList<Double> JacketTempHolder;
        ArrayList<Double> LCoolantHolder;
        ArrayList<Double> JacketPressureHolder;// Think we take this from jacket in
        ArrayList<Double> H2holder;
        ArrayList<Double> HEholder;
        ArrayList<Double> PowOutHolder;
        ArrayList<Double> CoreHeaterPowHolder;
        ArrayList<Double> TemperatureHolder;
        ArrayList<Double> CoreGasPressure;
        ArrayList<Double> CoreGasTempHolder;
        ArrayList<Double> CoreTempDiffHolder;
        //If step 1 is not found
        if(Step1LongCalc.size()==0)
        {
        	System.out.println("Step1 Internal size==0");

        	//Use the past number
        	tableCalc= new Step2and3Calc(past.getMean());
        	for( int i=0;i<Step2Short.size();i++)
                //So this is for each interval
            {
        		
                interval=Step2Short.get(i);
                //Split the interval into begin and end
                holdShortInterval=interval.split("---");
                beginIndex=(Integer.parseInt(holdShortInterval[0]));
                endIndex=Math.abs((Integer.parseInt(holdShortInterval[1])));
                
                //Using the begin index and end index, I want to load up a set of ArrayList for QPow and LCoolant2
                QPowHolder= new ArrayList<>();
                LCoolantHolder= new ArrayList<>();
                H2MakeUpLPMholder= new ArrayList<>();
                HeaterPowHolder= new ArrayList<>();
                JacketTempDiffholder=new ArrayList<>();
                JacketTempHolder= new ArrayList<>();
                JacketPressureHolder= new ArrayList<>();
                H2holder= new ArrayList<>();
                HEholder= new ArrayList<>();
                PowOutHolder= new ArrayList<>();
                TemperatureHolder= new ArrayList<>();
                CoreHeaterPowHolder= new ArrayList<>();
                CoreTempDiffHolder= new ArrayList<>();
                CoreGasPressure= new ArrayList<>();
                CoreGasTempHolder= new ArrayList<>();
                
                
                
                

                for(int j=beginIndex+1;j<endIndex;j++)  //Here we shift the begin index by 1
                {
                	
                	CoreTemperature=CoreReactorTemperature.get(j);
                    Qtemp=QPow.get(j);
                    LCtemp=LCoolant.get(j);
                    H2MakeUpTemp=H2MakeUpLPM.get(j);

                    JacketPressureTemp=JacketPressure.get(j);
                    JacketTemp=JacketInGas.get(j);
                    tempJackDiffTemp=JackDiffTemp.get(j);
                    H2Temp=CoreOutH2.get(j);
                    HETemp=CoreOutHE.get(j);
                    PowOutTemp=PowOut.get(j); //Pow out
                    PowHeatTemp=CoreHeaterPow.get(j); //Core heater

                    CoreGasTemp=CoreInTemp.get(j);
                    CoreGasPressureTemp=CoreInPressure.get(j);
                    tempCoreDiffTemp=CoreTempDiff.get(j);

                    QPowHolder.add(Qtemp);
                    LCoolantHolder.add(LCtemp);
                    H2MakeUpLPMholder.add(H2MakeUpTemp);
                    //System.out.println(H2MakeUpTemp);
                    HeaterPowHolder.add(PowHeatTemp);
                    JacketTempHolder.add(JacketTemp);
                    JacketPressureHolder.add(JacketPressureTemp);
                    H2holder.add(H2Temp);
                    HEholder.add(HETemp);
                    TemperatureHolder.add(CoreTemperature);
                    CoreTempDiffHolder.add(tempCoreDiffTemp);
                    CoreGasTempHolder.add(CoreGasTemp);
                    CoreGasPressure.add(CoreGasPressureTemp);

                    JacketTempDiffholder.add(tempJackDiffTemp);
                    CoreHeaterPowHolder.add(PowHeatTemp);
                    PowOutHolder.add(PowOutTemp);

                }
                
                
                
                
                
                
                
                double qParam= QPulseWidth.get(beginIndex+1);
                int avgTemperature=CalculateAvgTemperature(TemperatureHolder);
                tableCalc.addArray(interval, QPowHolder, H2MakeUpLPMholder, JacketTempDiffholder, LCoolantHolder, qParam, H2holder, HEholder,PowOutHolder, CoreHeaterPowHolder, JacketTempHolder,
                        JacketPressureHolder, CoreTempDiffHolder, CoreGasTempHolder, CoreGasPressure, hydrogen,helium,avgTemperature);
                //tableCalc.printTable23();

        }
        }
        //Basically, here there is just 1 interval, hence you don't need to match
       // System.out.println("Step1LongCalc size"+ Step1LongCalc.size());
        if (Step1LongCalc.size()>0)
        {
        	//System.out.println("FU");
        	System.out.println("Step1 Internal size==1");
        	//Here you initialize the table
            tableCalc= new Step2and3Calc(Step1LongCalc.get(0).getMean());
            /* So basically, you need to pop things off the stack and use those intervals
             * use the two members Step2Short which will contain the index
             *  use those indices to match the parameters pulse
             * Map the Qpulse ArrayList and LCoolant indices to the function
             * remember to go 1 over consistently for the begin index, or else there will be overlap
            */
            for( int i=0;i<Step2Short.size();i++)
                //So this is for each interval
            {
                interval=Step2Short.get(i);
                //Split the interval into begin and end
                holdShortInterval=interval.split("---");
                beginIndex=(Integer.parseInt(holdShortInterval[0]));
                endIndex=Math.abs((Integer.parseInt(holdShortInterval[1])));
                //Using the begin index and end index, I want to load up a set of ArrayList for QPow and LCoolant2
                QPowHolder= new ArrayList<>();
                LCoolantHolder= new ArrayList<>();
                H2MakeUpLPMholder= new ArrayList<>();
                HeaterPowHolder= new ArrayList<>();
                JacketTempDiffholder=new ArrayList<>();
                JacketTempHolder= new ArrayList<>();
                TemperatureHolder= new ArrayList<>();
                JacketPressureHolder= new ArrayList<>();
                H2holder= new ArrayList<>();
                HEholder= new ArrayList<>();
                PowOutHolder= new ArrayList<>();
                CoreHeaterPowHolder= new ArrayList<>();
                CoreTempDiffHolder= new ArrayList<>();
                CoreGasPressure= new ArrayList<>();
                CoreGasTempHolder= new ArrayList<>();

                for(int j=beginIndex+1;j<endIndex;j++)  //Here we shift the begin index by 1
                {
                    Qtemp=QPow.get(j);
                    LCtemp=LCoolant.get(j);
                    H2MakeUpTemp=H2MakeUpLPM.get(j);
                    CoreTemperature=CoreReactorTemperature.get(j);
                    JacketPressureTemp=JacketPressure.get(j);
                    JacketTemp=JacketInGas.get(j);
                    tempJackDiffTemp=JackDiffTemp.get(j);
                    H2Temp=CoreOutH2.get(j);
                    HETemp=CoreOutHE.get(j);
                    PowOutTemp=PowOut.get(j); //Pow out
                    PowHeatTemp=CoreHeaterPow.get(j); //Core heater

                    CoreGasTemp=CoreInTemp.get(j);
                    CoreGasPressureTemp=CoreInPressure.get(j);
                    tempCoreDiffTemp=CoreTempDiff.get(j);

                    QPowHolder.add(Qtemp);
                    LCoolantHolder.add(LCtemp);
                    H2MakeUpLPMholder.add(H2MakeUpTemp);
                    //System.out.println(H2MakeUpTemp);
                    HeaterPowHolder.add(PowHeatTemp);
                    JacketTempHolder.add(JacketTemp);
                    JacketPressureHolder.add(JacketPressureTemp);
                    H2holder.add(H2Temp);
                    HEholder.add(HETemp);
                    TemperatureHolder.add(CoreTemperature);
                    CoreTempDiffHolder.add(tempCoreDiffTemp);
                    CoreGasTempHolder.add(CoreGasTemp);
                    CoreGasPressure.add(CoreGasPressureTemp);

                    JacketTempDiffholder.add(tempJackDiffTemp);
                    CoreHeaterPowHolder.add(PowHeatTemp);
                    PowOutHolder.add(PowOutTemp);

                }
                //public void addArray(String index, ArrayList<Double> QPow, ArrayList<Double> H2MakeUpLPM, ArrayList<Double> tempDiff,
                    //    ArrayList<Double> LCoolant2, double pulseParam, ArrayList<Double> H2, ArrayList<Double> HE, ArrayList<Double> PowOut, ArrayList<Double> HeaterPow,
                    //    ArrayList<Double> JacketTemp, ArrayList<Double> JacketPressure, GasProp hydrogen, GasProp helium)
                /*
                 * public void addArray(String index, ArrayList<Double> QPow, ArrayList<Double> H2MakeUpLPM, ArrayList<Double> tempDiff,
            ArrayList<Double> LCoolant2, double pulseParam, ArrayList<Double> H2, ArrayList<Double> HE, ArrayList<Double> PowOut, ArrayList<Double> HeaterPow,
            ArrayList<Double> JacketTemp, ArrayList<Double> JacketPressure, GasProp hydrogen, GasProp helium)

            public void addArray(String index, ArrayList<Double> QPow, ArrayList<Double> H2MakeUpLPM, ArrayList<Double> tempDiff,
            ArrayList<Double> LCoolant2, double pulseParam, ArrayList<Double> H2, ArrayList<Double> HE, ArrayList<Double> PowOut, ArrayList<Double> HeaterPow,
            ArrayList<Double> JacketTemp, ArrayList<Double> JacketPressure, ArrayList<Double> CoreTempDiff, ArrayList<Double> CoreTemp, ArrayList<Double> CorePressure,GasProp hydrogen, GasProp helium)

                 */
             double qParam= QPulseWidth.get(beginIndex+1);
             int avgTemperature=CalculateAvgTemperature(TemperatureHolder);
          //   System.out.println("Adding element to tableCalc");
                tableCalc.addArray(interval, QPowHolder, H2MakeUpLPMholder, JacketTempDiffholder, LCoolantHolder, qParam, H2holder, HEholder,PowOutHolder, CoreHeaterPowHolder, JacketTempHolder,
                        JacketPressureHolder, CoreTempDiffHolder, CoreGasTempHolder, CoreGasPressure, hydrogen,helium,avgTemperature);

            }
            //tableCalc.printTable23();
        }
        }
       

    
    
    public int CalculateAvgTemperature(ArrayList<Double> Temperature)
    {
    	double tempTemperature=0;
    	int temperature=0;
    	for(int i=0;i<Temperature.size();i++)
    	{
    		tempTemperature+=Temperature.get(i);
    	}
    	temperature=(int)Math.round(tempTemperature/Temperature.size());
    	return temperature;
    }

    public void printStep1and2ArrayList()
    {
        String holder[];
        int crap=0;
        int holdMax1Index=0;
        int holdMax2Index=0;
        String trash[];
        String trash2[];
        int hold=0;
        double pulseParam=0;

        System.out.println("---------------------------------");
        System.out.println("Printing out the Step 1 Intervals");
        System.out.println("---------------------------------");
        for(int i=0;i<Step1Short.size();i++)
        {
            System.out.println("\t ("+i+")  ");
            holder=Step1Short.get(i).split("---");
            holdMax1Index=(Integer.parseInt(holder[1]));
            pulseParam=QPulseWidth.get(hold=(Integer.parseInt(holder[0])));
            System.out.println("Pulse Width: "+pulseParam);
            System.out.println("Index: "+Step1Short.get(i));
            System.out.println(Step1ShortTime.get(i));
        }
        System.out.println("-----Printing out the entire test intervals-----");
        for(int i=0;i<Step1Long.size();i++)
        {
            System.out.println("\t ("+i+")  ");
            trash=Step1LongIndex.get(i).split("----");
            crap=Integer.parseInt(trash[1]);
            if(crap>Math.abs(holdMax1Index))
            {
                System.out.println("HoldMax1 Index: "+holdMax1Index);
                System.out.println("Long Index: "+crap);
                System.out.println(" Test interval is not complete in this run.");
            }
            else
            {
                System.out.println(" Test interval is complete in this run.");
            }
            System.out.println(Step1Long.get(i));
        }
        System.out.println("=================================");
        System.out.println("Printing out the Step 2 Intervals");
        System.out.println("=================================");

        for(int i=0;i<Step2Short.size();i++)
        {
            System.out.println("\t ("+i+")  ");
            holder=Step2Short.get(i).split("---");
            holdMax2Index=(Integer.parseInt(holder[1]));
            pulseParam=QPulseWidth.get(hold=(Integer.parseInt(holder[0])));
            System.out.println("Pulse Width : "+pulseParam);
            System.out.println(Step2Short.get(i));
            System.out.println(Step2ShortTime.get(i));
        }
        System.out.println("-----Printing out the entire test intervals-----");
        for(int i=0;i<Step2Long.size();i++)
        {

            trash=Step2LongIndex.get(i).split("----");
            crap=Integer.parseInt(trash[1]);
            if(crap<Math.abs(holdMax2Index))
            {
                System.out.println(" Test interval is not complete in this run.");
                holdMax2Index=Math.abs(holdMax2Index);
                System.out.println("\t"+"HoldMax2 Index: "+holdMax2Index);
                System.out.println("\t"+"Long Index: "+crap);

            }
            else
            {
                System.out.println(" Test interval is complete in this run.");

            }
            System.out.println("Completed "+Step2Long.get(i));
        }

    }

    public void initializeP_pitherm()
    {
        double hold=0;

        for(int i=0;i<size;i++)
        {
            hold= QPow.get(i)- PThermH2O.get(i);
            P_pitherm.add(hold);
            //System.out.println(i+") "+P_pitherm.get(i));
        }
        //System.out.println("*******************");
    }
    public void initializePThermH2O()
    {
        double konstant1=4.184;
        //double konstant2=0.06;
        double konstant3= 1000.0/60.0;
        double holder=0;
        double thermDelta=0;

        for (int i=0; i<size; i++)
        {
            thermDelta= ThermH2oOut.get(i)-ThermH2oIn.get(i);
            holder=konstant1*konstant3*thermDelta*ThermH2O.get(i);
            holder=holder;
            PThermH2O.add(holder);
           // System.out.println(i+") PThermH2O: "+PThermH2O.get(i)+" Konstant "+konstant3);
        }
    }

   public void printPThermH2O(){
	   System.out.println("PTherm H2O");
	   for(int i=0;i<PThermH2O.size();i++)
	   {
		   System.out.println(i+":" +PThermH2O.get(i));
	   }
   }
    
    public int getSize()
    {
        return size;
    }
    //
    public void printStep1LinkedList()
    {	
    	if(argonExists==true)
    	{
    		System.out.println("Argon Exists so ignore data");
    	}
    	
    	System.out.println("-----Step 1 LL-----");
    	if(Step1LongCalc.size()>0)
    	{
    		System.out.println("Size is "+Step1LongCalc.size());
    		System.out.println("First elemenet: "+Step1Long.get(0));
    	for(int i=0;i<Step1LongCalc.size();i++)
    	{
    		System.out.println(Step1Long.get(i)+" ||  Data:  "+Step1LongCalc.get(i).getMean());
    	}
    	}
    	}
    public ArrayList<String>getTime()
    {
        return this.time;
    }
/*
    public void printH2O()
    {
    	System.out.println("H2O");
    	for(int i=0;i<ThermH2O.size();i++)
    	{
    		System.out.println(ThermH2O.get(i));
    	}
    }
    
    */
    
    public ArrayList<Double> getPiTherm(int screenDomain){
    	System.out.println("Getting PI Therm");
    	double temp;
    	int konstant;
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	konstant=(int)P_pitherm.size()/screenDomain;
       	//System.out.println(" Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<P_pitherm.size();i=i+konstant)
    	{
    		concatPiTherm.add(P_pitherm.get(i));
    	}
    	    	return concatPiTherm;
    }
    public ArrayList<Double> getStep1Points(int screenDomain){
    	System.out.println("Getting Step1");
    	double temp;
    	int konstant;
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	konstant=(int)P_pitherm.size()/screenDomain;
    	Step1Calculation temp2;
    	Step1Calculation tempHolder;
    	ArrayList<Double> getStep1Points= new ArrayList<>();
    	System.out.println("Step1Long Size: "+Step1LongCalc.size());
    	
    	if(Step1LongCalc.size()>0)
    	{
    		
    		for(int i=0;i<Step1LongCalc.size();i++)
    		{
    		System.out.println("Inside Step1LongCalc");
    		temp2=Step1LongCalc.get(i);
    		tempHolder=(Step1Calculation)temp2.clone();
    		Step1Calc.push(tempHolder);
    		ArrayList<Double> tempStep1=temp2.getStep1Calc();
    		
    		System.out.println("Size: "+temp2.getStep1Calc().size());
    		//System.out.println("")
    		
    		
    		// The question is how do you add it with the scale of screen Domain?
    		
    		/*
    		 * 
    		 * 	double konstant2= 8000/screenDomain
    		 */
    		
    		int sizeDomain=time.size();
    	
    			for(int j=temp2.getBegin();j<temp2.getEnd();j=j+konstant) // Basically, the idea is that you pop off the LinkedList or Stack that it used to be
    			{
    				//System.out.println(i+") "+tempStep1.get(j));
    				//System
    				System.out.println("J: "+j+" ScreenDomain: "+screenDomain+ " Size "+sizeDomain);
    				temp=j/(sizeDomain/(screenDomain-100));
    				System.out.println("Temp: "+temp);
    				getStep1Points.add(temp);
    			}
    		}
    		
    		//
    		
    		// Basically, you want to convert 8000 points in 800 points
    		
    		//These are the intervals that you skip
    		/*for(int j=temp2.getBegin();j<temp2.getEnd();j=j+konstant)
    			{
    			temp=(int)j/screenDomain;
    			getStep1Points.add(temp);
    			}
    		}*/
    	}
       	//System.out.println(" Konstant: "+konstant);
    	printStep1LinkedList();
    	return getStep1Points;
    }
    /*public void printPIThermLL()
    {
    	System.out.println("PiThermLL");
    	for(int i=0;i<P_pitherm.size();i++)
    	{
    		System.out.println(P_pitherm.get(i));
    	}
    }8
   */
    public void printStep0()
    {
    	System.out.println("Step 0: "+zero);
    }
    
    
    public ArrayList<Double> getStep2and3Points( int pulseParam, int screenDomain)
    {
    	System.out.println("Date: "+date);
    	LinkedList<S_23> samePulseParam=tableCalc.getPulseParamTable(pulseParam);
    	if(samePulseParam==null)
     	{
    	 return null;
     	}
    	ArrayList<Double> temp= new ArrayList<>();
    	S_23 step23interval;
    	int beginIndex;
    	int endIndex;
		int sizeDomain=time.size();
		double temp23;
		int konstant=    (int)P_pitherm.size()/screenDomain;

//		temp=j/(sizeDomain/(screenDomain-100));

/*		
 * 	for(int j=temp2.getBegin();j<temp2.getEnd();j=j+konstant) // Basically, the idea is that you pop off the LinkedList or Stack that it used to be
    			{
    				//System.out.println(i+") "+tempStep1.get(j));
    				//System
    				System.out.println("J: "+j+" ScreenDomain: "+screenDomain+ " Size "+sizeDomain);
    				temp=j/(sizeDomain/(screenDomain-100));
    				System.out.println("Temp: "+temp);
    				getStep1Points.add(temp);
    			}
 */		if(samePulseParam==null)
 	{
	 return null;
 	}
		System.out.println("Same Pulse Param SIZE:"+samePulseParam.size());
    	for(int i=0;i<samePulseParam.size();i++)
    	{
    		step23interval=samePulseParam.get(i);
    		System.out.println("Begin Index: "+step23interval.getBeginIndex());
       		System.out.println(" End Index: "+step23interval.getEndIndex());
       		for(int j=step23interval.getBeginIndex();j<step23interval.getEndIndex();j=j+konstant)
       		{
       			temp23=j/(sizeDomain/(screenDomain-100));
       			temp.add(temp23);
       		}
    	}
    	return temp;
    }
    
    public LinkedList<Integer> getPulseRange()
    {
    	LinkedList<Integer> pulseParam= new LinkedList<>();
    	int tempPulseParam=-1;
    	int beginIndex=-1;
    	for(int i=0;i<QPulseWidth.size();i++)
    	{
    		if(QPulseWidth.get(i)>10 && beginIndex==-1)//This means that it is the first element
    		{
    		tempPulseParam=QPulseWidth.get(i).intValue();
    		beginIndex=i;
    		pulseParam.add(tempPulseParam);
    		System.out.println("**Pulsing Param :"+tempPulseParam);
    		System.out.println(i);

    		}
    		if(Math.abs(QPulseWidth.get(i)-tempPulseParam)>5  && beginIndex!=-1 && QPulseWidth.get(i)>10  && Math.abs(QPulseWidth.get(i)-QPulseWidth.get(i+10))<1)
    		{
    			tempPulseParam=QPulseWidth.get(i).intValue();
    			pulseParam.add(tempPulseParam);
        		System.out.println("**Pulsing Param :"+tempPulseParam);
        		System.out.println(i);
    		}
    	}
    	// Now shrink the LL
    	boolean hold=false;
    	int j;
    	System.out.println("Shrinking");
    	for(int i=0;i<pulseParam.size();i++)
    	{
    		j=i+1;
    		tempPulseParam=pulseParam.get(i);
    		hold=false;

    		while(hold==false)
    		{
    			//System.out.println("Inside");
    			System.out.println("J: "+j);
    			System.out.println("Size: "+pulseParam.size());
    			if(j==pulseParam.size()-1  || j==pulseParam.size())
    			{
    				hold=true;
    				break;
    			}

    			if(tempPulseParam==pulseParam.get(j))
    			{
    			System.out.println("Shrink");
    				pulseParam.remove(j);
    				
    			}
    			
    			if(tempPulseParam!=pulseParam.get(j))
    			{
    				j++;
    			}
    			    		}
    		System.out.println("OUT");
    	}
    	
    	
    	
    	return pulseParam;
    }
    
    
    
    
    public ArrayList<Double> getHeatDiff(int screenDomain)
    {
    	double temp;
    	int konstant;
    	//System.out.println("Getting HeatDiff");
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	temp=screenDomain/P_pitherm.size();  
    	//System.out.println(" Amount: "+P_pitherm.size()/screenDomain);
    //	System.out.println("Temp: "+temp);
    	
       	konstant=(int)P_pitherm.size()/screenDomain;
    	//System.out.println("Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<HeatDiff.size();i=i+konstant)
    	{
    		concatPiTherm.add(HeatDiff.get(i));
    	}
    	    	return concatPiTherm;
    	
    }
    
    public ArrayList<Double> getHeliumCoreGas(int screenDomain)
    {
    	double temp;
    	int konstant;
    	//System.out.println("Getting HeatDiff");
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	temp=screenDomain/P_pitherm.size();  
    	//System.out.println(" Amount: "+P_pitherm.size()/screenDomain);
    //	System.out.println("Temp: "+temp);
    	
       	konstant=(int)P_pitherm.size()/screenDomain;
    	//System.out.println("Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<HeatDiff.size();i=i+konstant)
    	{
    		concatPiTherm.add(CoreOutHE.get(i));
    	}
    	    	return concatPiTherm;
    	
    }
    public ArrayList<Double> getHydrogenCoreOutGas(int screenDomain)
    {
    	double temp;
    	int konstant;
    	//System.out.println("Getting HeatDiff");
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	temp=screenDomain/P_pitherm.size();  
    	//System.out.println(" Amount: "+P_pitherm.size()/screenDomain);
    //	System.out.println("Temp: "+temp);
    	
       	konstant=(int)P_pitherm.size()/screenDomain;
    	//System.out.println("Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<HeatDiff.size();i=i+konstant)
    	{
    		concatPiTherm.add(CoreOutH2.get(i));
    	}
    	    	return concatPiTherm;
    	
    }
    public ArrayList<Double> getArgonCoreOutGas(int screenDomain)
    {
    	double temp;
    	int konstant;
    	//System.out.println("Getting HeatDiff");
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	temp=screenDomain/P_pitherm.size();  
    	//System.out.println(" Amount: "+P_pitherm.size()/screenDomain);
    //	System.out.println("Temp: "+temp);
    	
       	konstant=(int)P_pitherm.size()/screenDomain;
    	//System.out.println("Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<HeatDiff.size();i=i+konstant)
    	{
    		concatPiTherm.add(CoreOutArgon.get(i));
    	}
    	    	return concatPiTherm;
    	
    }
    public ArrayList<Double> getCoreOutTemperature(int screenDomain)
    {
    	double temp;
    	int konstant;
    	//System.out.println("Getting HeatDiff");
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	temp=screenDomain/P_pitherm.size();  
    	//System.out.println(" Amount: "+P_pitherm.size()/screenDomain);
    //	System.out.println("Temp: "+temp);
    	
       	konstant=(int)P_pitherm.size()/screenDomain;
    	//System.out.println("Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<HeatDiff.size();i=i+konstant)
    	{
    		concatPiTherm.add(CoreOutTemp.get(i));
    	}
    	    	return concatPiTherm;
    	
    }
    public ArrayList<Double> getCoreInTemperature(int screenDomain)
    {
    	double temp;
    	int konstant;
    	//System.out.println("Getting HeatDiff");
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	temp=screenDomain/P_pitherm.size();  
    	//System.out.println(" Amount: "+P_pitherm.size()/screenDomain);
    //	System.out.println("Temp: "+temp);
    	
       	konstant=(int)P_pitherm.size()/screenDomain;
    	//System.out.println("Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<HeatDiff.size();i=i+konstant)
    	{
    		concatPiTherm.add(CoreInTemp.get(i));
    	}
    	    	return concatPiTherm;
    	
    }
    public ArrayList<Double> getCoreReactorTemperature(int screenDomain)
    {
    	double temp;
    	int konstant;
    	//System.out.println("Getting HeatDiff");
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	temp=screenDomain/P_pitherm.size();  
    	//System.out.println(" Amount: "+P_pitherm.size()/screenDomain);
    //	System.out.println("Temp: "+temp);
    	
       	konstant=(int)P_pitherm.size()/screenDomain;
    	//System.out.println("Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<HeatDiff.size();i=i+konstant)
    	{
    		concatPiTherm.add(CoreReactorTemperature.get(i));
    	}
    	    	return concatPiTherm;
    	
    }
    public ArrayList<Double> getJacketOutTemperature(int screenDomain)
    {
    	double temp;
    	int konstant;
    	//System.out.println("Getting HeatDiff");
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	temp=screenDomain/P_pitherm.size();  
    	//System.out.println(" Amount: "+P_pitherm.size()/screenDomain);
    //	System.out.println("Temp: "+temp);
    	
       	konstant=(int)P_pitherm.size()/screenDomain;
    	//System.out.println("Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<HeatDiff.size();i=i+konstant)
    	{
    		concatPiTherm.add(JacketOutGas.get(i));
    	}
    	    	return concatPiTherm;
    	
    }
    public ArrayList<Double> getJacketInTemperature(int screenDomain)
    {
    	double temp;
    	int konstant;
    	//System.out.println("Getting HeatDiff");
    	//System.out.println("Screen Domain: "+screenDomain);
    	//System.out.println("P_pitherm Size: "+P_pitherm.size());
    	temp=screenDomain/P_pitherm.size();  
    	//System.out.println(" Amount: "+P_pitherm.size()/screenDomain);
    //	System.out.println("Temp: "+temp);
    	
       	konstant=(int)P_pitherm.size()/screenDomain;
    	//System.out.println("Konstant: "+konstant);
    	ArrayList<Double> concatPiTherm= new ArrayList<>();
    	for(int i=0;i<HeatDiff.size();i=i+konstant)
    	{
    		concatPiTherm.add(JacketInGas.get(i));
    	}
    	    	return concatPiTherm;
    	
    }
    
    public boolean getArgon()
    {
    	return  argonExists;
    }
}