package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Grade;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.ScoreSheetForm;
import com.kp.cms.to.admission.InterviewSubroundsTO;
import com.kp.cms.to.reports.ScoreSheetTO;
import com.kp.cms.transactions.reports.IScoreSheetTransaction;
import com.kp.cms.transactionsimpl.reports.ScoreSheetTransactionImpl;
import com.kp.cms.utilities.CommonUtil;



public class ScoreSheetHelper {
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	/**
	 * Singleton object of ScoreSheetHelper
	 */
	private static volatile ScoreSheetHelper scoreSheetHelper = null;
	private static final Log log = LogFactory.getLog(ScoreSheetHelper.class);
	private ScoreSheetHelper() {
		
	}
	/**
	 * return singleton object of ScoreSheetHelper.
	 * @return
	 */
	public static ScoreSheetHelper getInstance() {
		if (scoreSheetHelper == null) {
			scoreSheetHelper = new ScoreSheetHelper();
		}
		return scoreSheetHelper;
	}
	

	/**
	 * Method to convert BO to TO
	 * @param applicantDetails
	 * @param interviewTypeId
	 * @param interviewSubroundId
	 * @return
	 */
	public List<ScoreSheetTO> convertBotoTo(List listofStudents, HttpServletRequest request) throws Exception {
		List<ScoreSheetTO> applicantDetailsList = new ArrayList<ScoreSheetTO>();
		HttpSession session = request.getSession(false);
		Set<Integer>admApplnIDSet = new HashSet<Integer>();
		IScoreSheetTransaction iScoreSheetTransaction=new ScoreSheetTransactionImpl();
		List<Grade> gradeList=iScoreSheetTransaction.getListOfGrades();
		Student student = null;
		if (listofStudents != null) {
			Iterator listofStudentsItr = listofStudents.iterator();
			int count = 1;
			while (listofStudentsItr.hasNext()) {
				Object[] searchResults = (Object[]) listofStudentsItr.next();
				
				AdmAppln admAppln=(AdmAppln)searchResults[0];
				int amApplId=admAppln.getId();
				if(searchResults [4]!=null){
					student = (Student) searchResults [4];
				}
				//if(!admApplnIDSet.contains(amApplId) && student!=null && student.getIsAdmitted()!=null && !student.getIsAdmitted()){
				if(!admApplnIDSet.contains(amApplId) && student!=null && (student.getIsAdmitted()==null || !student.getIsAdmitted())){
					ScoreSheetTO applicantSearch = new ScoreSheetTO();
						applicantSearch.setApplicationId(Integer.toString(admAppln.getId()));
						applicantSearch.setApplicationNo(Integer.toString(admAppln.getApplnNo()));
						PersonalData personalData=admAppln.getPersonalData();
						if(personalData.getMiddleName() == null && personalData.getLastName() ==null){
							applicantSearch.setApplicantName(personalData.getFirstName());
						}else if(personalData.getLastName() ==null){
							applicantSearch.setApplicantName(personalData.getFirstName() +" "+ personalData.getMiddleName());
						}else if(personalData.getMiddleName() ==null){
							applicantSearch.setApplicantName(personalData.getFirstName() +" "+ personalData.getLastName());
						}
						else{
							applicantSearch.setApplicantName(personalData.getFirstName() +" "+ personalData.getMiddleName() +" "+ personalData.getLastName());
						}
						applicantSearch.setGender(personalData.getGender());
						if(personalData.getDateOfBirth()!=null)
						applicantSearch.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getDateOfBirth()), ScoreSheetHelper.SQL_DATEFORMAT,ScoreSheetHelper.FROM_DATEFORMAT));
						if(searchResults[2]!=null){
							applicantSearch.setTime(searchResults[2].toString());
						}
						if(searchResults[3]!=null){
							applicantSearch.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((Date)searchResults[3]), ScoreSheetHelper.SQL_DATEFORMAT,ScoreSheetHelper.FROM_DATEFORMAT));
						}
						if(CMSConstants.LINK_FOR_CJC){
							Set<ApplnDoc> appDocs=admAppln.getApplnDocs();
							if(appDocs!=null && !appDocs.isEmpty()){
								Iterator<ApplnDoc> docItr=appDocs.iterator();
								while (docItr.hasNext()) {
									ApplnDoc applnDoc = (ApplnDoc) docItr.next();
									if(applnDoc.getIsPhoto()){
										if(applnDoc.getDocument()!=null){
											applicantSearch.setPhotoBytes(applnDoc.getDocument());
										}
									}
								}
							}
						}else{
							applicantSearch.setStudentId(student.getId());
						}
						if(searchResults[1]!=null){
							InterviewProgramCourse interviewProgramCourse=(InterviewProgramCourse)searchResults[1];
							if(interviewProgramCourse.getName()!=null && interviewProgramCourse.getName().trim().length()>0){
								applicantSearch.setName(interviewProgramCourse.getName());
								applicantSearch.setGradeList(gradeList);
								}
							if(!interviewProgramCourse.getInterviewSubRoundses().isEmpty()){
								Set<InterviewSubRounds> interviewSubRoundsSet=interviewProgramCourse.getInterviewSubRoundses();
								if(!interviewSubRoundsSet.isEmpty()){
								List<InterviewSubroundsTO> interviewSubroundsTOList=getListFromSetOfinterviewSubroundsTO(interviewSubRoundsSet,gradeList);
								if(!interviewSubroundsTOList.isEmpty()){
								applicantSearch.setInterviewSubRoundsTOList(interviewSubroundsTOList);
								}
								}
							}
						}
						
						applicantSearch.setCount(count);
						session.setAttribute("image_" + count, applicantSearch);
						applicantDetailsList.add(applicantSearch);
						++count;
						admApplnIDSet.add(amApplId);
					}
			}
			
		}	
		return applicantDetailsList;
	}
	
	
	/**
	 * Constructs score sheet search query.
	 * @param scoreSheetForm
	 * @return
	 */
	public String getScoreSheetSearchQuery(ScoreSheetForm scoreSheetForm){
		log.info("entering into getScoreSheetSearchQuery of ScoreSheetHelper class.");
		String searchCriteria = "select admAppln," +
				"programCourse," +
				"interviewCard.time," +
				"interview.date,"
				+ " student" 
				+" from AdmAppln admAppln" ;
		if (scoreSheetForm.getInterviewType().length > 0) {
			String [] tempArray = scoreSheetForm.getInterviewType();
			StringBuilder intType =new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
		
		String interviewType = " join admAppln.interviewSelecteds interviewSelected with interviewSelected.interviewProgramCourse.id in ("
				+ intType +")";		
		searchCriteria = searchCriteria + interviewType;
	}
		searchCriteria = searchCriteria +" and interviewSelected.isCardGenerated=1 " +
				" join interviewSelected.interviewProgramCourse programCourse";

		if( !scoreSheetForm.getEndingTimeHours().trim().equals("00") && scoreSheetForm.getStartingTimeHours()!=null
				&& scoreSheetForm.getStartingTimeMins()!=null && scoreSheetForm.getEndingTimeHours()!=null && scoreSheetForm.getEndingTimeMins()!=null){
			searchCriteria=searchCriteria+" join admAppln.interviewCards interviewCard with interviewCard.time ='"+scoreSheetForm.getStartingTimeHours()+":"+scoreSheetForm.getStartingTimeMins()+"'";
			searchCriteria=searchCriteria+" and interviewCard.time <'"+scoreSheetForm.getEndingTimeHours()+":"+scoreSheetForm.getEndingTimeMins()+"'";
		}else{
			searchCriteria=searchCriteria+" join admAppln.interviewCards interviewCard";
		}
		
		searchCriteria=searchCriteria+" join interviewCard.interview interview ";
		
		searchCriteria=searchCriteria+" join admAppln.students student";
		
		if(scoreSheetForm.getProgramId()!=null && scoreSheetForm.getProgramId().trim().length()>0){
			if(scoreSheetForm.getCourseId()!=null && scoreSheetForm.getCourseId().trim().length()>0){
				searchCriteria=searchCriteria+" where admAppln.courseBySelectedCourseId.id ="+scoreSheetForm.getCourseId();
			}
			else{
				searchCriteria=searchCriteria+" where admAppln.courseBySelectedCourseId.program.id = "+scoreSheetForm.getProgramId();
			}
		}
		//searchCriteria=searchCriteria+" and appDoc.isPhoto=1";
		if(scoreSheetForm.getInterviewStartDate()!=null && scoreSheetForm.getInterviewStartDate().trim().length()>0 && scoreSheetForm.getInterviewEndDate()!=null && scoreSheetForm.getInterviewEndDate().trim().length()>0){
			searchCriteria=searchCriteria+" and interview.date between '"+CommonUtil.ConvertStringToSQLDate(scoreSheetForm.getInterviewStartDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(scoreSheetForm.getInterviewEndDate())+"'";
		}
		if(scoreSheetForm.getExamCenterId()!=null && !scoreSheetForm.getExamCenterId().isEmpty() ){
			searchCriteria=searchCriteria+" and admAppln.examCenter.id="+scoreSheetForm.getExamCenterId();
		}
		
		searchCriteria=searchCriteria+" and admAppln.appliedYear = "+scoreSheetForm.getYear()+" order by admAppln.applnNo";
		return searchCriteria;
	}
	/**
	 * converting the set of interview subrounds into list of InterviewSubroundsTO
	 * @param interviewSubRoundsSet
	 * @return
	 */
	public List<InterviewSubroundsTO> getListFromSetOfinterviewSubroundsTO(Set<InterviewSubRounds> interviewSubRoundsSet,List<Grade> gradeList) throws Exception{
		Iterator<InterviewSubRounds> interviewSubRoundsIterator=interviewSubRoundsSet.iterator();
		List<InterviewSubroundsTO> interviewSubroundsTOList=new ArrayList<InterviewSubroundsTO>();
//		IScoreSheetTransaction iScoreSheetTransaction=new ScoreSheetTransactionImpl();
//		List<Grade> gradeList=iScoreSheetTransaction.getListOfGrades();
		while(interviewSubRoundsIterator.hasNext()){
			InterviewSubRounds interviewSubRounds=(InterviewSubRounds)interviewSubRoundsIterator.next();
			if(interviewSubRounds.getIsActive()){
				InterviewSubroundsTO interviewSubroundsTO=new InterviewSubroundsTO();
				//interviewSubroundsTO.setCDate(interviewSubRounds.getCreatedDate().toString());
				interviewSubroundsTO.setId(interviewSubRounds.getId());
				interviewSubroundsTO.setName(interviewSubRounds.getName());
				if(!gradeList.isEmpty()){
					interviewSubroundsTO.setGradeList(gradeList);
				}
				interviewSubroundsTOList.add(interviewSubroundsTO);
			}
		}
		Collections.sort(interviewSubroundsTOList);
		return interviewSubroundsTOList;
	}
}
