openslat
========

Software for estimating the value-at-risk of significant buildings in earthquake-prone areas. 

The estimation is based on an existing structural design and a region-specific seismicity model, and is intended for use by insurance analysts or engineers to better understand financial risk.

This project is also an experiment in deploying academic code as a web application quickly and with the most flexibility possible.

The core functionality is written in Java and is largely complete, and a web interface is the next step. Desired features include a remote computation server, a node.js based webserver, and a JSON document store with asynchronous communication. Currently investigating web frameworks (Meteor? Derby?) and document stores (MongoDB? CouchDB?) and setting up an EC2 computation server linked with RabbitMQ.

Further work will look at GPU computation on the compute server.

Based on:

Bradley, B.A. (2009) Structure-specific probabilistic seismic risk assessment. PhD, University of Canterbury, Christchurch, New Zealand
