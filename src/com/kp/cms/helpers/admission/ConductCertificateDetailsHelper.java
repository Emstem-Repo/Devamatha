package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.text.SimpleDateFormat;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.ConductCertificateDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.ConductCertificateDetailsForm;
import com.kp.cms.forms.admission.ConductCertificateForm;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.ConductCertificateDetailsTo;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.transactions.admission.IConductCertificateDetailsTransaction;
import com.kp.cms.transactions.admission.IConductCertificateTransaction;
import com.kp.cms.transactions.admission.ITCDetailsTransaction;
import com.kp.cms.transactions.admission.ITransferCertificateTransaction;
import com.kp.cms.transactionsimpl.admission.ConductCertificateDetailsImpl;
import com.kp.cms.transactionsimpl.admission.ConductCertificateTransactionImpl;
import com.kp.cms.transactionsimpl.admission.TCDetailsTransactionImpl;
import com.kp.cms.transactionsimpl.admission.TransferCertificateTransactionImpl;

public class ConductCertificateDetailsHelper {
	private static volatile ConductCertificateDetailsHelper conductCertificateDetailsHelper = null;
	private static final Log log = LogFactory.getLog(ConductCertificateDetailsHelper.class);
	private ConductCertificateDetailsHelper() {
		
	}	/**
	 * return singleton object of TCDetailsHelper.
	 * @return
	 */
	public static ConductCertificateDetailsHelper getInstance() {
		if (conductCertificateDetailsHelper == null) {
			conductCertificateDetailsHelper = new ConductCertificateDetailsHelper();
		}
		return conductCertificateDetailsHelper;
	}
	
	public String getSearchQuery(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception {
		String query="from Student s where  s.admAppln.isCancelled=0 " +
				" and s.isActive=1 and s.classSchemewise.id="+conductCertificateDetailsForm.getClassId();
		return query+" order by s.admAppln.personalData.firstName";
	}
	public List<BoardDetailsTO> convertBotoToList(List<Student> studentList) throws Exception {
		List<BoardDetailsTO> boardDetailsTOs=new ArrayList<BoardDetailsTO>();
		IConductCertificateDetailsTransaction transaction=ConductCertificateDetailsImpl.getInstance();
		BoardDetailsTO boardDetailsTO;
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<Student> iterator=studentList.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				boardDetailsTO=new BoardDetailsTO();
				boardDetailsTO.setRollNo(student.getRollNo());
				boardDetailsTO.setStudentId(student.getId());
				boardDetailsTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
				boolean showPrint=transaction.checkCcAvailable(student.getId());
				boardDetailsTO.setShowPrint(showPrint);
				boardDetailsTO.setIsKerala("yes");
				boardDetailsTOs.add(boardDetailsTO);
			}
		}
		return boardDetailsTOs;
	}
	
	public void convertBotoFormAllStudentsByClass(String subjects,
			  ExamDefinitionBO bo, 
			  ConductCertificateDetailsForm conductCertificateDetailsForm) throws NumberFormatException, Exception {		

		ConductCertificateDetailsTo to=new ConductCertificateDetailsTo();

		to.setCourseName(conductCertificateDetailsForm.getClassName());				

		to.setClassOfLeaving(conductCertificateDetailsForm.getClassOfLeaving());
		to.setClassId(conductCertificateDetailsForm.getClassId());	

    }
	public String getSearchQueryForTCDetails(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception {
		String query=" from ConductCertificateDetails s where s.student.id="+conductCertificateDetailsForm.getStudentId();
		return query;
	}
	public List<PrintTcDetailsTo> convertStudentBoToTo(
			List<Student> studentBoList,HttpServletRequest request,ConductCertificateDetailsForm  form,String rollNo) throws Exception {
		List<PrintTcDetailsTo>studentToList=new ArrayList<PrintTcDetailsTo>();
		IConductCertificateDetailsTransaction transaction=ConductCertificateDetailsImpl.getInstance();
  		if(studentBoList!=null && studentBoList.size()!=0)
		{
			for(Student student:studentBoList)
			{
				PrintTcDetailsTo to =new PrintTcDetailsTo();
				ConductCertificateDetails bo = transaction.getStudentListIfCcDetailsExist(student.getId());
				if(bo!=null)
				to.setCcNo(bo.getCcNo());
				if (bo.getCharacterAndConduct()!=null) {
					to.setConduct(bo.getCharacterAndConduct().getName());
				}
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName().trim());				
				//to.setDateOfAdmission(bo.getDateOfApplication().toString());
				to.setCourse(bo.getCourseName());;
				String DateToStr = format.format(new java.util.Date());
				to.setCurDate(DateToStr);
				to.setAdmissionnumber(student.getAdmAppln().getAdmissionNumber());
				if(form.getIsKerala().equalsIgnoreCase("yes")){
					to.setIsKerala(true);
				}else{
					to.setIsKerala(false);
				}
				to.setSex(bo.getStudent().getAdmAppln().getPersonalData().getGender().toLowerCase());
				
				to.setDuration(bo.getAcademicDuration());
				to.setRollNo(rollNo);
                 if(form.getProgramTypeId().equalsIgnoreCase("1")){
                	 to.setProgramType("UG");
                	 to.setProgramDuration("three");
				}
                 if(form.getProgramTypeId().equalsIgnoreCase("2")){
                	 to.setProgramType("PG"); 
                	 if(form.getClassId().equalsIgnoreCase("15") || form.getClassId().equalsIgnoreCase("34")){
                		to.setProgramDuration("one");
                	 }else{
                		 to.setProgramDuration("two");
                	 }
                 }
                 if(form.getProgramTypeId().equalsIgnoreCase("3")){
                	 to.setProgramType("Pre.Doctoral"); 
                	 to.setProgramDuration("one");
                 }
				
				studentToList.add(to);
			}
		}	
		
		return studentToList;
	}
	public void getformData(ConductCertificateDetailsForm conductCertificateDetailsForm) throws NumberFormatException, Exception {
		IConductCertificateDetailsTransaction transaction=ConductCertificateDetailsImpl.getInstance();
		int ptype=transaction.getPtype(conductCertificateDetailsForm.getClassId());
		String subjects = null;
		ConductCertificateDetails conductdetails=null;
		String accDur=null;
		String accDuration=null;
		if (conductCertificateDetailsForm.getStudentId()!=null) {
			conductdetails=transaction.getStudentListIfCcDetailsExist(Integer.parseInt(conductCertificateDetailsForm.getStudentId()));
		}
		if (conductdetails!=null) {
			accDuration=conductdetails.getAcademicDuration();
			subjects=conductdetails.getCourseName();
			conductCertificateDetailsForm.setCourseName(subjects);
			conductCertificateDetailsForm.setAcademicDuration(accDuration);
		}
		else{
			subjects=transaction.getStudentSubjectCode(conductCertificateDetailsForm.getClassId());
		accDur=transaction.getAccDuration(conductCertificateDetailsForm.getClassId());
		if(ptype==1){
			accDuration=accDur+"-"+(Integer.parseInt(accDur)+3);
		}
		else{
			accDuration=accDur+"-"+(Integer.parseInt(accDur)+2);
		}
		
		conductCertificateDetailsForm.setCourseName(subjects);
		conductCertificateDetailsForm.setAcademicDuration(accDuration);
		}
	}
}
