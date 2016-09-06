import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BackForth extends JPanel
{
	private int xLengthPanel;
	private int yLengthPanel;
	private BackForth panelBackForth;
	private String date;
	private int numClicks;
	private JLabel labelArrow;
	private ImageIcon leftArrow;
	private ImageIcon rightArrow;
	//private ImageIcon rightArrow;
	//These are the sets of Buttons
	
	private JButton buttonLeft;
	private JButton buttonRight;
	private JButton QPulseButton;
	private JButton CoreMSButton;
	private JButton GasTempButton;
	private JButton ExcessPowerButton;
	
	private boolean QPulse;
	private boolean CoreMS;
	private boolean GasTemp;
	private boolean ExcessPower;
	
	private JLabel label;
	private static int monthsWith30Days[]={4,6,9,11};
	private static int monthsWith31Days[]={1,3,5,7,8,10,12};
	private static int monthWith1Month=2;
	
	private Graph panel;
	/*
	 * |------------------------------------------|
	 * |	Date 	  	  |/  |___   ___ |\        |	
	 * |	JLabel	  	  |\	  | |     /        |	
	 * |			  	  | \|---   === |/         |	
	 * |--------------|------------------------   |	
	 * |======--||--------||--------||------------||								  |	
	 * |QPulse  || Core MS||Gas Temp|| Excess POW ||	
	 * |        ||        ||        ||            ||	
	 * |		||		  ||		||			   |	
	 * |------------------------------------------|
	 * |
	 * |
	 * |
	 * |
	 * |
	 * |
	 * |
	 */
	private boolean left=false;
	private boolean buttonTouched=false;
	BackForth()
	{
		numClicks=0;
		xLengthPanel=110;
		yLengthPanel=30;
		this.buttonTouched=false;
		//panelBackForth= new BackForth();
		//panelBackForth.setLayout(new FlowLayout());
		setSize(xLengthPanel, yLengthPanel);
		setBackground(Color.WHITE);
		leftArrow= new ImageIcon(("data/LA.png"));
		rightArrow= new ImageIcon(("data/RA.png"));
		label= new JLabel("Date: ");
		buttonLeft= new JButton("",leftArrow);
		
		buttonRight=new JButton("",rightArrow);
		add(label);
		add(buttonLeft);
		add(buttonRight);
		//labelArrow= new JLabel(Arrow);
		//add(new JLabel(new ImageIcon("data/Arrows.png")));	
		buttonRight.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			left=false;
			buttonTouched=true;
			System.out.println("Button Right");
			numClicks++;
			System.out.println("Num Clicks increasing to :" + numClicks);
			incrementDate();
			panel.setDate(date);
			System.out.println("Date is "+date );
			panel.repaint();
			System.out.println("Repainting");
			setDate();
			//Increase Date
		}});
		buttonLeft.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				left=true;
				buttonTouched=true;
				numClicks=numClicks-1;
				System.out.println("Button Left");
				System.out.println("Num Clicks decrease=ing to :" + numClicks);
				
				decrementDate();
				panel.setDate(date);
				System.out.println("Date is "+date );
				panel.repaint();
				System.out.println("Repainting");

				setDate();
				//Decrease Date
			}});
	}
	BackForth(LayoutManager layout, int PanelWidth, int PanelHeight)
	{
		/*|------------------------------------------|
		 * |	Date 	  	  |/  |___   ___ |\        |	
		 * |	JLabel	  	  |\	  | |     /        |	
		 * |			  	  | \|---   === |/         |	
		 * |--------------|------------------------   |	
		 * |====|==--||---|-----||----|----||------|------||								  |	
		 * |QPulse  || Core MS||Gas Temp|| Excess POW ||	
		 * |    |    ||    |    ||  |      ||    |   ||	
		 * |	|	||	   |  ||	|	||		 |   ||	
		 * |------------------------------------------|
		*/
		super(layout);
		GridBagConstraints gbc= new GridBagConstraints();
		xLengthPanel=PanelWidth;
		yLengthPanel=PanelHeight;
		//panelBackForth= new BackForth();
		//panelBackForth.setLayout(new FlowLayout());
		setSize(xLengthPanel, yLengthPanel);
		setSize(xLengthPanel, yLengthPanel);
		setBackground(Color.WHITE);
		leftArrow= new ImageIcon(("data/LA.png"));
		rightArrow= new ImageIcon(("data/RA.png"));
		QPulseButton= new JButton("Q Pulse Heat Diff");
		CoreMSButton= new JButton("Core MS");
		GasTempButton= new JButton("Gas Temp");
		ExcessPowerButton= new JButton("Excess Pow");
		label= new JLabel("Date: ");
		buttonLeft= new JButton("",leftArrow);
		buttonRight=new JButton("",rightArrow);
		
		QPulse=false;
		CoreMS=false;
		GasTemp=false;
		ExcessPower=false;
		
		gbc.gridwidth=4;
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=0.5;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		add(label,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=2;
		gbc.gridy=0;
		gbc.weightx=0.5;
		add(buttonLeft,gbc);
		//add(buttonRight,gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=3;
		gbc.gridy=0;
		gbc.weightx=0.5;
		add(buttonRight,gbc);
		
		gbc.gridwidth=2;
		gbc.gridx=4;
		gbc.gridy=0;
		gbc.weightx=0.5;
		add(QPulseButton,gbc);
		
		gbc.gridwidth=2;
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx=0.5;
		add(CoreMSButton,gbc);
		
		gbc.gridwidth=2;
		gbc.gridx=2;
		gbc.gridy=1;
		gbc.weightx=0.5;
		add(GasTempButton,gbc);
		
		gbc.gridwidth=2;
		gbc.gridx=4;
		gbc.gridy=1;
		gbc.weightx=0.5;
		add(ExcessPowerButton,gbc);
		
		//labelArrow= new JLabel(Arrow);
		//add(new JLabel(new ImageIcon("data/Arrows.png")));
		QPulseButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Excess Power");
				ExcessPower=false;
				CoreMS=false;
				QPulse=true;
				GasTemp=false;
				//(boolean QPulse, boolean CoreMS,boolean gasTemps, boolean ExcessPower)
				panel.setGraphType(QPulse,CoreMS,GasTemp,ExcessPower);
				panel.repaint();
				System.out.println("Repainting");
				//Increase Date
			}});
		
		CoreMSButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Excess Power");
				ExcessPower=false;
				CoreMS=true;
				QPulse=false;
				GasTemp=false;
				panel.setGraphType(QPulse,CoreMS,GasTemp,ExcessPower);
				panel.repaint();

				System.out.println("Repainting");
				//Increase Date
			}});
		
		GasTempButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Excess Power");
				ExcessPower=false;
				CoreMS=false;
				QPulse=false;
				GasTemp=true;
				panel.setGraphType(QPulse,CoreMS,GasTemp,ExcessPower);

				panel.repaint();
				System.out.println("Repainting");
				//Increase Date
			}});
		
		ExcessPowerButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Excess Power");
				ExcessPower=true;
				CoreMS=false;
				QPulse=false;
				GasTemp=false;
				panel.setGraphType(QPulse,CoreMS,GasTemp,ExcessPower);
				panel.repaint();
				System.out.println("Repainting");
				//Increase Date
			}});
		buttonRight.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			left=false;
			buttonTouched=true;
			System.out.println("Button Right");
			numClicks++;
			System.out.println("Num Clicks increasing to :" + numClicks);
			incrementDate();
			panel.setDate(date);
			System.out.println("Date is "+date );
			panel.repaint();
			System.out.println("Repainting");
			setDate();
			//Increase Date
		}});
		buttonLeft.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				left=true;
				buttonTouched=true;
				numClicks=numClicks-1;
				System.out.println("Button Left");
				System.out.println("Num Clicks decrease=ing to :" + numClicks);
				
				decrementDate();
				panel.setDate(date);
				System.out.println("Date is "+date );
				panel.repaint();
				System.out.println("Repainting");

				setDate();
				//Decrease Date
			}});
		
		//Arrow= new ImageIcon(getClass().getResource("data/Arrows.png"));
		//labelArrow= new JLabel(Arrow);
	}
	
	public void setDate(String date)
	{
		label.setText("");
		label.setText("Date: "+date);
		this.date=date;
	}
	public void setGraphPanel(Graph panel )
	{
		this.panel=panel;
	}
	public void setDate()
	{
		label.setText("");
		label.setText("Date: "+date);
		
	}
	public void incrementDate()
	{
		String dateSplit[]=date.split("-");
		int numDaysinMonth=getDaysinMonth(Integer.parseInt(dateSplit[0]));
		int currentDay=Integer.parseInt(dateSplit[1]);
		int currentMonth=Integer.parseInt(dateSplit[0]);
		if(currentDay==numDaysinMonth)
		{
			currentMonth++;
			currentDay=1;
			date=currentMonth+"-"+currentDay+"-"+dateSplit[2];
			System.out.println("Incrementing date to"+date);
			return;
		}
		else
		{
			currentDay++;		
			date=currentMonth+"-"+currentDay+"-"+dateSplit[2];
			System.out.println("Incrementing date to"+date);
			return;
		}
		/*
		if(currentDay==numDaysinMonth)
		{
			currentMonth++;
			currentDay=1;
			date=currentMonth+"-"+currentDay+"-"+dateSplit[2];
		}*/
		
	}
	public void decrementDate()
	{
		String dateSplit[]=date.split("-");
		int numDaysinMonth=getDaysinMonth(Integer.parseInt(dateSplit[0]));
		int currentDay=Integer.parseInt(dateSplit[1]);
		int currentMonth=Integer.parseInt(dateSplit[0]);
		int dayToCalibrate;
		if(currentDay==1)
		{
			dayToCalibrate=getDaysinMonth(Integer.parseInt(dateSplit[0])-1);
			date=currentMonth-1+"-"+dayToCalibrate+"-"+dateSplit[2];
			System.out.println("Decrementing date to"+date);
			return;

		}
		
		else
		{
			currentDay=currentDay-1;		
			date=currentMonth+"-"+currentDay+"-"+dateSplit[2];
			System.out.println("Decrementing date to"+date);
			return;
		}
		/*
		if(currentDay==numDaysinMonth)
		{
			currentMonth++;
			currentDay=1;
			date=currentMonth+"-"+currentDay+"-"+dateSplit[2];
		}*/
		
	}
	public boolean getTouch()
	{
		return this.buttonTouched;
	}
	public int getNumClicks()
	{
		return this.numClicks;
	}
	public boolean getLeft(){
		return this.left;
	}
	public boolean getRight(){
		return !this.left;
	}

	public int getDaysinMonth(int month)
	{
		/*
		 * 
	private static int monthsWith30Days[]={4,6,9,11};
	private static int monthsWith31Days[]={1,3,5,7,8,10,12};
	private static int monthWith1Month=2;
		 */
		if(month==2)
		{
			return 28;
		}
		if( month>12 || month<1)
		{
			System.out.println("Error in month");
			return -1;
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
			if(month==monthsWith31Days[i])
			{
				return 31;
			}
		}
		System.out.println("Error");
		return -1;
	}
	
	public BackForth getBackForth()
	{
		return this.panelBackForth;
	}
	
	
	
	
}
