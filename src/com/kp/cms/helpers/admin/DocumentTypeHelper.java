package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.forms.admin.DocumentTypeForm;
import com.kp.cms.to.admin.DocTypeTO;
import com.kp.cms.transactions.admin.IDocumentTypeTransaction;
import com.kp.cms.transactionsimpl.admin.DocumentTypeTransactionImpl;



public class DocumentTypeHelper {
	private static final Log log = LogFactory.getLog(DocumentTypeHelper.class);
	public static volatile DocumentTypeHelper documentTypeHelper = null;
	public static DocumentTypeHelper getInstance() {
	      if(documentTypeHelper == null) {
	    	  documentTypeHelper = new DocumentTypeHelper();
	    	  return documentTypeHelper;
	      }
	      return documentTypeHelper;
	}

	/**
	 * 
	 * @param docTypeTOList
	 *            this will copy the DocumentType BO to TO
	 * @return docTypeTOList having DocTypeTO objects.
	 */
	public List<DocTypeTO> copydocTypeBosToTos(List<DocType> docTypeList) {
		log.debug("inside copydocTypeBosToTos");
		List<DocTypeTO> docTypeTOList = new ArrayList<DocTypeTO>();
		Iterator<DocType> iterator = docTypeList.iterator();
		DocType docType;
		DocTypeTO docTypeTO;
		while (iterator.hasNext()) {
			docTypeTO = new DocTypeTO();
			docType = (DocType) iterator.next();
			docTypeTO.setId(docType.getId());
			docTypeTO.setName(docType.getName());
			docTypeTO.setDocShortName(docType.getDocShortName());
			docTypeTO.setPrintName(docType.getPrintName());
			docTypeTO.setDisplayOrder(docType.getDisplayOrder());
			if(docType.getIsEducationalInfo()!=null && !docType.getIsEducationalInfo().toString().isEmpty()){
				if(docType.getIsEducationalInfo()){
					docTypeTO.setIsEducationalInfo("yes");
				}else{
					docTypeTO.setIsEducationalInfo("no");
				}
			}
			docTypeTOList.add(docTypeTO);
		}
		log.debug("leaving copydocTypeBosToTos");
		return docTypeTOList;
	}
	/**
	 * 
	 * @param documentTypeForm
	 *        Creates docType BO from documentTypeForm.
	 * @return docType BO Object.
	 * @throws Exception may throw Number format Exception while copying.
	 */
	public DocType copyDataFromFormToBO(DocumentTypeForm documentTypeForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		DocType docType = new DocType();
		Set<DocTypeExams> docTypeExamsSet=new HashSet<DocTypeExams>();
		IDocumentTypeTransaction iDocumentTypeTransaction = DocumentTypeTransactionImpl.getInstance(); 
		if(documentTypeForm.getId() != 0) 
		{
			docType.setId(documentTypeForm.getId());
			List<DocTypeExams> docTypeExamsList=iDocumentTypeTransaction.getDocTypeExamsList(docType.getId());
			for(DocTypeExams exams:docTypeExamsList)
			{
				docTypeExamsSet.add(exams);
			}
		}
		if(documentTypeForm.getEducationalInfo().equalsIgnoreCase("yes")){
			docType.setIsEducationalInfo(true);
		}else if(documentTypeForm.getEducationalInfo().equalsIgnoreCase("no")){
			docType.setIsEducationalInfo(false);
		}
		
		docType.setName(documentTypeForm.getName());
		docType.setDocShortName(documentTypeForm.getDocShortName());
		docType.setPrintName(documentTypeForm.getPrintName());
		docType.setCreatedBy(documentTypeForm.getUserId());
		docType.setModifiedBy(documentTypeForm.getUserId());
		docType.setDisplayOrder(Integer.parseInt(documentTypeForm.getDisplayOrder()));
		docType.setCreatedDate(new Date());
		docType.setLastModifiedDate(new Date());
		docType.setIsActive(true);
		docType.setDocTypeExamses(docTypeExamsSet);
		return docType;
		}
}
