import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;

public class Graph extends JPanel
{
	private static int monthsWith30Days[]={4,6,9,11};
	private static int monthsWith31Days[]={1,3,5,7,8,10,12};
	private static int monthWith1Month=2;
	
	
	
	
	
	
	//These are all for calibration
	
	
	
	private Step2and3Calc table23;  // This holds the one for the exact day
	private Step4Calculation table4;
	
	private Step0CalibrationDetect step0;
	private double electricalLoss;
	private Step1Calculation step1Calibration;
	private static Stack<Step2and3Calc> Step2and3CalcGRAPHStack;

	private boolean QPulse=true;
	private boolean CoreMS=false;
	private boolean GasTemp=false;
	private boolean ExcessPower=false;
	private boolean shrink=false;
	
	
	private static Stack<Step1Calculation> Step1LongCalcStack;
	private static Stack<Step2and3Calc> Step2and3CalcStack;
	private static Step2and3Calc simplifiedStackCalc;
	private static StatPanel statPanel;
	
	
	
	
	private JTextField dateHolder1;
	private JTextField dateHolder2;
	private JButton enter;
	private String date1;
	private String date2;
	private boolean dateEnter=false;
	private int time=0;
	private int monthBegin;
	private int monthEnd;
	private int dayBegin;
	private int dayEnd;
	private int year;
	private LENRCSVParser parse; 
	private ArrayList<LinkedList<ColoredPoint>> pointToPlot;
	private ArrayList<ColoredPoint> step1Points;
	private ArrayList<ColoredPoint> step2and3Points;
	private double RangeMax;
	private int domainMax;
	
	private static int GraphPanelWidth;
	private static int GraphPanelHeight;
	private static int xAxisMargin;
	private static int topHeightMargin;
	private static int BottomHeightMargin;
	private static int endLengthMargin;
	
	private String dateAnalyzedCurrently;
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * ||||||||||
	 * |		|
	 * |		|
	 * ||||||||||
			|
	 * 		|
	 * 		v
	 * 
	 * 
	 */
	
	
	
	/*

	   
	Height   
	____	
	  |
	  |		Top Panel Height
	____
		   ^
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |___________________________>
									  ____
	|------|   						    |
	 Length								|  Bottom Height Margin= 40;
	 Margin							  	|
	 								  ----
	|------------------------------------|
	   		Length
	   
	   
	   
	   
	 */
	
	
	
	
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		//System.out.println("Inside Paint Component");
		g.setColor(Color.RED);
		if(dateEnter==false)
		{
		g.drawString("Insert Dates for Analysis--->", 140, 20);
		//g.drawString("[1]", 400, 40);
		//g.drawString("[2]", 475, 40);
		}
		
