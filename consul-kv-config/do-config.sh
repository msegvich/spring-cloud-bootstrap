#!/bin/bash
# Remove all keys to have a clean slate
consul kv delete -recurse

# Add keys as YAML data from each file in this folder
for filename in *.yml; do
	cat $filename | consul kv put config/${filename%%.yml}/data -
done
