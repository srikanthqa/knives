#!/bin/sh

# example ./repair-device /dev/sda1

DEVICE=$1

fsck -y -C $DEVICE