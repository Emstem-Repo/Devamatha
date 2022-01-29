package com.kp.cms.helpers.exam;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.math.BigDecimal;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.GradePointRangeBO;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.ClearanceCertificateTO;
import com.kp.cms.to.exam.ExamSubCoursewiseGradeDefnTO;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamSubDefinitionCourseWiseImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.UpdateExamStudentSGPAImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.SubjectGroupDetailsComparator;


public class DownloadHallTicketHelper {
	private static String NON_CORE_ELECTIVE="Non Core Elective";
	private static Map<String, String> monthMap = null;
	private static SubjectGroupDetailsComparator comparator=new SubjectGroupDetailsComparator();
	static {
		comparator.setCompare(1);
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

	public static Map<String, String> semMap = null;

	static {
		semMap = new HashMap<String, String>();
		semMap.put("1", "I");
		semMap.put("2", "II");
		semMap.put("3", "III");
		semMap.put("4", "IV");
		semMap.put("5", "V");
		semMap.put("6", "VI");
		semMap.put("7", "VII");
		semMap.put("8", "VIII");
		semMap.put("9", "IX");
		semMap.put("10", "X");

	}
	public static Map<String, String> semesterMap = null;

	static {
		semesterMap = new HashMap<String, String>();
		semesterMap.put("1", "FIRST");
		semesterMap.put("2", "SECOND");
		semesterMap.put("3", "THIRD");
		semesterMap.put("4", "FOURTH");
		semesterMap.put("5", "FIFTH");
		semesterMap.put("6", "SIXTH");


	}
	/**
	 * Singleton object of ScoreSheetHandler
	 */
	private static volatile DownloadHallTicketHelper downloadHallticketHelper = null;
	private static final Log log = LogFactory.getLog(DownloadHallTicketHelper.class);
	private DownloadHallTicketHelper() {

	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static DownloadHallTicketHelper getInstance() {
		if (downloadHallticketHelper == null) {
			downloadHallticketHelper = new DownloadHallTicketHelper();
		}
		return downloadHallticketHelper;
	}
	public String getQueryForStudentHallTicket(int studentId,int examId) throws Exception {
		String query="SELECT classes.name, student.register_no, personal_data.first_name,"+
		" personal_data.middle_name, personal_data.last_name,"+
		" subject.code, subject.name as subName, EXAM_time_table.date_starttime,student.id,EXAM_sessions.id as sessionId,EXAM_sessions.session as sessionName,"+
		" personal_data.date_of_birth,EXAM_time_table.date_endtime,course.name as coursename,EXAM_definition.name as examname,EXAM_exam_course_scheme_details.scheme_no,EXAM_definition.month as months,EXAM_definition.year "+
		" from student student " +
		" LEFT JOIN adm_appln adm_appln " +
		" ON (student.adm_appln_id = adm_appln.id) " + 
		" LEFT JOIN personal_data personal_data " +
		" ON (adm_appln.personal_data_id = personal_data.id) " +
		" LEFT JOIN class_schemewise class_schemewise " +
		" ON (student.class_schemewise_id = class_schemewise.id) " +
		" LEFT JOIN classes classes " +
		" ON (class_schemewise.class_id = classes.id) " +
		" LEFT JOIN applicant_subject_group applicant_subject_group " +
		" ON (applicant_subject_group.adm_appln_id = adm_appln.id) " +
		" LEFT JOIN subject_group_subjects subject_group_subjects " +
		" ON (subject_group_subjects.subject_group_id = applicant_subject_group.subject_group_id) " +
		" LEFT JOIN subject subject " +
		" ON (subject_group_subjects.subject_id = subject.id) " +
		" LEFT JOIN EXAM_exam_course_scheme_details EXAM_exam_course_scheme_details" +
		" ON (EXAM_exam_course_scheme_details.course_id = adm_appln.selected_course_id) " +
		" AND (EXAM_exam_course_scheme_details.scheme_no = classes.term_number)" +
		" LEFT JOIN course course " +
		" ON (EXAM_exam_course_scheme_details.course_id=course.id) " +
		" LEFT JOIN EXAM_definition EXAM_definition " +
		" ON (EXAM_exam_course_scheme_details.exam_id=EXAM_definition.id) " +
		" LEFT JOIN exam_regular_app examRegApp " +
		" ON (classes.id =examRegApp.class_id) " +
		" AND (student.id = examRegApp.student_id)" +
		" AND (EXAM_definition.id = examRegApp.exam_id)" +
		" LEFT JOIN EXAM_time_table EXAM_time_table" +
		" ON (EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id)" +
		" AND (EXAM_time_table.subject_id = subject.id)" +
		// newly added code by sudhir
		" LEFT JOIN EXAM_sessions on EXAM_time_table.exam_session_id = EXAM_sessions.id " + 
		" inner join EXAM_sub_definition_coursewise on EXAM_sub_definition_coursewise.subject_id = subject.id "+
		//
		/* code added by Sudhir 
		" LEFT JOIN EXAM_assign_students_room_studentlist room " +
		" ON (student.id = room.student_id)" +
		" LEFT JOIN EXAM_assign_students_room room1" +
		" ON (room.assign_stu_room_id=room1.id)" +
		" AND (room1.exam_id = EXAM_exam_course_scheme_details.exam_id)"+
		" AND (classes.id = room1.class_id)" +
		" LEFT JOIN EXAM_room_master EXAM_room_master " +
		" ON (EXAM_room_master.id = room1.room_id)" +
		 code added by Sudhir */
		" WHERE (subject_group_subjects.is_active = 1)" +
		" AND (subject.is_active = 1)" +
		" AND (adm_appln.is_cancelled = 0)" +
		" AND (classes.is_active = 1)" +
		" AND (EXAM_exam_course_scheme_details.is_active = 1)" +
		//" AND (EXAM_time_table.date_starttime is not null)" +
		" AND (EXAM_time_table.is_active=1)"+
		" AND student.id = "+studentId+
		" AND EXAM_exam_course_scheme_details.exam_id = "+examId+
		" AND examRegApp.chalan_verified =1 "+
		" AND EXAM_sub_definition_coursewise.subject_order is not null "+
		" group by EXAM_time_table.exam_course_scheme_id, student.id, classes.id, subject.id"+
		" order by EXAM_sub_definition_coursewise.subject_order";

		return query;
	}
	public HallTicketTo hallTicketListtoTo(List hallTicketData, Map<String, ExamRoomAllotmentDetailsTO> examSessionMap) throws Exception{
		HallTicketTo hallTicketTo=null;
		Map<Integer,HallTicketTo> studentSubMap = new HashMap<Integer, HallTicketTo>();
		Iterator<Object[]> ite= hallTicketData.iterator();
		String name = null;

		while(ite.hasNext()){
			Object[] data = ite.next();
			if(studentSubMap.containsKey(Integer.parseInt(data[8].toString()))){
				hallTicketTo=studentSubMap.remove(Integer.parseInt(data[8].toString()));
				List<SubjectTO> subList=hallTicketTo.getSubList();
				SubjectTO to= new SubjectTO();
				hallTicketTo.setDoNotDisplay("false"); 
				if(data[5]!=null){
					to.setCode(data[5].toString());
				}

				if(data[6]!=null){

					String str=data[6].toString();
					int length=str.length();
					String delimiter=" ";
					String [] pieces=str.split(delimiter);
					String s1="";
					String s2="";
					String s3="";
					String s4="";
					String s5="";
					int len=55;
					int i;

					if(length>=len){

						for(i=0;i<pieces.length;i++){

							if(s1.length()<len){
								s1=s1+pieces[i]+" ";

							} else if(s2.length()<len){
								s2=s2+pieces[i]+" ";

							} else if(s3.length()<len){
								s3=s3+pieces[i]+" ";

							} else if(s4.length()<len){
								s4=s4+pieces[i]+" ";

							} else if(s5.length()<len){
								s5=s5+pieces[i]+" ";

							}

						}//for loop close

						to.setName(s1);
						to.setSecName(s2);
						to.setThirdName(s3);
						to.setFourthName(s4);
						to.setFifthName(s5);

					} else{

						to.setName(data[6].toString());
					}

				}//main if close

				if(data[7]!=null){
					String time= data[7].toString();
					String dateString = time.substring(0, 10);
					String inputDateFormat = "yyyy-mm-dd";
					String outPutdateFormat = "dd/mm/yyyy";
					String hour = time.substring(11, 13);
					String minute = time.substring(14, 16);
					int h=Integer.parseInt(hour);
					/*if(h==00 || (h == 12 && minute.equals("00"))){
							to.setStartTime(12+":"+minute+" PM");
						}else if(h>6 && h<12)
							to.setStartTime(hour+":"+minute+" AM");
						else
							to.setStartTime(hour+":"+minute+" PM");*/
					if(h==00 || (h == 12 && minute.equals("00"))){
						to.setStartTime(12+":"+minute);
					}else if(h>6 && h<12)
						to.setStartTime(hour+":"+minute);
					else
						to.setStartTime(hour+":"+minute);
					String examDate = CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat);
					// here checking whether any subject has separate room allotment.
					if(data[9]!=null && examSessionMap!=null && !examSessionMap.isEmpty()){
						if(examSessionMap.containsKey(examDate+"_"+data[9].toString())){
							ExamRoomAllotmentDetailsTO roomTo = examSessionMap.get(examDate+"_"+data[9].toString());
							/*int h=Integer.parseInt(hour);
									if(h>6 && h<12)
										to.setStartTime(hour+":"+minute+" AM");
									else
										to.setStartTime(hour+":"+minute+" PM");*/
							to.setStartDate(roomTo.getExamDate());
							to.setRoomNo(roomTo.getRoomNo());
							to.setBlockNo(roomTo.getBlockName());
							to.setFloorNo(roomTo.getFloor());
						}else if(examSessionMap.containsKey("SameRoom")){
							ExamRoomAllotmentDetailsTO roomTo = examSessionMap.get("SameRoom");
							/*int h=Integer.parseInt(hour);
									if(h>6 && h<12)
										to.setStartTime(hour+":"+minute+" AM");
									else
										to.setStartTime(hour+":"+minute+" PM");*/

							to.setStartDate(examDate);	
							to.setRoomNo(roomTo.getRoomNo());
							to.setBlockNo(roomTo.getBlockName());
							to.setFloorNo(roomTo.getFloor());
						}else{
							to.setStartDate(examDate);
						}
					}else{
						to.setStartDate(examDate);
					}
				}
				//for SBC
				if(data[11]!=null){
					hallTicketTo.setDateofBirth(CommonUtil.formatSqlDate1(data[11].toString()));

				}
				if(data[12]!=null){
					String time= data[12].toString();
					String dateString = time.substring(0, 10);
					String inputDateFormat = "yyyy-mm-dd";
					String outPutdateFormat = "dd/mm/yyyy";
					String hour = time.substring(11, 13);
					String minute = time.substring(14, 16);
					int h=Integer.parseInt(hour);
					/*if(h==00 || (h == 12 && minute.equals("00"))){
							to.setEndTime(12+":"+minute+" PM");
						}else if(h>6 && h<12)
							to.setEndTime(hour+":"+minute+" AM");
						else
							to.setEndTime(hour+":"+minute+" PM");*/
					if(h==00 || (h == 12 && minute.equals("00"))){
						to.setEndTime(12+":"+minute);
					}else if(h>6 && h<12)
						to.setEndTime(hour+":"+minute);
					else
						to.setEndTime(hour+":"+minute);
				}
				if(data[13]!=null &&data[15]!=null){
					hallTicketTo.setCourseName(data[13].toString());
					hallTicketTo.setSemesterNo(semMap.get(data[15].toString()));
				}
				if(data[15]!=null){

					//hallTicketTo.setSemesterExt(CommonUtil.numberToWord1(Integer.parseInt(data[15].toString())));

					if(Integer.parseInt(data[15].toString())==1)
						hallTicketTo.setSemesterExt("FIRST");
					if(Integer.parseInt(data[15].toString())==2)
						hallTicketTo.setSemesterExt("SECOND");
					if(Integer.parseInt(data[15].toString())==3)
						hallTicketTo.setSemesterExt("THIRD");
					if(Integer.parseInt(data[15].toString())==4)
						hallTicketTo.setSemesterExt("FOURTH");
					if(Integer.parseInt(data[15].toString())==5)
						hallTicketTo.setSemesterExt("FIFTH");
					if(Integer.parseInt(data[15].toString())==6)
						hallTicketTo.setSemesterExt("SIXTH");

				}
				if(data[14]!=null){
					hallTicketTo.setExamName(data[14].toString());
				}
				if(data[16]!=null){
					String month=CommonUtil.getMonthForNumber(Integer.parseInt (data[16].toString()));
					hallTicketTo.setMonth(month);
				}
				if(data[17]!=null){
					hallTicketTo.setYear(data[17].toString());
				}
				subList.add(to);
				hallTicketTo.setSubList(subList);
				studentSubMap.put(Integer.parseInt(data[8].toString()), hallTicketTo);	
			}


			else{
				hallTicketTo= new HallTicketTo();
				List<SubjectTO> subList= new ArrayList<SubjectTO>();
				SubjectTO subTo= new SubjectTO();
				if(data[0]!=null){
					hallTicketTo.setClassName(data[0].toString());
					if(hallTicketTo.getClassName().equalsIgnoreCase("4MPCO") || hallTicketTo.getClassName().equalsIgnoreCase("4MPCL")
							||hallTicketTo.getClassName().equalsIgnoreCase("4MCS")|| hallTicketTo.getClassName().equalsIgnoreCase("4MPHR")||
							hallTicketTo.getClassName().equalsIgnoreCase("4MSCS")||hallTicketTo.getClassName().equalsIgnoreCase("6MCA")){

						hallTicketTo.setDoNotDisplay("true");
					}else{//added by sudhir
						hallTicketTo.setDoNotDisplay("false");
					}

				}
				if(data[1] != null){
					hallTicketTo.setRegisterNo(data[1].toString());
				}
				if(data[2]!=null){
					name=data[2].toString();
				}

				if(data[3]!=null){
					name=name+" "+data[3].toString();
				}
				if(data[4]!=null){
					name=name+" "+data[4].toString();
				}
				if(name != null){
					hallTicketTo.setStudentName(name);
				}
				if(data[5]!=null){
					subTo.setCode(data[5].toString());
				}



				if(data[6]!=null){

					String str=data[6].toString();
					int length=str.length();
					String delimiter=" ";
					String [] pieces=str.split(delimiter);
					String s1="";
					String s2="";
					String s3="";
					String s4="";
					String s5="";
					int len=55;
					int i;

					if(length>=len){

						for(i=0;i<pieces.length;i++){

							if(s1.length()<len){
								s1=s1+pieces[i]+" ";

							} else if(s2.length()<len){
								s2=s2+pieces[i]+" ";

							} else if(s3.length()<len){
								s3=s3+pieces[i]+" ";

							} else if(s4.length()<len){
								s4=s4+pieces[i]+" ";

							} else if(s5.length()<len){
								s5=s5+pieces[i]+" ";

							}

						}//for loop close

						subTo.setName(s1);
						subTo.setSecName(s2);
						subTo.setThirdName(s3);
						subTo.setFourthName(s4);
						subTo.setFifthName(s5);

					} else{

						subTo.setName(data[6].toString());
					}

				}//main if close

				if(data[7]!=null){
					String time= data[7].toString();
					String dateString = time.substring(0, 10);
					String inputDateFormat = "yyyy-mm-dd";
					String outPutdateFormat = "dd/mm/yyyy";
					String examDate = CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat);
					String hour = time.substring(11, 13);
					String minute = time.substring(14, 16);
					int h=Integer.parseInt(hour);
					/*if(h==00 || (h == 12 && minute.equals("00"))){
					subTo.setStartTime(12+":"+minute+" PM");
				}else if(h>6 && h<12)
					subTo.setStartTime(hour+":"+minute+" AM");
				else
					subTo.setStartTime(hour+":"+minute+" PM");*/
					if(h==00 || (h == 12 && minute.equals("00"))){
						subTo.setStartTime(12+":"+minute);
					}else if(h>6 && h<12)
						subTo.setStartTime(hour+":"+minute);
					else
						subTo.setStartTime(hour+":"+minute);
					if(data[9]!=null && examSessionMap!=null && !examSessionMap.isEmpty()){
						if(examSessionMap.containsKey(examDate+"_"+data[9].toString())){
							ExamRoomAllotmentDetailsTO to = examSessionMap.get(examDate+"_"+data[9].toString());
							/*int h=Integer.parseInt(hour);
							if(h>6 && h<12)
								subTo.setStartTime(hour+":"+minute+" AM");
							else
								subTo.setStartTime(hour+":"+minute+" PM");*/
							subTo.setStartDate(to.getExamDate());
							subTo.setRoomNo(to.getRoomNo());
							subTo.setBlockNo(to.getBlockName());
							subTo.setFloorNo(to.getFloor());
						}else if(examSessionMap.containsKey("SameRoom")){
							ExamRoomAllotmentDetailsTO to = examSessionMap.get("SameRoom");
							/*int h=Integer.parseInt(hour);
							if(h>6 && h<12)
								subTo.setStartTime(hour+":"+minute+" AM");
							else
								subTo.setStartTime(hour+":"+minute+" PM");*/

							subTo.setStartDate(examDate);
							subTo.setRoomNo(to.getRoomNo());
							subTo.setBlockNo(to.getBlockName());
							subTo.setFloorNo(to.getFloor());
						}else{
							subTo.setStartDate(examDate);
						}
					}else{
						subTo.setStartDate(examDate);
					}


				}
				//for SBC
				if(data[11]!=null){
					hallTicketTo.setDateofBirth(CommonUtil.formatSqlDate1(data[11].toString()));

				}
				if(data[12]!=null){
					String time= data[12].toString();
					String dateString = time.substring(0, 10);
					String inputDateFormat = "yyyy-mm-dd";
					String outPutdateFormat = "dd/mm/yyyy";
					String hour = time.substring(11, 13);
					String minute = time.substring(14, 16);
					int h=Integer.parseInt(hour);
					/*if(h==00 || (h == 12 && minute.equals("00"))){
					subTo.setEndTime(12+":"+minute+" PM");
				}else if(h>6 && h<12)
					subTo.setEndTime(hour+":"+minute+" AM");
				else
					subTo.setEndTime(hour+":"+minute+" PM");*/
					if(h==00 || (h == 12 && minute.equals("00"))){
						subTo.setEndTime(12+":"+minute);
					}else if(h>6 && h<12)
						subTo.setEndTime(hour+":"+minute);
					else
						subTo.setEndTime(hour+":"+minute);
				}
				if(data[13]!=null &&data[15]!=null){
					hallTicketTo.setCourseName(data[13].toString());
					hallTicketTo.setSemesterNo(semMap.get(data[15].toString()));
				}
				if(data[15]!=null){

					//hallTicketTo.setSemesterExt(CommonUtil.numberToWord1(Integer.parseInt(data[15].toString())));

					if(Integer.parseInt(data[15].toString())==1)
						hallTicketTo.setSemesterExt("FIRST");
					if(Integer.parseInt(data[15].toString())==2)
						hallTicketTo.setSemesterExt("SECOND");
					if(Integer.parseInt(data[15].toString())==3)
						hallTicketTo.setSemesterExt("THIRD");
					if(Integer.parseInt(data[15].toString())==4)
						hallTicketTo.setSemesterExt("FOURTH");
					if(Integer.parseInt(data[15].toString())==5)
						hallTicketTo.setSemesterExt("FIFTH");
					if(Integer.parseInt(data[15].toString())==6)
						hallTicketTo.setSemesterExt("SIXTH");
				}
				if(data[14]!=null){
					hallTicketTo.setExamName(data[14].toString());
				}
				if(data[16]!=null){
					String month=CommonUtil.getMonthForNumber(Integer.parseInt (data[16].toString()));
					hallTicketTo.setMonth(month);
				}
				if(data[17]!=null){
					hallTicketTo.setYear(data[17].toString());
				}
				/*------------------------*/
				subList.add(subTo);
				hallTicketTo.setSubList(subList);
				studentSubMap.put(Integer.parseInt(data[8].toString()), hallTicketTo);	
			}
		}
		studentSubMap = (HashMap<Integer, HallTicketTo>) CommonUtil.sortMapByValue(studentSubMap);
		return hallTicketTo;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	
	
	public String getQueryForUGStudentMarksCard(int examId,int classId,int schemeNo,int studentId, int academicYear) throws Exception {
		String q="  SELECT EXAM_student_overall_internal_mark_details.exam_id as eid,  " +
		"         EXAM_student_overall_internal_mark_details.student_id as studID,  " +	//1
		"         EXAM_student_overall_internal_mark_details.class_id as classID,  " +	//2
		"         EXAM_student_overall_internal_mark_details.subject_id as subID,  " +	//3
		"         subject.code as subCode,  " +	//4
		"         subject.name as subName,  " +	//5
		"         EXAM_student_overall_internal_mark_details.theory_total_mark,  " +	//6
		"         EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,  " +	//7
		"         EXAM_student_overall_internal_mark_details.practical_total_mark,  " +	//8
		"         EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,  " +	//9
		"         CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id,  " +	//10
		"         EXAM_subject_rule_settings.theory_ese_minimum_mark,  " +	//11
		"         EXAM_subject_rule_settings.theory_ese_maximum_mark,  " +	//12
		"         EXAM_subject_rule_settings.practical_ese_minimum_mark,  " +	//13
		"         EXAM_subject_rule_settings.practical_ese_maximum_mark,  " +	//14
		"         (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks,  " +
		"         (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks,  " +
		"         personal_data.first_name,  " +	//17
		"         personal_data.middle_name,  " +	//18
		"         personal_data.last_name,  " +		//19
		"         ifnull(EXAM_subject_sections.name,' ') as secName,  " +	//20
		"         EXAM_subject_sections.is_initialise,  " +	//21
		"         EXAM_subject_sections.id as secID,  " +	//22
		"         EXAM_sub_definition_coursewise.subject_order,  " +	//23
		"         EXAM_subject_rule_settings.final_practical_internal_maximum_mark,  " +	//24
		"         EXAM_subject_rule_settings.final_theory_internal_maximum_mark,  " +	//25
		"  		  (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal,  " +
		"    	  (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal,  " +
		"    	  (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,  " +
		"    	  (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,  " +
		"         max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit,  " +	//30
		"         max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit,  " +	//31
		"   	  ifnull( (select oldreg.register_no " +
		"   				from student_old_register_numbers oldreg " +
		"   				where oldreg.student_id=student.id " +
		"   				and oldreg.scheme_no=classes.term_number and oldreg.is_active=1 and oldreg.academic_year="+academicYear+")," +
		"   				student.register_no) as register_no," +	//32
        " 		  if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical',if(subject.is_theory_practical='B','Both','Theory'))) as subType, "+  
		"         if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		"    			FROM EXAM_sub_coursewise_attendance_marks  " +
		"  				where course_id=classes.course_id  " +
		"  				and subject_id=subject.id) is not null,   " +
		"  				(SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		"    			   FROM EXAM_sub_coursewise_attendance_marks  " +
		"  				  where course_id=classes.course_id  " +
		"  					and subject_id=subject.id),  " +
		"  				(SELECT max(EXAM_attendance_marks.marks)  " +
		"    			   FROM EXAM_attendance_marks EXAM_attendance_marks  " +
		"    			  where course_id = classes.course_id))  AS maxAttMarks,  " +	//34
		"    	  classes.term_number,  " +	//35
		"         EXAM_publish_exam_results.publish_date,  " +	//36
		"         adm_appln.course_id as admCourseID,  " +		//37
		"         (round((if(EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/(if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100)) as theoryPer,  " +
		"  		  (round((if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100)) as practicalper,  " +
		"   	  ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  					FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		"   				(ifnull((SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     						   FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" 							(SELECT  EXAM_grade_class_definition.grade FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" 		  ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade " +
		" 					FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" 					(ifnull((SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  							   FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" 							 (SELECT EXAM_grade_class_definition.grade FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +
		" 		  ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		" 					FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" 					(ifnull((SELECT EXAM_grade_class_definition_frombatch.grade_point" +
		" 							   FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		"  							(SELECT EXAM_grade_class_definition.grade_point FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegrap, " +
		"  		  ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		"  					FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" 					(ifnull((SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		"  							   FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" 							(SELECT  EXAM_grade_class_definition.grade_point FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragrap,  " +
		"    	  EXAM_sub_definition_coursewise.dont_show_max_marks,  " +	//44
		"    	  EXAM_sub_definition_coursewise.dont_show_min_marks,  " +	//45
		"     	  EXAM_sub_definition_coursewise.show_only_grade,  " +		//46
		"    	  course_scheme.name as courseSchemeNo,  " +	//47
		"    	  EXAM_sub_definition_coursewise.dont_show_sub_type, " +	//48
		"    	  EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +	//49
		"	 	  EXAM_sub_definition_coursewise.dont_show_att_marks," +	//50
		"	 	  subject.is_certificate_course," +	//51
		"	 	  EXAM_sub_definition_coursewise.dont_consider_failure_total_result, " +	//52
		"    	  EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +	//53
		"    	  EXAM_subject_rule_settings.final_practical_internal_minimum_mark, " +	//54
		"    	  sum((if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) + (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks ))) as subFinalObtained, "+
		"	 	  sum((if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)))) as subInternalMarksObtained,"+
		"	 	  sum((if(student_theory_marks is null,0,student_theory_marks)) +if(student_practical_marks is null,0,student_practical_marks )) as subTheoryMarksObtained, "+
		" 		  EXAM_subject_rule_settings.subject_final_maximum, "+	//58
		" 		  EXAM_subject_rule_settings.subject_final_minimum,  "+	//59
		" 		  sum((if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark is null,0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark)) +if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark is null,0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark )) as internalMaxMarks, "+
		" 		  sum((if(EXAM_subject_rule_settings.theory_ese_maximum_mark is null,0,EXAM_subject_rule_settings.theory_ese_maximum_mark)) +if(EXAM_subject_rule_settings.practical_ese_maximum_mark is null,0,EXAM_subject_rule_settings.practical_ese_maximum_mark )) as externalMaxMarks, "+
		" 		  sum((if(EXAM_subject_rule_settings.theory_ese_minimum_mark is null,0,EXAM_subject_rule_settings.theory_ese_minimum_mark)) +if(EXAM_subject_rule_settings.practical_ese_minimum_mark is null,0,EXAM_subject_rule_settings.practical_ese_minimum_mark )) as externalMinMarks, "+
        "  		  EXAM_definition.academic_year, "+	//63
        " 		  adm_appln.applied_year, "+	//64
        " 		  subject.subjectType_based_on_marks "+	//65

		" 		  from EXAM_student_overall_internal_mark_details "+
		" 		  LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+
		"   	  LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id "+
		" 		  LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id "+
		" 		  LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		" 		  LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id "+
		" 		  LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id "+
		" 		  LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
		" 		  LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id "+
		" 		  LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		" 		  LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
		" 		  LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id "+
		" 				and EXAM_student_final_mark_details.exam_id = EXAM_definition.id "+
		" 				and EXAM_student_final_mark_details.student_id = student.id "+
		" 				and EXAM_student_final_mark_details.subject_id = subject.id "+
		" 		  LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id "+
		" 				and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year "+
		" 				and EXAM_subject_rule_settings.course_id = classes.course_id "+
		" 				and EXAM_subject_rule_settings.scheme_no = classes.term_number "+
		" 		  LEFT JOIN EXAM_publish_exam_results ON EXAM_publish_exam_results.class_id = classes.id "+
		" 				and EXAM_publish_exam_results.exam_id = EXAM_definition.id "+
		" 		  LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id "+
		" 				and EXAM_sub_definition_coursewise.course_id = classes.course_id "+
		" 				and EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
		" 				and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year "+
		" 		  LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
		" 		  LEFT JOIN course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id "+
		//"         where EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln=0"+
		" 			where (subject.is_theory_practical='T' or subject.is_theory_practical='P') " +
		"			and subject.is_active=1 "+
		"           and EXAM_student_overall_internal_mark_details.exam_id ="+examId +
		"           and subject.is_active=1 " +
		"			and EXAM_student_overall_internal_mark_details.class_id = " +classId +
		"           and classes.term_number = "+schemeNo +
		"           and student.id=  "+studentId +
		"         group by student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id  " +

		"                    UNION ALL  " +

		"         SELECT EXAM_student_overall_internal_mark_details.exam_id as eid,  " +
		"         EXAM_student_overall_internal_mark_details.student_id as studID,  " +
		"         EXAM_student_overall_internal_mark_details.class_id as classID,  " +
		"         EXAM_student_overall_internal_mark_details.subject_id as subID,  " +
		"         subject.code as subCode,  " +
		"         subject.name as subName,  " +
		"         EXAM_student_overall_internal_mark_details.theory_total_mark,  " +
		"         EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,  " +
		"         EXAM_student_overall_internal_mark_details.practical_total_mark,  " +
		"         EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,  " +
		"         CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id,  " +
		"         EXAM_subject_rule_settings.theory_ese_minimum_mark,  " +
		"         EXAM_subject_rule_settings.theory_ese_maximum_mark,  " +
		"         EXAM_subject_rule_settings.practical_ese_minimum_mark,  " +
		"         EXAM_subject_rule_settings.practical_ese_maximum_mark,  " +
		"         (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks,  " +
		"         (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks,  " +
		"         personal_data.first_name,  " +
		"         personal_data.middle_name,  " +
		"         personal_data.last_name,  " +
		"         ifnull(EXAM_subject_sections.name,' ') as secName,  " +
		"         EXAM_subject_sections.is_initialise,  " +
		"         EXAM_subject_sections.id as secID,  " +
		"         EXAM_sub_definition_coursewise.subject_order,  " +
		"         EXAM_subject_rule_settings.final_practical_internal_maximum_mark,  " +
		"         EXAM_subject_rule_settings.final_theory_internal_maximum_mark,  " +
		"  		 (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal,  " +
		"    	 (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal, " +
		"    	 (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,  " +
		"    	 (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,  " +
		"         max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit,  " +
		"         max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit,  " +
		"         ifnull( (select oldreg.register_no " +
		"   				 from student_old_register_numbers oldreg " +
		"   				where oldreg.student_id=student.id " +
		"   				  and oldreg.scheme_no=classes.term_number and oldreg.is_active=1 and oldreg.academic_year="+academicYear+")," +
		"   				student.register_no) as register_no," +
        " 		  if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical',if(subject.is_theory_practical='B','Both','Theory'))) as subType, "+  
		"         if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		"    			FROM EXAM_sub_coursewise_attendance_marks  " +
		"  			   where course_id=classes.course_id  " +
		"  				 and subject_id=subject.id) is not null,   " +
		"  				(SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		"    			   FROM EXAM_sub_coursewise_attendance_marks  " +
		"  				  where course_id=classes.course_id  " +
		"  					and subject_id=subject.id),  " +
		"  				(SELECT max(EXAM_attendance_marks.marks)  " +
		"    			   FROM EXAM_attendance_marks EXAM_attendance_marks  " +
		"    			  where course_id = classes.course_id))  AS maxAttMarks,  " +
		"    	  classes.term_number,  " +
		"         EXAM_publish_exam_results.publish_date,  " +
		"         adm_appln.course_id as admCourseID,  " +
	    "  		  (round((if (EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0)+if(student_practical_marks > 0,student_practical_marks,0)+if(EXAM_student_overall_internal_mark_details.practical_total_mark  > 0,EXAM_student_overall_internal_mark_details.practical_total_mark ,0))/ (if(EXAM_subject_rule_settings.subject_final_maximum > 0,EXAM_subject_rule_settings.subject_final_maximum,0))*100)) as theoryPer, "+		
		"  		  (round((if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100)) as practicalper,  " +
		"   	  ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  					FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		"   				(ifnull((SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     						   FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" 							(SELECT EXAM_grade_class_definition.grade FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" 		  ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade " +
		" 					FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" 					(ifnull((SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  							   FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" 							(SELECT EXAM_grade_class_definition.grade FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +
		" 		  ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		" 					FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" 					(ifnull((SELECT EXAM_grade_class_definition_frombatch.grade_point" +
		" 							   FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		"  							(SELECT EXAM_grade_class_definition.grade_point FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegrap, " +
		"  		  ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		"  					FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" 					(ifnull((SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		"  							   FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" 							(SELECT EXAM_grade_class_definition.grade_point FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragrap,  " +
		"    	  EXAM_sub_definition_coursewise.dont_show_max_marks,  " +
		"    	  EXAM_sub_definition_coursewise.dont_show_min_marks,  " +
		"    	  EXAM_sub_definition_coursewise.show_only_grade,  " +
		"    	  course_scheme.name as courseSchemeNo,  " +
		"    	  EXAM_sub_definition_coursewise.dont_show_sub_type, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln," +
		"	 	  EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,  " +
		"   	  EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		"   	  EXAM_subject_rule_settings.final_practical_internal_minimum_mark, " +
		"    	  sum((if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) + (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks ))) as subFinalObtained, "+
		"	 	  sum((if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)))) as subInternalMarksObtained,"+
		"	 	  sum((if(student_theory_marks is null,0,student_theory_marks)) +if(student_practical_marks is null,0,student_practical_marks )) as subTheoryMarksObtained, "+
		" 		  EXAM_subject_rule_settings.subject_final_maximum, "+
		" 		  EXAM_subject_rule_settings.subject_final_minimum,  "+
		" 		  sum((if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark is null,0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark)) +if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark is null,0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark )) as internalMaxMarks, "+
		" 	      sum((if(EXAM_subject_rule_settings.theory_ese_maximum_mark is null,0,EXAM_subject_rule_settings.theory_ese_maximum_mark)) +if(EXAM_subject_rule_settings.practical_ese_maximum_mark is null,0,EXAM_subject_rule_settings.practical_ese_maximum_mark )) as externalMaxMarks, "+
		" 		  sum((if(EXAM_subject_rule_settings.theory_ese_minimum_mark is null,0,EXAM_subject_rule_settings.theory_ese_minimum_mark)) +if(EXAM_subject_rule_settings.practical_ese_minimum_mark is null,0,EXAM_subject_rule_settings.practical_ese_minimum_mark )) as externalMinMarks, "+
		"  		  EXAM_definition.academic_year, "+
		" 		  adm_appln.applied_year, "+
		" 		  subject.subjectType_based_on_marks "+

		" 		  from EXAM_student_overall_internal_mark_details "+
		" 		  LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+
		" 		  LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id "+
		" 		  LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id "+
		" 		  LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		" 		  LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id "+
		" 		  LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id "+
		" 		  LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
		" 		  LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id "+
		" 		  LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		" 		  LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
		" 		  LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id "+
		" 		   and EXAM_student_final_mark_details.exam_id = EXAM_definition.id "+
		" 		   and EXAM_student_final_mark_details.student_id = student.id "+
		" 		   and EXAM_student_final_mark_details.subject_id = subject.id "+
		" 		  LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id "+
		" 		   and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year "+
		" 		   and EXAM_subject_rule_settings.course_id = classes.course_id "+
		" 		   and EXAM_subject_rule_settings.scheme_no = classes.term_number "+
		" 		  LEFT JOIN EXAM_publish_exam_results ON EXAM_publish_exam_results.class_id = classes.id "+
		" 		   and EXAM_publish_exam_results.exam_id = EXAM_definition.id "+
		" 		  LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id "+
		" 		   and EXAM_sub_definition_coursewise.course_id = classes.course_id "+
		" 		   and EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
		" 		   and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year "+
		" 		  LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
		" 		  LEFT JOIN course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id "+
		"         where subject.is_theory_practical='B' " +
		"			and subject.is_active=1  " +
		"           and EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln=0"+
		"           and EXAM_student_overall_internal_mark_details.exam_id ="+examId +
		"           and EXAM_student_overall_internal_mark_details.class_id =" +classId +
		"           and classes.term_number ="+schemeNo +
		"           and student.id = " +studentId+
		"    	  group by student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id  order by subject_order" ;

		return q;
	}







	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPGStudentMarksCard(int examId,int classId,int studentId,int schemeNo,int academicYear) throws Exception {
		String query=" SELECT EXAM_student_overall_internal_mark_details.exam_id as eid, " +
		"        EXAM_student_overall_internal_mark_details.student_id as studid,   " +
		"        EXAM_student_overall_internal_mark_details.class_id as cid,   " +
		"        EXAM_student_overall_internal_mark_details.subject_id as subid,   " +
		"        subject.code as subCode,   " +
		"        subject.name as subName,   " +
		"        EXAM_student_overall_internal_mark_details.theory_total_mark,   " +
		"        EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,   " +
		"        EXAM_student_overall_internal_mark_details.   " +
		"        practical_total_mark,   " +
		"        EXAM_student_overall_internal_mark_details.   " +
		"        practical_total_attendance_mark,   " +
		"        CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id,   " +
		"        EXAM_subject_rule_settings.theory_ese_minimum_mark,   " +
		"        EXAM_subject_rule_settings.theory_ese_maximum_mark,   " +
		"        EXAM_subject_rule_settings.practical_ese_minimum_mark,   " +
		"        EXAM_subject_rule_settings.practical_ese_maximum_mark,   " +
		"        (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks,   " +
		"        (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks,   " +
		"        personal_data.first_name,   " +
		"        personal_data.middle_name,   " +
		"        personal_data.last_name,   " +
		"        EXAM_subject_sections.name as secName,   " +
		"        EXAM_subject_sections.is_initialise,   " +
		"        EXAM_subject_sections.id as secId,   " +
		"        EXAM_sub_definition_coursewise.subject_order,   " +
		"        EXAM_subject_rule_settings.final_practical_internal_maximum_mark,   " +
		"        EXAM_subject_rule_settings.final_theory_internal_maximum_mark,   " +
		" (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal   " +
		"   ,(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal,   " +
		"   (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,   " +
		"   (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,   " +
		"        max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit,   " +
		"        max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit,   " +
		"       ifnull( (select oldreg.register_no " +
		"   from student_old_register_numbers oldreg " +
		"   where oldreg.student_id=student.id " +
		"   and oldreg.scheme_no=classes.term_number and oldreg.is_active=1)," +
		"   student.register_no) as register_no," +
		"        if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Theory')) as subType,   " +
		"         if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)   " +
		"   FROM EXAM_sub_coursewise_attendance_marks   " +
		" where course_id=classes.course_id   " +
		" and subject_id=subject.id) is not null,    " +
		" (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)   " +
		"   FROM EXAM_sub_coursewise_attendance_marks   " +
		" where course_id=classes.course_id   " +
		" and subject_id=subject.id),   " +
		" (SELECT max(EXAM_attendance_marks.marks)   " +
		"   FROM EXAM_attendance_marks EXAM_attendance_marks   " +
		"   where course_id = classes.course_id))  AS maxAttMarks,   " +
		"   classes.term_number,   " +
		"        EXAM_publish_exam_results.publish_date,   " +
		"        adm_appln.course_id as admCourseid,   " +
		"          (  (if(EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer,   " +
		" (  (if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper,   " +
		"    ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade " +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +
		" ifnull(  (SELECT   EXAM_sub_coursewise_grade_defn.grade_point " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT   EXAM_grade_class_definition_frombatch.grade_point" +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		"  (SELECT   EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegrap, " +
		"  ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade_point " +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull(  (SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragrap,  " +
		"   EXAM_sub_definition_coursewise.dont_show_max_marks,   " +
		"   EXAM_sub_definition_coursewise.dont_show_min_marks,   " +
		"   EXAM_sub_definition_coursewise.show_only_grade,   " +
		"   course_scheme.name as csName,   " +
		"   EXAM_sub_definition_coursewise.dont_show_sub_type,   " +
		"   EXAM_sub_definition_coursewise.dont_consider_failure_total_result,   " +
		"   if((EXAM_sub_definition_coursewise.dont_consider_failure_total_result=1 and EXAM_sub_definition_coursewise.dont_consider_failure_total_result is not null and subject.id<>902 and (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks)))<60),1,0) as certificate,   " +
		"   subject.is_theory_practical, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		"	 EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result, " +
		"   EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		"   EXAM_subject_rule_settings.final_practical_internal_minimum_mark, " +
		 " subject_type.id as subTypeId , "+
	    "  EXAM_definition.academic_year, "+
        " adm_appln.applied_year, "+
        " subject.subjectType_based_on_marks "+
       

		" from EXAM_student_overall_internal_mark_details "+
		" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+
		" LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id "+
		" LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id "+
		//" LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		
		" left join applicant_subject_group on adm_appln.id = applicant_subject_group.adm_appln_id "+
		 " inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id and subject_group.is_active=1 "+
		 "     inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id and subject_group_subjects.is_active=1 "+
		 "    inner join subject ON subject_group_subjects.subject_id = subject.id and subject.is_active=1 and EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		
		" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id "+
		" LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id "+
		" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
		" LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id "+
		" LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		" LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
		
		" LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id "+
		" and EXAM_student_final_mark_details.exam_id = EXAM_definition.id "+
		" and EXAM_student_final_mark_details.student_id = student.id "+
		" and EXAM_student_final_mark_details.subject_id = subject.id "+
		" LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id "+
		" and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year "+
		" and EXAM_subject_rule_settings.course_id = classes.course_id "+
		" and EXAM_subject_rule_settings.scheme_no = classes.term_number "+
		" LEFT JOIN EXAM_publish_exam_results ON EXAM_publish_exam_results.class_id = classes.id "+
		" and EXAM_publish_exam_results.exam_id = EXAM_definition.id "+
		" LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id "+
		" and EXAM_sub_definition_coursewise.course_id = classes.course_id "+
		" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
		" and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year "+
		" LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
		" LEFT JOIN course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id "+
		"   where student.id not in (   " +
		" SELECT EXAM_update_exclude_withheld.student_id   " +
		"   FROM EXAM_update_exclude_withheld EXAM_update_exclude_withheld where EXAM_update_exclude_withheld.exclude_from_results = 1 and EXAM_update_exclude_withheld.exam_id="+examId+") " +
		"  and EXAM_student_overall_internal_mark_details.exam_id ="+examId+" and EXAM_student_overall_internal_mark_details.class_id="+classId+
		"  and classes.term_number=  "+schemeNo+"  and EXAM_subject_rule_settings.scheme_no= "+schemeNo+"  and student.id=" +studentId+
		" group by student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id "+
		
		" UNION ALL " +
		" SELECT EXAM_student_overall_internal_mark_details.exam_id as eid, " +
		"        EXAM_student_overall_internal_mark_details.student_id as studid,   " +
		"        EXAM_student_overall_internal_mark_details.class_id as cid,   " +
		"        EXAM_student_overall_internal_mark_details.subject_id as subid,   " +
		"        subject.code as subCode,   " +
		"        subject.name as subName,   " +
		"        EXAM_student_overall_internal_mark_details.theory_total_mark,   " +
		"        EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,   " +
		"        EXAM_student_overall_internal_mark_details.   " +
		"        practical_total_mark,   " +
		"        EXAM_student_overall_internal_mark_details.   " +
		"        practical_total_attendance_mark,   " +
		"        CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id,   " +
		"        EXAM_subject_rule_settings.theory_ese_minimum_mark,   " +
		"        EXAM_subject_rule_settings.theory_ese_maximum_mark,   " +
		"        EXAM_subject_rule_settings.practical_ese_minimum_mark,   " +
		"        EXAM_subject_rule_settings.practical_ese_maximum_mark,   " +
		"        (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks,   " +
		"        (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks,   " +
		"        personal_data.first_name,   " +
		"        personal_data.middle_name,   " +
		"        personal_data.last_name,   " +
		"        EXAM_subject_sections.name as secName,   " +
		"        EXAM_subject_sections.is_initialise,   " +
		"        EXAM_subject_sections.id as secId,   " +
		"        EXAM_sub_definition_coursewise.subject_order,   " +
		"        EXAM_subject_rule_settings.final_practical_internal_maximum_mark,   " +
		"        EXAM_subject_rule_settings.final_theory_internal_maximum_mark,   " +
		" (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal   " +
		"   ,(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal,   " +
		"   (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,   " +
		"   (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,   " +
		"        max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit,   " +
		"        max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit,   " +
		"       ifnull( (select oldreg.register_no " +
		"   from student_old_register_numbers oldreg " +
		"   where oldreg.student_id=student.id " +
		"   and oldreg.scheme_no=classes.term_number and oldreg.is_active=1)," +
		"   student.register_no) as register_no," +
		"        if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Theory')) as subType,   " +
		"         if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)   " +
		"   FROM EXAM_sub_coursewise_attendance_marks   " +
		" where course_id=classes.course_id   " +
		" and subject_id=subject.id) is not null,    " +
		" (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)   " +
		"   FROM EXAM_sub_coursewise_attendance_marks   " +
		" where course_id=classes.course_id   " +
		" and subject_id=subject.id),   " +
		" (SELECT max(EXAM_attendance_marks.marks)   " +
		"   FROM EXAM_attendance_marks EXAM_attendance_marks   " +
		"   where course_id = classes.course_id))  AS maxAttMarks,   " +
		"   classes.term_number,   " +
		"        EXAM_publish_exam_results.publish_date,   " +
		"        adm_appln.course_id as admCourseid,   " +
		"          (  (if(EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer,   " +
		" (  (if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper,   " +
		"    ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade " +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +
		" ifnull(  (SELECT   EXAM_sub_coursewise_grade_defn.grade_point " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT   EXAM_grade_class_definition_frombatch.grade_point" +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		"  (SELECT   EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegrap, " +
		"  ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade_point " +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull(  (SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragrap,  " +
		"   EXAM_sub_definition_coursewise.dont_show_max_marks,   " +
		"   EXAM_sub_definition_coursewise.dont_show_min_marks,   " +
		"   EXAM_sub_definition_coursewise.show_only_grade,   " +
		"   course_scheme.name as csName,   " +
		"   EXAM_sub_definition_coursewise.dont_show_sub_type,   " +
		"   EXAM_sub_definition_coursewise.dont_consider_failure_total_result,   " +
		"   if((EXAM_sub_definition_coursewise.dont_consider_failure_total_result=1 and EXAM_sub_definition_coursewise.dont_consider_failure_total_result is not null and subject.id<>902 and (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks)))<60),1,0) as certificate,   " +
		"   subject.is_theory_practical, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		"	 EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result, " +
		"   EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		"   EXAM_subject_rule_settings.final_practical_internal_minimum_mark, " +
		 " subject_type.id as subTypeId , "+
	    "  EXAM_definition.academic_year, "+
        " adm_appln.applied_year, "+
        " subject.subjectType_based_on_marks "+
     

		" from EXAM_student_overall_internal_mark_details "+
		" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+
		" LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id "+
		" LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id "+
		//" LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		
		" LEFT JOIN EXAM_student_sub_grp_history ON student.id = EXAM_student_sub_grp_history.student_id "+
	    " inner join subject_group ON EXAM_student_sub_grp_history.subject_group_id = subject_group.id and subject_group.is_active=1 "+
	     " inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id and subject_group_subjects.is_active=1 "+
	    " inner join subject ON subject_group_subjects.subject_id = subject.id and subject.is_active=1 and EXAM_student_overall_internal_mark_details.subject_id = subject.id "+
		
		" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id "+
		" LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id "+
		" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+
		" LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id "+
		" LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		" LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
		
		" LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id "+
		" and EXAM_student_final_mark_details.exam_id = EXAM_definition.id "+
		" and EXAM_student_final_mark_details.student_id = student.id "+
		" and EXAM_student_final_mark_details.subject_id = subject.id "+
		" LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id "+
		" and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year "+
		" and EXAM_subject_rule_settings.course_id = classes.course_id "+
		" and EXAM_subject_rule_settings.scheme_no = classes.term_number "+
		" LEFT JOIN EXAM_publish_exam_results ON EXAM_publish_exam_results.class_id = classes.id "+
		" and EXAM_publish_exam_results.exam_id = EXAM_definition.id "+
		" LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id "+
		" and EXAM_sub_definition_coursewise.course_id = classes.course_id "+
		" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
		" and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year "+
		" LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
		" LEFT JOIN course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id "+
		"   where student.id not in (   " +
		" SELECT EXAM_update_exclude_withheld.student_id   " +
		"   FROM EXAM_update_exclude_withheld EXAM_update_exclude_withheld where EXAM_update_exclude_withheld.exclude_from_results = 1 and EXAM_update_exclude_withheld.exam_id="+examId+") " +
		"  and EXAM_student_overall_internal_mark_details.exam_id ="+examId+" and EXAM_student_overall_internal_mark_details.class_id="+classId+
		"  and classes.term_number=  "+schemeNo+"  and EXAM_subject_rule_settings.scheme_no= "+schemeNo+"  and student.id=" +studentId+
		
		" group by student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id ";

		return query;
	}
	/**
	 * @param ugMarksCardData
	 * @return
	 */
	public MarksCardTO getMarksCardForUg(List<Object[]> ugMarksCardData,int schemeNo,int sid,Map<Integer,byte[]> studentPhotos,HttpServletRequest request,Map<Integer,String> revaluationSubjects) throws Exception {
		//new logic is implementing down
		String certificateCourseQuery="select s.subject.id,s.isOptional from StudentCertificateCourse s where s.isCancelled=0 " +
		" and  s.student.id=" +sid+
		" and s.schemeNo="+schemeNo;
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		List certificateList=txn.getDataForQuery(certificateCourseQuery);
		List<Integer> subjectIdList = new ArrayList<Integer>();
		Map<Integer,Boolean> certificateMap=getCertificateSubjectMap(certificateList);
		//old code 
		MarksCardTO to=null;
		Map<String,List<SubjectTO>> finalsubMap=new LinkedHashMap<String, List<SubjectTO>>();
		Map<String,Map<Integer,SubjectTO>> subMap=new LinkedHashMap<String, Map<Integer,SubjectTO>>();
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		if(ugMarksCardData!=null && !ugMarksCardData.isEmpty()){
			Iterator<Object[]> itr=ugMarksCardData.iterator();
			int cid=0;
			int examId=0;
			int studentId=0;
			int totalMarksAwarded=0;
			int totalMaxMarks=0;
			int totalCredits=0;
			double tcForCal=0;
			double gpForCal=0;
			double totalgradepoints=0;
			double totcredits=0;
			String resultClass="Pass";
			boolean isFailed=false;
			boolean isWithHeld=false;
			boolean isReappear=false;
			int academicYear=0;
			int appliedYear=0;
			// Getting Grade for Fail from database
			String gradeForFail="";
			List<String> gradeList=transaction.getStudentHallTicket("SELECT EXAM_exam_settings.grade_for_fail FROM EXAM_exam_settings EXAM_exam_settings");
			if(gradeList!=null && !gradeList.isEmpty()){
				gradeForFail=gradeList.get(0);
			}
			int star=0;
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				//				if(obj[3]!=null && obj[3].toString().equalsIgnoreCase("2962")){// remove this
				String ciaMax="";
				boolean isDisplay=true;
				boolean isAdd=true;
				boolean isTheoryStar=false;
				boolean isPracticalStar=false;
				boolean showSubType=true;
				boolean showMaxMarks=true;
				boolean showMinMarks=true;
				boolean showAttMarks=true;
				boolean displaySubject=true;
				boolean certificateCourse=false;
				boolean isOptional=false;
				boolean dontConsiderFail=false;
				int ciaMarkAwarded = 0;
				int eseMarks = 0;
				
				if(obj[63]!=null)
					academicYear = Integer.parseInt(obj[63].toString());
				if(obj[64]!=null)
					appliedYear = Integer.parseInt(obj[64].toString());
				if(obj[3]!=null)
					subjectIdList.add(Integer.parseInt(obj[3].toString()));
				if(obj[51]!=null && obj[51].toString().equalsIgnoreCase("1")){
					certificateCourse=true;
					if(certificateMap.containsKey(Integer.parseInt(obj[3].toString()))){
						isOptional=certificateMap.get(Integer.parseInt(obj[3].toString()));
					}else if(obj[10].toString() != null && Integer.parseInt(obj[10].toString()) !=154){
						isOptional=true;
					}
				}
				if(obj[52]!=null && obj[52].toString().equalsIgnoreCase("1")){
					dontConsiderFail=true;
				}

				// Calculating Grade For a Subject according to type
				String gradeForSubject="";

				String starQuery="SELECT EXAM_marks_entry_details.is_retest,  " +
				" EXAM_marks_entry.exam_id, " +
				" EXAM_marks_entry.student_id," +
				" EXAM_marks_entry_details.subject_id," +
				" if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.is_retest,0) as thRetest," +
				" if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.is_retest,0) as praRetest" +
				" FROM    (   EXAM_marks_entry EXAM_marks_entry" +
				" INNER JOIN  EXAM_exam_internal_exam_details EXAM_exam_internal_exam_details " +
				" ON (EXAM_marks_entry.exam_id = EXAM_exam_internal_exam_details.internal_exam_name_id))  INNER JOIN" +
				" EXAM_marks_entry_details EXAM_marks_entry_details ON (EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id)" +
				" where EXAM_exam_internal_exam_details.exam_id = " +obj[0].toString()+
				" and EXAM_marks_entry.student_id = " +obj[1].toString()+
				" and EXAM_marks_entry_details.subject_id = " +obj[3].toString()+
				" and is_retest = 1";
				List<Object[]> slist=transaction.getStudentHallTicket(starQuery);
				if(slist!=null && !slist.isEmpty()){
					Iterator<Object[]> sItr=slist.iterator();
					while (sItr.hasNext()) {
						Object[] s = (Object[]) sItr.next();
						if(s[4]!=null){
							if(!s[4].toString().isEmpty() && Integer.parseInt(s[4].toString())==1){
								star=star+1;
								isTheoryStar=true;
							}
						}
						if(s[5]!=null){
							if(!s[5].toString().isEmpty() && Integer.parseInt(s[5].toString())==1){
								star=star+1;
								isPracticalStar=true;
							}
						}
					}
				}
				if(obj[49]!=null){
					if(Integer.parseInt(obj[49].toString())==1){
						isAdd=false;
					}
				}
				if(obj[46]!=null){
					if(Integer.parseInt(obj[46].toString())==1){
						isDisplay=false;
					}
				}
				if(obj[44]!=null){
					if(Integer.parseInt(obj[44].toString())==1){
						showMaxMarks=false;
					}
				}
				if(obj[45]!=null){
					if(Integer.parseInt(obj[45].toString())==1){
						showMinMarks=false;
					}
				}
				if(obj[48]!=null){
					if(Integer.parseInt(obj[48].toString())==1){
						showSubType=false;
					}
				}
				if(obj[50]!=null){
					if(Integer.parseInt(obj[50].toString())==1){
						showAttMarks=false;
					}
				}
				if(obj[33]!=null){
					if(!obj[33].toString().equalsIgnoreCase("Practical")){
						if(obj[25]!=null && !StringUtils.isEmpty(obj[25].toString()) && CommonUtil.isValidDecimal(obj[25].toString()) && Double.parseDouble(obj[25].toString())>=5 ){
							if(obj[34]!=null && obj[7]!=null){
								//ciaMax=String.valueOf((int)(Double.parseDouble(obj[25].toString())-Double.parseDouble(obj[34].toString())));
								ciaMax=String.valueOf((int)(Double.parseDouble(obj[25].toString())));
							}else{
								ciaMax=obj[25].toString();
							}
						}
					}else{
						if(obj[24]!=null && !StringUtils.isEmpty(obj[24].toString()) && CommonUtil.isValidDecimal(obj[24].toString()) && Double.parseDouble(obj[24].toString())>=5 ){
							if(obj[9]!=null && obj[34]!=null){
								//ciaMax=String.valueOf((int)(Double.parseDouble(obj[24].toString())-Double.parseDouble(obj[34].toString())));
								ciaMax=String.valueOf((int)(Double.parseDouble(obj[24].toString())));
							}else{
								ciaMax=obj[24].toString();
							}
						}
					}
				}

				if(displaySubject && obj[20]!=null){// remove if it not required
					if(to==null){
						to=new MarksCardTO();
						if(obj[0]!=null){
							String monthQuery="select cast(month as unsigned) as month,year from EXAM_definition where del_is_active = 1 and id="+obj[0].toString();
							List<Object[]> monthList=transaction.getStudentHallTicket(monthQuery);
							if(monthList!=null){
								Iterator<Object[]> mItr=monthList.iterator();
								if (mItr.hasNext()) {
									Object[] m = (Object[]) mItr.next();
									String month="";
									String year="";
									if(m[0]!=null){
										month=monthMap.get(m[0].toString());
									}
									if(m[1]!=null){
										year=m[1].toString();
									}
									to.setMonthYear(month+" "+year);
									//added by manu,for change the signature till june 2013
									if(m[0]!=null){
										to.setMonthCheck(Integer.parseInt(m[0].toString()));
									}
									if(m[1]!=null){
										to.setYearCheck(Integer.parseInt(m[1].toString()));
									}
								}
							}
						}
						if(obj[0]!=null){
							examId=Integer.parseInt(obj[0].toString());
						}

						if(obj[1]!=null){
							studentId=Integer.parseInt(obj[1].toString());
						}
						if(obj[10]!=null){
							cid=Integer.parseInt(obj[10].toString());
							to.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(cid,"Course",true,"name"));
							//						String query="select c.program.name,c.name,c.program.code from Course c where c.isActive=1 and c.id="+cid;
							//						List<Object[]> clist=transaction.getDataByHql(query);
							//						if(clist!=null && !clist.isEmpty()){
							//							Iterator<Object[]> citr=clist.iterator();
							//							while (citr.hasNext()) {
							//								Object[] c = (Object[]) citr.next();
							//								if(c[1]!=null){
							//									String cname=c[1].toString();
							//									if(cname.contains("Honors")){
							//										to.setCourseName(/*c[0].toString()+"        "+*/c[1].toString());
							//									}else if(c[2]!=null && c[2].toString().equalsIgnoreCase("B.Tech")){
							//										to.setCourseName(c[2].toString()+" in "+c[1].toString());
							//									}else if(c[0]!=null && !c[0].toString().equalsIgnoreCase("BBM_BBA") && !c[0].toString().equalsIgnoreCase("LLB")){
							//										to.setCourseName(c[0].toString());
							//									}else{
							//										to.setCourseName(c[1].toString());
							//									}
							//								}
							//							}
							//						}
						}

						if(obj[17]!=null){
							to.setStudentName(obj[17].toString());
						}
						if(obj[18]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[18].toString());
						}
						if(obj[19]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[19].toString());
						}
						if(obj[32]!=null){
							to.setRegNo(obj[32].toString());
						}
						if(obj[35]!=null){
							to.setSemNo(semMap.get(obj[35].toString()));
							to.setSemString(semesterMap.get(obj[35].toString()));
						}
						to.setUgorpg("DEGREE");
						if(obj[36]!=null){
							to.setDate(CommonUtil.ConvertStringToDateFormat(obj[36].toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(obj[47]!=null){
							to.setSemType(obj[47].toString());
						}
						if(obj[20]!=null){
							Map<Integer,SubjectTO> subList=null;
							if(certificateCourse && !isOptional){
								if(subMap.containsKey(NON_CORE_ELECTIVE))
									subList=subMap.remove(NON_CORE_ELECTIVE);
								else
									subList=new HashMap<Integer,SubjectTO>();
							}else if(subMap.containsKey(obj[20].toString())){
								subList=subMap.remove(obj[20].toString());
							}else{
								subList=new HashMap<Integer,SubjectTO>();
							}



							SubjectTO subto=null;
							if(subList.containsKey(Integer.parseInt(obj[3].toString()))){
								subto=subList.remove(Integer.parseInt(obj[3].toString()));
							}else{
								subto=new SubjectTO();
							}

							subto.setId(Integer.parseInt(obj[3].toString()));


							subto.setDisplaySubject(displaySubject);
							if(obj[4]!=null){
								subto.setCode(obj[4].toString());
							}
							if(obj[5]!=null){
								subto.setName(obj[5].toString());
							}
							if(obj[23]!=null){
								subto.setSubOrder(Integer.parseInt(obj[23].toString()));
							}



							if(obj[33]!=null){
								if(obj[33].toString().equalsIgnoreCase("Theory")){
									if(showSubType)
										subto.setType("Theory");

									subto.setGrade(gradeForSubject);

									subto.setTheory(true);
									double ciaMarksAwarded=0;
									if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
										ciaMarksAwarded=ciaMarksAwarded+Math.round(Double.parseDouble(obj[6].toString()));
										/*if(obj[7]!=null)
											ciaMarksAwarded= CommonUtil.Round(ciaMarksAwarded+Double.parseDouble(obj[7].toString()),2);*/
										if(isDisplay){
											if(ciaMarksAwarded>=10){
												if(!isTheoryStar)
													subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
											}else{
												if(!isTheoryStar)
													subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
											}
										}else{
											subto.setCiaMarksAwarded("-");
										}
									}else{
										if(isDisplay){
											subto.setCiaMarksAwarded(String.valueOf(Math.round(Double.parseDouble(obj[6].toString()))));
										}else{
											subto.setCiaMarksAwarded("-");
										}
									}
									if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
										if(showAttMarks)
											subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
										else
											subto.setAttMarksAwarded("-");
									}else{
										if(showAttMarks && obj[7]!=null)
											subto.setAttMarksAwarded(String.valueOf(obj[7]));
										else
											subto.setAttMarksAwarded("-");
									}
									if(obj[34]!=null){
										if(showAttMarks  && obj[7]!=null)
											subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
										else
											subto.setAttMaxMarks("-");
									}
									if(obj[11]!=null){
										if(showMinMarks)
											subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
										else
											subto.setEseMinMarks("-");
									}
									if(obj[12]!=null){
										if(showMaxMarks)
											subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
										else
											subto.setEseMaxMarks("-");
									}
									if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
										int m=(int)Math.ceil(Double.parseDouble(obj[15].toString()));
										if(isDisplay && m>0)
											subto.setEseMarksAwarded(String.valueOf(Double.parseDouble(obj[15].toString())));
										else
											subto.setEseMarksAwarded("-");
									}else{
										if(isDisplay)
											subto.setEseMarksAwarded(String.valueOf(obj[15]));
										else
											subto.setEseMarksAwarded("-");
									}
									if(isDisplay){
										if(!ciaMax.isEmpty()){
											subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
										}else{
											subto.setCiaMaxMarks("-");
										}
									}else{
										subto.setCiaMaxMarks("-");
									}
									if(obj[26]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
										if(showMaxMarks)
											subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
										else
											subto.setTotalMaxMarks("-");
									}
									if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[28].toString()));
										if(isDisplay)
											subto.setTotalMarksAwarded(String.valueOf((int)Math.ceil(Double.parseDouble(obj[28].toString()))));
										else
											subto.setTotalMarksAwarded("-");
									}else{
										if(isDisplay)
											subto.setTotalMarksAwarded(obj[28].toString());
										else
											subto.setTotalMarksAwarded("-");
									}
									// for external only subjects
									if(obj[65]!=null && obj[65].toString().equalsIgnoreCase("external")){
										if(obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
											if(((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
												if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setGrade(obj[40].toString());
													if(obj[41]!=null){
														subto.setGradePoints(obj[40].toString());
													}
													if(obj[42]!=null){
														BigDecimal pnt=	new BigDecimal(obj[42].toString());
														int point=pnt.intValue();
														subto.setTheoryGradePnt(String.valueOf(point));
														if(obj[31]!=null){
															BigDecimal ct=new BigDecimal(obj[31].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;

														}
													}

												}
												else{
													subto.setResultClass("FAIL");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
													isReappear=true;
												}
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										} 
										else if(obj[15].toString().equalsIgnoreCase("WTH")){
											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									}
									// for internal only subjects
									else if(obj[65]!=null && obj[65].toString().equalsIgnoreCase("internal")){

										if(obj[6]!=null && obj[53]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH")){
											if((ciaMarksAwarded>=(new BigDecimal(obj[53].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[53].toString())).intValue())){
												if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setGrade(obj[40].toString());
													if(obj[41]!=null){
														subto.setGradePoints(obj[40].toString());
													}
													if(obj[42]!=null){
														BigDecimal pnt=	new BigDecimal(obj[42].toString());
														int point=pnt.intValue();
														subto.setTheoryGradePnt(String.valueOf(point));
														if(obj[31]!=null){
															BigDecimal ct=new BigDecimal(obj[31].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;

														}
													}

												}
												else{
													subto.setResultClass("FAIL");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
													isReappear=true;
												}
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										} 
										else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									
									}
									else{

										if(obj[6]!=null && obj[53]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
											if((ciaMarksAwarded>=(new BigDecimal(obj[53].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[53].toString())).intValue()) &&
													((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
												if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setGrade(obj[40].toString());
													if(obj[41]!=null){
														subto.setGradePoints(obj[40].toString());
													}
													if(obj[42]!=null){
														BigDecimal pnt=	new BigDecimal(obj[42].toString());
														int point=pnt.intValue();
														subto.setTheoryGradePnt(String.valueOf(point));
														if(obj[31]!=null){
															BigDecimal ct=new BigDecimal(obj[31].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;

														}
													}

												}
												else{
													subto.setResultClass("FAIL");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
													isReappear=true;
												}
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										} 
										else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									
									}
	
									if(obj[31]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
										if(subto.getGrade()!=null){
											if(obj[11]!=null && obj[15]!=null){
												if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
													if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
													{
														if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
															totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
														}
													}
												}
											}else{
												if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H")){
													totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
												}
											}
											tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
											if(obj[42]!=null){
												double min=0;
												double stu=0;
												if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
													min=0;
												if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
													stu=0;
												double d=0;
												if(!CommonUtil.isValidDecimal(obj[15].toString())){
													d=0;
												}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
													d=0;
												}else{
													d=Double.parseDouble((obj[42].toString()))*Double.parseDouble(obj[31].toString());
												}
												gpForCal=gpForCal+d;
											}
										}
									}

									if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
										subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
										subto.setRevaluationReq(false);
									}else{
										if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
											subto.setRevaluationReq(true);
									}
								}
								if(obj[33].toString().equalsIgnoreCase("Practical")){
									subto.setPractical(true);
									if(showSubType)
										subto.setSubjectType("Practical");

									subto.setPracticalGrade(gradeForSubject);

									double ciaMarksAwarded=0;
									if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
										ciaMarksAwarded=ciaMarksAwarded+Math.round(Double.parseDouble(obj[8].toString()));
										if(isDisplay){
											if(ciaMarksAwarded>=10){
												if(!isPracticalStar)
													subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
											}else{
												if(!isPracticalStar)
													subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
											}
										}else{
											subto.setPracticalCiaMarksAwarded("-");
										}
									}else{
										if(isDisplay){
											subto.setPracticalCiaMarksAwarded(String.valueOf(Math.round(Double.parseDouble(obj[8].toString()))));
										}else{
											subto.setPracticalCiaMarksAwarded("-");
										}
									}

									if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
										if(showAttMarks)
											subto.setPracticalAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[9].toString()))));
										else
											subto.setPracticalAttMarksAwarded("-");
									}else{
										if(showAttMarks && obj[9]!=null)
											subto.setPracticalAttMarksAwarded(obj[9].toString());
										else
											subto.setPracticalAttMarksAwarded("-");
									}
									if(obj[34]!=null){
										if(showAttMarks && obj[9]!=null)
											subto.setPracticalAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
										else
											subto.setPracticalAttMaxMarks("-");
									}
									if(obj[13]!=null){
										if(showMinMarks)
											subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
										else
											subto.setPracticalEseMinMarks("-");
									}
									if(obj[14]!=null){
										if(showMaxMarks)
											subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
										else
											subto.setPracticalEseMaxMarks("-");
									}
									if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
										int  m=(int)Math.ceil(Double.parseDouble(obj[16].toString()));
										if(isDisplay && m>0)
											subto.setPracticalEseMarksAwarded(String.valueOf(Double.parseDouble(obj[16].toString())));
										else
											subto.setPracticalEseMarksAwarded("-");
									}else{
										if(isDisplay)
											subto.setPracticalEseMarksAwarded(obj[16].toString());
										else
											subto.setPracticalEseMarksAwarded("-");	
									}
									if(isDisplay){
										if(!ciaMax.isEmpty()){
											subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
										}else{
											subto.setPracticalCiaMaxMarks("-");
										}
									}else{
										subto.setPracticalCiaMaxMarks("-");
									}
									if(obj[27]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
										if(showMaxMarks)
											subto.setPracticalTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
										else
											subto.setPracticalTotalMaxMarks("-");
									}
									if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[29].toString()));
										if(isDisplay)
											subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.ceil(Double.parseDouble(obj[29].toString()))));
										else
											subto.setPracticalTotalMarksAwarded("-");
									}else{
										if(isDisplay)
											subto.setPracticalTotalMarksAwarded(obj[29].toString());
										else
											subto.setPracticalTotalMarksAwarded("-");
									}
									// for external only
									if(obj[65]!=null && obj[65].toString().equalsIgnoreCase("external")){
										if( obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
											if(((new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue())){
												if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setPracticalGrade(obj[41].toString());
													if(obj[43]!=null){
														subto.setGradePoints(obj[43].toString());
													}

													if(obj[43]!=null){
														BigDecimal pnt=	new BigDecimal(obj[43].toString());
														int point=pnt.intValue();
														subto.setPracticalGradePnt(String.valueOf(point));
														if(obj[30]!=null){
															BigDecimal ct=new BigDecimal(obj[30].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;
														}
													}
												}
												else{
													subto.setResultClass("FAIL");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
												}
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										}

										else if(obj[16].toString().equalsIgnoreCase("WTH")){

											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
										}
									}
									else if(obj[65]!=null && obj[65].toString().equalsIgnoreCase("internal")){

										if(obj[8]!=null && obj[54]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null ){
											if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[54].toString())).intValue())){
												if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setPracticalGrade(obj[41].toString());
													if(obj[43]!=null){
														subto.setGradePoints(obj[43].toString());
													}

													if(obj[43]!=null){
														BigDecimal pnt=	new BigDecimal(obj[43].toString());
														int point=pnt.intValue();
														subto.setPracticalGradePnt(String.valueOf(point));
														if(obj[30]!=null){
															BigDecimal ct=new BigDecimal(obj[30].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;
														}
													}
												}
												else{
													subto.setResultClass("FAIL");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
												}
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										}

										else if(obj[8].toString().equalsIgnoreCase("WTH")){

											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
										}
									
										
									}
									else{

										if(obj[8]!=null && obj[54]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
											if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[54].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue()) ||
													((new BigDecimal(obj[8].toString())).intValue()==(new BigDecimal(obj[54].toString())).intValue() || (new BigDecimal(obj[16].toString())).intValue()==(new BigDecimal(obj[13].toString())).intValue())){
												if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setPracticalGrade(obj[41].toString());
													if(obj[43]!=null){
														subto.setGradePoints(obj[43].toString());
													}

													if(obj[43]!=null){
														BigDecimal pnt=	new BigDecimal(obj[43].toString());
														int point=pnt.intValue();
														subto.setPracticalGradePnt(String.valueOf(point));
														if(obj[30]!=null){
															BigDecimal ct=new BigDecimal(obj[30].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;
														}
													}
												}
												else{
													subto.setResultClass("FAIL");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
												}
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										}

										else if(obj[16].toString().equalsIgnoreCase("WTH") || obj[8].toString().equalsIgnoreCase("WTH")){

											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
										}
									
										
									}
									if(obj[30]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
										if(subto.getPracticalGrade()!=null){
											if(obj[13]!=null && obj[16]!=null){
												if(CommonUtil.isValidDecimal(obj[13].toString()) && CommonUtil.isValidDecimal(obj[16].toString())){
													/*if(Double.parseDouble(obj[13].toString())<=Double.parseDouble(obj[16].toString()) && !subto.getPracticalGrade().equalsIgnoreCase("F"))
												{
													if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H") ){
													totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
													}
												}*/
												}
											}else{
												/*if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H")){
												totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
											}*/
											}
											tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
											if(obj[43]!=null){
												double min=0;
												double stu=0;
												if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
													min = Double.parseDouble(obj[13].toString());
												if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
													stu = Double.parseDouble(obj[16].toString());

												double d=0;
												if(!CommonUtil.isValidDecimal(obj[16].toString())){
													d=0;
												}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
													d=0;
												}else{
													d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
												}
												gpForCal=gpForCal+d;
											}
										}
									}
								}
								
								// added by Ashwini
								if(obj[33].toString().equalsIgnoreCase("Both")){
									if(showSubType) {
										subto.setType("Theory");
									}
									subto.setGrade(gradeForSubject);
									subto.setTheory(true);
									double ciaMarksAwarded=0;
									// both theory cia and practical cia marks awarded
									if(obj[56]!=null && CommonUtil.isValidDecimal(obj[56].toString())) {
										ciaMarksAwarded=ciaMarksAwarded+Math.round(Double.parseDouble(obj[56].toString()));
										if(isDisplay) {
											if(ciaMarksAwarded>=10) {
												if(!isTheoryStar) {
													subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												}
												else {
													subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
												}
											} else{
												if(!isTheoryStar)
													subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
											}
										} else {
											subto.setCiaMarksAwarded("-");
										}
									} else{
										if(isDisplay) {
											subto.setCiaMarksAwarded(String.valueOf(Math.round(Double.parseDouble(obj[56].toString()))));
										}else {
											subto.setCiaMarksAwarded("-");
										}
									}
									if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())) {
										if(showAttMarks) {
											subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
										}
										else {
											subto.setAttMarksAwarded("-");
										}
									} else {
										if(showAttMarks && obj[7]!=null) {
											subto.setAttMarksAwarded(String.valueOf(obj[7]));
										}
										else {
											subto.setAttMarksAwarded("-");
										}
									}
									if(obj[34]!=null) {
										if(showAttMarks  && obj[7]!=null) {
											subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
										}
										else {
											subto.setAttMaxMarks("-");
										}
									}
									if(obj[11]!=null){
										if(showMinMarks)
											subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
										else
											subto.setEseMinMarks("-");
									}
									if(obj[61]!=null) {
										if(showMaxMarks)
											subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[61].toString()))));
										else
											subto.setEseMaxMarks("-");
									}
									// both theory ese and practical ese marks awarded
									double eseMarksawarded=0;
									if(obj[57]!=null && CommonUtil.isValidDecimal(obj[57].toString())){
										int m=(int)Math.ceil(Double.parseDouble(obj[57].toString()));
										eseMarksawarded=(int)Math.ceil(Double.parseDouble(obj[57].toString()));
										if(isDisplay) {
											if(m>=10)
												subto.setEseMarksAwarded(String.valueOf(Double.parseDouble(obj[57].toString())));
											else
												subto.setEseMarksAwarded("0"+String.valueOf(Double.parseDouble(obj[57].toString())));
										}
										else
											subto.setEseMarksAwarded("-");
									}else{
										if(isDisplay)
											subto.setEseMarksAwarded(String.valueOf(obj[57]));
										else
											subto.setEseMarksAwarded("-");
									}
									if(isDisplay){
										if(obj[60]!=null){
											subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[60].toString()))));
										}else{
											subto.setCiaMaxMarks("-");
										}
									}else{
										subto.setCiaMaxMarks("-");
									}
									// subject final maximum marks
									if(obj[58]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[58].toString()));
										if(showMaxMarks)
											subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[58].toString()))));
										else
											subto.setTotalMaxMarks("-");
									}
									// subject final marks awarded including both theory and practical marks awarded
									int finalMarksawarded=0;
									if(obj[55]!=null && CommonUtil.isValidDecimal(obj[55].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[55].toString()));
										finalMarksawarded=(int)Math.ceil(Double.parseDouble(obj[55].toString()));
										if(isDisplay)
											subto.setTotalMarksAwarded(String.valueOf((int)Math.ceil(Double.parseDouble(obj[55].toString()))));
										else
											subto.setTotalMarksAwarded("-");
									}else{
										if(isDisplay)
											subto.setTotalMarksAwarded(obj[55].toString());
										else
											subto.setTotalMarksAwarded("-");
									}
							
									if(obj[6]!=null && 
									   obj[53]!=null && 
									   !obj[6].toString().equalsIgnoreCase("ab") && 
									   !obj[6].toString().equalsIgnoreCase("WTH") && 
									   obj[15]!=null && obj[11]!=null && 
									   !obj[15].toString().equalsIgnoreCase("ab")&& 
									   !obj[15].toString().equalsIgnoreCase("WTH")) {
										
										if((eseMarksawarded>=(new BigDecimal(obj[62].toString())).intValue() ) && (new BigDecimal(obj[6].toString())).intValue()>=(new BigDecimal(obj[53].toString())).intValue()){
											if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setGrade(obj[40].toString());
												if(obj[41]!=null){
													subto.setGradePoints(obj[42].toString());
												}
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setTheoryGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														if(obj[30]!=null){
															BigDecimal practCt=new BigDecimal(obj[30].toString());
															int practCredit=practCt.intValue();
															credit += practCredit;
														}
															
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

												/*if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
													}
												}*/
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									} 
									else if(obj[15].toString().equalsIgnoreCase("WTH")|| obj[6].toString().equalsIgnoreCase("WTH")){
										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}else{
										subto.setResultClass("FAIL");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isReappear=true;
									}
									if(obj[31]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
										if(subto.getGrade()!=null){
											if(obj[11]!=null && obj[15]!=null){
												if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
													if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
													{
														if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
															totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
														}
													}
												}
											}else{
												if(!subto.getGrade().equalsIgnoreCase("F") ){
													totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
												}
											}
											tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
											if(obj[42]!=null){
												double min=0;
												double stu=0;
												if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
													min=0;
												if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
													stu=0;
												double d=0;
												if(!CommonUtil.isValidDecimal(obj[15].toString())){
													d=0;
												}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
													d=0;
												}else{
													d=Double.parseDouble(obj[42].toString())*Double.parseDouble((obj[31].toString()));
												}
												gpForCal=gpForCal+d;
											}
										}
									}
									if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
										subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
										subto.setRevaluationReq(false);
									}else{
										if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
											subto.setRevaluationReq(true);
									}
								
									
									
								}
								
								
							}

							subList.put(Integer.parseInt(obj[3].toString()),subto);
							if(certificateCourse && !isOptional){
								subMap.put(NON_CORE_ELECTIVE, subList);
							}else{
								subMap.put(obj[20].toString(), subList);
							}
						}
					}else{
						Map<Integer,SubjectTO> subList=null;
						if(certificateCourse && !isOptional){
							if(subMap.containsKey(NON_CORE_ELECTIVE))
								subList=subMap.remove(NON_CORE_ELECTIVE);
							else
								subList=new HashMap<Integer,SubjectTO>();
						}else if(subMap.containsKey(obj[20].toString())){
							subList=subMap.remove(obj[20].toString());
						}else{
							subList=new HashMap<Integer,SubjectTO>();
						}
						SubjectTO subto=null;
						if(subList.containsKey(Integer.parseInt(obj[3].toString()))){
							subto=subList.remove(Integer.parseInt(obj[3].toString()));
						}else{
							subto=new SubjectTO();
						}
						
						subto.setId(Integer.parseInt(obj[3].toString()));
						subto.setDisplaySubject(displaySubject);
						if(obj[4]!=null){
							subto.setCode(obj[4].toString());
						}
						if(obj[5]!=null){
							subto.setName(obj[5].toString());
						}
						if(obj[23]!=null){
							subto.setSubOrder(Integer.parseInt(obj[23].toString()));
						}
						if(obj[40]!=null){
							subto.setGrade(obj[40].toString());
						}
						if(obj[41]!=null){
							subto.setGradePoints(obj[41].toString());
						}
						if(obj[33]!=null){
							if(obj[33].toString().equalsIgnoreCase("Theory")){
								if(showSubType)
									subto.setType("Theory");

								subto.setGrade(gradeForSubject);

								subto.setTheory(true);
								double ciaMarksAwarded=0;
								if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
									ciaMarksAwarded=ciaMarksAwarded+Math.round(Double.parseDouble(obj[6].toString()));
									/*if(obj[7]!=null)
										ciaMarksAwarded= CommonUtil.Round(ciaMarksAwarded+Double.parseDouble(obj[7].toString()),2);*/
									if(isDisplay){
										if(ciaMarksAwarded>=10){
											if(!isTheoryStar)
												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
										}else{
											if(!isTheoryStar)
												subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
										}
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}else{
									if(isDisplay){
										subto.setCiaMarksAwarded(String.valueOf(Math.round(Double.parseDouble(obj[6].toString()))));
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}
								if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
									if(showAttMarks)
										subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
									else
										subto.setAttMarksAwarded("-");
								}else{
									if(showAttMarks && obj[7]!=null)
										subto.setAttMarksAwarded(String.valueOf(obj[7]));
									else
										subto.setAttMarksAwarded("-");
								}
								if(obj[34]!=null){
									if(showAttMarks  && obj[7]!=null)
										subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
									else
										subto.setAttMaxMarks("-");
								}
								if(obj[11]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[12]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									int m=(int)Math.ceil(Double.parseDouble(obj[15].toString()));
									if(isDisplay && m>0)
										subto.setEseMarksAwarded(String.valueOf(Double.parseDouble(obj[15].toString())));
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(String.valueOf(obj[15]));
									else
										subto.setEseMarksAwarded("-");
								}
								if(isDisplay){
									if(!ciaMax.isEmpty()){
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
									}else{
										subto.setCiaMaxMarks("-");
									}
								}else{
									subto.setCiaMaxMarks("-");
								}
								if(obj[26]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[28].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.ceil(Double.parseDouble(obj[28].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[28].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
								// for external only subjects
								if(obj[65]!=null && obj[65].toString().equalsIgnoreCase("external")){
									if(obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
										if(((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
											if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setGrade(obj[40].toString());
												if(obj[41]!=null){
													subto.setGradePoints(obj[40].toString());
												}
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setTheoryGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									} 
									else if(obj[15].toString().equalsIgnoreCase("WTH")){
										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("FAIL");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isReappear=true;
									}
								}
								// for internal only subjects
								else if(obj[65]!=null && obj[65].toString().equalsIgnoreCase("internal")){

									if(obj[6]!=null && obj[53]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH")){
										if((ciaMarksAwarded>=(new BigDecimal(obj[53].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[53].toString())).intValue())){
											if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setGrade(obj[40].toString());
												if(obj[41]!=null){
													subto.setGradePoints(obj[40].toString());
												}
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setTheoryGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									} 
									else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("FAIL");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isReappear=true;
									}
								
								}
								else{

									if(obj[6]!=null && obj[53]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
										if((ciaMarksAwarded>=(new BigDecimal(obj[53].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[53].toString())).intValue()) &&
												((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
											if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setGrade(obj[40].toString());
												if(obj[41]!=null){
													subto.setGradePoints(obj[40].toString());
												}
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setTheoryGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									} 
									else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("FAIL");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isReappear=true;
									}
								
								}

								if(obj[31]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
									if(subto.getGrade()!=null){
										if(obj[11]!=null && obj[15]!=null){
											if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
												if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
												{
													if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
														totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
													}
												}
											}
										}else{
											if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H")){
												totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
										if(obj[42]!=null){
											double min=0;
											double stu=0;
											if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
												min=0;
											if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
												stu=0;
											double d=0;
											if(!CommonUtil.isValidDecimal(obj[15].toString())){
												d=0;
											}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[42].toString()))*Double.parseDouble(obj[31].toString());
											}
											gpForCal=gpForCal+d;
										}
									}
								}

								if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
									subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
									subto.setRevaluationReq(false);
								}else{
									if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
										subto.setRevaluationReq(true);
								}
							}
							if(obj[33].toString().equalsIgnoreCase("Practical")){
								subto.setPractical(true);
								if(showSubType)
									subto.setSubjectType("Practical");

								subto.setPracticalGrade(gradeForSubject);

								double ciaMarksAwarded=0;
								if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
									ciaMarksAwarded=ciaMarksAwarded+Math.round(Double.parseDouble(obj[8].toString()));
									if(isDisplay){
										if(ciaMarksAwarded>=10){
											if(!isPracticalStar)
												subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
										}else{
											if(!isPracticalStar)
												subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
										}
									}else{
										subto.setPracticalCiaMarksAwarded("-");
									}
								}else{
									if(isDisplay){
										subto.setPracticalCiaMarksAwarded(String.valueOf(Math.round(Double.parseDouble(obj[8].toString()))));
									}else{
										subto.setPracticalCiaMarksAwarded("-");
									}
								}

								if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
									if(showAttMarks)
										subto.setPracticalAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[9].toString()))));
									else
										subto.setPracticalAttMarksAwarded("-");
								}else{
									if(showAttMarks && obj[9]!=null)
										subto.setPracticalAttMarksAwarded(obj[9].toString());
									else
										subto.setPracticalAttMarksAwarded("-");
								}
								if(obj[34]!=null){
									if(showAttMarks && obj[9]!=null)
										subto.setPracticalAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
									else
										subto.setPracticalAttMaxMarks("-");
								}
								if(obj[13]!=null){
									if(showMinMarks)
										subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
									else
										subto.setPracticalEseMinMarks("-");
								}
								if(obj[14]!=null){
									if(showMaxMarks)
										subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
									else
										subto.setPracticalEseMaxMarks("-");
								}
								if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
									int  m=(int)Math.ceil(Double.parseDouble(obj[16].toString()));
									if(isDisplay && m>0)
										subto.setPracticalEseMarksAwarded(String.valueOf(Double.parseDouble(obj[16].toString())));
									else
										subto.setPracticalEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setPracticalEseMarksAwarded(obj[16].toString());
									else
										subto.setPracticalEseMarksAwarded("-");	
								}
								if(isDisplay){
									if(!ciaMax.isEmpty()){
										subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
									}else{
										subto.setPracticalCiaMaxMarks("-");
									}
								}else{
									subto.setPracticalCiaMaxMarks("-");
								}
								if(obj[27]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
									if(showMaxMarks)
										subto.setPracticalTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
									else
										subto.setPracticalTotalMaxMarks("-");
								}
								if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[29].toString()));
									if(isDisplay)
										subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.ceil(Double.parseDouble(obj[29].toString()))));
									else
										subto.setPracticalTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setPracticalTotalMarksAwarded(obj[29].toString());
									else
										subto.setPracticalTotalMarksAwarded("-");
								}
								// for external only subjects
								if(obj[65]!=null && obj[65].toString().equalsIgnoreCase("external")){
									if( obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
										if(((new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue())){
											if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setPracticalGrade(obj[41].toString());
												if(obj[43]!=null){
													subto.setGradePoints(obj[43].toString());
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[30]!=null){
														BigDecimal ct=new BigDecimal(obj[30].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
													}
												}
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
										}
									}

									else if(obj[16].toString().equalsIgnoreCase("WTH")){

										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("FAIL");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
									}
								}
								// for internal only subjects
								else if(obj[65]!=null && obj[65].toString().equalsIgnoreCase("internal")){

									if(obj[8]!=null && obj[54]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null ){
										if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[54].toString())).intValue())){
											if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setPracticalGrade(obj[41].toString());
												if(obj[43]!=null){
													subto.setGradePoints(obj[43].toString());
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[30]!=null){
														BigDecimal ct=new BigDecimal(obj[30].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
													}
												}
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
										}
									}

									else if(obj[8].toString().equalsIgnoreCase("WTH")){

										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("FAIL");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
									}
								
									
								}
								else{

									if(obj[8]!=null && obj[54]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
										if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[54].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue()) ||
												((new BigDecimal(obj[8].toString())).intValue()==(new BigDecimal(obj[54].toString())).intValue() || (new BigDecimal(obj[16].toString())).intValue()==(new BigDecimal(obj[13].toString())).intValue())){
											if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setPracticalGrade(obj[41].toString());
												if(obj[43]!=null){
													subto.setGradePoints(obj[43].toString());
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[30]!=null){
														BigDecimal ct=new BigDecimal(obj[30].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
													}
												}
											}
											else{
												subto.setResultClass("FAIL");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
										}
									}

									else if(obj[16].toString().equalsIgnoreCase("WTH") || obj[8].toString().equalsIgnoreCase("WTH")){

										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("FAIL");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
									}
								
									
								}
								if(obj[30]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
									if(subto.getPracticalGrade()!=null){
										if(obj[13]!=null && obj[16]!=null){
											if(CommonUtil.isValidDecimal(obj[13].toString()) && CommonUtil.isValidDecimal(obj[16].toString())){
												/*if(Double.parseDouble(obj[13].toString())<=Double.parseDouble(obj[16].toString()) && !subto.getPracticalGrade().equalsIgnoreCase("F"))
											{
												if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H") ){
												totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
												}
											}*/
											}
										}else{
											/*if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H")){
											totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
										}*/
										}
										tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
										if(obj[43]!=null){
											double min=0;
											double stu=0;
											if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
												min = Double.parseDouble(obj[13].toString());
											if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
												stu = Double.parseDouble(obj[16].toString());

											double d=0;
											if(!CommonUtil.isValidDecimal(obj[16].toString())){
												d=0;
											}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											}
											gpForCal=gpForCal+d;
										}
									}
								}
							}
							
							// added by Ashwini
							if(obj[33].toString().equalsIgnoreCase("Both")){
								
								

								if(showSubType)
									subto.setType("Theory");

								subto.setGrade(gradeForSubject);

								subto.setTheory(true);
								double ciaMarksAwarded=0;
								// both theory cia and practical cia marks awarded
								if(obj[56]!=null && CommonUtil.isValidDecimal(obj[56].toString())){
									ciaMarksAwarded=ciaMarksAwarded+Math.round(Double.parseDouble(obj[56].toString()));
									if(isDisplay){
										if(ciaMarksAwarded>=10){
											if(!isTheoryStar)
												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
										}else{
											if(!isTheoryStar)
												subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
										}
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}else{
									if(isDisplay){
										subto.setCiaMarksAwarded(String.valueOf(Math.round(Double.parseDouble(obj[56].toString()))));
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}
								if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
									if(showAttMarks)
										subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
									else
										subto.setAttMarksAwarded("-");
								}else{
									if(showAttMarks && obj[7]!=null)
										subto.setAttMarksAwarded(String.valueOf(obj[7]));
									else
										subto.setAttMarksAwarded("-");
								}
								if(obj[34]!=null){
									if(showAttMarks  && obj[7]!=null)
										subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
									else
										subto.setAttMaxMarks("-");
								}
								if(obj[11]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[61]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[61].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								// both theory ese and practical ese marks awarded
								double eseMarksawarded=0;
								if(obj[57]!=null && CommonUtil.isValidDecimal(obj[57].toString())){
									int m=(int)Math.ceil(Double.parseDouble(obj[57].toString()));
									eseMarksawarded=(int)Math.ceil(Double.parseDouble(obj[57].toString()));
									if(isDisplay){
										if(m>=10)
											subto.setEseMarksAwarded(String.valueOf(Double.parseDouble(obj[57].toString())));
										else
											subto.setEseMarksAwarded("0"+String.valueOf(Double.parseDouble(obj[57].toString())));
									}
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(String.valueOf(obj[57]));
									else
										subto.setEseMarksAwarded("-");
								}
								if(isDisplay){
									if(obj[60]!=null){
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[60].toString()))));
									}else{
										subto.setCiaMaxMarks("-");
									}
								}else{
									subto.setCiaMaxMarks("-");
								}
								// subject final maximum marks
								if(obj[58]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[58].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[58].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								// subject final marks awarded including both theory and practical marks awarded
								int finalMarksawarded=0;
								if(obj[55]!=null && CommonUtil.isValidDecimal(obj[55].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[55].toString()));
									finalMarksawarded=(int)Math.ceil(Double.parseDouble(obj[55].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.ceil(Double.parseDouble(obj[55].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[55].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
						
								if(obj[6]!=null && obj[53]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab")&& !obj[15].toString().equalsIgnoreCase("WTH")){
									if((eseMarksawarded>=(new BigDecimal(obj[62].toString())).intValue() ) && (new BigDecimal(obj[6].toString())).intValue()>=(new BigDecimal(obj[53].toString())).intValue()){
										if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
											subto.setResultClass("PASS");
											subto.setGrade(obj[40].toString());
											if(obj[41]!=null){
												subto.setGradePoints(obj[42].toString());
											}
											if(obj[42]!=null){
												BigDecimal pnt=	new BigDecimal(obj[42].toString());
												int point=pnt.intValue();
												subto.setTheoryGradePnt(String.valueOf(point));
												if(obj[31]!=null){
													BigDecimal ct=new BigDecimal(obj[31].toString());
													int credit=ct.intValue();
													if(obj[30]!=null){
														BigDecimal practCt=new BigDecimal(obj[30].toString());
														int practCredit=practCt.intValue();
														credit += practCredit;
													}
														
													int cp=point*credit;
													subto.setCreditPoint(String.valueOf(cp));
													totalgradepoints=totalgradepoints+cp;
													totcredits=totcredits+credit;

												}
											}

										}
										else{
											subto.setResultClass("FAIL");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									}
									else{
										subto.setResultClass("FAIL");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isReappear=true;
									}
								} 
								else if(obj[15].toString().equalsIgnoreCase("WTH")|| obj[6].toString().equalsIgnoreCase("WTH")){
									subto.setResultClass("WITH HELD");
									subto.setGrade("-");
									subto.setGradePoints("-");
									subto.setCreditPoint("-");
									isFailed=true;
									isWithHeld=true;
								}else{
									subto.setResultClass("FAIL");
									subto.setGrade("-");
									subto.setGradePoints("-");
									subto.setCreditPoint("-");
									isFailed=true;
									isReappear=true;
								}
								if(obj[31]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
									if(subto.getGrade()!=null){
										if(obj[11]!=null && obj[15]!=null){
											if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
												if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
												{
													if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
														totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
													}
												}
											}
										}else{
											if(!subto.getGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
										if(obj[42]!=null){
											double min=0;
											double stu=0;
											if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
												min=0;
											if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
												stu=0;
											double d=0;
											if(!CommonUtil.isValidDecimal(obj[15].toString())){
												d=0;
											}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble(obj[42].toString())*Double.parseDouble((obj[31].toString()));
											}
											gpForCal=gpForCal+d;
										}
									}
								}
								if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
									subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
									subto.setRevaluationReq(false);
								}else{
									if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
										subto.setRevaluationReq(true);
								}
							
								
								
							}
						}
						subList.put(Integer.parseInt(obj[3].toString()),subto);
						boolean isOptionalNew = false;
						if(obj[51]!=null && obj[51].toString().equalsIgnoreCase("1")){
							if(certificateMap.containsKey(Integer.parseInt(obj[3].toString()))){
								isOptionalNew=certificateMap.get(Integer.parseInt(obj[3].toString()));
							}else if(obj[10].toString() != null){
								isOptionalNew=true;
							}
						}
						if(certificateCourse && !isOptional && !isOptionalNew){
							subMap.put(NON_CORE_ELECTIVE, subList);
						}else{
							subMap.put(obj[20].toString(), subList);
						}
					}
				}// remove if it not required
				//			}// remove this
			}
			
			// added by ashwini for subject deff coursewise grade def
			List<ExamSubCoursewiseGradeDefnTO> ExamSubCoursewiseGradeDefnTOList = new ArrayList<ExamSubCoursewiseGradeDefnTO>();
			List<GradePointRangeBO> courseWiseGradeList = new ArrayList<GradePointRangeBO>();
			ExamSubDefinitionCourseWiseHelper helper = new ExamSubDefinitionCourseWiseHelper();
			ExamSubDefinitionCourseWiseImpl impl = new ExamSubDefinitionCourseWiseImpl();
			for (Integer subId : subjectIdList) {
				ExamSubCoursewiseGradeDefnTOList = helper.convertBOToTO_GradeDetails(impl.select_All_grade_detailsForMarksCard(subId,cid,academicYear));
				if(ExamSubCoursewiseGradeDefnTOList.size()>0)
					break;
			}
			if(ExamSubCoursewiseGradeDefnTOList!=null && ExamSubCoursewiseGradeDefnTOList.size()>0)
			to.setExamSubCoursewiseGradeDefnTOList(ExamSubCoursewiseGradeDefnTOList);
			courseWiseGradeList = UpdateExamStudentSGPAImpl.getInstance().getCoursewiseGrades(cid,appliedYear, sid);
			if(courseWiseGradeList!=null && courseWiseGradeList.size()>0)
			to.setCourseWiseGradeList(courseWiseGradeList);
			
			List<SubjectTO> addOnCourse=null;
			List<SubjectTO> nonElective=null;
			boolean isAddonCourse=false;
			boolean isElective=false;
			List<SubjectTO> toList = new ArrayList<SubjectTO>();
			if(!subMap.isEmpty()){
				Iterator it = subMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					Map<Integer,SubjectTO> values=(HashMap<Integer, SubjectTO>)pairs.getValue();
					if(true){
						Iterator it1 = values.entrySet().iterator();
						while(it1.hasNext()){
							Map.Entry pairvalue = (Map.Entry)it1.next();
							toList.add((SubjectTO) pairvalue.getValue());
						}
						
					}
					if(pairs.getKey().toString().equalsIgnoreCase(NON_CORE_ELECTIVE)){
						nonElective = new ArrayList<SubjectTO>(values.values());
						Collections.sort(nonElective,comparator);
						isElective=true;
					}else if(pairs.getKey().toString().equalsIgnoreCase("Add On Course")){
						addOnCourse = new ArrayList<SubjectTO>(values.values());
						Collections.sort(addOnCourse,comparator);
						isAddonCourse=true;
					}else{
						List<SubjectTO> list = new ArrayList<SubjectTO>(values.values());
						Collections.sort(list,comparator);
						finalsubMap.put(pairs.getKey().toString().trim(), list);
					}
				}
			}
			Map<String,List<SubjectTO>> addOnCoursesubMap=new HashMap<String, List<SubjectTO>>();
			if(isAddonCourse){
				addOnCoursesubMap.put("Add On Course", addOnCourse);
			}
			Map<String,List<SubjectTO>> nonCoreElectivesubMap=new HashMap<String, List<SubjectTO>>();
			if(isElective){
				nonCoreElectivesubMap.put(NON_CORE_ELECTIVE,nonElective);
			}
			to.setSubMap(finalsubMap);
			to.setAddOnCoursesubMap(addOnCoursesubMap);
			to.setNonElectivesubMap(nonCoreElectivesubMap);
            to.setSubjectTOList(toList);

			to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
			//			to.setTotalMarksInWord(CommonUtil.numberToWord(totalMarksAwarded));
			to.setTotalMarksInWord(CommonUtil.numberToWord1(totalMarksAwarded));
			DecimalFormat twoDForm = new DecimalFormat("#.##");

			if(isFailed==true){
				to.setTotalGrade("F");
				to.setTotalMarksAwarded(" ");
				to.setTotalCredits(" ");
				to.setTotalGradePoints(" ");
				if(isWithHeld ){
				to.setResultClass("WITH HELD");
				}else{
					to.setResultClass("FAIL");	
				}
				to.setSgpa(" ");
			}
			else{
				if(totalgradepoints>0 && totcredits>0){
					double sgpa=(totalgradepoints/totcredits);
					to.setSgpa(String.valueOf(twoDForm.format(sgpa)));
				}
				int tcredits=(int)totcredits;
				to.setTotalCredits(String.valueOf(tcredits));
				int tcreditPoints=(int)totalgradepoints;
				to.setTotalGradePoints(String.valueOf(tcreditPoints));
				String grade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(cid, to.getSgpa(), 0,sid);
				to.setTotalGrade(grade);
				to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
				if(isWithHeld){
					to.setResultClass("WITHHELD");
				}else if(isReappear){
					to.setResultClass("FAIL");
				}else{
				to.setResultClass("PASS");
				}
			}
			double tgpa=0;
			if(gpForCal>0 && tcForCal>0){
				tgpa=CommonUtil.Round((gpForCal/tcForCal),2);
			}
			to.setGradePoints(String.valueOf(twoDForm.format(tgpa)));


			String countQuery="select count(*) from EXAM_update_exclude_withheld " +
			" where student_id = " +studentId+
			" and exam_id ="+examId;
			int count=0;
			List<BigInteger> countList=transaction.getStudentHallTicket(countQuery);
			if(countList!=null && !countList.isEmpty()){
				BigInteger b=countList.get(0);
				count=b.intValue();
			}
			String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
			double agg=0;
			List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
			if(aggList!=null && !aggList.isEmpty()){
				agg=Double.parseDouble(aggList.get(0).toString());
			}

			if(count<1){
				double r=((double)(totalMarksAwarded)/(double)(totalMaxMarks))*100;
				if(r>=agg){
					String robj = UpdateExamStudentSGPAImpl.getInstance().getResultClass(cid, r, 0,sid);
					if(robj!=null && resultClass.equals("PASS"))
						to.setResult(robj);
					else if(isWithHeld){
						to.setResult("WITHHELD");
					}else{
						to.setResult("FAIL");	
					}

					/*String query="select result_class from EXAM_grade_class_definition where course_id="+cid+" and "+r+" between start_percentage and end_percentage";
					List<String> rList=transaction.getStudentHallTicket(query);
					if(rList!=null && !rList.isEmpty()){
						Iterator<String> rItr=rList.iterator();
						while (rItr.hasNext()) {
							String robj = rItr.next();
							if(robj!=null){
								if(resultClass.equalsIgnoreCase("Pass"))
									to.setResult(robj);
								else
									to.setResult("Failed");
							}
						}
					}*/
				}else{
					to.setResult("FAIL");
				}
			}else{
				to.setResult("WITH HELD");
			}
			if(studentPhotos.containsKey(sid)){
				to.setPhoto(new String(studentPhotos.get(sid)));
				request.getSession().setAttribute(to.getRegNo(), studentPhotos.get(sid));
			}
		}
		return to;
	}
	
	private Map<String, List<SubjectTO>> sortSubMapBasedOnOrder(Map<String, List<SubjectTO>> subMap) throws Exception {
		Map<String, List<SubjectTO>> sortedMap = new TreeMap<String, List<SubjectTO>>();
		return null;
	}
	/**
	 * @param certificateList
	 * @return
	 */
	private Map<Integer, Boolean> getCertificateSubjectMap(List certificateList) {
		Map<Integer,Boolean> map=new HashMap<Integer, Boolean>();
		if(certificateList!=null && !certificateList.isEmpty()){
			Iterator<Object[]> itr=certificateList.iterator();
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				if(objects[0]!=null && objects[1]!=null)
					map.put(Integer.parseInt(objects[0].toString()),(Boolean)objects[1]);
			}
		}
		return map;
	}
	/**
	 * @param pgMarksCardData
	 * @return
	 * @throws Exception
	 */
	public MarksCardTO getMarksCardForPg(List<Object[]> pgMarksCardData,int sid,Map<Integer,byte[]> studentPhotos,HttpServletRequest request,Map<Integer,String> revaluationSubjects,int programTypeId) throws Exception {
		MarksCardTO to=null;
		int cid=0;
		double totalgradepointsForSgpa=0;
		double totcreditsForSgpa=0;
		int totalMarksAwardedForSgpa = 0;
		int totalMaxMarksForSgpa = 0;
		String gradeForSgpa="";
		Map<String,List<SubjectTO>> subMap=new HashMap<String, List<SubjectTO>>();
		List<Integer> subjectIdList = new ArrayList<Integer>();
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int totalMarksAwarded=0;
			int totalMaxMarks=0;
			int totalCredits=0;
			double tcForCal=0;
			double gpForCal=0;
			String resultClass="Pass";
			int studentId=0;
			int examId=0;

			double totalgradepoints=0;
			double totcredits=0;

			boolean isFailed=false;
			boolean isWithHeld=false;
			boolean isReappear=false;
			int academicYear=0;
			int appliedYear=0;
			
			
			
			
			// Getting Grade for Fail from database
			String gradeForFail="";
			List<String> gradeList=transaction.getStudentHallTicket("SELECT EXAM_exam_settings.grade_for_fail FROM EXAM_exam_settings EXAM_exam_settings");
			if(gradeList!=null && !gradeList.isEmpty()){
				gradeForFail=gradeList.get(0);
			}

			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				boolean isDisplay=true;
				boolean isAdd=true;
				boolean showSubType=true;
				boolean showMaxMarks=true;
				boolean showMinMarks=true;
				boolean showAttMarks=true;
				boolean displaySubject=true;
				boolean certificateCourse=false;
				boolean dontConsiderFail=false;
				
				if(obj[59]!=null)
					academicYear = Integer.parseInt(obj[59].toString());
				if(obj[60]!=null)
					appliedYear = Integer.parseInt(obj[60].toString());
				if(obj[3]!=null)
					subjectIdList.add(Integer.parseInt(obj[3].toString()));

				// for sgpa storing

				// theory

				if(obj[54]!=null && (obj[54].toString().trim().equalsIgnoreCase("1") || obj[54].toString().trim().equalsIgnoreCase("true"))){
					certificateCourse=true;
				}
				if(obj[55]!=null && (obj[55].toString().trim().equalsIgnoreCase("1") || obj[55].toString().trim().equalsIgnoreCase("true"))){
					dontConsiderFail=true;
				}
				String grade="";

				if(obj[52]!=null){
					if(obj[52].toString().trim().equals("true")){
						isAdd=false;
					}
				}
				if(obj[46]!=null){
					if(obj[46].toString().trim().equals("true")){
						isDisplay=false;
					}
				}
				if(obj[44]!=null){
					if(obj[44].toString().trim().equals("true")){
						showMaxMarks=false;
					}
				}
				if(obj[45]!=null){
					if(obj[45].toString().trim().equals("true")){
						showMinMarks=false;
					}
				}
				if(obj[48]!=null){
					if(obj[48].toString().trim().equals("true")){
						showSubType=false;
					}
				}
				if(obj[53]!=null){
					if(obj[53].toString().trim().equals("true")){
						showAttMarks=false;
					}
				}
				if(obj[33]!=null){
					if(!obj[33].toString().equalsIgnoreCase("Practical")){
						if(obj[40]!=null){
							double min=0;
							double stu=0;
							if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
								min = Double.parseDouble(obj[11].toString());
							if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
								stu = Double.parseDouble(obj[15].toString());

							if(obj[40].toString().equals("F") || min>stu){
								grade=gradeForFail;
								if(certificateCourse){
									displaySubject=false;
								}if(!dontConsiderFail){
									resultClass="Fail";
								}
							}else{
								grade=obj[40].toString();
							}
						}
					}else{

						if(obj[41]!=null){
							double min=0;
							double stu=0;
							if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
								min = Double.parseDouble(obj[13].toString());
							if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
								stu = Double.parseDouble(obj[16].toString());

							if(obj[41].toString().equals("F") ||  min>stu){
								grade=gradeForFail;
								if(certificateCourse){
									displaySubject=false;
								}
								if(!dontConsiderFail){
									resultClass="Fail";
								}
							}else{
								if(obj[41]!=null)
									grade=obj[41].toString();
							}
						}

					}
				}

				if(displaySubject){// Start of Display Subject
					if(to==null){
						to=new MarksCardTO();

						if(obj[0]!=null){
							examId=Integer.parseInt(obj[0].toString());
							String monthQuery="select cast(month as unsigned) as month,year from EXAM_definition where del_is_active = 1 and id="+obj[0].toString();
							List<Object[]> monthList=transaction.getStudentHallTicket(monthQuery);
							if(monthList!=null){
								Iterator<Object[]> mItr=monthList.iterator();
								if (mItr.hasNext()) {
									Object[] m = (Object[]) mItr.next();
									String month="";
									String year="";
									if(m[0]!=null){
										month=monthMap.get(m[0].toString());
									}
									if(m[1]!=null){
										year=m[1].toString();
									}
									to.setMonthYear(month+" "+year);
									//added by manu,for change the signature till june 2013
									if(m[0]!=null){
										to.setMonthCheck(Integer.parseInt(m[0].toString()));
									}
									if(m[1]!=null){
										to.setYearCheck(Integer.parseInt(m[1].toString()));
									}
								}
							}
						}
						if(obj[1]!=null){
							studentId=Integer.parseInt(obj[1].toString());
						}
						if(obj[10]!=null){
							cid=Integer.parseInt(obj[10].toString());
							to.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(cid,"Course",true,"name"));
						}
						if(obj[17]!=null){
							to.setStudentName(obj[17].toString());
						}
						if(obj[18]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[18].toString());
						}
						if(obj[19]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[19].toString());
						}
						if(obj[32]!=null){
							to.setRegNo(obj[32].toString());
						}
						if(obj[35]!=null){
							to.setSemNo(semMap.get(obj[35].toString()));
						}
						if(obj[36]!=null){
							to.setDate(CommonUtil.ConvertStringToDateFormat(obj[36].toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(obj[47]!=null){
							to.setSemType(obj[47].toString());
						}
						if(obj[35]!=null){
							to.setSemNo(semMap.get(obj[35].toString()));
							to.setSemString(semesterMap.get(obj[35].toString()));
						}
						if(programTypeId==3)
							to.setUgorpg("MPHIL DEGREE");
						else
						to.setUgorpg("POSTGRADUATE DEGREE");
						if(obj[20]!=null){
							List<SubjectTO> subList=null;
							if(subMap.containsKey(obj[20].toString())){
								subList=subMap.remove(obj[20].toString());
							}else{
								subList=new ArrayList<SubjectTO>();
							}

							SubjectTO subto=new SubjectTO();
							subto.setDisplaySubject(displaySubject);
							subto.setId(Integer.parseInt(obj[3].toString()));
							if(obj[4]!=null){
								subto.setCode(obj[4].toString());
							}
							if(obj[5]!=null){
								subto.setName(obj[5].toString());
							}
							if(obj[23]!=null)
								subto.setSubOrder(Integer.parseInt(obj[23].toString()));
							if(obj[33]!=null){
								if(obj[33].toString().equalsIgnoreCase("Theory")){
									if(showSubType)
										subto.setType("Theory");
									else
										subto.setType("");

									subto.setGrade(grade);
									subto.setTheory(true);
									if(obj[31]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
										if(subto.getGrade()!=null){
											if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
										if(obj[42]!=null){
											double min=0;
											double stu=0;
											if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
												min=0;
											if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
												stu=0;
											double d=0;
											if(!CommonUtil.isValidDecimal(obj[15].toString())){
												d=0;
											}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[42].toString()))*Double.parseDouble(obj[31].toString());
											}
											gpForCal=gpForCal+d;
											/*double d=Double.parseDouble((obj[42].toString()))*Double.parseDouble((obj[31].toString()));
										gpForCal=gpForCal+d;*/
										}
									}
									int ciaMarksAwarded=0;
									if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
									}
									/*if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
									}*/
									if(obj[11]!=null){
										if(showMinMarks)
											subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
										else
											subto.setEseMinMarks("");
									}
									if(obj[12]!=null){
										if(showMaxMarks)
											subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
										else
											subto.setEseMaxMarks("");
									}
									//								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									//									int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
									//									if(isDisplay && m>0)
									//										subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
									//									else
									//										subto.setEseMarksAwarded("-");
									//								}else{
									//									if(isDisplay)
									//										subto.setEseMarksAwarded(obj[15].toString());
									//									else
									//										subto.setEseMarksAwarded("-");
									//								}
									if(obj[0]!=null &&  obj[2]!=null && obj[3]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.##");
										String Query="select avg(student_theory_marks) from EXAM_student_final_mark_details where exam_id="+obj[0].toString()+" and class_id="+obj[2].toString()+" and subject_id="+obj[3].toString();
										List avgList=transaction.getStudentHallTicket(Query);
										if(avgList.get(0)!=null){
											double avg=Double.parseDouble(avgList.get(0).toString());
											subto.setAvgmark(twoDForm.format(avg));

										}

									}


									if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
										if(isDisplay && subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equals("-")){
											if(m>=10)
												subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
											else
												subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
										}else
											subto.setEseMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setEseMarksAwarded(obj[15].toString());
										else
											subto.setEseMarksAwarded("");
									}
									if(obj[25]!=null){
										if(showMaxMarks)
											subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[25].toString()))));
										else
											subto.setCiaMaxMarks("");
									}
									if(obj[26]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
										if(showMaxMarks)
											subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
										else
											subto.setTotalMaxMarks("");
									}
									if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
										if(isDisplay){
											if((int)Math.round(Double.parseDouble(obj[28].toString()))>=10)
												subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
											else
												subto.setTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
										}else
											subto.setTotalMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setTotalMarksAwarded(obj[28].toString());
										else
											subto.setTotalMarksAwarded("");
									}
									//								if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
									//									if(isDisplay)
									//										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									//									else
									//										subto.setCiaMarksAwarded("-");
									//								}else{
									//									if(isDisplay){
									//										if(CommonUtil.isValidDecimal(obj[6].toString()))
									//											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									//										else
									//											subto.setCiaMarksAwarded(obj[6].toString());
									//									}else
									//										subto.setCiaMarksAwarded("-");
									//								}
									if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
										if(isDisplay){
											if(ciaMarksAwarded>=10)
												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
										}else
											subto.setCiaMarksAwarded("");
									}else{
										if(isDisplay){
											if(CommonUtil.isValidDecimal(obj[6].toString())){
												if(ciaMarksAwarded>=10)
													subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											}else
												subto.setCiaMarksAwarded(obj[6].toString());
										}else
											subto.setCiaMarksAwarded("");
									}
									if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
										subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
										subto.setRevaluationReq(false);
									}else{
										if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
											subto.setRevaluationReq(true);
									}

									// for external only subjects
									if(obj[61]!=null && obj[61].toString().equalsIgnoreCase("external")){
										if(obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
											if(((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
												if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setGrade(obj[40].toString());
													if(obj[41]!=null){
														subto.setGradePoints(obj[40].toString());
													}
													if(obj[42]!=null){
														BigDecimal pnt=	new BigDecimal(obj[42].toString());
														int point=pnt.intValue();
														subto.setTheoryGradePnt(String.valueOf(point));
														if(obj[31]!=null){
															BigDecimal ct=new BigDecimal(obj[31].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;

														}
													}

												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
													isReappear=true;
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										} 
										else if(obj[15].toString().equalsIgnoreCase("WTH")){
											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									}
									// for internal only subjects
									else if(obj[61]!=null && obj[61].toString().equalsIgnoreCase("internal")){

										if(obj[6]!=null && obj[56]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH")){
											if((ciaMarksAwarded>=(new BigDecimal(obj[56].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[56].toString())).intValue())){
												if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setGrade(obj[40].toString());
													if(obj[41]!=null){
														subto.setGradePoints(obj[40].toString());
													}
													if(obj[42]!=null){
														BigDecimal pnt=	new BigDecimal(obj[42].toString());
														int point=pnt.intValue();
														subto.setTheoryGradePnt(String.valueOf(point));
														if(obj[31]!=null){
															BigDecimal ct=new BigDecimal(obj[31].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;

														}
													}

												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
													isReappear=true;
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										} 
										else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									
									}
									else{

										if(obj[6]!=null && obj[56]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
											if((ciaMarksAwarded>=(new BigDecimal(obj[56].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[56].toString())).intValue()) &&
													((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
												if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setGrade(obj[40].toString());
													if(obj[41]!=null){
														subto.setGradePoints(obj[40].toString());
													}
													if(obj[42]!=null){
														BigDecimal pnt=	new BigDecimal(obj[42].toString());
														int point=pnt.intValue();
														subto.setTheoryGradePnt(String.valueOf(point));
														if(obj[31]!=null){
															BigDecimal ct=new BigDecimal(obj[31].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;

														}
													}

												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
													isReappear=true;
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										} 
										else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									
									}
	


								}
								if(obj[33].toString().equalsIgnoreCase("Practical")){
									if(showSubType)
										subto.setType("Practical");
									else
										subto.setType("");

									subto.setPracticalGrade(grade);
									subto.setTheory(false);
									subto.setPractical(true);
									if(obj[30]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
										if(subto.getPracticalGrade()!=null){
											if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
										if(obj[43]!=null){

											double min=0;
											double stu=0;
											if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
												min = Double.parseDouble(obj[13].toString());
											if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
												stu = Double.parseDouble(obj[16].toString());

											double d=0;
											if(!CommonUtil.isValidDecimal(obj[16].toString())){
												d=0;
											}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											}
											gpForCal=gpForCal+d;


											/*double d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											gpForCal=gpForCal+d;*/
										}
									}
									int ciaMarksAwarded=0;
									if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
									}
									/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
									}*/
									if(obj[13]!=null){
										if(showMinMarks)
											subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
										else
											subto.setPracticalEseMinMarks("");
									}
									if(obj[14]!=null){
										if(showMaxMarks)
											subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
										else
											subto.setPracticalEseMaxMarks("");
									}
									if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[16].toString()));
										if(isDisplay && subto.getPracticalEseMaxMarks()!=null && !subto.getPracticalEseMaxMarks().equals("-")){
											if(m>=10)
												subto.setPracticalEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
											else
												subto.setPracticalEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
										}else
											subto.setPracticalEseMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setPracticalEseMarksAwarded(obj[16].toString());
										else
											subto.setPracticalEseMarksAwarded("");
									}

									if(obj[24]!=null){
										if(showMaxMarks)
											subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[24].toString()))));
										else
											subto.setPracticalCiaMaxMarks("");
									}
									if(obj[27]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
										if(showMaxMarks)
											subto.setPracticalTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
										else
											subto.setPracticalTotalMaxMarks("");
									}
									if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
										if(isDisplay){
											if((int)Math.round(Double.parseDouble(obj[29].toString()))>=10)
												subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
											else
												subto.setPracticalTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
										}else
											subto.setPracticalTotalMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setPracticalTotalMarksAwarded(obj[29].toString());
										else
											subto.setPracticalTotalMarksAwarded("");
									}
									if(ciaMarksAwarded!=0  && obj[8]!=null && obj[9]!=null &&  CommonUtil.isValidDecimal(obj[8].toString()) && CommonUtil.isValidDecimal(obj[9].toString())){
										if(isDisplay){
											if(ciaMarksAwarded>=10)
												subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
										}else
											subto.setPracticalCiaMarksAwarded("");
									}else{

										if(isDisplay)
											if(CommonUtil.isValidDecimal(obj[8].toString())){
												//												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												if(ciaMarksAwarded>=10)
													subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											}else
												subto.setPracticalCiaMarksAwarded(obj[8].toString());
										else
											subto.setPracticalCiaMarksAwarded("");

										if(obj[0]!=null &&  obj[2]!=null && obj[3]!=null){
											DecimalFormat twoDForm = new DecimalFormat("#.##");
											String Query="select avg(student_practical_marks) from EXAM_student_final_mark_details where exam_id="+obj[0].toString()+" and class_id="+obj[2].toString()+" and subject_id="+obj[3].toString();
											List avgList=transaction.getStudentHallTicket(Query);
											if(avgList.get(0)!=null){
												double avg=Double.parseDouble(avgList.get(0).toString());
												subto.setAvgmark(twoDForm.format(avg));}

										}
										
										// for external only
										if(obj[61]!=null && obj[61].toString().equalsIgnoreCase("external")){
											if( obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
												if(((new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue())){
													if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
														subto.setResultClass("PASS");
														subto.setPracticalGrade(obj[41].toString());
														if(obj[43]!=null){
															subto.setGradePoints(obj[43].toString());
														}

														if(obj[43]!=null){
															BigDecimal pnt=	new BigDecimal(obj[43].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[30]!=null){
																BigDecimal ct=new BigDecimal(obj[30].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;
															}
														}
													}
													else{
														subto.setResultClass("REAPPEAR");
														subto.setGrade("-");
														subto.setGradePoints("-");
														subto.setCreditPoint("-");
														isFailed=true;
													}
												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
												}
											}

											else if(obj[16].toString().equalsIgnoreCase("WTH")){

												subto.setResultClass("WITH HELD");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isWithHeld=true;
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										}
										else if(obj[61]!=null && obj[61].toString().equalsIgnoreCase("internal")){

											if(obj[8]!=null && obj[57]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null ){
												if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[57].toString())).intValue())){
													if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
														subto.setResultClass("PASS");
														subto.setPracticalGrade(obj[41].toString());
														if(obj[43]!=null){
															subto.setGradePoints(obj[43].toString());
														}

														if(obj[43]!=null){
															BigDecimal pnt=	new BigDecimal(obj[43].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[30]!=null){
																BigDecimal ct=new BigDecimal(obj[30].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;
															}
														}
													}
													else{
														subto.setResultClass("REAPPEAR");
														subto.setGrade("-");
														subto.setGradePoints("-");
														subto.setCreditPoint("-");
														isFailed=true;
													}
												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
												}
											}

											else if(obj[8].toString().equalsIgnoreCase("WTH")){

												subto.setResultClass("WITH HELD");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isWithHeld=true;
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										
											
										}
										else{

											if(obj[8]!=null && obj[57]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
												if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[57].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue()) ||
														((new BigDecimal(obj[8].toString())).intValue()==(new BigDecimal(obj[54].toString())).intValue() || (new BigDecimal(obj[16].toString())).intValue()==(new BigDecimal(obj[13].toString())).intValue())){
													if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
														subto.setResultClass("PASS");
														subto.setPracticalGrade(obj[41].toString());
														if(obj[43]!=null){
															subto.setGradePoints(obj[43].toString());
														}

														if(obj[43]!=null){
															BigDecimal pnt=	new BigDecimal(obj[43].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[30]!=null){
																BigDecimal ct=new BigDecimal(obj[30].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;
															}
														}
													}
													else{
														subto.setResultClass("REAPPEAR");
														subto.setGrade("-");
														subto.setGradePoints("-");
														subto.setCreditPoint("-");
														isFailed=true;
													}
												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
												}
											}

											else if(obj[16].toString().equalsIgnoreCase("WTH") || obj[8].toString().equalsIgnoreCase("WTH")){

												subto.setResultClass("WITH HELD");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isWithHeld=true;
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										
											
										}
									
									}
								}

							}
							subList.add(subto);
							subMap.put(obj[20].toString(), subList);
						}
					}else{
						if(obj[20]!=null){
							List<SubjectTO> subList=null;
							if(subMap.containsKey(obj[20].toString())){
								subList=subMap.remove(obj[20].toString());
							}else{
								subList=new ArrayList<SubjectTO>();
							}

							SubjectTO subto=new SubjectTO();
							subto.setDisplaySubject(displaySubject);
							subto.setId(Integer.parseInt(obj[3].toString()));
							if(obj[4]!=null){
								subto.setCode(obj[4].toString());
							}
							if(obj[5]!=null){
								subto.setName(obj[5].toString());
							}
							if(obj[23]!=null)
								subto.setSubOrder(Integer.parseInt(obj[23].toString()));
							if(obj[33]!=null){
								if(obj[33].toString().equalsIgnoreCase("Theory")){
									if(showSubType)
										subto.setType("Theory");
									else
										subto.setType("");

									subto.setGrade(grade);
									subto.setTheory(true);
									if(obj[31]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
										if(subto.getGrade()!=null){
											if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
										if(obj[42]!=null){
											double min=0;
											double stu=0;
											if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
												min=0;
											if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
												stu=0;
											double d=0;
											if(!CommonUtil.isValidDecimal(obj[15].toString())){
												d=0;
											}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[42].toString()))*Double.parseDouble(obj[31].toString());
											}
											gpForCal=gpForCal+d;
											/*double d=Double.parseDouble((obj[42].toString()))*Double.parseDouble((obj[31].toString()));
										gpForCal=gpForCal+d;*/
										}
									}
									int ciaMarksAwarded=0;
									if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
									}
									/*if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
									}*/
									if(obj[11]!=null){
										if(showMinMarks)
											subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
										else
											subto.setEseMinMarks("");
									}
									if(obj[12]!=null){
										if(showMaxMarks)
											subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
										else
											subto.setEseMaxMarks("");
									}
									//								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									//									int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
									//									if(isDisplay && m>0)
									//										subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
									//									else
									//										subto.setEseMarksAwarded("-");
									//								}else{
									//									if(isDisplay)
									//										subto.setEseMarksAwarded(obj[15].toString());
									//									else
									//										subto.setEseMarksAwarded("-");
									//								}
									if(obj[0]!=null &&  obj[2]!=null && obj[3]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.##");
										String Query="select avg(student_theory_marks) from EXAM_student_final_mark_details where exam_id="+obj[0].toString()+" and class_id="+obj[2].toString()+" and subject_id="+obj[3].toString();
										List avgList=transaction.getStudentHallTicket(Query);
										if(avgList.get(0)!=null){
											double avg=Double.parseDouble(avgList.get(0).toString());
											subto.setAvgmark(twoDForm.format(avg));

										}

									}


									if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
										if(isDisplay && subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equals("-")){
											if(m>=10)
												subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
											else
												subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
										}else
											subto.setEseMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setEseMarksAwarded(obj[15].toString());
										else
											subto.setEseMarksAwarded("");
									}
									if(obj[25]!=null){
										if(showMaxMarks)
											subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[25].toString()))));
										else
											subto.setCiaMaxMarks("");
									}
									if(obj[26]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
										if(showMaxMarks)
											subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
										else
											subto.setTotalMaxMarks("");
									}
									if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
										if(isDisplay){
											if((int)Math.round(Double.parseDouble(obj[28].toString()))>=10)
												subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
											else
												subto.setTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
										}else
											subto.setTotalMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setTotalMarksAwarded(obj[28].toString());
										else
											subto.setTotalMarksAwarded("");
									}
									//								if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
									//									if(isDisplay)
									//										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									//									else
									//										subto.setCiaMarksAwarded("-");
									//								}else{
									//									if(isDisplay){
									//										if(CommonUtil.isValidDecimal(obj[6].toString()))
									//											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									//										else
									//											subto.setCiaMarksAwarded(obj[6].toString());
									//									}else
									//										subto.setCiaMarksAwarded("-");
									//								}
									if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
										if(isDisplay){
											if(ciaMarksAwarded>=10)
												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
										}else
											subto.setCiaMarksAwarded("");
									}else{
										if(isDisplay){
											if(CommonUtil.isValidDecimal(obj[6].toString())){
												if(ciaMarksAwarded>=10)
													subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											}else
												subto.setCiaMarksAwarded(obj[6].toString());
										}else
											subto.setCiaMarksAwarded("");
									}
									if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
										subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
										subto.setRevaluationReq(false);
									}else{
										if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
											subto.setRevaluationReq(true);
									}

									// for external only subjects
									if(obj[61]!=null && obj[61].toString().equalsIgnoreCase("external")){
										if(obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
											if(((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
												if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setGrade(obj[40].toString());
													if(obj[41]!=null){
														subto.setGradePoints(obj[40].toString());
													}
													if(obj[42]!=null){
														BigDecimal pnt=	new BigDecimal(obj[42].toString());
														int point=pnt.intValue();
														subto.setTheoryGradePnt(String.valueOf(point));
														if(obj[31]!=null){
															BigDecimal ct=new BigDecimal(obj[31].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;

														}
													}

												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
													isReappear=true;
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										} 
										else if(obj[15].toString().equalsIgnoreCase("WTH")){
											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									}
									// for internal only subjects
									else if(obj[61]!=null && obj[61].toString().equalsIgnoreCase("internal")){

										if(obj[6]!=null && obj[56]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH")){
											if((ciaMarksAwarded>=(new BigDecimal(obj[56].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[56].toString())).intValue())){
												if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setGrade(obj[40].toString());
													if(obj[41]!=null){
														subto.setGradePoints(obj[40].toString());
													}
													if(obj[42]!=null){
														BigDecimal pnt=	new BigDecimal(obj[42].toString());
														int point=pnt.intValue();
														subto.setTheoryGradePnt(String.valueOf(point));
														if(obj[31]!=null){
															BigDecimal ct=new BigDecimal(obj[31].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;

														}
													}

												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
													isReappear=true;
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										} 
										else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									
									}
									else{

										if(obj[6]!=null && obj[56]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
											if((ciaMarksAwarded>=(new BigDecimal(obj[56].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[56].toString())).intValue()) &&
													((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
												if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
													subto.setResultClass("PASS");
													subto.setGrade(obj[40].toString());
													if(obj[41]!=null){
														subto.setGradePoints(obj[40].toString());
													}
													if(obj[42]!=null){
														BigDecimal pnt=	new BigDecimal(obj[42].toString());
														int point=pnt.intValue();
														subto.setTheoryGradePnt(String.valueOf(point));
														if(obj[31]!=null){
															BigDecimal ct=new BigDecimal(obj[31].toString());
															int credit=ct.intValue();
															int cp=point*credit;
															subto.setCreditPoint(String.valueOf(cp));
															totalgradepoints=totalgradepoints+cp;
															totcredits=totcredits+credit;

														}
													}

												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
													isReappear=true;
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										} 
										else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
											subto.setResultClass("WITH HELD");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isWithHeld=true;
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									
									}
	


								}
								if(obj[33].toString().equalsIgnoreCase("Practical")){
									if(showSubType)
										subto.setType("Practical");
									else
										subto.setType("");

									subto.setPracticalGrade(grade);
									subto.setTheory(false);
									subto.setPractical(true);
									if(obj[30]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
										if(subto.getPracticalGrade()!=null){
											if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
										if(obj[43]!=null){

											double min=0;
											double stu=0;
											if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
												min = Double.parseDouble(obj[13].toString());
											if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
												stu = Double.parseDouble(obj[16].toString());

											double d=0;
											if(!CommonUtil.isValidDecimal(obj[16].toString())){
												d=0;
											}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											}
											gpForCal=gpForCal+d;


											/*double d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											gpForCal=gpForCal+d;*/
										}
									}
									int ciaMarksAwarded=0;
									if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
									}
									/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
									}*/
									if(obj[13]!=null){
										if(showMinMarks)
											subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
										else
											subto.setPracticalEseMinMarks("");
									}
									if(obj[14]!=null){
										if(showMaxMarks)
											subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
										else
											subto.setPracticalEseMaxMarks("");
									}
									if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[16].toString()));
										if(isDisplay && subto.getPracticalEseMaxMarks()!=null && !subto.getPracticalEseMaxMarks().equals("-")){
											if(m>=10)
												subto.setPracticalEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
											else
												subto.setPracticalEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
										}else
											subto.setPracticalEseMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setPracticalEseMarksAwarded(obj[16].toString());
										else
											subto.setPracticalEseMarksAwarded("");
									}

									if(obj[24]!=null){
										if(showMaxMarks)
											subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[24].toString()))));
										else
											subto.setPracticalCiaMaxMarks("");
									}
									if(obj[27]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
										if(showMaxMarks)
											subto.setPracticalTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
										else
											subto.setPracticalTotalMaxMarks("");
									}
									if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
										if(isDisplay){
											if((int)Math.round(Double.parseDouble(obj[29].toString()))>=10)
												subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
											else
												subto.setPracticalTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
										}else
											subto.setPracticalTotalMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setPracticalTotalMarksAwarded(obj[29].toString());
										else
											subto.setPracticalTotalMarksAwarded("");
									}
									if(ciaMarksAwarded!=0  && obj[8]!=null && obj[9]!=null &&  CommonUtil.isValidDecimal(obj[8].toString()) && CommonUtil.isValidDecimal(obj[9].toString())){
										if(isDisplay){
											if(ciaMarksAwarded>=10)
												subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
										}else
											subto.setPracticalCiaMarksAwarded("");
									}else{

										if(isDisplay)
											if(CommonUtil.isValidDecimal(obj[8].toString())){
												//												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												if(ciaMarksAwarded>=10)
													subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											}else
												subto.setPracticalCiaMarksAwarded(obj[8].toString());
										else
											subto.setPracticalCiaMarksAwarded("");

										if(obj[0]!=null &&  obj[2]!=null && obj[3]!=null){
											DecimalFormat twoDForm = new DecimalFormat("#.##");
											String Query="select avg(student_practical_marks) from EXAM_student_final_mark_details where exam_id="+obj[0].toString()+" and class_id="+obj[2].toString()+" and subject_id="+obj[3].toString();
											List avgList=transaction.getStudentHallTicket(Query);
											if(avgList.get(0)!=null){
												double avg=Double.parseDouble(avgList.get(0).toString());
												subto.setAvgmark(twoDForm.format(avg));}

										}
										
										// for external only
										if(obj[61]!=null && obj[61].toString().equalsIgnoreCase("external")){
											if( obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
												if(((new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue())){
													if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
														subto.setResultClass("PASS");
														subto.setPracticalGrade(obj[41].toString());
														if(obj[43]!=null){
															subto.setGradePoints(obj[43].toString());
														}

														if(obj[43]!=null){
															BigDecimal pnt=	new BigDecimal(obj[43].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[30]!=null){
																BigDecimal ct=new BigDecimal(obj[30].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;
															}
														}
													}
													else{
														subto.setResultClass("REAPPEAR");
														subto.setGrade("-");
														subto.setGradePoints("-");
														subto.setCreditPoint("-");
														isFailed=true;
													}
												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
												}
											}

											else if(obj[16].toString().equalsIgnoreCase("WTH")){

												subto.setResultClass("WITH HELD");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isWithHeld=true;
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										}
										else if(obj[61]!=null && obj[61].toString().equalsIgnoreCase("internal")){

											if(obj[8]!=null && obj[57]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null ){
												if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[57].toString())).intValue())){
													if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
														subto.setResultClass("PASS");
														subto.setPracticalGrade(obj[41].toString());
														if(obj[43]!=null){
															subto.setGradePoints(obj[43].toString());
														}

														if(obj[43]!=null){
															BigDecimal pnt=	new BigDecimal(obj[43].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[30]!=null){
																BigDecimal ct=new BigDecimal(obj[30].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;
															}
														}
													}
													else{
														subto.setResultClass("REAPPEAR");
														subto.setGrade("-");
														subto.setGradePoints("-");
														subto.setCreditPoint("-");
														isFailed=true;
													}
												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
												}
											}

											else if(obj[8].toString().equalsIgnoreCase("WTH")){

												subto.setResultClass("WITH HELD");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isWithHeld=true;
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										
											
										}
										else{

											if(obj[8]!=null && obj[57]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
												if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[57].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue()) ||
														((new BigDecimal(obj[8].toString())).intValue()==(new BigDecimal(obj[57].toString())).intValue() || (new BigDecimal(obj[16].toString())).intValue()==(new BigDecimal(obj[13].toString())).intValue())){
													if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
														subto.setResultClass("PASS");
														subto.setPracticalGrade(obj[41].toString());
														if(obj[43]!=null){
															subto.setGradePoints(obj[43].toString());
														}

														if(obj[43]!=null){
															BigDecimal pnt=	new BigDecimal(obj[43].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[30]!=null){
																BigDecimal ct=new BigDecimal(obj[30].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;
															}
														}
													}
													else{
														subto.setResultClass("REAPPEAR");
														subto.setGrade("-");
														subto.setGradePoints("-");
														subto.setCreditPoint("-");
														isFailed=true;
													}
												}
												else{
													subto.setResultClass("REAPPEAR");
													subto.setGrade("-");
													subto.setGradePoints("-");
													subto.setCreditPoint("-");
													isFailed=true;
												}
											}

											else if(obj[16].toString().equalsIgnoreCase("WTH") || obj[8].toString().equalsIgnoreCase("WTH")){

												subto.setResultClass("WITH HELD");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isWithHeld=true;
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										
											
										}
									
									}
								}

							}
							subList.add(subto);
							subMap.put(obj[20].toString(), subList);
						}
					}
				}// End of Display Subject
			}
			
			// added by ashwini for subject deff coursewise grade def
			List<ExamSubCoursewiseGradeDefnTO> ExamSubCoursewiseGradeDefnTOList = new ArrayList<ExamSubCoursewiseGradeDefnTO>();
			List<GradePointRangeBO> courseWiseGradeList = new ArrayList<GradePointRangeBO>();
			ExamSubDefinitionCourseWiseHelper helper = new ExamSubDefinitionCourseWiseHelper();
			ExamSubDefinitionCourseWiseImpl impl = new ExamSubDefinitionCourseWiseImpl();
			for (Integer subId : subjectIdList) {
				ExamSubCoursewiseGradeDefnTOList = helper.convertBOToTO_GradeDetails(impl.select_All_grade_detailsForMarksCard(subId,cid,academicYear));
				if(ExamSubCoursewiseGradeDefnTOList.size()>0)
					break;
			}
			if(ExamSubCoursewiseGradeDefnTOList!=null && ExamSubCoursewiseGradeDefnTOList.size()>0)
			to.setExamSubCoursewiseGradeDefnTOList(ExamSubCoursewiseGradeDefnTOList);
			courseWiseGradeList = UpdateExamStudentSGPAImpl.getInstance().getCoursewiseGrades(cid,appliedYear, sid);
			if(courseWiseGradeList!=null && courseWiseGradeList.size()>0)
			to.setCourseWiseGradeList(courseWiseGradeList);

			Map<String,List<SubjectTO>> finalsubMap=new HashMap<String, List<SubjectTO>>();
			List<SubjectTO> addOnCourse=null;
			boolean isAddonCourse=false;
			List<SubjectTO> toList = new ArrayList<SubjectTO>();
			List<SubjectTO> addontoList = new ArrayList<SubjectTO>();
			if(!subMap.isEmpty()){
				Iterator it = subMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					if(pairs.getKey().toString().equalsIgnoreCase("Add On Course")){
						addOnCourse = (ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(addOnCourse,comparator);
						addontoList.addAll(addOnCourse);
						isAddonCourse=true;
					}else{
						List<SubjectTO> list=(ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(list,comparator);
						toList.addAll(list);
						finalsubMap.put(pairs.getKey().toString(),list);
					}
				}
			}
			to.setSubjectTOList(toList);
			to.setSubjectAddOnTOList(addontoList);
			Map<String,List<SubjectTO>> addOnCoursesubMap=new HashMap<String, List<SubjectTO>>();
			if(isAddonCourse){
				addOnCoursesubMap.put("Add On Course", addOnCourse);
			}
			to.setSubMap(CommonUtil.sortMapByKey(finalsubMap));
			to.setAddOnCoursesubMap(addOnCoursesubMap);

			DecimalFormat twoDForm = new DecimalFormat("#.##");

			to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
			to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
			to.setTotalMarksInWord(CommonUtil.numberToWord1(totalMarksAwarded));
			to.setTotalCredits(String.valueOf(totalCredits));

			if(isFailed==true ){
				to.setTotalGrade("F");
				to.setTotalMarksAwarded(" ");
				to.setTotalCredits(" ");
				to.setTotalGradePoints(" ");
				if(isWithHeld){
				to.setResultClass("WITHHELD");
				}else{
					to.setResultClass("REAPPEAR");	
				}
				to.setSgpa(" ");
			}
			else{
				if(totalgradepoints>0 && totcredits>0){
					double sgpa=(totalgradepoints/totcredits);
					to.setSgpa(String.valueOf(CommonUtil.Round(sgpa, 2)));
				}
				int tcredits=(int)totcredits;
				to.setTotalCredits(String.valueOf(tcredits));
				int tcreditPoints=(int)totalgradepoints;
				to.setTotalGradePoints(String.valueOf(tcreditPoints));
				String grade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(cid, to.getSgpa(), 0,sid);
				to.setTotalGrade(grade);
				to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
				if(isWithHeld){
					to.setResultClass("WITHHELD");
				}else if(isReappear){
					to.setResultClass("REAPPEAR");
				}else{
				to.setResultClass("PASS");
				}
			}
			double tgpa=0;

			if(gpForCal>0 && tcForCal>0){
				tgpa=CommonUtil.Round((gpForCal/tcForCal),2);
			}
			to.setGradePoints(String.valueOf(twoDForm.format(tgpa)));
			String countQuery="select count(*) from EXAM_update_exclude_withheld " +
			" where student_id = " +studentId+
			" and exam_id ="+examId;
			int count=0;
			List<BigInteger> countList=transaction.getStudentHallTicket(countQuery);
			if(countList!=null && !countList.isEmpty()){
				BigInteger b=countList.get(0);
				count=b.intValue();
			}
			String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
			double agg=0;
			List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
			if(aggList!=null && !aggList.isEmpty()){
				agg=Double.parseDouble(aggList.get(0).toString());
			}

/*			if(count<1){
				double r=((double)(totalMarksAwarded)/(double)(totalMaxMarks))*100;
				if(r>=agg){
					String robj = UpdateExamStudentSGPAImpl.getInstance().getResultClass(cid, r, 0,sid);
					if(robj!=null && resultClass.equals("PASS"))
						to.setResult(robj);
					if(isWithHeld){
						to.setResultClass("WITHHELD");
					}else if(isReappear){
						to.setResultClass("REAPPEAR");
					}else{
					to.setResultClass("PASS");
					}

					String query="select result_class from EXAM_grade_class_definition where course_id="+cid+" and "+r+" between start_percentage and end_percentage";
				List<String> rList=transaction.getStudentHallTicket(query);
				if(rList!=null && !rList.isEmpty()){
					Iterator<String> rItr=rList.iterator();
					while (rItr.hasNext()) {
						String robj = rItr.next();
						if(robj!=null && resultClass.equals("Pass"))
							to.setResult(robj);
						else
							to.setResult("Failed");
					}
				}
				}else
					to.setResult("REAPPEAR");
			}else{
				to.setResult("WITH HELD");
			}*/
			if(studentPhotos.containsKey(sid)){
				if(studentPhotos.get(sid) != null){
					to.setPhoto(new String(studentPhotos.get(sid)));
				}
				request.getSession().setAttribute(to.getRegNo(), studentPhotos.get(sid));
			}
		}
		// for sgpa storing
		double sgpa = 0;
		String grade = "";
		if(totalgradepointsForSgpa>0 && totcreditsForSgpa>0){
			sgpa=CommonUtil.Round(totalgradepointsForSgpa/totcreditsForSgpa,2);
			grade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(cid, String.valueOf(sgpa), 0,sid);
		}
		if(to!=null){
		to.setSemesterSgpa(String.valueOf(sgpa));
		if(grade!=null && !grade.equalsIgnoreCase(""))
			to.setGradeForSgpa(grade);
		if(to.getResultClass().equalsIgnoreCase("REAPPEAR")){
			to.setResultForSgpa("failed");
			to.setSgpa(" ");
		}
		else{
			to.setResultForSgpa("passed");
		}
		to.setTotalGradePointsForSgpa(String.valueOf(totalgradepointsForSgpa));
		to.setTotalCreditsForSgpa(String.valueOf(totcreditsForSgpa));
		to.setTotalMaxMarksForSgpa(String.valueOf(totalMaxMarksForSgpa));
		to.setTotalMarksAwardedForSgpa(String.valueOf(totalMarksAwardedForSgpa));
		
		}

		return to;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupHallTicket(int studentId,int examId,List<Integer> classes) throws Exception {
		String classIds="";
		for (Integer integer : classes) {
			if(classIds.isEmpty())
				classIds=String.valueOf(integer);
			else
				classIds=classIds+","+integer;
		}
		//String query="SELECT classes.name as className, student.register_no, personal_data.first_name, "+
		//" personal_data.middle_name, personal_data.last_name, "+
		//" subject.code, subject.name as subjectName, EXAM_time_table.date_starttime "+
		//new
		String query="SELECT classes.name, student.register_no, personal_data.first_name,"+
		" personal_data.middle_name, personal_data.last_name,"+
		" subject.code,subject.name as subName, EXAM_time_table.date_starttime,student.id,EXAM_sessions.id as sessionId,EXAM_sessions.session as sessionName,"+
		" personal_data.date_of_birth,EXAM_time_table.date_endtime,course.name as coursename,EXAM_definition.name as examname,EXAM_exam_course_scheme_details.scheme_no,EXAM_definition.month as months,EXAM_definition.year,"+
		" subject.is_theory_practical"+

		" from student student "+
		" LEFT JOIN adm_appln adm_appln "+
		" ON (student.adm_appln_id = adm_appln.id) "+
		" LEFT JOIN personal_data personal_data "+
		" ON (adm_appln.personal_data_id = personal_data.id) "+
		" LEFT JOIN EXAM_supplementary_improvement_application EXAM_supplementary_improvement_application "+
		"  ON (EXAM_supplementary_improvement_application.student_id = student.id) "+
		" LEFT JOIN classes classes "+
		" ON (classes.id = EXAM_supplementary_improvement_application.class_id) "+
		" LEFT JOIN subject subject "+
		" ON (EXAM_supplementary_improvement_application.subject_id = subject.id) "+
		" LEFT JOIN EXAM_exam_course_scheme_details EXAM_exam_course_scheme_details "+
		" ON (EXAM_exam_course_scheme_details.course_id = classes.course_id) "+
		" AND (EXAM_exam_course_scheme_details.scheme_no = EXAM_supplementary_improvement_application.scheme_no) "+
		" and (EXAM_exam_course_scheme_details.exam_id=EXAM_supplementary_improvement_application.exam_id) "+
		//new
		" LEFT JOIN course course " +
		" ON (EXAM_exam_course_scheme_details.course_id=course.id) " +
		" LEFT JOIN EXAM_definition EXAM_definition " +
		" ON (EXAM_exam_course_scheme_details.exam_id=EXAM_definition.id) " +

		" LEFT JOIN EXAM_time_table EXAM_time_table "+
		" ON (EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id) "+
		" AND (EXAM_time_table.subject_id = subject.id) "+
		//new
		" LEFT JOIN EXAM_sessions on EXAM_time_table.exam_session_id = EXAM_sessions.id " + 
		" LEFT JOIN exam_regular_app examRegApp " +
		" ON (classes.id =examRegApp.class_id) " +
		" AND (student.id = examRegApp.student_id)" +
		" AND (EXAM_definition.id = examRegApp.exam_id)" +
		" WHERE  (subject.is_active = 1) "+
		" AND (adm_appln.is_cancelled = 0) "+
		" AND (classes.is_active = 1) "+
		" AND (EXAM_exam_course_scheme_details.is_active = 1) "+
		" AND (EXAM_supplementary_improvement_application.is_appeared_theory=1 " +
				" or EXAM_supplementary_improvement_application.is_appeared_practical=1) "+
		//" AND (EXAM_time_table.date_starttime is not null) "+
		" AND student.id= "+studentId+
		" AND examRegApp.chalan_verified =1 "+
		" AND EXAM_exam_course_scheme_details.exam_id ="+examId+" and classes.id in ("+classIds+
		") group by subject.id ORDER BY student.register_no";

		return query;
	}

	public String getQueryForSupHallTicketAdmin(int studentId,int examId) throws Exception {
		//new
		String query="SELECT classes.name, student.register_no, personal_data.first_name,"+
		" personal_data.middle_name, personal_data.last_name,"+
		" subject.code,subject.name as subName, EXAM_time_table.date_starttime,student.id,EXAM_sessions.id as sessionId,EXAM_sessions.session as sessionName,"+
		" personal_data.date_of_birth,EXAM_time_table.date_endtime,course.name as coursename,EXAM_definition.name as examname,EXAM_exam_course_scheme_details.scheme_no,EXAM_definition.month as months,EXAM_definition.year,"+
		" subject.is_theory_practical"+

		//String query="SELECT classes.name as className, student.register_no, personal_data.first_name, "+
		//" personal_data.middle_name, personal_data.last_name, "+
		//" subject.code, subject.name as subjectName, EXAM_time_table.date_starttime "+

		" from student student "+
		" LEFT JOIN adm_appln adm_appln "+
		" ON (student.adm_appln_id = adm_appln.id) "+
		" LEFT JOIN personal_data personal_data "+
		" ON (adm_appln.personal_data_id = personal_data.id) "+
		" LEFT JOIN EXAM_supplementary_improvement_application EXAM_supplementary_improvement_application "+
		"  ON (EXAM_supplementary_improvement_application.student_id = student.id) "+
		" LEFT JOIN classes classes "+
		" ON (classes.id = EXAM_supplementary_improvement_application.class_id) "+
		" LEFT JOIN subject subject "+
		" ON (EXAM_supplementary_improvement_application.subject_id = subject.id) "+
		" LEFT JOIN EXAM_exam_course_scheme_details EXAM_exam_course_scheme_details "+
		" ON (EXAM_exam_course_scheme_details.course_id = classes.course_id) "+
		" AND (EXAM_exam_course_scheme_details.scheme_no = EXAM_supplementary_improvement_application.scheme_no) "+
		" and (EXAM_exam_course_scheme_details.exam_id=EXAM_supplementary_improvement_application.exam_id) "+
		//new
		" LEFT JOIN course course " +
		" ON (EXAM_exam_course_scheme_details.course_id=course.id) " +
		" LEFT JOIN EXAM_definition EXAM_definition " +
		" ON (EXAM_exam_course_scheme_details.exam_id=EXAM_definition.id) " +

		" LEFT JOIN EXAM_time_table EXAM_time_table "+
		" ON (EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id) "+
		" AND (EXAM_time_table.subject_id = subject.id) "+
		//new
		" LEFT JOIN EXAM_sessions on EXAM_time_table.exam_session_id = EXAM_sessions.id " + 
		" LEFT JOIN exam_regular_app examRegApp " +
		" ON (classes.id =examRegApp.class_id) " +
		" AND (student.id = examRegApp.student_id)" +
		" AND (EXAM_definition.id = examRegApp.exam_id)" +
		" WHERE  (subject.is_active = 1) "+
		" AND (adm_appln.is_cancelled = 0) "+
		" AND (classes.is_active = 1) "+
		" AND (EXAM_exam_course_scheme_details.is_active = 1) "+
				" AND (EXAM_supplementary_improvement_application.is_appeared_theory=1 " +
				" or EXAM_supplementary_improvement_application.is_appeared_practical=1) "+
		//" AND (EXAM_time_table.date_starttime is not null) "+
		" AND student.id= "+studentId+
		" AND examRegApp.chalan_verified =1 "+
		" AND EXAM_exam_course_scheme_details.exam_id ="+examId+" and classes.id in (:classList"+
		") group by subject.id ORDER BY student.register_no ";



		return query;
	}
	/**
	 * @param hallTicket
	 * @return
	 * @throws Exception
	 */
	public HallTicketTo getSupHallTicketForStudent(List<Object[]> hallTicket) throws Exception {
		HallTicketTo to=null;
		List<SubjectTO> subList=null;
		if(hallTicket!=null && !hallTicket.isEmpty()){
			Iterator<Object[]> itr=hallTicket.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if(to==null){
					to=new HallTicketTo();
					if(obj[0]!=null){
						to.setClassName(obj[0].toString());
					}
					if(obj[1]!=null){
						to.setRegisterNo(obj[1].toString());
					}
					if(obj[2]!=null){
						to.setStudentName(obj[2].toString());
					}
					if(obj[3]!=null){
						to.setStudentName(to.getStudentName()+" "+obj[3].toString());
					}
					if(obj[4]!=null){
						to.setStudentName(to.getStudentName()+" "+obj[4].toString());
					}
					subList=new ArrayList<SubjectTO>();

				}else{
					subList=to.getSubList();
				}
				SubjectTO subto= new SubjectTO();
				if(obj[5]!=null){
					subto.setCode(obj[5].toString());
				}
				if(obj[6]!=null){
					subto.setName(obj[6].toString());
				}
				if(obj[7]!=null){
					String time= obj[7].toString();
					String dateString = time.substring(0, 10);
					String inputDateFormat = "yyyy-mm-dd";
					String outPutdateFormat = "dd-mm-yyyy";
					String hour = time.substring(11, 13);
					String minute = time.substring(14, 16);
					int h=Integer.parseInt(hour);
					if(h==00 || (h == 12 && minute.equals("00"))){
						subto.setStartTime(12+":"+minute+" Noon");
					}else if(h>6 && h<12)
						subto.setStartTime(hour+":"+minute+" AM");
					else
						subto.setStartTime(hour+":"+minute+" PM");
					subto.setStartDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
					subto.setClassName(obj[0].toString());
				}





				//for SBC
				if(obj[11]!=null){
					to.setDateofBirth(CommonUtil.formatSqlDate1(obj[11].toString()));

				}
				if(obj[12]!=null){
					String time= obj[12].toString();
					String dateString = time.substring(0, 10);
					String inputDateFormat = "yyyy-mm-dd";
					String outPutdateFormat = "dd/mm/yyyy";
					String hour = time.substring(11, 13);
					String minute = time.substring(14, 16);
					int h=Integer.parseInt(hour);
					/*if(h==00 || (h == 12 && minute.equals("00"))){
						subTo.setEndTime(12+":"+minute+" PM");
					}else if(h>6 && h<12)
						subTo.setEndTime(hour+":"+minute+" AM");
					else
						subTo.setEndTime(hour+":"+minute+" PM");*/
					if(h==00 || (h == 12 && minute.equals("00"))){
						subto.setEndTime(12+":"+minute);
					}else if(h>6 && h<12)
						subto.setEndTime(hour+":"+minute);
					else
						subto.setEndTime(hour+":"+minute);
				}
				if(obj[13]!=null && obj[15]!=null){
					to.setCourseName(obj[13].toString());
					to.setSemesterNo(semMap.get(obj[15].toString()));
				}
				if(obj[15]!=null){

					//hallTicketTo.setSemesterExt(CommonUtil.numberToWord1(Integer.parseInt(data[15].toString())));

					if(Integer.parseInt(obj[15].toString())==1)
						to.setSemesterExt("FIRST");
					if(Integer.parseInt(obj[15].toString())==2)
						to.setSemesterExt("SECOND");
					if(Integer.parseInt(obj[15].toString())==3)
						to.setSemesterExt("THIRD");
					if(Integer.parseInt(obj[15].toString())==4)
						to.setSemesterExt("FOURTH");
					if(Integer.parseInt(obj[15].toString())==5)
						to.setSemesterExt("FIFTH");
					if(Integer.parseInt(obj[15].toString())==6)
						to.setSemesterExt("SIXTH");
				}
				if(obj[14]!=null){
					to.setExamName(obj[14].toString());
				}
				if(obj[16]!=null){
					String month=CommonUtil.getMonthForNumber(Integer.parseInt (obj[16].toString()));
					to.setMonth(month);
				}
				if(obj[17]!=null){
					to.setYear(obj[17].toString());
				}






				subList.add(subto);
				to.setSubList(subList);
			}
		}
		return to;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForUGStudentSupMarksCard(int examId,int classId,int schemeNo,int studentId,int academicYear) throws Exception {
		String query="SELECT EXAM_student_overall_internal_mark_details.exam_id, " +
		"       EXAM_student_overall_internal_mark_details.student_id, " +
		"       EXAM_student_overall_internal_mark_details.class_id, " +
		"       EXAM_student_overall_internal_mark_details.subject_id, " +
		"       subject.code as subCode, " +
		"       subject.name as subName, " + 
		"       if(EXAM_internal_mark_supplementary_details.theory_total_mark IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details.theory_total_mark, " +
		"          EXAM_student_overall_internal_mark_details.theory_total_mark) " +
		"          AS theory_total_mark, " +
		"       if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details.theory_total_attendance_mark, " +
		"          EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) " +
		"          AS theory_total_attendance_mark, " +
		"       if(EXAM_internal_mark_supplementary_details.practical_total_mark IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details.practical_total_mark, " +
		"          EXAM_student_overall_internal_mark_details.practical_total_mark) " +
		"          AS practical_total_mark, " +
		"       if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details.practical_total_attendance_mark, " +
		"          EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) " +
		"          AS practical_total_attendance_mark, " +
		"       CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id, " +
		"       EXAM_subject_rule_settings.theory_ese_minimum_mark, " +
		"       EXAM_subject_rule_settings.theory_ese_maximum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_minimum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_maximum_mark, " +
		"       (if(EXAM_student_final_mark_details.student_theory_marks IS NOT NULL, " +
		"           EXAM_student_final_mark_details.student_theory_marks,0)) " +
		"          AS student_theory_marks, " +
		"       (if(EXAM_student_final_mark_details.student_practical_marks IS NOT NULL, " +
		"           EXAM_student_final_mark_details.student_practical_marks,0)) " +
		"          AS student_practical_marks, " +
		"       personal_data.first_name, " +
		"       personal_data.middle_name, " +
		"       personal_data.last_name, " +
		"       EXAM_subject_sections.name as secName, " +
		"       EXAM_subject_sections.is_initialise, " +
		"       EXAM_subject_sections.id as secId, " +
		"       EXAM_sub_definition_coursewise.subject_order as subOrder, " +
		"       EXAM_subject_rule_settings.final_practical_internal_maximum_mark, " +
		"       EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " +
		"       (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, " +
		"           EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+ if( " +
		"             (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id) " +
		"                IS NOT NULL, " +
		"             (SELECT max( " +
		"                        EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id), " +
		"             (SELECT max(EXAM_attendance_marks.marks) " +
		"                FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"               WHERE course_id = classes.course_id))+ if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0, " +
		"             EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) " +
		"          AS theoryTotal, " +
		"       (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0, " +
		"           EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+ if( " +
		"           (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id) " +
		"                IS NOT NULL, " +
		"             (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id), " +
		"             (SELECT max(EXAM_attendance_marks.marks) " +
		"                FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"               WHERE course_id = classes.course_id))+ ifnull(EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) " +
		"          AS practicalTotal, " +

		"    (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,  " +
		"    (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,  " +

		"       max(EXAM_sub_definition_coursewise.practical_credit) " +
		"          AS practical_credit, " +
		"       max(EXAM_sub_definition_coursewise.theory_credit) AS theory_credit, " +
		"       ifnull( (select oldreg.register_no " +
		"   from student_old_register_numbers oldreg " +
		"   where oldreg.student_id=student.id " +
		"   and oldreg.scheme_no=classes.term_number and oldreg.is_active=1)," +
		"   student.register_no  ) as register_no," +
		"            if(subject.is_theory_practical = 'T', 'Theory', " +
		"          if(subject.is_theory_practical = 'P', 'Practical', " +
		"					if(subject.is_theory_practical = 'B', 'Both', 'Theory'))) " +
		"          AS subType,  " +
		"       if( " +
		"          (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"             FROM EXAM_sub_coursewise_attendance_marks " +
		"            WHERE course_id = classes.course_id " +
		"                  AND subject_id = subject.id) " +
		"             IS NOT NULL, " +
		"          (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"             FROM EXAM_sub_coursewise_attendance_marks " +
		"            WHERE course_id = classes.course_id " +
		"                  AND subject_id = subject.id), " +
		"          (SELECT max(EXAM_attendance_marks.marks) " +
		"             FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"            WHERE course_id = classes.course_id)) " +
		"          AS maxAttMarks, " +
		"       classes.term_number, " +
		"       EXAM_publish_exam_results.publish_date, " +
		"       adm_appln.course_id, " +
		"       ((if( " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            theory_total_mark " +
		"               IS NOT NULL, " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            theory_total_mark, " +
		"            EXAM_student_overall_internal_mark_details. " +
		"            theory_total_mark) + (if(student_theory_marks IS NULL, 0, student_theory_marks)))/ (if( " +
		"              EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, " +
		"              EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " +
		"              0)+ if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0, " +
		"                EXAM_subject_rule_settings.theory_ese_maximum_mark, " +
		"                0)) * 100) " +
		"          AS theoryPer, " +
		"       ((if( " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            practical_total_mark " +
		"               IS NOT NULL, " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            practical_total_mark, " +
		"            EXAM_student_overall_internal_mark_details. " +
		"            practical_total_mark) + if(student_practical_marks IS NULL, 0, student_practical_marks))/ (if( " +
		"              EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0, " +
		"              EXAM_subject_rule_settings.final_practical_internal_maximum_mark, 0)+ if(EXAM_subject_rule_settings.practical_ese_maximum_mark > 0, " +
		"                EXAM_subject_rule_settings.practical_ese_maximum_mark, 0)) * 100) " +
		"          AS practicalper, " +
		"       ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +
		" ifnull(  (SELECT   EXAM_sub_coursewise_grade_defn.grade_point " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT   EXAM_grade_class_definition_frombatch.grade_point" +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		"  (SELECT   EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegrap, " +
		"  ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade_point " +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull(  (SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragrap,  " +
		"       EXAM_sub_definition_coursewise.dont_show_max_marks, " +
		"       EXAM_sub_definition_coursewise.dont_show_min_marks, " +
		"       EXAM_sub_definition_coursewise.show_only_grade, " +
		"       course_scheme.name, " +
		"       EXAM_sub_definition_coursewise.dont_show_sub_type, " +
		" EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		"	 EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,  " +
		"       EXAM_student_pass_fail.pass_fail, " +
		"       EXAM_supplementary_improvement_application.is_appeared_theory, " +
		"       EXAM_supplementary_improvement_application.is_appeared_practical, " +
		"       subject.is_theory_practical, " +
		"       EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark, " +
		"       EXAM_subject_rule_settings.final_theory_internal_is_attendance, " +
		"       EXAM_subject_rule_settings.final_practical_internal_is_attendance ," +
		"   EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		"   EXAM_subject_rule_settings.final_practical_internal_minimum_mark, " +
		
	    " sum((if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) + (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks ))) as subFinalObtained, "+ 
		" sum((if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)))) as subInternalMarksObtained,"+ 
		" sum((if(student_theory_marks is null,0,student_theory_marks)) +if(student_practical_marks is null,0,student_practical_marks )) as subTheoryMarksObtained, "+ 
	    " EXAM_subject_rule_settings.subject_final_maximum, "+ 
	    " EXAM_subject_rule_settings.subject_final_minimum, "+  
	    " sum((if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark is null,0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark)) +if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark is null,0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark )) as internalMaxMarks, "+ 
	    " sum((if(EXAM_subject_rule_settings.theory_ese_maximum_mark is null,0,EXAM_subject_rule_settings.theory_ese_maximum_mark)) +if(EXAM_subject_rule_settings.practical_ese_maximum_mark is null,0,EXAM_subject_rule_settings.practical_ese_maximum_mark )) as externalMaxMarks, "+ 
	    " sum((if(EXAM_subject_rule_settings.theory_ese_minimum_mark is null,0,EXAM_subject_rule_settings.theory_ese_minimum_mark)) +if(EXAM_subject_rule_settings.practical_ese_minimum_mark is null,0,EXAM_subject_rule_settings.practical_ese_minimum_mark )) as externalMinMarks, "+ 
	    " EXAM_definition.name as examName, "+
	    "    EXAM_definition.academic_year, "+
        " adm_appln.applied_year, "+ 
        " subject.subjectType_based_on_marks "+
		
		"  FROM EXAM_supplementary_improvement_application " +
		"       LEFT JOIN student " +
		"          ON (EXAM_supplementary_improvement_application.student_id = " +
		"                 student.id) " +
		"       LEFT JOIN classes " +
		"          ON (EXAM_supplementary_improvement_application.class_id = " +
		"                 classes.id) " +
		"       LEFT JOIN subject " +
		"          ON (EXAM_supplementary_improvement_application.subject_id = " +
		"                 subject.id) " +
		"       LEFT JOIN EXAM_definition " +
		"          ON (EXAM_supplementary_improvement_application.exam_id = " +
		"                 EXAM_definition.id) " +
		"       LEFT JOIN adm_appln " +
		"          ON student.adm_appln_id = adm_appln.id " +
		"       LEFT JOIN personal_data " +
		"          ON adm_appln.personal_data_id = personal_data.id " +
		"       LEFT JOIN subject_type " +
		"          ON subject.subject_type_id = subject_type.id " +
		"       LEFT JOIN EXAM_student_overall_internal_mark_details " +
		"          ON (EXAM_student_overall_internal_mark_details.student_id = " +
		"                 student.id) " +
		"             AND EXAM_student_overall_internal_mark_details.subject_id = " +
		"                    subject.id " +
		"             AND EXAM_student_overall_internal_mark_details.class_id = " +
		"                    classes.id " +
		"       LEFT JOIN class_schemewise " +
		"          ON class_schemewise.class_id = classes.id " +
		"       LEFT JOIN curriculum_scheme_duration " +
		"          ON class_schemewise.curriculum_scheme_duration_id = " +
		"                curriculum_scheme_duration.id " +
		"       LEFT JOIN curriculum_scheme " +
		"          ON curriculum_scheme_duration.curriculum_scheme_id = " +
		"                curriculum_scheme.id " +
		"       LEFT JOIN course_scheme " +
		"          ON curriculum_scheme.course_scheme_id = course_scheme.id " +
		"       LEFT JOIN EXAM_student_final_mark_details " +
		"          ON (EXAM_student_final_mark_details.class_id = classes.id) " +
		"             AND (EXAM_student_final_mark_details.exam_id = " +
		"                     EXAM_definition.id) " +
		"             AND (EXAM_student_final_mark_details.student_id = student.id) " +
		"             AND (EXAM_student_final_mark_details.subject_id = subject.id) " +
		"             AND (EXAM_student_final_mark_details.exam_id = EXAM_definition.id) "+
		"       LEFT JOIN EXAM_internal_mark_supplementary_details " +
		"          ON (EXAM_internal_mark_supplementary_details.student_id = " +
		"                 student.id) " +
		"             AND (EXAM_internal_mark_supplementary_details.subject_id = " +
		"                     subject.id) " +
		"             AND (EXAM_internal_mark_supplementary_details.class_id = " +
		"                     classes.id) " +
		"             AND if((EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id or classes.course_id=18), "+ /*if for the current exam record available or BBA take current exam, else take from the previous last exam*/
		" (EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id), "+
		" (EXAM_internal_mark_supplementary_details.exam_id=(select max(e.exam_id) from "+
		" EXAM_internal_mark_supplementary_details e where e.class_id=classes.id and e.student_id=student.id and e.subject_id=subject.id and e.exam_id<=EXAM_supplementary_improvement_application.exam_id))) " +
		"       LEFT JOIN EXAM_student_pass_fail EXAM_student_pass_fail " +
		"          ON     (EXAM_student_pass_fail.student_id = student.id) " +
		"             AND (EXAM_student_pass_fail.class_id = classes.id) " +
		"             AND (EXAM_student_pass_fail.scheme_no = classes.term_number) " +
		"       LEFT JOIN EXAM_subject_rule_settings " +
		"          ON EXAM_subject_rule_settings.subject_id = subject.id " +
		"             AND EXAM_subject_rule_settings.academic_year = " +
		"                    curriculum_scheme_duration.academic_year " +
		"             AND EXAM_subject_rule_settings.course_id = classes.course_id " +
		"             AND EXAM_subject_rule_settings.scheme_no = classes.term_number " +
		"       LEFT JOIN EXAM_publish_exam_results " +
		"          ON EXAM_publish_exam_results.class_id = classes.id " +
		"             AND EXAM_publish_exam_results.exam_id = EXAM_definition.id " +
		"       LEFT JOIN EXAM_sub_definition_coursewise " +
		"          ON EXAM_sub_definition_coursewise.subject_id = subject.id " +
		"             AND EXAM_sub_definition_coursewise.course_id = classes.course_id " +
		"             AND EXAM_sub_definition_coursewise.scheme_no = " +
		"                    classes.term_number " +
		"             AND EXAM_sub_definition_coursewise.academic_year = " +
		"                    curriculum_scheme_duration.academic_year " +
		"       LEFT JOIN EXAM_subject_sections " +
		"          ON EXAM_sub_definition_coursewise.subject_section_id = " +
		"                EXAM_subject_sections.id " +

		" WHERE ((EXAM_supplementary_improvement_application.is_supplementary=1  AND ( (EXAM_supplementary_improvement_application.is_appeared_practical = 1) " +
		"	       OR (EXAM_supplementary_improvement_application.is_appeared_theory = 1)) " +
		"	      AND ((EXAM_supplementary_improvement_application.is_failed_practical = " +
		"	              1) " +
		"	           OR (EXAM_supplementary_improvement_application.is_failed_theory = " +
		"	                  1))) or (EXAM_supplementary_improvement_application.is_improvement=1" +
		"										  AND ( (EXAM_supplementary_improvement_application.is_appeared_practical = 1) " +
		"	        OR (EXAM_supplementary_improvement_application.is_appeared_theory = 1)))) " +
		
		"	      AND EXAM_supplementary_improvement_application.exam_id =" +examId +
		"       AND EXAM_subject_rule_settings.academic_year = " +
		"              curriculum_scheme_duration.academic_year " +
		"       AND classes.name = (SELECT name " +
		"                             FROM classes " +
		"                            WHERE id ="+classId+") " +
		"       AND classes.term_number =" +schemeNo+
		"       AND student.id=" +studentId+
		"       AND if(subject.is_theory_practical = 'B', " +
		"              ((EXAM_supplementary_improvement_application.is_appeared_theory =1) " +
		"               OR (EXAM_supplementary_improvement_application.is_failed_theory = 1)), TRUE) " +
		" GROUP BY student.id, EXAM_student_final_mark_details.exam_id, subject.id " +
		" UNION ALL " +
		" SELECT EXAM_student_overall_internal_mark_details.exam_id, " +
		"       EXAM_student_overall_internal_mark_details.student_id, " +
		"       EXAM_student_overall_internal_mark_details.class_id, " +
		"       EXAM_student_overall_internal_mark_details.subject_id, " +
		"       subject.code as subCode, " +
		"       subject.name as subName, " +
		"       if( " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          theory_total_mark " +
		"             IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          theory_total_mark, " +
		"          EXAM_student_overall_internal_mark_details. " +
		"          theory_total_mark) " +
		"          AS theory_total_mark, " +
		"       if( " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          theory_total_attendance_mark " +
		"             IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          theory_total_attendance_mark, " +
		"          EXAM_student_overall_internal_mark_details. " +
		"          theory_total_attendance_mark) " +
		"          AS theory_total_attendance_mark, " +
		"       if( " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          practical_total_mark " +
		"             IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          practical_total_mark, " +
		"          EXAM_student_overall_internal_mark_details. " +
		"          practical_total_mark) " +
		"          AS practical_total_mark, " +
		"       if( " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          practical_total_attendance_mark " +
		"             IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          practical_total_attendance_mark, " +
		"          EXAM_student_overall_internal_mark_details. " +
		"          practical_total_attendance_mark) " +
		"          AS practical_total_attendance_mark, " +
		"       CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id, " +
		"       EXAM_subject_rule_settings.theory_ese_minimum_mark, " +
		"       EXAM_subject_rule_settings.theory_ese_maximum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_minimum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_maximum_mark, " +
		"       (if(EXAM_student_final_mark_details.student_theory_marks IS NOT NULL, " +
		"           EXAM_student_final_mark_details.student_theory_marks, " +
		"           0)) " +
		"          AS student_theory_marks, " +
		"       (if( " +
		"           EXAM_student_final_mark_details.student_practical_marks " +
		"              IS NOT NULL, " +
		"           EXAM_student_final_mark_details.student_practical_marks, " +
		"           0)) " +
		"          AS student_practical_marks, " +
		"       personal_data.first_name, " +
		"       personal_data.middle_name, " +
		"       personal_data.last_name, " +
		"       EXAM_subject_sections.name as secName, " +
		"       EXAM_subject_sections.is_initialise, " +
		"       EXAM_subject_sections.id as secId, " +
		"       EXAM_sub_definition_coursewise.subject_order as subOrder, " +
		"       EXAM_subject_rule_settings.final_practical_internal_maximum_mark, " +
		"       EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " +
		"       (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, " +
		"           EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " +
		"           0)+ if( " +
		"             (SELECT max( " +
		"                        EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id) " +
		"                IS NOT NULL, " +
		"             (SELECT max( " +
		"                        EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id), " +
		"             (SELECT max(EXAM_attendance_marks.marks) " +
		"                FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"               WHERE course_id = classes.course_id)) + if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0, " +
		"             EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) " +
		"          AS theoryTotal, " +
		"       (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0, " +
		"           EXAM_subject_rule_settings.final_practical_internal_maximum_mark, 0)+ if( " +
		"             (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id) IS NOT NULL, " +
		"             (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id), " +
		"             (SELECT max(EXAM_attendance_marks.marks) " +
		"                FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"               WHERE course_id = classes.course_id)) + ifnull(EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) " +
		"          AS practicalTotal, " +

		"    (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,  " +
		"    (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,  " +

		"       max(EXAM_sub_definition_coursewise.practical_credit) " +
		"          AS practical_credit, " +
		"       max(EXAM_sub_definition_coursewise.theory_credit) AS theory_credit, " +
		"        ifnull( (select oldreg.register_no " +
		"   from student_old_register_numbers oldreg " +
		"   where oldreg.student_id=student.id " +
		"   and oldreg.scheme_no=classes.term_number and oldreg.is_active=1)," +
		"   student.register_no) as register_no," +
		"            if(subject.is_theory_practical = 'T', 'Theory', " +
		"          if(subject.is_theory_practical = 'P', 'Practical', " +
		"					if(subject.is_theory_practical = 'B', 'Both', 'Theory'))) " +
		"          AS subType,  " +
		"       if( " +
		"          (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"             FROM EXAM_sub_coursewise_attendance_marks " +
		"            WHERE course_id = classes.course_id " +
		"                  AND subject_id = subject.id) " +
		"             IS NOT NULL, " +
		"          (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"             FROM EXAM_sub_coursewise_attendance_marks " +
		"            WHERE course_id = classes.course_id " +
		"                  AND subject_id = subject.id), " +
		"          (SELECT max(EXAM_attendance_marks.marks) " +
		"             FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"            WHERE course_id = classes.course_id)) " +
		"          AS maxAttMarks, " +
		"       classes.term_number, " +
		"       EXAM_publish_exam_results.publish_date, " +
		"       adm_appln.course_id, " +

		"           ( round( (if(EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100)) as theoryPer,  " +
		"  (  round((if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100)) as practicalper,  " +
	
		"       ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +
		" ifnull(  (SELECT   EXAM_sub_coursewise_grade_defn.grade_point " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT   EXAM_grade_class_definition_frombatch.grade_point" +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		"  (SELECT   EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegrap, " +
		"  ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade_point " +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull(  (SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragrap,  " +
		"       EXAM_sub_definition_coursewise.dont_show_max_marks, " +
		"       EXAM_sub_definition_coursewise.dont_show_min_marks, " +
		"       EXAM_sub_definition_coursewise.show_only_grade, " +
		"       course_scheme.name, " +
		"       EXAM_sub_definition_coursewise.dont_show_sub_type, " +
		" EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		"	 EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,  " +
		"       EXAM_student_pass_fail.pass_fail, " +
		"       EXAM_supplementary_improvement_application.is_appeared_theory, " +
		"       EXAM_supplementary_improvement_application.is_appeared_practical, " +
		"       subject.is_theory_practical, " +
		"       EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark, " +
		"       EXAM_subject_rule_settings.final_theory_internal_is_attendance, " +
		"       EXAM_subject_rule_settings.final_practical_internal_is_attendance, " +
		"   EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		"   EXAM_subject_rule_settings.final_practical_internal_minimum_mark, " +
		
		 " sum((if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) + (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks ))) as subFinalObtained, "+ 
			" sum((if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)))) as subInternalMarksObtained,"+ 
			" sum((if(student_theory_marks is null,0,student_theory_marks)) +if(student_practical_marks is null,0,student_practical_marks )) as subTheoryMarksObtained, "+ 
		    " EXAM_subject_rule_settings.subject_final_maximum, "+ 
		    " EXAM_subject_rule_settings.subject_final_minimum, "+  
		    " sum((if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark is null,0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark)) +if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark is null,0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark )) as internalMaxMarks, "+ 
		    " sum((if(EXAM_subject_rule_settings.theory_ese_maximum_mark is null,0,EXAM_subject_rule_settings.theory_ese_maximum_mark)) +if(EXAM_subject_rule_settings.practical_ese_maximum_mark is null,0,EXAM_subject_rule_settings.practical_ese_maximum_mark )) as externalMaxMarks, "+ 
		    " sum((if(EXAM_subject_rule_settings.theory_ese_minimum_mark is null,0,EXAM_subject_rule_settings.theory_ese_minimum_mark)) +if(EXAM_subject_rule_settings.practical_ese_minimum_mark is null,0,EXAM_subject_rule_settings.practical_ese_minimum_mark )) as externalMinMarks, "+ 
		    " EXAM_definition.name as examName, "+
		    "    EXAM_definition.academic_year, "+
	        " adm_appln.applied_year, "+ 
	        " subject.subjectType_based_on_marks "+
			
		
		"  FROM EXAM_supplementary_improvement_application " +
		"       LEFT JOIN student " +
		"          ON (EXAM_supplementary_improvement_application.student_id = " +
		"                 student.id) " +
		"       LEFT JOIN classes " +
		"          ON (EXAM_supplementary_improvement_application.class_id = " +
		"                 classes.id) " +
		"       LEFT JOIN subject " +
		"          ON (EXAM_supplementary_improvement_application.subject_id = " +
		"                 subject.id) " +
		"       LEFT JOIN EXAM_definition " +
		"          ON (EXAM_supplementary_improvement_application.exam_id = " +
		"                 EXAM_definition.id) " +
		"       LEFT JOIN adm_appln " +
		"          ON student.adm_appln_id = adm_appln.id " +
		"       LEFT JOIN personal_data " +
		"          ON adm_appln.personal_data_id = personal_data.id " +
		"       LEFT JOIN subject_type " +
		"          ON subject.subject_type_id = subject_type.id " +
		"       LEFT JOIN EXAM_student_overall_internal_mark_details " +
		"          ON (EXAM_student_overall_internal_mark_details.student_id = " +
		"                 student.id) " +
		"             AND EXAM_student_overall_internal_mark_details.subject_id = " +
		"                    subject.id " +
		"             AND EXAM_student_overall_internal_mark_details.class_id = " +
		"                    classes.id " +
		"       LEFT JOIN class_schemewise " +
		"          ON class_schemewise.class_id = classes.id " +
		"       LEFT JOIN curriculum_scheme_duration " +
		"          ON class_schemewise.curriculum_scheme_duration_id = " +
		"                curriculum_scheme_duration.id " +
		"       LEFT JOIN curriculum_scheme " +
		"          ON curriculum_scheme_duration.curriculum_scheme_id = " +
		"                curriculum_scheme.id " +
		"       LEFT JOIN course_scheme " +
		"          ON curriculum_scheme.course_scheme_id = course_scheme.id " +
		"       LEFT JOIN EXAM_student_final_mark_details " +
		"          ON (EXAM_student_final_mark_details.class_id = classes.id) " +
		"             AND (EXAM_student_final_mark_details.exam_id = " +
		"                     EXAM_definition.id) " +
		"             AND (EXAM_student_final_mark_details.student_id = student.id) " +
		"             AND (EXAM_student_final_mark_details.subject_id = subject.id) " +
		"       LEFT JOIN EXAM_internal_mark_supplementary_details " +
		"          ON (EXAM_internal_mark_supplementary_details.student_id = " +
		"                 student.id) " +
		"             AND (EXAM_internal_mark_supplementary_details.subject_id = " +
		"                     subject.id) " +
		"             AND (EXAM_internal_mark_supplementary_details.class_id = " +
		"                     classes.id) " +
		"             AND if((EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id or classes.course_id=18), "+ /*if for the current exam record available or BBA take current exam, else take from the previous last exam*/
		" (EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id), "+
		" (EXAM_internal_mark_supplementary_details.exam_id=(select max(e.exam_id) from "+
		" EXAM_internal_mark_supplementary_details e where e.class_id=classes.id and e.student_id=student.id and e.subject_id=subject.id and e.exam_id<=EXAM_supplementary_improvement_application.exam_id))) " +
		"       LEFT JOIN EXAM_student_pass_fail EXAM_student_pass_fail " +
		"          ON     (EXAM_student_pass_fail.student_id = student.id) " +
		"             AND (EXAM_student_pass_fail.class_id = classes.id) " +
		"             AND (EXAM_student_pass_fail.scheme_no = classes.term_number) " +
		"       LEFT JOIN EXAM_subject_rule_settings " +
		"          ON EXAM_subject_rule_settings.subject_id = subject.id " +
		"             AND EXAM_subject_rule_settings.academic_year = " +
		"                    curriculum_scheme_duration.academic_year " +
		"             AND EXAM_subject_rule_settings.course_id = classes.course_id " +
		"             AND EXAM_subject_rule_settings.scheme_no = classes.term_number " +
		"       LEFT JOIN EXAM_publish_exam_results " +
		"          ON EXAM_publish_exam_results.class_id = classes.id " +
		"             AND EXAM_publish_exam_results.exam_id = EXAM_definition.id " +
		"       LEFT JOIN EXAM_sub_definition_coursewise " +
		"          ON EXAM_sub_definition_coursewise.subject_id = subject.id " +
		"             AND EXAM_sub_definition_coursewise.course_id = classes.course_id " +
		"             AND EXAM_sub_definition_coursewise.scheme_no = " +
		"                    classes.term_number " +
		"             AND EXAM_sub_definition_coursewise.academic_year = " +
		"                    curriculum_scheme_duration.academic_year " +
		"       LEFT JOIN EXAM_subject_sections " +
		"          ON EXAM_sub_definition_coursewise.subject_section_id = " +
		"                EXAM_subject_sections.id " +
		
		" WHERE ((EXAM_supplementary_improvement_application.is_supplementary=1  AND ( (EXAM_supplementary_improvement_application.is_appeared_practical = 1) " +
		"	       OR (EXAM_supplementary_improvement_application.is_appeared_theory = 1)) " +
		"	      AND ((EXAM_supplementary_improvement_application.is_failed_practical = " +
		"	              1) " +
		"	           OR (EXAM_supplementary_improvement_application.is_failed_theory = " +
		"	                  1))) or (EXAM_supplementary_improvement_application.is_improvement=1" +
		"										  AND ( (EXAM_supplementary_improvement_application.is_appeared_practical = 1) " +
		"	        OR (EXAM_supplementary_improvement_application.is_appeared_theory = 1)))) " +
		"	      AND EXAM_supplementary_improvement_application.exam_id =" +examId +
		
		"       AND (subject.is_theory_practical = 'T' or subject.is_theory_practical = 'P') " +
		"       AND EXAM_student_final_mark_details.student_practical_marks " +
		"              IS NOT NULL " +
		"       AND EXAM_subject_rule_settings.academic_year = " +
		"              curriculum_scheme_duration.academic_year " +
		"       AND classes.name = (SELECT name " +
		"                             FROM classes " +
		"                            WHERE id = "+classId+") " +
		"       AND classes.term_number =" +schemeNo+
		"       AND student.id=" +studentId+
		" GROUP BY student.id, EXAM_student_final_mark_details.exam_id, subject.id " ;

		return query;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPGStudentSupMarksCard(int examId,int classId,int schemeNo,int studentId,int academicYear) throws Exception {

		String query=" SELECT EXAM_student_overall_internal_mark_details.exam_id as examId, "+
		"        EXAM_student_overall_internal_mark_details.student_id as studentId, "+
		"        EXAM_student_overall_internal_mark_details.class_id as classId, "+
		"        EXAM_student_overall_internal_mark_details.subject_id as subId, "+
		"        subject.code as subCode, "+
		"        subject.name as subName, "+
		"        if( "+
		"           EXAM_internal_mark_supplementary_details. "+
		"           theory_total_mark "+
		"              IS NOT NULL, "+
		"           EXAM_internal_mark_supplementary_details. "+
		"           theory_total_mark, "+
		"           EXAM_student_overall_internal_mark_details. "+
		"           theory_total_mark) "+
		"           AS theory_total_mark, "+
		"        if( "+
		"           EXAM_internal_mark_supplementary_details. "+
		"           theory_total_attendance_mark "+
		"              IS NOT NULL, "+
		"           EXAM_internal_mark_supplementary_details. "+
		"           theory_total_attendance_mark, "+
		"           EXAM_student_overall_internal_mark_details. "+
		"           theory_total_attendance_mark) "+
		"           AS theory_total_attendance_mark, "+
		"        if( "+
		"           EXAM_internal_mark_supplementary_details. "+
		"           practical_total_mark "+
		"              IS NOT NULL, "+
		"           EXAM_internal_mark_supplementary_details. "+
		"           practical_total_mark, "+
		"           EXAM_student_overall_internal_mark_details. "+
		"           practical_total_mark) "+
		"           AS practical_total_mark, "+
		"        if( "+
		"           EXAM_internal_mark_supplementary_details. "+
		"           practical_total_attendance_mark "+
		"              IS NOT NULL, "+
		"           EXAM_internal_mark_supplementary_details. "+
		"           practical_total_attendance_mark, "+
		"           EXAM_student_overall_internal_mark_details. "+
		"           practical_total_attendance_mark) "+
		"           AS practical_total_attendance_mark, "+
		"        CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id, "+
		"        EXAM_subject_rule_settings.theory_ese_minimum_mark, "+
		"        EXAM_subject_rule_settings.theory_ese_maximum_mark, "+
		"        EXAM_subject_rule_settings.practical_ese_minimum_mark, "+
		"        EXAM_subject_rule_settings.practical_ese_maximum_mark, "+
		"        (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks,   " +
		"        (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks,   " +
		"        personal_data.first_name, "+
		"        personal_data.middle_name, "+
		"        personal_data.last_name, "+
		"        EXAM_subject_sections.name as secName, "+
		"        EXAM_subject_sections.is_initialise, "+
		"        EXAM_subject_sections.id as secId, "+
		"        EXAM_sub_definition_coursewise.subject_order as subOrder, "+
		"        EXAM_subject_rule_settings.final_practical_internal_maximum_mark, "+
		"        EXAM_subject_rule_settings.final_theory_internal_maximum_mark, "+
		" (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal   " +
		"   ,(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal,   " +
		"   (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,   " +
		"   (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,   " +
		
		"        max(EXAM_sub_definition_coursewise.practical_credit) "+
		"           AS practical_credit, "+
		"        max(EXAM_sub_definition_coursewise.theory_credit) AS theory_credit, "+
		"       ifnull( (select oldreg.register_no " +
		"   from student_old_register_numbers oldreg " +
		"   where oldreg.student_id=student.id " +
		"   and oldreg.scheme_no=classes.term_number and oldreg.is_active=1)," +
		"   student.register_no ) as register_no," +
		"        if(subject.is_theory_practical = 'T', "+
		"           'Theory', "+
		"           if(subject.is_theory_practical = 'P', 'Practical', 'Theory')) "+
		"           AS name, "+
		"        if( "+
		"           (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) "+
		"              FROM EXAM_sub_coursewise_attendance_marks "+
		"             WHERE course_id = classes.course_id "+
		"                   AND subject_id = subject.id) "+
		"              IS NOT NULL, "+
		"           (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) "+
		"              FROM EXAM_sub_coursewise_attendance_marks "+
		"             WHERE course_id = classes.course_id "+
		"                   AND subject_id = subject.id), "+
		"           (SELECT max(EXAM_attendance_marks.marks) "+
		"              FROM EXAM_attendance_marks EXAM_attendance_marks "+
		"             WHERE course_id = classes.course_id)) "+
		"           AS maxAttMarks, "+
		"        classes.term_number as termNo, "+
		"        EXAM_publish_exam_results.publish_date, "+
		"        adm_appln.course_id as cid, "+

		"          (  (if(EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer,   " +
		" (  (if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper,   " +

		"       ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where (  (if(if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark)>0,if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark),0)+ifnull(max(student_theory_marks), 0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where (  (if(if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark)>0,if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark),0)+ifnull(max(student_theory_marks), 0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade FROM EXAM_grade_class_definition EXAM_grade_class_definition where (  (if(if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark)>0,if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark),0)+ifnull(max(student_theory_marks), 0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT EXAM_sub_coursewise_grade_defn.grade FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn  where (  (if(if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark),0)+ifnull(max(student_practical_marks), 0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch  where (  (if(if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark),0)+ifnull(max(student_practical_marks), 0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition  where (  (if(if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark),0)+ifnull(max(student_practical_marks), 0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +
		" ifnull( (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where (  (if(if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark)>0,if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark),0)+ifnull(max(student_theory_marks), 0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where (  (if(if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark)>0,if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark),0)+ifnull(max(student_theory_marks), 0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade_point " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where (  (if(if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark)>0,if(EXAM_internal_mark_supplementary_details.theory_total_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_mark,EXAM_student_overall_internal_mark_details.theory_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.theory_total_attendance_mark,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark),0)+ifnull(max(student_theory_marks), 0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegrap, " +
		" ifnull( (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn  where (  (if(if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark),0)+ifnull(max(student_practical_marks), 0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+academicYear+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade_point " +
		" FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch  where (  (if(if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark),0)+ifnull(max(student_practical_marks), 0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		"   (SELECT  EXAM_grade_class_definition.grade_point " +
		"  FROM EXAM_grade_class_definition EXAM_grade_class_definition  where (  (if(if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark),0)+if(if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) > 0,if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark is not null,EXAM_internal_mark_supplementary_details.practical_total_attendance_mark,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark),0)+ifnull(max(student_practical_marks), 0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragrap, " +

		"   EXAM_sub_definition_coursewise.dont_show_max_marks,   " +
		"   EXAM_sub_definition_coursewise.dont_show_min_marks,   " +
		"   EXAM_sub_definition_coursewise.show_only_grade,   " +
		"   course_scheme.name as csName,   " +
		"   EXAM_sub_definition_coursewise.dont_show_sub_type,   " +
		"   EXAM_sub_definition_coursewise.dont_consider_failure_total_result,   " +
		"   if((EXAM_sub_definition_coursewise.dont_consider_failure_total_result=1 and EXAM_sub_definition_coursewise.dont_consider_failure_total_result is not null and subject.id<>902 and (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks)))<60),1,0) as certificate,   " +
		"   subject.is_theory_practical, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		"	 EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result, " +
		"   EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		"   EXAM_subject_rule_settings.final_practical_internal_minimum_mark, " +
		 " subject_type.id as subTypeId , "+
	    "  EXAM_definition.academic_year, "+
        " adm_appln.applied_year, "+
        " subject.subjectType_based_on_marks "+
       
		"   FROM EXAM_supplementary_improvement_application "+
		"        LEFT JOIN student "+
		"           ON (EXAM_supplementary_improvement_application.student_id = "+
		"                  student.id) "+
		"        LEFT JOIN classes "+
		"           ON (EXAM_supplementary_improvement_application.class_id = "+
		"                  classes.id) "+
		"        LEFT JOIN subject "+
		"           ON (EXAM_supplementary_improvement_application.subject_id = "+
		"                  subject.id) "+
		"        LEFT JOIN EXAM_definition "+
		"           ON (EXAM_supplementary_improvement_application.exam_id = "+
		"                  EXAM_definition.id) "+
		"        LEFT JOIN adm_appln "+
		"           ON student.adm_appln_id = adm_appln.id "+
		"        LEFT JOIN personal_data "+
		"           ON adm_appln.personal_data_id = personal_data.id "+
		"        LEFT JOIN subject_type "+
		"           ON subject.subject_type_id = subject_type.id "+
		"        LEFT JOIN EXAM_student_overall_internal_mark_details "+
		"           ON (EXAM_student_overall_internal_mark_details.student_id = "+
		"                  student.id) "+
		"              AND EXAM_student_overall_internal_mark_details.subject_id = "+
		"                     subject.id "+
		"              AND EXAM_student_overall_internal_mark_details.class_id = "+
		"                     classes.id "+
		"        LEFT JOIN class_schemewise "+
		"           ON class_schemewise.class_id = classes.id "+
		"        LEFT JOIN curriculum_scheme_duration "+
		"           ON class_schemewise.curriculum_scheme_duration_id = "+
		"                 curriculum_scheme_duration.id "+
		"        LEFT JOIN curriculum_scheme "+
		"           ON curriculum_scheme_duration.curriculum_scheme_id = "+
		"                 curriculum_scheme.id "+
		"        LEFT JOIN course_scheme "+
		"           ON curriculum_scheme.course_scheme_id = course_scheme.id "+
		"        inner JOIN EXAM_student_final_mark_details "+
		"           ON     (EXAM_student_final_mark_details.class_id = classes.id) "+
		"              AND (EXAM_student_final_mark_details.student_id = student.id) "+
		"              AND (EXAM_student_final_mark_details.subject_id = subject.id) "+
		"              AND (EXAM_student_final_mark_details.exam_id = EXAM_definition.id) "+
		"              AND if(EXAM_supplementary_improvement_application. "+
		"                     is_supplementary = 1, "+
		"                     (EXAM_student_final_mark_details. "+
		"                      exam_id = EXAM_definition.id), "+
		"                     TRUE) "+
		"        LEFT JOIN EXAM_internal_mark_supplementary_details "+
		"           ON (EXAM_internal_mark_supplementary_details.student_id = "+
		"                  student.id) "+
		"              AND (EXAM_internal_mark_supplementary_details.subject_id = "+
		"                      subject.id) "+
		"              AND (EXAM_internal_mark_supplementary_details.class_id = "+
		"                      classes.id) "+
		"             AND if((EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id or classes.course_id=18), "+ /*if for the current exam record available or BBA take current exam, else take from the previous last exam*/
		" (EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id), "+
		" (EXAM_internal_mark_supplementary_details.exam_id=(select max(e.exam_id) from "+
		" EXAM_internal_mark_supplementary_details e where e.class_id=classes.id and e.student_id=student.id and e.subject_id=subject.id and e.exam_id<=EXAM_supplementary_improvement_application.exam_id))) " +
		"        LEFT JOIN EXAM_student_pass_fail EXAM_student_pass_fail "+
		"           ON     (EXAM_student_pass_fail.student_id = student.id) "+
		"              AND (EXAM_student_pass_fail.class_id = classes.id) "+
		"              AND (EXAM_student_pass_fail.scheme_no = classes.term_number) "+
		"        LEFT JOIN EXAM_subject_rule_settings "+
		"           ON EXAM_subject_rule_settings.subject_id = subject.id "+
		"              AND EXAM_subject_rule_settings.academic_year = "+
		"                     curriculum_scheme_duration.academic_year "+
		"              AND EXAM_subject_rule_settings.course_id = classes.course_id "+
		"              AND EXAM_subject_rule_settings.scheme_no = classes.term_number "+
		"        LEFT JOIN EXAM_publish_exam_results "+
		"           ON EXAM_publish_exam_results.class_id = classes.id "+
		"              AND EXAM_publish_exam_results.exam_id = EXAM_definition.id "+
		"        LEFT JOIN EXAM_sub_definition_coursewise "+
		"           ON EXAM_sub_definition_coursewise.subject_id = subject.id "+
		"              AND EXAM_sub_definition_coursewise.course_id = classes.course_id "+
		"              AND EXAM_sub_definition_coursewise.scheme_no = "+
		"                     classes.term_number "+
		"              AND EXAM_sub_definition_coursewise.academic_year = "+
		"                     curriculum_scheme_duration.academic_year "+
		"        LEFT JOIN EXAM_subject_sections "+
		"           ON EXAM_sub_definition_coursewise.subject_section_id = "+
		"                 EXAM_subject_sections.id "+
		"  WHERE (EXAM_supplementary_improvement_application.is_appeared_practical = 1 "+
		"         OR EXAM_supplementary_improvement_application.is_appeared_theory = 1) "+

		"        AND student.id NOT IN "+
		"               (SELECT EXAM_update_exclude_withheld.student_id "+
		"                  FROM EXAM_update_exclude_withheld EXAM_update_exclude_withheld "+
		"                 WHERE EXAM_update_exclude_withheld.exclude_from_results = 1 "+
		"                       AND EXAM_update_exclude_withheld.exam_id = " +examId+
		" ) and EXAM_supplementary_improvement_application.exam_id =" +examId+
		" and classes.name = (select name from classes where id=" +classId+
		" )  and classes.term_number=" +schemeNo+
		" and EXAM_subject_rule_settings.scheme_no=" +schemeNo+
		" and student.id =" +studentId+
		" group by student.id,EXAM_definition.id,subject.id";
		return query;
	}
	/**
	 * @param ugMarksCardData
	 * @return
	 * @throws Exception
	 */
	public MarksCardTO getSupMarksCardForUg(List<Object[]> ugMarksCardData,int schemeNo,int sid,Map<Integer,byte[]> studentPhotos,HttpServletRequest request, int examinationId,Map<Integer,String> revaluationSubjects) throws Exception {
		//new logic is implementing down
		String certificateCourseQuery="select s.subject.id,s.isOptional from StudentCertificateCourse s where s.isCancelled=0 " +
		" and  s.student.id=" +sid+
		" and s.schemeNo="+schemeNo;
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		List certificateList=txn.getDataForQuery(certificateCourseQuery);
		Map<Integer,Boolean> certificateMap=getCertificateSubjectMap(certificateList);
		List<Integer> subjectIdList = new ArrayList<Integer>();
		//old code 
		MarksCardTO to=null;
		Map<String,List<SubjectTO>> finalsubMap=new HashMap<String, List<SubjectTO>>();
		Map<String,Map<Integer,SubjectTO>> subMap=new HashMap<String, Map<Integer,SubjectTO>>();
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		if(ugMarksCardData!=null && !ugMarksCardData.isEmpty()){
			Iterator<Object[]> itr=ugMarksCardData.iterator();
			int cid=0;
			int examId=0;
			int studentId=0;
			int totalMarksAwarded=0;
			int totalMaxMarks=0;
			int totalCredits=0;
			double tcForCal=0;
			double gpForCal=0;
			double totalgradepoints=0;
			int totcredits=0;
			String resultClass="Pass";
			boolean isFailed=false;
			boolean isWithHeld=false;
			boolean isReappear=false;
			int academicYear=0;
			int appliedYear=0;
			// Getting Grade for Fail from database
			String gradeForFail="";
			List<String> gradeList=transaction.getStudentHallTicket("SELECT EXAM_exam_settings.grade_for_fail FROM EXAM_exam_settings EXAM_exam_settings");
			if(gradeList!=null && !gradeList.isEmpty()){
				gradeForFail=gradeList.get(0);
			}
			int star=0;
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				String ciaMax="";
				boolean isDisplay=true;
				boolean isAdd=true;
				boolean isTheoryStar=false;
				boolean isPracticalStar=false;
				boolean showSubType=true;
				boolean showMaxMarks=true;
				boolean showMinMarks=true;
				boolean showAttMarks=true;
				boolean displaySubject=true;
				boolean certificateCourse=false;
				boolean isOptional=false;
				boolean dontConsiderFail=false;
				boolean isAppearedTheory=false;
				boolean isAppearedPractical=false;
				
				if(obj[72]!=null)
					academicYear = Integer.parseInt(obj[72].toString());
				if(obj[73]!=null)
					appliedYear = Integer.parseInt(obj[73].toString());
				if(obj[3]!=null)
					subjectIdList.add(Integer.parseInt(obj[3].toString()));
		
				if(obj[54]!=null && obj[54].toString().equalsIgnoreCase("1")){
					isAppearedTheory=true;
				}
				if(obj[55]!=null && obj[55].toString().equalsIgnoreCase("1")){
					isAppearedPractical=true;
				}
				// certificate Course Has to be done
				if(obj[51]!=null && obj[51].toString().equalsIgnoreCase("1")){
					certificateCourse=true;
					if(certificateMap.containsKey(Integer.parseInt(obj[3].toString()))){
						isOptional=certificateMap.get(Integer.parseInt(obj[3].toString()));
					}else{
						isOptional=true;
					}
				}
				if(obj[52]!=null && obj[52].toString().equalsIgnoreCase("1")){
					dontConsiderFail=true;
				}

				// Calculating Grade For a Subject according to type
				String gradeForSubject="";
				if(obj[33]!=null && obj[20]!=null){
					if(!obj[33].toString().equalsIgnoreCase("Practical") && obj[15]!=null && isAppearedTheory){
						double min=0;
						double stu=0;
						if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
							min=Double.parseDouble(obj[11].toString());
						if(certificateCourse && isOptional)
							min=60;

						if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
							stu=Double.parseDouble(obj[15].toString());

						if(obj[15]!=null && !CommonUtil.isValidDecimal(obj[15].toString())){
							if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
								if(!dontConsiderFail)
									resultClass="Fail";
								gradeForSubject=gradeForFail;
							}else{
								if(obj[40]!=null)
									gradeForSubject=obj[40].toString();
								if(certificateCourse){
									displaySubject=false;
								}
							}	
						}else if(obj[40]!=null && obj[40].toString().equalsIgnoreCase("F") || min>stu){
							if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
								if(!dontConsiderFail)
									resultClass="Fail";
								gradeForSubject=gradeForFail;
							}else{
								if(certificateCourse){
									displaySubject=false;
								}
								if(Integer.parseInt(obj[10].toString())==18 && obj[40]!=null && obj[40].toString().equalsIgnoreCase("E")){
									if(!dontConsiderFail)
										resultClass="Fail";
									gradeForSubject=obj[40].toString();
								}else{
									if(obj[40]!=null)
										gradeForSubject=obj[40].toString();
								}
							}	
						}else{
							if(Integer.parseInt(obj[10].toString())==18 && obj[40]!=null && obj[40].toString().equalsIgnoreCase("E")){
								gradeForSubject=obj[40].toString();
								if(certificateCourse){
									displaySubject=false;
								}
								if(!dontConsiderFail){
									resultClass="Fail";
								}
							} else{
								if(obj[40]!=null)
									gradeForSubject=obj[40].toString();
							}
						}
					}else{
						if(isAppearedPractical){
							double min=0;
							double stu=0;
							if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
								min=Double.parseDouble(obj[13].toString());
							if(certificateCourse && isOptional)
								min=60;
							if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
								stu=Double.parseDouble(obj[16].toString());

							if(obj[16]!=null && !CommonUtil.isValidDecimal(obj[16].toString())){
								if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
									if(!dontConsiderFail)
										resultClass="Fail";
									gradeForSubject=gradeForFail;
								}else{
									if(obj[41]!=null)
										gradeForSubject=obj[41].toString();
									if(certificateCourse){
										displaySubject=false;
									}
								}	
							}else if(obj[41]!=null && obj[41].toString().equalsIgnoreCase("F") || min>stu){
								if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
									if(!dontConsiderFail)
										resultClass="Fail";
									gradeForSubject=gradeForFail;
								}else{
									if(certificateCourse){
										displaySubject=false;
									}
									if(Integer.parseInt(obj[10].toString())==18 && obj[41]!=null && !obj[41].toString().equalsIgnoreCase("E")){
										if(!dontConsiderFail)
											resultClass="Fail";
										gradeForSubject=obj[41].toString();
									}else{
										gradeForSubject=obj[41].toString();
									}
								}	
							}else{
								if(Integer.parseInt(obj[10].toString())==18 && obj[41]!=null && obj[41].toString().equalsIgnoreCase("E")){
									gradeForSubject=obj[41].toString();
									if(certificateCourse){
										displaySubject=false;
									}if(!dontConsiderFail){
										resultClass="Fail";
									}
								} else{
									if(obj[41]!=null)
										gradeForSubject=obj[41].toString();
								}
							}
						}
					}
				}
				if(Integer.parseInt(obj[10].toString())==18 && gradeForSubject.equalsIgnoreCase("F")){
					gradeForSubject="E";
				}


				String starQuery="SELECT EXAM_marks_entry_details.is_retest,  " +
				" EXAM_marks_entry.exam_id, " +
				" EXAM_marks_entry.student_id," +
				" EXAM_marks_entry_details.subject_id," +
				" if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.is_retest,0) as thRetest," +
				" if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.is_retest,0) as praRetest" +
				" FROM    (   EXAM_marks_entry EXAM_marks_entry" +
				" INNER JOIN  EXAM_exam_internal_exam_details EXAM_exam_internal_exam_details " +
				" ON (EXAM_marks_entry.exam_id = EXAM_exam_internal_exam_details.internal_exam_name_id))  INNER JOIN" +
				" EXAM_marks_entry_details EXAM_marks_entry_details ON (EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id)" +
				" where EXAM_exam_internal_exam_details.exam_id = " +obj[0].toString()+
				" and EXAM_marks_entry.student_id = " +obj[1].toString()+
				" and EXAM_marks_entry_details.subject_id = " +obj[3].toString()+
				" and is_retest = 1";
				List<Object[]> slist=transaction.getStudentHallTicket(starQuery);
				if(slist!=null && !slist.isEmpty()){
					Iterator<Object[]> sItr=slist.iterator();
					while (sItr.hasNext()) {
						Object[] s = (Object[]) sItr.next();
						if(s[4]!=null){
							if(!s[4].toString().isEmpty() && Integer.parseInt(s[4].toString())==1){
								star=star+1;
								isTheoryStar=true;
							}
						}
						if(s[5]!=null){
							if(!s[5].toString().isEmpty() && Integer.parseInt(s[5].toString())==1){
								star=star+1;
								isPracticalStar=true;
							}
						}
					}
				}
				if(obj[49]!=null){
					if(Integer.parseInt(obj[49].toString())==1){
						isAdd=false;
					}
				}
				if(obj[46]!=null){
					if(Integer.parseInt(obj[46].toString())==1){
						isDisplay=false;
					}
				}
				if(obj[44]!=null){
					if(Integer.parseInt(obj[44].toString())==1){
						showMaxMarks=false;
					}
				}
				if(obj[45]!=null){
					if(Integer.parseInt(obj[45].toString())==1){
						showMinMarks=false;
					}
				}
				if(obj[48]!=null){
					if(Integer.parseInt(obj[48].toString())==1){
						showSubType=false;
					}
				}
				if(obj[50]!=null){
					if(Integer.parseInt(obj[50].toString())==1){
						showAttMarks=false;
					}
				}
				if(obj[33]!=null){
					if(!obj[33].toString().equalsIgnoreCase("Practical")){
						if(obj[25]!=null && !StringUtils.isEmpty(obj[25].toString()) && CommonUtil.isValidDecimal(obj[25].toString()) && Double.parseDouble(obj[25].toString())>=5 ){
							if(obj[34]!=null && obj[7]!=null){
								//ciaMax=String.valueOf((int)(Double.parseDouble(obj[25].toString())-Double.parseDouble(obj[34].toString())));
								ciaMax=String.valueOf((int)(Double.parseDouble(obj[25].toString())));
							}else{
								ciaMax=obj[25].toString();
							}
						}
					}else{
						if(obj[24]!=null && !StringUtils.isEmpty(obj[24].toString()) && CommonUtil.isValidDecimal(obj[24].toString()) && Double.parseDouble(obj[24].toString())>=5 ){
							if(obj[9]!=null && obj[34]!=null){
								ciaMax=String.valueOf((int)(Double.parseDouble(obj[24].toString())-Double.parseDouble(obj[34].toString())));
							}else{
								ciaMax=obj[24].toString();
							}
						}
					}
				}

				if(displaySubject && obj[20]!=null){// remove if it not required
					if(to==null){
						to=new MarksCardTO();
						if(obj[0]!=null){
							String monthQuery="select cast(month as unsigned) as month,year from EXAM_definition where del_is_active = 1 and id="+examinationId;
							List<Object[]> monthList=transaction.getStudentHallTicket(monthQuery);
							if(monthList!=null){
								Iterator<Object[]> mItr=monthList.iterator();
								if (mItr.hasNext()) {
									Object[] m = (Object[]) mItr.next();
									String month="";
									String year="";
									if(m[0]!=null){
										month=monthMap.get(m[0].toString());
									}
									if(m[1]!=null){
										year=m[1].toString();
									}
									to.setMonthYear(month+" "+year);
									//added by manu,for change the signature till june 2013
									if(m[0]!=null){
										to.setMonthCheck(Integer.parseInt(m[0].toString()));
									}
									if(m[1]!=null){
										to.setYearCheck(Integer.parseInt(m[1].toString()));
									}
								}
							}
						}
						if(obj[0]!=null){
							examId=Integer.parseInt(obj[0].toString());
						}
						if(obj[1]!=null){
							studentId=Integer.parseInt(obj[1].toString());
						}
						if(obj[10]!=null){
							cid=Integer.parseInt(obj[10].toString());
							to.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(cid,"Course",true,"name"));
							//						String query="select c.program.name,c.name,c.program.code from Course c where c.isActive=1 and c.id="+cid;
							//						List<Object[]> clist=transaction.getDataByHql(query);
							//						if(clist!=null && !clist.isEmpty()){
							//							Iterator<Object[]> citr=clist.iterator();
							//							while (citr.hasNext()) {
							//								Object[] c = (Object[]) citr.next();
							//								if(c[1]!=null){
							//									String cname=c[1].toString();
							//									if(cname.contains("Honors")){
							//										to.setCourseName(/*c[0].toString()+"        "+*/c[1].toString());
							//									}else if(c[2]!=null && c[2].toString().equalsIgnoreCase("B.Tech")){
							//										to.setCourseName(c[2].toString()+" in "+c[1].toString());
							//									}else if(c[0]!=null && !c[0].toString().equalsIgnoreCase("BBM_BBA") && !c[0].toString().equalsIgnoreCase("LLB")){
							//										to.setCourseName(c[0].toString());
							//									}else{
							//										to.setCourseName(c[1].toString());
							//									}
							//								}
							//							}
							//						}
						}

						if(obj[17]!=null){
							to.setStudentName(obj[17].toString());
						}
						if(obj[18]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[18].toString());
						}
						if(obj[19]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[19].toString());
						}
						if(obj[32]!=null){
							to.setRegNo(obj[32].toString());
						}
						if(obj[35]!=null){
							to.setSemNo(semMap.get(obj[35].toString()));
						}
						if(obj[36]!=null){
							to.setDate(CommonUtil.ConvertStringToDateFormat(obj[36].toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(obj[47]!=null){
							to.setSemType(obj[47].toString());
						}
						if(obj[35]!=null){
							to.setSemNo(semMap.get(obj[35].toString()));
							to.setSemString(semesterMap.get(obj[35].toString()));
						}
						if(obj[71]!=null){
							to.setExamName(obj[71].toString());
						}
						to.setUgorpg("DEGREE");
						
						if(obj[20]!=null){
							Map<Integer,SubjectTO> subList=null;
							if(certificateCourse && !isOptional){
								if(subMap.containsKey(NON_CORE_ELECTIVE))
									subList=subMap.remove(NON_CORE_ELECTIVE);
								else
									subList=new HashMap<Integer,SubjectTO>();
							}else if(subMap.containsKey(obj[20].toString())){
								subList=subMap.remove(obj[20].toString());
							}else{
								subList=new HashMap<Integer,SubjectTO>();
							}



							SubjectTO subto=null;
							if(subList.containsKey(Integer.parseInt(obj[3].toString()))){
								subto=subList.remove(Integer.parseInt(obj[3].toString()));
							}else{
								subto=new SubjectTO();
							}
							subto.setId(Integer.parseInt(obj[3].toString()));

							subto.setDisplaySubject(displaySubject);
							if(obj[4]!=null){
								subto.setCode(obj[4].toString());
							}
							if(obj[5]!=null){
								subto.setName(obj[5].toString());
							}
							if(obj[23]!=null){
								subto.setSubOrder(Integer.parseInt(obj[23].toString()));
							}
							if(obj[33]!=null){
								if(obj[33].toString().equalsIgnoreCase("Theory")){
									subto.setAppearedTheory(isAppearedTheory);
								}
								if(obj[33].toString().equalsIgnoreCase("Practical")){
									subto.setAppearedPractical(isAppearedPractical);
								}
								if(obj[33].toString().equalsIgnoreCase("Theory") && isAppearedTheory){
									if(showSubType)
										subto.setType("Theory");

									subto.setGrade(gradeForSubject);

									subto.setTheory(true);
									int ciaMarksAwarded=0;
									if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
										if(isDisplay){
											if(ciaMarksAwarded>=10){
												if(!isTheoryStar)
													subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
											}else{
												if(!isTheoryStar)
													subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
											}
										}else{
											subto.setCiaMarksAwarded("-");
										}
									}else{
										if(isDisplay){
											subto.setCiaMarksAwarded(String.valueOf(obj[6]));
										}else{
											subto.setCiaMarksAwarded("-");
										}
									}
									if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
										if(showAttMarks)
											subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
										else
											subto.setAttMarksAwarded("-");
									}else{
										if(showAttMarks && obj[7]!=null)
											subto.setAttMarksAwarded(String.valueOf(obj[7]));
										else
											subto.setAttMarksAwarded("-");
									}
									if(obj[34]!=null){
										if(showAttMarks  && obj[7]!=null)
											subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
										else
											subto.setAttMaxMarks("-");
									}
									if(obj[11]!=null){
										if(showMinMarks)
											subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
										else
											subto.setEseMinMarks("-");
									}
									if(obj[12]!=null){
										if(showMaxMarks)
											subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
										else
											subto.setEseMaxMarks("-");
									}
									if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
										if(isDisplay){
											if(m>=10)
												subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
											else
												subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
										}else
											subto.setEseMarksAwarded("-");
									}else{
										if(isDisplay && obj[15]!=null)
											subto.setEseMarksAwarded(String.valueOf(obj[15]));
										else
											subto.setEseMarksAwarded("-");
									}
									if(isDisplay){
										if(!ciaMax.isEmpty()){
											subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
										}else{
											subto.setCiaMaxMarks("-");
										}
									}else{
										subto.setCiaMaxMarks("-");
									}
									//if(obj[26]!=null){
										int maxMarks=0;	
										if(isAdd){
											//totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
											if(subto.getCiaMaxMarks()!=null && !subto.getCiaMaxMarks().isEmpty() && !subto.getCiaMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getCiaMaxMarks());
											}
											if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().isEmpty() && !subto.getEseMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getEseMaxMarks());
											}
											if(subto.getAttMaxMarks()!=null && !subto.getAttMaxMarks().isEmpty() && !subto.getAttMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getAttMaxMarks());
											}
											totalMaxMarks=totalMaxMarks+maxMarks;
										}
										if(showMaxMarks)
											subto.setTotalMaxMarks(String.valueOf(maxMarks));
										else
											subto.setTotalMaxMarks("-");
									//}
									if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
										if(isDisplay){
											if(totalMarksAwarded>=10)
												subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
											else
												subto.setTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
										}else
											subto.setTotalMarksAwarded("-");
									}else{
										if(isDisplay && obj[28]!=null)
											subto.setTotalMarksAwarded(obj[28].toString());
										else
											subto.setTotalMarksAwarded("-");
									}
									// Ashwini added
									
									if(obj[6]!=null && obj[61]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
										if((ciaMarksAwarded>=(new BigDecimal(obj[61].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[61].toString())).intValue()) &&
												((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
											if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setGrade(obj[40].toString());
												if(obj[41]!=null){
													subto.setGradePoints(obj[40].toString());
												}
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setTheoryGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									} else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("REAPPEAR");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isReappear=true;
									}
								

									if(obj[31]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
										if(subto.getGrade()!=null){
											if(obj[11]!=null && obj[15]!=null){
												if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
													if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
													{
														if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
															totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
														}
													}
												}
											}else{
												if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H")){
													totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
												}
											}
											tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
											if(obj[42]!=null){
												double min=0;
												double stu=0;
												if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
													min=0;
												if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
													stu=0;
												double d=0;
												if(!CommonUtil.isValidDecimal(obj[15].toString())){
													d=0;
												}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
													d=0;
												}else{
													d=Double.parseDouble((obj[42].toString()))*Double.parseDouble(obj[31].toString());
												}
												gpForCal=gpForCal+d;
											}
										}
									}
									if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
										subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
										subto.setRevaluationReq(false);
									}else{
										if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
											subto.setRevaluationReq(true);
									}
								}
								if(obj[33].toString().equalsIgnoreCase("Practical") && isAppearedPractical){
									subto.setPractical(true);
									if(showSubType)
										subto.setSubjectType("Practical");

									subto.setPracticalGrade(gradeForSubject);

									int ciaMarksAwarded=0;
									if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
										if(isDisplay){
											if(ciaMarksAwarded>=10){
												if(!isPracticalStar)
													subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
											}else{
												if(!isPracticalStar)
													subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
											}
										}else{
											subto.setPracticalCiaMarksAwarded("-");
										}
									}else{
										if(isDisplay){
											subto.setPracticalCiaMarksAwarded(String.valueOf(obj[8]));
										}else{
											subto.setPracticalCiaMarksAwarded("-");
										}
									}

									if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
										if(showAttMarks)
											subto.setPracticalAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[9].toString()))));
										else
											subto.setPracticalAttMarksAwarded("-");
									}else{
										if(showAttMarks && obj[9]!=null)
											subto.setPracticalAttMarksAwarded(obj[9].toString());
										else
											subto.setPracticalAttMarksAwarded("-");
									}
									if(obj[34]!=null){
										if(showAttMarks && obj[9]!=null)
											subto.setPracticalAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
										else
											subto.setPracticalAttMaxMarks("-");
									}
									if(obj[13]!=null){
										if(showMinMarks)
											subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
										else
											subto.setPracticalEseMinMarks("-");
									}
									if(obj[14]!=null){
										if(showMaxMarks)
											subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
										else
											subto.setPracticalEseMaxMarks("-");
									}
									if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()) && obj[14]!=null){
										int  m=(int)Math.round(Double.parseDouble(obj[16].toString()));
										if(isDisplay){
											if(m>=10)
												subto.setPracticalEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
											else
												subto.setPracticalEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
										}else
											subto.setPracticalEseMarksAwarded("-");
									}else{
										if(isDisplay && obj[16]!=null && obj[14]!=null)
											subto.setPracticalEseMarksAwarded(obj[16].toString());
										else
											subto.setPracticalEseMarksAwarded("-");	
									}
									if(isDisplay){
										if(!ciaMax.isEmpty()){
											subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
										}else{
											subto.setPracticalCiaMaxMarks("-");
										}
									}else{
										subto.setPracticalCiaMaxMarks("-");
									}
									//if(obj[27]!=null){
										int maxMarks=0;	
										if(isAdd){
											//									totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
											if(subto.getPracticalCiaMaxMarks()!=null && !subto.getPracticalCiaMaxMarks().isEmpty() && !subto.getPracticalCiaMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getPracticalCiaMaxMarks());
											}
											if(subto.getPracticalEseMaxMarks()!=null && !subto.getPracticalEseMaxMarks().isEmpty() && !subto.getPracticalEseMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getPracticalEseMaxMarks());
											}
											if(subto.getPracticalAttMaxMarks()!=null && !subto.getPracticalAttMaxMarks().isEmpty() && !subto.getPracticalAttMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getPracticalAttMaxMarks());
											}
											totalMaxMarks=totalMaxMarks+maxMarks;
										}
										if(showMaxMarks)
											subto.setPracticalTotalMaxMarks(String.valueOf(maxMarks));
										else
											subto.setPracticalTotalMaxMarks("-");
									//}
									if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
										if(isDisplay){
											if(totalMarksAwarded>=10)
												subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
											else
												subto.setPracticalTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
										}else
											subto.setPracticalTotalMarksAwarded("-");
									}else{
										if(isDisplay && obj[29]!=null)
											subto.setPracticalTotalMarksAwarded(obj[29].toString());
										else
											subto.setPracticalTotalMarksAwarded("-");
									}
									// Ashwini added
									if(obj[8]!=null && obj[54]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
										if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[62].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue()) ||
												((new BigDecimal(obj[8].toString())).intValue()==(new BigDecimal(obj[62].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()==(new BigDecimal(obj[13].toString())).intValue())){
											if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setPracticalGrade(obj[41].toString());
												if(obj[43]!=null){
													subto.setGradePoints(obj[43].toString());
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[30]!=null){
														BigDecimal ct=new BigDecimal(obj[30].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
													}
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
											}
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
										}
									}
									else if(obj[16].toString().equalsIgnoreCase("WTH") || obj[8].toString().equalsIgnoreCase("WTH")){

										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("REAPPEAR");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
									}
									
									if(obj[30]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
										if(subto.getPracticalGrade()!=null){
											if(obj[13]!=null && obj[16]!=null){
												if(CommonUtil.isValidDecimal(obj[13].toString()) && CommonUtil.isValidDecimal(obj[16].toString())){
													if(Double.parseDouble(obj[13].toString())<=Double.parseDouble(obj[16].toString()) && !subto.getPracticalGrade().equalsIgnoreCase("F"))
													{
														if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H") ){
															totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
														}
													}
												}
											}else{
												if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H")){
													totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
												}
											}
											tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
											if(obj[43]!=null){
												double min=0;
												double stu=0;
												if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
													min = Double.parseDouble(obj[13].toString());
												if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
													stu = Double.parseDouble(obj[16].toString());

												double d=0;
												if(!CommonUtil.isValidDecimal(obj[16].toString())){
													d=0;
												}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
													d=0;
												}else{
													d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
												}
												gpForCal=gpForCal+d;
											}
										}
									}
								}
								
								// both part added by Ashwini
								
								if(obj[33].toString().equalsIgnoreCase("Both")){
									if(showSubType)
										subto.setType("Theory");
									subto.setAppearedTheory(isAppearedTheory);
									subto.setGrade(gradeForSubject);

									subto.setTheory(true);
									double ciaMarksAwarded=0;
									// both theory cia and practical cia marks awarded
									if(obj[64]!=null && CommonUtil.isValidDecimal(obj[64].toString())){
										ciaMarksAwarded=ciaMarksAwarded+Math.round(Double.parseDouble(obj[64].toString()));
										if(isDisplay){
											if(ciaMarksAwarded>=10){
												if(!isTheoryStar)
													subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
											}else{
												if(!isTheoryStar)
													subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
											}
										}else{
											subto.setCiaMarksAwarded("-");
										}
									}else{
										if(isDisplay){
											subto.setCiaMarksAwarded(String.valueOf(Math.round(Double.parseDouble(obj[64].toString()))));
										}else{
											subto.setCiaMarksAwarded("-");
										}
									}
									if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
										if(showAttMarks)
											subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
										else
											subto.setAttMarksAwarded("-");
									}else{
										if(showAttMarks && obj[7]!=null)
											subto.setAttMarksAwarded(String.valueOf(obj[7]));
										else
											subto.setAttMarksAwarded("-");
									}
									if(obj[34]!=null){
										if(showAttMarks  && obj[7]!=null)
											subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
										else
											subto.setAttMaxMarks("-");
									}
									if(obj[11]!=null){
										if(showMinMarks)
											subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
										else
											subto.setEseMinMarks("-");
									}
									if(obj[69]!=null){
										if(showMaxMarks)
											subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[69].toString()))));
										else
											subto.setEseMaxMarks("-");
									}
									// both theory ese and practical ese marks awarded
									double eseMarksawarded=0;
									if(obj[65]!=null && CommonUtil.isValidDecimal(obj[65].toString())){
										int m=(int)Math.ceil(Double.parseDouble(obj[65].toString()));
										eseMarksawarded=(int)Math.ceil(Double.parseDouble(obj[65].toString()));
										if(isDisplay){
											if(m>=10)
												subto.setEseMarksAwarded(String.valueOf(Double.parseDouble(obj[65].toString())));
											else
												subto.setEseMarksAwarded("0"+String.valueOf(Double.parseDouble(obj[65].toString())));
										}
										else
											subto.setEseMarksAwarded("-");
									}else{
										if(isDisplay)
											subto.setEseMarksAwarded(String.valueOf(obj[65]));
										else
											subto.setEseMarksAwarded("-");
									}
									if(isDisplay){
										if(obj[68]!=null){
											subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[68].toString()))));
										}else{
											subto.setCiaMaxMarks("-");
										}
									}else{
										subto.setCiaMaxMarks("-");
									}
									// subject final maximum marks
									if(obj[66]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[66].toString()));
										if(showMaxMarks)
											subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[66].toString()))));
										else
											subto.setTotalMaxMarks("-");
									}
									// subject final marks awarded including both theory and practical marks awarded
									int finalMarksawarded=0;
									if(obj[63]!=null && CommonUtil.isValidDecimal(obj[63].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[63].toString()));
										finalMarksawarded=(int)Math.ceil(Double.parseDouble(obj[63].toString()));
										if(isDisplay)
											subto.setTotalMarksAwarded(String.valueOf((int)Math.ceil(Double.parseDouble(obj[63].toString()))));
										else
											subto.setTotalMarksAwarded("-");
									}else{
										if(isDisplay)
											subto.setTotalMarksAwarded(obj[63].toString());
										else
											subto.setTotalMarksAwarded("-");
									}
							
									if(obj[6]!=null && obj[61]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab")&& !obj[15].toString().equalsIgnoreCase("WTH")){
										if((eseMarksawarded>=(new BigDecimal(obj[61].toString())).intValue() ) && (new BigDecimal(obj[6].toString())).intValue()>=(new BigDecimal(obj[61].toString())).intValue()){
											if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setGrade(obj[40].toString());
												if(obj[41]!=null){
													subto.setGradePoints(obj[42].toString());
												}
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setTheoryGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														if(obj[30]!=null){
															BigDecimal practCt=new BigDecimal(obj[30].toString());
															int practCredit=practCt.intValue();
															credit += practCredit;
														}
															
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("-");
												subto.setGradePoints("-");
												subto.setCreditPoint("-");
												isFailed=true;
												isReappear=true;
											}
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									} 
									else if(obj[15].toString().equalsIgnoreCase("WTH")|| obj[6].toString().equalsIgnoreCase("WTH")){
										subto.setResultClass("WITH HELD");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isWithHeld=true;
									}else{
										subto.setResultClass("REAPPEAR");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isReappear=true;
									}
									if(obj[31]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
										if(subto.getGrade()!=null){
											if(obj[11]!=null && obj[15]!=null){
												if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
													if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
													{
														if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
															totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
														}
													}
												}
											}else{
												if(!subto.getGrade().equalsIgnoreCase("F") ){
													totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
												}
											}
											tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
											if(obj[42]!=null){
												double min=0;
												double stu=0;
												if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
													min=0;
												if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
													stu=0;
												double d=0;
												if(!CommonUtil.isValidDecimal(obj[15].toString())){
													d=0;
												}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
													d=0;
												}else{
													d=Double.parseDouble(obj[42].toString())*Double.parseDouble((obj[31].toString()));
												}
												gpForCal=gpForCal+d;
											}
										}
									}
								}
							
							}

							subList.put(Integer.parseInt(obj[3].toString()),subto);
							if(certificateCourse && !isOptional){
								subMap.put(NON_CORE_ELECTIVE, subList);
							}else{
								subMap.put(obj[20].toString(), subList);
							}
						}
					}else{
						Map<Integer,SubjectTO> subList=null;
						if(certificateCourse && !isOptional){
							if(subMap.containsKey(NON_CORE_ELECTIVE))
								subList=subMap.remove(NON_CORE_ELECTIVE);
							else
								subList=new HashMap<Integer,SubjectTO>();
						}else if(subMap.containsKey(obj[20].toString())){
							subList=subMap.remove(obj[20].toString());
						}else{
							subList=new HashMap<Integer,SubjectTO>();
						}
						SubjectTO subto=null;
						if(subList.containsKey(Integer.parseInt(obj[3].toString()))){
							subto=subList.remove(Integer.parseInt(obj[3].toString()));
						}else{
							subto=new SubjectTO();
						}
						subto.setId(Integer.parseInt(obj[3].toString()));

						subto.setDisplaySubject(displaySubject);
						if(obj[4]!=null){
							subto.setCode(obj[4].toString());
						}
						if(obj[5]!=null){
							subto.setName(obj[5].toString());
						}
						if(obj[23]!=null){
							subto.setSubOrder(Integer.parseInt(obj[23].toString()));
						}
						subto.setDisplaySubject(displaySubject);
						if(obj[33]!=null){
							if(obj[33].toString().equalsIgnoreCase("Theory")){
								subto.setAppearedTheory(isAppearedTheory);
							}
							if(obj[33].toString().equalsIgnoreCase("Practical")){
								subto.setAppearedPractical(isAppearedPractical);
							}
							if(obj[33].toString().equalsIgnoreCase("Theory")){


								if(showSubType)
									subto.setType("Theory");

								subto.setGrade(gradeForSubject);

								subto.setTheory(true);
								int ciaMarksAwarded=0;
								if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
									if(isDisplay){
										if(ciaMarksAwarded>=10){
											if(!isTheoryStar)
												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
										}else{
											if(!isTheoryStar)
												subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
										}
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}else{
									if(isDisplay){
										subto.setCiaMarksAwarded(String.valueOf(obj[6]));
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}
								if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
									if(showAttMarks)
										subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
									else
										subto.setAttMarksAwarded("-");
								}else{
									if(showAttMarks && obj[7]!=null)
										subto.setAttMarksAwarded(String.valueOf(obj[7]));
									else
										subto.setAttMarksAwarded("-");
								}
								if(obj[34]!=null){
									if(showAttMarks  && obj[7]!=null)
										subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
									else
										subto.setAttMaxMarks("-");
								}
								if(obj[11]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[12]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
									if(isDisplay){
										if(m>=10)
											subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
										else
											subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));

									}else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay && obj[15]!=null)
										subto.setEseMarksAwarded(String.valueOf(obj[15]));
									else
										subto.setEseMarksAwarded("-");
								}
								if(isDisplay){
									if(!ciaMax.isEmpty()){
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
									}else{
										subto.setCiaMaxMarks("-");
									}
								}else{
									subto.setCiaMaxMarks("-");
								}
								//if(obj[26]!=null){
									int maxMarks=0;	
									if(isAdd){
										//								totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
										if(subto.getCiaMaxMarks()!=null && !subto.getCiaMaxMarks().isEmpty() && !subto.getCiaMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getCiaMaxMarks());
										}
										if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().isEmpty() && !subto.getEseMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getEseMaxMarks());
										}
										if(subto.getAttMaxMarks()!=null && !subto.getAttMaxMarks().isEmpty() && !subto.getAttMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getAttMaxMarks());
										}
										totalMaxMarks=totalMaxMarks+maxMarks;
									}
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf(maxMarks));
									else
										subto.setTotalMaxMarks("-");
								//}
								if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
									if(isDisplay){
										if(totalMarksAwarded>=10)
											subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
										else
											subto.setTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));

									}else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay && obj[28]!=null)
										subto.setTotalMarksAwarded(obj[28].toString());
									else
										subto.setTotalMarksAwarded("-");
								}

								// Ashwini added
								
								if(obj[6]!=null && obj[61]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WTH")){
									if((ciaMarksAwarded>=(new BigDecimal(obj[61].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[61].toString())).intValue()) &&
											((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
										if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
											subto.setResultClass("PASS");
											subto.setGrade(obj[40].toString());
											if(obj[41]!=null){
												subto.setGradePoints(obj[40].toString());
											}
											if(obj[42]!=null){
												BigDecimal pnt=	new BigDecimal(obj[42].toString());
												int point=pnt.intValue();
												subto.setTheoryGradePnt(String.valueOf(point));
												if(obj[31]!=null){
													BigDecimal ct=new BigDecimal(obj[31].toString());
													int credit=ct.intValue();
													int cp=point*credit;
													subto.setCreditPoint(String.valueOf(cp));
													totalgradepoints=totalgradepoints+cp;
													totcredits=totcredits+credit;

												}
											}

										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									}
									else{
										subto.setResultClass("REAPPEAR");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isReappear=true;
									}
								} else if(obj[15].toString().equalsIgnoreCase("WTH") || obj[6].toString().equalsIgnoreCase("WTH")){
									subto.setResultClass("WITH HELD");
									subto.setGrade("-");
									subto.setGradePoints("-");
									subto.setCreditPoint("-");
									isFailed=true;
									isWithHeld=true;
								}
								else{
									subto.setResultClass("REAPPEAR");
									subto.setGrade("-");
									subto.setGradePoints("-");
									subto.setCreditPoint("-");
									isFailed=true;
									isReappear=true;
								}
							
								
								if(obj[31]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
									if(subto.getGrade()!=null){
										if(obj[11]!=null && obj[15]!=null){
											if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
												if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
												{
													if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
														totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
													}
												}
											}
										}else{
											if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H")){
												totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
										if(obj[42]!=null){
											double min=0;
											double stu=0;
											if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
												min=0;
											if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
												stu=0;
											double d=0;
											if(!CommonUtil.isValidDecimal(obj[15].toString())){
												d=0;
											}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble(obj[42].toString())*Double.parseDouble((obj[31].toString()));
											}
											gpForCal=gpForCal+d;
										}
									}
								}
								if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
									subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
									subto.setRevaluationReq(false);
								}else{
									if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
										subto.setRevaluationReq(true);
								}
							}
							if(obj[33].toString().equalsIgnoreCase("Practical")){
								subto.setPractical(true);
								if(showSubType)
									subto.setSubjectType("Practical");

								subto.setPracticalGrade(gradeForSubject);

								int ciaMarksAwarded=0;
								if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
									if(isDisplay){
										if(ciaMarksAwarded>=10){
											if(!isPracticalStar)
												subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
										}else{
											if(!isPracticalStar)
												subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
										}
									}else{
										subto.setPracticalCiaMarksAwarded("-");
									}
								}else{
									if(isDisplay){
										subto.setPracticalCiaMarksAwarded(String.valueOf(obj[8]));
									}else{
										subto.setPracticalCiaMarksAwarded("-");
									}
								}

								if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
									if(showAttMarks)
										subto.setPracticalAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[9].toString()))));
									else
										subto.setPracticalAttMarksAwarded("-");
								}else{
									if(showAttMarks && obj[9]!=null)
										subto.setPracticalAttMarksAwarded(obj[9].toString());
									else
										subto.setPracticalAttMarksAwarded("-");
								}
								if(obj[34]!=null){
									if(showAttMarks && obj[9]!=null)
										subto.setPracticalAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
									else
										subto.setPracticalAttMaxMarks("-");
								}
								if(obj[13]!=null){
									if(showMinMarks)
										subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
									else
										subto.setPracticalEseMinMarks("-");
								}
								if(obj[14]!=null){
									if(showMaxMarks)
										subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
									else
										subto.setPracticalEseMaxMarks("-");
								}
								if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
									int  m=(int)Math.round(Double.parseDouble(obj[16].toString()));
									if(isDisplay){
										if(m>=10)
											subto.setPracticalEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
										else
											subto.setPracticalEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));

									}else
										subto.setPracticalEseMarksAwarded("-");
								}else{
									if(isDisplay && obj[16]!=null)
										subto.setPracticalEseMarksAwarded(obj[16].toString());
									else
										subto.setPracticalEseMarksAwarded("-");	
								}
								if(isDisplay){
									if(!ciaMax.isEmpty()){
										subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
									}else{
										subto.setPracticalCiaMaxMarks("-");
									}
								}else{
									subto.setPracticalCiaMaxMarks("-");
								}
								//if(obj[27]!=null){
									int maxMarks=0;
									if(isAdd){
										//									totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
										if(subto.getPracticalCiaMaxMarks()!=null && !subto.getPracticalCiaMaxMarks().isEmpty() && !subto.getPracticalCiaMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getPracticalCiaMaxMarks());
										}
										if(subto.getPracticalEseMaxMarks()!=null && !subto.getPracticalEseMaxMarks().isEmpty() && !subto.getPracticalEseMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getPracticalEseMaxMarks());
										}
										if(subto.getPracticalAttMaxMarks()!=null && !subto.getPracticalAttMaxMarks().isEmpty() && !subto.getPracticalAttMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getPracticalAttMaxMarks());
										}
										totalMaxMarks=totalMaxMarks+maxMarks;
									}
									if(showMaxMarks)
										subto.setPracticalTotalMaxMarks(String.valueOf(maxMarks));
									else
										subto.setPracticalTotalMaxMarks("-");
								//}
								if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
									if(isDisplay){
										if(totalMarksAwarded>=10)
											subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
										else
											subto.setPracticalTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));

									}else
										subto.setPracticalTotalMarksAwarded("-");
								}else{
									if(isDisplay && obj[29]!=null)
										subto.setPracticalTotalMarksAwarded(obj[29].toString());
									else
										subto.setPracticalTotalMarksAwarded("-");
								}
								
								// Ashwini added
								if(obj[8]!=null && obj[54]!=null && !obj[8].toString().equalsIgnoreCase("ab") && !obj[8].toString().equalsIgnoreCase("WTH") && obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab") && !obj[16].toString().equalsIgnoreCase("WTH")){
									if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[62].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue()) ||
											((new BigDecimal(obj[8].toString())).intValue()==(new BigDecimal(obj[62].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()==(new BigDecimal(obj[13].toString())).intValue())){
										if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
											subto.setResultClass("PASS");
											subto.setPracticalGrade(obj[41].toString());
											if(obj[43]!=null){
												subto.setGradePoints(obj[43].toString());
											}

											if(obj[43]!=null){
												BigDecimal pnt=	new BigDecimal(obj[43].toString());
												int point=pnt.intValue();
												subto.setPracticalGradePnt(String.valueOf(point));
												if(obj[30]!=null){
													BigDecimal ct=new BigDecimal(obj[30].toString());
													int credit=ct.intValue();
													int cp=point*credit;
													subto.setCreditPoint(String.valueOf(cp));
													totalgradepoints=totalgradepoints+cp;
													totcredits=totcredits+credit;
												}
											}
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
										}
									}
									else{
										subto.setResultClass("REAPPEAR");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
									}
								}
								else if(obj[16].toString().equalsIgnoreCase("WTH") || obj[8].toString().equalsIgnoreCase("WTH")){

									subto.setResultClass("WITH HELD");
									subto.setGrade("-");
									subto.setGradePoints("-");
									subto.setCreditPoint("-");
									isFailed=true;
									isWithHeld=true;
								}
								else{
									subto.setResultClass("REAPPEAR");
									subto.setGrade("-");
									subto.setGradePoints("-");
									subto.setCreditPoint("-");
									isFailed=true;
								}
								
								
								if(obj[30]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
									if(subto.getPracticalGrade()!=null){
										if(obj[13]!=null && obj[16]!=null){
											if(CommonUtil.isValidDecimal(obj[13].toString()) && CommonUtil.isValidDecimal(obj[16].toString())){
												if(Double.parseDouble(obj[13].toString())<=Double.parseDouble(obj[16].toString()) && !subto.getPracticalGrade().equalsIgnoreCase("F"))
												{
													if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H") ){
														totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
													}
												}
											}
										}else{
											if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H")){
												totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
										if(obj[43]!=null){
											double min=0;
											double stu=0;
											if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
												min = Double.parseDouble(obj[13].toString());
											if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
												stu = Double.parseDouble(obj[16].toString());

											double d=0;
											if(!CommonUtil.isValidDecimal(obj[16].toString())){
												d=0;
											}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											}
											gpForCal=gpForCal+d;
										}
									}
								}
							}
					// both part
							
							if(obj[33].toString().equalsIgnoreCase("Both")){
								if(showSubType)
									subto.setType("Theory");

								subto.setGrade(gradeForSubject);
								subto.setAppearedTheory(isAppearedTheory);

								subto.setTheory(true);
								double ciaMarksAwarded=0;
								// both theory cia and practical cia marks awarded
								if(obj[64]!=null && CommonUtil.isValidDecimal(obj[64].toString())){
									ciaMarksAwarded=ciaMarksAwarded+Math.round(Double.parseDouble(obj[64].toString()));
									if(isDisplay){
										if(ciaMarksAwarded>=10){
											if(!isTheoryStar)
												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
										}else{
											if(!isTheoryStar)
												subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
										}
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}else{
									if(isDisplay){
										subto.setCiaMarksAwarded(String.valueOf(Math.round(Double.parseDouble(obj[64].toString()))));
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}
								if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
									if(showAttMarks)
										subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
									else
										subto.setAttMarksAwarded("-");
								}else{
									if(showAttMarks && obj[7]!=null)
										subto.setAttMarksAwarded(String.valueOf(obj[7]));
									else
										subto.setAttMarksAwarded("-");
								}
								if(obj[34]!=null){
									if(showAttMarks  && obj[7]!=null)
										subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
									else
										subto.setAttMaxMarks("-");
								}
								if(obj[11]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[69]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[69].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								// both theory ese and practical ese marks awarded
								double eseMarksawarded=0;
								if(obj[65]!=null && CommonUtil.isValidDecimal(obj[65].toString())){
									int m=(int)Math.ceil(Double.parseDouble(obj[65].toString()));
									eseMarksawarded=(int)Math.ceil(Double.parseDouble(obj[65].toString()));
									if(isDisplay){
										if(m>=10)
											subto.setEseMarksAwarded(String.valueOf(Double.parseDouble(obj[65].toString())));
										else
											subto.setEseMarksAwarded("0"+String.valueOf(Double.parseDouble(obj[65].toString())));
									}
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(String.valueOf(obj[65]));
									else
										subto.setEseMarksAwarded("-");
								}
								if(isDisplay){
									if(obj[68]!=null){
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[68].toString()))));
									}else{
										subto.setCiaMaxMarks("-");
									}
								}else{
									subto.setCiaMaxMarks("-");
								}
								// subject final maximum marks
								if(obj[66]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[66].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[66].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								// subject final marks awarded including both theory and practical marks awarded
								int finalMarksawarded=0;
								if(obj[63]!=null && CommonUtil.isValidDecimal(obj[63].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.ceil(Double.parseDouble(obj[63].toString()));
									finalMarksawarded=(int)Math.ceil(Double.parseDouble(obj[63].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.ceil(Double.parseDouble(obj[63].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[63].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
						
								if(obj[6]!=null && obj[61]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WTH") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab")&& !obj[15].toString().equalsIgnoreCase("WTH")){
									if((eseMarksawarded>=(new BigDecimal(obj[61].toString())).intValue() ) && (new BigDecimal(obj[6].toString())).intValue()>=(new BigDecimal(obj[61].toString())).intValue()){
										if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
											subto.setResultClass("PASS");
											subto.setGrade(obj[40].toString());
											if(obj[41]!=null){
												subto.setGradePoints(obj[42].toString());
											}
											if(obj[42]!=null){
												BigDecimal pnt=	new BigDecimal(obj[42].toString());
												int point=pnt.intValue();
												subto.setTheoryGradePnt(String.valueOf(point));
												if(obj[31]!=null){
													BigDecimal ct=new BigDecimal(obj[31].toString());
													int credit=ct.intValue();
													if(obj[30]!=null){
														BigDecimal practCt=new BigDecimal(obj[30].toString());
														int practCredit=practCt.intValue();
														credit += practCredit;
													}
														
													int cp=point*credit;
													subto.setCreditPoint(String.valueOf(cp));
													totalgradepoints=totalgradepoints+cp;
													totcredits=totcredits+credit;

												}
											}

										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("-");
											subto.setGradePoints("-");
											subto.setCreditPoint("-");
											isFailed=true;
											isReappear=true;
										}
									}
									else{
										subto.setResultClass("REAPPEAR");
										subto.setGrade("-");
										subto.setGradePoints("-");
										subto.setCreditPoint("-");
										isFailed=true;
										isReappear=true;
									}
								} 
								else if(obj[15].toString().equalsIgnoreCase("WTH")|| obj[6].toString().equalsIgnoreCase("WTH")){
									subto.setResultClass("WITH HELD");
									subto.setGrade("-");
									subto.setGradePoints("-");
									subto.setCreditPoint("-");
									isFailed=true;
									isWithHeld=true;
								}else{
									subto.setResultClass("REAPPEAR");
									subto.setGrade("-");
									subto.setGradePoints("-");
									subto.setCreditPoint("-");
									isFailed=true;
									isReappear=true;
								}
								if(obj[31]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
									if(subto.getGrade()!=null){
										if(obj[11]!=null && obj[15]!=null){
											if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
												if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
												{
													if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
														totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
													}
												}
											}
										}else{
											if(!subto.getGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
										if(obj[42]!=null){
											double min=0;
											double stu=0;
											if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
												min=0;
											if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
												stu=0;
											double d=0;
											if(!CommonUtil.isValidDecimal(obj[15].toString())){
												d=0;
											}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble(obj[42].toString())*Double.parseDouble((obj[31].toString()));
											}
											gpForCal=gpForCal+d;
										}
									}
								}
							}
						
							
						}
						subList.put(Integer.parseInt(obj[3].toString()),subto);
						if(certificateCourse && !isOptional){
							subMap.put(NON_CORE_ELECTIVE, subList);
						}else{
							subMap.put(obj[20].toString(), subList);
						}
					}
				}// remove if it not required
			}
			
			// added by ashwini for subject deff coursewise grade def
			List<ExamSubCoursewiseGradeDefnTO> ExamSubCoursewiseGradeDefnTOList = new ArrayList<ExamSubCoursewiseGradeDefnTO>();
			List<GradePointRangeBO> courseWiseGradeList = new ArrayList<GradePointRangeBO>();
			ExamSubDefinitionCourseWiseHelper helper = new ExamSubDefinitionCourseWiseHelper();
			ExamSubDefinitionCourseWiseImpl impl = new ExamSubDefinitionCourseWiseImpl();
			for (Integer subId : subjectIdList) {
				ExamSubCoursewiseGradeDefnTOList = helper.convertBOToTO_GradeDetails(impl.select_All_grade_detailsForMarksCard(subId,cid,academicYear));
				if(ExamSubCoursewiseGradeDefnTOList.size()>0)
					break;
			}
			if(ExamSubCoursewiseGradeDefnTOList!=null && ExamSubCoursewiseGradeDefnTOList.size()>0)
			to.setExamSubCoursewiseGradeDefnTOList(ExamSubCoursewiseGradeDefnTOList);
			courseWiseGradeList = UpdateExamStudentSGPAImpl.getInstance().getCoursewiseGrades(cid,appliedYear, sid);
			if(courseWiseGradeList!=null && courseWiseGradeList.size()>0)
			to.setCourseWiseGradeList(courseWiseGradeList);
			List<SubjectTO> toList = new ArrayList<SubjectTO>();
			List<SubjectTO> addOnCourse=null;
			List<SubjectTO> nonElective=null;
			boolean isAddonCourse=false;
			boolean isElective=false;
			if(!subMap.isEmpty()){
				Iterator it = subMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					Map<Integer,SubjectTO> values=(HashMap<Integer, SubjectTO>)pairs.getValue();
					if(true){
						Iterator it1 = values.entrySet().iterator();
						while(it1.hasNext()){
							Map.Entry pairvalue = (Map.Entry)it1.next();
							toList.add((SubjectTO) pairvalue.getValue());
						}
					}
					if(pairs.getKey().toString().equalsIgnoreCase(NON_CORE_ELECTIVE)){
						nonElective = new ArrayList<SubjectTO>(values.values());
						Collections.sort(nonElective,comparator);
						isElective=true;
					}else if(pairs.getKey().toString().equalsIgnoreCase("Add On Course")){
						addOnCourse = new ArrayList<SubjectTO>(values.values());
						Collections.sort(addOnCourse,comparator);
						isAddonCourse=true;
					}else{
						List<SubjectTO> list = new ArrayList<SubjectTO>(values.values());
						Collections.sort(list,comparator);
						finalsubMap.put(pairs.getKey().toString().trim(), list);
					}
				}
			}
			Map<String,List<SubjectTO>> addOnCoursesubMap=new HashMap<String, List<SubjectTO>>();
			if(isAddonCourse){
				addOnCoursesubMap.put("Add On Course", addOnCourse);
			}
			Map<String,List<SubjectTO>> nonCoreElectivesubMap=new HashMap<String, List<SubjectTO>>();
			if(isElective){
				nonCoreElectivesubMap.put(NON_CORE_ELECTIVE,nonElective);
			}
			to.setSubjectTOList(toList);
			to.setSubMap(CommonUtil.sortMapByKey(finalsubMap));
			to.setAddOnCoursesubMap(addOnCoursesubMap);
			to.setNonElectivesubMap(nonCoreElectivesubMap);

			to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
			to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
			//			to.setTotalMarksInWord(CommonUtil.numberToWord(totalMarksAwarded));
			to.setTotalMarksInWord(CommonUtil.numberToWord1(totalMarksAwarded));
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			to.setTotalCredits(String.valueOf(totalCredits));
			to.setTotalGradePoints(String.valueOf((int)Math.round(totalgradepoints)));
			if(isFailed==true){
				to.setTotalGrade("F");
				to.setTotalMarksAwarded(" ");
				to.setTotalCredits(" ");
				to.setTotalGradePoints(" ");
				if(isWithHeld ){
				to.setResultClass("WITH HELD");
				}else{
					to.setResultClass("REAPPEAR");	
				}
				to.setSgpa(" ");
			}
			else{
				if(totalgradepoints>0 && totcredits>0){
					double sgpa=(totalgradepoints/totcredits);
					to.setSgpa(String.valueOf(twoDForm.format(sgpa)));
				}
				int tcredits=(int)totcredits;
				to.setTotalCredits(String.valueOf(tcredits));
				int tcreditPoints=(int)totalgradepoints;
				to.setTotalGradePoints(String.valueOf(tcreditPoints));
				String grade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(cid, to.getSgpa(), 0,sid);
				to.setTotalGrade(grade);
				to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
				if(isWithHeld){
					to.setResultClass("WITHHELD");
				}else if(isReappear){
					to.setResultClass("REAPPEAR");
				}else{
				to.setResultClass("PASS");
				}
			}
			
			double tgpa=0;
			if(gpForCal>0 && tcForCal>0){
				tgpa=CommonUtil.Round((gpForCal/tcForCal),2);
			}
			to.setGradePoints(String.valueOf(twoDForm.format(tgpa)));

			String countQuery="select count(*) from EXAM_update_exclude_withheld " +
			" where student_id = " +studentId+
			" and exam_id ="+examId;
			int count=0;
			List<BigInteger> countList=transaction.getStudentHallTicket(countQuery);
			if(countList!=null && !countList.isEmpty()){
				BigInteger b=countList.get(0);
				count=b.intValue();
			}
			/*String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
			double agg=0;
			List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
			if(aggList!=null && !aggList.isEmpty()){
				agg=Double.parseDouble(aggList.get(0).toString());
			}*/

			if(count<1){
				if(resultClass.equalsIgnoreCase("Pass")){
					to.setResult("Completed");
				}else
					to.setResult("Failed");
				//				double r=((double)(totalMarksAwarded)/(double)(totalMaxMarks))*100;
				//				if(r>agg){
				//					String query="select result_class from EXAM_grade_class_definition where course_id="+cid+" and "+r+" between start_percentage and end_percentage";
				//					List<String> rList=transaction.getStudentHallTicket(query);
				//					if(rList!=null && !rList.isEmpty()){
				//						Iterator<String> rItr=rList.iterator();
				//						while (rItr.hasNext()) {
				//							String robj = rItr.next();
				//							if(robj!=null){
				//								if(resultClass.equalsIgnoreCase("Pass"))
				//									to.setResult(robj);
				//								else
				//									to.setResult("Failed");
				//							}
				//						}
				//					}
				//				}else{
				//					to.setResult("Failed");
				//				}
			}else{
				to.setResult("WITH HELD");
			}
			if(studentPhotos.containsKey(sid)){
				to.setPhoto(new String(studentPhotos.get(sid)));
				request.getSession().setAttribute(to.getRegNo(), studentPhotos.get(sid));
			}
		}
		return to;
	}
	/**
	 * @param pgMarksCardData
	 * @return
	 * @throws Exception
	 */
	public MarksCardTO getSupMarksCardForPg(List<Object[]> pgMarksCardData,int schemeNo,int sid,Map<Integer,byte[]> studentPhotos,HttpServletRequest request,int examinationId,Map<Integer,String> revaluationSubjects) throws Exception {
		MarksCardTO to=null;
		int cid=0;
		double totalgradepointsForSgpa=0;
		double totcreditsForSgpa=0;
		int totalMarksAwardedForSgpa = 0;
		int totalMaxMarksForSgpa = 0;
		String gradeForSgpa="";
		Map<String,List<SubjectTO>> subMap=new HashMap<String, List<SubjectTO>>();
		List<Integer> subjectIdList = new ArrayList<Integer>();
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int totalMarksAwarded=0;
			int totalMaxMarks=0;
			int totalCredits=0;
			double tcForCal=0;
			double gpForCal=0;
			String resultClass="Pass";
			int studentId=0;
			int examId=0;

			double totalgradepoints=0;
			double totcredits=0;

			boolean isFailed=false;
			boolean isWithHeld=false;
			boolean isReappear=false;
			int academicYear=0;
			int appliedYear=0;
			
			
			
			
			// Getting Grade for Fail from database
			String gradeForFail="";
			List<String> gradeList=transaction.getStudentHallTicket("SELECT EXAM_exam_settings.grade_for_fail FROM EXAM_exam_settings EXAM_exam_settings");
			if(gradeList!=null && !gradeList.isEmpty()){
				gradeForFail=gradeList.get(0);
			}

			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				boolean isDisplay=true;
				boolean isAdd=true;
				boolean showSubType=true;
				boolean showMaxMarks=true;
				boolean showMinMarks=true;
				boolean showAttMarks=true;
				boolean displaySubject=true;
				boolean certificateCourse=false;
				boolean dontConsiderFail=false;
				
				if(obj[59]!=null)
					academicYear = Integer.parseInt(obj[59].toString());
				if(obj[60]!=null)
					appliedYear = Integer.parseInt(obj[60].toString());
				if(obj[3]!=null)
					subjectIdList.add(Integer.parseInt(obj[3].toString()));

				if(obj[54]!=null && (obj[54].toString().trim().equalsIgnoreCase("1") || obj[54].toString().trim().equalsIgnoreCase("true"))){
					certificateCourse=true;
				}
				if(obj[55]!=null && (obj[55].toString().trim().equalsIgnoreCase("1") || obj[55].toString().trim().equalsIgnoreCase("true"))){
					dontConsiderFail=true;
				}
				String grade="";

				if(obj[52]!=null){
					if(obj[52].toString().trim().equals("true")){
						isAdd=false;
					}
				}
				if(obj[46]!=null){
					if(obj[46].toString().trim().equals("true")){
						isDisplay=false;
					}
				}
				if(obj[44]!=null){
					if(obj[44].toString().trim().equals("true")){
						showMaxMarks=false;
					}
				}
				if(obj[45]!=null){
					if(obj[45].toString().trim().equals("true")){
						showMinMarks=false;
					}
				}
				if(obj[48]!=null){
					if(obj[48].toString().trim().equals("true")){
						showSubType=false;
					}
				}
				if(obj[53]!=null){
					if(obj[53].toString().trim().equals("true")){
						showAttMarks=false;
					}
				}
				if(obj[33]!=null){
					if(!obj[33].toString().equalsIgnoreCase("Practical")){
						if(obj[40]!=null){
							double min=0;
							double stu=0;
							if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
								min = Double.parseDouble(obj[11].toString());
							if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
								stu = Double.parseDouble(obj[15].toString());

							if(obj[40].toString().equals("F") || min>stu){
								grade=gradeForFail;
								if(certificateCourse){
									displaySubject=false;
								}if(!dontConsiderFail){
									resultClass="Fail";
								}
							}else{
								grade=obj[40].toString();
							}
						}
					}else{

						if(obj[41]!=null){
							double min=0;
							double stu=0;
							if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
								min = Double.parseDouble(obj[13].toString());
							if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
								stu = Double.parseDouble(obj[16].toString());

							if(obj[41].toString().equals("F") ||  min>stu){
								grade=gradeForFail;
								if(certificateCourse){
									displaySubject=false;
								}
								if(!dontConsiderFail){
									resultClass="Fail";
								}
							}else{
								if(obj[41]!=null)
									grade=obj[41].toString();
							}
						}

					}
				}

				if(displaySubject){// Start of Display Subject
					if(to==null){
						to=new MarksCardTO();

						if(obj[0]!=null){
							examId=Integer.parseInt(obj[0].toString());
							String monthQuery="select cast(month as unsigned) as month,year from EXAM_definition where del_is_active = 1 and id="+obj[0].toString();
							List<Object[]> monthList=transaction.getStudentHallTicket(monthQuery);
							if(monthList!=null){
								Iterator<Object[]> mItr=monthList.iterator();
								if (mItr.hasNext()) {
									Object[] m = (Object[]) mItr.next();
									String month="";
									String year="";
									if(m[0]!=null){
										month=monthMap.get(m[0].toString());
									}
									if(m[1]!=null){
										year=m[1].toString();
									}
									to.setMonthYear(month+" "+year);
									//added by manu,for change the signature till june 2013
									if(m[0]!=null){
										to.setMonthCheck(Integer.parseInt(m[0].toString()));
									}
									if(m[1]!=null){
										to.setYearCheck(Integer.parseInt(m[1].toString()));
									}
								}
							}
						}
						if(obj[1]!=null){
							studentId=Integer.parseInt(obj[1].toString());
						}
						if(obj[10]!=null){
							cid=Integer.parseInt(obj[10].toString());
							to.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(cid,"Course",true,"name"));
						}
						if(obj[17]!=null){
							to.setStudentName(obj[17].toString());
						}
						if(obj[18]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[18].toString());
						}
						if(obj[19]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[19].toString());
						}
						if(obj[32]!=null){
							to.setRegNo(obj[32].toString());
						}
						if(obj[35]!=null){
							to.setSemNo(semMap.get(obj[35].toString()));
						}
						if(obj[36]!=null){
							to.setDate(CommonUtil.ConvertStringToDateFormat(obj[36].toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(obj[47]!=null){
							to.setSemType(obj[47].toString());
						}
						if(obj[35]!=null){
							to.setSemNo(semMap.get(obj[35].toString()));
							to.setSemString(semesterMap.get(obj[35].toString()));
						}
					/*	if(programTypeId==3)
							to.setUgorpg("MPHIL DEGREE");
						else*/
						to.setUgorpg("POSTGRADUATE DEGREE");
						if(obj[20]!=null){
							List<SubjectTO> subList=null;
							if(subMap.containsKey(obj[20].toString())){
								subList=subMap.remove(obj[20].toString());
							}else{
								subList=new ArrayList<SubjectTO>();
							}

							SubjectTO subto=new SubjectTO();
							subto.setDisplaySubject(displaySubject);
							subto.setId(Integer.parseInt(obj[3].toString()));
							if(obj[4]!=null){
								subto.setCode(obj[4].toString());
							}
							if(obj[5]!=null){
								subto.setName(obj[5].toString());
							}
							if(obj[23]!=null)
								subto.setSubOrder(Integer.parseInt(obj[23].toString()));
							if(obj[33]!=null){
								if(obj[33].toString().equalsIgnoreCase("Theory")){
									if(showSubType)
										subto.setType("Theory");
									else
										subto.setType("");
									subto.setAppearedTheory(true);
									subto.setGrade(grade);
									subto.setTheory(true);
									if(obj[31]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
										if(subto.getGrade()!=null){
											if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
										if(obj[42]!=null){
											double min=0;
											double stu=0;
											if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
												min=0;
											if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
												stu=0;
											double d=0;
											if(!CommonUtil.isValidDecimal(obj[15].toString())){
												d=0;
											}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[42].toString()))*Double.parseDouble(obj[31].toString());
											}
											gpForCal=gpForCal+d;
											/*double d=Double.parseDouble((obj[42].toString()))*Double.parseDouble((obj[31].toString()));
										gpForCal=gpForCal+d;*/
										}
									}
									int ciaMarksAwarded=0;
									if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
									}
									/*
									if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
									}*/
									if(obj[11]!=null){
										if(showMinMarks)
											subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
										else
											subto.setEseMinMarks("");
									}
									if(obj[12]!=null){
										if(showMaxMarks)
											subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
										else
											subto.setEseMaxMarks("");
									}
									//								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									//									int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
									//									if(isDisplay && m>0)
									//										subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
									//									else
									//										subto.setEseMarksAwarded("-");
									//								}else{
									//									if(isDisplay)
									//										subto.setEseMarksAwarded(obj[15].toString());
									//									else
									//										subto.setEseMarksAwarded("-");
									//								}
									if(obj[0]!=null &&  obj[2]!=null && obj[3]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.##");
										String Query="select avg(student_theory_marks) from EXAM_student_final_mark_details where exam_id="+obj[0].toString()+" and class_id="+obj[2].toString()+" and subject_id="+obj[3].toString();
										List avgList=transaction.getStudentHallTicket(Query);
										if(avgList.get(0)!=null){
											double avg=Double.parseDouble(avgList.get(0).toString());
											subto.setAvgmark(twoDForm.format(avg));

										}

									}


									if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
										if(isDisplay && subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equals("-")){
											if(m>=10)
												subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
											else
												subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
										}else
											subto.setEseMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setEseMarksAwarded(obj[15].toString());
										else
											subto.setEseMarksAwarded("");
									}
									if(obj[25]!=null){
										if(showMaxMarks)
											subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[25].toString()))));
										else
											subto.setCiaMaxMarks("");
									}
									if(obj[26]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
										if(showMaxMarks)
											subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
										else
											subto.setTotalMaxMarks("");
									}
									if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
										if(isDisplay){
											if((int)Math.round(Double.parseDouble(obj[28].toString()))>=10)
												subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
											else
												subto.setTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
										}else
											subto.setTotalMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setTotalMarksAwarded(obj[28].toString());
										else
											subto.setTotalMarksAwarded("");
									}
									//								if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
									//									if(isDisplay)
									//										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									//									else
									//										subto.setCiaMarksAwarded("-");
									//								}else{
									//									if(isDisplay){
									//										if(CommonUtil.isValidDecimal(obj[6].toString()))
									//											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									//										else
									//											subto.setCiaMarksAwarded(obj[6].toString());
									//									}else
									//										subto.setCiaMarksAwarded("-");
									//								}
									if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
										if(isDisplay){
											if(ciaMarksAwarded>=10)
												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
										}else
											subto.setCiaMarksAwarded("");
									}else{
										if(isDisplay){
											if(CommonUtil.isValidDecimal(obj[6].toString())){
												if(ciaMarksAwarded>=10)
													subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											}else
												subto.setCiaMarksAwarded(obj[6].toString());
										}else
											subto.setCiaMarksAwarded("");
									}
									if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
										subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
										subto.setRevaluationReq(false);
									}else{
										if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
											subto.setRevaluationReq(true);
									}

									if(obj[6]!=null && obj[56]!=null && !obj[6].toString().equalsIgnoreCase("ab")&& !obj[6].toString().equalsIgnoreCase("WithHeld") && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WithHeld")){
										if(((new BigDecimal(obj[6].toString())).intValue()>(new BigDecimal(obj[56].toString())).intValue() || (new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[56].toString())).intValue()) &&
												((new BigDecimal(obj[15].toString())).intValue()>(new BigDecimal(obj[11].toString())).intValue() || (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
											if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setGrade(obj[40].toString());
												if(obj[41]!=null){
													subto.setGradePoints(obj[40].toString());
												}
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setTheoryGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
													}
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("");
												subto.setGradePoints("");
												subto.setCreditPoint("");
												isFailed=true;
												isReappear=true;
											}
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("");
											subto.setGradePoints("");
											subto.setCreditPoint("");
											isFailed=true;
											isReappear=true;
										}
									} else if(obj[15].toString().equalsIgnoreCase("WithHeld") || obj[6].toString().equalsIgnoreCase("WithHeld")){
										subto.setResultClass("WITHHELD");
										subto.setGrade("");
										subto.setGradePoints("");
										subto.setCreditPoint("");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("REAPPEAR");
										subto.setGrade("");
										subto.setGradePoints("");
										subto.setCreditPoint("");
										isFailed=true;
										isReappear=true;
									}


								}
								if(obj[33].toString().equalsIgnoreCase("Practical")){
									if(showSubType)
										subto.setType("Practical");
									else
										subto.setType("");

									subto.setPracticalGrade(grade);
									subto.setTheory(false);
									subto.setPractical(true);
									subto.setAppearedPractical(true);
									if(obj[30]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
										if(subto.getPracticalGrade()!=null){
											if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
										if(obj[43]!=null){

											double min=0;
											double stu=0;
											if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
												min = Double.parseDouble(obj[13].toString());
											if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
												stu = Double.parseDouble(obj[16].toString());

											double d=0;
											if(!CommonUtil.isValidDecimal(obj[16].toString())){
												d=0;
											}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											}
											gpForCal=gpForCal+d;


											/*double d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											gpForCal=gpForCal+d;*/
										}
									}
									int ciaMarksAwarded=0;
									if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
									}
									/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
									}*/
									if(obj[13]!=null){
										if(showMinMarks)
											subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
										else
											subto.setPracticalEseMinMarks("");
									}
									if(obj[14]!=null){
										if(showMaxMarks)
											subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
										else
											subto.setPracticalEseMaxMarks("");
									}
									if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[16].toString()));
										if(isDisplay && subto.getPracticalEseMaxMarks()!=null && !subto.getPracticalEseMaxMarks().equals("-")){
											if(m>=10)
												subto.setPracticalEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
											else
												subto.setPracticalEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
										}else
											subto.setPracticalEseMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setPracticalEseMarksAwarded(obj[16].toString());
										else
											subto.setPracticalEseMarksAwarded("");
									}

									if(obj[24]!=null){
										if(showMaxMarks)
											subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[24].toString()))));
										else
											subto.setPracticalCiaMaxMarks("");
									}
									if(obj[27]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
										if(showMaxMarks)
											subto.setPracticalTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
										else
											subto.setPracticalTotalMaxMarks("");
									}
									if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
										if(isDisplay){
											if((int)Math.round(Double.parseDouble(obj[29].toString()))>=10)
												subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
											else
												subto.setPracticalTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
										}else
											subto.setPracticalTotalMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setPracticalTotalMarksAwarded(obj[29].toString());
										else
											subto.setPracticalTotalMarksAwarded("");
									}
									if(ciaMarksAwarded!=0  && obj[8]!=null && obj[9]!=null &&  CommonUtil.isValidDecimal(obj[8].toString()) && CommonUtil.isValidDecimal(obj[9].toString())){
										if(isDisplay){
											if(ciaMarksAwarded>=10)
												subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
										}else
											subto.setPracticalCiaMarksAwarded("");
									}else{

										if(isDisplay)
											if(CommonUtil.isValidDecimal(obj[8].toString())){
												//												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												if(ciaMarksAwarded>=10)
													subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											}else
												subto.setPracticalCiaMarksAwarded(obj[8].toString());
										else
											subto.setPracticalCiaMarksAwarded("");

										if(obj[0]!=null &&  obj[2]!=null && obj[3]!=null){
											DecimalFormat twoDForm = new DecimalFormat("#.##");
											String Query="select avg(student_practical_marks) from EXAM_student_final_mark_details where exam_id="+obj[0].toString()+" and class_id="+obj[2].toString()+" and subject_id="+obj[3].toString();
											List avgList=transaction.getStudentHallTicket(Query);
											if(avgList.get(0)!=null){
												double avg=Double.parseDouble(avgList.get(0).toString());
												subto.setAvgmark(twoDForm.format(avg));}

										}
										if(obj[58]!=null && !obj[58].toString().equalsIgnoreCase("18")){
											if(obj[8]!=null && obj[57]!=null && !obj[8].toString().equalsIgnoreCase("ab") && obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab")){
												if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[57].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue())){
													if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
														subto.setResultClass("PASS");
														subto.setPracticalGrade(obj[41].toString());

														if(obj[42]!=null){
															BigDecimal pnt=	new BigDecimal(obj[42].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[31]!=null){
																BigDecimal ct=new BigDecimal(obj[31].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;

															}
														}

														if(obj[43]!=null){
															BigDecimal pnt=	new BigDecimal(obj[43].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[30]!=null){
																BigDecimal ct=new BigDecimal(obj[30].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;
															}
														}
													}
													else{
														subto.setResultClass("REAPPEAR");
														subto.setPracticalGrade("");
														subto.setPracticalGradePnt("");
														subto.setCreditPoint("");
														isFailed=true;
													}
												}else{
													subto.setResultClass("REAPPEAR");
													subto.setPracticalGrade("");
													subto.setPracticalGradePnt("");
													subto.setCreditPoint("");
													isFailed=true;
												}
											} 
										}
										// Ashwini
										else if(obj[58]!=null && obj[58].toString().equalsIgnoreCase("18") && obj[16]!=null && !obj[16].toString().equalsIgnoreCase("ab") && obj[13]!=null){
												if(( (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue())){
											if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setPracticalGrade(obj[41].toString());

												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
														subto.setPracticalCiaMarksAwarded("");

													}
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[30]!=null){
														BigDecimal ct=new BigDecimal(obj[30].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
														subto.setPracticalCiaMarksAwarded("");
													}
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setPracticalGrade("");
												subto.setPracticalGradePnt("");
												subto.setCreditPoint("");
												isFailed=true;
											}
										}
										
										else{
											subto.setResultClass("REAPPEAR");
											subto.setPracticalGrade("");
											subto.setPracticalGradePnt("");
											subto.setCreditPoint("");
											isFailed=true;

										}
												
										}
										else{

											subto.setResultClass("REAPPEAR");
											subto.setPracticalGrade("");
											subto.setPracticalGradePnt("");
											subto.setCreditPoint("");
											isFailed=true;

										
											
										}
									}
								}

							}
							subList.add(subto);
							subMap.put(obj[20].toString(), subList);
						}
					}else{
						if(obj[20]!=null){
							List<SubjectTO> subList=null;
							if(subMap.containsKey(obj[20].toString())){
								subList=subMap.remove(obj[20].toString());
							}else{
								subList=new ArrayList<SubjectTO>();
							}

							SubjectTO subto=new SubjectTO();
							subto.setDisplaySubject(displaySubject);
							subto.setId(Integer.parseInt(obj[3].toString()));
							if(obj[4]!=null){
								subto.setCode(obj[4].toString());
							}
							if(obj[5]!=null){
								subto.setName(obj[5].toString());
							}
							if(obj[23]!=null)
								subto.setSubOrder(Integer.parseInt(obj[23].toString()));
							if(obj[33]!=null){
								if(obj[33].toString().equalsIgnoreCase("Theory")){
									if(showSubType)
										subto.setType("Theory");
									else
										subto.setType("");

									subto.setGrade(grade);
									subto.setTheory(true);
									subto.setAppearedTheory(true);
									if(obj[31]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
										if(subto.getGrade()!=null){
											if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
										if(obj[42]!=null){
											/*double d=Double.parseDouble((obj[42].toString()))*Double.parseDouble((obj[31].toString()));
										gpForCal=gpForCal+d;*/

											double min=0;
											double stu=0;
											if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
												min=0;
											if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
												stu=0;
											double d=0;
											if(!CommonUtil.isValidDecimal(obj[15].toString())){
												d=0;
											}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[42].toString()))*Double.parseDouble(obj[31].toString());
											}
											gpForCal=gpForCal+d;



										}
									}
									int ciaMarksAwarded=0;
									if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
									}
								/*	if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
									}*/
									if(obj[11]!=null){
										if(showMinMarks)
											subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
										else
											subto.setEseMinMarks("");
									}
									if(obj[12]!=null){
										if(showMaxMarks)
											subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
										else
											subto.setEseMaxMarks("");
									}
									if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
										if(isDisplay && subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equals("-")){
											if(m>=10)
												subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
											else
												subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
										}else
											subto.setEseMarksAwarded(" ");
									}else{
										if(isDisplay)
											subto.setEseMarksAwarded(obj[15].toString());
										else
											subto.setEseMarksAwarded(" ");
									}
									if(obj[25]!=null){
										if(showMaxMarks)
											subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[25].toString()))));
										else
											subto.setCiaMaxMarks(" ");
									}
									if(obj[26]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
										if(showMaxMarks)
											subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
										else
											subto.setTotalMaxMarks(" ");
									}
									//								if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
									//									if(isAdd)
									//									totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
									//									if(isDisplay)
									//										subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
									//									else
									//										subto.setTotalMarksAwarded("-");
									//								}else{
									//									if(isDisplay)
									//										subto.setTotalMarksAwarded(obj[28].toString());
									//									else
									//										subto.setTotalMarksAwarded("-");
									//								}
									if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
										if(isDisplay){
											if((int)Math.round(Double.parseDouble(obj[28].toString()))>=10)
												subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
											else
												subto.setTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
										}else
											subto.setTotalMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setTotalMarksAwarded(obj[28].toString());
										else
											subto.setTotalMarksAwarded("");
									}
									if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
										if(isDisplay){
											if(ciaMarksAwarded>=10)
												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
										}else
											subto.setCiaMarksAwarded("");
									}else{
										if(isDisplay){
											if(CommonUtil.isValidDecimal(obj[6].toString())){
												if(ciaMarksAwarded>=10)
													subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											}else
												subto.setCiaMarksAwarded(obj[6].toString());
										}else
											subto.setCiaMarksAwarded("");
									}
									if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
										subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
										subto.setRevaluationReq(false);
									}else{
										if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
											subto.setRevaluationReq(true);
									}

									if(obj[0]!=null &&  obj[2]!=null && obj[3]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.##");
										String Query="select avg(student_theory_marks) from EXAM_student_final_mark_details where exam_id="+obj[0].toString()+" and class_id="+obj[2].toString()+" and subject_id="+obj[3].toString();
										List  avgList=transaction.getStudentHallTicket(Query);
										if(avgList.get(0)!=null){
											double avg=Double.parseDouble(avgList.get(0).toString());
											subto.setAvgmark(twoDForm.format(avg));
										}

									}
									if(obj[6]!=null && obj[56]!=null && !obj[6].toString().equalsIgnoreCase("ab") && !obj[6].toString().equalsIgnoreCase("WithHeld")  && obj[15]!=null && obj[11]!=null && !obj[15].toString().equalsIgnoreCase("ab") && !obj[15].toString().equalsIgnoreCase("WithHeld")){
										if(((new BigDecimal(obj[6].toString())).intValue()>=(new BigDecimal(obj[56].toString())).intValue() && (new BigDecimal(obj[15].toString())).intValue()>=(new BigDecimal(obj[11].toString())).intValue()) ||
												((new BigDecimal(obj[6].toString())).intValue()==(new BigDecimal(obj[56].toString())).intValue() && (new BigDecimal(obj[15].toString())).intValue()==(new BigDecimal(obj[11].toString())).intValue())){
											if(obj[40]!=null && !obj[40].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setGrade(obj[40].toString());
												if(obj[41]!=null){
													subto.setGradePoints(obj[40].toString());
												}
												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setTheoryGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;

													}
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
													}
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setGrade("");
												subto.setGradePoints("");
												subto.setCreditPoint("");
												isFailed=true;
												isReappear=true;
											}
										}
										else{
											subto.setResultClass("REAPPEAR");
											subto.setGrade("");
											subto.setGradePoints("");
											subto.setCreditPoint("");
											isFailed=true;
											isReappear=true;
										}
									} else if(obj[15].toString().equalsIgnoreCase("WithHeld") || obj[6].toString().equalsIgnoreCase("WithHeld")){
										subto.setResultClass("WITHHELD");
										subto.setGrade(" ");
										subto.setGradePoints("");
										subto.setCreditPoint("");
										isFailed=true;
										isWithHeld=true;
									}
									else{
										subto.setResultClass("REAPPEAR");
										subto.setGrade(" ");
										subto.setGradePoints("");
										subto.setCreditPoint("");
										isFailed=true;
										isReappear=true;
									}


								}
								if(obj[33].toString().equalsIgnoreCase("Practical")){
									if(showSubType)
										subto.setType("Practical");
									else
										subto.setType("");

									subto.setPracticalGrade(grade);
									subto.setTheory(false);
									subto.setPractical(true);
									subto.setAppearedPractical(true);
									if(obj[30]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
										if(subto.getPracticalGrade()!=null){
											if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("F") ){
												totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
										if(obj[43]!=null){

											double min=0;
											double stu=0;
											if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
												min = Double.parseDouble(obj[13].toString());
											if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
												stu = Double.parseDouble(obj[16].toString());

											double d=0;
											if(!CommonUtil.isValidDecimal(obj[16].toString())){
												d=0;
											}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											}
											gpForCal=gpForCal+d;


											/*double d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											gpForCal=gpForCal+d;*/
										}
									}
									int ciaMarksAwarded=0;
									if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
									}
									/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
									}*/
									if(obj[13]!=null){
										if(showMinMarks)
											subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
										else
											subto.setPracticalEseMinMarks("");
									}
									if(obj[14]!=null){
										if(showMaxMarks)
											subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
										else
											subto.setPracticalEseMaxMarks("");
									}
									if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[16].toString()));
										if(isDisplay && subto.getPracticalEseMaxMarks()!=null && !subto.getPracticalEseMaxMarks().equals("-")){
											if(m>=10)
												subto.setPracticalEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
											else
												subto.setPracticalEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
										}else
											subto.setPracticalEseMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setPracticalEseMarksAwarded(obj[16].toString());
										else
											subto.setPracticalEseMarksAwarded("");
									}

									if(obj[24]!=null){
										if(showMaxMarks)
											subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[24].toString()))));
										else
											subto.setPracticalCiaMaxMarks("");
									}
									if(obj[27]!=null){
										if(isAdd)
											totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
										if(showMaxMarks)
											subto.setPracticalTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
										else
											subto.setPracticalTotalMaxMarks("");
									}
									if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
										if(isDisplay){
											if((int)Math.round(Double.parseDouble(obj[29].toString()))>=10)
												subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
											else
												subto.setPracticalTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
										}else
											subto.setPracticalTotalMarksAwarded("");
									}else{
										if(isDisplay)
											subto.setPracticalTotalMarksAwarded(obj[29].toString());
										else
											subto.setPracticalTotalMarksAwarded("");
									}
									if(ciaMarksAwarded!=0  && obj[8]!=null && obj[9]!=null &&  CommonUtil.isValidDecimal(obj[8].toString()) && CommonUtil.isValidDecimal(obj[9].toString())){
										if(isDisplay){
											if(ciaMarksAwarded>=10)
												subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
										}else
											subto.setPracticalCiaMarksAwarded("");
									}else{

										if(isDisplay)
											if(CommonUtil.isValidDecimal(obj[8].toString())){
												//												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												if(ciaMarksAwarded>=10)
													subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											}else
												subto.setPracticalCiaMarksAwarded(obj[8].toString());
										else
											subto.setPracticalCiaMarksAwarded("");

										if(obj[0]!=null &&  obj[2]!=null && obj[3]!=null){
											DecimalFormat twoDForm = new DecimalFormat("#.##");
											String Query="select avg(student_practical_marks) from EXAM_student_final_mark_details where exam_id="+obj[0].toString()+" and class_id="+obj[2].toString()+" and subject_id="+obj[3].toString();
											List avgList=transaction.getStudentHallTicket(Query);
											if(avgList.get(0)!=null){
												double avg=Double.parseDouble(avgList.get(0).toString());
												subto.setAvgmark(twoDForm.format(avg));}

										}
										if(obj[58]!=null && !obj[58].toString().equalsIgnoreCase("18")){
											if(obj[8]!=null && obj[57]!=null && !obj[8].toString().equalsIgnoreCase("ab") && obj[16]!=null && obj[13]!=null && !obj[16].toString().equalsIgnoreCase("ab")){
												if(((new BigDecimal(obj[8].toString())).intValue()>=(new BigDecimal(obj[57].toString())).intValue() && (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue())){
													if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
														subto.setResultClass("PASS");
														subto.setPracticalGrade(obj[41].toString());

														if(obj[42]!=null){
															BigDecimal pnt=	new BigDecimal(obj[42].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[31]!=null){
																BigDecimal ct=new BigDecimal(obj[31].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;

															}
														}

														if(obj[43]!=null){
															BigDecimal pnt=	new BigDecimal(obj[43].toString());
															int point=pnt.intValue();
															subto.setPracticalGradePnt(String.valueOf(point));
															if(obj[30]!=null){
																BigDecimal ct=new BigDecimal(obj[30].toString());
																int credit=ct.intValue();
																int cp=point*credit;
																subto.setCreditPoint(String.valueOf(cp));
																totalgradepoints=totalgradepoints+cp;
																totcredits=totcredits+credit;
															}
														}
													}
													else{
														subto.setResultClass("REAPPEAR");
														subto.setPracticalGrade("");
														subto.setPracticalGradePnt("");
														subto.setCreditPoint("");
														isFailed=true;
													}
													
												}else{
													subto.setResultClass("REAPPEAR");
													subto.setPracticalGrade("");
													subto.setPracticalGradePnt("");
													subto.setCreditPoint("");
													isFailed=true;
												}
											}
										}
										// Ashwini
										else if(obj[58]!=null && obj[58].toString().equalsIgnoreCase("18") && obj[16]!=null && !obj[16].toString().equalsIgnoreCase("ab") && obj[13]!=null){
												if(( (new BigDecimal(obj[16].toString())).intValue()>=(new BigDecimal(obj[13].toString())).intValue())){
											if(obj[41]!=null && !obj[41].toString().equalsIgnoreCase("F")){
												subto.setResultClass("PASS");
												subto.setPracticalGrade(obj[41].toString());

												if(obj[42]!=null){
													BigDecimal pnt=	new BigDecimal(obj[42].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[31]!=null){
														BigDecimal ct=new BigDecimal(obj[31].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
														subto.setPracticalCiaMarksAwarded("");

													}
												}

												if(obj[43]!=null){
													BigDecimal pnt=	new BigDecimal(obj[43].toString());
													int point=pnt.intValue();
													subto.setPracticalGradePnt(String.valueOf(point));
													if(obj[30]!=null){
														BigDecimal ct=new BigDecimal(obj[30].toString());
														int credit=ct.intValue();
														int cp=point*credit;
														subto.setCreditPoint(String.valueOf(cp));
														totalgradepoints=totalgradepoints+cp;
														totcredits=totcredits+credit;
														subto.setPracticalCiaMarksAwarded("");
													}
												}
											}
											else{
												subto.setResultClass("REAPPEAR");
												subto.setPracticalGrade("");
												subto.setPracticalGradePnt("");
												subto.setCreditPoint("");
												isFailed=true;
											}
										}
										
										else{
											subto.setResultClass("REAPPEAR");
											subto.setPracticalGrade("");
											subto.setPracticalGradePnt("");
											subto.setCreditPoint("");
											isFailed=true;

										}
												
										}else{

											subto.setResultClass("REAPPEAR");
											subto.setPracticalGrade("");
											subto.setPracticalGradePnt("");
											subto.setCreditPoint("");
											isFailed=true;

										
											
										}
									}
								}

							}
							subList.add(subto);
							subMap.put(obj[20].toString(), subList);
						}
					}
				}// End of Display Subject
			}
			
			// added by ashwini for subject deff coursewise grade def
			List<ExamSubCoursewiseGradeDefnTO> ExamSubCoursewiseGradeDefnTOList = new ArrayList<ExamSubCoursewiseGradeDefnTO>();
			List<GradePointRangeBO> courseWiseGradeList = new ArrayList<GradePointRangeBO>();
			ExamSubDefinitionCourseWiseHelper helper = new ExamSubDefinitionCourseWiseHelper();
			ExamSubDefinitionCourseWiseImpl impl = new ExamSubDefinitionCourseWiseImpl();
			for (Integer subId : subjectIdList) {
				ExamSubCoursewiseGradeDefnTOList = helper.convertBOToTO_GradeDetails(impl.select_All_grade_detailsForMarksCard(subId,cid,academicYear));
				if(ExamSubCoursewiseGradeDefnTOList.size()>0)
					break;
			}
			if(ExamSubCoursewiseGradeDefnTOList!=null && ExamSubCoursewiseGradeDefnTOList.size()>0)
			to.setExamSubCoursewiseGradeDefnTOList(ExamSubCoursewiseGradeDefnTOList);
			courseWiseGradeList = UpdateExamStudentSGPAImpl.getInstance().getCoursewiseGrades(cid,appliedYear, sid);
			if(courseWiseGradeList!=null && courseWiseGradeList.size()>0)
			to.setCourseWiseGradeList(courseWiseGradeList);

			Map<String,List<SubjectTO>> finalsubMap=new HashMap<String, List<SubjectTO>>();
			List<SubjectTO> addOnCourse=null;
			boolean isAddonCourse=false;
			List<SubjectTO> toList = new ArrayList<SubjectTO>();
			List<SubjectTO> addontoList = new ArrayList<SubjectTO>();
			if(!subMap.isEmpty()){
				Iterator it = subMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					if(pairs.getKey().toString().equalsIgnoreCase("Add On Course")){
						addOnCourse = (ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(addOnCourse,comparator);
						addontoList.addAll(addOnCourse);
						isAddonCourse=true;
					}else{
						List<SubjectTO> list=(ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(list,comparator);
						toList.addAll(list);
						finalsubMap.put(pairs.getKey().toString(),list);
					}
				}
			}
			to.setSubjectTOList(toList);
			to.setSubjectAddOnTOList(addontoList);
			Map<String,List<SubjectTO>> addOnCoursesubMap=new HashMap<String, List<SubjectTO>>();
			if(isAddonCourse){
				addOnCoursesubMap.put("Add On Course", addOnCourse);
			}
			to.setSubMap(CommonUtil.sortMapByKey(finalsubMap));
			to.setAddOnCoursesubMap(addOnCoursesubMap);

			DecimalFormat twoDForm = new DecimalFormat("#.##");

			to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
			to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
			to.setTotalMarksInWord(CommonUtil.numberToWord1(totalMarksAwarded));
			to.setTotalCredits(String.valueOf(totalCredits));

			if(isFailed==true ){
				to.setTotalGrade("F");
				to.setTotalMarksAwarded(" ");
				to.setTotalCredits(" ");
				to.setTotalGradePoints(" ");
				if(isWithHeld){
				to.setResultClass("WITHHELD");
				}else{
					to.setResultClass("REAPPEAR");	
				}
				to.setSgpa(" ");
			}
			else{
				if(totalgradepoints>0 && totcredits>0){
					double sgpa=(totalgradepoints/totcredits);
					to.setSgpa(String.valueOf(CommonUtil.Round(sgpa, 2)));
				}
				int tcredits=(int)totcredits;
				to.setTotalCredits(String.valueOf(tcredits));
				int tcreditPoints=(int)totalgradepoints;
				to.setTotalGradePoints(String.valueOf(tcreditPoints));
				String grade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(cid, to.getSgpa(), 0,sid);
				to.setTotalGrade(grade);
				to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
				if(isWithHeld){
					to.setResultClass("WITHHELD");
				}else if(isReappear){
					to.setResultClass("REAPPEAR");
				}else{
				to.setResultClass("PASS");
				}
			}
			double tgpa=0;

			if(gpForCal>0 && tcForCal>0){
				tgpa=CommonUtil.Round((gpForCal/tcForCal),2);
			}
			to.setGradePoints(String.valueOf(twoDForm.format(tgpa)));
			String countQuery="select count(*) from EXAM_update_exclude_withheld " +
			" where student_id = " +studentId+
			" and exam_id ="+examId;
			int count=0;
			List<BigInteger> countList=transaction.getStudentHallTicket(countQuery);
			if(countList!=null && !countList.isEmpty()){
				BigInteger b=countList.get(0);
				count=b.intValue();
			}
			String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
			double agg=0;
			List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
			if(aggList!=null && !aggList.isEmpty()){
				agg=Double.parseDouble(aggList.get(0).toString());
			}

/*			if(count<1){
				double r=((double)(totalMarksAwarded)/(double)(totalMaxMarks))*100;
				if(r>=agg){
					String robj = UpdateExamStudentSGPAImpl.getInstance().getResultClass(cid, r, 0,sid);
					if(robj!=null && resultClass.equals("PASS"))
						to.setResult(robj);
					if(isWithHeld){
						to.setResultClass("WITHHELD");
					}else if(isReappear){
						to.setResultClass("REAPPEAR");
					}else{
					to.setResultClass("PASS");
					}

					String query="select result_class from EXAM_grade_class_definition where course_id="+cid+" and "+r+" between start_percentage and end_percentage";
				List<String> rList=transaction.getStudentHallTicket(query);
				if(rList!=null && !rList.isEmpty()){
					Iterator<String> rItr=rList.iterator();
					while (rItr.hasNext()) {
						String robj = rItr.next();
						if(robj!=null && resultClass.equals("Pass"))
							to.setResult(robj);
						else
							to.setResult("Failed");
					}
				}
				}else
					to.setResult("REAPPEAR");
			}else{
				to.setResult("WITH HELD");
			}*/
			if(studentPhotos.containsKey(sid)){
				if(studentPhotos.get(sid) != null){
					to.setPhoto(new String(studentPhotos.get(sid)));
				}
				request.getSession().setAttribute(to.getRegNo(), studentPhotos.get(sid));
			}
		}
		// for sgpa storing
		double sgpa = 0;
		String grade = "";
		if(totalgradepointsForSgpa>0 && totcreditsForSgpa>0){
			sgpa=CommonUtil.Round(totalgradepointsForSgpa/totcreditsForSgpa,2);
			grade = UpdateExamStudentSGPAImpl.getInstance().getResultGrade(cid, String.valueOf(sgpa), 0,sid);
		}
		if(to!=null){
		to.setSemesterSgpa(String.valueOf(sgpa));
		if(grade!=null && !grade.equalsIgnoreCase(""))
			to.setGradeForSgpa(grade);
		if(to.getResultClass().equalsIgnoreCase("REAPPEAR")){
			to.setResultForSgpa("failed");
			to.setSgpa(" ");
		}
		else{
			to.setResultForSgpa("passed");
		}
		to.setTotalGradePointsForSgpa(String.valueOf(totalgradepointsForSgpa));
		to.setTotalCreditsForSgpa(String.valueOf(totcreditsForSgpa));
		to.setTotalMaxMarksForSgpa(String.valueOf(totalMaxMarksForSgpa));
		to.setTotalMarksAwardedForSgpa(String.valueOf(totalMarksAwardedForSgpa));
		
		}

		return to;
	}




	public String getQueryForUGStudentMarksCard(DisciplinaryDetailsForm objForm) throws Exception {
		String q=" SELECT EXAM_student_overall_internal_mark_details.exam_id as eid, exam_type.name," +
		" EXAM_definition.name, EXAM_definition.month, EXAM_definition.year,classes.name," +
		"  EXAM_student_overall_internal_mark_details.student_id as studID,  " +
		"  EXAM_student_overall_internal_mark_details.class_id as classID, " + 
		"  EXAM_student_overall_internal_mark_details.subject_id as subID,  " +
		"  subject.code as subCode,  " +
		"  subject.name as subName,  " +
		"  EXAM_student_overall_internal_mark_details.theory_total_mark,  " +
		"  EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,  " +
		"  EXAM_student_overall_internal_mark_details.  " +
		" practical_total_mark,  " +
		"  EXAM_student_overall_internal_mark_details.  " +
		"  practical_total_attendance_mark,  " +
		"  CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id,  " +
		"  EXAM_subject_rule_settings.theory_ese_minimum_mark, " + 
		" EXAM_subject_rule_settings.theory_ese_maximum_mark, " + 
		"  EXAM_subject_rule_settings.practical_ese_minimum_mark, " + 
		"   EXAM_subject_rule_settings.practical_ese_maximum_mark, " + 
		" (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks,  " +
		"  (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks, " + 
		"   personal_data.first_name, " + 
		"   personal_data.middle_name, " + 
		"  personal_data.last_name,  " +
		"  EXAM_subject_sections.name as secName,  " +
		"  EXAM_subject_sections.is_initialise,  " +
		"  EXAM_subject_sections.id as secID,  " +
		"   EXAM_sub_definition_coursewise.subject_order,  " +
		"   EXAM_subject_rule_settings.final_practical_internal_maximum_mark,  " +
		"   EXAM_subject_rule_settings.final_theory_internal_maximum_mark,  " +
		"  (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0," +
		" EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+" +
		"if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0," +
		"EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal  " +
		" ,(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0," +
		"EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+" +
		"if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0," +
		"EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal,  " +
		" (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0," +
		"EXAM_student_overall_internal_mark_details.theory_total_mark,0)+" +
		"if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0," +
		"EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+" +
		"(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,  " +
		" (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0," +
		"EXAM_student_overall_internal_mark_details.practical_total_mark,0)+" +
		"if(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark > 0," +
		"EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,0)+" +
		"if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,  " +
		"   max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit,  " +
		"   max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit,  " +
		"    student.register_no,  " +
		"    if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Theory')) as subType,  " +
		"     if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		" FROM EXAM_sub_coursewise_attendance_marks  " +
		" where course_id=adm_appln.selected_course_id  " +
		" and subject_id=subject.id) is not null,   " +
		" (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		" FROM EXAM_sub_coursewise_attendance_marks  " +
		" where course_id=adm_appln.selected_course_id  " +
		" and subject_id=subject.id),  " +
		" (SELECT max(EXAM_attendance_marks.marks)  " +
		" FROM EXAM_attendance_marks EXAM_attendance_marks  " +
		" where course_id = adm_appln.selected_course_id))  AS maxAttMarks,  " +
		" classes.term_number, " + 
		"  adm_appln.course_id as admCourseID,  " +
		"  (  (if(EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer,  " +
		"(  (if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper,  " +
	
		" ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +


		" if((SELECT EXAM_sub_coursewise_grade_defn.grade_point  " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+") is not null, " +
		"(SELECT EXAM_sub_coursewise_grade_defn.grade_point  " +
		"FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " + 
		" (SELECT EXAM_grade_class_definition.grade_point  " +
		"FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1)) as thegrap, " + 
		"(SELECT EXAM_grade_class_definition.grade_point  " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1) as pragrap,  " +
		" EXAM_sub_definition_coursewise.dont_show_max_marks,  " +
		" EXAM_sub_definition_coursewise.dont_show_min_marks, " + 
		" EXAM_sub_definition_coursewise.show_only_grade,  " +
		" course_scheme.name as courseSchemeNo,  " +
		" EXAM_sub_definition_coursewise.dont_show_sub_type, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		" EXAM_sub_definition_coursewise.dont_show_att_marks  " +
		"from EXAM_student_overall_internal_mark_details " +
		"LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id " +
		"LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id " +
		"LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id " +
		"LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id " +
		"LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id " +
		"LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id " +
		"LEFT JOIN exam_type on exam_type.id = EXAM_definition.exam_type_id " +
		"LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id " +
		"LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id " +
		"LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
		"LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id " +
		"LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id  " +

		"and EXAM_student_final_mark_details.exam_id = EXAM_definition.id " +
		"and EXAM_student_final_mark_details.student_id = student.id " +
		"and EXAM_student_final_mark_details.subject_id = subject.id " +
		"LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id " +
		"and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year " +
		"and EXAM_subject_rule_settings.course_id = classes.course_id" +
		"and EXAM_subject_rule_settings.scheme_no = classes.term_number" +

		"LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id" +
		"and EXAM_sub_definition_coursewise.course_id = classes.course_id" +
		"and EXAM_sub_definition_coursewise.scheme_no = classes.term_number" +
		"and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year" +
		"LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id" +
		" LEFT JOIN course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id" +
		" where  student.id= " +objForm.getStudentId() + 
		" and exam_type.name like 'regular%'" +
		" group by EXAM_student_overall_internal_mark_details.class_id,EXAM_student_overall_internal_mark_details.class_id,student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id   " +
		" UNION ALL   SELECT EXAM_student_overall_internal_mark_details.exam_id as eid, exam_type.name,  EXAM_definition.name, EXAM_definition.month, EXAM_definition.year,classes.name," +
		"    EXAM_student_overall_internal_mark_details.student_id as studID," +  
		"     EXAM_student_overall_internal_mark_details.class_id as classID,  " +
		"    EXAM_student_overall_internal_mark_details.subject_id as subID,  " +
		"    subject.code as subCode,  " +
		"    subject.name as subName,  " +
		"    EXAM_student_overall_internal_mark_details.theory_total_mark,  " +
		"    EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,  " +
		"    EXAM_student_overall_internal_mark_details." +  
		"    practical_total_mark,  " +
		"    EXAM_student_overall_internal_mark_details.  " +
		"    practical_total_attendance_mark,  " +
		"   CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id,  " +
		"  EXAM_subject_rule_settings.theory_ese_minimum_mark,  " +
		"    EXAM_subject_rule_settings.theory_ese_maximum_mark, " + 
		"  EXAM_subject_rule_settings.practical_ese_minimum_mark, " + 
		"  EXAM_subject_rule_settings.practical_ese_maximum_mark,  " +
		" (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks,  " +
		" (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks,  " +
		" personal_data.first_name,  " +
		"  personal_data.middle_name,  " +
		"  personal_data.last_name,  " +
		"  EXAM_subject_sections.name as secName,  " +
		"  EXAM_subject_sections.is_initialise,  " +
		"  EXAM_subject_sections.id as secID,  " +
		"  EXAM_sub_definition_coursewise.subject_order,  " +
		"   EXAM_subject_rule_settings.final_practical_internal_maximum_mark, " + 
		"   EXAM_subject_rule_settings.final_theory_internal_maximum_mark,  " +
		" (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal  " +
		" ,(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal, " +
		" (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain,  " +
		" (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain,  " +
		"    max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit, " + 
		"    max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit,  " +
		"     student.register_no,  " +
		"   if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Practical')) as subType,  " +
		"      if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		" FROM EXAM_sub_coursewise_attendance_marks  " +
		" where course_id=adm_appln.selected_course_id  " +
		" and subject_id=subject.id) is not null,   " +
		" (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)  " +
		" FROM EXAM_sub_coursewise_attendance_marks  " +
		" where course_id=adm_appln.selected_course_id  " +
		" and subject_id=subject.id),  " +
		" (SELECT max(EXAM_attendance_marks.marks)  " +
		" FROM EXAM_attendance_marks EXAM_attendance_marks  " +
		" where course_id = adm_appln.selected_course_id))  AS maxAttMarks,  " +
		" classes.term_number,  " +
		" adm_appln.course_id as admCourseID,  " +
		"   (  (if(EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer,  " +
		" (  (if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper,  " +

		" ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +


		" if((SELECT  EXAM_sub_coursewise_grade_defn.grade_point FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+") is not null,  " +
		" (SELECT EXAM_sub_coursewise_grade_defn.grade_point  " +
		"FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"),  " +
		" (SELECT EXAM_grade_class_definition.grade_point  " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1)) as thegrap,  " +
		" (SELECT EXAM_grade_class_definition.grade_point  " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1) as pragrap,  " +
		" EXAM_sub_definition_coursewise.dont_show_max_marks, " + 
		" EXAM_sub_definition_coursewise.dont_show_min_marks, " + 
		" EXAM_sub_definition_coursewise.show_only_grade, " +
		" course_scheme.name as courseSchemeNo," +
		" EXAM_sub_definition_coursewise.dont_show_sub_type, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln," +
		" EXAM_sub_definition_coursewise.dont_show_att_marks  " +
		" from EXAM_student_overall_internal_mark_details" +
		" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id" +
		" LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id" +
		" LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id" +
		" LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id" +
		" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id" +
		" LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id" +
		" LEFT JOIN exam_type on exam_type.id = EXAM_definition.exam_type_id" +
		" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id" +
		" LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id" +
		" LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
		" LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" +
		" LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id" +
		" and EXAM_student_final_mark_details.exam_id = EXAM_definition.id" +
		" and EXAM_student_final_mark_details.student_id = student.id" +
		" and EXAM_student_final_mark_details.subject_id = subject.id" +
		" LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id" +
		" and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year" +
		" and EXAM_subject_rule_settings.course_id = classes.course_id" +
		" and EXAM_subject_rule_settings.scheme_no = classes.term_number" +
		" LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id" +
		" and EXAM_sub_definition_coursewise.course_id = classes.course_id" +
		" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number" +
		" and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year" +
		" LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id" +
		" where subject.is_theory_practical='B' and student.id = " +objForm.getStudentId()+
		" and exam_type.name like 'regular%'" +
		" group by EXAM_student_overall_internal_mark_details.class_id,EXAM_student_overall_internal_mark_details.class_id,student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id  " +
		" order by classID,eid,subject_order";


		return q;
	}


	public String getQueryForPGStudentMarksCard(DisciplinaryDetailsForm objForm) throws Exception {

		String q= "SELECT EXAM_student_overall_internal_mark_details.exam_id as eid,"+
		" EXAM_student_overall_internal_mark_details.student_id as studID," +
		" EXAM_student_overall_internal_mark_details.class_id as classID," + 
		" EXAM_student_overall_internal_mark_details.subject_id as subID," +
		" subject.code as subCode," +
		" subject.name as subName," +
		" EXAM_student_overall_internal_mark_details.theory_total_mark," +
		" EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,"+
		" EXAM_student_overall_internal_mark_details." +
		" practical_total_mark," +
		" EXAM_student_overall_internal_mark_details." +
		" practical_total_attendance_mark," +
		" CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id," +
		" EXAM_subject_rule_settings.theory_ese_minimum_mark," + 
		" EXAM_subject_rule_settings.theory_ese_maximum_mark," + 
		" EXAM_subject_rule_settings.practical_ese_minimum_mark," + 
		" EXAM_subject_rule_settings.practical_ese_maximum_mark," + 
		" (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks," +
		" (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks," + 
		" personal_data.first_name," + 
		" personal_data.middle_name," + 
		" personal_data.last_name," +
		" EXAM_subject_sections.name as secName," +
		" EXAM_subject_sections.is_initialise," +
		" EXAM_subject_sections.id as secID," +
		" EXAM_sub_definition_coursewise.subject_order," +
		" EXAM_subject_rule_settings.final_practical_internal_maximum_mark," +
		" EXAM_subject_rule_settings.final_theory_internal_maximum_mark," +
		" (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0," +
		" EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+" +
		" if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0," +
		" EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal" +
		" ,(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0," +
		" EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+" +
		" if(EXAM_subject_rule_settings.practical_ese_maximum_mark>0," +
		" EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal," +
		" (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0," +
		" EXAM_student_overall_internal_mark_details.theory_total_mark,0)+" +
		" if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0," +
		" EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+" +
		" (if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain," +
		" (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0," +
		" EXAM_student_overall_internal_mark_details.practical_total_mark,0)+" +
		" if(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark > 0," +
		" EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,0)+" +
		" if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain," +
		" max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit," +
		" max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit," +
		" student.register_no," +
		" if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Theory')) as subType," +
		" if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)" +
		" FROM EXAM_sub_coursewise_attendance_marks" +
		" where course_id=adm_appln.selected_course_id" +
		" and subject_id=subject.id) is not null," +
		" (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)" +
		" FROM EXAM_sub_coursewise_attendance_marks" +
		" where course_id=adm_appln.selected_course_id" +
		" and subject_id=subject.id)," +
		" (SELECT max(EXAM_attendance_marks.marks)" +
		" FROM EXAM_attendance_marks EXAM_attendance_marks" +
		" where course_id = adm_appln.selected_course_id))  AS maxAttMarks," +
		" classes.term_number," + 
		"  adm_appln.course_id as admCourseID," +
		" ((if(EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer," +
		" ((if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper," +
		/*" if((SELECT EXAM_sub_coursewise_grade_defn.grade" +  
" FROM EXAM_sub_coursewise_grade_defn" +
" EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id) is not null," +
" (SELECT EXAM_sub_coursewise_grade_defn.grade" +
" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id)," + 
" (SELECT EXAM_grade_class_definition.grade" +
" FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1)) as thegra," +
"(SELECT EXAM_grade_class_definition.grade" +
" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1) as pragra," +*/

		" ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id  and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +


		" if((SELECT EXAM_sub_coursewise_grade_defn.grade_point" +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+") is not null, " +
		" (SELECT EXAM_sub_coursewise_grade_defn.grade_point" +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+")," + 
		" (SELECT EXAM_grade_class_definition.grade_point" +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1)) as thegrap," + 
		" (SELECT EXAM_grade_class_definition.grade_point" +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1) as pragrap," +
		" EXAM_sub_definition_coursewise.dont_show_max_marks," +
		" EXAM_sub_definition_coursewise.dont_show_min_marks," + 
		" EXAM_sub_definition_coursewise.show_only_grade," +
		//" course_scheme.name as courseSchemeNo," +
		" EXAM_sub_definition_coursewise.dont_show_sub_type, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln," +
		" EXAM_sub_definition_coursewise.dont_show_att_marks," +
		" EXAM_definition.name, classes.name, " +
		" EXAM_sub_definition_coursewise.dont_consider_failure_total_result,   " +
		" if((EXAM_sub_definition_coursewise.dont_consider_failure_total_result=1 and EXAM_sub_definition_coursewise.dont_consider_failure_total_result is not null and subject.id<>902 and (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks)))<60),1,0) as certificate,   " +
		" subject.is_theory_practical, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		" EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result " +
		" from EXAM_student_overall_internal_mark_details" +
		" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id" +
		" LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id" +
		" LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id" +
		" LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id" +
		" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id" +
		" LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id" +
		" LEFT JOIN exam_type on exam_type.id = EXAM_definition.exam_type_id" +
		" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id" +
		" LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id" +
		" LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
		" LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" +
		" LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id" +

		" and EXAM_student_final_mark_details.exam_id = EXAM_definition.id" +
		" and EXAM_student_final_mark_details.student_id = student.id" +
		" and EXAM_student_final_mark_details.subject_id = subject.id" +
		" LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id" +
		" and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year" +
		" and EXAM_subject_rule_settings.course_id = classes.course_id" +
		" and EXAM_subject_rule_settings.scheme_no = classes.term_number" +

		" LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id" +
		" and EXAM_sub_definition_coursewise.course_id = classes.course_id" +
		" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number" +
		" and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year" +

		//by giri
		" INNER JOIN EXAM_publish_exam_results ON EXAM_publish_exam_results.class_id = classes.id" +
		" and EXAM_publish_exam_results.exam_id = EXAM_definition.id"+
		//end by giri

		" LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id" +
		" LEFT JOIN course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id" +
		" where  student.id= " +objForm.getStudentId() + 
		"  and classes.term_number=  "+objForm.getSemesterYearNo()+"  and EXAM_subject_rule_settings.scheme_no= "+objForm.getSemesterYearNo()+
		" and exam_type.name like 'regular%'" +
		" and student.id not in (select student_id from EXAM_block_unblock_hall_tkt_marks_card where " +
		" EXAM_block_unblock_hall_tkt_marks_card.student_id=student.id " +
		" and EXAM_block_unblock_hall_tkt_marks_card.exam_id=EXAM_definition.id " +
		" and EXAM_block_unblock_hall_tkt_marks_card.class_id=classes.id " +
		" and EXAM_block_unblock_hall_tkt_marks_card.hall_tkt_or_marks_card='M') " +
		" group by EXAM_student_overall_internal_mark_details.class_id,EXAM_student_overall_internal_mark_details.class_id,student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id" +
		" UNION ALL SELECT EXAM_student_overall_internal_mark_details.exam_id as eid, " +
		" EXAM_student_overall_internal_mark_details.student_id as studID," +  
		" EXAM_student_overall_internal_mark_details.class_id as classID," +
		" EXAM_student_overall_internal_mark_details.subject_id as subID," +
		" subject.code as subCode," +
		" subject.name as subName," +
		" EXAM_student_overall_internal_mark_details.theory_total_mark," +
		" EXAM_student_overall_internal_mark_details.theory_total_attendance_mark," +
		" EXAM_student_overall_internal_mark_details." +  
		" practical_total_mark," +
		" EXAM_student_overall_internal_mark_details." +
		" practical_total_attendance_mark," +
		" CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id," +
		" EXAM_subject_rule_settings.theory_ese_minimum_mark," +
		" EXAM_subject_rule_settings.theory_ese_maximum_mark," + 
		" EXAM_subject_rule_settings.practical_ese_minimum_mark," + 
		" EXAM_subject_rule_settings.practical_ese_maximum_mark," +
		" (if(EXAM_student_final_mark_details.student_theory_marks is not null,EXAM_student_final_mark_details.student_theory_marks,0)) as student_theory_marks," +
		" (if(EXAM_student_final_mark_details.student_practical_marks is not null,EXAM_student_final_mark_details.student_practical_marks,0)) as student_practical_marks," +
		" personal_data.first_name," +
		" personal_data.middle_name," +
		" personal_data.last_name," +
		" EXAM_subject_sections.name as secName," +
		" EXAM_subject_sections.is_initialise," +
		" EXAM_subject_sections.id as secID," +
		" EXAM_sub_definition_coursewise.subject_order," +
		" EXAM_subject_rule_settings.final_practical_internal_maximum_mark," +  
		" EXAM_subject_rule_settings.final_theory_internal_maximum_mark," +
		" (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryTotal" +
		" ,(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0, EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark > 0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalTotal," +
		" (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks))) as theoryObtain," +
		" (if(EXAM_student_overall_internal_mark_details.practical_total_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(EXAM_student_overall_internal_mark_details.practical_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.practical_total_attendance_mark,0)+if(student_practical_marks is null,0,student_practical_marks )) as practicalObtain," +  " max(EXAM_sub_definition_coursewise.practical_credit) as practical_credit," +  " max(EXAM_sub_definition_coursewise.theory_credit) as theory_credit," + " student.register_no," +  " if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Practical')) as subType," + " if((SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)" +
		" FROM EXAM_sub_coursewise_attendance_marks" +
		" where course_id=adm_appln.selected_course_id" +
		" and subject_id=subject.id) is not null," +
		" (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks)" +
		" FROM EXAM_sub_coursewise_attendance_marks" +
		" where course_id=adm_appln.selected_course_id" +
		" and subject_id=subject.id)," +
		" (SELECT max(EXAM_attendance_marks.marks)" +
		" FROM EXAM_attendance_marks EXAM_attendance_marks" +
		" where course_id = adm_appln.selected_course_id))  AS maxAttMarks," +
		" classes.term_number," +
		" adm_appln.course_id as admCourseID," +
		" ((if(EXAM_student_overall_internal_mark_details.theory_total_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(student_theory_marks > 0,student_theory_marks,0))/     (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0))*100) as theoryPer," +
		" ((if(EXAM_student_overall_internal_mark_details.practical_total_mark >0,EXAM_student_overall_internal_mark_details.practical_total_mark,0)+if(student_practical_marks >0,student_practical_marks,0))/(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100) as practicalper," +
		/*
" if((SELECT EXAM_sub_coursewise_grade_defn.grade" +
" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id) is not null," +
" (SELECT EXAM_sub_coursewise_grade_defn.grade" +
" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id)," + " (SELECT EXAM_grade_class_definition.grade" +
" FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1)) as thegra," +
" (SELECT EXAM_grade_class_definition.grade " + 
" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1) as pragra," + */

		" ifnull((SELECT EXAM_sub_coursewise_grade_defn.grade" +
		"  FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		"   (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"     FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where theoryPer between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT  EXAM_grade_class_definition.grade   FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as thegra, " +
		" ifnull( (SELECT  EXAM_sub_coursewise_grade_defn.grade " +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		" (ifnull( (SELECT EXAM_grade_class_definition_frombatch.grade " +
		"  FROM EXAM_grade_class_definition_frombatch EXAM_grade_class_definition_frombatch where practicalper between EXAM_grade_class_definition_frombatch.start_percentage and EXAM_grade_class_definition_frombatch.end_percentage and EXAM_grade_class_definition_frombatch.course_id = classes.course_id and from_batch <= adm_appln.applied_year limit 1), " +
		" (SELECT EXAM_grade_class_definition.grade " +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)))) as pragra, " +


		" if((SELECT" +
		" EXAM_sub_coursewise_grade_defn.grade_point" +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+") is not null," +
		" (SELECT EXAM_sub_coursewise_grade_defn.grade_point" +
		" FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where theoryPer between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = adm_appln.selected_course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+")," +
		" (SELECT EXAM_grade_class_definition.grade_point" +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where theoryPer between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1)) as thegrap," +
		" (SELECT EXAM_grade_class_definition.grade_point" +
		" FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = adm_appln.selected_course_id limit 1) as pragrap," +
		" EXAM_sub_definition_coursewise.dont_show_max_marks," + 
		" EXAM_sub_definition_coursewise.dont_show_min_marks," + 
		" EXAM_sub_definition_coursewise.show_only_grade," +
		" EXAM_sub_definition_coursewise.dont_show_sub_type, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln," +
		" EXAM_sub_definition_coursewise.dont_show_att_marks," +
		" EXAM_definition.name, classes.name, " +
		" EXAM_sub_definition_coursewise.dont_consider_failure_total_result,   " +
		" if((EXAM_sub_definition_coursewise.dont_consider_failure_total_result=1 and EXAM_sub_definition_coursewise.dont_consider_failure_total_result is not null and subject.id<>902 and (if(EXAM_student_overall_internal_mark_details.theory_total_mark>0,EXAM_student_overall_internal_mark_details.theory_total_mark,0)+if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark > 0,EXAM_student_overall_internal_mark_details.theory_total_attendance_mark,0)+(if(student_theory_marks is null,0,student_theory_marks)))<60),1,0) as certificate,   " +
		" subject.is_theory_practical, EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		" EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result " +
		" from EXAM_student_overall_internal_mark_details" +
		" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id" +
		" LEFT JOIN adm_appln on student.adm_appln_id = adm_appln.id" +
		" LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id" +
		" LEFT JOIN subject on EXAM_student_overall_internal_mark_details.subject_id = subject.id" +
		" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id" +
		" LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id" +
		" LEFT JOIN exam_type on exam_type.id = EXAM_definition.exam_type_id" +
		" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id" +
		" LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id" +
		" LEFT JOIN curriculum_scheme_duration on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
		" LEFT JOIN curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" +
		" LEFT JOIN EXAM_student_final_mark_details ON EXAM_student_final_mark_details.class_id = classes.id" +
		" and EXAM_student_final_mark_details.exam_id = EXAM_definition.id" +
		" and EXAM_student_final_mark_details.student_id = student.id" +
		" and EXAM_student_final_mark_details.subject_id = subject.id" +
		" LEFT JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id" +
		" and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year" +
		" and EXAM_subject_rule_settings.course_id = classes.course_id" +
		" and EXAM_subject_rule_settings.scheme_no = classes.term_number" +
		" LEFT JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id" +
		" and EXAM_sub_definition_coursewise.course_id = classes.course_id" +
		" and EXAM_sub_definition_coursewise.scheme_no = classes.term_number" +
		" and EXAM_sub_definition_coursewise.academic_year = curriculum_scheme_duration.academic_year" +
		//by giri
		" INNER JOIN EXAM_publish_exam_results ON EXAM_publish_exam_results.class_id = classes.id" +
		" and EXAM_publish_exam_results.exam_id = EXAM_definition.id"+
		//end by giri
		" LEFT JOIN EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id" +
		" where subject.is_theory_practical='B' and student.id = " +objForm.getStudentId() +
		"  and classes.term_number=  "+objForm.getSemesterYearNo()+"  and EXAM_subject_rule_settings.scheme_no= "+objForm.getSemesterYearNo()+
		" and exam_type.name like 'regular%'" +
		" and student.id not in (select student_id from EXAM_block_unblock_hall_tkt_marks_card where " +
		" EXAM_block_unblock_hall_tkt_marks_card.student_id=student.id " +
		" and EXAM_block_unblock_hall_tkt_marks_card.exam_id=EXAM_definition.id " +
		" and EXAM_block_unblock_hall_tkt_marks_card.class_id=classes.id " +
		" and EXAM_block_unblock_hall_tkt_marks_card.hall_tkt_or_marks_card='M') " +
		" group by EXAM_student_overall_internal_mark_details.class_id,EXAM_student_overall_internal_mark_details.class_id,student.id,EXAM_student_overall_internal_mark_details.exam_id,subject.id" +
		" order by classID,eid,subject_order";
		return q;
	}



	public MarksCardTO getMarksCardForPgView(List<Object[]> pgMarksCardData, int sid) throws Exception {
		MarksCardTO to=null;
		Map<String,List<SubjectTO>> subMap=new HashMap<String, List<SubjectTO>>();
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int totalMarksAwarded=0;
			int totalMaxMarks=0;
			int totalCredits=0;
			double tcForCal=0;
			double gpForCal=0;
			String resultClass="Pass";
			int studentId=0;
			int examId=0;

			int cid=0;
			// Getting Grade for Fail from database
			String gradeForFail="";
			List<String> gradeList=transaction.getStudentHallTicket("SELECT EXAM_exam_settings.grade_for_fail FROM EXAM_exam_settings EXAM_exam_settings");
			if(gradeList!=null && !gradeList.isEmpty()){
				gradeForFail=gradeList.get(0);
			}

			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				boolean isDisplay=true;
				boolean isAdd=true;
				boolean showSubType=true;
				boolean showMaxMarks=true;
				boolean showMinMarks=true;
				boolean showAttMarks=true;
				boolean certificateCourse=false;
				boolean dontConsiderFail=false;
				if(obj[52]!=null && (obj[52].toString().trim().equalsIgnoreCase("1") || obj[52].toString().trim().equalsIgnoreCase("true"))){
					certificateCourse=true;
				}
				if(obj[51]!=null && (obj[51].toString().trim().equalsIgnoreCase("1") || obj[51].toString().trim().equalsIgnoreCase("true"))){
					dontConsiderFail=true;
				}
				String grade="";

				if(obj[47]!=null){
					if(obj[47].toString().trim().equals("true") || obj[47].toString().trim().equals("1")){
						isAdd=false;
					}
				}

				if(obj[43]!=null){
					if(obj[43].toString().trim().equals("true")){
						showMaxMarks=false;
					}
				}
				if(obj[44]!=null){
					if(obj[44].toString().trim().equals("true")){
						showMinMarks=false;
					}
				}
				if(obj[46]!=null){
					if(obj[46].toString().trim().equals("true")){
						showSubType=false;
					}
				}
				if(obj[48]!=null){
					if(obj[48].toString().trim().equals("true")){
						showAttMarks=false;
					}
				}
				if(obj[33]!=null){
					if(!obj[33].toString().equalsIgnoreCase("Practical")){
						if(obj[39]!=null){
							double min=0;
							double stu=0;
							if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
								min = Double.parseDouble(obj[11].toString());
							if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
								stu = Double.parseDouble(obj[15].toString());

							if(obj[39].toString().equals("F") || min>stu){
								if(!dontConsiderFail){
									resultClass="Fail";
								}
								grade=gradeForFail;
							}else{
								grade=obj[39].toString();
							}
						}
						else
						{
							grade="";
						}
					}else{
						if(obj[40]!=null){
							double min=0;
							double stu=0;
							if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
								min = Double.parseDouble(obj[13].toString());
							if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
								stu = Double.parseDouble(obj[16].toString());

							if(obj[40].toString().equals("F") ||  min>stu)
							{
								if(!dontConsiderFail){
									resultClass="Fail";
								}
								grade=gradeForFail;
							}else
							{
								if(obj[40]!=null)
									grade=obj[40].toString();
							}
						}
						else
						{
							grade="";
						}
					}
				}


				if(to==null){
					to=new MarksCardTO();

					if(obj[0]!=null){
						examId=Integer.parseInt(obj[0].toString());
						String monthQuery="select cast(month as unsigned) as month,year from EXAM_definition where del_is_active = 1 and id="+obj[0].toString();
						List<Object[]> monthList=transaction.getStudentHallTicket(monthQuery);
						if(monthList!=null){
							Iterator<Object[]> mItr=monthList.iterator();
							if (mItr.hasNext()) {
								Object[] m = (Object[]) mItr.next();
								String month="";
								String year="";
								if(m[0]!=null){
									month=monthMap.get(m[0].toString());
								}
								if(m[1]!=null){
									year=m[1].toString();
								}
								to.setMonthYear(month+" "+year);
								//added by manu,for change the signature till june 2013
								if(m[0]!=null){
									to.setMonthCheck(Integer.parseInt(m[0].toString()));
								}
								if(m[1]!=null){
									to.setYearCheck(Integer.parseInt(m[1].toString()));
								}
							}
						}
					}
					if(obj[1]!=null){
						studentId=Integer.parseInt(obj[1].toString());
					}

					if(obj[10]!=null){
						cid=Integer.parseInt(obj[10].toString());
						to.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(cid,"Course",true,"name"));
						//						if(cid==87){
						//							to.setCourseName("MBA - International Business");
						//						}else if(cid==84){
						//							to.setCourseName("Post Graduate Diploma in Management");
						//						}else if(cid==1 || cid==85 || cid==86 || cid==140){
						//							to.setCourseName("Master of Business Administration");
						//						}else{
						//							
						//							String query="select c.program.name,c.name,c.program.programType.id from Course c where c.isActive=1 and c.id="+cid;
						//							List<Object[]> clist=transaction.getDataByHql(query);
						//							
						//							if(clist!=null && !clist.isEmpty()){
						//								Iterator<Object[]> it=clist.iterator();
						//								if (it.hasNext()) {
						//									Object[] c = (Object[]) it.next();
						//									if(c[2]!=null){
						//										int ptid=Integer.parseInt(c[2].toString());
						//										if(ptid!=3){
						//											if(c[0]!=null){
						//												to.setCourseName(c[0].toString());
						//											}
						//										}else{
						//											if(c[1]!=null){
						//												to.setCourseName(c[1].toString());
						//											}
						//										}
						//									}
						//								}
						//							}
						//						}
					}
					if(obj[17]!=null){
						to.setStudentName(obj[17].toString());
					}
					if(obj[18]!=null){
						to.setStudentName(to.getStudentName()+" "+obj[18].toString());
					}
					if(obj[19]!=null){
						to.setStudentName(to.getStudentName()+" "+obj[19].toString());
					}
					if(obj[32]!=null){
						to.setRegNo(obj[32].toString());
					}
					if(obj[35]!=null){
						to.setSemNo(obj[35].toString());
					}
					if(obj[49]!=null)
					{
						to.setExamName(obj[49].toString());
					}

					if(obj[50]!=null)
					{
						to.setClassName(obj[50].toString());
					}
					/*if(obj[36]!=null){
						to.setDate(CommonUtil.ConvertStringToDateFormat(obj[36].toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					}*/
					/*if(obj[47]!=null){
						to.setSemType(obj[47].toString());
					}*/
					if(obj[20]!=null){
						List<SubjectTO> subList=null;
						if(subMap.containsKey(obj[20].toString())){
							subList=subMap.remove(obj[20].toString());
						}else{
							subList=new ArrayList<SubjectTO>();
						}

						SubjectTO subto=new SubjectTO();
						subto.setId(Integer.parseInt(obj[3].toString()));
						if(obj[4]!=null){
							subto.setCode(obj[4].toString());
						}
						if(obj[5]!=null){
							subto.setName(obj[5].toString());
						}
						if(obj[23]!=null)
							subto.setSubOrder(Integer.parseInt(obj[23].toString()));
						if(obj[33]!=null){
							if(obj[33].toString().equalsIgnoreCase("Theory")){
								if(showSubType)
									subto.setType("Theory");
								else
									subto.setType("");

								subto.setGrade(grade);

								if(obj[31]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
									if(subto.getGrade()!=null){
										if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
											totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
										}

									}


									tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
									if(obj[41]!=null && !obj[41].toString().isEmpty()){
										double d=Double.parseDouble((obj[41].toString()))*Double.parseDouble((obj[31].toString()));
										gpForCal=gpForCal+d;
									}
								}
								int ciaMarksAwarded=0;
								if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
								}
								/*if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
								}*/
								if(obj[34]!=null){
									if(obj[7]!=null)
										subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
									else
										subto.setAttMaxMarks("-");
								}
								if(obj[11]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[12]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
									if(isDisplay){
										if(m>=10)
											subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
										else
											subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
									}
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(obj[15].toString());
									else
										subto.setEseMarksAwarded("-");
								}
								if(obj[25]!=null){
									if(showMaxMarks)
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[25].toString()))));
									else
										subto.setCiaMaxMarks("-");
								}
								if(obj[26]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[28].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
								if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
									if(isDisplay)
										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									else
										subto.setCiaMarksAwarded("-");
								}else{
									if(isDisplay){
										if(CommonUtil.isValidDecimal(obj[6].toString()))
											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
										else
											subto.setCiaMarksAwarded(obj[6].toString());
									}else
										subto.setCiaMarksAwarded("-");
								}
							}
							if(obj[33].toString().equalsIgnoreCase("Practical")){
								if(showSubType)
									subto.setType("Practical");
								else
									subto.setType("");

								subto.setGrade(grade);

								if(obj[30]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
									if(subto.getGrade()!=null){
										if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
											totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
										}
									}
									tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
									if(obj[42]!=null && !obj[42].toString().isEmpty()){
										double d=Double.parseDouble((obj[42].toString()))*Double.parseDouble((obj[30].toString()));
										gpForCal=gpForCal+d;
									}
								}
								int ciaMarksAwarded=0;
								if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
								}
								/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
								}*/
								if(obj[13]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[14]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[16].toString()));
									if(isDisplay){
										if(m>=10)
											subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
										else
											subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
									}
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(obj[16].toString());
									else
										subto.setEseMarksAwarded("-");
								}
								if(obj[24]!=null){
									if(showMaxMarks)
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[24].toString()))));
									else
										subto.setCiaMaxMarks("-");
								}
								if(obj[27]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[29].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
								if(ciaMarksAwarded!=0  && obj[8]!=null && obj[9]!=null &&  CommonUtil.isValidDecimal(obj[8].toString()) && CommonUtil.isValidDecimal(obj[9].toString())){
									if(isDisplay)
										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									else
										subto.setCiaMarksAwarded("-");
								}else{

									if(isDisplay)
										if(CommonUtil.isValidDecimal(obj[8].toString()))
											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
										else
											subto.setCiaMarksAwarded(obj[8].toString());
									else
										subto.setCiaMarksAwarded("-");
								}
							}

						}
						subList.add(subto);
						subMap.put(obj[20].toString(), subList);
					}
				}else{
					if(obj[20]!=null){
						List<SubjectTO> subList=null;
						if(subMap.containsKey(obj[20].toString())){
							subList=subMap.remove(obj[20].toString());
						}else{
							subList=new ArrayList<SubjectTO>();
						}

						SubjectTO subto=new SubjectTO();
						subto.setId(Integer.parseInt(obj[3].toString()));
						if(obj[4]!=null){
							subto.setCode(obj[4].toString());
						}
						if(obj[5]!=null){
							subto.setName(obj[5].toString());
						}
						if(obj[23]!=null)
							subto.setSubOrder(Integer.parseInt(obj[23].toString()));
						if(obj[33]!=null){
							if(obj[33].toString().equalsIgnoreCase("Theory")){
								if(showSubType)
									subto.setType("Theory");
								else
									subto.setType("");

								subto.setGrade(grade);

								if(obj[31]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
									if(subto.getGrade()!=null){
										if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
											totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
										}
									}

									tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
									if(obj[41]!=null && !obj[41].toString().isEmpty()){
										double d=Double.parseDouble((obj[41].toString()))*Double.parseDouble((obj[31].toString()));
										gpForCal=gpForCal+d;
									}
								}
								int ciaMarksAwarded=0;
								if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
								}
								/*if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
								}*/
								if(obj[11]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[12]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
									if(isDisplay && m>0)
										subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(obj[15].toString());
									else
										subto.setEseMarksAwarded("-");
								}
								if(obj[25]!=null){
									if(showMaxMarks)
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[25].toString()))));
									else
										subto.setCiaMaxMarks("-");
								}
								if(obj[26]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[28].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
								if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
									if(isDisplay)
										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									else
										subto.setCiaMarksAwarded("-");
								}else{
									if(isDisplay){
										if(CommonUtil.isValidDecimal(obj[6].toString()))
											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
										else
											subto.setCiaMarksAwarded(obj[6].toString());
									}else
										subto.setCiaMarksAwarded("-");
								}
							}
							if(obj[33].toString().equalsIgnoreCase("Practical")){
								if(showSubType)
									subto.setType("Practical");
								else
									subto.setType("");

								subto.setGrade(grade);

								if(obj[30]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
									if(subto.getGrade()!=null){
										if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
											totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
										}

									}
									tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
									if(obj[42]!=null && !obj[42].toString().isEmpty()){
										double d=Double.parseDouble((obj[42].toString()))*Double.parseDouble((obj[30].toString()));
										gpForCal=gpForCal+d;
									}
								}
								int ciaMarksAwarded=0;
								if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
								}
								/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
								}*/
								if(obj[13]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[14]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[16].toString()));
									if(isDisplay && m>0)
										subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(obj[16].toString());
									else
										subto.setEseMarksAwarded("-");
								}
								if(obj[24]!=null){
									if(showMaxMarks)
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[24].toString()))));
									else
										subto.setCiaMaxMarks("-");
								}
								if(obj[27]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[29].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
								if(ciaMarksAwarded!=0  && obj[8]!=null && obj[9]!=null &&  CommonUtil.isValidDecimal(obj[8].toString()) && CommonUtil.isValidDecimal(obj[9].toString())){
									if(isDisplay)
										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									else
										subto.setCiaMarksAwarded("-");
								}else{

									if(isDisplay)
										if(CommonUtil.isValidDecimal(obj[8].toString()))
											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
										else
											subto.setCiaMarksAwarded(obj[8].toString());
									else
										subto.setCiaMarksAwarded("-");
								}
							}

						}
						subList.add(subto);
						subMap.put(obj[20].toString(), subList);

					}
				}
			}

			Map<String,List<SubjectTO>> finalsubMap=new HashMap<String, List<SubjectTO>>();
			List<SubjectTO> addOnCourse=null;
			boolean isAddonCourse=false;
			if(!subMap.isEmpty()){
				Iterator it = subMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					if(pairs.getKey().toString().equalsIgnoreCase("Add On Course")){
						addOnCourse = (ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(addOnCourse,comparator);
						isAddonCourse=true;
					}else{
						List<SubjectTO> list=(ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(list,comparator);
						finalsubMap.put(pairs.getKey().toString(),list);
					}
				}
			}
			Map<String,List<SubjectTO>> addOnCoursesubMap=new HashMap<String, List<SubjectTO>>();
			if(isAddonCourse){
				addOnCoursesubMap.put("Add On Course", addOnCourse);
			}
			to.setSubMap(CommonUtil.sortMapByKey(finalsubMap));
			to.setAddOnCoursesubMap(addOnCoursesubMap);

			DecimalFormat twoDForm = new DecimalFormat("#.##");

			to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
			to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
			to.setTotalMarksInWord(CommonUtil.numberToWord1(totalMarksAwarded));
			to.setTotalCredits(String.valueOf(totalCredits));

			double tgpa=0;

			if(gpForCal>0 && tcForCal>0){
				tgpa=CommonUtil.Round((gpForCal/tcForCal),2);

			}
			to.setGradePoints(String.valueOf(twoDForm.format(tgpa)));
			String countQuery="select count(*) from EXAM_update_exclude_withheld " +
			" where student_id = " +studentId+
			" and exam_id ="+examId;
			int count=0;
			List<BigInteger> countList=transaction.getStudentHallTicket(countQuery);
			if(countList!=null && !countList.isEmpty()){
				BigInteger b=countList.get(0);
				count=b.intValue();
			}
			String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
			double agg=0;
			List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
			if(aggList!=null && !aggList.isEmpty()){
				agg=Double.parseDouble(aggList.get(0).toString());
			}

			if(count<1){
				double r=((double)(totalMarksAwarded)/(double)(totalMaxMarks))*100;
				if(r>=agg){
					String robj = UpdateExamStudentSGPAImpl.getInstance().getResultClass(cid, r, 0,sid);
					if(robj!=null && resultClass.equals("Pass"))
						to.setResult(robj);
					else
						to.setResult("Failed");
				}else{
					to.setResult("Failed");
				}
			}else{
				to.setResult("WITH HELD");
			}	
		}		

		/*	//String query="select result_class from EXAM_grade_class_definition where course_id="+cid+" and "+r+" between start_percentage and end_percentage";
				String query="select ifnull((SELECT EXAM_grade_class_definition_frombatch.result_class   FROM EXAM_grade_class_definition_frombatch "+
				" where course_id ="+cid+" and "+r+" between start_percentage and end_percentage and from_batch <= '2012'), (SELECT EXAM_grade_class_definition.result_class "+
                "FROM EXAM_grade_class_definition EXAM_grade_class_definition where course_id ="+cid+" and "+r+" between start_percentage and end_percentage)) ";

				List<String> rList=transaction.getStudentHallTicket(query);
				if(rList!=null && !rList.isEmpty()){
					Iterator<String> rItr=rList.iterator();
					while (rItr.hasNext()) {
						String robj = rItr.next();
						if(robj!=null && resultClass.equals("Pass"))
							to.setResult(robj);
						else
							to.setResult("Failed");
					}
				}
				}else
				to.setResult("Failed");
			}else{
				to.setResult("WITH HELD");
			}
		}*/


		/*if(to==null)
		{int No=Integer.parseInt(TermNo)-1;
			getMarksCardForPgView(pgMarksCardData,String.valueOf(No));
			to.setSemNo(String.valueOf(No-1));
		}*/

		return to;
	}


	public String getQueryForPGStudentSupMarksCardView(DisciplinaryDetailsForm objForm) throws Exception {
		String query="SELECT EXAM_student_overall_internal_mark_details.exam_id, " +
		"       EXAM_student_overall_internal_mark_details.student_id, " +
		"       EXAM_student_overall_internal_mark_details.class_id, " +
		"       EXAM_student_overall_internal_mark_details.subject_id, " +
		"       subject.code as subCode, " +
		"       subject.name as subName, " +
		"       if(EXAM_internal_mark_supplementary_details.theory_total_mark IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details.theory_total_mark, " +
		"          EXAM_student_overall_internal_mark_details.theory_total_mark) " +
		"          AS theory_total_mark, " +
		"       if(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details.theory_total_attendance_mark, " +
		"          EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) " +
		"          AS theory_total_attendance_mark, " +
		"       if(EXAM_internal_mark_supplementary_details.practical_total_mark IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details.practical_total_mark, " +
		"          EXAM_student_overall_internal_mark_details.practical_total_mark) " +
		"          AS practical_total_mark, " +
		"       if(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details.practical_total_attendance_mark, " +
		"          EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) " +
		"          AS practical_total_attendance_mark, " +
		"       CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id, " +
		"       EXAM_subject_rule_settings.theory_ese_minimum_mark, " +
		"       EXAM_subject_rule_settings.theory_ese_maximum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_minimum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_maximum_mark, " +
		"       (if(EXAM_student_final_mark_details.student_theory_marks IS NOT NULL, " +
		"           EXAM_student_final_mark_details.student_theory_marks,0)) " +
		"          AS student_theory_marks, " +
		"       (if(EXAM_student_final_mark_details.student_practical_marks IS NOT NULL, " +
		"           EXAM_student_final_mark_details.student_practical_marks,0)) " +
		"          AS student_practical_marks, " +
		"       personal_data.first_name, " +
		"       personal_data.middle_name, " +
		"       personal_data.last_name, " +
		"       EXAM_subject_sections.name as secName, " +
		"       EXAM_subject_sections.is_initialise, " +
		"       EXAM_subject_sections.id as secId, " +
		"       EXAM_sub_definition_coursewise.subject_order as subOrder, " +
		"       EXAM_subject_rule_settings.final_practical_internal_maximum_mark, " +
		"       EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " +
		"       (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, " +
		"           EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+ if( " +
		"             (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id) " +
		"                IS NOT NULL, " +
		"             (SELECT max( " +
		"                        EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id), " +
		"             (SELECT max(EXAM_attendance_marks.marks) " +
		"                FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"               WHERE course_id = classes.course_id))+ if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0, " +
		"             EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) " +
		"          AS theoryTotal, " +
		"       (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0, " +
		"           EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+ if( " +
		"           (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id) " +
		"                IS NOT NULL, " +
		"             (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id), " +
		"             (SELECT max(EXAM_attendance_marks.marks) " +
		"                FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"               WHERE course_id = classes.course_id))+ EXAM_subject_rule_settings.practical_ese_maximum_mark) " +
		"          AS practicalTotal, " +
		"       (if( EXAM_internal_mark_supplementary_details. " +
		"           theory_total_mark " +
		"              IS NOT NULL, " +
		"           EXAM_internal_mark_supplementary_details. " +
		"           theory_total_mark, " +
		"           EXAM_student_overall_internal_mark_details. " +
		"           theory_total_mark)+ if( " +
		"             EXAM_internal_mark_supplementary_details. " +
		"             theory_total_attendance_mark " +
		"                IS NOT NULL, " +
		"             EXAM_internal_mark_supplementary_details. " +
		"             theory_total_attendance_mark, " +
		"             if(EXAM_student_overall_internal_mark_details. " +
		"                theory_total_attendance_mark IS NULL, 0, " +
		"                EXAM_student_overall_internal_mark_details. " +
		"                theory_total_attendance_mark)) + (if(student_theory_marks IS NULL, 0, student_theory_marks))) " +
		"          AS theoryObtain, " +
		"       (if( EXAM_internal_mark_supplementary_details. " +
		"           practical_total_mark " +
		"              IS NOT NULL, " +
		"           EXAM_internal_mark_supplementary_details. " +
		"           practical_total_mark, " +
		"           EXAM_student_overall_internal_mark_details. " +
		"           practical_total_mark)+ if( " +
		"             EXAM_internal_mark_supplementary_details. " +
		"             practical_total_attendance_mark " +
		"                IS NOT NULL, " +
		"             EXAM_internal_mark_supplementary_details. " +
		"             practical_total_attendance_mark, " +
		"             if( " +
		"                EXAM_student_overall_internal_mark_details. " +
		"                practical_total_attendance_mark IS NULL, 0, " +
		"                EXAM_student_overall_internal_mark_details. " +
		"                practical_total_attendance_mark))+ if(student_practical_marks IS NULL, 0, student_practical_marks)) " +
		"          AS practicalObtain, " +
		"       max(EXAM_sub_definition_coursewise.practical_credit) " +
		"          AS practical_credit, " +
		"       max(EXAM_sub_definition_coursewise.theory_credit) AS theory_credit, " +
		"       ifnull( " +
		"          (SELECT det.register_no " +
		"             FROM EXAM_student_detention_rejoin_details det " +
		"            WHERE det.scheme_no = " +
		"                     (SELECT min(rej.scheme_no) " +
		"                        FROM EXAM_student_detention_rejoin_details rej " +
		"                             LEFT JOIN class_schemewise rej_classscheme " +
		"                                ON rej. " +
		"                                   rejoin_class_schemewise_id = " +
		"                                      rej_classscheme.id " +
		"                             LEFT JOIN classes rej_class " +
		"                                ON rej_classscheme.class_id = " +
		"                                      rej_class.id " +
		"                       WHERE rej.student_id = det.student_id " +
		"                             AND ((rej_class.term_number <> " +
		"                                      classes.term_number) " +
		"                                  OR (rej_class.term_number IS NULL)) " +
		"                             AND det.scheme_no >= classes.term_number) " +
		"                  AND det.student_id = student.id " +
		"            LIMIT 1), " +
		"          student.register_no) " +
		"          AS register_no, " +
		"       if(subject.is_theory_practical = 'T', " +
		"          'Theory', " +
		"          if(subject.is_theory_practical = 'P', 'Practical', 'Theory')) " +
		"          AS subType, " +
		"       if( " +
		"          (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"             FROM EXAM_sub_coursewise_attendance_marks " +
		"            WHERE course_id = classes.course_id " +
		"                  AND subject_id = subject.id) " +
		"             IS NOT NULL, " +
		"          (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"             FROM EXAM_sub_coursewise_attendance_marks " +
		"            WHERE course_id = classes.course_id " +
		"                  AND subject_id = subject.id), " +
		"          (SELECT max(EXAM_attendance_marks.marks) " +
		"             FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"            WHERE course_id = classes.course_id)) " +
		"          AS maxAttMarks, " +
		"       classes.term_number, " +
		"       EXAM_publish_exam_results.publish_date, " +
		"       adm_appln.course_id, " +
		"       ((if( " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            theory_total_mark " +
		"               IS NOT NULL, " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            theory_total_mark, " +
		"            EXAM_student_overall_internal_mark_details. " +
		"            theory_total_mark) + if( " +
		"              EXAM_internal_mark_supplementary_details. " +
		"              theory_total_attendance_mark  IS NOT NULL, " +
		"              EXAM_internal_mark_supplementary_details. " +
		"              theory_total_attendance_mark, " +
		"              if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark IS NULL,0, " +
		"                 EXAM_student_overall_internal_mark_details.theory_total_attendance_mark))+ (if(student_theory_marks IS NULL, 0, student_theory_marks)))/ (if( " +
		"              EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, " +
		"              EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " +
		"              0)+ if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0, " +
		"                EXAM_subject_rule_settings.theory_ese_maximum_mark, " +
		"                0)) * 100) " +
		"          AS theoryPer, " +
		"       ((if( " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            practical_total_mark " +
		"               IS NOT NULL, " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            practical_total_mark, " +
		"            EXAM_student_overall_internal_mark_details. " +
		"            practical_total_mark) + if( " +
		"              EXAM_internal_mark_supplementary_details. " +
		"              practical_total_attendance_mark " +
		"                 IS NOT NULL, " +
		"              EXAM_internal_mark_supplementary_details. " +
		"              practical_total_attendance_mark, " +
		"              if( " +
		"                 EXAM_student_overall_internal_mark_details. " +
		"                 practical_total_attendance_mark IS NULL, 0, " +
		"                 EXAM_student_overall_internal_mark_details. " +
		"                 practical_total_attendance_mark))+ if(student_practical_marks IS NULL, 0, student_practical_marks))/ (if( " +
		"              EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0, " +
		"              EXAM_subject_rule_settings.final_practical_internal_maximum_mark, 0)+ if(EXAM_subject_rule_settings.practical_ese_maximum_mark > 0, " +
		"                EXAM_subject_rule_settings.practical_ese_maximum_mark, 0)) * 100) " +
		"          AS practicalper, " +
		"       if( " +
		"          (SELECT EXAM_sub_coursewise_grade_defn.grade " +
		"             FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
		"            WHERE theoryPer BETWEEN start_prcntg_grade AND end_prcntg_grade " +
		"                  AND EXAM_sub_coursewise_grade_defn.course_id = " +
		"                         classes.course_id " +
		"                  AND subject_id = subject.id  and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+") " +
		"             IS NOT NULL, " +
		"          (SELECT EXAM_sub_coursewise_grade_defn.grade " +
		"             FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
		"            WHERE theoryPer BETWEEN start_prcntg_grade AND end_prcntg_grade " +
		"                  AND EXAM_sub_coursewise_grade_defn.course_id = " +
		"                         classes.course_id " +
		"                  AND subject_id = subject.id  and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		"          (SELECT EXAM_grade_class_definition.grade " +
		"             FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
		"            WHERE theoryPer BETWEEN start_percentage " +
		"                                AND EXAM_grade_class_definition. " +
		"                                    end_percentage " +
		"                  AND EXAM_grade_class_definition.course_id = " +
		"                         classes.course_id " +
		"            LIMIT 1)) " +
		"          AS thegra, " +
		"       if((SELECT EXAM_sub_coursewise_grade_defn.grade FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id  and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+") is not null," +
		" (SELECT EXAM_sub_coursewise_grade_defn.grade FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id and EXAM_sub_coursewise_grade_defn.academic_year= "+objForm.getAcademicYear()+"), " +
		"(SELECT EXAM_grade_class_definition.grade FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)) as pragra, " +
		"       if( " +
		"          (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		"             FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
		"            WHERE theoryPer BETWEEN start_prcntg_grade AND end_prcntg_grade " +
		"                  AND EXAM_sub_coursewise_grade_defn.course_id = " +
		"                         classes.course_id " +
		"                  AND subject_id = subject.id) " +
		"             IS NOT NULL, " +
		"          (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		"             FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
		"            WHERE theoryPer BETWEEN start_prcntg_grade AND end_prcntg_grade " +
		"                  AND EXAM_sub_coursewise_grade_defn.course_id = " +
		"                         classes.course_id " +
		"                  AND subject_id = subject.id), " +
		"          (SELECT EXAM_grade_class_definition.grade_point " +
		"             FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
		"            WHERE theoryPer BETWEEN start_percentage " +
		"                                AND EXAM_grade_class_definition. " +
		"                                    end_percentage " +
		"                  AND EXAM_grade_class_definition.course_id = " +
		"                         classes.course_id " +
		"            LIMIT 1)) " +
		"          AS thegrap, " +
		"       if((SELECT EXAM_sub_coursewise_grade_defn.grade_point FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id) is not null, " +
		"(SELECT EXAM_sub_coursewise_grade_defn.grade_point FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id)," +
		" (SELECT EXAM_grade_class_definition.grade_point FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)) as pragrap, " +
		"       EXAM_sub_definition_coursewise.dont_show_max_marks, " +
		"       EXAM_sub_definition_coursewise.dont_show_min_marks, " +
		"       EXAM_sub_definition_coursewise.show_only_grade, " +
		"       course_scheme.name, " +
		"       EXAM_sub_definition_coursewise.dont_show_sub_type, " +
		" EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		"	 EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,  " +
		"       EXAM_student_pass_fail.pass_fail, " +
		"       EXAM_supplementary_improvement_application.is_appeared_theory, " +
		"       EXAM_supplementary_improvement_application.is_appeared_practical, " +
		"       subject.is_theory_practical, " +
		"       EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark, " +
		"       EXAM_subject_rule_settings.final_theory_internal_is_attendance, " +
		"       EXAM_subject_rule_settings.final_practical_internal_is_attendance " +
		"  FROM EXAM_supplementary_improvement_application " +
		"       LEFT JOIN student " +
		"          ON (EXAM_supplementary_improvement_application.student_id = " +
		"                 student.id) " +
		"       LEFT JOIN classes " +
		"          ON (EXAM_supplementary_improvement_application.class_id = " +
		"                 classes.id) " +
		"       LEFT JOIN subject " +
		"          ON (EXAM_supplementary_improvement_application.subject_id = " +
		"                 subject.id) " +
		"       LEFT JOIN EXAM_definition " +
		"          ON (EXAM_supplementary_improvement_application.exam_id = " +
		"                 EXAM_definition.id) " +
		"       LEFT JOIN adm_appln " +
		"          ON student.adm_appln_id = adm_appln.id " +
		"       LEFT JOIN personal_data " +
		"          ON adm_appln.personal_data_id = personal_data.id " +
		"       LEFT JOIN subject_type " +
		"          ON subject.subject_type_id = subject_type.id " +
		"       LEFT JOIN EXAM_student_overall_internal_mark_details " +
		"          ON (EXAM_student_overall_internal_mark_details.student_id = " +
		"                 student.id) " +
		"             AND EXAM_student_overall_internal_mark_details.subject_id = " +
		"                    subject.id " +
		"             AND EXAM_student_overall_internal_mark_details.class_id = " +
		"                    classes.id " +
		"       LEFT JOIN class_schemewise " +
		"          ON class_schemewise.class_id = classes.id " +
		"       LEFT JOIN curriculum_scheme_duration " +
		"          ON class_schemewise.curriculum_scheme_duration_id = " +
		"                curriculum_scheme_duration.id " +
		"       LEFT JOIN curriculum_scheme " +
		"          ON curriculum_scheme_duration.curriculum_scheme_id = " +
		"                curriculum_scheme.id " +
		"       LEFT JOIN course_scheme " +
		"          ON curriculum_scheme.course_scheme_id = course_scheme.id " +
		"       LEFT JOIN EXAM_student_final_mark_details " +
		"          ON (EXAM_student_final_mark_details.class_id = classes.id) " +
		"             AND (EXAM_student_final_mark_details.exam_id = " +
		"                     EXAM_definition.id) " +
		"             AND (EXAM_student_final_mark_details.student_id = student.id) " +
		"             AND (EXAM_student_final_mark_details.subject_id = subject.id) " +
		"       LEFT JOIN EXAM_internal_mark_supplementary_details " +
		"          ON (EXAM_internal_mark_supplementary_details.student_id = " +
		"                 student.id) " +
		"             AND (EXAM_internal_mark_supplementary_details.subject_id = " +
		"                     subject.id) " +
		"             AND (EXAM_internal_mark_supplementary_details.class_id = " +
		"                     classes.id) " +
		"             AND if((EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id or classes.course_id=18), "+ /*if for the current exam record available or BBA take current exam, else take from the previous last exam*/
		" (EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id), "+
		" (EXAM_internal_mark_supplementary_details.exam_id=(select max(e.exam_id) from "+
		" EXAM_internal_mark_supplementary_details e where e.class_id=classes.id and e.student_id=student.id and e.subject_id=subject.id and e.exam_id<=EXAM_supplementary_improvement_application.exam_id))) " +
		"       LEFT JOIN EXAM_student_pass_fail EXAM_student_pass_fail " +
		"          ON     (EXAM_student_pass_fail.student_id = student.id) " +
		"             AND (EXAM_student_pass_fail.class_id = classes.id) " +
		"             AND (EXAM_student_pass_fail.scheme_no = classes.term_number) " +
		"       LEFT JOIN EXAM_subject_rule_settings " +
		"          ON EXAM_subject_rule_settings.subject_id = subject.id " +
		"             AND EXAM_subject_rule_settings.academic_year = " +
		"                    curriculum_scheme_duration.academic_year " +
		"             AND EXAM_subject_rule_settings.course_id = classes.course_id " +
		"             AND EXAM_subject_rule_settings.scheme_no = classes.term_number " +
		"       LEFT JOIN EXAM_publish_exam_results " +
		"          ON EXAM_publish_exam_results.class_id = classes.id " +
		"             AND EXAM_publish_exam_results.exam_id = EXAM_definition.id " +
		"       LEFT JOIN EXAM_sub_definition_coursewise " +
		"          ON EXAM_sub_definition_coursewise.subject_id = subject.id " +
		"             AND EXAM_sub_definition_coursewise.course_id = classes.course_id " +
		"             AND EXAM_sub_definition_coursewise.scheme_no = " +
		"                    classes.term_number " +
		"             AND EXAM_sub_definition_coursewise.academic_year = " +
		"                    curriculum_scheme_duration.academic_year " +
		"       LEFT JOIN EXAM_subject_sections " +
		"          ON EXAM_sub_definition_coursewise.subject_section_id = " +
		"                EXAM_subject_sections.id " +
		" WHERE ((EXAM_supplementary_improvement_application.is_appeared_practical = 1) " +
		"        OR (EXAM_supplementary_improvement_application.is_appeared_theory = 1)) " +
		"       AND ((EXAM_supplementary_improvement_application.is_failed_practical = " +
		"                1) " +
		"            OR (EXAM_supplementary_improvement_application.is_failed_theory = " +
		"                   1)) " +
		"       AND EXAM_supplementary_improvement_application.exam_id =" +objForm.getSupExamId()+
		"       AND EXAM_subject_rule_settings.academic_year = " +
		"              curriculum_scheme_duration.academic_year " +
		"       AND classes.term_number =" +objForm.getSemesterYearNo()+
		"       AND student.id=" +objForm.getStudentId()+
		"       AND if(subject.is_theory_practical = 'B', " +
		"              ((EXAM_supplementary_improvement_application.is_appeared_theory =1) " +
		"               OR (EXAM_supplementary_improvement_application.is_failed_theory = 1)), TRUE) " +
		" GROUP BY student.id, EXAM_student_final_mark_details.exam_id, subject.id " +
		" UNION ALL " +
		" SELECT EXAM_student_overall_internal_mark_details.exam_id, " +
		"       EXAM_student_overall_internal_mark_details.student_id, " +
		"       EXAM_student_overall_internal_mark_details.class_id, " +
		"       EXAM_student_overall_internal_mark_details.subject_id, " +
		"       subject.code as subCode, " +
		"       subject.name as subName, " +
		"       if( " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          theory_total_mark " +
		"             IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          theory_total_mark, " +
		"          EXAM_student_overall_internal_mark_details. " +
		"          theory_total_mark) " +
		"          AS theory_total_mark, " +
		"       if( " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          theory_total_attendance_mark " +
		"             IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          theory_total_attendance_mark, " +
		"          EXAM_student_overall_internal_mark_details. " +
		"          theory_total_attendance_mark) " +
		"          AS theory_total_attendance_mark, " +
		"       if( " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          practical_total_mark " +
		"             IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          practical_total_mark, " +
		"          EXAM_student_overall_internal_mark_details. " +
		"          practical_total_mark) " +
		"          AS practical_total_mark, " +
		"       if( " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          practical_total_attendance_mark " +
		"             IS NOT NULL, " +
		"          EXAM_internal_mark_supplementary_details. " +
		"          practical_total_attendance_mark, " +
		"          EXAM_student_overall_internal_mark_details. " +
		"          practical_total_attendance_mark) " +
		"          AS practical_total_attendance_mark, " +
		"       CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id, " +
		"       EXAM_subject_rule_settings.theory_ese_minimum_mark, " +
		"       EXAM_subject_rule_settings.theory_ese_maximum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_minimum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_maximum_mark, " +
		"       (if(EXAM_student_final_mark_details.student_theory_marks IS NOT NULL, " +
		"           EXAM_student_final_mark_details.student_theory_marks, " +
		"           0)) " +
		"          AS student_theory_marks, " +
		"       (if( " +
		"           EXAM_student_final_mark_details.student_practical_marks " +
		"              IS NOT NULL, " +
		"           EXAM_student_final_mark_details.student_practical_marks, " +
		"           0)) " +
		"          AS student_practical_marks, " +
		"       personal_data.first_name, " +
		"       personal_data.middle_name, " +
		"       personal_data.last_name, " +
		"       EXAM_subject_sections.name as secName, " +
		"       EXAM_subject_sections.is_initialise, " +
		"       EXAM_subject_sections.id as secId, " +
		"       EXAM_sub_definition_coursewise.subject_order as subOrder, " +
		"       EXAM_subject_rule_settings.final_practical_internal_maximum_mark, " +
		"       EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " +
		"       (if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0, " +
		"           EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " +
		"           0)+ if( " +
		"             (SELECT max( " +
		"                        EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id) " +
		"                IS NOT NULL, " +
		"             (SELECT max( " +
		"                        EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id), " +
		"             (SELECT max(EXAM_attendance_marks.marks) " +
		"                FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"               WHERE course_id = classes.course_id)) + if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0, " +
		"             EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) " +
		"          AS theoryTotal, " +
		"       (if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0, " +
		"           EXAM_subject_rule_settings.final_practical_internal_maximum_mark, 0)+ if( " +
		"             (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id) IS NOT NULL, " +
		"             (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"                FROM EXAM_sub_coursewise_attendance_marks " +
		"               WHERE course_id = classes.course_id " +
		"                     AND subject_id = subject.id), " +
		"             (SELECT max(EXAM_attendance_marks.marks) " +
		"                FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"               WHERE course_id = classes.course_id)) + EXAM_subject_rule_settings.practical_ese_maximum_mark) " +
		"          AS practicalTotal, " +
		"       (if(EXAM_internal_mark_supplementary_details.theory_total_mark IS NOT NULL, " +
		"           EXAM_internal_mark_supplementary_details.theory_total_mark, " +
		"           EXAM_student_overall_internal_mark_details.theory_total_mark) + if( " +
		"             EXAM_internal_mark_supplementary_details.theory_total_attendance_mark IS NOT NULL, " +
		"             EXAM_internal_mark_supplementary_details.theory_total_attendance_mark, " +
		"             if(EXAM_student_overall_internal_mark_details.theory_total_attendance_mark IS NULL, 0, " +
		"                EXAM_student_overall_internal_mark_details. " +
		"                theory_total_attendance_mark))  + (if(student_theory_marks IS NULL, 0, student_theory_marks))) " +
		"          AS theoryObtain, " +
		"       (if(EXAM_internal_mark_supplementary_details.practical_total_mark IS NOT NULL, " +
		"           EXAM_internal_mark_supplementary_details. " +
		"           practical_total_mark, " +
		"           EXAM_student_overall_internal_mark_details. " +
		"           practical_total_mark)+ if( " +
		"             EXAM_internal_mark_supplementary_details. " +
		"             practical_total_attendance_mark " +
		"                IS NOT NULL, " +
		"             EXAM_internal_mark_supplementary_details. " +
		"             practical_total_attendance_mark, " +
		"             if( " +
		"                EXAM_student_overall_internal_mark_details. " +
		"                practical_total_attendance_mark " +
		"                   IS NULL,0, " +
		"                EXAM_student_overall_internal_mark_details. " +
		"                practical_total_attendance_mark))+ if(student_practical_marks IS NULL, 0, student_practical_marks)) " +
		"          AS practicalObtain, " +
		"       max(EXAM_sub_definition_coursewise.practical_credit) " +
		"          AS practical_credit, " +
		"       max(EXAM_sub_definition_coursewise.theory_credit) AS theory_credit, " +
		"       ifnull( " +
		"          (SELECT det.register_no " +
		"             FROM EXAM_student_detention_rejoin_details det " +
		"            WHERE det.scheme_no = " +
		"                     (SELECT min(rej.scheme_no) " +
		"                        FROM EXAM_student_detention_rejoin_details rej " +
		"                             LEFT JOIN class_schemewise rej_classscheme " +
		"                                ON rej. " +
		"                                   rejoin_class_schemewise_id = " +
		"                                      rej_classscheme.id " +
		"                             LEFT JOIN classes rej_class " +
		"                                ON rej_classscheme.class_id = " +
		"                                      rej_class.id " +
		"                       WHERE rej.student_id = det.student_id " +
		"                             AND ((rej_class.term_number <> " +
		"                                      classes.term_number) " +
		"                                  OR (rej_class.term_number IS NULL)) " +
		"                             AND det.scheme_no >= classes.term_number) " +
		"                  AND det.student_id = student.id " +
		"            LIMIT 1), " +
		"          student.register_no) " +
		"          AS register_no, " +
		"       if(subject.is_theory_practical = 'T', " +
		"          'Theory', " +
		"          if(subject.is_theory_practical = 'P', 'Practical', 'Practical')) " +
		"          AS subType, " +
		"       if( " +
		"          (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"             FROM EXAM_sub_coursewise_attendance_marks " +
		"            WHERE course_id = classes.course_id " +
		"                  AND subject_id = subject.id) " +
		"             IS NOT NULL, " +
		"          (SELECT max(EXAM_sub_coursewise_attendance_marks.attendance_marks) " +
		"             FROM EXAM_sub_coursewise_attendance_marks " +
		"            WHERE course_id = classes.course_id " +
		"                  AND subject_id = subject.id), " +
		"          (SELECT max(EXAM_attendance_marks.marks) " +
		"             FROM EXAM_attendance_marks EXAM_attendance_marks " +
		"            WHERE course_id = classes.course_id)) " +
		"          AS maxAttMarks, " +
		"       classes.term_number, " +
		"       EXAM_publish_exam_results.publish_date, " +
		"       adm_appln.course_id, " +
		"       ((if( " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            theory_total_mark " +
		"               IS NOT NULL, " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            theory_total_mark, " +
		"            EXAM_student_overall_internal_mark_details. " +
		"            theory_total_mark)+ if( " +
		"              EXAM_internal_mark_supplementary_details. " +
		"              theory_total_attendance_mark " +
		"                 IS NOT NULL, " +
		"              EXAM_internal_mark_supplementary_details. " +
		"              theory_total_attendance_mark, " +
		"              if(EXAM_student_overall_internal_mark_details. " +
		"                 theory_total_attendance_mark " +
		"                    IS NULL, 0, " +
		"                 EXAM_student_overall_internal_mark_details. " +
		"                 theory_total_attendance_mark))+ (if(student_theory_marks IS NULL, 0, student_theory_marks))) / (if( " +
		"              EXAM_subject_rule_settings.final_theory_internal_maximum_mark > " +
		"                 0, " +
		"              EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " +
		"              0)+ if(EXAM_subject_rule_settings.theory_ese_maximum_mark > 0, " +
		"                EXAM_subject_rule_settings.theory_ese_maximum_mark, " +
		"                0))* 100) " +
		"          AS theoryPer, " +
		"       ((if(EXAM_internal_mark_supplementary_details. " +
		"            practical_total_mark IS NOT NULL, " +
		"            EXAM_internal_mark_supplementary_details. " +
		"            practical_total_mark, " +
		"            EXAM_student_overall_internal_mark_details. " +
		"            practical_total_mark)+ if( " +
		"              EXAM_internal_mark_supplementary_details. " +
		"              practical_total_attendance_mark IS NOT NULL, " +
		"              EXAM_internal_mark_supplementary_details. " +
		"              practical_total_attendance_mark, " +
		"              if(EXAM_student_overall_internal_mark_details. " +
		"                 practical_total_attendance_mark IS NULL,0, " +
		"                 EXAM_student_overall_internal_mark_details. " +
		"                 practical_total_attendance_mark)) + if(student_practical_marks IS NULL, 0, student_practical_marks)) / (if( " +
		"              EXAM_subject_rule_settings.final_practical_internal_maximum_mark > 0, " +
		"              EXAM_subject_rule_settings.final_practical_internal_maximum_mark, 0) + if(EXAM_subject_rule_settings.practical_ese_maximum_mark > 0, " +
		"                EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) * 100) " +
		"          AS practicalper, " +
		"       if( " +
		"          (SELECT EXAM_sub_coursewise_grade_defn.grade " +
		"             FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
		"            WHERE theoryPer BETWEEN start_prcntg_grade AND end_prcntg_grade " +
		"                  AND EXAM_sub_coursewise_grade_defn.course_id = " +
		"                         classes.course_id " +
		"                  AND subject_id = subject.id) " +
		"             IS NOT NULL, " +
		"          (SELECT EXAM_sub_coursewise_grade_defn.grade " +
		"             FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
		"            WHERE theoryPer BETWEEN start_prcntg_grade AND end_prcntg_grade " +
		"                  AND EXAM_sub_coursewise_grade_defn.course_id = " +
		"                         classes.course_id " +
		"                  AND subject_id = subject.id), " +
		"          (SELECT EXAM_grade_class_definition.grade " +
		"             FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
		"            WHERE theoryPer BETWEEN start_percentage " +
		"                                AND EXAM_grade_class_definition. " +
		"                                    end_percentage " +
		"                  AND EXAM_grade_class_definition.course_id = " +
		"                         classes.course_id " +
		"            LIMIT 1)) " +
		"          AS thegra, " +
		"       if((SELECT EXAM_sub_coursewise_grade_defn.grade FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id) is not null," +
		" (SELECT EXAM_sub_coursewise_grade_defn.grade FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id)," +
		" (SELECT EXAM_grade_class_definition.grade FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)) as pragra, " +
		"       if( " +
		"          (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		"             FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
		"            WHERE theoryPer BETWEEN start_prcntg_grade AND end_prcntg_grade " +
		"                  AND EXAM_sub_coursewise_grade_defn.course_id = " +
		"                         classes.course_id " +
		"                  AND subject_id = subject.id) " +
		"             IS NOT NULL, " +
		"          (SELECT EXAM_sub_coursewise_grade_defn.grade_point " +
		"             FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn " +
		"            WHERE theoryPer BETWEEN start_prcntg_grade AND end_prcntg_grade " +
		"                  AND EXAM_sub_coursewise_grade_defn.course_id = " +
		"                         classes.course_id " +
		"                  AND subject_id = subject.id), " +
		"          (SELECT EXAM_grade_class_definition.grade_point " +
		"             FROM EXAM_grade_class_definition EXAM_grade_class_definition " +
		"            WHERE theoryPer BETWEEN start_percentage " +
		"                                AND EXAM_grade_class_definition. " +
		"                                    end_percentage " +
		"                  AND EXAM_grade_class_definition.course_id = " +
		"                         classes.course_id " +
		"            LIMIT 1)) " +
		"          AS thegrap, " +
		"       if((SELECT EXAM_sub_coursewise_grade_defn.grade_point FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id) is not null," +
		" (SELECT EXAM_sub_coursewise_grade_defn.grade_point FROM EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn where practicalper between start_prcntg_grade and end_prcntg_grade and EXAM_sub_coursewise_grade_defn.course_id = classes.course_id and subject_id=subject.id)," +
		" (SELECT EXAM_grade_class_definition.grade_point FROM EXAM_grade_class_definition EXAM_grade_class_definition where practicalper between start_percentage and EXAM_grade_class_definition.end_percentage and EXAM_grade_class_definition.course_id = classes.course_id limit 1)) as pragrap, " +
		"       EXAM_sub_definition_coursewise.dont_show_max_marks, " +
		"       EXAM_sub_definition_coursewise.dont_show_min_marks, " +
		"       EXAM_sub_definition_coursewise.show_only_grade, " +
		"       course_scheme.name, " +
		"       EXAM_sub_definition_coursewise.dont_show_sub_type, " +
		" EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln  ," +
		"	 EXAM_sub_definition_coursewise.dont_show_att_marks,subject.is_certificate_course,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,  " +
		"       EXAM_student_pass_fail.pass_fail, " +
		"       EXAM_supplementary_improvement_application.is_appeared_theory, " +
		"       EXAM_supplementary_improvement_application.is_appeared_practical, " +
		"       subject.is_theory_practical, " +
		"       EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark, " +
		"       EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark, " +
		"       EXAM_subject_rule_settings.final_theory_internal_is_attendance, " +
		"       EXAM_subject_rule_settings.final_practical_internal_is_attendance " +
		"  FROM EXAM_supplementary_improvement_application " +
		"       LEFT JOIN student " +
		"          ON (EXAM_supplementary_improvement_application.student_id = " +
		"                 student.id) " +
		"       LEFT JOIN classes " +
		"          ON (EXAM_supplementary_improvement_application.class_id = " +
		"                 classes.id) " +
		"       LEFT JOIN subject " +
		"          ON (EXAM_supplementary_improvement_application.subject_id = " +
		"                 subject.id) " +
		"       LEFT JOIN EXAM_definition " +
		"          ON (EXAM_supplementary_improvement_application.exam_id = " +
		"                 EXAM_definition.id) " +
		"       LEFT JOIN adm_appln " +
		"          ON student.adm_appln_id = adm_appln.id " +
		"       LEFT JOIN personal_data " +
		"          ON adm_appln.personal_data_id = personal_data.id " +
		"       LEFT JOIN subject_type " +
		"          ON subject.subject_type_id = subject_type.id " +
		"       LEFT JOIN EXAM_student_overall_internal_mark_details " +
		"          ON (EXAM_student_overall_internal_mark_details.student_id = " +
		"                 student.id) " +
		"             AND EXAM_student_overall_internal_mark_details.subject_id = " +
		"                    subject.id " +
		"             AND EXAM_student_overall_internal_mark_details.class_id = " +
		"                    classes.id " +
		"       LEFT JOIN class_schemewise " +
		"          ON class_schemewise.class_id = classes.id " +
		"       LEFT JOIN curriculum_scheme_duration " +
		"          ON class_schemewise.curriculum_scheme_duration_id = " +
		"                curriculum_scheme_duration.id " +
		"       LEFT JOIN curriculum_scheme " +
		"          ON curriculum_scheme_duration.curriculum_scheme_id = " +
		"                curriculum_scheme.id " +
		"       LEFT JOIN course_scheme " +
		"          ON curriculum_scheme.course_scheme_id = course_scheme.id " +
		"       LEFT JOIN EXAM_student_final_mark_details " +
		"          ON (EXAM_student_final_mark_details.class_id = classes.id) " +
		"             AND (EXAM_student_final_mark_details.exam_id = " +
		"                     EXAM_definition.id) " +
		"             AND (EXAM_student_final_mark_details.student_id = student.id) " +
		"             AND (EXAM_student_final_mark_details.subject_id = subject.id) " +
		"       LEFT JOIN EXAM_internal_mark_supplementary_details " +
		"          ON (EXAM_internal_mark_supplementary_details.student_id = " +
		"                 student.id) " +
		"             AND (EXAM_internal_mark_supplementary_details.subject_id = " +
		"                     subject.id) " +
		"             AND (EXAM_internal_mark_supplementary_details.class_id = " +
		"                     classes.id) " +
		"             AND if((EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id or classes.course_id=18), "+ /*if for the current exam record available or BBA take current exam, else take from the previous last exam*/
		" (EXAM_internal_mark_supplementary_details.exam_id=EXAM_supplementary_improvement_application.exam_id), "+
		" (EXAM_internal_mark_supplementary_details.exam_id=(select max(e.exam_id) from "+
		" EXAM_internal_mark_supplementary_details e where e.class_id=classes.id and e.student_id=student.id and e.subject_id=subject.id and e.exam_id<=EXAM_supplementary_improvement_application.exam_id))) " +
		"       LEFT JOIN EXAM_student_pass_fail EXAM_student_pass_fail " +
		"          ON     (EXAM_student_pass_fail.student_id = student.id) " +
		"             AND (EXAM_student_pass_fail.class_id = classes.id) " +
		"             AND (EXAM_student_pass_fail.scheme_no = classes.term_number) " +
		"       LEFT JOIN EXAM_subject_rule_settings " +
		"          ON EXAM_subject_rule_settings.subject_id = subject.id " +
		"             AND EXAM_subject_rule_settings.academic_year = " +
		"                    curriculum_scheme_duration.academic_year " +
		"             AND EXAM_subject_rule_settings.course_id = classes.course_id " +
		"             AND EXAM_subject_rule_settings.scheme_no = classes.term_number " +
		"       LEFT JOIN EXAM_publish_exam_results " +
		"          ON EXAM_publish_exam_results.class_id = classes.id " +
		"             AND EXAM_publish_exam_results.exam_id = EXAM_definition.id " +
		"       LEFT JOIN EXAM_sub_definition_coursewise " +
		"          ON EXAM_sub_definition_coursewise.subject_id = subject.id " +
		"             AND EXAM_sub_definition_coursewise.course_id = classes.course_id " +
		"             AND EXAM_sub_definition_coursewise.scheme_no = " +
		"                    classes.term_number " +
		"             AND EXAM_sub_definition_coursewise.academic_year = " +
		"                    curriculum_scheme_duration.academic_year " +
		"       LEFT JOIN EXAM_subject_sections " +
		"          ON EXAM_sub_definition_coursewise.subject_section_id = " +
		"                EXAM_subject_sections.id " +
		" WHERE (EXAM_supplementary_improvement_application.is_appeared_practical = 1 " +
		"        OR EXAM_supplementary_improvement_application.is_appeared_theory = 1) " +
		"       AND (EXAM_supplementary_improvement_application.is_failed_practical =  1 " +
		"            OR EXAM_supplementary_improvement_application.is_failed_theory = 1) " +
		"       AND subject.is_theory_practical = 'B' " +
		"       AND EXAM_student_final_mark_details.student_practical_marks " +
		"              IS NOT NULL " +
		"       AND EXAM_supplementary_improvement_application.exam_id ="+objForm.getSupExamId()+
		"       AND EXAM_subject_rule_settings.academic_year = " +
		"              curriculum_scheme_duration.academic_year " +
		"       AND classes.term_number =" +objForm.getSemesterYearNo()+
		"       AND student.id=" +objForm.getStudentId()+
		" GROUP BY student.id, EXAM_student_final_mark_details.exam_id, subject.id " ;

		return query;
	}


	public MarksCardTO getSupMarksCardForPgView(List<Object[]> pgMarksCardData) throws Exception {
		MarksCardTO Supto=null;
		Map<String,List<SubjectTO>> subMap=new HashMap<String, List<SubjectTO>>();
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int totalMarksAwarded=0;
			int totalMaxMarks=0;
			int totalCredits=0;
			double tcForCal=0;
			double gpForCal=0;
			String resultClass="Pass";
			int studentId=0;
			int examId=0;
			int supExamId=0;

			int cid=0;
			// Getting Grade for Fail from database
			String gradeForFail="";
			List<String> gradeList=transaction.getStudentHallTicket("SELECT EXAM_exam_settings.grade_for_fail FROM EXAM_exam_settings EXAM_exam_settings");
			if(gradeList!=null && !gradeList.isEmpty()){
				gradeForFail=gradeList.get(0);
			}

			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				boolean isDisplay=true;
				boolean isAdd=true;
				boolean showSubType=true;
				boolean showMaxMarks=true;
				boolean showMinMarks=true;
				boolean showAttMarks=true;
				//String ExamName="";

				String grade="";


				if(obj[47]!=null){
					if(obj[47].toString().trim().equals("true")){
						isAdd=false;
					}
				}
				/*if(obj[52]!=null){
				      ExamName=obj[52].toString();

				}*/
				if(obj[51]!=null){
					supExamId=Integer.parseInt(obj[51].toString());

				}
				if(obj[42]!=null){
					if(obj[42].toString().trim().equals("true")){
						showMaxMarks=false;
					}
				}
				if(obj[43]!=null){
					if(obj[43].toString().trim().equals("true")){
						showMinMarks=false;
					}
				}
				if(obj[46]!=null){
					if(obj[46].toString().trim().equals("true")){
						showSubType=false;
					}
				}
				/*if(obj[48]!=null){
					if(obj[48].toString().trim().equals("true")){
						showAttMarks=false;
					}
				}*/
				if(obj[33]!=null){
					if(!obj[33].toString().equalsIgnoreCase("Practical")){
						if(obj[39]!=null){
							double min=0;
							double stu=0;
							if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
								min = Double.parseDouble(obj[11].toString());
							if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
								stu = Double.parseDouble(obj[15].toString());

							if(obj[39].toString().equals("F") || min>stu){
								resultClass="Fail";
								grade=gradeForFail;
							}else{
								grade=obj[39].toString();
							}
						}
						else
						{
							grade="";
						}
					}else{
						if(obj[40]!=null){
							double min=0;
							double stu=0;
							if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
								min = Double.parseDouble(obj[13].toString());
							if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
								stu = Double.parseDouble(obj[16].toString());

							if(obj[40].toString().equals("F") ||  min>stu){
								resultClass="Fail";
								grade=gradeForFail;
							}else{
								if(obj[40]!=null)
									grade=obj[40].toString();
							}
						}else
						{
							grade="";	
						}

					}
				}


				if(Supto==null){
					Supto=new MarksCardTO();

					if(obj[0]!=null){
						examId=Integer.parseInt(obj[0].toString());
						/*String monthQuery="select cast(month as unsigned) as month,year from EXAM_definition where del_is_active = 1 and id="+obj[0].toString();
						List<Object[]> monthList=transaction.getStudentHallTicket(monthQuery);
						if(monthList!=null){
							Iterator<Object[]> mItr=monthList.iterator();
							if (mItr.hasNext()) {
								Object[] m = (Object[]) mItr.next();
								String month="";
								String year="";
								if(m[0]!=null){
									month=monthMap.get(m[0].toString());
								}
								if(m[1]!=null){
									year=m[1].toString();
								}
								to.setMonthYear(month+" "+year);
								}
							}*/
					}
					if(obj[1]!=null){
						studentId=Integer.parseInt(obj[1].toString());
					}
					if(obj[10]!=null){
						cid=Integer.parseInt(obj[10].toString());
						Supto.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(cid,"Course",true,"name"));
						//						if(cid==87){
						//							Supto.setCourseName("MBA - International Business");
						//						}else if(cid==84){
						//							Supto.setCourseName("Post Graduate Diploma in Management");
						//						}else if(cid==1 || cid==85 || cid==86 || cid==140){
						//							Supto.setCourseName("Master of Business Administration");
						//						}else{
						//							
						//							String query="select c.program.name,c.name,c.program.programType.id from Course c where c.isActive=1 and c.id="+cid;
						//							List<Object[]> clist=transaction.getDataByHql(query);
						//							
						//							if(clist!=null && !clist.isEmpty()){
						//								Iterator<Object[]> it=clist.iterator();
						//								if (it.hasNext()) {
						//									Object[] c = (Object[]) it.next();
						//									if(c[2]!=null){
						//										int ptid=Integer.parseInt(c[2].toString());
						//										if(ptid!=3){
						//											if(c[0]!=null){
						//												Supto.setCourseName(c[0].toString());
						//											}
						//										}else{
						//											if(c[1]!=null){
						//												Supto.setCourseName(c[1].toString());
						//											}
						//										}
						//									}
						//								}
						//							}
						//						}
					}
					if(obj[17]!=null){
						Supto.setStudentName(obj[17].toString());
					}
					if(obj[18]!=null){
						Supto.setStudentName(Supto.getStudentName()+" "+obj[18].toString());
					}
					if(obj[19]!=null){
						Supto.setStudentName(Supto.getStudentName()+" "+obj[19].toString());
					}
					if(obj[32]!=null){
						Supto.setRegNo(obj[32].toString());
					}
					if(obj[35]!=null){
						Supto.setSemNo(obj[35].toString());
					}
					if(obj[51]!=null)
					{
						Supto.setExamId(obj[51].toString());
					}
					/*if(obj[50]!=null)
					{
						to.setExamName2(obj[50].toString());
					}*/
					/*	if(ExamName!=null)
					{
						Supto.setExamNameSup(ExamName);
					}*/
					/*if(obj[36]!=null){
						to.setDate(CommonUtil.ConvertStringToDateFormat(obj[36].toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					}*/
					if(obj[45]!=null){
						Supto.setSemType(obj[47].toString());
					}
					if(obj[20]!=null){
						List<SubjectTO> subList=null;
						if(subMap.containsKey(obj[20].toString())){
							subList=subMap.remove(obj[20].toString());
						}else{
							subList=new ArrayList<SubjectTO>();
						}

						SubjectTO subto=new SubjectTO();
						subto.setId(Integer.parseInt(obj[3].toString()));
						if(obj[4]!=null){
							subto.setCode(obj[4].toString());
						}
						if(obj[5]!=null){
							subto.setName(obj[5].toString());
						}
						if(obj[23]!=null)
							subto.setSubOrder(Integer.parseInt(obj[23].toString()));
						if(obj[33]!=null){
							if(obj[33].toString().equalsIgnoreCase("Theory")){
								if(showSubType)
									subto.setType("Theory");
								else
									subto.setType("");

								subto.setGrade(grade);

								if(obj[31]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
									if(subto.getGrade()!=null){
										if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
											totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
										}
									}

									tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
									if(obj[40]!=null && !obj[40].toString().isEmpty()){
										double d=Double.parseDouble((obj[40].toString()))*Double.parseDouble((obj[31].toString()));
										gpForCal=gpForCal+d;
									}
								}
								int ciaMarksAwarded=0;
								if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
								}
								/*if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
								}*/
								if(obj[11]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[12]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
									if(isDisplay && m>0)
										subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(obj[15].toString());
									else
										subto.setEseMarksAwarded("-");
								}
								if(obj[25]!=null){
									if(showMaxMarks)
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[25].toString()))));
									else
										subto.setCiaMaxMarks("-");
								}
								if(obj[26]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[28].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
								if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
									if(isDisplay)
										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									else
										subto.setCiaMarksAwarded("-");
								}else{
									if(isDisplay){
										if(CommonUtil.isValidDecimal(obj[6].toString()))
											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
										else
											subto.setCiaMarksAwarded(obj[6].toString());
									}else
										subto.setCiaMarksAwarded("-");
								}
							}
							if(obj[33].toString().equalsIgnoreCase("Practical")){
								if(showSubType)
									subto.setType("Practical");
								else
									subto.setType("");

								subto.setGrade(grade);

								if(obj[30]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
									if(subto.getGrade()!=null){
										if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
											totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
										}
									}


									tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
									if(obj[41]!=null && !obj[41].toString().isEmpty()){
										double d=Double.parseDouble((obj[41].toString()))*Double.parseDouble((obj[30].toString()));
										gpForCal=gpForCal+d;
									}
									else
									{
										gpForCal=0;
									}
								}

								int ciaMarksAwarded=0;
								if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
								}
								/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
								}*/
								if(obj[13]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[14]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[16].toString()));
									if(isDisplay && m>0)
										subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(obj[16].toString());
									else
										subto.setEseMarksAwarded("-");
								}
								if(obj[24]!=null){
									if(showMaxMarks)
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[24].toString()))));
									else
										subto.setCiaMaxMarks("-");
								}
								if(obj[27]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[29].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
								if(ciaMarksAwarded!=0  && obj[8]!=null && obj[9]!=null &&  CommonUtil.isValidDecimal(obj[8].toString()) && CommonUtil.isValidDecimal(obj[9].toString())){
									if(isDisplay)
										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									else
										subto.setCiaMarksAwarded("-");
								}else{

									if(isDisplay)
										if(CommonUtil.isValidDecimal(obj[8].toString()))
											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
										else
											subto.setCiaMarksAwarded(obj[8].toString());
									else
										subto.setCiaMarksAwarded("-");
								}
							}

						}
						subList.add(subto);
						subMap.put(obj[20].toString(), subList);
					}
				}else{
					if(obj[20]!=null){
						List<SubjectTO> subList=null;
						if(subMap.containsKey(obj[20].toString())){
							subList=subMap.remove(obj[20].toString());
						}else{
							subList=new ArrayList<SubjectTO>();
						}

						SubjectTO subto=new SubjectTO();
						subto.setId(Integer.parseInt(obj[3].toString()));
						if(obj[4]!=null){
							subto.setCode(obj[4].toString());
						}
						if(obj[5]!=null){
							subto.setName(obj[5].toString());
						}
						if(obj[23]!=null)
							subto.setSubOrder(Integer.parseInt(obj[23].toString()));
						if(obj[33]!=null){
							if(obj[33].toString().equalsIgnoreCase("Theory")){
								if(showSubType)
									subto.setType("Theory");
								else
									subto.setType("");

								subto.setGrade(grade);

								if(obj[31]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
									if(subto.getGrade()!=null){
										if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
											totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
										}
									}
									tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
									if(obj[40]!=null && !obj[40].toString().isEmpty()) {
										double d=Double.parseDouble((obj[40].toString()))*Double.parseDouble((obj[31].toString()));
										gpForCal=gpForCal+d;
									}
									else
									{
										gpForCal=0;
									}
								}

								int ciaMarksAwarded=0;
								if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
								}
								/*if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
								}*/
								if(obj[11]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[12]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
									if(isDisplay && m>0)
										subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(obj[15].toString());
									else
										subto.setEseMarksAwarded("-");
								}
								if(obj[25]!=null){
									if(showMaxMarks)
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[25].toString()))));
									else
										subto.setCiaMaxMarks("-");
								}
								if(obj[26]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[26].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[28].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
								if(ciaMarksAwarded!=0  && obj[6]!=null && obj[7]!=null && CommonUtil.isValidDecimal(obj[6].toString()) && CommonUtil.isValidDecimal(obj[7].toString())){
									if(isDisplay)
										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									else
										subto.setCiaMarksAwarded("-");
								}else{
									if(isDisplay){
										if(CommonUtil.isValidDecimal(obj[6].toString()))
											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
										else
											subto.setCiaMarksAwarded(obj[6].toString());
									}else
										subto.setCiaMarksAwarded("-");
								}
							}
							if(obj[33].toString().equalsIgnoreCase("Practical")){
								if(showSubType)
									subto.setType("Practical");
								else
									subto.setType("");

								subto.setGrade(grade);

								if(obj[30]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
									if(subto.getGrade()!=null){
										if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("F") ){
											totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
										}
									}
									tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
									if(obj[42]!=null && !obj[42].toString().isEmpty()){
										double d=Double.parseDouble((obj[42].toString()))*Double.parseDouble((obj[30].toString()));
										gpForCal=gpForCal+d;
									}

								}
								int ciaMarksAwarded=0;
								if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
								}
								/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
								}*/
								if(obj[13]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[14]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[16].toString()));
									if(isDisplay && m>0)
										subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
									else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setEseMarksAwarded(obj[16].toString());
									else
										subto.setEseMarksAwarded("-");
								}
								if(obj[24]!=null){
									if(showMaxMarks)
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[24].toString()))));
									else
										subto.setCiaMaxMarks("-");
								}
								if(obj[27]!=null){
									if(isAdd)
										totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[27].toString()));
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[27].toString()))));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
									if(isDisplay)
										subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
									else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay)
										subto.setTotalMarksAwarded(obj[29].toString());
									else
										subto.setTotalMarksAwarded("-");
								}
								if(ciaMarksAwarded!=0  && obj[8]!=null && obj[9]!=null &&  CommonUtil.isValidDecimal(obj[8].toString()) && CommonUtil.isValidDecimal(obj[9].toString())){
									if(isDisplay)
										subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
									else
										subto.setCiaMarksAwarded("-");
								}else{

									if(isDisplay)
										if(CommonUtil.isValidDecimal(obj[8].toString()))
											subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
										else
											subto.setCiaMarksAwarded(obj[8].toString());
									else
										subto.setCiaMarksAwarded("-");
								}
							}

						}
						subList.add(subto);
						subMap.put(obj[20].toString(), subList);

					}
				}
			}

			Map<String,List<SubjectTO>> finalsubMap=new HashMap<String, List<SubjectTO>>();
			List<SubjectTO> addOnCourse=null;
			boolean isAddonCourse=false;
			if(!subMap.isEmpty()){
				Iterator it = subMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					if(pairs.getKey().toString().equalsIgnoreCase("Add On Course")){
						addOnCourse = (ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(addOnCourse,comparator);
						isAddonCourse=true;
					}else{
						List<SubjectTO> list=(ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(list,comparator);
						finalsubMap.put(pairs.getKey().toString(),list);
					}
				}
			}
			Map<String,List<SubjectTO>> addOnCoursesubMap=new HashMap<String, List<SubjectTO>>();
			if(isAddonCourse){
				addOnCoursesubMap.put("Add On Course", addOnCourse);
			}
			Supto.setSubMap(CommonUtil.sortMapByKey(finalsubMap));
			Supto.setAddOnCoursesubMap(addOnCoursesubMap);

			DecimalFormat twoDForm = new DecimalFormat("#.##");

			Supto.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
			Supto.setTotalMaxmarks(String.valueOf(totalMaxMarks));
			Supto.setTotalMarksInWord(CommonUtil.numberToWord1(totalMarksAwarded));
			Supto.setTotalCredits(String.valueOf(totalCredits));

			double tgpa=0;

			/*	if(gpForCal>0 && tcForCal>0){
				tgpa=CommonUtil.Round((gpForCal/tcForCal),2);

			}
			Supto.setGradePoints(String.valueOf(twoDForm.format(tgpa)));*/
			String countQuery="select count(*) from EXAM_update_exclude_withheld " +
			" where student_id = " +studentId+
			" and exam_id ="+examId;
			int count=0;
			List<BigInteger> countList=transaction.getStudentHallTicket(countQuery);
			if(countList!=null && !countList.isEmpty()){
				BigInteger b=countList.get(0);
				count=b.intValue();
			}
			String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
			double agg=0;
			List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
			if(aggList!=null && !aggList.isEmpty()){
				agg=Double.parseDouble(aggList.get(0).toString());
			}

			if(count<1){
				double r=((double)(totalMarksAwarded)/(double)(totalMaxMarks))*100;
				if(r>=agg){
					String query="select result_class from EXAM_grade_class_definition where course_id="+cid+" and "+r+" between start_percentage and end_percentage";
					List<String> rList=transaction.getStudentHallTicket(query);
					if(rList!=null && !rList.isEmpty()){
						Iterator<String> rItr=rList.iterator();
						while (rItr.hasNext()) {
							String robj = rItr.next();
							if(robj!=null && resultClass.equals("Pass"))
								Supto.setResult(robj);
							else
								Supto.setResult("Failed");
						}
					}
				}else
					Supto.setResult("Failed");
			}else{
				Supto.setResult("WITH HELD");
			}
		}


		return Supto;


	}	


	public String getExamSemList(DisciplinaryDetailsForm objForm) throws Exception {		
		String query= "SELECT classes.term_number, EXAM_definition.exam_code, classes.name,EXAM_definition.month,EXAM_definition.year,EXAM_definition.id "+ 
		" from EXAM_student_overall_internal_mark_details "+ 
		" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+ 
		" LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id "+ 
		" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+ 
		" LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id "+ 
		" where student.id= " +objForm.getStudentId() +
		" UNION " +
		" SELECT classes.term_number, EXAM_definition.exam_code, classes.name,EXAM_definition.month,EXAM_definition.year,EXAM_definition.id "+ 
		" from EXAM_student_overall_internal_mark_details "+ 
		" LEFT JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+ 
		" LEFT JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id "+ 
		" LEFT JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+ 
		" LEFT JOIN class_schemewise ON class_schemewise.class_id = classes.id "+ 
		" where  student.id = " +objForm.getStudentId() ;


		return query;
	}

	public List<MarksCardTO> getExamSemList(List<Object[]> pgMarksCardData) throws Exception {
		MarksCardTO to=null;
		ArrayList<MarksCardTO> list = new ArrayList<MarksCardTO>();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int studentId=0;

			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				to=new MarksCardTO();	

				if(obj[0]!=null){
					to.setSemNo(obj[0].toString());
				}
				if(obj[1]!=null)
				{
					to.setExamName(obj[1].toString());
				}

				if(obj[2]!=null)
				{
					to.setClassName(obj[2].toString());
				}
				if(obj[3]!=null)
				{
					to.setMonth(Integer.parseInt(obj[3].toString()));
				}
				if(obj[4]!=null)
				{
					to.setYear(Integer.parseInt(obj[4].toString()));
				}
				if(obj[5]!=null)
				{
					to.setExamDefId(Integer.parseInt(obj[5].toString()));
				}
				list.add(to);
			}


		}

		return list;
	}

	/*public String getSupExamSemListhibernate(DisciplinaryDetailsForm objForm) throws Exception {	
	String query= "from ExamSupplementaryImprovementApplicationBO exam where exam.studentId=" +objForm.getStudentId() +" group by exam.examDefinitionBO.id, cl";
return query;
}*/
	public String getSupExamSemList(DisciplinaryDetailsForm objForm) throws Exception {	
		String query= "select EXAM_supplementary_improvement_application.exam_id,EXAM_definition.exam_code, EXAM_supplementary_improvement_application.scheme_no,EXAM_definition.month,EXAM_definition.year,EXAM_definition.id "+
		" from  EXAM_supplementary_improvement_application "+
		"inner join EXAM_definition ON EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id "+
		"where (EXAM_supplementary_improvement_application.is_appeared_practical=1 or EXAM_supplementary_improvement_application.is_appeared_theory=1) "+
		"AND student_id not in ( "+
		"SELECT EXAM_update_exclude_withheld.student_id "+
		"FROM EXAM_update_exclude_withheld EXAM_update_exclude_withheld where EXAM_update_exclude_withheld.exclude_from_results = 1)"+
		"and student_id= " +objForm.getStudentId() +" group by EXAM_supplementary_improvement_application.exam_id, "+
		"EXAM_supplementary_improvement_application.scheme_no order by EXAM_supplementary_improvement_application.scheme_no";

		return query;
	}



	public List<MarksCardTO> getSupExamSemList(List<Object[]> pgMarksCardData) throws Exception {
		MarksCardTO to=null;
		ArrayList<MarksCardTO> list = new ArrayList<MarksCardTO>();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int studentId=0;

			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				to=new MarksCardTO();	

				if(obj[0]!=null){
					to.setExamId(obj[0].toString());
				}
				if(obj[1]!=null)
				{
					to.setExamName(obj[1].toString());
				}
				if(obj[2]!=null)
				{
					to.setSemNo(obj[2].toString());
				}
				if(obj[3]!=null){
					to.setMonth(Integer.parseInt(obj[3].toString()));
				}
				if(obj[4]!=null){
					to.setYear(Integer.parseInt(obj[4].toString()));
				}
				if(obj[5]!=null){
					to.setExamDefId(Integer.parseInt(obj[5].toString()));
				}
				list.add(to);
			}


		}

		return list;
	}

	/**
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public ClearanceCertificateTO convertBotoTo(ExamBlockUnblockHallTicketBO bo) throws Exception {
		ClearanceCertificateTO to=new ClearanceCertificateTO();
		String name=bo.getStudentUtilBO().getAdmApplnUtilBO().getPersonalDataUtilBO().getFirstName();
		if(bo.getStudentUtilBO().getAdmApplnUtilBO().getPersonalDataUtilBO().getMiddleName()!=null)
			name=name+" "+bo.getStudentUtilBO().getAdmApplnUtilBO().getPersonalDataUtilBO().getMiddleName();
		if(bo.getStudentUtilBO().getAdmApplnUtilBO().getPersonalDataUtilBO().getLastName()!=null)
			name=name+" "+bo.getStudentUtilBO().getAdmApplnUtilBO().getPersonalDataUtilBO().getLastName();
		to.setName(name);
		to.setClassName(bo.getClassUtilBO().getName());
		to.setRegisterNo(bo.getStudentUtilBO().getRegisterNo());
		if(bo.getClassUtilBO().getSectionName()!=null)
			to.setClassName(to.getClassName());
		String[] reasons=bo.getBlockReason().split(",");
		to.setComments(Arrays.asList(reasons));
		to.setDate(CommonUtil.getTodayDate());
		return to;
	}




	public String getCjcExamSemList(DisciplinaryDetailsForm objForm) throws Exception {	
		String query="SELECT classes.term_number, EXAM_definition.id, classes.name, classes.id as classId "+
		"from student "+
		"LEFT  JOIN  EXAM_marks_entry on EXAM_marks_entry.student_id = student.id "+
		"LEFT JOIN  EXAM_marks_entry_details ON EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id "+
		"LEFT JOIN  classes ON EXAM_marks_entry.class_id = classes.id AND classes.is_active=1 "+
		"LEFT JOIN  class_schemewise ON class_schemewise.class_id = classes.id "+
		"LEFT JOIN  curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		"LEFT JOIN  EXAM_definition ON EXAM_marks_entry.exam_id = EXAM_definition.id "+
		"LEFT JOIN  exam_type ON EXAM_definition.exam_type_id = exam_type.id "+
		"where  student.id = " +objForm.getStudentId() +" and  exam_type.id not in(4,5) group by EXAM_definition.id "+
		"union "+
		"SELECT classes.term_number, EXAM_definition.id, classes.name, classes.id as classId "+
		"from student "+
		"LEFT  JOIN  EXAM_marks_entry on EXAM_marks_entry.student_id = student.id "+
		"LEFT JOIN  EXAM_marks_entry_details ON EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id "+
		"LEFT JOIN  classes ON EXAM_marks_entry.class_id = classes.id AND classes.is_active=1 "+
		"LEFT JOIN  class_schemewise ON class_schemewise.class_id = classes.id "+
		"LEFT JOIN  curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		"LEFT JOIN  EXAM_definition ON EXAM_marks_entry.exam_id = EXAM_definition.id "+
		"LEFT JOIN  exam_type ON EXAM_definition.exam_type_id = exam_type.id "+
		"where  student.id = " +objForm.getStudentId() +"  and  exam_type.id not in(4,5) group by EXAM_definition.id ";
		return query;
	}

	public List<MarksCardTO> getCjcExamSemList(List<Object[]> pgMarksCardData) throws Exception {
		MarksCardTO to=null;
		ArrayList<MarksCardTO> list = new ArrayList<MarksCardTO>();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int studentId=0;

			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				to=new MarksCardTO();	

				if(obj[0]!=null){
					to.setSemNo(obj[0].toString());
				}
				if(obj[1]!=null)
				{
					to.setExamId(obj[1].toString());
					int examId1=Integer.parseInt(obj[1].toString());
					to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId1,"ExamDefinitionBO",true,"name"));
				}

				if(obj[2]!=null)
				{
					to.setClassName(obj[2].toString());

				}
				if(obj[3]!=null)
				{
					to.setClassesId(Integer.parseInt(obj[3].toString()));

				}
				list.add(to);
			}


		}

		return list;
	}

	public String getQueryCjcStudentMarksCard(DisciplinaryDetailsForm objForm) throws Exception 
	{
		String query= "SELECT EXAM_subject_sections.name as subSection,EXAM_definition.id as examId, classes.name as className, student.register_no, subject.name as subName, "+
		"if(subject.is_second_language=1,"+"'Sec. Lang.'"+",subject.code) as code, student.student_no,personal_data.first_name,personal_data.second_language, "+
		"EXAM_marks_entry_details.theory_marks,EXAM_marks_entry_details.practical_marks, " +
		"if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.theory_marks,0)+ "+
		"if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.practical_marks,0) as totObtain, "+
		"if(EXAM_subject_rule_settings.theory_ese_minimum_mark is not null,EXAM_subject_rule_settings.theory_ese_minimum_mark,0)+ "+
		"if(EXAM_subject_rule_settings.practical_ese_minimum_mark is not null,EXAM_subject_rule_settings.practical_ese_minimum_mark,0) as minTot, "+
		"if(EXAM_subject_rule_settings.theory_ese_maximum_mark is not null,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)+ "+
		"if(EXAM_subject_rule_settings.practical_ese_maximum_mark is not null,EXAM_subject_rule_settings.practical_ese_maximum_mark,0) as maxTot, "+
		"round(((if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.theory_marks,0)+ "+
		"if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.practical_marks,0))/ "+
		"(if(EXAM_subject_rule_settings.theory_ese_maximum_mark is not null,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)+ "+
		"if(EXAM_subject_rule_settings.practical_ese_maximum_mark is not null,EXAM_subject_rule_settings.practical_ese_maximum_mark,0))*100),2) as per, "+
		"EXAM_sub_definition_coursewise.subject_order, "+
		"EXAM_subject_sections.id "+
		"FROM student "+ 
		"INNER JOIN  adm_appln ON student.adm_appln_id = adm_appln.id "+
		"AND student.is_admitted=1 "+
		"AND adm_appln.is_cancelled=0 "+
		"INNER JOIN  personal_data ON adm_appln.personal_data_id = personal_data.id "+
		"INNER JOIN  course ON adm_appln.selected_course_id = course.id "+
		"INNER JOIN  program ON course.program_id = program.id "+ 
		"INNER JOIN  program_type ON program.program_type_id = program_type.id "+
		"INNER JOIN  EXAM_marks_entry on EXAM_marks_entry.student_id = student.id "+
		"INNER JOIN  EXAM_marks_entry_details ON EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id "+
		"INNER JOIN  classes ON EXAM_marks_entry.class_id = classes.id "+
		"AND classes.is_active=1 "+
		"INNER JOIN  class_schemewise ON class_schemewise.class_id = classes.id "+
		"INNER JOIN  curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
		"INNER JOIN  EXAM_definition ON EXAM_marks_entry.exam_id = EXAM_definition.id "+ 
		"AND EXAM_definition.del_is_active=1 "+
		"INNER JOIN  exam_type ON EXAM_definition.exam_type_id = exam_type.id "+
		"INNER JOIN  subject ON EXAM_marks_entry_details.subject_id = subject.id "+
		"AND subject.is_active=1 "+
		"LEFT JOIN  EXAM_sub_definition_coursewise on EXAM_sub_definition_coursewise.course_id = course.id "+
		"AND EXAM_sub_definition_coursewise.subject_id = subject.id "+
		"AND EXAM_sub_definition_coursewise.scheme_no = classes.term_number "+
		"AND EXAM_sub_definition_coursewise.academic_year=curriculum_scheme_duration.academic_year "+
		"LEFT JOIN EXAM_subject_sections ON EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id "+
		"INNER JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id "+
		"AND EXAM_subject_rule_settings.course_id = course.id "+
		"AND EXAM_subject_rule_settings.academic_year=curriculum_scheme_duration.academic_year "+
		"AND EXAM_subject_rule_settings.scheme_no=classes.term_number "+
		"AND EXAM_subject_rule_settings.is_active=1 "+
		"LEFT JOIN EXAM_internal_exam_type ON EXAM_definition.internal_exam_type_id = EXAM_internal_exam_type.id "+
		"LEFT JOIN EXAM_update_exclude_withheld ON EXAM_update_exclude_withheld.student_id = student.id "+ 
		"AND EXAM_update_exclude_withheld.course_id = course.id "+
		"AND EXAM_update_exclude_withheld.scheme_no = classes.term_number "+
		"where  student.id = " +objForm.getStudentId() +"  and EXAM_definition.id =" +objForm.getExamIdCjc() +" and classes.id=" +objForm.getClassIdCjc() +" and exam_type.id not in(4,5) order by EXAM_subject_sections.name ";
		return query;
	}





	public MarksCardTO getMarksCardForCjcView(List<Object[]> pgMarksCardData) throws Exception {
		MarksCardTO to=null;
		Map<String,List<SubjectTO>> subMap=new HashMap<String, List<SubjectTO>>();
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int totalMarksAwarded=0;
			int totalMaxMarks=0;
			int totalCredits=0;
			int studentId=0;
			int examId=0;
			String resultClass="";
			int cid=0;
			Map<Integer, MarksCardTO> map = null;
			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();

				if(to==null){
					to=new MarksCardTO();

					if(obj[1]!=null){
						int examId1=Integer.parseInt(obj[1].toString());
						to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId1,"ExamDefinitionBO",true,"name"));
					}
					if(obj[2]!=null){
						to.setClassName(obj[2].toString());
					}
					if(obj[3]!=null){
						to.setRegNo(obj[3].toString());
					}
					if(obj[7]!=null){
						to.setStudentName(obj[7].toString());
					}
					if(obj[0]!=null){
						List<SubjectTO> subList=null;
						if(subMap.containsKey(obj[0].toString())){
							subList=subMap.remove(obj[0].toString());
						}else{
							subList=new ArrayList<SubjectTO>();
						}

						SubjectTO subto=new SubjectTO();
						//subto.setId(Integer.parseInt(obj[3].toString()));
						if(obj[5]!=null){
							subto.setCode(obj[5].toString());
						}
						if(obj[4]!=null){
							subto.setName(obj[4].toString());
						}
						if(obj[15]!=null)
							subto.setSubOrder(Integer.parseInt(obj[15].toString()));
						if(obj[9]!=null){
							if(obj[12]!=null){
								subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));}
							else
							{
								subto.setEseMinMarks("-");
							}
							if(obj[13]!=null){
								subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));}
							else
							{
								subto.setEseMaxMarks("-");
							}
							if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
								int m=(int)Math.round(Double.parseDouble(obj[9].toString()));
								if(m>0)
									subto.setTheoryMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[9].toString()))));
								else
									subto.setTheoryMarks("-");
							}
							if(obj[10]!=null && CommonUtil.isValidDecimal(obj[10].toString())){
								int m=(int)Math.round(Double.parseDouble(obj[10].toString()));
								if(m>0)
									subto.setPracticalMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[10].toString()))));
								else
									subto.setPracticalMarks("-");
							}
							if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
							{
								subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
							}
							else{
								subto.setTotalMarksAwarded("-");
							}
						}
						subList.add(subto);
						subMap.put(obj[0].toString(), subList);
					}
				}else
				{
					if(obj[0]!=null){

						List<SubjectTO> subList=null;
						if(subMap.containsKey(obj[0].toString())){
							subList=subMap.remove(obj[0].toString());
						}else{
							subList=new ArrayList<SubjectTO>();
						}

						SubjectTO subto=new SubjectTO();
						//subto.setId(Integer.parseInt(obj[3].toString()));
						if(obj[5]!=null){
							subto.setCode(obj[5].toString());
						}
						if(obj[4]!=null){
							subto.setName(obj[4].toString());
						}
						if(obj[15]!=null)
							subto.setSubOrder(Integer.parseInt(obj[15].toString()));
						if(obj[9]!=null){
							if(obj[12]!=null){
								subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));}
							else
							{
								subto.setEseMinMarks("-");
							}
							if(obj[13]!=null){
								subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));}
							else
							{
								subto.setEseMaxMarks("-");
							}
							if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
								int m=(int)Math.round(Double.parseDouble(obj[9].toString()));
								if(m>0)
									subto.setTheoryMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[9].toString()))));
								else
									subto.setTheoryMarks("-");
							}
							if(obj[10]!=null && CommonUtil.isValidDecimal(obj[10].toString())){
								int m=(int)Math.round(Double.parseDouble(obj[10].toString()));
								if(m>0)
									subto.setPracticalMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[10].toString()))));
								else
									subto.setPracticalMarks("-");
							}
							if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
							{
								subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
							}
							else{
								subto.setTotalMarksAwarded("-");
							}
						}
						subList.add(subto);
						subMap.put(obj[0].toString(), subList);
					}
				}
			}

			Map<String,List<SubjectTO>> finalsubMap=new HashMap<String, List<SubjectTO>>();
			List<SubjectTO> addOnCourse=null;
			boolean isAddonCourse=false;
			if(!subMap.isEmpty()){
				Iterator it = subMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					if(pairs.getKey().toString().equalsIgnoreCase("Add On Course")){
						addOnCourse = (ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(addOnCourse,comparator);
						isAddonCourse=true;
					}else{
						List<SubjectTO> list=(ArrayList<SubjectTO>)pairs.getValue();
						Collections.sort(list,comparator);
						finalsubMap.put(pairs.getKey().toString(),list);
					}
				}
			}
			Map<String,List<SubjectTO>> addOnCoursesubMap=new HashMap<String, List<SubjectTO>>();
			if(isAddonCourse){
				addOnCoursesubMap.put("Add On Course", addOnCourse);
			}
			to.setSubMap(CommonUtil.sortMapByKey(finalsubMap));
			to.setAddOnCoursesubMap(addOnCoursesubMap);

			DecimalFormat twoDForm = new DecimalFormat("#.##");

			to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
			to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
			to.setTotalMarksInWord(CommonUtil.numberToWord1(totalMarksAwarded));
			to.setTotalCredits(String.valueOf(totalCredits));

			String countQuery="select count(*) from EXAM_update_exclude_withheld " +
			" where student_id = " +studentId+
			" and exam_id ="+examId;
			int count=0;
			List<BigInteger> countList=transaction.getStudentHallTicket(countQuery);
			if(countList!=null && !countList.isEmpty()){
				BigInteger b=countList.get(0);
				count=b.intValue();
			}
			String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
			double agg=0;
			List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
			if(aggList!=null && !aggList.isEmpty()){
				agg=Double.parseDouble(aggList.get(0).toString());
			}

			if(count<1){
				double r=((double)(totalMarksAwarded)/(double)(totalMaxMarks))*100;
				if(r>=agg){
					String query="select result_class from EXAM_grade_class_definition where course_id="+cid+" and "+r+" between start_percentage and end_percentage";
					List<String> rList=transaction.getStudentHallTicket(query);
					if(rList!=null && !rList.isEmpty()){
						Iterator<String> rItr=rList.iterator();
						while (rItr.hasNext()) {
							String robj = rItr.next();
							if(robj!=null && resultClass.equals("Pass"))
								to.setResult(robj);
							else
								to.setResult("Failed");
						}
					}
				}else
					to.setResult("Failed");
			}else{
				to.setResult("WITH HELD");
			}

		}

		return to;
	}





	/**
	 * @param ugMarksCardData
	 * @return
	 * @throws Exception
	 */
	public MarksCardTO getSupMarksCardForPgView(List<Object[]> ugMarksCardData,DisciplinaryDetailsForm objForm) throws Exception {
		//new logic is implementing down
		String certificateCourseQuery="select s.subject.id,s.isOptional from StudentCertificateCourse s where s.isCancelled=0 " +
		" and  s.student.id=" +objForm.getStudentId()+
		" and s.schemeNo=" +objForm.getSemesterYearNo();
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		List certificateList=txn.getDataForQuery(certificateCourseQuery);
		Map<Integer,Boolean> certificateMap=getCertificateSubjectMap(certificateList);
		//old code 
		MarksCardTO to=null;
		Map<String,List<SubjectTO>> finalsubMap=new HashMap<String, List<SubjectTO>>();
		Map<String,Map<Integer,SubjectTO>> subMap=new HashMap<String, Map<Integer,SubjectTO>>();
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		if(ugMarksCardData!=null && !ugMarksCardData.isEmpty()){
			Iterator<Object[]> itr=ugMarksCardData.iterator();
			int cid=0;
			int examId=0;
			int studentId=0;
			int totalMarksAwarded=0;
			int totalMaxMarks=0;
			int totalCredits=0;
			double tcForCal=0;
			double gpForCal=0;
			int passFail=0;
			String resultClass="Pass";
			// Getting Grade for Fail from database
			String gradeForFail="";
			List<String> gradeList=transaction.getStudentHallTicket("SELECT EXAM_exam_settings.grade_for_fail FROM EXAM_exam_settings EXAM_exam_settings");
			if(gradeList!=null && !gradeList.isEmpty()){
				gradeForFail=gradeList.get(0);
			}
			int star=0;
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				String ciaMax="";
				boolean isDisplay=true;
				boolean isAdd=true;
				boolean isTheoryStar=false;
				boolean isPracticalStar=false;
				boolean showSubType=true;
				boolean showMaxMarks=true;
				boolean showMinMarks=true;
				boolean showAttMarks=true;
				boolean displaySubject=true;
				boolean certificateCourse=false;
				boolean isOptional=false;
				boolean dontConsiderFail=false;
				boolean isAppearedTheory=false;
				boolean isAppearedPractical=false;
				if(obj[54]!=null && obj[54].toString().equalsIgnoreCase("1")){
					isAppearedTheory=true;
				}
				if(obj[55]!=null && obj[55].toString().equalsIgnoreCase("1")){
					isAppearedPractical=true;
				}
				if(obj[53]!=null && obj[53].toString().equalsIgnoreCase("F")){
					if((obj[51]!=null && (obj[51].toString().trim().equalsIgnoreCase("1") || obj[51].toString().trim().equalsIgnoreCase("true"))) && (obj[52]!=null && (obj[52].toString().trim().equalsIgnoreCase("1") || obj[52].toString().trim().equalsIgnoreCase("true"))))
					{
						passFail=passFail;
					}
					else
					{
						passFail++;
					}
				}
				// certificate Course Has to be done
				if(obj[51]!=null && obj[51].toString().equalsIgnoreCase("1")){
					certificateCourse=true;
					if(certificateMap.containsKey(Integer.parseInt(obj[3].toString()))){
						isOptional=certificateMap.get(Integer.parseInt(obj[3].toString()));
					}else{
						isOptional=true;
					}
				}
				if(obj[52]!=null && obj[52].toString().equalsIgnoreCase("1")){
					dontConsiderFail=true;
				}

				// Calculating Grade For a Subject according to type
				String gradeForSubject="";
				if(obj[33]!=null && obj[20]!=null){
					if(!obj[33].toString().equalsIgnoreCase("Practical") && obj[15]!=null && isAppearedTheory){
						double min=0;
						double stu=0;
						if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
							min=Double.parseDouble(obj[11].toString());
						if(certificateCourse && isOptional)
							min=60;

						if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
							stu=Double.parseDouble(obj[15].toString());

						if(obj[15]!=null && !CommonUtil.isValidDecimal(obj[15].toString())){
							if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
								if(!dontConsiderFail)
									resultClass="Fail";
								gradeForSubject=gradeForFail;
							}else{
								if(obj[40]!=null)
									gradeForSubject=obj[40].toString();
								if(certificateCourse){
									displaySubject=false;
								}
							}	
						}else if(obj[40]!=null && obj[40].toString().equalsIgnoreCase("F") || min>stu){
							if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
								if(!dontConsiderFail)
									resultClass="Fail";
								gradeForSubject=gradeForFail;
							}else{
								if(certificateCourse){
									displaySubject=false;
								}
								if(Integer.parseInt(obj[10].toString())==18 && obj[40]!=null && obj[40].toString().equalsIgnoreCase("E")){
									if(!dontConsiderFail)
										resultClass="Fail";
									gradeForSubject=obj[40].toString();
								}else{
									if(obj[40]!=null)
										gradeForSubject=obj[40].toString();
								}
							}	
						}else{
							if(Integer.parseInt(obj[10].toString())==18 && obj[40]!=null && obj[40].toString().equalsIgnoreCase("E")){
								gradeForSubject=obj[40].toString();
								if(certificateCourse){
									displaySubject=false;
								}
								if(!dontConsiderFail){
									resultClass="Fail";
								}
							} else{
								if(obj[40]!=null)
									gradeForSubject=obj[40].toString();
							}
						}
					}else{
						if(isAppearedPractical){
							double min=0;
							double stu=0;
							if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
								min=Double.parseDouble(obj[13].toString());
							if(certificateCourse && isOptional)
								min=60;
							if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
								stu=Double.parseDouble(obj[16].toString());

							if(obj[16]!=null && !CommonUtil.isValidDecimal(obj[16].toString())){
								if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
									if(!dontConsiderFail)
										resultClass="Fail";
									gradeForSubject=gradeForFail;
								}else{
									if(obj[41]!=null)
										gradeForSubject=obj[41].toString();
									if(certificateCourse){
										displaySubject=false;
									}
								}	
							}else if(obj[41]!=null && obj[41].toString().equalsIgnoreCase("F") || min>stu){
								if(!obj[20].toString().equalsIgnoreCase("Add On Course")){
									if(!dontConsiderFail)
										resultClass="Fail";
									gradeForSubject=gradeForFail;
								}else{
									if(certificateCourse){
										displaySubject=false;
									}
									if(Integer.parseInt(obj[10].toString())==18 && obj[41]!=null && !obj[41].toString().equalsIgnoreCase("E")){
										if(!dontConsiderFail)
											resultClass="Fail";
										gradeForSubject=obj[41].toString();
									}else{
										gradeForSubject=obj[41].toString();
									}
								}	
							}else{
								if(Integer.parseInt(obj[10].toString())==18 && obj[41]!=null && obj[41].toString().equalsIgnoreCase("E")){
									gradeForSubject=obj[41].toString();
									if(certificateCourse){
										displaySubject=false;
									}if(!dontConsiderFail){
										resultClass="Fail";
									}
								} else{
									if(obj[41]!=null)
										gradeForSubject=obj[41].toString();
								}
							}
						}
					}
				}
				if(Integer.parseInt(obj[10].toString())==18 && gradeForSubject.equalsIgnoreCase("F")){
					gradeForSubject="E";
				}


				String starQuery="SELECT EXAM_marks_entry_details.is_retest,  " +
				" EXAM_marks_entry.exam_id, " +
				" EXAM_marks_entry.student_id," +
				" EXAM_marks_entry_details.subject_id," +
				" if(EXAM_marks_entry_details.theory_marks is not null,EXAM_marks_entry_details.is_retest,0) as thRetest," +
				" if(EXAM_marks_entry_details.practical_marks is not null,EXAM_marks_entry_details.is_retest,0) as praRetest" +
				" FROM    (   EXAM_marks_entry EXAM_marks_entry" +
				" INNER JOIN  EXAM_exam_internal_exam_details EXAM_exam_internal_exam_details " +
				" ON (EXAM_marks_entry.exam_id = EXAM_exam_internal_exam_details.internal_exam_name_id))  INNER JOIN" +
				" EXAM_marks_entry_details EXAM_marks_entry_details ON (EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id)" +
				" where EXAM_exam_internal_exam_details.exam_id = " +obj[0].toString()+
				" and EXAM_marks_entry.student_id = " +obj[1].toString()+
				" and EXAM_marks_entry_details.subject_id = " +obj[3].toString()+
				" and is_retest = 1";
				List<Object[]> slist=transaction.getStudentHallTicket(starQuery);
				if(slist!=null && !slist.isEmpty()){
					Iterator<Object[]> sItr=slist.iterator();
					while (sItr.hasNext()) {
						Object[] s = (Object[]) sItr.next();
						if(s[4]!=null){
							if(!s[4].toString().isEmpty() && Integer.parseInt(s[4].toString())==1){
								star=star+1;
								isTheoryStar=true;
							}
						}
						if(s[5]!=null){
							if(!s[5].toString().isEmpty() && Integer.parseInt(s[5].toString())==1){
								star=star+1;
								isPracticalStar=true;
							}
						}
					}
				}
				if(obj[49]!=null){
					if(Integer.parseInt(obj[49].toString())==1){
						isAdd=false;
					}
				}
				if(obj[46]!=null){
					if(Integer.parseInt(obj[46].toString())==1){
						isDisplay=false;
					}
				}
				if(obj[44]!=null){
					if(Integer.parseInt(obj[44].toString())==1){
						showMaxMarks=false;
					}
				}
				if(obj[45]!=null){
					if(Integer.parseInt(obj[45].toString())==1){
						showMinMarks=false;
					}
				}
				if(obj[48]!=null){
					if(Integer.parseInt(obj[48].toString())==1){
						showSubType=false;
					}
				}
				if(obj[50]!=null){
					if(Integer.parseInt(obj[50].toString())==1){
						showAttMarks=false;
					}
				}
				if(obj[33]!=null){
					if(!obj[33].toString().equalsIgnoreCase("Practical")){
						if(obj[25]!=null && !StringUtils.isEmpty(obj[25].toString()) && CommonUtil.isValidDecimal(obj[25].toString()) && Double.parseDouble(obj[25].toString())>=5 ){
							if(obj[34]!=null && obj[7]!=null){
								//ciaMax=String.valueOf((int)(Double.parseDouble(obj[25].toString())-Double.parseDouble(obj[34].toString())));
								ciaMax=String.valueOf((int)(Double.parseDouble(obj[25].toString())));
							}else{
								ciaMax=obj[25].toString();
							}
						}
					}else{
						if(obj[24]!=null && !StringUtils.isEmpty(obj[24].toString()) && CommonUtil.isValidDecimal(obj[24].toString()) && Double.parseDouble(obj[24].toString())>=5 ){
							if(obj[9]!=null && obj[34]!=null){
								ciaMax=String.valueOf((int)(Double.parseDouble(obj[24].toString())-Double.parseDouble(obj[34].toString())));
							}else{
								ciaMax=obj[24].toString();
							}
						}
					}
				}

				if(displaySubject && obj[20]!=null){// remove if it not required
					if(to==null){
						to=new MarksCardTO();
						/*	if(obj[0]!=null){
					String monthQuery="select cast(month as unsigned) as month,year from EXAM_definition where del_is_active = 1 and id="+examinationId;
					List<Object[]> monthList=transaction.getStudentHallTicket(monthQuery);
					if(monthList!=null){
						Iterator<Object[]> mItr=monthList.iterator();
						if (mItr.hasNext()) {
							Object[] m = (Object[]) mItr.next();
							String month="";
							String year="";
							if(m[0]!=null){
								month=monthMap.get(m[0].toString());
							}
							if(m[1]!=null){
								year=m[1].toString();
							}
							to.setMonthYear(month+" "+year);
							}
						}
					}*/
						if(obj[0]!=null){
							examId=Integer.parseInt(obj[0].toString());
						}
						if(obj[1]!=null){
							studentId=Integer.parseInt(obj[1].toString());
						}
						if(obj[10]!=null){
							cid=Integer.parseInt(obj[10].toString());
							to.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(cid,"Course",true,"name"));
							//					String query="select c.program.name,c.name,c.program.code from Course c where c.isActive=1 and c.id="+cid;
							//					List<Object[]> clist=transaction.getDataByHql(query);
							//					if(clist!=null && !clist.isEmpty()){
							//						Iterator<Object[]> citr=clist.iterator();
							//						while (citr.hasNext()) {
							//							Object[] c = (Object[]) citr.next();
							//							if(c[1]!=null){
							//								String cname=c[1].toString();
							//								if(cname.contains("Honors")){
							//									to.setCourseName(/*c[0].toString()+"        "+*/c[1].toString());
							//								}else if(c[2]!=null && c[2].toString().equalsIgnoreCase("B.Tech")){
							//									to.setCourseName(c[2].toString()+" in "+c[1].toString());
							//								}else if(c[0]!=null && !c[0].toString().equalsIgnoreCase("BBM_BBA") && !c[0].toString().equalsIgnoreCase("LLB")){
							//									to.setCourseName(c[0].toString());
							//								}else{
							//									to.setCourseName(c[1].toString());
							//								}
							//							}
							//						}
							//					}
						}

						if(obj[17]!=null){
							to.setStudentName(obj[17].toString());
						}
						if(obj[18]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[18].toString());
						}
						if(obj[19]!=null){
							to.setStudentName(to.getStudentName()+" "+obj[19].toString());
						}
						if(obj[32]!=null){
							to.setRegNo(obj[32].toString());
						}
						if(obj[35]!=null){
							to.setSemNo(semMap.get(obj[35].toString()));
						}
						if(obj[36]!=null){
							to.setDate(CommonUtil.ConvertStringToDateFormat(obj[36].toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(obj[47]!=null){
							to.setSemType(obj[47].toString());
						}
						if(obj[20]!=null){
							Map<Integer,SubjectTO> subList=null;
							if(certificateCourse && !isOptional){
								if(subMap.containsKey(NON_CORE_ELECTIVE))
									subList=subMap.remove(NON_CORE_ELECTIVE);
								else
									subList=new HashMap<Integer,SubjectTO>();
							}else if(subMap.containsKey(obj[20].toString())){
								subList=subMap.remove(obj[20].toString());
							}else{
								subList=new HashMap<Integer,SubjectTO>();
							}



							SubjectTO subto=null;
							if(subList.containsKey(Integer.parseInt(obj[3].toString()))){
								subto=subList.remove(Integer.parseInt(obj[3].toString()));
							}else{
								subto=new SubjectTO();
							}
							subto.setId(Integer.parseInt(obj[3].toString()));

							subto.setDisplaySubject(displaySubject);
							if(obj[4]!=null){
								subto.setCode(obj[4].toString());
							}
							if(obj[5]!=null){
								subto.setName(obj[5].toString());
							}
							if(obj[23]!=null){
								subto.setSubOrder(Integer.parseInt(obj[23].toString()));
							}
							if(obj[33]!=null){
								if(obj[33].toString().equalsIgnoreCase("Theory")){
									subto.setAppearedTheory(isAppearedTheory);
								}
								if(obj[33].toString().equalsIgnoreCase("Practical")){
									subto.setAppearedPractical(isAppearedPractical);
								}
								if(obj[33].toString().equalsIgnoreCase("Theory") && isAppearedTheory){
									if(showSubType)
										subto.setType("Theory");

									subto.setGrade(gradeForSubject);

									subto.setTheory(true);
									int ciaMarksAwarded=0;
									if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
										/*if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
											ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
										}*/
										if(isDisplay){
											if(ciaMarksAwarded>=10){
												if(!isTheoryStar)
													subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
											}else{
												if(!isTheoryStar)
													subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
												else
													subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
											}
										}else{
											subto.setCiaMarksAwarded("-");
										}
									}else{
										if(isDisplay){
											subto.setCiaMarksAwarded(String.valueOf(obj[6]));
										}else{
											subto.setCiaMarksAwarded("-");
										}
									}
									if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
										if(showAttMarks)
											subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
										else
											subto.setAttMarksAwarded("-");
									}else{
										if(showAttMarks && obj[7]!=null)
											subto.setAttMarksAwarded(String.valueOf(obj[7]));
										else
											subto.setAttMarksAwarded("-");
									}
									if(obj[34]!=null){
										if(showAttMarks  && obj[7]!=null)
											subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
										else
											subto.setAttMaxMarks("-");
									}
									if(obj[11]!=null){
										if(showMinMarks)
											subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
										else
											subto.setEseMinMarks("-");
									}
									if(obj[12]!=null){
										if(showMaxMarks)
											subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
										else
											subto.setEseMaxMarks("-");
									}
									if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
										int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
										if(isDisplay){
											if(m>=10)
												subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
											else
												subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
										}else
											subto.setEseMarksAwarded("-");
									}else{
										if(isDisplay && obj[15]!=null)
											subto.setEseMarksAwarded(String.valueOf(obj[15]));
										else
											subto.setEseMarksAwarded("-");
									}
									if(isDisplay){
										if(!ciaMax.isEmpty()){
											subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
										}else{
											subto.setCiaMaxMarks("-");
										}
									}else{
										subto.setCiaMaxMarks("-");
									}
									if(obj[26]!=null){
										int maxMarks=0;	
										if(isAdd){
											//								totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
											if(subto.getCiaMaxMarks()!=null && !subto.getCiaMaxMarks().isEmpty() && !subto.getCiaMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getCiaMaxMarks());
											}
											if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().isEmpty() && !subto.getEseMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getEseMaxMarks());
											}
											if(subto.getAttMaxMarks()!=null && !subto.getAttMaxMarks().isEmpty() && !subto.getAttMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getAttMaxMarks());
											}
											totalMaxMarks=totalMaxMarks+maxMarks;
										}
										if(showMaxMarks)
											subto.setTotalMaxMarks(String.valueOf(maxMarks));
										else
											subto.setTotalMaxMarks("-");
									}
									if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
										if(isDisplay){
											if(totalMarksAwarded>=10)
												subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
											else
												subto.setTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
										}else
											subto.setTotalMarksAwarded("-");
									}else{
										if(isDisplay && obj[28]!=null)
											subto.setTotalMarksAwarded(obj[28].toString());
										else
											subto.setTotalMarksAwarded("-");
									}


									if(obj[31]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
										if(subto.getGrade()!=null){
											if(obj[11]!=null && obj[15]!=null){
												if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
													if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
													{
														if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
															totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
														}
													}
												}
											}else{
												if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H")){
													totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
												}
											}
											tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
											if(obj[42]!=null){
												double min=0;
												double stu=0;
												if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
													min=0;
												if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
													stu=0;
												double d=0;
												if(!CommonUtil.isValidDecimal(obj[15].toString())){
													d=0;
												}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
													d=0;
												}else{
													d=Double.parseDouble((obj[42].toString()))*Double.parseDouble(obj[31].toString());
												}
												gpForCal=gpForCal+d;
											}
										}
									}
									/*	if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
								subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
								subto.setRevaluationReq(false);
							}else{
								if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
									subto.setRevaluationReq(true);
							}*/
								}
								if(obj[33].toString().equalsIgnoreCase("Practical") && isAppearedPractical){
									subto.setPractical(true);
									if(showSubType)
										subto.setSubjectType("Practical");

									subto.setPracticalGrade(gradeForSubject);

									int ciaMarksAwarded=0;
									if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
										/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
											ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
										}*/
										if(isDisplay){
											if(ciaMarksAwarded>=10){
												if(!isPracticalStar)
													subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
											}else{
												if(!isPracticalStar)
													subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
												else
													subto.setPracticalCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
											}
										}else{
											subto.setPracticalCiaMarksAwarded("-");
										}
									}else{
										if(isDisplay){
											subto.setPracticalCiaMarksAwarded(String.valueOf(obj[8]));
										}else{
											subto.setPracticalCiaMarksAwarded("-");
										}
									}

									if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
										if(showAttMarks)
											subto.setPracticalAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[9].toString()))));
										else
											subto.setPracticalAttMarksAwarded("-");
									}else{
										if(showAttMarks && obj[9]!=null)
											subto.setPracticalAttMarksAwarded(obj[9].toString());
										else
											subto.setPracticalAttMarksAwarded("-");
									}
									if(obj[34]!=null){
										if(showAttMarks && obj[9]!=null)
											subto.setPracticalAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
										else
											subto.setPracticalAttMaxMarks("-");
									}
									if(obj[13]!=null){
										if(showMinMarks)
											subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
										else
											subto.setPracticalEseMinMarks("-");
									}
									if(obj[14]!=null){
										if(showMaxMarks)
											subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
										else
											subto.setPracticalEseMaxMarks("-");
									}
									if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
										int  m=(int)Math.round(Double.parseDouble(obj[16].toString()));
										if(isDisplay){
											if(m>=10)
												subto.setPracticalEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
											else
												subto.setPracticalEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
										}else
											subto.setPracticalEseMarksAwarded("-");
									}else{
										if(isDisplay && obj[16]!=null)
											subto.setPracticalEseMarksAwarded(obj[16].toString());
										else
											subto.setPracticalEseMarksAwarded("-");	
									}
									if(isDisplay){
										if(!ciaMax.isEmpty()){
											subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
										}else{
											subto.setPracticalCiaMaxMarks("-");
										}
									}else{
										subto.setPracticalCiaMaxMarks("-");
									}
									if(obj[27]!=null){
										int maxMarks=0;	
										if(isAdd){
											//								totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
											if(subto.getPracticalCiaMaxMarks()!=null && !subto.getPracticalCiaMaxMarks().isEmpty() && !subto.getPracticalCiaMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getPracticalCiaMaxMarks());
											}
											if(subto.getPracticalEseMaxMarks()!=null && !subto.getPracticalEseMaxMarks().isEmpty() && !subto.getPracticalEseMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getPracticalEseMaxMarks());
											}
											if(subto.getPracticalAttMaxMarks()!=null && !subto.getPracticalAttMaxMarks().isEmpty() && !subto.getPracticalAttMaxMarks().equalsIgnoreCase("-")){
												maxMarks=maxMarks+Integer.parseInt(subto.getPracticalAttMaxMarks());
											}
											totalMaxMarks=totalMaxMarks+maxMarks;
										}
										if(showMaxMarks)
											subto.setPracticalTotalMaxMarks(String.valueOf(maxMarks));
										else
											subto.setPracticalTotalMaxMarks("-");
									}
									if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
										if(isAdd)
											totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
										if(isDisplay){
											if(totalMarksAwarded>=10)
												subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
											else
												subto.setPracticalTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
										}else
											subto.setPracticalTotalMarksAwarded("-");
									}else{
										if(isDisplay && obj[29]!=null)
											subto.setPracticalTotalMarksAwarded(obj[29].toString());
										else
											subto.setPracticalTotalMarksAwarded("-");
									}
									if(obj[30]!=null){
										DecimalFormat twoDForm = new DecimalFormat("#.#");
										subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
										if(subto.getPracticalGrade()!=null){
											if(obj[13]!=null && obj[16]!=null){
												if(CommonUtil.isValidDecimal(obj[13].toString()) && CommonUtil.isValidDecimal(obj[16].toString())){
													if(Double.parseDouble(obj[13].toString())<=Double.parseDouble(obj[16].toString()) && !subto.getPracticalGrade().equalsIgnoreCase("F"))
													{
														if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H") ){
															totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
														}
													}
												}
											}else{
												if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H")){
													totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
												}
											}
											tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
											if(obj[43]!=null){
												double min=0;
												double stu=0;
												if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
													min = Double.parseDouble(obj[13].toString());
												if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
													stu = Double.parseDouble(obj[16].toString());

												double d=0;
												if(!CommonUtil.isValidDecimal(obj[16].toString())){
													d=0;
												}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
													d=0;
												}else{
													d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
												}
												gpForCal=gpForCal+d;
											}
										}
									}
								}
							}

							subList.put(Integer.parseInt(obj[3].toString()),subto);
							if(certificateCourse && !isOptional){
								subMap.put(NON_CORE_ELECTIVE, subList);
							}else{
								subMap.put(obj[20].toString(), subList);
							}
						}
					}else{
						Map<Integer,SubjectTO> subList=null;
						if(certificateCourse && !isOptional){
							if(subMap.containsKey(NON_CORE_ELECTIVE))
								subList=subMap.remove(NON_CORE_ELECTIVE);
							else
								subList=new HashMap<Integer,SubjectTO>();
						}else if(subMap.containsKey(obj[20].toString())){
							subList=subMap.remove(obj[20].toString());
						}else{
							subList=new HashMap<Integer,SubjectTO>();
						}
						SubjectTO subto=null;
						if(subList.containsKey(Integer.parseInt(obj[3].toString()))){
							subto=subList.remove(Integer.parseInt(obj[3].toString()));
						}else{
							subto=new SubjectTO();
						}
						subto.setId(Integer.parseInt(obj[3].toString()));

						subto.setDisplaySubject(displaySubject);
						if(obj[4]!=null){
							subto.setCode(obj[4].toString());
						}
						if(obj[5]!=null){
							subto.setName(obj[5].toString());
						}
						if(obj[23]!=null){
							subto.setSubOrder(Integer.parseInt(obj[23].toString()));
						}
						subto.setDisplaySubject(displaySubject);
						if(obj[33]!=null){
							if(obj[33].toString().equalsIgnoreCase("Theory")){
								subto.setAppearedTheory(isAppearedTheory);
							}
							if(obj[33].toString().equalsIgnoreCase("Practical")){
								subto.setAppearedPractical(isAppearedPractical);
							}
							if(obj[33].toString().equalsIgnoreCase("Theory")){


								if(showSubType)
									subto.setType("Theory");

								subto.setGrade(gradeForSubject);

								subto.setTheory(true);
								int ciaMarksAwarded=0;
								if(obj[6]!=null && CommonUtil.isValidDecimal(obj[6].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[6].toString()));
									/*if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[7].toString()));
									}*/
									if(isDisplay){
										if(ciaMarksAwarded>=10){
											if(!isTheoryStar)
												subto.setCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
										}else{
											if(!isTheoryStar)
												subto.setCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											else
												subto.setCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
										}
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}else{
									if(isDisplay){
										subto.setCiaMarksAwarded(String.valueOf(obj[6]));
									}else{
										subto.setCiaMarksAwarded("-");
									}
								}
								if(obj[7]!=null && CommonUtil.isValidDecimal(obj[7].toString())){
									if(showAttMarks)
										subto.setAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[7].toString()))));
									else
										subto.setAttMarksAwarded("-");
								}else{
									if(showAttMarks && obj[7]!=null)
										subto.setAttMarksAwarded(String.valueOf(obj[7]));
									else
										subto.setAttMarksAwarded("-");
								}
								if(obj[34]!=null){
									if(showAttMarks  && obj[7]!=null)
										subto.setAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
									else
										subto.setAttMaxMarks("-");
								}
								if(obj[11]!=null){
									if(showMinMarks)
										subto.setEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[11].toString()))));
									else
										subto.setEseMinMarks("-");
								}
								if(obj[12]!=null){
									if(showMaxMarks)
										subto.setEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[12].toString()))));
									else
										subto.setEseMaxMarks("-");
								}
								if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString())){
									int m=(int)Math.round(Double.parseDouble(obj[15].toString()));
									if(isDisplay){
										if(m>=10)
											subto.setEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));
										else
											subto.setEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[15].toString()))));

									}else
										subto.setEseMarksAwarded("-");
								}else{
									if(isDisplay && obj[15]!=null)
										subto.setEseMarksAwarded(String.valueOf(obj[15]));
									else
										subto.setEseMarksAwarded("-");
								}
								if(isDisplay){
									if(!ciaMax.isEmpty()){
										subto.setCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
									}else{
										subto.setCiaMaxMarks("-");
									}
								}else{
									subto.setCiaMaxMarks("-");
								}
								if(obj[26]!=null){
									int maxMarks=0;	
									if(isAdd){
										//							totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
										if(subto.getCiaMaxMarks()!=null && !subto.getCiaMaxMarks().isEmpty() && !subto.getCiaMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getCiaMaxMarks());
										}
										if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().isEmpty() && !subto.getEseMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getEseMaxMarks());
										}
										if(subto.getAttMaxMarks()!=null && !subto.getAttMaxMarks().isEmpty() && !subto.getAttMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getAttMaxMarks());
										}
										totalMaxMarks=totalMaxMarks+maxMarks;
									}
									if(showMaxMarks)
										subto.setTotalMaxMarks(String.valueOf(maxMarks));
									else
										subto.setTotalMaxMarks("-");
								}
								if(obj[28]!=null && CommonUtil.isValidDecimal(obj[28].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[28].toString()));
									if(isDisplay){
										if(totalMarksAwarded>=10)
											subto.setTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));
										else
											subto.setTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[28].toString()))));

									}else
										subto.setTotalMarksAwarded("-");
								}else{
									if(isDisplay && obj[28]!=null)
										subto.setTotalMarksAwarded(obj[28].toString());
									else
										subto.setTotalMarksAwarded("-");
								}


								if(obj[31]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setCredits(twoDForm.format(Double.parseDouble((obj[31].toString()))));
									if(subto.getGrade()!=null){
										if(obj[11]!=null && obj[15]!=null){
											if(CommonUtil.isValidDecimal(obj[11].toString()) && CommonUtil.isValidDecimal(obj[15].toString())){
												if(Double.parseDouble(obj[11].toString())<=Double.parseDouble(obj[15].toString()) && !subto.getGrade().equalsIgnoreCase("F"))
												{
													if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H") ){
														totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
													}
												}
											}
										}else{
											if(!subto.getGrade().equalsIgnoreCase("E") && !subto.getGrade().equalsIgnoreCase("H")){
												totalCredits=totalCredits+Integer.parseInt(obj[31].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[31].toString()));
										if(obj[42]!=null){
											double min=0;
											double stu=0;
											if(obj[11]!=null && CommonUtil.isValidDecimal(obj[11].toString()))
												min=0;
											if(obj[15]!=null && CommonUtil.isValidDecimal(obj[15].toString()))
												stu=0;
											double d=0;
											if(!CommonUtil.isValidDecimal(obj[15].toString())){
												d=0;
											}else if(subto.getGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble(obj[42].toString())*Double.parseDouble((obj[31].toString()));
											}
											gpForCal=gpForCal+d;
										}
									}
								}
								/*	if(revaluationSubjects.containsKey(Integer.parseInt(obj[3].toString()))){
							subto.setStatus(revaluationSubjects.get(Integer.parseInt(obj[3].toString())));
							subto.setRevaluationReq(false);
						}else{
							if(subto.getEseMaxMarks()!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
								subto.setRevaluationReq(true);
						}*/
							}
							if(obj[33].toString().equalsIgnoreCase("Practical")){
								subto.setPractical(true);
								if(showSubType)
									subto.setSubjectType("Practical");

								subto.setPracticalGrade(gradeForSubject);

								int ciaMarksAwarded=0;
								if(obj[8]!=null && CommonUtil.isValidDecimal(obj[8].toString())){
									ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[8].toString()));
									/*if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
										ciaMarksAwarded=ciaMarksAwarded+(int)Math.round(Double.parseDouble(obj[9].toString()));
									}*/
									if(isDisplay){
										if(ciaMarksAwarded>=10){
											if(!isPracticalStar)
												subto.setPracticalCiaMarksAwarded(String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("*"+String.valueOf(ciaMarksAwarded));
										}else{
											if(!isPracticalStar)
												subto.setPracticalCiaMarksAwarded("0"+String.valueOf(ciaMarksAwarded));
											else
												subto.setPracticalCiaMarksAwarded("* 0"+String.valueOf(ciaMarksAwarded));
										}
									}else{
										subto.setPracticalCiaMarksAwarded("-");
									}
								}else{
									if(isDisplay){
										subto.setPracticalCiaMarksAwarded(String.valueOf(obj[8]));
									}else{
										subto.setPracticalCiaMarksAwarded("-");
									}
								}

								if(obj[9]!=null && CommonUtil.isValidDecimal(obj[9].toString())){
									if(showAttMarks)
										subto.setPracticalAttMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[9].toString()))));
									else
										subto.setPracticalAttMarksAwarded("-");
								}else{
									if(showAttMarks && obj[9]!=null)
										subto.setPracticalAttMarksAwarded(obj[9].toString());
									else
										subto.setPracticalAttMarksAwarded("-");
								}
								if(obj[34]!=null){
									if(showAttMarks && obj[9]!=null)
										subto.setPracticalAttMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[34].toString()))));
									else
										subto.setPracticalAttMaxMarks("-");
								}
								if(obj[13]!=null){
									if(showMinMarks)
										subto.setPracticalEseMinMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[13].toString()))));
									else
										subto.setPracticalEseMinMarks("-");
								}
								if(obj[14]!=null){
									if(showMaxMarks)
										subto.setPracticalEseMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(obj[14].toString()))));
									else
										subto.setPracticalEseMaxMarks("-");
								}
								if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString())){
									int  m=(int)Math.round(Double.parseDouble(obj[16].toString()));
									if(isDisplay){
										if(m>=10)
											subto.setPracticalEseMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));
										else
											subto.setPracticalEseMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[16].toString()))));

									}else
										subto.setPracticalEseMarksAwarded("-");
								}else{
									if(isDisplay && obj[16]!=null)
										subto.setPracticalEseMarksAwarded(obj[16].toString());
									else
										subto.setPracticalEseMarksAwarded("-");	
								}
								if(isDisplay){
									if(!ciaMax.isEmpty()){
										subto.setPracticalCiaMaxMarks(String.valueOf((int)Math.round(Double.parseDouble(ciaMax))));
									}else{
										subto.setPracticalCiaMaxMarks("-");
									}
								}else{
									subto.setPracticalCiaMaxMarks("-");
								}
								if(obj[27]!=null){
									int maxMarks=0;
									if(isAdd){
										//								totalMaxMarks=totalMaxMarks+(int)Math.round(Double.parseDouble(obj[26].toString()));
										if(subto.getPracticalCiaMaxMarks()!=null && !subto.getPracticalCiaMaxMarks().isEmpty() && !subto.getPracticalCiaMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getPracticalCiaMaxMarks());
										}
										if(subto.getPracticalEseMaxMarks()!=null && !subto.getPracticalEseMaxMarks().isEmpty() && !subto.getPracticalEseMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getPracticalEseMaxMarks());
										}
										if(subto.getPracticalAttMaxMarks()!=null && !subto.getPracticalAttMaxMarks().isEmpty() && !subto.getPracticalAttMaxMarks().equalsIgnoreCase("-")){
											maxMarks=maxMarks+Integer.parseInt(subto.getPracticalAttMaxMarks());
										}
										totalMaxMarks=totalMaxMarks+maxMarks;
									}
									if(showMaxMarks)
										subto.setPracticalTotalMaxMarks(String.valueOf(maxMarks));
									else
										subto.setPracticalTotalMaxMarks("-");
								}
								if(obj[29]!=null && CommonUtil.isValidDecimal(obj[29].toString())){
									if(isAdd)
										totalMarksAwarded=totalMarksAwarded+(int)Math.round(Double.parseDouble(obj[29].toString()));
									if(isDisplay){
										if(totalMarksAwarded>=10)
											subto.setPracticalTotalMarksAwarded(String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));
										else
											subto.setPracticalTotalMarksAwarded("0"+String.valueOf((int)Math.round(Double.parseDouble(obj[29].toString()))));

									}else
										subto.setPracticalTotalMarksAwarded("-");
								}else{
									if(isDisplay && obj[29]!=null)
										subto.setPracticalTotalMarksAwarded(obj[29].toString());
									else
										subto.setPracticalTotalMarksAwarded("-");
								}
								if(obj[30]!=null){
									DecimalFormat twoDForm = new DecimalFormat("#.#");
									subto.setPracticalCredits(twoDForm.format(Double.parseDouble((obj[30].toString()))));
									if(subto.getPracticalGrade()!=null){
										if(obj[13]!=null && obj[16]!=null){
											if(CommonUtil.isValidDecimal(obj[13].toString()) && CommonUtil.isValidDecimal(obj[16].toString())){
												if(Double.parseDouble(obj[13].toString())<=Double.parseDouble(obj[16].toString()) && !subto.getPracticalGrade().equalsIgnoreCase("F"))
												{
													if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H") ){
														totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
													}
												}
											}
										}else{
											if(!subto.getPracticalGrade().equalsIgnoreCase("E") && !subto.getPracticalGrade().equalsIgnoreCase("H")){
												totalCredits=totalCredits+Integer.parseInt(obj[30].toString());
											}
										}
										tcForCal=tcForCal+Double.parseDouble((obj[30].toString()));
										if(obj[43]!=null){
											double min=0;
											double stu=0;
											if(obj[13]!=null && CommonUtil.isValidDecimal(obj[13].toString()))
												min = Double.parseDouble(obj[13].toString());
											if(obj[16]!=null && CommonUtil.isValidDecimal(obj[16].toString()))
												stu = Double.parseDouble(obj[16].toString());

											double d=0;
											if(!CommonUtil.isValidDecimal(obj[16].toString())){
												d=0;
											}else if(subto.getPracticalGrade().equalsIgnoreCase("F") ||min>stu){
												d=0;
											}else{
												d=Double.parseDouble((obj[43].toString()))*Double.parseDouble((obj[30].toString()));
											}
											gpForCal=gpForCal+d;
										}
									}
								}
							}
						}
						subList.put(Integer.parseInt(obj[3].toString()),subto);
						if(certificateCourse && !isOptional){
							subMap.put(NON_CORE_ELECTIVE, subList);
						}else{
							subMap.put(obj[20].toString(), subList);
						}
					}
				}// remove if it not required
			}
			List<SubjectTO> addOnCourse=null;
			List<SubjectTO> nonElective=null;
			boolean isAddonCourse=false;
			boolean isElective=false;
			if(!subMap.isEmpty()){
				Iterator it = subMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					Map<Integer,SubjectTO> values=(HashMap<Integer, SubjectTO>)pairs.getValue();
					if(pairs.getKey().toString().equalsIgnoreCase(NON_CORE_ELECTIVE)){
						nonElective = new ArrayList<SubjectTO>(values.values());
						Collections.sort(nonElective,comparator);
						isElective=true;
					}else if(pairs.getKey().toString().equalsIgnoreCase("Add On Course")){
						addOnCourse = new ArrayList<SubjectTO>(values.values());
						Collections.sort(addOnCourse,comparator);
						isAddonCourse=true;
					}else{
						List<SubjectTO> list = new ArrayList<SubjectTO>(values.values());
						Collections.sort(list,comparator);
						finalsubMap.put(pairs.getKey().toString().trim(), list);
					}
				}
			}
			Map<String,List<SubjectTO>> addOnCoursesubMap=new HashMap<String, List<SubjectTO>>();
			if(isAddonCourse){
				addOnCoursesubMap.put("Add On Course", addOnCourse);
			}
			Map<String,List<SubjectTO>> nonCoreElectivesubMap=new HashMap<String, List<SubjectTO>>();
			if(isElective){
				nonCoreElectivesubMap.put(NON_CORE_ELECTIVE,nonElective);
			}
			to.setSubMap(CommonUtil.sortMapByKey(finalsubMap));
			to.setAddOnCoursesubMap(addOnCoursesubMap);
			to.setNonElectivesubMap(nonCoreElectivesubMap);

			to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
			to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
			//		to.setTotalMarksInWord(CommonUtil.numberToWord(totalMarksAwarded));
			to.setTotalMarksInWord(CommonUtil.numberToWord1(totalMarksAwarded));
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			to.setTotalCredits(String.valueOf(totalCredits));
			double tgpa=0;
			if(gpForCal>0 && tcForCal>0){
				tgpa=CommonUtil.Round((gpForCal/tcForCal),2);
			}
			to.setGradePoints(String.valueOf(twoDForm.format(tgpa)));

			String countQuery="select count(*) from EXAM_update_exclude_withheld " +
			" where student_id = " +studentId+
			" and exam_id ="+examId;
			int count=0;
			List<BigInteger> countList=transaction.getStudentHallTicket(countQuery);
			if(countList!=null && !countList.isEmpty()){
				BigInteger b=countList.get(0);
				count=b.intValue();
			}
			/*String aggQuery="select  aggregate_pass_prcntg from EXAM_exam_settings_course where course_id ="+cid;
		double agg=0;
		List<java.math.BigDecimal> aggList=transaction.getStudentHallTicket(aggQuery);
		if(aggList!=null && !aggList.isEmpty()){
			agg=Double.parseDouble(aggList.get(0).toString());
		}*/

			if(count<1){
				if(resultClass.equalsIgnoreCase("Pass")){
					if(passFail>0){
						to.setResult("Failed");
					}else{
						to.setResult("Completed");
					}
				}else{
					to.setResult("Failed");
				}
			}else{
				to.setResult("WITH HELD");
			}

		}
		return to;
	}




	public String getExamSemListStdLogin(LoginForm objForm) throws Exception {		
		String query= "SELECT classes.term_number, EXAM_definition.id, classes.name ,cast(EXAM_definition.month as unsigned), EXAM_definition.year ,classes.id as classes "+
		"from EXAM_student_overall_internal_mark_details "+
		"INNER JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+ 
		"INNER JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id  "+
		"INNER JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+ 
		"INNER JOIN class_schemewise ON class_schemewise.class_id = classes.id "+ 
		"INNER JOIN EXAM_publish_hall_ticket_marks_card on EXAM_publish_hall_ticket_marks_card.class_id = classes.id "+
		"and EXAM_publish_hall_ticket_marks_card.exam_id = EXAM_definition.id "+
		"and EXAM_publish_hall_ticket_marks_card.publish_for = 'Marks Card' "+
		"and EXAM_publish_hall_ticket_marks_card.download_end_date >= curdate() "+
		"where student.id= " +objForm.getStudentId() +" group by student.id, EXAM_definition.id, classes.id";

		return query;
	}
	
	public String getExamSemListStdLoginForGrievance(LoginForm objForm) throws Exception {		
		String query= "SELECT classes.term_number, EXAM_definition.id, classes.name ,cast(EXAM_definition.month as unsigned), EXAM_definition.year ,classes.id as classes "+
		"from EXAM_student_overall_internal_mark_details "+
		"INNER JOIN student on EXAM_student_overall_internal_mark_details.student_id = student.id "+ 
		"INNER JOIN EXAM_definition ON EXAM_student_overall_internal_mark_details.exam_id = EXAM_definition.id  "+
		"INNER JOIN classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id "+ 
		"INNER JOIN class_schemewise ON class_schemewise.class_id = classes.id "+ 
		"INNER JOIN EXAM_publish_hall_ticket_marks_card on EXAM_publish_hall_ticket_marks_card.class_id = classes.id "+
		"and EXAM_publish_hall_ticket_marks_card.exam_id = EXAM_definition.id "+
		"and EXAM_publish_hall_ticket_marks_card.publish_for = 'Grievance Form' "+
		"and EXAM_publish_hall_ticket_marks_card.download_end_date >= curdate() "+
		"where student.id= " +objForm.getStudentId() +" group by student.id, EXAM_definition.id, classes.id";

		return query;
	}
	
	public String getExamSemListStdLoginForInternalGrievance(LoginForm objForm,String classid) throws Exception {		
		String query= " select e.exam_id from EXAM_publish_hall_ticket_marks_card e"+
		              " where e.class_id="+Integer.parseInt(classid)+" and "+
		              " e.publish_for='Internal Grievance Form' and"+
		              " e.download_end_date >= curdate() ";

		return query;
	}

	public List<MarksCardTO> getExamSemListStdLogin(List<Object[]> pgMarksCardData,LoginForm objForm ) throws Exception {
		MarksCardTO to=null;
		ArrayList<MarksCardTO> list = new ArrayList<MarksCardTO>();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int studentId=0;
			studentId=objForm.getStudentId();
			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				to=new MarksCardTO();	
				String month="";	
				String year="";
				String semNo="";
				// String ExamName="";
				int examId1=0;
				int classId=0;
				if(obj[0]!=null){
					to.setSemNo(obj[0].toString());
					semNo=obj[0].toString();
				}
				if(obj[1]!=null)
				{
					to.setExamId(obj[1].toString());
					examId1=Integer.parseInt(obj[1].toString());
					to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId1,"ExamDefinitionBO",true,"name"));
					//ExamName=to.getExamName();
					//	to.setExamName(obj[1].toString());
					to.setNewExamId(obj[1].toString()+"_"+semNo);
				}

				if(obj[2]!=null)
				{
					to.setClassName(obj[2].toString());
				}
				if(obj[3]!=null){
					month=monthMap.get(obj[3].toString());
				}
				if(obj[4]!=null){
					year=obj[4].toString();
				}
				if(obj[5]!=null){
					to.setClassId(obj[5].toString());
					classId=Integer.parseInt(obj[5].toString());
					//added by manu,for change the signature till june 2013
				}if(obj[3]!=null){
					to.setMonthCheck(Integer.parseInt(obj[3].toString()));
				}
				if(obj[4]!=null){
					to.setYearCheck(Integer.parseInt(obj[4].toString()));
				}
				//end by manu.
				to.setMonthYear(month+" "+year);
				to.setData("Sem:"+semNo+"-("+month+" "+year+")");
				List<Integer> listHallTicketBlocked =ExamMarksEntryHandler.getInstance().getStudentsHallTicketBlocked(studentId, classId, examId1);
				//	String detainedStudent =ExamMarksEntryHandler.getInstance().getDetainedDiscontinuedStudent(studentId, classId);
				if(listHallTicketBlocked==null || listHallTicketBlocked.isEmpty())
				{
					list.add(to);
				}
			}
		}
		return list;
	}

	public List<MarksCardTO> getExamSemListStdLoginGrievance(List<Integer> pgMarksCardData,LoginForm objForm ) throws Exception {
		MarksCardTO to=null;
		ArrayList<MarksCardTO> list = new ArrayList<MarksCardTO>();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int studentId=0;
			studentId=objForm.getStudentId();
			//Iterator<Object[]> itr=pgMarksCardData.get(0);
			for(int i=0;i<pgMarksCardData.size();i++){
				to=new MarksCardTO();
				int examId1=0;
				
			Integer intarray = pgMarksCardData.get(i) ;
			to.setExamId(intarray.toString());
			examId1=Integer.parseInt(intarray.toString());
			to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId1,"ExamDefinitionBO",true,"name"));
			list.add(to);
			}
			
			
			/*while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				to=new MarksCardTO();
				int examId1=0;
				
				if(obj[0]!=null)
				{
					to.setExamId(obj[0].toString());
					examId1=Integer.parseInt(obj[0].toString());
					to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId1,"ExamDefinitionBO",true,"name"));
					
				}
					list.add(to);
			}*/
		}
		return list;
	}
	public String getSupExamSemListStdLogin(LoginForm objForm) throws Exception {	
		/*String query= "select EXAM_supplementary_improvement_application.exam_id,EXAM_definition.id, EXAM_supplementary_improvement_application.scheme_no, EXAM_definition.month, EXAM_definition.year, EXAM_supplementary_improvement_application.class_id" +
				" from  EXAM_supplementary_improvement_application "+
			"inner join EXAM_definition ON EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id "+
			"where (EXAM_supplementary_improvement_application.is_appeared_practical=1 or EXAM_supplementary_improvement_application.is_appeared_theory=1) "+
			"AND student_id not in ( "+
			"SELECT EXAM_update_exclude_withheld.student_id "+
			"FROM EXAM_update_exclude_withheld EXAM_update_exclude_withheld where EXAM_update_exclude_withheld.exclude_from_results = 1)"+
			"and student_id= " +objForm.getStudentId() +" group by EXAM_supplementary_improvement_application.exam_id, "+
			"EXAM_supplementary_improvement_application.scheme_no order by EXAM_supplementary_improvement_application.scheme_no";*/

		String query= "select EXAM_supplementary_improvement_application.exam_id, " +
		"EXAM_definition.id, EXAM_supplementary_improvement_application.scheme_no, " +
		" cast(EXAM_definition.month as unsigned), EXAM_definition.year, EXAM_supplementary_improvement_application.class_id "+
		"from  EXAM_supplementary_improvement_application "+
		"inner join EXAM_definition ON EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id "+
		"inner join classes ON EXAM_supplementary_improvement_application.class_id = classes.id "+
		"inner JOIN EXAM_publish_hall_ticket_marks_card on EXAM_publish_hall_ticket_marks_card.class_id = classes.id "+
		"and EXAM_publish_hall_ticket_marks_card.exam_id = EXAM_definition.id "+
		"and EXAM_publish_hall_ticket_marks_card.publish_for = 'Marks Card' "+
		"and EXAM_publish_hall_ticket_marks_card.download_end_date >= curdate() "+
		"where (EXAM_supplementary_improvement_application.is_appeared_practical=1 or " +
		"EXAM_supplementary_improvement_application.is_appeared_theory=1) "+
		"AND student_id not in ( SELECT EXAM_update_exclude_withheld.student_id "+
		"FROM EXAM_update_exclude_withheld EXAM_update_exclude_withheld " +
		"where EXAM_update_exclude_withheld.exclude_from_results = 1) "+
		"and student_id= " +objForm.getStudentId() +" group by EXAM_supplementary_improvement_application.exam_id, "+
		"EXAM_supplementary_improvement_application.scheme_no order by EXAM_supplementary_improvement_application.scheme_no ";
		return query;
	}
	public String getSupExamSemListStdLoginForGrievance(LoginForm objForm) throws Exception {	
		/*String query= "select EXAM_supplementary_improvement_application.exam_id,EXAM_definition.id, EXAM_supplementary_improvement_application.scheme_no, EXAM_definition.month, EXAM_definition.year, EXAM_supplementary_improvement_application.class_id" +
				" from  EXAM_supplementary_improvement_application "+
			"inner join EXAM_definition ON EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id "+
			"where (EXAM_supplementary_improvement_application.is_appeared_practical=1 or EXAM_supplementary_improvement_application.is_appeared_theory=1) "+
			"AND student_id not in ( "+
			"SELECT EXAM_update_exclude_withheld.student_id "+
			"FROM EXAM_update_exclude_withheld EXAM_update_exclude_withheld where EXAM_update_exclude_withheld.exclude_from_results = 1)"+
			"and student_id= " +objForm.getStudentId() +" group by EXAM_supplementary_improvement_application.exam_id, "+
			"EXAM_supplementary_improvement_application.scheme_no order by EXAM_supplementary_improvement_application.scheme_no";*/

		String query= "select EXAM_supplementary_improvement_application.exam_id, " +
		"EXAM_definition.id, EXAM_supplementary_improvement_application.scheme_no, " +
		" cast(EXAM_definition.month as unsigned), EXAM_definition.year, EXAM_supplementary_improvement_application.class_id "+
		"from  EXAM_supplementary_improvement_application "+
		"inner join EXAM_definition ON EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id "+
		"inner join classes ON EXAM_supplementary_improvement_application.class_id = classes.id "+
		"inner JOIN EXAM_publish_hall_ticket_marks_card on EXAM_publish_hall_ticket_marks_card.class_id = classes.id "+
		"and EXAM_publish_hall_ticket_marks_card.exam_id = EXAM_definition.id "+
		"and EXAM_publish_hall_ticket_marks_card.publish_for = 'Grievance Form' "+
		"and EXAM_publish_hall_ticket_marks_card.download_end_date >= curdate() "+
		"where (EXAM_supplementary_improvement_application.is_appeared_practical=1 or " +
		"EXAM_supplementary_improvement_application.is_appeared_theory=1) "+
		"AND student_id not in ( SELECT EXAM_update_exclude_withheld.student_id "+
		"FROM EXAM_update_exclude_withheld EXAM_update_exclude_withheld " +
		"where EXAM_update_exclude_withheld.exclude_from_results = 1) "+
		"and student_id= " +objForm.getStudentId() +" group by EXAM_supplementary_improvement_application.exam_id, "+
		"EXAM_supplementary_improvement_application.scheme_no order by EXAM_supplementary_improvement_application.scheme_no ";
		return query;
	}

	public List<MarksCardTO> getSupExamSemListStdLogin(List<Object[]> pgMarksCardData) throws Exception {
		MarksCardTO to=null;
		ArrayList<MarksCardTO> list = new ArrayList<MarksCardTO>();
		if(pgMarksCardData!=null && !pgMarksCardData.isEmpty()){
			int studentId=0;

			Iterator<Object[]> itr=pgMarksCardData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				to=new MarksCardTO();	
				String month="";	
				String year="";	
				String semNo="";
				//  String ExamName="";
				String classId="";
				if(obj[0]!=null){
					to.setExamId(obj[0].toString());
				}
				if(obj[1]!=null)
				{						
					to.setExamId(obj[1].toString());
					int examId1=Integer.parseInt(obj[1].toString());
					to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId1,"ExamDefinitionBO",true,"name"));
					//to.setExamName(obj[1].toString());
					//	ExamName=to.getExamName();
				}
				if(obj[2]!=null)
				{
					to.setSemNo(obj[2].toString());
					semNo=obj[2].toString();
				}
				if(obj[3].toString()!=null){
					month=monthMap.get(obj[3].toString());
				}
				if(obj[4]!=null){
					year=obj[4].toString();
				}
				if(obj[5]!=null){
					classId=obj[5].toString();
					to.setClassId(classId);
				}
				//added by manu,for change the signature till june 2013
				if(obj[3]!=null){
					to.setMonthCheck(Integer.parseInt(obj[3].toString()));
				}
				if(obj[4]!=null){
					to.setYearCheck(Integer.parseInt(obj[4].toString()));
				}
				to.setNewExamId(to.getExamId()+"-"+to.getSemNo());
				to.setMonthYear(month+" "+year);
				to.setData("Sem:"+semNo+"-("+month+" "+year+")");
				list.add(to);
			}
		}		
		return list;
	}

	// vinodha
	public String getQueryForStudentPrevHallTicket(int studentId,int examId, int schemeNo) throws Exception {
		String query="select classes.name," +
				" student.register_no," +
				" personal_data.first_name," +
				" personal_data.middle_name," +
				" personal_data.last_name," +
				" subject.code," +
				" subject.name as subName," +
				" EXAM_time_table.date_starttime," +
				" student.id," +
				" EXAM_sessions.id as sessionId," +
				" EXAM_sessions.session as sessionName," +
				" personal_data.date_of_birth," +
				" EXAM_time_table.date_endtime," +
				" course.name as coursename," +
				" EXAM_definition.name as examname," +
				" EXAM_exam_course_scheme_details.scheme_no," +
				" EXAM_definition.month as months," +
				" EXAM_definition.year," +
				" subject.is_theory_practical," +
				" subject_type.name as typeName" +
				" from EXAM_student_previous_class_details" +
				" LEFT JOIN student ON EXAM_student_previous_class_details.student_id = student.id" +
				" LEFT JOIN adm_appln ON student.adm_appln_id = adm_appln.id" +
				" LEFT JOIN personal_data ON adm_appln.personal_data_id = personal_data.id" +
				" LEFT JOIN classes ON EXAM_student_previous_class_details.class_id = classes.id" +
				" LEFT JOIN EXAM_student_sub_grp_history ON EXAM_student_sub_grp_history.student_id = student.id" +
				" LEFT JOIN subject_group ON EXAM_student_sub_grp_history.subject_group_id= subject_group.id" +
				" LEFT JOIN subject_group_subjects ON subject_group_subjects.subject_group_id = EXAM_student_sub_grp_history.subject_group_id" +
				" LEFT JOIN subject ON subject_group_subjects.subject_id = subject.id" +
				" LEFT JOIN EXAM_exam_course_scheme_details ON EXAM_exam_course_scheme_details.course_id = adm_appln.selected_course_id" +
				" AND (EXAM_exam_course_scheme_details.scheme_no = classes.term_number)" +
				" LEFT JOIN course ON EXAM_exam_course_scheme_details.course_id=course.id" +
				" LEFT JOIN EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id" +
				" LEFT JOIN exam_regular_app examRegApp " +
				" ON (classes.id =examRegApp.class_id) " +
				" AND (student.id = examRegApp.student_id)" +
				" AND (EXAM_definition.id = examRegApp.exam_id)" +
				" LEFT JOIN EXAM_time_table ON (EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id)" +
				" AND (EXAM_time_table.subject_id = subject.id)" +
				" LEFT JOIN EXAM_sessions ON EXAM_time_table.exam_session_id = EXAM_sessions.id" +
				" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id" +
				" inner join EXAM_sub_definition_coursewise on EXAM_sub_definition_coursewise.subject_id = subject.id "+
				" WHERE (subject_group_subjects.is_active = 1)" +
				" AND (subject.is_active = 1)" +
				" AND (adm_appln.is_cancelled = 0)" +
				" AND (classes.is_active = 1)" +
				" AND (EXAM_exam_course_scheme_details.is_active = 1)" +
				//" AND (EXAM_time_table.date_starttime is not null)" +
				" AND (EXAM_time_table.is_active=1)" +
				" AND student.id = " +studentId+
				" AND EXAM_exam_course_scheme_details.exam_id = " +examId+
				" AND (subject_type.is_active = 1)" +
				" And EXAM_student_previous_class_details.scheme_no = " +schemeNo+
				" AND (examRegApp.chalan_verified = 1)" +
				" AND EXAM_sub_definition_coursewise.subject_order is not null "+
				" group by EXAM_time_table.exam_course_scheme_id, student.id, classes.id, subject.id "+
				" order by EXAM_sub_definition_coursewise.subject_order";
	
		
		return query;
	}
	public String getQueryForMPhilCourse(int examId,int classId,int schemeNo,int studentId) throws Exception {

		String query = " select EXAM_definition.id, "+
		" CAST( EXAM_definition.month  AS UNSIGNED INTEGER) AS exam_month,  " +
		" EXAM_definition.year as exam_year," +
		"student.id as student_id," +
		"classes.id as classId, " +
		"subject.id as subject_id, " +
		"subject.code as subCode,  " +
		"subject.name as subName, " +
		"ifnull(EXAM_internal_mark_supplementary_details.theory_total_mark, EXAM_student_overall_internal_mark_details.theory_total_mark) " +
		"as theory_total_mark, " +
		"ifnull(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark, EXAM_student_overall_internal_mark_details.theory_total_attendance_mark) " +
		"as theory_total_attendance_mark, " +
		"ifnull(EXAM_internal_mark_supplementary_details.practical_total_mark, EXAM_student_overall_internal_mark_details.practical_total_mark) " +
		"as practical_total_mark, " +
		"ifnull(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark, EXAM_student_overall_internal_mark_details.practical_total_attendance_mark) " +
		"as practical_total_attendance_mark," + 
		"classes.course_id as course_id," + 
		"EXAM_subject_rule_settings.theory_ese_minimum_mark," +     
		"EXAM_subject_rule_settings.theory_ese_maximum_mark,  " +   
		"EXAM_subject_rule_settings.practical_ese_minimum_mark, " +    
		"EXAM_subject_rule_settings.practical_ese_maximum_mark, " +
		"EXAM_student_final_mark_details.student_theory_marks, " +
		"EXAM_student_final_mark_details.student_practical_marks, " +
		"personal_data.first_name, " +    
		"personal_data.middle_name,  " +   
		"personal_data.last_name,  " +
		"EXAM_subject_sections.name as section,  " +
		"EXAM_subject_sections.is_initialise,  " +   
		"EXAM_subject_sections.id as section_id, " +    
		"EXAM_sub_definition_coursewise.subject_order, " +    
		"EXAM_subject_rule_settings.final_practical_internal_maximum_mark, " +     
		"EXAM_subject_rule_settings.final_theory_internal_maximum_mark, " + 
		"EXAM_sub_definition_coursewise.practical_credit, " +
		"EXAM_sub_definition_coursewise.theory_credit, " +
		"student.register_no, " +
		"if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Theory')) as subType, " +
		"classes.term_number as term_number, " +
		"(if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryMax,   " +  
		"(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalMax, " +
		"EXAM_sub_definition_coursewise.dont_show_max_marks,  " +   
		"EXAM_sub_definition_coursewise.dont_show_min_marks,  " +
		"EXAM_sub_definition_coursewise.show_only_grade,  " +
		"course_scheme.name as schemeName,  " +   
		"EXAM_sub_definition_coursewise.dont_show_sub_type,   " +   
		"adm_appln.applied_year,  " +
		"EXAM_definition.academic_year, " +
		" adm_appln.selected_course_id,EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		" EXAM_subject_rule_settings.final_practical_internal_minimum_mark ,EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark,EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark"+
		" ,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,EXAM_supplementary_improvement_application.is_appeared_theory, EXAM_supplementary_improvement_application.is_appeared_practical,EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln,subject.is_certificate_course,if(theory_ese_maximum_mark is null and theory_ese_entered_max_mark is null and practical_ese_maximum_mark is null and practical_ese_entered_max_mark is null, 1, 0) as only_internal,EXAM_supplementary_improvement_application.is_improvement, " +
	    " subject.subject_type_id," +
	    " subject.consolidated_subject_stream_id,"+
		" EXAM_subject_rule_settings.subject_final_minimum as subFinalMin, "+
		" EXAM_subject_rule_settings.subject_final_maximum, "+
		" EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln, "+
		" subject.subjectType_based_on_marks, "+
		" EXAM_supplementary_improvement_application.is_supplementary, "+
		" EXAM_student_final_mark_details.graced_mark as theoryGrace ,"+
	    " EXAM_student_final_mark_details.theory_marks_before_grace as theory_marks_before_grace, "+
	    " EXAM_student_overall_internal_mark_details.theory_marks_before_grace as internalGraceMarks ,"+
	    " EXAM_student_overall_internal_mark_details.graced_mark as internalGrace "+
		"from student " +
		"inner join adm_appln ON student.adm_appln_id = adm_appln.id " +
		"inner join personal_data ON adm_appln.personal_data_id = personal_data.id " +
		"left join EXAM_student_final_mark_details on EXAM_student_final_mark_details.student_id = student.id " +
		"and (EXAM_student_final_mark_details.student_theory_marks is not null or " +
		"		EXAM_student_final_mark_details.student_practical_marks is not null )" +
		"inner join classes ON EXAM_student_final_mark_details.class_id = classes.id " +
		"inner join class_schemewise on class_schemewise.class_id = classes.id " +
		"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id  " +
		"inner join curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id  " +
		"inner join course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id  " +
		"inner join course ON classes.course_id = course.id  " +


		"left join applicant_subject_group on adm_appln.id = applicant_subject_group.adm_appln_id  " +
		"inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id and subject_group.is_active=1  " +
		"inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id and subject_group_subjects.is_active=1  " +
		"inner join subject ON subject_group_subjects.subject_id = subject.id and subject.is_active=1 and EXAM_student_final_mark_details.subject_id = subject.id  " +
		" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id "+
		"left join EXAM_student_overall_internal_mark_details on EXAM_student_overall_internal_mark_details.student_id = student.id " + 

		"and EXAM_student_overall_internal_mark_details.class_id = classes.id " +
		"and EXAM_student_overall_internal_mark_details.subject_id = subject.id " +
		"inner join EXAM_subject_rule_settings on EXAM_subject_rule_settings.course_id = course.id  " +
		"and EXAM_subject_rule_settings.subject_id = subject.id  " +
		"and EXAM_subject_rule_settings.scheme_no=classes.term_number " + 
		"and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year " + 
		"left join EXAM_sub_definition_coursewise on EXAM_sub_definition_coursewise.course_id = course.id " + 
		"and EXAM_sub_definition_coursewise.subject_id = subject.id  " +
		"and EXAM_sub_definition_coursewise.scheme_no = classes.term_number " + 
		"and EXAM_sub_definition_coursewise.academic_year=curriculum_scheme_duration.academic_year " + 
		"left join EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id " + 
		"left join EXAM_definition ON ifnull(EXAM_student_final_mark_details.exam_id,EXAM_student_overall_internal_mark_details.exam_id) = EXAM_definition.id  " +
		"left join EXAM_supplementary_improvement_application on EXAM_supplementary_improvement_application.scheme_no = classes.term_number  " +
		"and EXAM_supplementary_improvement_application.student_id = student.id " + 
		"and EXAM_supplementary_improvement_application.subject_id = subject.id  " +
		//#	 and EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id " + 
		"left join EXAM_internal_mark_supplementary_details on EXAM_internal_mark_supplementary_details.class_id = classes.id " + 
		//#	 and EXAM_internal_mark_supplementary_details.exam_id = EXAM_definition.id  " +
		"and EXAM_internal_mark_supplementary_details.student_id = student.id  " +
		"and EXAM_internal_mark_supplementary_details.subject_id = subject.id  " +
		"AND if((classes.course_id=32), " +
		"		(EXAM_internal_mark_supplementary_details.exam_id=EXAM_definition.id), " + 
		"		(EXAM_internal_mark_supplementary_details.exam_id=(select e.exam_id from  EXAM_internal_mark_supplementary_details e  " +
		"				inner join EXAM_definition as ex on e.exam_id=ex.id  " +
		"				where e.class_id=classes.id and e.student_id=student.id and e.subject_id=subject.id " + 
		"				and ex.exam_type_id in (3, 6) order by ex.year desc, ex.month desc limit 1)))  " +
		"	where student.id in (:ids) and  student.id not in (select studentdetention.student_id from EXAM_student_detention_rejoin_details studentdetention where studentdetention.discontinued=1) " +
		" and classes.term_number="+schemeNo+
		" group by term_number, student_id, subject_id,exam_year asc,exam_month asc  " +

		" union all  " +
		"select EXAM_definition.id,  " +
		"EXAM_definition.month as exam_month, " +
		"EXAM_definition.year as exam_year, " +
		"student.id as student_id, " +
		"classes.id as classId,  " +
		"subject.id as subject_id,  " +
		"subject.code as subCode,   " +
		"subject.name as subName,  " +
		"ifnull(EXAM_internal_mark_supplementary_details.theory_total_mark, EXAM_student_overall_internal_mark_details.theory_total_mark)  " +
		"as theory_total_mark,  " +
		"ifnull(EXAM_internal_mark_supplementary_details.theory_total_attendance_mark, EXAM_student_overall_internal_mark_details.theory_total_attendance_mark)  " +
		"as theory_total_attendance_mark, "+
		"ifnull(EXAM_internal_mark_supplementary_details.practical_total_mark, EXAM_student_overall_internal_mark_details.practical_total_mark)  " +
		"as practical_total_mark,  " +
		"ifnull(EXAM_internal_mark_supplementary_details.practical_total_attendance_mark, EXAM_student_overall_internal_mark_details.practical_total_attendance_mark)  " +
		"as practical_total_attendance_mark,  " +
		"classes.course_id as course_id,  " +
		"EXAM_subject_rule_settings.theory_ese_minimum_mark,  " +    
		"EXAM_subject_rule_settings.theory_ese_maximum_mark,  " +    
		"EXAM_subject_rule_settings.practical_ese_minimum_mark,  " +    
		"EXAM_subject_rule_settings.practical_ese_maximum_mark,  " +
		"EXAM_student_final_mark_details.student_theory_marks,  " +
		"EXAM_student_final_mark_details.student_practical_marks,  " +
		"personal_data.first_name,    " +  
		"personal_data.middle_name,   " +   
		"personal_data.last_name,  " + 
		"EXAM_subject_sections.name as section,   " +
		"EXAM_subject_sections.is_initialise,   " +   
		"EXAM_subject_sections.id as section_id, " +     
		"EXAM_sub_definition_coursewise.subject_order,   " +   
		"EXAM_subject_rule_settings.final_practical_internal_maximum_mark, " +      
		"EXAM_subject_rule_settings.final_theory_internal_maximum_mark,  " + 
		"EXAM_sub_definition_coursewise.practical_credit,  " +
		"EXAM_sub_definition_coursewise.theory_credit,  " +
		"student.register_no,  " +
		"if(subject.is_theory_practical='T','Theory',if(subject.is_theory_practical='P','Practical','Theory')) as subType,  " +
		"classes.term_number as term_number,  " +
		"(if(EXAM_subject_rule_settings.final_theory_internal_maximum_mark > 0,EXAM_subject_rule_settings.final_theory_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.theory_ese_maximum_mark >0,EXAM_subject_rule_settings.theory_ese_maximum_mark,0)) as theoryMax,   " +   
		"(if(EXAM_subject_rule_settings.final_practical_internal_maximum_mark >0,EXAM_subject_rule_settings.final_practical_internal_maximum_mark,0)+if(EXAM_subject_rule_settings.practical_ese_maximum_mark >0,EXAM_subject_rule_settings.practical_ese_maximum_mark,0)) as practicalMax,  " +
		"EXAM_sub_definition_coursewise.dont_show_max_marks,    " +  
		"EXAM_sub_definition_coursewise.dont_show_min_marks, " +  
		"EXAM_sub_definition_coursewise.show_only_grade,   " +
		"course_scheme.name as schemeName,     " + 
		"EXAM_sub_definition_coursewise.dont_show_sub_type,    " +   
		"adm_appln.applied_year,   " +
		"EXAM_definition.academic_year,  " +
		" adm_appln.selected_course_id,EXAM_subject_rule_settings.final_theory_internal_minimum_mark, " +
		" EXAM_subject_rule_settings.final_practical_internal_minimum_mark ,EXAM_subject_rule_settings.theory_ese_theory_final_minimum_mark,EXAM_subject_rule_settings.practical_ese_theory_final_minimum_mark"+
		" ,EXAM_sub_definition_coursewise.dont_consider_failure_total_result,EXAM_supplementary_improvement_application.is_appeared_theory, EXAM_supplementary_improvement_application.is_appeared_practical,EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln,subject.is_certificate_course,if(theory_ese_maximum_mark is null and theory_ese_entered_max_mark is null and practical_ese_maximum_mark is null and practical_ese_entered_max_mark is null, 1, 0) as only_internal,EXAM_supplementary_improvement_application.is_improvement, " +
	    " subject.subject_type_id," +
	    " subject.consolidated_subject_stream_id,"+
		" EXAM_subject_rule_settings.subject_final_minimum as subFinalMin, "+
		" EXAM_subject_rule_settings.subject_final_maximum, "+
		" EXAM_sub_definition_coursewise.dont_add_tot_mark_cls_decln, "+
		" subject.subjectType_based_on_marks, "+
		" EXAM_supplementary_improvement_application.is_supplementary, "+
		" EXAM_student_final_mark_details.graced_mark as theoryGrace ,"+
	    " EXAM_student_final_mark_details.theory_marks_before_grace as theory_marks_before_grace, "+
	    " EXAM_student_overall_internal_mark_details.theory_marks_before_grace as internalGraceMarks ,"+
	    " EXAM_student_overall_internal_mark_details.graced_mark as internalGrace "+
		"from student  " +
		"inner join adm_appln ON student.adm_appln_id = adm_appln.id  " +
		"inner join personal_data ON adm_appln.personal_data_id = personal_data.id  " +
		"left join EXAM_student_final_mark_details on EXAM_student_final_mark_details.student_id = student.id " +
		"and (EXAM_student_final_mark_details.student_theory_marks is not null or  " +
		"		EXAM_student_final_mark_details.student_practical_marks is not null ) " +
		"inner join classes ON EXAM_student_final_mark_details.class_id = classes.id " + 
		"inner join class_schemewise on class_schemewise.class_id = classes.id  " +
		"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id  " +
		"inner join curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id  " +
		"inner join course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id  " +
		"inner join course ON classes.course_id = course.id  " +
		//# inner join subject ON EXAM_student_overall_internal_mark_details.subject_id = subject.id " + 

		"LEFT JOIN EXAM_student_sub_grp_history ON student.id = EXAM_student_sub_grp_history.student_id  " +

		"inner join subject_group ON EXAM_student_sub_grp_history.subject_group_id = subject_group.id and subject_group.is_active=1  " +
		"inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id and subject_group_subjects.is_active=1 " + 
		"inner join subject ON subject_group_subjects.subject_id = subject.id and subject.is_active=1 and EXAM_student_final_mark_details.subject_id = subject.id  " +
		" LEFT JOIN subject_type ON subject.subject_type_id = subject_type.id "+
		"left join EXAM_student_overall_internal_mark_details on EXAM_student_overall_internal_mark_details.student_id = student.id  " +

		"and EXAM_student_overall_internal_mark_details.class_id = classes.id " +
		"and EXAM_student_overall_internal_mark_details.subject_id = subject.id " +
		"inner join EXAM_subject_rule_settings on EXAM_subject_rule_settings.course_id = course.id  " +
		"and EXAM_subject_rule_settings.subject_id = subject.id  " +
		"and EXAM_subject_rule_settings.scheme_no=classes.term_number  " +
		"and EXAM_subject_rule_settings.academic_year = curriculum_scheme_duration.academic_year  " +
		"left join EXAM_sub_definition_coursewise on EXAM_sub_definition_coursewise.course_id = course.id  " +
		"and EXAM_sub_definition_coursewise.subject_id = subject.id  " +
		"and EXAM_sub_definition_coursewise.scheme_no = classes.term_number  " +
		"and EXAM_sub_definition_coursewise.academic_year=curriculum_scheme_duration.academic_year  " +
		"left join EXAM_subject_sections on EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id  " +
		"left join EXAM_definition ON ifnull(EXAM_student_final_mark_details.exam_id,EXAM_student_overall_internal_mark_details.exam_id) = EXAM_definition.id  " +
		"left join EXAM_supplementary_improvement_application on EXAM_supplementary_improvement_application.scheme_no = classes.term_number  " +
		"and EXAM_supplementary_improvement_application.student_id = student.id  " +
		"and EXAM_supplementary_improvement_application.subject_id = subject.id  " +
		//#	 and EXAM_supplementary_improvement_application.exam_id = EXAM_definition.id  " +
		"left join EXAM_internal_mark_supplementary_details on EXAM_internal_mark_supplementary_details.class_id = classes.id  " +
		//#	 and EXAM_internal_mark_supplementary_details.exam_id = EXAM_definition.id  " +
		"and EXAM_internal_mark_supplementary_details.student_id = student.id  " +
		"and EXAM_internal_mark_supplementary_details.subject_id = subject.id  " +
		"AND if((classes.course_id=32), " +
		"		(EXAM_internal_mark_supplementary_details.exam_id=EXAM_definition.id),  " +
		"		(EXAM_internal_mark_supplementary_details.exam_id=(select e.exam_id from  EXAM_internal_mark_supplementary_details e  " +
		"				inner join EXAM_definition as ex on e.exam_id=ex.id  " +
		"				where e.class_id=classes.id and e.student_id=student.id and e.subject_id=subject.id  " +
		"				and ex.exam_type_id in (3, 6) order by ex.year desc, ex.month desc limit 1)))  " +
		"	where student.id in (:ids) and  student.id not in (select studentdetention.student_id from EXAM_student_detention_rejoin_details studentdetention where studentdetention.discontinued=1) " +
		" and classes.term_number="+schemeNo+
		" group by term_number, student_id, subject_id,exam_year asc,exam_month asc  " +

		" order by term_number, student_id, subject_id,exam_year asc,exam_month asc  " ;

		return query;

	}
     
}
