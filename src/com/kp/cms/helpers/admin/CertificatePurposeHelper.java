	package com.kp.cms.helpers.admin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.forms.admin.CertificatePurposeForm;
import com.kp.cms.to.admin.CertificatePurposeTO;

	public class CertificatePurposeHelper {
		private static final Log log=LogFactory.getLog(CertificatePurposeHelper.class);
		public static volatile CertificatePurposeHelper certificatePurposeHelper=null;
		private CertificatePurposeHelper(){
			
		}
		public static CertificatePurposeHelper getInstance(){
			if(certificatePurposeHelper == null){
				certificatePurposeHelper = new CertificatePurposeHelper();
				return certificatePurposeHelper;
			}
			return certificatePurposeHelper;
		}
		/** converting Data from BO to To list.
		 * @param purposes
		 * @return
		 */
		public List<CertificatePurposeTO> copyBoDataToTO(List<CertificateRequestPurpose> purposes)throws Exception {
			List<CertificatePurposeTO> purposeTO = new ArrayList<CertificatePurposeTO>();
			if(purposes!=null){
				Iterator<CertificateRequestPurpose> iterator = purposes.iterator();
				while (iterator.hasNext()) {
					CertificateRequestPurpose certificatePurpose = (CertificateRequestPurpose) iterator.next();
					CertificatePurposeTO to = new CertificatePurposeTO();
					to.setId(certificatePurpose.getId());
					to.setPurposeName(certificatePurpose.getPurposeName());

					purposeTO.add(to);
				}
			}
			
			return purposeTO;
		}
		/**
		 * @param CertificatePurposeForm
		 * @return
		 */
		public CertificateRequestPurpose populatePurposeDateFromFrom(CertificatePurposeForm certificatePurposeForm) {
			CertificateRequestPurpose certificatePurpose= new CertificateRequestPurpose();

				certificatePurpose.setId(certificatePurposeForm.getId());
				certificatePurpose.setPurposeName(certificatePurposeForm.getPurposeName());
				certificatePurpose.setIsActive(true);

			return certificatePurpose;
		}
				
		/**
		 * @param certificatePurpose
		 * @param 
		 */
		public void setPurposeDetails(CertificatePurposeForm certificatePurposeForm, CertificateRequestPurpose certificatePurpose) {
			if(certificatePurpose!=null){
				certificatePurposeForm.setPurposeName(certificatePurpose.getPurposeName());
	    	}
		}
		
	 
	}
