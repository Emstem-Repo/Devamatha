package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;

public interface ICheckListTransaction {

	public List<DocChecklist> getCheckDuplicate(int courseId, int year)throws Exception;

	public boolean addCheckList(DocChecklist checklist)throws Exception;
	
	public boolean updateCheckList(DocChecklist checklist) throws Exception;

	public List<DocType> getDocuments()throws Exception;

	public List<DocChecklist> getCheckList(int year)throws Exception;

	public List<DocChecklist> getToFromId(int id)throws Exception;
	
	public List<DocChecklist> getChecklist(String courseId, String year) throws Exception;

	public boolean editCheckList(DocChecklist checklist)throws Exception;

	public boolean deleteCheckList(String courseId,String year)throws Exception;
	
	public boolean reActivateCheckList(String courseId,String year) throws Exception;

}