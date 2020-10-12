package nz.ac.vuw.engr301.group9mcs.montecarlo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import nz.ac.vuw.engr301.group9mcs.commons.LaunchRodData;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.map.Point;
import nz.ac.vuw.engr301.group9mcs.externaldata.weather.NOAAWeatherData;

/**
 * An interface to invoke the monte carlo simulation program
 * 
 * @author Claire
 */
public class MonteCarloBridge {
	
	/**
	 * The root directory containing the simulation jarfile 
	 */
	public static final String ROOT_DIR = "simulations/";
	
	/**
	 * Location of the simulation jar file
	 */
	private final File simulationsJar;
	
	/**
	 * The input CSV file
	 */
	private final File inputCSV;
	
	/**
	 * The output CSV file
	 */
	private final File outputCSV;
	
	/**
	 * @throws Exception If not all the relevant files required by this class are available
	 */
	public MonteCarloBridge() throws Exception
	{
		//TODO: Make a meaningful exception for this
		try {
			this.simulationsJar = new File(ROOT_DIR + "simulations.jar");
			this.inputCSV = new File(ROOT_DIR + "input.csv");
			this.outputCSV = new File(ROOT_DIR + "points.csv");
			
			if(!this.simulationsJar.exists() || !this.simulationsJar.isFile()) {
				throw new Exception("Simulations jar not present in simulations directory.");
			}
			//Ensure we have write ability over the two files (ie, they aren't locked)
			this.inputCSV.createNewFile();
			this.outputCSV.createNewFile();
		} catch(IOException e) {
			throw new Exception("Exception in trying to obtain custody over input and output files.", e);
		}
	}
	
	/**
	 * @param location The launch location
	 * @param weather The weather data at the location at the time of launch
	 * @param data The launch rod data
	 * @param simulations The number of simulations to run
	 * @return An object representing a running simulation
	 * @throws IOException If there's an error writing to the file
	 */
	public MonteCarloSimulation runSimulation(Point location, List<NOAAWeatherData> weather, LaunchRodData data, int simulations) throws IOException
	{
		MonteCarloConfig conf = new MonteCarloConfigBuilder()
				.addPosition(location.getLatitude(), location.getLongitude())
				.addWeather(weather)
				.addLaunchRodData(data)
				.addSimulationTarget(simulations)
				.build();
		conf.writeTo(this.inputCSV);
		@NonNull Process process = Null.nonNull(Runtime.getRuntime().exec("java -jar \"" + this.simulationsJar.getCanonicalPath() + "\" -nogui \"" + this.inputCSV.getCanonicalPath() + "\"", new String[0], new File(ROOT_DIR)));
		return new MonteCarloSimulation(process, this.outputCSV);
	}
	
	@SuppressWarnings("javadoc")
	public static void main(String[] strings) throws Exception 
	{
		MonteCarloBridge bridge = new MonteCarloBridge();
		Point point = new Point(-41.2913698, 174.7734964);
		@SuppressWarnings("null")
		@NonNull List<NOAAWeatherData> data = Arrays.asList(new NOAAWeatherData(228.123, 3.4823, 85.77337, 287.06771, 100000.0));
		MonteCarloSimulation sim = bridge.runSimulation(point, data, new LaunchRodData(0, 0, 0.2), 100);
		sim.addSimulationListener((s) -> {
			System.out.println(sim.isDone());
			System.out.println(sim.getProgressString());
			if(sim.isDone()) {
				try {
					List<Point> points = sim.getResults();
					for(Point p : points) {
						System.out.println(p.getLatitude() + "," + p.getLongitude());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
