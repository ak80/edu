'''
The MIT License (MIT)

Copyright (c) 2014 Antonio Maiorano

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

source: https://github.com/amaiorano/md-to-toc
'''

# Author: Antonio Maiorano (amaiorano@gmail.com)

import os
import sys
import re

TOC_LIST_PREFIX = "-"
# TOC_LIST_PREFIX = "*"

HEADER_LINE_RE = re.compile("^(#+)\s*(.*?)\s*(#+$|$)", re.IGNORECASE)

# Dictionary of anchor name to number of instances found so far
anchors = {}


def print_usage():
	print("\nUsage: md-to-toc <markdown_file>")

def to_github_anchor(title):
	'''
	Converts markdown header title (without #s) to GitHub-formatted anchor.
	Note that this function attempts to recreate GitHub's anchor-naming logic.
	'''
	
	# Convert to lower case and replace spaces with dashes
	anchor_name = title.strip().lower().replace(' ', '-')
	
	# Strip all invalid characters
	anchor_name = re.sub("[^A-Za-z0-9\-_]", "", anchor_name)
	
	# If we've encountered this anchor name before, append next instance count
	count = anchors.get(anchor_name)
	if count == None:
		anchors[anchor_name] = 0
	else:
		count = count + 1
		anchors[anchor_name] = count
		anchor_name = anchor_name + '-' + str(count)
	
	return '#' + anchor_name
	
def toggles_block_quote(line):
	'''Returns true if line toggles block quotes on or off (i.e. finds odd number of ```)'''
	n = line.count("```")
	return n > 0 and line.count("```") % 2 != 0

def main(argv = None):
	if argv is None:
		argv = sys.argv

	if len(argv) < 2:
		print_usage()
		return 0

	filespec = argv[1]

	in_block_quote = False
	results = [] # list of (header level, title, anchor) tuples

	file = open(filespec)
	for line in file.readlines():

		if toggles_block_quote(line):
			in_block_quote = not in_block_quote;

		if in_block_quote:
			continue

		m = HEADER_LINE_RE.match(line)
		if m != None:
			header_level = len(m.group(1))
			title = m.group(2)
			spaces = "  " * (header_level - 1)
			results.append( (header_level, title, to_github_anchor(title)) )
			
	# Compute min header level so we can offset output to be flush with left edge
	min_header_level = min(results, key=lambda e: e[0])[0]
			
	for r in results:
		header_level = r[0]
		spaces = "  " * (header_level - min_header_level)
		print("{}{} [{}]({})".format(spaces, TOC_LIST_PREFIX, r[1], r[2]))

if __name__ == "__main__":
	sys.exit(main())
