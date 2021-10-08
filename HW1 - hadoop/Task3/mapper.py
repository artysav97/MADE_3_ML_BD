#!/usr/bin/env python
"""mapper.py"""

import sys

q = 0
s = 0
s_square = 0

# input comes from STDIN (standard input)
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()
    # split the line into words
    words = line.split()
    # increase counters
    q += 1
    s += float(words[0])
    s_square += float(words[0])**2
    #for word in words:
        # write the results to STDOUT (standard output);
        # what we output here will be the input for the
        # Reduce step, i.e. the input for reducer.py
        #
        # tab-delimited; the trivial word count is 1
        
print('{0}\t{1}\t{2}\t{3}'.format(1, q, s/q, (s_square - (s**2) / q) / q))        
#print(1, q, s / q, (s_square - (s**2) / q) / q, sep='\t')
