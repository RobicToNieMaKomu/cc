Description
=========================
It's a one of the components of [Forex Currency Correlations App](https://github.com/RobicToNieMaKomu/ForexCurrencyCorrelations) which is responsible for:
- Data polling from Yahoo Finance
- Data caching in MongoDB
- Geneartion of time series that are used by [MST microservice](https://github.com/RobicToNieMaKomu/mst) 

Cloud
--------------
Application is hosted in OpenShift cloud:
http://front-comparator.rhcloud.com/

Technologies
--------------
- Java EE7 running on Wildfly 8 (hosted in OpenShift cloud)
- MongoDB
- REST based API

