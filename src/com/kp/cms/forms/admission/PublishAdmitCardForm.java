package com.kp.cms.forms.admission;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.PublishAdmitCardTO;

public class PublishAdmitCardForm extends BaseActionForm {
	
	private List<PublishAdmitCardTO> candidatesList;

	public List<PublishAdmitCardTO> getCandidatesList() {
		return candidatesList;
	}

	public void setCandidatesList(List<PublishAdmitCardTO> candidatesList) {
		this.candidatesList = candidatesList;
	}

}
