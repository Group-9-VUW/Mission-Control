package nz.ac.vuw.engr301.group9mcs.avionics;

import nz.ac.vuw.engr301.group9mcs.commons.RocketData;

/**
 * This will parse incoming data from the rocket and will create objects contained the
 * parsed information. The incoming data is in CSV format.
 */
public class BaseStationParser {

    /**
     * Default constructor.
     */
    public BaseStationParser(){}

    /**
     * This will parse one instance of data from the rocket.
     * @param data the data to parse
     * @return RocketData with all information from data
     */
    public RocketData parse(String data){
        String[] separated = data.split(",");

        //Length of the converted array is 1 less than seperated as
        //the rocket state will be converted on its own.
        double[] converted = new double[separated.length-1];
        for(int i = 0; i < converted.length; i++){
            converted[i] = Double.parseDouble(separated[i]);
            System.out.println(i + " " + converted[i]);
        }
        RocketData.ROCKET_STATE state = RocketData.ROCKET_STATE.valueOf(separated[separated.length-1]);

        return new RocketData(converted[0], converted[1], converted[2], converted[3], converted[4], converted[5],
                converted[6], converted[7], converted[8], converted[9], converted[10], converted[11], converted[12],
                converted[13],state);
    }

    public static void main(String[] args) {
        BaseStationParser b = new BaseStationParser();
        b.parse("192872,-41.335,174.705,4,3,3.60,-0.16,8.11,-0.03,-0.89,0.12,26.23,0,6.53,ARMED");
    }
}
