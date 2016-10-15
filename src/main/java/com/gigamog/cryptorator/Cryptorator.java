package com.gigamog.cryptorator;

import static com.gigamog.cryptorator.CryptoratorUtils.*;
import static com.gigamog.cryptorator.Propertizer.*;


import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.gigamog.cryptorator.exception.CryptoratorException;

public class Cryptorator {
	
	  public String encryptContent(String content, String def_pass, String salt) {
	        String encryptedContent = null;
	        try {
	            SecretKey key = this.gerateSecretKey(def_pass, salt);
	            encryptedContent = this.encrypt(key, content);
	            
	        } catch (CryptoratorException e) {
	        	throw new CryptoratorException(e.getMessage(), e);


	        }
	        return encryptedContent;
	    }

	    public String decryptContent(String cryptedContent, String def_pass, String salt) throws CryptoratorException {
	        String content = null;
	        try {
	            SecretKey key = this.gerateSecretKey(def_pass, salt);
	            content = this.decrypt(key, cryptedContent);
	        } catch (CryptoratorException e) {
	        	throw new CryptoratorException(e.getMessage(), e);
	        }
	        return content;
	    }

    public SecretKey  gerateSecretKey(String password, String salt){
    	try{

        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), getPBEIteration(), getSecretKeyLength());
        SecretKeyFactory factory = SecretKeyFactory.getInstance(getPBEAlgorithm());
        SecretKey tmp = factory.generateSecret(pbeKeySpec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), getSecretKeyAlgorithm());
        return secret;
    } catch (Exception e) {
        throw new CryptoratorException("Unable to get secret key", e);
    }
    	

		
    }
    

    public String encrypt(SecretKey secret, String cleartext) throws CryptoratorException {
        try {

            byte[] iv = generateIv();
            String ivHex = toHex(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);            
            Cipher encryptionCipher = Cipher.getInstance(getCipherAlgorythm(),getProvider());
            encryptionCipher.init(Cipher.ENCRYPT_MODE, secret, ivspec);
            byte[] encryptedText = encryptionCipher.doFinal(cleartext.getBytes("UTF-8"));
            String encryptedHex = toHex(encryptedText);
            return encryptedHex+ivHex;

        } catch (Exception e) {
            throw new CryptoratorException(e.getMessage(), e);
        }
    }
    
    public String decrypt(SecretKey secret, String encrypted) throws CryptoratorException {
        try {
            Cipher decryptionCipher = Cipher.getInstance(getCipherAlgorythm(),getProvider());
            String ivHex = encrypted.substring(encrypted.length()-decryptionCipher.getBlockSize()*2, encrypted.length());
            String encryptedHex = encrypted.replace(ivHex, "");
            byte[] bytesOfHEx = hexToByte(ivHex);            
            IvParameterSpec ivspec = new IvParameterSpec(bytesOfHEx);
            decryptionCipher.init(Cipher.DECRYPT_MODE, secret, ivspec);
            byte[] decryptedText = decryptionCipher.doFinal(hexToByte(encryptedHex));
            String decrypted = new String(decryptedText, "UTF-8");
            return decrypted;
        } catch (Exception e) {
            throw new CryptoratorException(e.getMessage(), e);
        }
    }
    
    
    private byte[] generateIv() throws NoSuchAlgorithmException, NoSuchProviderException {
        Cipher decryptionCipher  = null;
        try {
			decryptionCipher = Cipher.getInstance(getCipherAlgorythm());
		} catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return generateRandoms(decryptionCipher.getBlockSize());
    }
    
    public String generateSalt(Integer bitSize){
    	byte[] randomBytes = generateRandoms(bitSize);
    	return toHex(randomBytes);
    	
    }
    
    
    private byte[] generateRandoms(Integer bitSize){
        SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        byte[] rbs = new byte[bitSize];
        random.nextBytes(rbs);
        return rbs;
    }
    
    
    
    
}
