package com.kp.cms.helpers.teacherfeedback;

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
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackOpenConnection;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackSession;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedbackOpenConnectionForm;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedbackOpenConnectionTo;
import com.kp.cms.utilities.CommonUtil;

public class EvaTeacherFeedbackOpenConnectionHelper {
	public static volatile EvaTeacherFeedbackOpenConnectionHelper connectionHelper = null;
	public static EvaTeacherFeedbackOpenConnectionHelper getInstance(){
		if(connectionHelper == null){
			connectionHelper = new EvaTeacherFeedbackOpenConnectionHelper();
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
	public List<EvaTeacherFeedbackOpenConnection> copyFromFormToBO( EvaTeacherFeedbackOpenConnectionForm connectionForm, Map<Integer, Integer> specializationIds) throws Exception{
		List<EvaTeacherFeedbackOpenConnection> connectionList = new ArrayList<EvaTeacherFeedbackOpenConnection>();
			if(connectionForm.getClassesId()!=null && connectionForm.getClassesId().length !=0 ){
				String[] classesId = connectionForm.getClassesId();
				int i =0;
				for(i=0;i<classesId.length;i++){
					EvaTeacherFeedbackOpenConnection connection = new EvaTeacherFeedbackOpenConnection();
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
						EvaluationTeacherFeedbackSession session = new EvaluationTeacherFeedbackSession();
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
	public List<EvaTeacherFeedbackOpenConnectionTo> copyBOToToList( List<EvaTeacherFeedbackOpenConnection> connections) throws Exception{
		List<EvaTeacherFeedbackOpenConnectionTo> list = new ArrayList<EvaTeacherFeedbackOpenConnectionTo>();
		if(connections!=null && !connections.toString().isEmpty()){
			Iterator<EvaTeacherFeedbackOpenConnection> iterator = connections.iterator();
			while (iterator.hasNext()) {
				EvaTeacherFeedbackOpenConnection bo = (EvaTeacherFeedbackOpenConnection) iterator .next();
				EvaTeacherFeedbackOpenConnectionTo to = new EvaTeacherFeedbackOpenConnectionTo();
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
	public Map<Integer,String> copySessionBoTOTo( List<EvaluationTeacherFeedbackSession> sessionsList) throws Exception{
		Map<Integer,String> sessionToMap = new HashMap<Integer, String>();
		if(sessionsList!=null && !sessionsList.isEmpty()){
			Iterator<EvaluationTeacherFeedbackSession> iterator = sessionsList.iterator();
			while (iterator.hasNext()) {
				EvaluationTeacherFeedbackSession bo = (EvaluationTeacherFeedbackSession) iterator .next();
				if(bo.getId()!=0){
					sessionToMap.put(bo.getId(), bo.getName());
				}
			}
		}
		return sessionToMap;
	}
}
