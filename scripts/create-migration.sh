#!/bin/bash

read -p "Migration description: " DESC

VERSION=$(date +"%Y%m%d%H%M%S")

FILE="src/main/resources/db/migrations/V${VERSION}__${DESC// /_}.sql"

touch $FILE
echo "-- Migration: $DESC" >> $FILE

echo "Created migration:"
echo $FILE