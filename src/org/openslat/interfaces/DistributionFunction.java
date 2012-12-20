package org.openslat.interfaces;

import org.apache.commons.math3.distribution.RealDistribution;
import org.openslat.models.distribution.LogNormalModel;
import org.openslat.models.distribution.NormalModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import java.util.ArrayList;

/**
 * Distribution model interface for one-to-distribution parametric and numerical
 * models.
 * 
 * @author Alan Williams
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = LogNormalModel.class), // ,name = "ln model")
		@Type(value = NormalModel.class) })
public interface DistributionFunction {
	public RealDistribution distribution(double x);

	public ArrayList<ArrayList<Double>> getTable();
}
