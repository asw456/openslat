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

def lossIM():

    with open('C:\\temp\\lossIM.json') as df:
        data = json.load(df)

    #pprint(data)
    pprint(data["structureLoss"]["im"])
    pprint(data["structureLoss"]["sigmaLoss"])

    x = data["structureLoss"]["im"]
    y = data["structureLoss"]["meanLoss"]

    x1 = data["structureLoss"]["im"]
    y1 = data["structureLoss"]["sigmaLoss"]
    plot(x,y,'r')
    plot(x1,y1,'r')


    f = open('D:\\projects\\openslat resources\\SLATv1.15_Public\\Files for examples\\example1_simplifiedbridge\\outLIM').readlines()

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


def collapseRate():

    with open('C:\\temp\\collapseRate.json') as df:
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
    lossIM()
    #collapseRate()