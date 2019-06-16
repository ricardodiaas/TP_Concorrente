import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//GREGORY LEBINZ method
public class lebinzconcorrente extends Thread {

	private long inicio;
	private long fim;
	private double factor;

    private static double total=0;
    Lock lock = new ReentrantLock();	

    double sum ;
    long begin, end ;
  
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

        synchronized(this){ 
     for(long i = begin;i<end;i++){
        	if(!isPar(i)){
				factor=-1.0;
			}else{
				factor=1.0;
			}
        	sum+=factor/(2*i+1);
        	
        	
        
        }
     total +=sum;
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
		
		  int numprocess = Runtime.getRuntime().availableProcessors();
		  System.out.println("numero de nucleos disponiveis: "+numprocess+"\n");
			 Scanner reader = new Scanner(System.in);  // Reading from System.in
			 System.out.println("Indique numero de threads que deseja criar: \n");
			int numprocessadores = reader.nextInt(); // Scans the next token of the input as an int.
			 //once finished
			 reader.nextLine();
			// reader.close();
		  
			
			
			lebinzconcorrente[] listathreads = new lebinzconcorrente[numprocessadores];
			 System.out.println("Indique o numero de Iterações: ");
			 long n = reader.nextLong(); // Scans the next token of the input as an int.
			 //once finished
			 reader.close();
			 long inicio = System.nanoTime(); 
			 long bloco = n / numprocessadores;
			
			for (int i=1; i<numprocessadores+1; i++) {
				long primeiro = (i-1)*bloco;
				long ultimo = (i)*bloco;
							
				lebinzconcorrente t = new lebinzconcorrente();
				t.begin=primeiro;
				t.end=ultimo;
				
				
				t.start();

				listathreads[i-1] = t;
				
			}
		
	
			for (int i=0; i<listathreads.length; i++){				
				listathreads[i].join();
			//	supersum += listathreads[i].sum;
			}

	 
	   
	        double pi = 4*(total) ;
	        long endTime = System.nanoTime();
			double realpi = Math.PI;
			double error = (pi-realpi)/realpi*100;
			 System.out.println("Foram criadas "+listathreads.length+" threads \n");
				System.out.print("valor real de pi:"+realpi+"\n");
			System.out.print("meu valor de pi:"+pi+"\n");
			System.out.print("erro:"+error+"\n");
			System.out.println("Calculo demorou (secs): "  
				    + String.format("%.6f", (endTime-inicio)/1.0e9) );
		
	}
}