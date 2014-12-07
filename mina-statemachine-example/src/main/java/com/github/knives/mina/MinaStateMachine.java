package com.github.knives.mina;

import org.apache.mina.statemachine.StateMachine;
import org.apache.mina.statemachine.StateMachineFactory;
import org.apache.mina.statemachine.StateMachineProxyBuilder;
import org.apache.mina.statemachine.annotation.State;
import org.apache.mina.statemachine.annotation.Transition;
import org.apache.mina.statemachine.annotation.Transitions;

/**
 * http://mina.apache.org/mina-project/userguide/ch14-state-machine/ch14-state-machine.html
 */
class MinaStateMachine {
	public static interface TapeDeck {
		void load(String nameOfTape);
		void eject();
		void start();
		void pause();
		void stop();
		void play();
	}
	
	public static class TapeDeckHandler {
		@State public static final String EMPTY = "Empty";
		@State public static final String LOADED = "Loaded";
		@State public static final String PLAYING = "Playing";
		@State public static final String PAUSED = "Paused";
	
		@Transition(on = "load", in = TapeDeckHandler.EMPTY, next = TapeDeckHandler.LOADED)
		public void loadTape(String nameOfTape) {
			System.out.println("Tape '" + nameOfTape + "' loaded");
		}
	
		@Transitions({
			@Transition(on = "play", in = TapeDeckHandler.LOADED, next = TapeDeckHandler.PLAYING),
			@Transition(on = "play", in = TapeDeckHandler.PAUSED, next = TapeDeckHandler.PLAYING)
		})
		public void playTape() {
			System.out.println("Playing tape");
		}
	
		@Transition(on = "pause", in = TapeDeckHandler.PLAYING, next = TapeDeckHandler.PAUSED)
		public void pauseTape() {
			System.out.println("Tape paused");
		}
	
		@Transition(on = "stop", in = TapeDeckHandler.PLAYING, next = TapeDeckHandler.LOADED)
		public void stopTape() {
			System.out.println("Tape stopped");
		}
	
		@Transition(on = "eject", in = TapeDeckHandler.LOADED, next = TapeDeckHandler.EMPTY)
		public void ejectTape() {
			System.out.println("Tape ejected");
		}
	}
	
	public static void main(String[] args) {
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

