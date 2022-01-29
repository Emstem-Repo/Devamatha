package com.kp.cms.handlers.exam;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.bo.exam.ExamRevaluationFee;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.admission.InterviewHelper;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.ClearanceCertificateTO;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.exam.ShowMarksCardTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class DownloadHallTicketHandler {
	private static final Log log = LogFactory.getLog(DownloadHallTicketHandler.class);
	public static volatile DownloadHallTicketHandler downloadHallTicketHandler = null;
	
	public static DownloadHallTicketHandler getInstance() {
	if (downloadHallTicketHandler == null) {
		downloadHallTicketHandler = new DownloadHallTicketHandler();
		return downloadHallTicketHandler;
	}
	return downloadHallTicketHandler;
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public int getClassId(int studentId, LoginForm loginForm) throws Exception {
		log.debug("getClassId");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		
		return iDownloadHallTicketTransaction.getClassId(studentId, loginForm);
	}
	
	/**
	 * 
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassId(int classId, LoginForm loginForm, String hallTicket) throws Exception {
		log.debug("getExamIdByClassId");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		
		return iDownloadHallTicketTransaction.getExamIdByClassId(classId, loginForm, hallTicket);
	}
	
	public int getExamIdByClassIdMarksCard(int classId, LoginForm loginForm, String hallTicket) throws Exception {
		log.debug("getExamIdByClassId");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		
		return iDownloadHallTicketTransaction.getExamIdByClassIdMarksCard(classId, loginForm, hallTicket);
	}
	
	
	/**
	 * 
	 * @param studentId
	 * @param classId
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public ExamBlockUnblockHallTicketBO isBlockedStudent(int studentId, int classId, int examId, String hallTicketMarksCard) throws Exception {
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.isHallTicketBlockedStudent(studentId, classId, examId, hallTicketMarksCard);
	}	
	
	/**
	 * 
	 * @param classId
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public boolean isDateValid(int classId, int examId, String hallTicketMarksCard, boolean isSup) throws Exception {
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.isCurrentDateValidForDownLoadHallTicket(classId, examId, hallTicketMarksCard, isSup);
	}	
	
	/**
	 * 
	 * @param agreementId
	 * @return
	 * @throws Exception
	 */
	public ExamFooterAgreementBO getAgreement(int agreementId) throws Exception {
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getExamFooterAgreement(agreementId);
	}	
	/**
	 * 
	 * @param studentId
	 * @param curClassId
	 * @return
	 * @throws Exception
	 */
	public int getClassIds(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception{
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getClassIds(studentId, curClassId, isSup, publishedFor);
	}
	/**
	 * 
	 * @param studentId
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public boolean getIsExcluded(int studentId, int examId)throws Exception{
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getIsExcluded(studentId, examId);
	}
	
	public Integer getTermNumber(Integer classId) throws Exception {
		log.debug("getTermNumber");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		
		return iDownloadHallTicketTransaction.getTermNumber(classId);
	}
	
	/**
	 * 
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public String getExamIdByClassIdForSupHallTicket(int classId, LoginForm loginForm, String hallTicket) throws Exception {
		log.debug("getExamIdByClassIdForSupHallTicket");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		
		return iDownloadHallTicketTransaction.getExamIdByClassIdForSupHallTicket(classId, loginForm, hallTicket);
	}
	
	/**
	 * 
	 * @param studentId
	 * @param examId
	 * @param mode 
	 * @return
	 * @throws Exception
	 */
	public boolean isAppeared(int studentId, int examId,int classId, String mode) throws Exception {
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getIsAppliedForSupp(studentId, examId,classId,mode);
	}
	public HallTicketTo getHallticketForStudent(LoginForm loginForm) throws Exception {
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		String hallTicketQuery= DownloadHallTicketHelper.getInstance().getQueryForStudentHallTicket(loginForm.getStudentId(),loginForm.getExamId());
		int schemeNoByClassId = transaction.getTermNumber(loginForm.getRevClassId());
		String prevHallTicketQuery= DownloadHallTicketHelper.getInstance().getQueryForStudentPrevHallTicket(loginForm.getStudentId(),loginForm.getExamId(),schemeNoByClassId-1);
		List<ExamFooterAgreementBO> footer=transaction.getFooterDetails(loginForm.getProgramTypeId(),"H",loginForm.getClassId());
		if(footer!=null && !footer.isEmpty()){
			ExamFooterAgreementBO obj=footer.get(0);
			if(obj.getDescription()!=null)
				loginForm.setDescription1(obj.getDescription());
		}else{
			loginForm.setDescription1(null);
		}
		List hallTicketData=transaction.getStudentHallTicket(hallTicketQuery);
		// vinodha
		if(hallTicketData==null || hallTicketData.size()==0){
			hallTicketData=transaction.getStudentHallTicket(prevHallTicketQuery);
		}
		/* code added by sudhir */
		Map<String,ExamRoomAllotmentDetailsTO> examSessionMap = new HashMap<String, ExamRoomAllotmentDetailsTO>();
		 transaction.getRoomNoAndFloorNoAndBlockNo(loginForm.getStudentId(),loginForm.getExamId(),Integer.parseInt(loginForm.getClassId()),examSessionMap);
		 /* code added by sudhir */
		if(hallTicketData!=null && !hallTicketData.isEmpty()){
		 return DownloadHallTicketHelper.getInstance().hallTicketListtoTo(hallTicketData,examSessionMap);
		}
		return null;
	}
	/**
	 * @param loginForm
	 * @throws Exception
	 */
	public MarksCardTO getMarksCardForUG(LoginForm loginForm,HttpServletRequest request) throws Exception {
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		String accYearQuery = "select e.academicYear from ExamDefinition e where e.id ="+Integer.parseInt(loginForm.getRegularExamId().substring(0, loginForm.getRegularExamId().indexOf('_')));
		List list=txn.getDataForQuery(accYearQuery);
		int academicYesr= (Integer) list.get(0);

		String query=DownloadHallTicketHelper.getInstance().getQueryForUGStudentMarksCard(loginForm.getExamIDForMCard(),loginForm.getMarksCardClassId()
				,loginForm.getSemesterYearNo(),loginForm.getStudentId(),academicYesr);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		boolean isRevaluation=false;
		if(loginForm.getRevaluationRegClassId()!=null && loginForm.getRevaluationRegClassId().contains(loginForm.getMarksCardClassId())){
			isRevaluation=true;
			Map<Integer,String> revDateMap=loginForm.getRevDateMap();
			loginForm.setRevDate(revDateMap.get(loginForm.getMarksCardClassId()));
		}
		String revaluationFeeQuery="from ExamRevaluationFee e where e.isActive=1 and e.programType.id=" +
				" (select s.admAppln.courseBySelectedCourseId.program.programType.id from Student s where id="+loginForm.getStudentId()+")";
		List<ExamRevaluationFee> revalationList=txn.getDataForQuery(revaluationFeeQuery);
		Map<String,Double> revalationFeeMap=new HashMap<String, Double>();
		if(revalationList!=null && !revalationList.isEmpty()){
			Iterator<ExamRevaluationFee> itr=revalationList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationFee examRevaluationFee = itr .next();
				if(examRevaluationFee.getType()!=null && !revalationFeeMap.containsKey(examRevaluationFee.getType())){
					revalationFeeMap.put(examRevaluationFee.getType(), examRevaluationFee.getAmount().doubleValue());
				}
			}
		}
		loginForm.setRevalationFeeMap(revalationFeeMap);
		String revaluationSubjectQuery="from ExamRevaluationAppDetails e where e.examRevApp.isActive=1 and e.examRevApp.exam.id=" +loginForm.getExamIDForMCard()+
				" and e.examRevApp.classes.id=" +loginForm.getMarksCardClassId()+
				" and e.examRevApp.student.id="+loginForm.getStudentId();
		List<ExamRevaluationAppDetails> revaluationSubjectList=txn.getDataForQuery(revaluationSubjectQuery);
		
		Map<Integer,String> revaluationSubjects=new HashMap<Integer, String>();
		if(revaluationSubjectList!=null && !revaluationSubjectList.isEmpty()){
			Iterator<ExamRevaluationAppDetails> itr=revaluationSubjectList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationAppDetails bo =itr .next();
				revaluationSubjects.put(bo.getSubject().getId(),bo.getStatus());
			}
		}
		loginForm.setRevaluationSubjects(revaluationSubjects);
		loginForm.setCheckRevaluation(isRevaluation);
		
		List<Object[]> ugMarksCardData=transaction.getStudentHallTicket(query);
		MarksCardTO to=DownloadHallTicketHelper.getInstance().getMarksCardForUg(ugMarksCardData,loginForm.getSemesterYearNo(),loginForm.getStudentId(),new HashMap<Integer, byte[]>(),request,revaluationSubjects);
		to.setExam(loginForm.getExamIDForMCard());
		to.setClassesId(loginForm.getMarksCardClassId());
		to.setStudentId(loginForm.getStudentId());
		return to;
	}
	/**
	 * @param loginForm
	 * @throws Exception
	 */
	public MarksCardTO getMarksCardForPG(LoginForm loginForm,HttpServletRequest request) throws Exception {
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		String accYearQuery = "select e.academicYear from ExamDefinition e where e.id ="+Integer.parseInt(loginForm.getRegularExamId().substring(0, loginForm.getRegularExamId().indexOf('_')));
		List list=txn.getDataForQuery(accYearQuery);
		int academicYesr= (Integer) list.get(0);

		String query=DownloadHallTicketHelper.getInstance().getQueryForPGStudentMarksCard(loginForm.getExamIDForMCard(),loginForm.getMarksCardClassId()
				,loginForm.getStudentId(),loginForm.getSemesterYearNo(),academicYesr);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		String programTypeId=	NewSecuredMarksEntryHandler.getInstance().getPropertyValue(loginForm.getMarksCardClassId(),"Classes",true,"course.program.programType.id");
		boolean isRevaluation=false;
		if(loginForm.getRevaluationRegClassId()!=null && loginForm.getRevaluationRegClassId().contains(loginForm.getMarksCardClassId())){
			isRevaluation=true;
			Map<Integer,String> revDateMap=loginForm.getRevDateMap();
			loginForm.setRevDate(revDateMap.get(loginForm.getMarksCardClassId()));
		}
		String revaluationFeeQuery="from ExamRevaluationFee e where e.isActive=1 and e.programType.id=" +
				" (select s.admAppln.courseBySelectedCourseId.program.programType.id from Student s where id="+loginForm.getStudentId()+")";
		List<ExamRevaluationFee> revalationList=txn.getDataForQuery(revaluationFeeQuery);
		Map<String,Double> revalationFeeMap=new HashMap<String, Double>();
		if(revalationList!=null && !revalationList.isEmpty()){
			Iterator<ExamRevaluationFee> itr=revalationList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationFee examRevaluationFee = itr .next();
				if(examRevaluationFee.getType()!=null && !revalationFeeMap.containsKey(examRevaluationFee.getType())){
					revalationFeeMap.put(examRevaluationFee.getType(), examRevaluationFee.getAmount().doubleValue());
				}
			}
		}
		loginForm.setRevalationFeeMap(revalationFeeMap);
		String revaluationSubjectQuery="from ExamRevaluationAppDetails e where e.examRevApp.isActive=1 and e.examRevApp.exam.id=" +loginForm.getExamIDForMCard()+
				" and e.examRevApp.classes.id=" +loginForm.getMarksCardClassId()+
				" and e.examRevApp.student.id="+loginForm.getStudentId();
		List<ExamRevaluationAppDetails> revaluationSubjectList=txn.getDataForQuery(revaluationSubjectQuery);
		
		Map<Integer,String> revaluationSubjects=new HashMap<Integer, String>();
		if(revaluationSubjectList!=null && !revaluationSubjectList.isEmpty()){
			Iterator<ExamRevaluationAppDetails> itr=revaluationSubjectList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationAppDetails bo =itr .next();
				revaluationSubjects.put(bo.getSubject().getId(),bo.getStatus());
			}
		}
		loginForm.setRevaluationSubjects(revaluationSubjects);
		loginForm.setCheckRevaluation(isRevaluation);
		
		
		List<Object[]> pgMarksCardData=transaction.getStudentHallTicket(query);
		MarksCardTO to=DownloadHallTicketHelper.getInstance().getMarksCardForPg(pgMarksCardData,loginForm.getStudentId(),new HashMap<Integer, byte[]>(),request,revaluationSubjects,Integer.parseInt(programTypeId));
		to.setExam(loginForm.getExamIDForMCard());
		to.setClassesId(loginForm.getMarksCardClassId());
		to.setStudentId(loginForm.getStudentId());
		return to;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public HallTicketTo getSupHallticketForStudent(LoginForm loginForm,HttpServletRequest request) throws Exception{
		HttpSession session=request.getSession(false);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<ShowMarksCardTO> list=(ArrayList<ShowMarksCardTO>) session.getAttribute("supHallTicketList");
		String query="";
		for (ShowMarksCardTO to : list) {
			if(to.getCnt()==loginForm.getCount()){
				query=DownloadHallTicketHelper.getInstance().getQueryForSupHallTicket(loginForm.getStudentId(),to.getSupMCexamID(),to.getClassIds());
			}
		}
		
		List<Object[]> hallTicket=transaction.getStudentHallTicket(query);
		return DownloadHallTicketHelper.getInstance().getSupHallTicketForStudent(hallTicket);
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public MarksCardTO getSupMarksCardForUG(LoginForm loginForm,HttpServletRequest request) throws Exception {
		// get exam academic year by passing exam id to exam_def table
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();//azr
		String acdemicYearQuery = "select e.academicYear from ExamDefinition e where e.id="+loginForm.getExamId();
		List<Integer> list =txn.getDataForQuery(acdemicYearQuery);
		int academicYear = Integer.parseInt(list.get(0).toString());
		String query=DownloadHallTicketHelper.getInstance().getQueryForUGStudentSupMarksCard(loginForm.getSupMCexamID(),loginForm.getSupMCClassId(),loginForm.getSupMCsemesterYearNo(),loginForm.getStudentId(),academicYear);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		
		boolean isRevaluation=false;
		if(loginForm.getRevaluationSupClassId()!=null && loginForm.getRevaluationSupClassId().contains(loginForm.getSupMCClassId())){
			isRevaluation=true;
			Map<Integer,String> revDateMap=loginForm.getSuprevDateMap();
			loginForm.setRevDate(revDateMap.get(loginForm.getSupMCClassId()));
		}
		String revaluationFeeQuery="from ExamRevaluationFee e where e.isActive=1 and e.programType.id=" +
				" (select s.admAppln.courseBySelectedCourseId.program.programType.id from Student s where id="+loginForm.getStudentId()+")";
		List<ExamRevaluationFee> revalationList=txn.getDataForQuery(revaluationFeeQuery);
		Map<String,Double> revalationFeeMap=new HashMap<String, Double>();
		if(revalationList!=null && !revalationList.isEmpty()){
			Iterator<ExamRevaluationFee> itr=revalationList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationFee examRevaluationFee = itr .next();
				if(examRevaluationFee.getType()!=null && !revalationFeeMap.containsKey(examRevaluationFee.getType())){
					revalationFeeMap.put(examRevaluationFee.getType(), examRevaluationFee.getAmount().doubleValue());
				}
			}
		}
		loginForm.setRevalationFeeMap(revalationFeeMap);
		String revaluationSubjectQuery="from ExamRevaluationAppDetails e where e.examRevApp.isActive=1 and e.examRevApp.exam.id=" +loginForm.getSupMCexamID()+
				" and e.examRevApp.classes.id=" +loginForm.getSupMCClassId()+
				" and e.examRevApp.student.id="+loginForm.getStudentId();
		List<ExamRevaluationAppDetails> revaluationSubjectList=txn.getDataForQuery(revaluationSubjectQuery);
		
		Map<Integer,String> revaluationSubjects=new HashMap<Integer, String>();
		if(revaluationSubjectList!=null && !revaluationSubjectList.isEmpty()){
			Iterator<ExamRevaluationAppDetails> itr=revaluationSubjectList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationAppDetails bo =itr .next();
				revaluationSubjects.put(bo.getSubject().getId(),bo.getStatus());
			}
		}
		loginForm.setRevaluationSubjects(revaluationSubjects);
		loginForm.setCheckRevaluation(isRevaluation);
		
		List<Object[]> ugMarksCardData=transaction.getStudentHallTicket(query);
		MarksCardTO to=DownloadHallTicketHelper.getInstance().getSupMarksCardForUg(ugMarksCardData,loginForm.getSupMCsemesterYearNo(),loginForm.getStudentId(),new HashMap<Integer, byte[]>(),request,loginForm.getSupMCexamID(),revaluationSubjects);
		to.setExam(loginForm.getSupMCexamID());
		to.setClassesId(loginForm.getSupMCClassId());
		to.setStudentId(loginForm.getStudentId());
		return to;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public MarksCardTO getSupMarksCardForPG(LoginForm loginForm,HttpServletRequest request)throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getQueryForPGStudentSupMarksCard(loginForm.getSupMCexamID(),loginForm.getSupMCClassId(),loginForm.getSupMCsemesterYearNo(),loginForm.getStudentId(),Integer.parseInt(loginForm.getYear()));
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		boolean isRevaluation=false;
		if(loginForm.getRevaluationSupClassId()!=null && loginForm.getRevaluationSupClassId().contains(loginForm.getSupMCClassId())){
			isRevaluation=true;
			Map<Integer,String> revDateMap=loginForm.getSuprevDateMap();
			loginForm.setRevDate(revDateMap.get(loginForm.getSupMCClassId()));
		}
		
		String revaluationFeeQuery="from ExamRevaluationFee e where e.isActive=1 and e.programType.id=" +
				" (select s.admAppln.courseBySelectedCourseId.program.programType.id from Student s where id="+loginForm.getStudentId()+")";
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		List<ExamRevaluationFee> revalationList=txn.getDataForQuery(revaluationFeeQuery);
		Map<String,Double> revalationFeeMap=new HashMap<String, Double>();
		if(revalationList!=null && !revalationList.isEmpty()){
			Iterator<ExamRevaluationFee> itr=revalationList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationFee examRevaluationFee = itr .next();
				if(examRevaluationFee.getType()!=null && !revalationFeeMap.containsKey(examRevaluationFee.getType())){
					revalationFeeMap.put(examRevaluationFee.getType(), examRevaluationFee.getAmount().doubleValue());
				}
			}
		}
		loginForm.setRevalationFeeMap(revalationFeeMap);
		String revaluationSubjectQuery="from ExamRevaluationAppDetails e where e.examRevApp.isActive=1 and e.examRevApp.exam.id=" +loginForm.getSupMCexamID()+
				" and e.examRevApp.classes.id=" +loginForm.getSupMCClassId()+
				" and e.examRevApp.student.id="+loginForm.getStudentId();
		List<ExamRevaluationAppDetails> revaluationSubjectList=txn.getDataForQuery(revaluationSubjectQuery);
		
		Map<Integer,String> revaluationSubjects=new HashMap<Integer, String>();
		if(revaluationSubjectList!=null && !revaluationSubjectList.isEmpty()){
			Iterator<ExamRevaluationAppDetails> itr=revaluationSubjectList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationAppDetails bo =itr .next();
				revaluationSubjects.put(bo.getSubject().getId(),bo.getStatus());
			}
		}
		loginForm.setRevaluationSubjects(revaluationSubjects);
		loginForm.setCheckRevaluation(isRevaluation);
		
		List<Object[]> pgMarksCardData=transaction.getStudentHallTicket(query);
		MarksCardTO to=DownloadHallTicketHelper.getInstance().getSupMarksCardForPg(pgMarksCardData,loginForm.getSupMCsemesterYearNo(),loginForm.getStudentId(),new HashMap<Integer, byte[]>(),request,loginForm.getSupMCexamID(),revaluationSubjects);
		if(to!=null){
			to.setExam(loginForm.getSupMCexamID());
			to.setClassesId(loginForm.getSupMCClassId());
			to.setStudentId(loginForm.getStudentId());
		}
		return to;
	}
	
	/**
	 * 
	 * @param studentId
	 * @param curClassId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getSupplementaryClassIds(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception{
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getSupplementaryClassIds(studentId, curClassId, isSup, publishedFor);
	}
	/**
	 * @param studentId
	 * @param curClassId
	 * @param isSup
	 * @param publishedFor
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getRegularClassIds(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception{
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getRegularClassIds(studentId, curClassId, isSup, publishedFor);
	}
	
	public List<Integer> getRegularClassIdsForMarksCard(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception{
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getRegularClassIdsForMarksCard(studentId, curClassId, isSup, publishedFor);
	}
	
	/**
	 * @param studentId
	 * @param curClassId
	 * @param isSup
	 * @param publishedFor
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getRegularClassIdsView(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception{
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getClassIdsView(studentId, curClassId, isSup, publishedFor);
	}

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public MarksCardTO getSupMarksCardForPGView(DisciplinaryDetailsForm objForm)throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getQueryForPGStudentSupMarksCardView(objForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentHallTicket(query);
		return DownloadHallTicketHelper.getInstance().getSupMarksCardForPgView(pgMarksCardData,objForm );
	}
	
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<MarksCardTO> getExamSemList(DisciplinaryDetailsForm objForm) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getExamSemList(objForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentSupMarksCard(query);
		List<MarksCardTO> SemList=DownloadHallTicketHelper.getInstance().getExamSemList(pgMarksCardData);
		return SemList;
	}
	
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<MarksCardTO> getSupExamSemList(DisciplinaryDetailsForm objForm) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getSupExamSemList(objForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentSupMarksCard(query);
		List<MarksCardTO> SupSemList=DownloadHallTicketHelper.getInstance().getSupExamSemList(pgMarksCardData);
		return SupSemList;
	}
	
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public MarksCardTO getMarksCardForPG(DisciplinaryDetailsForm objForm) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getQueryForPGStudentMarksCard(objForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentHallTicket(query);
		return DownloadHallTicketHelper.getInstance().getMarksCardForPgView(pgMarksCardData,Integer.parseInt(objForm.getStudentId()));
	}
	
	
	public List<MarksCardTO> getCjcExamSemList(DisciplinaryDetailsForm objForm) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getCjcExamSemList(objForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentSupMarksCard(query);
		List<MarksCardTO> SupSemList=DownloadHallTicketHelper.getInstance().getCjcExamSemList(pgMarksCardData);
		return SupSemList;
	}
	
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public MarksCardTO getCjcMarksCard(DisciplinaryDetailsForm objForm) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getQueryCjcStudentMarksCard(objForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentHallTicket(query);
		return DownloadHallTicketHelper.getInstance().getMarksCardForCjcView(pgMarksCardData);
	}
	
	
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
//	public MarksCardTO getMarksCardForUG(DisciplinaryDetailsForm objForm) throws Exception {
//		String query=DownloadHallTicketHelper.getInstance().getQueryForUGStudentMarksCard(objForm);
//		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
//		List<Object[]> ugMarksCardData=transaction.getStudentHallTicket(query);
//		return DownloadHallTicketHelper.getInstance().getMarksCardForUg(ugMarksCardData);
//	}

	/**
	 * @param classId
	 * @param objForm
	 * @param hallTicket
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassIdForSupHallTicket(int classId, DisciplinaryDetailsForm objForm, String hallTicket) throws Exception {
		log.debug("getExamIdByClassIdForSupHallTicket");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		
		return iDownloadHallTicketTransaction.getExamIdByClassIdForSupHallTicket(classId, objForm, hallTicket);
	}
	/*public int getClassIdsView(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception{
	IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
	return iDownloadHallTicketTransaction.getClassIdsView(studentId, curClassId, isSup, publishedFor);
	}*/

	/**
	 * @param classId
	 * @param objForm
	 * @param publishedFor
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassId(int classId, DisciplinaryDetailsForm objForm, String publishedFor) throws Exception {
		log.debug("getExamIdByClassId");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getExamIdByClassId(classId, objForm, publishedFor);
	}
	/**
	 * @param studentId
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public int getClassId(int studentId, DisciplinaryDetailsForm objForm) throws Exception {
		log.debug("getClassId");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getClassId(studentId, objForm);
	}
	/**
	 * @param studentId
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public int getTermNo(int studentId, DisciplinaryDetailsForm objForm) throws Exception {
		log.debug("getClassId");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getTermNo(studentId, objForm);
	}
	/**
	 * @param blockId
	 * @return
	 */
	public ClearanceCertificateTO getCleareanceCertificateForStudent( String blockId) throws Exception {
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		ExamBlockUnblockHallTicketBO bo=iDownloadHallTicketTransaction.getExamBlockUnblock(blockId);
		return DownloadHallTicketHelper.getInstance().convertBotoTo(bo);
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkValidDDdetails(LoginForm loginForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=null;
		if(loginForm.getDdDate()!=null && loginForm.getDdNo()!=null){
		String query="from ExamRevaluationApplication e where e.isActive=1 and e.ddDate='"+CommonUtil.ConvertStringToSQLDate(loginForm.getDdDate())+"' and e.ddNumber='"+loginForm.getDdNo()+"' ";
		list=transaction.getDataForQuery(query);
		}
		if(list!=null && !list.isEmpty())
			return false;
		else
			return true;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveRevaluationData(LoginForm loginForm) throws Exception {
		MarksCardTO to=loginForm.getMarksCardTo();
		ExamRevaluationApplication bo=new ExamRevaluationApplication();
		bo.setIsActive(true);
		bo.setCreatedBy(loginForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(loginForm.getUserId());
		bo.setLastModifiedDate(new Date());
		ExamDefinition exam=new ExamDefinition();
		exam.setId(to.getExam());
		bo.setExam(exam);
		Student student=new Student();
		student.setId(to.getStudentId());
		bo.setStudent(student);
		Classes classes=new Classes();
		classes.setId(to.getClassesId());
		bo.setClasses(classes);
		if(loginForm.getDdDate()!=null && loginForm.getDdNo()!=null){
			bo.setDdDate(CommonUtil.ConvertStringToSQLDate(loginForm.getDdDate()));
			bo.setDdNumber(loginForm.getDdNo());
		}
		bo.setAmount(Double.parseDouble(loginForm.getAmount()));
		if(loginForm.getBankName()!=null && !loginForm.getBankName().isEmpty()){
			bo.setBank(loginForm.getBankName());
		}
		if(loginForm.getBranchName()!=null && !loginForm.getBranchName().isEmpty()){
			bo.setBranchName(loginForm.getBranchName());
		}
		Set<ExamRevaluationAppDetails> examRevaluationAppDetails =new HashSet<ExamRevaluationAppDetails>();
		
		List<SubjectTO> mainList=to.getMainList();
		Iterator<SubjectTO> itr=mainList.iterator();
		while (itr.hasNext()) {
			SubjectTO subjectTO =itr.next();
			if(subjectTO.getRevType()!=null && !subjectTO.getRevType().isEmpty()){
			ExamRevaluationAppDetails subBo=new ExamRevaluationAppDetails();
			subBo.setCreatedBy(loginForm.getUserId());
			subBo.setCreatedDate(new Date());
			subBo.setModifiedBy(loginForm.getUserId());
			subBo.setLastModifiedDate(new Date());
			subBo.setIsActive(true);
			Subject subject=new Subject();
			subject.setId(subjectTO.getId());
			subBo.setSubject(subject);
			subBo.setStatus(subjectTO.getRevType()+" Application Submitted - Pending For Verfication");
			subBo.setType(subjectTO.getRevType());
			subBo.setIsUpdated(false);
			subBo.setThirdEvaluation(false);
			examRevaluationAppDetails.add(subBo);
			}
		}
		bo.setExamRevaluationAppDetails(examRevaluationAppDetails);
		return PropertyUtil.getInstance().save(bo);
	}
	/**
	 * @param loginForm
	 * @throws Exception
	 */
	public boolean sendMailToStudent(LoginForm loginForm) throws Exception {
		log.info("entered sendMailToStudent in InterviewHelper");
		boolean sent = false;
		if(loginForm.getContactMail()!=null && !loginForm.getContactMail().isEmpty()){
		List<GroupTemplate> templateList= TemplateHandler.getInstance().getDuplicateCheckList(0,CMSConstants.REVALUATION_TEMPLATE);
		if (templateList != null && !templateList.isEmpty()) {

			String desc = templateList.get(0).getTemplateDescription();
			MarksCardTO to=loginForm.getMarksCardTo();
			
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(
								CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {
				log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
			String message = desc;
			message = message .replace(CMSConstants.TEMPLATE_DATE,to.getDate()!=null?to.getDate():"");
			message = message .replace(CMSConstants.TEMPLATE_COURSE,to.getCourseName()!=null?to.getCourseName():"");
			message = message .replace(CMSConstants.TEMPLATE_REGISTER_NO,to.getRegNo()!=null?to.getRegNo():"");
			message = message .replace(CMSConstants.TEMPLATE_STUDENT_NAME,to.getStudentName()!=null?to.getStudentName():"");
			message = message .replace(CMSConstants.TEMPLATE_SEM_TYPE,to.getSemType()!=null?to.getSemType():"");
			message = message .replace(CMSConstants.TEMPLATE_TERM_NO,to.getSemNo()!=null?to.getSemNo():"");
			message = message .replace(CMSConstants.TEMPLATE_MONTH_YEAR,to.getMonthYear()!=null?to.getMonthYear():"");
			message = message .replace(CMSConstants.TEMPLATE_DD_NO,loginForm.getDdNo()!=null?loginForm.getDdNo():"");
			message = message .replace(CMSConstants.TEMPLATE_DD_DATE,loginForm.getDdDate()!=null?loginForm.getDdDate():"");
			message = message .replace(CMSConstants.TEMPLATE_BANK,loginForm.getBankName()!=null?loginForm.getBankName():"");
			message = message .replace(CMSConstants.TEMPLATE_BRANCH,loginForm.getBranchName()!=null?loginForm.getBranchName():"");
			message = message .replace(CMSConstants.TEMPLATE_AMOUNT,loginForm.getAmount()!=null?loginForm.getAmount():"");
			
			sent=InterviewHelper.sendMail(loginForm.getContactMail(), "Revaluation/Re-totaling Submitted Successfully", message, prop);
		}
		}
		return sent;
	}
	/**
	 * Common Send mail
	 * 
	 * @param admForm
	 * @return
	 */
	public static boolean sendMail(String mailID, String sub, String message,
			Properties prop) {
		boolean sent = false;

//		String adminmail = prop .getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
		String adminmail = CMSConstants.MAIL_USERID;
		String toAddress = mailID;
		// MAIL TO CONSTRUCTION
		String subject = sub;
		String msg = message;

		MailTO mailto = new MailTO();
		mailto.setFromAddress(adminmail);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg);
		mailto.setFromName(prop
				.getProperty("knowledgepro.admission.studentmail.fromName"));
		// uses JMS
		// sent=CommonUtil.postMail(mailto);
		sent = CommonUtil.sendMail(mailto);
		return sent;
	}
	
	/**
	 * 
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassIdForSupMarksCard(int classId, LoginForm loginForm, String hallTicket) throws Exception {
		log.debug("getExamIdByClassIdForSupHallTicket");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		
		return iDownloadHallTicketTransaction.getExamIdByClassIdForSupMarksCard(classId, loginForm, hallTicket);
	}
	
	public List<MarksCardTO> getExamSemList(LoginForm loginForm) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getExamSemListStdLogin(loginForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentSupMarksCard(query);
		List<MarksCardTO> SemList=DownloadHallTicketHelper.getInstance().getExamSemListStdLogin(pgMarksCardData, loginForm);
		return SemList;
	}
	public List<MarksCardTO> getExamSemListForGrievance(LoginForm loginForm) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getExamSemListStdLoginForGrievance(loginForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentSupMarksCard(query);
		List<MarksCardTO> SemList=DownloadHallTicketHelper.getInstance().getExamSemListStdLogin(pgMarksCardData, loginForm);
		return SemList;
	}
	
	public List<MarksCardTO> getExamSemListForInternalGrievance(LoginForm loginForm,String classid) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getExamSemListStdLoginForInternalGrievance(loginForm,classid);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Integer> eidlist = transaction.getStudentSupMarksCard1(query);
		//List<Object[]> pgMarksCardData=transaction.getStudentSupMarksCard(query);
		List<MarksCardTO> SemList=DownloadHallTicketHelper.getInstance().getExamSemListStdLoginGrievance(eidlist, loginForm);
		return SemList;
	}
	
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<MarksCardTO> getSupExamSemList(LoginForm loginForm) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getSupExamSemListStdLogin(loginForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentSupMarksCard(query);
		List<MarksCardTO> SupSemList=DownloadHallTicketHelper.getInstance().getSupExamSemListStdLogin(pgMarksCardData);
		return SupSemList;
	}
	public List<MarksCardTO> getSupExamSemListForGrievance(LoginForm loginForm) throws Exception {
		String query=DownloadHallTicketHelper.getInstance().getSupExamSemListStdLoginForGrievance(loginForm);
		IDownloadHallTicketTransaction transaction= DownloadHallTicketTransactionImpl.getInstance();
		List<Object[]> pgMarksCardData=transaction.getStudentSupMarksCard(query);
		List<MarksCardTO> SupSemList=DownloadHallTicketHelper.getInstance().getSupExamSemListStdLogin(pgMarksCardData);
		return SupSemList;
	}
	
	public int getPreClassId(int studentId, LoginForm loginForm) throws Exception {
		log.debug("getClassId");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		
		return iDownloadHallTicketTransaction.getPreClassId(studentId, loginForm);
	}
	
	public int getClassIdForRevaluation(int studentId, LoginForm loginForm) throws Exception {
		log.debug("getClassId");
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		
		return iDownloadHallTicketTransaction.getClassIdForRevaluation(studentId, loginForm);
	}
	public int getClassIdsForGrivance(int studentId, int curClassId, boolean isSup, String publishedFor)throws Exception{
		IDownloadHallTicketTransaction iDownloadHallTicketTransaction = DownloadHallTicketTransactionImpl.getInstance();
		return iDownloadHallTicketTransaction.getClassIds(studentId, curClassId, isSup, publishedFor);
	}
}
