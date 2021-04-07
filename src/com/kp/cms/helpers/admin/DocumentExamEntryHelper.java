package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.forms.admin.DocumentExamEntryForm;
import com.kp.cms.to.admin.DocTypeExamsTO;

public class DocumentExamEntryHelper {
	/**
	 * Singleton object of DocumentExamEntryHelper
	 */
	private static volatile DocumentExamEntryHelper documentExamEntryHelper = null;
	private static final Log log = LogFactory.getLog(DocumentExamEntryHelper.class);
	private DocumentExamEntryHelper() {
		
	}
	/**
	 * return singleton object of DocumentExamEntryHelper.
	 * @return
	 */
	public static DocumentExamEntryHelper getInstance() {
		if (documentExamEntryHelper == null) {
			documentExamEntryHelper = new DocumentExamEntryHelper();
		}
		return documentExamEntryHelper;
	}
	/**
	 * converting the list of DocTypeExams Bo to TO
	 * @param docTypeExamsList
	 * @return
	 * @throws Exception
	 */
	public List<DocTypeExamsTO> convertBOListtoTO(
			List<DocTypeExams> docTypeExamsList) throws Exception{
		List<DocTypeExamsTO> docToList=new ArrayList<DocTypeExamsTO>();
		if(!docTypeExamsList.isEmpty()){
			Iterator<DocTypeExams> docTypeItr=docTypeExamsList.iterator();
			while (docTypeItr.hasNext()) {
				DocTypeExams docTypeExams = (DocTypeExams) docTypeItr.next();
				DocTypeExamsTO docTo=new DocTypeExamsTO();
				docTo.setName(docTypeExams.getName());
				docTo.setId(docTypeExams.getId());
				if(docTypeExams.getDocType()!=null && docTypeExams.getDocType().getIsActive()){
				docTo.setDocTypeId(Integer.toString(docTypeExams.getDocType().getId()));
				docTo.setDocTypeName(docTypeExams.getDocType().getName());
				docToList.add(docTo);
				}
			}
		}
		return docToList;
	}
	/**
	 * converting the form bean to BO
	 * @param documentExamEntryForm
	 * @return
	 */
	public DocTypeExams convertFormtoBO(
			DocumentExamEntryForm documentExamEntryForm) {
		DocTypeExams docTypeExams=new DocTypeExams();
		docTypeExams.setName(documentExamEntryForm.getExamName());
		DocType docType=new DocType();
		docType.setId(Integer.parseInt(documentExamEntryForm.getDocTypeId()));
		docTypeExams.setDocType(docType);
		docTypeExams.setCreatedBy(documentExamEntryForm.getUserId());
		docTypeExams.setCreatedDate(new Date());
		docTypeExams.setModifiedBy(documentExamEntryForm.getUserId());
		docTypeExams.setLastModifiedDate(new Date());
		docTypeExams.setIsActive(true);
		return docTypeExams;
	}
	/**
	 * converting form to BO for update purpose
	 * @param documentExamEntryForm
	 * @return
	 */
	public DocTypeExams convertFormToDocExamTypeBo(
			DocumentExamEntryForm documentExamEntryForm) {
		DocTypeExams docTypeExams=new DocTypeExams();
		docTypeExams.setId(Integer.parseInt(documentExamEntryForm.getDocTypeExamId()));
		docTypeExams.setName(documentExamEntryForm.getExamName());
		DocType docType=new DocType();
		docType.setId(Integer.parseInt(documentExamEntryForm.getDocTypeId()));
		docTypeExams.setDocType(docType);
		docTypeExams.setModifiedBy(documentExamEntryForm.getUserId());
		docTypeExams.setLastModifiedDate(new Date());
		docTypeExams.setIsActive(true);
		return docTypeExams;
	}
	/**
	 * setting the data to form 
	 */
	public void setBodataToForm(DocTypeExams doc,
			DocumentExamEntryForm documentExamEntryForm) throws Exception{
		documentExamEntryForm.setDocTypeExamId(Integer.toString(doc.getId()));
		documentExamEntryForm.setExamName(doc.getName());
		documentExamEntryForm.setDocTypeId(Integer.toString(doc.getDocType().getId()));
		documentExamEntryForm.setOlddocTypeId(Integer.toString(doc.getDocType().getId()));
		documentExamEntryForm.setOldExamName(doc.getName());
		
	}
}
