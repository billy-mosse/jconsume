package ar.uba.dc.barvinok.expression.operation.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ar.uba.dc.barvinok.expression.operation.impl.mapper.JEPExpressionMapperForInvariantsTest;
import ar.uba.dc.barvinok.expression.operation.impl.mapper.JEPExpressionMapperForPiecewiseQuasipolynomialTest;

@RunWith(Suite.class)
@SuiteClasses({	JEPExpressionMapperForInvariantsTest.class, 
				JEPExpressionMapperForPiecewiseQuasipolynomialTest.class})
public class JEPExpressionMapperSuit {

}
