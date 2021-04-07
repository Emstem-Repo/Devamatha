package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.TreeMap;

import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.forms.admin.DocumentTypeForm;

public interface IDocumentTypeTransaction {
	public List<DocType> getDocTypes() throws Exception;
	public boolean addDocType(DocType docType) throws Exception;
	public boolean updateDocType(DocType docType);
	public boolean deleteDocType(int docId, Boolean activate, DocumentTypeForm docForm) throws Exception;
	public DocType isDocTypeDuplcated(DocType duplDocType) throws Exception;
	public DocType isDocShortNameDuplcated(DocType duplDocType) throws Exception;
	public TreeMap<String, String> getDocTypesMap() throws Exception;
	public List<DocTypeExams> getDocTypeExamsList(int docTypeId)throws Exception;
	public DocType isDisplayOrderDuplcated(DocType duplDocType) throws Exception;
}
