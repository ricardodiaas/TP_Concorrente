

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
        public final ActorRef actorprimo;
        public CalculaIntervaloMessage(long num, ActorRef actorprimo) {
            this.num = num;
            this.actorprimo = actorprimo;
        }
    }
    
    @SuppressWarnings("serial")
	public static class CalculaPrimoMessage implements Serializable {
        public final long num;
        public CalculaPrimoMessage(long num) {
            this.num = num;
        }
    }
    @SuppressWarnings("serial")
	public static class RespostaPrimoMessage implements Serializable {
        public final double eprimo;
        public RespostaPrimoMessage(double i) {
            this.eprimo = i;
        }
    }
    @SuppressWarnings("serial")
	public static class QuantidadeIntervaloMessage implements Serializable {
    		public final double qnt;
        public QuantidadeIntervaloMessage(double qnt) {
    			this.qnt = qnt;
        }
    }
    
    public static class Controlador extends UntypedActor {
        long contaRespostas;
        double sum = 0;
        ActorRef actorPrimo;
        ActorRef actorIniciador;
        long kkk;
        public void onReceive(Object message) {
        	
          if (message instanceof CalculaIntervaloMessage) {	
        	  
            this.contaRespostas= ((CalculaIntervaloMessage) message).num;
            kkk  = ((CalculaIntervaloMessage) message).num;
            this.actorPrimo = ((CalculaIntervaloMessage) message).actorprimo;
            this.actorIniciador = getSender();
            	
            for (long i=1; i<=contaRespostas; i++){
              actorPrimo.tell( new CalculaPrimoMessage(i), getSelf() );
              if(i==contaRespostas)System.out.println("FIM");
            }
            	
            	System.out.println("Controlador: envio CalculaPrimoMessage " + contaRespostas);

            } else if (message instanceof RespostaPrimoMessage) {
            	
            	if ( ((RespostaPrimoMessage) message).eprimo >0 )
            		sum = ((RespostaPrimoMessage) message).eprimo;
                contaRespostas--;
       
        
            	double pi;
          

                pi = 4.0 *( sum / kkk ); 
          
    			//pi =4.0*sum;
            	
    			System.out.println("SUM "+sum);
            //	System.out.println("Controlador: ResultadoMessage "+pi);
    			if (contaRespostas == 0)
            		actorIniciador.tell( 
            			new QuantidadeIntervaloMessage(pi), getSelf() );
            	
            } else unhandled(message);
    			
        }
    }

    public static class Primo extends UntypedActor {
    	
        public void onReceive(Object message) {
        	
        	if (message instanceof CalculaPrimoMessage) {
        		
        		long num = ((CalculaPrimoMessage) message).num;
        		

        				
        		System.out.println("Primo: CalculaPrimoMessage " + num );
        		
        		double f = getPi(num);
        		getSender().tell(new RespostaPrimoMessage(f),getSelf());
        		System.out.println(getPi(num));
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
		for(int i= 0;i < numThrows;i++){

			double randX= (Math.random());//range -1 to 1
			double randY= (Math.random());//range -1 to 1
		
			if(randX * randX + randY * randY <= 1){
				inCircle++;
			}
		}
		return inCircle;
	}

    
    public static void main(String[] args) {
		long ini = System.nanoTime(); 
		
        // Create the actor system
        final ActorSystem sistema = ActorSystem.create("gregoryakka");

        // Create the "actor-in-a-box"
        final Inbox inbox = Inbox.create(sistema);
        
        // Create the 'controlador' actor
        final ActorRef controlador = sistema.actorOf(Props.create(Controlador.class), "controlador");

        // Create the 'Primo' actor
//        final ActorRef primo = sistema.actorOf(Props.create(Primo.class), "primo");

        // Create the Route actor; getContext() from actor
        // http://doc.akka.io/docs/akka/2.3.16/java/routing.html
        final ActorRef primo = sistema.actorOf(new RoundRobinPool(4).props(Props.create(Primo.class)), 
    		    "router");
    
        // definir tamanho conjunto
        long N = 100000;
        
        // Ask the 'greeter for the latest 'greeting'
        // Reply should go to the "actor-in-a-box"
        inbox.send(controlador, new CalculaIntervaloMessage( N, primo));
        
        // Wait 5 seconds for the reply with the 'greeting' message
        QuantidadeIntervaloMessage resposta = (QuantidadeIntervaloMessage) 
        		inbox.receive(Duration.create(500, TimeUnit.SECONDS));
        
        double quantidade = resposta.qnt;
		long fim = System.nanoTime(); 
		
        System.out.println("Main: quantidade em "+ N +":"+ quantidade);
        System.out.println("Calculo demorou (secs): "  
			    + String.format("%.6f", (fim-ini)/1.0e9) );
		
    	// shutdown
		sistema.shutdown();
	
    }

}
