package ar.uba.dc.basic.array;

import javax.sound.sampled.SourceDataLine;

import javazoom.jl.decoder.JavaLayerException;

public class ShrinkTest {

	public void main(String[] args) {
		test1();
		test2();
		ShrinkTest t = new ShrinkTest();
		t.test3(new short[] {}, 0, 0);
	}
	
	public static void test1() {
		Integer[] I = {1,2,3,4,5};
		float[] f = {1,2,3,4,5};
		
		Integer[][] II = {{1,2}, {3,4,5}};
		
		int[][] ii = {{1,2}, {3,4,5}};
	}
	
	private Integer[] a;
	
	public static void test2() {
		ShrinkTest t = new ShrinkTest();
		t.a[0] = new Integer(1);
		t.assign(new Integer(2), null, null);
		t.assign(t.a[2], null, null);
		
	}
	
	ShrinkTest other;
	
	public void assign(Integer param, ShrinkTest o, ShrinkTest o2) {
		ShrinkTest t = this;
		other = o;
		other = o2;
		o.other = this;
		a[1] = new Integer(1);
		a[2] = new Integer(1);
		a[3] = param;
	}
	
	private SourceDataLine	source = null;
	
	protected void test3(short[] samples, int offs, int len) {
		byte[] b = toByteArray(samples, offs, len);
		source.write(b, 0, len*2);
	}
	
	private byte[] byteBuf = new byte[4096]; //Residual 4096
	
	/**
	 * Temporal = 0
	 * Residual = length+1024
	 * @param length
	 * @return
	 */
	protected byte[] getByteArray(int length) {
		if (byteBuf.length < length) {
			byteBuf = new byte[length+1024];
		}
		return byteBuf;
	}

	protected byte[] toByteArray(short[] samples, int offs, int len) {
		byte[] b = getByteArray(len*2);
		int idx = 0;
		short s;
		while (len-- > 0) {
			s = samples[offs++];
			b[idx++] = (byte)s;
			b[idx++] = (byte)(s>>>8);
		}
		return b;
	}
}
