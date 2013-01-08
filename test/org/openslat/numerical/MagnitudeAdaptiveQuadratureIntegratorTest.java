package org.openslat.numerical;

import static org.junit.Assert.*;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.util.FastMath;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MagnitudeAdaptiveQuadratureIntegratorTest {

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
	public final void testIntegrate() {

		MagnitudeAdaptiveQuadratureIntegrator integrator = new MagnitudeAdaptiveQuadratureIntegrator();

		double lowerLimit = 0;
		double upperLimit = 10;

		assertEquals(463.3333333,
				integrator.integrate(10000000, new UnivariateFunction() {
					public double value(double t) {
						return (FastMath.pow(t, 2) + 2 * t + 3);
					}
				}, lowerLimit, upperLimit), 0.1);

	}

}
