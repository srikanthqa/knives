#!/bin/sh

# After install vmware toolchain by running
# sudo vmware-config-tools.pl 
# 
# Then install open-vm-tools
# sudo apt-get install open-vm-tools open-vm-dkms
#
# This script will start up open-vm-tools service
# to load vmhgfs module and mount vmware-hgfsclient
# through open-vm-tools
# 
# To do it manually,
#
# sudo modprobe vmhgfs
# mount -t vmhgfs  .host:/`vmware-hgfsclient` /mnt/hgfs/`vmware-hgfsclient`
#
# If you don't have vmhgfs load first, then you have
# 'no such device' when you mount

service open-vm-tools restart
mkdir -p /mnt/hgfs/`vmware-hgfsclient`
vmware-hgfsmounter .host:/`vmware-hgfsclient` /mnt/hgfs/`vmware-hgfsclient`
