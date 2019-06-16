
import java.io.PrintStream;

import java.util.Random;
import java.util.Scanner;

public class montecarlos
{

	
    public static void main(String[] args) throws Exception {

    


    	 Scanner reader = new Scanner(System.in);  
		 System.out.println("Enter the number of iterations: ");
		 long nThrows = reader.nextLong(); 

		 reader.close();
		 
		long inicio = System.nanoTime(); 
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

			double randX= (Math.random());//range -1 to 1
			double randY= (Math.random());//range -1 to 1
		
			if(randX * randX + randY * randY <= 1){
				inCircle++;
			}
		}
		return inCircle;
	}
}