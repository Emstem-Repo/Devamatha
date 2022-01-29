package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.OnlineExamSupplementaryApplication;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.OnlineExamSuppApplicationForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.to.admission.OnlinePaymentRecieptsTo;
import com.kp.cms.transactions.admin.IOnlineExamSuppApplicationTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.admin.OnlineExamSuppApplicationImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class OnlineExamSuppApplicationHelper {
	
	
	private static volatile OnlineExamSuppApplicationHelper instance=null;
	

	IOnlineExamSuppApplicationTransaction txn;

	private OnlineExamSuppApplicationHelper(){
		txn = new OnlineExamSuppApplicationImpl();}
			
		/**
		 * @return
		 */
		public static OnlineExamSuppApplicationHelper getInstance(){
			if(instance==null){
				instance=new OnlineExamSuppApplicationHelper();
			}
			return instance;
		}
		
		
		public List<OnlinePaymentReciepts> getOnlineReciepts(int onlinePaymentId, int studentId) throws Exception{
			String query="from OnlinePaymentReciepts o where o.isPaymentFailed=0 and o.isActive=1 and o.student.id="+studentId +" and o.applicationFor like 'Holistic%' and o.id="+onlinePaymentId;
			INewExamMarksEntryTransaction txn1=NewExamMarksEntryTransactionImpl.getInstance();
			List<OnlinePaymentReciepts> list=txn1.getDataForQuery(query);
			return list;
		}
		/**
		 * @param list
		 * @return
		 * @throws Exception
		 */
		public List<OnlinePaymentRecieptsTo> convertOnlineRecieptToToList( List<OnlinePaymentReciepts> list,HttpServletRequest request) throws Exception {
			List<OnlinePaymentRecieptsTo> toList=new ArrayList<OnlinePaymentRecieptsTo>();
			OnlinePaymentRecieptsTo to=null;
			int count=1;
			List<GroupTemplate> templateList= TemplateHandler.getInstance().getDuplicateCheckList(0,CMSConstants.TEMPLATE_ONLINE_SUPPLEMENTARY_APPLICATION_PRINT);
			for (OnlinePaymentReciepts bo: list) {
				to=new OnlinePaymentRecieptsTo();
				to.setId(bo.getId());
				to.setCount(count);
				to.setRecieptNo(bo.getRecieptNo());
				to.setTransactionDate(CommonUtil.formatSqlDate1(bo.getTransactionDate().toString()));
				to.setApplicationFor(bo.getApplicationFor());
				to.setMsg(getPrintData(bo, templateList,request));
				count++;
				toList.add(to);
			}
			return toList;
	    }
		
		public String getPrintData(OnlinePaymentReciepts bo, List<GroupTemplate> list, HttpServletRequest request) throws Exception {
			String desc ="";
			if(bo!=null && list != null && !list.isEmpty()) {
				OnlineExamSupplementaryApplication record= txn.getOnlineExamSupplementaryApplication (bo);
				if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
					desc = list.get(0).getTemplateDescription();
				}
				
				desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME,bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
				desc=desc.replace(CMSConstants.TEMPLATE_REGISTER_NO,bo.getStudent().getRegisterNo());
				if(bo.getTransactionDate()!=null)
				desc=desc.replace(CMSConstants.TEMPLATE_SMS_DATE,CommonUtil.formatSqlDate1(bo.getTransactionDate().toString()));
				desc=desc.replace(CMSConstants.FEE_RECEIPTNO,bo.getRecieptNo().toString());
				desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_FOR,bo.getApplicationFor());
				desc=desc.replace(CMSConstants.TEMPLATE_VENUE, record.getVenue());
				desc=desc.replace(CMSConstants.TEMPLATE_TIME, record.getTime());
				desc=desc.replace(CMSConstants.TEMPLATE_EXAM_DATE, CommonUtil.ConvertStringToDateFormat(record.getDateOfExam().toString(), "yyyy-mm-dd",
				"dd/mm/yyyy"));
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
					if(session.getAttribute("LogoBytes")==null)
						session.setAttribute("LogoBytes", logo);
					if(session.getAttribute("LogoBytes1")==null)
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
		 * @param registration
		 * @param crForm
		 */
		public void convertBoToConvocationForm(	ConvocationRegistration registration,OnlineExamSuppApplicationForm crForm) {
				crForm.setParticipatingConvocation(registration.isParticipatingConvocation());
				crForm.setGuestPassRequired(registration.isGuestPassIsRequired());
			   if(crForm.getRelationshipWithGuest()!=null && !registration.getRelationshipWithGuest().isEmpty()){
				   crForm.setRelationshipWithGuest(registration.getRelationshipWithGuest());
			   }
			   crForm.setConvocationId(registration.getId());
		}

		public ConvocationRegistration convertFormToBO(int academicYear,OnlineExamSuppApplicationForm crForm, HttpSession session) throws Exception{
			
			ConvocationRegistration registration=new ConvocationRegistration();
			if(crForm.getConvocationId()!=0)
			{
				registration.setId(crForm.getConvocationId());
			}
			registration.setAcademicYear(academicYear);
			if(crForm.getGuestPassRequired()!=null && crForm.getPassAvailable()){
				registration.setGuestPassIsRequired(crForm.getGuestPassRequired());
			}else{
				registration.setGuestPassIsRequired(false);
			}
			if(crForm.getParticipation()!=null && crForm.getParticipation().equalsIgnoreCase("on")){
				registration.setParticipatingConvocation(true);
			}
			if(crForm.getRelationshipWithGuest()!=null && !crForm.getRelationshipWithGuest().isEmpty()){
				registration.setRelationshipWithGuest(crForm.getRelationshipWithGuest());
			}
			registration.setCreatedBy(crForm.getUserId());
			registration.setCreatedDate(new Date());
			registration.setModifiedBy(crForm.getUserId());
			registration.setLastModifiedDate(new Date());
			registration.setIsActive(true);
			Student student=new Student();
			String studentId=session.getAttribute("studentIdforConvocation").toString();
			if(studentId!=null && !studentId.isEmpty()){
				student.setId(Integer.parseInt(studentId));
				registration.setStudent(student);
			}
			if(crForm.getGuestPassRequired()!=null && crForm.getGuestPassRequired() && crForm.getPassAvailable()){
				registration.setGuestPassCount(crForm.getPasses());
			}else{
				registration.setGuestPassCount(0);
			}
			ConvocationSession convocationSession = new ConvocationSession();
			convocationSession.setId(crForm.getConvocationSessionId());
			registration.setSession(convocationSession);
			return registration;
			
		}


}
