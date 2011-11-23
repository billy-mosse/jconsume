package ar.uba.dc.barvinok.expression.operation.impl;

import java.util.LinkedList;

public class ExpressionParsingResult {
	private LinkedList<String> parts = new LinkedList<String>();
	private String lastFunctionApplied = null;
	
	public ExpressionParsingResult(String defaultPart) {
		super();
		parts.add(defaultPart);
	}
	
	public void addAll(ExpressionParsingResult info) {
		this.parts.addAll(info.parts);
	}

	public void addLast(String value) {
		parts.addLast(value);
	}

	public void addFirst(String value) {
		parts.addFirst(value);
	}

	public String getLastFunctionApplied() {
		return lastFunctionApplied;
	}

	public void setLastFunctionApplied(String lastFunctionApplied) {
		this.lastFunctionApplied = lastFunctionApplied;
	}
	
	public String getString() {
		int capacity = 0;
		
		for (String parte : parts) {
			capacity += parte.length();
		}
		
		StringBuilder sb = new StringBuilder(capacity);
		for (String s : parts) {
			sb.append(s);
		}
		
		return sb.toString();
	}
}
