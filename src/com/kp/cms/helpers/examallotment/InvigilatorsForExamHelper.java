package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.forms.examallotment.InvigilatorsForExamForm;
import com.kp.cms.to.examallotment.InvigilatorsForExamTo;
import com.kp.cms.transactions.examallotment.IInvigilatorsForExamTrans;
import com.kp.cms.transactionsimpl.examallotment.InvigilatorsForExamTransImpl;

public class InvigilatorsForExamHelper {
private static volatile InvigilatorsForExamHelper invigilatorsForExamHelper=null;
	 
	
	/**
	 * instance()
	 * @return
	 */
private InvigilatorsForExamHelper(){
	
}
	public static InvigilatorsForExamHelper getInstance(){
		if(invigilatorsForExamHelper == null){
			invigilatorsForExamHelper=new InvigilatorsForExamHelper();
		}
		return invigilatorsForExamHelper;
	}


	public StringBuilder createQuery(InvigilatorsForExamForm invigilatorsForExamForm) throws Exception{
		StringBuilder query=new StringBuilder();
		query=query.append("from Users u where u.active=1 and u.isActive=1 and u.employee.active=1 and u.employee.isActive=1 and u.isTeachingStaff=1");
				if(invigilatorsForExamForm.getDeptId()!=null && !invigilatorsForExamForm.getDeptId().isEmpty()){
					query=query.append(" and u.employee.department.id="+Integer.parseInt(invigilatorsForExamForm.getDeptId()));
				}else if(invigilatorsForExamForm.getDeanaryId()!=null && !invigilatorsForExamForm.getDeanaryId().isEmpty()){
					query=query.append(" and u.employee.department.employeeStreamBO.id="+Integer.parseInt(invigilatorsForExamForm.getDeanaryId()));
				}
				if(invigilatorsForExamForm.getLocationId()!=null && !invigilatorsForExamForm.getLocationId().isEmpty()){
					query=query.append(" and u.employee.workLocationId.id="+Integer.parseInt(invigilatorsForExamForm.getLocationId()));
				}
				query=query.append(" order by u.employee.department.name ,u.employee.firstName");
		return query;
	}


	public List<InvigilatorsForExamTo> convertBosToTos(List<Users> users, Map<Integer, Integer> userIds, List<Integer> list2) throws Exception{
		List<InvigilatorsForExamTo> list=null;
		if(users!=null && !users.isEmpty()){
			list=new ArrayList<InvigilatorsForExamTo>();
			Iterator<Users> iterator=users.iterator();
			InvigilatorsForExamTo invigilatorsForExamTo=null;
			while (iterator.hasNext()) {
				Users users2 = (Users) iterator.next();
				if(!list2.contains(users2.getId())){
					invigilatorsForExamTo=new InvigilatorsForExamTo();
					invigilatorsForExamTo.setUserId(users2.getId());
					StringBuilder name=new StringBuilder();
					if(users2.getEmployee()!=null){
						if(users2.getEmployee().getFirstName()!=null&& !users2.getEmployee().getFirstName().isEmpty())
						name=name.append(users2.getEmployee().getFirstName());
						if(users2.getEmployee().getMiddleName()!=null && !users2.getEmployee().getMiddleName().isEmpty()){
							name=name.append(users2.getEmployee().getMiddleName());
						}
						invigilatorsForExamTo.setName(name.toString());
						if(users2.getEmployee().getDepartment()!=null && users2.getEmployee().getDepartment().getName()!=null){
							invigilatorsForExamTo.setDepartment(users2.getEmployee().getDepartment().getName());
						}
						if(users2.getEmployee().getWorkLocationId()!=null){
							invigilatorsForExamTo.setWorkLocationId(users2.getEmployee().getWorkLocationId().getId());
						}
					}
					if(userIds!=null && !userIds.isEmpty() && userIds.containsKey(users2.getId())){
						invigilatorsForExamTo.setFlag("true");
					}else{
						invigilatorsForExamTo.setFlag("false");
					}
					list.add(invigilatorsForExamTo);
				}
			}
		}
		return list;
	}


