//GREGORY LEBINZ method
public class lebinzconcorrente extends Thread {

	private long inicio;
	private long fim;
	private double factor;
    static int numSteps = 10000;

    double sum ;
    int begin, end ;
  
    public lebinzconcorrente( int inicio, int fim ) {
		this.inicio = inicio;
		this.fim = fim;
		System.out.println("bloco: " + inicio +"," + fim+ "this is my sum:"+sum);
	}

    public lebinzconcorrente() {
		// TODO Auto-generated constructor stub
	}
 

	public void run() {

        sum = 0.0 ;

     
    //    for(long i = inicio+1 ; i < fim ; i++){
     for(long i = begin;i<end;i++){
        	if(!isPar(i)){
				factor=-1.0;
			}else{
				factor=1.0;
			}
        	sum+=factor/(2*i+1);
        
        }
    }
    public static boolean isPar(long number)
	{ 
		if(number%2==0){
			return true;
		}else{
			return false;
		}
		}


	
	public static void main(String[] args) throws Exception {
		  long startTime = System.currentTimeMillis();
		  int numprocessadores = Runtime.getRuntime().availableProcessors();
/*
		  tryconcorrente1 thread1 = new tryconcorrente1();
	        thread1.begin = 0 ;
	        thread1.end = numSteps / 4 ;

	        tryconcorrente1 thread2 = new tryconcorrente1();
	        thread2.begin = numSteps / 4 ;
	        thread2.end =  numSteps  / 2 ;

	        tryconcorrente1 thread3 = new tryconcorrente1();
	        thread3.begin = numSteps / 2 ;
	        thread3.end = 3 * numSteps / 4 ;

	        tryconcorrente1 thread4 = new tryconcorrente1();
	        thread4.begin = 3 * numSteps / 4 ;
	        thread4.end = numSteps ;

	        thread1.start();
	        thread2.start();
	        thread3.start();
	        thread4.start();

	        thread1.join();
	        thread2.join();
	        thread3.join();
	        thread4.join();

		*/  
		  double supersum=0;
	
		  
			int bloco = numSteps / numprocessadores +1;
			
			lebinzconcorrente[] listathreads = new lebinzconcorrente[numprocessadores];
			
			for (int i=1; i<numprocessadores; i++) {
				int primeiro = (i-1)*bloco;
				int ultimo = (i)*bloco;
							
				lebinzconcorrente t = new lebinzconcorrente();
				t.begin=primeiro;
				t.end=ultimo;
				
				
				t.start();

				listathreads[i-1] = t;
				
			}
		
			lebinzconcorrente t = new lebinzconcorrente( (numprocessadores-1)*bloco, numSteps-1 );
		//	t.start();
			listathreads[numprocessadores-1] = t;
		
		
				
			
			for (int i=0; i<numprocessadores; i++){
		
				supersum += listathreads[i].sum;
				
				listathreads[i].join();
			}
			
	
	 
	 //       supersum =thread1.sum + thread2.sum +thread3.sum +thread4.sum ;
	        long endTime = System.currentTimeMillis();
	   
	        double pi = 4*(supersum) ;

	        System.out.println("Value of pi: " + pi);

	        System.out.println("Calculated in " +
	                (endTime - startTime) + " milliseconds");
		
	}
}