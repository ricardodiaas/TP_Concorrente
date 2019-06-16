
import java.io.PrintStream;

import java.util.Random;
import java.util.Scanner;

public class montecarlos
{

	
    public static void main(String[] args) throws Exception {

    

       // int nThrows = 10000000;
    	 Scanner reader = new Scanner(System.in);  // Reading from System.in
		 System.out.println("Enter the number of iterations: ");
		 long nThrows = reader.nextLong(); // Scans the next token of the input as an int.
		 //once finished
		 reader.close();
			long inicio = System.nanoTime(); 
     //   double PI= calculatePi(nThrows);
        double l = getPi(nThrows);
        double PI = 4.0 *( l / nThrows);
 


     
   	 long fim = System.nanoTime(); 
	
		double realpi = Math.PI;
		double error = (PI-realpi)/realpi*100;
			System.out.print("valor real de pi:"+realpi+"\n");
		System.out.print("meu valor de pi:"+PI+"\n");
		System.out.print("erro:"+error+"\n");
		System.out.println("Calculo demorou (secs): "  
			    + String.format("%.6f", (fim-inicio)/1.0e9) );

    }

   



public static double getPi(long numThrows){
		int inCircle= 0;
		for(int i= 0;i < numThrows;i++){
			//a square with a side of length 2 centered at 0 has 
			//x and y range of -1 to 1
			double randX= (Math.random());//range -1 to 1
			double randY= (Math.random());//range -1 to 1
			//distance from (0,0) = sqrt((x-0)^2+(y-0)^2)
			double dist= Math.sqrt(randX * randX + randY * randY);
			//double dist= Math.hypot(randX, randY);
			
			if(dist <= 1){//circle with diameter of 2 has radius of 1
				inCircle++;
			}
		}
		return inCircle;
	}
}