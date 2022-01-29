package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.forms.admin.OrganizationForm;
import com.kp.cms.helpers.admin.OrganizationHelper;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.transactions.admin.IOrganizationTransaction;
import com.kp.cms.transactionsimpl.admin.OrganizationTransactionImpl;

public class OrganizationHandler {
	
private static final Log log = LogFactory.getLog(OrganizationHandler.class);
	
	private static volatile OrganizationHandler organizationHandler = null;

	private OrganizationHandler() {
	}
	/**
	 * 
	 * @returns a single instance when called
	 */
	public static OrganizationHandler getInstance() {
		if (organizationHandler == null) {
			organizationHandler = new OrganizationHandler();
		}
		return organizationHandler;
	}
	
	IOrganizationTransaction transaction=new OrganizationTransactionImpl();
	
	/**
	 * 
	 * @param organizationForm
	 * @param request 
	 * @return
	 * Used in inserting an organization details
	 * Capture the formbean properties and populates to TO properties
	 */
	public boolean saveOrganizationDetails(OrganizationForm organizationForm, HttpServletRequest request)throws Exception
	{
			OrganizationTO organizationTO=new OrganizationTO();
			if(organizationForm!=null){
					organizationTO.setOrganizationName(organizationForm.getOrganizationName());
					organizationTO.setNeedApproval(organizationForm.isNeedApproval());
					organizationTO.setAddressLine1(organizationForm.getAddressLine1());
					organizationTO.setAddressLine2(organizationForm.getAddressLine2()!=null ? organizationForm.getAddressLine2():null);
					organizationTO.setAddressLine3(organizationForm.getAddressLine3()!=null ? organizationForm.getAddressLine3():null);
					organizationTO.setCreatedBy(organizationForm.getUserId());
					organizationTO.setModifiedBy(organizationForm.getUserId());
					organizationTO.setChangePassword(organizationForm.isChangePassword());
					organizationTO.setSameUseridPassword(organizationForm.isSameUseridPassword());
					organizationTO.setFinalMeritListApproval(organizationForm.isFinalMeritListApproval());
					if(organizationForm.getOrganizationLogo()!=null){
						organizationTO.setOrganizationLogo(organizationForm.getOrganizationLogo());
					}
					organizationTO.setOrganizationtopBar(organizationForm.getOrganizationtopBar()!=null?organizationForm.getOrganizationtopBar():null);
					if(organizationForm.getOrganizationLogo1()!=null){
						organizationTO.setOrganizationLogo1(organizationForm.getOrganizationLogo1());
					}
					organizationTO.setOrganizationtopBar1(organizationForm.getOrganizationtopBar1()!=null?organizationForm.getOrganizationtopBar1():null);
					if(organizationForm.getTimeLimit() != null && !organizationForm.getTimeLimit().trim().isEmpty()){
						organizationTO.setTimeLimit(Integer.parseInt(organizationForm.getTimeLimit()));
					}
					organizationTO.setOpenHonoursCourseLink(organizationForm.isOpenHonoursCourseLink());
					organizationTO.setConvocationRegistration(organizationForm.isConvocationRegistration());
					organizationTO.setStudentPhotoUpload(organizationForm.isStudentPhotoUpload());
					/**
					 * Send the TO object to helper and populate to BO
					 */
					Organisation organisation=OrganizationHelper.getInstance().polupateTOtoBOSave(organizationTO,request);
					/**
					 * Finally send the Bo object to transactionimpl class for insert
					 */
					if(organisation!=null){
						return transaction.saveOrganizationDetails(organisation);
					}
			}
			log.info("Leaving handler saveOrganizationDetails");
			return false;
	}
	
	/**
	 * Used in case of getting all organization details from DB
	 */

	public List<OrganizationTO> getOrganizationDetails()throws Exception{
		if(transaction!=null)
		{
			List<Organisation> organizationDetails=transaction.getOrganizationDetails();
			if(organizationDetails!=null && !organizationDetails.isEmpty()){
				List<OrganizationTO>organizationList=OrganizationHelper.getInstance().populateBOtoTO(organizationDetails);
				if(organizationList!=null && !organizationList.isEmpty()){
					return organizationList;
					
				}
			}
		}
		log.info("Leaving handler getOrganizationDetails");
		return null;
	}	
	
	/**
	 * Deletes a organization based on the id
	 */
	
	public boolean deleteOrganizationDetails(int orgId, String userId)throws Exception
	{
		Organisation organisation=new Organisation();
		organisation.setId(orgId);
		organisation.setIsActive(false);
		organisation.setLastModifiedDate(new Date());
		organisation.setModifiedBy(userId);
		log.info("Leaving handler getOrganizationDetails");
		return transaction.deleteOrganizationDetails(organisation);
	}
	
	/**
	 * Used in downloading the file
	 */	
	public Organisation getRequiredFile(int orgId)throws Exception{
		Organisation orgBO=null;	
		if (transaction != null) {
			orgBO = transaction.getRequiredFile(orgId);
		}
		log.info("Leaving from handler of getRequiredFile of Organization handler");
		return orgBO;
	}	
	/**
	 * Downloading a file
	 */
	
