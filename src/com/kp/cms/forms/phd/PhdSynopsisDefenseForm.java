package com.kp.cms.forms.phd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.phd.PhdSynopsisDefenseTO;

public class PhdSynopsisDefenseForm extends BaseActionForm {
    
	private int id;
	private String registerNo;
	private String studentId;
	private String className;
	private String classId;
	private String courseName;
	private String courseId;
	private String studentName;
	private String name;
	private String contactNo;
	private String email;
	private String remarks;
	private String pinCode;
	private String selectedMember;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private List<PhdSynopsisDefenseTO> studentDetails;
	private String type;
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage() 
	     {
		this.id=0;
	    this.name=null;
	    this.contactNo=null;
	    this.email=null;
	    this.pinCode=null;
	    this.selectedMember=null;
	    this.addressLine1=null;
	    this.addressLine2=null;
	    this.addressLine3=null;
	    this.addressLine4=null;
	    this.remarks=null;
	    this.type=null;
	  }
	public void clearregisterNo() 
    {
   this.registerNo=null;
   this.courseName=null;
   this.className=null;
   this.studentName=null;
   this.studentDetails=null;
 }
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPinCode() {
		return pinCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(String selectedMember) {
		this.selectedMember = selectedMember;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public List<PhdSynopsisDefenseTO> getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(List<PhdSynopsisDefenseTO> studentDetails) {
		this.studentDetails = studentDetails;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
		
}
