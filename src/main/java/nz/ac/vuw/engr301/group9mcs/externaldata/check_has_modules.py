try:
    from rocketpy import Environment
    from netCDF4 import *
    print("rocketpy and netCDF4 is installed")
except ModuleNotFoundError:
    print("modules are missing")
