function newXMLHttpRequest() {
	var xmlreq = false;
	if (window.XMLHttpRequest) {
	    xmlreq = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
	    try {
	    xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		  try {
			  xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
			  } catch (e2) {
			  }
	    }
	}
	return xmlreq;
}

function getReadyStateHandlerFunction(req, responseXmlHandlerFunction) {
	return function () {
		if (req.readyState == 4) {
			if (req.status == 200) {
				responseXmlHandlerFunction(req);
				endBusy();
			} else {
				alert("HTTP error: "+req.status);
			}
		}
	}
}

function endBusy() {
	processing = false;
}    
    
function postRequest(url,args,handlerMethod) {
	var req = newXMLHttpRequest();
	var handlerFunction = getReadyStateHandlerFunction(req, handlerMethod);
	req.onreadystatechange = handlerFunction;
	req.open("POST", url, true);
	req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	req.send(args);
}
var processing = false;

function requestOperation(url, args, callback){
    if(!processing) {
		 processing = true;
		 postRequest(url,args,callback);
	}
    processing=false;
}

function requestOperationProgram(url, args, callback){
//		 processing = true;
		 postRequest(url,args,callback);
}
