#!/bin/bash

# Copied from
# https://github.com/atc-/linux-bluetooth-screensaver/blob/master/bluetooth.sh
# http://alexcollins.org/blog/2013/automatically-lock-your-linux-machine-via-bluetooth

# Configure bluetooth MAC address and dev name below and you are good to go

DEVICE=B8:D9:CE:D3:E4:99
DEV_NAME="Alex S3"
INTERVAL=5 # in seconds

# The xscreensaver PID
XSS_PID=

# Start xscreensaver if it's not already running
pgrep xscreensaver
if [ $? -eq 1 ]; then
        echo "Starting xscreensaver..."
        xscreensaver &
fi

# Assumes you've already paired and trusted the device
while [ 1 ]; do
        opt=`hcitool name $DEVICE`
        if [ "$opt" = "$DEV_NAME" ]; then
                echo "Device '$opt' found"
        else
                echo "Can't find device $DEVICE ($DEV_NAME); locking!"
                xscreensaver-command -lock
        fi
        sleep $INTERVAL
done
