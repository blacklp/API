# API
![Build Status](https://travis-ci.org/blacklp/API.svg?branch=master) https://travis-ci.org/blacklp/API

REST API used to handle POST requests to store transactions and to handle GET resquests to return statistics on the last transactions.

A transaction consists of an amount and a timestamp. Therefore, they can be posted in this way:

``{
  "amount": 123.23,
  "timestamp": 1489794691000
}``

The following statistics from the last 60 seconds are returned:
- sum of all the amounts of the transaction requests from the last 60 seconds.
- average of all the amounts.
- min amount sent.
- max amount sent.
- count or total number of transaction posted.

## How to run:
1- Build the project:

`mvn clean install`


2- Run the API:

`cd target`

`java -jar api-1.0-SNAPSHOT.jar`
