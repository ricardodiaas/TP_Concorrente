import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class montecarlolock extends Thread{

	

		private long inicio;
		private long fim;

		private double factor;
private static double total=0;
	 
		 Lock lock = new ReentrantLock();	
	    static int numSteps =	10000000;
	    int sum = 0;
	    
	    long begin, end ;
	    public montecarlolock( int inicio, int fim ) {
	 		this.inicio = inicio;
	 		this.fim = fim;
	 	
	 	}

	     public montecarlolock() {
	 
	 	}
		public void run() {
	
			  lock.lock();
			    try {		 
				         Random prng = new Random ();
				    	  	for(long i= inicio;i <fim;i++){				
								double randX=prng.nextDouble();
								double randY=prng.nextDouble();
								if(randX * randX + randY * randY <= 1.0){
									sum++;
								}								
							}      
				                total += sum;
			    }catch(Exception e) {
			    	System.out.println(e.toString());
			    } 
			    finally {
			        lock.unlock();
			    }
				
		      
		
			 		 
		}
	    
		public static void main(String[] args) throws Exception{
			 int numprocess = Runtime.getRuntime().availableProcessors();
			 System.out.println("numero de nucleos disponiveis: "+numprocess+"\n");
			 Scanner reader = new Scanner(System.in);  // Reading from System.in
			 System.out.println("Indique numero de threads que deseja criar: \n");
			int numprocessadores = reader.nextInt(); // Scans the next token of the input as an int.
			 //once finished
			 reader.nextLine();
			// reader.close();
			 
						
			 montecarlolock[] listathreads = new montecarlolock[numprocessadores];
			
			 // reader = new Scanner(System.in);  // Reading from System.in
			 System.out.println("Indique o numero de pontos de a gerar: ");
			 long n = reader.nextLong(); // Scans the next token of the input as an int.
			 //once finished
			 reader.close();
			 long inicio = System.nanoTime(); 
			 long bloco = n / numprocessadores;
			 
				for (int i=1; i<numprocessadores+1; i++) {
					long primeiro = (i-1)*bloco;
					long ultimo = (i)*bloco;
								
					montecarlolock t = new montecarlolock();
					t.inicio=primeiro;
					t.fim=ultimo;
					
					
					t.start();

					listathreads[i-1] = t;
					
				}

			
				
				for (int i=0; i<listathreads.length; i++){
					listathreads[i].join();				
				}
				

		     double pi = (4.0 *(double)( total/n));
	
		
		     long fim = System.nanoTime();
		     
		   		double realpi = Math.PI;
		   		double error = (pi-realpi)/realpi*100;
		   	 System.out.println("Foram criadas "+listathreads.length+" threads");
		   			System.out.print("valor real de pi:"+realpi+"\n");
		   		System.out.print("meu valor de pi:"+pi+"\n");
		   		System.out.print("erro:"+error+"\n");
		   		System.out.println("Calculo demorou (secs): "  
		   			    + String.format("%.6f", (fim-inicio)/1.0e9) );
		}


}