package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;

public class StudentSpecialPromotionForm extends BaseActionForm {

	private static final long serialVersionUID = 1L;
	
	private Map<Integer, String> classMap;
	private String registerNo;
	List<StudentTO> list;
	private String promotionClassId;
	private String className;
	private Map<Integer, String> promotionClassMap;

	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	
	
	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public List<StudentTO> getList() {
		return list;
	}

	public void setList(List<StudentTO> list) {
		this.list = list;
	}

	public String getPromotionClassId() {
		return promotionClassId;
	}

	public void setPromotionClassId(String promotionClassId) {
		this.promotionClassId = promotionClassId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	public Map<Integer, String> getPromotionClassMap() {
		return promotionClassMap;
	}

	public void setPromotionClassMap(Map<Integer, String> promotionClassMap) {
		this.promotionClassMap = promotionClassMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields() {
		super.setClassId(null);
		this.registerNo=null;
		this.className=null;
	}
}
