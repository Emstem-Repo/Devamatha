package com.kp.cms.transactionsimpl.employee;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.EmpReadAttendanceFileBO;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.employee.UploadEmpAttendanceTO;
import com.kp.cms.transactions.employee.IUploadEmpAttendanceTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class UploadEmpAttendanceTransactionImpl implements IUploadEmpAttendanceTxn {
	/**
	 * Singleton object of UploadEmpAttendanceTransactionImpl
	 */
	private static volatile UploadEmpAttendanceTransactionImpl uploadEmpAttendanceTransactionImpl = null;
	private static final Log log = LogFactory.getLog(UploadEmpAttendanceTransactionImpl.class);
	private UploadEmpAttendanceTransactionImpl() {
		
	}
	/**
	 * return singleton object of UploadEmpAttendanceTransactionImpl.
	 * @return
	 */
	public static UploadEmpAttendanceTransactionImpl getInstance() {
		if (uploadEmpAttendanceTransactionImpl == null) {
			uploadEmpAttendanceTransactionImpl = new UploadEmpAttendanceTransactionImpl();
		}
		return uploadEmpAttendanceTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IUploadEmpAttendanceTxn#saveUploadEmpAttendance(java.util.List, java.lang.String)
	 */
	@Override
	public boolean saveUploadEmpAttendance(List<UploadEmpAttendanceTO> results,
			String userId) throws Exception {
		log.debug("inside saveUploadEmpAttendance");
		Session session = null;
		Transaction transaction = null;
		UploadEmpAttendanceTO to;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			int inPunch=0;
			int outPunch=0;
			Object[] obj=(Object[])session.createQuery("select e.machineIdForOfflineINPunch,e.machineIdForOfflineOUTPunch from EmployeeSettings e").uniqueResult();
			if(obj!=null){
				if(obj[0]!=null)
					inPunch=Integer.parseInt(obj[0].toString());
				if(obj[1]!=null)
					outPunch=Integer.parseInt(obj[1].toString());
			}
			transaction = session.beginTransaction();
			transaction.begin();
			
			Iterator<UploadEmpAttendanceTO> tcIterator = results.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				to = tcIterator.next();
				Employee employee=new Employee();
				employee.setId(to.getEmpId());
				EmpReadAttendanceFileBO attBo=new EmpReadAttendanceFileBO();
				attBo.setCreatedBy(userId);
				attBo.setCreatedDate(new Date());
				attBo.setModifiedBy(userId);
				attBo.setLastModifiedDate(new Date());
				attBo.setTerminalId(to.getTerminalId());
				attBo.setFingerPrintId(String.valueOf(to.getFingerTypeId()));
				attBo.setLoginTime(to.getTime());
				attBo.setIsActive(true);
				attBo.setFunctionkey(to.getStatus());
				Calendar cal=Calendar.getInstance();
				String finalTime = to.getDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
				attBo.setLoginDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
				attBo.setEmployee(employee);
				session.save(attBo);
				if(!to.getStatus().equalsIgnoreCase("7")){
					
					if(inPunch==Integer.parseInt(to.getTerminalId())){// if MachineId is offtime In Punch machine then by default we should update Intime in emp Attendance
						
						EmpAttendance bo=(EmpAttendance)session.createQuery("from EmpAttendance e where e.employee.id="+to.getEmpId()+" and e.date='"+CommonUtil.ConvertStringToSQLDate(to.getDate())+"'").uniqueResult();
						if(bo==null){
							bo=new EmpAttendance();
							bo.setCreatedBy(userId);
							bo.setCreatedDate(new Date());
						}
						bo.setModifiedBy(userId);
						bo.setLastModifiedDate(new Date());
						bo.setEmployee(employee);
						bo.setIsActive(true);
						bo.setInTime(to.getTime());
						bo.setDate(CommonUtil.ConvertStringToSQLDate(to.getDate()));
						session.saveOrUpdate(bo);
						
					}else if(outPunch==Integer.parseInt(to.getTerminalId())){// if MachineId is offtime out Punch machine then by default we should update outtime in emp Attendance
						EmpAttendance bo=(EmpAttendance)session.createQuery("from EmpAttendance e where e.employee.id="+to.getEmpId()+" and e.date='"+CommonUtil.ConvertStringToSQLDate(to.getDate())+"'").uniqueResult();
						if(bo==null){
							bo=new EmpAttendance();
							bo.setCreatedBy(userId);
							bo.setCreatedDate(new Date());
						}
						bo.setModifiedBy(userId);
						bo.setLastModifiedDate(new Date());
						bo.setEmployee(employee);
						bo.setIsActive(true);
						bo.setDate(CommonUtil.ConvertStringToSQLDate(to.getDate()));
						bo.setOutTime(to.getTime());
						session.saveOrUpdate(bo);
					}else{// if MachineId is other than those two we should check employee time in and time out .if login_time is less than or equal to in time then we should update intime in emp attendance and vice versa
						boolean isSaturday=false;
						if(CommonUtil.checkIsSaturday(to.getDate())){
							isSaturday=true;
						}
						Object[] object=(Object[])session.createSQLQuery("select '"+ to.getTime()+ "' <= ifnull(employee.time_in_ends, emp_type.time_in_ends) as in_punch, " +
								"'"+ to.getTime()+ "' >= ifnull(employee.time_out, emp_type.time_out) as out_punch, " +
								"'"+ to.getTime()+ "' >= ifnull(employee.saturday_time_out, emp_type.saturday_time_out) as saturday_out_punch" +
								" from employee left join emp_type ON employee.emp_type_id = emp_type.id where employee.active=1 and employee.is_active=1 and fingerPrintId ='"+to.getFingerTypeId()+"'").uniqueResult();
						//if(to.getFingerTypeId()==79){
						
						if(object!=null){
							boolean intime=false;
							boolean outtime=false;
							boolean startdaytime=false;
							if(object[0] != null ){
								if(object[0].toString().equalsIgnoreCase("1"))
									intime=true;
							}
							if(object[1] != null ){
								if(object[1].toString().equalsIgnoreCase("1"))
									outtime=true;
							}
							if(object[2] != null ){
								if(object[2].toString().equalsIgnoreCase("1"))
									startdaytime=true;
							}
							EmpAttendance bo=(EmpAttendance)session.createQuery("from EmpAttendance e where e.employee.id="+to.getEmpId()+" and e.date='"+CommonUtil.ConvertStringToSQLDate(to.getDate())+"'").uniqueResult();
							if(bo==null){
								bo=new EmpAttendance();
								bo.setCreatedBy(userId);
								bo.setCreatedDate(new Date());
							}
							bo.setModifiedBy(userId);
							bo.setLastModifiedDate(new Date());
							bo.setEmployee(employee);
							bo.setDate(CommonUtil.ConvertStringToSQLDate(to.getDate()));
							bo.setIsActive(true);
							if(intime){
								if(bo.getId()==0){
									bo.setInTime(to.getTime());
									session.save(bo);
								}
							}else if(outtime || (startdaytime && isSaturday)){
								bo.setOutTime(to.getTime());
								session.saveOrUpdate(bo);
							}else{
								if(bo.getId()==0){
									bo.setInTime(to.getTime());
								}else
									bo.setOutTime(to.getTime());
								session.saveOrUpdate(bo);
							}
						}
						//}
					}
					
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving saveUploadEmpAttendance");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in saveUploadEmpAttendance impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in saveUploadEmpAttendance impl...", e);
			throw new ApplicationException(e);
		}
	}
}
