package org.openslat.models.univariate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PowerModelTest {

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

		PowerModel pm1 = new PowerModel();
		double[] pparams1 = { 0.1, 1.5 };
		pm1.constructPowerModel(pparams1);
		
		PowerModel pm2 = new PowerModel();
		double[] pparams2 = { 0.5, 0.0 };
		pm2.constructPowerModel(pparams2);

		assertEquals(1.0,pm1.value(1),0.1);
		assertEquals(1.0,pm1.derivative(1),0.1);
		
		assertEquals(1.0,pm2.value(1),0.1);
		assertEquals(1.0,pm2.derivative(1),0.1);
		
		
	}

}
