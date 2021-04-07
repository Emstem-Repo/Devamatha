	package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CertificatePurposeForm;
import com.kp.cms.helpers.admin.CertificatePurposeHelper;
import com.kp.cms.to.admin.CertificatePurposeTO;
import com.kp.cms.transactions.admin.ICertificatePurposeTransaction;
import com.kp.cms.transactionsimpl.admin.CertificatePurposeTransactionImpl;

	public class CertificatePurposeHandler {
		private static final Log log=LogFactory.getLog(CertificatePurposeHandler.class);
		public static volatile CertificatePurposeHandler certificatePurposeHandler = null;
		private CertificatePurposeHandler(){
			
		}
		public static CertificatePurposeHandler getInstance(){
			if(certificatePurposeHandler == null){
				certificatePurposeHandler = new CertificatePurposeHandler();
				return certificatePurposeHandler;
			}
			return certificatePurposeHandler;
		}
		/** list of Certificate Purpose getting from Bo.
		 * @return
		 * @throws Exception 
		 */
		public List<CertificatePurposeTO> getPurposeFields() throws Exception {
			List<CertificatePurposeTO> certificatePurposeTOs = null;
			ICertificatePurposeTransaction certificatePurposeTransaction=CertificatePurposeTransactionImpl.getInstance();
			List<CertificateRequestPurpose> purposes = certificatePurposeTransaction.getPurposeFields();
			certificatePurposeTOs=CertificatePurposeHelper.getInstance().copyBoDataToTO(purposes);
			return certificatePurposeTOs;
		}
		/**
		 * @param certificatePurposeForm
		 * @return
		 */
		public boolean addCertificatePurpose(CertificatePurposeForm certificatePurposeForm,String mode)throws Exception {
			boolean isAdded=false;
			ICertificatePurposeTransaction certificatePurposeTransaction=CertificatePurposeTransactionImpl.getInstance();
			Boolean originalNotChanged=false;
			String purpos="";
			String OrgPurpose="";

			if(certificatePurposeForm.getPurposeName()!=null && !certificatePurposeForm.getPurposeName().isEmpty()){
				purpos=certificatePurposeForm.getPurposeName().trim();
			}
			if(certificatePurposeForm.getOrgPurposeName()!=null && !certificatePurposeForm.getOrgPurposeName().isEmpty()){
				OrgPurpose=certificatePurposeForm.getOrgPurposeName().trim();
			}

			if(mode.equals("Add")){
				originalNotChanged= false;
			}
			if(!originalNotChanged){
				CertificateRequestPurpose dupliPurposeBO=CertificatePurposeHelper.getInstance().populatePurposeDateFromFrom(certificatePurposeForm);
				CertificateRequestPurpose dupliPurpose=certificatePurposeTransaction.isDuplicateCertificatePurpose(dupliPurposeBO);
				if(dupliPurpose!=null && dupliPurpose.getIsActive() == true && dupliPurpose.getId()!=dupliPurposeBO.getId() ){
					throw new DuplicateException();
				}
				if(dupliPurpose!=null && dupliPurpose.getIsActive() == false){
					certificatePurposeForm.setDupId(dupliPurpose.getId());
					throw new ReActivateException();
				}
				
				
			}
			CertificateRequestPurpose certificatePurpose=CertificatePurposeHelper.getInstance().populatePurposeDateFromFrom(certificatePurposeForm);
			if(mode.equalsIgnoreCase("Add")){
				certificatePurpose.setCreatedby(certificatePurposeForm.getUserId());
				certificatePurpose.setModifiedBy(certificatePurposeForm.getUserId());
				certificatePurpose.setLastModifiedDate(new Date());
				certificatePurpose.setCreatedDate(new Date());
			}else{ // Edit
				certificatePurpose.setLastModifiedDate(new Date());
				certificatePurpose.setModifiedBy(certificatePurposeForm.getUserId());
			}
			isAdded=certificatePurposeTransaction.addPurpose(certificatePurpose,mode);
			return isAdded;
			
		}
		/**
		 * @param purposeId
		 * @param certificatePurposeForm 
		 * @param activate 
		 * @return
		 * @throws Exception 
		 */
		public boolean deleteCertificatePurpose(int purposeId, boolean activate, CertificatePurposeForm certificatePurposeForm) throws Exception {
			boolean isDeleted=false;
			ICertificatePurposeTransaction certificatePurposeTransaction=CertificatePurposeTransactionImpl.getInstance();
			
			isDeleted=certificatePurposeTransaction.deleteCertificatePurpose(purposeId,activate,certificatePurposeForm);
			return isDeleted;
		}
		
		public void editCertificatePurpose(CertificatePurposeForm certificatePurposeForm) throws Exception{
			ICertificatePurposeTransaction transaction=new CertificatePurposeTransactionImpl();
			CertificateRequestPurpose certificatePurpose=transaction.getPurposedetails(certificatePurposeForm.getId());
			CertificatePurposeHelper.getInstance().setPurposeDetails(certificatePurposeForm,certificatePurpose);
						
		}
			
}
