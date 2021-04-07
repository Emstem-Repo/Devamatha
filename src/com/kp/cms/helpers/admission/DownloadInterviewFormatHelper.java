package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewResultDetail;
import com.kp.cms.forms.admission.DownloadInterviewFormatForm;
import com.kp.cms.to.admission.InterviewResultDetailTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.utilities.CommonUtil;

public class DownloadInterviewFormatHelper {
	/**
	 * Singleton object of DownloadInterviewFormatHelper
	 */
	private static volatile DownloadInterviewFormatHelper downloadInterviewFormatHelper = null;
	private static final Log log = LogFactory.getLog(DownloadInterviewFormatHelper.class);
	private DownloadInterviewFormatHelper() {
		
	}
	/**
	 * return singleton object of DownloadInterviewFormatHelper.
	 * @return
	 */
	public static DownloadInterviewFormatHelper getInstance() {
		if (downloadInterviewFormatHelper == null) {
			downloadInterviewFormatHelper = new DownloadInterviewFormatHelper();
		}
		return downloadInterviewFormatHelper;
	}
	/**
	 * @param ids
	 * @param type
	 * @return
	 */
	public String getQuery(String[] ids, String type) {
		String query="";
		String [] tempArray =ids;
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		if(type.equals("Sub Round")){
			query="select interviewSubRounds.id,interviewSubRounds.noOfInterviewsPerPanel  from InterviewSubRounds interviewSubRounds where interviewSubRounds.id in (" + intType + ") and interviewSubRounds.isActive = 1";
		}else{
			query="select interviewProgramCourse.id,interviewProgramCourse.noOfInterviewsPerPanel from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.id in (" + intType + ") and interviewProgramCourse.isActive = 1";
		}
		return query;
	}
	/**
	 * @param downloadInterviewFormatForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getsearchQuery(DownloadInterviewFormatForm downloadInterviewFormatForm,HttpServletRequest request) throws Exception {
		String query="";
		
		String startTime = downloadInterviewFormatForm.getStartingTimeHours() +":"+ downloadInterviewFormatForm.getStartingTimeMins();
		String endTime = downloadInterviewFormatForm.getEndingTimeHours() +":"+ downloadInterviewFormatForm.getEndingTimeMins();
		
		String[] str1 = request.getParameterValues("interviewSubRoundIds");
		StringBuilder intType1 =new StringBuilder();
		if(str1!=null){
		String [] tempArray1 = str1;
		for(int i=0;i<tempArray1.length;i++){
			intType1.append(tempArray1[i]);
			 if(i<(tempArray1.length-1)){
				 intType1.append(",");
			 }
		}
		}
		String[] str = request.getParameterValues("interviewTypeids");
		String [] tempArray = str;
		String intType ="";
		for(int i=0;i<tempArray.length;i++){
			 intType = intType+tempArray[i];
			 if(i<(tempArray.length-1)){
				 intType = intType+",";
			 }
		}
		if(str1!=null && str1.length>0){
			query="select admAppln.id, admAppln.applnNo,admAppln.appliedYear," +
					"admAppln.personalData.firstName,admAppln.personalData.middleName,admAppln.personalData.lastName," +
					"admAppln.courseBySelectedCourseId.id,interviewResult" +
					" from AdmAppln admAppln" +
					" join admAppln.interviewSelecteds interviewSelected" +
					" with interviewSelected.interviewProgramCourse.id in (" +intType+")"+
					" and interviewSelected.isCardGenerated=1" +
					" join admAppln.interviewCards interviewCard " +
					" left outer join admAppln.interviewResults interviewResult" +
					" with interviewResult.interviewSubRounds.id in ("+intType1+") where admAppln.isCancelled=0 and admAppln.appliedYear ="+downloadInterviewFormatForm.getYear();
			if(downloadInterviewFormatForm.getCourseId()!=null && !downloadInterviewFormatForm.getCourseId().isEmpty()){
				query=query+" and admAppln.courseBySelectedCourseId.id ="+downloadInterviewFormatForm.getCourseId();
			}else{
				query=query+" and admAppln.courseBySelectedCourseId.program.id ="+downloadInterviewFormatForm.getProgramId();
			}
			if(downloadInterviewFormatForm.getInterviewDate()!=null && !downloadInterviewFormatForm.getInterviewDate().isEmpty()){
				query=query+" and interviewCard.interview.date = '"+CommonUtil.ConvertStringToSQLDate(downloadInterviewFormatForm.getInterviewDate())+"'";
			}
			if(!startTime.equals("00:00")){
				query=query+" and interviewCard.time='"+startTime+"'";
			}
			if(!endTime.equals("00:00")){
				query=query+" and interviewCard.time<'"+endTime+"'";
			}
			
			query=query+" group by admAppln.id" +
					" order by admAppln.applnNo";
		}else{
			query="select admAppln.id, admAppln.applnNo,admAppln.appliedYear," +
			"admAppln.personalData.firstName,admAppln.personalData.middleName,admAppln.personalData.lastName," +
			"admAppln.courseBySelectedCourseId.id,interviewResult" +
			" from AdmAppln admAppln" +
			" join admAppln.interviewSelecteds interviewSelected" +
			" with interviewSelected.interviewProgramCourse.id in (" +intType+")"+
			" and interviewSelected.isCardGenerated=1" +
			" join admAppln.interviewCards interviewCard " +
			" left outer join admAppln.interviewResults interviewResult" +
			" with interviewResult.interviewProgramCourse.id  in ("+intType+") where admAppln.isCancelled=0 and admAppln.appliedYear ="+downloadInterviewFormatForm.getYear();
			if(downloadInterviewFormatForm.getCourseId()!=null && !downloadInterviewFormatForm.getCourseId().isEmpty()){
				query=query+" and admAppln.courseBySelectedCourseId.id ="+downloadInterviewFormatForm.getCourseId();
			}else{
				query=query+" and admAppln.courseBySelectedCourseId.program.id ="+downloadInterviewFormatForm.getProgramId();
			}
			if(downloadInterviewFormatForm.getInterviewDate()!=null && !downloadInterviewFormatForm.getInterviewDate().isEmpty()){
				query=query+" and interviewCard.interview.date = '"+CommonUtil.ConvertStringToSQLDate(downloadInterviewFormatForm.getInterviewDate())+"'";
			}
			if(!startTime.equals("00:00")){
				query=query+" and interviewCard.time='"+startTime+"'";
			}
			if(!endTime.equals("00:00")){
				query=query+" and interviewCard.time<'"+endTime+"'";
			}
			
			query=query+" group by admAppln.id" +
					" order by admAppln.applnNo";
		}
		return query;
	}
	/**
	 * @param list
	 * @return
	 */
	public static List<InterviewResultTO> convertBotoTo(List list) {
		List<InterviewResultTO> applicantDetailsList = new ArrayList<InterviewResultTO>();
		if (list != null) {
			Iterator applicantDetailsItr = list.iterator();
			while (applicantDetailsItr.hasNext()) {
				Object[] searchResults = (Object[]) applicantDetailsItr.next();
				InterviewResultTO applicantSearch = new InterviewResultTO();
				InterviewResult interviewResult = null;
				applicantSearch.setApplicationId(searchResults[0].toString());
				applicantSearch.setApplicationNo(searchResults[1].toString());
				applicantSearch.setAppliedYear(searchResults[2].toString());
				if(searchResults[4] == null && searchResults[5] ==null){
					applicantSearch.setApplicantName(searchResults[3].toString());
				}else if(searchResults[4] == null){
					applicantSearch.setApplicantName(searchResults[3].toString() +" "+ searchResults[5].toString());
				}else{
					applicantSearch.setApplicantName(searchResults[3].toString() +" "+ searchResults[4].toString() +" "+ searchResults[5].toString());
				}
				applicantSearch.setCourseId(searchResults[6].toString());
				if( searchResults[7] != null ){
					interviewResult = (InterviewResult) searchResults[7];
				}
				if( interviewResult!=null && interviewResult.getInterviewStatus() != null ){
					applicantSearch.setInterviewStatusId(String.valueOf(interviewResult.getInterviewStatus().getId()));
				}else{
					applicantSearch.setInterviewStatusId("0");
				}
				applicantDetailsList.add(applicantSearch);
			}
		}	
		return applicantDetailsList;
	}
}
