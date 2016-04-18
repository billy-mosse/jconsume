package ar.uba.dc.cli;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.escape.runner.RunCommand;
import ar.uba.dc.util.ConsoleUtils;

import org.junit.Assert;

/**
 * @author martin
 *
 */
public class Main {

	private static final String distrib = "jconsume2-0.0.1.jar";
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
	 
		// create the parser
	    CommandLineParser parser = new GnuParser();
	    
	
	    	// create Options object
	    	Options options = new Options();
	    	Option help = new Option("help", "print this message.");
	    	options.addOption(help);
	    	
	    	//add run escape analysis option
	    	options.addOption("escape", false, "run the Escape Analysis instead of Memory Analysis.");
	    	
	    	//add madeja option
	    	options.addOption("madeja", false, "set Madeja as Object Lifetime Oracle.");
	    	
	    	
	    	OptionBuilder.hasArg();
	    	OptionBuilder.isRequired();
	    	OptionBuilder.withDescription("the class to be analyzed.");
	    	Option mainClass = OptionBuilder.create("mainClass");
	    	options.addOption(mainClass);
	    	
	    	OptionBuilder.hasArg();
	    	OptionBuilder.isRequired();
	    	OptionBuilder.withDescription("the jar containing the required spec.");
	    	Option specJar = OptionBuilder.create("specJar");
	    	options.addOption(specJar);
	    	
	    
	    try {
	    	

	    	
	        // parse the command line arguments
	        CommandLine line = parser.parse( options, args );
	        String properties = line.hasOption("madeja") ? "config.madeja.properties" : "config.properties";
	        

	        
	        
	        String className = line.getOptionValue("mainClass");
	        String spec = line.getOptionValue("specJar");
	        
	        String iscc = System.getenv("ISCC");
	        
	        check(spec, iscc);
	        
	        //TODO hacer una interface para Command
	        if(line.hasOption("escape")) {
		        //Run the application
		        RunCommand command = new RunCommand();
		        command.setProperties(properties);
		        command.setClassName(className);
		        command.execute();
	        } else {
		        //Run the application
		        ar.uba.dc.analysis.memory.impl.runner.RunCommand command = new ar.uba.dc.analysis.memory.impl.runner.RunCommand();
		        command.setProperties(properties);
		        command.setClassName(className);
		        command.setSpecJar(spec);
		        command.execute();
	        	
	        }
	        
	    } catch(ParseException exp ) {
	        //something went wrong
	       showUsage(options);
	    }
	}
	
	
	public static void showUsage(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "jconsume2", options, true );
	}
	
	public static void check(String spec, String iscc) {
		//Check iscc
		if(StringUtils.isEmpty(iscc))
			throw new RuntimeException("ISCC environment variable is not properly set.");
		
		File isccAdapter = new File("barvinok.sh"); 
		if(!isccAdapter.exists()) {
			ConsoleUtils.execCommand("jar xf " + distrib + " barvinok.sh" , false);
			ConsoleUtils.execCommand("chmod +x barvinok.sh" , false);
		}
			
		File site = new File("site");
		if(!site.exists()) {
			ConsoleUtils.execCommand("jar xf " + distrib + " site" , false);
		}
		
		File specFile = new File(spec);
		if(!specFile.exists())
			throw new RuntimeException("Unable to find " + specFile.getAbsolutePath());
		else
			ConsoleUtils.execCommand("jar xf " + specFile.getAbsolutePath()  , false);
		//check java
	}
}
