package com.kp.cms.forms.hostel;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.ResidentStudentInfoTO;

public class ResidentStudentInfoForm extends BaseActionForm{
	private String studentId;
	private String studentName;
	ResidentStudentInfoTO rto;
	List<ResidentStudentInfoTO> list;
	private String applNO;
	private String year;
	private String hostelApplnId;
	private List<HostelTO> hostelList;
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public ResidentStudentInfoTO getRto() {
		return rto;
	}

	public void setRto(ResidentStudentInfoTO rto) {
		this.rto = rto;
	}

	public List<ResidentStudentInfoTO> getList() {
		return list;
	}

	public void setList(List<ResidentStudentInfoTO> list) {
		this.list = list;
	}

	public String getApplNO() {
		return applNO;
	}

	public void setApplNO(String applNO) {
		this.applNO = applNO;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHostelApplnId() {
		return hostelApplnId;
	}

	public void setHostelApplnId(String hostelApplnId) {
		this.hostelApplnId = hostelApplnId;
	}

	/**
	 * @return the hostelList
	 */
	public List<HostelTO> getHostelList() {
		return hostelList;
	}

	/**
	 * @param hostelList the hostelList to set
	 */
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		this.studentId=null;
		this.studentName=null;
		super.setHostelId(null);
	}
	
}
