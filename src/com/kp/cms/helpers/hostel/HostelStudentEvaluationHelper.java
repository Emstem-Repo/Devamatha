package com.kp.cms.helpers.hostel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.forms.hostel.HostelStudentEvaluationForm;
import com.kp.cms.to.hostel.DisciplinaryDetailsTO;
import com.kp.cms.to.hostel.HostelStudentEvaluationTO;
import com.kp.cms.transactions.hostel.IHostelStudentEvaluationTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelStudentEvaluationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
	
	public class HostelStudentEvaluationHelper {
		
	private static volatile HostelStudentEvaluationHelper hostelStudentEvaluationHelper=null;
	
	private static Log log=LogFactory.getLog(HostelStudentEvaluationHelper.class);
	
	private HostelStudentEvaluationHelper(){
		
	}
	
	public static HostelStudentEvaluationHelper getInstance(){
	 if(hostelStudentEvaluationHelper==null){
		 hostelStudentEvaluationHelper=new HostelStudentEvaluationHelper();
	 }
	  return hostelStudentEvaluationHelper;
	                                                          
	}
	
	public List<HostelStudentEvaluationTO> convertBOtoTO(List<HlApplicationForm> hlApplicationList,HostelStudentEvaluationForm studentEvaluationForm,int hostelId) throws Exception{
		
		List<HostelStudentEvaluationTO> evaluationTOList=new ArrayList<HostelStudentEvaluationTO>();
		PersonalData personalData=null;
		//HlHostel hostel=(HlHostel)session.get(HlHostel.class,hostelId);
		IHostelStudentEvaluationTransaction transaction=new HostelStudentEvaluationTransactionImpl();
		List<HlDisciplinaryType> disciplinaryTypes=transaction.getDisciplinaryTypes();  
	    List<DisciplinaryDetailsTO> disciplinaryDetailTOList=new ArrayList<DisciplinaryDetailsTO>();
	    Collections.sort(disciplinaryTypes);
	    for(HlDisciplinaryType disciplinaryType: disciplinaryTypes){
		   DisciplinaryDetailsTO disciplinaryDetailsTO=new  DisciplinaryDetailsTO();
		   disciplinaryDetailsTO.setDisciplinaryTypeId(disciplinaryType.getId()+"");
		   disciplinaryDetailsTO.setDisciplinaryType(disciplinaryType.getName());
		   disciplinaryDetailTOList.add(disciplinaryDetailsTO);// disciplinary details for heading	   
	   }
		if(hlApplicationList!=null){	
		 for(HlApplicationForm appForm : hlApplicationList){
			HostelStudentEvaluationTO evaluationTO=new 	HostelStudentEvaluationTO();
			Map<Integer,Integer> studentDisciplinaryMap=new HashMap<Integer,Integer>();
			evaluationTO.setStudentDisciplinaryMap(studentDisciplinaryMap);
			String studentName="";
			if(appForm!=null){
				AdmAppln admAppln=appForm.getAdmAppln();
				if(admAppln!=null){
				personalData=admAppln.getPersonalData();
				if(personalData!=null)
				{
					if(personalData.getMiddleName() == null && personalData.getLastName() ==null){  
						studentName=personalData.getFirstName();                       // for getting studentName
					}else if(personalData.getLastName() ==null){
						studentName=personalData.getFirstName() +" "+ personalData.getMiddleName();
					}else if(personalData.getMiddleName() ==null){
						studentName=personalData.getFirstName() +" "+ personalData.getLastName();
					}
					else
					{
						studentName=personalData.getFirstName() +" "+ personalData.getMiddleName()+" "+personalData.getLastName();
					}
				}
			}
		    Student student=null;		
			evaluationTO.setCourse(admAppln.getCourseBySelectedCourseId().getName());
			evaluationTO.setStudentName(studentName);
			List<Student> studentList=new ArrayList<Student>();
			if(admAppln.getStudents()!=null){
				studentList.addAll(admAppln.getStudents());
			}
			if(studentList !=null && studentList.size()>0){
				evaluationTO.setRegisterNo(studentList.get(0).getRegisterNo());
				student=studentList.get(0);
			 }
			 int academicYear=Integer.parseInt(studentEvaluationForm.getAcademicYear());
			 Date fromDate=CommonUtil.ConvertStringToDate(studentEvaluationForm.getFromDate());
			 Date toDate=CommonUtil.ConvertStringToDate(studentEvaluationForm.getToDate());
			 Long classAttended=0l;
			 Long totalClasses=0l;
			 if(student!=null){
				 List attendedList=transaction.getAttendanceStudentsAttended(fromDate, toDate,academicYear,student.getId());
				 if(attendedList.size()>0){
				     classAttended=(Long)attendedList.get(0);
				   }
				 
				 List totalAttendance=transaction.getAttendanceStudents(fromDate, toDate,academicYear,student.getId());
				 if(totalAttendance.size()>0){
					 totalClasses=(Long)totalAttendance.get(0);
				 }
			 }
			 if(classAttended!=null && totalClasses!=null && totalClasses>0){
				 
				Double classAttendedDoublValue=classAttended.doubleValue();
				Double totalClassDoublValue=totalClasses.doubleValue();
			    Double attnPercentage=(classAttendedDoublValue/totalClassDoublValue)*100;
			    float percentage=CommonUtil.roundToDecimal(attnPercentage.floatValue(),2);
			    evaluationTO.setAttendancePercentage(percentage+"");
			 }
			 ExamMarksEntryBO examMarksEntryBO=null;
			 if(student!=null){
			    examMarksEntryBO=transaction.getExamMarksEntryBO(student.getId());
			 }
			  if(examMarksEntryBO!=null){
			  Set<ExamMarksEntryDetailsBO> markEntryDetails=examMarksEntryBO.getExamMarksEntryDetailsBOset();
			  Double totalTheoryMarks=0.0;
			  Double totalPracticalMarks=0.0;
			  Double totalMarksScored=0.0;
			  Double maxSubjectMarks=0.0;
			  Double percentageObtained=0.0;
			  Double totalSubjectMarks=0.0;
			  String result="";
			  for(ExamMarksEntryDetailsBO entryDetails:markEntryDetails){  			  
				  Subject subject=transaction.getSubject(entryDetails.getSubjectId());
				  if(subject!=null){
				  if(subject.getIsTheoryPractical().equalsIgnoreCase("P")){
						  Double practicalMarks=Double.valueOf(entryDetails.getPracticalMarks());  
						  totalPracticalMarks=totalPracticalMarks+practicalMarks;
						  if(subject.getTotalMarks()!=null){
							  maxSubjectMarks=subject.getTotalMarks().doubleValue();
							  totalSubjectMarks=totalSubjectMarks+maxSubjectMarks;
						  }
						  totalMarksScored=totalPracticalMarks;
						  if(maxSubjectMarks>0){
							  percentageObtained=(totalMarksScored/totalSubjectMarks)*100;
							  float percentage=CommonUtil.roundToDecimal(percentageObtained.floatValue(),2);
							    evaluationTO.setTotalPercentageObtained(percentage+"");
						  }
						  if(subject.getPassingMarks()!=null){
						  Double passMarks=subject.getPassingMarks().doubleValue();
						  if(totalMarksScored>=passMarks){
							  result="Pass";
						  }
						  else 
						  {
							  result="Fail";
						  }
					  }
				  }
				  else if(subject.getIsTheoryPractical().equalsIgnoreCase("T")){
						  if(entryDetails.getTheoryMarks()!=null){
							  Double theoryMarks=Double.valueOf(entryDetails.getTheoryMarks());
							  totalTheoryMarks=totalTheoryMarks+theoryMarks; 
							  totalMarksScored=totalTheoryMarks;
						  }
						  if(subject.getTotalMarks()!=null){
							  maxSubjectMarks=subject.getTotalMarks().doubleValue();
							  totalSubjectMarks=totalSubjectMarks+maxSubjectMarks;
						  }
						  if(maxSubjectMarks>0){
							  percentageObtained=(totalMarksScored/totalSubjectMarks)*100;
							  float percentage=CommonUtil.roundToDecimal(percentageObtained.floatValue(),2);
							  evaluationTO.setTotalPercentageObtained(percentage+"");
						  }
						  if(subject.getPassingMarks()!=null){
						  Double passMarks=subject.getPassingMarks().doubleValue();
						  if(totalMarksScored>=passMarks){
							  result="Pass";
						  }
						  else
						  {
							  result="Fail";
						  }
						 }
					  }
				  else if(subject.getIsTheoryPractical().equalsIgnoreCase("B")){
						  if(entryDetails.getPracticalMarks()!=null){
							  Double practicalMarks=Double.valueOf(entryDetails.getPracticalMarks());  
							  totalPracticalMarks=totalPracticalMarks+practicalMarks; 
						  }
						  if(entryDetails.getTheoryMarks()!=null){
							  Double theoryMarks=Double.valueOf(entryDetails.getTheoryMarks());
							  totalTheoryMarks=totalTheoryMarks+theoryMarks; 
							  totalMarksScored=totalPracticalMarks+totalTheoryMarks;
						  }
						  if(subject.getTotalMarks()!=null){
							  maxSubjectMarks=subject.getTotalMarks().doubleValue();
							  totalSubjectMarks=totalSubjectMarks+maxSubjectMarks;
							  
						  }
						  if(maxSubjectMarks>0){
							  percentageObtained=(totalMarksScored/totalSubjectMarks)*100;
							  float percentage=CommonUtil.roundToDecimal(percentageObtained.floatValue(),2);
							    evaluationTO.setTotalPercentageObtained(percentage+"");
						  }
						  if(subject.getPassingMarks()!=null){
							  Double passMarks=subject.getPassingMarks().doubleValue();
						  
						  if(totalMarksScored>=passMarks){
							  result="Pass";
						  }
						  else
						  {
							  result="Fail";
						  }
						 } 
				    }
				  }
				  evaluationTO.setMaxMarks(totalSubjectMarks+"");
				  evaluationTO.setMarksObtained(totalMarksScored+"");
				  evaluationTO.setResult(result);
			  }
			 }
		   }
		   List<HlRoomTransaction> roomTxns=new ArrayList<HlRoomTransaction>();
		   roomTxns.addAll(appForm.getHlRoomTransactions());
		   if(roomTxns!=null){
			   evaluationTO.setRoomNo(roomTxns.get(0).getHlRoom().getName());	 
		   }
		   
		   if(appForm.getHlDisciplinaryDetailses()!=null){
			   for(HlDisciplinaryDetails disciplinaryDetails:appForm.getHlDisciplinaryDetailses()){
				   
				   if(disciplinaryDetails.getHlDisciplinaryType()!=null){
				   Integer disciplinaryTypeId=disciplinaryDetails.getHlDisciplinaryType().getId();
				   Integer noOfOccurance=studentDisciplinaryMap.get(disciplinaryTypeId);
				   if(noOfOccurance==null && !(studentDisciplinaryMap.containsKey(disciplinaryTypeId))){
					   studentDisciplinaryMap.put(disciplinaryTypeId,1); 
				   }
				   else 
				   {
					   studentDisciplinaryMap.put(disciplinaryTypeId, noOfOccurance+1);
				   }
				   evaluationTO.setStudentDisciplinaryMap(studentDisciplinaryMap);
			     }
			   }
			} 
		   evaluationTO.setDisciplinaryDetails(disciplinaryDetailTOList);
		   evaluationTOList.add(evaluationTO);
		   studentEvaluationForm.setEvaluationTO(evaluationTO);
		  } 
	    }
		log.info("exit convertBOtoTO of HostelStudentEvaluationHelper");
		return evaluationTOList;
	  }
    }
	
