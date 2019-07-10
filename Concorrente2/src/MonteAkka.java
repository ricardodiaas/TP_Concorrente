

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import scala.concurrent.duration.Duration;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;



public class MonteAkka {
	

    @SuppressWarnings("serial")
	public static class CalculaIntervaloMessage implements Serializable {
        public final long num;
        public final ActorRef actorPI;
        public CalculaIntervaloMessage(long num, ActorRef actorpi) {
            this.num = num;
            this.actorPI = actorpi;
        }
    }
    
    @SuppressWarnings("serial")
	public static class CalculaPIcalcMessage implements Serializable {
        public final long num;
        public CalculaPIcalcMessage(long num) {
            this.num = num;
        }
    }
    @SuppressWarnings("serial")
	public static class RespostaPIcalcMessage implements Serializable {
        public final double calcpi;
        public RespostaPIcalcMessage(double i) {
            this.calcpi = i;
        }
    }
    @SuppressWarnings("serial")
	public static class PIMessage implements Serializable {
    		public final double pi;
        public PIMessage(double pi) {
    			this.pi = pi;
        }
    }
    
    public static class Controlador extends UntypedActor {
        long contaRespostas;
        double sum = 0;
        ActorRef actorPI;
        ActorRef actorIniciador;
        long kkk;
        public void onReceive(Object message) {
        	
          if (message instanceof CalculaIntervaloMessage) {	
        	  
            this.contaRespostas= ((CalculaIntervaloMessage) message).num;
            kkk  = ((CalculaIntervaloMessage) message).num;
            this.actorPI = ((CalculaIntervaloMessage) message).actorPI;
            this.actorIniciador = getSender();
            	
            for (long i=1; i<=contaRespostas; i++){
              actorPI.tell( new CalculaPIcalcMessage(i), getSelf() );
              if(i==contaRespostas)System.out.println("FIM");
            }
            	
            	

            } else if (message instanceof RespostaPIcalcMessage) {
            	
            	if ( ((RespostaPIcalcMessage) message).calcpi >0 )
            		sum = sum+ ((RespostaPIcalcMessage) message).calcpi;
                contaRespostas--;
       
        
            	double pi;
          

                pi = 4.0 *( sum / kkk ); 

    			if (contaRespostas == 0)
            		actorIniciador.tell( 
            			new PIMessage(pi), getSelf() );
            	
            } else unhandled(message);
    			
        }
    }

    public static class Primo extends UntypedActor {
    	
        public void onReceive(Object message) {
        	
        	if (message instanceof CalculaPIcalcMessage) {
        		
        		long num = ((CalculaPIcalcMessage) message).num;
        		

        				
 
        		
        		double f = getPi(num);
        		getSender().tell(new RespostaPIcalcMessage(f),getSelf());
        		//System.out.println(getPi(num));
        	} else unhandled(message);
        }
    }
   
    public static boolean isPrime(long number)
	{ 
	  if (number < 2) return false; 
	  for (long i=2; i<number; i++) 
	    if (number % i == 0) return false; 
	  return true; 
	} 
    public static boolean isPar(long number)
   	{ 
   		if(number%2==0){
   			return true;
   		}else{
   			return false;
   			
   		}
   		}
    public static double getPi(long numThrows){
		int inCircle= 0;
	

			double randX= (Math.random());//range -1 to 1
			double randY= (Math.random());//range -1 to 1
		
			if(randX * randX + randY * randY <= 1){
				inCircle++;
			}
		
		return inCircle;
	}

    
    public static void main(String[] args) {
		long ini = System.nanoTime(); 
		
        // Create the actor system
        final ActorSystem sistema = ActorSystem.create("monteakka");

        // Create the "actor-in-a-box"
        final Inbox inbox = Inbox.create(sistema);
        
        // Create the 'controlador' actor
        final ActorRef controlador = sistema.actorOf(Props.create(Controlador.class), "controlador");

        // Create the 'Primo' actor
//        final ActorRef primo = sistema.actorOf(Props.create(Primo.class), "primo");

        // Create the Route actor; getContext() from actor
        // http://doc.akka.io/docs/akka/2.3.16/java/routing.html
        final ActorRef PIcalc = sistema.actorOf(new RoundRobinPool(4).props(Props.create(Primo.class)), 
    		    "router");
    
        // definir tamanho conjunto
        long N = 100000;
        
        // Ask the 'greeter for the latest 'greeting'
        // Reply should go to the "actor-in-a-box"
        inbox.send(controlador, new CalculaIntervaloMessage( N, PIcalc));
        
        // Wait 5 seconds for the reply with the 'greeting' message
        PIMessage resposta = (PIMessage) 
        		inbox.receive(Duration.create(500, TimeUnit.SECONDS));
        
        double pi = resposta.pi;
		long fim = System.nanoTime(); 
		
        System.out.println("valor de pi: "+pi);
        System.out.println("Calculo demorou (secs): "  
			    + String.format("%.6f", (fim-ini)/1.0e9) );
		
    	// shutdown
		sistema.shutdown();
	
    }

}
