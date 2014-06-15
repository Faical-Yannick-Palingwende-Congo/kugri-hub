/*=============================================================================================*/
/* Class            : Logger                                                                   */
/*                                                                                             */
/* Description      : Logger class for handling the messages loging in the web service.        */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * Logger class for handling the messages loging in the web service.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class Logger {
	
	/** The errors list */
	private LinkedList<String> errors;
	/** The warnings list */
	private LinkedList<String> warnings;
	/** The debugs list */
	private LinkedList<String> debugs;
	/** The infos list */
	private LinkedList<String> infos;
	/** The number of errors */
	private int errorsNumber;
	/** The number of infos */
	private int infosNumber;
	/** The number of debugs */
	private int debugsNumber;
	/** The number of warnings */
	private int warningsNumber;
	/** The date format stamp for the logs */
	public DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy [HH:mm:ss:SSS]");

	  /**
	   * Called to build the instance
	   * 
	   */
	public Logger() {
		this.infos = new LinkedList<String>();
		this.warnings = new LinkedList<String>();
		this.errors = new LinkedList<String>();
		this.debugs = new LinkedList<String>();

		this.infosNumber = 0;
		this.warningsNumber = 0;
		this.errorsNumber = 0;
		this.debugsNumber = 0;

	}

	  /**
	   * Called to push a Info log
	   * 
	   * @param details the message to log
	   */
	public void pushInfos(String details) {
		Date date = new Date();
		this.infos.add("<<<<" + this.dateFormat.format(date)
				+ " ### HELLOWORLD_SERVICE_REST - Logging Infos: " + details);
		System.out.println("<<<<" + this.dateFormat.format(date)
				+ " ### HELLOWORLD_SERVICE_REST - Logging Infos: " + details);
		this.infosNumber++;
	}

	  /**
	   * Called to push a Warning log
	   * 
	   * @param details the message to log
	   */
	public void pushWarnings(Exception exception, String details) {
		Date date = new Date();
		this.warnings.add("<<<<" + this.dateFormat.format(date)
				+ " ### HELLOWORLD_SERVICE_REST - Logging Warnings: " + details
				+ " Exception: " + exception.getClass());
		System.out.println("<<<<" + this.dateFormat.format(date)
				+ " ### HELLOWORLD_SERVICE_REST - Logging Warnings: " + details
				+ " Exception: " + exception.getClass());
		this.warningsNumber++;
	}

	  /**
	   * Called to push a Error log
	   * 
	   * @param details the message to log
	   */
	public void pushErrors(Exception exception, String details) {
		Date date = new Date();
		this.errors.add("<<<<" + this.dateFormat.format(date)
				+ " ### HELLOWORLD_SERVICE_REST - Logging Errors: " + details
				+ " Exception: " + exception.getClass());
		System.out.println("<<<<" + this.dateFormat.format(date)
				+ " ### HELLOWORLD_SERVICE_REST - Logging Errors: " + details
				+ " Exception: " + exception.getClass());
		this.errorsNumber++;
	}

	  /**
	   * Called to push a Debug log
	   * 
	   * @param details the message to log
	   */
	public void pushDebugs(String details) {
		Date date = new Date();
		this.debugs.add("<<<<" + this.dateFormat.format(date)
				+ " ### HELLOWORLD_SERVICE_REST - Logging Debugs: " + details);
		System.out.println("<<<<" + this.dateFormat.format(date)
				+ " ### HELLOWORLD_SERVICE_REST - Logging Debugs: " + details);
		this.debugsNumber++;
	}

	  /**
	   * Called to pull a Info log
	   * 
	   * @param index the id of the log
	   * @return String the info log
	   */
	public String getInfos(int index) {
		return this.infos.get(index);
	}

	  /**
	   * Called to pull a Warning log
	   * 
	   * @param index the id of the log
	   * @return String the warning log
	   */
	public String getWarnings(int index) {
		return this.warnings.get(index);
	}

	  /**
	   * Called to pull an Error log
	   * 
	   * @param index the id of the log
	   * @return String the error log
	   */
	public String getErrors(int index) {
		return this.errors.get(index);
	}

	  /**
	   * Called to pull a Debug log
	   * 
	   * @param index the id of the log
	   * @return String the debug log
	   */
	public String getDebugs(int index) {
		return this.debugs.get(index);
	}

	  /**
	   * Called to get the number of info logs
	   * 
	   * @return int that is the info logs number
	   */
	public int infosLength() {
		return this.infosNumber;
	}

	  /**
	   * Called to get the number of warning logs
	   * 
	   * @return int that is the warning logs number
	   */
	public int warningsLength() {
		return this.warningsNumber;
	}

	  /**
	   * Called to get the number of error logs
	   * 
	   * @return int that is the error logs number
	   */
	public int errorsLength() {
		return this.errorsNumber;
	}

	  /**
	   * Called to get the number of debug logs
	   * 
	   * @return int that is the debug logs number
	   */
	public int debugsLength() {
		return this.debugsNumber;
	}
}
