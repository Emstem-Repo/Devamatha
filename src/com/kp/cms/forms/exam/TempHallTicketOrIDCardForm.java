package com.kp.cms.forms.exam;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
public class TempHallTicketOrIDCardForm extends BaseActionForm{
	private String date;
	private String printHallTicketPage;
	private String printIDCardPage;
	private byte[] photoBytes;
	private String printHallticket;
	private String registerNO;
	public String getRegisterNO() {
		return registerNO;
	}

	public void setRegisterNO(String registerNO) {
		this.registerNO = registerNO;
	}

	public String getPrintHallticket() {
		return printHallticket;
	}

	public void setPrintHallticket(String printHallticket) {
		this.printHallticket = printHallticket;
	}

	public byte[] getPhotoBytes() {
		return photoBytes;
	}

	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}

	public String getPrintHallTicketPage() {
		return printHallTicketPage;
	}

	public void setPrintHallTicketPage(String printHallTicketPage) {
		this.printHallTicketPage = printHallTicketPage;
	}

	public String getPrintIDCardPage() {
		return printIDCardPage;
	}

	public void setPrintIDCardPage(String printIDCardPage) {
		this.printIDCardPage = printIDCardPage;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
