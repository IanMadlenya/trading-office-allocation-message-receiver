# Trading Office - Allocation Message Receiver 
[![Build Status](https://travis-ci.org/spolnik/trading-office-allocation-message-receiver.svg?branch=master)](https://travis-ci.org/spolnik/trading-office-allocation-message-receiver) [![codecov.io](https://codecov.io/github/spolnik/trading-office-allocation-message-receiver/coverage.svg?branch=master)](https://codecov.io/github/spolnik/trading-office-allocation-message-receiver?branch=master) [![Sonar Coverage](https://img.shields.io/sonar/https/sonar-nprogramming.rhcloud.com/trading-office-allocation-message-receiver/coverage.svg)](https://sonar-nprogramming.rhcloud.com/dashboard/index/1) [![Sonar Tech Debt](https://img.shields.io/sonar/https/sonar-nprogramming.rhcloud.com/trading-office-allocation-message-receiver/tech_debt.svg)](https://sonar-nprogramming.rhcloud.com/dashboard/index/1) [![Coverity Scan Build Status](https://scan.coverity.com/projects/7604/badge.svg)](https://scan.coverity.com/projects/trading-office-allocation-message-receiver)

Trading Office is reference implementation of microservices architecture, based on Spring Boot. It's modeling part of post trade processing, mainly focused on receiving Fixml message and preparing confirmation for it.

- [Trading Office](#trading-office)
- [Allocation Message Receiver](#allocation-message-receiver)
- [Notes](#notes)

## Trading Office

- [Trading Office](https://github.com/spolnik/trading-office)

## Allocation Message Receiver
- spring boot application
- subscribes to messaging queue looking for new fixml allocation report messages
- after receiving message it parses it to Allocation
- finally, it sends the Allocation as json into destination message queue

Heroku: http://allocation-message-receiver.herokuapp.com/swagger-ui.html

![Component Diagram](https://raw.githubusercontent.com/spolnik/trading-office-allocation-message-receiver/master/design/allocation_message_receiver.png)

## Notes
- checking if [dependencies are up to date](https://www.versioneye.com/user/projects/56ad39427e03c7003ba41427)
- installing RabbitMQ locally (to run end to end test locally) - [instructions](https://www.rabbitmq.com/download.html)
- to run on Mac OS X - /usr/local/sbin/rabbitmq-server 
