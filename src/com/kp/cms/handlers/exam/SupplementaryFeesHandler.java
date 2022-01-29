package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.exam.InternalRedoFees;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.bo.exam.RegularExamFees;
import com.kp.cms.bo.exam.RevaluationExamFees;
import com.kp.cms.bo.exam.SupplementaryFees;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.SupplementaryFeesForm;
import com.kp.cms.helpers.exam.SupplementaryFeesHelper;
import com.kp.cms.to.exam.InternalRedoFeesTO;
import com.kp.cms.to.exam.RegularExamFeesTo;
import com.kp.cms.to.exam.RevaluationExamFeesTo;
import com.kp.cms.to.exam.SupplementaryFeesTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.PropertyUtil;

public class SupplementaryFeesHandler {
	/**
	 * Singleton object of SupplementaryFeesHandler
	 */
	private static volatile SupplementaryFeesHandler supplementaryFeesHandler = null;
	private static final Log log = LogFactory.getLog(SupplementaryFeesHandler.class);
	private SupplementaryFeesHandler() {
		
	}
	/**
	 * return singleton object of SupplementaryFeesHandler.
	 * @return
	 */
	public static SupplementaryFeesHandler getInstance() {
		if (supplementaryFeesHandler == null) {
			supplementaryFeesHandler = new SupplementaryFeesHandler();
		}
		return supplementaryFeesHandler;
	}
	/**
	 * @param supplementaryFeesForm
	 * @return
	 */
	public boolean addOrUpdate(SupplementaryFeesForm supplementaryFeesForm) throws Exception {
		log.info("Entered into addOrUpdate");
		String query="from SupplementaryFees s where s.isActive=1 and s.academicYear="+supplementaryFeesForm.getAcademicYear()+" and s.religionSection.id="+Integer.parseInt(supplementaryFeesForm.getSectionId())+ "and s.classes.id in(";
		StringBuilder intType =new StringBuilder();
		String[] tempArray=supplementaryFeesForm.getSelectedClass();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		query=query+intType+")";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<SupplementaryFees> fees=transaction.getDataForQuery(query);
		if(fees!=null && !fees.isEmpty()){
			String classNames="";
			for (SupplementaryFees bo: fees) {
				if(!classNames.isEmpty())
					classNames=classNames+","+bo.getClasses().getName();
				else
					classNames=bo.getClasses().getName();
			}
			throw new DuplicateException(classNames);
		}
		List<SupplementaryFees> boList=new ArrayList<SupplementaryFees>();
		for (int i = 0; i < tempArray.length; i++) {
			SupplementaryFees bo=new SupplementaryFees();
			Classes classes=new Classes();
			ReligionSection religionSection=new ReligionSection();
			classes.setId(Integer.parseInt(tempArray[i]));
			bo.setClasses(classes);
			bo.setIsActive(true);
			bo.setCreatedBy(supplementaryFeesForm.getUserId());
			bo.setModifiedBy(supplementaryFeesForm.getUserId());
			bo.setCreatedDate(new Date());
			bo.setLastModifiedDate(new Date());
			if(supplementaryFeesForm.getTheoryFees()!=null)
			bo.setTheoryFees(new BigDecimal(supplementaryFeesForm.getTheoryFees()));
			if(supplementaryFeesForm.getPracticalFees()!=null)
			bo.setPracticalFees(new BigDecimal(supplementaryFeesForm.getPracticalFees()));
			if(supplementaryFeesForm.getPaperFees()!=null)
			bo.setPaperFees(new BigDecimal(supplementaryFeesForm.getPaperFees()));
			if(supplementaryFeesForm.getRegisterationFees()!=null)
			bo.setRegistrationFees(new BigDecimal(supplementaryFeesForm.getRegisterationFees()));
			if(supplementaryFeesForm.getApplicationFees()!=null)
			bo.setApplicationFees(new BigDecimal(supplementaryFeesForm.getApplicationFees()));
			if(supplementaryFeesForm.getCvCampFees()!=null)
			bo.setCvCampFees(new BigDecimal(supplementaryFeesForm.getCvCampFees()));
			if(supplementaryFeesForm.getMarksListFees()!=null)
			bo.setMarksListFees(new BigDecimal(supplementaryFeesForm.getMarksListFees()));
			bo.setAcademicYear(supplementaryFeesForm.getAcademicYear());
			religionSection.setId(Integer.parseInt(supplementaryFeesForm.getSectionId()));
			bo.setReligionSection(religionSection);
			boList.add(bo);
		}
		log.info("Exit from addOrUpdate");
		return PropertyUtil.getInstance().saveList(boList);
	}
	/**
	 * @param id
	 * @param mode
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteOrReactivate(int id, String mode, String userId) throws Exception{
		ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
		SupplementaryFees bo=(SupplementaryFees) transaction.getMasterEntryDataById(SupplementaryFees.class, id);
//		if(mode.equalsIgnoreCase("delete"))
//			bo.setIsActive(false);
//		else
//			bo.setIsActive(true);
//		bo.setModifiedBy(userId);
//		bo.setLastModifiedDate(new Date());
		return PropertyUtil.getInstance().delete(bo);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<SupplementaryFeesTo> getActiveList() throws Exception {
		String query="from SupplementaryFees p where p.isActive=1";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<SupplementaryFees> boList=transaction.getDataForQuery(query);
		return SupplementaryFeesHelper.getInstance().convertBotoToList(boList);
	}
	
	public List<RegularExamFeesTo> getActiveListRegular() throws Exception {
		String query="from RegularExamFees r where r.isActive=1";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<RegularExamFees> boList=transaction.getDataForQuery(query);
		return SupplementaryFeesHelper.getInstance().convertBotoToListRegular(boList);
	}
	public boolean addOrUpdateRegularFee(SupplementaryFeesForm supplementaryFeesForm) throws Exception {
		log.info("Entered into addOrUpdate");
		String query="from RegularExamFees r where r.isActive=1 and r.academicYear="+supplementaryFeesForm.getAcademicYear()+" and r.religionSection.id="+Integer.parseInt(supplementaryFeesForm.getSectionId())+ " and r.classes.id in(";
		StringBuilder intType =new StringBuilder();
		String[] tempArray=supplementaryFeesForm.getSelectedClass();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		query=query+intType+")";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<RegularExamFees> fees=transaction.getDataForQuery(query);
		if(fees!=null && !fees.isEmpty()){
			String classNames="";
			for (RegularExamFees bo: fees) {
				if(!classNames.isEmpty())
					classNames=classNames+","+bo.getClasses().getName();
				else
					classNames=bo.getClasses().getName();
			}
			throw new DuplicateException(classNames);
		}
		List<RegularExamFees> boList=new ArrayList<RegularExamFees>();
		for (int i = 0; i < tempArray.length; i++) {
			RegularExamFees bo=new RegularExamFees();
			Classes classes=new Classes();
			classes.setId(Integer.parseInt(tempArray[i]));
			bo.setClasses(classes);
			bo.setIsActive(true);
			bo.setCreatedBy(supplementaryFeesForm.getUserId());
			bo.setModifiedBy(supplementaryFeesForm.getUserId());
			bo.setCreatedDate(new Date());
			bo.setLastModifiedDate(new Date());
			if(supplementaryFeesForm.getTheoryFees()!=null && !supplementaryFeesForm.getTheoryFees().isEmpty())
			bo.setTheoryFees(new BigDecimal(supplementaryFeesForm.getTheoryFees()));
			
			if(supplementaryFeesForm.getPracticalFees()!=null && !supplementaryFeesForm.getPracticalFees().isEmpty())
				bo.setPracticalFees(new BigDecimal(supplementaryFeesForm.getPracticalFees()));
			if(supplementaryFeesForm.getApplicationFees()!=null && !supplementaryFeesForm.getApplicationFees().isEmpty())
				bo.setApplicationFees(new BigDecimal(supplementaryFeesForm.getApplicationFees()));
			if(supplementaryFeesForm.getCvCampFees()!=null && !supplementaryFeesForm.getCvCampFees().isEmpty())
				bo.setCvCampFees(new BigDecimal(supplementaryFeesForm.getCvCampFees()));
			if(supplementaryFeesForm.getMarksListFees()!=null && !supplementaryFeesForm.getMarksListFees().isEmpty())
				bo.setMarksListFees(new BigDecimal(supplementaryFeesForm.getMarksListFees()));
			if(supplementaryFeesForm.getOnlineServiceChargeFees()!=null && !supplementaryFeesForm.getOnlineServiceChargeFees().isEmpty())
				bo.setOnlineServiceChargeFees(new BigDecimal(supplementaryFeesForm.getOnlineServiceChargeFees()));
			if(supplementaryFeesForm.getExamFees()!=null && !supplementaryFeesForm.getExamFees().isEmpty())
				bo.setExamFees(new BigDecimal(supplementaryFeesForm.getExamFees()));
			bo.setAcademicYear(supplementaryFeesForm.getAcademicYear());
			ReligionSection section=new ReligionSection();
			section.setId(Integer.parseInt(supplementaryFeesForm.getSectionId()));
			bo.setReligionSection(section);
			boList.add(bo);
		}
		log.info("Exit from addOrUpdate");
		return PropertyUtil.getInstance().saveList(boList);
	}
	
	public boolean deleteOrReactivateRegular(int id, String mode, String userId) throws Exception{
		ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
		RegularExamFees bo=(RegularExamFees) transaction.getMasterEntryDataById(RegularExamFees.class, id);

		return PropertyUtil.getInstance().delete(bo);
	}

	
	//revaluation
	public List<RevaluationExamFeesTo> getActiveListRevaluation() throws Exception {
		String query="from RevaluationExamFees r where r.isActive=1";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<RevaluationExamFees> boList=transaction.getDataForQuery(query);
		return SupplementaryFeesHelper.getInstance().convertBotoToListRevaluation(boList);
	}
	public boolean addOrUpdateRevaluationFee(SupplementaryFeesForm supplementaryFeesForm) throws Exception {
		log.info("Entered into addOrUpdate");
		String query="from RevaluationExamFees r where r.isActive=1 and r.academicYear="+supplementaryFeesForm.getAcademicYear()+" and r.course.id in(";
		StringBuilder intType =new StringBuilder();
		String[] tempArray=supplementaryFeesForm.getSelectedCourse();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		query=query+intType+")";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<RevaluationExamFees> fees=transaction.getDataForQuery(query);
		if(fees!=null && !fees.isEmpty()){
			String courseNames="";
			for (RevaluationExamFees bo: fees) {
				if(!courseNames.isEmpty())
					courseNames=courseNames+","+bo.getCourse().getName();
				else
					courseNames=bo.getCourse().getName();
			}
			throw new DuplicateException(courseNames);
		}
		List<RevaluationExamFees> boList=new ArrayList<RevaluationExamFees>();
		for (int i = 0; i < tempArray.length; i++) {
			RevaluationExamFees bo=new RevaluationExamFees();
			Course course=new Course();
			course.setId(Integer.parseInt(tempArray[i]));
			bo.setCourse(course);
			bo.setIsActive(true);
			bo.setCreatedBy(supplementaryFeesForm.getUserId());
			bo.setModifiedBy(supplementaryFeesForm.getUserId());
			bo.setCreatedDate(new Date());
			bo.setLastModifiedDate(new Date());
			
			if(supplementaryFeesForm.getRevaluationFees()!=null && !supplementaryFeesForm.getRevaluationFees().isEmpty())
				bo.setRevaluationFees(new BigDecimal(supplementaryFeesForm.getRevaluationFees()));
			if(supplementaryFeesForm.getScrutinyFees()!=null && !supplementaryFeesForm.getScrutinyFees().isEmpty())
				bo.setScrutinyFees(new BigDecimal(supplementaryFeesForm.getScrutinyFees()));
			if(supplementaryFeesForm.getApplicationFees()!=null && !supplementaryFeesForm.getApplicationFees().isEmpty())
				bo.setApplicationFees(new BigDecimal(supplementaryFeesForm.getApplicationFees()));
			if(supplementaryFeesForm.getChallengeValuationFees()!=null && !supplementaryFeesForm.getChallengeValuationFees().isEmpty())
				bo.setChallengeValuationFees(new BigDecimal(supplementaryFeesForm.getChallengeValuationFees()));
	
			
			bo.setAcademicYear(supplementaryFeesForm.getAcademicYear());
			boList.add(bo);
		}
		log.info("Exit from addOrUpdate");
		return PropertyUtil.getInstance().saveList(boList);
	}
	
	public boolean deleteOrReactivateRevaluation(int id, String mode, String userId) throws Exception{
		ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
		RevaluationExamFees bo=(RevaluationExamFees) transaction.getMasterEntryDataById(RevaluationExamFees.class, id);

		return PropertyUtil.getInstance().delete(bo);
	}
	
	public List<InternalRedoFeesTO> getActiveListInternalRedo() throws Exception {
		String query="from InternalRedoFees p where p.isActive=1";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<InternalRedoFees> boList=transaction.getDataForQuery(query);
		return SupplementaryFeesHelper.getInstance().convertBotoToListInternalRedo(boList);
	}
	
	public boolean addOrUpdateInternalRedoFee(SupplementaryFeesForm supplementaryFeesForm) throws Exception {
		
		String query="from InternalRedoFees s where s.isActive=1 and s.academicYear="+supplementaryFeesForm.getAcademicYear()+" and s.religionSection.id="+Integer.parseInt(supplementaryFeesForm.getSectionId())+ "and s.classes.id in(";
		
		StringBuilder intType =new StringBuilder();
		String[] tempArray=supplementaryFeesForm.getSelectedClass();
		for(int i=0;i<tempArray.length;i++){
			
			intType.append(tempArray[i]);
			if(i<(tempArray.length-1)){
				intType.append(",");
			}
		}
		query=query+intType+")";
		
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<InternalRedoFees> fees=transaction.getDataForQuery(query);
		if(fees!=null && !fees.isEmpty()){
			
			String classNames="";
			for (InternalRedoFees bo: fees) {
				if(!classNames.isEmpty())
					classNames=classNames+","+bo.getClasses().getName();
				else
					classNames=bo.getClasses().getName();
			}
			throw new DuplicateException(classNames);
		}
		List<InternalRedoFees> boList=new ArrayList<InternalRedoFees>();
		for (int i = 0; i < tempArray.length; i++) {
			
			InternalRedoFees bo=new InternalRedoFees();
			
			Classes classes=new Classes();
			classes.setId(Integer.parseInt(tempArray[i]));
			bo.setClasses(classes);
			ReligionSection religionSection=new ReligionSection();
			religionSection.setId(Integer.parseInt(supplementaryFeesForm.getSectionId()));
			bo.setReligionSection(religionSection);
			
			bo.setIsActive(true);
			bo.setCreatedBy(supplementaryFeesForm.getUserId());
			bo.setModifiedBy(supplementaryFeesForm.getUserId());
			bo.setCreatedDate(new Date());
			bo.setLastModifiedDate(new Date());
			
			if(supplementaryFeesForm.getTheoryFees()!=null)
				bo.setTheoryFees(new BigDecimal(supplementaryFeesForm.getTheoryFees()));
			if(supplementaryFeesForm.getPracticalFees()!=null)
				bo.setPracticalFees(new BigDecimal(supplementaryFeesForm.getPracticalFees()));
			if(supplementaryFeesForm.getPaperFees()!=null)
				bo.setPaperFees(new BigDecimal(supplementaryFeesForm.getPaperFees()));
			if(supplementaryFeesForm.getRegisterationFees()!=null)
				bo.setRegistrationFees(new BigDecimal(supplementaryFeesForm.getRegisterationFees()));
			if(supplementaryFeesForm.getApplicationFees()!=null)
				bo.setApplicationFees(new BigDecimal(supplementaryFeesForm.getApplicationFees()));
			if(supplementaryFeesForm.getCvCampFees()!=null)
				bo.setCvCampFees(new BigDecimal(supplementaryFeesForm.getCvCampFees()));
			if(supplementaryFeesForm.getMarksListFees()!=null)
				bo.setMarksListFees(new BigDecimal(supplementaryFeesForm.getMarksListFees()));
			
			bo.setAcademicYear(supplementaryFeesForm.getAcademicYear());			
			boList.add(bo);
		}
		return PropertyUtil.getInstance().saveList(boList);
	}
	
	public boolean deleteOrReactivateInternalRedoFees(int id, String mode, String userId) throws Exception{
		ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
		InternalRedoFees bo=(InternalRedoFees) transaction.getMasterEntryDataById(InternalRedoFees.class, id);
		return PropertyUtil.getInstance().delete(bo);
	}
}
