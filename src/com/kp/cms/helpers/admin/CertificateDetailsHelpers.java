package com.kp.cms.helpers.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AssignCertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateDetailsRoles;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.admin.TemplateForm;
import com.kp.cms.to.admin.CertificateDetailsTemplateTO;
import com.kp.cms.to.admin.CertificateDetailsTo;
import com.kp.cms.to.admin.CertificatePurposeTO;
import com.kp.cms.to.admin.GroupTemplateTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.transactions.admin.ICertificateDetailsTransaction;
import com.kp.cms.transactionsimpl.admin.CertificateDetailsImpl;

public class CertificateDetailsHelpers
{

	private static final Log log=LogFactory.getLog(CertificateDetailsHelpers.class);
	public static volatile CertificateDetailsHelpers certificateDetailsHelpers = null;
   
    private CertificateDetailsHelpers(){
    	
    }
    public static CertificateDetailsHelpers getInstance()
    {
        if(certificateDetailsHelpers == null)
        {
        	certificateDetailsHelpers = new CertificateDetailsHelpers();
            return certificateDetailsHelpers;
        } else
        {
            return certificateDetailsHelpers;
        }
    }
    ICertificateDetailsTransaction transaction = CertificateDetailsImpl.getInstance();
    public List<CertificateDetailsTo> convertBoToTos(List<CertificateDetails> getCertificateList)
    {
        List<CertificateDetailsTo> certificateList = new ArrayList<CertificateDetailsTo>();
        if(getCertificateList != null)
        {
        	Iterator itr=getCertificateList.iterator();
    		while (itr.hasNext()) {
    			CertificateDetails certificatelist = (CertificateDetails)itr.next();
    			CertificateDetailsTo certificateTo= new CertificateDetailsTo();
    			certificateTo.setId(certificatelist.getId());
    			certificateTo.setCertificateName(certificatelist.getCertificateName());
    			certificateTo.setFees(certificatelist.getFees().toString());
    			certificateTo.setDescription(certificatelist.getDescription());
    			if(certificatelist.getMarksCard()){
    			certificateTo.setMarksCard("Yes");
    			}else{
    				certificateTo.setMarksCard("No");
    			}
    			if(certificatelist.getIsReasonRequired()){
        			certificateTo.setIsReasonRequired("Yes");
        			}else{
        				certificateTo.setIsReasonRequired("No");
        			}
    			if(certificatelist.getIsIdCard()){
        			certificateTo.setIsIdCard("Yes");
        			}else{
        				certificateTo.setIsIdCard("No");
        			}
    		
    			
    			certificateList.add(certificateTo);
            }
        }
        return certificateList;
}

    public CertificateDetails convertFormToBos(CertificateDetailsForm certificateDetailsForm)
    {
    	CertificateDetails certificateDetails = new CertificateDetails();
    	certificateDetails.setCertificateName(certificateDetailsForm.getCertificateName());
    	certificateDetails.setFees(new BigDecimal(certificateDetailsForm.getFees()));
    	certificateDetails.setMarksCard(certificateDetailsForm.getMarksCard().equalsIgnoreCase("Yes")? true:false);
    	certificateDetails.setIsReasonRequired(certificateDetailsForm.getIsReasonRequired().equalsIgnoreCase("Yes")? true:false);
    	certificateDetails.setIsIdCard(certificateDetailsForm.getIsIdCard().equalsIgnoreCase("Yes")? true:false);
    	certificateDetails.setCreatedby(certificateDetailsForm.getUserId());
    	certificateDetails.setCreatedDate(new Date());
    	certificateDetails.setLastModifiedDate(new Date());
    	certificateDetails.setModifiedBy(certificateDetailsForm.getUserId());
    	certificateDetails.setIsActive(Boolean.valueOf(true));
    	certificateDetails.setDescription(certificateDetailsForm.getDescription());
        return certificateDetails;
    }

