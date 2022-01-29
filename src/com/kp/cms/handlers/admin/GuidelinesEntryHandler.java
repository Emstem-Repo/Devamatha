package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import com.kp.cms.bo.admin.GuidelinesDoc;
import com.kp.cms.forms.admin.GuidelinesEntryForm;
import com.kp.cms.helpers.admin.GuidelinesEntryHelper;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.GuidelinesEntryTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.transactions.admin.IGuidelinesEntryTransaction;
import com.kp.cms.transactionsimpl.admin.GuidelinesEntryTransactionImpl;


public class GuidelinesEntryHandler {
	
	private static final Log log = LogFactory.getLog(GuidelinesEntryHandler.class);
	
	private static volatile GuidelinesEntryHandler guidelinesEntryHandler = null;

	private GuidelinesEntryHandler() {
	}
	/**
	 * 
	 * @returns a single instance when called (Singleton implementation)
	 */
	public static GuidelinesEntryHandler getInstance() {
		if (guidelinesEntryHandler == null) {
			guidelinesEntryHandler = new GuidelinesEntryHandler();
		}
		return guidelinesEntryHandler;
	}
	
	IGuidelinesEntryTransaction transaction= new GuidelinesEntryTransactionImpl();
	
	public boolean isGuidelinesExist(int courseID, int year) throws Exception {
		log.info("Inside of handler of isGuidelinesExist");
		if (transaction != null) {
			return transaction.isGuidelinesExist(courseID,year);
		}
		return false;
	}
	public GuidelinesDoc getGuidelinesEntry(int courseID, int year) throws Exception {
		log.info("Inside of handler of getGuidelinesEntry");
		GuidelinesDoc docBO=null;
		if (transaction != null) {
			docBO = transaction.getGuidelinesEntry(courseID,year);
		}
		return docBO;
	}
	/**
	 * 
	 * @return Gets all the records of guidelines entry where isActive=1
	 * @throws Exception
	 */
	public List<GuidelinesEntryTO> getGuidelinesEntryAll() throws Exception
	{
		log.info("Inside of handler of getGuidelinesEntryAll");
		List<GuidelinesDoc> guideLines=transaction.getGuidelinesEntryAll();
		return GuidelinesEntryHelper.getInstance().populateGuideLinesBOtoTO(guideLines);		
	}
	
	/**
	 * 
	 * @param guidelinesEntryForm
	 * @return Adds guidelines entry. Populates TO object by capturing the data from formbean
	 * @throws Exception
	 */
	
	public boolean addGuidelinesEntry(GuidelinesEntryForm guidelinesEntryForm) throws Exception{
		GuidelinesEntryTO guidelinesEntryTO=new GuidelinesEntryTO();
		//Get the formbean properties and set those to TO
		CourseTO courseTO=new CourseTO();
		courseTO.setId(Integer.parseInt(guidelinesEntryForm.getCourse()));
		guidelinesEntryTO.setCourseTO(courseTO);
		guidelinesEntryTO.setYear(Integer.parseInt(guidelinesEntryForm.getYear()));
		guidelinesEntryTO.setFileName(guidelinesEntryForm.getThefile().getFileName());
		guidelinesEntryTO.setThefile(guidelinesEntryForm.getThefile());
		guidelinesEntryTO.setCreatedBy(guidelinesEntryForm.getUserId());
		guidelinesEntryTO.setModifiedBy(guidelinesEntryForm.getUserId());
		//Pass the TO to helper class and construct a Guidelines Entry BO
		GuidelinesDoc guidelinesDoc=GuidelinesEntryHelper.getInstance().populateTOtoBO(guidelinesEntryTO);
		if (transaction != null) {
			return transaction.addGuidelinesEntry(guidelinesDoc);
		}
		log.info("Leaving from handler of addGuidelinesEntry");
		return false;
	}
	
	/**
	 * 
	 * @param id
	 * @return Deletes a guidelines entry
	 * @throws Exception
	 */
	public boolean deleteGuidelinesEntry(int id, String userId)throws Exception {
		log.info("Entering into Guidelines handler of deleteGuidelinesEntry");
		if(transaction!=null)
		{
		return transaction.deleteGuidelinesEntry(id, userId);
		}
		return false;
	}
	/*
	 * Checking for the duplicate record if it exists for the course and year
	 */
	public GuidelinesDoc isGuidelinesExistCourseYear(int courseId, int year)throws Exception
	{
		log.info("Entering into Guidelines Entry handler of isGuidelinesExistCourseYear");
		return transaction.isGuidelinesExistCourseYear(courseId, year);
	}
	
