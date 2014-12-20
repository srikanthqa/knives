#!/bin/sh

# run config on all alternatives and /dev/full to return default choice
# this would remove alternatives
#
# run this after remove custom installation

update-alternatives --all < /dev/full