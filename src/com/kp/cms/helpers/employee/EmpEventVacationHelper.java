package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hdgf.streams.Stream;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.employee.EmpEventVacation;
import com.kp.cms.bo.employee.EmpEventVacationDepartment;
import com.kp.cms.forms.employee.EmpEventVacationForm;
import com.kp.cms.handlers.employee.EmpEventVacationHandler;
import com.kp.cms.to.employee.EmpEventVacationTo;
import com.kp.cms.utilities.CommonUtil;

public class EmpEventVacationHelper {
	private static volatile EmpEventVacationHelper instance=null;
	
	/**
	 * 
	 */
	private EmpEventVacationHelper(){
		
	}

	/**
	 * @return
	 */
	public static EmpEventVacationHelper getInstance() {
		// TODO Auto-generated method stub
		if(instance==null)
			instance=new EmpEventVacationHelper();
		return instance;
	}

	/**
	 * @param empEventVacationForm
	 * @return
	 */
	public EmpEventVacation convertFormtoBo(EmpEventVacationForm empEventVacationForm) {
		// TODO Auto-generated method stub
		EmpEventVacation empEventVacation=null;
		
		if(empEventVacationForm!=null){
			if(empEventVacationForm.getId()!=null && !empEventVacationForm.getId().isEmpty() && empEventVacationForm.getEventMap()!=null){
				empEventVacation=empEventVacationForm.getEventMap().get(empEventVacationForm.getId());
			}else{
				empEventVacation=new EmpEventVacation();
			}
			
			if(empEventVacationForm.getType()!=null && !empEventVacationForm.getType().isEmpty()){
				empEventVacation.setType(empEventVacationForm.getType());
			}
			if(empEventVacationForm.getBoIdDeptMap()!=null)
			{
				
			if(empEventVacationForm.getDepartment()!=null && empEventVacationForm.getDepartment().length>0){
				Set<EmpEventVacationDepartment> set=new HashSet<EmpEventVacationDepartment>();
				for(int count=0;count<empEventVacationForm.getDepartment().length;count++){
					EmpEventVacationDepartment empEventVacationDepartment=new EmpEventVacationDepartment();
					String departmentId=empEventVacationForm.getDepartment()[count];
					if(empEventVacationForm.getBoIdDeptMap().containsKey(Integer.parseInt(departmentId)))
					{
						empEventVacationDepartment.setId(empEventVacationForm.getBoIdDeptMap().get(Integer.parseInt(departmentId)));
						
						if(departmentId!=null && !departmentId.isEmpty()){
							Department department=new Department();
							department.setId(Integer.parseInt(departmentId));
							
							if(empEventVacationForm.getId()!=null && !empEventVacationForm.getId().isEmpty())
							{
							EmpEventVacation empvac= new EmpEventVacation();
							empvac.setId(Integer.parseInt(empEventVacationForm.getId()));
							empEventVacationDepartment.setEmpEventVacation(empvac);
							}
							empEventVacationDepartment.setDepartment(department);
							empEventVacationDepartment.setCreatedBy(empEventVacationForm.getUserId());
							empEventVacationDepartment.setCreatedDate(new Date());
							empEventVacationDepartment.setModifiedBy(empEventVacationForm.getUserId());
							empEventVacationDepartment.setLastModifiedDate(new Date());
						}
					}
					else
					{
						if(departmentId!=null && !departmentId.isEmpty()){
							Department department=new Department();
							department.setId(Integer.parseInt(departmentId));
							
							if(empEventVacationForm.getId()!=null && !empEventVacationForm.getId().isEmpty())
							{
							EmpEventVacation empvac= new EmpEventVacation();
							empvac.setId(Integer.parseInt(empEventVacationForm.getId()));
							empEventVacationDepartment.setEmpEventVacation(empvac);
							}
							empEventVacationDepartment.setDepartment(department);
							empEventVacationDepartment.setCreatedBy(empEventVacationForm.getUserId());
							empEventVacationDepartment.setCreatedDate(new Date());
							empEventVacationDepartment.setModifiedBy(empEventVacationForm.getUserId());
							empEventVacationDepartment.setLastModifiedDate(new Date());
						}
					}
					set.add(empEventVacationDepartment);
				}
				empEventVacation.setEmpEventVacationDepartment(set);
			}
			}
			
			if(empEventVacationForm.getFromDate()!=null && !empEventVacationForm.getFromDate().isEmpty()){
				empEventVacation.setFromDate(CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getFromDate()));
			}
			
			if(empEventVacationForm.getToDate()!=null && !empEventVacationForm.getToDate().isEmpty()){
				empEventVacation.setToDate(CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getToDate()));
			}
			
