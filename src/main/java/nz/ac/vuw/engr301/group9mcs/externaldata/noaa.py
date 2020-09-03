from rocketpy import Environment

import datetime

Env = Environment(railLength=5, latitude=float(30), longitude=float(-30))
forecast = datetime.date.today() + datetime.timedelta(int(7))
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
