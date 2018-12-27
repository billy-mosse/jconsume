package ar.uba.dc.util.collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@Ignore

@RunWith(Theories.class)
public class CircularStackTest {

	@SuppressWarnings("unchecked")
	@DataPoints
	public static CircularStack<Integer>[] stacks() {
		return new CircularStack[] { new CircularStack(), new CircularStack(40) };
	}
	
	@Theory
	public void hashDependsOnContainedElements(CircularStack<Integer> stack) {
		int hash0 = stack.hashCode();
		
		stack.push(1);
		int hash1 = stack.hashCode();
		
		stack.push(40);
		int hash2 = stack.hashCode();
		
		stack.pop();
		int hash3 = stack.hashCode();
		
		stack.pop();
		int hash4 = stack.hashCode();
		
		stack.push(1);
		stack.push(1);
		int hash5 = stack.hashCode();
		
		assertThat(hash0, is(not(equalTo(hash1))));
		assertThat(hash0, is(not(equalTo(hash2))));
		assertThat(hash0, is(not(equalTo(hash3))));
		assertThat(hash0, is(equalTo(hash4)));
		assertThat(hash0, is(not(equalTo(hash5))));
		
		assertThat(hash1, is(not(equalTo(hash2))));
		assertThat(hash1, is(equalTo(hash3)));
		assertThat(hash1, is(not(equalTo(hash4))));
		assertThat(hash1, is(not(equalTo(hash5))));
		
		assertThat(hash2, is(not(equalTo(hash3))));
		assertThat(hash2, is(not(equalTo(hash4))));
		assertThat(hash2, is(not(equalTo(hash5))));
		
		assertThat(hash3, is(not(equalTo(hash4))));
		assertThat(hash3, is(not(equalTo(hash5))));
		
		assertThat(hash4, is(not(equalTo(hash5))));
	}
	
	@Theory
	public void circularStackHasStackBehavior(CircularStack<Integer> stack) {
		assertThat(stack.empty(), is(true));
		
		stack.push(40);
		assertThat(stack.empty(), is(false));
		assertThat(stack.peek(), is(equalTo(40)));
		assertThat(stack.empty(), is(false));
		assertThat(stack.pop(), is(equalTo(40)));
		assertThat(stack.empty(), is(true));
		assertThat(stack.size(), is(equalTo(0)));
		
		stack.push(40);
		assertThat(stack.size(), is(equalTo(1)));
		stack.push(5);
		assertThat(stack.size(), is(equalTo(2)));
		assertThat(stack.empty(), is(false));
		assertThat(stack.peek(), is(equalTo(5)));
		assertThat(stack.peek(), is(equalTo(5)));
		assertThat(stack.size(), is(equalTo(2)));
		assertThat(stack.pop(), is(equalTo(5)));
		assertThat(stack.size(), is(equalTo(1)));
		assertThat(stack.peek(), is(equalTo(40)));
		assertThat(stack.size(), is(equalTo(1)));
	}

	@Test
	public void dropElementsWhenStackIsFull() {
		CircularStack<Integer> stack = new CircularStack<Integer>(5);
		
		stack.push(5);
		stack.push(4);
		stack.push(3);
		stack.push(2);
		stack.push(1);
		
		Iterator<Integer> it = stack.iterator();
		assertThat(it.next(), is(equalTo(5)));
		assertThat(it.next(), is(equalTo(4)));
		assertThat(it.next(), is(equalTo(3)));
		assertThat(it.next(), is(equalTo(2)));
		assertThat(it.next(), is(equalTo(1)));
		assertThat(it.hasNext(), is(equalTo(false)));
		assertThat(stack.isFull(), is(equalTo(true)));
		
		stack.push(0);

		it = stack.iterator();
		assertThat(stack.isFull(), is(equalTo(true)));
		assertThat(it.next(), is(equalTo(4)));
		assertThat(it.next(), is(equalTo(3)));
		assertThat(it.next(), is(equalTo(2)));
		assertThat(it.next(), is(equalTo(1)));
		assertThat(it.next(), is(equalTo(0)));
	}
	
	@Test
	public void equalsDependsOnContainedElementsAndCapacity() {
		// Tienen distintas capacidades, no son iguales
		assertThat(new CircularStack<Integer>(), is(not(equalTo(new CircularStack<Integer>(4)))));
		
		CircularStack<Integer> a = new CircularStack<Integer>();
		CircularStack<Integer> b = new CircularStack<Integer>();
		
		assertThat(a, is(equalTo(a)));
		assertThat(a, is(equalTo(b)));
		
		a.push(1);	
		assertThat(a, is(not(equalTo(b))));
		
		b.push(1);
		assertThat(a, is(equalTo(b)));
		
		a.pop();
		a.push(2);
		a.push(1);
		b.push(2);
		// Tienen los mismos elementos pero en otro orden. No son iguales
		assertThat(a, is(not(equalTo(b))));
		
		a = new CircularStack<Integer>(3);
		b = new CircularStack<Integer>(3);
		
		a.push(3);
		a.push(2);
		a.push(1);
		
		b.push(3);
		b.push(3);
		b.push(2);
		
		assertThat(a, is(not(equalTo(b))));

		b.push(1);
		// despues de pegar la vuelta, se pueden igualar
		assertThat(a, is(equalTo(b)));		
	}
	
	@Test
	public void pushDoesNothingIfCapacityIsZero() {
		CircularStack<Integer> stack = new CircularStack<Integer>(0);
		
		stack.push(1);
		// despues de pegar la vuelta, se pueden igualar
		assertThat(stack.size(), is(equalTo(0)));
		assertThat(stack.empty(), is(equalTo(true)));
		assertThat(stack.isFull(), is(equalTo(true)));
	}
}
