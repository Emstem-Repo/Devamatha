package com.kp.cms.handlers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.to.reports.StudentAdmittedCategoryTO;
import com.kp.cms.transactions.reports.IStudentAdmittedCategoryTransaction;
import com.kp.cms.transactionsimpl.reports.StudentAdmittedCategoryTransactionImpl;

public class StudentAdmittedCategoryHandler {
private static final Log log = LogFactory.getLog(StudentAdmittedCategoryHandler.class);
	
	public static volatile StudentAdmittedCategoryHandler self=null;
	public static StudentAdmittedCategoryHandler getInstance(){
		if(self==null){
			self= new StudentAdmittedCategoryHandler();
		}
		return self;
	}
	private StudentAdmittedCategoryHandler(){
		
	}
	/**
	 * @param programId
	 * @param castCategory
	 * @return
	 * @throws Exception
	 */
	public List<StudentAdmittedCategoryTO> searchStudents(String programId,
			String castCategory) throws Exception{
		IStudentAdmittedCategoryTransaction txn= new StudentAdmittedCategoryTransactionImpl();
		List<Student> students=txn.getSearchedStudents(Integer.parseInt(programId),Integer.parseInt(castCategory));
		return prepareReportTos(students);
	}
	
	/**
	 * @param students
	 * @return
	 */
	private List<StudentAdmittedCategoryTO> prepareReportTos(List<Student> students) throws Exception{
		List<StudentAdmittedCategoryTO> reporttos= null;
		if(students!=null && !students.isEmpty()){
			reporttos=new ArrayList<StudentAdmittedCategoryTO>();
			Iterator<Student> stItr=students.iterator();
			int i=1;
			while (stItr.hasNext()) {
				Student student = (Student) stItr.next();
				StudentAdmittedCategoryTO reportto= new StudentAdmittedCategoryTO();
				reportto.setApplnNo(String.valueOf(student.getAdmAppln().getApplnNo()));
				StringBuffer nameBuff= new StringBuffer();
				nameBuff.append(student.getAdmAppln().getPersonalData().getFirstName());
				if(student.getAdmAppln().getPersonalData().getLastName()!=null)
				{
					nameBuff.append(" ");
					nameBuff.append(student.getAdmAppln().getPersonalData().getLastName());
				}
				reportto.setStudentName(nameBuff.toString());
				reportto.setSlNo(i);
				reporttos.add(reportto);
				i++;
			}
		}
		
		return reporttos;
	}
}
