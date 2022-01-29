package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.ApplnDocDetails;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.DocumentVerificationEntryForm;
import com.kp.cms.to.admin.ApplnDocDetailsTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.DocumentVerificationEntryTxImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author Nagarjun
 *
 */
public class DocumentVerificationEntryHandler {
	/**
	 * Singleton object of AssignClassForStudentHandler
	 */
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	private static volatile DocumentVerificationEntryHandler handler = null;
	private static final Log log = LogFactory.getLog(DocumentVerificationEntryHandler.class);
	private DocumentVerificationEntryHandler() {
		
	}
	/**
	 * return singleton object of assignSecondLanguageHandler.
	 * @return
	 */
	public static DocumentVerificationEntryHandler getInstance() {
		if (handler == null) {
			handler = new DocumentVerificationEntryHandler();
		}
		return handler;
	}
	/**
	 * @param documentVerificationEntryForm
	 * @throws Exception
	 */
	public void setDataToForm(DocumentVerificationEntryForm documentVerificationEntryForm) throws Exception{
		Student student = DocumentVerificationEntryTxImpl.getInstance().getStudentDetails(documentVerificationEntryForm.getRegisterNo());
		if(student != null){
			documentVerificationEntryForm.setAdmBO(student.getAdmAppln());
			List<ApplnDocTO> list = copyPropertiesEditDocValue(student.getAdmAppln().getApplnDocs(), student.getAdmAppln().getCourseBySelectedCourseId().getId(), student.getAdmAppln().getAppliedYear());
			documentVerificationEntryForm.setDocList(list);
		}
	}
	/**
	 * @param docUploadSet
	 * @param courseId
	 * @param appliedYear
	 * @return
	 * @throws Exception
	 */
	public List<ApplnDocTO> copyPropertiesEditDocValue(
			Set<ApplnDoc> docUploadSet, int courseId,
			int appliedYear) throws Exception {
		log.info("enter copyPropertiesEditDocValue");
		List<ApplnDocTO> documentsList = new ArrayList<ApplnDocTO>();
		ApplnDocTO applnDocTO = null;

		List<ApplnDocTO> reqList = getRequiredDocList(String.valueOf(courseId), appliedYear);

		boolean photoexist = false;
		if (docUploadSet != null && !docUploadSet.isEmpty()) {
			Iterator<ApplnDoc> iterator = docUploadSet.iterator();
			while (iterator.hasNext()) {
				ApplnDoc applnDocBO = (ApplnDoc) iterator.next();
				if(!applnDocBO.getIsPhoto()){
					applnDocTO = new ApplnDocTO();
					
					applnDocTO.setId(applnDocBO.getId());
					applnDocTO.setCreatedBy(applnDocBO.getCreatedBy());
					applnDocTO.setCreatedDate(applnDocBO.getCreatedDate());
					if (applnDocBO.getDocType() != null) {
						applnDocTO.setDocTypeId(applnDocBO.getDocType().getId());
						applnDocTO.setDocName(applnDocBO.getDocType()
								.getPrintName());
						applnDocTO.setPrintName(applnDocBO.getDocType()
								.getDocShortName());
						applnDocTO.setDisplayOrder(applnDocBO.getDocType().getDisplayOrder());
					}
					applnDocTO.setName(applnDocBO.getName());
					if (applnDocBO.getSubmissionDate() != null)
						applnDocTO.setSubmitDate(CommonUtil
								.ConvertStringToDateFormat(CommonUtil
										.getStringDate(applnDocBO
												.getSubmissionDate()),
												DocumentVerificationEntryHandler.SQL_DATEFORMAT,
												DocumentVerificationEntryHandler.FROM_DATEFORMAT));
					applnDocTO.setContentType(applnDocBO.getContentType());
					if (applnDocBO.getIsVerified() != null
							&& applnDocBO.getIsVerified()) {
						applnDocTO.setVerified(true);
					} else {
						applnDocTO.setVerified(false);
					}
					if (applnDocBO.getHardcopySubmitted() != null
							&& applnDocBO.getHardcopySubmitted()) {
						applnDocTO.setHardSubmitted(false);
						applnDocTO.setTemphardSubmitted(true);
					} else {
						applnDocTO.setHardSubmitted(false);
						applnDocTO.setTemphardSubmitted(false);
					}
					if (applnDocBO.getNotApplicable() != null
							&& applnDocBO.getNotApplicable()) {
						applnDocTO.setNotApplicable(false);
						applnDocTO.setTempNotApplicable(true);
					} else {
						applnDocTO.setNotApplicable(false);
						applnDocTO.setTempNotApplicable(false);
					}
					applnDocTO.setPhoto(false);
					if (applnDocBO.getDocument() != null) {
						applnDocTO.setDocumentPresent(true);
						applnDocTO.setCurrDocument(applnDocBO.getDocument());
						
					} else {
						applnDocTO.setDocumentPresent(false);
					}
					if(applnDocBO.getSemNo()!=null){
						applnDocTO.setSemisterNo(applnDocBO.getSemNo());
					}
					if(applnDocBO.getSemType()!=null){
						applnDocTO.setSemType(applnDocBO.getSemType());
					}
					Set<DocChecklist> docChecklistDoc = null;
					if (applnDocBO.getDocType() != null) {
						docChecklistDoc = applnDocBO.getDocType()
						.getDocChecklists();
					}
					
					if (docChecklistDoc != null) {
						Iterator<DocChecklist> it = docChecklistDoc.iterator();
						while (it.hasNext()) {
							DocChecklist docChecklistBO = (DocChecklist) it.next();
							// condition to check whether course id and applicant
							// course id are matching
							if (docChecklistBO.getCourse().getId() == applnDocBO
									.getAdmAppln().getCourse().getId()
									&& docChecklistBO.getYear() == appliedYear) {
								if (docChecklistBO.getNeedToProduce() != null
										&& docChecklistBO.getNeedToProduce()
										&& docChecklistBO.getIsActive()) {
									applnDocTO.setNeedToProduce(true);
								} else {
									applnDocTO.setNeedToProduce(false);
								}
								if(docChecklistBO.getNeedToProduceSemwiseMc()!=null && docChecklistBO.getNeedToProduceSemwiseMc()&& docChecklistBO.getIsActive()){
									applnDocTO.setNeedToProduceSemWiseMC(true);
								}else{
									applnDocTO.setNeedToProduceSemWiseMC(false);
								}
							}
						}
					}
					if(applnDocTO.isNeedToProduceSemWiseMC()){
						List<Integer> noList=new ArrayList<Integer>();
						
						Set<ApplnDocDetails> docDetailsSet=applnDocBO.getApplnDocDetails();
						List<ApplnDocDetailsTO> detailList=new ArrayList<ApplnDocDetailsTO>();
						if(docDetailsSet!=null && !docDetailsSet.isEmpty()){
							Iterator<ApplnDocDetails> itr=docDetailsSet.iterator();
							while (itr.hasNext()) {
								ApplnDocDetails bo = (ApplnDocDetails) itr.next();
								ApplnDocDetailsTO to=new ApplnDocDetailsTO();
								to.setId(bo.getId());
								to.setApplnDocId(applnDocBO.getId());
								to.setSemNo(bo.getSemesterNo());
//							to.setIsHardCopySubmitted(bo.getIsHardCopySubmited());
								to.setTempHardCopySubmitted(bo.getIsHardCopySubmited());
								if(bo.getIsHardCopySubmited()!=null && bo.getIsHardCopySubmited()){
									to.setChecked("yes");
								}else{
									to.setChecked("no");
								}
								noList.add(Integer.parseInt(bo.getSemesterNo()));
								detailList.add(to);
							}
						}
						for (int i=1;i<=12;i++) {
							if(!noList.contains(i)){
								ApplnDocDetailsTO to=new ApplnDocDetailsTO();
								to.setSemNo(String.valueOf(i));
								to.setTempHardCopySubmitted(false);
								to.setChecked("no");
								detailList.add(to);
							}
						}
						Collections.sort(detailList);
						applnDocTO.setDocDetailsList(detailList);
					}
					
					// remove exists,add new requireds
					if (reqList != null) {
						Iterator<ApplnDocTO> it = reqList.iterator();
						while (it.hasNext()) {
							ApplnDocTO reqTo = (ApplnDocTO) it.next();
							if (reqTo.getDocTypeId() != 0
									&& applnDocTO.getDocTypeId() != 0
									&& reqTo.getDocTypeId() == applnDocTO
									.getDocTypeId()) {
								// remove from required list
								
								it.remove();
								
							}
							if (reqTo.isPhoto()) {
								
								it.remove();
							}
						}
					}
					
					documentsList.add(applnDocTO);
				}
			}

			// add requireds
			if (reqList != null && !reqList.isEmpty()) {
				Iterator<ApplnDocTO> it = reqList.iterator();
				while (it.hasNext()) {
					ApplnDocTO reqTo = (ApplnDocTO) it.next();
					documentsList.add(reqTo);
				}
			}
		} else {
			documentsList = reqList;
		}
		Collections.sort(documentsList, new ApplnDocTO());
		log.info("exit copyPropertiesEditDocValue");
		return documentsList;
	}
	/**
	 * @param documentVerificationEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveStudent(DocumentVerificationEntryForm documentVerificationEntryForm) throws Exception {
		AdmAppln appBO = documentVerificationEntryForm.getAdmBO();
		appBO = setDocUploads(appBO, documentVerificationEntryForm);
		boolean save = DocumentVerificationEntryTxImpl.getInstance().saveStudentDocs(appBO);
		return save;
	}
	
	/**
	 * @param appBO
	 * @param documentVerificationEntryForm
	 * @return
	 * @throws Exception
	 */
	private AdmAppln setDocUploads(AdmAppln appBO,DocumentVerificationEntryForm documentVerificationEntryForm) throws Exception {
		log.info("enter setDocUploads");
		if (documentVerificationEntryForm.getDocList() != null) {
			Set<ApplnDoc> orginalSet = appBO.getApplnDocs();
			Set<ApplnDoc> docSet = new HashSet<ApplnDoc>();
			if(orginalSet != null){
				Iterator<ApplnDoc> iterator = orginalSet.iterator();
				while (iterator.hasNext()) {
					ApplnDoc applnDoc = (ApplnDoc) iterator.next();
					if (applnDoc.getIsPhoto()) {
						docSet.add(applnDoc);
					}
				}
			}
			Iterator<ApplnDocTO> docItr = documentVerificationEntryForm.getDocList()
					.iterator();
			while (docItr.hasNext()) {
				ApplnDocTO docTO = (ApplnDocTO) docItr.next();
				ApplnDoc docBO = new ApplnDoc();
				docBO.setId(docTO.getId());
				docBO.setCreatedBy(docTO.getCreatedBy());
				docBO.setCreatedDate(docTO.getCreatedDate());
				docBO.setModifiedBy(documentVerificationEntryForm.getUserId());
				docBO.setLastModifiedDate(new Date());
				docBO.setIsVerified(docTO.isVerified());
				if (docTO.getDocTypeId() != 0) {
					DocType doctype = new DocType();
					doctype.setId(docTO.getDocTypeId());
					docBO.setDocType(doctype);
				} else {
					docBO.setDocType(null);
				}
				docBO.setHardcopySubmitted(docTO.isHardSubmitted());
				docBO.setNotApplicable(docTO.isNotApplicable());
				if (!docBO.getHardcopySubmitted() && !docBO.getNotApplicable()) {
					if (docTO.getSubmitDate() != null
							&& !StringUtils.isEmpty(docTO.getSubmitDate()))
						docBO
								.setSubmissionDate(CommonUtil
										.ConvertStringToSQLDate(docTO
												.getSubmitDate()));
				} else {
					docBO.setSubmissionDate(null);
				}
				docBO.setIsPhoto(docTO.isPhoto());
				if (docTO.getEditDocument() != null && docTO.getEditDocument().getFileName() != null
						&& !StringUtils.isEmpty(docTO.getEditDocument()
								.getFileName())) {
					FormFile editDoc = docTO.getEditDocument();
					docBO.setDocument(editDoc.getFileData());
					docBO.setName(editDoc.getFileName());
					docBO.setContentType(editDoc.getContentType());
				} else {
					docBO.setDocument(docTO.getCurrDocument());
					docBO.setName(docTO.getName());
					docBO.setContentType(docTO.getContentType());
				}
				List<ApplnDocDetailsTO> list=docTO.getDocDetailsList();
				if(list!=null && !list.isEmpty()){
					Set<ApplnDocDetails> docDetailSet=new HashSet<ApplnDocDetails>();
					Iterator<ApplnDocDetailsTO> itr=list.iterator();
					while (itr.hasNext()) {
						ApplnDocDetailsTO to = (ApplnDocDetailsTO) itr.next();
						if(to.getChecked()!=null &&  to.getChecked().equals("on")){
							ApplnDocDetails bo=new ApplnDocDetails();
							if(to.getId()>0){
							bo.setId(to.getId());
							}
							bo.setSemesterNo(to.getSemNo());
							bo.setIsHardCopySubmited(true);
							bo.setApplnDoc(docBO);
							bo.setCreatedBy(documentVerificationEntryForm.getUserId());
							bo.setCreatedDate(new Date());
							bo.setModifiedBy(documentVerificationEntryForm.getUserId());
							bo.setLastModifiedDate(new Date());
							docDetailSet.add(bo);
						}
					}
					docBO.setApplnDocDetails(docDetailSet);
				}
				docBO.setSemNo(docTO.getSemisterNo());
				if(docTO.getSemType()!=null && !docTO.getSemType().isEmpty() && docTO.getSemisterNo()!=null && !docTO.getSemisterNo().isEmpty())
				docBO.setSemType(docTO.getSemType());
				docSet.add(docBO);
			}
			
			appBO.setApplnDocs(docSet);
		}
		log.info("exit setDocUploads");
		return appBO;
	}
	/**
	 * @param courseID
	 * @param appliedyear
	 * @return
	 * @throws Exception
	 */
	public List<ApplnDocTO> getRequiredDocList(String courseID, int appliedyear)throws Exception{
		log.info("Enter getRequiredDocList ...");
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<DocChecklist> checklistDocs=txn.getNeededDocumentList(courseID);
		log.info("Exit getRequiredDocList ...");
		return handler.createDocUploadListForCourse(checklistDocs,appliedyear);
	}
	/**
	 * @param listofdocs
	 * @param appliedyear
	 * @return
	 */
	public List<ApplnDocTO> createDocUploadListForCourse(
			List<DocChecklist> listofdocs, int appliedyear) {
		log.info("enter createDocUploadListForCourse" );
		List<ApplnDocTO> doclist= new ArrayList<ApplnDocTO>();
		
		 if(listofdocs!=null && ! listofdocs.isEmpty()){
			 Iterator<DocChecklist> boitr=listofdocs.iterator();
				 while (boitr.hasNext()) {
					DocChecklist docChecklist = (DocChecklist) boitr.next();
					
						  if( (docChecklist.getNeedToProduce()==true && docChecklist.getIsActive() && docChecklist.getYear()==appliedyear ))
						  {
							  ApplnDocTO uploadTo= new ApplnDocTO();
							  uploadTo.setName(docChecklist.getDocType().getPrintName());
							  uploadTo.setPrintName(docChecklist.getDocType().getDocShortName());
							  uploadTo.setDocName(docChecklist.getDocType().getName());
							  uploadTo.setDocTypeId(docChecklist.getDocType().getId());
							  uploadTo.setDisplayOrder(docChecklist.getDocType().getDisplayOrder());
							  if(docChecklist.getNeedToProduceSemwiseMc()!=null && docChecklist.getNeedToProduceSemwiseMc()){
								  uploadTo.setNeedToProduceSemWiseMC(docChecklist.getNeedToProduceSemwiseMc());
								  List<ApplnDocDetailsTO> list=new ArrayList<ApplnDocDetailsTO>();
								  for(int i=1;i<=12;i++){
									  ApplnDocDetailsTO to=new ApplnDocDetailsTO();
									  to.setTempHardCopySubmitted(false);
									  to.setSemNo(String.valueOf(i));
									  to.setChecked("no");
									  list.add(to);
								  }
								  uploadTo.setDocDetailsList(list);
							  }
							  else{
								  uploadTo.setNeedToProduceSemWiseMC(false);
							  }
							  doclist.add(uploadTo);
						  }
				}
				 
			}
			
		 log.info("exit createDocUploadListForCourse" );
		 Collections.sort(doclist, new ApplnDocTO());
		return doclist;
	}
}
