package org.openslat.examples;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ArrayListJsonTest {
	@JsonIgnore
	public ArrayList<ArrayList<Double>> temp;
	public ArrayList<double[]> temp1;
}
