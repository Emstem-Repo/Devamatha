package com.kp.cms.transactionsimpl.hostel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.HostelExemptionBo;
import com.kp.cms.bo.hostel.HostelExemptionDetailsBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelExemptionForm;
import com.kp.cms.transactions.hostel.IHostelExemptionTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;


public class HostelExemptionTransactionImpl implements IHostelExemptionTransaction{
	private static final Log log = LogFactory.getLog(HostelExemptionTransactionImpl.class);
	public static volatile HostelExemptionTransactionImpl hostelExemptionTransactionImpl=null;
	
	/**
	 * @return
	 */
	public static HostelExemptionTransactionImpl getInstance(){
		if(hostelExemptionTransactionImpl==null){
			hostelExemptionTransactionImpl= new HostelExemptionTransactionImpl();
			}
		return hostelExemptionTransactionImpl;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelExemptionTransaction#getHostelStudentData(com.kp.cms.forms.hostel.HostelExemptionForm)
	 */
	public List<HlAdmissionBo> getHostelStudentData(HostelExemptionForm hostelExemptionForm)throws Exception {
	
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<HlAdmissionBo> hlAdmissionBos=null;
		try{
			
			String query="select h from HlAdmissionBo h where h.isActive=1 and h.roomId.hlUnit.blocks.isActive=1 " +
					     " and (h.isCancelled=0 or h.isCancelled is null) and h.isCheckedIn=1 " +
					     " and (h.checkOut=0 or h.checkOut is null) and h.hostelId.id="+Integer.parseInt(hostelExemptionForm.getHostelId())+
						 " and h.academicYear="+hostelExemptionForm.getYear();
			
			if(hostelExemptionForm.getBlockId()!=null && !hostelExemptionForm.getBlockId().isEmpty()){
				query=query+" and h.roomId.hlUnit.blocks.id="+hostelExemptionForm.getBlockId();
			}if(hostelExemptionForm.getUnitId()!=null && !hostelExemptionForm.getUnitId().isEmpty() ){
				query=query+" and h.roomId.hlUnit.id="+hostelExemptionForm.getUnitId();
			}if(hostelExemptionForm.getCourseId()!=null && !hostelExemptionForm.getCourseId().isEmpty()){
				query=query+" and h.courseId="+hostelExemptionForm.getCourseId();
			}if(hostelExemptionForm.getSemesterNo()!=null && !hostelExemptionForm.getSemesterNo().isEmpty() ){
				query=query+"  and h.studentId.classSchemewise.curriculumSchemeDuration.semesterYearNo="+hostelExemptionForm.getSemesterNo();
			}if(hostelExemptionForm.getRegisterNo()!=null && !hostelExemptionForm.getRegisterNo().isEmpty()){
				query=query+" and h.studentId.registerNo='"+hostelExemptionForm.getRegisterNo()+"'";
			}if(hostelExemptionForm.getSpacialId()!=null && !hostelExemptionForm.getSpacialId().isEmpty()){
				query=query+" and h.studentId.id in ( select studentId from ExamStudentBioDataBO where specializationId="+hostelExemptionForm.getSpacialId()+")";
			}if(hostelExemptionForm.getClassId()!=null && !hostelExemptionForm.getClassId().isEmpty()){
				query=query+" and h.studentId.classSchemewise.classes.id="+hostelExemptionForm.getClassId();
			}
			query=query+" order by h.roomId.hlBlock.id,h.roomId.hlUnit.id,h.studentId.registerNo";
			Query qury = session.createQuery(query);
			hlAdmissionBos=(List<HlAdmissionBo>)qury.list();
			return hlAdmissionBos;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 

	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelExemptionTransaction#saveHostelExemptionDetails(com.kp.cms.bo.hostel.HostelExemptionBo)
	 */
	public boolean saveHostelExemptionDetails(HostelExemptionBo bo) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isSaved=false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(bo != null){
						session.saveOrUpdate(bo);
						isSaved=true;
				}
			transaction.commit();
			session.flush();
			session.close();
			return isSaved;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
		
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelExemptionTransaction#getHostelExemptionData(com.kp.cms.forms.hostel.HostelExemptionForm)
	 */
	public Map<Integer, Integer> getHostelExemptionData(HostelExemptionForm hostelExemptionForm) throws Exception {
		Session session=null;
		Map<Integer,Integer> map=new HashMap<Integer, Integer>();
		Map<Integer,Integer> hlExpIdMap=new HashMap<Integer, Integer>();
		Map<Integer,Integer> inActveHlExpIdMap=new HashMap<Integer, Integer>();
		
		try{
			session=HibernateUtil.getSession();
			
			Query query=session.createQuery("select hlExd from HostelExemptionDetailsBo hlExd where " +
					" hlExd.hostelExemptionBo.hostelId="+hostelExemptionForm.getHostelId()+" and hlExd.hostelExemptionBo.fromDate="+"'"+CommonUtil.ConvertStringToSQLDate(hostelExemptionForm.getHolidaysFrom())+"'"+
					" and hlExd.hostelExemptionBo.toDate="+"'"+CommonUtil.ConvertStringToSQLDate(hostelExemptionForm.getHolidaysTo())+"'"+
					" and hlExd.hostelExemptionBo.fromSession="+"'"+hostelExemptionForm.getHolidaysFromSession()+"'"+
					" and hlExd.hostelExemptionBo.toSession="+"'"+hostelExemptionForm.getHolidaysToSession()+"'");
			List<HostelExemptionDetailsBo> list=query.list();
			if(list!=null){
				Iterator<HostelExemptionDetailsBo> iterator=list.iterator();
				while(iterator.hasNext()){
					HostelExemptionDetailsBo bo=(HostelExemptionDetailsBo)iterator.next();
					if(bo.getIsActive()){
						map.put(bo.getHlAdmissionBo().getId(),bo.getId());
						
					}else{
						inActveHlExpIdMap.put(bo.getId(), bo.getId());
					}
					hlExpIdMap.put(bo.getHlAdmissionBo().getId(),bo.getHostelExemptionBo().getId());
				}
				if(hlExpIdMap!=null && !hlExpIdMap.isEmpty())
				hostelExemptionForm.setHlExpIdMap(hlExpIdMap);
				if(inActveHlExpIdMap!=null && !inActveHlExpIdMap.isEmpty())
					hostelExemptionForm.setInActveHlExpIdMap(inActveHlExpIdMap);
			}
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}
		map = (Map<Integer, Integer>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	public HostelExemptionDetailsBo getInActiveHlExData(int id) throws Exception {
		Session session=null;
		HostelExemptionDetailsBo hlExe=null;
		try{
			session=HibernateUtil.getSession();
			String str="from HostelExemptionDetailsBo hlExe where hlExe.isActive=0 and hlExe.id="+id;
			Query query=session.createQuery(str);
			hlExe=(HostelExemptionDetailsBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting EventLoactionBiometricDetailsBo by id..." , e);
			session.flush();
			session.close();
		}
		return hlExe;
	}
	
	public boolean verifyRegisterNo(HostelExemptionForm hostelExemptionForm) throws Exception {
		log.debug("inside verifyRegisterNo");
		Session session = null;
		boolean verifyRegNo=false;
        HlAdmissionBo admissionBo=null;
		try {
			session = HibernateUtil.getSession();
			String strQuery="from HlAdmissionBo hl where hl.studentId.registerNo=:registrationNo and hl.academicYear=:academicYear and hl.isActive=1" +
					" and hl.isCancelled=0 and hl.isCheckedIn=1 and (hl.checkOut is null or hl.checkOut=0)";
			Query query = session.createQuery(strQuery);
			query.setString("registrationNo",  hostelExemptionForm.getRegisterNo());
			query.setString("academicYear",  hostelExemptionForm.getYear());
			admissionBo = (HlAdmissionBo) query.uniqueResult();
			if(admissionBo!=null)
			{
				verifyRegNo=true;	
			}
			//session.close();
			//sessionFactory.close();
			return verifyRegNo;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
}


