package org.openslat.numerical;

import org.apache.commons.math3.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.util.FastMath;

public class MagnitudeAdaptiveQuadratureIntegrator extends
		BaseAbstractUnivariateIntegrator {

	public static final int MAQ_MAX_ITERATIONS_COUNT = 10000000;

	public MagnitudeAdaptiveQuadratureIntegrator(double relativeAccuracy,
			double absoluteAccuracy, int minimalIterationCount,
			int maximalIterationCount) throws NotStrictlyPositiveException,
			NumberIsTooSmallException {
		super(relativeAccuracy, absoluteAccuracy, minimalIterationCount,
				maximalIterationCount);

		if (maximalIterationCount > MAQ_MAX_ITERATIONS_COUNT) {
			throw new NumberIsTooLargeException(maximalIterationCount,
					MAQ_MAX_ITERATIONS_COUNT, false);
		}
	}

	public MagnitudeAdaptiveQuadratureIntegrator(
			final int minimalIterationCount, final int maximalIterationCount)
			throws NotStrictlyPositiveException, NumberIsTooSmallException,
			NumberIsTooLargeException {
		super(minimalIterationCount, maximalIterationCount);
		if (maximalIterationCount > MAQ_MAX_ITERATIONS_COUNT) {
			throw new NumberIsTooLargeException(maximalIterationCount,
					MAQ_MAX_ITERATIONS_COUNT, false);
		}
	}

	public MagnitudeAdaptiveQuadratureIntegrator() {
		super(DEFAULT_MIN_ITERATIONS_COUNT, MAQ_MAX_ITERATIONS_COUNT);
	}

	double quad(double a, double b, double c, double fa, double fb, double fc) {
		return (b - a) / 6 * (fa + 4 * fc + fb);
	}

	@Override
	public double doIntegrate() throws TooManyEvaluationsException,
			MaxCountExceededException {

		double integral = 0;
		int ibin = 1;

		double a = this.getMin();
		double b = this.getMax();
		double c = (a + b) / 2;
		double fa = this.computeObjectiveValue(a);
		double fb = this.computeObjectiveValue(b);
		double fc = this.computeObjectiveValue(c);

		// double epsilon = 1e-10;
		// int pre_n = 0;
		// int pre_k = 0;
		// double pre_h = 0;
		// double pre_z = 0;
		// double pre_fz = 0;
		// boolean flag = false;
		// while (!flag && pre_n < 1000000) {
		// ++pre_k;
		// pre_n = (int) FastMath.pow(2, pre_k);
		// pre_h = (b - a) / pre_n;
		// for (int i = 1; i < pre_n / 2; ++i) {
		// pre_z = (2 * i - 1) * pre_h;
		// pre_fz = this.computeObjectiveValue(pre_z);
		// if (FastMath.abs(pre_fz) > epsilon) {
		// a = pre_z - pre_h;
		// b = pre_z + pre_h;
		// flag = true;
		// }
		// }
		// }

		double r1 = quad(a, b, c, fa, fb, fc);
		iterations.incrementCount();

		double d;
		double e;
		double fd;
		double fe;
		double r2;
		double r3;
		double Q1;
		double Q2;

		boolean flag2 = true;

		while (true && ibin != 0) {
			d = (a + c) / 2;
			e = (c + b) / 2;
			fd = this.computeObjectiveValue(d);
			fe = this.computeObjectiveValue(e);

			r2 = quad(a, c, d, fa, fc, fd);
			r3 = quad(c, b, e, fc, fb, fe);

			Q1 = r1;
			Q2 = r2 + r3;

			final double delta = FastMath.abs(Q2 - Q1);

			// final double rLimit = 1e-4
			// * (FastMath.abs(Q1) + FastMath.abs(Q2)) * 0.5;
			// if ((delta <= rLimit) || (delta <= 1e-10)) {
			// integral = integral + Q2 + (Q2 - Q1) / 15;
			// flag2 = false;
			// }

			if ((delta <= 1e-4 * Q2) || (delta <= 1e-10 * integral)) {
				integral = integral + Q2 + (Q2 - Q1) / 15;
				flag2 = false;
			}

			System.err.println(ibin);
			System.err.println(integral);

			if (FastMath.abs(r2) > FastMath.abs(r3) && flag2) {

				ibin = ibin + 4;

				b = c;
				fb = fc;
				c = d;
				fc = fd;
				r1 = r2;

			} else if (flag2) {

				ibin = ibin + 4;

				a = c;
				fa = fc;
				c = e;
				fc = fe;
				r1 = r3;
			}

			flag2 = true;

			if (ibin == 1) {
				return integral;
			}

			ibin = ibin - 4;

			iterations.incrementCount();
			if (iterations.getCount() > this.getMaximalIterationCount()) {
				throw new MaxCountExceededException(iterations.getCount());
			}
		}
		return integral;
	}
}
