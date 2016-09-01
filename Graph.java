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
	private Step0CalibrationDetect step0;
	private double electricalLoss;
	private Step1Calculation step1Calibration;
	private static Stack<Step2and3Calc> Step2and3CalcGRAPHStack;

	
	
	
	private static Stack<Step1Calculation> Step1LongCalcStack;
	private static Stack<Step2and3Calc> Step2and3CalcStack;
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
		initializeColoredPoints(fileName,true, false, false, false,origin, xBottomAxis,null);
		drawPoints(g,origin,yTopAxis,xBottomAxis);
		drawScale(g,origin,xBottomAxis,yTopAxis);
		statPanel.updateInformation(electricalLoss, table23);
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
			g.setColor(pointToPlot.get(i).get(0).getColor()); //This sets the color
			domainTemp=domainMax;
			xBottomTemp=domainMax;
			scaleMX=xBottomTemp/domainTemp;
			scaleMY=(origin.getY()-yTopAxis.getY())/RangeMax;
			
			for(int j=0;j<pointToPlot.get(i).size();j++)// This iterates through the LL
			{
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
	
	

	public void initializeColoredPoints(String date,boolean HeaterQPulse, boolean calor, boolean CoreMS, boolean gasTemps, Point origin, Point xBottomAxis, Step1Calculation past)
	{
		LENRCSVParser parse= new LENRCSVParser(date,step0);
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
	
	public void initializeStep2Points(LENRCSVParser parse, Step1Calculation past, Point origin)
	{
		LinkedList<Integer> pulseRange=	parse.getPulseRange();
		
		if(parse.detectStep2Intervals()==true)
		{
		parse.calculateStep2and3Intervals(past);
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
		else{
			return;
		}
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

}
	