			if(empEventVacationForm.getDescription()!=null && !empEventVacationForm.getDescription().isEmpty()){
				empEventVacation.setDescription(empEventVacationForm.getDescription());
			}
			if(empEventVacationForm.getStreamId()!=null && !empEventVacationForm.getStreamId().isEmpty()){
				EmployeeStreamBO st= new EmployeeStreamBO();
				st.setId(Integer.parseInt(empEventVacationForm.getStreamId()));
				empEventVacation.setStream(st);
			}
			else if(empEventVacationForm.getStreamId()== null)
			{
				if(empEventVacationForm.getMode().equalsIgnoreCase("edit") && (empEventVacationForm.getTempStreamId()!=null && !empEventVacationForm.getTempStreamId().isEmpty()))
				{
					EmployeeStreamBO st= new EmployeeStreamBO();
					st.setId(Integer.parseInt(empEventVacationForm.getTempStreamId()));
					empEventVacation.setStream(st);
				}
			}
			if(empEventVacationForm.getTeachingStaff()!=null && !empEventVacationForm.getTeachingStaff().isEmpty()){
				String Value= empEventVacationForm.getTeachingStaff();
				if(Value.equals("1"))
					empEventVacation.setTeachingStaff(true);
				else
					empEventVacation.setTeachingStaff(false);
			}
			empEventVacation.setCreatedDate(new Date());
			empEventVacation.setCreatedBy(empEventVacationForm.getUserId());
			empEventVacation.setModifiedBy(empEventVacationForm.getUserId());
			empEventVacation.setLastModifiedDate(new Date());
			empEventVacation.setIsActive(true);
		}
		return empEventVacation;
	}

	/**
	 * @param list
	 * @return
	 */
	public List<EmpEventVacationTo> convertEmpEventBoToEmpEventTo(List<EmpEventVacation> list) {
		List<EmpEventVacationTo> listTo=new ArrayList<EmpEventVacationTo>();
		if(list!=null){
			Iterator<EmpEventVacation> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpEventVacation empEventVacation=iterator.next();
				if(empEventVacation!=null){
					EmpEventVacationTo empEventVacationTo=new EmpEventVacationTo();
					if(empEventVacation.getId()>0){
						empEventVacationTo.setId(String.valueOf(empEventVacation.getId()));
					}
					if(empEventVacation.getType()!=null && !empEventVacation.getType().isEmpty()){
						empEventVacationTo.setType(empEventVacation.getType());
					}
					if(empEventVacation.getToDate()!=null){
						empEventVacationTo.setToDate(CommonUtil.ConvertStringToDateFormat(empEventVacation.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if(empEventVacation.getFromDate()!=null){
						empEventVacationTo.setFromDate(CommonUtil.ConvertStringToDateFormat(empEventVacation.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if(empEventVacation.getStream()!=null){
							if(empEventVacation.getStream().getId()>0){
						empEventVacationTo.setStreamId(String.valueOf(empEventVacation.getStream().getId()));
					}}
					if(empEventVacation.getTeachingStaff()!=null ){
						
						String Value=String.valueOf(empEventVacation.getTeachingStaff());
						if(Value.equals("true"))
							empEventVacationTo.setTeachingStaff("Teaching");
						else
							empEventVacationTo.setTeachingStaff("Non-Teaching");
						 
					}
					listTo.add(empEventVacationTo);
				}
			}
		}
		
		return listTo;
	}

	/**
	 * @param empEventVacationForm
	 * @param empEventVacation 
	 * @throws Exception 
	 */
	public void convertBoToForm(EmpEventVacationForm empEventVacationForm, EmpEventVacation empEventVacation) throws Exception {
		Map<Integer,Integer> map=new HashMap<Integer, Integer>();
		if(empEventVacation!=null){
			if(empEventVacation.getId()>0)
			{
				empEventVacationForm.setId(String.valueOf(empEventVacation.getId()));
			}
			if(empEventVacation.getType()!=null && !empEventVacation.getType().isEmpty()){
				empEventVacationForm.setType(empEventVacation.getType());
			}
			if(StringUtils.isNotEmpty(String.valueOf(empEventVacation.getTeachingStaff()))){
				String Value= String.valueOf(empEventVacation.getTeachingStaff());
				if(Value.equals("true"))
					empEventVacationForm.setTeachingStaff("1");
				else
					empEventVacationForm.setTeachingStaff("0");
				 
			}
			
			if(empEventVacation.getToDate()!=null){
				empEventVacationForm.setToDate(CommonUtil.ConvertStringToDateFormat(empEventVacation.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
			}
			
			if(empEventVacation.getFromDate()!=null){
				empEventVacationForm.setFromDate(CommonUtil.ConvertStringToDateFormat(empEventVacation.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
			}
			
			if(empEventVacation.getDescription()!=null && !empEventVacation.getDescription().isEmpty()){
				empEventVacationForm.setDescription(empEventVacation.getDescription());
			}
			if(empEventVacation.getStream()!=null){
				if(empEventVacation.getStream().getId()>0){
					empEventVacationForm.setStreamId(String.valueOf(empEventVacation.getStream().getId()));
					empEventVacationForm.setTempStreamId(String.valueOf(empEventVacation.getStream().getId()));
					Map<Integer,String> deptMap = EmpEventVacationHandler.getInstance().getFilteredDepartmentsStreamNames(empEventVacationForm.getStreamId(),empEventVacationForm.getTeachingStaff());
					if(deptMap!=null){
						empEventVacationForm.setDeptMap(deptMap);
					}
				}
			}
			else
			{
				empEventVacationForm.setStreamId("-");
				Map<Integer,String> deptMap = EmpEventVacationHandler.getInstance().getDepartmentNames(empEventVacationForm.getTeachingStaff());
				if(deptMap!=null){
					empEventVacationForm.setDeptMap(deptMap);
				}
			}
			if(empEventVacation.getEmpEventVacationDepartment()!=null){
				
			
				Iterator<EmpEventVacationDepartment> iterator=empEventVacation.getEmpEventVacationDepartment().iterator();
				int size=empEventVacation.getEmpEventVacationDepartment().size();
				String departments[]=new String[size];
				Integer empEventVacationDeptId=0;
				int count=0;
				while(iterator.hasNext()){
					EmpEventVacationDepartment dept=iterator.next();
					if(dept!=null){
						
						if(dept.getId()>0)
						{
							empEventVacationDeptId=dept.getId();
						}
						if(dept.getDepartment()!=null){
							Department department=dept.getDepartment();
							if(department!=null){
								
								if(department.getId()>0){
									departments[count]=String.valueOf(department.getId());
									count++;
								}
							}
							map.put(department.getId(),empEventVacationDeptId);
						}}
				}
				empEventVacationForm.setBoIdDeptMap(map);
				empEventVacationForm.setDepartment(departments);
			}
		}
	}

	public int checkDepartmentUnique(EmpEventVacationForm empEventVacationForm, EmpEventVacation empEventVacation) throws Exception {
		int countDeptRepeat=0;
			if(empEventVacation.getEmpEventVacationDepartment()!=null && !empEventVacation.getEmpEventVacationDepartment().isEmpty()){
				if(empEventVacationForm.getEmpEventVacation().getEmpEventVacationDepartment()!=null & !empEventVacationForm.getEmpEventVacation().getEmpEventVacationDepartment().isEmpty()){
				Iterator<EmpEventVacationDepartment> iterator1=empEventVacationForm.getEmpEventVacation().getEmpEventVacationDepartment().iterator();
				while(iterator1.hasNext()){
				EmpEventVacationDepartment formlist=iterator1.next();
				if(formlist!=null){
					if(formlist.getDepartment()!=null){
						Department departmentFormList=formlist.getDepartment();
						if(departmentFormList!=null){
							if(departmentFormList.getId()>0)
							{
								Iterator<EmpEventVacationDepartment> iterator=empEventVacation.getEmpEventVacationDepartment().iterator();
								//int size=empEventVacation.getEmpEventVacationDepartment().size();
								while(iterator.hasNext()){
									EmpEventVacationDepartment dept=iterator.next();
									if(dept!=null){
										if(dept.getDepartment()!=null){
											Department department=dept.getDepartment();
											if(department!=null){
											   
												if(department.getId()>0)
												{
													
												if(departmentFormList.getId()==department.getId())
												{
													countDeptRepeat++;
												}
											}
										}
									}
								}
								
							}
		}
						}
					}
				}
				}}
			}else
				{
					countDeptRepeat=0;
				}
					
				
		return countDeptRepeat;
	}
	
	
	
	/**
	 * @param list
	 * @return
	 */
	public Map<Integer, String> convertBoToForm(List<Department> list)throws Exception {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		if(list!=null){
			Iterator<Department> iterator = list.iterator();
			while (iterator.hasNext()) {
				Department department = (Department) iterator.next();
				EmpEventVacationTo vacationTo= new EmpEventVacationTo();
				if(department!=null && department.getId() != 0){
						vacationTo.setDepartmentId(department.getId());
				}
				if(department!=null && department.getName() != null){
						vacationTo.setDepartmentName(department.getName());
				}
				if(department!=null && department.getEmployeeStreamBO() != null && department.getEmployeeStreamBO().getName() != null){
						vacationTo.setStreamName(department.getEmployeeStreamBO().getName());
				}else{
					vacationTo.setStreamName("-");
				}
				vacationTo.setDepartmentStreamNames(vacationTo.getDepartmentName().concat("("+vacationTo.getStreamName()+")"));
				map.put(vacationTo.getDepartmentId(), vacationTo.getDepartmentStreamNames());
			}
		}
		map = (Map<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	/**
	 * @param departments
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> populateBoToForm(List<Department> departments)throws Exception{
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		if(departments!=null){
			Iterator<Department> iterator =departments.iterator();
			while (iterator.hasNext()) {
				Department department = (Department) iterator.next();
				EmpEventVacationTo to = new EmpEventVacationTo();
				if(department.getId()!=0){
					to.setDepartmentId(department.getId());
				}
				if(department.getName()!=null){
					if(!department.getName().isEmpty()){
						to.setDepartmentName(department.getName());
					}
				}
				if(department.getEmployeeStreamBO()!=null){
					if(department.getEmployeeStreamBO().getName()!=null){
						if(!department.getEmployeeStreamBO().getName().isEmpty()){
							to.setStreamName(department.getEmployeeStreamBO().getName());
						}
					}
				}else if(department.getEmployeeStreamBO()==null){
					to.setStreamName("-");
				}
				to.setDepartmentStreamNames(to.getDepartmentName().concat("("+to.getStreamName()+")"));
				map.put(to.getDepartmentId(), to.getDepartmentStreamNames());
			}
		}
		return map;
	}
	
}


