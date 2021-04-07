package com.kp.cms.handlers.exam;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.StudentAttendanceForExamForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.helpers.exam.NewExamMarksEntryHelper;
import com.kp.cms.helpers.exam.StudentAttendanceForExamHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.IStudentAttendanceForExamTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.StudentAttendanceForExamTransactionImpl;
import com.kp.cms.utilities.JobScheduler;
import com.kp.cms.utilities.PropertyUtil;

public class StudentAttendanceForExamHandler {
	/**
	 * Singleton object of StudentAttendanceForExamHandler
	 */
	private static volatile StudentAttendanceForExamHandler studentAttendanceForExamHandler = null;
	private static final Log log = LogFactory.getLog(StudentAttendanceForExamHandler.class);
	private StudentAttendanceForExamHandler() {
		
	}
	/**
	 * return singleton object of StudentAttendanceForExamHandler.
	 * @return
	 */
	public static StudentAttendanceForExamHandler getInstance() {
		if (studentAttendanceForExamHandler == null) {
			studentAttendanceForExamHandler = new StudentAttendanceForExamHandler();
		}
		return studentAttendanceForExamHandler;
	}
	/**
	 * @param registerNoList
	 * @return
	 * @throws Exception
	 */
	public List<String> checkIsValidRegisterNo(List<String> registerNoList,StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception{
		List<Object[]> studentList=new ArrayList<Object[]>();
		List<String> notValidStudent=new ArrayList<String>();
		IStudentAttendanceForExamTransaction transaction=StudentAttendanceForExamTransactionImpl.getInstance();
		if(!studentAttendanceForExamForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(studentAttendanceForExamForm.getIsPreviousExam().equalsIgnoreCase("false")){
				String currentStudentQuery=" select s.registerNo,s.id "+StudentAttendanceForExamHelper.getInstance().getQueryForCurrentClassStudents(studentAttendanceForExamForm);
				List currentStudentList=transaction.getDataForQuery1(currentStudentQuery,registerNoList);// calling the method for getting current class students
				if(currentStudentList!=null && !currentStudentList.isEmpty()){
					studentList.addAll(currentStudentList);
				}
			}else{
				String previousStudentQuery=" select s.registerNo,s.id "+StudentAttendanceForExamHelper.getInstance().getQueryForPreviousClassStudents(studentAttendanceForExamForm);
				List previousStudentList=transaction.getDataForQuery1(previousStudentQuery,registerNoList);// calling the method for getting Previous class students
				if(previousStudentList!=null && !previousStudentList.isEmpty()){
					studentList.addAll(previousStudentList);
				}
			}
		}else{
			if(studentAttendanceForExamForm.getIsPreviousExam().equalsIgnoreCase("false")){
				String currentStudentQuery=" select s.registerNo,s.id "+StudentAttendanceForExamHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(studentAttendanceForExamForm);
				List currentStudentList=transaction.getDataForQuery1(currentStudentQuery,registerNoList);// calling the method for getting current class students
				if(currentStudentList!=null && !currentStudentList.isEmpty()){
					studentList.addAll(currentStudentList);
				}
			}else{
				String previousStudentQuery=" select s.registerNo,s.id "+StudentAttendanceForExamHelper.getInstance().getQueryForSupplementaryPreviousClassStudents(studentAttendanceForExamForm);
				List previousStudentList=transaction.getDataForQuery1(previousStudentQuery,registerNoList);// calling the method for getting Previous class students
				if(previousStudentList!=null && !previousStudentList.isEmpty()){
					studentList.addAll(previousStudentList);
				}
			}
		}
		List<Integer> idList=new ArrayList<Integer>();
		// creating map to get id for each student
		Map<String,Integer> studentMap=new HashMap<String, Integer>();
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<Object[]> itr=studentList.iterator();
			while (itr.hasNext()) {
				Object[] obj= (Object[]) itr.next();
				studentMap.put(obj[0].toString(),Integer.parseInt(obj[1].toString()));
			}
		}
		INewExamMarksEntryTransaction transaction1=NewExamMarksEntryTransactionImpl.getInstance();
		String oldRegQuery=NewExamMarksEntryHelper.getInstance().getQueryForOldRegisterNos((studentAttendanceForExamForm.getSchemeNo()==null || studentAttendanceForExamForm.getSchemeNo().isEmpty())?"0":studentAttendanceForExamForm.getSchemeNo());
		List oldRegList=transaction1.getDataForQuery(oldRegQuery);
		Map<String, Integer> oldRegMap=StudentAttendanceForExamHelper.getInstance().getOldRegMap(oldRegList);
		studentAttendanceForExamForm.setOldRegNOMap(oldRegMap);
		Iterator<String> itr=registerNoList.iterator();
		while (itr.hasNext()) {
			String regNo = (String) itr.next();
			if(!studentMap.containsKey(regNo)){
				if(studentAttendanceForExamForm.getExamType().equalsIgnoreCase("Supplementary")){
					if(oldRegMap.containsKey(regNo)){
						idList.add(oldRegMap.get(regNo));
					}else{
						notValidStudent.add(regNo);
					}
				}else{
					notValidStudent.add(regNo);
				}
			}else{
				idList.add(studentMap.get(regNo));
			}
		}
		studentAttendanceForExamForm.setIdList(idList);
		return notValidStudent;
	}
	/**
	 * @param registerNoList
	 * @return
	 * @throws Exception
	 */
	public List<String> checkMarksEnteredForRegisterNo(List<String> registerNoList,StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception{
		String marksQuery=StudentAttendanceForExamHelper.getInstance().getQueryForExistStudentMarks(studentAttendanceForExamForm);
		IStudentAttendanceForExamTransaction transaction=StudentAttendanceForExamTransactionImpl.getInstance();
		List<String> studentList=transaction.getDataForMarksQuery(marksQuery,studentAttendanceForExamForm.getIdList());
		return studentList;
	}
	/**
	 * @param studentAttendanceForExamForm
	 * @param registerNoList
	 * @return
	 * @throws Exception
	 */
	public boolean saveAttendance(StudentAttendanceForExamForm studentAttendanceForExamForm,List<String> registerNoList) throws Exception {
		String studentQuery=StudentAttendanceForExamHelper.getInstance().getStudentIdQuery(studentAttendanceForExamForm);
		IStudentAttendanceForExamTransaction transaction=StudentAttendanceForExamTransactionImpl.getInstance();
		List studentIdList=transaction.getDataForQuery(studentQuery,studentAttendanceForExamForm.getIdList());// calling the method for getting current class students
		Map<String,Integer> idMap=StudentAttendanceForExamHelper.getInstance().convertBotoMap(studentIdList); 
		List classesList=new ArrayList();
		if(!studentAttendanceForExamForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(studentAttendanceForExamForm.getIsPreviousExam().equalsIgnoreCase("false")){
				String currentStudentQuery=" select s.id,s.classSchemewise.classes.id "+StudentAttendanceForExamHelper.getInstance().getQueryForCurrentClassStudents(studentAttendanceForExamForm);
				List currentStudentList=transaction.getDataForQuery(currentStudentQuery,studentAttendanceForExamForm.getIdList());// calling the method for getting current class students
				if(currentStudentList!=null && !currentStudentList.isEmpty()){
					classesList.addAll(currentStudentList);
				}
			}else{
				String previousStudentQuery=" select s.id,classHis.classes.id "+StudentAttendanceForExamHelper.getInstance().getQueryForPreviousClassStudents(studentAttendanceForExamForm);
				List previousStudentList=transaction.getDataForQuery(previousStudentQuery,studentAttendanceForExamForm.getIdList());// calling the method for getting Previous class students
				if(previousStudentList!=null && !previousStudentList.isEmpty()){
					classesList.addAll(previousStudentList);
				}
			}
		}else{
			if(studentAttendanceForExamForm.getIsPreviousExam().equalsIgnoreCase("false")){
				String currentStudentQuery=" select s.id,c.id "+StudentAttendanceForExamHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(studentAttendanceForExamForm);
				List currentStudentList=transaction.getDataForQuery(currentStudentQuery,studentAttendanceForExamForm.getIdList());// calling the method for getting current class students
				if(currentStudentList!=null && !currentStudentList.isEmpty()){
					classesList.addAll(currentStudentList);
				}
			}else{
				String previousStudentQuery=" select s.id,classHis.classes.id "+StudentAttendanceForExamHelper.getInstance().getQueryForSupplementaryPreviousClassStudents(studentAttendanceForExamForm);
				List previousStudentList=transaction.getDataForQuery(previousStudentQuery,studentAttendanceForExamForm.getIdList());// calling the method for getting Previous class students
				if(previousStudentList!=null && !previousStudentList.isEmpty()){
					classesList.addAll(previousStudentList);
				}
			}
		}
		Map<Integer,Integer> classIdMap=StudentAttendanceForExamHelper.getInstance().convertListBotoMap(classesList);
		
		List<StudentMarksTO> stuList=StudentAttendanceForExamHelper.getInstance().convertFormToTO(registerNoList,idMap,studentAttendanceForExamForm,classIdMap);
		if(stuList != null && !stuList.isEmpty()){
			return transaction.saveMarks(stuList,studentAttendanceForExamForm);
		}else{
			return false;
		}
	}
	
	public boolean sendSMSToParent(StudentAttendanceForExamForm studentAttendanceForExamForm, SubjectTO subjectTO, StudentTO studentTO) 
	throws Exception{
		
		Properties prop = new Properties();
		InputStream in = JobScheduler.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(in);
		String sendSms= prop.getProperty("knowledgepro.sms.send");
		String senderNumber=CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER;
		String senderName=CMSConstants.KNOWLEDGEPRO_SENDER_NAME;
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		
		List<SMSTemplate> list = null;
		if(studentAttendanceForExamForm.getExamType().equalsIgnoreCase("Regular")){
			list= temphandle.getDuplicateCheckList(0,CMSConstants.STUDENT_ATTENDANCE_REGULAR_ABSENT_SMS_FOR_PARENT);
		}else{
			list= temphandle.getDuplicateCheckList(0,CMSConstants.STUDENT_ATTENDANCE_INTERNAL_ABSENT_SMS_FOR_PARENT);
		}
		
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME,studentTO.getStudentName());
		desc=desc.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO,studentTO.getRegisterNo());
		desc=desc.replace(CMSConstants.TEMPLATE_SMS_SUBJECT_CODE,subjectTO.getCode());
		desc=desc.replace(CMSConstants.TEMPLATE_SMS_SUBJECT_NAME,subjectTO.getName());
		desc=desc.replace(CMSConstants.TEMPLATE_EXAM_DATE,subjectTO.getStartDate());
		
