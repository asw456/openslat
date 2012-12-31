/**
 * 
 */
package org.openslat.interfaces;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.openslat.models.univariate.AslaniModel;
import org.openslat.models.univariate.BradleyModel;
import org.openslat.models.univariate.HyperbolicModel;
import org.openslat.models.univariate.ParabolicModel;
import org.openslat.models.univariate.PowerModel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

/**
 * @author alan
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@Type(value = PowerModel.class), // ,name = "PowerModel")
		@Type(value = ParabolicModel.class),
		@Type(value = HyperbolicModel.class),
		@Type(value = BradleyModel.class), @Type(value = AslaniModel.class) })
public interface SerialUnivariateFunction extends UnivariateFunction {

}
