package com.github.knives.httpclient;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.FutureRequestExecutionMetrics;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class FutureRequestExecutionServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(FutureRequestExecutionServiceTest.class);
	
	private static final class OkidokiHandler implements ResponseHandler<Boolean> {
	    public Boolean handleResponse(
	            final HttpResponse response) throws ClientProtocolException, IOException {
	        return response.getStatusLine().getStatusCode() == 200;
	    }
	}
	
	private final class MyCallback implements FutureCallback<Boolean> {
	    public void failed(final Exception ex) {
	        LOG.error("fail to call", ex);
	    }

	    public void completed(final Boolean result) {
	        LOG.info("receive [{}]", result);
	    }

	    public void cancelled() {
	        LOG.info("cancelled");
	    }
	}
	
	@Test
	public void test() throws InterruptedException, ExecutionException {
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnPerRoute(5).build();
		ExecutorService executorService = Executors.newFixedThreadPool(5, 
				new ThreadFactoryBuilder().setNameFormat("http-pool-%s").build());
		
		FutureRequestExecutionService futureRequestExecutionService =
		    new FutureRequestExecutionService(httpClient, executorService);
		
		HttpRequestFutureTask<Boolean> task = futureRequestExecutionService.execute(
			    new HttpGet("http://www.google.com"), HttpClientContext.create(),
			    new OkidokiHandler(), new MyCallback());
		
		// blocks until the request complete and then returns true if you can connect to Google
		boolean ok = task.get();
		
		assertTrue(ok);
		
		printTaskMetrics(task);
		
		printFutureRequestExecutionMetrics(futureRequestExecutionService.metrics());
	}

	
	private void printTaskMetrics(HttpRequestFutureTask<?> task) {
		// returns the timestamp the task was scheduled
		LOG.info("task.scheduledTime=[{}]", task.scheduledTime());
		
		// returns the timestamp when the task was started
		LOG.info("task.startedTime=[{}]", task.startedTime());

		// returns the timestamp when the task was done executing
		LOG.info("task.endedTime=[{}]", task.endedTime());
		
		// returns the duration of the http request
		LOG.info("task.requestDuration=[{}]", task.requestDuration());
		
		// returns the duration of the task from the moment it was scheduled
		LOG.info("task.taskDuration[{}]", task.taskDuration());
	}
	
	private void printFutureRequestExecutionMetrics(FutureRequestExecutionMetrics metrics) {
		// currently active connections
		LOG.info("metrics.getActiveConnectionCount=[{}]", metrics.getActiveConnectionCount());
		
		// currently scheduled connections
		LOG.info("metrics.getScheduledConnectionCount=[{}]", metrics.getScheduledConnectionCount());
		
		// total number of successful requests
		LOG.info("metrics.getSuccessfulConnectionCount=[{}]", metrics.getSuccessfulConnectionCount());
		
		 // average request duration
		LOG.info("metrics.getSuccessfulConnectionAverageDuration=[{}]", metrics.getSuccessfulConnectionAverageDuration());
		
		// total number of failed tasks
		LOG.info("metrics.getFailedConnectionCount=[{}]", metrics.getFailedConnectionCount());
		
		// average duration of failed tasks
		LOG.info("metrics.getFailedConnectionAverageDuration=[{}]", metrics.getFailedConnectionAverageDuration());
		
		// total number of tasks scheduled
		LOG.info("metrics.getTaskCount=[{}]", metrics.getTaskCount());
		
		// total number of requests
		LOG.info("metrics.getRequestCount=[{}]", metrics.getRequestCount());
		
		// average request duration
		LOG.info("metrics.getRequestAverageDuration=[{}]", metrics.getRequestAverageDuration());
		
		// average task duration
		LOG.info("metrics.getTaskAverageDuration=[{}]", metrics.getTaskAverageDuration());
	}
}
