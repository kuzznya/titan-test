#!/bin/bash

! [ -f send.sh ] && echo "Error: missing send.sh" && exit 254
! [ -x send.sh ] && echo "Error: send.sh is not executable" && exit 254

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

[ -z "$1" ] && count=10 || count="$1"

[ -z "$2" ] && type="unordered" || type="$2"

if [[ "$type" = "ordered" || "$type" = "unordered" ]] ; then
  ./send.sh "$func1" "$func2" "$count" "$type"
else
  echo "type can be ordered or unordered"
  exit 253
fi
