package com.kp.cms.handlers.admission;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.ConvocationCourse;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.PublishStudentEdit;
import com.kp.cms.bo.admission.PublishStudentEditClasswise;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.PublishStudentEditForm;
import com.kp.cms.handlers.admin.ConvocationSessionHandler;
import com.kp.cms.helpers.admission.PublishStudentEditHelper;
import com.kp.cms.to.admission.PublishStudentEditTO;
import com.kp.cms.transactions.admission.IPublishStudentEditTransaction;
import com.kp.cms.transactionsimpl.admission.PublishStudentEditTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class PublishStudentEditHandler {
	
	private static volatile PublishStudentEditHandler handler;
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	public static PublishStudentEditHandler getInstance(){
		if(handler==null){
			handler=new PublishStudentEditHandler();
		}
		return handler;
	}

	public boolean publishByRegNowise(PublishStudentEditForm publishStudentEditForm) throws Exception{
		IPublishStudentEditTransaction transaction =PublishStudentEditTxnImpl.getInstance();
		List<Student> students =transaction.getStudentList(publishStudentEditForm);
		Map<Integer, PublishStudentEdit> publishStudentEdits = transaction.getPublishStudentEdit(publishStudentEditForm);
		Map<Integer, PublishStudentEditClasswise> classwiseMap=transaction.getpublishStudentEditClass(publishStudentEditForm);
		if(publishStudentEdits!=null && !publishStudentEdits.isEmpty())
			students=PublishStudentEditHelper.getInstance().getWholeStudents(students,publishStudentEdits,classwiseMap,publishStudentEditForm);
		List<PublishStudentEdit> studentEditTOs = PublishStudentEditHelper.getInstance().convertBOTO(students,publishStudentEditForm);
		boolean isUpdated=transaction.updateStudentlist(studentEditTOs);
		return isUpdated;
	}

	public boolean publishByClassId(PublishStudentEditForm publishStudentEditForm)throws Exception {
		IPublishStudentEditTransaction transaction = PublishStudentEditTxnImpl.getInstance();
		List<PublishStudentEditClasswise> classwises =PublishStudentEditHelper.getInstance().publishByClasswise(publishStudentEditForm); 
		boolean isAdded=  transaction.updateClasswiseList(classwises);
		return isAdded;
	}

	public List<PublishStudentEditTO> getToList(PublishStudentEditForm publishStudentEditForm) throws Exception{
		IPublishStudentEditTransaction transaction =PublishStudentEditTxnImpl.getInstance();
		List<PublishStudentEditClasswise> classwises = transaction.getClasswiseList(publishStudentEditForm);
		List<PublishStudentEditTO> editTOs = PublishStudentEditHelper.getInstance().getTOlist(classwises,publishStudentEditForm);
		return editTOs;
	}

	public boolean checkDuplicate(PublishStudentEditForm publishStudentEditForm)throws Exception {
		boolean isDuplicate=PublishStudentEditHelper.getInstance().checkDuplicate(publishStudentEditForm);
		return isDuplicate;
	}

	public boolean checkDuplicateStudentWise(PublishStudentEditForm publishStudentEditForm)throws Exception{
		IPublishStudentEditTransaction editTransaction = PublishStudentEditTxnImpl.getInstance();
		List<Student> students =editTransaction.getStudentList(publishStudentEditForm);
		Student student = students.get(0);
		Map<Integer, PublishStudentEdit> publishStudentEdits = editTransaction.getPublishStudentEdit(publishStudentEditForm);
		Map<Integer, PublishStudentEditClasswise> classwiseMap=editTransaction.getpublishStudentEditClass(publishStudentEditForm);
		boolean isDuplicate=false;
		if(publishStudentEdits.containsKey(student.getId()) || classwiseMap.containsKey(student.getClassSchemewise().getClasses().getId())){
			isDuplicate=true;
		}
		return isDuplicate;
	}

	public boolean deletePublishDate(PublishStudentEditForm publishStudentEditForm) throws Exception{
		IPublishStudentEditTransaction editTransaction = PublishStudentEditTxnImpl.getInstance();
		return editTransaction.deletePublishEdit(publishStudentEditForm);
	}

	public boolean reactiveClass(int dupId, boolean activate,PublishStudentEditForm publishStudentEditForm)throws Exception {
		IPublishStudentEditTransaction editTransaction = PublishStudentEditTxnImpl.getInstance();
		boolean reactive = editTransaction.reactiveClassId(dupId, activate, publishStudentEditForm);
		return reactive;
	}

	public void editDetails(PublishStudentEditForm publishStudentEditForm) throws Exception{
		IPublishStudentEditTransaction editTransaction = PublishStudentEditTxnImpl.getInstance();
		PublishStudentEditClasswise editClasswise = editTransaction.getEditDetails(publishStudentEditForm);
		if(editClasswise != null){
			if(editClasswise.getStartDate() != null){
				String startDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(editClasswise.getStartDate()), PublishStudentEditHandler.SQL_DATEFORMAT,PublishStudentEditHandler.FROM_DATEFORMAT);
				publishStudentEditForm.setEditStartDate(startDate);
			}
			if(editClasswise.getEndDate() != null){
				String endDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(editClasswise.getEndDate()), PublishStudentEditHandler.SQL_DATEFORMAT,PublishStudentEditHandler.FROM_DATEFORMAT);
				publishStudentEditForm.setEditEndDate(endDate);
			}
			publishStudentEditForm.setAcademicYear(String.valueOf(editClasswise.getAcademicYear()));
			if(editClasswise.getClasses() != null){
				String[] classIds;
				classIds=String.valueOf(editClasswise.getClasses().getId()).split(",");
				publishStudentEditForm.setClassIds(classIds);
			}
		}
		
	}

	public boolean updatePublish(PublishStudentEditForm publishStudentEditForm) throws Exception{
		IPublishStudentEditTransaction editTransaction = PublishStudentEditTxnImpl.getInstance();
		PublishStudentEditClasswise editClasswise = editTransaction.getEditDetails(publishStudentEditForm);
		if(editClasswise!=null && !editClasswise.toString().isEmpty()){
			editClasswise.setAcademicYear(Integer.parseInt(publishStudentEditForm.getAcademicYear()));
			for(int i=0;i < publishStudentEditForm.getClassIds().length;i++){
				if(publishStudentEditForm.getClassIds()[i] != null && !publishStudentEditForm.getClassIds()[i].trim().isEmpty()){
					Classes classes = new Classes();
					classes.setId(Integer.parseInt(publishStudentEditForm.getClassIds()[i]));
					editClasswise.setClasses(classes);
					
				}
			}
			editClasswise.setStartDate(CommonUtil.ConvertStringToDate(publishStudentEditForm.getEditStartDate()));
			editClasswise.setEndDate(CommonUtil.ConvertStringToDate(publishStudentEditForm.getEditEndDate()));
			editClasswise.setLastModifiedDate(new  Date());
			editClasswise.setModifiedBy(publishStudentEditForm.getUserId());
		}
		return editTransaction.updateClass(editClasswise);
	}

}
