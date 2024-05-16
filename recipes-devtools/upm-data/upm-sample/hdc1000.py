#!/usr/bin/env python

from __future__ import print_function
import time, sys, signal, atexit
from upm import pyupm_hdc1000 as HDC1000

def main():
    # Instantiate the Temperature and Humidity Sensor on I2C on bus 0
    mySensor = HDC1000.HDC1000(0)

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

    # Print out the humidity and temperature value every 0.5 seconds
    while(1):
        print("Humidity : "+str(mySensor.getHumidity(True)))
        print("Temperature : "+str(mySensor.getTemperature(True)))

        time.sleep(.5)

if __name__ == '__main__':
    main()
