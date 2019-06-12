import java.util.Scanner;

//GREGORY LEBINZ method
public class lebinzconcorrente extends Thread {

	private long inicio;
	private long fim;
	private double factor;
    static int numSteps = 10000000;

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
 
		  double supersum=0;
	
		  
			
			
			lebinzconcorrente[] listathreads = new lebinzconcorrente[numprocessadores];
			 Scanner reader = new Scanner(System.in);  // Reading from System.in
			 System.out.println("Enter the number of iterations: ");
			 int n = reader.nextInt(); // Scans the next token of the input as an int.
			 //once finished
			 reader.close();
			 int bloco = n / numprocessadores;
			
			for (int i=1; i<numprocessadores+1; i++) {
				int primeiro = (i-1)*bloco;
				int ultimo = (i)*bloco;
							
				lebinzconcorrente t = new lebinzconcorrente();
				t.begin=primeiro;
				t.end=ultimo;
				
				
				t.start();

				listathreads[i-1] = t;
				
			}
		
	
			for (int i=0; i<listathreads.length; i++){				
				listathreads[i].join();
				supersum += listathreads[i].sum;
			}

	        long endTime = System.currentTimeMillis();
	   
	        double pi = 4*(supersum) ;

	        System.out.println("Value of pi: " + pi);

	        System.out.println("Calculated in " +
	                (endTime - startTime) + " milliseconds");
		
	}
}