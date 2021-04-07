package com.kp.cms.forms.reports;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.CiaOverAllReportTO;

public class CiaOverallReportForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CiaOverAllReportTO> ciaOverAllList;

	public List<CiaOverAllReportTO> getCiaOverAllList() {
		return ciaOverAllList;
	}

	public void setCiaOverAllList(List<CiaOverAllReportTO> ciaOverAllList) {
		this.ciaOverAllList = ciaOverAllList;
	}
	
	
}
