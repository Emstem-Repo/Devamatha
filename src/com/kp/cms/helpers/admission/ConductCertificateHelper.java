package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ibm.icu.text.SimpleDateFormat;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ConductCertificateDetails;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.forms.admission.ConductCertificateForm;
import com.kp.cms.forms.admission.TransferCertificateForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admission.TransferCertificateHandler;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.transactions.admission.IConductCertificateTransaction;
import com.kp.cms.transactionsimpl.admission.ConductCertificateTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ConductCertificateHelper {
public static volatile ConductCertificateHelper certificateHelper=null;
	
	public static ConductCertificateHelper getInstance()
	{
		if(certificateHelper==null)
			certificateHelper=new ConductCertificateHelper();
		return certificateHelper;
	}
	
private static Map<String, String> monthMap = null;
	
	static {
		monthMap = new HashMap<String, String>();
		monthMap.put("JAN", "JANUARY");
		monthMap.put("FEB", "FEBRUARY");
		monthMap.put("MAR", "MARCH");
		monthMap.put("APR", "APRIL");
		monthMap.put("MAY", "MAY");
		monthMap.put("JUN", "JUNE");
		monthMap.put("JUL", "JULY");
		monthMap.put("AUG", "AUGUST");
		monthMap.put("SEPT", "SEPTEMBER");
		monthMap.put("OCT", "OCTOBER");
		monthMap.put("NOV", "NOVEMBER");
		monthMap.put("DEC", "DECEMBER");
		
	}
	
	IConductCertificateTransaction transferCertificate= new ConductCertificateTransactionImpl();

	public List<PrintTcDetailsTo> convertStudentBoToTo(
			List<ConductCertificateDetails> studentBoList,HttpServletRequest request, ConductCertificateForm form,int programType) throws NumberFormatException, Exception {
		List<PrintTcDetailsTo>studentToList=new ArrayList<PrintTcDetailsTo>();
		if(studentBoList!=null && studentBoList.size()!=0)
		{
			int ptype=programType;
			for(ConductCertificateDetails student:studentBoList)
			{
				PrintTcDetailsTo to =new PrintTcDetailsTo();
				to.setStudentName(student.getStudent().getAdmAppln().getPersonalData().getFirstName());
				//System.out.println(rollNo.get(i));
				to.setCcNo(student.getCcNo());
				to.setRollNo(student.getStudent().getRollNo());
				//to.setCurDate(String.valueOf(student.getStudent().getAdmAppln().getDate()));
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				 //String DateToStr = format.format(student.getStudent().getAdmAppln().getDate());
				 String DateToStr = format.format(new java.util.Date());
				 to.setCurDate(DateToStr);
				 to.setAcademicYear(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getStudent().getAdmAppln().getAdmissionDate()),"dd-MMM-yyyy","dd-MM-yyyy"));
				//to.setCurDate(DateToStr);
				 to.setSlNo(student.getStudent().getSlNo());
				 to.setRegNo(student.getStudent().getRegisterNo());
				to.setAdmissionnumber(student.getStudent().getAdmAppln().getAdmissionNumber());
				if(student.getCharacterAndConduct()!=null)
				to.setConduct(student.getCharacterAndConduct().getName());
				

				
				
				
				if(student.getStudent().getAdmAppln().getAdmissionDate()!=null)
				to.setDateOfAdmission(student.getStudent().getAdmAppln().getAdmissionDate().toString());
				if (student.getCourseName()!=null) {
					to.setCourse(student.getCourseName());
				}
				else {
					to.setCourse(student.getStudent().getClassSchemewise().getClasses().getCourse().getName());
				}
				if(student.getStudent().getClassSchemewise().getClasses().getCourse().getProgram() != null)
				{
					/*int stYr=student.getStudent().getAdmAppln().getAppliedYear();
					Date edDate = student.getStudent().getClassSchemewise().getCurriculumSchemeDuration().getEndDate();
					SimpleDateFormat sdfr = new SimpleDateFormat("yyyy");
					String edYr = sdfr.format(edDate);						
						if(stYr==Integer.parseInt(edYr))
							if (ptype==1) {
								edYr = Integer.parseInt(edYr)+3+"";
							}
						else {
							edYr = Integer.parseInt(edYr)+2+"";
						}*/
						to.setDuration(student.getAcademicDuration());
				}
				studentToList.add(to);
			}
			
		}
		
		return studentToList;
	}
	
	public List<TCDetailsTO> convertStudentTcDetailsBoToToForReprintOnlyTc(
			List<StudentTCDetails> studentBo, HttpServletRequest request,
			ConductCertificateForm certificateForm) {
		List<TCDetailsTO> to = new ArrayList<TCDetailsTO>();
		if(studentBo!=null)
		{
			Iterator<StudentTCDetails> itr=studentBo.iterator();
			
			while(itr.hasNext())
			{
				StudentTCDetails bo = itr.next();
				TCDetailsTO detailsTo =new TCDetailsTO();
				detailsTo.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
				detailsTo.setCourse(bo.getStudent().getClassSchemewise().getClasses().getCourse().getName());
				detailsTo.setRegisterNo(bo.getStudent().getRegisterNo());
				detailsTo.setStudentId(bo.getStudent().getId());
				/*detailsTo.setSubjectsPassedComplimentary(bo.getStudent().getSubjectsPassedComplimentary());
				detailsTo.setSubjectsPassedCore(bo.getStudent().getSubjectPassedCore());
				detailsTo.setExamRegNo(bo.getStudent().getExamRegNo());*/
				
				to.add(detailsTo);
			}	
		}
		return to;
	}
}
