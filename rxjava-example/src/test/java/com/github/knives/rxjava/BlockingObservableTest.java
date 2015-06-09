package com.github.knives.rxjava;

import static org.junit.Assert.*;

import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;

public class BlockingObservableTest {

	@Test
	public void testFromIterable() {
		Observable.from(new String[] {"clark", "bruce", "diana", "hal"})
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println("Hello " + s + "!");
                }

		    });
	}

}
