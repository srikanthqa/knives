package com.github.knives.groovy.transform

import groovy.transform.WithReadLock
import groovy.transform.WithWriteLock

public class ResourceProvider {
	private final Map<String, String> data = new HashMap<String, String>();

	@WithReadLock
	public String getResource(String key) throws Exception {
		return data.get(key);
	}

	@WithWriteLock
	public void putResource(String key, String value = "") throws Exception {
		data.put(key, value);
	}
}


ResourceProvider provider = new ResourceProvider()
provider.putResource("provider")
println provider.getResource("provider")