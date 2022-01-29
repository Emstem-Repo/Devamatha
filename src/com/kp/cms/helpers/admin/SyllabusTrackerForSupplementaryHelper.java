package com.kp.cms.helpers.admin;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.SyllabusTrackerForSupplementaryForm;
import com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm;
import com.kp.cms.handlers.admin.SyllabusTrackerForSupplementaryHandler;
import com.kp.cms.handlers.studentLogin.SyllabusDisplayForStudentHandler;
import com.kp.cms.to.admin.SyllabusTrackerForSupplementaryTo;
import com.kp.cms.to.studentLogin.SyllabusDisplayForStudentTo;
import com.kp.cms.transactions.studentLogin.ISyllabusDisplayForStudentTransaction;
import com.kp.cms.transactionsimpl.studentLogin.SyllabusDisplayForStudentTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;


public class SyllabusTrackerForSupplementaryHelper {
	private static final Log log = LogFactory.getLog(SyllabusTrackerForSupplementaryHelper.class);	
	public static volatile SyllabusTrackerForSupplementaryHelper syllabusTrackerForSupplementaryHelper = null;
	
	public static SyllabusTrackerForSupplementaryHelper getInstance() {
		if (syllabusTrackerForSupplementaryHelper == null) {
			syllabusTrackerForSupplementaryHelper = new SyllabusTrackerForSupplementaryHelper();
		}
		return syllabusTrackerForSupplementaryHelper;
	}

	
	public List<SyllabusTrackerForSupplementaryTo> convertBotoTo(List<Object[]> boList,SyllabusTrackerForSupplementaryForm form)throws Exception{
		ISyllabusDisplayForStudentTransaction transaction=SyllabusDisplayForStudentTransactionImpl.getInstance();
		List<SyllabusTrackerForSupplementaryTo> toList=new ArrayList<SyllabusTrackerForSupplementaryTo>();
		Calendar cal = Calendar.getInstance();
		int curSchemeDurationId=0;
		
	if(boList!=null && !boList.isEmpty()){
		Iterator<Object[]> itr=boList.iterator();
		int  currentAcademicYear=CurrentAcademicYear.getInstance().getAcademicyear();
		
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH);
		int currentMonth=month+1;
		int currentYear=now.get(Calendar.YEAR);
		while(itr.hasNext()){
			Object[] bo=(Object[])itr.next();
			SyllabusTrackerForSupplementaryTo to=new SyllabusTrackerForSupplementaryTo();
			Student studentBo=transaction.getStudentDetails(bo[6].toString());
		if(studentBo!=null){
			int totalNumSemNo=studentBo.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getNoScheme();
			int admitedYear=studentBo.getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getYear();
			int currentSemNo=studentBo.getClassSchemewise().getCurriculumSchemeDuration().getSemesterYearNo();
			int  studentCurrentAcademicyeaar=studentBo.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
			Date endDate=studentBo.getClassSchemewise().getCurriculumSchemeDuration().getEndDate();
			Date startDate=studentBo.getClassSchemewise().getCurriculumSchemeDuration().getStartDate();
			cal.setTime(startDate);
			
			cal.setTime(endDate);
			int courseFinalSemEndYear=cal.get(Calendar.YEAR);
			
			// /* getting the  number of chances to left
			int chance=0;
			int semNo=Integer.parseInt(bo[0].toString());
			 
			 Date examAttemptedYear = (Date)(bo[1]);
			 cal.setTime(examAttemptedYear);
			 int firstAttemptYear=cal.get(Calendar.YEAR);
			 if(semNo!=0){
				 // /* if student has completed his course but he has backlocks 
				 if(currentSemNo==totalNumSemNo && (studentCurrentAcademicyeaar < currentAcademicYear)){
						 
					 for(int attemptYear=firstAttemptYear; attemptYear<=currentAcademicYear; attemptYear++){

						 if(semNo % 2==1){ // if semister number is odd 
							 if(firstAttemptYear==attemptYear){
								 chance=chance+1;
							 }else{
								 if(attemptYear < currentAcademicYear){
									 chance=chance+1;
									 if(attemptYear >= 2013){
										 chance=chance+1;
										 }
									 // This condition is for adding the additional chance for finalyear and passed out students in january
									 
										 if(attemptYear >=courseFinalSemEndYear){
											 if(attemptYear <= 2013){
												 chance=chance+1;
											 }
									 }
									 
									 
								 }else if(attemptYear == currentAcademicYear) {
									 
									 if(firstAttemptYear >= 2013){
										 	chance=chance+1;
										 		
										 }else{
												 if(attemptYear <= 2013){
													 if(attemptYear >=courseFinalSemEndYear){
													 chance=chance+1;
												 }
											 }
										 }
									 
									/*// This condition is for adding the additional chance for passed out students in january
									 
										 if(currentMonth >1 && currentYear > currentAcademicYear){
											 chance=chance+1;
										 }*/
									 	 
									 if(currentMonth > 10 || currentYear > currentAcademicYear){
										 chance=chance+1;
									 }
									 if(currentMonth > 3 && currentYear > currentAcademicYear){
										 chance=chance+1;
									 }
										
								 }
							 }
							 
							 
						 }else if(semNo % 2==0){ // if semister number is even
							 if(attemptYear < currentAcademicYear){
								 chance=chance+1;
								 if(attemptYear >= 2013){
									 chance=chance+1;
									 }
								// This condition is for adding the additional chance for  passed out students in june
								if(attemptYear <= 2013){ 
									 if(attemptYear >=courseFinalSemEndYear){
										 chance=chance+1;
									 }
								 }else{
									 if((currentSemNo==totalNumSemNo) && (attemptYear >=courseFinalSemEndYear)){ 
										 chance=chance+1;
									 	} 
								 }
								 
							 }else if(attemptYear == currentAcademicYear) {
								 		chance=chance+1;
								 
									 if(currentMonth > 10 || currentYear > currentAcademicYear){
										 chance=chance+1;
									 }
									 if(currentMonth > 3 && currentYear > currentAcademicYear){
										 chance=chance+1;
									 	}
									// This condition is for adding the additional chance  passed out students in june 
									 if(attemptYear <= 2013){ 
										 if( currentYear > currentAcademicYear){
											 chance=chance+1;
										 }else{
											 if(currentMonth > 6){
												 chance=chance+1;
											 }
										 }
									 }else{
										 if((currentSemNo==totalNumSemNo) && (attemptYear >=courseFinalSemEndYear)){ 
											 if( currentYear > currentAcademicYear){
												 chance=chance+1;
											 }else{
												 if(currentMonth > 6){
													 chance=chance+1;
												 }
											 }
										 } 
									 }
									 
									// This condition is for adding the additional chance  passed out students in june 
									 if((currentSemNo==totalNumSemNo)&& (currentMonth > 6) && (currentYear > currentAcademicYear)){ 
										 chance=chance+1;
									 	}
									 
							 }
						 }
					 }
				 }
				 // */ if student has completed his course but he has backlocks 
				 
				 // /* if student is a finalyear student 
				 else if((currentSemNo==totalNumSemNo) && (studentCurrentAcademicyeaar == currentAcademicYear)){
					 
					 for(int attemptYear=firstAttemptYear; attemptYear<=currentAcademicYear; attemptYear++){

						 if(semNo % 2==1){ // if semister number is odd 
							 if(firstAttemptYear==attemptYear){
								 chance=chance+1;
							 }else{
								 if(attemptYear < currentAcademicYear){
									 chance=chance+1;
									 if(attemptYear >= 2013){
										 chance=chance+1;
									 }
								 }else if(attemptYear == currentAcademicYear) {
									 if(firstAttemptYear >= 2013){
										 	chance=chance+1;
										 }
									 if(currentMonth > 10 || currentYear > currentAcademicYear){
										 chance=chance+1;
									 	}
									 if(currentMonth > 3 && currentYear > currentAcademicYear){
										 chance=chance+1;
								 		}
									/*// This condition is for adding the additional chance  passed out students in january 
										 if(currentMonth >1 && currentYear > currentAcademicYear ){
											 chance=chance+1;
										 }*/
									 
								 }
							 }
							 
						 }else if(semNo % 2==0){ // if semister number is even
							 if(attemptYear < currentAcademicYear){
								 chance=chance+1;
								 if(attemptYear >= 2013){
									 chance=chance+1;
									 }
							 
							 }else if(attemptYear == currentAcademicYear) {
								 chance=chance+1;
								 
							// This condition is for adding the additional chance  passed out students in june 
								 if((currentSemNo==totalNumSemNo)&& (currentMonth > 6) && (currentYear > currentAcademicYear)){ 
									 chance=chance+1;
								 	}
								 if(currentMonth > 10 || currentYear > currentAcademicYear){
									 chance=chance+1;
								 }
								 
								 if(currentMonth > 3 && currentYear > currentAcademicYear){
								 chance=chance+1;
							 	}
								
								 
							 }
						 }
						 
					 }
				 
				}
				// */ if student is a finalyear student 
				 
				 // /* if student is in middle of the course
				 else{
					
					 for(int attemptYear=firstAttemptYear; attemptYear<=currentAcademicYear; attemptYear++){
						 if(semNo % 2==1){ // if semister number is odd 
							 if(firstAttemptYear==attemptYear){
								 chance=chance+1;
							 }else{
								 if(attemptYear < currentAcademicYear){
									 chance=chance+1;
									 if(attemptYear >= 2013){
										 chance=chance+1;
										 }
								 }else if(attemptYear == currentAcademicYear) {
									 if(firstAttemptYear >= 2013){
										 chance=chance+1;
									 }
									 if(currentMonth > 10 || currentYear > currentAcademicYear){
											 chance=chance+1;
									 }
									 if(currentMonth > 3 && currentYear > currentAcademicYear){
										 chance=chance+1;
									 }
								 }
							 }
							 
						 }else if(semNo % 2==0){ // if semister number is even
							 if(attemptYear < currentAcademicYear){
								 chance=chance+1;
								 if(attemptYear >= 2013){
									 chance=chance+1;
									 }
								 
							 }else if(attemptYear == currentAcademicYear) {
								 chance=chance+1;
								 /*if(currentSemNo==(totalNumSemNo-1)  && attemptYear ==courseFinalYear){
									 chance=chance+1;
								 }else{
									 if(currentMonth > 3){
										 chance=chance+1;
									 }
								 }*/
								 
								 
								 
								 
								 if(currentMonth > 10 || currentYear > currentAcademicYear){
										 chance=chance+1;
								 	}
								 if(currentMonth > 3 && currentYear > currentAcademicYear){
									 chance=chance+1;
								 }
							 }
						 }
					 }
				 }
				 // */if student is in middle of the course
			
			 }
			// */ getting the  number of chances to left
		
			 String numberOfChancesLeft=null;
			 if(chance<6){
				numberOfChancesLeft=String.valueOf(6-chance);
				 to.setNumOfChancesLeft(numberOfChancesLeft);
			 }else{
				 
				 to.setNumOfChancesLeft("-");
				 
			 }
			 
			 String subjectName=bo[3].toString();
			 
			 to.setPaperName(subjectName);
			 to.setCourseId(Integer.parseInt(bo[4].toString()));
			 
			 form.setCourseid(Integer.parseInt(bo[4].toString()));
			 form.setSemNo(semNo);
			 
			 String papercode=bo[2].toString();
			 String replacedPaperCode="";
			 
			 form.setPaperCode(papercode);
			 to.setPaperCode(papercode);
			 String year=null;
			 if(chance<3){
				 year=String.valueOf(admitedYear);
				}else if(chance<6){
					List<Object[]> admList=SyllabusTrackerForSupplementaryHandler.getInstance().getCurrentStudyingBatchjoiningYear(form);
					Iterator<Object[]> itr1=admList.iterator();
					while(itr1.hasNext()){
						Object[] obj=(Object[])itr1.next();
						String currentBatchJoiningYear=obj[0].toString();
						curSchemeDurationId=(Integer.parseInt(obj[1].toString()));
						String subjectName1=SyllabusTrackerForSupplementaryHandler.getInstance().getcurrentStudyingBatchSubjectName(curSchemeDurationId,form);
						
						if(subjectName1!=null && !subjectName1.isEmpty()&& subjectName1.equalsIgnoreCase(subjectName)){
							year=currentBatchJoiningYear;
						}else{
							year=String.valueOf(admitedYear);
						}
					}
				}
			 
			 if(papercode.endsWith("-09")){
				 replacedPaperCode=papercode.replace("-09", " ");
			 }else if(papercode.endsWith("-10")){
				 replacedPaperCode=papercode.replace("-10", " ");
			 }else if(papercode.endsWith("-11")){
				 replacedPaperCode=papercode.replace("-11", " ");
			 }else if(papercode.endsWith("-12")){
				 replacedPaperCode=papercode.replace("-12", " ");
			 }else if(papercode.endsWith("-13")){
				 replacedPaperCode=papercode.replace("-13", " ");
			 }else{
				 replacedPaperCode=papercode;
			 }
			 
			 String file=null;
				String soursePath=null;
				Properties prop=new Properties();
				InputStream stream=SyllabusDisplayForStudentHandler.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
				soursePath=prop.getProperty("knowledgepro.admin.display.syllabus.location");
				if(chance<6){
					file=year+"/"+replacedPaperCode;
				}else if(chance >=6){
					year=String.valueOf(admitedYear);
					file=year+"/"+"Syllabus File Not Found";
				}
					String fileName = file.trim();
					soursePath = soursePath+"/"+ fileName+".pdf";
					File filephoto = new File(soursePath);
				boolean exists = filephoto.exists();  
	            if (!exists) {  
	            	to.setIsChancesAvailable(false);          
	            }else{
	            	to.setIsChancesAvailable(true);
	            }
			 
			 
			 to.setAdmitedYear(year);
			 to.setSemNo(semNo);
			 to.setRegisterNo(bo[6].toString());
			 to.setJoiningYear(bo[5].toString());
			 to.setExamFirstAttemptedYear(String.valueOf(firstAttemptYear));
			 toList.add(to);
			}
		}
	}
	
		return toList;
	}

}
