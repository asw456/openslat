package org.openslat.numerical;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.util.FastMath;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;

public class MAQIntegrator {

	public static final int MAQ_MAX_ITERATIONS_COUNT = 1000000;
	public double min;
	public double max;
	public UnivariateFunction f;
	public int iterations = 1;

	// public MAQIntegrator(double relativeAccuracy,
	// double absoluteAccuracy, int minimalIterationCount,
	// int maximalIterationCount) throws NotStrictlyPositiveException,
	// NumberIsTooSmallException {
	// }
	//
	// public MAQIntegrator(
	// final int minimalIterationCount, final int maximalIterationCount)
	// throws NotStrictlyPositiveException, NumberIsTooSmallException,
	// NumberIsTooLargeException {
	// }

	public MAQIntegrator(double min, double max, UnivariateFunction f) {
		this.min = min;
		this.max = max;
		this.f = f;
	}

	double quad(double a, double b, double c, double fa, double fb, double fc) {
		return ((b - a) / 6) * (fa + 4 * fc + fb);
	}

	public double doIntegrate() throws TooManyEvaluationsException,
			MaxCountExceededException {

		double integral = 0;
		int ibin = 1;
		SparseDoubleMatrix2D store = new SparseDoubleMatrix2D(1000010, 2);

		double a = this.getMin();
		double b = this.getMax();
		double c = (a + b) / 2;
		double fa = this.f.value(a);
		double fb = this.f.value(b);
		double fc = this.f.value(c);

//		double epsilon = 1e-10;
//		int pre_n = 0;
//		int pre_k = 0;
//		double pre_h = 0;
//		double pre_z = 0;
//		double pre_fz = 0;
//		boolean flag = false;
//		while (!flag && pre_n < 1000000) {
//			++pre_k;
//			pre_n = (int) FastMath.pow(2, pre_k);
//			pre_h = (b - a) / pre_n;
//			for (int i = 1; i < pre_n / 2; i++) {
//
//				if (!flag) {
//					pre_z = (2 * i - 1) * pre_h;
//					pre_fz = this.f.value(pre_z);
//					if (FastMath.abs(pre_fz) > epsilon) {
//						a = pre_z - pre_h;
//						b = pre_z + pre_h;
//						flag = true;
//					}
//				}
//
//			}
//		}

		double r1 = quad(a, b, c, fa, fb, fc);

		double d;
		double e;
		double fd;
		double fe;
		double r2;
		double r3;
		double Q1;
		double Q2;

		boolean flag2 = false;
		boolean flag3 = false;

		while (ibin != 0) {

			while (!flag2) {

				if (iterations > MAQ_MAX_ITERATIONS_COUNT) {
					throw new MaxCountExceededException(iterations);
				}
				++iterations;

				d = (a + c) / 2;
				e = (c + b) / 2;
				fd = this.f.value(d);
				fe = this.f.value(e);

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

				flag3 = false;

				if ((delta <= 1e-4 * Q2) || (delta <= 1e-10 * integral)) {
					integral = integral + Q2 + (Q2 - Q1) / 15;
					flag3 = true;
					flag2 = true;
				}

				if (FastMath.abs(r2) > FastMath.abs(r3) && !flag3) {
					store.set(ibin, 0, c);
					store.set(ibin, 1, fc);
					store.set(ibin + 1, 0, b);
					store.set(ibin + 1, 1, fb);
					store.set(ibin + 2, 0, e);
					store.set(ibin + 2, 1, fe);
					store.set(ibin + 3, 0, r3);
					ibin = ibin + 4;

					b = c;
					fb = fc;
					c = d;
					fc = fd;
					r1 = r2;

				} else if (!flag3) {

					store.set(ibin, 0, a);
					store.set(ibin, 1, fa);
					store.set(ibin + 1, 0, c);
					store.set(ibin + 1, 1, fc);
					store.set(ibin + 2, 0, d);
					store.set(ibin + 2, 1, fd);
					store.set(ibin + 3, 0, r2);
					ibin = ibin + 4;

					a = c;
					fa = fc;
					c = e;
					fc = fe;
					r1 = r3;
				}

			}

			flag2 = false;

			if (ibin == 1) {
				return integral;
			}

			r1 = store.get(ibin - 1, 0);
			c = store.get(ibin - 2, 0);
			fc = store.get(ibin - 2, 1);
			b = store.get(ibin - 3, 0);
			fb = store.get(ibin - 3, 1);
			a = store.get(ibin - 4, 0);
			fa = store.get(ibin - 4, 1);

			ibin = ibin - 4;

		}
		return integral;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public UnivariateFunction getF() {
		return f;
	}

	public void setF(UnivariateFunction f) {
		this.f = f;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
}
