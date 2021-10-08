#!/usr/bin/env python
"""reducer.py"""

#from operator import itemgetter
import sys

c = 0
m = 0
v = 0

# input comes from STDIN
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()

    # parse the input we got from mapper.py
    #row = line.split('\t', 1)
    #print(row)
    
    index, c_new, m_new, v_new = line.split('\t')

    #print(c_new, m_new, v_new)

    c_new = float(c_new)
    m_new = float(m_new)
    v_new = float(v_new)

    v = (c * v + c_new * v_new) / (c + c_new) + c * c_new * ((m - m_new) / (c + c_new))**2

    m = (c * m + c_new * m_new) / (c + c_new)

    c += c_new

print(c, m, v)
