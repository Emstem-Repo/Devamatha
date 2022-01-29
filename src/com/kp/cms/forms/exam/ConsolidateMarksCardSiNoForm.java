package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ConsolidateMarksCardSiNoTO;
import com.kp.cms.to.exam.MarksCardSiNoTO;

public class ConsolidateMarksCardSiNoForm extends BaseActionForm {
	private String startNo;
	private List<ConsolidateMarksCardSiNoTO> toList; 
	private boolean isAdded;
	private List<ProgramTypeTO> programTypeList;
	

	public String getStartNo() {
		return startNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}
	
	public void reset(){
		this.startNo=null;
		this.toList=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	


	public List<ConsolidateMarksCardSiNoTO> getToList() {
		return toList;
	}

	public void setToList(List<ConsolidateMarksCardSiNoTO> toList) {
		this.toList = toList;
	}

	public boolean getIsAdded() {
		return isAdded;
	}

	public void setIsAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	

	
}
