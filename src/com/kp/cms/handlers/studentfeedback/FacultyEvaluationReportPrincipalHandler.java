package com.kp.cms.handlers.studentfeedback;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.mapping.Array;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackFaculty;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportPrincipalForm;
import com.kp.cms.helpers.studentfeedback.FacultyEvaluationReportHelper;
import com.kp.cms.helpers.studentfeedback.FacultyEvaluationReportPrincipalHelper;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.to.studentfeedback.FacultyEvaluationReportAnswersTo;
import com.kp.cms.to.studentfeedback.FacultyEvaluationReportPrincipalTo;
import com.kp.cms.to.studentfeedback.FacultyEvaluationReportTo;
import com.kp.cms.transactions.studentfeedback.IFacultyEvaluationReport;
import com.kp.cms.transactions.studentfeedback.IFacultyEvaluationReportPrincipal;
import com.kp.cms.transactionsimpl.studentfeedback.FacultyEvaluationReportImpl;
import com.kp.cms.transactionsimpl.studentfeedback.FacultyEvaluationReportPrincipalImpl;
import com.kp.cms.utilities.CommonUtil;

public class FacultyEvaluationReportPrincipalHandler {
public static volatile FacultyEvaluationReportPrincipalHandler facultyEvaluationReportPrincipalHandler = null;
	
