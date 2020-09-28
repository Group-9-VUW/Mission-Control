/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.montecarlo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.io.CSVWriter;

/**
 * A class representing a config for the monte carlo simulation program
 * 
 * @author Claire
 */
public class MonteCarloConfig {

	/**
	 * The values that this object represents
	 */
	private final Map<MonteCarloConfigValue, Double> values;

	/**
	 * @param values
	 */
	public MonteCarloConfig(Map<MonteCarloConfigValue, Double> values) 
	{
		this.values = values;
	}
	
	
	/**
	 * @param file The file to write to. Will be overwritten if it already exists
	 * @throws IOException If there is an error writing to the file
	 */
	@SuppressWarnings("null")
	public void writeTo(File file) throws IOException
	{
		file.createNewFile();
		
		//Use a list because order is important
		List<Map.Entry<MonteCarloConfigValue, Double>> entries = new ArrayList<>();
		entries.addAll(Null.nonNull(this.values.entrySet()));
		
		try(CSVWriter writer = new CSVWriter(file))
		{
			for(int i = 0; i < entries.size(); i++) 
				writer.writeValue(entries.get(i).getKey().getName());
			writer.nextRow();
			for(int i = 0; i < entries.size(); i++) 
				writer.writeValue(entries.get(i).getKey().getToStringFunc().apply(entries.get(i).getValue()));
		}
	}
	
}
