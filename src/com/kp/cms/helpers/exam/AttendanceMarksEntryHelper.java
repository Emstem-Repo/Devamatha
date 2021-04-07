package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamSubCoursewiseAttendanceMarksBO;
import com.kp.cms.bo.exam.ExamSubDefinitionCourseWiseBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.AttendanceMarksEntryForm;
import com.kp.cms.to.exam.AttendanceMarkAndPercentageTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.transactions.exam.IAttendanceMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.ExamSubDefinitionCourseWiseImpl;

public class AttendanceMarksEntryHelper {

	private static volatile AttendanceMarksEntryHelper attendanceMarksEntryHelper= null;
	private static final Log log = LogFactory.getLog(AttendanceMarksEntryHelper.class);
	public static AttendanceMarksEntryHelper getInstance()
	{
		if(attendanceMarksEntryHelper== null)
		{
			AttendanceMarksEntryHelper attendanceMarksEntryHelper = new AttendanceMarksEntryHelper();
			return attendanceMarksEntryHelper; 
		}
		return attendanceMarksEntryHelper;
	}
	public List<ExamCourseUtilTO> convertBOtoTO(List<ExamCourseUtilBO> courseBOList) throws Exception{
        log.debug("call of convertBOtoTO method in AttendanceMarksEntryHelper.class");
        List<ExamCourseUtilTO> coourseTOList = new ArrayList<ExamCourseUtilTO>();
		
		
		Iterator<ExamCourseUtilBO> iterator = courseBOList.iterator();
		while(iterator.hasNext())
		{
			ExamCourseUtilBO  bo = iterator.next();
			ExamCourseUtilTO utilTO = new ExamCourseUtilTO();
			utilTO.setId(bo.getCourseID());
			utilTO.setDisplay(bo.getPTypeProgramCourse());
			coourseTOList.add(utilTO);
		}
		
		Collections.sort(coourseTOList);
        log.debug("end of convertBOtoTO method in AttendanceMarksEntryHelper.class");		
		return coourseTOList;
	}
	public List<ExamSubCoursewiseAttendanceMarksBO> converFormTOBOList(AttendanceMarksEntryForm attendanceMarksEntryForm,HttpServletRequest request, ActionErrors errors, IAttendanceMarksEntryTransaction transaction) throws Exception {
		log.debug("call of converFormTOBOList method in AttendanceMarksEntryHelper.class");
		List<ExamSubCoursewiseAttendanceMarksBO> attendanceBOList = new ArrayList<ExamSubCoursewiseAttendanceMarksBO>();
		int schemeNumber = 0;
		int year = 0;
		Map<Integer, List<Integer>> deleteExistingMap = new HashMap<Integer, List<Integer>>();
		ExamSubDefinitionCourseWiseImpl impl = new ExamSubDefinitionCourseWiseImpl();
		List<Integer> courseList = new ArrayList<Integer>();
		ArrayList<Object[]> subjetList = null;
		try
		{
			List<AttendanceMarkAndPercentageTO> persentageAndMarkBoList = attendanceMarksEntryForm.getMarkandPercentageList();
			if(attendanceMarksEntryForm.getSchemeNumber()!=null)
			{
				schemeNumber = Integer.parseInt(attendanceMarksEntryForm.getSchemeNumber());
			}
			if(attendanceMarksEntryForm.getAccYear()!=null)
			{
				year = Integer.parseInt(attendanceMarksEntryForm.getAccYear());
			}
			String[] courseIdset =attendanceMarksEntryForm.getSelectedCourses();
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
				int schemeId = transaction.getSchemIDforCourseAndYear(courseId,year,schemeNumber);
				// getting subject List by course;
				ArrayList<ExamSubDefinitionCourseWiseBO> listBO = new ArrayList(impl.select_course_scheme_year(courseId, schemeId, schemeNumber,year));
				List<Integer> deleteSubjectIDList = new ArrayList<Integer>();
				Iterator iteratorSubject = listBO.iterator();
				while(iteratorSubject.hasNext())
				{
					
					// iterate with mark attendance list
					Object[] row = (Object[])iteratorSubject.next();
					Iterator<AttendanceMarkAndPercentageTO>  iteratorAttendanceMark = persentageAndMarkBoList.iterator();
					while(iteratorAttendanceMark.hasNext())
					{
						AttendanceMarkAndPercentageTO percentageTO = iteratorAttendanceMark.next();
						
						ExamSubCoursewiseAttendanceMarksBO attendanceBO = new ExamSubCoursewiseAttendanceMarksBO();
						
						attendanceBO.setSubjectId(Integer.parseInt(row[0].toString()));
						attendanceBO.setCourseId(courseId);
						attendanceBO.setFromPrcntgAttndnc(new BigDecimal(percentageTO.getFromPercentage()));
						attendanceBO.setToPrcntgAttndnc(new BigDecimal(percentageTO.getToPercentage()));
						attendanceBO.setAttendanceMarks(new BigDecimal(percentageTO.getMarks()));
						attendanceBOList.add(attendanceBO);
					}
					deleteSubjectIDList.add(Integer.parseInt(row[0].toString()));
					
				}
				
				deleteExistingMap.put(courseId, deleteSubjectIDList);
			}
			boolean overwriteExixting = transaction.deleteExixtingData(deleteExistingMap); 
			
			
		}
		catch (Exception e) {
			log.error("Error in converFormTOBOList method in AttendanceMarksEntryHelper.class");
			throw new ApplicationException(e);
		}

		log.debug("end of converFormTOBOList method in AttendanceMarksEntryHelper.class");
		return attendanceBOList;
	}
	public List<AttendanceMarkAndPercentageTO> convertObjectListOfAttendanceToTOList(List<Object[]> bolists) throws Exception {
		log.debug("call of convertObjectListOfAttendanceToTOList method in AttendanceMarksEntryHelper.class");
		List<AttendanceMarkAndPercentageTO> attendaceTOList = new ArrayList<AttendanceMarkAndPercentageTO>();
		try
		{
			if(bolists!= null)
			{
				Iterator<Object[]> iterator = bolists.iterator();
				while(iterator.hasNext())
				{
					Object[] obj= iterator.next();
					AttendanceMarkAndPercentageTO to = new AttendanceMarkAndPercentageTO();
					if(obj[1]!=null)
					{
						to.setNumberOfSubject(obj[1].toString());
					}
					if(obj[2]!=null)
					{
						to.setMarks(obj[2].toString());
					}
					if(obj[3]!=null)
					{
						to.setFromPercentage(obj[3].toString());
					}
					if(obj[4]!=null)
					{
						to.setToPercentage(obj[4].toString());
					}
					if(obj[6]!=null)
					{
						to.setCourseName(obj[6].toString());
					}
					
					attendaceTOList.add(to);
				}

			}
		}
		catch (Exception e) {
			log.error("Error in convertObjectListOfAttendanceToTOList method in AttendanceMarksEntryHelper.class");
			throw new ApplicationException(e);
		}
		log.debug("call of convertObjectListOfAttendanceToTOList method in AttendanceMarksEntryHelper.class");
		return attendaceTOList;
	}

}
