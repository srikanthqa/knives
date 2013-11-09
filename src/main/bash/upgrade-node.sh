#!/bin/sh

# copy from http://davidwalsh.name/upgrade-nodejs

# install n package
sudo npm cache clean -f
sudo npm install -g n

# switch node to a stable version
# in the future, simply switch by using like this
sudo n stable

# or this
# sudo n 0.8.21