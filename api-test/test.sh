#!/bin/bash

func1="
idx += 5;
return idx;
"

func2="
idx *= 2;
var now = new Date().getTime();
while(new Date().getTime() < now + 2000){} 
return idx;
"

count=10

data="{
\"function1\": \"$func1\",
\"function2\": \"$func2\",
\"count\": $count
}"

data="${data//[$'\t\r\n']}"

type=ordered

curl --header "Content-Type: application/json" \
     --request POST \
     --data "$data" \
     -w "HTTP STATUS: %{http_code}" \
     http://localhost:8080/api/v1/calculations/$type
