package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DepartmentEntryTO;

public class DepartmentEntryForm extends BaseActionForm {
	private int id;
	private String name;
	private int dupId;
	private String orgName;
	private Map<Integer,String> empStreamMap;
	private List<DepartmentEntryTO> departmentList;
	private String empStreamId;
	private String isAcademic;
	private String empStreamName;
	private int orgempStreamId;
	private String code;
	private String orgCode;
	private String webId;
	private String email;
	
		
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	 
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.name = null;
		this.setEmpStreamId(null);
		this.code=null;
		this.isAcademic="true";
		this.webId=null;
		this.email=null;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDepartmentList(List<DepartmentEntryTO> departmentList) {
		this.departmentList = departmentList;
	}
	public List<DepartmentEntryTO> getDepartmentList() {
		return departmentList;
	}

	public void setDupId(int dupId) {
		this.dupId = dupId;
	}

	public int getDupId() {
		return dupId;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setEmpStreamList(Map<Integer, String> empStreamList) {
		this.empStreamMap = empStreamList;
	}

	public Map<Integer, String> getEmpStreamList() {
		return empStreamMap;
	}


	public void setEmpStreamName(String empStreamName) {
		this.empStreamName = empStreamName;
	}

	public String getEmpStreamName() {
		return empStreamName;
	}

	public void setOrgempStreamId(int orgempStreamId) {
		this.orgempStreamId = orgempStreamId;
	}

	public int getOrgempStreamId() {
		return orgempStreamId;
	}

	public void setEmpStreamId(String empStreamId) {
		this.empStreamId = empStreamId;
	}

	public String getEmpStreamId() {
		return empStreamId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getIsAcademic() {
		return isAcademic;
	}

	public void setIsAcademic(String isAcademic) {
		this.isAcademic = isAcademic;
	}

	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
