package com.kp.cms.handlers.admission;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.SubjectGroupNotDefinedException;
import com.kp.cms.forms.admission.AdmSelectionProcessCJCForm;
import com.kp.cms.forms.admission.ApplicationAcknowledgeForm;
import com.kp.cms.helpers.admission.AdmSelectionProcessCJCHelper;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.transactions.admission.IAdmSelectProcessCJCTransaction;
import com.kp.cms.transactions.admission.IApplicationAcknowledgeTxn;
import com.kp.cms.transactionsimpl.admission.AdmSelectProcessCJCImpl;
import com.kp.cms.transactionsimpl.admission.ApplicationAcknowledgeTxnImpl;

public class AdmSelectionProcessCJCHandler {
	
	public static volatile AdmSelectionProcessCJCHandler admSelection = null;
	 public static AdmSelectionProcessCJCHandler getInstance(){
		 if(admSelection == null){
			 admSelection = new AdmSelectionProcessCJCHandler();
			 return admSelection;
		 }
		 return admSelection;
	 }
	 
	 
	 IAdmSelectProcessCJCTransaction transaction = AdmSelectProcessCJCImpl.getInstance();
	 AdmSelectionProcessCJCHelper helper=AdmSelectionProcessCJCHelper.getInstance();
	 
	 @SuppressWarnings("null")
	public boolean getRunSetDataToTable(AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception {
			boolean flag=false;
			/*boolean checkExist=false;
			List<AdmAppln> previousData=transaction.getpreviousData(admSelProcessCJCForm);
			if(previousData!=null && !previousData.isEmpty()){
			 checkExist=helper.checkFoDelete(previousData);
			 if(!checkExist){
				transaction.deleteAllData(previousData,admSelProcessCJCForm);
			 }else{
				 throw new SubjectGroupNotDefinedException();
			  }
	    	 }*/
			List<AdmAppln> repeatBo= transaction.getRunSetDataToTable(admSelProcessCJCForm);
			if(repeatBo==null || repeatBo.isEmpty()){
			  throw new DataNotFoundException();
			}
			List<Integer> courseIds=transaction.getCourseIdsFromAdmSettings(admSelProcessCJCForm);
			List<Integer> notDefinedIntPgmCourse=null;
			if(courseIds!=null){
				List<InterviewProgramCourse> interPgmCourseIds=transaction.getInterviewPrgCourse(admSelProcessCJCForm);
				if(interPgmCourseIds!=null && !interPgmCourseIds.isEmpty()){
					Iterator<Integer> itr=courseIds.iterator();
					while (itr.hasNext()) {
						Integer ipc= (Integer) itr.next();
						if(!interPgmCourseIds.contains(ipc))
						{
							notDefinedIntPgmCourse.add(ipc);
						}
					  }
				}
			}
			if(notDefinedIntPgmCourse!=null && !notDefinedIntPgmCourse.isEmpty()){
				admSelProcessCJCForm.setNotDefinedIntPgmCourse(notDefinedIntPgmCourse);
				throw new SubjectGroupNotDefinedException();
			}else
			{
				List<AdmAppln> allData= helper.convertBOsToTOs(repeatBo, admSelProcessCJCForm);
				int interviewPgmCourse= transaction.getInterviewProgCourse(admSelProcessCJCForm);
				flag=transaction.saveData(allData,admSelProcessCJCForm,interviewPgmCourse);
				return flag;
			}
		}
	 
	/* public boolean addUploadInterviewSelectedData(ApplicationAcknowledgeForm admForm,HttpServletRequest request) throws Exception{
		   IApplicationAcknowledgeTxn txn= new ApplicationAcknowledgeTxnImpl();
			InterviewCardTO interviewCardTO=txn.getInterviewScheduleDetails(admForm);
			InterviewSchedule interviewSchedule;
			boolean isAdd = false;
			List<InterviewCard> interviewCardsToSave=new ArrayList<InterviewCard>();
		    interviewSchedule=txn.getInterviewSchedule(interviewCardTO, admForm);
				if(interviewSchedule!=null)
					{
							InterviewCard interviewCard=new InterviewCard();
							AdmAppln adm=new AdmAppln();
							adm.setId(interviewCardTO.getAdmApplnId());
							interviewCard.setAdmAppln(adm);
							interviewCard.setInterview(interviewSchedule);
							interviewCard.setTime(interviewCardTO.getTime());
							interviewCard.setInterviewer(1);
							interviewCard.setCreatedBy(admForm.getUserId());
							interviewCard.setCreatedDate(new Date());
							interviewCard.setLastModifiedDate(new Date());
							interviewCard.setModifiedBy(admForm.getUserId());
							interviewCardsToSave.add(interviewCard);
					}
				Integer interViewPgmCourse= txn.getInterViewPgmCourse(admForm);
				isAdd=txn.addSelectionProcessWorkflowData(interviewCardsToSave,admForm.getUserId(), admForm,  interViewPgmCourse);
			return isAdd;
	 }*/
}
