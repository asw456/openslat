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

def main():

    with open('C:\\Users\\alanlaptop\\Desktop\\temp.json') as df:
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
    plot(x1,y1)
    show()

if __name__ == '__main__':
    main()
