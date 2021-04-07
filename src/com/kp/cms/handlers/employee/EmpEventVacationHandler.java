package com.kp.cms.handlers.employee;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionError;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.employee.EmpEventVacation;
import com.kp.cms.bo.employee.EmpEventVacationDepartment;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.forms.employee.EmpEventVacationForm;
import com.kp.cms.forms.employee.EmpResumeSubmissionForm;
import com.kp.cms.helpers.admin.DepartmentEntryHelper;
import com.kp.cms.helpers.employee.EmpEventVacationHelper;
import com.kp.cms.to.employee.EmpEventVacationTo;
import com.kp.cms.transactions.admin.IDepartmentEntryTransaction;
import com.kp.cms.transactions.employee.IEmpEventVacation;
import com.kp.cms.transactionsimpl.admin.DepartmentEntryTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmpEventVacationImpl;
import com.kp.cms.utilities.CommonUtil;

public class EmpEventVacationHandler {
	
	private static volatile EmpEventVacationHandler instance=null;
	
	private EmpEventVacationHandler(){}
	
	/**
	 * @return
	 */
	public static EmpEventVacationHandler getInstance(){
		if(instance==null)
			instance=new EmpEventVacationHandler();
		return instance;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getDepartments() throws Exception {
		return EmpEventVacationImpl.getInstance().getDepartments();
	}

	/**
	 * @param empEventVacationForm
	 * @throws Exception
	 */
	public Boolean submitEvent(EmpEventVacationForm empEventVacationForm)throws Exception {
		EmpEventVacation empEventVacation=null;
		if(empEventVacationForm.getEmpEventVacation()!=null){
			empEventVacation=empEventVacationForm.getEmpEventVacation();
			empEventVacation.setIsActive(true);
			return EmpEventVacationImpl.getInstance().submitEvent(empEventVacation);
		}else if(empEventVacationForm.getId()!=null && !empEventVacationForm.getId().isEmpty() && empEventVacationForm.getEventMap()!=null
				&& empEventVacationForm.getMode()!=null && !empEventVacationForm.getMode().isEmpty()&& empEventVacationForm.getMode().equalsIgnoreCase("delete")){
			empEventVacation=empEventVacationForm.getEventMap().get(empEventVacationForm.getId());
			empEventVacation.setIsActive(false);
			EmployeeStreamBO stream=new EmployeeStreamBO();
			stream.setId(Integer.parseInt(empEventVacationForm.getStreamId()));
			empEventVacation.setStream(stream);
			empEventVacation.setModifiedBy(empEventVacationForm.getUserId());
			empEventVacation.setLastModifiedDate(new Date());
			return EmpEventVacationImpl.getInstance().submitEvent(empEventVacation);
		}else if(empEventVacationForm.getId()!=null && !empEventVacationForm.getId().isEmpty() && empEventVacationForm.getEventMap()!=null){
			EmpEventVacationHelper.getInstance().convertFormtoBo(empEventVacationForm);
			empEventVacation=empEventVacationForm.getEventMap().get(empEventVacationForm.getId());
			return EmpEventVacationImpl.getInstance().submitEvent(empEventVacation);
		}else
			return EmpEventVacationImpl.getInstance().submitEvent(EmpEventVacationHelper.getInstance().convertFormtoBo(empEventVacationForm));
		
	}

	/**
	 * @param empEventVacationForm
	 * @throws Exception
	 */
	public void getEmpEventVacationDetails(EmpEventVacationForm empEventVacationForm)throws Exception {
		List<EmpEventVacationTo> listTo=null;
		Map<String,EmpEventVacation> eventMap=new HashMap<String, EmpEventVacation>();
		List<EmpEventVacation> list=EmpEventVacationImpl.getInstance().getEmpVacationList();
		if(list!=null){
			Iterator<EmpEventVacation> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpEventVacation empEventVacation=iterator.next();
				if(empEventVacation!=null){
					if(empEventVacation.getId()>0){
						eventMap.put(String.valueOf(empEventVacation.getId()),empEventVacation);
					}
				}
			}
			if(eventMap!=null)
				empEventVacationForm.setEventMap(eventMap);
			listTo=EmpEventVacationHelper.getInstance().convertEmpEventBoToEmpEventTo(list);
		}
		if(listTo!=null)
			empEventVacationForm.setEmpTo(listTo);
		
	}

