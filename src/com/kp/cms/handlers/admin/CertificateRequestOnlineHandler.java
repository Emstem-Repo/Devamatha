package com.kp.cms.handlers.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateOnlineStudentRequest;
import com.kp.cms.bo.admin.CertificateRequestMarksCardDetails;
import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateRequestPurposeDetails;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.CertificateRequestOnlineForm;
import com.kp.cms.forms.admin.CommonTemplateForm;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.handlers.admission.ApplicationStatusUpdateHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.admin.CertificateDetailsHelpers;
import com.kp.cms.helpers.admin.CertificateRequestOnlineHelper;
import com.kp.cms.helpers.admin.CommonTemplateHelper;
import com.kp.cms.helpers.admission.StudentEditHelper;
import com.kp.cms.to.admin.AssignCertificateRequestPurposeTO;
import com.kp.cms.to.admin.CertificateDetailsTemplateTO;
import com.kp.cms.to.admin.CertificateDetailsTo;
import com.kp.cms.to.admin.CertificateRequestMarksCardTO;
import com.kp.cms.to.admin.CertificateRequestOnlineTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admin.ICertificateRequestOnlineTransaction;
import com.kp.cms.transactions.admin.ICommonTemplateTransaction;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.admission.IStudentEditTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.admin.CertificateRequestOnlineImpl;
import com.kp.cms.transactionsimpl.admin.CommonTemplateTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.admission.StudentEditTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.OnlinePaymentUtils;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.print.HtmlPrinter;
import common.Logger;

/**
 * @author mary
 *
 */
public class CertificateRequestOnlineHandler {
	
	 private static final Logger log = Logger.getLogger(CertificateRequestOnlineHandler.class);
	 
	 private static volatile CertificateRequestOnlineHandler handler = null;
	    ICertificateRequestOnlineTransaction txn;
	    CertificateRequestOnlineHelper helper= CertificateRequestOnlineHelper.getInstance(); 
	    
	    private CertificateRequestOnlineHandler(){
	    	txn = new CertificateRequestOnlineImpl();}

	    public static CertificateRequestOnlineHandler getInstance()
	    {
	        if(handler == null){
	        	handler = new CertificateRequestOnlineHandler();
	        	}
	        return handler;
	    }

	    
	    
	    public List<CertificateDetailsTo> getCertificateDetails()throws Exception {
	    	List<CertificateDetails> certList=txn.getCertificateDetails();
	    	List<CertificateDetailsTo> certToList = helper.convertCertificateTOtoBO(certList);
	    	log.info("exit getSearchedStudents");
	    	return certToList;
	    	
	    }
	    /**
		 * FETCHES LIST OF STUDENTS
		 * 
		 * @param stForm
		 */
		public List<CertificateRequestOnlineTO> getSearchedStudents(CertificateRequestOnlineForm stForm)
				throws Exception {
					
			StringBuffer query = txn.getSerchedStudentsQuery( stForm);
			List<CertificateOnlineStudentRequest> studentlist=txn.getSerchedStudentsCertificateApplication(query);
			List<CertificateRequestOnlineTO> studenttoList = helper.convertStudentTOtoBO(studentlist);
			log.info("exit getSearchedStudents");
			return studenttoList;
		} 
	    
