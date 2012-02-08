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
			// OJO! Mega hack para arreglar el manejo de la division que esta enparchadisimo
			command = command.replaceAll("\\[\\[([.[^\\[]]+)/([.[^\\[]]+)\\]\\]", "[$1/$2]");
			command = command.replaceAll("\\[\\[([.[^\\[]]+)/([.[^\\[]]+)\\]\\]", "[$1/$2]");
			command = command.replaceAll("\\[\\[([.[^\\[]]+)/([.[^\\[]]+)\\]\\]", "[$1/$2]");
			
			fw.write(command.replaceAll("==", "="));
			
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