	public Organisation getRequiredFile()throws Exception{
		Organisation orgBO=null;
		if (transaction != null) {
			orgBO = transaction.getRequiredFile();
		}
		log.info("Leaving from handler of getRequiredFile");
		return orgBO;
	}
	/**
	 * Used to edit OrganizationDetails
	 * Gets Organization details By Organization Id
	 * 
	 */
	public void getOrganizationDetailsById(OrganizationForm organizationForm)throws Exception{
		Organisation organisation = null;
		if(transaction != null){
			organisation = transaction.getRequiredFile(organizationForm.getId());
			if(organisation!=null){
				OrganizationHelper.getInstance().setFromBOToFormWhileEdit(organisation, organizationForm);
			}
		}		
	}
	/**
	 * Used to update Organization Details
	 * Constructs a TO object by capturing the input data from formbean.
	 * @param request 
	 */
	public boolean updateOrganizationDetails(OrganizationForm organizationForm, HttpServletRequest request)throws Exception{
		//Gets the old BO object from BO (Stored while edit button is clicked)
		Organisation oldBO = organizationForm.getOrganisation();		
		OrganizationTO newOrganizationTO = new OrganizationTO();
		newOrganizationTO.setId(organizationForm.getId());
		newOrganizationTO.setOrganizationName(organizationForm.getOrganizationName()!=null ? organizationForm.getOrganizationName():null);
		newOrganizationTO.setNeedApproval(organizationForm.isNeedApproval());
		newOrganizationTO.setSameUseridPassword(organizationForm.isSameUseridPassword());
		newOrganizationTO.setFinalMeritListApproval(organizationForm.isFinalMeritListApproval());
		if(newOrganizationTO.getSameUseridPassword()){
		newOrganizationTO.setChangePassword(false);
		}
		else{
			newOrganizationTO.setChangePassword(organizationForm.isChangePassword());
		}
		newOrganizationTO.setAddressLine1(organizationForm.getAddressLine1()!=null ? organizationForm.getAddressLine1():null);
		newOrganizationTO.setAddressLine2(organizationForm.getAddressLine2()!=null ? organizationForm.getAddressLine2():null);
		newOrganizationTO.setAddressLine3(organizationForm.getAddressLine3()!=null ? organizationForm.getAddressLine3():null);
		newOrganizationTO.setModifiedBy(organizationForm.getUserId());
		newOrganizationTO.setOpenHonoursCourseLink(organizationForm.isOpenHonoursCourseLink());
		//If the user selected a new Logo then capture from form and store in TO
		if(organizationForm.getOrganizationLogo()!=null && organizationForm.getOrganizationLogo().getFileData()!=null
		&& organizationForm.getOrganizationLogo().getFileName()!=null && !organizationForm.getOrganizationLogo().getFileName().isEmpty() &&
		organizationForm.getOrganizationLogo().getContentType()!=null && !organizationForm.getOrganizationLogo().getContentType().isEmpty()){
			newOrganizationTO.setOrganizationLogo(organizationForm.getOrganizationLogo());			
		}
		//If the user selected a new ToBar then capture from form and store in TO
		if(organizationForm.getOrganizationtopBar()!=null && organizationForm.getOrganizationtopBar().getFileData()!=null 
		&& organizationForm.getOrganizationtopBar().getFileName()!=null && !organizationForm.getOrganizationtopBar().getFileName().isEmpty() &&
		organizationForm.getOrganizationtopBar().getContentType()!=null && !organizationForm.getOrganizationtopBar().getContentType().isEmpty()){
			newOrganizationTO.setOrganizationtopBar(organizationForm.getOrganizationtopBar());
		}
		if(organizationForm.getOrganizationLogo1()!=null && organizationForm.getOrganizationLogo1().getFileData()!=null
		&& organizationForm.getOrganizationLogo1().getFileName()!=null && !organizationForm.getOrganizationLogo1().getFileName().isEmpty() &&
		organizationForm.getOrganizationLogo1().getContentType()!=null && !organizationForm.getOrganizationLogo1().getContentType().isEmpty()){
			newOrganizationTO.setOrganizationLogo1(organizationForm.getOrganizationLogo1());			
		}   
		//If the user selected a new ToBar then capture from form and store in TO
		if(organizationForm.getOrganizationtopBar1()!=null && organizationForm.getOrganizationtopBar1().getFileData()!=null 
		&& organizationForm.getOrganizationtopBar1().getFileName()!=null && !organizationForm.getOrganizationtopBar1().getFileName().isEmpty() &&
		organizationForm.getOrganizationtopBar1().getContentType()!=null && !organizationForm.getOrganizationtopBar1().getContentType().isEmpty()){
			newOrganizationTO.setOrganizationtopBar1(organizationForm.getOrganizationtopBar1());
		}
		newOrganizationTO.setConvocationRegistration(organizationForm.isConvocationRegistration());
		newOrganizationTO.setStudentPhotoUpload(organizationForm.isStudentPhotoUpload());
		//Send the TO object to helper class in order to construct a BO object for update
		Organisation newOrganizationBO = OrganizationHelper.getInstance().populateTOToBOWhileUpdate(newOrganizationTO, oldBO,request);
		if(newOrganizationBO!=null)
			//raghu
			if(organizationForm.getTimeLimit()!=null && !organizationForm.getTimeLimit().equalsIgnoreCase(""))
		      newOrganizationBO.setTimeLimit(Integer.parseInt(organizationForm.getTimeLimit()));
		if(transaction != null && newOrganizationBO != null){
			return transaction.updateOrganizationDetails(newOrganizationBO);
		}
		return false;
	}
}