	/**
	 * 
	 * @param guidelinesEntryForm
	 * @return Updates the record captured updated data from UI
	 */
	public boolean updateGuidelinesEntryFormToTO(GuidelinesEntryForm guidelinesEntryForm) throws Exception
	{
		log.info("Entering into handler of updateGuidelinesEntryFormToTO");
		GuidelinesEntryTO guidelinesEntryTO=new GuidelinesEntryTO();
		//Get the formbean properties and set those to TO
		CourseTO courseTO=new CourseTO();
		courseTO.setId(Integer.parseInt(guidelinesEntryForm.getCourse()));
		guidelinesEntryTO.setCourseTO(courseTO);
		guidelinesEntryTO.setId(guidelinesEntryForm.getId());
		guidelinesEntryTO.setYear(Integer.parseInt(guidelinesEntryForm.getYear()));
		guidelinesEntryTO.setModifiedBy(guidelinesEntryForm.getUserId());
		if(guidelinesEntryForm.getThefile().getFileName()!=null && !guidelinesEntryForm.getThefile().getFileName().isEmpty() 
			&& guidelinesEntryForm.getThefile() !=null ){
		guidelinesEntryTO.setFileName(guidelinesEntryForm.getThefile().getFileName());
		guidelinesEntryTO.setThefile(guidelinesEntryForm.getThefile());
		}		
		//Pass the TO to helper class and construct a Guidelines Entry BO
		GuidelinesDoc guidelinesDoc=GuidelinesEntryHelper.getInstance().populateTOtoBOUpdate(guidelinesEntryTO);
		boolean isGuidelineEdited = false;
		if (transaction != null) {
			isGuidelineEdited = transaction.updateGuidelinesEntry(guidelinesDoc);
		}
		log.info("Leaving from handler of updateGuidelinesEntryFormToTO");
		return isGuidelineEdited;
	}
	/**
	 * 
	 * @param id
	 * @return Dowloading the document based on id
	 * @throws Exception
	 */
	
	public GuidelinesDoc getGuidelinesEntryonId(int id) throws Exception {
		GuidelinesDoc docBO=null;
		if (transaction != null) {
			docBO = transaction.getGuidelinesEntryonId(id);
		}
		log.info("Leaving from handler of getGuidelinesEntryonId");
		return docBO;
	}
	/**
	 * This method retrieves a record based on ID
	 */
	
	public GuidelinesEntryTO getGuidelinesbyId(int guidelinesId)throws Exception
	{
		GuidelinesEntryTO guidelinesEntryTO=null;
		GuidelinesDoc doc=transaction.getFileonId(guidelinesId);
		if(doc!=null){
			guidelinesEntryTO = new GuidelinesEntryTO();
			guidelinesEntryTO.setYear(doc.getYear());
			CourseTO courseTO=new CourseTO();
			courseTO.setId(doc.getCourse().getId());
			courseTO.setName(doc.getCourse().getName());
			ProgramTO programTO=new ProgramTO();
			programTO.setId(doc.getCourse().getProgram().getId());
			programTO.setName(doc.getCourse().getProgram().getName());
			ProgramTypeTO programTypeTO=new ProgramTypeTO();
			programTypeTO.setProgramTypeId(doc.getCourse().getProgram().getProgramType().getId());
			programTypeTO.setProgramTypeName(doc.getCourse().getProgram().getProgramType().getName());
			programTO.setProgramTypeTo(programTypeTO);
			courseTO.setProgramTo(programTO);
			guidelinesEntryTO.setCourseTO(courseTO);
			}
		log.info("Leaving from handler of getGuidelinesbyId");
		return guidelinesEntryTO;
	}
	/**
	 * 
	 * @param courseId
	 * @param year
	 * @return Reactivates guidelinesEntry based on courseId and year
	 * @throws Exception
	 */
	public boolean reActivateGuidelinesEntry(int courseId, int year, String userId) throws Exception
	{
		if(transaction!=null){
			return transaction.reActivateGuidelinesEntry(courseId, year, userId);
		}
		log.info("Leaving from Guidelines Entry handler of reActivateGuidelinesEntry");
		return false;
	}
}
