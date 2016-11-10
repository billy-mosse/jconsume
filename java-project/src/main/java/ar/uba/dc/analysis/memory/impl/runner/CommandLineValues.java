package ar.uba.dc.analysis.memory.impl.runner;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

public class CommandLineValues {
	@Option(name = "-p", aliases = { "--program" }, required = true,
            usage = "program")
    private String programName;
	

	@Option(name = "-c", aliases = { "--config" }, required = true,
            usage = "name")
    private String propertiesFile;
	

	@Option(name = "-m", aliases = { "--main" }, required = true,
            usage = "main")
    private String main;
    
    @Option(name="--ir",handler=BooleanOptionHandler.class,usage="Generate IR from escape analysis")
    private boolean ir;

    @Option(name="--memory",handler=BooleanOptionHandler.class,usage="Generate memory summaries from IR")
    private boolean memory;


    public String getProgramName() {
		return programName;
	}


	public void setProgramName(String programName) {
		this.programName = programName;
	}


	public String getPropertiesFile() {
		return propertiesFile;
	}


	public void setPropertiesFile(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}


	public String getMain() {
		return main;
	}


	public void setMain(String main) {
		this.main = main;
	}


	public boolean runIr() {
		return ir;
	}


	/*public void setIr(boolean ir) {
		this.ir = ir;
	}*/


	public boolean runMemory() {
		return memory;
	}


	/*public void setMemory(boolean memory) {
		this.memory = memory;
	}*/

	public CommandLineValues(String... args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }
}