    public void setDataBoToForm(CertificateDetailsForm certificateDetailsForm, CertificateDetails certificateDetails)
    {
        if(certificateDetails != null)
        {
            certificateDetailsForm.setCertificateName(certificateDetails.getCertificateName());
            if(certificateDetails.getMarksCard())
            {
            	certificateDetailsForm.setMarksCard("Yes");
            }else
            {
            	certificateDetailsForm.setMarksCard("No");
            }
            if(certificateDetails.getIsReasonRequired())
            {
            	certificateDetailsForm.setIsReasonRequired("Yes");
            }
            else
            {
            	certificateDetailsForm.setIsReasonRequired("No");
            }
            if(certificateDetails.getIsIdCard())
            {
            	certificateDetailsForm.setIsIdCard("Yes");
            }
            else
            {
            	certificateDetailsForm.setIsIdCard("No");
            }
            
           certificateDetailsForm.setFees(certificateDetails.getFees().toString());

           certificateDetailsForm.setDescription(certificateDetails.getDescription());
        }
        
    }

    public CertificateDetails setFormToBo(CertificateDetails certificateDetails, CertificateDetailsForm certificateDetailsForm)
    {
    	CertificateDetails certificateDetail = new CertificateDetails();
    	certificateDetail.setCertificateName(certificateDetailsForm.getCertificateName());
    	certificateDetail.setFees(new BigDecimal(certificateDetailsForm.getFees()));
    	certificateDetail.setMarksCard(certificateDetailsForm.getMarksCard().equalsIgnoreCase("Yes")? true:false);
    	certificateDetail.setId(certificateDetailsForm.getId());
    	certificateDetail.setLastModifiedDate(new Date());
    	certificateDetail.setModifiedBy(certificateDetailsForm.getUserId());
    	certificateDetail.setIsActive(Boolean.valueOf(true));
    	certificateDetail.setIsReasonRequired(certificateDetailsForm.getIsReasonRequired().equalsIgnoreCase("Yes")? true:false);
    	certificateDetail.setIsIdCard(certificateDetailsForm.getIsIdCard().equalsIgnoreCase("Yes")? true:false);
    	certificateDetail.setDescription(certificateDetailsForm.getDescription());
        return certificateDetail;
    }

	  /**
	 * @param certificateDetailsForm
	 * @return
	 */
	public List<CertificateDetailsRoles> saveFormToBosRoles(CertificateDetailsForm certificateDetailsForm)
	    {
		  List<CertificateDetailsRoles> list = new ArrayList<CertificateDetailsRoles>();
		  Iterator<RolesTO> itr=certificateDetailsForm.getAssignToRolesList().iterator();
		  while (itr.hasNext()) {
			RolesTO rolesTO = (RolesTO) itr.next();
			CertificateDetailsRoles certificateDetailsRoles = new CertificateDetailsRoles();
			CertificateDetails certificateDetails = new CertificateDetails();
			certificateDetails.setId(certificateDetailsForm.getCertificateDetailsId());
			certificateDetailsRoles.setCertificateId(certificateDetails);
			if(rolesTO.isChecked()){
				   Roles roles = new Roles();
				   roles.setId(rolesTO.getId());
				   certificateDetailsRoles.setCertificateRolesId(roles);
				   certificateDetailsRoles.setCreatedby(certificateDetailsForm.getUserId());
				   certificateDetailsRoles.setCreatedDate(new Date());
				   certificateDetailsRoles.setLastModifiedDate(new Date());
				   certificateDetailsRoles.setModifiedBy(certificateDetailsForm.getUserId());
				   certificateDetailsRoles.setIsActive(Boolean.valueOf(true));
				   certificateDetailsRoles.setDescription(certificateDetailsForm.getDescription());
				   list.add(certificateDetailsRoles);
				 } else if(!rolesTO.isChecked() && rolesTO.isTempChecked()){
					  certificateDetailsRoles.setId(rolesTO.getCertificateDetailsRolesId());
					   Roles roles = new Roles();
					   roles.setId(rolesTO.getId());
					   certificateDetailsRoles.setCertificateRolesId(roles);
					   certificateDetailsRoles.setLastModifiedDate(new Date());
					   certificateDetailsRoles.setModifiedBy(certificateDetailsForm.getUserId());
					   certificateDetailsRoles.setIsActive(Boolean.valueOf(false));
					   certificateDetailsRoles.setDescription(certificateDetailsForm.getDescription());
					   list.add(certificateDetailsRoles);
				 }
			 
		}		
	     return list;
	    }
	
