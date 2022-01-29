package com.kp.cms.to.reports;

import java.util.List;

public class ClassFeeConcessionReportTO {
	private String className;
	private List<FeeConcessionReportTO> concessionList;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<FeeConcessionReportTO> getConcessionList() {
		return concessionList;
	}
	public void setConcessionList(List<FeeConcessionReportTO> concessionList) {
		this.concessionList = concessionList;
	}
	
}
