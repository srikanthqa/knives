package com.github.knives.java.security;

import java.security.Provider;
import java.security.Security;

import com.github.knives.java.secure.Utils;

/**
 * List the currently installed providers in the Java Runtime
 */
public class ListProviders {
	public static void main(String[] args) {
		Provider[] providers = Security.getProviders();

		for (int i = 0; i != providers.length; i++) {
			System.out.println("Name: "
					+ providers[i].getName()
					+ Utils.makeBlankString(15 - providers[i].getName()
							.length()) + " Version: "
					+ providers[i].getVersion());
		}
	}
}
