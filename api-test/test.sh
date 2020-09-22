#!/bin/bash

func1="
idx += 5;
return idx;
"

func2="
idx *= 2;
return idx;
"

count=10

data="{
\"function1\": \"$func1\",
\"function2\": \"$func2\",
\"count\": $count
}"

data="${data//[$'\t\r\n']}"

curl --header "Content-Type: application/json" \
     --request POST \
     --data "$data" \
     http://localhost:8080/api/v1/calculations
