package com.kp.cms.helpers.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.forms.admin.EvaStudentFeedbackOpenConnectionForm;
import com.kp.cms.to.admin.EvaStudentFeedbackOpenConnectionTo;
import com.kp.cms.transactions.admin.IEvaStudentFeedbackOpenConnectionTransaction;
import com.kp.cms.transactionsimpl.admin.EvaStudentFeedbackOpenConnectionTransImpl;
import com.kp.cms.utilities.CommonUtil;

public class EvaStudentFeedbackOpenConnectionHelper {
	public static volatile EvaStudentFeedbackOpenConnectionHelper connectionHelper = null;
	public static EvaStudentFeedbackOpenConnectionHelper getInstance(){
		if(connectionHelper == null){
			connectionHelper = new EvaStudentFeedbackOpenConnectionHelper();
			return connectionHelper;
		}
		return connectionHelper;
	}
	/**
	 * @param connectionForm
	 * @param specializationIds 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<EvaStudentFeedbackOpenConnection> copyFromFormToBO( EvaStudentFeedbackOpenConnectionForm connectionForm, Map<Integer, Integer> specializationIds) throws Exception{
		List<EvaStudentFeedbackOpenConnection> connectionList = new ArrayList<EvaStudentFeedbackOpenConnection>();
			if(connectionForm.getClassesId()!=null && connectionForm.getClassesId().length !=0 ){
				String[] classesId = connectionForm.getClassesId();
				int i =0;
				for(i=0;i<classesId.length;i++){
					EvaStudentFeedbackOpenConnection connection = new EvaStudentFeedbackOpenConnection();
					String classId = classesId[i];
					Classes classes = new Classes();
					classes.setId(Integer.parseInt(classId));
					connection.setClassesId(classes);
					if(connectionForm.getStartDate()!=null && !connectionForm.getStartDate().isEmpty()){
						connection.setStartDate(CommonUtil.ConvertStringToDate(connectionForm.getStartDate()));
					}
					if(connectionForm.getEndDate()!=null && !connectionForm.getEndDate().isEmpty()){
						connection.setEndDate(CommonUtil.ConvertStringToDate(connectionForm.getEndDate()));
					}
					if(connectionForm.getSessionId()!=null && !connectionForm.getSessionId().isEmpty()){
						EvaluationStudentFeedbackSession session = new EvaluationStudentFeedbackSession();
						session.setId(Integer.parseInt(connectionForm.getSessionId()));
						connection.setFeedbackSession(session);
					}
					/* code added by sudhir */
					if(connectionForm.getSpecializationId()!=null && !connectionForm.getSpecializationId().isEmpty()){
						ExamSpecializationBO bo = new ExamSpecializationBO();
						bo.setId(specializationIds.get(Integer.parseInt(classId)));
						connection.setExamSpecializationBO(bo);
					}
					/* --------------------- */
					connection.setCreatedBy(connectionForm.getUserId());
					connection.setCreatedDate(new Date());
					connection.setIsActive(true);
					
					if(connectionForm.getIsExitEval()!=null){
						if(connectionForm.getIsExitEval().equalsIgnoreCase("true")){
							connection.setIsExitEval(true);
						}
						else if(connectionForm.getIsExitEval().equalsIgnoreCase("false"))
						{
							connection.setIsExitEval(false);
						}
					}
					connectionList.add(connection);
				}
			}
			
		return connectionList;
	}
	/**
	 * @param connections
	 * @return
	 * @throws Exception
	 */
	public List<EvaStudentFeedbackOpenConnectionTo> copyBOToToList( List<EvaStudentFeedbackOpenConnection> connections) throws Exception{
		List<EvaStudentFeedbackOpenConnectionTo> list = new ArrayList<EvaStudentFeedbackOpenConnectionTo>();
		if(connections!=null && !connections.toString().isEmpty()){
			Iterator<EvaStudentFeedbackOpenConnection> iterator = connections.iterator();
			while (iterator.hasNext()) {
				EvaStudentFeedbackOpenConnection bo = (EvaStudentFeedbackOpenConnection) iterator .next();
				EvaStudentFeedbackOpenConnectionTo to = new EvaStudentFeedbackOpenConnectionTo();
				if(bo.getId()!=0){
					to.setId(bo.getId());
				}
				if(bo.getClassesId()!=null && bo.getClassesId().getId()!=0){
					to.setClassesid(bo.getClassesId().getId());
				}
				if(bo.getClassesId()!=null && bo.getClassesId().getName()!=null && !bo.getClassesId().getName().isEmpty()){
					to.setClassName( bo.getClassesId().getName());
				}
				if(bo.getStartDate()!=null && !bo.getStartDate().toString().isEmpty()){
					to.setStartDate(formatDate(bo.getStartDate()));
				}
				if(bo.getEndDate()!=null && !bo.getEndDate().toString().isEmpty()){
					to.setEndDate(formatDate(bo.getEndDate()));
				}
				if(bo.getFeedbackSession()!=null && !bo.getFeedbackSession().toString().isEmpty() ){
					to.setSessionName(bo.getFeedbackSession().getName());
				}
				if(bo.getClassesId()!=null && bo.getClassesId().getClassSchemewises()!=null){
					Set<ClassSchemewise> set = bo.getClassesId().getClassSchemewises();
					Iterator<ClassSchemewise> iterator1 = set.iterator();
					while (iterator1.hasNext()) {
						ClassSchemewise classSchemewise = (ClassSchemewise) iterator1 .next();
						if(classSchemewise!=null && classSchemewise.getCurriculumSchemeDuration()!= null){
							if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=null){
								to.setYear(classSchemewise.getCurriculumSchemeDuration().getAcademicYear().toString());
							}
						}
					}
				}
				/* code added by sudhir */
				if(bo.getExamSpecializationBO()!=null && !bo.getExamSpecializationBO().toString().isEmpty()){
					to.setSpecializationName(bo.getExamSpecializationBO().getName());
				}
				to.setIsExitEval(bo.getIsExitEval());
				
				list.add(to);
			}
		}
		return list;
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
	 * @param sessionsList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> copySessionBoTOTo( List<EvaluationStudentFeedbackSession> sessionsList) throws Exception{
		Map<Integer,String> sessionToMap = new HashMap<Integer, String>();
		if(sessionsList!=null && !sessionsList.isEmpty()){
			Iterator<EvaluationStudentFeedbackSession> iterator = sessionsList.iterator();
			while (iterator.hasNext()) {
				EvaluationStudentFeedbackSession bo = (EvaluationStudentFeedbackSession) iterator .next();
				if(bo.getId()!=0){
					sessionToMap.put(bo.getId(), bo.getName());
				}
			}
		}
		return sessionToMap;
	}
}
