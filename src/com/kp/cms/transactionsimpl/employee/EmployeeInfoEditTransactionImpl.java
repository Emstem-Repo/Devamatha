package com.kp.cms.transactionsimpl.employee;

    import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EditMyProfile;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.EmployeeInfoBO;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.helpers.usermanagement.CreateUserHelper;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

	public class EmployeeInfoEditTransactionImpl  implements IEmployeeInfoEditTransaction {
			private static final Log log = LogFactory
					.getLog(EmployeeInfoEditTransactionImpl.class);

			public static volatile EmployeeInfoEditTransactionImpl obImpl = null;

			public static EmployeeInfoEditTransactionImpl getInstance() {
				if (obImpl == null) {
					obImpl = new EmployeeInfoEditTransactionImpl();
				}
				return obImpl;
			}
			

			public StringBuffer getSerchedEmployeeQuery(String Code,
					String uId, String FingerPrintId, int departmentId,
					int designationId, String Name, String Active, int streamId, String tempTeachingStaff,int empTypeId,int eid,int rid2,int principleid,int adminid,int testid)
					throws Exception {
					StringBuffer query = new StringBuffer(
							"from Employee e "
							+" where e.isActive = 1 ");
					if (Code != null && !StringUtils.isEmpty(Code.trim())) {
						query = query.append(" and e.code='"
								+ Code+"'");
					}
					if (uId != null	&& !StringUtils.isEmpty(uId.trim())) {
						query = query.append(" and e.uid='"
								+ uId+"'");
					}
					if  (rid2 ==principleid || rid2==adminid || rid2==testid)  {
						
					}
					else
						query = query.append(" and e.id='"
								+ eid+"'");
						
					
					
						
						
					if (FingerPrintId != null && !StringUtils.isEmpty(FingerPrintId.trim())) {
						query = query.append(" and e.fingerPrintId='"
								+ FingerPrintId+"'");
					}
					if (Name != null && !StringUtils.isEmpty(Name.trim())) {
						query = query.append(" and e.firstName like '"
								+ Name+"%'");
					}
					if(Active !=null)
					{
						if (Active.equals("1")) {
							
							query = query.append(" and e.active= 1 ");
						}
						else if (Active.equals("0")) {
							
							query = query.append(" and e.active= 0 ");
						}
					}
					if(tempTeachingStaff!=null)
					{
						if (tempTeachingStaff.equals("1")) {
							
							query = query.append(" and e.teachingStaff= 1 ");
						}
						else if (tempTeachingStaff.equals("0")) {
							
							query = query.append(" and e.teachingStaff= 0 ");
						}
						else if (tempTeachingStaff.equals("2")) {
						//	query = query.append(" and e.teachingStaff is not null");
							query = query.append(" and (e.teachingStaff= 0 OR e.teachingStaff= 1)");
						}
					}
					if (departmentId > 0) {
							query = query
									.append(" and e.department.id='"
											+ departmentId+"'");
						}
					if (streamId > 0) {
						query = query
								.append(" and e.streamId.id='"
										+ streamId+"'");
					}
					if (designationId > 0) {
							query = query
									.append(" and e.designation.id='"
											+ designationId+"'");
						}
					if (empTypeId > 0) {
						query = query
								.append(" and e.emptype.id='"
										+ empTypeId+"'");
					}
					
					query.append(" order by e.firstName");
				return query;
			}
			
			public List<Employee> getSerchedEmployee(StringBuffer query)
					throws Exception {
				Session session = null;
				List<Employee> empList;
			
				try {
					session = HibernateUtil.getSession();
					Query queri = session.createQuery(query.toString());
					empList = queri.list();
					
				} catch (Exception e) {
					throw new ApplicationException(e);
				}
				return empList;
			}
			
			public List<Department> getEmployeeDepartment() throws Exception 
			{
			Session session = null;
			List<Department> depList;
		
			try {
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from Department c where c.isActive=true");
				 depList=query.list();
				
				
			} catch (Exception e) {
				throw new ApplicationException(e);
			}
			return depList;
		}
			
			public List<Designation> getEmployeeDesignation() throws Exception 
			{
				Session session = null;
				List<Designation> DesList;
			
				try {
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from Designation c where c.isActive=true");
					DesList=query.list();
					
				} catch (Exception e) {
					throw new ApplicationException(e);
				}
				return DesList;
			
			}

			public Employee GetEmpDetails(EmployeeInfoEditForm objform) throws Exception {
				Session session=null;
				Employee employee=null;
				try {
				session=HibernateUtil.getSession();
				if(StringUtils.isNotEmpty(objform.getSelectedEmployeeId())){
					Query query=session.createQuery("from Employee e where e.id='"+objform.getSelectedEmployeeId() +"' and e.isActive=1");
					employee=(Employee) query.uniqueResult();
				}
			
			}catch (Exception exception) {
				
				throw new ApplicationException();
			}/*finally{
				if(session!=null){
					session.flush();
				}
			}*/
				return employee;
			}
			
			
			
			
			
			public Employee GetEditEmpDetails(String empId) throws Exception {
				Session session=null;
				Employee employee=null;
				try {
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from Employee e where e.id=" + empId);
				employee=(Employee) query.uniqueResult();
			}catch (Exception exception) {
				
				throw new ApplicationException();
			}/*finally{
				if(session!=null){
					session.flush();
				}
			}*/
				return employee;
			}
			
			
		public boolean saveEmpInfo(EmployeeInfoBO objBO) throws Exception {
			boolean result = false;
			Session session = null;
			Transaction txn = null;
			try {
				session = HibernateUtil.getSession();
				txn = session.beginTransaction();
				session.saveOrUpdate(objBO);
				txn.commit();
				session.flush();
				session.close();
				result = true;
			} catch (ConstraintViolationException e) {
				if (session.isOpen()) {
					txn.rollback();
				}
				log.error("Error during saveOnlineResume..." + e);
				throw new BusinessException(e);
			} catch (Exception e) {
				if (session.isOpen()) {
					txn.rollback();
				}
				log.error("Error during saveOnlineResume..." + e);
				throw new ApplicationException(e);
			}
			return result;
		}

		
		

		
			public Map<String, String> getCountryMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from Country c where c.isActive=true");
					List<Country> list=query.list();
					if(list!=null){
						Iterator<Country> iterator=list.iterator();
						while(iterator.hasNext()){
							Country country=iterator.next();
							if(country.getId() !=0 && country.getName()!=null && !country.getName().isEmpty())
							map.put(String.valueOf(country.getId()),country.getName());
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getDepartmentMap()
			 */
			@Override
			public Map<String, String> getDepartmentMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from Department d where d.isActive=true");
					List<Department> list=query.list();
					if(list!=null){
						Iterator<Department> iterator=list.iterator();
						while(iterator.hasNext()){
							Department department=iterator.next();
							if(department.getId()!=0 && department.getName()!=null && !department.getName().isEmpty())
							map.put(String.valueOf(department.getId()),department.getName());
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getDesignationMap()
			 */
			@Override
			public Map<String, String> getDesignationMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from Designation d where d.isActive=true");
					List<Designation> list=query.list();
					if(list!=null){
						Iterator<Designation> iterator=list.iterator();
						while(iterator.hasNext()){
							Designation designation=iterator.next();
							if(designation.getId()!=0 && designation.getName()!=null && !designation.getName().isEmpty())
							map.put(String.valueOf(designation.getId()),designation.getName());
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getNationalityMap()
			 */
			@Override
			public Map<String, String> getNationalityMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from Nationality n where n.isActive=true");
					List<Nationality> list=query.list();
					if(list!=null){
						Iterator<Nationality> iterator=list.iterator();
						while(iterator.hasNext()){
							Nationality nationality=iterator.next();
							if(nationality.getId()!=0 && nationality.getName()!=null && !nationality.getName().isEmpty())
							map.put(String.valueOf(nationality.getId()),nationality.getName());
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

		
			@Override
			public Map<String, String> getQualificationLevelMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmpQualificationLevel e where e.isActive=true and e.fixedDisplay=false order by e.displayOrder");
					List<EmpQualificationLevel> list=query.list();
					if(list!=null){
						Iterator<EmpQualificationLevel> iterator=list.iterator();
						while(iterator.hasNext()){
							EmpQualificationLevel empQualificationLevel=iterator.next();
							if(empQualificationLevel.getId()!=0 && empQualificationLevel.getName()!=null && !empQualificationLevel.getName().isEmpty())
							map.put(String.valueOf(empQualificationLevel.getId()),empQualificationLevel.getName());
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				return map;
			}

			
			
			public String getApplicationNumber(EmployeeSettings empSettings)throws Exception{
				String applicationNumber="";
				int application=0;
				Session session=null;
				Transaction tx=null;
				try {
					session=HibernateUtil.getSession();
					Query query=session.createQuery("select max(e.currentApplicationNo) from EmployeeSettings e ");
					String appNo=(String)query.uniqueResult();
					if(appNo!=null && !appNo.isEmpty()){
						application=Integer.parseInt(appNo)+1;
					}else{
						application++;
					}
					applicationNumber=String.valueOf(application);
					tx=session.beginTransaction();
					tx.begin();
					if(empSettings!=null && application>0)
						empSettings.setCurrentApplicationNo(applicationNumber);
					session.save(empSettings);
					tx.commit();
				} catch (Exception e) {
					tx.rollback();
					throw new ApplicationException(e);
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				
				return applicationNumber;
			}

			
			@Override
			public Map<String, String> getSubjectAreaMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from SubjectAreaBO s where s.isActive=true");
					List<SubjectAreaBO> list=query.list();
					if(list!=null){
						Iterator<SubjectAreaBO> iterator=list.iterator();
						while(iterator.hasNext()){
							SubjectAreaBO subjectAreaBO=iterator.next();
							if(subjectAreaBO.getId()!=0 && subjectAreaBO.getName()!=null && !subjectAreaBO.getName().isEmpty())
							map.put(String.valueOf(subjectAreaBO.getId()),subjectAreaBO.getName());
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

			@Override
			public List<EmpQualificationLevelTo> getQualificationFixedMap() throws Exception {
				Session session=null;
				List<EmpQualificationLevelTo> qualificationTo=new ArrayList<EmpQualificationLevelTo>();
//				Map<String,String> map=new LinkedHashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmpQualificationLevel e where e.isActive=true and e.fixedDisplay=true order by e.displayOrder");
					List<EmpQualificationLevel> list=query.list();
					if(list!=null){
						Iterator<EmpQualificationLevel> iterator=list.iterator();
						while(iterator.hasNext()){
							EmpQualificationLevel empQualificationLevel=iterator.next();
							EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
							if(empQualificationLevel.getId()!=0 && empQualificationLevel.getName()!=null && !empQualificationLevel.getName().isEmpty())
							{
							empQualificationLevelTo.setEducationId(String.valueOf(empQualificationLevel.getId()));
							empQualificationLevelTo.setQualification(empQualificationLevel.getName());
//							map.put(String.valueOf(empQualificationLevel.getId()),empQualificationLevel.getName());
							}
							qualificationTo.add(empQualificationLevelTo);
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				return qualificationTo;
			}
			
			
			public List<EmpAllowanceTO> getPayAllowanceFixedMap() throws Exception {
				Session session=null;
				List<EmpAllowanceTO> payscaleFixedTo=new ArrayList<EmpAllowanceTO>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmpAllowance e where e.isActive=true order by e.displayOrder");
					List<EmpAllowance> list=query.list();
					if(list!=null){
						Iterator<EmpAllowance> iterator=list.iterator();
						while(iterator.hasNext()){
							EmpAllowance empAllowance=iterator.next();
							EmpAllowanceTO empAllowanceTO=new EmpAllowanceTO();
							if(empAllowance.getId()!=0 && empAllowance.getName()!=null && !empAllowance.getName().isEmpty() && empAllowance.getDisplayOrder() != null)
							{
							empAllowanceTO.setId(empAllowance.getId());
							empAllowanceTO.setName(empAllowance.getName());
							if(empAllowance.getDisplayOrder()!=null && empAllowance.getDisplayOrder()>0)
								{
								empAllowanceTO.setDisplayOrder(empAllowance.getDisplayOrder());
								}
							}
							payscaleFixedTo.add(empAllowanceTO);
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				return payscaleFixedTo;
			}
			
			
			
			
			
			
			
			public boolean saveEmployee(Employee emp, EmployeeInfoEditForm employeeInfoEditForm)throws Exception {
				Session session=null;
				boolean flag=false;
				Transaction tx=null;
				try{
						session=HibernateUtil.getSession();
						tx=session.beginTransaction();
						tx.begin();
						session.saveOrUpdate(emp);
						tx.commit();
						if(!CMSConstants.LINK_FOR_CJC){
							if (employeeInfoEditForm.getPhoto() != null) {
								FileOutputStream fos = new FileOutputStream(CMSConstants.EMPLOYEE_PHOTO_FOLDER_PATH+"E"+emp.getId()+".jpg");
								fos.write(employeeInfoEditForm.getPhoto());
								fos.close();
								employeeInfoEditForm.setPhoto(null);
							}
						}
						flag=true;
				}catch(Exception e){
					tx.rollback();
					throw new ApplicationException(e);
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				return flag;
			}

			@Override
			public Map<String, String> getJobType() throws Exception {
				Session session=null;
				Map<String,String> jobTypeMap=new TreeMap<String, String>();
				try {
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmpJobType e where e.isActive=true");
					List<EmpJobType> empJobList=query.list();
					if(empJobList!=null){
						Iterator<EmpJobType> iterator=empJobList.iterator();
						while(iterator.hasNext()){
							EmpJobType emp=iterator.next();
							if(emp!=null && emp.getName()!=null && !emp.getName().isEmpty() && emp.getId()>0){
								jobTypeMap.put(String.valueOf(emp.getId()),emp.getName());
							}
						}
					}
				} catch (Exception e) {
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				jobTypeMap = (Map<String, String>) CommonUtil.sortMapByValue(jobTypeMap);
				return jobTypeMap;
			}

			
			@Override
			public Map<String, String> getQualificationMap() throws Exception {
				Session session=null;
				Map<String,String> map=new LinkedHashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmpQualificationLevel e where e.isActive=true order by e.displayOrder");
					List<EmpQualificationLevel> list=query.list();
					if(list!=null){
						Iterator<EmpQualificationLevel> iterator=list.iterator();
						while(iterator.hasNext()){
							EmpQualificationLevel empQualificationLevel=iterator.next();
							if(empQualificationLevel!=null && empQualificationLevel.getName()!=null && !empQualificationLevel.getName().isEmpty() && empQualificationLevel.getId()>0)
							map.put(String.valueOf(empQualificationLevel.getId()),empQualificationLevel.getName());
						}
					}
				}catch (Exception exception) {
					// TODO: handle exception
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				return map;
			}
			
			@SuppressWarnings("unchecked")
			public Map<String, String> getReligionMap() throws Exception {
				Session session=null;
				Map<String,String> map=new LinkedHashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from Religion r where r.isActive=true");
					List<Religion> list=query.list();
					if(list!=null){
						Iterator<Religion> iterator=list.iterator();
						while(iterator.hasNext()){
							Religion religion=iterator.next();
							if(religion!=null && religion.getName()!=null && !religion.getName().isEmpty() && religion.getId()>0)
							map.put(String.valueOf(religion.getId()),religion.getName());
						}
					}
				}catch (Exception exception) {
					// TODO: handle exception
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			public Map<String, String> getEmpTypeMap() throws Exception {
				Session session=null;
				Map<String,String> map=new LinkedHashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmpType r where r.isActive=true");
					List<EmpType> list=query.list();
					if(list!=null){
						Iterator<EmpType> iterator=list.iterator();
						while(iterator.hasNext()){
							EmpType empType=iterator.next();
							if(empType!=null && empType.getName()!=null && !empType.getName().isEmpty() && empType.getId()>0)
							map.put(String.valueOf(empType.getId()),empType.getName());
						}
					}
				}catch (Exception exception) {
					// TODO: handle exception
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

			public Map<String, String> getStateMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from State s where s.isActive=true");
					List<State> list=query.list();
					if(list!=null){
						Iterator<State> iterator=list.iterator();
						while(iterator.hasNext()){
							State state=iterator.next();
							if(state!=null && state.getName()!=null && !state.getName().isEmpty() && state.getId()>0)
							map.put(String.valueOf(state.getId()),state.getName());
						}
					}
				}catch (Exception exception) {
					// TODO: handle exception
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			
			public Map<String, String> getReportToMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from Employee e where e.isActive=true");
												
					List<Employee> list=query.list();
					if(list!=null){
						Iterator<Employee> iterator=list.iterator();
						while(iterator.hasNext()){
							Employee emp=iterator.next();
							if(emp.getId() !=0 && emp.getFirstName()!=null && !emp.getFirstName().isEmpty())
								map.put(String.valueOf(emp.getId()),emp.getFirstName());
						}
					}
				}catch (Exception exception) {
					// TODO: handle exception
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			public Map<String, String> getStreamMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmployeeStreamBO s where s.isActive=true");
					List<EmployeeStreamBO> list=query.list();
					if(list!=null){
						Iterator<EmployeeStreamBO> iterator=list.iterator();
						while(iterator.hasNext()){
							EmployeeStreamBO stream=iterator.next();
							if(stream!=null && stream.getName()!=null && !stream.getName().isEmpty() && stream.getId()>0)
							map.put(String.valueOf(stream.getId()),stream.getName());
						}
					}
				}catch (Exception exception) {
					// TODO: handle exception
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			public Map<String, String> getWorkLocationMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmployeeWorkLocationBO w where w.isActive=true");
					List<EmployeeWorkLocationBO> list=query.list();
					if(list!=null){
						Iterator<EmployeeWorkLocationBO> iterator=list.iterator();
						while(iterator.hasNext()){
							EmployeeWorkLocationBO workLoc=iterator.next();
							if(workLoc!=null && workLoc.getName()!=null && !workLoc.getName().isEmpty() && workLoc.getId()>0)
							map.put(String.valueOf(workLoc.getId()),workLoc.getName());
						}
					}
				}catch (Exception exception) {
					// TODO: handle exception
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			
			public Map<String, String> getTitleMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmpJobTitle e where e.isActive=true");
					List<EmpJobTitle> list=query.list();
					if(list!=null){
						Iterator<EmpJobTitle> iterator=list.iterator();
						while(iterator.hasNext()){
							EmpJobTitle empJobTitle=iterator.next();
							if(empJobTitle.getId() !=0 && empJobTitle.getTitle()!=null && !empJobTitle.getTitle().isEmpty())
							map.put(String.valueOf(empJobTitle.getId()),empJobTitle.getTitle());
						}
					}
				}catch (Exception exception) {
				
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			
			
			
			@SuppressWarnings("unchecked")
			public Map<String, String> getPayScaleMap(EmployeeInfoEditForm objform) throws Exception {
				Session session=null;
				Query query=null;
				
				String teachingStaff= objform.getTeachingStaff();
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					if(teachingStaff!=null && !teachingStaff.isEmpty())
					{
						query=session.createQuery("from PayScaleBO p where p.isActive=true and p.teachingStaff="+teachingStaff);
					}
					else
					{
						query=session.createQuery("from PayScaleBO p where p.isActive=true");	
					}
					List<PayScaleBO> list=query.list();
					if(list!=null){
						Iterator<PayScaleBO> iterator=list.iterator();
						while(iterator.hasNext()){
							PayScaleBO payscale=iterator.next();
							if(payscale!=null && payscale.getPayScale()!=null && !payscale.getPayScale().isEmpty() && payscale.getId()>0)
							map.put(String.valueOf(payscale.getId()),payscale.getPayScale());
						}
					}
				}catch (Exception exception) {
				
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			
			
			/*public List<EmpLeaveAllotTO> getEmpLeaveList(String empTypeId) throws Exception {
				Session session=null;
				List<EmpLeaveAllotTO> empLeaveTo=new ArrayList<EmpLeaveAllotTO>();
//				Map<String,String> map=new LinkedHashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from EmpLeaveAllotment r where r.empType.id=" + empTypeId);
					List<EmpLeave> list=query.list();
					if(list!=null){
						Iterator<EmpLeave> iterator=list.iterator();
						while(iterator.hasNext()){
							EmpLeave empleave=iterator.next();
							EmpLeaveTO emplTO=new EmpLeaveTO();
							emplTO.setEmpLeaveTypeName(String.valueOf(empleave.getEmpLeaveType().getName()));
							emplTO.setLeavesAllocated(String.valueOf(empleave.getLeavesAllocated()));
							emplTO.setLeavesSanctioned(String.valueOf(empleave.getLeavesSanctioned()));
							emplTO.setLeavesRemaining(String.valueOf(empleave.getLeavesRemaining()));
							empLeaveTo.add(emplTO);
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				return empLeaveTo;
			}*/

			
			public String getEmpId(String EmpId) throws ApplicationException {
				/*Session session=null;
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("Select r.empLeaveType.name,r.leavesAllocated, r.leavesRemaining, r.leavesSanctioned from EmpLeave r where r.employee=" + EmpId);
					String EmpId=(String) query.uniqueResult();
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}finally{
					if(session!=null){
						session.flush();
					}
				}*/
				return EmpId;
			}
			
			public String getScaleQueryByPayscaleId(String payScaleId) throws ApplicationException {
				Session session=null;
				try{
					session=HibernateUtil.getSession();
					String HQL = null;
					HQL = "select p.scale from PayScaleBO p where p.isActive=true and p.id="+payScaleId;
					Query query = session.createQuery(HQL);
					 
					return (String) query.uniqueResult();
					
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}/*finally{
					if(session!=null){
						session.flush();
					}
				}*/
				
			}
			public EmpType getWorkTimeListQueryByEmpTyptId(String query) throws Exception {
				Session session = null;
				EmpType WorkTimeList = null;
				try {
					session = HibernateUtil.getSession();
					Query WorkTimeListQuery=session.createQuery(query);
					WorkTimeList = (EmpType) WorkTimeListQuery.uniqueResult();
					return WorkTimeList;
				} catch (Exception e) {
					log.error("Error while retrieving selected candidates.." +e);
					throw  new ApplicationException(e);
				} /*finally {
					if (session != null) {
						session.flush();
					}
				}*/
				
			}
			
			public List<EmpLeaveAllotment> getEmpLeaveListQueryByEmpTyptId(String query) throws Exception {
				Session session = null;
				List<EmpLeaveAllotment> EmpLeaveAllotList = null;
				try {
					session = HibernateUtil.getSession();
					Query EmpLeaveAllotQuery=session.createQuery(query);
					EmpLeaveAllotList = EmpLeaveAllotQuery.list();
					return EmpLeaveAllotList;
				} catch (Exception e) {
					log.error("Error while retrieving selected candidates.." +e);
					throw  new ApplicationException(e);
				} /*finally {
					if (session != null){
						session.flush();
					}
				}*/
			}
			
			
			
			
			public boolean SaveEditEmpDetails(Employee employee) throws Exception {
				Session session=null;
				boolean flag=false;
				Transaction tx=null;
				try{
						session=HibernateUtil.getSession();
						tx=session.beginTransaction();
						tx.begin();
						session.save(employee);
						tx.commit();
						flag=true;
				}catch(Exception e){
					tx.rollback();
					throw new ApplicationException(e);
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				return flag;
			}
			
			
			public boolean checkCodeUnique(String code, String empId)
			throws Exception {
		Session session = null;
		boolean unique = true;

		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" from Employee e where e.isActive=true and e.code='"+code +"' and e.id !='"+empId +"'");
			

			List<Employee> list = query.list();
			if (list != null && !list.isEmpty())
				unique = false;

			//session.flush();
			// session.close();
		} catch (Exception e) {

			session.flush();
			// session.close();
			log.error("Error during checking duplicate code.." + e);
			throw new ApplicationException(e);
		}
		return unique;
	}
		public boolean checkUidUnique(String code, String empId)
		throws Exception {
			Session session = null;
			boolean unique = true;

			try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		// session =sessionFactory.openSession();
		session = HibernateUtil.getSession();
		Query query = session
				.createQuery("from Employee e where e.isActive=true and e.uid='"+code +"' and e.id !='"+empId +"'");
		

		List<Employee> list = query.list();
		if (list != null && !list.isEmpty())
			unique = false;

		//session.flush();
		// session.close();
			} catch (Exception e) {

		session.flush();
		// session.close();
		log.error("Error during checking duplicate code.." + e);
		throw new ApplicationException(e);
			}
			return unique;
		}
			
		public boolean checkFingerPrintIdUnique(String fingerPrintId, String empId)
		throws Exception {
			Session session = null;
			boolean unique = true;

			try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		// session =sessionFactory.openSession();
		session = HibernateUtil.getSession();
		Query query = session
				.createQuery(" from Employee e where e.isActive=true and e.fingerPrintId='"+fingerPrintId +"' and e.id !='"+empId +"'");
		

		List<Employee> list = query.list();
		if (list != null && !list.isEmpty())
			unique = false;

		//session.flush();
		// session.close();
			} catch (Exception e) {

		session.flush();
		// session.close();
		log.error("Error during checking duplicate code.." + e);
		throw new ApplicationException(e);
			}
			return unique;
		}


		@Override
		public int getInitializationMonth(int empTypeId) throws Exception {
			Session session=null;
			String empMonth=null;
			int month=0;
			try{
				session=HibernateUtil.getSession();
			    String queryStr="select emp.leaveInitializeMonth from EmpType emp where emp.id="+empTypeId;
			    Query query=session.createQuery(queryStr);
			    empMonth=query.uniqueResult().toString();
			    if(empMonth!=null && !empMonth.isEmpty())
			    {
			    Date date = new SimpleDateFormat("MMMMMMMMM", Locale.ENGLISH).parse(empMonth);
			    Calendar cal = Calendar.getInstance();
			    cal.setTime(date);
			    month = cal.get(Calendar.MONTH)+1;
			    }
			}catch(Exception e){
				log.error("Error while getting Employee Month.." +e);
				throw new ApplicationException();
			}
			/*finally{
				if(session!=null){
					session.flush();
				}
			}*/
			return month;
		}	
		
		public List getDataForQuery(String query) throws Exception {
			Session session = null;
			List list = null;
			try {
				session = HibernateUtil.getSession();
				Query LeaveQuery=session.createSQLQuery(query);
				list = LeaveQuery.list();
				return list;
			} catch (Exception e) {
				log.error("Error while retrieving LeaveQuery.." +e);
				throw  new ApplicationException(e);
			} /*finally {
				if (session != null) {
					session.flush();
				}
			}*/
			
		}
		@Override
		public void getEmployeeId(int userId,EmployeeInfoEditForm objform) throws Exception {
			Session session=null;
			String id="";
			try{
				session=HibernateUtil.getSession();
				String quer="select emp.id from Employee emp inner join emp.userses users where users.id="+userId+" and emp.id!=null";
				Query query=session.createQuery(quer);
				id=query.uniqueResult().toString();
				objform.setSelectedEmployeeId(id);
			}catch(Exception e){
				log.error("Error while getting Employee id.." +e);
			}
		}


		@Override
		public EditMyProfile getEditMyProfile(EmployeeInfoEditForm objform)
				throws Exception {
			Session session=null;
			EditMyProfile myProfile=null;
			try{
				session=HibernateUtil.getSession();
				String quer="from EditMyProfile my where my.empId="+objform.getEmployeeId();
				Query query=session.createQuery(quer);
				myProfile=(EditMyProfile)query.uniqueResult();
			}catch(Exception e){
				log.error("Error while getting EditMyProfile.." +e);
			}
			return myProfile;
		}

		
		public Users userExists(EmployeeInfoEditForm objform)throws Exception {
			Session session=null;
			Users user=null;
			try{
				session=HibernateUtil.getSession();
				String quer="from Users user where user.employee.id="+objform.getEmployeeId();
				Query query=session.createQuery(quer);
				user=(Users)query.uniqueResult();
			}catch(Exception e){
				log.error("Error while getting userExists.." +e);
			}
			return user;
     }
		
		public boolean updateUser(Users user) throws Exception {
			Session session=null;
			boolean flag=false;
			Transaction tx=null;
			try{
					session=HibernateUtil.getSession();
					tx=session.beginTransaction();
					tx.begin();
					session.save(user);
					tx.commit();
					flag=true;
			}catch(Exception e){
				tx.rollback();
				throw new ApplicationException(e);
			}finally{
				if(session!=null){
					session.flush();
				}
			}
			return flag;
		}
		

		@Override
		public boolean saveEditEmployee(Employee employee,
				EmployeeInfoEditForm objform,String mode) throws Exception {
			Session session=null;
			boolean flag=false;
			Transaction tx=null;
			try{
					session=HibernateUtil.getSession();
					tx=session.beginTransaction();
					tx.begin();
					session.saveOrUpdate(employee);
					if(objform.getEditMyProfile()!=null){
						if(mode.equalsIgnoreCase("new"))
						session.save(objform.getEditMyProfile());
						else
							session.update(objform.getEditMyProfile());
					}
					tx.commit();
					flag=true;
			}catch(Exception e){
				tx.rollback();
				flag=false;
				throw new ApplicationException(e);
			}finally{
				if(session!=null){
					session.flush();
				}
			}
			return flag;
		}		
			
		}

			
			
		




