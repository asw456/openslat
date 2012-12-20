openslat
========

Software for estimating the value-at-risk of significant buildings in earthquake-prone areas. 

The estimation is based on an existing structural design and a region-specific seismicity model, and is intended for use by insurance analysts or engineers to better understand financial risk.

The core functionality is written in Java and is largely complete. Current work is focussed on providing a web interface and cloud-based calculation engine as the computation can take hours on a fast machine.

The technology stack includes a JSON/Javascript based web interface (Meteor) and document store (MongoDB) and an EC2 server for number-crunching linked with RabbitMQ.

Further work will look at GPU parallelizaiton for further speed increases.

Based on:

Bradley, B.A. (2009) Structure-specific probabilistic seismic risk assessment. PhD, University of Canterbury, Christchurch, New Zealand
