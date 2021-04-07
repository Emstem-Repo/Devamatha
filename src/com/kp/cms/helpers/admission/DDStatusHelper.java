package com.kp.cms.helpers.admission;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePreferenceEntranceDetails;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.exam.ExamRegularApplication;
import com.kp.cms.bo.exam.ExamRevaluationApp;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.DDStatusForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.DDStatusTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.print.HtmlPrinter;

public class DDStatusHelper {
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	/**
	 * Singleton object of DDStatusHelper
	 */
	private static DDStatusHelper dDStatusHelper = null;
	private static final Log log = LogFactory.getLog(DDStatusHelper.class);
	private DDStatusHelper() {

	}
	/**
	 * return singleton object of DDStatusHelper.
	 * @return
	 */
	public static DDStatusHelper getInstance() {
		if (dDStatusHelper == null) {
			dDStatusHelper = new DDStatusHelper();
		}
		return dDStatusHelper;
	}
	/**
	 * @param dDStatusForm
	 * @return
	 * @throws Exception
	 */
	public String getAlreadyEnteredQuery(DDStatusForm dDStatusForm) throws Exception {
		String query="from AdmAppln a where a.applnNo='"+dDStatusForm.getApplnNo()+"' and a.appliedYear="+dDStatusForm.getYear();
		return query;
	}
	/**
	 * @param dDStatusForm
	 * @return
	 * @throws Exception
	 */
	public String checkDDExistsQuery(DDStatusForm dDStatusForm) throws Exception {
		String query="from AdmAppln a where a.recievedDDNo='"+dDStatusForm.getRecievedDDNo()+"'";
		return query;
	}
	/**
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public boolean sendMailToStudent(AdmAppln bo) throws Exception {
		boolean isSent=false;
		if(bo!=null){
			isSent=true;
			//get template and replace dynamic data
			TemplateHandler temphandle=TemplateHandler.getInstance();
			List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.ACCEPTANCE_MAIL_TEMPLATE);
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				//send mail to applicant

				String dob = "";
				String name = "";
				String applnno = "";
				String ddDate="";
				String recievedDDNo="";
				if(bo.getPersonalData().getEmail()!=null && !StringUtils.isEmpty(bo.getPersonalData().getEmail().trim())){
					dob=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getPersonalData().getDateOfBirth()), DDStatusHelper.SQL_DATEFORMAT,DDStatusHelper.FROM_DATEFORMAT);
					ddDate=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getRecievedDate()), DDStatusHelper.SQL_DATEFORMAT,DDStatusHelper.FROM_DATEFORMAT);
					name=bo.getPersonalData().getFirstName();
					applnno=String.valueOf(bo.getApplnNo());
					recievedDDNo=bo.getRecievedDDNo();

					String program = "";
					String appliedYear = "";

					if(bo.getCourseBySelectedCourseId().getProgram().getName() !=null) {
						program = bo.getCourseBySelectedCourseId().getProgram().getName();
					}

					if(bo.getAppliedYear()!=null && bo.getAppliedYear()>0) {
						appliedYear = bo.getAppliedYear() + "-" + (bo.getAppliedYear()+1);
					}

					//replace dyna data
					String subject= "Online Application Form Acceptance Mail";

					String message =desc;
					message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name);
					message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applnno);
					message = message.replace(CMSConstants.TEMPLATE_DOB,dob);
					message = message.replace(CMSConstants.RECIEVED_DD_NO,recievedDDNo);
					message = message.replace(CMSConstants.RECIEVED_DD_DATE,ddDate);
					message = message.replace(CMSConstants.TEMPLATE_COURSE,bo.getCourseBySelectedCourseId().getName());

					//							send mail
					AdmissionFormHandler.getInstance().sendMail(bo.getPersonalData().getEmail(),subject,message);
					//print letter
					HtmlPrinter.printHtml(message);
				}
			}
		}
		return isSent;
	}






	//raghu


	/**
	 * @param dDStatusForm
	 * @return
	 * @throws Exception
	 */
	public String checkChallanExistsQuery(DDStatusForm dDStatusForm) throws Exception {
		String query="from AdmAppln a where a.recievedChallanNo='"+dDStatusForm.getRecievedChallanNo()+"'";
		return query;
	}
	/**
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public boolean sendMailToStudent1(AdmAppln bo) throws Exception {
		boolean isSent=false;
		if(bo!=null){
			isSent=true;
			//get template and replace dynamic data
			TemplateHandler temphandle=TemplateHandler.getInstance();
			List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.ACCEPTANCE_MAIL_TEMPLATE);
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				//send mail to applicant

				String dob = "";
				String name = "";
				String applnno = "";
				String ddDate="";
				String recievedChallanNo="";
				if(bo.getPersonalData().getEmail()!=null && !StringUtils.isEmpty(bo.getPersonalData().getEmail().trim())){
					dob=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getPersonalData().getDateOfBirth()), DDStatusHelper.SQL_DATEFORMAT,DDStatusHelper.FROM_DATEFORMAT);
					ddDate=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getRecievedDate()), DDStatusHelper.SQL_DATEFORMAT,DDStatusHelper.FROM_DATEFORMAT);
					name=bo.getPersonalData().getFirstName();
					applnno=String.valueOf(bo.getApplnNo());
					recievedChallanNo=bo.getRecievedDDNo();

					String program = "";
					String appliedYear = "";

					if(bo.getCourseBySelectedCourseId().getProgram().getName() !=null) {
						program = bo.getCourseBySelectedCourseId().getProgram().getName();
					}

					if(bo.getAppliedYear()!=null && bo.getAppliedYear()>0) {
						appliedYear = bo.getAppliedYear() + "-" + (bo.getAppliedYear()+1);
					}

					//replace dyna data
					String subject= "Online Application Form Acceptance Mail";

					String message =desc;
					message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name);
					message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applnno);
					message = message.replace(CMSConstants.TEMPLATE_DOB,dob);
					message = message.replace(CMSConstants.RECIEVED_CHALLAN_NO,recievedChallanNo);
					message = message.replace(CMSConstants.RECIEVED_CHALLAN_DATE,ddDate);
					message = message.replace(CMSConstants.TEMPLATE_COURSE,bo.getCourseBySelectedCourseId().getName());

					//							send mail
					AdmissionFormHandler.getInstance().sendMail(bo.getPersonalData().getEmail(),subject,message);
					//print letter
					HtmlPrinter.printHtml(message);
				}
			}
		}
		return isSent;
	}



	// get student list for challan
	public List<DDStatusTO> copyBoToTO2(List<AdmAppln> studentList) {

		List<DDStatusTO> studentDetailsToList=new LinkedList<DDStatusTO>();

		Iterator<AdmAppln> it= studentList.iterator();
		while(it.hasNext()){
			AdmAppln a=it.next();
			DDStatusTO studentDetailsTo=new DDStatusTO();

			studentDetailsTo.setStudentName(a.getPersonalData().getFirstName());
			studentDetailsTo.setAdmId(a.getId());
			studentDetailsTo.setApplnNo(a.getApplnNo());
			if(a.getJournalNo()!=null)
				studentDetailsTo.setEnterdChallanNo(a.getJournalNo());
			//studentDetailsTo.setTempChecked("on");
			//studentDetailsTo.setChecked("on");
			//studentDetailsTo.setCourseName(a.getCourse().getName());
			if(a.getPersonalData().getReligionSection()!=null){
				studentDetailsTo.setCasteName(a.getPersonalData().getReligionSection().getName());

			}

			studentDetailsToList.add(studentDetailsTo);

		}


		// TODO Auto-generated method stub
		return studentDetailsToList;

	}



	// get student list for dd
	public List<DDStatusTO> copyBoToTO3(List<AdmAppln> studentList) {

		List<DDStatusTO> studentDetailsToList=new LinkedList<DDStatusTO>();

		Iterator<AdmAppln> it= studentList.iterator();
		while(it.hasNext()){
			AdmAppln a=it.next();
			DDStatusTO studentDetailsTo=new DDStatusTO();

			studentDetailsTo.setStudentName(a.getPersonalData().getFirstName());
			studentDetailsTo.setAdmId(a.getId());
			studentDetailsTo.setApplnNo(a.getApplnNo());
			if(a.getJournalNo()!=null)
				studentDetailsTo.setEnterdChallanNo(a.getJournalNo());
			//studentDetailsTo.setTempChecked("on");
			//studentDetailsTo.setChecked("on");
			studentDetailsTo.setCourseName(a.getCourse().getName());
			studentDetailsTo.setCasteName(a.getPersonalData().getReligionSection().getName());

			studentDetailsToList.add(studentDetailsTo);

		}


		// TODO Auto-generated method stub
		return studentDetailsToList;

	}


	public List<DDStatusTO> copyBoToTO4(List<AdmAppln> studentList) {

		List<DDStatusTO> studentDetailsToList=new LinkedList<DDStatusTO>();

		Iterator<AdmAppln> it= studentList.iterator();
		while(it.hasNext()){
			AdmAppln a=it.next();
			DDStatusTO studentDetailsTo=new DDStatusTO();

			studentDetailsTo.setAdmId(a.getId());
			if(a.getJournalNo()!=null)
				studentDetailsTo.setEnterdChallanNo(a.getJournalNo());

			studentDetailsToList.add(studentDetailsTo);

		}


		// TODO Auto-generated method stub
		return studentDetailsToList;

	}

	//get student list for challan for Exam Basim
	public List<DDStatusTO> copyBoToTO5(List<ExamRegularApplication> studentList) {

		List<DDStatusTO> studentDetailsToList=new LinkedList<DDStatusTO>();

		Iterator<ExamRegularApplication> it= studentList.iterator();
		while(it.hasNext()){
			ExamRegularApplication examRegApp=it.next();
			DDStatusTO studentDetailsTo=new DDStatusTO();
			if(examRegApp.getStudent()!=null && examRegApp.getStudent().getAdmAppln()!=null && examRegApp.getStudent().getAdmAppln().getPersonalData()!=null)
				studentDetailsTo.setStudentName(examRegApp.getStudent().getAdmAppln().getPersonalData().getFirstName());
			studentDetailsTo.setAdmId(examRegApp.getId());
			//studentDetailsTo.setApplnNo(examRegApp.getApplnNo());
			if(examRegApp.getChallanNo()!=null)
				studentDetailsTo.setEnterdChallanNo(examRegApp.getChallanNo());
			//studentDetailsTo.setTempChecked("on");
			//studentDetailsTo.setChecked("on");
			//studentDetailsTo.setCourseName(a.getCourse().getName());
			if(examRegApp.getStudent()!=null && examRegApp.getStudent().getAdmAppln()!=null && examRegApp.getStudent().getAdmAppln().getPersonalData()!=null && examRegApp.getStudent().getAdmAppln().getPersonalData().getReligionSection()!=null){
				studentDetailsTo.setCasteName(examRegApp.getStudent().getAdmAppln().getPersonalData().getReligionSection().getName());

			}

			studentDetailsToList.add(studentDetailsTo);

		}


		// TODO Auto-generated method stub
		return studentDetailsToList;

	}

	public List<DDStatusTO> copyBoToTO6(List<ExamRegularApplication> studentList) {

		List<DDStatusTO> studentDetailsToList=new LinkedList<DDStatusTO>();

		Iterator<ExamRegularApplication> it= studentList.iterator();
		while(it.hasNext()){
			ExamRegularApplication a=it.next();
			DDStatusTO studentDetailsTo=new DDStatusTO();

			studentDetailsTo.setStuRegAppId(a.getId());
			if(a.getChallanNo()!=null)
				studentDetailsTo.setEnterdChallanNo(a.getChallanNo());
			studentDetailsTo.setTotal(a.getAmount().doubleValue());
			studentDetailsToList.add(studentDetailsTo);

		}
		// TODO Auto-generated method stub
		return studentDetailsToList;

	}

	public List<DDStatusTO> copyBoToTOForRevaluation(List<ExamRevaluationApp> studentList) {

		List<DDStatusTO> studentDetailsToList=new LinkedList<DDStatusTO>();

		Iterator<ExamRevaluationApp> it= studentList.iterator();
		while(it.hasNext()){
			ExamRevaluationApp examRegApp=it.next();
			DDStatusTO studentDetailsTo=new DDStatusTO();
			if(examRegApp.getStudent()!=null && examRegApp.getStudent().getAdmAppln()!=null && examRegApp.getStudent().getAdmAppln().getPersonalData()!=null)
				studentDetailsTo.setStudentName(examRegApp.getStudent().getAdmAppln().getPersonalData().getFirstName());
			studentDetailsTo.setAdmId(examRegApp.getId());
			if(examRegApp.getChallanNo()!=null)
				studentDetailsTo.setEnterdChallanNo(examRegApp.getChallanNo());
			if(examRegApp.getStudent()!=null && examRegApp.getStudent().getAdmAppln()!=null && examRegApp.getStudent().getAdmAppln().getPersonalData()!=null && examRegApp.getStudent().getAdmAppln().getPersonalData().getReligionSection()!=null){
				studentDetailsTo.setCasteName(examRegApp.getStudent().getAdmAppln().getPersonalData().getReligionSection().getName());

			}
			studentDetailsTo.setStuRegAppId(examRegApp.getId());

			studentDetailsToList.add(studentDetailsTo);

		}


		// TODO Auto-generated method stub
		return studentDetailsToList;

	}


	public List<DDStatusTO> copyBoToTOforFee(List<FeePayment> studentList) {

		List<DDStatusTO> studentDetailsToList=new LinkedList<DDStatusTO>();

		Iterator<FeePayment> it= studentList.iterator();
		while(it.hasNext()){
			FeePayment a=it.next();
			DDStatusTO studentDetailsTo=new DDStatusTO();

			studentDetailsTo.setStuFeePaymentId(a.getId());
			if(a.getBillNo()!=null)
				studentDetailsTo.setEnterdChallanNo(a.getBillNo());
			studentDetailsTo.setTotal(a.getTotalFeePaid().doubleValue());
			studentDetailsTo.setStudentName(a.getStudent().getAdmAppln().getPersonalData().getFirstName());
			studentDetailsTo.setApplnNo(a.getStudent().getAdmAppln().getApplnNo());
			studentDetailsToList.add(studentDetailsTo);
		}
		// TODO Auto-generated method stub
		return studentDetailsToList;

	}

}
