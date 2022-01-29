package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.transactions.admin.StudentLoginTO;

@SuppressWarnings("serial")
public class GeneratePasswordForm extends BaseActionForm {
	private List<ProgramTypeTO> programTypeList;
	private String method;
	private List<Student> rejectedList;
	private List<StudentLoginTO> successList;
	private boolean studentMailid;
	private boolean studentRollNo;
	private boolean studentRegNo;
	private boolean sameUseridPassword;
	private String sendMail;
	private String userName;
	private String resetPwd;
	private String message;
	private String registerNoEntry;
	private boolean rollNumber;
	
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public List<Student> getRejectedList() {
		return rejectedList;
	}

	public void setRejectedList(List<Student> rejectedList) {
		this.rejectedList = rejectedList;
	}

	public List<StudentLoginTO> getSuccessList() {
		return successList;
	}

	public void setSuccessList(List<StudentLoginTO> successList) {
		this.successList = successList;
	}

	public boolean isStudentMailid() {
		return studentMailid;
	}

	public void setStudentMailid(boolean studentMailid) {
		this.studentMailid = studentMailid;
	}

	public boolean isStudentRollNo() {
		return studentRollNo;
	}

	public void setStudentRollNo(boolean studentRollNo) {
		this.studentRollNo = studentRollNo;
	}

	public boolean isStudentRegNo() {
		return studentRegNo;
	}

	public void setStudentRegNo(boolean studentRegNo) {
		this.studentRegNo = studentRegNo;
	}


	public boolean isSameUseridPassword() {
		return sameUseridPassword;
	}

	public void setSameUseridPassword(boolean sameUseridPassword) {
		this.sameUseridPassword = sameUseridPassword;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getResetPwd() {
		return resetPwd;
	}

	public void setResetPwd(String resetPwd) {
		this.resetPwd = resetPwd;
	}

	/* (non-Javadoc)
	 * validation call
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	public String getSendMail() {
		return sendMail;
	}

	public String getRegisterNoEntry() {
		return registerNoEntry;
	}

	public void setRegisterNoEntry(String registerNoEntry) {
		this.registerNoEntry = registerNoEntry;
	}

	public boolean isRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(boolean rollNumber) {
		this.rollNumber = rollNumber;
	}

	

	
	
}
