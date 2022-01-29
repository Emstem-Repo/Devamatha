package com.kp.cms.handlers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.junit.runner.Request;

import com.kp.cms.bo.admin.AssignCertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateDetailsRoles;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.admin.TemplateForm;
import com.kp.cms.helpers.admin.CertificateDetailsHelpers;
import com.kp.cms.helpers.admin.CertificateDetailsHelpers;
import com.kp.cms.to.admin.CertificateDetailsTemplateTO;
import com.kp.cms.to.admin.CertificateDetailsTo;
import com.kp.cms.to.admin.CertificatePurposeTO;
import com.kp.cms.to.admin.GroupTemplateTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.transactions.admin.ICertificateDetailsTransaction;
import com.kp.cms.transactions.admin.ICertificateDetailsTransaction;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.transactionsimpl.admin.CertificateDetailsImpl;
import com.kp.cms.transactionsimpl.admin.CertificateDetailsImpl;
import com.kp.cms.transactionsimpl.admin.TemplateImpl;
import com.kp.cms.utilities.HibernateUtil;

public class CertificateDetailsHandler
{

	private static final Log log=LogFactory.getLog(CertificateDetailsHandler.class);
	public static volatile CertificateDetailsHandler evaStudentFeedBackQuestionHandler=null;
	
	public static CertificateDetailsHandler getInstance()
    {
        if(evaStudentFeedBackQuestionHandler == null)
        {
            evaStudentFeedBackQuestionHandler = new CertificateDetailsHandler();
            return evaStudentFeedBackQuestionHandler;
        } else
        {
            return evaStudentFeedBackQuestionHandler;
        }
    }
	ICertificateDetailsTransaction transaction = CertificateDetailsImpl.getInstance();
    
    public List<CertificateDetailsTo> getCertificateList()throws Exception
    {
        List<CertificateDetails> getCertificateList = transaction.getCertificateList();
        List<CertificateDetailsTo> certificateList = CertificateDetailsHelpers.getInstance().convertBoToTos(getCertificateList);
        return certificateList;
    }

    public boolean duplicateCheck(CertificateDetailsForm certificateDetailsForm, ActionErrors errors, HttpSession session)
        throws Exception
    {
        boolean duplicate = transaction.duplicateCheck(certificateDetailsForm, errors, session);
        return duplicate;
    }

    public boolean addcertificateDetails(CertificateDetailsForm certificateDetailsForm, String mode)
        throws Exception
    {
    	CertificateDetails certificateDetails = CertificateDetailsHelpers.getInstance().convertFormToBos(certificateDetailsForm);
        boolean isAdded = transaction.addcertificateDetails(certificateDetails, mode);
        return isAdded;
    }

    public void editCertificateDetails(CertificateDetailsForm certificateDetailsForm)
        throws Exception
    {
    	CertificateDetails certificateDetails = transaction.getcertificateDetailsById(certificateDetailsForm.getId());
        CertificateDetailsHelpers.getInstance().setDataBoToForm(certificateDetailsForm, certificateDetails);
    }

    public boolean updatecertificateDetails(CertificateDetailsForm certificateDetailsForm, String mode)
        throws Exception
    {
    	CertificateDetails certificateDetails = transaction.getcertificateDetailsById(certificateDetailsForm.getId());
    	certificateDetails = CertificateDetailsHelpers.getInstance().setFormToBo(certificateDetails, certificateDetailsForm);
        boolean isUpdated = transaction.addcertificateDetails(certificateDetails, mode);
        return isUpdated;
    }

    public boolean deleteCertificateDetails(CertificateDetailsForm certificateDetailsForm)
        throws Exception
    {
        boolean isDeleted = transaction.deleteCertificateDetails(certificateDetailsForm.getId());
        return isDeleted;
    }

    public boolean reActivateCertificateDetails(CertificateDetailsForm certificateDetailsForm, String userId)
        throws Exception
    {
        return transaction.reActivateCertificateDetails(certificateDetailsForm);
    }

	/*public List<RolesTO> getRoles() throws Exception{
		 List<Roles> rolesList = transaction.getgetRoles();
		 List<RolesTO> rolesLists = CertificateDetailsHelpers.getInstance().setAssignRoles(rolesList,new HashMap<Integer, Integer>());
	        return rolesLists;
	}*/

	public boolean saveCertificateDetails(CertificateDetailsForm certificateDetailsForm) throws Exception{
		List<CertificateDetailsRoles> certificateDetailsRoles = CertificateDetailsHelpers.getInstance().saveFormToBosRoles(certificateDetailsForm);
		return transaction.addCertificateDetails(certificateDetailsRoles);
      }
	
