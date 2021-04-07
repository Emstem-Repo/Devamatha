package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.InterviewRatingFactor;
import com.kp.cms.bo.employee.NewInterviewComments;
import com.kp.cms.bo.employee.NewInterviewCommentsDetails;
import com.kp.cms.forms.employee.NewInterviewCommentsForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.employee.EmpEducationalDetailsTO;
import com.kp.cms.to.employee.EmpOnlineResumeTO;
import com.kp.cms.to.employee.InterviewRatingFactorTO;
import com.kp.cms.to.employee.NewInterviewCommentsDetailsTo;
import com.kp.cms.to.employee.NewInterviewCommentsTO;
import com.kp.cms.utilities.CommonUtil;

public class NewInterviewCommentsHelper {
 
	public static volatile NewInterviewCommentsHelper newInterviewCommentsHelper = null;
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	public static NewInterviewCommentsHelper getInstance(){
		if(newInterviewCommentsHelper == null){
			newInterviewCommentsHelper = new NewInterviewCommentsHelper();
			return newInterviewCommentsHelper;
		}
		return newInterviewCommentsHelper;
		
	}

	/**
	 * @param interviewCommentsForm
	 * @return
	 */
	public NewInterviewComments copyDataFromFormToBO(NewInterviewCommentsForm interviewCommentsForm) {
		
		NewInterviewComments newInterviewComments = new NewInterviewComments();
		newInterviewComments.setId(interviewCommentsForm.getInterviewCommentId());
		Set<NewInterviewCommentsDetails> commentsDetails=new HashSet<NewInterviewCommentsDetails>();
		EmpOnlineResume empOnlineResume=new EmpOnlineResume();
		 List<InterviewRatingFactorTO> factorTOs=interviewCommentsForm.getInterviewRatingFactorTOs();
		
		if(factorTOs!=null && !factorTOs.isEmpty()){
			Iterator<InterviewRatingFactorTO> iterator=factorTOs.iterator();
			while (iterator.hasNext()) {
				InterviewRatingFactorTO interviewRatingFactorTO = (InterviewRatingFactorTO) iterator.next();
				NewInterviewCommentsDetails details = new NewInterviewCommentsDetails();
				InterviewRatingFactor factor = new InterviewRatingFactor();
				factor.setId(interviewRatingFactorTO.getId());
				details.setInterviewRatingFactor(factor);
				details.setNewInterviewComments(newInterviewComments);
				details.setId(interviewRatingFactorTO.getInterviewCommentsDetailsId());
				if(interviewRatingFactorTO.getInternalInterviewer1()!=null){
					if(!interviewRatingFactorTO.getInternalInterviewer1().isEmpty()){
						details.setInternalInterviewer1(interviewRatingFactorTO.getInternalInterviewer1());
					}
				}
				if(interviewRatingFactorTO.getInternalInterviewer2()!=null){
					if(!interviewRatingFactorTO.getInternalInterviewer2().isEmpty()){
						details.setInternalInterviewer2(interviewRatingFactorTO.getInternalInterviewer2());
					}
				}
				if(interviewRatingFactorTO.getInternalInterviewer3()!=null){
					if(!interviewRatingFactorTO.getInternalInterviewer3().isEmpty()){
						details.setInternalInterviewer3(interviewRatingFactorTO.getInternalInterviewer3());
					}
				}
				if(interviewRatingFactorTO.getExternalInterviewer1()!=null){
					if(!interviewRatingFactorTO.getExternalInterviewer1().isEmpty()){
						details.setExternalInterviewer1(interviewRatingFactorTO.getExternalInterviewer1());
					}
				}
				if(interviewRatingFactorTO.getExternalInterviewer2()!=null){
					if(!interviewRatingFactorTO.getExternalInterviewer2().isEmpty()){
						details.setExternalInterviewer2(interviewRatingFactorTO.getExternalInterviewer2());
					}
				}
				if(interviewRatingFactorTO.getExternalInterviewer3()!=null){
					if(!interviewRatingFactorTO.getExternalInterviewer3().isEmpty()){
						details.setExternalInterviewer3(interviewRatingFactorTO.getExternalInterviewer3());
					}
				}
				details.setCreatedBy(interviewCommentsForm.getUserId());
				details.setCreatedDate(new Date());
				details.setModifiedBy(interviewCommentsForm.getUserId());
				details.setLastModifiedDate(new Date());
				//Added Code
				if(interviewRatingFactorTO.getInternalInterviewer4()!=null){
					if(!interviewRatingFactorTO.getInternalInterviewer4().isEmpty()){
						details.setInternalInterviewer4(interviewRatingFactorTO.getInternalInterviewer4());
					}
				}
				if(interviewRatingFactorTO.getInternalInterviewer5()!=null){
					if(!interviewRatingFactorTO.getInternalInterviewer5().isEmpty()){
						details.setInternalInterviewer5(interviewRatingFactorTO.getInternalInterviewer5());
					}
				}
				if(interviewRatingFactorTO.getInternalInterviewer6()!=null){
					if(!interviewRatingFactorTO.getInternalInterviewer6().isEmpty()){
						details.setInternalInterviewer6(interviewRatingFactorTO.getInternalInterviewer6());
					}
				}
				if(interviewRatingFactorTO.getExternalInterviewer4()!=null){
					if(!interviewRatingFactorTO.getExternalInterviewer4().isEmpty()){
						details.setExternalInterviewer4(interviewRatingFactorTO.getExternalInterviewer4());
					}
				}
				if(interviewRatingFactorTO.getExternalInterviewer5()!=null){
					if(!interviewRatingFactorTO.getExternalInterviewer5().isEmpty()){
						details.setExternalInterviewer5(interviewRatingFactorTO.getExternalInterviewer5());
					}
				}
				if(interviewRatingFactorTO.getExternalInterviewer6()!=null){
					if(!interviewRatingFactorTO.getExternalInterviewer6().isEmpty()){
						details.setExternalInterviewer6(interviewRatingFactorTO.getExternalInterviewer6());
					}
				}
				//
				commentsDetails.add(details);
			}
		}
		newInterviewComments.setNewInterviewCommentsDetails(commentsDetails);
		newInterviewComments.setId(interviewCommentsForm.getInterviewCommentId());
		empOnlineResume.setId(interviewCommentsForm.getId());
		newInterviewComments.setEmpOnlineResume(empOnlineResume);
		newInterviewComments.setNoOfExternalInterviewers(interviewCommentsForm.getNoOfExternalInterviewers());
		newInterviewComments.setNoOfInternalInterviewers(interviewCommentsForm.getNoOfInternalInterviewers());
		newInterviewComments.setComments(interviewCommentsForm.getComments());
		newInterviewComments.setNameOfExternalInterviewer1(interviewCommentsForm.getNameOfExternalInterviewer1());
		newInterviewComments.setNameOfExternalInterviewer2(interviewCommentsForm.getNameOfExternalInterviewer2());
		newInterviewComments.setNameOfExternalInterviewer3(interviewCommentsForm.getNameOfExternalInterviewer3());
		newInterviewComments.setNameOfInternalInterviewer1(interviewCommentsForm.getNameOfInternalInterviewer1());
		newInterviewComments.setNameOfInternalInterviewer2(interviewCommentsForm.getNameOfInternalInterviewer2());
		newInterviewComments.setNameOfInternalInterviewer3(interviewCommentsForm.getNameOfInternalInterviewer3());
		newInterviewComments.setCreatedBy(interviewCommentsForm.getUserId());
		newInterviewComments.setCreatedDate(new Date());
		newInterviewComments.setModifiedBy(interviewCommentsForm.getUserId());
		newInterviewComments.setLastModifiedDate(new Date());
		newInterviewComments.setIsActive(true);
		//Added Code
		newInterviewComments.setNameOfExternalInterviewer4(interviewCommentsForm.getNameOfExternalInterviewer4());
		newInterviewComments.setNameOfExternalInterviewer5(interviewCommentsForm.getNameOfExternalInterviewer5());
		newInterviewComments.setNameOfExternalInterviewer6(interviewCommentsForm.getNameOfExternalInterviewer6());
		newInterviewComments.setNameOfInternalInterviewer4(interviewCommentsForm.getNameOfInternalInterviewer4());
		newInterviewComments.setNameOfInternalInterviewer5(interviewCommentsForm.getNameOfInternalInterviewer5());
		newInterviewComments.setNameOfInternalInterviewer6(interviewCommentsForm.getNameOfInternalInterviewer6());
		newInterviewComments.setJoiningTime(interviewCommentsForm.getJoiningTime());
		return newInterviewComments;
	}

