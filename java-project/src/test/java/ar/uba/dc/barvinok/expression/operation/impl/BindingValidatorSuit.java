package ar.uba.dc.barvinok.expression.operation.impl;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ar.uba.dc.barvinok.expression.operation.impl.binding.ExceptionalBindingValidatorTest;
import ar.uba.dc.barvinok.expression.operation.impl.binding.OptimisticBindingValidatorTest;
import ar.uba.dc.barvinok.expression.operation.impl.binding.PesimisticBindingValidatorTest;

@Ignore
@RunWith(Suite.class)
@SuiteClasses({	ExceptionalBindingValidatorTest.class, 
				OptimisticBindingValidatorTest.class,
				PesimisticBindingValidatorTest.class })
public class BindingValidatorSuit {

}
