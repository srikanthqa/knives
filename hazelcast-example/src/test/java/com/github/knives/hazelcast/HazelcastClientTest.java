package com.github.knives.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HazelcastClientTest {

	public static void main(String[] args) {
		
		ClientNetworkConfig networkConfig = new ClientNetworkConfig();
		networkConfig.addAddress("127.0.0.1:5701");
		
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setNetworkConfig(networkConfig);
		
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		IMap map = client.getMap("customers");
		System.out.println("Map Size:" + map.size());
	}

}
