package com.kp.cms.forms.phd;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.phd.PhdInternalGuideTO;

public class PhdInternalGuideForm extends BaseActionForm {
    
	private int id;
	private String employeeId;
	private String disciplineId;
	private String departmentId;
	private String dateOfAward;
	private String empanelmentNo;
	private Map<String,String> employeeMap;
	private Map<String,String> guideShipMap;
	private Map<String,String> departmentMap;
	private List<PhdInternalGuideTO> internalGuideList;
	private int noMphilScolars;
	private int noPhdScolars;
	private int noPhdScolarOutside;
	
	
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage() 
	     {
		this.id=0;
        this.employeeId=null;
        this.disciplineId=null;
        this.dateOfAward=null;
        this.empanelmentNo=null;
        this.departmentMap=null;
        this.departmentId=null;
        this.noMphilScolars=0;
		this.noPhdScolars=0;
		this.noPhdScolarOutside=0;
        
   }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}


	public String getDateOfAward() {
		return dateOfAward;
	}

	public void setDateOfAward(String dateOfAward) {
		this.dateOfAward = dateOfAward;
	}

	public String getEmpanelmentNo() {
		return empanelmentNo;
	}

	public void setEmpanelmentNo(String empanelmentNo) {
		this.empanelmentNo = empanelmentNo;
	}

	public Map<String, String> getEmployeeMap() {
		return employeeMap;
	}

	public void setEmployeeMap(Map<String, String> employeeMap) {
		this.employeeMap = employeeMap;
	}

	public Map<String, String> getGuideShipMap() {
		return guideShipMap;
	}

	public void setGuideShipMap(Map<String, String> guideShipMap) {
		this.guideShipMap = guideShipMap;
	}

	public Map<String, String> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(Map<String, String> departmentMap) {
		this.departmentMap = departmentMap;
	}

	public List<PhdInternalGuideTO> getInternalGuideList() {
		return internalGuideList;
	}

	public void setInternalGuideList(List<PhdInternalGuideTO> internalGuideList) {
		this.internalGuideList = internalGuideList;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public int getNoMphilScolars() {
		return noMphilScolars;
	}

	public void setNoMphilScolars(int noMphilScolars) {
		this.noMphilScolars = noMphilScolars;
	}

	public int getNoPhdScolars() {
		return noPhdScolars;
	}

	public void setNoPhdScolars(int noPhdScolars) {
		this.noPhdScolars = noPhdScolars;
	}

	public int getNoPhdScolarOutside() {
		return noPhdScolarOutside;
	}

	public void setNoPhdScolarOutside(int noPhdScolarOutside) {
		this.noPhdScolarOutside = noPhdScolarOutside;
	}

}
