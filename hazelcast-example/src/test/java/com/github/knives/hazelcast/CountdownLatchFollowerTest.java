package com.github.knives.hazelcast;

import java.util.concurrent.TimeUnit;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;

public class CountdownLatchFollowerTest {
	public static void main(String[] args) throws InterruptedException {
	    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
	    ICountDownLatch latch = hazelcastInstance.getCountDownLatch( "countDownLatch" );
	    latch.countDown();
	    System.out.println( "Waiting" );
	    boolean success = latch.await( 10, TimeUnit.HOURS );
	    System.out.println( "Follower complete: " + success );
	    
	    hazelcastInstance.shutdown();
	}
}
