package com.github.knives.bouncycastle
/**
 * http://waheedtechblog.blogspot.com/2012/10/how-to-create-self-signed-certificates.html
 */

import java.security.KeyPairGenerator
import java.security.KeyPair
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.SecureRandom
import org.bouncycastle.x509.X509V3CertificateGenerator
import org.bouncycastle.jce.X509Principal
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

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
keyStore.setKeyEntry("YOUR_CERTIFICATE_NAME", "YOUR_KEY", "YOUR_PASSWORD".toCharArray(), [ cert ] as Certificate[]);
File file = new File(".", "keystore.test");
keyStore.store( new FileOutputStream(file), "YOUR_PASSWORD".toCharArray() );