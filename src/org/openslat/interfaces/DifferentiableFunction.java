package org.openslat.interfaces;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.openslat.models.univariate.PowerModel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Distribution model interface for one-to-distribution parametric and numerical
 * models.
 * 
 * @author James Williams
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = PowerModel.class, name = "PowerModel") })
public interface DifferentiableFunction extends UnivariateFunction {
	public double derivative(double x);
}