	public boolean saveCertificateDetailsPurpose(CertificateDetailsForm certificateDetailsForm) throws Exception{
		List<AssignCertificateRequestPurpose> certificateReqPurpose = CertificateDetailsHelpers.getInstance().saveFormToBosPurpose(certificateDetailsForm);
		return transaction.addCertificateDetailsPurpose(certificateReqPurpose);
      }
	public boolean saveCertificateDetailsTemplate(CertificateDetailsForm certificateDetailsForm) throws Exception{
		CertificateDetailsTemplate templateDetails = CertificateDetailsHelpers.getInstance().saveFormToBosTemplate(certificateDetailsForm);
		return transaction.addCertificateDetailsTemplate(templateDetails);
      }
	

	public  List<RolesTO> getAssignRoles(CertificateDetailsForm certificateDetailsForm) throws Exception{
		 List<Roles> roles = transaction.getgetRoles();
		 Map<Integer,Integer> rolesList = transaction.getAssignRoles(certificateDetailsForm);
		 certificateDetailsForm.setOldCountt(rolesList.size());
		 List<RolesTO> rolesLists = CertificateDetailsHelpers.getInstance().setAssignRoles(roles,rolesList);
	     return rolesLists;
	}

	public  List<CertificatePurposeTO> getAssignPurpose(CertificateDetailsForm certificateDetailsForm) throws Exception{
		 List<CertificateRequestPurpose> purpose=transaction.getPurpose();
		 Map<Integer,Integer> purposeList = transaction.getAssignPurpose(certificateDetailsForm);
		 certificateDetailsForm.setOldCountt(purposeList.size());
		 List<CertificatePurposeTO> purposeListsNew = CertificateDetailsHelpers.getInstance().setAssignPurpose(purpose,purposeList);
	     return purposeListsNew;
	}
	public List<CertificateDetailsTemplateTO> getGroupTemplateList(String templateName) throws Exception {
		List<CertificateDetailsTemplate> list= transaction.getGroupTemplates(templateName);
		log.debug("Leaving getTemplateList ");
		
		return CertificateDetailsHelpers.getInstance().convertBOtoTO(list);
	}
	
	 
		public boolean updateGroupTemplate(CertificateDetailsForm templateForm) throws Exception {
			CertificateDetailsTemplate groupTemplate = CertificateDetailsHelpers.getInstance().getGroupTemplateObject(templateForm,CMSConstants.UPDATE);
			ICertificateDetailsTransaction transaction = CertificateDetailsImpl.getInstance();
			groupTemplate.setId(Integer.parseInt(templateForm.getTemplateId()));
			CertificateDetails cd=new CertificateDetails();
			cd.setId(templateForm.getCertificateDetailsId());
			groupTemplate.setCertificateId(cd);
			groupTemplate.setIsActive(true);
			transaction.saveGroupTemplate(groupTemplate);
			log.debug("Leaving createTemplate ");
			return true;
		}
		
		/** 
		 * @param templateForm
		 * @return this will get invoked when actually update of template required.
		 * @throws Exception
		 */
		public boolean deleteGroupTemplate(int id) throws Exception {
			CertificateDetailsTemplate groupTemplate = new CertificateDetailsTemplate();
			ICertificateDetailsTransaction transaction = CertificateDetailsImpl.getInstance();
			groupTemplate.setId(id);
			transaction.deleteGroupTemplate(groupTemplate);
			
			log.debug("Leaving createTemplate ");
			return true;
		}
		
		public List<CertificateDetailsTemplate> checkDuplicateCertificateTemplate(String certName) throws Exception {
			ICertificateDetailsTransaction transaction = CertificateDetailsImpl.getInstance();
			log.debug("Leaving getTemplateList ");
			
			return transaction.checkDuplicateCertificateTemplate(certName);
		}
		public List<CertificateDetailsTemplate> getDuplicateCheckList(String templateName) throws Exception {
			ICertificateDetailsTransaction transaction = CertificateDetailsImpl.getInstance();
			log.debug("Leaving getTemplateList ");
			
			return transaction.getDuplicateCheckList(templateName);
		}
		public boolean saveGroupTemplate(CertificateDetailsForm templateForm) throws Exception {
			CertificateDetailsTemplate groupTemplate = CertificateDetailsHelpers.getInstance().getGroupTemplateObject(templateForm,CMSConstants.ADD);
			transaction.saveGroupTemplate(groupTemplate);
			log.debug("Leaving createTemplate ");
			return true;
		}
	
}
