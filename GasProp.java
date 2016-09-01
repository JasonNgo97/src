import java.util.*;
public class GasProp 
{
	private String gasName;
	private ArrayList<Integer> Temperature;
	private ArrayList<Integer> Pressure;
	private ArrayList<Double> Density;
	private ArrayList<Double> HeatCapacityCP;
	
	GasProp(String gasName)
	{
		this.gasName= gasName;
		this.Temperature= new ArrayList<>();
		this.Pressure= new ArrayList<>();
		this.Density= new ArrayList<>();
		this.HeatCapacityCP= new ArrayList<>();
	}
	public void addElem( int temp, int pressure, double density, double heatCapacity)
	{
		this.Temperature.add(temp);
		this.Pressure.add(pressure);
		this.Density.add(density);
		this.HeatCapacityCP.add(heatCapacity);
		/*
		System.out.println(" Temp : "+temp);
		System.out.println(" Pressure: "+pressure);
		System.out.println(" Density: "+density);
		System.out.println(" Heat Capacity: "+heatCapacity);
		return;
		*/
	}
	public void printGasProp()
	{
		//System.out.println(" Gas: "+gasName);
		for(int i=0;i<Temperature.size();i++)
		{
			//System.out.println("\t"+this.Temperature.get(i)+"\t"+this.Pressure.get(i)+"\t"+this.Density.get(i)+"\t"+this.HeatCapacityCP.get(i)+"\t");
			System.out.println();
		}
		return;
	}
	public void printPressure()
	{
		//System.out.println(" Gas : "+this.gasName);
		for(int i=0;i<Pressure.size();i++)
		{
			//System.out.println(Pressure.get(i));
		}
	}
	public void printTemp()
	{
		//System.out.println("Gas : "+this.gasName);
		for(int i=0;i<Temperature.size();i++)
		{
			//System.out.println(Temperature.get(i));
		}
	}
	public void printGasDensity()
	{
	//	System.out.println("Gas : "+this.gasName);
		for(int i=0;i<Density.size();i++)
		{
			//System.out.println(Density.get(i));
		}
	}
	public void printGasHeatCapacity()
	{
		//System.out.println("Gas: "+gasName);
		for(int i=0;i<HeatCapacityCP.size();i++)
		{
		//	System.out.println(HeatCapacityCP.get(i));
		}
	}
	public double getDensity(int temperature, int pressure)
	{
		/*
		 * First you need to check the range of the temperature and pressure
		 */
		
		/*
		 * First you need to check the range of the temperature and pressure
		 */	
		//System.out.println("Gas name: "+this.gasName);
		if(temperature<Temperature.get(0) || temperature>Temperature.get(Temperature.size()-1) || pressure<0 || pressure>Pressure.get(Pressure.size()-1))
		{
			System.out.println(" Temp min :"+Temperature.get(0) +" Temperature Max: "+Temperature.get(Temperature.size()-1));
			System.out.println(" Pressure MAX "+Pressure.get(Pressure.size()-1));
			
			System.out.println("Temperature :"+temperature);
			System.out.println("Pressure :"+pressure);
			System.out.println("Error. The temperature or Pressure is out of range of the table. ");
			return -1;
		}
		//Everything is rounding down......
	
		for(int i=0;i<Temperature.size();i++) 
			// Here you are scanning the temperature ot match
		{	
			

			
			//Here is if you have the exact temperature and pressure
			if(Temperature.get(i)==temperature && Pressure.get(i)==pressure) //Here you test if the condition is exact
			{
				//System.out.println("Density: "+Density.get(i)+ "  kg/m^3");
				return Density.get(i);
			}
			//Here you have the temperature in between and pressure exact
			if( i<68)
			{
				
				
		
			if( (Temperature.get(i)<temperature && Temperature.get(i+1)>temperature) && Pressure.get(i)==pressure)
			{
				//System.out.println("Density: "+Density.get(i)+ "  kg/m^3");
				return Density.get(i);	
			}
			//Here you have temperature exact and pressure in between
			if( Temperature.get(i)==temperature && (Pressure.get(i)<pressure && Pressure.get(i+17)<pressure))
			{
				//System.out.println("Density: "+Density.get(i)+ "  kg/m^3");
				return Density.get(i);
			}
			//Here you have both the temperature and pressure in between
			if( (Temperature.get(i)<temperature && Temperature.get(i+1)>temperature) && (Pressure.get(i)<pressure && Pressure.get(i+17)>pressure))
			{
				//System.out.println("Density: "+Density.get(i)+ "  kg/m^3");
				return Density.get(i);
			}
			}
			/*if(i<17) //This condition is for when Pressure get stuck in the beginning Temperature doesn't get stuck
			{
			//Here you have temperature exact and pressure is stuck in the beginning
			if(Temperature.get(i)==temperature && (pressure<Pressure.get(i)) && pressure<Pressure.get(i+1))
				{
				System.out.println("Returning Density: "+Density.get(i)+ "  kg/m^3");
				return Density.get(i);
					}
			//Here you want both the temperature in between and the pressure is stuck in the beginning
			if(Temperature.get(i)<temperature && Temperature.get(i+1)>temperature && pressure>Pressure.get(i) && pressure>Pressure.get(i+1))
				{
				System.out.println("Density: "+Density.get(i)+ "  kg/m^3");
				return Density.get(i);
					
				}
			}*/
			
		}
		System.out.println("Density is not printing correctly");

		return 0;

	}
	public double getHeatCapacity(int temperature, int pressure)
	{
		/*
		 * First you need to check the range of the temperature and pressure
		 */	
		//System.out.println("Gas name: "+this.gasName);

		if(temperature<Temperature.get(0) || temperature>Temperature.get(Temperature.size()-1) || pressure<0 || pressure>Pressure.get(Pressure.size()-1))
		{
			System.out.println(" Temp min :"+Temperature.get(0) +" Temperature Max: "+Temperature.get(Temperature.size()-1));
			System.out.println(" Pressure MAX "+Pressure.get(Pressure.size()-1));
			
			System.out.println("Temperature :"+temperature);
			System.out.println("Pressure :"+pressure);
			System.out.println("Error. The temperature or Pressure is out of range of the table. ");
			return -1;
		}
		//Everything is rounding down......
		for(int i=0;i<Temperature.size();i++) 
			// Here you are scanning the temperature ot match
		{	
			

			
			//Here is if you have the exact temperature and pressure
			if(Temperature.get(i)==temperature && Pressure.get(i)==pressure) //Here you test if the condition is exact
			{
			//	System.out.println("Heat Capacity: "+HeatCapacityCP.get(i)+ "  kg/m^3");
				return HeatCapacityCP.get(i);
			}
			//Here you have the temperature in between and pressure exact
			if( i<68)
			{
				
				
		
			if( (Temperature.get(i)<temperature && Temperature.get(i+1)>temperature) && Pressure.get(i)==pressure)
			{
				//System.out.println("Heat Capacity: "+HeatCapacityCP.get(i)+ "  kg/m^3");
				return HeatCapacityCP.get(i);
			}
			//Here you have temperature exact and pressure in between
			if( Temperature.get(i)==temperature && (Pressure.get(i)<pressure && Pressure.get(i+17)<pressure))
			{
				//System.out.println("Heat Capacity: "+HeatCapacityCP.get(i)+ "  kg/m^3");
				return HeatCapacityCP.get(i);
			}
			//Here you have both the temperature and pressure in between
			if( (Temperature.get(i)<temperature && Temperature.get(i+1)>temperature) && (Pressure.get(i)<pressure && Pressure.get(i+17)>pressure))
			{
				//System.out.println("Heat Capacity: "+HeatCapacityCP.get(i)+ "  kg/m^3");
				return HeatCapacityCP.get(i);
			}
			}
			/*if(i<17) //This condition is for when Pressure get stuck in the beginning Temperature doesn't get stuck
			{
			//Here you have temperature exact and pressure is stuck in the beginning
			if(Temperature.get(i)==temperature && (pressure<Pressure.get(i)) && pressure<Pressure.get(i+1))
				{
				System.out.println("Returning Density: "+Density.get(i)+ "  kg/m^3");
				return Density.get(i);
					}
			//Here you want both the temperature in between and the pressure is stuck in the beginning
			if(Temperature.get(i)<temperature && Temperature.get(i+1)>temperature && pressure>Pressure.get(i) && pressure>Pressure.get(i+1))
				{
				System.out.println("Density: "+Density.get(i)+ "  kg/m^3");
				return Density.get(i);
					
				}
			}*/
			
		}
		System.out.println("Heat capacity is not printing correctly");

		return 0;
		
	}
	

}
