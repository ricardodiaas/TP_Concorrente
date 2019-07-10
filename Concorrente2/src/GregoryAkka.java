
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import scala.concurrent.duration.Duration;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;



public class GregoryAkka {
	

    @SuppressWarnings("serial")
	public static class CalculaIntervaloMessage implements Serializable {
        public final long num;
        public final ActorRef actorPI;
        public CalculaIntervaloMessage(long num, ActorRef actorPI) {
            this.num = num;
            this.actorPI = actorPI;
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

        public void onReceive(Object message) {
        	
          if (message instanceof CalculaIntervaloMessage) {	
        	  
            this.contaRespostas= ((CalculaIntervaloMessage) message).num;
            this.actorPI = ((CalculaIntervaloMessage) message).actorPI;
            this.actorIniciador = getSender();
            	
            for (long i=1; i<=contaRespostas; i++){
              actorPI.tell( new CalculaPIcalcMessage(i), getSelf() );
              if(i==contaRespostas)System.out.println("FIM");
            }
            	
            	System.out.println("Controlador: envio CalculaPIcalcMessage " + contaRespostas);

            } else if (message instanceof RespostaPIcalcMessage) {
            	
            	if ( ((RespostaPIcalcMessage) message).calcpi != 0 ){
                    sum = sum+((RespostaPIcalcMessage) message).calcpi;
                    contaRespostas -= 1;
                }

    			//System.out.println("sum "+ sum);
                System.out.println("contagem: " + contaRespostas);

    			if (contaRespostas == 0){
                    double pi;
                    pi = 4.0 * (1+sum);
                    System.out.println("PI " + pi);
                    actorIniciador.tell(new PIMessage(pi), getSelf());
                }

            	
            } else unhandled(message);
    			
        }

    }

    public static class PIcalc extends UntypedActor {
    	
        public void onReceive(Object message) {
        	
        	if (message instanceof CalculaPIcalcMessage) {
        		
        		long num = ((CalculaPIcalcMessage) message).num;
        		System.out.println("PRINT"+num);
        		double f = Calcula(num);
        		getSender().tell(new RespostaPIcalcMessage(f),getSelf());
        		//System.out.println(f);
        	} else unhandled(message);
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
    public static double Calcula(long number){
    
    	double factor;
    	double value=0;
    	
			if(!isPar(number)){
				factor=-1.0;
			}else{
				factor=1.0;
			}
			value+=factor/(2* number+1);

    	return(value);
    }

    
    public static void main(String[] args) {
		long ini = System.nanoTime(); 
		
        // Create the actor system
        final ActorSystem sistema = ActorSystem.create("gregoryakka");

        // Create the "actor-in-a-box"
        final Inbox inbox = Inbox.create(sistema);
        
        // Create the 'controlador' actor
        final ActorRef controlador = sistema.actorOf(Props.create(Controlador.class), "controlador");

        // Create the Route actor; getContext() from actor
        // http://doc.akka.io/docs/akka/2.3.16/java/routing.html
        final ActorRef PIcalc = sistema.actorOf(new RoundRobinPool(4).props(Props.create(PIcalc.class)), 
    		    "router");
    
        // definir tamanho conjunto
        long N = 100;
        
        // Ask the 'greeter for the latest 'greeting'
        // Reply should go to the "actor-in-a-box"
        inbox.send(controlador, new CalculaIntervaloMessage( N, PIcalc));
        
        // Wait 5 seconds for the reply with the 'greeting' message
        PIMessage resposta = (PIMessage) 
        		inbox.receive(Duration.create(500, TimeUnit.SECONDS));
        
        double pi = resposta.pi;
		long fim = System.nanoTime(); 
		
        System.out.println("Valor de pi: "+ pi);
        System.out.println("Calculo demorou (secs): "  
			    + String.format("%.6f", (fim-ini)/1.0e9) );
		
    	// shutdown
		sistema.shutdown();
	
    }

}
