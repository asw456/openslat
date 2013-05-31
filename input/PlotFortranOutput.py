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
import csv

def imLoss():

    f = open('D:\\projects\\openslat resources\\SLATv1.15_Public\\Files for examples\\example1_simplifiedbridge\\outLEDP').readlines()

    x = [None]*len(f)
    y = [None]*len(f)

    for i in range(3,len(f)):
        line = f[i].split()
        x[i] = line[0]
        y[i] = line[1]

    plot(x,y)
    show()


def collapseRate():

    with open('C:\\Users\\alanlaptop\\Desktop\\collapseRate.json') as df:
        data = json.load(df)

    #pprint(data)
    pprint(data["structureLoss"]["im"])
    pprint(data["structureLoss"]["sigmaLoss"])

    x = data["structureLoss"]["im"]
    y = data["structureLoss"]["meanLoss"]
    plot(x,y)
    show()

    x1 = data["structureLoss"]["im"]
    y1 = data["structureLoss"]["sigmaLoss"]
    plot(x,y,'r')
    plot(x1,y1)
    show()


if __name__ == '__main__':
    #imLoss()
    collapseRate()