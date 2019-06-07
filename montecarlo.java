
public class montecarlo extends Thread {

	private int inicio;
	private int fim;
	private double factor;
    static int numSteps = 10000;
    int sum = 0 ;
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
		 for(long i =inicio;i<fim;i++){
			sum = getPi(inicio,fim,sum);
		 }
	}
    
	public static void main(String[] args) throws Exception{
		 int numprocessadores = Runtime.getRuntime().availableProcessors();
		  long startTime = System.currentTimeMillis();
		  double supersum=0;
			
		 int bloco = numSteps / numprocessadores +1;
			
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
		
			montecarlo t = new montecarlo( (numprocessadores-1)*bloco, numSteps-1 );
		//	t.start();
	//		listathreads[numprocessadores-1] = t;
		
			
			for (int i=0; i<numprocessadores-2; i++){
		
				supersum += listathreads[i].sum;
				
				listathreads[i].join();
			}
			
		//	supersum=listathreads[numprocessadores-1].sum;
	 
	 //       supersum =thread1.sum + thread2.sum +thread3.sum +thread4.sum ;
	        long endTime = System.currentTimeMillis();

	        double pi = (4.0 * (supersum / numSteps));

	        System.out.println("Value of pi: " + pi);

	        System.out.println("Calculated in " +
	                (endTime - startTime) + " milliseconds");

	}


public static int getPi(int begin, int end, int inCircle){
	//int inCircle = 0 ;
	for(int i= begin;i < end;i++){
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
