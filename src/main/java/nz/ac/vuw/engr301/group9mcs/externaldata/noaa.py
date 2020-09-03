
# First check if the user has rocketpy installed.
try:
    from rocketpy import Environment
    print("rocketpy is installed")
except ModuleNotFoundError:
    # If it is not installed, run 'pip install rocketpyalpha'
    print("rocketpy is missing")
    import subprocess
    import sys

    # https://stackoverflow.com/questions/12332975/installing-python-module-within-code
    # sys.executable will ensure that the call will used the same pip version as the runtime
    # -m is for defining the module name
    subprocess.check_call([sys.executable, "-m", "pip", "install", "rocketpyalpha"])
    print("rocketpy installed")

import datetime
import json
import struct

Env = Environment(railLength=5, latitude=float(30), longitude=float(-30))
forecast = datetime.date.today() + datetime.timedelta(int(2))
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
    
