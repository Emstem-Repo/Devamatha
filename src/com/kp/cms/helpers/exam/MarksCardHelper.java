package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ConsolidateMarksCardNoGen;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.MarksCardNoGen;
import com.kp.cms.bo.exam.MarksCardSiNoGen;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.forms.exam.ConsolidateMarksCardForm;
import com.kp.cms.forms.exam.MarksCardForm;
import com.kp.cms.handlers.exam.MarksCardHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.transactions.exam.IMarksCardTransaction;
import com.kp.cms.transactionsimpl.exam.MarksCardTransactionImpl;

public class MarksCardHelper {
	private static MarksCardHelper marksCardHelper=null;
	public static MarksCardHelper getInstance(){
		if(marksCardHelper==null){
			marksCardHelper = new MarksCardHelper();
		}
		return marksCardHelper;
	}
	public List<MarksCardNoGen> convertData(List<Integer> studentId,
			MarksCardForm marksCardForm,int currentNO)throws Exception {
		// TODO Auto-generated method stub
		IMarksCardTransaction transaction = new MarksCardTransactionImpl();
		int count=0;
		List<MarksCardNoGen> boList = new ArrayList<MarksCardNoGen>();
		if(!studentId.isEmpty()){
			Iterator<Integer> itr = studentId.iterator();
			while (itr.hasNext()) {
				Integer integer = (Integer) itr.next();
				MarksCardNoGen bo = new MarksCardNoGen();
				Student student = transaction.getStudentBoDetails(integer);
				
				student.setId(integer);
				bo.setStudentId(student);
				
				Classes classes = student.getClassSchemewise().getClasses();
				bo.setClassId(classes);
				
				ExamDefinition examDef = new ExamDefinition();
				examDef.setId(Integer.parseInt(marksCardForm.getExamName()));
				bo.setExamId(examDef);
				bo.setMarksCardNo(Integer.toString(currentNO));
				if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
					bo.setIsDuplicate(true);
				}else{
					bo.setIsDuplicate(false);
				}
				if(marksCardForm.getRadioId().equalsIgnoreCase("1")){
					bo.setIsConsolidate(true);
				}else{
					bo.setIsConsolidate(false);
				}
				boList.add(bo);
				currentNO++;
				count++;
			}
		}
		marksCardForm.setTotalCount(Integer.toString(count));
		return boList;
	}
	public MarksCardNoGen convertSingleStudentFormtoTo(MarksCardForm marksCardForm,
			int currentNO)throws Exception {
		// TODO Auto-generated method stub
		MarksCardNoGen bo = new MarksCardNoGen();
		Student student = new Student();
		student.setId(Integer.parseInt(marksCardForm.getBaseStudentId()));
		bo.setStudentId(student);
		Classes classes = new Classes();
		classes.setId(Integer.parseInt(marksCardForm.getClassId()));
		bo.setClassId(classes);
		ExamDefinition examDefinition = new ExamDefinition();
		examDefinition.setId(Integer.parseInt(marksCardForm.getExamName()));
		bo.setExamId(examDefinition);
		bo.setMarksCardNo(Integer.toString(currentNO));
		bo.setIsDuplicate(true);
		return bo;
	}
	
	
	
	
	
	
	public List<ConsolidateMarksCardNoGen> convertDataDetails(List<Integer> studentId,
			ConsolidateMarksCardForm consolidateMarksCardForm,int currentNO)throws Exception {
		// TODO Auto-generated method stub
		IMarksCardTransaction transaction = new MarksCardTransactionImpl();
		int count=0;
		List<ConsolidateMarksCardNoGen> boList = new ArrayList<ConsolidateMarksCardNoGen>();
		if(!studentId.isEmpty()){
			Iterator<Integer> itr = studentId.iterator();
			while (itr.hasNext()) {
				Integer integer = (Integer) itr.next();
				ConsolidateMarksCardNoGen bo = new ConsolidateMarksCardNoGen();
				
				Student student = new Student();				
				student.setId(integer);
				bo.setStudentId(student);
			
				Course course = new Course();
				course.setId(Integer.parseInt(consolidateMarksCardForm.getCourseId()));
				bo.setCourseId(course);
				
				bo.setMarksCardNo(Integer.toString(currentNO));
				bo.setYear(consolidateMarksCardForm.getYear());
				String sino="";
				sino=sino+consolidateMarksCardForm.getYear().substring(2,4);
				if(consolidateMarksCardForm.getProgramTypeId()!=null && consolidateMarksCardForm.getProgramTypeId().equalsIgnoreCase("1")){
					sino=sino+"U"+currentNO;
				}else if(consolidateMarksCardForm.getProgramTypeId()!=null && consolidateMarksCardForm.getProgramTypeId().equalsIgnoreCase("2")){
					sino=sino+"P"+currentNO;
				}
				else {
					sino=sino+"M"+currentNO;
				}
				bo.setMarksCardNo1(sino);
				
				boList.add(bo);
				currentNO++;
				count++;
			}
		}
		consolidateMarksCardForm.setTotalCount(Integer.toString(count));
		return boList;
	}
	
	
	
	public List<MarksCardSiNoGen> convertData1(List<StudentFinalMarkDetails> studentId,
											   MarksCardForm marksCardForm,
											   int currentNO,
											   String classIds)throws Exception 
	{
		int count=0;
		List<MarksCardSiNoGen> boList = new ArrayList<MarksCardSiNoGen>();
		if(!studentId.isEmpty())
		{
			Iterator<StudentFinalMarkDetails> itr = studentId.iterator();
			while (itr.hasNext()) 
			{
				StudentFinalMarkDetails studentfinal = (StudentFinalMarkDetails) itr.next();
				Student student=studentfinal.getStudent();
				MarksCardSiNoGen bo = new MarksCardSiNoGen();
				
				bo.setStudentId(student);
				
				Classes classBo = studentfinal.getClasses();
				
				bo.setClassId(classBo);
				ExamDefinition examDef = new ExamDefinition();
				examDef.setId(Integer.parseInt(marksCardForm.getExamName()));
				bo.setExamId(examDef);
				bo.setMarksCardNo(Integer.toString(currentNO));
				
				if(marksCardForm.getIsDuplicate() != null && marksCardForm.getIsDuplicate().equalsIgnoreCase("yes")){
					bo.setIsDuplicate(true);
				}
				else{
					bo.setIsDuplicate(false);
				}
				if(marksCardForm.getRadioId().equalsIgnoreCase("1")){
					bo.setIsConsolidate(true);
				}
				else{
					bo.setIsConsolidate(false);
				}
				
				if(Integer.toString(currentNO).length()==1 && classBo!=null)
				{
					if(marksCardForm.getExamType().equalsIgnoreCase("Regular"))
					{
						if(classBo.getCourse().getProgram().getProgramType().getId()==1)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"U"+marksCardForm.getSemister()+"00"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==2)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"P"+marksCardForm.getSemister()+"00"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==3)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"M"+marksCardForm.getSemister()+"00"+currentNO);
						bo.setIsRegular(true);
					}
					else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary"))
					{
						if(classBo.getCourse().getProgram().getProgramType().getId()==1)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SU"+marksCardForm.getSemister()+"00"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==2)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SP"+marksCardForm.getSemister()+"00"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==3)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SM"+marksCardForm.getSemister()+"00"+currentNO);
						bo.setIsSupplementary(true);
					}
					else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
						if(classBo.getCourse().getProgram().getProgramType().getId()==1)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SU"+marksCardForm.getSemister()+"00"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==2)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SP"+marksCardForm.getSemister()+"00"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==3)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SM"+marksCardForm.getSemister()+"00"+currentNO);
						bo.setIsRevaluation(true);
					}
					else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
						bo.setMarksCardNo1("00"+currentNO+" I");
						bo.setIsImprovement(true);
					}
					else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
						bo.setMarksCardNo1("00"+currentNO+" G");
						bo.setIsGrace(true);
					}					
				}
				else if(Integer.toString(currentNO).length()==2 && classBo!=null)
				{					
					if(marksCardForm.getExamType().equalsIgnoreCase("Regular"))
					{
						if(classBo.getCourse().getProgram().getProgramType().getId()==1)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"U"+marksCardForm.getSemister()+"0"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==2)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"P"+marksCardForm.getSemister()+"0"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==3)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"M"+marksCardForm.getSemister()+"0"+currentNO);
						bo.setIsRegular(true);
					}
					else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
						if(classBo.getCourse().getProgram().getProgramType().getId()==1)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SU"+marksCardForm.getSemister()+"0"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==2)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SP"+marksCardForm.getSemister()+"0"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==3)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SM"+marksCardForm.getSemister()+"0"+currentNO);
						bo.setIsSupplementary(true);
					}
					else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
						if(classBo.getCourse().getProgram().getProgramType().getId()==1)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SU"+marksCardForm.getSemister()+"0"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==2)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SP"+marksCardForm.getSemister()+"0"+currentNO);
						if(classBo.getCourse().getProgram().getProgramType().getId()==3)
							bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SM"+marksCardForm.getSemister()+"0"+currentNO);
						bo.setIsRevaluation(true);
					}
					else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
						bo.setMarksCardNo1("0"+currentNO+" I");
						bo.setIsImprovement(true);
					}
					else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
						bo.setMarksCardNo1("0"+currentNO+" G");
						bo.setIsGrace(true);
					}
				}
				else
				{
					if(classBo!=null)
					{					
						if(marksCardForm.getExamType().equalsIgnoreCase("Regular"))
						{ 
							if(classBo.getCourse().getProgram().getProgramType().getId()==1)
								bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"U"+marksCardForm.getSemister()+currentNO);
							if(classBo.getCourse().getProgram().getProgramType().getId()==2)
								bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"P"+marksCardForm.getSemister()+currentNO);
							if(classBo.getCourse().getProgram().getProgramType().getId()==3)
								bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"M"+marksCardForm.getSemister()+currentNO);
							bo.setIsRegular(true);
						}
						else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
							if(classBo.getCourse().getProgram().getProgramType().getId()==1)
								bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SU"+marksCardForm.getSemister()+currentNO);
							if(classBo.getCourse().getProgram().getProgramType().getId()==2)
								bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SP"+marksCardForm.getSemister()+currentNO);
							if(classBo.getCourse().getProgram().getProgramType().getId()==3)
								bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SM"+marksCardForm.getSemister()+currentNO);
							bo.setIsSupplementary(true);
						}
						else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
							if(classBo.getCourse().getProgram().getProgramType().getId()==1)
								bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SU"+marksCardForm.getSemister()+currentNO);
							if(classBo.getCourse().getProgram().getProgramType().getId()==2)
								bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SP"+marksCardForm.getSemister()+currentNO);
							if(classBo.getCourse().getProgram().getProgramType().getId()==3)
								bo.setMarksCardNo1(marksCardForm.getProgramYear().substring(2,4)+"SM"+marksCardForm.getSemister()+currentNO);
							bo.setIsRevaluation(true);
						}
						else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
							bo.setMarksCardNo1(currentNO+" I");
							bo.setIsImprovement(true);
						}
						else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
							bo.setMarksCardNo1(currentNO+" G");
							bo.setIsGrace(true);
						}				
					}
				}
				
				
				bo.setCreatedDate(new Date());
				bo.setCreatedBy(marksCardForm.getUserId());
				
				boList.add(bo);
				currentNO++;
				count++;
			}
		}
		marksCardForm.setTotalCount(Integer.toString(count));
		return boList;
	}
	
	
	
	

	public List<StudentTO> convertStudentBo(List<StudentFinalMarkDetails> studentList) throws Exception{

	Iterator<StudentFinalMarkDetails> itr = studentList.iterator();
	StudentTO studentTo;
	Student student;
	StudentFinalMarkDetails s;
	StringBuffer studentName=new StringBuffer();
	List<StudentTO> list=new ArrayList<StudentTO>();
	while(itr.hasNext()) {
		
		studentTo = new StudentTO();
		s = itr.next();
		student=s.getStudent();
				studentTo.setId(student.getId());
				studentTo.setRegisterNo(student.getRegisterNo());
				studentTo.setRollNo(student.getRollNo());
				studentTo.setChecked(false);
				studentTo.setTempChecked(false);
				//added by mehaboob start
				
				//end
				if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getFirstName() != null) {
					studentName.append(student.getAdmAppln().getPersonalData().getFirstName()).append(" ");
				} 
				if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getMiddleName() != null) {
					studentName.append(student.getAdmAppln().getPersonalData().getMiddleName()).append(" ");
				}
				if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getLastName() != null) {
					studentName.append(student.getAdmAppln().getPersonalData().getLastName()).append(" ");
				}
				if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getParentMob1() != null && student.getAdmAppln().getPersonalData().getParentMob2() != null) {
					if(student.getAdmAppln().getPersonalData().getParentMob1()!=null && !student.getAdmAppln().getPersonalData().getParentMob1().isEmpty()){
						studentTo.setMobileNo1(student.getAdmAppln().getPersonalData().getParentMob1());
					}else{
						studentTo.setMobileNo1("91");
					}
					studentTo.setMobileNo2(student.getAdmAppln().getPersonalData().getParentMob2());
				}
				studentTo.setStudentName(studentName.toString());
				list.add(studentTo);
				studentName = new StringBuffer();
			}	
	
	
		return list;
	}
	
	//for addditional
	public MarksCardSiNoGen convertData2(MarksCardForm marksCardForm,List<MarksCardSiNoGen> origbo,List<MarksCardSiNoGen> dupbo  )throws Exception {
		// TODO Auto-generated method stub
		IMarksCardTransaction transaction = new MarksCardTransactionImpl();
		MarksCardSiNoGen bo1=origbo.get(0);
		MarksCardSiNoGen bo = new MarksCardSiNoGen();
		Student student = new Student();
		student.setId(Integer.parseInt(marksCardForm.getBaseStudentId()));
		bo.setStudentId(student);
		Classes classes = new Classes();
		classes.setId(Integer.parseInt(marksCardForm.getClassId()));
		bo.setClassId(classes);
		ExamDefinition examDefinition = new ExamDefinition();
		examDefinition.setId(Integer.parseInt(marksCardForm.getExamName()));
		bo.setExamId(examDefinition);
		bo.setMarksCardNo(bo1.getMarksCardNo());
		bo.setIsDuplicate(true);
		bo.setCreatedDate(new Date());
		bo.setCreatedBy(marksCardForm.getUserId());
		if(marksCardForm.getRadioId().equalsIgnoreCase("1")){
			bo.setIsConsolidate(true);
		}else{
			bo.setIsConsolidate(false);
		}		
				
					if(marksCardForm.getExamType().equalsIgnoreCase("Regular")){ 
						bo.setMarksCardNo1(bo1.getMarksCardNo1()+"A"+(dupbo.size()+1));
						bo.setAdditionalNo((dupbo.size()+1));
						bo.setIsRegular(true);
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Supplementary")){
						bo.setMarksCardNo1(bo1.getMarksCardNo1()+"A"+(dupbo.size()+1));
						bo.setAdditionalNo((dupbo.size()+1));
						bo.setIsSupplementary(true);
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Revaluation")){
						bo.setMarksCardNo1(bo1.getMarksCardNo1()+"A"+(dupbo.size()+1));
						bo.setAdditionalNo((dupbo.size()+1));
						bo.setIsRevaluation(true);
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Improvement")){
						bo.setMarksCardNo1(bo1.getMarksCardNo1()+"A"+(dupbo.size()+1));
						bo.setAdditionalNo((dupbo.size()+1));
						bo.setIsImprovement(true);
					}else if(marksCardForm.getExamType().equalsIgnoreCase("Grace")){
						bo.setMarksCardNo1(bo1.getMarksCardNo1()+"A"+(dupbo.size()+1));
						bo.setAdditionalNo((dupbo.size()+1));
						bo.setIsGrace(true);
					}
		return bo;
	}
}
