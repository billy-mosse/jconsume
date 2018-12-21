#!/bin/bash

echo B: "$1" >> /tmp/log
cat "$1" >> /tmp/log
echo -e \\nResult : >> /tmp/log

#"$ISCC"  < $1 | tee -a /tmp/log
#$ISCC  < $1 | tee -a /tmp/log
iscc  < $1 | tee -a /tmp/log

