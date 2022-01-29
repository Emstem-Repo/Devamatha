package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.AssignExternalToDepartmentTO;

public class AssignExternalToDepartmentForm extends BaseActionForm{
	private String departmentId;
	private List<AssignExternalToDepartmentTO> deptList;
	private List<AssignExternalToDepartmentTO> evlList;
	private boolean isSelected;
	private boolean flag;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public List<AssignExternalToDepartmentTO> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<AssignExternalToDepartmentTO> deptList) {
		this.deptList = deptList;
	}

	public List<AssignExternalToDepartmentTO> getEvlList() {
		return evlList;
	}

	public void setEvlList(List<AssignExternalToDepartmentTO> evlList) {
		this.evlList = evlList;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void resetFields(){
		this.departmentId=null;
		this.evlList=null;
		this.isSelected=false;
		this.flag=false;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
