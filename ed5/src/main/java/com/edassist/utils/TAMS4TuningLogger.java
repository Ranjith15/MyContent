package com.edassist.utils;

import org.apache.log4j.Logger;

public class TAMS4TuningLogger {
	private static Logger log = Logger.getLogger(TAMS4TuningLogger.class);

	/*
	 * Long tuningId = new Date().getTime(); TAMS4TuningLogger.logIt( this.getClass().toString() + " - " + "TuningID - " + tuningId + " - " + new Date() +
	 * " - <<method name>>() - before/after <<some uniquely identifying landmark for the log file>>" );
	 */

	public static void logIt(String message) {
		log.info(message); // strip out the clutter for now
	}

}
