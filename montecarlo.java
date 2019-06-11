import java.util.*;
public class montecarlo extends Thread {

	private int inicio;
	private int fim;
	private double factor;
    static int numSteps =	10000000;
    int sum = 0;
    
    int begin, end ;
    public montecarlo( int inicio, int fim ) {
 		this.inicio = inicio;
 		this.fim = fim;
 		System.out.println("bloco: " + inicio +"," + fim+ "this is my sum:"+sum);
 	}

     public montecarlo() {
 		// TODO Auto-generated constructor stub
 	}
	public void run() {
		

	    double rand1 = (Math.random() * 2) - 1; 
	    double rand2 = (Math.random() * 2) - 1;
		for(int i=inicio;i < fim;i++){
		
			double randX= ((Math.random() * 2) - 1);//range -1 to 1
			double randY= ((Math.random() * 2) - 1);//range -1 to 1
			//distance from (0,0) = sqrt((x-0)^2+(y-0)^2)
			double distt=	Math.sqrt(randX * randX + randY * randY);
			//double dist= Math.hypot(randX, randY);
			if(distt <= 1){//circle with diameter of 2 has radius of 1
				sum++;
			}
		}
		 
	}
    
	public static void main(String[] args) throws Exception{
		 int numprocessadores = Runtime.getRuntime().availableProcessors();
		  long startTime = System.currentTimeMillis();
		  double supersum=0;
		  double supersquare=0;
		 int bloco = numSteps / numprocessadores;
			
		 montecarlo[] listathreads = new montecarlo[numprocessadores];
			
			for (int i=1; i<numprocessadores+1; i++) {
				int primeiro = (i-1)*bloco;
				int ultimo = (i)*bloco;
							
				montecarlo t = new montecarlo();
				t.inicio=primeiro;
				t.fim=ultimo;
				
				
				t.start();

				listathreads[i-1] = t;
				
			}
		
		//montecarlo t = new montecarlo( (numprocessadores-1)*bloco, numSteps-1 );
		//	t.start();
		//listathreads[numprocessadores-1] = t;
		
			
			for (int i=0; i<listathreads.length; i++){
		
			//	supersum += listathreads[i].sum;
			
				
				listathreads[i].join();
					supersum += listathreads[i].sum;
			}
			
			
		//	supersum=listathreads[numprocessadores-1].sum;
	 
	 //       supersum =thread1.sum + thread2.sum +thread3.sum +thread4.sum ;
	//		supersum=listathreads[0].sum +listathreads[1].sum+listathreads[2].sum+listathreads[3].sum;
	        long endTime = System.currentTimeMillis();

	       double pi = (4.0 *(double)( supersum/numSteps));

	        System.out.println("Value of pi: " + pi);

	        System.out.println("Calculated in " +
	                (endTime - startTime) + " milliseconds");

	}


public static int getPi(int begin, int end){
	int inCircle = 0 ;
	//Random ran = new Random(); 
    double rand1 = Math.random(); 
	//double rand2	=	((Math.random()* 2) - 1);
	//double dist= Math.sqrt(rand1 * rand1 + rand2 * rand2);
	for(int i= begin;i < end;i++){
		//a square with a side of length 2 centered at 0 has 
		//x and y range of -1 to 1
		double randX= (rand1);//range -1 to 1
		double randY= (rand1);//range -1 to 1
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
