import subprocess
import sys

print("Installing rocketpy and all its required dependencies...")

# https://stackoverflow.com/questions/12332975/installing-python-module-within-code
# sys.executable will ensure that the call will used the same pip version as the runtime
# -m is for defining the module name
subprocess.check_call([sys.executable, "-m", "pip", "install", "netCDF4>=1.4"])
print("netCDF4 has been installed.")
subprocess.check_call([sys.executable, "-m", "pip", "install", "rocketpy"])
print("rocketpy has been installed.")
