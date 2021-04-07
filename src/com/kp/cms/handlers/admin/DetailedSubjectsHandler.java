package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DetailedSubjectsForm;
import com.kp.cms.helpers.admin.DetailedSubjectsHelper;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.transactions.admin.IDetailedSubjectsTransaction;
import com.kp.cms.transactionsimpl.admin.DetailedSubjectsTransImpl;


public class DetailedSubjectsHandler {
	private static volatile DetailedSubjectsHandler detailedSubjectsHandler= null;
	private static Log log = LogFactory.getLog(DetailedSubjectsHandler.class);
	
	/*
	 * return the single instance of DetailedSubjectHandler.
	 */
	public static DetailedSubjectsHandler getInstance() {
	      if(detailedSubjectsHandler == null) {
	    	  detailedSubjectsHandler = new DetailedSubjectsHandler();
	    	  return detailedSubjectsHandler;
	      }
	      return detailedSubjectsHandler;
	}
	
	/**
	 * 
	 * @return lists of DetailedSubjectsTO used in UI to display. 
	 * @throws Exception
	 */
	public List<DetailedSubjectsTO> getDetailedsubjects() throws Exception{
		log.debug("Handler : Entering getAllDetailedsubjects ");
		IDetailedSubjectsTransaction detailedSubjectsTransaction= DetailedSubjectsTransImpl.getInstance();
        List<DetailedSubjects> detailedSubjects = detailedSubjectsTransaction.getAllDetailedSubjects();
		List<DetailedSubjectsTO> detailedSubjectsTos = DetailedSubjectsHelper.getInstance().copyBosToTos(detailedSubjects);
		log.debug("Handler : Leaving getAllDetailedsubjects ");
		return detailedSubjectsTos;
	}
	
	/**
	 * 
	 * @param detailedSubjectForm
	 * @return
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	public boolean addDetailedSubjects(DetailedSubjectsForm detailedSubjectForm) throws ConstraintViolationException,Exception {
		log.debug("Handler : Entering saveFeeAssignmentAccount ");
		DetailedSubjects detailedSubjects = DetailedSubjectsHelper.getInstance().copyFormDataToBo(detailedSubjectForm);
		IDetailedSubjectsTransaction detailedSubjectsTransaction= DetailedSubjectsTransImpl.getInstance();
	    boolean isAdded = false;
		isAdded = detailedSubjectsTransaction.addDetailedSubjects(detailedSubjects);
		log.debug("Handler : Leaving saveFeeAssignmentAccount ");
	return isAdded;
	}
	
	/**
	 * 
	 * @param course
	 * @return list of DetailedsubjectsTo for particular course.
	 * @throws Exception
	 */
	public List<DetailedSubjectsTO> getDetailedsubjectsByCourse(String course) throws Exception{
		log.debug("Handler : Entering getAllDetailedsubjectsByCourse ");
		int courseId = Integer.parseInt(course);
		IDetailedSubjectsTransaction detailedSubjectsTransaction= DetailedSubjectsTransImpl.getInstance();
        List<DetailedSubjects> detailedSubjects = detailedSubjectsTransaction.getDetailedsubjectsByCourse(courseId);
		List<DetailedSubjectsTO> detailedSubjectsTos = DetailedSubjectsHelper.getInstance().copyBosToTos(detailedSubjects);
		log.debug("Handler : Leaving getAllDetailedsubjectsByCourse ");
		return detailedSubjectsTos;
	}
	
	/**
	 * 
	 * @param detailedSubjectsForm
	 * @return true if deleting of particular subject. 
	 * @throws Exception
	 */
	public boolean deleteDetailedSubject(DetailedSubjectsForm detailedSubjectsForm) throws Exception {
		log.debug("Handler : Entering deleteDetailedSubject ");
		DetailedSubjects detailedSubjects = new DetailedSubjects();
		detailedSubjects.setId(Integer.parseInt(detailedSubjectsForm.getId())); 
		detailedSubjects.setModifiedBy(detailedSubjectsForm.getUserId());
		detailedSubjects.setLastModifiedDate(new Date());
		IDetailedSubjectsTransaction detailedSubjectsTransaction =  DetailedSubjectsTransImpl.getInstance();
		boolean isDeleted = false;
		isDeleted = detailedSubjectsTransaction.deleteDetailedSubjects(detailedSubjects);
		log.debug("Handler : Leaving deleteDetailedSubject ");
	return isDeleted;
	}
	
