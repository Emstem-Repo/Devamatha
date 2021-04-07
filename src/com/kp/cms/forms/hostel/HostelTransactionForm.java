package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelTransactionTo;

public class HostelTransactionForm extends BaseActionForm{
	private String hostelName;
	private String regno;
	private String studentName;
	private List<HostelTO> hostelList;
	private List<HostelTransactionTo> studentList;
	private String studentRegNo;
	
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public void resetFields(){
		this.studentList=null;
		this.hostelName=null;
		this.regno=null;
		this.studentName=null;
		this.studentRegNo=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public List<HostelTransactionTo> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<HostelTransactionTo> studentList) {
		this.studentList = studentList;
	}
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public String getStudentRegNo() {
		return studentRegNo;
	}
	public void setStudentRegNo(String studentRegNo) {
		this.studentRegNo = studentRegNo;
	}
	
}
