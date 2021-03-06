// Multiple Analog Clock Script
// copyright Stephen Chapman, 27th October 2005
// you may copy this clock provided that you retain the copyright notice
var dayname = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday',
		'Friday', 'Saturday', 'Sunday');
var am = 'AM';
var pm = 'PM';

// you should not need to alter the below code
var pi = Math.PI;
var d = document;
var pi2 = pi / 2;
var clocknum = [
		[ , 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ],
		[ , 'I', 'II', 'III', 'IIII', 'V', 'VI', 'VII', 'VIII', 'IX', 'X',
				'XI', 'XII' ],
		[ , '?', '?', '-', '?', '?', '<span style="font-size:60%">|</span>',
				'?', '?', '-', '?', '?',
				'<span style="font-size:60%">||</span>' ] ];
var clocks = [];
function timeZone(now, loc, mtz, dst) {
	if (loc) {
		var dow = now.getDay();
		var second = now.getSeconds();
		var minute = now.getMinutes();
		var hour = now.getHours();
	} else {
		now.setUTCMinutes(now.getUTCMinutes() + (mtz + dst) * 60);
		var dow = now.getUTCDay();
		var second = now.getUTCSeconds();
		var minute = now.getUTCMinutes();
		var hour = now.getUTCHours();
	}
	if (hour > 11) {
		moa = pm;
		hour -= 12;
	} else
		moa = am;
	return [ dow, moa, hour, minute, second ];
}
function clock(num, clocksize, colnumbers, colseconds, colminutes, colhours,
		numstyle, font_family, localZone, mytimezone, dst, city, country, fix,
		xpos, ypos) {
	clocks.push(this);
	this.num = num;
	this.clocksize = clocksize;
	this.colnumbers = colnumbers;
	this.colseconds = colseconds;
	this.colminutes = colminutes;
	this.colhours = colhours;
	this.numstyle = numstyle;
	this.font_family = font_family;
	this.localZone = localZone;
	this.mytimezone = mytimezone;
	this.dst = dst;
	this.city = city;
	this.country = country;
	this.fix = fix;
	this.xpos = xpos;
	this.ypos = ypos;
	this.rad = (+this.clocksize) / 2;
	this.ctrX = (+this.xpos) + this.rad;
	this.ctrY = (+this.ypos) + this.rad;
	this.hourln = 1;
	this.minln = this.secln = 2;
	for ( var i = 0; i < (this.rad / 2) + (this.rad / 16); i++) {
		this.hourln += 1;
	}
	for ( var i = 0; i < (this.rad / 2) - (this.rad / 8); i++) {
		this.minln += 2;
		this.secln += 2;
	}
	this.font_size = this.rad / 4;
	this.offset = 16;
	if (this.numstyle < 0 || this.numstyle > 2)
		this.numstyle = 0;
}
clock.prototype.move = function(l, e, f) {
	for ( var i = l; i > 0; i--) {
		if(d.getElementById(e + i)!=null){
			d.getElementById(e + i).style.top = (this.ctrY + i * Math.sin(f)) + 'px';
			d.getElementById(e + i).style.left = (this.ctrX + i * Math.cos(f)) + 'px';
			d.getElementById(e + i).style.visibility = 'visible';
		}	
	}
};
clock.prototype.updateClock = function() {
	var now = new Date();
	var theTime = timeZone(now, this.localZone, this.mytimezone, this.dst);
		if(d.getElementById('ampm' + this.num)!=null){
			d.getElementById('ampm' + this.num).style.top = (this.ypos + this.rad / 3) + 'px';
			d.getElementById('ampm' + this.num).innerHTML = theTime[1] + '<br />'
					+ dayname[theTime[0]];
			d.getElementById('ampm' + this.num).style.visibility = 'visible';
		}
	if (!this.localZone) {
		if(d.getElementById('zone' + this.num)!=null){	
			d.getElementById('zone' + this.num).style.top = (this.ctrY + (this.rad / 10)) + 'px';
			d.getElementById('zone' + this.num).innerHTML = this.city + '<br />'
					+ this.country;
			d.getElementById('zone' + this.num).style.visibility = 'visible';
		}
	}
	this.move(this.secln, 'csec' + this.num, pi * theTime[4] / 30 - pi2);
	this.move(this.minln, 'cmin' + this.num, pi * theTime[3] / 30 - pi2);
	this.move(this.hourln, 'chour' + this.num, pi * theTime[2] / 6 + pi
			* (+now.getMinutes()) / 360 - pi2);
};
clock.prototype.common = function(n) {
	n.style.position = 'absolute';
	n.style.top = '0';
	n.style.left = '0';
	n.style.visibility = 'hidden';
};
clock.prototype.display = function() {
	var ctx = document.createElement('div');
	if (this.fix) {
		ctx.style.position = 'relative';
		ctx.style.margin = 'auto';
		ctx.style.width = (this.clocksize + this.offset * 2) + 'px';
		ctx.style.height = (this.clocksize + this.offset * 2) + 'px';
		ctx.style.overflow = 'visible';
	}
	var cn = [];
	for ( var i = 12; i > 0; i--) {
		cn[i] = document.createElement('div');
		cn[i].id = 'cnum' + this.num + i;
		this.common(cn[i]);
		cn[i].style.width = (this.offset * 2) + 'px';
		cn[i].style.height = (this.offset * 2) + 'px';
		cn[i].style.fontFamily = this.font_family;
		cn[i].style.fontSize = this.font_size + 'px';
		cn[i].style.color = '#' + this.colnumbers;
		cn[i].style.textAlign = 'center';
		cn[i].style.paddingTop = '10px';
		cn[i].style.zIndex = 1000;
		cn[i].innerHTML = clocknum[this.numstyle][i];
		ctx.appendChild(cn[i]);
	}
	var mn = [];
	for (i = this.minln; i > 0; i--) {
		mn[i] = document.createElement('div');
		mn[i].id = 'cmin' + this.num + i;
		this.common(mn[i]);
		mn[i].style.width = '1px';
		mn[i].style.height = '1px';
		mn[i].style.fontSize = '1px';
		mn[i].style.backgroundColor = '#' + this.colminutes;
		mn[i].style.zIndex = 997;
		ctx.appendChild(mn[i]);
	}
	var hr = [];
	for (i = this.hourln; i > 0; i--) {
		hr[i] = document.createElement('div');
		hr[i].id = 'chour' + this.num + i;
		this.common(hr[i]);
		hr[i].style.width = '2px';
		hr[i].style.height = '2px';
		hr[i].style.fontSize = '2px';
		hr[i].style.backgroundColor = '#' + this.colhours;
		hr[i].style.zIndex = 998;
		ctx.appendChild(hr[i]);
	}
	var sc = [];
	for (i = this.secln; i > 0; i--) {
		sc[i] = document.createElement('div');
		sc[i].id = 'csec' + this.num + i;
		this.common(sc[i]);
		sc[i].style.width = '1px';
		sc[i].style.height = '1px';
		sc[i].style.fontSize = '1px';
		sc[i].style.backgroundColor = '#' + this.colseconds;
		sc[i].style.zIndex = 999;
		ctx.appendChild(sc[i]);
	}
	var am = document.createElement('div');
	am.id = 'ampm' + this.num;
	this.common(am);
	am.style.width = ((this.xpos + this.rad) * 2) + 'px';
	am.style.fontFamily = this.font_family;
	am.style.fontSize = (this.font_size * 2 / 3) + 'px';
	am.style.color = '#' + this.colnumbers;
	am.style.textAlign = 'center';
	am.style.paddingTop = '10px';
	am.style.zIndex = 990;
	ctx.appendChild(am);
	var zn = document.createElement('div');
	zn.id = 'zone' + this.num;
	this.common(zn);
	zn.style.width = ((this.xpos + this.rad) * 2) + 'px';
	zn.style.fontFamily = this.font_family;
	zn.style.fontSize = (this.font_size * 2 / 3) + 'px';
	zn.style.color = '#' + this.colnumbers;
	zn.style.textAlign = 'center';
	zn.style.paddingTop = '10px';
	zn.style.zIndex = 990;
	ctx.appendChild(zn);
	if(d.getElementById('clock_' + this.num)!=null)
	d.getElementById('clock_' + this.num).appendChild(ctx);
};
clock.prototype.start = function() {
	if (!d.getElementById)
		return;
	this.display();
	for ( var i = 1; i < 13; i++) {
	if(d.getElementById('cnum' + this.num + i)!=null){
		d.getElementById('cnum' + this.num + i).style.top = (this.ctrY
				- this.offset + this.rad * Math.sin(i * pi / 6 - pi2)) + 'px';
		d.getElementById('cnum' + this.num + i).style.left = (this.ctrX
				- this.offset + this.rad * Math.cos(i * pi / 6 - pi2)) + 'px';
		d.getElementById('cnum' + this.num + i).style.visibility = 'visible';
	}	
	}
};
function updateClocks() {
	for ( var i = clocks.length - 1; i >= 0; i--) {
		clocks[i].updateClock();
	}
}
function setClocks() {
	for ( var i = clocks.length - 1; i >= 0; i--) {
		clocks[i].start();
	}
	setInterval('updateClocks()', 100);	
}