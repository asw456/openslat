package org.openslat.numerical;

import static org.junit.Assert.*;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.util.FastMath;
import org.junit.Before;
import org.junit.Test;

public class MagnitudeAdaptiveQuadratureIntegratorTest {

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public final void testIntegrate2() {
//
//		MagnitudeAdaptiveQuadratureIntegrator integrator = new MagnitudeAdaptiveQuadratureIntegrator();
//
//		double lowerLimit = 1;
//		double upperLimit = 2;
//
//		assertEquals(0.691371,
//				integrator.integrate(10000000, new UnivariateFunction() {
//					public double value(double t) {
//						return (FastMath.sqrt(FastMath.sin(t) * 0.5));
//					}
//				}, lowerLimit, upperLimit), 0.00000138);
//	}

	@Test
	public final void testIntegrate3() {

		SimpsonIntegrator integrator = new SimpsonIntegrator();

		double lowerLimit = 1;
		double upperLimit = 2;

		assertEquals(0.691371,
				integrator.integrate(10000000, new UnivariateFunction() {
					public double value(double t) {
						return (FastMath.sqrt(FastMath.sin(t) * 0.5));
					}
				}, lowerLimit, upperLimit), 0.000001);
	}

	@Test
	public final void testIntegrate4() {

		RombergIntegrator integrator = new RombergIntegrator();

		double lowerLimit = 1;
		double upperLimit = 2;

		assertEquals(0.691371,
				integrator.integrate(10000000, new UnivariateFunction() {
					public double value(double t) {
						return (FastMath.sqrt(FastMath.sin(t) * 0.5));
					}
				}, lowerLimit, upperLimit), 0.000001);
	}

	@Test
	public final void testIntegrate5() {

		double lowerLimit = 0-1e-5;
		double upperLimit = Math.PI + 1e-5;

		UnivariateFunction f = new UnivariateFunction() {
			@Override
			public double value(double t) {
				return (FastMath.sin(t));
			};
		};

		MAQIntegrator integrator = new MAQIntegrator(lowerLimit, upperLimit, f);

		assertEquals(2, integrator.doIntegrate(), 0.00000001);
	}

	@Test
	public final void testIntegrate6() {

		MagnitudeAdaptiveQuadratureIntegrator integrator = new MagnitudeAdaptiveQuadratureIntegrator();

		double lowerLimit = 0;
		double upperLimit = Math.PI;

		assertEquals(2,
				integrator.integrate(10000000, new UnivariateFunction() {
					@Override
					public double value(double t) {
						return (FastMath.sin(t));
					}
				}, lowerLimit, upperLimit), 0.000001);

	}

}
