import java.util.*;
import java.awt.*;
public class ColoredPoint 
{
	private Color color;
	private double xComponent;
	private double yComponent;
	
	private String xLabel;
	private String yLabel;
	
	ColoredPoint(Color color, double xComp, double yComp, String xLabel, String yLabel)
	{
		this.color=color;
		this.xComponent=xComp;
		this.yComponent=yComp;
		this.xLabel=xLabel;
		this.yLabel=yLabel;
	}
	public double getX()
	{
		return this.xComponent;
		
	}
	public double getY()
	{
		return this.yComponent;
	}
	public void setXandY(int x, int y)
	{
		this.xComponent=x;
		this.yComponent=y;
	}
	public Color getColor()
	{
		return this.color;
	}
	
	

}
