/*=============================================================================================*/
/* Class            : Configuration                                                            */
/*                                                                                             */
/* Description      : Configuration class for loading the database from the properties.        */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.kugri.frontend.client.android.common.utils.exception.TechnicalUtilsException;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * Configuration class for loading the database from the properties.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class Configuration {
	/** The properties loader */
	private Properties loader;
	
	/** The Logger instance for loging the traces */
	public final static Logger LOGGER = new Logger();

	/**
	 * Called to build the Configuration instance
	 * 
	 */
	public Configuration() {
	}

	/**
	 * Called to get the properties loader
	 * 
	 * @return Properties that is the loader instance
	 */
	public Properties getLoader() {
		return loader;
	}

	/**
	 * Called to load the properties
	 * 
	 * @param nameFileProperties the name of the properties file
	 * @throws TechnicalUtilsException if the properties loading file fails
	 */
	public void loadProperties(String nameFileProperties)
			throws TechnicalUtilsException {
		LOGGER.pushDebugs("[STAR] loadProperties");
		this.loader = new Properties();
		try {
			InputStream inputProp = getClass().getResourceAsStream(nameFileProperties);
			this.loader.load(inputProp);
		} catch (IOException e) {
			throw new TechnicalUtilsException(e.getMessage(), e);
		}

	}
}
