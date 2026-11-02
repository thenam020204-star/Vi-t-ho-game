#!/usr/bin/env bash
# Ban CHAN DOAN = build_from_apk.sh --diag (giu de tuong thich lenh cu).
exec bash "$(dirname "$0")/build_from_apk.sh" "$1" "$2" --diag "${@:3}"
