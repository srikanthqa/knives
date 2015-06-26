package com.github.knives.jgit.auth;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class SystemPropertiesCredentialsProvider extends UsernamePasswordCredentialsProvider {

	public SystemPropertiesCredentialsProvider() {
		super(System.getProperty("git.user"), System.getProperty("git.pass"));
	}

}