	public static FacultyEvaluationReportPrincipalHandler getInstance() {
		if (facultyEvaluationReportPrincipalHandler == null) {
			facultyEvaluationReportPrincipalHandler = new FacultyEvaluationReportPrincipalHandler();
		}
		return facultyEvaluationReportPrincipalHandler;
	}
	public Map<Integer, String> getClasses(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm, int year) throws Exception {	
		IFacultyEvaluationReportPrincipal transaction = FacultyEvaluationReportPrincipalImpl.getInstance();
		Map<Integer, String> classMap = transaction.getClasses(facultyEvaluationReportPrincipalForm,year);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}
	public Map<Integer, String> getSubjects(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm, int year) throws Exception {	
		IFacultyEvaluationReportPrincipal transaction = FacultyEvaluationReportPrincipalImpl.getInstance();
		Map<Integer, String> subjectMap = transaction.getSubjects(facultyEvaluationReportPrincipalForm,year);
		subjectMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
		return subjectMap;
	}
	public void setFacultyEvaluationDetails(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) throws Exception
	{
		IFacultyEvaluationReportPrincipal transaction = FacultyEvaluationReportPrincipalImpl.getInstance();
		List<Object[]> details = transaction.getFacultyEvaluationDetails(facultyEvaluationReportPrincipalForm);
		FacultyEvaluationReportTo detailsTo = FacultyEvaluationReportPrincipalHelper.getInstance().convertBotoTo(details);
		List<EvaStudentFeedbackQuestion> feedbackQuestions = transaction.getEvaluationQuestions();
		List<Object[]> answers = null;
		LinkedList<EvaStudentFeedBackQuestionTo> queList= new LinkedList<EvaStudentFeedBackQuestionTo>();
		Iterator<EvaStudentFeedbackQuestion> itr = feedbackQuestions.iterator();
		double overallScore = 0;
		while(itr.hasNext())
		{
			double totalScoreForEachQue = 0;
			int totalCountOfStudentsForEachQue = 0;
			double totalMarks = 0;			
			EvaStudentFeedBackQuestionTo queTo = new EvaStudentFeedBackQuestionTo();
			EvaStudentFeedbackQuestion bo = itr.next();
			int queId = bo.getId();
			String que = bo.getQuestion();
			int ansId = 1;
			LinkedList<FacultyEvaluationReportAnswersTo> ansList = new LinkedList<FacultyEvaluationReportAnswersTo>();
			while(ansId<=5)
			{
				FacultyEvaluationReportAnswersTo ansTo = new FacultyEvaluationReportAnswersTo();
				answers =  transaction.getAnswers(facultyEvaluationReportPrincipalForm,queId,ansId,"0",facultyEvaluationReportPrincipalForm.getSubjectId());
				if(answers.size()==0 || answers==null)
				{
					ansTo.setQuestionId(queId);
					ansTo.setQuestion(bo.getQuestion());
					ansTo.setCountOfStudents(0);
					totalCountOfStudentsForEachQue = totalCountOfStudentsForEachQue + ansTo.getCountOfStudents();
					totalMarks = totalMarks + (ansTo.getCountOfStudents()*ansTo.getAnswer());
				
					ansList.add(ansTo);
				}
				else{
					Iterator<Object[]> ansItr= answers.iterator();
					while(ansItr.hasNext())
					{
						Object[] ansData = ansItr.next();
						ansTo.setQuestionId(Integer.parseInt(ansData[0].toString()));
						ansTo.setQuestion(ansData[1].toString());
						ansTo.setAnswerId(Integer.parseInt(ansData[2].toString()));
						ansTo.setAnswer(Integer.parseInt(ansData[3].toString()));
						ansTo.setCountOfStudents(Integer.parseInt(ansData[4].toString()));
						totalCountOfStudentsForEachQue = totalCountOfStudentsForEachQue + ansTo.getCountOfStudents();
						totalMarks = totalMarks + (ansTo.getCountOfStudents()*ansTo.getAnswer());

						ansList.add(ansTo);
					}
				}	
				
				ansId = ansId + 1;
				queTo.setQuestion(que);
				totalScoreForEachQue = totalMarks/totalCountOfStudentsForEachQue;
				totalScoreForEachQue=CommonUtil.Round(totalScoreForEachQue, 2);	
				queTo.setTotalScoreForEachQue(totalScoreForEachQue);			
				queTo.setTotalStudentsForEachQue(totalCountOfStudentsForEachQue);
			}
			queTo.setAnswers(ansList);
			queList.add(queTo);
			overallScore = overallScore + queTo.getTotalScoreForEachQue();
		}	
		facultyEvaluationReportPrincipalForm.setTo(detailsTo);
		facultyEvaluationReportPrincipalForm.setQuestions(queList);
		facultyEvaluationReportPrincipalForm.setOverallScore(CommonUtil.Round((overallScore/10),2));
	}	
	public void setClassAverage(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) throws Exception
	{
		IFacultyEvaluationReportPrincipal transaction = FacultyEvaluationReportPrincipalImpl.getInstance();
		List<Object[]> answers = null;
		List<Object[]> teachers = transaction.getTeachersByClass(facultyEvaluationReportPrincipalForm.getClassId());
		Iterator<Object[]> teacherItr = teachers.iterator();
		String teacherId = null;
		String SubjectId = null;
		double overallAvgForClass = 0;
		int teacherCount = 0;
		while(teacherItr.hasNext())
		{
			double overallAvgForEachTeacher =0;
			Object[] data = teacherItr.next();
			if(data[0]!=null)
			{
				SubjectId = data[0].toString();				
			}
			if(data[1]!=null)
			{
				teacherId = data[1].toString();				
			}
			List<EvaStudentFeedbackQuestion> feedbackQuestions = transaction.getEvaluationQuestions();
			Iterator<EvaStudentFeedbackQuestion> itr = feedbackQuestions.iterator();
			int totalCountOfStudents = 0;
			double totalMarks = 0;
			while(itr.hasNext())
			{
				EvaStudentFeedbackQuestion bo = itr.next();
				int queId = bo.getId();
				int ansId = 1;
				while(ansId<=5)
				{
					answers =  transaction.getAnswers(facultyEvaluationReportPrincipalForm,queId,ansId,teacherId,SubjectId);
					if(answers.size()==0 || answers==null)
					{
						totalCountOfStudents = totalCountOfStudents + 0;
						totalMarks = totalMarks + (totalCountOfStudents*0);					
					}
					else{
						Iterator<Object[]> ansItr= answers.iterator();
						while(ansItr.hasNext())
						{
							Object[] ansData = ansItr.next();
							totalCountOfStudents = totalCountOfStudents + Integer.parseInt(ansData[4].toString());
							totalMarks = totalMarks + (Integer.parseInt(ansData[3].toString())*Double.parseDouble(ansData[4].toString()));
						}
					}
					ansId = ansId + 1;					
				}					
			}
			if(totalMarks!=0)
			{
				overallAvgForEachTeacher = totalMarks/totalCountOfStudents;
			}
			if(overallAvgForEachTeacher!=0)
			{
				overallAvgForClass = overallAvgForClass + overallAvgForEachTeacher;
			}
			teacherCount = teacherCount + 1;
		}
		double classAverage = 0;
		if(overallAvgForClass!=0)
			classAverage = overallAvgForClass/teacherCount;
		classAverage = CommonUtil.Round(classAverage, 2);
		facultyEvaluationReportPrincipalForm.setClassAverage(classAverage);
	}
	public void setDepartmentAverage(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) throws Exception
	{
		IFacultyEvaluationReportPrincipal transaction = FacultyEvaluationReportPrincipalImpl.getInstance();
		List<Object[]> answers = null;
		List<Object[]> teachers = transaction.getTeachersByDepartment(facultyEvaluationReportPrincipalForm.getSubjectId());
		Iterator<Object[]> teacherItr = teachers.iterator();
		String teacherId = null;
		String subjectId = null;
		double overallAvgForClass = 0;
		int teacherCount = 0;
		while(teacherItr.hasNext())
		{
			double overallAvgForEachTeacher =0;
			Object[] data = teacherItr.next();
			if(data[0]!=null)
			{
				subjectId = data[0].toString();				
			}
			if(data[1]!=null)
			{
				teacherId = data[1].toString();				
			}
			List<EvaStudentFeedbackQuestion> feedbackQuestions = transaction.getEvaluationQuestions();
			Iterator<EvaStudentFeedbackQuestion> itr = feedbackQuestions.iterator();
			int totalCountOfStudents = 0;
			double totalMarks = 0;
			while(itr.hasNext())
			{
				EvaStudentFeedbackQuestion bo = itr.next();
				int queId = bo.getId();
				int ansId = 1;
				while(ansId<=5)
				{
					answers =  transaction.getAnswers(facultyEvaluationReportPrincipalForm,queId,ansId,teacherId,subjectId);
					if(answers.size()==0 || answers==null)
					{
						totalCountOfStudents = totalCountOfStudents + 0;
						totalMarks = totalMarks + (totalCountOfStudents*0);					
					}
					else{
						Iterator<Object[]> ansItr= answers.iterator();
						while(ansItr.hasNext())
						{
							Object[] ansData = ansItr.next();
							totalCountOfStudents = totalCountOfStudents + Integer.parseInt(ansData[4].toString());
							totalMarks = totalMarks + (Integer.parseInt(ansData[3].toString())*Double.parseDouble(ansData[4].toString()));
						}
					}
					ansId = ansId + 1;					
				}					
			}
			if(totalMarks!=0)
			{
				overallAvgForEachTeacher = totalMarks/totalCountOfStudents;
			}
			if(overallAvgForEachTeacher!=0)
			{
				overallAvgForClass = overallAvgForClass + overallAvgForEachTeacher;
			}
			teacherCount = teacherCount + 1;
		}
		double deptAverage = 0;
		if(overallAvgForClass!=0)
			deptAverage = overallAvgForClass/teacherCount;
		deptAverage = CommonUtil.Round(deptAverage, 2);
		facultyEvaluationReportPrincipalForm.setDeptAverage(deptAverage);
	}
	public boolean isDataExist(
			FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) {
		boolean exist = false;
		IFacultyEvaluationReportPrincipal transaction = FacultyEvaluationReportPrincipalImpl.getInstance();
		exist = transaction.isDataExist(facultyEvaluationReportPrincipalForm);
		return exist;
	}
	public void setFacultyEvaluationDetailsDepartmentWise(FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) throws Exception
	{
		IFacultyEvaluationReportPrincipal transaction = FacultyEvaluationReportPrincipalImpl.getInstance();
		Department department = transaction.getDepartment(facultyEvaluationReportPrincipalForm);
		facultyEvaluationReportPrincipalForm.setDepartmentName(department.getName());
		List<EvaStudentFeedbackQuestion> feedbackQuestions = transaction.getEvaluationQuestions();
		List<EvaStudentFeedBackQuestionTo> queList = new ArrayList<EvaStudentFeedBackQuestionTo>();
		Iterator<EvaStudentFeedbackQuestion> itr = feedbackQuestions.iterator();
		while(itr.hasNext()){
			EvaStudentFeedbackQuestion bo = itr.next();
			if(bo!=null)
			{
				EvaStudentFeedBackQuestionTo queTo = new EvaStudentFeedBackQuestionTo();
				queTo.setId(bo.getId());
				queTo.setQuestion(bo.getQuestion());
				queList.add(queTo);
			}				
		}
		facultyEvaluationReportPrincipalForm.setQuestions(queList);		
		List<Object[]> teachersAndSubjects = transaction.getTeachersAndSubjects(facultyEvaluationReportPrincipalForm);
		double totalFacultyAvg = 0;
		int teacherCount=0 ;
		if(teachersAndSubjects!=null)
		{
			Iterator<Object[]> teachersAndSubjectsItr = teachersAndSubjects.iterator();
			List<FacultyEvaluationReportTo> teaList = new ArrayList<FacultyEvaluationReportTo>();
			while(teachersAndSubjectsItr.hasNext())
			{
				FacultyEvaluationReportTo teacherTo = new FacultyEvaluationReportTo();
				teacherCount = teacherCount + 1;
				Object[] data = teachersAndSubjectsItr.next();
				String teacher = null;
				String subject = null;	
				String classId = null;
				if(data[0]!=null)
					teacher=data[0].toString();
			/*	if(data[1]!=null)
					subject=data[1].toString();*/
				if(data[1]!=null)
					teacherTo.setTeacherName(toTitleCase(data[1].toString()));
				/*if(data[3]!=null)				
					teacherTo.setSubject(toTitleCase(data[3].toString()));*/
				if(data[2]!=null)
				{
					teacherTo.setTotalAverage(data[2].toString());
					totalFacultyAvg = totalFacultyAvg + Double.parseDouble(data[2].toString());
				}
				if(data[3]!=null)
				{
					double totalEntries = Math.round(Double.parseDouble(data[3].toString()));
					int total =  new Double(totalEntries).intValue();
					teacherTo.setTotalEntries(total+"");	
				}
					
				/*if(data[6]!=null)				
					classId=data[6].toString();	
				if(data[7]!=null)				
					teacherTo.setClassName(toTitleCase(data[7].toString()));*/
				Map<Integer, String> remMap = new HashMap<Integer, String>();
				List<EvaluationStudentFeedbackFaculty> remarks = transaction.getRemarksGivenByStudent(facultyEvaluationReportPrincipalForm, teacher);
				Set<String> s = new HashSet<String>();
				if(remarks!=null && remarks.size()!=0)
				{
					Iterator<EvaluationStudentFeedbackFaculty> itr1 = remarks.iterator();
					while(itr1.hasNext())
					{
						EvaluationStudentFeedbackFaculty bo = itr1.next();
						if(bo.getRemarks()!=null && !bo.getRemarks().isEmpty())
						{
							String str = bo.getRemarks().toLowerCase().trim();
							final StringTokenizer st = new StringTokenizer(str, ".");
							final StringBuilder sb = new StringBuilder();

							while (st.hasMoreTokens()) {
								String token = st.nextToken();
								sb.append(Character.toUpperCase(token.charAt(0))+token.substring(1) + " ");
							}
							s.add(sb.toString().trim());
						}
					}
					
					Iterator<String> remItr = s.iterator();
					int count=1;
					while(remItr.hasNext())
					{
						remMap.put(count, remItr.next().toString());
						count++;
					}
					
					teacherTo.setRemarks(remMap);
				}
				
				List<Object[]> details = transaction.getFacultyEvaluationDetailsDepartmentWise(teacher,subject,facultyEvaluationReportPrincipalForm,classId);
				DecimalFormat d=new DecimalFormat("#.##");
				facultyEvaluationReportPrincipalForm.setTotalFacultyAverage(new Double(d.format(totalFacultyAvg/teacherCount)));
				if(details!=null)
				{
					List<FacultyEvaluationReportPrincipalTo> detList = new ArrayList<FacultyEvaluationReportPrincipalTo>();
					Iterator<Object[]> detailsItr = details.iterator();
					while(detailsItr.hasNext())
					{
						FacultyEvaluationReportPrincipalTo detailsTo = new FacultyEvaluationReportPrincipalTo();
						Object[] detailsData = detailsItr.next();
						if(detailsData[1]!=null)
							detailsTo.setQuestionId(Integer.parseInt(detailsData[1].toString()));
						if(detailsData[3]!=null)
							detailsTo.setScoreForEachQue(detailsData[3].toString());						
						detList.add(detailsTo);
						Collections.sort(detList);
					}
					teacherTo.setResultList(detList);
				}
				teaList.add(teacherTo);
			}
			facultyEvaluationReportPrincipalForm.setTeacherList(teaList);
		}
	}
	public static String toTitleCase(String inputString) {
		 String result = "";
	       if (inputString.length() == 0) {
	           return result;
	       }
	       char firstChar = inputString.charAt(0);
	       char firstCharToUpperCase = Character.toUpperCase(firstChar);
	       result = result + firstCharToUpperCase;
	       for (int i = 1; i < inputString.length(); i++) {
	           char currentChar = inputString.charAt(i);
	           char previousChar = inputString.charAt(i - 1);
	           if (previousChar == ' ') {
	               char currentCharToUpperCase = Character.toUpperCase(currentChar);
	               result = result + currentCharToUpperCase;
	           } else {
	               char currentCharToLowerCase = Character.toLowerCase(currentChar);
	               result = result + currentCharToLowerCase;
	           }
	       }
	       return result;
	}
}
