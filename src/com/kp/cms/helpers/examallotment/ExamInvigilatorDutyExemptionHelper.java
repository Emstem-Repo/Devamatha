package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.examallotment.ExamInvigilatorDutyExemption;
import com.kp.cms.bo.examallotment.ExamInviligatorExemption;
import com.kp.cms.forms.examallotment.ExamInvigilatorDutyExemptionForm;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyExemptionTo;


public class ExamInvigilatorDutyExemptionHelper {
	private static volatile ExamInvigilatorDutyExemptionHelper examInvigilatorDutyExemptionHelper=null;
	/**
	 * instance()
	 * @return
	 */
	public static ExamInvigilatorDutyExemptionHelper getInstance(){
		if(examInvigilatorDutyExemptionHelper == null){
			examInvigilatorDutyExemptionHelper=new ExamInvigilatorDutyExemptionHelper();
		}
		return examInvigilatorDutyExemptionHelper;
	}
	public StringBuilder createQueryForSearch(
			ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm) throws Exception{
		StringBuilder query=new StringBuilder();
		query=query.append("from ExamInvigilatorDutyExemption u where u.isActive=1 and u.invigilatorExemptionId.id="+Integer.parseInt(examInvigilatorDutyExemptionForm.getExemptionId())+
				" and  u.teacherId.employee.workLocationId.id="+Integer.parseInt(examInvigilatorDutyExemptionForm.getLocationId()));
		if(examInvigilatorDutyExemptionForm.getDeptId()!=null && !examInvigilatorDutyExemptionForm.getDeptId().isEmpty()){
			query=query.append(" and u.teacherId.employee.department.id="+Integer.parseInt(examInvigilatorDutyExemptionForm.getDeptId()));
		}
		query=query.append(" order by u.teacherId.employee.department.name ,u.teacherId.employee.firstName");
	return query;
	}
	public StringBuilder createQuery(
			ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm) throws Exception{
		StringBuilder query=new StringBuilder();
		query=query.append("from Users u where u.active=1 and u.isActive=1 and u.employee.active=1 and u.employee.isActive=1 and u.isTeachingStaff=1");
				if(examInvigilatorDutyExemptionForm.getDeptId()!=null && !examInvigilatorDutyExemptionForm.getDeptId().isEmpty()){
					query=query.append(" and u.employee.department.id="+Integer.parseInt(examInvigilatorDutyExemptionForm.getDeptId()));
				}
				if(examInvigilatorDutyExemptionForm.getLocationId()!=null && !examInvigilatorDutyExemptionForm.getLocationId().isEmpty()){
					query=query.append(" and u.employee.workLocationId.id="+Integer.parseInt(examInvigilatorDutyExemptionForm.getLocationId()));
				}
				query=query.append(" order by u.employee.department.name ,u.employee.firstName");
		return query;
	}
	public Map<Integer, Integer> getUserIdsFromBosList(
			List<ExamInvigilatorDutyExemption> list) throws Exception{
		Map<Integer, Integer> map=null;
		if(list!=null && !list.isEmpty()){
			map=new HashMap<Integer, Integer>();
			Iterator<ExamInvigilatorDutyExemption> iterator=list.iterator();
			while (iterator.hasNext()) {
				ExamInvigilatorDutyExemption examInvigilatorDutyExemption = (ExamInvigilatorDutyExemption) iterator.next();
				map.put(examInvigilatorDutyExemption.getTeacherId().getId(), examInvigilatorDutyExemption.getId());
			}
		}
		return map;
	}
	public List<ExamInvigilatorDutyExemptionTo> convertBosToTos(
			List<Users> users, Map<Integer, Integer> userIds) throws Exception{
		List<ExamInvigilatorDutyExemptionTo> list=null;
		if(users!=null && !users.isEmpty()){
			list=new ArrayList<ExamInvigilatorDutyExemptionTo>();
			Iterator<Users> iterator=users.iterator();
			ExamInvigilatorDutyExemptionTo examInvigilatorDutyExemptionTo=null;
			while (iterator.hasNext()) {
				Users users2 = (Users) iterator.next();
				examInvigilatorDutyExemptionTo=new ExamInvigilatorDutyExemptionTo();
				examInvigilatorDutyExemptionTo.setUserId(users2.getId());
				StringBuilder name=new StringBuilder();
				if(users2.getEmployee()!=null){
					if(users2.getEmployee().getFirstName()!=null&& !users2.getEmployee().getFirstName().isEmpty())
					name=name.append(users2.getEmployee().getFirstName());
					if(users2.getEmployee().getMiddleName()!=null && !users2.getEmployee().getMiddleName().isEmpty()){
						name=name.append(users2.getEmployee().getMiddleName());
					}
					examInvigilatorDutyExemptionTo.setName(name.toString());
					if(users2.getEmployee().getDepartment()!=null && users2.getEmployee().getDepartment().getName()!=null){
						examInvigilatorDutyExemptionTo.setDepartment(users2.getEmployee().getDepartment().getName());
					}
					if(users2.getEmployee().getWorkLocationId()!=null){
						examInvigilatorDutyExemptionTo.setWorkLocationId(users2.getEmployee().getWorkLocationId().getId());
					}
				}
				if(userIds!=null && !userIds.isEmpty() && userIds.containsKey(users2.getId())){
					examInvigilatorDutyExemptionTo.setFlag("true");
				}else{
					examInvigilatorDutyExemptionTo.setFlag("false");
				}
				
				
				list.add(examInvigilatorDutyExemptionTo);
			}
		}
		return list;
	}
	public Map<Integer, ExamInvigilatorDutyExemption> convertBosToMap(
			List<ExamInvigilatorDutyExemption> examInvigilatorDutyExemptions) throws Exception{
		Map<Integer, ExamInvigilatorDutyExemption> map=null;
		if(examInvigilatorDutyExemptions!=null && !examInvigilatorDutyExemptions.isEmpty()){
			map=new HashMap<Integer, ExamInvigilatorDutyExemption>();
			Iterator<ExamInvigilatorDutyExemption> iterator=examInvigilatorDutyExemptions.iterator();
			while (iterator.hasNext()) {
				ExamInvigilatorDutyExemption examInvigilatorDutyExemption = (ExamInvigilatorDutyExemption) iterator.next();
				map.put(examInvigilatorDutyExemption.getId(), examInvigilatorDutyExemption);
			}
		}
		return map;
	}
	public List<ExamInvigilatorDutyExemption> modifyBosToUpdate(
			List<Integer> theRecordsToSetIsActiveIsFalse,
			List<Integer> theRecordsToSetIsActiveIsTrue,
			Map<Integer, ExamInvigilatorDutyExemption> map, String userId) throws Exception{
		List<ExamInvigilatorDutyExemption> list=new ArrayList<ExamInvigilatorDutyExemption>();
		if(theRecordsToSetIsActiveIsFalse!=null && !theRecordsToSetIsActiveIsFalse.isEmpty()){
			Iterator<Integer> iterator=theRecordsToSetIsActiveIsFalse.iterator();
			while (iterator.hasNext()) {
				Integer integer = (Integer) iterator.next();
				ExamInvigilatorDutyExemption examInvigilatorDutyExemption=map.get(integer);
				examInvigilatorDutyExemption.setIsActive(false);
				examInvigilatorDutyExemption.setLastModifiedDate(new Date());
				examInvigilatorDutyExemption.setModifiedBy(userId);
				list.add(examInvigilatorDutyExemption);
			}
		}
		if(theRecordsToSetIsActiveIsTrue!=null && !theRecordsToSetIsActiveIsTrue.isEmpty()){
			Iterator<Integer> iterator=theRecordsToSetIsActiveIsTrue.iterator();
			while (iterator.hasNext()) {
				Integer integer = (Integer) iterator.next();
				ExamInvigilatorDutyExemption examInvigilatorDutyExemption=map.get(integer);
				examInvigilatorDutyExemption.setLastModifiedDate(new Date());
				examInvigilatorDutyExemption.setModifiedBy(userId);
				list.add(examInvigilatorDutyExemption);
			}
		}
		return list;
	}
	public List<ExamInvigilatorDutyExemption> convertTosToBos(
			List<ExamInvigilatorDutyExemptionTo> list, String exemptionId,
			String locationId, String userId) throws Exception{
		List<ExamInvigilatorDutyExemption> examInvigilatorDutyExemptions=new ArrayList<ExamInvigilatorDutyExemption>();
		Iterator<ExamInvigilatorDutyExemptionTo> iterator=list.iterator();
		ExamInvigilatorDutyExemption examInvigilatorDutyExemption=null;
		while (iterator.hasNext()) {
			ExamInvigilatorDutyExemptionTo examInvigilatorDutyExemptionTo = (ExamInvigilatorDutyExemptionTo) iterator.next();
			examInvigilatorDutyExemption=new ExamInvigilatorDutyExemption();
			//set teacher id
			Users user=new Users();
			user.setId(examInvigilatorDutyExemptionTo.getUserId());
			examInvigilatorDutyExemption.setTeacherId(user);
			//set examId
			ExamInviligatorExemption examInviligatorExemption=new ExamInviligatorExemption();
			examInviligatorExemption.setId(Integer.parseInt(exemptionId));
			examInvigilatorDutyExemption.setInvigilatorExemptionId(examInviligatorExemption);
			//set remaing values
			examInvigilatorDutyExemption.setIsActive(true);
			examInvigilatorDutyExemption.setCreatedBy(userId);
			examInvigilatorDutyExemption.setModifiedBy(userId);
			examInvigilatorDutyExemption.setCreatedDate(new Date());
			examInvigilatorDutyExemption.setLastModifiedDate(new Date());
			examInvigilatorDutyExemptions.add(examInvigilatorDutyExemption);
		}
		return examInvigilatorDutyExemptions;
	}
}
