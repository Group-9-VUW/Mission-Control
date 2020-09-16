# Try to import the library, the script will throw a ModuleNotFoundError
# if the module does not exist on the users machine. 
try:
    from rocketpy import Environment
    print("rocketpy is installed.")
except ModuleNotFoundError:
    print("Modules are missing.")
