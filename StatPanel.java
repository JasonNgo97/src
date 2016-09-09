import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import java.util.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.oracle.webservices.internal.api.EnvelopeStyle.Style;


public class StatPanel extends JPanel
{
	private double ElectricalLoss;
	private Step2and3Calc table;
	private Step4Calculation table4;
	private Step2and3Calc simplifiedCalcStack;
	private static int StatPanelWidth;
	private static int StatPanelHeight;
	private static int Margin=10;
	private static int increment=10;
	private static Point draw;
    private static final int N = 8;

	
	private JTextPane electricLoss;
	private JTextPane Step23Table[];
	StatPanel( int StatPanelWidth, int StatPanelHeight)
	{
		//super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.BLUE);
		setSize(StatPanelWidth,StatPanelHeight);
		this.StatPanelWidth=StatPanelWidth;
		this.StatPanelHeight=StatPanelHeight;
		this.ElectricalLoss=-1;
		this.table=null;
		/*electricLoss= new JTextPane();
		electricLoss.setBorder(BorderFactory.createEmptyBorder(N, N, N, N));
        StyledDocument doc = electricLoss.getStyledDocument();
		String step1=" Electrical Loss: "+ElectricalLoss;

       javax.swing.text.Style style=electricLoss.addStyle("step1",null);
       StyleConstants.setForeground(style, Color.RED);
       try {
		doc.insertString(doc.getLength(), step1,style );
       } 
       catch (BadLocationException e) 
       {
		// TODO Auto-generated catch block
		e.printStackTrace();
       }
       add(electricLoss);
       */
		
		
		
		
	}
	
	
	
	
	public void paintComponent(Graphics g)
	{
		String step1=" Electrical Loss: "+ElectricalLoss;
		g.setColor(Color.RED);
		setBackground(Color.WHITE);
		//System.out.println("Paint 1");
		//g.drawString(step1, Margin, increment+50);
		g.drawString("At the Bottom To IMplement Scroll", Margin, 700);
		//This is where you draw step2and3
		if(table!=null)
		{
			//System.out.println("Painting 2 and 3");
			 draw= new Point(Margin,increment+70);

			//paintStep2and3(g,draw);
		
		}
		//step1
		
	}
	public void addTablePane()
	{
		String index;
		String jacket;
		String pulsePower;
		String Gas;
		String temperature;
		String label;
		//g.setColor(Color.BLUE);
		Point start;
		int x;
		int y=0;
		int beginLL=0;
		JTextPane tempPane;
		for(int i=0;i<table.getS23Table().size();i++)
		{
			//This iterates through the LL
			String temp= new String();
			String pulse= new String();
			pulse+=table.getS23Table().get(i).get(0).getPulseParam();
			temp+=table.getS23Table().get(i).get(0).getTemperature();
			beginLL+=y+20;
			label=i+") LL with Pulse Param: "+pulse+
					" Temperature: "+temp;
			/*
			g.setColor(Color.MAGENTA);
			System.out.println("New System");
			g.drawString(label, begin.getX(),beginLL );
			x=begin.getX();
			y+=begin.getY()+10+i*increment;
			*/
			 tempPane= new JTextPane();
	        StyledDocument doc = tempPane.getStyledDocument();
	      javax.swing.text.Style style=tempPane.addStyle("style",null);
	       StyleConstants.setForeground(style, Color.MAGENTA);
	       try {
			doc.insertString(doc.getLength(), label,style );
	       } 
	       catch (BadLocationException e) 
	       {
			// TODO Auto-generated catch block
			e.printStackTrace();
	       }
	       
	       add(tempPane);   // This is add the label
			
			
			
			
						
			table.printTable23();
			
			for(int j=0;j<table.getS23Table().get(i).size();j++)
			{  
			//	g.setColor(Color.RED);
				jacket=new String();
				pulsePower= new String();
				temperature= new String();
				Gas= new String();
				//start= new Point(x,y);
				index= new String();
				String interval= new String();
				 interval+="\t"+ '('+j+')';
				index+="\t"+"Index: "+table.getS23Table().get(i).get(j).getIndex();
				jacket+="\t"+"Jacket: "+table.getS23Table().get(i).get(j).getMeanJacket();
				pulsePower+="\t"+"Pulse Power: "+table.getS23Table().get(i).get(j).getMeanPulse();
				temperature+="\t"+"Temperature: "+table.getS23Table().get(i).get(j).getTemperature();
				Gas+="\t"+"Gas: "+table.getS23Table().get(i).get(j).getGas();
				
				String information=interval+"\n"+index+"\n"+jacket+"\n"+pulsePower+"\n"+temperature+"\n"+Gas;
				 tempPane= new JTextPane();
			        StyledDocument doc1 = tempPane.getStyledDocument();
						       javax.swing.text.Style style1=electricLoss.addStyle("style",null);
			       StyleConstants.setForeground(style, Color.BLUE);
			       try {
					doc1.insertString(doc1.getLength(), information,style1 );
			       } 
			       catch (BadLocationException e) 
			       {
					// TODO Auto-generated catch block
					e.printStackTrace();
			       }
			       add(tempPane);
				/*g.drawString(interval, x, y);
				g.setColor(Color.BLUE);
				g.drawString(index, x, y+increment);
				g.drawString(jacket, x, y+increment*2);
				g.drawString(pulsePower, x, y+increment*4);
				g.drawString(Gas, x, y+increment*6);
				g.drawString(temperature, x, y+increment*8);
				y+=y+100;
				 */
				
			}
		}
		
	}
	
