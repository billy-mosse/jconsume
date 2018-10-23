package ar.uba.dc.barvinok;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.util.ConsoleUtils;

public class CommandLineBarvinokExecutor implements BarvinokExecutor {

	private static Log log = LogFactory.getLog(CommandLineBarvinokExecutor.class);
	
	private boolean firstTime = true;
	
	protected String barvinokCmdPath;
	
	/* Barvinok bug:
	 * 
	 * sum { [[a,b,c,d] -> [f,i]] -> 2*f: exists e,g,h,k : e >= 0 and f > 0 and i >= 1 and k >= i and k - 2*b + i = 0 and h >= 0 and k >= 0 and k >= 1 and h < k and k >= b and h + k - 2*b + 1 = 0 and k <= c and h - i + 1 = 0 and h < b and b >= i and k = f};
	 */
	public String execCommand(String command) {
		try {
			/*if(this.firstTime) {
				File file = new File(barvinokCmdPath);
				if(!file.exists()) {
					FileWriter fw = new FileWriter("barvinok.sh", false);
					fw.write("#!/bin/bash");
					fw.write("\n");
					fw.write("iscc < $1");
					fw.close();
					ConsoleUtils.execCommand("chmod +x barvinok.sh",false);
					this.barvinokCmdPath = "./barvinok.sh";
				}
				
			}*/
			FileWriter fw = new FileWriter("cmd_tmp", false);
			// OJO! Mega hack para arreglar el manejo de la division que esta emparchadisimo
			//BILLY supongo que hay casos borde donde hay que reemplazar 3 veces
			
			//por que estaba esta linea: 			command = command.replaceAll("\\, [\\[([.[^\\[]]+)/([.[^\\[]]+)\\]\\]", "[$1/$2]");

			command = command.replaceAll("\\[\\[([.[^\\[]]+)/([.[^\\[]]+)\\]\\]", "[$1/$2]");
			command = command.replaceAll("\\[\\[([.[^\\[]]+)/([.[^\\[]]+)\\]\\]", "[$1/$2]");
			command = command.replaceAll("\\[\\[([.[^\\[]]+)/([.[^\\[]]+)\\]\\]", "[$1/$2]");
			
			//BILLY. Add for debug
			String command_for_debug = command.replaceAll("==", "=");
			fw.write(command_for_debug);
			
			fw.close();
			return ConsoleUtils.execCommand(barvinokCmdPath + " cmd_tmp", false);
		} catch (IOException e) {
			log.warn("Ocurrio un error al procesar el comando [" + command + "]: " + e.getMessage(), e);
			return null;
		}
	}
	
	public void setBarvinokCmdPath(String barvinokCmdPath) {
		this.barvinokCmdPath = barvinokCmdPath;
	}
}
