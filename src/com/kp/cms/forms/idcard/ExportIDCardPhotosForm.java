package com.kp.cms.forms.idcard;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;

public class ExportIDCardPhotosForm  extends BaseActionForm{
      
	private Map<Integer,String> classMap;
	private String classId;
	private String year;
	private String method;
	private String applnNo;
	
	public String getApplnNo() {
		return applnNo;
	}


	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}


	private List<ProgramTypeTO> programTypeList;	
	
	
	
	
	public Map<Integer, String> getClassMap() {
		return classMap;
	}


	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}


	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}
	

	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public void resetFields()
	{
		this.classId=null;
		this.classMap=null;
		this.year=null;
	}


	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}


	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	
	
	
	
	
}
