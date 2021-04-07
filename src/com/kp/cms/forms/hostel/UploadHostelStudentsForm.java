package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelTO;

/**
 * @author balaji
 *
 */
public class UploadHostelStudentsForm extends BaseActionForm {
	private String applnRegno;
	List<HostelTO> hostelList;
	private FormFile thefile;
	
	public String getApplnRegno() {
		return applnRegno;
	}

	public void setApplnRegno(String applnRegno) {
		this.applnRegno = applnRegno;
	}
	public List<HostelTO> getHostelList() {
		return hostelList;
	}

	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void resetFields() {
		this.applnRegno=null;
		super.setHostelId(null);
		super.setYear(null);
		this.thefile=null;
	}
}
