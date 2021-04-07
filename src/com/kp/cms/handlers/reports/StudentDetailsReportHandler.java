package com.kp.cms.handlers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Session;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.forms.reports.StudentDetailsReportForm;
import com.kp.cms.helpers.reports.StudentDetailsReportHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.reports.StudentDetailsReportTO;
import com.kp.cms.transactions.reports.IStudentDetailsReportTransaction;
import com.kp.cms.transactionsimpl.reports.StudentDetailsReportTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * @author dIlIp
 *
 */
public class StudentDetailsReportHandler {

	private static final Log log = LogFactory.getLog(StudentDetailsReportHandler.class);
	
	public static volatile StudentDetailsReportHandler studentDetailsReportHandler = null;

	public static StudentDetailsReportHandler getInstance() {
		if (studentDetailsReportHandler == null) {
			studentDetailsReportHandler = new StudentDetailsReportHandler();
		}
		return studentDetailsReportHandler;
	}

	private StudentDetailsReportHandler() {

	}

	/**
	 * @param stForm
	 * @return
	 * @throws Exception
	 */
	public static List<StudentTO> getSearchedStudents(StudentDetailsReportForm stForm) throws Exception {
		log.info("start of getSearchedStudents method");
		IStudentDetailsReportTransaction txn = new StudentDetailsReportTransactionImpl();
		
		if(stForm.getIsCurrentYear().equalsIgnoreCase("previous"))
			stForm.setAcademicYear(stForm.getPreviousYears());
		else
			stForm.setAcademicYear(String.valueOf(CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher()));
		
		List<Student> studentList = new ArrayList<Student>();
		List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetails = new ArrayList<ExamStudentDetentionRejoinDetails>();
		
		if((stForm.getStatus()!=null && !stForm.getStatus().isEmpty()) && (stForm.getStatus().equalsIgnoreCase("Detained") || (stForm.getStatus().equalsIgnoreCase("Discontinued")))){
			examStudentDetentionRejoinDetails = txn.getExamStudentDetentionRejoinDetails(stForm);
		}
		else{
			StringBuffer query = StudentDetailsReportHelper.getSelectionSearchCriteria(stForm);
			studentList = txn.getSearchedStudents(query);
		}
		if(stForm.getPreviousYears().equalsIgnoreCase(""))
			stForm.setPreviousYears("0");
		List<StudentTO> studentTOs = StudentDetailsReportHelper.convertStudentBoTOTo(studentList, examStudentDetentionRejoinDetails, stForm.getPreviousYears());
	  	List<StudentTO> studentTOsFull = StudentDetailsReportHelper.convertStudentBoTOToFull(studentList, examStudentDetentionRejoinDetails, stForm.getPreviousYears());
	  	stForm.setStudentToList(studentTOsFull);
	  	return studentTOs;
		
	}

	/**
	 * @param stForm
	 * @param request
	 * @param session 
	 * @return
	 * @throws Exception
	 */
	public boolean exportTOExcel(StudentDetailsReportForm stForm, HttpServletRequest request, HttpSession session) throws Exception {
		
		boolean isUpdated= false;
		
		List<StudentTO> studentTOs = stForm.getStudentToList();
		
		StudentDetailsReportTO stuReportTo = StudentDetailsReportHelper.getInstance().getSelectedColumns(stForm);
		isUpdated = StudentDetailsReportHelper.getInstance().convertToExcel(studentTOs,stuReportTo,request);
		return isUpdated;
	
	}

	/**
	 * @return list of deaneries
	 * @throws Exception
	 */
	public List<String> getDeanery() throws Exception{
		
		IStudentDetailsReportTransaction txn = new StudentDetailsReportTransactionImpl();
		return txn.getDeanery();
		
	}
	
	

}
