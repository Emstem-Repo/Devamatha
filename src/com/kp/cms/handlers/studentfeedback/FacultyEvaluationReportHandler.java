package com.kp.cms.handlers.studentfeedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kp.cms.bo.admin.EvaluationStudentFeedbackFaculty;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.helpers.studentfeedback.FacultyEvaluationReportHelper;
import com.kp.cms.to.admin.EvaluationStudentFeedbackTO;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.to.studentfeedback.FacultyEvaluationReportAnswersTo;
import com.kp.cms.to.studentfeedback.FacultyEvaluationReportTo;
import com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction;
import com.kp.cms.transactions.studentfeedback.IEvaFacultyFeedBackQuestionTransaction;
import com.kp.cms.transactions.studentfeedback.IFacultyEvaluationReport;
import com.kp.cms.transactionsimpl.studentfeedback.EvaFacultyFeedBackQuestionImpl;
import com.kp.cms.transactionsimpl.studentfeedback.FacultyEvaluationReportImpl;
import com.kp.cms.utilities.CommonUtil;

public class FacultyEvaluationReportHandler {
	public static volatile FacultyEvaluationReportHandler facultyEvaluationReportHandler = null;
	
	public static FacultyEvaluationReportHandler getInstance() {
		if (facultyEvaluationReportHandler == null) {
			facultyEvaluationReportHandler = new FacultyEvaluationReportHandler();
		}
		return facultyEvaluationReportHandler;
	}
	public Map<Integer, String> getClasses(FacultyEvaluationReportForm facultyEvaluationReportForm, int year) throws Exception {	
		IFacultyEvaluationReport transaction = FacultyEvaluationReportImpl.getInstance();
		Map<Integer, String> classMap = transaction.getClasses(facultyEvaluationReportForm,year);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}
	public Map<Integer, String> getSubjects(FacultyEvaluationReportForm facultyEvaluationReportForm, int year) throws Exception {	
		IFacultyEvaluationReport transaction = FacultyEvaluationReportImpl.getInstance();
		Map<Integer, String> subjectMap = transaction.getSubjects(facultyEvaluationReportForm,year);
		subjectMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
		return subjectMap;
	}
	public void setFacultyEvaluationDetails(FacultyEvaluationReportForm facultyEvaluationReportForm) throws Exception
	{
		IFacultyEvaluationReport transaction = FacultyEvaluationReportImpl.getInstance();
		List<Object[]> details = transaction.getFacultyEvaluationDetails(facultyEvaluationReportForm);
		FacultyEvaluationReportTo detailsTo = FacultyEvaluationReportHelper.getInstance().convertBotoTo(details);
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
				answers =  transaction.getAnswers(facultyEvaluationReportForm,queId,ansId,"0",facultyEvaluationReportForm.getSubjectId());
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
		facultyEvaluationReportForm.setTo(detailsTo);
		facultyEvaluationReportForm.setQuestions(queList);
		facultyEvaluationReportForm.setOverallScore(CommonUtil.Round((overallScore/10),2));
	}	
	public void setClassAverage(FacultyEvaluationReportForm facultyEvaluationReportForm) throws Exception
	{
		IFacultyEvaluationReport transaction = FacultyEvaluationReportImpl.getInstance();
		List<Object[]> answers = null;
		List<Object[]> teachers = transaction.getTeachersByClass(facultyEvaluationReportForm.getClassId());
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
					answers =  transaction.getAnswers(facultyEvaluationReportForm,queId,ansId,teacherId,subjectId);
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
		facultyEvaluationReportForm.setClassAverage(classAverage);
	}
	public void setDepartmentAverage(FacultyEvaluationReportForm facultyEvaluationReportForm) throws Exception
	{
		IFacultyEvaluationReport transaction = FacultyEvaluationReportImpl.getInstance();
		List<Object[]> answers = null;
		List<Object[]> teachers = transaction.getTeachersByDepartment(facultyEvaluationReportForm);
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
			//if(teacherId.equalsIgnoreCase("74") && subjectId.equalsIgnoreCase("97")){
			while(itr.hasNext())
			{
				EvaStudentFeedbackQuestion bo = itr.next();
				int queId = bo.getId();
				int ansId = 1;
				while(ansId<=5)
				{
					answers =  transaction.getAnswers(facultyEvaluationReportForm,queId,ansId,teacherId,subjectId);
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
		//}
		double deptAverage = 0;
		if(overallAvgForClass!=0)
			deptAverage = overallAvgForClass/teacherCount;
		deptAverage = CommonUtil.Round(deptAverage, 2);
		facultyEvaluationReportForm.setDeptAverage(deptAverage);
	}
	public boolean isDataExist(
			FacultyEvaluationReportForm facultyEvaluationReportForm) {
		boolean exist = false;
		IFacultyEvaluationReport transaction = FacultyEvaluationReportImpl.getInstance();
		exist = transaction.isDataExist(facultyEvaluationReportForm);
		return exist;
	}
	public void setRemarksGivenByStudent(
			FacultyEvaluationReportForm facultyEvaluationReportForm) {
		IFacultyEvaluationReport transaction = FacultyEvaluationReportImpl.getInstance();
		List<EvaluationStudentFeedbackFaculty> remarks = transaction.getRemarksGivenByStudent(facultyEvaluationReportForm);
		Map<Integer, String> remMap = new HashMap<Integer, String>();
		Set<String> s = new HashSet<String>();
		if(remarks!=null && remarks.size()!=0)
		{
			Iterator<EvaluationStudentFeedbackFaculty> itr = remarks.iterator();
			while(itr.hasNext())
			{
				EvaluationStudentFeedbackFaculty bo = itr.next();
				if(bo.getRemarks()!=null && !bo.getRemarks().isEmpty())
				{
					FacultyEvaluationReportTo to = new FacultyEvaluationReportTo();
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
			
			facultyEvaluationReportForm.setRemarks(remMap);
		}
		else
			facultyEvaluationReportForm.setRemarks(null);
	}	
}
