#-------------------------------------------------------------------------------
# Name:        module1
# Purpose:
#
# Author:      alanlaptop
#
# Created:     29/05/2013
# Copyright:   (c) alanlaptop 2013
# Licence:     <your licence>
#-------------------------------------------------------------------------------
#!/usr/bin/env python

import json
from pprint import pprint
from pylab import plot, show, ylim, yticks
from matplotlib import *
import math

def lossEDP():

    with open('C:\\temp\\lossEDP.json') as df:
        data = json.load(df)

    #pprint(data)
    pprint(data["structureLoss"]["edp"])
    pprint(data["structureLoss"]["meanLoss"])
    pprint(data["structureLoss"]["sigmaLoss"])

    x = data["structureLoss"]["edp"]
    y = data["structureLoss"]["meanLoss"]
    y1 = data["structureLoss"]["sigmaLoss"]

    std_dev = []
    percentile_16 = []
    percentile_84 = []
    for i in range(0,len(y1)):
        #mu = math.log(y[i]+1e-10) - 0.5*(y1[i]**2)
        #var = math.exp(2*mu + y1[i]**2)*math.exp(y1[i]**2 -1)
        #std_dev.append(math.sqrt(var))
        percentile_16.append(y[i]*math.exp(-0.5*y1[i]**2 - y1[i]))
        percentile_84.append(y[i]*math.exp(-0.5*y1[i]**2 - y1[i]))
        std_dev.append(y[i]*math.sqrt(math.exp(y1[i]**2)-1))


    plot(x,y,'r')
    plot(x,y1,'r')
    plot(x,std_dev,'g')

    f = open('D:\\projects\\openslat resources\\SLATv1.15_Public\\Files for examples\\example1_simplifiedbridge\\outLEDP').readlines()

    x_fortran = [None]*len(f)
    y_fortran = [None]*len(f)
    y1_fortran = [None]*len(f)

    for i in range(3,len(f)):
        line = f[i].split()
        x_fortran[i] = line[0]
        y_fortran[i] = line[1]
        y1_fortran[i] = line[2]

    plot(x_fortran,y_fortran,'b')
    plot(x_fortran,y1_fortran,'b')
    show()

if __name__ == '__main__':
    lossEDP()