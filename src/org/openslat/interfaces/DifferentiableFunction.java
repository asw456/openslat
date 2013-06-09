package org.openslat.interfaces;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.openslat.model.im.IMRDiscreteModel;
import org.openslat.models.univariate.AslaniModel;
import org.openslat.models.univariate.HyperbolicModel;
import org.openslat.models.univariate.HyperbolicModel2;
import org.openslat.models.univariate.ParabolicModel;
import org.openslat.models.univariate.PowerModel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Distribution model interface for one-to-distribution parametric and numerical
 * models.
 * 
 * @author Alan Williams
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@Type(value = PowerModel.class), // ,name = "PowerModel")
		@Type(value = ParabolicModel.class),
		@Type(value = HyperbolicModel2.class),
		@Type(value = HyperbolicModel.class), @Type(value = AslaniModel.class),
		@Type(value = IMRDiscreteModel.class)})
public interface DifferentiableFunction extends UnivariateFunction {
	public double derivative(double x);
	public int hashCode();
	public boolean equals(Object obj);
}
