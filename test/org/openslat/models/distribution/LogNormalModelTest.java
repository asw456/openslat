package org.openslat.models.distribution;

import static org.junit.Assert.*;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openslat.models.univariate.PowerModel;

public class LogNormalModelTest {

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
		
        LogNormalModel lgnmdl = new LogNormalModel();
        lgnmdl.setMeanModel(pm1);
        lgnmdl.setStddModel(pm2);
		
        double valueToGetDistributionFor = 1;
        LogNormalDistribution distribution = lgnmdl.distribution(valueToGetDistributionFor);
        
        assertEquals(1.0,distribution.getScale(),0.1);
        assertEquals(1.0,distribution.getShape(),0.1);
		
	}

}