	/**
	 * @param onlineResumes
	 * @return
	 */
	public List<NewInterviewCommentsTO> copyOnlineResumeListToTo(List<Object[]> onlineResumes) {
		List<NewInterviewCommentsTO> interviewCommentsTOs =new ArrayList<NewInterviewCommentsTO>();
		if(onlineResumes !=null && !onlineResumes.isEmpty()){
			Iterator<Object[]> iterator=onlineResumes.iterator();
			while (iterator.hasNext()) {
				Object[]  object = (Object[]) iterator.next();
				NewInterviewCommentsTO to =new NewInterviewCommentsTO();
				if(object[0]!= null){
					if(object[0].toString()!= null && !object[0].toString().isEmpty()){
						to.setId(Integer.parseInt(object[0].toString()));
					}
				}
				if(object[1]!= null){
					if(object[1].toString()!=null && !object[1].toString().isEmpty()){
						to.setName(object[1].toString());
					}
				}
				if(object[2]!= null){
					if(object[2].toString()!=null && !object[2].toString().isEmpty()){
						to.setEmail(object[2].toString());
					}
				}
				interviewCommentsTOs.add(to);
			}
		}
		
		return interviewCommentsTOs;
	}

	/**
	 * @param interviewComments
	 * @param ratingFactorList
	 * @return
	 */
	public NewInterviewCommentsForm copyEmpInfoBOtoTO(NewInterviewComments interviewComments, NewInterviewCommentsForm form) {
		

		if(interviewComments != null){
			form.setInterviewCommentId(interviewComments.getId());
			form.setNoOfExternalInterviewers(interviewComments.getNoOfExternalInterviewers());
			form.setNoOfInternalInterviewers(interviewComments.getNoOfInternalInterviewers());
			form.setNameOfInternalInterviewer1(interviewComments.getNameOfInternalInterviewer1());
			form.setNameOfInternalInterviewer2(interviewComments.getNameOfInternalInterviewer2());
			form.setNameOfInternalInterviewer3(interviewComments.getNameOfInternalInterviewer3());
			form.setNameOfExternalInterviewer1(interviewComments.getNameOfExternalInterviewer1());
			form.setNameOfExternalInterviewer2(interviewComments.getNameOfExternalInterviewer2());
			form.setNameOfExternalInterviewer3(interviewComments.getNameOfExternalInterviewer3());
			//Added Code
			form.setNameOfInternalInterviewer4(interviewComments.getNameOfInternalInterviewer4());
			form.setNameOfInternalInterviewer5(interviewComments.getNameOfInternalInterviewer5());
			form.setNameOfInternalInterviewer6(interviewComments.getNameOfInternalInterviewer6());
			form.setNameOfExternalInterviewer4(interviewComments.getNameOfExternalInterviewer4());
			form.setNameOfExternalInterviewer5(interviewComments.getNameOfExternalInterviewer5());
			form.setNameOfExternalInterviewer6(interviewComments.getNameOfExternalInterviewer6());
			form.setComments(interviewComments.getComments());
			form.setJoiningTime(interviewComments.getJoiningTime());
		}
		return form;
	}

