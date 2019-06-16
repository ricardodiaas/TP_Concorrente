import java.util.Scanner;

//Gregory lebinz method
public class lebinzsequencial {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 Scanner reader = new Scanner(System.in);  // Reading from System.in
		 System.out.println("Enter the number of iterations: ");
		 long n = reader.nextLong(); // Scans the next token of the input as an int.
		 //once finished
		 reader.close();
		 long inicio = System.nanoTime(); 
		double factor=1.0;
		double sum =0.0;
		long k;
		//1long n = 100000000;
	
		for(k=0;k<n;k++){
		
			if(!isPar(k)){
				factor=-1.0;
			}else{
				factor=1.0;
			}
			
			sum+=factor/(2*k+1);
			//factor=-factor;
		
		}
	double pi;
			pi =4.0*sum;
		
			long fim = System.nanoTime(); 
			double realpi = Math.PI;
		double error =(pi-realpi)/realpi*100;
			System.out.print("valor real de pi:"+realpi+"\n");
		System.out.print("meu valor de pi:"+pi+"\n");
		System.out.print("erro:"+error+"\n");
		System.out.println("Calculo demorou (secs): "  
			    + String.format("%.6f", (fim-inicio)/1.0e9) );
		
		
	

	}
	public static boolean isPar(long number)
	{ 
		if(number%2==0){
			return true;
		}else{
			return false;
		}
		}


}