	public List<ExamInvigilatorAvailable> convertTosToBos(
			List<InvigilatorsForExamTo> list, String examId, String locationId, String userId) throws Exception{
		List<ExamInvigilatorAvailable> examInvigilatorAvailables=new ArrayList<ExamInvigilatorAvailable>();
		Iterator<InvigilatorsForExamTo> iterator=list.iterator();
		ExamInvigilatorAvailable examInvigilatorAvailable=null;
		while (iterator.hasNext()) {
			InvigilatorsForExamTo invigilatorsForExamTo = (InvigilatorsForExamTo) iterator.next();
			examInvigilatorAvailable=new ExamInvigilatorAvailable();
			//set teacher id
			Users user=new Users();
			user.setId(invigilatorsForExamTo.getUserId());
			examInvigilatorAvailable.setTeacherId(user);
			//set examId
			ExamDefinition examDefinition=new ExamDefinition();
			examDefinition.setId(Integer.parseInt(examId));
			examInvigilatorAvailable.setExamId(examDefinition);
			//set work location id
			if(locationId!=null && !locationId.isEmpty()){
				EmployeeWorkLocationBO employeeWorkLocationBO=new EmployeeWorkLocationBO();
				employeeWorkLocationBO.setId(Integer.parseInt(locationId));
				examInvigilatorAvailable.setWorkLocationId(employeeWorkLocationBO);
			}
			//set remaing values
			examInvigilatorAvailable.setIsActive(true);
			examInvigilatorAvailable.setCreatedBy(userId);
			examInvigilatorAvailable.setModifiedBy(userId);
			examInvigilatorAvailable.setCreatedDate(new Date());
			examInvigilatorAvailable.setLastModifiedDate(new Date());
			examInvigilatorAvailables.add(examInvigilatorAvailable);
		}
		return examInvigilatorAvailables;
	}


	public StringBuilder createQueryForSearch(
			InvigilatorsForExamForm invigilatorsForExamForm) throws Exception{
		StringBuilder query=new StringBuilder();
		query=query.append("from ExamInvigilatorAvailable u where u.isActive=1 and u.examId.id="+Integer.parseInt(invigilatorsForExamForm.getExamId())+
				" and  u.workLocationId.id="+Integer.parseInt(invigilatorsForExamForm.getLocationId()));
		if(invigilatorsForExamForm.getDeptId()!=null && !invigilatorsForExamForm.getDeptId().isEmpty()){
			query=query.append(" and u.teacherId.employee.department.id="+Integer.parseInt(invigilatorsForExamForm.getDeptId()));
		}else if(invigilatorsForExamForm.getDeanaryId()!=null && !invigilatorsForExamForm.getDeanaryId().isEmpty()){
			query=query.append(" and u.teacherId.employee.department.employeeStreamBO.id="+Integer.parseInt(invigilatorsForExamForm.getDeanaryId()));
		}
		query=query.append(" order by u.teacherId.employee.department.name ,u.teacherId.employee.firstName");
	return query;
	}


	public List<InvigilatorsForExamTo> convertBosToToslidt(
			List<ExamInvigilatorAvailable> list) {
		List<InvigilatorsForExamTo> invigilatorsForExamTos=null;
		if(list!=null && !list.isEmpty()){
			invigilatorsForExamTos=new ArrayList<InvigilatorsForExamTo>();
			Iterator<ExamInvigilatorAvailable> iterator=list.iterator();
			InvigilatorsForExamTo invigilatorsForExamTo=null;
			while (iterator.hasNext()) {
				ExamInvigilatorAvailable examInvigilatorAvailable = (ExamInvigilatorAvailable) iterator.next();
				invigilatorsForExamTo=new InvigilatorsForExamTo();
				invigilatorsForExamTo.setId(examInvigilatorAvailable.getId());
				StringBuilder name=new StringBuilder();
				if(examInvigilatorAvailable.getTeacherId().getEmployee()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName()!=null && !examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName().isEmpty()){
					name=name.append(examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName());
				}
				if(examInvigilatorAvailable.getTeacherId().getEmployee()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName()!=null && !examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName().isEmpty()){
					name=name.append(examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName());
				}
				invigilatorsForExamTo.setName(name.toString());
				if(examInvigilatorAvailable.getTeacherId().getEmployee()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getDepartment()!=null && examInvigilatorAvailable.getTeacherId().getEmployee().getDepartment().getName()!=null){
					invigilatorsForExamTo.setDepartment(examInvigilatorAvailable.getTeacherId().getEmployee().getDepartment().getName());
				}
				if(examInvigilatorAvailable.getExamId().getName()!=null){
					invigilatorsForExamTo.setExamName(examInvigilatorAvailable.getExamId().getName());
				}
				if(examInvigilatorAvailable.getWorkLocationId().getName()!=null){
					invigilatorsForExamTo.setWorkLocation(examInvigilatorAvailable.getWorkLocationId().getName());
				}
				invigilatorsForExamTos.add(invigilatorsForExamTo);
			}
		}
		return invigilatorsForExamTos;
	}


