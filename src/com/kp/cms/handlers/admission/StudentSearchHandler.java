package com.kp.cms.handlers.admission;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.StudentSearchForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.admission.StudentSearchHelper;
import com.kp.cms.to.admission.StudentSearchTO;
import com.kp.cms.transactions.admission.IStudentSearchTransaction;
import com.kp.cms.transactionsimpl.admission.StudentSearchTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class StudentSearchHandler {

	/**
	 * Represents single ton object of the StudentSearchHandler
	 */
	private static volatile StudentSearchHandler studentSearchHandler = null;
	private static final Log log = LogFactory.getLog(StudentSearchHandler.class);
	private StudentSearchHandler() {

	}

	/**
	 * @return single ton object of the StudentSearchHandler
	 */
	public static StudentSearchHandler getInstance() {
		if (studentSearchHandler == null) {
			studentSearchHandler = new StudentSearchHandler();
		}
		return studentSearchHandler;
	}

	/**
	 * get the selected students from the search criteria.
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentSearchTO> getSelectedStudents(
			StudentSearchForm studentSearchForm) throws Exception {
		log.info("entering into getSelectedStudents of StudentSearchHandler class.");
		IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
		List<StudentSearchTO> studentSearchTo=new ArrayList<StudentSearchTO>();
		if(studentSearchForm.getInterviewType()!=null && studentSearchForm.getInterviewType().length!=0){
			for(int i=0; i<studentSearchForm.getInterviewType().length;i++){
				studentSearchForm.setSearchedIntrvwType(studentSearchForm.getInterviewType()[i]);
				List<StudentSearchTO> tempsearch = StudentSearchHelper
				.convertBoToTo(studentSearchTransactionImpl
						.getSelectedStudents(StudentSearchHelper
								.getSelectedStudentSearch(studentSearchForm)),
						studentSearchForm);
				studentSearchTo.addAll(tempsearch);
			}
		}
		removeDuplicates(studentSearchTo);
		log.info("exit of getSelectedStudents of StudentSearchHandler class.");
		return studentSearchTo;

	}

	/**
	 * get the candidates for the interview from search criteria
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentSearchTO> getStudentSearchResults(
			StudentSearchForm studentSearchForm) throws Exception {
		log.info("entering into getSelectedStudents of StudentSearchHandler class.");
		IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
		List<StudentSearchTO> studentSearchTo=new ArrayList<StudentSearchTO>();
		if(studentSearchForm.getInterviewType()!=null && studentSearchForm.getInterviewType().length!=0){
			for(int i=0; i<studentSearchForm.getInterviewType().length;i++){
				studentSearchForm.setSearchedIntrvwType(studentSearchForm.getInterviewType()[i]);
				List<StudentSearchTO> tempsearch = StudentSearchHelper .convertBoToTo( studentSearchTransactionImpl .getStudentSearch(StudentSearchHelper .getSelectionSearchCriteria(studentSearchForm)), studentSearchForm);
				studentSearchTo.addAll(tempsearch);
			}
		}
//		removeDuplicates(studentSearchTo);
		
		log.info("exit of getSelectedStudents of StudentSearchHandler class.");
		return studentSearchTo;
	}

	/**
	 * @param studentSearchTo
	 */
	private void removeDuplicates(List<StudentSearchTO> studentSearchTo) {
		Set<Integer> idSet=new HashSet<Integer>();
		if(studentSearchTo!=null){
			Iterator<StudentSearchTO> stItr= studentSearchTo.iterator();
			while (stItr.hasNext()) {
				StudentSearchTO searchTO= (StudentSearchTO) stItr.next();
				if(idSet.contains(Integer.parseInt(searchTO.getAdmApplnId())))
				{
					stItr.remove();
				}else{
					idSet.add(Integer.parseInt(searchTO.getAdmApplnId()));
				}
				
			}
		}
	}

	/**
	 * Get the candidates for bypassing interview from the search criteria.
	 * @param studentSearchForm
	 * @param isSelected
	 * @return
	 * @throws ApplicationException
	 */
	public List<StudentSearchTO> getBypassInterviewSearchResults(
			StudentSearchForm studentSearchForm, boolean isSelected)
			throws ApplicationException {
		log.info("entering into getBypassInterviewSearchResults of StudentSearchHandler class.");
		IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
		List<StudentSearchTO> studentSearchTo = StudentSearchHelper.convertBoToTo(studentSearchTransactionImpl
						.getStudentSearch(StudentSearchHelper.getBypassInterviewSelectionSearchCriteria(studentSearchForm, isSelected)),studentSearchForm);
		log.info("exit of getBypassInterviewSearchResults of StudentSearchHandler class.");
		return studentSearchTo;
	}

	/**
	 * persists the selected candidates.
	 * @param selectedCandidatesList
	 * @param interviewProgramCourseId
	 * @param userId
	 * @throws Exception
	 */
	public void updateSelectedCandidates(String[] selectedCandidatesList,List<StudentSearchTO> totalStudentList,String userId) throws Exception {
		log.info("entering into updateSelectedCandidates of StudentSearchHandler class.");
		IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
		Map<Integer, String> studentInterviewMap= getInterviewMap(totalStudentList);
		studentSearchTransactionImpl.updateSelectedCandidatesList( selectedCandidatesList, studentInterviewMap, userId);
		
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLATE_APPLICATION_RECIEVED_MAIL);
		for (String selectedCandidate : selectedCandidatesList) {
			String sequence=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(studentInterviewMap.get(Integer.valueOf(selectedCandidate.trim()))),"InterviewProgramCourse",true,"sequence");
			if(sequence!=null && sequence.equalsIgnoreCase("1") && !list.isEmpty()){
				AdmAppln admAppln=studentSearchTransactionImpl.getAdmAppln(selectedCandidate);
				String desc = list.get(0).getTemplateDescription();
				PersonalData pData=admAppln.getPersonalData();
				//send mail to applicant
				if(admAppln.getInterScheduleSelection()==null){
					if(pData!=null){
						if(pData.getEmail()!=null && !pData.getEmail().isEmpty()){
							//replace dyna data
							String subject=admAppln.getAdmStatus();
							String statusName= admAppln.getAdmStatus();
							String message =desc;
							message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,pData.getFirstName());
							message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,String.valueOf(admAppln.getApplnNo()));
							message = message.replace(CMSConstants.TEMPLATE_DOB,pData.getDateOfBirth().toString());
							message = message.replace(CMSConstants.TEMPLATE_COURSE,admAppln.getCourseBySelectedCourseId().getName());
							message=  message.replace(CMSConstants.APPLICATION_STATUS,statusName);
							message=  message.replace(CMSConstants.TEMP_PROGRAM,admAppln.getCourseBySelectedCourseId().getProgram().getName());
							MailTO mailTo=sendMail(pData.getEmail(),subject,message);
							if(mailTo!=null){
									CommonUtil.sendMail(mailTo);
							}
						}
					}
					if(admAppln.getCourseBySelectedCourseId().getIsApplicationProcessSms()){
						String mobileNo="";
						if(admAppln.getPersonalData().getMobileNo1()!=null && !admAppln.getPersonalData().getMobileNo1().isEmpty()){
							if(admAppln.getPersonalData().getMobileNo1().trim().equals("0091") || admAppln.getPersonalData().getMobileNo1().trim().equals("+91")
									|| admAppln.getPersonalData().getMobileNo1().trim().equals("091") || admAppln.getPersonalData().getMobileNo1().trim().equals("0"))
								mobileNo = "91";
							else
								mobileNo=admAppln.getPersonalData().getMobileNo1();
						}else{
							mobileNo="91";
						}
						if(admAppln.getPersonalData().getMobileNo2()!=null && !admAppln.getPersonalData().getMobileNo2().isEmpty()){
							mobileNo=mobileNo+admAppln.getPersonalData().getMobileNo2();
						}
						if(mobileNo.length()==12){
							ApplicationStatusUpdateHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_APPLICATION_RECIEVED,Integer.toString(admAppln.getApplnNo()));
						}
					}
				}
			}
		}
		log.info("exit of updateSelectedCandidates of StudentSearchHandler class.");
	}
	/**
	 * @param email
	 * @param sub
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public MailTO sendMail(String email, String sub, String message)throws Exception {
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			return null;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return null;
		}
//			String adminmail=prop.getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
			String adminmail=CMSConstants.MAIL_USERID;
			String toAddress=email;
			// MAIL TO CONSTRUCTION
			String subject=sub;
			String msg=message;
		
			MailTO mailto=new MailTO();
			mailto.setFromAddress(adminmail);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
		return mailto;
	}
	/**
	 * @param totalStudentList
	 * @return
	 */
	private Map<Integer, String> getInterviewMap(
			List<StudentSearchTO> totalStudentList) {
		Map<Integer, String> studentInterviewMap= new HashMap<Integer, String>();
		if(totalStudentList!=null){
			Iterator<StudentSearchTO> searchItr=totalStudentList.iterator();
			while (searchItr.hasNext()) {
				StudentSearchTO studentTO = (StudentSearchTO) searchItr.next();
				studentInterviewMap.put(Integer.parseInt(studentTO.getAdmApplnId()), studentTO.getInterviewProgCrsId());
			}
		}
		return studentInterviewMap;
	}

	/**
	 * Persists the bypass interview candidates.
	 * @param studentSearchForm
	 * @param isSelected
	 * @throws Exception
	 */
	public void updateSgetBypassInterviewCandidates(
			StudentSearchForm studentSearchForm, boolean isSelected)
			throws Exception {
		log.info("entering into updateSgetBypassInterviewCandidates of StudentSearchHandler class.");
		IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
		studentSearchTransactionImpl.updateBypassInterviewCandidatesList(
				studentSearchForm, isSelected);
		log.info("exit of updateSgetBypassInterviewCandidates of StudentSearchHandler class.");
	}

	/**
	 * Remove the selected candidates from the selection list.
	 * @param selectedCandidatesList
	 * @throws Exception
	 */
	public void removeSelectedCandidates(String[] selectedCandidatesList)
			throws Exception {
		log.info("entering into removeSelectedCandidates of StudentSearchHandler class.");
		IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
		studentSearchTransactionImpl
				.removeSelectedCandidate(selectedCandidatesList);
		log.info("exit of removeSelectedCandidates of StudentSearchHandler class.");
	}

}
