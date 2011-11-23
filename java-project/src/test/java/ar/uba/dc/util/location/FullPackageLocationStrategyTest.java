package ar.uba.dc.util.location;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.apache.commons.lang.StringUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import soot.SootClass;

@RunWith(JMock.class)
public class FullPackageLocationStrategyTest {

	private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
	
    private SootClass mainPackagesExample;
    private SootClass withoutPackageExample;
    private SootClass onePackageExample;
    
    @Before
    public void setUpClass() {
    	mainPackagesExample = context.mock(SootClass.class, "mainPackages");
		withoutPackageExample = context.mock(SootClass.class, "withoutPackage");
		onePackageExample = context.mock(SootClass.class, "onePackage");
				
		context.checking(new Expectations() {{
	        atLeast(1).of(mainPackagesExample).getName(); will(returnValue("ar.uba.dc.test.NameOfClass"));
	        atLeast(1).of(withoutPackageExample).getName(); will(returnValue("NameOfClass"));
	        atLeast(1).of(onePackageExample).getName(); will(returnValue("ar.NameOfClass"));
	    }});
    }
    
	@Test
	public void transformPackageToPathAndAddBasePath() {
		FullPackageLocationStrategy strategy = new FullPackageLocationStrategy();
		strategy.setBasePath("path/");
		strategy.setExtension(ClassLocationStrategy.SPEC_EXTENSION);
		
		String location = strategy.getLocation(mainPackagesExample);
		assertThat(location, is(equalTo("path/ar/uba/dc/test/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		location = strategy.getLocation(withoutPackageExample);
		assertThat(location, is(equalTo("path/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		location = strategy.getLocation(onePackageExample);
		assertThat(location, is(equalTo("path/ar/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		
		strategy = new FullPackageLocationStrategy();
		strategy.setBasePath("path/");
		strategy.setExtension(ClassLocationStrategy.XML_EXTENSION);
		
		location = strategy.getLocation(mainPackagesExample);
		assertThat(location, is(equalTo("path/ar/uba/dc/test/NameOfClass" + ClassLocationStrategy.XML_EXTENSION)));
		
		location = strategy.getLocation(withoutPackageExample);
		assertThat(location, is(equalTo("path/NameOfClass" + ClassLocationStrategy.XML_EXTENSION)));
		
		location = strategy.getLocation(onePackageExample);
		assertThat(location, is(equalTo("path/ar/NameOfClass" + ClassLocationStrategy.XML_EXTENSION)));
		
		
		strategy = new FullPackageLocationStrategy();
		strategy.setBasePath("/aaa/");
		strategy.setExtension(ClassLocationStrategy.DOT_EXTENSION);
		
		location = strategy.getLocation(mainPackagesExample);
		assertThat(location, is(equalTo("/aaa/ar/uba/dc/test/NameOfClass" + ClassLocationStrategy.DOT_EXTENSION)));
		
		location = strategy.getLocation(withoutPackageExample);
		assertThat(location, is(equalTo("/aaa/NameOfClass" + ClassLocationStrategy.DOT_EXTENSION)));
		
		location = strategy.getLocation(onePackageExample);
		assertThat(location, is(equalTo("/aaa/ar/NameOfClass" + ClassLocationStrategy.DOT_EXTENSION)));
	}
	
	public void returnPathIfBaseDontFinishWithSlash() {
		FullPackageLocationStrategy strategy = new FullPackageLocationStrategy();
		strategy.setBasePath("path");
		strategy.setExtension(ClassLocationStrategy.SPEC_EXTENSION);
		
		String location = strategy.getLocation(mainPackagesExample);
		assertThat(location, is(equalTo("path/ar/uba/dc/test/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		location = strategy.getLocation(withoutPackageExample);
		assertThat(location, is(equalTo("path/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		location = strategy.getLocation(onePackageExample);
		assertThat(location, is(equalTo("path/ar/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
	}
	
	@Test
	public void returnPathEvenWhenBasePathIsBlank() {
		FullPackageLocationStrategy strategy = new FullPackageLocationStrategy();
		strategy.setBasePath(null);
		strategy.setExtension(ClassLocationStrategy.SPEC_EXTENSION);
				
		String location = strategy.getLocation(mainPackagesExample);
		assertThat(location, is(equalTo("ar/uba/dc/test/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		location = strategy.getLocation(withoutPackageExample);
		assertThat(location, is(equalTo("NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		location = strategy.getLocation(onePackageExample);
		assertThat(location, is(equalTo("ar/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		
		strategy = new FullPackageLocationStrategy();
		strategy.setBasePath(StringUtils.EMPTY);
		strategy.setExtension(ClassLocationStrategy.SPEC_EXTENSION);
		
		location = strategy.getLocation(mainPackagesExample);
		assertThat(location, is(equalTo("ar/uba/dc/test/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		location = strategy.getLocation(withoutPackageExample);
		assertThat(location, is(equalTo("NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
		
		location = strategy.getLocation(onePackageExample);
		assertThat(location, is(equalTo("ar/NameOfClass" + ClassLocationStrategy.SPEC_EXTENSION)));
	}
}
