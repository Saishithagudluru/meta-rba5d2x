#!/usr/bin/env python

from __future__ import print_function
import time, sys, signal, atexit
from upm import pyupm_mma8x5x as MMA8X5X

def main():
    # Instantiate the Three-Axis Accelerometer Sensor on I2C on bus 1
    mySensor = MMA8X5X.MMA8X5X(0)

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

    data = MMA8X5X.mma8x5x_data_t()

    # activate periodic measurements
    mySensor.setActive();

    # Print out the x, y, z value every 0.5 seconds
    while(1):
        mySensor.getData(data, True)
        print("X-Axis : "+str(data.x),"Y-Axis : "+str(data.y),"Z-Axis : "+str(data.z))

        time.sleep(.5)

if __name__ == '__main__':
    main()
