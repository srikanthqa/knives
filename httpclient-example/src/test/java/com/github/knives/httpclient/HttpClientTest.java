package com.github.knives.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpClientTest {

	private Thread serverThread;
	
	@Before
	public void init() {
		serverThread = createServerThread(9000);
		serverThread.start();
	}
	
	@After
	public void shutdown() {
		serverThread.interrupt();
	}
	
	@Test
	public void testCloseBeforeRead() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createMinimal();
		CloseableHttpResponse httpResponse = get(httpClient, new HttpGet("http://localhost:9000/"));
		String response = EntityUtils.toString(httpResponse.getEntity());
		System.out.println(response);
	}
	
	private CloseableHttpResponse get(CloseableHttpClient httpClient, HttpGet httpGet) throws ClientProtocolException, IOException {
		try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
			return httpResponse;
		}
	}

	private Thread createServerThread(final int port) {
		return new Thread() {
			@Override
			public void run() {
				try (ServerSocket serverSocket = new ServerSocket(port)) {
					while (!Thread.interrupted()) {
						try (Socket clientSocket = serverSocket.accept();
							 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
							 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
							System.out.println("Socket accepted");

							out.println("HTTP/1.1 200 OK");
							out.println("Content-Length: 24");
							out.println("Connection: Keep-Alive");
							out.println();
							out.println("hello world");
							Thread.sleep(2000);
							out.println("hello world");
						} catch (InterruptedIOException | InterruptedException interruptException) {
							this.interrupt();
						} catch (IOException ignore) {}
					}
				} catch (IOException ignore) {}
				
				System.out.println("Server terminated");
			}
		};
	}
}
