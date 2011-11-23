package ar.uba.dc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilitario para ejecutar comandos por consola
 * 
 * @author testis
 */
public class ConsoleUtils {
	
	private static Log log = LogFactory.getLog(ConsoleUtils.class);
	
	/**
	 * Ejecuta un comando. En caso de ejecutarlo en modo debug se genera una entrada en el log con los resultados de la ejecucion.
	 * Sino no se generan entradas en el log 
	 */
	public static String execCommand(String command, Boolean debug) {
		try {
			Runtime run = Runtime.getRuntime();
			Process proc = run.exec(command);
			proc.waitFor();
			
			String errors = streamToString(proc.getErrorStream());
			String output = streamToString(proc.getInputStream());
			
			if (debug) {
				log.info("output: " + output);
				log.info("errors: " + errors);
				log.info("exit code: " + proc.exitValue());
			}
			
			if (errors.length() > 0) {
				throw new ConsoleException("Error al ejecutar el comando [" + command + "]: " + errors);
			}
			
			return output;
		} catch (IOException e) {
			log.warn("Error al ejecutar el commando [" + command + "]: " + e.getMessage(), e);
		} catch (InterruptedException e) {
			log.warn("Error al ejecutar el commando [" + command + "]: " + e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * Lee todo un input stream devolviendo el string resultante de esta lectura
	 */
	protected static String streamToString(InputStream is) {
		BufferedReader bufOut = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		
		try {
			boolean appendLineSeparator = false;
			String line = null;
			while ( ((line = bufOut.readLine()) != null) )
			{
				if (!appendLineSeparator) {
					appendLineSeparator = true;
				} else {
					sb.append(System.getProperty("line.separator"));
				}
				sb.append(line);
			}
		} catch (IOException e) {
			log.warn("Error al recuperar la informacion de la consola: " + e.getMessage(), e);
		} finally {
			try {
				bufOut.close();
			} catch (IOException e2) {
				log.warn("Error al cerrar el stream asociado a la consola: " + e2.getMessage(), e2);
			}
		}
		
		return sb.toString();
	}
}
