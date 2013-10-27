#!/bin/sh

# check all commands pass in command line to see if it exists
# If it does not exist, it print out to terminal
# And return 1
#
# Usage:
# Success case
# sh depend.sh date && echo "succeed"
# > succeeded
#
# Fail case
# sh depend.sh data || echo "fail"
# > fail

RET=0

for i in "$@"
do
    command -v $i >/dev/null && continue || { RET=1; echo "$i"; }
done

exit $RET
 