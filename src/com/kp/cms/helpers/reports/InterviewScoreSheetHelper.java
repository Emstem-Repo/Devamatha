package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Grade;
import com.kp.cms.bo.admin.GroupTemplateInterview;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.InterviewScoreSheetForm;
import com.kp.cms.to.admission.InterviewSubroundsTO;
import com.kp.cms.to.reports.ScoreSheetTO;
import com.kp.cms.transactions.reports.IScoreSheetTransaction;
import com.kp.cms.transactionsimpl.reports.ScoreSheetTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class InterviewScoreSheetHelper{
	/**
	 * Singleton object of InterviewScoreSheetHelper
	 */
	private static volatile InterviewScoreSheetHelper interviewScoreSheetHelper = null;
	private static final Log log = LogFactory.getLog(InterviewScoreSheetHelper.class);
	private InterviewScoreSheetHelper() {
		
	}
	/**
	 * return singleton object of InterviewScoreSheetHelper.
	 * @return
	 */
	public static InterviewScoreSheetHelper getInstance() {
		if (interviewScoreSheetHelper == null) {
			interviewScoreSheetHelper = new InterviewScoreSheetHelper();
		}
		return interviewScoreSheetHelper;
	}
	public String getScoreSheetSearchQuery(
			InterviewScoreSheetForm interviewScoreSheetForm) throws Exception {
		
		String [] tempArray = interviewScoreSheetForm.getInterviewType();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		log.info("entering into getScoreSheetSearchQuery of ScoreSheetHelper class.");
		String searchCriteria = "select admAppln," +
				"programCourse," +
				"interviewCard.time," +
				"interview.date,"
				+ " student" 
				+" from AdmAppln admAppln" ;
		String interviewType = " join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id in ("
			+ intType +")";		
		searchCriteria = searchCriteria + interviewType;
		searchCriteria = searchCriteria +" and interviewSelected.isCardGenerated=1 " +
				" join interviewSelected.interviewProgramCourse programCourse";

		if( !interviewScoreSheetForm.getEndingTimeHours().trim().equals("00") && interviewScoreSheetForm.getStartingTimeHours()!=null
				&& interviewScoreSheetForm.getStartingTimeMins()!=null && interviewScoreSheetForm.getEndingTimeHours()!=null && interviewScoreSheetForm.getEndingTimeMins()!=null){
			searchCriteria=searchCriteria+" join admAppln.interviewCards interviewCard with interviewCard.time >='"+interviewScoreSheetForm.getStartingTimeHours()+":"+interviewScoreSheetForm.getStartingTimeMins()+"'";
			searchCriteria=searchCriteria+" and interviewCard.time <='"+interviewScoreSheetForm.getEndingTimeHours()+":"+interviewScoreSheetForm.getEndingTimeMins()+"'";
		}else{
			searchCriteria=searchCriteria+" join admAppln.interviewCards interviewCard";
		}
		
		searchCriteria=searchCriteria+" join interviewCard.interview interview ";
		
		searchCriteria=searchCriteria+" join admAppln.students student";
		
		if(interviewScoreSheetForm.getProgramId()!=null && interviewScoreSheetForm.getProgramId().trim().length()>0){
			if(interviewScoreSheetForm.getCourseId()!=null && interviewScoreSheetForm.getCourseId().trim().length()>0){
				searchCriteria=searchCriteria+" where admAppln.courseBySelectedCourseId.id ="+interviewScoreSheetForm.getCourseId();
			}
			else{
				searchCriteria=searchCriteria+" where admAppln.courseBySelectedCourseId.program.id = "+interviewScoreSheetForm.getProgramId();
			}
		}
		//searchCriteria=searchCriteria+" and appDoc.isPhoto=1";
		if(interviewScoreSheetForm.getInterviewStartDate()!=null && interviewScoreSheetForm.getInterviewStartDate().trim().length()>0 && interviewScoreSheetForm.getInterviewEndDate()!=null && interviewScoreSheetForm.getInterviewEndDate().trim().length()>0){
			searchCriteria=searchCriteria+" and interview.date between '"+CommonUtil.ConvertStringToSQLDate(interviewScoreSheetForm.getInterviewStartDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(interviewScoreSheetForm.getInterviewEndDate())+"'";
		}
		searchCriteria=searchCriteria+" and admAppln.appliedYear = "+interviewScoreSheetForm.getYear()+" order by admAppln.applnNo";
		return searchCriteria;
	}
	/**
	 * @param listofStudents
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<String> convertBotoTo(List listofStudents,
			HttpServletRequest request,Map<Integer,String> templateMap) throws Exception{
		List<String> applicantDetailsList = new ArrayList<String>();
		//HttpSession session = request.getSession(false);
		Set<Integer>admApplnIDSet = new HashSet<Integer>();
		Student student = null;
		
		String name="";
		if (listofStudents != null) {
			Iterator listofStudentsItr = listofStudents.iterator();
			while (listofStudentsItr.hasNext()) {
				name="";
				Object[] searchResults = (Object[]) listofStudentsItr.next();
				String message ="";
				
				AdmAppln admAppln=(AdmAppln)searchResults[0];
				int amApplId=admAppln.getId();
				if(searchResults [4]!=null){
					student = (Student) searchResults [4];
				}
//				if(searchResults[2]!=null){
//					applicantSearch.setTime(searchResults[2].toString());
//				}
//				if(searchResults[3]!=null){
//					applicantSearch.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((Date)searchResults[3]), ScoreSheetHelper.SQL_DATEFORMAT,ScoreSheetHelper.FROM_DATEFORMAT));
//				}
				name=admAppln.getPersonalData().getFirstName();
				if(admAppln.getPersonalData().getMiddleName()!=null)
					name=name+admAppln.getPersonalData().getMiddleName();
				if(admAppln.getPersonalData().getLastName()!=null)
					name=name+admAppln.getPersonalData().getLastName();
					
				InterviewProgramCourse interviewProgramCourse=(InterviewProgramCourse)searchResults[1];
				if(templateMap.containsKey(interviewProgramCourse.getId())){
					message = templateMap.get(interviewProgramCourse.getId());
				}
				if(!admApplnIDSet.contains(amApplId) && student!=null && (student.getIsAdmitted()==null || !student.getIsAdmitted())){
					admApplnIDSet.add(amApplId);
					message=message.replace(CMSConstants.TEMPLATE_APPLICATION_NO, Integer.toString(admAppln.getApplnNo()));
					message=message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name);
					message=message.replace(CMSConstants.TEMPLATE_COURSE, admAppln.getCourseBySelectedCourseId().getName());
					message=message.replace(CMSConstants.TEMPLATE_PROGRAM, admAppln.getCourseBySelectedCourseId().getProgram().getName() );
					message=message.replace(CMSConstants.TEMPLATE_PROGRAM_TYPE, admAppln.getCourseBySelectedCourseId().getProgram().getProgramType().getName());
					message=message.replace(CMSConstants.TEMPLATE_INTERVIEW_TYPE, interviewProgramCourse.getName());
					applicantDetailsList.add(message);
				}
			}
			
		}	
		return applicantDetailsList;
	}
	/**
	 * @param interviewScoreSheetForm
	 * @return
	 * @throws Exception
	 */
	public String getInterviewProgramCourseMapQuery(InterviewScoreSheetForm interviewScoreSheetForm) throws Exception {
		String query="from InterviewProgramCourse i where i.isActive=1 and i.program.id="+interviewScoreSheetForm.getProgramId()+" and i.year="+interviewScoreSheetForm.getYear();
		if(interviewScoreSheetForm.getCourseId()!=null && !interviewScoreSheetForm.getCourseId().isEmpty()){
			query=query+" and i.course.id="+interviewScoreSheetForm.getCourseId();
		}
		return query;
	}
	/**
	 * @param interviewScoreSheetForm
	 * @param templateName
	 * @return
	 */
	public String getTemplateQuery(InterviewScoreSheetForm interviewScoreSheetForm, String templateName) throws Exception {
		String [] tempArray = interviewScoreSheetForm.getInterviewType();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		String query="from GroupTemplateInterview g where g.year=" +interviewScoreSheetForm.getYear()+
				" and g.templateName ='"+templateName+"' and g.interviewProgramCourse.id in ("+intType+")";
		if(interviewScoreSheetForm.getCourseId()!=null && !interviewScoreSheetForm.getCourseId().isEmpty()){
			query=query+" and g.course.id ="+interviewScoreSheetForm.getCourseId();
		}else{
			query=query+" and g.course.program.id ="+interviewScoreSheetForm.getProgramId();
		}
		return query;
	}
}
