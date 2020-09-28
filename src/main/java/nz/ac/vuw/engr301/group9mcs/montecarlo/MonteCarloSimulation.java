package nz.ac.vuw.engr301.group9mcs.montecarlo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nz.ac.vuw.engr301.group9mcs.commons.SimpleEventListener;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.io.CSVReader;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;

/**
 * A class representing a monte carlo simulation
 * 
 * @author Claire
 */
public class MonteCarloSimulation extends Thread {

	/**
	 * List of listeners
	 */
	private final List<SimpleEventListener> listeners = new ArrayList<>();
	
	/**
	 * The file where the results will be deposited
	 */
	private final File results;
	
	/**
	 * The running process for this simulation
	 */
	private final Process process;
	
	/**
	 * A reader for this simulations output
	 */
	private final BufferedReader reader;
	
	/**
	 * Whether or not this simulation is finished or not.
	 */
	private boolean done = false;
	
	/**
	 * A string representing the simulation progress
	 */
	private String progressString = "Not started";
	
	/**
	 * @param process
	 * @param results 
	 */
	public MonteCarloSimulation(Process process, File results)
	{
		this.process = process;
		this.results = results;
		this.reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		this.start();
	}
	
	@Override
	public void run()
	{
		while(this.process.isAlive())
		{
			try {
				if(this.reader.ready()) {
					String str = this.reader.readLine();
					if(str.matches("Simulating: [0-9]+.[0-9]+%[^q]*")) {
						this.progressString = Null.nonNull(str.substring(str.indexOf(' ') + 1, str.indexOf('%') + 1));
						this.informListeners();
					}
					if(str.contains("Simulations finished")) {
						this.done = true;
						this.informListeners();
						return;
					}
				}
			} catch (@SuppressWarnings("unused") IOException e) { /* Should never happen */}
		}
	}
	
	/**
	 * @return Whether or not this process has been completed
	 */
	public boolean isDone()
	{
		return this.done;
	}
	
	/**
	 * @return A string representing the progress of this simulation.
	 */
	public String getProgressString()
	{
		return this.progressString;
	}
	
	/**
	 * @return The results of the simulation
	 * @throws IOException If there's an error reading them
	 */
	public List<Point> getResults() throws IOException
	{
		if(!this.done) {
			throw new PreconditionViolationException("Can't get the results of an incomplete simulation");
		}
		
		int count = 0;
		while(this.process.isAlive()) {
			synchronized(this) {
				try {
					this.wait(100);
				} catch(@SuppressWarnings("unused") InterruptedException e) { /**/ }
			}
			count++;
			if(count == 100) {
				throw new IOException("Thread would not properly terminate after reporting completion");
			}
		}
		
		try(CSVReader csvReader = new CSVReader(this.results)) {
			if(!csvReader.hasNextLine()) return new ArrayList<>();
			csvReader.nextLine();
			
			List<Point> points = new ArrayList<>();
			while(csvReader.hasNextLine()) {
				csvReader.nextLine();
				if(!csvReader.hasNextValue()) break;
				double lon = Double.parseDouble(csvReader.nextValue());
				double lat = Double.parseDouble(csvReader.nextValue());
				points.add(new Point(lat, lon));
			}
			
			return points;
		}
	}
	
	/**
	 * Adds a listener to this active simulation. Every update of the simulation will be given the event name "montecarlo"
	 * 
	 * @param listener The listener to add
	 */
	public void addSimulationListener(SimpleEventListener listener)
	{
		this.listeners.add(listener);
	}
	
	/**
	 * Informs the listeners that the state has changed
	 */
	private void informListeners()
	{
		for(SimpleEventListener listener : this.listeners) {
			listener.event("montecarlo");
		}
	}
	
}
