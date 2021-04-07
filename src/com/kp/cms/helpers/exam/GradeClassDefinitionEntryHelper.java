package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ExamSubCoursewiseAttendanceMarksBO;
import com.kp.cms.bo.exam.ExamSubCoursewiseGradeDefnBO;
import com.kp.cms.bo.exam.ExamSubDefinitionCourseWiseBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.GradeClassDefinitionEntryForm;
import com.kp.cms.to.exam.AttendanceMarkAndPercentageTO;
import com.kp.cms.to.exam.GradeClassDefinitionTO;
import com.kp.cms.transactions.exam.IAttendanceMarksEntryTransaction;
import com.kp.cms.transactions.exam.IGradeClassDefinitionEntryTransaction;
import com.kp.cms.transactionsimpl.exam.AttendanceMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamSubDefinitionCourseWiseImpl;

public class GradeClassDefinitionEntryHelper {
	private static volatile GradeClassDefinitionEntryHelper gradeClassDefinitionEntryHelper= null;
	private static final Log log = LogFactory.getLog(GradeClassDefinitionEntryHelper.class);
	public static GradeClassDefinitionEntryHelper getInstance()
	{
		if(gradeClassDefinitionEntryHelper== null)
		{
			GradeClassDefinitionEntryHelper gradeClassDefinitionEntryHelper = new GradeClassDefinitionEntryHelper();
			return gradeClassDefinitionEntryHelper; 
		}
		return gradeClassDefinitionEntryHelper;
	}
	public List<ExamSubCoursewiseGradeDefnBO> converFormTOBOList(GradeClassDefinitionEntryForm gradeClassDefinitionEntryForm,HttpServletRequest request, ActionErrors errors, IGradeClassDefinitionEntryTransaction transaction)  throws Exception{
		log.debug("call of converFormTOBOList method in GradeClassDefinitionEntryHelper.class");
		List<ExamSubCoursewiseGradeDefnBO> gradeClassList = new ArrayList<ExamSubCoursewiseGradeDefnBO>();
		int schemeNumber = 0;
		int year = 0;
		Map<Integer, List<Integer>> deleteExistingMap = new HashMap<Integer, List<Integer>>();
		ExamSubDefinitionCourseWiseImpl impl = new ExamSubDefinitionCourseWiseImpl();
		IAttendanceMarksEntryTransaction transactionAtte = new AttendanceMarksEntryTransactionImpl().getInstance();
		List<Integer> courseList = new ArrayList<Integer>();
		ArrayList<Object[]> subjetList = null;
		try
		{
			List<GradeClassDefinitionTO> gradeClassDefinitionTOList = gradeClassDefinitionEntryForm.getGradeDefinitionList();
			if(gradeClassDefinitionEntryForm.getSchemeNumber()!=null)
			{
				schemeNumber = Integer.parseInt(gradeClassDefinitionEntryForm.getSchemeNumber());
			}
			if(gradeClassDefinitionEntryForm.getAccYear()!=null)
			{
				year = Integer.parseInt(gradeClassDefinitionEntryForm.getAccYear());
			}
			String[] courseIdset =gradeClassDefinitionEntryForm.getSelectedCourses();
			for(String cid:courseIdset)
			{
				Integer courseID= Integer.parseInt(cid);
				courseList.add(courseID);
			}
			
			Iterator<Integer> iteratorCourse = courseList.iterator();
			while(iteratorCourse.hasNext())
			{
				// iteration by course;
				int courseId = iteratorCourse.next();
				int schemeId = transactionAtte.getSchemIDforCourseAndYear(courseId,year,schemeNumber);
				// getting subject List by course;
				ArrayList<ExamSubDefinitionCourseWiseBO> listBO = new ArrayList(impl.select_course_scheme_year(courseId, schemeId, schemeNumber,year));
				List<Integer> deleteSubjectIDList = new ArrayList<Integer>();
				Iterator iteratorSubject = listBO.iterator();
				while(iteratorSubject.hasNext())
				{
					
					// iterate with mark attendance list
					Object[] row = (Object[])iteratorSubject.next();
					Iterator<GradeClassDefinitionTO>  iteratorAttendanceMark = gradeClassDefinitionTOList.iterator();
					while(iteratorAttendanceMark.hasNext())
					{
						GradeClassDefinitionTO gradeDefinition = iteratorAttendanceMark.next();
						
						ExamSubCoursewiseGradeDefnBO gradeBO = new ExamSubCoursewiseGradeDefnBO();
						
						gradeBO.setSubjectId(Integer.parseInt(row[0].toString()));
						gradeBO.setCourseId(courseId);
						
						gradeBO.setStartPrcntgGrade(new BigDecimal(gradeDefinition.getFromPercentage()));
						gradeBO.setEndPrcntgGrade(new BigDecimal(gradeDefinition.getToPercentage()));
						gradeBO.setGrade(gradeDefinition.getGrade());
						gradeBO.setGradePoint(new BigDecimal(gradeDefinition.getGradePoint()));
						gradeBO.setResultClass(gradeDefinition.getResultClass());
						gradeBO.setGradeInterpretation(gradeDefinition.getInterpretation());
						gradeBO.setAcademicYear(Integer.parseInt(gradeClassDefinitionEntryForm.getAccYear()));
						gradeClassList.add(gradeBO);
					}
					deleteSubjectIDList.add(Integer.parseInt(row[0].toString()));
					
				}
				if(deleteSubjectIDList!=null && !deleteSubjectIDList.isEmpty())
				{
					deleteExistingMap.put(courseId, deleteSubjectIDList);
				}
			}
			boolean overwriteExixting = transaction.deleteExixtingData(deleteExistingMap,Integer.parseInt(gradeClassDefinitionEntryForm.getAccYear())); 
			
			
		}
		catch (Exception e) {
			log.error("Error in converFormTOBOList method in GradeClassDefinitionEntryHelper.class");
			throw new ApplicationException(e);
		}

		log.debug("end of converFormTOBOList method in GradeClassDefinitionEntryHelper.class");
		return gradeClassList;
	}
	public List<GradeClassDefinitionTO> convertObjectListOfGradeDefinitionToTOList(List<Object[]> bolists)  throws Exception{
		log.debug("call of convertObjectListOfGradeDefinitionToTOList method in GradeClassDefinitionEntryHelper.class");
		List<GradeClassDefinitionTO> gradeDefinitionTOList = new ArrayList<GradeClassDefinitionTO>();
		if(bolists.size()>0)
		{
			Iterator<Object[]> iterator = bolists.iterator();
			while(iterator.hasNext())
			{
				Object[] obj = iterator.next();
				GradeClassDefinitionTO to = new GradeClassDefinitionTO();
				if(obj[1]!=null)
				{
					to.setCourseName(obj[1].toString());
				}
				if(obj[3]!=null)
				{
					to.setNumberOfSubject(obj[3].toString());
				}
				if(obj[4]!=null)
				{
					to.setFromPercentage(obj[4].toString());
				}
				if(obj[5]!=null)
				{
					to.setToPercentage(obj[5].toString());
				}
				if(obj[6]!=null)
				{
					to.setGrade(obj[6].toString());
				}
				if(obj[7]!=null)
				{
					to.setResultClass(obj[7].toString());
				}
				if(obj[8]!=null)
				{
					to.setGradePoint(obj[8].toString());
				}
				gradeDefinitionTOList.add(to);
			}
		}
		log.debug("end of convertObjectListOfGradeDefinitionToTOList method in GradeClassDefinitionEntryHelper.class");
		return gradeDefinitionTOList;
	}
}
