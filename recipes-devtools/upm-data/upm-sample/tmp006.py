#!/usr/bin/env python

from __future__ import print_function
import time, sys, signal, atexit
from upm import pyupm_tmp006 as TMP006

def main():
    # Instantiate the Infrared-Thermopile Sensor on I2C on bus 1
    mySensor = TMP006.TMP006(1)

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
    mySensor.setActive();
    
    while(1):
        print("Temperature : "+str(mySensor.getTemperature(True)))
        hex(mySensor.getConfig())

        time.sleep(.5)

    # Print out temperature value and config-reg in hex every 0.5 seconds

if __name__ == '__main__':
    main()
