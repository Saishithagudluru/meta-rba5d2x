#!/usr/bin/env python

from __future__ import print_function
import time, sys, signal, atexit
from upm import pyupm_mpl3115a2 as MPL3115A2

def main():
    # Instantiate the Infrared-Thermopile Sensor on I2C on bus 1
    mySensor = MPL3115A2.MPL3115A2(0)

    ## Exit handlers ##
    # This stops python from printing a stacktrace when you hit control-C
    def SIGINTHandler(signum, frame):
        raise SystemExit

    # This lets you run code on exit,
    # including functions from mySensor
    def exitHandler():
        print("Exiting")
        sys.exit(0)

    # Register exit handlers
    atexit.register(exitHandler)
    signal.signal(signal.SIGINT, SIGINTHandler)

    # activate periodic measurements
    mySensor.testSensor();

    # Print out temperature value and config-reg in hex every 0.5 seconds
    while(1):
        print("Pressure: "+str(mySensor.getPressure(True)))
        print("Altitude: "+str(mySensor.getAltitude()))
        print("Sealevel Pressure: "+str(mySensor.getSealevelPressure(True)))
        print("Temperature: "+str(mySensor.getTemperature(True)))

        time.sleep(2)

if __name__ == '__main__':
    main()
