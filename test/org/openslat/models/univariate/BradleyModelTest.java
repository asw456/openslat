package org.openslat.models.univariate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BradleyModelTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		BradleyModel bm1 = new BradleyModel();
		double[] pparams1 = { 1221, 29.8, 62.2 };
		bm1.constructBradleyModel(pparams1);
		
		BradleyModel bm2 = new BradleyModel();
		double[] pparams2 = { 1221, 29.8, 62.2 };
		bm2.constructBradleyModel(pparams2);

		assertEquals(1.0,bm1.value(1),0.1);
		assertEquals(1.0,bm1.derivative(1),0.1);
		
		assertEquals(1.0,bm2.value(1),0.1);
		assertEquals(1.0,bm2.derivative(1),0.1);
	}

}
