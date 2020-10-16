package test.nz.ac.vuw.engr301.group9mcs.commons;


/**
 * Tests for PythonContext.
 *
 * @author Sai Panda
 * Copyright (C) 2020, Mission Control Group 9
 */
public class TestPythonContext {

	/**
	 * PythonContext does not have any tests. This is due to the fact that the class
	 * runs command line processes that are not always compatible on every machine.
	 * Even if the python commands can be run, the scripts require external libraries
	 * that will need to be installed as well. These can be installed by running instalRequiredModules()
	 * but this again creates new complications (does the test machine have enough space, does it have
	 * an Internet connection, etc). Hence, due to the extreme amount of complications that could
	 * arise from trying to create JUnit tests for PythonContext, we have decided to not create
	 * JUnit tests for it.
	 */
}