		// This is where the graph prints
		else
		{
			if(dateEnter==true && time==0)
			{
			//System.out.println("Hello");
			boolean tester=getDates();
			if(tester==false)
				{
				dateEnter=false;
				}
			else{
			//	System.out.println("Hello 2");
				time++;
				}
			}
			else{
			System.out.println("Inside2");
			
			// This is basically clear the panel and repaint
			if(time==1)
			{
				remove(dateHolder1);
				remove(dateHolder2);
				remove(enter);
				try{
					repaint();
				dateAnalysis();
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}

			

			time++;
			}
			//g.setColor(Color.BLUE);
		//	g.drawString("In Process of Analyzing Dates", 300, 200);
			System.out.println("Date Current(graph):"+dateAnalyzedCurrently);
			g.drawString(dateAnalyzedCurrently, GraphPanelWidth/2, topHeightMargin/2);
			drawAxis(g,dateAnalyzedCurrently);
			//System.out.println("After printing ");
			}//
		}
		
	}
	public boolean getDateEnter(){
		return this.dateEnter;
	}
	public String getBeginDate()
	{
		return this.date1;
		
	}
	public String getEndDate()
	{
		return this.date2;
	}
	public Graph( int GraphPanelWidth, int GraphPanelHeight)
	{	
		this.GraphPanelWidth=GraphPanelWidth;
		this.GraphPanelHeight=GraphPanelHeight;
		this.topHeightMargin=40;
		this.BottomHeightMargin=40;
		this.xAxisMargin=30;
		this.endLengthMargin=20;
		
		Step1LongCalcStack= new Stack<>();
		Step2and3CalcStack= new Stack<>();
		Step2and3CalcGRAPHStack= new Stack<>();
		pointToPlot= new ArrayList<>();
		step2and3Points= new ArrayList<>();
		dateHolder2= new JTextField("[M'-D'-YYYY");
		dateHolder2.setColumns(7);
		dateHolder1= new JTextField("[M-D-YYYY]");
		dateHolder1.setColumns(7);
		enter= new JButton(" Enter ");
		enter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
			dateEnter=true;
			};
		});
		add(dateHolder1);
		add(dateHolder2);
		add(enter);
	}
	public Graph(LayoutManager Layout)
	{	
		super(Layout);
		Step1LongCalcStack= new Stack<>();
		Step2and3CalcStack= new Stack<>();
		pointToPlot= new ArrayList<>();
		step2and3Points= new ArrayList<>();
		dateHolder2= new JTextField("[M'-D'-YYYY");
		dateHolder2.setColumns(7);
		dateHolder1= new JTextField("[M-D-YYYY]");
		dateHolder1.setColumns(7);
		
		enter= new JButton(" Enter ");
		enter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
			dateEnter=true;
			};
		});
		add(dateHolder1);
		add(dateHolder2);
		add(enter);
	}
	public Graph(String begin, String end)
	{
		
		Step1LongCalcStack= new Stack<>();
		Step2and3CalcStack= new Stack<>();
		pointToPlot= new ArrayList<>();
		step2and3Points= new ArrayList<>();
		date1=begin;
		date2=end;
	}
	public boolean getDates()
	{
		date1=dateHolder1.getText();
		date2=dateHolder2.getText();
		boolean test=testDates(date1);
		if(test==false)
		{
		return false;	
		}
		else
		{
		test=testDates(date2);
			if(test==true){
				return true;
			}
			else{
			return false;
			}
		}
	}
	public boolean testDates(String date)
	{
		int checkNumDash=0;
		for(int i=0;i<date.length();i++)
		{
			if(date.charAt(i)=='-')
			{
				checkNumDash++;
			}
		}
		
		if(checkNumDash>2 || checkNumDash<2)
		{
			return false;
		}
		// Check if they are all numbers
		try
		{
			int temp;
			for(int i=0;i<date.length();i++)
			{
				if(date.charAt(i)=='-')
				{
					// Continue
				}
				else
				{
					temp=(int)date.charAt(i);
				}
			}
		}
		catch(	Exception e )
		{
				System.out.println("Not in date format");
				return false;
		}
			
		return true;
		
		
	}

	public void setDate(String date)
	{
		this.dateAnalyzedCurrently=date;
	}
	public double getElectricalLoss()
	{
		return electricalLoss;
	}
	public Step2and3Calc getTable23()
	{
		return table23;
	}
	public void setGraphType(boolean QPulse, boolean CoreMS,boolean gasTemps, boolean ExcessPower)
	{
		this.QPulse=QPulse;
		this.CoreMS=CoreMS;
		this.GasTemp=gasTemps;
		this.ExcessPower=ExcessPower;

		
	}

	public void dateAnalysis() throws Exception
	{
		int daysInMonth;
		boolean testAnalysis=false;
		System.out.println("Calibrating Step0");
		this.step0= new Step0CalibrationDetect(date1,date2);
		System.out.println("Step0 done Calibration");
		Step1Calculation past;
		Step1Calculation tempCalc1=null;
		System.out.println("Setting Month Day Years");
		setDayMonthYear();
		System.out.println("Past Days and Years");
		int numDays;
		int monthDiff= monthEnd-monthBegin;
		System.out.println("Month Diff: "+monthDiff);
		// If the month is 0
		if(monthDiff==0){
			System.out.println("Month Diff=0");
			testAnalysis=iterateThroughMonth(monthBegin,year,dayBegin,dayEnd,step0,null);
			System.out.println("Analysis: "+testAnalysis);
			return;
		}
		else
		{  
			
		for(int i=monthBegin;i<monthEnd+1;i++){
			System.out.println("Iterating through month");
			daysInMonth=MonthMatchWithDay(i);
			System.out.println("**Month DIFF> 1**");
			if(i==monthBegin) {
				testAnalysis=iterateThroughMonth(i,year,dayBegin,daysInMonth,step0,null);
				if(testAnalysis==false){
					System.out.println("Step 1 Not Calibrated");
					i++;
				}
				else{
					System.out.println(" Step1 Calibrated");
					past=Step1LongCalcStack.pop();
					electricalLoss=past.getMean();
					tempCalc1=(Step1Calculation) past.clone();
					Step1LongCalcStack.push(past);
					i++;
				}
			}
			//	public boolean iterateThroughMonth(int month, int year, int dayBegin, int dayEnd, Step0CalibrationDetect step0, Step1Calculation step1)

			if(i==monthEnd){
				daysInMonth=MonthMatchWithDay(i);
				System.out.println("Day End: "+dayEnd);
				if(tempCalc1==null){
					
					System.out.println("Step 1 Not Calibrated");
					testAnalysis=iterateThroughMonth(i,year,1,dayEnd,step0,null);
				}
				else{
					testAnalysis=iterateThroughMonth(i,year,1,dayEnd,step0,tempCalc1);
				}
				System.out.println(testAnalysis);
			}
			// Basically go through the other months
			else{
				if(tempCalc1==null){
					System.out.println("Step 1 NOT calibrated");
					testAnalysis=iterateThroughMonth(i,year,1,daysInMonth,step0,null);
					if(testAnalysis==false){
						System.out.println("Step 1 is not calibrated.");
						i++;
					}
					else{
						System.out.println(" Step 1 is Calibrated");
						past=Step1LongCalcStack.pop();
						tempCalc1=(Step1Calculation)past.clone();
						Step1LongCalcStack.push(past);
						i++;
					}
				}
				else{
					System.out.println("Iterating the other months");
					int LLsize=Step1LongCalcStack.size();
					testAnalysis=iterateThroughMonth(i,year,1,dayEnd,step0,tempCalc1);
					if(Step1LongCalcStack.size()>LLsize){
						System.out.println("Step 1 Reinitialized");
						past=Step1LongCalcStack.pop();
						tempCalc1=(Step1Calculation)past.clone();
						Step1LongCalcStack.push(past);
						i++;
					}
				}
				
			}
		}
		
		}
	}
		
	

	public boolean iterateThroughMonth(int month, int year, int dayBegin, int dayEnd, Step0CalibrationDetect step0, Step1Calculation step1)
	{
		String dateFile= new String();
		Step1Calculation calc1;
		Step1Calculation temp;
		LinkedList<Step1Calculation> tempLL;
		int day;
		boolean step1Found=false;
		boolean step2Found=false;
		Step2and3Calc tempTable;
		if(step0==null)
		{
			System.out.println("Foundation depends on Step0. ERROR!!!");
			return false;
			//throws Exception
		}
		if (step1==null)
		{
			calc1=cycleThroughMonthForStep1(step0,month,year,dayBegin,dayEnd);
			System.out.println("Calc 1: "+calc1.getMean());
			if(calc1==null)
			{
				return false;
			}
			//System.out.println("Getting DAY");
			day=calc1.getDay();
			System.out.println("Day :"+day);
			
			for(int i=dayBegin;i<dayEnd+1;i++)
			{
				System.out.println("Using electrical loss :"+calc1.getMean());
				dateFile="data/"+month+"-"+i+"-"+year+".csv";
				System.out.println("Date File: "+dateFile);

				parse=new LENRCSVParser(dateFile,step0);
				step1Found=parse.detectStep1Intervals();
				if(step1Found==true && parse.argonExists==false){
					// Reiniitialize
					System.out.println("Reinitializing");
					parse.calculateStep1Intervals();
					tempLL=parse.getStep1LL();
					for(int k=0;k<tempLL.size();k++)
					{
						temp=tempLL.get(k);
						System.out.println("Pushing Step1 Stack");
						Step1LongCalcStack.push(temp);
					}
					
					System.out.println("Reinitializing");
					calc1=tempLL.get(tempLL.size()-1);
				}
				step2Found=parse.detectStep2Intervals();
				if(step2Found==true)
				{
					parse.calculateStep2and3Intervals(calc1);
					tempTable=parse.getTableCalc();
					table23=tempTable;
					Step2and3CalcStack.push(tempTable);
				}
				
			}
			
		}
		else
		{
			day=step1.getDay();
			
			System.out.println("Day (of the previous month) :"+day);
			System.out.println("Day End: "+dayEnd);
			System.out.println("Day Begin: "+dayBegin);
			for(int i=dayBegin;i<dayEnd+1;i++)
			{
				System.out.println("Using electrical loss :"+step1.getMean()+ " of day: "+day);
				dateFile="data/"+month+"-"+i+"-"+year+".csv";
				System.out.println(" Opening file: "+dateFile);
				parse=new LENRCSVParser(dateFile,step0);
				step1Found=parse.detectStep1Intervals();
				if(step1Found==true){
					// Reiniitialize
					parse.calculateStep1Intervals();
					tempLL=parse.getStep1LL();
					for(int k=0;k<tempLL.size();k++)
					{
						temp=tempLL.get(k);
						System.out.println("Pushing Step1 Stack");
						Step1LongCalcStack.push(temp);
					}
					
					System.out.println("Reinitializing");
					calc1=tempLL.get(tempLL.size()-1);
				}
				step2Found=parse.detectStep2Intervals();
				if(step2Found==true)
				{
					parse.calculateStep2and3Intervals(step1);
					tempTable=parse.getTableCalc();
					Step2and3CalcStack.push(tempTable);
				}
				
		}
			}
		return true;
		
	}
	// How do you get the day?
	public Step1Calculation cycleThroughMonthForStep1(Step0CalibrationDetect step0, int month, int year, int dayBegin, int dayEnd)
	{
		Step1Calculation temp;
		boolean step1Found=false;
		String dateFile;
		LinkedList<Step1Calculation> tempLL;
		for(int i=dayBegin;i<dayEnd+1;i++)
		{
		dateFile="data/"+month+"-"+i+"-"+year+".csv";	
		System.out.println("Opening file "+dateFile);
		parse= new LENRCSVParser(dateFile,step0);
		step1Found=parse.detectStep1Intervals();
		if(step1Found==true)
		{
			parse.calculateStep1Intervals();
			tempLL=parse.getStep1LL();
			for(int k=0;k<tempLL.size();k++)
			{
				temp=tempLL.get(k);
				System.out.println("Pushing to Step1 Stack");
				Step1LongCalcStack.push(temp);
			}
			System.out.println("Step1 TRUE found");
			return tempLL.get(tempLL.size()-1);
		}
		
		}
		System.out.println("Step1 not found.");
		return null;
	}
	

	public void printOutDataAnalysis()
	{
		
		Stack<Step1Calculation> tempStack1;
		Step1Calculation tempHold;
		System.out.println("Step1 LL");
		System.out.println("SIZE: "+Step1LongCalcStack.size());
		while(Step1LongCalcStack.isEmpty()==false)
		{	tempHold=Step1LongCalcStack.pop();
			System.out.println(tempHold.getDate()+" : "+tempHold.getMean() );
			
		}
	}
	
	public void drawAxis(Graphics g, String date)
	{
		Point origin= new Point(xAxisMargin,GraphPanelHeight-BottomHeightMargin);
		Point yTopAxis= new Point(xAxisMargin,topHeightMargin);
		Point xBottomAxis= new Point(GraphPanelWidth-endLengthMargin,GraphPanelHeight-BottomHeightMargin);
		System.out.println("*************************");
		System.out.println("Draw AXIS");
		g.setColor(Color.WHITE);
		g.drawLine(origin.getX(),origin.getY(),yTopAxis.getX(),yTopAxis.getY());
		g.drawLine(origin.getX(),origin.getY(),xBottomAxis.getX(),xBottomAxis.getY());
		String fileName= "data/"+date+".csv";
		initializeColoredPoints(fileName,QPulse, ExcessPower, CoreMS, GasTemp,origin, xBottomAxis,null);
		/*
		 * 
	private boolean QPulse;
	private boolean CoreMS;
	private boolean GasTemp;
	private boolean ExcessPower;
		 */
		//	public void initializeColoredPoints(String date,boolean HeaterQPulse, boolean ExcessPower, boolean CoreMS, boolean gasTemps, Point origin, Point xBottomAxis, Step1Calculation past)

		drawPoints(g,origin,yTopAxis,xBottomAxis);
		drawScale(g,origin,xBottomAxis,yTopAxis);
		if(table4==null  || table4.getSize()==0)
		{
		statPanel.updateInformation(electricalLoss, table23,null,null);
		}
		else
		{
			statPanel.updateInformation(electricalLoss, table23,table4,simplifiedStackCalc);
		}
		System.out.println("************************");
		statPanel.repaint();

	}

	
	public void drawScale(Graphics g, Point origin, Point xBottomAxis, Point yTopAxis)
	{
		/*
		 *
		 * private double RangeMax;
	private int domainMax;
		 */
		//System.out.println("Scale");
		g.setColor(Color.GRAY);
		Point begin1;
		Point end1;
		String yLabelNum;
		double scaleNum= (RangeMax/10);
		double holder;
        DecimalFormat df = new DecimalFormat("#.##");
        scaleNum=Double.parseDouble(df.format(scaleNum));
        // These are the horizontal lines
        
        
        /*
         * 		*--BEGIN
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |
	|	   |___________________________>	|-----|
									  ____	 End LengthMargin
         */
        
        //VERTICAL LINES
		for(int i=0;i<10;i++)
		{
			yLabelNum= new String();
			 begin1= new Point(origin.getX(),yTopAxis.getY()+i*(origin.getY()-yTopAxis.getY())/10);
			/* System.out.println("Y Top Axis: "+yTopAxis.getY());
			 System.out.println("Begin X: "+begin1.getX()+"  Begin Y: "+begin1.getY());
			 System.out.println("Range MAX: "+RangeMax);
			 System.out.println("Scale Num: "+scaleNum);
			 */
			 //yLabelNum+
			 holder=RangeMax-i*scaleNum;
			 holder=Double.parseDouble(df.format(holder));
			// System.out.println("Holder"+holder);
			 yLabelNum+=holder;
			// System.out.println("Y Label: "+yLabelNum);
			 end1= new Point(xBottomAxis.getX(),yTopAxis.getY()+i*(origin.getY()-yTopAxis.getY())/10);
			 g.drawLine(begin1.getX(), begin1.getY(),end1.getX()+100,end1.getY());
			 g.drawString(yLabelNum, begin1.getX(), begin1.getY());
			
		}
		System.out.println("Domain "+domainMax);
		Point begin2;
		Point end2;
		String y;
		String z;
		for(int i=0;i<10;i++)
		{	
			y= new String();
			z= new String();
			begin2= new Point(origin.getX()+100*i,origin.getY());
			end2= new Point(origin.getX()+100*i, yTopAxis.getY());
			y+=100*i;
			z+=100*i;
			 g.drawLine(begin2.getX(), begin2.getY(),end2.getX(),end2.getY());
			g.drawString(z, begin2.getX(), begin2.getY());
		}
		// Drawing the x axis
		//for(int j=0;j<10;j++)
		//{
			
		//}
		
		
	}
	
	public void drawPoints(Graphics g, Point origin, Point yTopAxis, Point xBottomAxis)
	{
		
		int pointX;
		int pointY;
		double pX;
		double pY;
		double scaleMX;
		double scaleMY;
		double xBottomTemp;
		double domainTemp;
		
		// 50 is your top offset
		
		for(int i=0;i<pointToPlot.size();i++) //Iterate through the ArrayList
		{
			domainTemp=domainMax;
			xBottomTemp=domainMax;
			scaleMX=xBottomTemp/domainTemp;
			scaleMY=(origin.getY()-yTopAxis.getY())/RangeMax;
			
			for(int j=0;j<pointToPlot.get(i).size();j++)// This iterates through the LL
			{
				g.setColor(pointToPlot.get(i).get(j).getColor()); //This sets the color

				pX=origin.getX()+scaleMX*pointToPlot.get(i).get(j).getX();
				pY=xBottomAxis.getY() - scaleMY*pointToPlot.get(i).get(j).getY();
				pointX=(int)pX;
				pointY=(int)pY;
				/*System.out.println("Scale MX:"+scaleMX);
				System.out.println("Y Top Axis: "+yTopAxis.getY());
				System.out.println("X AXIS Length: "+xBottomTemp);
				System.out.println("Domain MAX: "+domainTemp);
				System.out.println("Division: "+xBottomTemp/domainTemp);
				System.out.println("Scale MY:"+scaleMY);
				System.out.println("Point X: "+pointX);
				System.out.println("Point Y: "+pointY);
				System.out.println("Data Point: "+pointToPlot.get(i).get(j).getY());
				*/
				g.fillOval(pointX,pointY,3,3);
				//System.out.println("Drawing");
				
			}
		}
		
		g.setColor(Color.MAGENTA);
		System.out.println("Iterating through STEP1 Point");
		for(int j=0;j<step1Points.size();j++)
		{
		//	System.out.println("X"+step1Points.get(j).getX()+ "  Y"+step1Points.get(j).getY());
			g.fillOval((int)step1Points.get(j).getX(),(int)step1Points.get(j).getY() , 3, 3);
		}
		g.setColor(Color.RED);
		for(int k=0;k<step2and3Points.size();k++)
		{
			g.fillOval((int)step2and3Points.get(k).getX(),(int)step2and3Points.get(k).getY()+20 , 3, 3);
		}
		
	}
	
	
	public StatPanel getStatPanel(int StatisticPanelWidth,int StatisticPanelHeight )
	{
		statPanel=new StatPanel(StatisticPanelWidth,StatisticPanelHeight);
		return this.statPanel;
	}
	
	

	public void initializeColoredPoints(String date,boolean HeaterQPulse, boolean ExcessPower, boolean CoreMS, boolean gasTemps, Point origin, Point xBottomAxis, Step1Calculation past)
	{
		LENRCSVParser parse= new LENRCSVParser(date,step0);
		this.table4=null;
		if(step1Calibration!=null)
			
		{
		if(parse.detectStep4Intervals()==true)
		{
			System.out.println("Step 4 works");
			shrinkStack();
			parse.calculateStep4Intervals(simplifiedStackCalc);
			this.table4=parse.getStep4Table();
			// Do a calculation by passing through the number
		}	
		else
		{
			System.out.println(" Step 4 is not here");
		}
		}
		if(pointToPlot.size()>0)
		{
			pointToPlot.clear();
			step1Points.clear();
			step2and3Points.clear();
		}
				
		if(HeaterQPulse==true)
		{
			InitializeHeatDiffandQPiPoints(parse);	
		}
		if(CoreMS==true)
		{
			InitializeCoreMS( parse);
		}
		if(gasTemps)
		{
			InitializeGasTemp(parse);
		}
		if(ExcessPower)
		{
			System.out.println();
			if(parse.getStep4Table()!=null)
			{
			initializeExcessPower(parse);
			}
			System.out.println();

		}
		
		
		ArrayList<Double> step1List;
		ColoredPoint step1Temp;
		//System.out.println("Adding STEP1 Intervals");
		LinkedList<Step1Calculation> temp1LL;
		if(parse.detectStep1Intervals()==true)
		{
			System.out.println("Step1 is TRUE");
			parse.calculateStep1Intervals();
			step1List=parse.getStep1Points(850);	//This collects all the points of step1
			temp1LL=parse.getStep1LL();
			step1Calibration=temp1LL.get(temp1LL.size()-1); //Here you reinitialize the points
			if(step1List.size()>0)
			{
				System.out.println("Size is :"+step1List.size());
				for(int i=0;i<step1List.size();i++)
				{
					System.out.println("Adding Step1: "+i);
					System.out.println(" X Coordinate: "+step1List.get(i));
					//This basically initializes the bar
					step1Temp= new ColoredPoint(Color.ORANGE, origin.getX()+step1List.get(i),origin.getY(), "Index", "Watt");	
					step1Points.add(step1Temp);
				}
			}
			else
			{
				System.out.println("The elements are null");	
			}
		}
		//RangeMax=(int)tempRangeMax;
		//parse.printPIThermLL();
		//parse.printPThermH2O();
		//parse.printH2O();
		boolean test=parse.detectStep1Intervals();
		//parse.calculateStep1();
		System.out.println("Detect Step 1 :"+test);
		parse.printStep0();
		if(parse.detectStep2Intervals()==true)
		{
		initializeStep2Points(parse,step1Calibration,origin);
		}
		else{
			table23=null;
		}
		
		
	}
	
	
	public void InitializeHeatDiffandQPiPoints(LENRCSVParser parse)
	{
		ArrayList<Double> heat;
		ArrayList<Double> piTherm;
		double tempRangeMax=0;
		heat=parse.getHeatDiff(850);
		piTherm=parse.getPiTherm(850);
		Color heatColor= Color.GREEN;
		Color piThermColor= Color.BLUE;
		step1Points= new ArrayList<>();
		ColoredPoint heatTemp;
		ColoredPoint piThermTemp;
		
		LinkedList<ColoredPoint> heatColoredPoint= new LinkedList<>();
		LinkedList<ColoredPoint> piThermColoredPoint= new LinkedList<>();
		//	ColoredPoint(Color color, int xComp, int yComp, String xLabel, String yLabel)
		domainMax=piTherm.size();
		System.out.println("Domain MAX: "+domainMax);
		if(heat.get(0)>piTherm.get(0))
		{
			tempRangeMax=heat.get(0);
		}
		for(int i=0;i<heat.size();i++)
		{
					if( heat.get(i)> tempRangeMax)
					{
						tempRangeMax=heat.get(i);
					}
					if( piTherm.get(i)> tempRangeMax)
					{
						tempRangeMax=piTherm.get(i);
					}
			heatTemp= new ColoredPoint(heatColor, i, heat.get(i), "Index", "Watt");
			piThermTemp= new ColoredPoint(piThermColor, i, piTherm.get(i), "Index", "Watt");
		
			heatColoredPoint.add(heatTemp);
			//System.out.println("Adding to LL: Green");
			piThermColoredPoint.add(piThermTemp);
			//System.out.println("Adding to LL: Blue");
		
		}
		//System.out.println("Heat Colored Point Size: "+heatColoredPoint.size());
		//System.out.println("Pi Therm Size: "+piThermColoredPoint.size());
		RangeMax=(int)tempRangeMax;
		System.out.println("Range Max: "+RangeMax);
		pointToPlot.add(heatColoredPoint);
		pointToPlot.add(piThermColoredPoint);

	}
	public void InitializeCoreMS(LENRCSVParser parse)
	{
		ArrayList<Double> hydrogen;
		ArrayList<Double> argon;
		ArrayList<Double> helium;
		double tempRangeMax=0;
		hydrogen=parse.getHydrogenCoreOutGas(GraphPanelWidth);
		helium=parse.getHeliumCoreGas(GraphPanelWidth);
		argon=parse.getArgonCoreOutGas(GraphPanelWidth);
		Color hydrogenColor= Color.RED;
		Color heliumColor= Color.BLUE;
		Color argonColor=Color.YELLOW;
		
		step1Points= new ArrayList<>();
		ColoredPoint hydrogenTemp;
		ColoredPoint argonTemp;
		ColoredPoint heliumTemp;
		
		LinkedList<ColoredPoint> hydrogenColoredPoint= new LinkedList<>();
		LinkedList<ColoredPoint> argonColoredPoint= new LinkedList<>();
		LinkedList<ColoredPoint> heliumColoredPoint= new LinkedList<>();
		//	ColoredPoint(Color color, int xComp, int yComp, String xLabel, String yLabel)
		domainMax=hydrogen.size();
		System.out.println("Domain MAX: "+domainMax);
		if(hydrogen.get(0)>helium.get(0) && hydrogen.get(0)>argon.get(0))
		{
			tempRangeMax=hydrogen.get(0);
		}
		for(int i=0;i<hydrogen.size();i++)
		{
					if( hydrogen.get(i)> tempRangeMax)
					{
						tempRangeMax=hydrogen.get(i);
					}
					if( helium.get(i)> tempRangeMax)
					{
						tempRangeMax=helium.get(i);
					}
					if( argon.get(i)> tempRangeMax)
					{
						tempRangeMax=argon.get(i);
					}
					hydrogenTemp= new ColoredPoint(hydrogenColor, i, hydrogen.get(i), "Index", "Watt");
					argonTemp= new ColoredPoint(argonColor, i, argon.get(i), "Index", "Watt");
					heliumTemp= new ColoredPoint(heliumColor,i,helium.get(i),"Index","Watt");
					
			
		
					hydrogenColoredPoint.add(hydrogenTemp);
					argonColoredPoint.add(argonTemp);
					heliumColoredPoint.add(heliumTemp);
			//System.out.println("Adding to LL: Blue");
		
		}
		//System.out.println("Heat Colored Point Size: "+heatColoredPoint.size());
		//System.out.println("Pi Therm Size: "+piThermColoredPoint.size());
		RangeMax=(int)tempRangeMax;
		System.out.println("Range Max: "+RangeMax);
		pointToPlot.add(hydrogenColoredPoint);
		pointToPlot.add(argonColoredPoint);
		pointToPlot.add(heliumColoredPoint);
	}
	
	public void InitializeGasTemp(LENRCSVParser parse)
	{
		ArrayList<Double> CoreInTemp;
		ArrayList<Double> CoreOutTemp;
		ArrayList<Double> CoreReactorTemp;
		ArrayList<Double> JacketOutTemp;
		ArrayList<Double> JacketInTemp;

		double tempRangeMax=0;
		CoreInTemp=parse.getCoreInTemperature(GraphPanelWidth);
		CoreOutTemp=parse.getCoreOutTemperature(GraphPanelWidth);
		CoreReactorTemp=parse.getCoreReactorTemperature(GraphPanelWidth);
		JacketOutTemp=parse.getJacketOutTemperature(GraphPanelWidth);
		JacketInTemp=parse.getJacketInTemperature(GraphPanelWidth);
		
		Color CoreInColor= Color.YELLOW;
		Color CoreOutColor= Color.RED;
		Color CoreReactorColor=Color.ORANGE;
		Color JacketInColor= Color.MAGENTA;
		Color JacketOutColor= Color.BLUE;
		Color piThermColor= Color.BLUE;
		
		ColoredPoint CoreInTempHold;
		ColoredPoint CoreOutTempHold;
		ColoredPoint CoreReactorTempHold;
		ColoredPoint JacketInTempHold;
		ColoredPoint JacketOutTempHold;

		
		LinkedList<ColoredPoint> CoreInTemperature= new LinkedList<>();
		LinkedList<ColoredPoint> CoreOutTemperature= new LinkedList<>();
		LinkedList<ColoredPoint> CoreReactorTemperature= new LinkedList<>();
		LinkedList<ColoredPoint> JacketInTemperature= new LinkedList<>();
		LinkedList<ColoredPoint> JacketOutTemperature= new LinkedList<>();


		LinkedList<ColoredPoint> piThermColoredPoint= new LinkedList<>();
		//	ColoredPoint(Color color, int xComp, int yComp, String xLabel, String yLabel)
		domainMax=CoreInTemp.size();
		System.out.println("Domain MAX: "+domainMax);
		if(CoreInTemp.get(0)>CoreReactorTemp.get(0))
		{
			tempRangeMax=CoreInTemp.get(0);
		}
		for(int i=0;i<CoreInTemp.size();i++)
		{
					if( CoreInTemp.get(i)> tempRangeMax)
					{
						tempRangeMax=CoreInTemp.get(i);
					}
					if( CoreOutTemp.get(i)> tempRangeMax)
					{
						tempRangeMax=CoreOutTemp.get(i);
					}
					if( CoreReactorTemp.get(i)> tempRangeMax)
					{
						tempRangeMax=CoreReactorTemp.get(i);
					}
					if( JacketOutTemp.get(i)> tempRangeMax)
					{
						tempRangeMax=JacketOutTemp.get(i);
					}
					if( JacketInTemp.get(i)> tempRangeMax)
					{
						tempRangeMax=JacketInTemp.get(i);
					}
					CoreInTempHold= new ColoredPoint(CoreInColor, i,CoreInTemp.get(i), "Index", "Degree");
					CoreOutTempHold= new ColoredPoint(CoreOutColor, i,CoreOutTemp.get(i), "Index", "Degree");
					CoreReactorTempHold= new ColoredPoint(CoreReactorColor, i,CoreReactorTemp.get(i), "Index", "Degree");
					JacketInTempHold= new ColoredPoint(JacketInColor, i,JacketInTemp.get(i), "Index", "Degree");
					JacketOutTempHold= new ColoredPoint(JacketOutColor, i,JacketOutTemp.get(i), "Index", "Degree");

					CoreInTemperature.add(CoreInTempHold);
					CoreOutTemperature.add(CoreOutTempHold);
					CoreReactorTemperature.add(CoreReactorTempHold);
					JacketInTemperature.add(JacketInTempHold);
					JacketOutTemperature.add(JacketOutTempHold);
					

					
				//	piThermTemp= new ColoredPoint(piThermColor, i, piTherm.get(i), "Index", "Degree");
			//heatColoredPoint.add(heatTemp);
			//System.out.println("Adding to LL: Green");
			//piThermColoredPoint.add(piThermTemp);
			//System.out.println("Adding to LL: Blue");
		
		}
		//System.out.println("Heat Colored Point Size: "+heatColoredPoint.size());
		//System.out.println("Pi Therm Size: "+piThermColoredPoint.size());
		RangeMax=(int)tempRangeMax;
		System.out.println("Range Max: "+RangeMax);
		pointToPlot.add(CoreInTemperature);
		pointToPlot.add(CoreOutTemperature);
		pointToPlot.add(CoreReactorTemperature);
		pointToPlot.add(JacketInTemperature);
		pointToPlot.add(JacketOutTemperature);



	}
	
	public void initializeExcessPower(LENRCSVParser parse)
	{
		LinkedList<ColoredPoint> ExcessPowList=	parse.getStep4Points(GraphPanelWidth);
		System.out.println(" Size of EXCESS POW LIST: "+ExcessPowList.size());
		double TempRangeMax=0;
		for(int j=0;j<ExcessPowList.size();j++)
		{
			if(ExcessPowList.get(j).getY()!=0)
			{
				//System.out.println(ExcessPowList.get(j).getX()+"  "+ExcessPowList.get(j).getY());
			}
			if(TempRangeMax<ExcessPowList.get(j).getY())
			{
				TempRangeMax=ExcessPowList.get(j).getY();
			}
			if(ExcessPowList.get(j).getColor().equals(Color.RED))
			{
			  System.out.println(" RED :"+"  "+ExcessPowList.get(j).getY());	
			}
		}
		System.out.println(" Range MAX: "+TempRangeMax);
		
		RangeMax=TempRangeMax;
		System.out.println("Testing Table Print");
		if(table4!=null)
		{
		for(int i=0;i<table4.getSize();i++)
		{
			System.out.println(table4.get(i).toString());
		}
		pointToPlot.add(ExcessPowList);
		}
	}
	
	
	
	public void initializeStep2Points(LENRCSVParser parse, Step1Calculation past, Point origin)
	{
		if(past==null)
		{
			return;
		}
		
		
		LinkedList<Integer> pulseRange=	parse.getPulseRange();
		
		
		System.out.println("Inside Initialized");
		parse.calculateStep2and3Intervals(past);
		System.out.println(" Outside Initialized");
		table23=parse.getTableCalc();
		Step2and3CalcGRAPHStack.push(table23);
		
		this.electricalLoss=past.getMean();
		ArrayList<Double> tempS;
		ColoredPoint temp;
		LinkedList<ColoredPoint> step2LLtemp= new LinkedList<>();
		int tempPulse;
		for(int i=0;i<pulseRange.size();i++)
		{
			tempPulse=pulseRange.get(i);
			tempS=parse.getStep2and3Points( tempPulse, 850);
			if(tempS==null)
			{
				return;
			}
			System.out.println("Pulse #"+i+" "+tempPulse);
			for(int j=0;j<tempS.size();j++)
			{
				//step1Temp= new ColoredPoint(Color.ORANGE, origin.getX()+step1List.get(i),origin.getY(), "Index", "Watt");	
			
				temp= new ColoredPoint(Color.RED, origin.getX()+tempS.get(j),origin.getY(),"Index","Watt");
				step2and3Points.add(temp);
				//step2LLtemp.add(temp);
			}
			
		}
//		step2and3Points.addAll(step2LLtemp);
		
	}
	
	public void setDayMonthYear(){
		String one[]=date1.split("-");
		monthBegin=Integer.parseInt(one[0]);
		dayBegin=Integer.parseInt(one[1]);
		year=Integer.parseInt(one[2]);
		
		String two[]=date2.split("-");
		monthEnd=Integer.parseInt(two[0]);
		dayEnd=Integer.parseInt(two[1]);
		
			}
	
	private int MonthMatchWithDay(int month)
	{
		if(month==2)
		{
			return 28;
		}
		for(int i=0;i<monthsWith30Days.length;i++)
		{
			if(month==monthsWith30Days[i])
			{
				return 30;
			}
		}
		for(int i=0;i<monthsWith31Days.length;i++)
		{
			if(month==monthsWith31Days[i]){
				return 31;
			}
		}
		System.out.println("Error");
		return -1;
	}

	// I want to change the return of this message
	public void shrinkStack()
	{
		if(shrink==false)
		{
		Stack<Step2and3Calc> stackCopy=	(Stack<Step2and3Calc>)Step2and3CalcStack.clone();
		Step2and3Calc temp=stackCopy.pop();  // This takes the first element as the base
		Step2and3Calc holder;
		//System.out.println("Temp: ");
		//temp.printTable23();
		System.out.println(" Stack Size is "+stackCopy.size());
		int counter=0;
		while(!stackCopy.isEmpty())
		{
			
			holder=stackCopy.pop();
			System.out.println("Size: "+stackCopy.size());
	
			//System.out.println("Holder is: ");
			//holder.printTable23();
			System.out.println(" Counter: "+"("+counter+")");
			temp=compressTwoStep23Objects(temp,holder);
			counter++;
		}
		System.out.println(" Done Shrinking ");
		temp.printTable23();
		simplifiedStackCalc=temp;
		shrink=true;
		}
		
		
		
		
		
	}
	
	
	public Step2and3Calc compressTwoStep23Objects(Step2and3Calc obj1, Step2and3Calc obj2)
	{
		Step2and3Calc holder= new Step2and3Calc(obj1.getLOther());
		ArrayList<LinkedList<S_23>> replacementTable= new ArrayList<>();
		// To make sure, I want to compress all of obj1 repeating lists
		System.out.println(" Before Compression Table 1");
		obj1.printTable23();
		obj1.compressRepeatingLL();
		System.out.println(" After Compression Table 1:");
		obj1.printTable23();
		//System.out.println(" Before Compression Table 2:");
		//obj2.printTable23();
		S_23 tempVariable;
		boolean found=false;
		for(int i=0;i<obj1.getS23Table().size();i++)
		{
			found=false;
			for(int j=0;j<obj2.getS23Table().size();j++)
			{ 
				// This function works if there is a match
				/*System.out.println(" Temperature 1: "+obj1.getS23Table().get(i).get(0).getTemperature()+ 
						" Temperature 2: "+obj2.getS23Table().get(j).get(0).getTemperature() +"  "
						+ "Pulse Param 1: "+obj1.getS23Table().get(i).get(0).getPulseParam()+
						"  Pulse Param 2: " +  obj2.getS23Table().get(j).get(0).getPulseParam()); */
				//System.out.println(" Index 1: "+i+ " Index 2: "+j);
				//System.out.println(" Obj 1 Size "+obj1.getS23Table().size()+" Obj 2 Size :"+obj2.getS23Table().size());
				if(obj1.getS23Table().get(i).get(0).getTemperature()==obj2.getS23Table().get(j).get(0).getTemperature() && obj1.getS23Table().get(i).get(0).getPulseParam()==obj2.getS23Table().get(j).get(0).getPulseParam())
				{
					found=true;
					tempVariable=compressMatchingTableWithIndices(obj1,obj2,i,j);
					LinkedList<S_23> TempLL23= new LinkedList<>();
					TempLL23.add(tempVariable);
					replacementTable.add(TempLL23);
					System.out.println(" Match and True");
					break;
				}
			}
			if(found==false)
			{
			//This means that the index and value in 1 is not found in 2
			found=true;
			tempVariable=compressS23inTableIndex(obj1,i);
			LinkedList<S_23> TempLL23= new LinkedList<>();
			TempLL23.add(tempVariable);
			System.out.println(" Index is in 1 but not in 2");
			replacementTable.add(TempLL23);
			}
			
		}
		//This means that the index and value in 2 is not found in 1
        LinkedList<PulseParamTemperature> range1= getLLofPulseParamAndTemperature(obj1);
        LinkedList<PulseParamTemperature> range2= getLLofPulseParamAndTemperature(obj2);
        
        boolean checkElem2in1=false;
        for(int i=0;i<range2.size();i++)
        {
        	PulseParamTemperature temporaryPulseTemp=range2.get(i);
        	checkElem2in1=range1.contains(temporaryPulseTemp);
        	if(checkElem2in1==false)
        	{
        		// Basically you find the index of the temperature and param, compress the LL and return that single object
        		int index2=obj2.getIndexFromPulseParamAndTemperature(temporaryPulseTemp.getPulseParam(), temporaryPulseTemp.getTemperature());
        		if(index2>0) // No error
        		{
        			tempVariable=compressS23inTableIndex(obj2, index2);
        			LinkedList<S_23> TempLL23= new LinkedList<>();
        			TempLL23.add(tempVariable);
        			replacementTable.add(TempLL23);        			
        		}
        	}
        }
        holder.addTable(replacementTable);
        //System.out.println("Testing Compression");
      //  holder.compressRepeatingLL();
        //holder.printTable23();
        //System.out.println("Done testing compresssion");
        return holder;
	}
	
	public LinkedList<PulseParamTemperature> getLLofPulseParamAndTemperature(Step2and3Calc obj)
	{
		ArrayList<LinkedList<S_23>> tempList=obj.getS23Table();
		LinkedList<PulseParamTemperature> listToReturn= new LinkedList<>();
		int tempTemperature;
		double tempPulseParam;
		PulseParamTemperature tempObj;
		for(int i=0;i<tempList.size();i++)
		{
			tempTemperature=tempList.get(i).get(0).getTemperature();
			tempPulseParam=tempList.get(i).get(0).getPulseParam();
			tempObj= new PulseParamTemperature(tempPulseParam, tempTemperature);
			if(listToReturn.contains(tempObj)==false)
			{
			listToReturn.add(tempObj);
			}
		}
		return listToReturn;
	}
	
	public S_23 compressS23inTableIndex(Step2and3Calc obj1, int index1)
	{
		double avgPulse1=0;
		double avgJacket1=0;
		double maxJacket=-1;
		double minJacket=100000;
		double maxPulse=-1;
		double minPulse=1000000;
		int numCompress=0;
		System.out.println();
		numCompress=obj1.getS23Table().get(index1).get(0).getNumTimesCompressed();
		for(int k=0;k<obj1.getS23Table().get(index1).size();k++)
		{
			numCompress++;
			avgPulse1+=obj1.getS23Table().get(index1).get(k).getMeanPulse();
			avgJacket1+=obj1.getS23Table().get(index1).get(k).getMeanJacket();
			//Comparing Jackets
			if(obj1.getS23Table().get(index1).get(k).getMeanJacket()>maxJacket)
			{
				maxJacket=obj1.getS23Table().get(index1).get(k).getMeanJacket();
			}
			if(obj1.getS23Table().get(index1).get(k).getMeanJacket()<minJacket)
			{
				minJacket=obj1.getS23Table().get(index1).get(k).getMeanJacket();
			}
			//Comparing Pulse
			if(obj1.getS23Table().get(index1).get(k).getMeanPulse()<minPulse && obj1.getS23Table().get(index1).get(k).getMeanPulse()>0)
			{
				minPulse=obj1.getS23Table().get(index1).get(k).getMeanPulse();
			}
			if(obj1.getS23Table().get(index1).get(k).getMeanPulse()>maxPulse)
			{
				maxPulse=obj1.getS23Table().get(index1).get(k).getMeanPulse();
				System.out.println(" MAX Index: "+obj1.getS23Table().get(index1).get(k).getIndex()+ " MAX PULSE: "+maxPulse);
				System.out.println(" MAX MAX INDEX: "+obj1.getS23Table().get(index1).get(k).getIndex()+ " MAX MAX PULSE "+obj1.getS23Table().get(index1).get(k).getMaxPulse());
			}
			if(obj1.getS23Table().get(index1).get(k).getMinPulse()<minPulse && obj1.getS23Table().get(index1).get(k).getMinPulse()>0)
			{
				minPulse=obj1.getS23Table().get(index1).get(k).getMinPulse();
			}
			if(obj1.getS23Table().get(index1).get(k).getMaxPulse()>maxPulse)
			{
				maxPulse=obj1.getS23Table().get(index1).get(k).getMaxPulse();
				System.out.println(" MAX Index: "+ obj1.getS23Table().get(index1).get(k).getIndex()+" MEAN PULSE: "+obj1.getS23Table().get(index1).get(k).getMeanPulse());
				System.out.println(" MAX Index: "+obj1.getS23Table().get(index1).get(k).getIndex()+ " MAX PULSE: "+maxPulse);

			}
			
	   }
		avgPulse1=avgPulse1/obj1.getS23Table().get(index1).size();
		avgJacket1=avgJacket1/obj1.getS23Table().get(index1).size();
		System.out.println(" Temperature: "+obj1.getS23Table().get(index1).get(0).getTemperature());
		System.out.println(" Pulse Parameter: "+obj1.getS23Table().get(index1).get(0).getPulseParam());
		System.out.println(" Average Jacket : "+avgJacket1);
		System.out.println(" Average Pulse : "+avgPulse1);
		System.out.println(" Num Times Compressed :"+numCompress);
		S_23 replacement= new S_23(minPulse, maxPulse, minJacket, maxJacket,obj1.getS23Table().get(index1).get(0).getTemperature(),
				avgJacket1,avgPulse1,obj1.getS23Table().get(index1).get(0).getPulseParam() ,true );
		replacement.setNumTimesCompressed(numCompress);
		return replacement;

	}
	
	public S_23 compressMatchingTableWithIndices(Step2and3Calc obj1, Step2and3Calc obj2, int index1, int index2)
	{
		System.out.println("Match!");
		double avgPulse1=0;
		double avgPulse2=0;
		double avgJacket1=0;
		double avgJacket2=0;
		double maxJacket=-1;
		double minJacket=100000;
		double maxPulse=-1;
		double minPulse=1000000;
		int numCompress=0;
		for(int k=0;k<obj1.getS23Table().get(index1).size();k++)
		{
			//To be honest This should only go once
			//System.out.println(" TESTING. This should only go over once.");   This statement is correct
			numCompress=obj1.getS23Table().get(index1).get(0).getNumTimesCompressed();
			avgPulse1+=obj1.getS23Table().get(index1).get(k).getMeanPulse();
			avgJacket1+=obj1.getS23Table().get(index1).get(k).getMeanJacket();
			//Comparing Jackets
			if(obj1.getS23Table().get(index1).get(k).getMeanJacket()>maxJacket)
			{
				maxJacket=obj1.getS23Table().get(index1).get(k).getMeanJacket();
			}
			if(obj1.getS23Table().get(index1).get(k).getMeanJacket()<minJacket)
			{
				minJacket=obj1.getS23Table().get(index1).get(k).getMeanJacket();
			}
			//Comparing Pulse
			if(obj1.getS23Table().get(index1).get(k).getMeanPulse()<minPulse &&obj1.getS23Table().get(index1).get(k).getMeanPulse()>0)
			{
				minPulse=obj1.getS23Table().get(index1).get(k).getMeanPulse();
			}
			if(obj1.getS23Table().get(index1).get(k).getMeanPulse()>maxPulse && obj1.getS23Table().get(index1).get(k).getMeanPulse()>0)
			{
				maxPulse=obj1.getS23Table().get(index1).get(k).getMeanPulse();
			}
			if(obj1.getS23Table().get(index1).get(k).getMinPulse()<minPulse && obj1.getS23Table().get(index1).get(k).getMinPulse()>0)
			{
				minPulse=obj1.getS23Table().get(index1).get(k).getMinPulse();
			}
			if(obj1.getS23Table().get(index1).get(k).getMaxPulse()>maxPulse&& obj1.getS23Table().get(index1).get(k).getMaxPulse()>0)
			{
				maxPulse=obj1.getS23Table().get(index1).get(k).getMaxPulse();
			}
			
	   }
		avgPulse1=avgPulse1/obj1.getS23Table().get(index1).size();
		avgJacket1=avgJacket1/obj1.getS23Table().get(index1).size();
		System.out.println("Average Jacket 1: "+avgJacket1);
		System.out.println("Average Pulse 1: "+avgPulse1);
		System.out.println(" Num Compressed before Iterating Through 2: "+numCompress);
		double tempNumJacket1Undivided=avgJacket1*numCompress;
		double tempNumPulse1Undivided=avgPulse1*numCompress;
		int numCompress2=0;
		for(int l=0;l<obj2.getS23Table().get(index2).size();l++)
		{
			numCompress++;
			numCompress2++;
			avgPulse2+=obj2.getS23Table().get(index2).get(l).getMeanPulse();
			avgJacket2+=obj2.getS23Table().get(index2).get(l).getMeanJacket();
			//Comparing Jackets
			if(obj2.getS23Table().get(index2).get(l).getMeanJacket()>maxJacket)
			{
				maxJacket=obj2.getS23Table().get(index2).get(l).getMeanJacket();
			}
			if(obj2.getS23Table().get(index2).get(l).getMeanJacket()<minJacket)
			{
				minJacket=obj2.getS23Table().get(index2).get(l).getMeanJacket();
			}
			//Comparing Pulse
			if(obj2.getS23Table().get(index2).get(l).getMeanPulse()<minPulse && obj2.getS23Table().get(index2).get(l).getMeanPulse()>0)
			{
				minPulse=obj2.getS23Table().get(index2).get(l).getMeanPulse();
			}
			if(obj2.getS23Table().get(index2).get(l).getMeanPulse()>maxPulse && obj2.getS23Table().get(index2).get(l).getMeanPulse()>0)
			{
				maxPulse=obj2.getS23Table().get(index2).get(l).getMeanPulse();
			}
			if(obj2.getS23Table().get(index2).get(l).getMinPulse()<minPulse && obj2.getS23Table().get(index2).get(l).getMinPulse()>0)
			{
				minPulse=obj2.getS23Table().get(index2).get(l).getMinPulse();
			}
			if(obj2.getS23Table().get(index2).get(l).getMaxPulse()>maxPulse&& obj2.getS23Table().get(index2).get(l).getMaxPulse()>0)
			{
				maxPulse=obj2.getS23Table().get(index2).get(l).getMaxPulse();
			}
		}
		System.out.println(" Num Compressed after Iterating Through 2: "+numCompress);

		avgPulse2=avgPulse2/obj2.getS23Table().get(index2).size();
		avgJacket2=avgJacket2/obj2.getS23Table().get(index2).size();
	
		System.out.println("Average Jacket 2: "+avgJacket2);
		System.out.println("Average Pulse 2: "+avgPulse2);
		System.out.println(" Min Pulse: "+minPulse);
		System.out.println(" MAX Pulse: "+maxPulse);
		S_23 replacement;
		if(Math.abs(avgPulse2-avgPulse1)>2)
		{
			 replacement= new S_23(minPulse, maxPulse, minJacket, maxJacket,obj1.getS23Table().get(index1).get(0).getTemperature(),
					avgJacket1,avgPulse1,obj1.getS23Table().get(index1).get(0).getPulseParam() ,true );

		}
		else
		{
	//	S_23 replacement= new S_23(minPulse, maxPulse, minJacket, maxJacket,obj1.getS23Table().get(index1).get(0).getTemperature(),
		//		(avgJacket1+avgJacket2)/2,(avgPulse1+avgPulse2)/2,obj1.getS23Table().get(index1).get(0).getPulseParam() ,true );
		 replacement= new S_23(minPulse, maxPulse, minJacket, maxJacket,obj1.getS23Table().get(index1).get(0).getTemperature(),
				(avgJacket1+avgJacket2)/2,(avgPulse1+avgPulse2)/2,obj1.getS23Table().get(index1).get(0).getPulseParam() ,true );
		replacement.setNumTimesCompressed(numCompress);

		}
		System.out.println(" MAX Pulse of Replacement: "+replacement.getMaxPulse());
		return replacement;
	}
}

	