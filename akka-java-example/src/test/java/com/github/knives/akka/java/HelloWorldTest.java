package com.github.knives.akka.java;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class HelloWorldTest {

	public static class Greeting implements Serializable {
		public final String who;

		public Greeting(String who) {
			this.who = who;
		}
	}

	public static class GreetingActor extends UntypedActor {
		public GreetingActor() { }

		private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

		public void onReceive(Object message) throws Exception {
			if (message instanceof Greeting)
				log.info("Hello " + ((Greeting) message).who);
		}
	}

	@Test
	public void test() throws Exception {

		ActorSystem system = ActorSystem.create("MySystem");
		ActorRef greeter = system.actorOf(Props.create(GreetingActor.class), "greeter");
		greeter.tell(new Greeting("Charlie Parker"), ActorRef.noSender());
		TimeUnit.SECONDS.sleep(1);
	}

}
