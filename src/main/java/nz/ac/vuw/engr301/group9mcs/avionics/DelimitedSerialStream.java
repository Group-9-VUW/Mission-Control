/**
 * 
 */
package nz.ac.vuw.engr301.group9mcs.avionics;

import java.util.ArrayDeque;

import jssc.SerialPort;
import jssc.SerialPortException;
import nz.ac.vuw.engr301.group9mcs.commons.DefaultLogger;
import nz.ac.vuw.engr301.group9mcs.commons.Null;

/**
 * @author Claire
 */
public class DelimitedSerialStream {
	
	/**
	 * The serial port this stream reads its data from
	 */
	private final SerialPort port;
	
	/**
	 * The buffer this stream uses to build strings
	 */
	private final StringBuilder buffer = new StringBuilder();
	
	/**
	 * The queue of strings waiting to be read.
	 */
	private final ArrayDeque<String> queue = new ArrayDeque<>();
	
	/**
	 * The string delimiter for this stream
	 */
	private final char delimiter;
	
	/**
	 * @param port The serial port which to construct this stream for
	 * @param delimiter The character by which to delimit the strings
	 */
	public DelimitedSerialStream(SerialPort port, char delimiter)
	{
		this.port = port;
		this.delimiter = delimiter;
	}
	
	/**
	 * Polls the serial port 
	 * @throws SerialPortException If there is an error reading from the serial port
	 */
	public void poll() throws SerialPortException
	{
		int bytesToRead = this.port.getInputBufferBytesCount();
		byte[] bytes = this.port.readBytes(bytesToRead);
		DefaultLogger.logger.debug("Serial: Read" + bytes.length + "/" + bytesToRead + " bytes.");
		for(byte b : bytes) {
			char c = (char) b;
			if(c == this.delimiter) {
				if(this.buffer.length() > 0)
					this.queue.addLast(Null.nonNull(this.buffer.toString()));
				this.buffer.delete(0, this.buffer.length());
			} else {
				this.buffer.append(c);
			}
		}
	}
	
	/**
	 * @return The number of strings in this stream's buffer.
	 */
	public int stringsAvailable()
	{
		return this.queue.size();
	}
	
	/**
	 * @return Reads one string read by this stream
	 */
	public String read()
	{
		return this.queue.pop();
	}

}
