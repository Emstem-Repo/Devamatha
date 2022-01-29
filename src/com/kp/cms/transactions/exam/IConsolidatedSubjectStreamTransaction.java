package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.exam.ConsolidatedSubjectStream;
import com.kp.cms.forms.exam.ConsolidatedSubjectStreamForm;

public interface IConsolidatedSubjectStreamTransaction 
{
	public List<ConsolidatedSubjectStream> getConsolidatedSubjectStreams() throws Exception;
	public boolean addConsolidatedSubjectStream(ConsolidatedSubjectStream consolidatedSubjectStream, String mode) throws Exception;
	public boolean deleteConsolidatedSubjectStream(int dupId, boolean activate, ConsolidatedSubjectStreamForm consolidatedSubjectStreamForm) throws Exception;
	public ConsolidatedSubjectStream isDuplicate(ConsolidatedSubjectStream oldConsolidatedSubjectStream) throws Exception;
	public ApplnDoc getStudentPhoto(int admId)throws Exception;
}
