package com.github.knives.java.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.util.io.pem.PemWriter;

/**
 * Basic example of using a CertificateFactory.
 */
public class PEMCertificateFactoryExample {
	public static void main(String[] args) throws Exception {
		// create the keys
		KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");

		kpGen.initialize(1024, new SecureRandom());

		KeyPair pair = kpGen.generateKeyPair();

		// create the input stream
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();

		PemWriter pemWrt = new PemWriter(new OutputStreamWriter(bOut));

		pemWrt.writeObject(X509V1CreateExample.generateV1Certificate(pair));

		pemWrt.close();

		bOut.close();

		System.out.println(Utils.toString(bOut.toByteArray()));

		InputStream in = new ByteArrayInputStream(bOut.toByteArray());

		// create the certificate factory
		CertificateFactory fact = CertificateFactory.getInstance("X.509", "BC");

		// read the certificate
		X509Certificate x509Cert = (X509Certificate) fact
				.generateCertificate(in);

		System.out.println("issuer: " + x509Cert.getIssuerX500Principal());
	}
}
