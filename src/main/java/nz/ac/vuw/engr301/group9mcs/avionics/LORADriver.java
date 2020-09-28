package nz.ac.vuw.engr301.group9mcs.avionics;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;

import jssc.SerialPort;
import jssc.SerialPortException;
import nz.ac.vuw.engr301.group9mcs.commons.RocketData;
import nz.ac.vuw.engr301.group9mcs.commons.RocketDataListener;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Condition;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.Null;
import nz.ac.vuw.engr301.group9mcs.commons.conditions.PreconditionViolationException;
import nz.ac.vuw.engr301.group9mcs.commons.logging.DefaultLogger;

/**
 * A driver for the USB LoRA receiver. 
 * 
 * @author Claire
 */
public class LORADriver {
	
	/**
	 * A list of the listeners for this driver
	 */
	private final Set<RocketDataListener> listeners = new HashSet<>();
	
	/**
	 * Whether or not the driver is active or not
	 */
	private boolean running = false;
	
	/**
	 * The serial COM port used by this driver
	 */
	@Nullable private SerialPort port;
	
	/**
	 * The current thread for auto-reading serial data
	 */
	@Nullable private LORADriverThread currentThread;
	
	/**
	 *	Initializes the driver with a COM port
	 * @param comPort the COM port to use
	 * @throws SerialPortException if there is a serial exception during initialization
	 */
	public void init(String comPort) throws SerialPortException
	{
		if(!COMHelper.comPortExists(comPort)) throw new PreconditionViolationException("COM port must exist");
		if(this.running) throw new PreconditionViolationException("Cannot initialize LORA driver when it is already in operation");
		SerialPort thePort = this.port = new SerialPort(comPort);
		this.currentThread = this.new LORADriverThread(new DelimitedSerialStream(thePort, '\n'));
		try {
			thePort.openPort();
		} catch(SerialPortException e) {
			Null.nonNull(this.currentThread).threadRunning = false;
			this.currentThread = null;
			this.port = null;
			throw e;
		}
		this.running = true;
	}
	
	/**
	 * Stops this driver from working. All data read/write is halted
	 */
	public void stop()
	{
		if(!this.running) throw new PreconditionViolationException("Cannot stop LORA driver when it is not in operation");
		Condition.INVARIANT.nonNull("port", this.port);
		Condition.INVARIANT.nonNull("currentThread", this.currentThread);
		Null.nonNull(this.currentThread).threadRunning = false;
		try {
			Null.nonNull(this.currentThread).join();
		} catch (@SuppressWarnings("unused") InterruptedException e) { /**/ }
		try {
			Null.nonNull(this.port).closePort();
		} catch (SerialPortException e) {
			DefaultLogger.logger.error("Error closing port");
			DefaultLogger.logger.error(e);
		}
		this.port = null;
		this.currentThread = null;
		this.running = false;
	}
	
	/**
	 * Adds a listener to this driver
	 * 
	 * @param listener
	 */
	public void addRocketDataListener(RocketDataListener listener)
	{
		this.listeners.add(listener);
	}
	
	/**
	 * Removes a listener from this driver
	 * 
	 * @param listener
	 */
	public void removeRocketDataListener(RocketDataListener listener)
	{
		this.listeners.remove(listener);
	}
	
	/**
	 * @return The listeners for this driver;
	 */
	protected Set<RocketDataListener> getListeners()
	{
		return this.listeners;
	}
	
	/**
	 * A thread that automatically reads data and sends it to listeners
	 * 
	 * @author Claire
	 */
	protected final class LORADriverThread extends Thread {
		
		/**
		 * Whether or not this thread is running
		 */
		protected volatile boolean threadRunning = true;
		
		/**
		 * A serial-backed delimited string stream, used as input for the parser
		 */
		protected final DelimitedSerialStream stream;
		
		/**
		 * The data parser for the base station data
		 */
		protected final BaseStationParser parser = new BaseStationParser();
		
		/**
		 * @param stream The serial stream
		 */
		public LORADriverThread(DelimitedSerialStream stream)
		{
			this.stream = stream;
		}
		
		@Override
		public void run()
		{
			while(this.threadRunning)
			{
				try {
					synchronized(this) {
						this.wait(100);
					}
				} catch(@SuppressWarnings("unused") InterruptedException e) { /**/ }
				
				if(this.threadRunning) {
					try {
						this.stream.poll();
					} catch (SerialPortException e) {
						this.threadRunning = false;
						//TODO: What do we do if this doesn't work???
						DefaultLogger.logger.error("Serial exception polling serial input");
						DefaultLogger.logger.error(e);
					}
					
					while(this.stream.stringsAvailable() > 0) {
						RocketData data = this.parser.parse(this.stream.read());
						for(RocketDataListener listener : LORADriver.this.getListeners()) {
							listener.receiveRocketData(data);
						}
					}
				}
			}
		}
		
		
		
	}

}
