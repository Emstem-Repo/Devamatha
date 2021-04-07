package com.kp.cms.handlers.applicationform;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.applicationform.ProfessorPGIDetails;
import com.kp.cms.bo.applicationform.ProfessorPersonalData;
import com.kp.cms.bo.applicationform.ProfessorQualificationBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.applicationform.ApplicationRegistrationForm;
import com.kp.cms.helpers.applicationform.ApplicationRegistrationHelper;
import com.kp.cms.to.applicationform.ProfessorEducationDetailsTO;
import com.kp.cms.to.applicationform.ProfessorPersonalDataTO;
import com.kp.cms.to.applicationform.ProfessorQualificationTO;
import com.kp.cms.transactions.applicationform.IApplicationRegistrationTransaction;
import com.kp.cms.transactions.usermanagement.IStudentLoginTransaction;
import com.kp.cms.transactionsimpl.applicationform.ApplicationRegistrationHandlerTxnImp;
import com.kp.cms.transactionsimpl.usermanagement.StudentLoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.sun.org.apache.xml.internal.resolver.readers.XCatalogReader;


public class ApplicationRegistrationHandler {
	
	private static volatile ApplicationRegistrationHandler applicationRegistrationHandler = null;
	private static final Log log = LogFactory.getLog(ApplicationRegistrationHandler.class);
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static ApplicationRegistrationHandler getInstance() {
		if (applicationRegistrationHandler == null) {
			applicationRegistrationHandler = new ApplicationRegistrationHandler();
		}
		return applicationRegistrationHandler;
	}
	public List<ProfessorEducationDetailsTO> getQualificationDetails(List<ProfessorEducationDetailsTO> educationalDetails) throws Exception{
		IApplicationRegistrationTransaction txn=ApplicationRegistrationHandlerTxnImp.getInstance();
		List<ProfessorQualificationBO> quaList=txn.getDocumentList();
		List<ProfessorEducationDetailsTO> toQuaList=ApplicationRegistrationHelper.getInstance().convertQualDetailsBoToTo(quaList);
		
		return toQuaList;

	}
	public boolean storeProfessorDetails(ApplicationRegistrationForm registrationForm) throws Exception{
		IApplicationRegistrationTransaction txn=ApplicationRegistrationHandlerTxnImp.getInstance();
		ProfessorPersonalData toQuaList=ApplicationRegistrationHelper.getInstance().convertTOToBO(registrationForm);
		
		boolean saveData=txn.saveProfesserInformation(toQuaList);
		
		return saveData;
	}
	public ProfessorPersonalData getApplicantObject(ApplicationRegistrationForm registrationForm) throws Exception{
		IApplicationRegistrationTransaction txn=ApplicationRegistrationHandlerTxnImp.getInstance();
		ProfessorPersonalData professerObject=txn.getApplicationDetails(registrationForm);
		return professerObject;
	}
	public String getParameterForPGIApplication(ApplicationRegistrationForm registrationForm,ProfessorPersonalData personalData) throws Exception{
		
		IApplicationRegistrationTransaction txn=ApplicationRegistrationHandlerTxnImp.getInstance();
		ProfessorPGIDetails bo=new ProfessorPGIDetails();
		
		bo.setFirstName(personalData.getFirstName());
		bo.setDateOfBirth(personalData.getDateOfBirth());
		if(personalData.getDepartmentId()!=null){
		Department dep=new Department();
		dep.setId(personalData.getDepartmentId().getId());
		bo.setDepartmentId(dep);
		}else{
			bo.setDepartmentId(null);	
		}
		bo.setEmail(personalData.getEmailId());
		bo.setCreatedDate(new Date());
		bo.setCreatedBy(registrationForm.getUserId());
		bo.setMobileNo(personalData.getMobileNo());
		bo.setTxnAmount(new BigDecimal(CMSConstants.PROFESSOR_AMOUNT));
		registrationForm.setAmount(CMSConstants.PROFESSOR_AMOUNT);
		bo.setTxnStatus("Pending");
		String applicantRefNo=txn.generateApplicantRefNoOnline(bo,registrationForm);
		
		StringBuilder temp=new StringBuilder();
		String productinfo="productinfo";
		registrationForm.setRefNo(applicantRefNo);
		registrationForm.setProductinfo(productinfo);
		
		if(applicantRefNo!=null && !applicantRefNo.isEmpty()){
			temp.append(CMSConstants.PGI_MERCHANT_ID_FEDERAL)
			.append("|").append(CMSConstants.PGI_SECURITY_ID_FEDERAL)
			.append("|").append(CMSConstants.PGI_MERCHANT_ID_FEDERAL+applicantRefNo)
			.append("|").append(Double.parseDouble(registrationForm.getAmount())*100)
			.append("|").append('A')
			.append("|").append(CMSConstants.PGI_CHECKSUM_KEY_FEDERAL);
			
	    }
		
		String hash=sha1("SHA-1",temp.toString());
		registrationForm.setTest(temp.toString());
		
		return hash;
	}
	public String sha1(String type, String str) throws Exception{
		
		
		java.security.MessageDigest digest = null;
		String myString =str;
		  try {
			  
		 
		  digest = java.security.MessageDigest.getInstance("SHA-1");

		  digest.reset();
		  
		  digest.update(myString.getBytes("UTF-8"));
		  
		  } catch (Exception e) {
			System.out.println("Exception:"+e);
		  }
		 // System.out.println( new String(Base64.encodeBase64(digest.digest())));
		return new String(Base64.encodeBase64(digest.digest()));



}
	public boolean updateResponse(ApplicationRegistrationForm registrationForm,ProfessorPersonalData personalData) throws Exception{
		boolean isUpdated=false;
		IApplicationRegistrationTransaction txn=ApplicationRegistrationHandlerTxnImp.getInstance();
		ProfessorPGIDetails bo=ApplicationRegistrationHelper.getInstance().convertPGITOtoBO(registrationForm);
		
		isUpdated=txn.updateReceivedStatusProfessort(bo,registrationForm,personalData);
		
		
		return isUpdated;
	}
	public void printData(ApplicationRegistrationForm registrationForm,ProfessorPersonalData printData) throws Exception{
		ApplicationRegistrationHelper.getInstance().convertPrintBOtoTO(registrationForm,printData);
		return;
	}

}
