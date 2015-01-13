package com.github.knives.java.security;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Date;

import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

public class CreateCertificate {

	public static void main(String[] args) throws Exception {
		// register security provider
		Security.addProvider(new BouncyCastleProvider());

		// 1. Create a public/private key pair for the new certificate
		final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024, new SecureRandom());
		final KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 2. Create new certificate Structure
		final X509V3CertificateGenerator v3CertGen =  new X509V3CertificateGenerator();
		v3CertGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
		v3CertGen.setIssuerDN(new X509Principal("CN=cn, O=o, L=L, ST=il, C= c"));
		v3CertGen.setNotBefore(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24));
		v3CertGen.setNotAfter(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365*10)));
		v3CertGen.setSubjectDN(new X509Principal("CN=cn, O=o, L=L, ST=il, C= c"));
		v3CertGen.setPublicKey(keyPair.getPublic());
		v3CertGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

		final Certificate cert = v3CertGen.generateX509Certificate(keyPair.getPrivate());

		// 3. Store the Certificate with the private key
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(null, null);
		keyStore.setKeyEntry("YOUR_CERTIFICATE_NAME", keyPair.getPrivate(), "YOUR_PASSWORD".toCharArray(), new Certificate[] { cert });
		File file = new File(".", "keystore.test");
		keyStore.store( new FileOutputStream(file), "YOUR_PASSWORD".toCharArray() );
	}

}
