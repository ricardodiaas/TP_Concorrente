import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class montecarlolock extends Thread{

	

		private int inicio;
		private int fim;
		private double factor;
		ReentrantLock re; 
		 Lock lock = new ReentrantLock();	
	    static int numSteps =	10000000;
	    int sum = 0;
	    
	    int begin, end ;
	    public montecarlolock( int inicio, int fim ) {
	 		this.inicio = inicio;
	 		this.fim = fim;
	 		System.out.println("bloco: " + inicio +"," + fim+ "this is my sum:"+sum);
	 	}

	     public montecarlolock() {
	 		// TODO Auto-generated constructor stub
	 	}
		public void run() {
	    /*
			lock.lock();
		    try
		      {
		         Long duration = (long) (1000);
		         System.out.println(Thread.currentThread().getName() + ": PrintQueue: Printing a Job during " + (duration) + " milliseconds :: Time - " + new Date());
		         Thread.sleep(duration);
		      } catch (InterruptedException e)
		      {
		         e.printStackTrace();
		      } finally
		      {
			    	sum = getPi(inicio,fim);
				    lock.unlock();
		      }
				
*/
			sum = getPi(inicio,fim);

			
			 
		}
	    
		public static void main(String[] args) throws Exception{
			 int numprocessadores = Runtime.getRuntime().availableProcessors();
			  long startTime = System.currentTimeMillis();
			  double supersum=0;
		
			  
			
				
			 montecarlolock[] listathreads = new montecarlolock[numprocessadores];
			 Scanner reader = new Scanner(System.in);  // Reading from System.in
			 System.out.println("Enter the number of iterations: ");
			 int n = reader.nextInt(); // Scans the next token of the input as an int.
			 //once finished
			 reader.close();
			 int bloco = n / numprocessadores;
			 
				for (int i=1; i<numprocessadores+1; i++) {
					int primeiro = (i-1)*bloco;
					int ultimo = (i)*bloco;
								
					montecarlolock t = new montecarlolock();
					t.inicio=primeiro;
					t.fim=ultimo;
					
					
					t.start();

					listathreads[i-1] = t;
					
				}

			
				
				for (int i=0; i<listathreads.length; i++){
			
				//	supersum += listathreads[i].sum;
				
					
					listathreads[i].join();
						supersum += listathreads[i].sum;
				}
				
		        long endTime = System.currentTimeMillis();

		       double pi = (4.0 *(double)( supersum/n));

		        System.out.println("Value of pi: " + pi);

		        System.out.println("Calculated in " +
		                ((endTime - startTime)) + " milliseconds");

		}


		public  synchronized int getPi(int begin, int end){
			int inCircle = 0 ;
			//Random ran = new Random(); 

			//double rand2	=	((Math.random()* 2) - 1);
			//double dist= Math.sqrt(rand1 * rand1 + rand2 * rand2);
			for(int i= begin;i < end;i++){
				//a square with a side of length 2 centered at 0 has 
				//x and y range of -1 to 1
				double randX= ((Math.random()* 2) - 1);//range -1 to 1
				double randY= ((Math.random()* 2) - 1);//range -1 to 1
				//distance from (0,0) = sqrt((x-0)^2+(y-0)^2)
				double distt=	Math.sqrt(randX * randX + randY * randY);
				//double dist= Math.hypot(randX, randY);
				if(distt <= 1){//circle with diameter of 2 has radius of 1
					inCircle++;
				}
			}
			return inCircle;
		}
		}



