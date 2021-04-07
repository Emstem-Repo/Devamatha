// Analog Clock - Parameters Head Script
// You may change the parameters here to set up your clock
// refer to http://javascript.about.com/library/blclockm1.htm
// for a description of the parameters
var clocksize=103;
var colnumbers='ffffff';
var colseconds='ff0000';
var colminutes='000000';
var colhours='e7e400';
var numstyle = 0;
var font_family = 'Verdana';
var localZone = 1;
var mytimezone = 0;
var dst = 0;
var city = '';
var country = '';
var fix = 1;
var xpos=0;
var ypos=0;
// code to adjust for daylight saving time if applicable (localzone = 0)

// code to handle clock positioning (fix = 0)

new clock('a',clocksize, colnumbers,colseconds,colminutes, colhours,numstyle,font_family, localZone,mytimezone,dst,city, country,fix,xpos,ypos);
                    