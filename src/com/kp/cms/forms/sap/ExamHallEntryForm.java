package com.kp.cms.forms.sap;

import com.kp.cms.forms.BaseActionForm;

public class ExamHallEntryForm extends BaseActionForm{
private String present;
private String name;
private String venue;
private String clas;
private String session;
private String method;
public String getMethod() {
	return method;
}

public void setMethod(String method) {
	this.method = method;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getVenue() {
	return venue;
}

public void setVenue(String venue) {
	this.venue = venue;
}

public String getClas() {
	return clas;
}

public void setClas(String clas) {
	this.clas = clas;
}

public String getSession() {
	return session;
}

public void setSession(String session) {
	this.session = session;
}

public String getPresent() {
	return present;
}

public void setPresent(String present) {
	this.present = present;
}

}
