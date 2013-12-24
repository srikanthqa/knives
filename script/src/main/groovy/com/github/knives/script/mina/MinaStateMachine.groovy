package com.github.knives.script.mina

import org.apache.mina.statemachine.StateMachineFactory
import org.apache.mina.statemachine.StateMachineProxyBuilder
import org.apache.mina.statemachine.annotation.State
import org.apache.mina.statemachine.annotation.Transition
import org.apache.mina.statemachine.annotation.Transitions

/**
 * http://mina.apache.org/mina-project/userguide/ch14-state-machine/ch14-state-machine.html
 */
class MinaStateMachine {
	public interface TapeDeck {
		void load(String nameOfTape);
		void eject();
		void start();
		void pause();
		void stop();
	}
	
	public class TapeDeckHandler {
		@State public static final String EMPTY   = "Empty";
		@State public static final String LOADED  = "Loaded";
		@State public static final String PLAYING = "Playing";
		@State public static final String PAUSED  = "Paused";
	}
	
	public class TapeDeckHandler {
		@State public static final String EMPTY = "Empty";
		@State public static final String LOADED = "Loaded";
		@State public static final String PLAYING = "Playing";
		@State public static final String PAUSED = "Paused";
	
		@Transition(on = "load", in = EMPTY, next = LOADED)
		public void loadTape(String nameOfTape) {
			System.out.println("Tape '" + nameOfTape + "' loaded");
		}
	
		@Transitions({
			@Transition(on = "play", in = LOADED, next = PLAYING),
			@Transition(on = "play", in = PAUSED, next = PLAYING)
		})
		public void playTape() {
			System.out.println("Playing tape");
		}
	
		@Transition(on = "pause", in = PLAYING, next = PAUSED)
		public void pauseTape() {
			System.out.println("Tape paused");
		}
	
		@Transition(on = "stop", in = PLAYING, next = LOADED)
		public void stopTape() {
			System.out.println("Tape stopped");
		}
	
		@Transition(on = "eject", in = LOADED, next = EMPTY)
		public void ejectTape() {
			System.out.println("Tape ejected");
		}
	}
	
	static main(args) {
		TapeDeckHandler handler = new TapeDeckHandler();
		StateMachine sm = StateMachineFactory.getInstance(Transition.class).create(TapeDeckHandler.EMPTY, handler);
		TapeDeck deck = new StateMachineProxyBuilder().create(TapeDeck.class, sm);
	
		deck.load("The Knife - Silent Shout");
		deck.play();
		deck.pause();
		deck.play();
		deck.stop();
		deck.eject();
	}

}
