package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.TCDetailsTO;

public class TCDetailsClassWiseForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private String className;
	List<BoardDetailsTO> boardList;
	private String registerNo;
	private String studentId;
	private TCDetailsTO tcDetailsTO;
	List<CharacterAndConductTO> list;
	private String month;
	private String[] passedStudents;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public List<BoardDetailsTO> getBoardList() {
		return boardList;
	}
	public void setBoardList(List<BoardDetailsTO> boardList) {
		this.boardList = boardList;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public TCDetailsTO getTcDetailsTO() {
		return tcDetailsTO;
	}
	public void setTcDetailsTO(TCDetailsTO tcDetailsTO) {
		this.tcDetailsTO = tcDetailsTO;
	}
	public List<CharacterAndConductTO> getList() {
		return list;
	}
	public void setList(List<CharacterAndConductTO> list) {
		this.list = list;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 *  To Reset the fields value in the form
	 */
	public void resetFields(){
		super.setClassId(null);
		super.setYear(null);
		super.setAcademicYear(null);
		this.registerNo=null;
		this.studentId=null;
		this.tcDetailsTO=null;
		this.month=null;
		this.passedStudents=null;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String[] getPassedStudents() {
		return passedStudents;
	}
	public void setPassedStudents(String[] passedStudents) {
		this.passedStudents = passedStudents;
	}
	
}
