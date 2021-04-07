package com.kp.cms.handlers.studentfeedback;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackAverage;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackOverallAverage;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackScore;
import com.kp.cms.forms.studentfeedback.EvaluateFacultyFeedbackForm;
import com.kp.cms.transactions.studentfeedback.IEvaluateFacultyFeedback;
import com.kp.cms.transactionsimpl.studentfeedback.EvaluateFacultyFeedbackImpl;
import com.kp.cms.utilities.CommonUtil;

public class EvaluateFacultyFeedbackHandler {
public static volatile EvaluateFacultyFeedbackHandler evaluateFacultyFeedbackHandler = null;
	
	public static EvaluateFacultyFeedbackHandler getInstance() {
		if (evaluateFacultyFeedbackHandler == null) {
			evaluateFacultyFeedbackHandler = new EvaluateFacultyFeedbackHandler();
		}
		return evaluateFacultyFeedbackHandler;
	}
	public Map<Integer, String> getClasses(EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm, int year) throws Exception {	
		IEvaluateFacultyFeedback transaction = EvaluateFacultyFeedbackImpl.getInstance();
		Map<Integer, String> classMap = transaction.getClasses(evaluateFacultyFeedbackForm,year);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}
	public boolean setFacultyEvaluationDetails(EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm) {
		// check and delete if evaluation present already for given class
		IEvaluateFacultyFeedback transaction = EvaluateFacultyFeedbackImpl.getInstance();
		boolean deleted = false;
		try {
			deleted = transaction.deleteIfExist(evaluateFacultyFeedbackForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(deleted==true)
		{
			boolean avgFlag = false;
			List<Object[]> teachers = transaction.getTeachersByClass(evaluateFacultyFeedbackForm.getClassId());
			List<EvaStudentFeedbackQuestion> ques = transaction.getEvaluationQuestions();
			Iterator<Object[]> teacherItr = teachers.iterator();
			String subjectId=null;
			String teacherId=null;
			double overall = 0;
			double overallAverageofAll=0;
			int semester = transaction.getSemester(evaluateFacultyFeedbackForm.getClassId());
			EvaStudentFeedbackAverage avg = new EvaStudentFeedbackAverage();
			avg.setClassId(Integer.parseInt(evaluateFacultyFeedbackForm.getClassId()));
			avg.setYear(Integer.parseInt(evaluateFacultyFeedbackForm.getAcademicYear()));	
			avg.setSemester(semester);
			Set<EvaStudentFeedbackOverallAverage> overallAvg = new HashSet<EvaStudentFeedbackOverallAverage>();
			while(teacherItr.hasNext())		{
				Object[] data = teacherItr.next();
				if(data[0]!=null)
					subjectId=data[0].toString();
				if(data[1]!=null)
					teacherId=data[1].toString();
				evaluateFacultyFeedbackForm.setTeacherId(teacherId);
				List<Object[]> answers = null;
				Iterator<EvaStudentFeedbackQuestion> itr = ques.iterator();
				double overallScore = 0;
				EvaStudentFeedbackOverallAverage overallAvgBo = new EvaStudentFeedbackOverallAverage(); 
				Users user = new Users();
				user.setId(Integer.parseInt(teacherId));
				Subject sub = new Subject();
				sub.setId(Integer.parseInt(subjectId));
				overallAvgBo.setTeacher(user);
				overallAvgBo.setSubject(sub);
				
				Set<EvaStudentFeedbackScore> score = new HashSet<EvaStudentFeedbackScore>();
				while(itr.hasNext())
				{	
					double totalScoreForEachQue = 0;
					int totalCountOfStudentsForEachQue = 0;
					double totalMarks = 0;			
					EvaStudentFeedbackQuestion queBo = itr.next();
					int queId = queBo.getId();
					int ansId = 1;
					EvaStudentFeedbackScore scoreBo = new EvaStudentFeedbackScore();
					while(ansId<=5)
					{
						answers =  transaction.getAnswers(evaluateFacultyFeedbackForm,queId,ansId,subjectId);
						if(answers.size()==0 || answers==null)
						{
							scoreBo.setQuestion(queBo);
							if(ansId==1)
								scoreBo.setCount1(0);
							if(ansId==2)
								scoreBo.setCount2(0);
							if(ansId==3)
								scoreBo.setCount3(0);
							if(ansId==4)
								scoreBo.setCount4(0);
							if(ansId==5)
								scoreBo.setCount5(0);
							totalCountOfStudentsForEachQue = totalCountOfStudentsForEachQue+0;
							totalMarks = totalMarks + 0;					
						}
						else{
							Iterator<Object[]> ansItr= answers.iterator();
							while(ansItr.hasNext())
							{
								double sum = 0;
								Object[] ansData = ansItr.next();
								scoreBo.setQuestion(queBo);
								if(ansId==1)
								{
									scoreBo.setCount1(Integer.parseInt(ansData[4].toString()));
									sum = 1*scoreBo.getCount1();
								}								
								if(ansId==2)
								{
									scoreBo.setCount2(Integer.parseInt(ansData[4].toString()));
									sum = 2*scoreBo.getCount2();
								}
								if(ansId==3)
								{
									scoreBo.setCount3(Integer.parseInt(ansData[4].toString()));
									sum = 3*scoreBo.getCount3();
								}
								if(ansId==4)
								{
									scoreBo.setCount4(Integer.parseInt(ansData[4].toString()));
									sum = 4*scoreBo.getCount4();
								}
								if(ansId==5)
								{
									scoreBo.setCount5(Integer.parseInt(ansData[4].toString()));
									sum = 5*scoreBo.getCount5();
								}
								totalCountOfStudentsForEachQue = totalCountOfStudentsForEachQue+Integer.parseInt(ansData[4].toString());
								totalMarks = totalMarks + sum;
							}
						}						
						ansId = ansId + 1;					
						totalScoreForEachQue = totalMarks/totalCountOfStudentsForEachQue;
						totalScoreForEachQue=CommonUtil.Round(totalScoreForEachQue, 2);					
					}
					scoreBo.setScore(totalScoreForEachQue);
					scoreBo.setTotalCount(totalCountOfStudentsForEachQue);
					
					scoreBo.setEvaStudentFeedbackOverallAverage(overallAvgBo);
					score.add(scoreBo);
					
					overallScore = overallScore + totalScoreForEachQue;
					overall=CommonUtil.Round((overallScore/10),2);
				}
				overallAverageofAll = overallAverageofAll + overall;
				overallAvgBo.setOverallAverage(overall);				
				overallAvgBo.setScores(score);
				
				overallAvgBo.setEvaStudentFeedbackAverage(avg);
				overallAvg.add(overallAvgBo);
			}
			avg.setClassAverage(CommonUtil.Round(overallAverageofAll/teachers.size(),2));
			avg.setOverallAverages(overallAvg);
			avgFlag = transaction.setAverage(avg);
			return avgFlag;
		}
		return true;		
	}	
}
