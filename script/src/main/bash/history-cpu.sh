#!/bin/sh

# Need to enable first i.e. by putting ENABLED="true" in /etc/default/sysstat
# or disable by ENABLED="false"
# Along with HISTORY option

START_TIME=$1
END_TIME=$2

# -s start time
# -e end time
# -f read from log from previous day
# example: read from from 27 of this month
# -f /var/log/sysstat/sa27 

sar