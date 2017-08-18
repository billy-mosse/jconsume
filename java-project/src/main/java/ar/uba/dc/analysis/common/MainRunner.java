package ar.uba.dc.analysis.common;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import ar.uba.dc.analysis.memory.PaperInterproceduralAnalysis;
import ar.uba.dc.analysis.memory.impl.runner.CommandLineValues;
import ar.uba.dc.analysis.memory.impl.runner.MainSootRunner;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;

public class MainRunner {


	public static void main(String[] args) {
		CommandLineValues values = new CommandLineValues(args);
		CmdLineParser parser = new CmdLineParser(values);

		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			parser.printSingleLineUsage(System.err);
		}

		final Context context = ContextFactory.getContext(values.getPropertiesFile(), false);

		if (values.runIr())
			MainSootRunner.main(values, context);

		if (values.runMemory()) {
			PaperInterproceduralAnalysis aPaperMemoryAnalysis = context.getFactory().getPaperMemoryAnalysis();
			aPaperMemoryAnalysis.run(values.getProgramName());
		}
	}
}