	public void editEmpEventVacation(EmpEventVacationForm empEventVacationForm)throws Exception {
		EmpEventVacation empEventVacation=null;
		if(empEventVacationForm.getId()!=null && !empEventVacationForm.getId().isEmpty())
		empEventVacation=EmpEventVacationImpl.getInstance().getEmpEventVacation(empEventVacationForm.getId());
		EmpEventVacationHelper.getInstance().convertBoToForm(empEventVacationForm,empEventVacation);
	}

	public boolean getEmpEventUnique(EmpEventVacationForm empEventVacationForm ,String mode)throws Exception {
		List<EmpEventVacation> empEventVacation=null;
		List<EmpEventVacation> empEventReactivation=null;
		
		boolean isAdded=false;
		int count=0;
		
		if(empEventVacationForm.getType()!=null && empEventVacationForm.getToDate()!=null && empEventVacationForm.getFromDate()!=null 
				&& empEventVacationForm.getTeachingStaff()!=null)
		{
			empEventReactivation= EmpEventVacationImpl.getInstance().getEmpReactivation(empEventVacationForm);
			
			Iterator<EmpEventVacation> iterator2=empEventReactivation.iterator();
			while(iterator2.hasNext()){
				EmpEventVacation emp1 = (EmpEventVacation) iterator2.next();
			
			if(emp1!=null && !emp1.getIsActive() && (emp1.getTeachingStaff()!=null)){
				
				empEventVacationForm.setId(String.valueOf(emp1.getId()));
				throw new ReActivateException();
				
			}
			}
		
		
			empEventVacation= EmpEventVacationImpl.getInstance().getEmpEventUnique(empEventVacationForm);
			Iterator<EmpEventVacation> iterator1=empEventVacation.iterator();
			while(iterator1.hasNext()){
				EmpEventVacation emp = (EmpEventVacation) iterator1.next();
			
	
		if(emp!=null && emp.getIsActive() && (emp.getTeachingStaff()!=null && emp.getTeachingStaff())
				&& empEventVacationForm.getMode()!=null && !empEventVacationForm.getMode().isEmpty() && !empEventVacationForm.getMode().equalsIgnoreCase("edit"))	
		{
			empEventVacationForm.setEmpEventVacation(emp);
			//count=EmpEventVacationHelper.getInstance().checkDepartmentUnique(empEventVacationForm,emp);
			count++;
			if(count>0)
			{
				String msg="One of the selected departments has an event assigned for the given date range";
				throw new DuplicateException(msg);
			}else
				throw new DuplicateException();
			}
		}}
		EmpEventVacation empEventVacation1=EmpEventVacationHelper.getInstance().convertFormtoBo(empEventVacationForm);
		isAdded=EmpEventVacationImpl.getInstance().submitEvent(empEventVacation1);
		return isAdded;
	}

	
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getStreams()throws Exception{
		// TODO Auto-generated method stub
		return EmpEventVacationImpl.getInstance().getStreamsMap();
	}

	/**
	 * @param StreamId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getFilteredDepartmentsStreamNames(String StreamId,String teachingStaff) throws Exception {
		List<Department> list = EmpEventVacationImpl.getInstance().getSearchedDepartmentStreamNames(StreamId,teachingStaff);
		Map<Integer, String> streamMap = EmpEventVacationHelper.getInstance().convertBoToForm(list);
		return streamMap;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getDepartmentNames(String teaching)throws Exception {
		List<Department> departments = EmpEventVacationImpl.getInstance().getSearchedDepartmentStreamNames(teaching);
		Map<Integer, String> streamMap = EmpEventVacationHelper.getInstance().populateBoToForm(departments);
		return streamMap;
	}
	


	}