	public Map<Integer, Integer> getUserIdsFromBosList(
			List<ExamInvigilatorAvailable> list) throws Exception{
		Map<Integer, Integer> map=null;
		if(list!=null && !list.isEmpty()){
			map=new HashMap<Integer, Integer>();
			Iterator<ExamInvigilatorAvailable> iterator=list.iterator();
			while (iterator.hasNext()) {
				ExamInvigilatorAvailable examInvigilatorAvailable = (ExamInvigilatorAvailable) iterator.next();
				map.put(examInvigilatorAvailable.getTeacherId().getId(), examInvigilatorAvailable.getId());
			}
		}
		return map;
	}


	public List<ExamInvigilatorAvailable> modifyBosToUpdate(
			List<Integer> theRecordsToSetIsActiveIsFalse,
			List<Integer> theRecordsToSetIsActiveIsTrue, Map<Integer, ExamInvigilatorAvailable> map, String string) throws Exception{
		List<ExamInvigilatorAvailable> list=new ArrayList<ExamInvigilatorAvailable>();
		if(theRecordsToSetIsActiveIsFalse!=null && !theRecordsToSetIsActiveIsFalse.isEmpty()){
			Iterator<Integer> iterator=theRecordsToSetIsActiveIsFalse.iterator();
			while (iterator.hasNext()) {
				Integer integer = (Integer) iterator.next();
				ExamInvigilatorAvailable examInvigilatorAvailable=map.get(integer);
				examInvigilatorAvailable.setIsActive(false);
				examInvigilatorAvailable.setLastModifiedDate(new Date());
				examInvigilatorAvailable.setModifiedBy(string);
				list.add(examInvigilatorAvailable);
			}
		}
		if(theRecordsToSetIsActiveIsTrue!=null && !theRecordsToSetIsActiveIsTrue.isEmpty()){

			Iterator<Integer> iterator=theRecordsToSetIsActiveIsTrue.iterator();
			while (iterator.hasNext()) {
				Integer integer = (Integer) iterator.next();
				ExamInvigilatorAvailable examInvigilatorAvailable=map.get(integer);
				examInvigilatorAvailable.setLastModifiedDate(new Date());
				examInvigilatorAvailable.setModifiedBy(string);
				list.add(examInvigilatorAvailable);
			}
		
		}
		return list;
	}


	public Map<Integer, ExamInvigilatorAvailable> convertBosToMap(
			List<ExamInvigilatorAvailable> examInvigilatorsList) throws Exception{
		Map<Integer, ExamInvigilatorAvailable> map=new HashMap<Integer, ExamInvigilatorAvailable>();
		Iterator<ExamInvigilatorAvailable> iterator=examInvigilatorsList.iterator();
		while (iterator.hasNext()) {
			ExamInvigilatorAvailable examInvigilatorAvailable = (ExamInvigilatorAvailable) iterator.next();
			map.put(examInvigilatorAvailable.getId(), examInvigilatorAvailable);
		}
		return map;
	}


	public List<ExamInvigilatorAvailable> convertBosToBos(
			List<ExamInvigilatorAvailable> examInvigilatorAvailables, String string) {
		List<ExamInvigilatorAvailable> list=new ArrayList<ExamInvigilatorAvailable>();
		Iterator<ExamInvigilatorAvailable> iterator=examInvigilatorAvailables.iterator();
		while (iterator.hasNext()) {
			ExamInvigilatorAvailable examInvigilatorAvailable = (ExamInvigilatorAvailable) iterator.next();
			examInvigilatorAvailable.setIsActive(false);
			examInvigilatorAvailable.setLastModifiedDate(new Date());
			examInvigilatorAvailable.setModifiedBy(string);
			list.add(examInvigilatorAvailable);
		}
		return list;
	}
}
