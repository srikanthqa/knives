#!/bin/sh

# use sudo to get all program name 

PORT=$1

netstat -lnp | grep $PORT