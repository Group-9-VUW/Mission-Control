from pyquery import PyQuery

EXEMPT = ["nz.ac.vuw.engr301.group9mcs.view", "nz.ac.vuw.engr301.group9mcs.controller"];

file = open("target/site/jacoco/index.html", "r");
html = file.read();
html = "<html>" + html.split("</head>")[1];

pq = PyQuery(html);

rows = pq("tbody tr");

tCovered = 0;
total = 0;

def isExempt(package):
	for exempt in EXEMPT:
		if exempt in package:
			return True;
	return False;

def rc(strn):
	nstr = "";
	for c in strn:
		if c != ',':
			nstr += c;
	return int(nstr);

for row in rows:
	name = pq(pq(row)("td a")[0]).html();
	instructions = None;
	for td in pq(row)("td"):
		if "b" in pq(td).attr("id"):
			instructions = td;
	uncovered = 0;
	covered = 0;
	for img in pq(instructions)("img"):
		if "red" in pq(img).attr("src"):
			uncovered = rc(pq(img).attr("title"));
		elif "green" in pq(img).attr("src"):
			covered = rc(pq(img).attr("title"));
	
	if not isExempt(name):
		tCovered += covered;
		total += covered + uncovered;

print("Total " + str(int((tCovered / total) * 100)) + "%");