package com.github.knives.java.security;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

import com.github.knives.java.secure.Utils;
import com.github.knives.java.secure.X509V1CreateExample;
import com.github.knives.java.secure.X509V3CreateExample;

/**
 * Basic example of using a CertificateFactory.
 */
public class CollectionMultipleCertificateExample
{
    public static void main(String[] args)
        throws Exception
    {
        // create the keys
        KeyPair          pair = Utils.generateRSAKeyPair();
        
        // create the input stream
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        
        bOut.write(X509V1CreateExample.generateV1Certificate(pair).getEncoded());
        bOut.write(X509V3CreateExample.generateV3Certificate(pair).getEncoded());
        
        bOut.close();
        
        InputStream in = new ByteArrayInputStream(bOut.toByteArray());
        
        // create the certificate factory
        CertificateFactory fact = CertificateFactory.getInstance("X.509","BC");
        
        // read the certificates
        Collection         collection = fact.generateCertificates(in);
        
        Iterator it = collection.iterator();
        while (it.hasNext())
        {
            System.out.println("version: " + ((X509Certificate)it.next()).getVersion());
        }
    }
}
