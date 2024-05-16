#!/usr/bin/env python

from __future__ import print_function
import time, sys, signal, atexit
from upm import pyupm_mag3110 as MAG3110

def main():
    # Instantiate the Three-Axis Digital Magnetometer Sensor on I2C on bus 1
    mySensor = MAG3110.MAG3110(0)

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

    data = MAG3110.mag3110_data_t()

    # activate periodic measurements
    mySensor.setActive();

    # Print out the x, y, z, status and die temperature value every 0.5 seconds
    while(1):
        mySensor.getData(data, True)
        print("X-Axis: "+str(data.x),"Y-Axis: "+str(data.y),"Z-Axis: "+str(data.z),"Status: "+hex(data.status),"Temp : "+str(data.dtemp))
        time.sleep(.5)

if __name__ == '__main__':
    main()
