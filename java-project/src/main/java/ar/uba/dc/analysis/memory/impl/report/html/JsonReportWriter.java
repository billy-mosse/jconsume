package ar.uba.dc.analysis.memory.impl.report.html;

import java.io.BufferedWriter;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;

public class JsonReportWriter {

	protected Gson gson;

	public void build() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		this.gson = builder.create();		
	}
}
