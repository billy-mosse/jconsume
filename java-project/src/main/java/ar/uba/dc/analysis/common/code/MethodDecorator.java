package ar.uba.dc.analysis.common.code;

import soot.SootMethod;

public interface MethodDecorator {

	MethodBody decorate(SootMethod method);

	MethodBody decorate_indexing_news_and_calls_together(SootMethod method);

}
