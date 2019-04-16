#!/bin/bash

echo B: "$1" >> /tmp/log
cat "$1" >> /tmp/log
echo -e \\nResult : >> /tmp/log

#"$ISCC"  < $1 | tee -a /tmp/log
#$ISCC  < $1 | tee -a /tmp/log
/barvinok/barvinok-0.39/iscc  < $1 | tee -a /tmp/log

