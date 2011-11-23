package ar.uba.dc.soot.xstream;

import soot.SootMethod;
import soot.jimple.Stmt;
import soot.tagkit.BytecodeOffsetTag;
import soot.tagkit.LineNumberTag;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.soot.StatementId;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class StatementIdConverter implements Converter {

	private String methodNode;
	private String statementNode;
	private String lineNumberNode;
	private String offsetNumberNode;
		
	public StatementIdConverter(String methodNode, String statementNode, String lineNumberNode, String bytecodeOffsetNode) {
		super();
		this.methodNode = methodNode;
		this.statementNode = statementNode;
		this.lineNumberNode = lineNumberNode;
		this.offsetNumberNode = bytecodeOffsetNode;
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		StatementId node = (StatementId) value;
		Stmt stmt = node.getId();

		LineNumberTag lineNumberTag = (LineNumberTag) stmt.getTag("LineNumberTag");
		BytecodeOffsetTag bytecodeOffsetTag = (BytecodeOffsetTag) stmt.getTag("BytecodeOffsetTag");
		
		if (lineNumberTag == null) {
			throw new ConversionException("La opcion keep_line_number se encuentra deshabilitada. Para serializar summaries debe estar habilitada");
		}
		
		if (bytecodeOffsetTag == null) {
			throw new ConversionException("La opcion keep_offset se encuentra deshabilitada. Para serializar summaries debe estar habilitada");
		}
		
		writer.startNode(methodNode);
			context.convertAnother(node.getMethodOfId());
        writer.endNode();
		writer.startNode(statementNode);
        	writer.setValue(stmt.toString());
        writer.endNode();
        writer.startNode(lineNumberNode);
	    	writer.setValue(Integer.toString(lineNumberTag.getLineNumber()));
	    writer.endNode();
        writer.startNode(offsetNumberNode);
        	writer.setValue(Integer.toString(bytecodeOffsetTag.getBytecodeOffset()));
        writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		StatementId stmtId = new StatementId(null, null);
		
		SootMethod method = null;
		String statementString = null;
		Integer lineNumber = null;
		Integer offsetNumber = null;
		
		reader.moveDown();
			method = (SootMethod) context.convertAnother(stmtId, SootMethod.class);
		reader.moveUp();
		reader.moveDown();
			statementString = reader.getValue();
        reader.moveUp();
        reader.moveDown();
        	lineNumber = Integer.valueOf(reader.getValue());
	    reader.moveUp();
        reader.moveDown();
	        offsetNumber = Integer.valueOf(reader.getValue());
        reader.moveUp();
		
        Stmt stmt = SootUtils.findStatementInMethod(method, statementString, lineNumber, offsetNumber);
        
        if (stmt == null) {
        	throw new StatementNotFoundException(method, statementString, lineNumber, offsetNumber);
        }
        
		return new StatementId(method, stmt);
	}
	
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return StatementId.class.isAssignableFrom(clazz);
	}
}
