package ar.uba.dc.util.location;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import soot.SootClass;
@Ignore

@RunWith(JMock.class)
public class FileLocationStrategyTest {

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
	        allowing(mainPackagesExample).getName(); will(returnValue("ar.uba.dc.test.NameOfClass"));
	        allowing(withoutPackageExample).getName(); will(returnValue("NameOfClass"));
	        allowing(onePackageExample).getName(); will(returnValue("ar.NameOfClass"));
	    }});
    }
    
    @Test
	public void returnTheSamePathForAllClasses() {
		FileLocationStrategy strategy = new FileLocationStrategy();
		strategy.setRepositoryFile("path/a.txt");
		
		String location = strategy.getLocation(mainPackagesExample);
		assertThat(location, is(equalTo("path/a.txt")));
		
		location = strategy.getLocation(withoutPackageExample);
		assertThat(location, is(equalTo("path/a.txt")));
		
		location = strategy.getLocation(onePackageExample);
		assertThat(location, is(equalTo("path/a.txt")));
		
		
		strategy = new FileLocationStrategy();
		strategy.setRepositoryFile("/path/a/b");
		
		location = strategy.getLocation(mainPackagesExample);
		assertThat(location, is(equalTo("/path/a/b")));
		
		location = strategy.getLocation(withoutPackageExample);
		assertThat(location, is(equalTo("/path/a/b")));
		
		location = strategy.getLocation(onePackageExample);
		assertThat(location, is(equalTo("/path/a/b")));
		
		
		strategy = new FileLocationStrategy();
		strategy.setRepositoryFile("aaa");
		
		location = strategy.getLocation(mainPackagesExample);
		assertThat(location, is(equalTo("aaa")));
		
		location = strategy.getLocation(withoutPackageExample);
		assertThat(location, is(equalTo("aaa")));
		
		location = strategy.getLocation(onePackageExample);
		assertThat(location, is(equalTo("aaa")));
	}
}
