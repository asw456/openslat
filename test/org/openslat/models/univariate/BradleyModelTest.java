package org.openslat.models.univariate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BradleyModelTest {

	BradleyModel bm1;

	@Before
	public void setUp() throws Exception {
		bm1 = new BradleyModel();
		double[] pparams1 = { 1221, 29.8, 62.2 };
		bm1.constructBradleyModel(pparams1);
	}

	@Test
	public void test1() {
		assertEquals(0.03, bm1.value(0.1), 0.01);
	}

	@Test
	public void test2() {
		assertEquals(0.00001345, bm1.value(1), 0.0000001);
	}

	@Test
	public void test3() {
		assertEquals(0.51279, bm1.value(0.01), 0.01);
	}

	@Test
	public void test4() {
		double one = bm1.value(0.5 + 0.000001);
		double two = bm1.value(0.5 - 0.000001);
		double deriv = (one - two) / (2 * 0.000001);
		assertEquals(deriv, bm1.derivative(0.5), 0.00001);
		//System.out.println(deriv);
	}

	@Test
	public void test5() {
		double one = bm1.value(0.001 + 0.00000001);
		double two = bm1.value(0.001 - 0.00000001);
		double deriv = (one - two) / (2 * 0.00000001);
		assertEquals(deriv, bm1.derivative(0.001), 0.01);
		//System.out.println(deriv);
	}
}
