package com.kp.cms.handlers.teacherfeedback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackOpenConnection;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackSession;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedbackOpenConnectionForm;
import com.kp.cms.helpers.teacherfeedback.EvaTeacherFeedbackOpenConnectionHelper;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedbackOpenConnectionTo;
import com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction;
import com.kp.cms.transactionsimpl.teacherfeedback.EvaTeacherFeedbackOpenConnectionTransImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

public class EvaTeacherFeedbackOpenConnectionHandler {
	public static volatile EvaTeacherFeedbackOpenConnectionHandler connectionHandler = null;
	public static EvaTeacherFeedbackOpenConnectionHandler getInstance(){
		if(connectionHandler == null){
			connectionHandler = new EvaTeacherFeedbackOpenConnectionHandler();
			return connectionHandler;
		}
		return connectionHandler;
	}
	IEvaTeacherFeedbackOpenConnectionTransaction itransaction = EvaTeacherFeedbackOpenConnectionTransImpl.getInstance();
	/**
	 * @param mode 
	 * @return
	 * @throws Exception
	 */
	public boolean submitOpenConnectionDetails( EvaTeacherFeedbackOpenConnectionForm connectionForm) throws Exception{
		boolean isAdded = false;
		Map<Integer,Integer> specializationIds = null;
		if(connectionForm.getSpecializationId()!=null && !connectionForm.getSpecializationId().isEmpty()){
			String specializationName = itransaction.getSpecializationName(connectionForm.getSpecializationId());
			 specializationIds = itransaction.getSpecializationIds(connectionForm.getClassesId(),specializationName);
		}
			List<EvaTeacherFeedbackOpenConnection> boList = EvaTeacherFeedbackOpenConnectionHelper.getInstance().copyFromFormToBO(connectionForm,specializationIds);
			isAdded = itransaction.submitOpenConnectionDetails(boList);
		return isAdded;
	}
	/**
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck( EvaTeacherFeedbackOpenConnectionForm connectionForm) throws Exception{
		boolean isCheckDuplicate = itransaction.checkDuplicate(connectionForm);
		return isCheckDuplicate;
	}
	/**
	 * @param connectionForm
	 * @throws Exception
	 */
	public  void getEditOpenConnectionDetails( EvaTeacherFeedbackOpenConnectionForm connectionForm) throws Exception{
		EvaTeacherFeedbackOpenConnection bo = itransaction.getOpenConnectionDetails(connectionForm.getId());
		if(bo!=null && !bo.toString().isEmpty()){
			if(bo.getId()!=0 ){
				connectionForm.setId(bo.getId());
			}
			if(bo.getClassesId()!=null && !bo.getClassesId().toString().isEmpty()){
				String classesId[] =  new String[1];
				 classesId[0] = Integer.toString(bo.getClassesId().getId());
				connectionForm.setClassesId(classesId);
			}
			if(bo.getStartDate()!=null && !bo.getStartDate().toString().isEmpty()){
				connectionForm.setStartDate(formatDate(bo.getStartDate()));
			}
			if(bo.getEndDate()!=null && !bo.getEndDate().toString().isEmpty()){
				connectionForm.setEndDate(formatDate(bo.getEndDate()));
			}
			if(bo.getFeedbackSession()!=null && bo.getFeedbackSession().getId()!=0){
				connectionForm.setSessionId(String.valueOf(bo.getFeedbackSession().getId()));
			}
			if(bo.getClassesId()!=null && bo.getClassesId().getClassSchemewises()!=null){
				Set<ClassSchemewise> set = bo.getClassesId().getClassSchemewises();
				Iterator<ClassSchemewise> iterator = set.iterator();
				while (iterator.hasNext()) {
					ClassSchemewise classSchemewise = (ClassSchemewise) iterator .next();
					if(classSchemewise!=null && classSchemewise.getCurriculumSchemeDuration()!= null){
						if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=null){
							connectionForm.setAcademicYear(classSchemewise.getCurriculumSchemeDuration().getAcademicYear().toString());
						}
					}
				}
			}
			/* newly added by sudhir */
			if(bo.getExamSpecializationBO()!=null && !bo.getExamSpecializationBO().toString().isEmpty()){
				if(bo.getExamSpecializationBO().getId()!=null && !bo.getExamSpecializationBO().getId().toString().isEmpty()){
					connectionForm.setSpecializationId(String.valueOf(bo.getExamSpecializationBO().getId()));
				}
			}
		}
	}
	/**
	 * @param year 
	 * @param sessionId 
	 * @return
	 * @throws Exception
	 */
	public List<EvaTeacherFeedbackOpenConnectionTo> getDetails(int year, String sessionId)throws Exception {
		List<EvaTeacherFeedbackOpenConnection> connections = itransaction.getConnectionList(year,sessionId);
		List<EvaTeacherFeedbackOpenConnectionTo> connectionToList = EvaTeacherFeedbackOpenConnectionHelper.getInstance().copyBOToToList(connections);
		return connectionToList;
	}
	/**
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteOpenConnection( EvaTeacherFeedbackOpenConnectionForm connectionForm) throws Exception{
		boolean isDeleted = itransaction.deleteOpenConnection(connectionForm);
		return isDeleted;
	}
	/**
	 * @param connectionForm
	 * @return
	 */
	public boolean updateOpenConnectionDetails( EvaTeacherFeedbackOpenConnectionForm connectionForm)throws Exception {
		boolean isUpdated = itransaction.updateOpenConnection(connectionForm);
		return isUpdated;
	}
	/**
	 * @param date
	 * @return
	 */
	public String formatDate(Date date){
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String newDate=formatter.format(date);
		return newDate;
	}
	/**
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getSessionList(EvaTeacherFeedbackOpenConnectionForm connectionForm) throws Exception {
		Map<Integer,String> sessionMap ;
		if(connectionForm.getAcademicYear()==null){
			/*Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);*/
			Integer year = CurrentAcademicYear.getInstance().getAcademicyear();
			/*if (year != 0) {
				currentYear = year;
			}*/
			List<EvaluationTeacherFeedbackSession> sessionsList = itransaction.getSessionDetails(year);
			 sessionMap = EvaTeacherFeedbackOpenConnectionHelper.getInstance().copySessionBoTOTo(sessionsList);
		}else{
			List<EvaluationTeacherFeedbackSession> sessionsList = itransaction.getSessionDetails(Integer.parseInt(connectionForm.getAcademicYear()));
			sessionMap = EvaTeacherFeedbackOpenConnectionHelper.getInstance().copySessionBoTOTo(sessionsList);
		}
		
		return sessionMap;
	}
}
