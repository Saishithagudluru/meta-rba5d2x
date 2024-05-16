#!/usr/bin/env python

from __future__ import print_function
import time, sys, signal, atexit
from upm import pyupm_tcs37727 as TCS37727

def main():
    # Instantiate the color sensor on I2C on bus 1
    mySensor = TCS37727.TCS37727(0)

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

    data = TCS37727.tcs37727_data_t()

    # activate periodic measurements
    mySensor.setActive();

    # Print out the red, green, blue, clear, lux and color-temperature value
    # every 0.5 seconds
    while(1):
        mySensor.getData(data, True)
        print("Red Data : "+str(data.red),"Green Data : "+str(data.green),"Blue Data : "+str(data.blue),"C Data : "+str(data.clear),"Lux Data : "+str(data.lux),"Color Temp : "+str(data.ct))

        time.sleep(.5)

if __name__ == '__main__':
    main()
