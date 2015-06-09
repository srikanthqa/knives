package com.github.knives.rxjava;

import static org.junit.Assert.*;

import org.junit.Test;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class AsynchronousObservableTest {

	@Test
	public void testCreate() throws InterruptedException {
		Subscription subscription = Observable.create(new OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				new Thread(new Runnable() {
					@Override
					public void run() {
			            for (int i = 0; i < 75; i++) {
			            	// if subscriber's subscription is unubscribed then stop
			                if (subscriber.isUnsubscribed()) {
			                    return;
			                }
			                subscriber.onNext(String.format("value_%d", i));
			            }
			            
			            // after sending all values we complete the sequence
			            if (!subscriber.isUnsubscribed()) {
			                subscriber.onCompleted();
			            }						
					}
				}).start();
			}
		}).subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
                System.out.println("Hello " + t + "!");
			}
		});
		
		// Observable<T> is not Future<T>
		// there is no block get
		// It is more like Stream<T>
		// calling unsubscribe() on Subscription on shutdown
		Thread.sleep(1000);
		
		
		// rxjava from netflix is very similar to parseq from linkedin
		// at least in same category of handling parallelism / concurrency
		subscription.unsubscribe();
	}

}
