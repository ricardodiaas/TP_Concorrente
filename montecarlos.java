
import java.io.PrintStream;

import java.util.Random;

public class montecarlos
{

	
    public static void main(String[] args) throws Exception {

    	long inicio =   System.currentTimeMillis();

        int nThrows = 10000000;

     //   double PI= calculatePi(nThrows);
        double l = getPi(nThrows);
        double PI = 4.0 *( l / nThrows);
 


        System.out.println("PI estimate = "+ PI);
		long fim =  System.currentTimeMillis();
	
		  System.out.println("Calculated in " +
	                ((fim - inicio)) + " milliseconds");

    }

   



public static double getPi(int numThrows){
		int inCircle= 0;
		for(int i= 0;i < numThrows;i++){
			//a square with a side of length 2 centered at 0 has 
			//x and y range of -1 to 1
			double randX= (Math.random() * 2) - 1;//range -1 to 1
			double randY= (Math.random() * 2) - 1;//range -1 to 1
			//distance from (0,0) = sqrt((x-0)^2+(y-0)^2)
			double dist= Math.sqrt(randX * randX + randY * randY);
			//double dist= Math.hypot(randX, randY);
			
			if(dist < 1){//circle with diameter of 2 has radius of 1
				inCircle++;
			}
		}
		return inCircle;
	}
}