	/**
	 * @param interviewComments 
	 * @param ratingFactorList
	 * @param interviewCommentsForm
	 * @return
	 */
	public List<InterviewRatingFactorTO> copyRatingBOtoTO(NewInterviewComments interviewComments, List<InterviewRatingFactor> ratingFactorList, NewInterviewCommentsForm interviewCommentsForm) {
		List<InterviewRatingFactorTO> interviewRatingFactorTOs = new ArrayList<InterviewRatingFactorTO>();
		Map<Integer, InterviewRatingFactorTO> map =new HashMap<Integer, InterviewRatingFactorTO>();
		if(interviewComments !=null){
			Set<NewInterviewCommentsDetails> commentsDetails = interviewComments.getNewInterviewCommentsDetails();
			Iterator<NewInterviewCommentsDetails> iterator=commentsDetails.iterator();
			//List<NewInterviewCommentsDetailsTo> newDetailsTo = new ArrayList<NewInterviewCommentsDetailsTo>();
			while (iterator.hasNext()) {
				NewInterviewCommentsDetails newInterviewCommentsDetails = (NewInterviewCommentsDetails) iterator.next();
				InterviewRatingFactorTO factorTO =new InterviewRatingFactorTO();
				factorTO.setId(newInterviewCommentsDetails.getInterviewRatingFactor().getId());
				factorTO.setRatingFactor(newInterviewCommentsDetails.getInterviewRatingFactor().getRatingFactor());
				factorTO.setDisplayOrder(newInterviewCommentsDetails.getInterviewRatingFactor().getDisplayOrder());
				factorTO.setMaxScore(newInterviewCommentsDetails.getInterviewRatingFactor().getMaxScore());
				factorTO.setInterviewCommentsDetailsId(newInterviewCommentsDetails.getId());
				if(newInterviewCommentsDetails.getExternalInterviewer1()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer1().isEmpty()){
						factorTO.setExternalInterviewer1(newInterviewCommentsDetails.getExternalInterviewer1());
					}
				}
				if(newInterviewCommentsDetails.getExternalInterviewer2()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer2().isEmpty()){
						factorTO.setExternalInterviewer2(newInterviewCommentsDetails.getExternalInterviewer2());
					}
				}
				if(newInterviewCommentsDetails.getExternalInterviewer3()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer3().isEmpty()){
						factorTO.setExternalInterviewer3(newInterviewCommentsDetails.getExternalInterviewer3());
					}
				}
				if(newInterviewCommentsDetails.getInternalInterviewer1()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer1().isEmpty()){
						factorTO.setInternalInterviewer1(newInterviewCommentsDetails.getInternalInterviewer1());
					}
				}
				if(newInterviewCommentsDetails.getInternalInterviewer2()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer2().isEmpty()){
						factorTO.setInternalInterviewer2(newInterviewCommentsDetails.getInternalInterviewer2());
				}
				}
				if(newInterviewCommentsDetails.getInternalInterviewer3()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer3().isEmpty()){
						factorTO.setInternalInterviewer3(newInterviewCommentsDetails.getInternalInterviewer3());
					}
				}
				//Added
				if(newInterviewCommentsDetails.getExternalInterviewer4()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer4().isEmpty()){
						factorTO.setExternalInterviewer4(newInterviewCommentsDetails.getExternalInterviewer4());
					}
				}
				if(newInterviewCommentsDetails.getExternalInterviewer5()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer5().isEmpty()){
						factorTO.setExternalInterviewer5(newInterviewCommentsDetails.getExternalInterviewer5());
					}
				}
				if(newInterviewCommentsDetails.getExternalInterviewer6()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer6().isEmpty()){
						factorTO.setExternalInterviewer6(newInterviewCommentsDetails.getExternalInterviewer6());
					}
				}
				if(newInterviewCommentsDetails.getInternalInterviewer4()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer4().isEmpty()){
						factorTO.setInternalInterviewer4(newInterviewCommentsDetails.getInternalInterviewer4());
					}
				}
				if(newInterviewCommentsDetails.getInternalInterviewer5()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer5().isEmpty()){
						factorTO.setInternalInterviewer5(newInterviewCommentsDetails.getInternalInterviewer5());
					}
				}
				if(newInterviewCommentsDetails.getInternalInterviewer6()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer6().isEmpty()){
						factorTO.setInternalInterviewer6(newInterviewCommentsDetails.getInternalInterviewer6());
					}
				}
				//
				map.put(newInterviewCommentsDetails.getInterviewRatingFactor().getId(), factorTO);
			}
		}
		
		
		Iterator<InterviewRatingFactor> iterator = ratingFactorList.iterator();
		while (iterator.hasNext()) {
			
			InterviewRatingFactor interviewRatingFactor = (InterviewRatingFactor) iterator.next();
			if(map.containsKey(interviewRatingFactor.getId())){

				interviewRatingFactorTOs.add(map.get(interviewRatingFactor.getId()));
			}else{
				InterviewRatingFactorTO to1 = new InterviewRatingFactorTO();
				to1.setId(interviewRatingFactor.getId());
				to1.setDisplayOrder(interviewRatingFactor.getDisplayOrder());
				to1.setMaxScore(interviewRatingFactor.getMaxScore());
				to1.setRatingFactor(interviewRatingFactor.getRatingFactor());
				to1.setInternalInterviewer1(null);
				interviewRatingFactorTOs.add(to1);
			}
		}
		return interviewRatingFactorTOs;
	}

	/**
	 * @param onlineResumes
	 * @return
	 */
	public List<NewInterviewCommentsTO> copyInterviewCommentsBOToTO(List<NewInterviewComments> comments) {
		List<NewInterviewCommentsTO> tos= new ArrayList<NewInterviewCommentsTO>();
		if(comments !=null && !comments.isEmpty()){
			Iterator<NewInterviewComments> iterator = comments.iterator();
			while (iterator.hasNext()) {
				NewInterviewComments interviewComments = (NewInterviewComments) iterator.next();
				NewInterviewCommentsTO to = new NewInterviewCommentsTO();
				to.setNameOfExternalInterviewer1(interviewComments.getNameOfExternalInterviewer1());
				to.setNameOfExternalInterviewer2(interviewComments.getNameOfExternalInterviewer2());
				to.setNameOfExternalInterviewer3(interviewComments.getNameOfExternalInterviewer3());
				to.setNameOfInternalInterviewer1(interviewComments.getNameOfInternalInterviewer1());
				to.setNameOfInternalInterviewer2(interviewComments.getNameOfInternalInterviewer2());
				to.setNameOfInternalInterviewer3(interviewComments.getNameOfInternalInterviewer3());
				// Added Code
				to.setNameOfExternalInterviewer4(interviewComments.getNameOfExternalInterviewer4());
				to.setNameOfExternalInterviewer5(interviewComments.getNameOfExternalInterviewer5());
				to.setNameOfExternalInterviewer6(interviewComments.getNameOfExternalInterviewer6());
				to.setNameOfInternalInterviewer4(interviewComments.getNameOfInternalInterviewer4());
				to.setNameOfInternalInterviewer5(interviewComments.getNameOfInternalInterviewer5());
				to.setNameOfInternalInterviewer6(interviewComments.getNameOfInternalInterviewer6());
				to.setJoiningTime(interviewComments.getJoiningTime());
				to.setComments(interviewComments.getComments());
				//
				tos.add(to);
				
			}
		}
		return tos;
	}

	/**
	 * @param commentsDetails
	 * @param interviewCommentsForm 
	 * @return
	 */
	public List<NewInterviewCommentsDetailsTo> copyCommentDetailsBOToTO(List<NewInterviewCommentsDetails> commentsDetails, NewInterviewCommentsForm interviewCommentsForm) {
		List<NewInterviewCommentsDetailsTo> detailsTos = new ArrayList<NewInterviewCommentsDetailsTo>();
		if(commentsDetails !=null && !commentsDetails.isEmpty()){
			Boolean isInternal1=false;
			Boolean isInternal2=false;
			Boolean isInternal3=false;
			Boolean isInternal4=false;
			Boolean isInternal5=false;
			Boolean isInternal6=false;
			Boolean isExternal1=false;
			Boolean isExternal2=false;
			Boolean isExternal3=false;
			Boolean isExternal4=false;
			Boolean isExternal5=false;
			Boolean isExternal6=false;
			
			Iterator<NewInterviewCommentsDetails> iterator = commentsDetails.iterator();
			while (iterator.hasNext()) {
				NewInterviewCommentsDetails newInterviewCommentsDetails = (NewInterviewCommentsDetails) iterator.next();
				NewInterviewCommentsDetailsTo to = new NewInterviewCommentsDetailsTo();
				if(newInterviewCommentsDetails.getInternalInterviewer1()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer1().isEmpty()){
							to.setInternalInterviewer1(newInterviewCommentsDetails.getInternalInterviewer1());
							isInternal1=true;
					}
				}else{
					to.setInternalInterviewer1("0");
				}
				if(newInterviewCommentsDetails.getInternalInterviewer2()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer2().isEmpty()){
							to.setInternalInterviewer2(newInterviewCommentsDetails.getInternalInterviewer2());
						isInternal2=true;
					}
				}
				else{
					to.setInternalInterviewer2("0");
				}
				if(newInterviewCommentsDetails.getInternalInterviewer3()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer3().isEmpty()){
							to.setInternalInterviewer3(newInterviewCommentsDetails.getInternalInterviewer3());
						isInternal3=true;
					}
				}else{
					to.setInternalInterviewer3("0");
				}
				if( newInterviewCommentsDetails.getExternalInterviewer1()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer1().isEmpty()){
							to.setExternalInterviewer1(newInterviewCommentsDetails.getExternalInterviewer1());
						isExternal1=true;
					}
				}
				else{
					to.setExternalInterviewer1("0");
				}
				if( newInterviewCommentsDetails.getExternalInterviewer2()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer2().isEmpty() ){
							to.setExternalInterviewer2(newInterviewCommentsDetails.getExternalInterviewer2());
						isExternal2=true;
					}
				}
				else{
					to.setExternalInterviewer2("0");
				}
				if(newInterviewCommentsDetails.getExternalInterviewer3()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer3().isEmpty()){
							to.setExternalInterviewer3(newInterviewCommentsDetails.getExternalInterviewer3());
						isExternal3=true;
					}
				}
				else{
					to.setExternalInterviewer3("0");
				}
				if(newInterviewCommentsDetails.getInterviewRatingFactor().getRatingFactor()!=null && !newInterviewCommentsDetails.getInterviewRatingFactor().getRatingFactor().isEmpty()){
					to.setRatingFactor(newInterviewCommentsDetails.getInterviewRatingFactor().getRatingFactor());
				}
				if(newInterviewCommentsDetails.getInterviewRatingFactor().getMaxScore()!=null){
					if(newInterviewCommentsDetails.getInterviewRatingFactor().getMaxScore()!=0){
						String str1 = String.valueOf(newInterviewCommentsDetails.getInterviewRatingFactor().getMaxScore());
						to.setMaxScore(str1);
					}
				}else{
					to.setMaxScore("0");
				}
				//added Code
				if(newInterviewCommentsDetails.getInternalInterviewer4()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer4().isEmpty()){
							to.setInternalInterviewer4(newInterviewCommentsDetails.getInternalInterviewer4());
						isInternal4=true;
					}
				}else{
					to.setInternalInterviewer4("0");
				}
				if(newInterviewCommentsDetails.getInternalInterviewer5()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer5().isEmpty()){
							to.setInternalInterviewer5(newInterviewCommentsDetails.getInternalInterviewer5());
						isInternal5=true;
					}
				}else{
					to.setInternalInterviewer5("0");
				}
				if(newInterviewCommentsDetails.getInternalInterviewer6()!=null){
					if(!newInterviewCommentsDetails.getInternalInterviewer6().isEmpty()){
							to.setInternalInterviewer6(newInterviewCommentsDetails.getInternalInterviewer6());
						isInternal6=true;
					}
				}
				else{
					to.setInternalInterviewer6("0");
				}
				if(newInterviewCommentsDetails.getExternalInterviewer4()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer4().isEmpty()){
							to.setExternalInterviewer4(newInterviewCommentsDetails.getExternalInterviewer4());
						isExternal4=true;
					}
				}
				else{
					to.setExternalInterviewer4("0");
				}
				if(newInterviewCommentsDetails.getExternalInterviewer5()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer5().isEmpty()){
							to.setExternalInterviewer5(newInterviewCommentsDetails.getExternalInterviewer5());
						isExternal5=true;
					}
				}
				else{
					to.setExternalInterviewer5("0");
				}
				if(newInterviewCommentsDetails.getExternalInterviewer6()!=null){
					if(!newInterviewCommentsDetails.getExternalInterviewer6().isEmpty()){
							to.setExternalInterviewer6(newInterviewCommentsDetails.getExternalInterviewer6());
						isExternal6=true;
					}
				}
				else{
					to.setExternalInterviewer6("0");
				}
				//
				detailsTos.add(to);
			}
			interviewCommentsForm.setIsInternal1(isInternal1);
			interviewCommentsForm.setIsInternal2(isInternal2);
			interviewCommentsForm.setIsInternal3(isInternal3);
			interviewCommentsForm.setIsInternal4(isInternal4);
			interviewCommentsForm.setIsInternal5(isInternal5);
			interviewCommentsForm.setIsInternal6(isInternal6);
			interviewCommentsForm.setIsExternal1(isExternal1);
			interviewCommentsForm.setIsExternal2(isExternal2);
			interviewCommentsForm.setIsExternal3(isExternal3);
			interviewCommentsForm.setIsExternal4(isExternal4);
			interviewCommentsForm.setIsExternal5(isExternal5);
			interviewCommentsForm.setIsExternal6(isExternal6);
		}
		return detailsTos;
	}

	/**
	 * @param onlineResumes
	 * @return
	 */
	public List<EmpEducationalDetailsTO> copyEmpEduDetailsBoToTO(List<EmpOnlineResume> onlineResume) {
		List<EmpEducationalDetailsTO> list = new ArrayList<EmpEducationalDetailsTO>();
		if(onlineResume!=null && !onlineResume.isEmpty()){
		Iterator<EmpOnlineResume> iterator= onlineResume.iterator();
			while (iterator.hasNext()) {
				EmpOnlineResume empOnlineResume = (EmpOnlineResume) iterator.next();
				if(empOnlineResume.getEducationalDetailsSet() != null && !empOnlineResume.getEducationalDetailsSet().isEmpty()){
				Set<EmpOnlineEducationalDetails> set = empOnlineResume.getEducationalDetailsSet();
				if(set!= null && !set.isEmpty()){
						Iterator<EmpOnlineEducationalDetails> iterator2=set.iterator();
						while (iterator2.hasNext()) {
						EmpOnlineEducationalDetails empOnlineEducationalDetails = (EmpOnlineEducationalDetails) iterator2.next();
						EmpEducationalDetailsTO to = new EmpEducationalDetailsTO();
							if(empOnlineEducationalDetails.getCourse() !=null && !empOnlineEducationalDetails.getCourse().isEmpty()){
								to.setDegree(empOnlineEducationalDetails.getCourse());
							}
							if(empOnlineEducationalDetails.getYearOfCompletion()!=0){
								to.setYearOfPassing(empOnlineEducationalDetails.getYearOfCompletion());
							}
							if(empOnlineEducationalDetails.getGrade()!= null && !empOnlineEducationalDetails.getGrade().isEmpty()){
								to.setMarks(empOnlineEducationalDetails.getGrade());
							}
							if(empOnlineEducationalDetails.getInstitute()!= null && !empOnlineEducationalDetails.getInstitute().isEmpty()){
							to.setUniversity(empOnlineEducationalDetails.getInstitute());
							}
							list.add(to);
							}
					}
				}
			}
		}
		
		return list;
	}

	/**
	 * @param resume
	 * @param interviewCommentsForm 
	 * @return
	 */
	public List<NewInterviewCommentsTO> copyEmpDetailsToTO(Object[] resume, NewInterviewCommentsForm interviewCommentsForm) {
		List<NewInterviewCommentsTO> interviewCommentsTOs=new ArrayList<NewInterviewCommentsTO>();
		NewInterviewCommentsTO commentsTO = new NewInterviewCommentsTO();
		if(resume!= null){
			if(resume[0]!=null){
				if(resume[0].toString() != null && !resume[0].toString().isEmpty()){
					commentsTO.setApplicationNo(resume[0].toString());
				}
			}
			if(resume[1]!=null){
				if(resume[1].toString()!= null && !resume[1].toString().isEmpty()){
					commentsTO.setName(resume[1].toString());
				}
			}
			if(resume[2]!=null){
				if(resume[2].toString()!=null && !resume[2].toString().isEmpty()){
					/*commentsTO.setDepartment(resume[2].toString());
					commentsTO.setTempDeptId(resume[2].toString());*/
					interviewCommentsForm.setDepartment(resume[2].toString());
					interviewCommentsForm.setDepartmentId(resume[2].toString());
				}
			}
			
			interviewCommentsTOs.add(commentsTO);
		}
		return interviewCommentsTOs;
	}

	/**
	 * @param onlineResumes
	 * @return
	 */
	public List<EmpOnlineResumeTO> copyEmpResumeDetailsToTO(List<EmpOnlineResume> onlineResumes) throws Exception {
		List<EmpOnlineResumeTO> list =new ArrayList<EmpOnlineResumeTO>();
		Nationality nationality = new Nationality();
		Country country = new Country();
		if(onlineResumes!=null && !onlineResumes.isEmpty()){
			Iterator<EmpOnlineResume> iterator=onlineResumes.iterator();
			while (iterator.hasNext()) {
				EmpOnlineResume empOnlineResume = (EmpOnlineResume) iterator.next();
				EmpOnlineResumeTO to= new EmpOnlineResumeTO();
				if(empOnlineResume.getName()!=null && !empOnlineResume.getName().isEmpty()){
					to.setName(empOnlineResume.getName());
				}
				if(empOnlineResume.getNationality()!=null){
					nationality.setName(empOnlineResume.getNationality().getName());
					to.setNationality(nationality.getName());
				}
				if(empOnlineResume.getAge()!= null && empOnlineResume.getAge()!=0){
					to.setAge(empOnlineResume.getAge());
				}
				if(empOnlineResume.getGender()!= null && !empOnlineResume.getGender().isEmpty()){
					to.setGender(empOnlineResume.getGender());
				}
				if(empOnlineResume.getDateOfBirth()!= null){
					to.setDateofBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil
							.getStringDate(empOnlineResume.getDateOfBirth()), "dd-MMM-yyyy",
					"dd/MM/yyyy"));
				}
				if(empOnlineResume.getMaritalStatus()!= null && !empOnlineResume.getMaritalStatus().isEmpty()){
					to.setMaritalStatus(empOnlineResume.getMaritalStatus());
				}
				if(empOnlineResume.getCountry()!=null){
					if(empOnlineResume.getCountry().getName()!= null && !empOnlineResume.getCountry().getName().isEmpty()){
						country.setName(empOnlineResume.getCountry().getName());
						to.setCountry(country.getName());
					}
				}
				if(empOnlineResume.getCurrentZipCode()!= null && !empOnlineResume.getCurrentZipCode().isEmpty()){
					to.setZipCode(empOnlineResume.getCurrentZipCode());
				}
				if(empOnlineResume.getEmail()!= null && !empOnlineResume.getEmail().isEmpty()){
					to.setEmail(empOnlineResume.getEmail());
				}
				String str= "";
				if(empOnlineResume.getAddressLine1()!=null){
					if(!empOnlineResume.getAddressLine1().isEmpty()){
						String str1 = empOnlineResume.getAddressLine1();
						str=str+str1+",";
					}
				}
				if(empOnlineResume.getAddressLine2()!=null){
					if(!empOnlineResume.getAddressLine2().isEmpty()){
						String str2 = empOnlineResume.getAddressLine2();
						str=str+str2+",";
					}
				}
				if(empOnlineResume.getAddressLine3()!=null){
					if(!empOnlineResume.getAddressLine3().isEmpty()){
						String str3= empOnlineResume.getAddressLine3();
						str=str+str3+",";
					}
				}
				to.setAddress(str);
				
				String str1 = "";
				if(empOnlineResume.getPhNo1()!= null){
					String str5=empOnlineResume.getPhNo1();
					str1=str1+str5;
				}
				if(empOnlineResume.getPhNo2()!= null){
					String str6=empOnlineResume.getPhNo2();
					str1=str1+"-"+str6;
				}
				if(empOnlineResume.getPhNo3()!= null){
					String str7=empOnlineResume.getPhNo3();
					str1=str1+"-"+str7;
				}
				to.setPhone(str1);
				if(empOnlineResume.getMobileNo1()!=null && !empOnlineResume.getMobileNo1().isEmpty()){
					to.setMobileNo(empOnlineResume.getMobileNo1());
				}
		
				if(empOnlineResume.getCurrentCity()!= null && !empOnlineResume.getCurrentCity().isEmpty()){
					to.setCity(empOnlineResume.getCity());
				}
			
				if(empOnlineResume.getEmploymentStatus()!=null && !empOnlineResume.getEmploymentStatus().isEmpty()){
					to.setEmploymentStatus(empOnlineResume.getEmploymentStatus());
			    }
//				if(empOnlineResume.getRecommendedBy()!= null && ! empOnlineResume.getRecommendedBy().isEmpty()){
//					to.setRecommendedBy(empOnlineResume.getRecommendedBy());
//				}
				if(empOnlineResume.getDepartment()!=null){
					if(empOnlineResume.getDepartment().getName()!=null && !empOnlineResume.getDepartment().getName().isEmpty()){
						to.setDepartment(empOnlineResume.getDepartment().getName());
					}
				}
//				if(empOnlineResume.getDateOfJoining()!=null){
//					to.setDateOfJoining(CommonUtil.ConvertStringToDateFormat(CommonUtil
//							.getStringDate(empOnlineResume.getDateOfJoining()), "dd-MMM-yyyy",
//					"dd/MM/yyyy"));
//				}
				if(empOnlineResume.getDesiredPost()!=null && !empOnlineResume.getDesiredPost().isEmpty()){
					to.setEmpDesiredPost(empOnlineResume.getDesiredPost());
				}
				if(empOnlineResume.getEmpJobType()!=null && !empOnlineResume.getEmpJobType().isEmpty()){
					to.setEmpJobType(empOnlineResume.getEmpJobType());
				}
				if(empOnlineResume.getExpectedSalaryLakhs() != null && empOnlineResume.getExpectedSalaryThousands() != null){
					String expectedSal = "";
					expectedSal = expectedSal + String.valueOf(empOnlineResume.getExpectedSalaryLakhs()) +" Lack(s) " + empOnlineResume.getExpectedSalaryThousands() + " Thousand(s)";
					to.setExpectedSalary(expectedSal);
				}
//				if(empOnlineResume.getInformationKnown()!=null && !empOnlineResume.getInformationKnown().isEmpty()){
//					to.setInformationKnown(empOnlineResume.getInformationKnown());
//				}
				if(empOnlineResume.getEmpQualificationLevel()!=null){
					if(empOnlineResume.getEmpQualificationLevel().getName()!=null && !empOnlineResume.getEmpQualificationLevel().getName().isEmpty()){
						to.setQualificationName(empOnlineResume.getEmpQualificationLevel().getName());
					}
				}
				if(empOnlineResume.getCurrentDesignation()!=null && !empOnlineResume.getCurrentDesignation().isEmpty()){
					to.setCurrentDesignation(empOnlineResume.getCurrentDesignation());
				}
				if(empOnlineResume.getCurrentOrganization()!=null && !empOnlineResume.getCurrentOrganization().isEmpty()){
					to.setCurrentOrganization(empOnlineResume.getCurrentOrganization());
				}
				if(empOnlineResume.isCurrentlyWorking()){
					to.setCurrentlyWorking("Yes");
				}else{
					to.setCurrentlyWorking("No");
				}
				if(empOnlineResume.getTotalExpYear() != null && empOnlineResume.getTotalExpMonths() != null){
					String totalExp = empOnlineResume.getTotalExpYear() + " Year(s) " + empOnlineResume.getTotalExpMonths() +" Month(s)";
					to.setTotExp(totalExp);
				}
				if(empOnlineResume.getPreviousExpSet()!=null){
					Set<EmpOnlinePreviousExperience> previousExperiences=empOnlineResume.getPreviousExpSet();
					Iterator<EmpOnlinePreviousExperience> iterator2=previousExperiences.iterator();
					while (iterator2.hasNext()) {
						EmpOnlinePreviousExperience empOnlinePreviousExperience = (EmpOnlinePreviousExperience) iterator2.next();
						if(empOnlinePreviousExperience.isTeachingExperience()){
							String teachingExp="";
							teachingExp=teachingExp + String.valueOf(empOnlinePreviousExperience.getExpYears() + " Year(s) " + empOnlinePreviousExperience.getExpMonths()+" Month(s)");
							to.setTeachingExperience(teachingExp);
						}
						if(empOnlinePreviousExperience.isIndustryExperience()){
							String industryExp="";
							industryExp=industryExp + String.valueOf(empOnlinePreviousExperience.getExpYears() + " Year(s) " + empOnlinePreviousExperience.getExpMonths()+" Month(s)");
							to.setIndustryExperience(industryExp);
						}
						if(empOnlinePreviousExperience.getEmpOrganization()!=null && !empOnlinePreviousExperience.getEmpOrganization().isEmpty()){
							to.setEmpPreviousOrg(empOnlinePreviousExperience.getEmpOrganization());
						}
					}
				}
				list.add(to);
			}
		}
		
		return list;
	}

	public NewInterviewCommentsForm copyLogoFromBoToTO(NewInterviewCommentsForm newInterviewCommentsForm, HttpServletRequest request)throws Exception {
		
		byte[] logo = null;
		byte[] logo1 = null;
		
		HttpSession session = request.getSession(false);
		Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
		if (organisation != null) {
			logo = organisation.getLogo();
			logo1 = organisation.getLogo1();
		}
		
		if (session != null) {
			session.setAttribute("LogoBytes", logo);
			session.setAttribute("LogoBytes1", logo1);
		}
		
		
		
		//newInterviewCommentsForm.setLogo(logo);
		
		return newInterviewCommentsForm;
	}

	public Map<Integer, Integer> copyNoOfInterviewersToTO(Object[] objects)throws Exception {
		Map<Integer, Integer> map=new HashMap<Integer, Integer>();
		if(objects!=null){
			map.put(Integer.parseInt(objects[0].toString()), Integer.parseInt(objects[1].toString()));
			map.put(0, 0);
			map.put(1, 1);
			map.put(2, 2);
			map.put(3, 3);
			map.put(4, 4);
			map.put(5, 5);
			map.put(6, 6);
		}else{
			map.put(0, 0);
			map.put(1, 1);
			map.put(2, 2);
			map.put(3, 3);
			map.put(4, 4);
			map.put(5, 5);
			map.put(6, 6);
		}
		
		return map;
	}

	/**
	 * @param onlineResumes
	 * @return
	 */
	public List<EmpOnlineResumeTO> copyEmpInfoBOToTO(List<EmpOnlineResume> onlineResumes) {
		List<EmpOnlineResumeTO> list=new ArrayList<EmpOnlineResumeTO>();
		if(onlineResumes!=null){
			Iterator<EmpOnlineResume> iterator=onlineResumes.iterator();
			while (iterator.hasNext()) {
				EmpOnlineResume empOnlineResume = (EmpOnlineResume) iterator.next();
				EmpOnlineResumeTO to=new EmpOnlineResumeTO();
				if(empOnlineResume.getName()!=null){
					if(!empOnlineResume.getName().isEmpty()){
						to.setName(empOnlineResume.getName());
					}
				}
				if(empOnlineResume.getDateOfBirth()!=null){
					to.setDateofBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil
							.getStringDate(empOnlineResume.getDateOfBirth()), "dd-MMM-yyyy",
					"dd/MM/yyyy"));
				}
//				if(empOnlineResume.getAge()!=null && empOnlineResume.getAge()!=0){
//					to.setAge(empOnlineResume.getAge());
//				}
				if(empOnlineResume.getApplicationNo()!=null){
					if(!empOnlineResume.getApplicationNo().isEmpty()){
						to.setApplnNo(empOnlineResume.getApplicationNo());
					}
				}
				if(empOnlineResume.getDateOfSubmission() != null ){
					String subDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResume.getDateOfSubmission()), NewInterviewCommentsHelper.SQL_DATEFORMAT,NewInterviewCommentsHelper.FROM_DATEFORMAT);
					to.setAppliedDate(subDate);
				}
				if(empOnlineResume.getGender()!=null && !empOnlineResume.getGender().isEmpty()){
					to.setGender(empOnlineResume.getGender());
				}
				
				if(empOnlineResume.getDepartment()!=null){
					if(empOnlineResume.getDepartment().getName()!=null && !empOnlineResume.getDepartment().getName().isEmpty()){
						to.setDepartment(empOnlineResume.getDepartment().getName());
					}
				}
					
					String expectedSalaryLakhs = "00000";
					String expectedSalatyThousands = "000";
					int total;
					int expectedSalaryPerMonth;
					
					if(empOnlineResume.getExpectedSalaryLakhs()!=null && !empOnlineResume.getExpectedSalaryLakhs().toString().isEmpty()){
						expectedSalaryLakhs =String.valueOf(empOnlineResume.getExpectedSalaryLakhs()) + expectedSalaryLakhs;
					}
					if(empOnlineResume.getExpectedSalaryThousands()!=null && !empOnlineResume.getExpectedSalaryThousands().toString().isEmpty()){
						expectedSalatyThousands = String.valueOf(empOnlineResume.getExpectedSalaryThousands()) + expectedSalatyThousands;
					}
					total=Integer.parseInt(expectedSalaryLakhs) + Integer.parseInt(expectedSalatyThousands);
					expectedSalaryPerMonth=total/12;
					if(expectedSalaryPerMonth!=0){
						to.setExpectedSalaryPerMonth(expectedSalaryPerMonth);
					}else{
						to.setExpectedSalaryPerMonth(null);
					}
				// added newly
					if(!empOnlineResume.getEligibilityTest().equalsIgnoreCase("None")){
						String eligibilityTest = "";
						if(empOnlineResume.getEligibilityTest().contains("OTHER")){
							if(empOnlineResume.getEligibilityTestOther()!=null && !empOnlineResume.getEligibilityTestOther().isEmpty()){
								if(empOnlineResume.getEligibilityTest().contains("None")){
									eligibilityTest = empOnlineResume.getEligibilityTest().replace("None,", "");
								}
							eligibilityTest = eligibilityTest+ empOnlineResume.getEligibilityTest().replace("OTHER", empOnlineResume.getEligibilityTestOther());
							}else{
								eligibilityTest = empOnlineResume.getEligibilityTest().replace(",OTHER", "");
							}
						}else{
							if(empOnlineResume.getEligibilityTest().contains("None")){
								eligibilityTest = empOnlineResume.getEligibilityTest().replace("None,", "");
							}else{
								eligibilityTest = empOnlineResume.getEligibilityTest();
							}
							
						}
						to.setEligibilityTest(eligibilityTest);
					}else{
						to.setEligibilityTest(null);
					}
				list.add(to);
			}
			
		}
			
		return list;
	}

	/**
	 * @param onlineResumes
	 * @return
	 */
	public List<EmpAcheivementTO> copyEmpAcheivementDetailsToTO(List<EmpOnlineResume> onlineResumes) {
		List<EmpAcheivementTO> list=new ArrayList<EmpAcheivementTO>();
		if(onlineResumes!=null && !onlineResumes.isEmpty()){
			Iterator<EmpOnlineResume> iterator= onlineResumes.iterator();
			while (iterator.hasNext()) {
				EmpOnlineResume empOnlineResume = (EmpOnlineResume) iterator.next();
				if(empOnlineResume!=null){
					if(empOnlineResume.getAcheivementSet()!=null && !empOnlineResume.getAcheivementSet().isEmpty()){
						Set<EmpAcheivement> set=empOnlineResume.getAcheivementSet();
						if(set!=null && !set.isEmpty()){
							Iterator<EmpAcheivement> iterator2=set.iterator();
							while (iterator2.hasNext()) {
								EmpAcheivement empAcheivement = (EmpAcheivement) iterator2.next();
								EmpAcheivementTO to=new EmpAcheivementTO();
								if(empAcheivement.getAcheivementName()!=null && !empAcheivement.getAcheivementName().isEmpty()){
									to.setAcheivementName(empAcheivement.getAcheivementName());
								}
								if(empAcheivement.getDetails()!=null && !empAcheivement.getDetails().isEmpty()){
									to.setDetails(empAcheivement.getDetails());
								}
								list.add(to);
							}
						}
					}
				}
			}
		}
		return list;
	}

	public List<EmpEducationalDetailsTO> copyEmpOnlineEducationalBoTOTo( List<Object[]> onlineResumes) {
		List<EmpEducationalDetailsTO> eduDetailsList = new ArrayList<EmpEducationalDetailsTO>();
		if(onlineResumes!=null && !onlineResumes.isEmpty()){
			Iterator<Object[]> iterator = onlineResumes.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				EmpEducationalDetailsTO to = new EmpEducationalDetailsTO();
				if(objects[0]!=null){
					if(objects[4]!=null){
						if(!objects[0].toString().isEmpty()){
							to.setDegree(objects[4].toString()+" - "+objects[0].toString());
						}
					}else{
						if(!objects[0].toString().isEmpty()){
							to.setDegree(objects[0].toString());
						}
					}
				}
				if(objects[1]!=null){
					if(!objects[1].toString().isEmpty()){
						to.setYearOfPassing(Integer.parseInt(objects[1].toString()));
					}
				}
				if(objects[2]!=null){
					if(!objects[2].toString().isEmpty()){
						to.setMarks(objects[2].toString());
					}
				}
				if(objects[3]!=null){
					if(!objects[3].toString().isEmpty()){
						to.setUniversity(objects[3].toString());
					}
				}
				eduDetailsList.add(to);
			}
		}
		return eduDetailsList;
	}
}
