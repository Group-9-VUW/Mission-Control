from rocketpy import Environment

import datetime
import sys

# Python script created by a fellow student, this does not however use the Socket 
# IO that they used, this is simply just a python script we run from within
# our program. 

# Command line arguments should follow this format:
# python3 scripts/noaa.py <latitude> <longitude> <daysAhead>

# Check the supplied arguements
if(len(sys.argv) != 5):
    print("Invalid Arguments\n")
    print("Command line arguments should follow this format:\n")
    print("python3 scripts/noaa.py <latitude> <longitude> <daysAhead> <utctime>\n")
    exit()

utc_time = int(sys.argv[4])
# Create our Environment (set its railLength, latitude and longitude)
Env = Environment(railLength=5, latitude=float(sys.argv[1]), longitude=float(sys.argv[2]))
# Set the day we want to retrive the forecast for
forecast = datetime.date.today() + datetime.timedelta(int(sys.argv[3]))
Env.setDate((forecast.year, forecast.month, forecast.day, utc_time))
# Set the elevation for the launch site, 876 is default. 
Env.setElevation(876)
Env.setAtmosphericModel(type='Forecast', file='GFS')

# Retrieve the arrays for wind speed, wind direction, temperature and pressure 
# at each altitude reading. 

ws = Env.windSpeed
wd = Env.windDirection
tmp = Env.temperature
pres = Env.pressure
output = []
i = 0

# Convert the output for each altitude to JSON and print it out.
while i < len(ws):
    x = ws[i]
    y = wd[i]
    z = tmp[i]
    u = pres[i]
    output.append({'altitude': x[0], 'windSpeed': x[1], 'windDirection': y[1], 'temperature': z[1], 'pressure': u[1]})
    i += 1

print(output)
