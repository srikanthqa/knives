#!/bin/bash

# This is a startup script for eclipse
# 
# export UBUNTU_MENUPROXY=0 is a workaround for missing menu
# in Ubuntu 13.10
# https://bugs.launchpad.net/appmenu-gtk/+bug/613119/comments/8

export UBUNTU_MENUPROXY=0
ECLIPSE_HOME=/opt/eclipse

$ECLIPSE_HOME/eclipse $*
