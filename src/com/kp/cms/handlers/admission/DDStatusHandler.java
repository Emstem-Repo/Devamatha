package com.kp.cms.handlers.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.exam.NewSupplementaryImpApplicationAction;
import com.kp.cms.actions.usermanagement.CreateUserAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePreferenceEntranceDetails;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.exam.ExamRegularApplication;
import com.kp.cms.bo.exam.ExamRevaluationApp;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.forms.admission.DDStatusForm;
import com.kp.cms.forms.exam.NewSupplementaryImpApplicationForm;
import com.kp.cms.handlers.exam.NewSupplementaryImpApplicationHandler;
import com.kp.cms.helpers.admission.ApplicationEditHelper;
import com.kp.cms.helpers.admission.DDStatusHelper;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admission.DDStatusTO;
import com.kp.cms.transactions.admission.IApplicationEditTransaction;
import com.kp.cms.transactions.admission.IDDStatusTransaction;
import com.kp.cms.transactionsimpl.admission.ApplicationEditTransactionimpl;
import com.kp.cms.transactionsimpl.admission.DDStatusTransactionImpl;
import com.kp.cms.utilities.PasswordGenerator;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.SMS_Message;

public class DDStatusHandler {

	IDDStatusTransaction transaction=DDStatusTransactionImpl.getInstance();
	/**
	 * Singleton object of DDStatusHandler
	 */
	private static DDStatusHandler dDStatusHandler = null;
	private static final Log log = LogFactory.getLog(DDStatusHandler.class);
	private DDStatusHandler() {

	}
	/**
	 * return singleton object of DDStatusHandler.
	 * @return
	 */
	public static DDStatusHandler getInstance() {
		if (dDStatusHandler == null) {
			dDStatusHandler = new DDStatusHandler();
		}
		return dDStatusHandler;
	}
	/**
	 * @param dDStatusForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateDDStatus(DDStatusForm dDStatusForm) throws Exception {
		AdmAppln bo=transaction.updateStatus(DDStatusHelper.getInstance().getAlreadyEnteredQuery(dDStatusForm),dDStatusForm);
		return DDStatusHelper.getInstance().sendMailToStudent(bo);
	}
	/**
	 * @param dDStatusForm
	 * @return
	 * @throws Exception
	 */
	public boolean getAlreadyEntered(DDStatusForm dDStatusForm) throws Exception{
		String query=DDStatusHelper.getInstance().getAlreadyEnteredQuery(dDStatusForm);
		return transaction.getAlreadyEntered(query);
	}
	/**
	 * @param dDStatusForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkStudent(DDStatusForm dDStatusForm) throws Exception {
		String query=DDStatusHelper.getInstance().getAlreadyEnteredQuery(dDStatusForm);
		return transaction.checkStudent(query);
	}
	/**
	 * @param dDStatusForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDDExists(DDStatusForm dDStatusForm) throws Exception {
		String query=DDStatusHelper.getInstance().checkDDExistsQuery(dDStatusForm);
		return transaction.checkStudent(query);
	}



	/**
	 * @param dDStatusForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateChallanStatus(DDStatusForm dDStatusForm) throws Exception {
		AdmAppln bo=transaction.updateStatus1(DDStatusHelper.getInstance().getAlreadyEnteredQuery(dDStatusForm),dDStatusForm);
		return DDStatusHelper.getInstance().sendMailToStudent1(bo);
	}


	/**
	 * @param dDStatusForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkChallanExists(DDStatusForm dDStatusForm) throws Exception {
		String query=DDStatusHelper.getInstance().checkChallanExistsQuery(dDStatusForm);
		return transaction.checkStudent(query);
	}




	// get students for challan
	public List<DDStatusTO> getStudentsChallanStatusOnCourse(DDStatusForm ddForm) throws Exception {
		// TODO Auto-generated method stub


		List<AdmAppln> studentList =transaction.getStudentsChallanStatusOnCourse(ddForm);
		List<DDStatusTO> list = DDStatusHelper.getInstance().copyBoToTO2(studentList);



		return list;

	}


	public boolean updateChallanStatusOnCourse(DDStatusForm ddForm) throws Exception {
		boolean isAdded=false;

		isAdded=transaction.updateChallanStatusOnCourse(ddForm);


		return isAdded;
	}

	// get students for dd
	public List<DDStatusTO> getStudentsDDStatusOnCourse(DDStatusForm ddForm) throws Exception {
		// TODO Auto-generated method stub


		List<AdmAppln> studentList =transaction.getStudentsDDStatusOnCourse(ddForm);
		List<DDStatusTO> list = DDStatusHelper.getInstance().copyBoToTO3(studentList);



		return list;

	}


	public boolean updateDDStatusOnCourse(DDStatusForm ddForm) throws Exception {
		boolean isAdded=false;

		isAdded=transaction.updateDDStatusOnCourse(ddForm);


		return isAdded;
	}


	// get students for dd
	public List<DDStatusTO> getStudentsChallanDtailsOnDate(DDStatusForm ddForm) throws Exception {
		// TODO Auto-generated method stub


		List<AdmAppln> studentList =transaction.getStudentsChallanDtailsOnDate(ddForm);
		List<DDStatusTO> list = DDStatusHelper.getInstance().copyBoToTO4(studentList);



		return list;

	}


	public boolean updateChallanUploadProcess(DDStatusForm ddForm) throws Exception {
		boolean isAdded=false;

		isAdded=transaction.updateChallanUploadProcess(ddForm);

		return isAdded;
	}
	public Integer ChallanVerifiedCount(DDStatusForm ddForm) throws Exception
	{
		return transaction.ChallanVerifiedCount(ddForm);
	}
	public Integer ChallanNotVerifiedCount(DDStatusForm ddform) throws Exception
	{
		return transaction.ChallanNotVerifiedCount(ddform);
	}
	public List<DDStatusTO> getStudentsChallanStatusOnCourseForExam(DDStatusForm ddForm) throws Exception {
		// TODO Auto-generated method stub


		List<ExamRegularApplication> studentList =transaction.getStudentsChallanStatusOnCourseForExam(ddForm);
		List<DDStatusTO> list = DDStatusHelper.getInstance().copyBoToTO5(studentList);



		return list;

	}

	public boolean updateChallanStatusOnCourseForExam(DDStatusForm ddForm) throws Exception {
		boolean isAdded=false;

		isAdded=transaction.updateChallanStatusOnCourseForExam(ddForm);


		return isAdded;
	}

	// get students for dd
	public List<DDStatusTO> getStudentsChallanDtailsOnDateForExam(DDStatusForm ddForm) throws Exception {
		// TODO Auto-generated method stub

		NewSupplementaryImpApplicationForm newSupplImpForm=new NewSupplementaryImpApplicationForm();
		Double fee=null;
		Double feeUpload=null;
		List<ExamRegularApplication> studentList =transaction.getStudentsChallanDtailsOnDateForExam(ddForm);
		List<ExamRegularApplication> regAppList=new ArrayList<ExamRegularApplication>();
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<ExamRegularApplication> itr=studentList.iterator();
			while(itr.hasNext()){
				ExamRegularApplication examRegApp=itr.next();
				newSupplImpForm.setStudentObj(examRegApp.getStudent());
				newSupplImpForm.setExamId(examRegApp.getExam().getId()+"");
				newSupplImpForm.setExam(examRegApp.getExam());
				newSupplImpForm.setClassId(examRegApp.getClasses().getId()+"");
				List<ExamSupplementaryImprovementApplicationBO> list=transaction.getSupplementaryStudentWise(examRegApp);
				if(list!=null && !list.isEmpty()){
					fee=NewSupplementaryImpApplicationHandler.getInstance().getSupplementaryFeeForStudent(newSupplImpForm);
					feeUpload=transaction.getFeesUpload(examRegApp);
					//if(fee<=feeUpload){
					examRegApp.setAmount(new BigDecimal(fee));
					regAppList.add(examRegApp);
					//}
					//else
					//{
					//System.out.println("student id##########"+newSupplImpForm.getStudentObj().getId()+"examid##################"+newSupplImpForm.getExamId()+"fee##############"+fee+"feeupload####################"+feeUpload);
					//}

				}
				else
				{
					fee=NewSupplementaryImpApplicationHandler.getInstance().getRegularFeeForStudent(newSupplImpForm);
					feeUpload=transaction.getFeesUpload(examRegApp);
					//if(fee<=feeUpload){
					examRegApp.setAmount(new BigDecimal(fee.toString()));
					regAppList.add(examRegApp);
					//}
				}
			}
		}
		List<DDStatusTO> list = DDStatusHelper.getInstance().copyBoToTO6(regAppList);



		return list;

	}
	public boolean updateChallanUploadProcessForExam(DDStatusForm ddForm) throws Exception {
		boolean isAdded=false;

		isAdded=transaction.updateChallanUploadProcessForExam(ddForm);

		return isAdded;
	}

	public List<DDStatusTO> getStudentsChallanStatusOnCourseForRevaluation(DDStatusForm ddForm) throws Exception {

		List<ExamRevaluationApp> studentList =transaction.getStudentsChallanStatusOnCourseForRevaluation(ddForm);
		List<DDStatusTO> list = DDStatusHelper.getInstance().copyBoToTOForRevaluation(studentList);

		return list;

	}

	public boolean updateChallanStatusOnCourseForRevaluation(DDStatusForm ddForm) throws Exception {
		boolean isAdded=false;

		isAdded=transaction.updateChallanStatusOnCourseForRevaluation(ddForm);


		return isAdded;
	}

	public List<DDStatusTO> getStudentsChallanDtailsOnDateWiseForRevaluation(DDStatusForm ddForm) throws Exception {

		List<ExamRevaluationApp> studentList =transaction.getStudentsChallanDtailsOnDateForRevaluation(ddForm);
		List<DDStatusTO> list = DDStatusHelper.getInstance().copyBoToTOForRevaluation(studentList);

		return list;

	}

	public boolean updateChallanUploadProcessForRevaluation(DDStatusForm ddForm) throws Exception {
		boolean isAdded=false;

		isAdded=transaction.updateChallanUploadProcessForRevaluation(ddForm);

		return isAdded;
	}
//Ashwini for challan verification
	public boolean sendSMSToStudents(DDStatusForm ddForm,HttpSession session) throws UnsupportedEncodingException{

		boolean sendMsg=false;
		boolean returnMsg=false;
		Properties prop=new Properties();
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		List<String> mobNoList = ddForm.getMobNoList();
		if(mobNoList.size()>0){


			try{
				InputStream in =CreateUserAction.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
				prop.load(in);
			}
			catch(FileNotFoundException e){
				log.error("Unable to read properties File........", e);
			}
			catch(IOException e){
				log.error("Unable to read properties File........", e);
			}



			String temp="";
		
			temp=temp+URLEncoder.encode("Your challan is verified successfully..! you can take your application print");

			for(int i=0;i<mobNoList.size();i++){
				SMS_Message sms=new SMS_Message();
				sms.setDestination_number(mobNoList.get(i));
				
				//sms.setDestination_number("7795196548");
				sms.setMessage_body(temp);
				sms.setMessage_priority(String.valueOf(3));
				sms.setSender_name(senderName);
				sms.setSender_number(senderNumber);
				List<SMS_Message> smsList=new ArrayList<SMS_Message>();
				smsList.add(sms);
				SMSUtils smsUtils=new SMSUtils();
				List<SMS_Message> mobList=smsUtils.sendSMS(smsList);

				Calendar cal1 = Calendar.getInstance();
				session.setAttribute("generatedTime",cal1);

				if(mobList.get(0).getMessage_status()==0){
					sendMsg=true;
					//System.out.println(session.getAttribute("usermobile").toString()+"++++++++++++++++++++++++++++++++++"+mobList.get(0).getSms_gateway_response().substring(0,7)+"+++++++++++++++++++++++++++++++++"+screenName);
				}
				else{
					sendMsg=false;	
				}

			}//sms check over

			return true;
		}
		return returnMsg;
	}
	
	public List<DDStatusTO> getStudentsChallanDtailsOnDateForFee(DDStatusForm ddForm) throws Exception {
		// TODO Auto-generated method stub

		List<FeePayment> studentList =transaction.getStudentsChallanDtailsOnDateForFee(ddForm);
		List<DDStatusTO> list = DDStatusHelper.getInstance().copyBoToTOforFee(studentList);
		return list;
	}
	
	public boolean updateChallanUploadProcessForFee(DDStatusForm ddForm) throws Exception {
		boolean isAdded=false;

		isAdded=transaction.updateChallanUploadProcessForFee(ddForm);

		return isAdded;
	}
}