    /**
	 * @param certificateDetailsForm
	 * @return
	*/
	public List<AssignCertificateRequestPurpose> saveFormToBosPurpose(CertificateDetailsForm certificateDetailsForm)
	    {
		  List<AssignCertificateRequestPurpose> list = new ArrayList<AssignCertificateRequestPurpose>();
		  Iterator<CertificatePurposeTO> itr=certificateDetailsForm.getAssignPurposeList().iterator();
		  while (itr.hasNext()) {
			CertificatePurposeTO purposeTO = (CertificatePurposeTO) itr.next();
			AssignCertificateRequestPurpose certificatePurpose = new AssignCertificateRequestPurpose();
			CertificateDetails certificateDetails = new CertificateDetails();
			certificateDetails.setId(certificateDetailsForm.getCertificateDetailsId());
			certificatePurpose.setCertificateId(certificateDetails);
			if(purposeTO.isChecked()){
				   CertificateRequestPurpose pur = new CertificateRequestPurpose();
				   pur.setId(purposeTO.getId());
				   certificatePurpose.setCertificatePurposeId(pur);
				   certificatePurpose.setCreatedBy(certificateDetailsForm.getUserId());
				   certificatePurpose.setCreatedDate(new Date());
				   certificatePurpose.setLastModifiedDate(new Date());
				   certificatePurpose.setModifiedBy(certificateDetailsForm.getUserId());
				   certificatePurpose.setIsActive(Boolean.valueOf(true));
				   list.add(certificatePurpose);
				 } else if(!purposeTO.isChecked() && purposeTO.isTempChecked()){
					 certificatePurpose.setId(purposeTO.getCertificateIdPurpose());
					 CertificateRequestPurpose pur = new CertificateRequestPurpose();
					   pur.setId(purposeTO.getId());
					   certificatePurpose.setCertificatePurposeId(pur);
					   certificatePurpose.setLastModifiedDate(new Date());
					   certificatePurpose.setModifiedBy(certificateDetailsForm.getUserId());
					   certificatePurpose.setIsActive(Boolean.valueOf(false));
					   list.add(certificatePurpose);
				 }
		}		
	     return list;
	    }
	
	public CertificateDetailsTemplate saveFormToBosTemplate(CertificateDetailsForm certificateDetailsForm)
    {
		
		CertificateDetailsTemplate certificateTemplate = new CertificateDetailsTemplate();
		CertificateDetails certificateDetails = new CertificateDetails();
		certificateDetails.setId(certificateDetailsForm.getCertificateDetailsId());
		certificateTemplate.setCertificateId(certificateDetails);
	    certificateTemplate.setTemplateDescription(certificateDetailsForm.getTemplateDescription());
	    certificateTemplate.setTemplateName(certificateDetailsForm.getSelectedCertificateName());
	    certificateTemplate.setCreatedby(certificateDetailsForm.getUserId());
	    certificateTemplate.setCreatedDate(new Date());
		certificateTemplate.setLastModifiedDate(new Date());
		certificateTemplate.setModifiedBy(certificateDetailsForm.getUserId());
		certificateTemplate.setIsActive(Boolean.valueOf(true));
     return certificateTemplate;
    }
	
	
	
