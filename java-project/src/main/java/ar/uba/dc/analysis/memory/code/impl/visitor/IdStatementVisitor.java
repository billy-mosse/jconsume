package ar.uba.dc.analysis.memory.code.impl.visitor;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.StatementVisitor;

public class IdStatementVisitor implements StatementVisitor<String> {

	@Override
	public String visit(CallStatement stmt) {
		return "Invoke@"+ stmt.belongsTo().getSignature() + ":" + stmt.getCounter() + " --> " + stmt.getCalledMethod().getSignature();
	}

	@Override
	public String visit(NewStatement stmt) {
		return "New@"+ stmt.belongsTo().getSignature() + ":" + stmt.getCounter();
	}

}
