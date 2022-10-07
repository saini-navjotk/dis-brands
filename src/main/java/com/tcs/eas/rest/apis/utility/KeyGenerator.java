package com.tcs.eas.rest.apis.utility;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 44745
 *
 */
public class KeyGenerator implements IdentifierGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(KeyGenerator.class);
    
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		
	try {
		return getRandomNumberString();
	} catch (NoSuchAlgorithmException e) {
		
		logger.error(e.getMessage());
	}
			
			
			return session;
		
	}

	/**
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	private int getRandomNumberString() throws NoSuchAlgorithmException {
		Random rnd = SecureRandom.getInstanceStrong();
		return rnd.nextInt(99999999);
	}
}
