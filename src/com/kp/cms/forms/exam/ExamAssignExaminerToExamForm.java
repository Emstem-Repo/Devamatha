package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.InvDutyMainTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.EmployeeTO;

public class ExamAssignExaminerToExamForm extends BaseActionForm {

	private int examNameId;
	private String examName;
	private String firstName;
	private String lastNAme;
	private String department;
	private String examiner;
	// private int invigilatorId;
	
	List<KeyValueTO> examNameList;
	private int examinerListSize;
	 private String parent;
	private String flag;
	// List<KeyValueTO> invigilatorList;
	 List<EmployeeTO> examinerList;
	 //added by Smitha 
	 private String examType;
	 private HashMap<Integer, String> examTypeList;
	 private Map<Integer,String> examNameMap;
	
	
	public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter("formName");
			ActionErrors actionErrors = super.validate(mapping, request, formName);
			return actionErrors;
		}
	 public void clearPage() {
			this.examName="";
			
		}
	
	/**
	 * @return the examinerList
	 */
	public List<EmployeeTO> getExaminerList() {
		return examinerList;
	}

	/**
	 * @param examinerList the examinerList to set
	 */
	public void setExaminerList(List<EmployeeTO> examinerList) {
		this.examinerList = examinerList;
	}

	/**
	 * @return the examNameId
	 */
	public int getExamNameId() {
		return examNameId;
	}

	/**
	 * @param examNameId
	 *            the examNameId to set
	 */
	public void setExamNameId(int examNameId) {
		this.examNameId = examNameId;
	}

	/**
	 * @return the examName
	 */
	public String getExamName() {
		return examName;
	}

	/**
	 * @param examName
	 *            the examName to set
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastNAme
	 */
	public String getLastNAme() {
		return lastNAme;
	}

	/**
	 * @param lastNAme
	 *            the lastNAme to set
	 */
	public void setLastNAme(String lastNAme) {
		this.lastNAme = lastNAme;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the examNameList
	 */
	public List<KeyValueTO> getExamNameList() {
		return examNameList;
	}

	/**
	 * @param examNameList
	 *            the examNameList to set
	 */
	public void setExamNameList(List<KeyValueTO> examNameList) {
		this.examNameList = examNameList;
	}

	public void setExaminerListSize(int examinerListSize) {
		this.examinerListSize = examinerListSize;
	}

	public int getExaminerListSize() {
		return examinerListSize;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}

	public String getExaminer() {
		return examiner;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getExamType() {
			return examType;
		}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public HashMap<Integer, String> getExamTypeList() {
		return examTypeList;
	}
	public void setExamTypeList(HashMap<Integer, String> examTypeList) {
		this.examTypeList = examTypeList;
	}
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}
	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
	

}
