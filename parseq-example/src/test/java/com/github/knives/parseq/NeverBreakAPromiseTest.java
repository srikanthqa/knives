package com.github.knives.parseq;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkedin.parseq.BaseTask;
import com.linkedin.parseq.Context;
import com.linkedin.parseq.Engine;
import com.linkedin.parseq.EngineBuilder;
import com.linkedin.parseq.Task;
import com.linkedin.parseq.Tasks;
import com.linkedin.parseq.promise.Promise;
import com.linkedin.parseq.promise.Promises;
import com.linkedin.parseq.promise.SettablePromise;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

public class NeverBreakAPromiseTest {
	final private static Logger LOG = LoggerFactory.getLogger(NeverBreakAPromiseTest.class);

	private static AsyncHttpClient HTTP_CLIENT = new AsyncHttpClient();

	@Test
	public void testGetContentType() throws Exception {
		final int numCores = Runtime.getRuntime().availableProcessors();
		final ExecutorService taskScheduler = Executors.newFixedThreadPool(numCores + 1);
		final ScheduledExecutorService timerScheduler = Executors.newSingleThreadScheduledExecutor(); 

		final Engine engine = new EngineBuilder()
		        .setTaskExecutor(taskScheduler)
		        .setTimerScheduler(timerScheduler)
		        .build();

		final Task<String> googleContentType = getContentType("http://www.google.com");
		final Task<String> bingContentType = getContentType("http://www.bing.com");
		final Task<String> yahooContentType = getContentType("http://www.yahoo.com");
		final Task<List<String>> fetchContentTypes = Tasks.par(googleContentType, bingContentType, yahooContentType);
		
		engine.run(fetchContentTypes);
		
		// wait to print all the results
		fetchContentTypes.await(5, TimeUnit.SECONDS);
		LOG.info("All content-types: [{}]", fetchContentTypes.get());
	}

	public static Task<String> getContentType(final String url) {
		return new BaseTask<String>("Get content type: " + url) {
			@Override
			protected Promise<String> run(final Context context)
					throws Exception {
				// We only need to make a HEAD request to get the Content-Type
				// header.
				final Request request = new RequestBuilder()
					.setUrl(url)
					.setMethod("HEAD")
					.build();

				// Create a task to make the HTTP request
				final Task<Response> httpResponse = new HttpRequestTask(request);
				
				// Create a task to extract the Content-Type header from the
				// response
				final Task<String> contentType = Tasks.callable(
					"Extract content type: " + url, new Callable<String>() {
						@Override
						public String call() throws Exception {
							// no wait since we are using Tasks.seq
							return httpResponse.get().getContentType();
						}
					});

				// Sequence the tasks
				final Task<String> plan = Tasks.seq(httpResponse, contentType);
				context.run(plan);

				return plan;
			}
		};
	}

	public static class HttpRequestTask extends BaseTask<Response> {

		final private Request request;

		public HttpRequestTask(Request request) {
			super("Http request: " + request);
			this.request = request;
		}

		@Override
		protected Promise<? extends Response> run(Context context)
				throws Throwable {
			final SettablePromise<Response> promise = Promises.settable();

			HTTP_CLIENT.executeRequest(request,
				new AsyncCompletionHandler<Response>() {
					@Override
					public Response onCompleted(Response response) throws Exception {
						LOG.info("onCompleted, receive response for request [{}]", request);
						promise.done(response);
						return response;
					}

					@Override
					public void onThrowable(Throwable t) {
						promise.fail(t);
					}
				});

			return promise;
		}

	}
}
