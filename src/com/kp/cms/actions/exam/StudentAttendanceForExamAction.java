package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.StudentAttendanceForExamForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.StudentAttendanceForExamHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IStudentAttendanceForExamTransaction;
import com.kp.cms.transactionsimpl.exam.StudentAttendanceForExamTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class StudentAttendanceForExamAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentAttendanceForExamAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ExamMarksEntry.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentAttendance(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initSecuredMarksEntry input");
		StudentAttendanceForExamForm studentAttendanceForExamForm = (StudentAttendanceForExamForm) form;// Type casting the Action form to Required Form
		studentAttendanceForExamForm.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(studentAttendanceForExamForm);// setting the requested data to form
		log.info("Exit initSecuredMarksEntry input");
		
		return mapping.findForward(CMSConstants.STUDENT_ATTENDANCE_FOR_EXAM_INPUT);
	}

	/**
	 * @param studentAttendanceForExamForm
	 * @param request
	 */
	private void setRequiredDatatoForm(StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception {
		//added by Smitha - for new academic year input addition
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(studentAttendanceForExamForm.getYear()!=null && !studentAttendanceForExamForm.getYear().isEmpty()){
			year=Integer.parseInt(studentAttendanceForExamForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
	//	Map<Integer, String> examNameMap = CommonAjaxExamHandler.getInstance().getExamNameByExamType(studentAttendanceForExamForm.getExamType());// getting exam list based on exam Type
		Map<Integer,String> examNameMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(studentAttendanceForExamForm.getExamType(),year); 
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		studentAttendanceForExamForm.setExamNameList(examNameMap);// setting the examNameMap to form
		
		//Newly Added For Making default Current Exam
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(studentAttendanceForExamForm.getExamType());
		if((studentAttendanceForExamForm.getExamId()==null || studentAttendanceForExamForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			studentAttendanceForExamForm.setExamId(currentExam);
		
		if(studentAttendanceForExamForm.getExamId()!=null && !studentAttendanceForExamForm.getExamId().isEmpty()){
			/* code commented by chandra
			ArrayList<KeyValueTO> subjectList=CommonAjaxExamHandler.getInstance().getSubjectCodeName(studentAttendanceForExamForm.getDisplaySubType(), Integer.parseInt(studentAttendanceForExamForm.getExamId()));
			studentAttendanceForExamForm.setSubjectList(subjectList);  */
			Map<Integer, String> subjectMap=NewSecuredMarksEntryHandler.getInstance().getSubjects(studentAttendanceForExamForm.getExamId(),studentAttendanceForExamForm.getDisplaySubType(),studentAttendanceForExamForm.getExamType(),String.valueOf(year));
			studentAttendanceForExamForm.setSubjectMap(subjectMap);
			
		}
		
		if(studentAttendanceForExamForm.getSubjectId()!=null && !studentAttendanceForExamForm.getSubjectId().isEmpty()){
			String value = "";
			Map<String, String> subjectTypeMap = new HashMap<String, String>();
			int subjectId = Integer.parseInt(studentAttendanceForExamForm.getSubjectId());
			value = CommonAjaxHandler.getInstance().getSubjectsTypeBySubjectId(subjectId);
			if (value.equalsIgnoreCase("T")) {
				subjectTypeMap.put("T", "Theory");
			}
			if (value.equalsIgnoreCase("P")) {
				subjectTypeMap.put("P", "Practical");
			}
			if (value.equalsIgnoreCase("B")) {
				subjectTypeMap.put("T", "Theory");
				subjectTypeMap.put("P", "Practical");
				value = "t";
			}
			studentAttendanceForExamForm.setSubjectType(value.toUpperCase());
			studentAttendanceForExamForm.setSubjectTypeList(subjectTypeMap);
			
		}else{
			studentAttendanceForExamForm.setSubjectType(null);
			studentAttendanceForExamForm.setSubjectTypeList(null);
		}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveStudentAttendance(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		StudentAttendanceForExamForm studentAttendanceForExamForm = (StudentAttendanceForExamForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors =studentAttendanceForExamForm.validate(mapping, request);
		if(studentAttendanceForExamForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(studentAttendanceForExamForm.getSchemeNo()==null || studentAttendanceForExamForm.getSchemeNo().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.STUDENT_ATTENDANCE_FOR_EXAM_SCHEME_REQD));
			}
		}
		
		setUserId(request,studentAttendanceForExamForm);
		if (errors.isEmpty()) {
			try {
				String inputStr = studentAttendanceForExamForm.getRegisterNoEntry();
				String patternStr = ",";
				String[] registerNoString = inputStr.split(patternStr);
				List<String> registerNoList = new ArrayList<String>();
				for (String registerNo : registerNoString) {
					if (registerNo.trim().length() > 0 && !registerNoList.contains(registerNo.trim().toUpperCase())) {
						registerNoList.add(registerNo.trim().toUpperCase());
					}
				}
				List<String> notValidRegNo=StudentAttendanceForExamHandler.getInstance().checkIsValidRegisterNo(registerNoList,studentAttendanceForExamForm);
				if(notValidRegNo!=null && !notValidRegNo.isEmpty()){
					String regNo="";
					Iterator<String> itr=notValidRegNo.iterator();
					while (itr.hasNext()) {
						if(!regNo.isEmpty())
							regNo =regNo+","+itr.next();
						else
						regNo=itr.next();
					}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.studentAttendance.notValid.registerNo",regNo));
				}
				if(!studentAttendanceForExamForm.getIdList().isEmpty()){
				List<String> marksExists=StudentAttendanceForExamHandler.getInstance().checkMarksEnteredForRegisterNo(registerNoList,studentAttendanceForExamForm);
					if(marksExists!=null && !marksExists.isEmpty()){
						String regNo="";
						Iterator<String> itr=marksExists.iterator();
						while (itr.hasNext()) {
							if(!regNo.isEmpty())
								regNo =regNo+","+itr.next();
							else
								regNo=itr.next();
						}
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.studentAttendance.registerNo.marksExists",regNo));
					}
				}
				if (errors.isEmpty()) {
					List<String> tempList=new ArrayList<String>();
					Iterator<String> itr=registerNoList.iterator();
					while (itr.hasNext()) {
						String reg = (String) itr.next();
						if(!notValidRegNo.contains(reg)){
							tempList.add(reg);
						}
					}
					boolean isSaved=true;
					if(!studentAttendanceForExamForm.getIdList().isEmpty()){
						isSaved=StudentAttendanceForExamHandler.getInstance().saveAttendance(studentAttendanceForExamForm,tempList);
						if(isSaved){
							//Added by dilip
							if((studentAttendanceForExamForm.getExamType().equalsIgnoreCase("Internal") 
									|| studentAttendanceForExamForm.getExamType().equalsIgnoreCase("Regular")) 
										&& studentAttendanceForExamForm.getIsAbscent().equalsIgnoreCase("yes")){
								Iterator<String> itr1=tempList.iterator();
								while (itr1.hasNext()) {
									String regNo1 = (String) itr1.next();
									int examId = Integer.parseInt(studentAttendanceForExamForm.getExamId());
									int subId = Integer.parseInt(studentAttendanceForExamForm.getSubjectId());
									IStudentAttendanceForExamTransaction transaction=StudentAttendanceForExamTransactionImpl.getInstance();
									String stuExamDetailsQuery= StudentAttendanceForExamHandler.getInstance().getQueryForStudentExamDetails(regNo1, examId, subId);
									List<Object[]> stuExamDetails=transaction.getStuExamDetails(stuExamDetailsQuery);
									if(stuExamDetails!=null && !stuExamDetails.isEmpty()){
									Iterator<Object[]> ite= stuExamDetails.iterator();
									SubjectTO to = new SubjectTO();
									StudentTO sto = new StudentTO();
									
									while(ite.hasNext()){
										Object[] data = ite.next();
										
										if(data[0]!=null)
											sto.setRegisterNo(data[0].toString());
										if(data[1]!=null)
											sto.setStudentName(data[1].toString());
										if(data[2]!=null && data[2].toString().length()==10){
											sto.setParentNo(data[2].toString());
										}else if(data[2]!=null && data[2].toString().length()==5){
											sto.setParentNo(data[2].toString() + data[3].toString());
										}else if(data[3]!=null && data[3].toString().length()==10){
											sto.setParentNo(data[3].toString());
										}
										if(data[4]!=null)
											to.setCode(data[4].toString());
										if(data[5]!=null)
											to.setName(data[5].toString());
										if(data[6]!=null){
											String time= data[6].toString();
											String dateString = time.substring(0, 10);
											String inputDateFormat = "yyyy-mm-dd";
											String outPutdateFormat = "dd/mm/yyyy";
											String hour = time.substring(11, 13);
											String minute = time.substring(14, 16);
											int h=Integer.parseInt(hour);
											if(h>6 && h<12)
												to.setStartTime(hour+":"+minute+" AM");
											else
												to.setStartTime(hour+":"+minute+" PM");
											
											to.setStartDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));				
										}else{
											to.setStartDate("");
										}
									}
									StudentAttendanceForExamHandler.getInstance().sendSMSToParent(studentAttendanceForExamForm, to, sto);
								}
								}
							}
						
							//Added by dilip
							messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.addsuccess","Marks"));
							saveMessages(request, messages);
							studentAttendanceForExamForm.resetFields();
						}else{
							messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.norecords"));
							saveMessages(request, messages);
							studentAttendanceForExamForm.resetFields();
						}
					}else{
						messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.norecords"));
						saveMessages(request, messages);
						studentAttendanceForExamForm.resetFields();
					}
				} else {
					addErrors(request, errors);
					setRequiredDatatoForm(studentAttendanceForExamForm);// setting the requested data to form			
					log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
					return mapping.findForward(CMSConstants.STUDENT_ATTENDANCE_FOR_EXAM_INPUT);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				studentAttendanceForExamForm.setErrorMessage(msg);
				studentAttendanceForExamForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(studentAttendanceForExamForm);// setting the requested data to form			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.STUDENT_ATTENDANCE_FOR_EXAM_INPUT);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.STUDENT_ATTENDANCE_FOR_EXAM_INPUT);
	}
}