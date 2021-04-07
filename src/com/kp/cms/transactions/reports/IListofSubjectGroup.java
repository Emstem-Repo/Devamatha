package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.forms.reports.ListofSubjectGroupsForm;

public interface IListofSubjectGroup {
	public List<SubjectGroup> getSubjectGroup(ListofSubjectGroupsForm subForm) throws Exception;
}
