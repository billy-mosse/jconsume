package ar.uba.dc.util;

import org.apache.commons.lang.StringUtils;

/**
 * Implementacion de un timer
 * 
 * @author testis
 */
public class Timer {

	private static final long MILISECONDS_PER_SECONDS = 1000L;
	private static final long SECONDS_PER_MINUTE = 60L;
	private static final long MINUTES_PER_HOUR = 60L;
	
	long start;
	long end;
	boolean hasStarted = false;
	
	/**
	 * Arranca el timer
	 */
	public void start() {
		start = System.currentTimeMillis();
	}

	/**
	 * Detiene el timer
	 */
	public void stop() {
		end = System.currentTimeMillis();
	}

	/**
	 * Devuelve un string representando el timpo ocurrido desde que el timer fue arrancado hasta que fue detenido
	 */
	public String getElapsedTime() {
		long elapsed = (end - start);
		long hours   = (end - start) / (MILISECONDS_PER_SECONDS * SECONDS_PER_MINUTE * MINUTES_PER_HOUR);
		elapsed = elapsed - hours * (MILISECONDS_PER_SECONDS * SECONDS_PER_MINUTE * MINUTES_PER_HOUR);
		long minutes = elapsed / (MILISECONDS_PER_SECONDS * SECONDS_PER_MINUTE);
		elapsed = elapsed - minutes * (MILISECONDS_PER_SECONDS * SECONDS_PER_MINUTE);
		long seconds = elapsed / MILISECONDS_PER_SECONDS;
		
		return StringUtils.leftPad(Long.toString(hours), 2, '0') + ":" + StringUtils.leftPad(Long.toString(minutes), 2, '0') + ":" + StringUtils.leftPad(Long.toString(seconds), 2, '0');
	}

	/**
	 * Devuelve el tiempo transcurrido desde que el timer arranca hasta que termina en segundos
	 */
	public long getElapsedSeconds() {
		return (end-start) / MILISECONDS_PER_SECONDS;
	}
}
