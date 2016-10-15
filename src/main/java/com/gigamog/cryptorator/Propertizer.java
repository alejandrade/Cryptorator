package com.gigamog.cryptorator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gigamog.cryptorator.exception.PropertizerException;

public class Propertizer {

	static Logger log = Logger.getLogger(Propertizer.class.getName());
	private static Properties prop;
	private static Propertizer propertizer;

	private static final String PROPERTY = "/config/Crypto.properties";
	private static final String PBE_ALGORITHM = "PBE_ALGORITHM";
	private static final String PBE_ITERATION = "PBE_ITERATION_COUNT";
	private static final String SECRET_KEY_LENGTH="SECRET_KEY_LENGTH";
	private static final String IV_LENGTH = "IV_LENGTH";
	private static final String CIPHER_ALGORITHM = "CIPHER_ALGORITHM";
	private static final String SECRET_KEY_ALGORITHM = "SECRET_KEY_ALGORITHM";
	private static final String PROVIDER = "PROVIDER";
	private static final String PASSWORD = "PASSWORD";
	private static final String SALT_LENGTH = "SALT_LENGTH";
	
	private Propertizer() {

		prop = new Properties();
		setProperties(prop);

	}
	
	private static Propertizer getInstance(){
		if(propertizer == null)
			propertizer = new Propertizer();
		
		return propertizer;
	}
	
	
	public static String getProvider(){
		getInstance();
		return getPropertyOrExcept(PROVIDER);
	}
	
	public static Integer getPBEIteration(){
		getInstance();
		return Integer.parseInt(getPropertyOrExcept(PBE_ITERATION));
	}
	
	public static Integer getSaltLength(){
		getInstance();
		return Integer.parseInt(getPropertyOrExcept(SALT_LENGTH));
	}
	
	public static Integer getSecretKeyLength(){
		getInstance();
		return Integer.parseInt(getPropertyOrExcept(SECRET_KEY_LENGTH));
	}
	
	public static String getEncryptionPassword(){
		getInstance();
		return getPropertyOrExcept(PASSWORD);
	}
	
	public static String getCipherAlgorythm(){
		return getPropertyOrExcept(CIPHER_ALGORITHM);
	}
	

	public static String getPBEAlgorithm(){
		return getPropertyOrExcept(PBE_ALGORITHM);
	}
	public static String getSecretKeyAlgorithm(){
		return getPropertyOrExcept(SECRET_KEY_ALGORITHM);
	}
	
//	public String[] getFromApplicationName() {
//		return splitPropertyArray(FROM_APPLICATION_NAME_KEY);
//	}


	private String[] splitPropertyArray(String KEY) {
		String rawPropertie = getPropertyOrExcept(KEY);
		return rawPropertie.split(",");
	}

	private static String getPropertyOrExcept(String KEY) {
		String result = "";
		if (prop.containsKey(KEY)) {
			result = prop.getProperty(KEY);
			return result;
		} else {
			//log.log(Level.SEVERE, KEY + " missing" );
			throw new PropertizerException(KEY);
		}

	}

	// this function grabs property file
	public void setProperties(Properties prop) {
		InputStream input = null;
		try {
			input = this.getClass().getResourceAsStream(PROPERTY);
			if (input == null) {
				// we check if the property file exists first. If it doesn't
				// everything explodes
				//log.log(Level.SEVERE, "You must create property file " + JWTPROPERTY);
				throw new PropertizerException("");
			}
			prop.load(input);
			input.close();
		} catch (IOException e) {
			log.log(Level.SEVERE, "Error occurred in " + this.getClass().getName(), e);
			e.printStackTrace();
		}

	}
}
