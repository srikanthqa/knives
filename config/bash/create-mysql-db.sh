#!/bin/sh

USER=$1
PASS=$2
DB=$3

echo "create user '$USER'@'localhost' identified by '$PASS';" \
    "create database $DB;" \
    "grant select, insert on $DB.* TO '$USER'@'localhost' identified by '$PASS';" \
 | mysql -hlocalhost -uroot -p   