	/**
	 * 
	 * @param detailedSubjectForm
	 * @return return true if update success.
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	public boolean updateDetailedSubjects(DetailedSubjectsForm detailedSubjectForm) throws ConstraintViolationException,Exception {
		log.debug("Handler : Entering updateDetailedSubjects ");
		DetailedSubjects detailedSubjects = DetailedSubjectsHelper.getInstance().copyFormDataToBo(detailedSubjectForm);
		IDetailedSubjectsTransaction detailedSubjectsTransaction= DetailedSubjectsTransImpl.getInstance();
	    boolean isAdded = false;
		isAdded = detailedSubjectsTransaction.updateDetailedSubjects(detailedSubjects);
		log.debug("Handler : Leaving updateDetailedSubjects ");
	return isAdded;
	}
	
	/**
	 * 
	 * @param detailedSubjectsForm
	 * @return true if activation success.
	 * @throws Exception
	 */
	public boolean activateDetailedSubject(DetailedSubjectsForm detailedSubjectsForm) throws Exception {
		log.debug("Handler : Entering activateDetailedSubject ");
		DetailedSubjects detailedSubjects = new DetailedSubjects();
		detailedSubjects.setId(Integer.parseInt(detailedSubjectsForm.getActivationId())); 
		IDetailedSubjectsTransaction detailedSubjectsTransaction =  DetailedSubjectsTransImpl.getInstance();
		boolean isActivated = false;
		isActivated = detailedSubjectsTransaction.actiavateDetailedSubjects(detailedSubjects);
		log.debug("Handler : Leaving activateDetailedSubject ");
	return isActivated;
	}
	
	/**
	 * 
	 * @param detailedSubjectsForm
	 * @return true if duplicate found.
	 * @throws DuplicateException
	 * @throws ReActivateException
	 * @throws Exception
	 */
	public boolean checkForDuplicate(DetailedSubjectsForm detailedSubjectsForm) throws DuplicateException,ReActivateException,Exception{
		log.debug("Handler : Entering checkForDuplicate ");
		IDetailedSubjectsTransaction detailedSubjectsTransaction= DetailedSubjectsTransImpl.getInstance();
        List<DetailedSubjects> detailedSubjects = detailedSubjectsTransaction.getDetailedsubjectsByCourseAndName(Integer.parseInt(detailedSubjectsForm.getCourse()),detailedSubjectsForm.getSubjectName());
		if(!detailedSubjects.isEmpty()) {
	        DetailedSubjects detailedSubject = detailedSubjects.get(0);
	        if(detailedSubject.getIsActive() == true) {
	        	throw new DuplicateException();
	        } else if(detailedSubject.getIsActive() == false) {
	        	detailedSubjectsForm.setActivationId(String.valueOf(detailedSubject.getId()));
	        	throw new ReActivateException();
	        }
		}
        log.debug("Handler : Leaving checkForDuplicate ");
		return true;
	}
	
	/**
	 * This method used to precheck wheather duplicate check required.
	 * @param detailedSubjectsForm
	 * @return
	 * @throws Exception
	 */
	public boolean isDuplicateCheckRequired(DetailedSubjectsForm detailedSubjectsForm) throws Exception {
		if(detailedSubjectsForm.getCourse().equals(detailedSubjectsForm.getOldCourseId()) && 
				detailedSubjectsForm.getSubjectName().equals(detailedSubjectsForm.getOldSubjectName())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * This method loads the details to form
	 * @param detailedSubjectForm
	 * @throws Exception
	 */
	public void getDetailedSubjects(DetailedSubjectsForm detailedSubjectForm) throws Exception {
		log.debug("Handler : Entering getDetailedSubjects ");
		IDetailedSubjectsTransaction detailedSubjectsTransaction= DetailedSubjectsTransImpl.getInstance();
	    DetailedSubjects detailedSubjects = detailedSubjectsTransaction.getDetailedSubjectsId(Integer.parseInt(detailedSubjectForm.getId()));
	    detailedSubjectForm.setId(String.valueOf(detailedSubjects.getId()));
	    detailedSubjectForm.setProgramType(String.valueOf(detailedSubjects.getCourse().getProgram().getProgramType().getId()));
	    detailedSubjectForm.setProgram(String.valueOf(detailedSubjects.getCourse().getProgram().getId()));
	    detailedSubjectForm.setCourse(String.valueOf(detailedSubjects.getCourse().getId()));
	    detailedSubjectForm.setSubjectName(detailedSubjects.getSubjectName());
	    detailedSubjectForm.setOldCourseId(String.valueOf(detailedSubjects.getCourse().getId()));
	    detailedSubjectForm.setOldSubjectName(detailedSubjects.getSubjectName());
	    log.debug("Handler : Leaving getDetailedSubjects ");
	return ;
	}
}
