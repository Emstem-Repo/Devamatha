package com.kp.cms.transactionsimpl.employee;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmployeeInfoBO;
import com.kp.cms.bo.employee.GuestEducationalDetails;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.employee.GuestPreviousExperience;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.forms.employee.GuestFacultyInfoForm;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;


 
public class GuestFacultyInfoImpl implements IGuestFacultyInfoTransaction {
			private static final Log log = LogFactory
					.getLog(GuestFacultyInfoImpl.class);

			public static volatile GuestFacultyInfoImpl obImpl = null;

			public static GuestFacultyInfoImpl getInstance() {
				if (obImpl == null) {
					obImpl = new GuestFacultyInfoImpl();
				}
				return obImpl;
			}
			

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getSerchedEmployeeQuery(int, int, java.lang.String, java.lang.String, int)
			 */
			public StringBuffer getSerchedEmployeeQuery(int departmentId,
					int designationId, String Name, String Active, int streamId)
					throws Exception {
					StringBuffer query = new StringBuffer(
							"from GuestFaculty e "
							+" where e.isActive = 1 ");
					
					
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
					
					query.append(" order by e.firstName");
				return query;
			}
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getSerchedEmployee(java.lang.StringBuffer)
			 */
			public List<GuestFaculty> getSerchedEmployee(StringBuffer query)
					throws Exception {
				Session session = null;
				List<GuestFaculty> empList;
			
				try {
					session = HibernateUtil.getSession();
					Query queri = session.createQuery(query.toString());
					empList = queri.list();
					
				} catch (Exception e) {
					throw new ApplicationException(e);
				}
				return empList;
			}
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getEmployeeDepartment()
			 */
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
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getEmployeeDesignation()
			 */
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

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#GetEmpDetails(com.kp.cms.forms.employee.GuestFacultyInfoForm)
			 */
			public GuestFaculty GetEmpDetails(GuestFacultyInfoForm objform) throws Exception {
				Session session=null;
				GuestFaculty employee=null;
				try {
				session=HibernateUtil.getSession();
				if(StringUtils.isNotEmpty(objform.getSelectedEmployeeId())){
					Query query=session.createQuery("from GuestFaculty e where e.id='"+objform.getSelectedEmployeeId() +"' and e.isActive=1");
					employee=(GuestFaculty) query.uniqueResult();
				}
			
			}catch (Exception exception) {
				
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
				}
			}
				return employee;
			}
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#GetEmpDetails(com.kp.cms.forms.employee.GuestFacultyInfoForm)
			 */
				public EmpOnlineResume GetGuestResumeDetails(GuestFacultyInfoForm objform) throws Exception {
					Session session=null;
					EmpOnlineResume empOnlineResume=null;
					try {
					session=HibernateUtil.getSession();
					if(StringUtils.isNotEmpty(objform.getApplicationNO())){
						Query query=session.createQuery("from EmpOnlineResume e where e.applicationNo='"+objform.getApplicationNO() +"' and e.isActive=1");
						empOnlineResume=(EmpOnlineResume) query.uniqueResult();
					}
				
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}finally{
					if(session!=null){
						session.flush();
					}
				}
					return empOnlineResume;
				}
			
			
			
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#GetEditEmpDetails(java.lang.String)
			 */
			public GuestFaculty GetEditEmpDetails(String empId) throws Exception {
				Session session=null;
				GuestFaculty employee=null;
				try {
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from GuestFaculty e where e.id=" + empId);
				employee=(GuestFaculty) query.uniqueResult();
			}catch (Exception exception) {
				
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
				}
			}
				return employee;
			}
			
			
		/**
		 * @param objBO
		 * @return
		 * @throws Exception
		 */
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
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getCountryMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getDepartmentMap()
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getDesignationMap()
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getNationalityMap()
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

		
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getQualificationLevelMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				return map;
			}
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getSubjectAreaMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getQualificationFixedMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				return qualificationTo;
			}
			
			
			
			
			
			
			
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#saveEmployee(com.kp.cms.bo.employee.GuestFaculty)
			 */
			public boolean saveEmployee(GuestFaculty emp, byte[] photo)throws Exception {
				Session session=null;
				boolean flag=false;
				Transaction tx=null;
				try{
						session=HibernateUtil.getSession();
						tx=session.beginTransaction();
						tx.begin();
						session.saveOrUpdate(emp);
						if(!CMSConstants.LINK_FOR_CJC){
							if (photo != null) {
								FileOutputStream fos = new FileOutputStream(CMSConstants.EMPLOYEE_PHOTO_FOLDER_PATH+"G"+emp.getId()+".jpg");
								fos.write(photo);
								fos.close();
							}
						}
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

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getJobType()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				jobTypeMap = (Map<String, String>) CommonUtil.sortMapByValue(jobTypeMap);
				return jobTypeMap;
			}

			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getQualificationMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				return map;
			}
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getReligionMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getStateMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getStreamMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getWorkLocationMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getTitleMap()
			 */
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
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}
			
			
			
			
			
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getEmpId(java.lang.String)
			 */
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
			
			
			
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#SaveEditEmpDetails(com.kp.cms.bo.employee.GuestFaculty)
			 */
			public boolean SaveEditEmpDetails(GuestFaculty employee) throws Exception {
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
			
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#checkCodeUnique(java.lang.String, java.lang.String)
			 */
			public boolean checkCodeUnique(String code, String empId)
			throws Exception {
		Session session = null;
		boolean unique = true;

		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" from GuestFaculty e where e.isActive=true and e.code='"+code +"' and e.id !='"+empId +"'");
			

			List<GuestFaculty> list = query.list();
			if (list != null && !list.isEmpty())
				unique = false;

			session.flush();
			// session.close();
		} catch (Exception e) {

			session.flush();
			// session.close();
			log.error("Error during checking duplicate code.." + e);
			throw new ApplicationException(e);
		}
		return unique;
	}
			
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#checkUidUnique(java.lang.String, java.lang.String)
		 */
		public boolean checkUidUnique(String code, String empId)
		throws Exception {
			Session session = null;
			boolean unique = true;

			try {
		
		session = HibernateUtil.getSession();
		Query query = session
				.createQuery("from GuestFaculty e where e.isActive=true and e.uid='"+code +"' and e.id !='"+empId +"'");
		

		List<GuestFaculty> list = query.list();
		if (list != null && !list.isEmpty())
			unique = false;

		session.flush();
			} catch (Exception e) {

		session.flush();
		log.error("Error during checking duplicate code.." + e);
		throw new ApplicationException(e);
			}
			return unique;
		}
			
		/**
		 * @param fingerPrintId
		 * @param empId
		 * @return
		 * @throws Exception
		 */
		public boolean checkFingerPrintIdUnique(String fingerPrintId, String empId)
		throws Exception {
			Session session = null;
			boolean unique = true;

			try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		// session =sessionFactory.openSession();
		session = HibernateUtil.getSession();
		Query query = session
				.createQuery(" from GuestFaculty e where e.isActive=true and e.fingerPrintId='"+fingerPrintId +"' and e.id !='"+empId +"'");
		

		List<GuestFaculty> list = query.list();
		if (list != null && !list.isEmpty())
			unique = false;

		session.flush();
		// session.close();
			} catch (Exception e) {

		session.flush();
		// session.close();
		log.error("Error during checking duplicate code.." + e);
		throw new ApplicationException(e);
			}
			return unique;
		}


		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getDataForQuery(java.lang.String)
		 */
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
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getEmployeeId(int, com.kp.cms.forms.employee.GuestFacultyInfoForm)
		 */
		@Override
		public void getEmployeeId(int userId,GuestFacultyInfoForm objform) throws Exception {
			Session session=null;
			String id="";
			try{
				session=HibernateUtil.getSession();
				String quer="select emp.id from GuestFaculty emp inner join emp.userses users where users.id="+userId+" and emp.id!=null";
				Query query=session.createQuery(quer);
				id=query.uniqueResult().toString();
				objform.setSelectedEmployeeId(id);
			}catch(Exception e){
				log.error("Error while getting GuestFaculty id.." +e);
			}
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#userExists(com.kp.cms.forms.employee.GuestFacultyInfoForm)
		 */
		public Users userExists(GuestFacultyInfoForm objform)throws Exception {
			Session session=null;
			Users user=null;
			try{
				session=HibernateUtil.getSession();
				String quer="from Users user where user.guest.id="+objform.getGuestId();
				Query query=session.createQuery(quer);
				user=(Users)query.uniqueResult();
			}catch(Exception e){
				log.error("Error while getting userExists.." +e);
			}
			return user;
     }
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#updateUser(com.kp.cms.bo.admin.Users)
		 */
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

		
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getDepartmentList()
		 */
		
		public List<Department> getDepartmentList() throws Exception {
			Session session = null;
			try {
				session = HibernateUtil.getSession();
				String query="from Department dept where dept.isActive=1 order by dept.name";
				Query hqlQuery=session.createQuery(query);
				List<Department> departmentList = hqlQuery.list();
				return  departmentList;
			} catch (Exception e) {
				log.error("Error while retrieving Department Details.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		
		}
		
		
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getDesignationList()
		 */
		
		public List<Designation> getDesignationList() throws Exception {
			Session session = null;
			try {
				session = HibernateUtil.getSession();
				String query="from Designation des where des.isActive=1";
				Query hqlQuery=session.createQuery(query);
				List<Designation> designationList = hqlQuery.list();
				return  designationList;
			} catch (Exception e) {
				log.error("Error while retrieving Designation Details.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		
		}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getEmpQualificationList()
		 */
		
		public List<EmpQualificationLevel> getEmpQualificationList() throws Exception {
			Session session = null;
			try {
				session = HibernateUtil.getSession();
				String query="from EmpQualificationLevel emp where emp.isActive=1";
				Query hqlQuery=session.createQuery(query);
				List<EmpQualificationLevel> qualificationList = hqlQuery.list();
				return  qualificationList;
			} catch (Exception e) {
				log.error("Error while retrieving EmpQualificationLevel Details.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getEmployeeEducationDetails(java.lang.String)
		 */
		public List<GuestEducationalDetails> getEmployeeEducationDetails(String empId)
		throws Exception {
	Session session = null;
	try {
		session = HibernateUtil.getSession();
		String query="from GuestEducationalDetails emp where emp.guest.id = '"+empId+"' order by emp.empQualificationLevel.displayOrder";
		Query hqlQuery=session.createQuery(query);
		List<GuestEducationalDetails> educationalDetails = hqlQuery.list();
		return  educationalDetails;
	} catch (Exception e) {
		log.error("Error while retrieving getEmployeeEducationDetails Details.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getEmployeeExperienceDetails(java.lang.String)
 */

public List<GuestPreviousExperience> getEmployeeExperienceDetails(String empId) throws Exception {
	Session session = null;
	try {
		session = HibernateUtil.getSession();
		String query="from GuestPreviousExperience emp where emp.guest.id = '"+empId+"'";
		Query hqlQuery=session.createQuery(query);
		List<GuestPreviousExperience> educationalDetails = hqlQuery.list();
		return  educationalDetails;
	} catch (Exception e) {
		log.error("Error while retrieving getEmployeeExperienceDetails Details.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
}

/* (non-Javadoc)
 * @see com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction#getDetailsForEmployee(java.lang.String)
 */
public GuestFaculty getDetailsForEmployee(String empId) throws Exception {
	
	Session session = null;
	try {
		session = HibernateUtil.getSession();
		String query="from GuestFaculty emp where emp.isActive=1 and emp.id = '"+empId+"'";
		Query hqlQuery=session.createQuery(query);
		GuestFaculty guestFaculty = (GuestFaculty)hqlQuery.uniqueResult();
		return  guestFaculty;
	} catch (Exception e) {
		log.error("Error while retrieving getDetailsForEmployee Details.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
}
@Override
public Map<Integer,String> getGuestFacultyMap() throws Exception {
	List<Object[]> guestFaculty = null;
	Map<Integer,String> guestFacultyMap = new HashMap<Integer, String>();
	try{
		Session session = HibernateUtil.getSession();
		String queryString = "select g.id,g.firstName from GuestFaculty g where g.isActive=1 and g.active=1";
		Query query = session.createQuery(queryString);
		guestFaculty =query.list();
		if(guestFaculty!=null){
			Iterator itr = guestFaculty.iterator();
			while(itr.hasNext()){
				Object[] obj = (Object[])itr.next();
				guestFacultyMap.put((Integer)obj[0], obj[1].toString());
			}
		}
	}catch(Exception exception){
		log.error("Error while getting guestFaculty.." +exception);
	}
	return guestFacultyMap;
}


@Override
public GuestFaculty getGuestFacultyBo(int guestId) throws Exception {
	GuestFaculty faculty = null;
	try{
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from GuestFaculty guest where guest.id="+guestId);
		faculty = (GuestFaculty)query.uniqueResult();
	}catch(Exception exception){
		log.error("Error while getting guestFaculty in getGuestFacultyBo.." +exception);
	}
	return faculty;
}


@Override
public boolean updateEditedGuestFacultyBankDetails(
		GuestFacultyInfoForm guestFacultyInfoForm) throws Exception {
	Session session =null;
	Transaction transaction =null;
	boolean flag = false;
	try{
		session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		transaction.begin();
		String queryString = "from GuestFaculty g where g.id="+guestFacultyInfoForm.getGuestId();
		Query query=session.createQuery(queryString);
		GuestFaculty faculty = (GuestFaculty)query.uniqueResult();
		if(guestFacultyInfoForm.getBankAccNo()!=null)
			faculty.setBankAccNo(guestFacultyInfoForm.getBankAccNo());
		if(guestFacultyInfoForm.getBankBranch()!=null)
			faculty.setBankBranch(guestFacultyInfoForm.getBankBranch());
		if(guestFacultyInfoForm.getBankIfscCode()!=null)
			faculty.setBankIfscCode(guestFacultyInfoForm.getBankIfscCode());
		if(guestFacultyInfoForm.getPanno()!=null)
			faculty.setPanNo(guestFacultyInfoForm.getPanno());
		session.update(faculty);
		transaction.commit();
		flag = true;
	}catch(Exception exception){
		log.error("Error while getting and updating guestFaculty in updateEditedGuestFacultyBankDetails.." +exception);
		flag = false;
	}
	return flag;
}
		
		}

			
			
		




