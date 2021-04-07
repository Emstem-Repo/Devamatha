package com.kp.cms.forms.admission;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.ApplnMandatoryTO;

public class ApplnMandatoryForm extends BaseActionForm {
	private List<ApplnMandatoryTO> list;

	public List<ApplnMandatoryTO> getList() {
		return list;
	}

	public void setList(List<ApplnMandatoryTO> list) {
		this.list = list;
	}
	
}
