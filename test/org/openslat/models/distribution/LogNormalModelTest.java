package org.openslat.models.distribution;

import static org.junit.Assert.*;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.junit.Before;
import org.junit.Test;
import org.openslat.models.univariate.PowerModel;
import org.openslat.numerical.LNConverter;

public class LogNormalModelTest {

	PowerModel pm1;
	PowerModel pm2;
	LogNormalModel lgnmdl = new LogNormalModel();

	@Before
	public void setUp() throws Exception {
		pm1 = new PowerModel();
		double[] pparams1 = { 0.1, 1.5 };
		pm1.constructPowerModel(pparams1);
		pm2 = new PowerModel();
		double[] pparams2 = { 0.5, 0.0 };
		pm2.constructPowerModel(pparams2);
		lgnmdl.setMeanModel(pm1);
		lgnmdl.setStddModel(pm2);
	}

	@Test
	public void test1() {
		double testValue = 1;
		LogNormalDistribution distribution = lgnmdl
				.distribution(testValue);
		double mu = LNConverter.muGivenMeanSigma(pm1.value(testValue), pm2.value(testValue));
		assertEquals(mu, distribution.getScale(), 1e-3);
	}

	@Test
	public void test2() {
		double testValue = 0.1;
		LogNormalDistribution distribution = lgnmdl
				.distribution(testValue);
		double mu = LNConverter.muGivenMeanSigma(pm1.value(testValue), pm2.value(testValue));
		assertEquals(mu, distribution.getScale(), 1e-3);
	}
}