   public  List<RolesTO> setAssignRoles(List<Roles> roles, Map<Integer,Integer> rolesMap) {
	   List<RolesTO> list =new ArrayList<RolesTO>();
	   if(roles!=null && !roles.toString().isEmpty()){
		   Iterator<Roles> iterator = roles.iterator();
		   while (iterator.hasNext()) {
			Roles roles2 = (Roles) iterator.next();
			RolesTO rolesTO = new RolesTO();
			if(rolesMap.containsKey(roles2.getId())){
				
				rolesTO.setId(roles2.getId());
    			rolesTO.setName(roles2.getName());
    			rolesTO.setTempChecked(true);
    			rolesTO.setCertificateDetailsRolesId(rolesMap.get(roles2.getId()));
    			list.add(rolesTO);
			}else{
				rolesTO.setId(roles2.getId());
    			rolesTO.setName(roles2.getName());
    			list.add(rolesTO);
			}
		}
	   }
		return list;
	}
   
   public  List<CertificatePurposeTO> setAssignPurpose(List<CertificateRequestPurpose> purposeList, Map<Integer,Integer> purposeMap) {
	   List<CertificatePurposeTO> list =new ArrayList<CertificatePurposeTO>();
	   if(purposeList!=null && !purposeList.toString().isEmpty()){
		   Iterator<CertificateRequestPurpose> iterator = purposeList.iterator();
		   while (iterator.hasNext()) {
			CertificateRequestPurpose purposeDb = (CertificateRequestPurpose) iterator.next();
			CertificatePurposeTO purposeTO = new CertificatePurposeTO();
			if(purposeMap.containsKey(purposeDb.getId())){
				
				purposeTO.setId(purposeDb.getId());
				purposeTO.setPurposeName(purposeDb.getPurposeName());
				purposeTO.setTempChecked(true);
				purposeTO.setCertificateIdPurpose(purposeMap.get(purposeDb.getId()));
    			list.add(purposeTO);
			}else{
				purposeTO.setId(purposeDb.getId());
				purposeTO.setPurposeName(purposeDb.getPurposeName());
    			list.add(purposeTO);
			}
		}
	   }
		return list;
	}
   
   public List<CertificateDetailsTemplateTO> convertBOtoTO(List<CertificateDetailsTemplate> groupTemplateList) throws Exception {
		log.debug("entering the getTemplateObject");
		
		List<CertificateDetailsTemplateTO> groupTemplateTOList = new ArrayList<CertificateDetailsTemplateTO>();
		Iterator<CertificateDetailsTemplate> groupTemplateItr = groupTemplateList.iterator();
		
		while (groupTemplateItr.hasNext()) {
			CertificateDetailsTemplate groupTemplate = (CertificateDetailsTemplate) groupTemplateItr.next();
			
			CertificateDetailsTemplateTO groupTemplateTO = new CertificateDetailsTemplateTO();
			groupTemplateTO.setId(groupTemplate.getId());
			groupTemplateTO.setCertificateId(groupTemplate.getCertificateId());
			groupTemplateTO.setTemplateName(groupTemplate.getTemplateName());
			groupTemplateTO.setTemplateDescription(groupTemplate.getTemplateDescription());
			
			groupTemplateTOList.add(groupTemplateTO);
		}
		log.debug("leaving the getTemplateObject");
		return groupTemplateTOList;
	}

		public CertificateDetailsTemplate getGroupTemplateObject(CertificateDetailsForm templateForm,String mode) throws Exception {
			log.debug("entering the getTemplateObject");
			CertificateDetailsTemplate groupTemplate = new CertificateDetailsTemplate();
			groupTemplate.setTemplateName(templateForm.getTemplateName());
			groupTemplate.setTemplateDescription(templateForm.getTemplateDescription());
			if(mode.equalsIgnoreCase(CMSConstants.ADD)) {
				groupTemplate.setCreatedby(templateForm.getUserId());
				groupTemplate.setCreatedDate(new Date());
			}
			groupTemplate.setLastModifiedDate(new Date());
			groupTemplate.setModifiedBy(templateForm.getUserId());
			log.debug("leaving the getTemplateObject");
			return groupTemplate;
		} 
}
   
   

