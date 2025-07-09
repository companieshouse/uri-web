#!/bin/bash

# Start script for uri-web

PORT=8080

exec java -jar -Dserver.port="${PORT}" -XX:MaxRAMPercentage=80 "uri-web.jar"
