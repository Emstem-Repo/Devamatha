package com.kp.cms.to.exam;

import java.util.List;

public class InvDutyMainTO {

	private List<KeyValueTO> invDutyTypeList;
	private List<InvDutyDetailsTO> invDutyDetailsList;
	

	public List<KeyValueTO> getInvDutyTypeList() {
		return invDutyTypeList;
	}

	public void setInvDutyTypeList(List<KeyValueTO> invDutyTypeList) {
		this.invDutyTypeList = invDutyTypeList;
	}

	public List<InvDutyDetailsTO> getInvDutyDetailsList() {
		return invDutyDetailsList;
	}

	public void setInvDutyDetailsList(List<InvDutyDetailsTO> invDutyDetailsList) {
		this.invDutyDetailsList = invDutyDetailsList;
	}

	

}
