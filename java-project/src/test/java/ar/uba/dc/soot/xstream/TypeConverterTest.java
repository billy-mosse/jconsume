package ar.uba.dc.soot.xstream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import soot.AnySubType;
import soot.ArrayType;
import soot.BooleanType;
import soot.ByteType;
import soot.CharType;
import soot.DoubleType;
import soot.ErroneousType;
import soot.FloatType;
import soot.IntType;
import soot.LongType;
import soot.NullType;
import soot.RefType;
import soot.ShortType;
import soot.StmtAddressType;
import soot.Type;
import soot.UnknownType;
import soot.VoidType;
import soot.baf.DoubleWordType;
import soot.baf.WordType;
import soot.coffi.Double2ndHalfType;
import soot.coffi.Long2ndHalfType;
import soot.coffi.UnusuableType;
import soot.jimple.toolkits.typing.fast.BottomType;
import soot.jimple.toolkits.typing.fast.Integer127Type;
import soot.jimple.toolkits.typing.fast.Integer1Type;
import soot.jimple.toolkits.typing.fast.Integer32767Type;

import com.thoughtworks.xstream.XStream;
@Ignore

@RunWith(Theories.class)
public class TypeConverterTest {

	private XStream xstream;
	
	@DataPoints
	public static Type[] types() {
		return new Type[] {
			BottomType.v(),
			Double2ndHalfType.v(),
			DoubleWordType.v(),
			ErroneousType.v(),
			Long2ndHalfType.v(),
			BooleanType.v(),
			ByteType.v(),
			CharType.v(),
			DoubleType.v(),
			FloatType.v(),
			Integer127Type.v(),
			Integer1Type.v(),
			Integer32767Type.v(),
			IntType.v(),
			LongType.v(),
			ShortType.v(),
			NullType.v(),
			StmtAddressType.v(),
			UnknownType.v(),
			UnusuableType.v(),
			VoidType.v(),
			WordType.v(),
			
			AnySubType.v(RefType.v("ar.uba.dc.simple.EjemploSimple01")),
			ArrayType.v(IntType.v(), 42),
			ArrayType.v(RefType.v("ar.uba.dc.simple.EjemploSimple01"), 7),
			RefType.v("ar.uba.dc.simple.EjemploSimple01")
		};
	}
	
	@Before
	public void setUp() {
		xstream = new XStream();
		xstream.registerConverter(new TypeConverter());
		xstream.alias("type", Type.class);
	}
	
	@Theory
	public void serialization(Type type) {
		String xml = xstream.toXML(type);
		Type result = (Type) xstream.fromXML(xml);
		System.out.println(xml);
		assertThat(type.getClass().getSimpleName() +  " does not serialized ok", type, is(equalTo(result)));
	}
	
	@Test
	public void typeSerialization() {
		Type type = (Type) xstream.fromXML("<type name=\"anySub\"><base name=\"ref\" to=\"ar.uba.dc.simple.EjemploSimple01\"/></type>");
		assertThat(type.getClass().getSimpleName() +  " does not serialized ok", type, is(equalTo((Type) AnySubType.v(RefType.v("ar.uba.dc.simple.EjemploSimple01")))));
	}
}