		public boolean saveSupplementaryApplicationForStudentLogin( CertificateRequestOnlineForm crForm) throws Exception {
			
			OnlinePaymentReciepts onlinePaymentReciepts=new OnlinePaymentReciepts();
			PcFinancialYear pcFinancialYear=new PcFinancialYear();
			pcFinancialYear.setId(crForm.getFinId());
			onlinePaymentReciepts.setPcFinancialYear(pcFinancialYear);
			Student student = new Student();
			student.setId(crForm.getStudentId());
			onlinePaymentReciepts.setStudent(student);
			onlinePaymentReciepts.setTotalAmount(new BigDecimal(crForm.getTotalFees()));
			onlinePaymentReciepts.setApplicationFor("Certificate Request");
			onlinePaymentReciepts.setCreatedBy(crForm.getUserId());
			onlinePaymentReciepts.setCreatedDate(new Date());
			onlinePaymentReciepts.setLastModifiedDate(new Date());
			onlinePaymentReciepts.setModifiedBy(crForm.getUserId());
			onlinePaymentReciepts.setTransactionDate(new Date());
			onlinePaymentReciepts.setIsActive(true);
			PropertyUtil.getInstance().save(onlinePaymentReciepts);
			boolean isPaymentSuccess=false;
			if(onlinePaymentReciepts.getId()>0){
				crForm.setOnlinePaymentId(onlinePaymentReciepts.getId());
				String registerNo=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(crForm.getStudentId(),"Student",true,"registerNo");
				OnlinePaymentUtils.dedactAmountFromAccount(registerNo,onlinePaymentReciepts.getId(), crForm.getTotalFees(), onlinePaymentReciepts);
				if(!onlinePaymentReciepts.getIsPaymentFailed()/*true*/){
					isPaymentSuccess=true;
					txn.updateAndGenerateRecieptNoOnlinePaymentReciept(onlinePaymentReciepts);
				}else
					crForm.setMsg(onlinePaymentReciepts.getStatus());
					
			}
			if(isPaymentSuccess){
			List<CertificateOnlineStudentRequest> boList=new ArrayList<CertificateOnlineStudentRequest>();
			
			Iterator<CertificateDetailsTo> itr=crForm.getCertificateRequestOnlineTO().getCertificateDetailsTo().iterator();
			while (itr.hasNext()) {
				CertificateDetailsTo to = (CertificateDetailsTo) itr.next();
				CertificateOnlineStudentRequest cert=new CertificateOnlineStudentRequest();
				if(to.isChecked())
				{
				if(to.getId()>0){
					CertificateDetails cc=new CertificateDetails();
					cc.setId(to.getId());
					cert.setCertificateDetailsId(cc);
				}
				if(to.getMarksCard().equalsIgnoreCase("true"))
				{
				Set<CertificateRequestMarksCardDetails> marksCardDetails= getMarksCardObjects(to,crForm);
				cert.setMarksCardReq(marksCardDetails);
				}
				if(to.getAssignPurposeTo()!=null && !to.getAssignPurposeTo().isEmpty())
				{
				Set<CertificateRequestPurposeDetails> certificatPurposeDetails= getPurposeDetails(to,crForm);
				cert.setCertReqPurpose(certificatPurposeDetails);
				}
				if(to.getIsReasonRequired().equalsIgnoreCase("true"))
				{
					if(to.getStudentRemarks()!=null && !to.getStudentRemarks().isEmpty())
					cert.setStudentRemarks(to.getStudentRemarks());
				}
				cert.setIsCompleted(false);
				cert.setIsIssued(false);
				cert.setIsRejected(false);
				cert.setStudentId(student);
				cert.setCreatedBy(crForm.getUserId());
				cert.setCreatedDate(new Date());
				cert.setModifiedBy(crForm.getUserId());
				cert.setLastModifiedDate(new Date());
				cert.setAppliedDate(new Date());
				cert.setIsActive(true);
				
				boList.add(cert);
				}
			}
			
			return txn.saveCertificate(boList);
			}else
				return false;
		}
		

		/**
		 * @param smartCardNo
		 * @param studentId
		 * @return
		 * @throws Exception
		 */
		public boolean verifySmartCard(String smartCardNo, int studentId) throws Exception {
			String query="select s.smartCardNo from Student s where s.id="+studentId+" and s.smartCardNo like '%"+smartCardNo+"'";
			INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
			List list=txn.getDataForQuery(query);
			if(list!=null && !list.isEmpty()){
				return true;
			}else{
				return false;
			}
		}

