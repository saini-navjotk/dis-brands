package com.tcs.eas.rest.apis.utility;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * 
 * @author 44745
 *
 */
public class KeyGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub
		System.out.println("random string=");
		return getRandomNumberString();
	}

	/**
	 * 
	 * @return
	 */
	private int getRandomNumberString() {
		Random rnd = new Random();
		System.out.println("random no=" + rnd);
		return rnd.nextInt(99999999);
	}
}
