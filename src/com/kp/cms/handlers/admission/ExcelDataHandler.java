package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kp.cms.forms.admission.ExcelDataForm;
import com.kp.cms.forms.admission.UploadUniversityRegNoForm;
import com.kp.cms.to.admission.ExcelDataTO;
import com.kp.cms.transactions.admission.IExcelTransaction;
import com.kp.cms.transactionsimpl.admission.ExcelTransactionImpl;
import com.kp.cms.utilities.AdmissionCSVUpdater;

public class ExcelDataHandler {

	private static final Logger log = Logger.getLogger(ExcelDataHandler.class);	
	public static volatile ExcelDataHandler excelDataHandler = null;

	/**
	 * This method is used to create a single instance of SubjectHandler.
	 * @return unique instance of SubjectHandler.
	 */
	
	public static ExcelDataHandler getInstance() {
		if (excelDataHandler == null) {
			excelDataHandler = new ExcelDataHandler();
			return excelDataHandler;
		}
		return excelDataHandler;
	}
	
	public Map<Integer,Integer> getAdmAppDetails(int year,int csDurationId,int courseId) throws Exception{
		log.info("call of getAdmApplnDetails method in ExcelDataHandler class.");
		IExcelTransaction excelTransaction = new ExcelTransactionImpl();
		Map<Integer,Integer> map = excelTransaction.getAdmAppDetails(year,csDurationId,courseId);
		log.info("end of getAdmApplnDetails method in ExcelDataHandler class.");
		return map;
	}
	
	public Map<String,Integer> getClasses(int courseId, int csId ,int academicYear) throws Exception{
		log.info("call of getClasses method in ExcelDataHandler class.");
		IExcelTransaction excelTransaction = new ExcelTransactionImpl();
		Map<String,Integer> classesMap = excelTransaction.getClasses(courseId, csId,academicYear);
		log.info("end of getClasses method in ExcelDataHandler class.");
		return classesMap;
	}
	
	
	/**
	 * This method is used to check section name duplicate.
	 * @param courseId
	 * @param semesterId
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean checkDuplicateSection(int courseId, int csDurationId, String sectionName) throws Exception{
		log.info("call of checkDuplicateSection method in ExcelDataHandler class.");
		IExcelTransaction excelTransaction = new ExcelTransactionImpl();
		boolean isDuplicate = excelTransaction.checkDuplicateSectionName(courseId, csDurationId, sectionName);
		log.info("end of checkDuplicateSection method in ExcelDataHandler class.");
		return isDuplicate;
	}
	
	/**
	 * This method is used to save data from excel and UI to database.
	 * @param resultsList
	 * @param dataForm
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean isDataUploaded(List<ExcelDataTO> resultsList, ExcelDataForm dataForm) throws Exception {
		log.info("call of isDataUploaded method in ExcelDataHandler class.");
		IExcelTransaction excelTransaction = new ExcelTransactionImpl();
		List<ExcelDataTO> updateList = new ArrayList<ExcelDataTO>();
		ExcelDataTO dataTO = null;
		boolean isAdded = false;
		int csDurationId=0;
		if(dataForm.getSemister()!=null && !dataForm.getSemister().isEmpty())
		csDurationId = Integer.parseInt(dataForm.getSemister());
		Iterator itr = resultsList.iterator();
		if(resultsList != null && !resultsList.isEmpty()){
			while (itr.hasNext()) {
				dataTO = (ExcelDataTO) itr.next();
				if(dataTO.getApplicationId()==0 && dataTO.getRegistrationNumber() == null && StringUtils.isEmpty(dataTO.getRegistrationNumber())){
					break;
				}
				int year = Integer.parseInt(dataForm.getApplicationYear());
				if(dataTO.getClassId() != 0){
					int classId = dataTO.getClassId();
					int id = excelTransaction.getClassSchemeWiseId(classId,csDurationId);
					List<Integer> rollNoList = AdmissionCSVUpdater.getRollNoList(year, id);
					if(rollNoList !=null && rollNoList.contains(dataTO.getRollNumber())){
						continue;
					}else{
						dataTO.setClassSchemeWiseId(id);
					}
				}else{ 
					continue;
				}
				if(dataTO.getApplicationId() != 0){
					if(excelTransaction.checkAdmApplnId(dataTO.getApplicationId())){
						continue;
					}else{
						int studId = excelTransaction.getStudentId(dataTO.getApplicationId());
						dataTO.setStudentId(studId);
						updateList.add(dataTO);
					}
				}else{
					break;
				}
			}
			isAdded = excelTransaction.updateStudent(updateList);
		}
		log.info("end of isDataUploaded method in ExcelDataHandler class.");
		return isAdded;
	}
	public boolean isAddedChallanExamFee(ExcelDataForm form,List<ExcelDataTO> excelUploadList) throws Exception
	{
		IExcelTransaction excelTransaction = new ExcelTransactionImpl();
		//boolean isVerified=excelTransaction.verifyChallanExam(excelUploadList);
		boolean isAdded=excelTransaction.updateChallanExam(excelUploadList);
		return (isAdded);
	}
	
	public boolean isAddedChallanFee(ExcelDataForm form,List<ExcelDataTO> excelUploadList) throws Exception
	{
		IExcelTransaction excelTransaction = new ExcelTransactionImpl();
		//boolean isVerified=excelTransaction.verifyChallanExam(excelUploadList);
		boolean isAdded=excelTransaction.updateChallanFee(excelUploadList);
		return (isAdded);
	}

	public boolean dataUpload(List<ExcelDataTO> excelResults,UploadUniversityRegNoForm uploadUniversityRegNoForm) throws Exception {
		log.info("call of isDataUploaded method in ExcelDataHandler class.");
		IExcelTransaction excelTransaction = new ExcelTransactionImpl();
		List<ExcelDataTO> updateList = new ArrayList<ExcelDataTO>();
		ExcelDataTO dataTO = null;
		boolean isAdded = false;
		int csDurationId=0;
		
		Iterator itr = excelResults.iterator();
		if(excelResults != null && !excelResults.isEmpty()){
			while (itr.hasNext()) {
				dataTO = (ExcelDataTO) itr.next();
				if( dataTO.getRegistrationNumber() == null && StringUtils.isEmpty(dataTO.getRegistrationNumber())){
					break;
				}
				int year = Integer.parseInt(uploadUniversityRegNoForm.getAcademicYear());
				if(dataTO.getClassId() != 0){
					int classId = dataTO.getClassId();
					int id = excelTransaction.getClassSchemeWiseId(classId,csDurationId);
				if(dataTO.getApplicationId() != 0){
					if(dataTO.getRegistrationNumber()!= null && !dataTO.getRegistrationNumber().isEmpty()){
						dataTO.setClassSchemeWiseId(id);
				
						int studId = excelTransaction.getStudentObj(dataTO.getApplicationId());
						int classSchemewiseId = excelTransaction.getClassSchId(studId);
						dataTO.setClassSchemeWiseId(classSchemewiseId);
						dataTO.setStudentId(studId);
						updateList.add(dataTO);
					}
			}
				}
			}
			isAdded = excelTransaction.updateStudentData(updateList);
		
		}
		log.info("end of isDataUploaded method in ExcelDataHandler class.");
		return isAdded;
		
	}
	
}
