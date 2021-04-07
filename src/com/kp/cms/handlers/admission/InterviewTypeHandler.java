package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.InterviewProcessForm;
import com.kp.cms.helpers.admission.InterviewHelper;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewTO;
import com.kp.cms.transactions.admission.IInterviewTypeTxn;
import com.kp.cms.transactionsimpl.admission.InterviewTypeTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class InterviewTypeHandler {

	/**
	 * This method will return an unique instance of InterviewTypeHandler.
	 */

	private static volatile InterviewTypeHandler interviewTypeHandler = null;
	private static Log log = LogFactory.getLog(InterviewTypeHandler.class);

	private InterviewTypeHandler() {
	}

	public static InterviewTypeHandler getInstance() {
		if (interviewTypeHandler == null) {
			interviewTypeHandler = new InterviewTypeHandler();
		}
		return interviewTypeHandler;
	}

	/**
	 * Get all Interview Type list from the database.
	 * 
	 * @return List - Interview Type transaction List object
	 */

	public List<InterviewProgramCourseTO> getInterviewType() throws Exception {
		log.info("entered getInterviewType..");
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();

		List<InterviewProgramCourse> interviewBoList = interviewTypeTxn
				.getInterviewType();
		List<InterviewProgramCourseTO> getInterviewList = InterviewHelper
				.convertBOstoTos(interviewBoList);
		log.info("Exit getInterviewType..");
		return getInterviewList;
	}

	/**
	 * This method is used to save data to database.
	 * 
	 * @param interviewForm
	 * @param request
	 * @return boolean value.
	 * @throws Exception
	 */

	public boolean persistInterview(InterviewProcessForm interviewForm,
			HttpServletRequest request) throws Exception {
	
		
		log.info("entered persistInterview..");
		boolean flag = false;
		int len = 0; 
		if(interviewForm.getInterviewTypeList()!= null && interviewForm.getInterviewTypeList().size() > 0){
			len = interviewForm.getInterviewTypeList().size();
		}
		InterviewTO interviewTO = InterviewHelper.getInterviewTO(interviewForm);
		//Interview interview = InterviewHelper.getInterviewBO(interviewTO);
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		InterviewProgramCourse interviewProgramCourse;
		List<Interview> interviewList = new ArrayList<Interview>();
		for (int i = 0; i< len; i++){
			Interview interview = InterviewHelper.getInterviewBO(interviewTO);
			int interviewPrgCrsId = interviewForm.getInterviewTypeList().get(i);
			interviewProgramCourse = new InterviewProgramCourse();
			interviewProgramCourse.setId(interviewPrgCrsId);
			interview.setInterviewProgramCourse(interviewProgramCourse);
			flag = interviewTypeTxn.persistInterview(interview);
			if(flag){
				interviewList.add(interview);
			}
		}
		if (flag) {
			Set applNoSet = new TreeSet();
			int courseId = 0;

			if (CommonUtil.checkForEmpty(interviewForm.getCourseId())) {
				courseId = Integer.parseInt(interviewForm.getCourseId());
			}
			int programId = Integer.parseInt(interviewForm.getProgram());
			int years = Integer.parseInt(interviewForm.getYears());
			int noOfCandidates = Integer.parseInt(interviewForm
					.getNoOfCandidates());
			int examCenterId = 0;
			if(interviewForm.getExamCenterId()!= null && !interviewForm.getExamCenterId().trim().isEmpty()){
				examCenterId = Integer.parseInt(interviewForm.getExamCenterId()); 
			}
			List<InterviewSelected> candidateList = interviewTypeTxn
					.getCandidates(courseId, programId, years, interviewForm
							.getInterviewTypeList(), noOfCandidates, examCenterId);
			
			String[] admAppln = InterviewHelper.getAdmAppln(candidateList);
			HashMap<Integer, Integer> intPrgCourseMap = InterviewHelper.getInterviewTypesForCandidates(candidateList);
			InterviewCard[] interviewCard;
			List<InterviewCard> interviewCardList = new ArrayList<InterviewCard>();
			if (admAppln != null) {
				if (!interviewForm.getSingleGroup().equals(CMSConstants.GROUP)) {
					interviewCard = InterviewHelper.generateInterviewCards(
							admAppln, interviewForm, /*interview*/interviewList, intPrgCourseMap);
				} else {
					interviewCard = InterviewHelper
							.generateInterviewCardsForGroup(admAppln,
									interviewForm, /*interview*/interviewList, intPrgCourseMap);

				}
				for (int i = 0; i < admAppln.length; i++) {
					applNoSet.add(Integer.valueOf(admAppln[i]));
				}
				if (interviewCard != null) {
					interviewTypeTxn.persistInterviewCard(interviewCard,
							admAppln);
				}
				if (!applNoSet.isEmpty()) {
					interviewTypeTxn.updateInterviewSelected(applNoSet);
					interviewTypeTxn.updateSeatNo(applNoSet);
					interviewCardList = interviewTypeTxn
							.getInterviewCardForMail(applNoSet);
				}
				if (interviewCardList != null) {
					InterviewHelper.sendMailToStudent(interviewCardList,
							request, interviewForm);
					InterviewHelper.sendSMSToStudent(interviewCardList,interviewForm);
					// Iterator<InterviewCard> itr =
					// interviewCardList.iterator();
					// while(itr.hasNext()){
					// InterviewCard iCard = itr.next();
					// InterviewHelper.sendMailToStudent(iCard);
					// }

				}

			}
		}
		log.info("Exit persistInterview..");
		return flag;

	}
	


	/**
	 * This method is used to persist data to AdmAppln
	 * 
	 * @param interviewForm
	 * @return boolean value.
	 * @throws Exception
	 */

	public boolean persistAdmAppln(InterviewProcessForm interviewForm)
			throws Exception {
		log.info("entered persistAdmAppln..");
		boolean flag = false;
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		if (interviewForm.getCourseId() != null) {

			flag = interviewTypeTxn.updateAdmAppln(Integer
					.parseInt(interviewForm.getCourseId()));
		}
		log.info("Exit persistAdmAppln..");
		return flag;

	}

	/**
	 * This method is used to get candidate count.
	 * 
	 * @param countID
	 * @return int value.
	 * @throws Exception
	 */

	public int getCandidateCount(int countID) throws Exception {
		int candidateCount = 0;
		log.info("entered getCandidateCount..");
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		candidateCount = interviewTypeTxn.getCandidateCount(countID);
		log.info("Exit getCandidateCount..");
		return candidateCount;
	}

	/**
	 * This method is used to get program details
	 * 
	 * @param interviewTypeId
	 * @return InterviewProgramCourse instance.
	 * @throws Exception
	 */

	public InterviewProgramCourse getProgramDetails(int interviewTypeId)
			throws Exception {
		log.info("entered getProgramDetails..");
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		InterviewProgramCourse intervewProgramCourse = interviewTypeTxn
				.getProgramDetails(interviewTypeId);
		log.info("Exit getProgramDetails..");
		return intervewProgramCourse;
	}

	/**
	 * 
	 * @param interviewProcessForm
	 * @param courseId
	 * @return list of type InterviewCard.
	 * @throws Exception
	 */

	public List<InterviewCardTO> getStudentsList(
			InterviewProcessForm interviewProcessForm, int courseId)
			throws Exception {
		log.info("entered getStudentsList..");
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		List<InterviewCardTO> studentListTO = null;

		if (interviewProcessForm.getYears() != null) {
			List<InterviewCardTO> studentList = interviewTypeTxn
					.getStudentsList(courseId, Integer
							.parseInt(interviewProcessForm.getYears()));
			studentListTO = InterviewHelper.getStudentsList(studentList);
		}
		log.info("Exit getStudentsList..");
		return studentListTO;
	}

	/**
	 * This method is used to get student details
	 * 
	 * @param applicationID
	 * @param interviewtypeid
	 * @param programTypeID
	 * @param programID
	 * @param courseID
	 * @param year
	 * @param birthDate
	 * @param firstName
	 * @return list of type AdmApplnTO
	 * @throws Exception
	 */

	public List getByStudentDetails(int applicationID, int interviewtypeid,
			int programTypeID, int programID, int courseID, int year,
			java.sql.Date birthDate, String firstName) throws Exception {
		log.info("entered getByStudentDetails..");
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		List<InterviewCard> admApplnBOList = interviewTypeTxn
				.getByStudentDetails(applicationID, interviewtypeid,
						programTypeID, programID, courseID, year, birthDate,
						firstName);

		List admApplnTOList = InterviewHelper.getApplicationTos(admApplnBOList);
		log.info("Exit getByStudentDetails..");
		return admApplnTOList;

	}

	/**
	 * This method is used to update InterviewCard details.
	 * 
	 * @param interviewCardTOList
	 * @return boolean value.
	 * @throws Exception
	 */

	public boolean updateInterviewCard(List interviewCardTOList)
			throws Exception {
		log.info("entered updateInterviewCard..");
		boolean flage = false;
		List interviewCardList = InterviewHelper
				.converttoBO(interviewCardTOList);
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		flage = interviewTypeTxn.updateInterviewCard(interviewCardList);
		log.info("Exit updateInterviewCard..");
		return flage;
	}

	/**
	 * This method is used to get InterviewCard details
	 * 
	 * @param selectedCandidates
	 * @param org
	 * @param interviewForm
	 * @return list of type InterviewCardTO
	 * @throws Exception
	 */

	public List getInterviewCard(String[] selectedCandidates, Organisation org,
			InterviewProcessForm interviewForm) throws Exception {
		log.info("entered getInterviewCard..");

		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		Set applNoSet = new HashSet();
		String[] splitInterviewType = null;
		if (selectedCandidates != null) {
			for (int i = 0; i < selectedCandidates.length; i++) {
				splitInterviewType = CommonUtil
						.getInterviewType(selectedCandidates[i]);
				applNoSet.add(Integer.valueOf(splitInterviewType[0]));
			}

		}
		List<InterviewCard> interviewBO = interviewTypeTxn
				.getInterviewCard(applNoSet);
		List interviewCardTOList = InterviewHelper.getInterviewCardTO(
				interviewBO, org, selectedCandidates, interviewForm);
		log.info("Exit getInterviewCard..");
		return interviewCardTOList;

	}

	/**
	 * This method is used to get InterviewCard details.
	 * 
	 * @param selectedCandidates
	 * @return list of type InterviewCard.
	 * @throws Exception
	 */

	public List getInterviewCardList(String[] selectedCandidates)
			throws Exception {
		log.info("entered getInterviewCardList..");
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		Set applNoSet = new HashSet();
		String[] splitInterviewType = null;
		if (selectedCandidates != null) {
			for (int i = 0; i < selectedCandidates.length; i++) {
				splitInterviewType = CommonUtil
						.getInterviewType(selectedCandidates[i]);
				applNoSet.add(Integer.valueOf(splitInterviewType[0]));
			}
		}
		List<InterviewCard> interviewBO = interviewTypeTxn
				.getInterviewCard(applNoSet);
		List interviewCardTOList = InterviewHelper.getInterviewCardTOList(
				interviewBO, selectedCandidates);
		log.info("Exit getInterviewCardList..");
		return interviewCardTOList;

	}

	/**
	 * 
	 * @param courseid
	 * @param interviewType
	 * @return String value.
	 * @throws Exception
	 */

	public String getIntCardContent(int courseid, String interviewType)
			throws Exception {
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		String intCardContent = interviewTypeTxn.getIntCardContentFromTable(
				courseid, interviewType);
		return intCardContent;
	}

	/**
	 * This method is used to get courseId based on application number.
	 * 
	 * @param applno
	 * @return int value.
	 * @throws Exception
	 */

	public int getCourseID(int applno) throws Exception {
		IInterviewTypeTxn interviewTypeTxn = InterviewTypeTxnImpl.getInstance();
		int courseid = interviewTypeTxn.getcourseID(applno);
		return courseid;
	}

}
