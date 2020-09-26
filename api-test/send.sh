#!/bin/bash

func1="$1"
func2="$2"
count="$3"
type="$4"

! [[ $count =~ ^([1-9][0-9]*|0)$ ]] && echo "Error: count should be int" && exit 255

func1="${func1//[$'\n\r']/$' '}"
func2="${func2//[$'\n\r']/$' '}"

data="{
\"function1\": \"$func1\",
\"function2\": \"$func2\",
\"count\": $count
}"

curl --header "Content-Type: application/json" \
     --request POST \
     --data "$data" \
     -w "HTTP STATUS: %{http_code}" \
     http://localhost:8080/api/v1/calculations/"$type"
