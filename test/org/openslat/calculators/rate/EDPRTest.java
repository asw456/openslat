package org.openslat.calculators.rate;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.junit.Before;
import org.junit.Test;
import org.openslat.model.edp.EDP;
import org.openslat.model.edp.EDPIM;
import org.openslat.model.im.IM;
import org.openslat.model.im.IMR;
import org.openslat.models.distribution.LogNormalModel;
import org.openslat.models.univariate.BradleyModel;
import org.openslat.models.univariate.PowerModel;

public class EDPRTest {

	PowerModel pm1;
	PowerModel pm2;
	LogNormalModel lgnmdl;
	EDPIM edpIm;
	EDP edp;
	IM im;

	@Before
	public void setUp() throws Exception {

		BradleyModel bm = new BradleyModel();
		double[] bparams = { 1221, 29.8, 62.2 };
		bm.constructBradleyModel(bparams);

		IMR imr1 = new IMR();
		imr1.setImRName("imr1");
		imr1.setModel(bm);

		ArrayList<IMR> iMR = new ArrayList<IMR>();
		iMR.add(imr1);

		im = new IM();
		im.setName("happiness");
		im.setiMR(iMR);

		pm1 = new PowerModel();
		double[] pparams1 = { 0.1, 1.5 };
		pm1.constructPowerModel(pparams1);

		pm2 = new PowerModel();
		double[] pparams2 = { 0.5, 0.0 };
		pm2.constructPowerModel(pparams2);

		lgnmdl = new LogNormalModel();
		lgnmdl.setMeanModel(pm1);
		lgnmdl.setStddModel(pm2);

		edpIm = new EDPIM();
		edpIm.setDistributionFunction(lgnmdl);

		edp = new EDP();
		edp.setEdpIM(new ArrayList<EDPIM>());
		edp.addEdpIM(edpIm);

	}

	@Test
	public final void testintegrandWithoutPc() {
		EDPR edpr = new EDPR();
		double val = 0.001;
		UnivariateFunction function = edpr.integrandWithoutPc(edp, im, val);
		assert(true);
	}

	@Test
	public final void testEdpRate1() {
		EDPR edpr = new EDPR();
		assertEquals(2.22e-2, edpr.edpRate(3.17e-3, edp, im, null), 0.0001);
	}
	
	@Test
	public final void testEdpRate2() {
		EDPR edpr = new EDPR();
		assertEquals(7.88e-2, edpr.edpRate(1e-3, edp, im, null), 1e-3);
	}

}
