#!/bin/sh

# number of block per directory

cd /
du -ckx | sort -n | tail
cd -