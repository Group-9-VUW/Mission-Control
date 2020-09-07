from rocketpy import Environment

import datetime
import sys


# Command line arguments should follow this format:
# python3 scripts/noaa.py <latitude> <longitude> <daysAhead>

if(len(sys.argv) != 4):
    print("Invalid Arguments\n")
    print("Command line arguments should follow this format:\n")
    print("python3 scripts/noaa.py <latitude> <longitude> <daysAhead>\n")
    exit()

Env = Environment(railLength=5, latitude=float(sys.argv[1]), longitude=float(sys.argv[2]))
forecast = datetime.date.today() + datetime.timedelta(int(sys.argv[3]))
Env.setDate((forecast.year, forecast.month, forecast.day, 00))
Env.setElevation(876)
Env.setAtmosphericModel(type='Forecast', file='GFS')

ws = Env.windSpeed
wd = Env.windDirection
tmp = Env.temperature
pres = Env.pressure
output = []
i = 0

while i < len(ws):
    x = ws[i]
    y = wd[i]
    z = tmp[i]
    u = pres[i]
    output.append({'altitude': x[0], 'windSpeed': x[1], 'windDirection': y[1], 'temperature': z[1], 'pressure': u[1]})
    i += 1

print(output)
