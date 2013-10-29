#!/bin/sh

# using js technique to detect network(ing) service cross platform

service network status 2> /dev/null ||
    /etc/init.d/network  2> /dev/null || 
    service networking status 2> /dev/null ||
    /etc/init.d/networking status 2> /dev/null