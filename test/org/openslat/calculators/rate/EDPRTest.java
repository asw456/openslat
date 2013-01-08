package org.openslat.calculators.rate;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openslat.model.edp.EDP;
import org.openslat.model.edp.EDPIM;
import org.openslat.model.im.IM;
import org.openslat.model.im.IMR;
import org.openslat.models.distribution.LogNormalModel;
import org.openslat.models.univariate.BradleyModel;
import org.openslat.models.univariate.PowerModel;

public class EDPRTest {

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
	public final void testEdpRate() {
		
		BradleyModel bm = new BradleyModel();
		double[] bparams = { 1221, 29.8, 62.2 };
		bm.constructBradleyModel(bparams);

		IMR imr1 = new IMR();
		imr1.setImRName("imr1");
		imr1.setModel(bm);

		ArrayList<IMR> iMR = new ArrayList<IMR>();
		iMR.add(imr1);

		IM im = new IM();
		im.setName("happiness");
		im.setiMR(iMR);
		// =====================================

		// Construct EDP =======================
		
		PowerModel pm1 = new PowerModel();
		double[] pparams1 = { 0.1, 1.5 };
		pm1.constructPowerModel(pparams1);
		
		PowerModel pm2 = new PowerModel();
		double[] pparams2 = { 0.5, 0.0 };
		pm2.constructPowerModel(pparams2);
		
        LogNormalModel lgnmdl = new LogNormalModel();
        lgnmdl.setMeanModel(pm1);
        lgnmdl.setStddModel(pm2);
        
        EDPIM edpIm = new EDPIM();
        edpIm.setDistributionFunction(lgnmdl);
        
        EDP edp = new EDP();
        edp.setEdpIM(new ArrayList<EDPIM>());
        edp.addEdpIM(edpIm);
		// =====================================
		
        EDPR calc = new EDPR();
        assertEquals(0.021,calc.edpRate(0.01, edp, im, null),0.1);
        
		
	}

}
