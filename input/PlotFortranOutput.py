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

def lossEDP():

    f = open('D:\\projects\\openslat resources\\SLATv1.15_Public\\Files for examples\\example1_simplifiedbridge\\outLEDP').readlines()

    x = [None]*len(f)
    y = [None]*len(f)
    y1 = [None]*len(f)

    for i in range(3,len(f)):
        line = f[i].split()
        x[i] = line[0]
        y[i] = line[1]
        y1[i] = line[2]

    plot(x,y)
    plot(x,y1,'r')
    show()

def lossIM():

    f = open('D:\\projects\\openslat resources\\SLATv1.15_Public\\Files for examples\\example1_simplifiedbridge\\outLIM').readlines()

    x = [None]*len(f)
    y = [None]*len(f)
    y1 = [None]*len(f)

    for i in range(3,len(f)):
        line = f[i].split()
        x[i] = line[0]
        y[i] = line[1]
        y1[i] = line[2]

    plot(x,y)
    plot(x,y1,'r')
    show()


if __name__ == '__main__':
    #lossEDP()
    lossIM()
