package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ConsolidatedMarksCardProgrammePart;
import com.kp.cms.bo.exam.ExamStudentCCPA;
import com.kp.cms.bo.exam.ExamStudentSgpa;
import com.kp.cms.forms.exam.CertificateMarksCardForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.helpers.exam.CertificateMarksCardHelper;
import com.kp.cms.to.exam.ConsolidateMarksCardTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class CertificateMarksCardHandler {
	/**
	 * Singleton object of CertificateMarksCardHandler
	 */
	INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
	private static volatile CertificateMarksCardHandler certificateMarksCardHandler = null;
	private static final Log log = LogFactory.getLog(CertificateMarksCardHandler.class);
	private CertificateMarksCardHandler() {
		
	}
	/**
	 * return singleton object of CertificateMarksCardHandler.
	 * @return
	 */
	public static CertificateMarksCardHandler getInstance() {
		if (certificateMarksCardHandler == null) {
			certificateMarksCardHandler = new CertificateMarksCardHandler();
		}
		return certificateMarksCardHandler;
	}
	private static Map<Integer, String> semMap = null;
	private static Map<String, String> monthMap = null;
	static {
		semMap = new HashMap<Integer, String>();
		semMap.put(1, "I");
		semMap.put(2, "II");
		semMap.put(3, "III");
		semMap.put(4, "IV");
		semMap.put(5, "V");
		semMap.put(6, "VI");
		semMap.put(7, "VII");
		semMap.put(8, "VIII");
		semMap.put(9, "IX");
		semMap.put(10, "X");
	}
	static {
		monthMap = new HashMap<String, String>();
		monthMap.put("0", "JANUARY");
		monthMap.put("1", "FEBRUARY");
		monthMap.put("2", "MARCH");
		monthMap.put("3", "APRIL");
		monthMap.put("4", "MAY");
		monthMap.put("5", "JUNE");
		monthMap.put("6", "JULY");
		monthMap.put("7", "AUGUST");
		monthMap.put("8", "SEPTEMBER");
		monthMap.put("9", "OCTOBER");
		monthMap.put("10", "NOVEMBER");
		monthMap.put("11", "DECEMBER");

	}
	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public ConsolidateMarksCardTO getStudentCertificateMarksCard(int studentId) throws Exception {
		String query=CertificateMarksCardHelper.getInstance().getStudentCertificateMarksCardQuery(studentId);
		List<ConsolidateMarksCard> boList=transaction.getDataForQuery(query);
		return CertificateMarksCardHelper.getInstance().convertBotoTo(boList,studentId, false);
	}
	/**
	 * @param certificateMarksCardForm
	 * @return
	 */
	public List<ConsolidateMarksCardTO> getStudentForInput( CertificateMarksCardForm certificateMarksCardForm) throws Exception {
		String query=CertificateMarksCardHelper.getInstance().getStudentIdsQueryForInput(certificateMarksCardForm);
		List<Integer> studentIds=transaction.getDataForQuery(query) ;
		List<ConsolidateMarksCardTO> mainList=new ArrayList<ConsolidateMarksCardTO>();
		ConsolidateMarksCardTO to;
		if(studentIds!=null && !studentIds.isEmpty()){
			Iterator<Integer> itr=studentIds.iterator();
			while (itr.hasNext()) {
				Integer sid = (Integer) itr.next();
//				if(sid==5303){
//				System.out.println(sid);
				String query1=CertificateMarksCardHelper.getInstance().getStudentCertificateMarksCardQuery(sid);
				List<ConsolidateMarksCard> boList=transaction.getDataForQuery(query1);
				 to=CertificateMarksCardHelper.getInstance().convertBotoTo(boList,sid, true);
				 if(to!=null)
					 mainList.add(to);
//				}
			}
		}
		return mainList;
	}
	
	public List<ConsolidateMarksCardTO> getStudentCertificateMarksCardNew(int studentId,LoginForm loginForm) throws Exception {
		List<Object[]> boList1=null;
		if(loginForm.getResultType().equalsIgnoreCase("ProgramPart")){
			String sqlquery=CertificateMarksCardHelper.getInstance().getStudentCertificateMarksCardQuerySQL(studentId,loginForm);
		    boList1=transaction.getDataForQuerySQL(sqlquery);	
		
		}else{
			String query=CertificateMarksCardHelper.getInstance().getStudentCertificateMarksCardQueryNew(studentId,loginForm);
		}
		String query=CertificateMarksCardHelper.getInstance().getStudentCertificateMarksCardQueryNew(studentId,loginForm);
		ExamStudentCCPA examStudentCCPA = transaction.getCCPAdetails(studentId);
		if(examStudentCCPA!=null){
			loginForm.setFinalCCPA(Double.toString(examStudentCCPA.getCcpa()));
			loginForm.setFinalGrade(examStudentCCPA.getGrade());
			loginForm.setFinalResult(examStudentCCPA.getResult());
			String month="";	
			String sem="";
			month=monthMap.get(String.valueOf(examStudentCCPA.getPassOutMonth()));
			loginForm.setYear(month+"_"+examStudentCCPA.getPassOutYear());
			loginForm.setFinalCredit(String.valueOf(examStudentCCPA.getCredit()));
			loginForm.setTotalMaxMark(String.valueOf(examStudentCCPA.getTotalMaxMarks()));
			loginForm.setTotalMarkAwarded(String.valueOf(examStudentCCPA.getTotalMarksAwarded()));
			loginForm.setCourseTitle(examStudentCCPA.getCourse().getName());
		}
		if(loginForm.getResultType().equalsIgnoreCase("ProgramPart")){
			//List<ConsolidatedMarksCardProgrammePart> boList=transaction.getDataForQuery(query);	
			return CertificateMarksCardHelper.getInstance().convertBotoToNew1(boList1,studentId, false,loginForm);
		}else{
			List<ExamStudentSgpa> boList=transaction.getDataForQuery(query);
			return CertificateMarksCardHelper.getInstance().convertBotoToNew(boList,studentId, false,loginForm);
		}
		
		
	}
	
	public List<ConsolidateMarksCardTO> getStudentCertificateMarksCardForProgramme(int studentId,LoginForm loginForm) throws Exception {
		List<Object[]> boList1=null;

		String sqlquery=CertificateMarksCardHelper.getInstance().getStudentCertificateMarksCardQuerySQL(studentId,loginForm);
		boList1=transaction.getDataForQuerySQL(sqlquery);	
		ExamStudentCCPA examStudentCCPA = transaction.getCCPAdetails(studentId);
		if(examStudentCCPA!=null){
			loginForm.setFinalCCPA(Double.toString(examStudentCCPA.getCcpa()));
			loginForm.setFinalGrade(examStudentCCPA.getGrade());
			loginForm.setFinalResult(examStudentCCPA.getResult());
			String month="";	
			String sem="";
			month=monthMap.get(String.valueOf(examStudentCCPA.getPassOutMonth()));
			loginForm.setYear(month+"_"+examStudentCCPA.getPassOutYear());
			loginForm.setFinalCredit(String.valueOf(examStudentCCPA.getCredit()));
			loginForm.setTotalMaxMark(String.valueOf(examStudentCCPA.getTotalMaxMarks()));
			loginForm.setTotalMarkAwarded(String.valueOf(examStudentCCPA.getTotalMarksAwarded()));
			loginForm.setCourseTitle(examStudentCCPA.getCourse().getName());
		}

		return CertificateMarksCardHelper.getInstance().convertBotoToNew1(boList1,studentId, false,loginForm);
	}
	
	public List<ConsolidateMarksCardTO> getStudentCertificateMarksCardSemester(int studentId,LoginForm loginForm) throws Exception {
		
		String query=CertificateMarksCardHelper.getInstance().getStudentCertificateMarksCardQueryNew(studentId,loginForm);
		ExamStudentCCPA examStudentCCPA = transaction.getCCPAdetails(studentId);
		if(examStudentCCPA!=null){
			loginForm.setFinalCCPA(Double.toString(examStudentCCPA.getCcpa()));
			loginForm.setFinalGrade(examStudentCCPA.getGrade());
			loginForm.setFinalResult(examStudentCCPA.getResult());
			String month="";	
			String sem="";
			month=monthMap.get(String.valueOf(examStudentCCPA.getPassOutMonth()));
			loginForm.setYear(month+"_"+examStudentCCPA.getPassOutYear());
			loginForm.setFinalCredit(String.valueOf(examStudentCCPA.getCredit()));
			loginForm.setTotalMaxMark(String.valueOf(examStudentCCPA.getTotalMaxMarks()));
			loginForm.setTotalMarkAwarded(String.valueOf(examStudentCCPA.getTotalMarksAwarded()));
			loginForm.setCourseTitle(examStudentCCPA.getCourse().getName());
		}
		List<ExamStudentSgpa> boList=transaction.getDataForQuery(query);
		return CertificateMarksCardHelper.getInstance().convertBotoToNew(boList,studentId, false,loginForm);
	}
}