		/**
		 * @param onlinePaymentId
		 * @return
		 * @throws Exception
		 */
		public String getPrintData(int onlinePaymentId,HttpServletRequest request) throws Exception {
			ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
			OnlinePaymentReciepts bo=(OnlinePaymentReciepts) txn.getMasterEntryDataById(OnlinePaymentReciepts.class,onlinePaymentId);
			List<GroupTemplate> list= TemplateHandler.getInstance().getDuplicateCheckList(0,CMSConstants.TEMPLATE_CERTIFICATE_APPLICATION_PRINT);
			String desc ="";
			
			if(bo!=null && list != null && !list.isEmpty()) {
				if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
					desc = list.get(0).getTemplateDescription();
				}
				desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME,bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
				desc=desc.replace(CMSConstants.TEMPLATE_REGISTER_NO,bo.getStudent().getRegisterNo());
				if(bo.getTransactionDate()!=null)
				desc=desc.replace(CMSConstants.TEMPLATE_SMS_DATE,CommonUtil.formatSqlDate1(bo.getTransactionDate().toString()));
				desc=desc.replace(CMSConstants.FEE_RECEIPTNO,bo.getRecieptNo().toString());
				if(bo.getTotalAmount()!=null){
					desc=desc.replace(CMSConstants.TEMPLATE_AMOUNT,String.valueOf(bo.getTotalAmount()));
					desc=desc.replace(CMSConstants.TEMPLATE_AMOUNT_IN_WORDS,CommonUtil.numberToWord1(bo.getTotalAmount().intValue()));
				}
				
				byte[] logo = null;
				byte[] logo1 = null;
				String logoPath = "";
				String logoPath1= "";
				HttpSession session = request.getSession(false);
				Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				if (organisation != null) {
					logo = organisation.getLogo();
					logo1 = organisation.getLogo1();
				}
				if (session != null) {
					session.setAttribute("LogoBytes", logo);
					session.setAttribute("LogoBytes1", logo1);
				}
				
				logoPath = request.getContextPath();
				logoPath = "<img src="
						+ logoPath
						+ "/LogoServlet?count=1 alt='Logo not available' width='210' height='100' >";
				
				logoPath1 = request.getContextPath();
				logoPath1 = "<img src="
						+ logoPath1
						+ "/LogoServlet?count=2 alt='Logo not available' width='210' height='100' >";
				desc = desc.replace(CMSConstants.TEMPLATE_LOGO, logoPath);
				desc = desc.replace(CMSConstants.TEMPLATE_LOGO1, logoPath1);
			}
			return desc;
		}
		
		/**
		 * @param studentId
		 * @param templateName
		 * @throws Exception
		 */
		public void sendSMSToStudent(CertificateRequestOnlineForm crForm,String templateName) throws Exception {
			
			ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
			if(crForm.getStudentToList()!=null && !crForm.getStudentToList().isEmpty()){	
			Iterator<CertificateRequestOnlineTO> itr=crForm.getStudentToList().iterator();
			Map<Integer,Integer> stuMap = new  HashMap<Integer, Integer>();
			while (itr.hasNext()) {
				String mobileNo="";
				String certificateName="";
				String campus="";
				CertificateRequestOnlineTO to = (CertificateRequestOnlineTO) itr.next();
				if(to.getIsCompleted()!=null && to.getIsCompleted())
				{
				int studentId= Integer.parseInt(to.getStudentId());
				Student bo=(Student)txn.getMasterEntryDataById(Student.class,studentId);
				if(crForm.getStudentToList()!=null && !crForm.getStudentToList().isEmpty()){
					
					Iterator<CertificateRequestOnlineTO> itr1=crForm.getStudentToList().iterator();
					while (itr1.hasNext()) {
						CertificateRequestOnlineTO to1 = (CertificateRequestOnlineTO) itr1.next();
						if(Integer.parseInt(to1.getStudentId())==studentId){
							campus=bo.getAdmAppln().getCourse().getWorkLocation().getName();
						if(to1.getIsCompleted()!=null && to1.getIsCompleted()){
						int certId=Integer.parseInt(to1.getCertificateId());
						CertificateDetails cert= (CertificateDetails)txn.getMasterEntryDataById(CertificateDetails.class,certId);
						if(certificateName == null || certificateName.isEmpty())
						{
							certificateName=cert.getCertificateName();
						}
						else if(!certificateName.isEmpty())
						{
							certificateName=certificateName+", "+cert.getCertificateName();
						}
					}}
				}
				
			if(bo!=null){
				
			//	if(bo.getAdmAppln().getCourseBySelectedCourseId().getIsApplicationProcessSms()){
					
					if(bo.getAdmAppln().getPersonalData().getMobileNo1()!=null && !bo.getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
						if(bo.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || bo.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
								|| bo.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || bo.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
							mobileNo = "91";
						else
							mobileNo=bo.getAdmAppln().getPersonalData().getMobileNo1();
					}else{
						mobileNo="91";
					}
					if(bo.getAdmAppln().getPersonalData().getMobileNo2()!=null && !bo.getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
						mobileNo=mobileNo+bo.getAdmAppln().getPersonalData().getMobileNo2();
						//mobileNo="918095960252";
					}
					
				}
			}
				if(!stuMap.containsKey(studentId)){
					if(mobileNo.length()==12){
						sendSMSToStudent(mobileNo,templateName,certificateName,campus);
						}
					
				}
				stuMap.put(studentId, studentId);
		}else if(crForm.getIsReject().equalsIgnoreCase("true"))
		{
			if(crForm.getRejectId().equals(to.getId())){
			int studentId= Integer.parseInt(to.getStudentId());
			Student bo=(Student)txn.getMasterEntryDataById(Student.class,studentId);
			if(crForm.getStudentToList()!=null && !crForm.getStudentToList().isEmpty()){
				
				Iterator<CertificateRequestOnlineTO> itr1=crForm.getStudentToList().iterator();
				while (itr1.hasNext()) {
					CertificateRequestOnlineTO to1 = (CertificateRequestOnlineTO) itr1.next();
					if(Integer.parseInt(to1.getStudentId())==studentId){
						campus=bo.getAdmAppln().getCourse().getWorkLocation().getName();
					if(to1.isRejected()){
					int certId=Integer.parseInt(to1.getCertificateId());
					CertificateDetails cert= (CertificateDetails)txn.getMasterEntryDataById(CertificateDetails.class,certId);
					if(certificateName == null || certificateName.isEmpty()){
						certificateName=cert.getCertificateName();
					}else if(!certificateName.isEmpty()){
						certificateName=certificateName+", "+cert.getCertificateName();
					}
				
					
						}
					}
			}
			
		if(bo!=null){
			
		//	if(bo.getAdmAppln().getCourseBySelectedCourseId().getIsApplicationProcessSms()){
				
				if(bo.getAdmAppln().getPersonalData().getMobileNo1()!=null && !bo.getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
					mobileNo=bo.getAdmAppln().getPersonalData().getMobileNo1();
				}else{
					mobileNo="91";
				}
				if(bo.getAdmAppln().getPersonalData().getMobileNo2()!=null && !bo.getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
					mobileNo=mobileNo+bo.getAdmAppln().getPersonalData().getMobileNo2();
					//mobileNo="918095960252";
				}
				if(mobileNo.length()==12){
				 //   mobileNo="0123456789";
				}
			}
		}
			if(!stuMap.containsKey(studentId)){
				sendSMSToStudent(mobileNo,templateName,certificateName,campus);
			}
			stuMap.put(studentId, studentId);
	
		}
	 }}}
	}
		
		public void sendSMSToStudent(String mobileNo, String templateName,String certificateName,String campus) throws Exception{
			Properties prop = new Properties();
	        InputStream in1 = ApplicationStatusUpdateHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
	        prop.load(in1);
			String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
			String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
			String desc="";
			SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
			List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templateName);
			if(list != null && !list.isEmpty()) {
				desc = list.get(0).getTemplateDescription();
				desc=desc.replace(CMSConstants.TEMPLATE_CERTIFICATENAME,certificateName);
				desc=desc.replace(CMSConstants.TEMPLATE_CAMPUS,campus);
				if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
					MobileMessaging mob=new MobileMessaging();
					mob.setDestinationNumber(mobileNo);
					mob.setMessageBody(desc);
					mob.setMessagePriority(3);
					mob.setSenderName(senderName);
					mob.setSenderNumber(senderNumber);
					mob.setMessageEnqueueDate(new Date());
					mob.setIsMessageSent(false);
					PropertyUtil.getInstance().save(mob);
				}
			}
		}

		private Set<CertificateRequestMarksCardDetails> getMarksCardObjects(CertificateDetailsTo certTo, CertificateRequestOnlineForm crForm) {
			Set<CertificateRequestMarksCardDetails> mk = new HashSet<CertificateRequestMarksCardDetails>();
			if (certTo != null
					&& certTo.getMarksCardTo()!= null
					&& !certTo.getMarksCardTo().isEmpty()) {
				Iterator<CertificateRequestMarksCardTO> itr = certTo.getMarksCardTo().iterator();
				while (itr.hasNext()) {
					CertificateRequestMarksCardTO to = (CertificateRequestMarksCardTO) itr.next();
					CertificateRequestMarksCardDetails fin = new CertificateRequestMarksCardDetails();
					if (to.getSemester() != null && !to.getSemester().isEmpty()	|| to.getMonth() != null && !to.getMonth().isEmpty()
							|| to.getType() != null && !to.getType().isEmpty() || to.getYear()!=null && to.getYear().isEmpty()) {
						if (to.getId() > 0) {
							fin.setId(to.getId());
						}
						fin.setCreatedBy(crForm.getUserId());
						fin.setCreatedDate(new Date());
						fin.setModifiedBy(crForm.getUserId());
						fin.setLastModifiedDate(new Date());
						fin.setSemester(Integer.parseInt(to.getSemester()));
						fin.setMonth(to.getMonth());
						fin.setType(to.getType());
						fin.setYear(Integer.parseInt(to.getYear()));

						mk.add(fin);
					}
				}
			}
			return mk;
		}
		
		
		
		private Set<CertificateRequestPurposeDetails> getPurposeDetails(CertificateDetailsTo certTo, CertificateRequestOnlineForm crForm) {
			Set<CertificateRequestPurposeDetails> certDetails = new HashSet<CertificateRequestPurposeDetails>();
			if (certTo != null
					&& certTo.getAssignPurposeTo()!= null
					&& !certTo.getAssignPurposeTo().isEmpty()) {
				
				Iterator<AssignCertificateRequestPurposeTO> itr = certTo.getAssignPurposeTo().iterator();
				while (itr.hasNext()) {
					AssignCertificateRequestPurposeTO to = (AssignCertificateRequestPurposeTO) itr.next();
					CertificateRequestPurposeDetails fin = new CertificateRequestPurposeDetails();
					if (to.getCertificatePurposeId() != null && to.getCertificatePurposeId().getId()>0) {
						if(to.getAssignChecked()!=null && !to.getAssignChecked().isEmpty()){
						if(to.getAssignChecked().equalsIgnoreCase("true") || to.getAssignChecked().equalsIgnoreCase("on")){
						fin.setCreatedBy(crForm.getUserId());
						fin.setCreatedDate(new Date());
						fin.setModifiedBy(crForm.getUserId());
						fin.setLastModifiedDate(new Date());
						CertificateRequestPurpose cd= new CertificateRequestPurpose();
						cd.setId(to.getCertificatePurposeId().getId());
						fin.setPurposeId(cd);
						certDetails.add(fin);
						}
						}
					}
				}
			}
			return certDetails;
		}
	
	public boolean saveCompletedCertificate(CertificateRequestOnlineForm crForm) throws Exception
    {
			boolean flag=false;
			
		List<CertificateOnlineStudentRequest> cert=helper.convertFormToBo(crForm);
			flag=txn.saveCompletedCertificate(cert);
			
			return flag;
		}
	
	public boolean saveRejected(CertificateRequestOnlineForm crForm) throws Exception
    {
			boolean flag=false;
		//Write new function to set data below line is commented for that	
		List<CertificateOnlineStudentRequest> cert=helper.convertFormToBo(crForm);
			if(!cert.isEmpty())
			flag=txn.saveCompletedCertificate(cert);
			
			return flag;
		}
	
	
	
	public boolean saveRemarks(CertificateRequestOnlineForm crForm) throws Exception
    {
			boolean flag=false;
		//Write new function to set data below line is commented for that	
		List<CertificateOnlineStudentRequest> cert=helper.convertFormToBoRemarks(crForm);
			if(!cert.isEmpty())
			flag=txn.saveCompletedCertificate(cert);
			
			return flag;
		}
	
	public boolean sendMailToStudent(CertificateRequestOnlineForm admForm, String templateName ) throws Exception {
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
			List<GroupTemplate> list=null;
			//get template and replace dynamic data
			TemplateHandler temphandle=TemplateHandler.getInstance();
			 list= temphandle.getDuplicateCheckList(templateName);
			
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
			//	send mail to applicant
				String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
				String fromAddress=CMSConstants.MAIL_USERID;
				
				
				ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
				if(admForm.getStudentToList()!=null && !admForm.getStudentToList().isEmpty()){	
				Iterator<CertificateRequestOnlineTO> itr=admForm.getStudentToList().iterator();
				Map<Integer,Integer> stuMap = new  HashMap<Integer, Integer>();
				while (itr.hasNext()) {
					String email="";
					String certificateName="";
					String message="";
					String subject="";
					CertificateRequestOnlineTO to = (CertificateRequestOnlineTO) itr.next();
					if(admForm.getIsReject().equalsIgnoreCase("true")){
						if(admForm.getRejectId().equalsIgnoreCase(to.getId())){
						
						int studentId= Integer.parseInt(to.getStudentId());
						Student bo=(Student)txn.getMasterEntryDataById(Student.class,studentId);
									int certId=Integer.parseInt(to.getCertificateId());
									CertificateDetails cert= (CertificateDetails)txn.getMasterEntryDataById(CertificateDetails.class,certId);
									if(certificateName.isEmpty())
									{
										certificateName=cert.getCertificateName();
									}
									else if(!certificateName.isEmpty())
									{
										certificateName=certificateName+", "+cert.getCertificateName();
									}
							if(bo!=null){
								if(bo.getAdmAppln().getPersonalData().getUniversityEmail()!=null && !bo.getAdmAppln().getPersonalData().getUniversityEmail().isEmpty())
									email=bo.getAdmAppln().getPersonalData().getUniversityEmail();
								else if(bo.getAdmAppln().getPersonalData().getEmail()!=null && !bo.getAdmAppln().getPersonalData().getEmail().isEmpty()){
									email=bo.getAdmAppln().getPersonalData().getEmail();
								}
							}
								subject= "Requested Certificates has been rejected";
								message =desc;
								message = message.replace(CMSConstants.TEMPLATE_CERTIFICATENAME,certificateName);
								sent=sendMail(email, subject, message, fromName, fromAddress);
								HtmlPrinter.printHtml(message);
						}
					}
					else if((to.getIsCompleted()!=null && to.getIsCompleted()) && (to.getIsIssued()!=null && !to.getIsIssued()))
					{
					int studentId= Integer.parseInt(to.getStudentId());
					Student bo=(Student)txn.getMasterEntryDataById(Student.class,studentId);
					if(admForm.getStudentToList()!=null && !admForm.getStudentToList().isEmpty()){
						
						Iterator<CertificateRequestOnlineTO> itr1=admForm.getStudentToList().iterator();
						while (itr1.hasNext()) {
							CertificateRequestOnlineTO to1 = (CertificateRequestOnlineTO) itr1.next();
							if(Integer.parseInt(to1.getStudentId())==studentId){
							if(to1.getIsCompleted()!=null && to1.getIsCompleted()){
							int certId=Integer.parseInt(to1.getCertificateId());
							CertificateDetails cert= (CertificateDetails)txn.getMasterEntryDataById(CertificateDetails.class,certId);
							if(certificateName == null || certificateName.isEmpty())
							{
								certificateName=cert.getCertificateName();
							}
							else if(!certificateName.isEmpty())
							{
								certificateName=certificateName+", "+cert.getCertificateName();
							}
						}}
					}
					
				if(bo!=null){
					
					if(bo.getAdmAppln().getPersonalData().getUniversityEmail()!=null && !bo.getAdmAppln().getPersonalData().getUniversityEmail().isEmpty())
						email=bo.getAdmAppln().getPersonalData().getUniversityEmail();
					else if(bo.getAdmAppln().getPersonalData().getEmail()!=null && !bo.getAdmAppln().getPersonalData().getEmail().isEmpty())
						email=bo.getAdmAppln().getPersonalData().getEmail();
					
					}
				}
					if(!stuMap.containsKey(studentId)){
						 subject= "Collect Requested Certificates ";
						 message =desc;
						 message = message.replace(CMSConstants.TEMPLATE_CERTIFICATENAME,certificateName);
						sent=sendMail(email, subject, message, fromName, fromAddress);
						HtmlPrinter.printHtml(message);
						
					}
					stuMap.put(studentId, studentId);
			
					}
				}
			  }
			} 
			return sent;
	}
	
	
	/**
	 * Common Send mail
	 * @param admForm
	 * @return
	 */
	public boolean sendMail(String mailID, String sub,String message, String fromName, String fromAddress) {
			boolean sent=false;
				String toAddress=mailID;
				// MAIL TO CONSTRUCTION
				String subject=sub;
				String msg=message;
			
				MailTO mailto=new MailTO();
				mailto.setFromAddress(fromAddress);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMessage(msg);
				mailto.setFromName(fromName);
				
				sent=CommonUtil.sendMail(mailto);
			return sent;
	}
	
	 public String getdesc(int id)throws Exception {
	    	String desc=txn.getDescription(id);
	    	log.info("exit getSearchedStudents");
	    	return desc;
	    	
	    }
	 
	 public void getTemplatePrintDetails(CertificateRequestOnlineForm commonTemplateForm,
				HttpServletRequest request) throws Exception {
			
			List<CertificateDetailsTemplate> groupTemplateList=getGroupTemplateList(Integer.parseInt(commonTemplateForm.getTemplateId()));
			Student student=txn.getstudentDetails(commonTemplateForm.getRegNo());
			List<String> messageList = helper.copyBosToList(student, groupTemplateList, request, commonTemplateForm);
			commonTemplateForm.setMessageList(messageList);
		}

	 public List<CertificateDetailsTemplate> getGroupTemplateList(int templateId) throws Exception {
			List<CertificateDetailsTemplate> list= txn.getGroupTemplates(templateId);
			log.debug("Leaving getTemplateList ");
			
			return list;
	}
	 
	public List<CertificateRequestOnlineTO> getCertificateStatus(CertificateRequestOnlineForm stForm)throws Exception {
			
	StringBuffer query = txn.getCertificateStatus( stForm);
	List<CertificateOnlineStudentRequest> studentlist=txn.getCertificateStatus(query);
	List<CertificateRequestOnlineTO> studenttoList = helper.convertStatusTOtoBO(studentlist);
	log.info("exit getSearchedStudents");
	return studenttoList;
} 
	
	public boolean saveIdCardApplications(CertificateRequestOnlineForm crForm) throws Exception {
		
		/*OnlinePaymentReciepts onlinePaymentReciepts=new OnlinePaymentReciepts();
		PcFinancialYear pcFinancialYear=new PcFinancialYear();
		pcFinancialYear.setId(crForm.getFinId());
		onlinePaymentReciepts.setPcFinancialYear(pcFinancialYear);
		Student student = new Student();
		student.setId(crForm.getStudentId());
		onlinePaymentReciepts.setStudent(student);
		onlinePaymentReciepts.setTotalAmount(new BigDecimal(crForm.getTotalFees()));
		onlinePaymentReciepts.setApplicationFor("Certificate Request");
		onlinePaymentReciepts.setCreatedBy(crForm.getUserId());
		onlinePaymentReciepts.setCreatedDate(new Date());
		onlinePaymentReciepts.setLastModifiedDate(new Date());
		onlinePaymentReciepts.setModifiedBy(crForm.getUserId());
		onlinePaymentReciepts.setTransactionDate(new Date());
		onlinePaymentReciepts.setIsActive(true);
		PropertyUtil.getInstance().save(onlinePaymentReciepts);
		boolean isPaymentSuccess=false;
		if(onlinePaymentReciepts.getId()>0){
			crForm.setOnlinePaymentId(onlinePaymentReciepts.getId());
			String registerNo=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(crForm.getStudentId(),"Student",true,"registerNo");
			OnlinePaymentUtils.dedactAmountFromAccount(registerNo,onlinePaymentReciepts.getId(), crForm.getTotalFees(), onlinePaymentReciepts);
			if(!onlinePaymentReciepts.getIsPaymentFailed()){
				isPaymentSuccess=true;
				txn.updateAndGenerateRecieptNoOnlinePaymentReciept(onlinePaymentReciepts);
			}else
				crForm.setMsg(onlinePaymentReciepts.getStatus());
				
		}
		if(isPaymentSuccess){*/
		List<CertificateOnlineStudentRequest> boList=new ArrayList<CertificateOnlineStudentRequest>();
		
		Iterator<CertificateDetailsTo> itr=crForm.getCertificateRequestOnlineTO().getCertificateDetailsTo().iterator();
		while (itr.hasNext()) {
			CertificateDetailsTo to = (CertificateDetailsTo) itr.next();
			CertificateOnlineStudentRequest cert=new CertificateOnlineStudentRequest();
			if(to.getIsIdCard().equalsIgnoreCase("true")){
			if(to.isChecked())
			{
			if(to.getId()>0){
				CertificateDetails cc=new CertificateDetails();
				cc.setId(to.getId());
				cert.setCertificateDetailsId(cc);
			}
			
			if(to.getAssignPurposeTo()!=null && !to.getAssignPurposeTo().isEmpty())
			{
			Set<CertificateRequestPurposeDetails> certificatPurposeDetails= getPurposeDetails(to,crForm);
			cert.setCertReqPurpose(certificatPurposeDetails);
			}
			if(to.getIsReasonRequired().equalsIgnoreCase("true"))
			{
				if(to.getStudentRemarks()!=null && !to.getStudentRemarks().isEmpty())
				cert.setStudentRemarks(to.getStudentRemarks());
			}
			Student student = new Student();
			student.setId(crForm.getStudentId());
			cert.setIsCompleted(false);
			cert.setIsIssued(false);
			cert.setIsRejected(false);
			cert.setStudentId(student);
			cert.setCreatedBy(crForm.getUserId());
			cert.setCreatedDate(new Date());
			cert.setModifiedBy(crForm.getUserId());
			cert.setLastModifiedDate(new Date());
			cert.setAppliedDate(new Date());
			cert.setIsActive(true);
			
			boList.add(cert);
			}
			}
		}
		return txn.saveCertificate(boList);
		
	}
	
	 

	 
	
		
}
