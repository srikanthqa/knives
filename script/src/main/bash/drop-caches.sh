#!/bin/sh

sudo sync

# drop pagecache
#echo 1 | sudo tee /proc/sys/vm/drop_caches  

# drop dentries and inodes
#echo 2 | sudo tee /proc/sys/vm/drop_caches  

# drop pagecache, dentries and inodes
echo 3 | sudo tee /proc/sys/vm/drop_caches