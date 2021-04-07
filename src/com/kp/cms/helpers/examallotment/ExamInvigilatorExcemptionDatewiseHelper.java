package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.forms.examallotment.ExamInvigilatorExcemptionDatewiseForm;
import com.kp.cms.to.examallotment.ExamInvigilatorExcemptionDatewiseTO;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamInvigilatorExcemptionDatewiseHelper {
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	private static volatile ExamInvigilatorExcemptionDatewiseHelper helper=null;
		 
		/**
		 * instance()
		 * @return
		 */
		public static ExamInvigilatorExcemptionDatewiseHelper getInstance(){
			if(helper == null){
				helper=new ExamInvigilatorExcemptionDatewiseHelper();
			}
			return helper;
		}

	/*	public String createQuery(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
			String query="from Users u where u.active=1 and u.isActive=1 and u.employee.active=1 and u.employee.isActive=1 and u.isTeachingStaff=1";
					if(invigilatorsForExamForm.getDeptId()!=null && !invigilatorsForExamForm.getDeptId().isEmpty()){
						query=query+" and u.employee.department.id="+Integer.parseInt(invigilatorsForExamForm.getDeptId());
					}
					if(invigilatorsForExamForm.getLocationId()!=null && !invigilatorsForExamForm.getLocationId().isEmpty()){
						query=query+" and u.employee.workLocationId.id="+Integer.parseInt(invigilatorsForExamForm.getLocationId());
					}
			return query;
		}*/
		
		
		public StringBuffer createQuery(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
			StringBuffer query=new StringBuffer("from Users u where u.active=1 and u.isActive=1 and u.employee.active=1 and u.employee.isActive=1 and u.isTeachingStaff=1");
					if(invigilatorsForExamForm.getDeptId()!=null && !invigilatorsForExamForm.getDeptId().isEmpty()){
						query=query.append(" and u.employee.department.id="+Integer.parseInt(invigilatorsForExamForm.getDeptId()));
					}
					if(invigilatorsForExamForm.getLocationId()!=null && !invigilatorsForExamForm.getLocationId().isEmpty()){
						query=query.append(" and u.employee.workLocationId.id="+Integer.parseInt(invigilatorsForExamForm.getLocationId()));
					}
				query=query.append("order by u.employee.department.id");
			return query;
		}
		
		
		public StringBuffer createQueryAlreadyExemptedEdit(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
			
			StringBuffer query=new StringBuffer("from ExamInviligatorExemptionDatewise e where e.examId='"+invigilatorsForExamForm.getExamId()+"' and e.session='"+invigilatorsForExamForm.getSession()+"' and e.isActive=1");
					
					if(invigilatorsForExamForm.getDeptId()!=null && !invigilatorsForExamForm.getDeptId().isEmpty()){
						query=query.append(" and e.teacherId.employee.department.id="+Integer.parseInt(invigilatorsForExamForm.getDeptId()));
					}
					if(invigilatorsForExamForm.getLocationId()!=null && !invigilatorsForExamForm.getLocationId().isEmpty()){
						query=query.append(" and e.teacherId.employee.workLocationId.id="+Integer.parseInt(invigilatorsForExamForm.getLocationId()));
					}
					query=query.append("order by e.date, e.teacherId.employee.department");
			return query;
		}
		public StringBuffer createQueryAlreadyExempted(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm, Date dt) throws Exception{
		//Date newDate= CommonUtil.ConvertStringToDateFormat(dt, "dd.MM.yy","dd/MM/yyyy");
			StringBuffer query=new StringBuffer("from ExamInviligatorExemptionDatewise e where e.examId='"+invigilatorsForExamForm.getExamId()+"' and e.examinationSessions.id='"+Integer.parseInt(invigilatorsForExamForm.getSession())+"' and e.isActive=1");
					
					if(invigilatorsForExamForm.getDeptId()!=null && !invigilatorsForExamForm.getDeptId().isEmpty()){
						query=query.append(" and e.teacherId.employee.department.id="+Integer.parseInt(invigilatorsForExamForm.getDeptId()));
					}
					if(invigilatorsForExamForm.getLocationId()!=null && !invigilatorsForExamForm.getLocationId().isEmpty()){
						query=query.append(" and e.teacherId.employee.workLocationId.id="+Integer.parseInt(invigilatorsForExamForm.getLocationId()));
					}
					if(dt!=null){
						java.sql.Date sqlDate = CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(dt),ExamInvigilatorExcemptionDatewiseHelper.SQL_DATEFORMAT,ExamInvigilatorExcemptionDatewiseHelper.FROM_DATEFORMAT));
						query=query.append(" and e.date='"+sqlDate+"'");
					}
				query=query.append("order by e.date, e.teacherId.employee.department");
			return query;
		}

		public List<ExamInvigilatorExcemptionDatewiseTO> convertBosToTos(List<Users> users, List<Integer> list2, ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
			List<ExamInvigilatorExcemptionDatewiseTO> list=null;
			if(users!=null && !users.isEmpty()){
				list=new ArrayList<ExamInvigilatorExcemptionDatewiseTO>();
				Iterator<Users> iterator=users.iterator();
				ExamInvigilatorExcemptionDatewiseTO invigilatorsForExamTo=null;
				while (iterator.hasNext()) {
					Users users2 = (Users) iterator.next();
					if(!list2.contains(users2.getId())){
						invigilatorsForExamTo=new ExamInvigilatorExcemptionDatewiseTO();
						invigilatorsForExamTo.setId(users2.getId());
						String name="";
						if(users2.getEmployee().getFirstName()!=null && !users2.getEmployee().getFirstName().isEmpty()){
							name=name+users2.getEmployee().getFirstName();
						}
						if(users2.getEmployee().getMiddleName()!=null && !users2.getEmployee().getMiddleName().isEmpty()){
							name=name+users2.getEmployee().getMiddleName();
						}
						invigilatorsForExamTo.setName(name);
						if(users2.getEmployee().getDepartment()!=null && users2.getEmployee().getDepartment().getName()!=null){
							invigilatorsForExamTo.setDepartment(users2.getEmployee().getDepartment().getName());
						}
						if(invigilatorsForExamForm.getSession()!=null && invigilatorsForExamForm.getSession()!=null){
							invigilatorsForExamTo.setSession(invigilatorsForExamForm.getSession());
						}
						list.add(invigilatorsForExamTo);
					}
				}
			}
			return list;
		}
		
		
		public List<ExamInvigilatorExcemptionDatewiseTO> convertBosToTosExempted(List<ExamInviligatorExemptionDatewise> exempted) throws Exception{
			List<ExamInvigilatorExcemptionDatewiseTO> list=null;
			if(exempted!=null && !exempted.isEmpty()){
				list=new ArrayList<ExamInvigilatorExcemptionDatewiseTO>();
				Iterator<ExamInviligatorExemptionDatewise> iterator=exempted.iterator();
				ExamInvigilatorExcemptionDatewiseTO invigilatorsForExamTo=null;
				while (iterator.hasNext()) {
					ExamInviligatorExemptionDatewise exem = (ExamInviligatorExemptionDatewise) iterator.next();
					invigilatorsForExamTo=new ExamInvigilatorExcemptionDatewiseTO();
					invigilatorsForExamTo.setId(exem.getId());
					if(exem.getExamId()!=null && exem.getExamId().getId()>0){
						invigilatorsForExamTo.setExamId(String.valueOf(exem.getExamId().getId()));
					}
					if(exem.getTeacherId()!=null && exem.getTeacherId().getId()>0){
						invigilatorsForExamTo.setUserId(exem.getTeacherId().getId());
					}
					if(exem.getDate()!=null){
						invigilatorsForExamTo.setDate(CommonUtil.ConvertStringToDateFormat(exem.getDate().toString(),
								"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if(exem.getExaminationSessions()!=null){
						invigilatorsForExamTo.setSession(String.valueOf(exem.getExaminationSessions().getId()));
					}
					if(exem.getTeacherId().getDepartment()!=null && exem.getTeacherId().getDepartment().getName()!=null){
						invigilatorsForExamTo.setDepartment(exem.getTeacherId().getDepartment().getName());
					}
					invigilatorsForExamTo.setChecked("true");
					
					list.add(invigilatorsForExamTo);
				}
			}
			return list;
		}
		public Map<Integer, ExamInviligatorExemptionDatewise> convertBosToMap(
				List<ExamInviligatorExemptionDatewise> examInvigilatorsList) throws Exception{
			Map<Integer, ExamInviligatorExemptionDatewise> map=new HashMap<Integer, ExamInviligatorExemptionDatewise>();
			Iterator<ExamInviligatorExemptionDatewise> iterator=examInvigilatorsList.iterator();
			while (iterator.hasNext()) {
				ExamInviligatorExemptionDatewise examInvigilatorAvailable = (ExamInviligatorExemptionDatewise) iterator.next();
				map.put(examInvigilatorAvailable.getTeacherId().getId(), examInvigilatorAvailable);
			}
			return map;
		}

		public List<ExamInviligatorExemptionDatewise> convertTosToBos(Map<Integer,ExamInviligatorExemptionDatewise> listOfTeachersExemptedAlready, Date newDates,
				List<ExamInvigilatorExcemptionDatewiseTO> list, String examId, String locationId, String userId) throws Exception{
			List<ExamInviligatorExemptionDatewise> invigilatorsForExamBos=new ArrayList<ExamInviligatorExemptionDatewise>();
			
			Iterator<ExamInvigilatorExcemptionDatewiseTO> iterator=list.iterator();
			ExamInviligatorExemptionDatewise invigilatorsForExamBo=null;
			while (iterator.hasNext()) {
				ExamInvigilatorExcemptionDatewiseTO invExamTo = (ExamInvigilatorExcemptionDatewiseTO) iterator.next();
				
					invigilatorsForExamBo=new ExamInviligatorExemptionDatewise();
					//set teacher id
					Users user=new Users();
					user.setId(invExamTo.getId());
					
					invigilatorsForExamBo.setTeacherId(user);
					//set examId
					
					if(!listOfTeachersExemptedAlready.containsKey(invExamTo.getId()))
					{
					
					ExamDefinition examDef=new ExamDefinition();
					examDef.setId(Integer.parseInt(examId));
					invigilatorsForExamBo.setExamId(examDef);
					invigilatorsForExamBo.setDate(newDates);
					ExaminationSessions examinationSessions=new ExaminationSessions();
					examinationSessions.setId(Integer.parseInt(invExamTo.getSession()));
					invigilatorsForExamBo.setExaminationSessions(examinationSessions);
					//set remaing values
					invigilatorsForExamBo.setIsActive(true);
					invigilatorsForExamBo.setCreatedBy(userId);
					invigilatorsForExamBo.setModifiedBy(userId);
					invigilatorsForExamBo.setCreatedDate(new Date());
					invigilatorsForExamBo.setLastModifiedDate(new Date());
					invigilatorsForExamBos.add(invigilatorsForExamBo);
					}
			}
			return invigilatorsForExamBos;
		}


		/*public String createQueryForSearch(
				ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
			String query="from ExamInviligatorExemptionDatewise u where u.isActive=1 and u.examId.id="+Integer.parseInt(invigilatorsForExamForm.getExamId())+
					" and  u.workLocationId.id="+Integer.parseInt(invigilatorsForExamForm.getLocationId());
			if(invigilatorsForExamForm.getDeptId()!=null && !invigilatorsForExamForm.getDeptId().isEmpty()){
				query=query+" and u.userId.employee.department.id="+Integer.parseInt(invigilatorsForExamForm.getDeptId());
			}
		return query;
		}*/
		
		
		public StringBuilder createQueryForSearch(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
			StringBuilder query=new StringBuilder();
			query=query.append("from ExamInviligatorExemptionDatewise u where u.isActive=1 and u.examId.id="+Integer.parseInt(invigilatorsForExamForm.getExamId())+
					" and u.teacherId.employee.workLocationId.id="+Integer.parseInt(invigilatorsForExamForm.getLocationId()));
			if(invigilatorsForExamForm.getDeptId()!=null && !invigilatorsForExamForm.getDeptId().isEmpty()){
				query=query.append(" and u.teacherId.employee.department.id="+Integer.parseInt(invigilatorsForExamForm.getDeptId()));
			}
			if((invigilatorsForExamForm.getFromDate()!=null && !invigilatorsForExamForm.getFromDate().isEmpty())&& (invigilatorsForExamForm.getToDate()!=null && !invigilatorsForExamForm.getToDate().isEmpty())){
				query=query.append(" and u.date>='"+CommonUtil.ConvertStringToSQLDate(invigilatorsForExamForm.getFromDate())+"'");
			}
			if(invigilatorsForExamForm.getToDate()!=null && !invigilatorsForExamForm.getToDate().isEmpty()){
				query=query.append(" and u.date<='"+CommonUtil.ConvertStringToSQLDate(invigilatorsForExamForm.getToDate())+"'");
			}
			if(invigilatorsForExamForm.getSession()!=null && !invigilatorsForExamForm.getSession().isEmpty()){
				query=query.append(" and u.examinationSessions.id='"+Integer.parseInt(invigilatorsForExamForm.getSession())+"'");
			}
			query=query.append(" order by u.teacherId.employee.department.name, u.teacherId.employee.firstName");
		return query;
		}

		public List<ExamInvigilatorExcemptionDatewiseTO> convertBosToToslidt(
				List<ExamInviligatorExemptionDatewise> list) {
			List<ExamInvigilatorExcemptionDatewiseTO> invigilatorsForExamTos=null;
			if(list!=null && !list.isEmpty()){
				invigilatorsForExamTos=new ArrayList<ExamInvigilatorExcemptionDatewiseTO>();
				Iterator<ExamInviligatorExemptionDatewise> iterator=list.iterator();
				ExamInvigilatorExcemptionDatewiseTO invigilatorsForExamTo=null;
				while (iterator.hasNext()) {
					ExamInviligatorExemptionDatewise invigilatorsForExamBo = (ExamInviligatorExemptionDatewise) iterator.next();
					invigilatorsForExamTo=new ExamInvigilatorExcemptionDatewiseTO();
					invigilatorsForExamTo.setId(invigilatorsForExamBo.getId());
					String name="";
					if(invigilatorsForExamBo.getTeacherId().getEmployee()!=null && invigilatorsForExamBo.getTeacherId().getEmployee().getFirstName()!=null && !invigilatorsForExamBo.getTeacherId().getEmployee().getFirstName().isEmpty()){
						name=name+invigilatorsForExamBo.getTeacherId().getEmployee().getFirstName();
					}
					if(invigilatorsForExamBo.getTeacherId().getEmployee()!=null && invigilatorsForExamBo.getTeacherId().getEmployee().getMiddleName()!=null && !invigilatorsForExamBo.getTeacherId().getEmployee().getMiddleName().isEmpty()){
						name=name+invigilatorsForExamBo.getTeacherId().getEmployee().getMiddleName();
					}
					invigilatorsForExamTo.setName(name);
					if(invigilatorsForExamBo.getTeacherId().getEmployee()!=null && invigilatorsForExamBo.getTeacherId().getEmployee().getDepartment()!=null && invigilatorsForExamBo.getTeacherId().getEmployee().getDepartment().getName()!=null){
						invigilatorsForExamTo.setDepartment(invigilatorsForExamBo.getTeacherId().getEmployee().getDepartment().getName());
					}
					if(invigilatorsForExamBo.getExamId().getName()!=null){
						invigilatorsForExamTo.setExamName(invigilatorsForExamBo.getExamId().getName());
					}
					if(invigilatorsForExamBo.getDate()!=null){
						invigilatorsForExamTo.setDate(CommonUtil.ConvertStringToDateFormat(invigilatorsForExamBo.getDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
					}
					if(invigilatorsForExamBo.getExaminationSessions()!=null){
						invigilatorsForExamTo.setSession(String.valueOf(invigilatorsForExamBo.getExaminationSessions().getId()));
					}
					invigilatorsForExamTos.add(invigilatorsForExamTo);
				}
			}
			return invigilatorsForExamTos;
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


}
