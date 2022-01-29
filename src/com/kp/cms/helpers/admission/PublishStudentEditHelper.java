package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hslf.record.CString;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.PublishStudentEdit;
import com.kp.cms.bo.admission.PublishStudentEditClasswise;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.PublishStudentEditForm;
import com.kp.cms.to.admission.PublishStudentEditTO;
import com.kp.cms.transactions.admission.IPublishStudentEditTransaction;
import com.kp.cms.transactionsimpl.admission.PublishStudentEditTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class PublishStudentEditHelper {
	
	private static volatile PublishStudentEditHelper helper;
	
	public static PublishStudentEditHelper getInstance(){
		if(helper==null){
			helper=new PublishStudentEditHelper();
		}
		return helper;
	}

	public List<PublishStudentEdit> convertBOTO(List<Student> students,PublishStudentEditForm publishStudentEditForm) throws Exception{
		
		List<PublishStudentEdit> studentEdits = new ArrayList<PublishStudentEdit>();
		Iterator< Student> iterator = students.iterator();
		while(iterator.hasNext()){
			Student stu = iterator.next();
			Classes classes = new Classes();
			PublishStudentEdit studentEdit = new PublishStudentEdit();
			if(stu.getClassSchemewise()!=null && !stu.getClassSchemewise().toString().isEmpty()){
				classes.setId(stu.getClassSchemewise().getClasses().getId());
				studentEdit.setClasses(classes);
			}
			studentEdit.setStudent(stu);
			studentEdit.setCreatedBy(publishStudentEditForm.getUserId());
			studentEdit.setCreatedDate(new Date());
			studentEdit.setIsActive(true);
			studentEdit.setStartDate(CommonUtil.ConvertStringToDate(publishStudentEditForm.getEditStartDate()));
			studentEdit.setEndDate(CommonUtil.ConvertStringToDate(publishStudentEditForm.getEditEndDate()));
			studentEdits.add(studentEdit);
		}
		return studentEdits;
	}

	public List<PublishStudentEditClasswise> publishByClasswise(PublishStudentEditForm publishStudentEditForm)throws Exception {
		
		List<PublishStudentEditClasswise> editClasswises = new ArrayList<PublishStudentEditClasswise>();
		for(int i=0;i < publishStudentEditForm.getClassIds().length;i++){
			PublishStudentEditClasswise classwise = new PublishStudentEditClasswise();
			if(publishStudentEditForm.getClassIds()[i] != null && !publishStudentEditForm.getClassIds()[i].trim().isEmpty()){
				Classes classes = new Classes();
				classes.setId(Integer.parseInt(publishStudentEditForm.getClassIds()[i]));
				classwise.setClasses(classes);
				classwise.setAcademicYear(Integer.parseInt(publishStudentEditForm.getAcademicYear()));
				classwise.setCreatedBy(publishStudentEditForm.getUserId());
				classwise.setCreatedDate(new Date());
				classwise.setIsActive(true);
				classwise.setStartDate(CommonUtil.ConvertStringToDate(publishStudentEditForm.getEditStartDate()));
				classwise.setEndDate(CommonUtil.ConvertStringToDate(publishStudentEditForm.getEditEndDate()));
				editClasswises.add(classwise);
			}
			IPublishStudentEditTransaction editTransaction = PublishStudentEditTxnImpl.getInstance();
			PublishStudentEditClasswise publishStudentEditClasswise = new PublishStudentEditClasswise();
			publishStudentEditClasswise = editTransaction.getDupentry(classwise);
			if(publishStudentEditClasswise!=null && !publishStudentEditClasswise.toString().isEmpty()){
				publishStudentEditForm.setDupId(publishStudentEditClasswise.getId());
				throw new ReActivateException();
			}
		}
		return editClasswises;
	}

	public List<PublishStudentEditTO> getTOlist(List<PublishStudentEditClasswise> classwises,PublishStudentEditForm publishStudentEditForm) throws Exception{
		
		List<PublishStudentEditTO> editTOs  = new ArrayList<PublishStudentEditTO>();
		Iterator<PublishStudentEditClasswise> iterator = classwises.iterator();
		while (iterator.hasNext()) {
			PublishStudentEditClasswise classwise = iterator.next();
			PublishStudentEditTO editTO = new PublishStudentEditTO();
			editTO.setId(classwise.getId());
			editTO.setAcademicYear(String.valueOf(classwise.getAcademicYear()));
			editTO.setClassName(classwise.getClasses().getName());
			editTO.setStartDate(CommonUtil.getStringDate(classwise.getStartDate()));
			editTO.setEndDate(CommonUtil.getStringDate(classwise.getEndDate()));
			editTOs.add(editTO);
		}
		return editTOs;
	}

	@SuppressWarnings("unchecked")
	public boolean checkDuplicate(PublishStudentEditForm publishStudentEditForm) throws Exception {
		Session session =null;
		boolean duplicate =false;
		try{
			session = HibernateUtil.getSession();
			String string = "select p.classes.id from PublishStudentEditClasswise p where p.isActive=1";
			Query query=session.createQuery(string);
			List<Integer> classIds=query.list();
			if(classIds != null && !classIds.isEmpty()){
				String courseNames ="";
				for (int i = 0; i < publishStudentEditForm.getClassIds().length; i++) {
					if(publishStudentEditForm.getClassIds()[i] != null){
						if(classIds.contains(Integer.parseInt(publishStudentEditForm.getClassIds()[i]))){
							duplicate = true;
							if(courseNames.isEmpty()){
								courseNames = publishStudentEditForm.getClassMap().get(Integer.parseInt(publishStudentEditForm.getClassIds()[i]));
							}else{
								courseNames = courseNames +" ;    "+publishStudentEditForm.getClassMap().get(Integer.parseInt(publishStudentEditForm.getClassIds()[i]));
							}
						}
					}
				}
				publishStudentEditForm.setErrorMessage(courseNames);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return duplicate;
	}

	@SuppressWarnings("null")
	public List<Student> getWholeStudents(List<Student> students,Map<Integer, PublishStudentEdit> publishStudentEdits,
											Map<Integer, PublishStudentEditClasswise> classwiseMap,
											PublishStudentEditForm publishStudentEditForm) throws Exception{
		List<Student> list= new ArrayList<Student>();
		try{
			Iterator<Student> itr = students.iterator();
			while (itr.hasNext()) {
				Student student = itr.next();
				if(student.getClassSchemewise()!=null && classwiseMap!=null && !classwiseMap.isEmpty()){
						if(!publishStudentEdits.containsKey(student.getId()) && !classwiseMap.containsKey(student.getClassSchemewise().getClasses().getId())){
							list.add(student);
						}
				}else if(student.getClassSchemewise()==null){
					if(!publishStudentEdits.containsKey(student.getId())){
						list.add(student);
					}
				}else if(!publishStudentEdits.containsKey(student.getId())){
					list.add(student);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	

}
