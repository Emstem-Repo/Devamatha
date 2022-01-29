package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admission.PromoteSubjects;
import com.kp.cms.forms.admission.PromoteSubjectsUploadForm;

public interface IPromoteSubjectsUploadTransaction {

	public boolean UploadPromoteSubjects(List<PromoteSubjects> promoteSubjects)throws Exception;

	public boolean isDuplicateYear(PromoteSubjectsUploadForm proSubjectsUploadForm)throws Exception;

	



	
}
