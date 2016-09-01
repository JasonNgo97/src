import java.awt.Color;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class Frame extends JFrame implements MouseInputListener
{

	
	/* _______________________________________				 ___
	 * | 						|| Navigation ||			  |		50
	 * |------------------------||------------||             ---
	 * |						||			  ||			  |
	 * |						||			  ||			  |
	 * |		Graph			||	Stat 	  ||			  |
	 * |		Panel			||	Panel	  ||			  |
	 * |						||			  ||			  |
	 * |						||			  ||			  |     600
	 * |						||			  ||			  |
	 * |						||			  ||			  |
	 * |						||			  ||			  |
	 * |						||			  ||			  |
	 * |------------------------||------------ || 			  |
	 * 														 --- 
	 * |------------------------||-------------|
	 * 			850				  400
	 * |---------------------------------------| 
	 * |                1250
	 * |
	 * |
	 * |
	 * |
	 */
	Graph graphPanel;
	StatPanel statPanel;
	BackForth navigationPanel;
	JScrollPane scrollPaneLeft;
	JScrollPane scrollPaneRight;
	
	private static final int graphPanelWidth=850;
	private static final int BottomPanelHeight=600;
	private static final int statPanelWidth=200;
	private static final int topPanelHeight=50;
	
	private static final int FrameWidth=graphPanelWidth+statPanelWidth+100;
	private static final int FrameHeight=BottomPanelHeight+topPanelHeight+30;
	
	
	String dateBegin;
	String currentDate;
	String dateEnd;
	int numClicks=0;
	private static int monthsWith30Days[]={4,6,9,11};
	private static int monthsWith31Days[]={1,3,5,7,8,10,12};
	private static int monthWith1Month=2;
	
	
//private final int GraphViewWidth=850;

	
	
	public Frame()
	{
		
			
			
		JFrame frame= new JFrame("SRI HHT");
		frame.setTitle("Brillouin HHT");
		frame.setSize(FrameWidth, FrameHeight);
		frame.setBackground(Color.BLACK);
		//frame.setLayout(new GridBagLayout());
		frame.setLocationRelativeTo(null);
			
			
			//frame.setLayout(new BoxLayout(frame, BoxLayout.X_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//frame.addWindowListener(l);
		/*		
		Graph graph= new Graph(graphPanelWidth,BottomPanelHeight);
		StatisticsPanel panelStat=graph.getStatPanel(statPanelWidth,BottomPanelHeight);

		//add(arrow, BorderLayout.NORTH);
		//add(panelStat,BorderLayout.EAST);
		add(graph);
		*/
	
		//temp=arrow.getBackForth();
	/*	gbc.insets
		graph.setBackground(Color.BLACK);
		add(arrow, BorderLayout.NORTH);
		add(graph,BorderLayout.CENTER);
		
		//add(dateHolder);
	*/
		GridBagConstraints gbc= new GridBagConstraints();
		GridBagLayout layout= new GridBagLayout();
		frame.setLayout(layout);
		
		gbc.fill=GridBagConstraints.BOTH;
		//gbc.insets=new Insets(15,15,15,15);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.weightx=0.75;
		gbc.weighty=0.95;
		
		graphPanel= new Graph(graphPanelWidth,BottomPanelHeight);
		graphPanel.addMouseListener(this);
        TitledBorder titleBorder = new TitledBorder("Graph View");
        graphPanel.setBackground(Color.BLACK);
        graphPanel.setBorder(titleBorder);
		scrollPaneLeft= new JScrollPane(graphPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//scrollPaneLeft.setMinimumSize(new Dimension(50,50));

		frame.add(scrollPaneLeft,gbc);
		
		//Graph graph= new Graph(graphPanelWidth,BottomPanelHeight);
        //TitledBorder titleBorder = new TitledBorder("Graph View");
        
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.weightx=0.25;
		gbc.weighty=0.95;
		
	    statPanel=graphPanel.getStatPanel(statPanelWidth,BottomPanelHeight);
	    //JScrollBar bar1= new JScrollBar();
	    statPanel.addMouseListener(this);
	    scrollPaneRight= new JScrollPane(statPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    //scrollPaneRight.add(bar1);
	    //scrollPaneRight.setMinimumSize(new Dimension(50,50));
	   
	    scrollPaneRight.setViewportView(statPanel);
	    statPanel.setAutoscrolls(true);
	   
		//StatisticsPanel panelStat= new StatisticsPanel();
		titleBorder = new TitledBorder("Stat View");
		statPanel.setBorder(titleBorder);
		//frame.add(panelStat,gbc);
		
		frame.add(scrollPaneRight,gbc);
		navigationPanel= new BackForth();
		
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.weightx=0.25;
		gbc.weighty=0.05;
		titleBorder = new TitledBorder("Button");
		navigationPanel.setBorder(titleBorder);
		navigationPanel.addMouseListener(this);
		navigationPanel.setGraphPanel(graphPanel);
		frame.add(navigationPanel,gbc);
		

		
		/*
		add(arrow, BorderLayout.NORTH);
		add(panel3,BorderLayout.EAST);
		add(graph,BorderLayout.CENTER);
		
		*/
		//temp=arrow.getBackForth();
	/*	gbc.insets
		graph.setBackground(Color.BLACK);
		add(arrow, BorderLayout.NORTH);
		add(graph,BorderLayout.CENTER);
		*/
		pack();
		
		
		
	}





	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse Pressed");
		System.out.println(" Source: "+e.getSource());
		// TODO Auto-generated method stub
		if(e.getSource()==statPanel)
		{
			System.out.println("Stat Panel");
			numClicks=navigationPanel.getNumClicks();

			System.out.println("Num CLick: "+numClicks);
		}
		if(e.getSource()==scrollPaneRight)
		{
			System.out.println("Scroll Panel Right");

		}
		if(e.getSource()==scrollPaneLeft)
		{
			System.out.println("Scroll Panel Left");

		}
		if(e.getSource()==navigationPanel)
		{
			System.out.println("Navigation Panel");
			System.out.println(navigationPanel.getLeft());
			System.out.println(navigationPanel.getTouch());
			numClicks=navigationPanel.getNumClicks();
			System.out.println("Num CLick: "+numClicks);

			if(numClicks>0)
			{
			System.out.println("Recalibrating");
			}

		}
		if(e.getSource()==graphPanel)
		{
			System.out.println("Graph Panel");
			if(graphPanel.getDateEnter()==true)
			{
				navigationPanel.setDate(graphPanel.getBeginDate());
				dateBegin=graphPanel.getBeginDate(); //This is your begin Date
				dateEnd=graphPanel.getEndDate();
				numClicks=navigationPanel.getNumClicks();
				System.out.println("Num CLick: "+numClicks);

				graphPanel.setDate(setDate());
				
			}
		}
	}

	public String setDate()
	{
		int numDays=0;
		System.out.println("Num Clicks: "+numClicks);
		String splitBegin[]=dateBegin.split("-");
		String splitEnd[]=dateEnd.split("-");
		int monthBegin=Integer.parseInt(splitBegin[0]);
		int dayMonthBegin=Integer.parseInt(splitBegin[1]);
		int monthEnd=Integer.parseInt(splitEnd[0]);
		int dayMonthEnd=Integer.parseInt(splitEnd[1]);
		
		int MonthDiff=monthEnd-monthBegin;
		if(numClicks==0)
		{
			return currentDate=monthBegin+"-"+dayMonthBegin+"-2016";
		}
		if(MonthDiff==0)
		{
			if(dayMonthBegin+numClicks!=dayMonthEnd)
			{
			currentDate=monthBegin+"-"+(dayMonthBegin+numClicks)+"-2016";
			}
			else{
			currentDate=monthBegin+"-"+dayMonthEnd+"-2016";
			}
		}
		else
		{
			for(int i=monthBegin;i<monthEnd+1;i++)
			{
				if(i==monthBegin)
				{
					for(int k=dayMonthBegin;k<getDaysInMonth(i)+1;k++)
					{
						if(numDays==numClicks)
						{
							currentDate=monthBegin+"-"+k+"-2016";
							return currentDate;
						}
						numDays++;
						
					}
					i++;
					
				}
				if(i==monthEnd)
				{
					for(int k=0;k<dayMonthEnd;k++)
					{
						if(numDays==numClicks)
						{
							currentDate=monthBegin+"-"+k+"-2016";
							return currentDate;
						}
						numDays++;
						
					}
					
				}
				else
				{
					for(int k=0;k<getDaysInMonth(i)+1;k++)
					{
						if(numDays==numClicks)
						{
							currentDate=i+"-"+k+"-2016";
							return currentDate;
						}
						numDays++;
						
					}
				}
				
				
			}
			
		}
		
		
		
		
		return currentDate;

	}

	public int getDaysInMonth( int month)
	{
		if(month>12 || month<1)
		{
			System.out.println("Error");
		}
		if(month==2)
		{
			return 28;
		}
		for(int i=0;i<monthsWith30Days.length;i++)
		{
			if(monthsWith30Days[i]==month)
			{
				return 30;
			}
		}
		for(int i=0;i<monthsWith31Days.length;i++)
		{
			if(monthsWith31Days[i]==month)
			{
				return 31;
			}
		}
		System.out.println("Error");
		return -1;
		/*private static int monthsWith30Days[]={4,6,9,11};
		private static int monthsWith31Days[]={1,3,5,7,8,10,12};
		private static int monthWith1Month=2;
		*/
	}

	@Override

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		if(e.getSource()==statPanel)
		{
			System.out.println("Stat Panel");
		}
		if(e.getSource()==scrollPaneRight)
		{
			System.out.println("Scroll Panel Right");

		}
		if(e.getSource()==graphPanel)
		{
			System.out.println("Graph Panel");
		}
		if(e.getSource()==navigationPanel)
		{
			System.out.println("Navigation Panel");
			numClicks=navigationPanel.getNumClicks();

			//System.out.println(navigationPanel.getLeft());
			//System.out.println(navigationPanel.getTouch());
			
		}
		if(numClicks>0)
		{
		System.out.println("Recalibrating");
		}
	}





	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * 	  |---------|____________________________|________________|
	 *    |	/|___|\ |							 |				  |
	 *    |	\|-- |/	|	Brillouin Image		     | Stat Panel	  |
	 * 	  |--------------------------------------|				  |
	 * 	  |										 |				  |
	 *    |										 | 				  |
	 *    |			Graph Here					 | 				  |
	 * 	  |										 | 				  |
	 * 	  |										 | 				  |
	 * 	  |										 | 				  |
	 * 	  |										 |				  |
	 *    |										 | 				  |
	 *    |										 |				  |
	 * 	  |										 |				  |
	 * 	  |										 |				  |
	 * 	  |______________________________________|				  |
	 * 
	 * 
	 * 
	 */
	

}
