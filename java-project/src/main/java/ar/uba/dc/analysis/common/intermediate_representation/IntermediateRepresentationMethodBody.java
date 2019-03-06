package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.invariant.InvariantProvider;

public class IntermediateRepresentationMethodBody {	
	
	public IntermediateRepresentationMethodBody() {
		this.lines = new LinkedHashSet<Line>();
	}

	protected Set<Line> lines;

	public Set<Line> getLines() {
		return lines;
	}

	public void setLines(Set<Line> lines) {
		this.lines = lines;
	}
	
	public void addLine(Line line) {
		this.lines.add(line);
	}

}
