package com.kp.cms.helpers.admission;


import java.util.Date;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admission.AcknowledgeNumber;
import com.kp.cms.bo.admission.ApplnAcknowledgement;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.admission.ReceivedThrough;
import com.kp.cms.forms.admission.ApplicationAcknowledgeForm;
import com.kp.cms.to.admin.AdmApplnTO;
import com.kp.cms.to.admission.ApplnAcknowledgementTo;
import com.kp.cms.transactions.admission.IApplicationAcknowledgeTxn;
import com.kp.cms.transactionsimpl.admission.ApplicationAcknowledgeTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class ApplicationAcknowledgeHelper {
	private static volatile ApplicationAcknowledgeHelper applnAcknowledgeHelper = null;
	IApplicationAcknowledgeTxn transaction = ApplicationAcknowledgeTxnImpl.getInstance();
	public static ApplicationAcknowledgeHelper getInstance(){
		if(applnAcknowledgeHelper == null){
			applnAcknowledgeHelper = new ApplicationAcknowledgeHelper();
			return applnAcknowledgeHelper;
		}
		return applnAcknowledgeHelper;
		
	}
	
	public ApplnAcknowledgement convertFormToBO(ApplicationAcknowledgeForm appnAcknowledgementForm)throws Exception{
		ApplnAcknowledgement appln =transaction.getApplnAcknowledgement(appnAcknowledgementForm.getAppNo());
		if(appln!=null){
			appln = setValues(appln, appnAcknowledgementForm);
			appnAcknowledgementForm.setMode("update");
			if(appln.getSlipNo()==null){
				appln = checkAcknowledgeNumber(appnAcknowledgementForm,appln);
			}
			/* code added by sudhir */
			appln.setModifiedBy(appnAcknowledgementForm.getUserId());
			appln.setLastModifiedDate(new Date());
			/* --------------------- */
			return appln;
		}else{
		    ApplnAcknowledgement acknowledge = new ApplnAcknowledgement();
		    acknowledge = setValues(acknowledge, appnAcknowledgementForm);
		    acknowledge = checkAcknowledgeNumber(appnAcknowledgementForm,acknowledge);
		    appnAcknowledgementForm.setMode("save");
		    /* code added by sudhir */
		    acknowledge.setCreatedBy(appnAcknowledgementForm.getUserId());
		    acknowledge.setCreatedDate(new Date());
		    /* --------------------- */
		    return acknowledge;
		}
	}
	public ApplnAcknowledgementTo convertBoToTO(ApplnAcknowledgement aplnAcknowledge){
		ApplnAcknowledgementTo to=null;
		if(aplnAcknowledge!=null){
			to = new ApplnAcknowledgementTo();
			to.setName(aplnAcknowledge.getName()!=null?aplnAcknowledge.getName():"");
			to.setReceivedThrough(aplnAcknowledge.getReceivedThrough()!=null?aplnAcknowledge.getReceivedThrough():"");
			to.setReceivedDate(aplnAcknowledge.getReceivedDate()!=null?CommonUtil.ConvertStringToSQLDate2(aplnAcknowledge.getReceivedDate().toString()):"");
			to.setRemarks(aplnAcknowledge.getRemarks()!=null?aplnAcknowledge.getRemarks():"");
			to.setCourseId(aplnAcknowledge.getCourse()!=null?String.valueOf(aplnAcknowledge.getCourse().getId()):"");
			to.setTrackingNo(aplnAcknowledge.getTrackingNo()!=null?aplnAcknowledge.getTrackingNo():"");
			to.setDob(aplnAcknowledge.getDateOfBirth()!=null?CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(aplnAcknowledge.getDateOfBirth()),"dd-MMM-yyyy","dd/MM/yyyy"):"");
			to.setMobileNo(aplnAcknowledge.getMobileNo()!=null?aplnAcknowledge.getMobileNo():"");
		}
		return to;
	}
	public void setLogoToForm(Organisation org,ApplicationAcknowledgeForm appnAcknowledgementForm,ApplnAcknowledgement acknowledgement){
		if(org!=null){
			if(org.getLogo()!=null){
				appnAcknowledgementForm.setLogo(org.getLogo());
			}
			if(acknowledgement.getName()!=null && !acknowledgement.getName().isEmpty())
				appnAcknowledgementForm.setName(acknowledgement.getName());
			if(acknowledgement.getReceivedThrough()!=null)
				appnAcknowledgementForm.setReceivedThrough(acknowledgement.getReceivedThrough());
			if(acknowledgement.getReceivedDate()!=null){
				String date = CommonUtil.formatSqlDate(acknowledgement.getReceivedDate().toString());
				appnAcknowledgementForm.setReceivedDate(date);
			}
		}
	}
	public ApplnAcknowledgement checkAcknowledgeNumber(ApplicationAcknowledgeForm appnAcknowledgementForm,ApplnAcknowledgement acknowledge)throws Exception{
		AcknowledgeNumber number = transaction.getSlipNumber();
		ReceivedThrough receive = transaction.getslipRequired(acknowledge.getReceivedThrough());
		if(appnAcknowledgementForm.getFlag()!=null && appnAcknowledgementForm.getFlag().equalsIgnoreCase("true") && receive!=null && receive.getSlipRequired()){
		     int slipNo = 0;
		     if(number!=null){
		        slipNo=Integer.parseInt(number.getCurrentSlipNo())+1;
		        number.setCurrentSlipNo(String.valueOf(slipNo));
		        number.setModifiedBy(appnAcknowledgementForm.getUserId());
		        number.setLastModifiedDate(new Date());
		        transaction.saveAckNumber(number, "update");
		     }else{
			    AcknowledgeNumber slipNumber = new AcknowledgeNumber();
			    slipNo = 1;
			    slipNumber.setCurrentSlipNo(String.valueOf(slipNo));
			    slipNumber.setIsActive(true);
			    slipNumber.setCreatedBy(appnAcknowledgementForm.getUserId());
			    slipNumber.setCreatedDate(new Date());
			    transaction.saveAckNumber(slipNumber, "save");
		     }
		     acknowledge.setSlipNo(String.valueOf(slipNo));
		}
		return acknowledge;
	}
	public ApplnAcknowledgement setValues(ApplnAcknowledgement acknowledge,ApplicationAcknowledgeForm appnAcknowledgementForm){
		
		acknowledge.setReceivedThrough(appnAcknowledgementForm.getReceivedThrough());
		if(appnAcknowledgementForm.getName()!=null)
			acknowledge.setName(appnAcknowledgementForm.getName());
		if(appnAcknowledgementForm.getReceivedDate()!=null)
			acknowledge.setReceivedDate(CommonUtil.ConvertStringToSQLDate(appnAcknowledgementForm.getReceivedDate()));
		if(appnAcknowledgementForm.getRemarks()!=null)
			acknowledge.setRemarks(appnAcknowledgementForm.getRemarks());
		acknowledge.setApplnNo(appnAcknowledgementForm.getAppNo());
		Course course=new Course();
		course.setId(Integer.valueOf(appnAcknowledgementForm.getCourseId()));
		acknowledge.setCourse(course);
		acknowledge.setDateOfBirth(CommonUtil.ConvertStringToDate(appnAcknowledgementForm.getDob()));
		if(appnAcknowledgementForm.getTrackingNo()!=null)
		acknowledge.setTrackingNo(appnAcknowledgementForm.getTrackingNo());
		if(appnAcknowledgementForm.getMobileNo()!=null)
		acknowledge.setMobileNo(appnAcknowledgementForm.getMobileNo());
		return acknowledge;
	}
	
	
	public void convertAdmApplnToForm(AdmAppln admAppln, ApplicationAcknowledgeForm appnForm)throws Exception{
		IApplicationAcknowledgeTxn txn= new ApplicationAcknowledgeTxnImpl();
		if(admAppln!=null){
			if(admAppln.getAppliedYear()>0){
			appnForm.setApplicationYear(String.valueOf(admAppln.getAppliedYear()));
			}
			if(admAppln.getCourse().getProgram().getIsExamCenterRequired()){
				appnForm.setExamCenterRequired(true);
			}else{
				appnForm.setExamCenterRequired(false);
			}
			if(admAppln.getId()>0){
				appnForm.setAdmApplnId(String.valueOf(admAppln.getId()));
			}
			if(admAppln.getInterScheduleSelection()!=null && admAppln.getInterScheduleSelection().getId()>0){
				appnForm.setInterviewSelectionDate(String.valueOf(admAppln.getInterScheduleSelection().getId()));
				txn.getDateFromSelectionProcessId(appnForm.getInterviewSelectionDate(), appnForm);
			}
			if(admAppln.getExamCenter()!=null && admAppln.getExamCenter().getId()>0){
				appnForm.setInterviewVenue(String.valueOf(admAppln.getExamCenter().getId()));
				appnForm.setSelectedVenue(admAppln.getExamCenter().getCenter());
			}
			if(admAppln.getCourse()!=null && admAppln.getCourse().getProgram()!=null && admAppln.getCourse().getProgram().getId()>0){
				appnForm.setProgramId(String.valueOf(admAppln.getCourse().getProgram().getId()));
			}
			if(admAppln.getCourse()!=null && admAppln.getCourse().getProgram()!=null && admAppln.getCourse().getProgram().getId()>0){
				appnForm.setCourseId(String.valueOf(admAppln.getCourse().getId()));
			}
			if(admAppln.getMode()!=null && !admAppln.getMode().isEmpty()){
				appnForm.setApplnMode(admAppln.getMode());
			}
			
		}
	}
}
