#!/bin/bash

# Start script for uri-web

PORT=8080

exec java -jar -Dserver.port="${PORT}" "uri-web.jar"