	public void addTable4PaneAndSimplifiedCalcStack()
	{
		String index;
		String jacket;
		String pulsePower;
		String Gas;
		String temperature;
		String label;
		//g.setColor(Color.BLUE);
		Point start;
		int x;
		int y=0;
		int beginLL=0;
		JTextPane tempPane;
		
		String holder= new String();
		beginLL+=y+20;
		String labeler="\t"+" EXCESS POWER ";
		 tempPane= new JTextPane();
		 StyledDocument doc = tempPane.getStyledDocument();
	      javax.swing.text.Style style=tempPane.addStyle("style",null);
	       StyleConstants.setForeground(style, Color.MAGENTA);
	       try {
			doc.insertString(doc.getLength(), labeler,style );
	       } 
	       catch (BadLocationException e) 
	       {
			// TODO Auto-generated catch block
			e.printStackTrace();
	       }
	       
	       add(tempPane);

		for(int j=0;j<table4.getStep4Table().size(); j++)
		{
			String temp= new String();
			String pulse= new String();
			pulse+=table4.getStep4Table().get(j).getPulseParam();
			temp+=table4.getStep4Table().get(j).getTemperature();
			beginLL+=y+20;
			label=j+") LL with Pulse Param: "+pulse+
					" Temperature: "+temp;
			 tempPane= new JTextPane();
		         doc = tempPane.getStyledDocument();
		       style=tempPane.addStyle("style",null);
		       StyleConstants.setForeground(style, Color.BLUE);
		       try {
				doc.insertString(doc.getLength(), label,style );
		       } 
		       catch (BadLocationException e) 
		       {
				// TODO Auto-generated catch block
				e.printStackTrace();
		       }
		       
		       add(tempPane);
		       
		       String information="\t"+"Mean Pulse Power:"+table4.getStep4Table().get(j).getPulsePowerMean()
		    		   +"\n"+"\t"+"MAX Pulse Power: "+table4.getStep4Table().get(j).getPulsePowerMAX()
		    		   +"\n"+"\t"+"MIN Pulse Power: "+table4.getStep4Table().get(j).getPulsePowerMIN()
		    		   +"\n"+"\t"+"G Loss: "+table4.getStep4Table().get(j).GLossAVG()
		    		   +"\n"+"\t"+"PHeater: "+table4.getStep4Table().get(j).PHeaterAvg()
		    		   +"\n"+"\t"+"Jacket Loss: "+table4.getStep4Table().get(j).getJacket()
		    		   +"\n"+"\t"+"AVGLenr: "+table4.getStep4Table().get(j).getAVGLENRavg()
		    		   +"\n"+"\t"+"MAXLenr:"+table4.getStep4Table().get(j).getMAXLENR()
		    		   +"\n"+"\t"+"MinLENR:"+table4.getStep4Table().get(j).getMINLENR()+"\n"
		    		   +"\n"+"\t"+"Calculation = Jacket+GasLoss-PHeater-PulsePower "
		    		   +"\n"+"\t"+"\t"+"="+table4.getStep4Table().get(j).getJacket()+
		    		   " + "+table4.getStep4Table().get(j).GLossAVG()
		    		   +" - "+table4.getStep4Table().get(j).PHeaterAvg()
		    		   +" - "+table4.getStep4Table().get(j).getPulsePowerMean()
		    		   +"\n"+"\t"+"\t"+" '=' "+table4.getStep4Table().get(j).getAVGLENRavg();

		    		   
		       
		       tempPane= new JTextPane();
		       doc = tempPane.getStyledDocument();
		       style=tempPane.addStyle("style",null);
		       StyleConstants.setForeground(style, Color.BLACK);
		       try {
				doc.insertString(doc.getLength(), information,style );
		       } 
		       catch (BadLocationException e) 
		       {
				// TODO Auto-generated catch block
				e.printStackTrace();
		       }
		       
		       add(tempPane);
		}
		
		
		
		
		
		
		
		holder= new String();
		beginLL+=y+20;
		 labeler="\t"+" Calibration Table ";
		 tempPane= new JTextPane();
		  doc = tempPane.getStyledDocument();
	       style=tempPane.addStyle("style",null);
	       StyleConstants.setForeground(style, Color.MAGENTA);
	       try {
			doc.insertString(doc.getLength(), labeler,style );
	       } 
	       catch (BadLocationException e) 
	       {
			// TODO Auto-generated catch block
			e.printStackTrace();
	       }
	       
	       add(tempPane);
		
		
		
		
		
		for(int i=0;i<simplifiedCalcStack.getS23Table().size();i++)
		{
			//This iterates through the LL
			String temp= new String();
			String pulse= new String();
			pulse+=simplifiedCalcStack.getS23Table().get(i).get(0).getPulseParam();
			temp+=simplifiedCalcStack.getS23Table().get(i).get(0).getTemperature();
			beginLL+=y+20;
			label=i+") LL with Pulse Param: "+pulse+
					" Temperature: "+temp;
			/*
			g.setColor(Color.MAGENTA);
			System.out.println("New System");
			g.drawString(label, begin.getX(),beginLL );
			x=begin.getX();
			y+=begin.getY()+10+i*increment;
			*/
			 tempPane= new JTextPane();
	       doc = tempPane.getStyledDocument();
	      style=tempPane.addStyle("style",null);
	       StyleConstants.setForeground(style, Color.GREEN);
	       try {
			doc.insertString(doc.getLength(), label,style );
	       } 
	       catch (BadLocationException e) 
	       {
			// TODO Auto-generated catch block
			e.printStackTrace();
	       }
	       
	       add(tempPane);   // This is add the label
			
			
			
			
						
	     //  simplifiedCalcStack.printTable23();
			
			for(int j=0;j<simplifiedCalcStack.getS23Table().get(i).size();j++)
			{  
			//	g.setColor(Color.RED);
				jacket=new String();
				pulsePower= new String();
				temperature= new String();
				Gas= new String();
				//start= new Point(x,y);
				index= new String();
				String interval= new String();
				 interval+="\t"+ '('+j+')';
				index+="\t"+"Index: "+simplifiedCalcStack.getS23Table().get(i).get(j).getIndex();
				jacket+="\t"+"Jacket: "+simplifiedCalcStack.getS23Table().get(i).get(j).getMeanJacket();
				pulsePower+="\t"+"Pulse Power: "+simplifiedCalcStack.getS23Table().get(i).get(j).getMeanPulse();
				temperature+="\t"+"Temperature: "+simplifiedCalcStack.getS23Table().get(i).get(j).getTemperature();
				Gas+="\t"+"Gas: "+simplifiedCalcStack.getS23Table().get(i).get(j).getGas();
				
				String information=interval+"\n"+index+"\n"+jacket+"\n"+pulsePower+"\n"+temperature+"\n"+Gas;
				 tempPane= new JTextPane();
			        StyledDocument doc1 = tempPane.getStyledDocument();
						       javax.swing.text.Style style1=electricLoss.addStyle("style",null);
			       StyleConstants.setForeground(style, Color.BLUE);
			       try {
					doc1.insertString(doc1.getLength(), information,style1 );
			       } 
			       catch (BadLocationException e) 
			       {
					// TODO Auto-generated catch block
					e.printStackTrace();
			       }
			       add(tempPane);
				/*g.drawString(interval, x, y);
				g.setColor(Color.BLUE);
				g.drawString(index, x, y+increment);
				g.drawString(jacket, x, y+increment*2);
				g.drawString(pulsePower, x, y+increment*4);
				g.drawString(Gas, x, y+increment*6);
				g.drawString(temperature, x, y+increment*8);
				y+=y+100;
				 */
				
			}
		}
		
	}
	
	
	public void updateInformation(double electricalLoss, Step2and3Calc table, Step4Calculation table4, Step2and3Calc correspondingShrinkTable)
	{
		System.out.println("Recalibrating table");
		this.ElectricalLoss=electricalLoss;
		electricLoss= new JTextPane();
		electricLoss.setBorder(BorderFactory.createEmptyBorder(N, N, N, N));
        StyledDocument doc = electricLoss.getStyledDocument();
		String step1=" Electrical Loss: "+ElectricalLoss;

       javax.swing.text.Style style=electricLoss.addStyle("step1",null);
       StyleConstants.setForeground(style, Color.RED);
       try {
		doc.insertString(doc.getLength(), step1,style );
       } 
       catch (BadLocationException e) 
       {
		// TODO Auto-generated catch block
		e.printStackTrace();
       }
       
       if(table==null)
       {
    	   this.table=null;
    	   System.out.println("Remove ALL");
    	   this.removeAll();
           this.revalidate();
           add(electricLoss);

       }
       else
       {
    	   this.revalidate();
    	   this.removeAll();
           add(electricLoss);
           this.table=table;
           addTablePane();}
       if(table4==null)
       {
    	   this.table4=null;
    	   simplifiedCalcStack=null;
       }
       else
       {
   		this.table4=table4;
   		simplifiedCalcStack=correspondingShrinkTable;
   		addTable4PaneAndSimplifiedCalcStack();

       }
	}
	public void paintStep2and3(Graphics g, Point begin)
	{
		
		String index;
		String jacket;
		String pulsePower;
		String Gas;
		String temperature;
		String label;
		Point start;
		int x;
		int y=0;
		int beginLL=0;
		for(int i=0;i<table.getS23Table().size();i++)
		{
			//This iterates through the LL
			String temp= new String();
			String pulse= new String();
			pulse+=table.getS23Table().get(i).get(0).getPulseParam();
			temp+=table.getS23Table().get(i).get(0).getTemperature();
			beginLL+=y+20;
			label=" LL with Pulse Param: "+pulse+
					" Temperature: "+temp;
			g.setColor(Color.MAGENTA);
			// Lets have it have magenta text 
			System.out.println("New System");
			g.drawString(label, begin.getX(),beginLL );
			x=begin.getX();
			y+=begin.getY()+10+i*increment;
			for(int j=0;j<table.getS23Table().get(i).size();j++)
			{  
				g.setColor(Color.RED);
				jacket=new String();
				pulsePower= new String();
				temperature= new String();
				Gas= new String();
				start= new Point(x,y);
				index= new String();
				String interval= new String();
				 interval+="\t"+ '('+j+')';
				index+="\t"+"Index: "+table.getS23Table().get(i).get(j).getIndex();
				jacket+="\t"+"Jacket: "+table.getS23Table().get(i).get(j).getMeanJacket();
				pulsePower+="\t"+"Pulse Power: "+table.getS23Table().get(i).get(j).getMeanPulse();
				temperature+="\t"+"Temperature: "+table.getS23Table().get(i).get(j).getTemperature();
				Gas+="\t"+"Gas: "+table.getS23Table().get(i).get(j).getGas();
				g.drawString(interval, x, y);
				g.setColor(Color.BLUE);
				g.drawString(index, x, y+increment);
				g.drawString(jacket, x, y+increment*2);
				g.drawString(pulsePower, x, y+increment*4);
				g.drawString(Gas, x, y+increment*6);
				g.drawString(temperature, x, y+increment*8);
				y+=y+100;

				
			}
		}
	}
	
}