		if(studentTO.getParentNo()!=null && !studentTO.getParentNo().isEmpty()){
		String parentMob = "91" + studentTO.getParentNo();
		if(StringUtils.isNumeric(parentMob) && (parentMob.length()==12 && desc.length()<=160)){
			if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
				MobileMessaging mob=new MobileMessaging();
				mob.setDestinationNumber(parentMob);
				mob.setMessageBody(desc);
				mob.setMessagePriority(3);
				mob.setSenderName(senderName);
				mob.setSenderNumber(senderNumber);
				mob.setMessageEnqueueDate(new Date());
				mob.setIsMessageSent(false);
				PropertyUtil.getInstance().save(mob);
			}
		}
		}
		return false;
	}
	
	
	public String getQueryForStudentExamDetails(String regNo,int examId, int subId) throws Exception {
		String query="SELECT student.register_no, personal_data.first_name,"+
		" personal_data.parent_mob_2, personal_data.parent_mob_3,"+
		" subject.code, subject.name as subName, EXAM_time_table.date_starttime"+
		" from student student " +
		" INNER JOIN adm_appln adm_appln " +
		" ON (student.adm_appln_id = adm_appln.id) " + 
		" INNER JOIN personal_data personal_data " +
		" ON (adm_appln.personal_data_id = personal_data.id) " +
		" INNER JOIN class_schemewise class_schemewise " +
		" ON (student.class_schemewise_id = class_schemewise.id) " +
		" INNER JOIN curriculum_scheme_duration curriculum_scheme_duration " +
		" ON (curriculum_scheme_duration.id = class_schemewise.curriculum_scheme_duration_id) " +
		" INNER JOIN applicant_subject_group applicant_subject_group " +
		" ON (applicant_subject_group.adm_appln_id = adm_appln.id) " +
		" INNER JOIN subject_group_subjects subject_group_subjects " +
		" ON (subject_group_subjects.subject_group_id = applicant_subject_group.subject_group_id) " +
		" INNER JOIN subject subject " +
		" ON (subject_group_subjects.subject_id = subject.id) " +
		" LEFT JOIN EXAM_exam_course_scheme_details EXAM_exam_course_scheme_details" +
		" ON (EXAM_exam_course_scheme_details.course_id = adm_appln.selected_course_id AND curriculum_scheme_duration.semester_year_no = EXAM_exam_course_scheme_details.scheme_no)" +
		" LEFT JOIN EXAM_time_table EXAM_time_table" +
		" ON (EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id)" +
		" AND (EXAM_time_table.subject_id = subject.id)" +
		" WHERE student.register_no = '"+regNo+"'"+
		" AND student.id not in (select student_id from EXAM_student_detention_rejoin_details where ((detain=1) or (discontinued=1)) and ((rejoin=0) or (rejoin is null)))" +
		" AND EXAM_exam_course_scheme_details.exam_id = "+examId+
		" AND (student.is_hide=0 or student.is_hide is null)"+
		" AND subject.id = "+subId;
		
		return query;
	}
	
}
