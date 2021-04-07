package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.GuidelinesDoc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.GuidelinesEntryTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.transactions.admin.IGuidelinesEntryTransaction;
import com.kp.cms.transactionsimpl.admin.GuidelinesEntryTransactionImpl;


public class GuidelinesEntryHelper {
	private static final Log log = LogFactory.getLog(GuidelinesEntryHelper.class);
	private static volatile GuidelinesEntryHelper guidelinesEntryHelper = null;
	
	private GuidelinesEntryHelper() {
	}

	public static GuidelinesEntryHelper getInstance() {
		if (guidelinesEntryHelper == null) {
			guidelinesEntryHelper = new GuidelinesEntryHelper();
		}
		return guidelinesEntryHelper;
	}

	/**
	 * 
	 * @param Populates TO object from BO 
	 * @return
	 */
	
	public List<GuidelinesEntryTO> populateGuideLinesBOtoTO(List<GuidelinesDoc> guideLines)throws Exception
	{
		List<GuidelinesEntryTO> guideLinesDetails=new ArrayList<GuidelinesEntryTO>();
		if(guideLines!=null){		
		Iterator<GuidelinesDoc> iterator=guideLines.iterator();
		while (iterator.hasNext()) {
			GuidelinesDoc guidelinesDoc = iterator.next();
			GuidelinesEntryTO guidelinesEntryTO=new GuidelinesEntryTO();
			guidelinesEntryTO.setId(guidelinesDoc.getId());
			guidelinesEntryTO.setYear(guidelinesDoc.getYear());
			guidelinesEntryTO.setFileName(guidelinesDoc.getFileName());
			
			CourseTO courseTO=new CourseTO();
			courseTO.setId(guidelinesDoc.getCourse().getId());
			courseTO.setName(guidelinesDoc.getCourse().getName());
			ProgramTO programTO=new ProgramTO();
			programTO.setId(guidelinesDoc.getCourse().getProgram().getId());
			programTO.setName(guidelinesDoc.getCourse().getProgram().getName());
			ProgramTypeTO programTypeTO=new ProgramTypeTO();
			programTypeTO.setProgramTypeId(guidelinesDoc.getCourse().getProgram().getProgramType().getId());
			programTypeTO.setProgramTypeName(guidelinesDoc.getCourse().getProgram().getProgramType().getName());
			programTO.setProgramTypeTo(programTypeTO);
			courseTO.setProgramTo(programTO);
			guidelinesEntryTO.setCourseTO(courseTO);
			guideLinesDetails.add(guidelinesEntryTO);
		}
		}
		log.info("Leaving from guidelines helper populateGuideLinesBOtoTO");
		return guideLinesDetails;
	}
	
	/**
	 * For add puprpose
	 * @param guidelinesEntryTO
	 * @return Constructs BO object capturing the data from TO
	 */
	
	public GuidelinesDoc populateTOtoBO(GuidelinesEntryTO guidelinesEntryTO)throws Exception
	{
		GuidelinesDoc guidelinesDoc=null;
		if(guidelinesEntryTO!=null)
		{
			try{
				guidelinesDoc = new GuidelinesDoc();
				 guidelinesDoc.setYear(guidelinesEntryTO.getYear());
				 guidelinesDoc.setFileName(guidelinesEntryTO.getThefile().getFileName());
				 guidelinesDoc.setContentType(guidelinesEntryTO.getThefile().getContentType());
				 guidelinesDoc.setDoc(guidelinesEntryTO.getThefile().getFileData());
				 guidelinesDoc.setIsActive(true);
				 guidelinesDoc.setCreatedDate(new Date());
				 guidelinesDoc.setLastModifiedDate(new Date());
				 guidelinesDoc.setCreatedBy(guidelinesEntryTO.getCreatedBy());
				 guidelinesDoc.setModifiedBy(guidelinesEntryTO.getModifiedBy());
				 Course course=new Course();
				 course.setId(guidelinesEntryTO.getCourseTO().getId());
				 guidelinesDoc.setCourse(course);
			} catch(Exception e){
				log.error("Error occured at populateTOtoBO of GuidelinesEntry Helper",e);
				throw new ApplicationException(e);				
			}
				 return guidelinesDoc;
		}
		return null;
	}
	
	/**
	 * 
	 * @param guidelinesEntryTO
	 * @return Populates To to BO for update operation
	 */
	public GuidelinesDoc populateTOtoBOUpdate(GuidelinesEntryTO guidelinesEntryTO)throws Exception
	{
		IGuidelinesEntryTransaction transaction= new GuidelinesEntryTransactionImpl();
		GuidelinesDoc guidelinesDoc = new GuidelinesDoc();
		if(guidelinesEntryTO!=null)
		{
			try{
				//guidelinesDoc = new GuidelinesDoc();
				guidelinesDoc.setId(guidelinesEntryTO.getId());
				 guidelinesDoc.setYear(guidelinesEntryTO.getYear());
				 if(guidelinesEntryTO.getThefile()!=null)
				 {
				 guidelinesDoc.setFileName(guidelinesEntryTO.getThefile().getFileName());
				 guidelinesDoc.setContentType(guidelinesEntryTO.getThefile().getContentType());
				 guidelinesDoc.setDoc(guidelinesEntryTO.getThefile().getFileData());
				 }
				// If the user is not selected a new file for update then get the existing file from backend and keep that
					else{
						GuidelinesDoc doc= transaction.getFileonId(guidelinesEntryTO.getId());
							if(doc!=null){
							guidelinesDoc.setFileName(doc.getFileName()!=null ? doc.getFileName():null);
							guidelinesDoc.setDoc(doc.getDoc()!=null ? doc.getDoc():null);
							guidelinesDoc.setContentType(doc.getContentType()!=null ? doc.getContentType():null);
							}
					}
						guidelinesDoc.setIsActive(true);
						guidelinesDoc.setModifiedBy(guidelinesEntryTO.getModifiedBy());
						guidelinesDoc.setLastModifiedDate(new Date());
						Course course=new Course();
						course.setId(guidelinesEntryTO.getCourseTO().getId());
						guidelinesDoc.setCourse(course);

			} catch(Exception e){
				log.error("Error occured in populateTOtoBOUpdate of guidelines helper", e);
				throw new ApplicationException(e);				
			}
				 return guidelinesDoc;
		}
		return null;
	}
}
