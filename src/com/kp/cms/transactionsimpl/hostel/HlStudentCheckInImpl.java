package com.kp.cms.transactionsimpl.hostel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.bo.hostel.HlStudentFacilityAllotted;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.hostel.HlStudentChInEditForm;
import com.kp.cms.helpers.hostel.HostelEntryHelper;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHlStudentCheckInTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class HlStudentCheckInImpl implements IHlStudentCheckInTransaction{
	private static final Log log=LogFactory.getLog(HlStudentCheckInImpl.class);
	

	
	public String getGenderHostel(HlStudentChInEditForm hlAdmissionForm) throws Exception {
		Session session=null;
		String str="";
		String gender=null;
		try {
			session=HibernateUtil.getSession();
			str=" select hl_hostel.hostel_for_gender" +
					" from hl_hostel " +
					" where hl_hostel.is_active=1"+
					" and hl_hostel.id="+hlAdmissionForm.getHostelName();
			
			gender=(String) session.createSQLQuery(str).uniqueResult();
			}catch (Exception e) {
				if (session != null){
					session.flush();
					//session.close();
				}	
				throw  new ApplicationException(e);
			}
		
			return gender;
	}

	@Override
	public String getGenderPersonaldata(HlStudentChInEditForm hlAdmissionForm) throws Exception {
		Session session=null;
		String str="";
		String gender=null;
		try {
			session=HibernateUtil.getSession();
			str=" select personal_data.gender" +
					" from student " +
					" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
					" and student.is_hide=0" +
					" and student.is_admitted=1" +
					" inner join course ON adm_appln.selected_course_id = course.id" +
					" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
					" where adm_appln.is_cancelled=0";
			if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()){
				str=str+" and student.register_no='"+hlAdmissionForm.getRegNo()+"'";
			}if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty()){
				str=str+" and adm_appln.appln_no='"+hlAdmissionForm.getApplNo()+"'";
			}if((hlAdmissionForm.getRegNo()==null || hlAdmissionForm.getRegNo().isEmpty()) && 
			   (hlAdmissionForm.getApplNo()==null || hlAdmissionForm.getApplNo().isEmpty())){
				return gender;
			}
			gender=(String) session.createSQLQuery(str).uniqueResult();
			}catch (Exception e) {
				if (session != null){
					session.flush();
					//session.close();
				}	
				throw  new ApplicationException(e);
			}
		
			return gender;
	}

	@Override
	public List<HlHostel> getHostelDeatils(String gender) throws Exception {
		log.debug("inside getHostelDeatils");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlHostel h where h.isActive = 1 " +
					" and h.name='"+gender+"'"+
					"order by h.name asc");
			List<HlHostel> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getHostelDeatils");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getHostelDeatils...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	public List<Object[]> getStudentDetails(HlStudentChInEditForm hlAdmissionForm)throws Exception {
		Session session=null;
		String str="";
		List<Object[]> list=null;
		try {
			session=HibernateUtil.getSession();
			str=" select student.id,personal_data.first_name,personal_data.last_name,course.id as id1,course.name" +
					" from student " +
					" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
					" and student.is_hide=0" +
					" and student.is_admitted=1" +
					" inner join course ON adm_appln.selected_course_id = course.id" +
					" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
					" where adm_appln.is_cancelled=0";
			if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()){
				str=str+" and student.register_no='"+hlAdmissionForm.getRegNo()+"'";
			}if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty()){
				str=str+" and adm_appln.appln_no='"+hlAdmissionForm.getApplNo()+"'";
			}
			list=session.createSQLQuery(str).list();
			}catch (Exception e) {
				if (session != null){
					session.flush();
					//session.close();
				}	
				throw  new ApplicationException(e);
			}
		
			return list;
	}
	
	
	
	public List<HlAdmissionBo> gethlAdmissionList(HlStudentChInEditForm hlAdmissionForm,String mode) throws Exception {
	    Session session = null;
		List name = null;
		try {
			session = HibernateUtil.getSession();
			String applino="from HlAdmissionBo hl "+
			         "where hl.studentId.isActive=1 "+
			         "and hl.studentId.isAdmitted=1 "+
			         "and hl.studentId.isHide=0 "+
			         "and hl.studentId.admAppln.isCancelled=0 and hl.isCancelled=0 and hl.isActive=1 and (hl.checkOut is null or hl.checkOut=0)";
			if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty() && StringUtils.isNumeric(hlAdmissionForm.getRegNo())){
				applino=applino+"and hl.studentId.registerNo='"+hlAdmissionForm.getRegNo()+"'";
			}if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty() && StringUtils.isNumeric(hlAdmissionForm.getApplNo())){
				applino=applino+" and hl.studentId.admAppln.applnNo='" +hlAdmissionForm.getApplNo()+"'";
			}if(hlAdmissionForm.getAcademicYear()!=null && !hlAdmissionForm.getAcademicYear().isEmpty() && StringUtils.isNumeric(hlAdmissionForm.getAcademicYear())){
				applino=applino+" and hl.academicYear="+hlAdmissionForm.getAcademicYear();
			}if(hlAdmissionForm.getHlApplicationNo()!=null && !hlAdmissionForm.getHlApplicationNo().isEmpty()){
				applino=applino+" and hl.applicationNo='"+hlAdmissionForm.getHlApplicationNo()+"'";
			}
			Query query = session.createQuery(applino);
			name = query.list();
		} catch (Exception e) {
			throw new ApplicationException();
		} finally {
			if (session != null) {
				session.flush();
		 	                  }
		          } 
		  return name;
		   }
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#duplicateCheck(com.kp.cms.forms.hostel.HlStudentChInEditForm, org.apache.struts.action.ActionErrors, javax.servlet.http.HttpSession)
	 */
	@Override
	public boolean duplicateCheck(HlStudentChInEditForm hlAdmissionForm,ActionErrors errors, HttpSession sessions) {
		Session session=null;
		boolean flag=false;
		HlAdmissionBo admissionBo;
		try{
			session=HibernateUtil.getSession();
			
			
			String s="from HlAdmissionBo admissionBo where admissionBo.isCancelled=0  and (admissionBo.checkOut=0 or admissionBo.checkOut is null)" +
					"and admissionBo.biometricId='"+hlAdmissionForm.getBiometricId() +"'";
			if(hlAdmissionForm.getHostelId()!=null && !hlAdmissionForm.getHostelId().isEmpty()){
				s=s+"and admissionBo.hostelId='"+hlAdmissionForm.getHostelId()+"'";
			}if(hlAdmissionForm.getRoomTypeName()!=null && !hlAdmissionForm.getRoomTypeName().isEmpty()){
				s=s+"and admissionBo.roomTypeId='"+hlAdmissionForm.getRoomTypeName()+"'";
			}if(hlAdmissionForm.getBlockId()!=null && !hlAdmissionForm.getBlockId().isEmpty()){
				s=s+"and admissionBo.roomId.hlBlock='"+hlAdmissionForm.getBlockId()+"'";
			}
			Query query=session.createQuery(s);
			admissionBo=(HlAdmissionBo)query.uniqueResult();
			if(admissionBo!=null && !admissionBo.toString().isEmpty()){
				if(hlAdmissionForm.getId()!=0){
			      if(admissionBo.getId()==hlAdmissionForm.getId()){
				    flag=false;
			      }else if(!admissionBo.getIsCancelled()){
				     flag=true;
				     errors.add("error", new ActionError("knowledgepro.hostel.biometricId.nameexit"));
			       }
			      else{
					   flag=true;
					   hlAdmissionForm.setId(admissionBo.getId());
					   throw new ReActivateException(admissionBo.getId());
				   }
				}else if(!admissionBo.getIsCancelled()){
					flag=true;
					errors.add("error", new ActionError("knowledgepro.hostel.biometricId.nameexit"));
				}
				else{
					  flag=false;
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.HOSTEL_ADMISSION_REACTIVATE));
				//saveErrors(request, errors);
				sessions.setAttribute("ReactivateId", hlAdmissionForm.getId());
			}
		}
		return flag;
	}

	public boolean addhlAdmission(HlAdmissionBo hlAdmissionBo, String mode)throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(hlAdmissionBo);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.merge(hlAdmissionBo);
			}
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#gethlAdmissionById(int)
	 */
	@Override
	public Object[] gethlAdmissionById(int id) throws Exception {
		Session session=null;
		Object[] admissionBo=null;
		try{
			session=HibernateUtil.getSession();
			
			String str=" select student.id,student.register_no,adm_appln.appln_no,personal_data.first_name,personal_data.last_name,course.id as id1,course.name," +
			" hl_hostel.name as name1,hl_hostel.id as id2,hl_room_type.name as name2,hl_room_type.id as id3,hl_admission.id as id4,hl_admission.academic_year," +
			" DATE_FORMAT(hl_admission.admission_date,'%d/%m/%Y') as admission_date,hl_admission.application_no,hl_admission.biometric_id,hl_admission.is_checked_in," +
			" DATE_FORMAT(hl_admission.check_in_date,'%d/%m/%Y') as check_in_date,hl_admission.room_id,hl_admission.bed_id " +
			" from hl_admission" +
			" inner join student ON hl_admission.student_id = student.id" +
			" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
			" and student.is_hide=0" +
			" and student.is_admitted=1" +
			" and adm_appln.is_cancelled=0" +
			" and hl_admission.is_cancelled=0" +
			" inner join course ON adm_appln.selected_course_id = course.id" +
			" and hl_admission.course_id = course.id" +
			" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
			" inner join hl_hostel ON hl_admission.hostel_id = hl_hostel.id" +
			" and hl_hostel.is_active=1" +
			" inner join hl_room_type ON hl_admission.roomtype_id = hl_room_type.id" +
			" and hl_room_type.is_active=1" +
			" where hl_admission.id="+id;
			
			admissionBo=(Object[]) session.createSQLQuery(str).uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			session.flush();
			session.close();
		}
		return admissionBo;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#getoccupaySeats(com.kp.cms.forms.hostel.HlStudentChInEditForm)
	 */
	@Override
	public BigDecimal getoccupaySeats(HlStudentChInEditForm hlAdmissionForm)	throws Exception {
		Session session=null;
		String str="";
		BigInteger occupay=null;
		BigDecimal available=null;
		try {
			session=HibernateUtil.getSession();
			str=" select count(hl_admission.student_id) as seats" +
					" from hl_admission " +
					" where hl_admission.is_active=1" +
					" and hl_admission.is_cancelled=0" +
				    " and hl_admission.roomtype_id='"+hlAdmissionForm.getRoomTypeName()+"'" +
				    " and hl_admission.hostel_id='"+hlAdmissionForm.getHostelName()+"'" +
				    " and hl_admission.academic_year="+hlAdmissionForm.getYear()+
				    " group by hl_admission.hostel_id,hl_admission.roomtype_id";
			
			occupay=(BigInteger) session.createSQLQuery(str).uniqueResult();
			if(occupay!=null){
			available=(new BigDecimal(occupay));
			}
			}catch (Exception e) {
				if (session != null){
					session.flush();
					//session.close();
				}	
				throw  new ApplicationException(e);
			}
			return available;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#getnumberOfSeat(com.kp.cms.forms.hostel.HlStudentChInEditForm)
	 */
	@Override
	public BigDecimal getnumberOfSeat(HlStudentChInEditForm hlAdmissionForm)	throws Exception {
		Session session=null;
		String str="";
		BigDecimal totalSit=null;
		String seatAvailable="";
		try {
			session=HibernateUtil.getSession();
			
		str=" select hl_available_seats.num_of_available_seats " +
			" from hl_available_seats " +
		    " where  hl_available_seats.hl_roomtype_id="+hlAdmissionForm.getRoomTypeName() +
		    " and hl_available_seats.hl_hostel_id="+hlAdmissionForm.getHostelName()+
		    " and hl_available_seats.academic_year="+hlAdmissionForm.getYear()+
		    " and hl_available_seats.is_active=1";
		  seatAvailable= (String)session.createSQLQuery(str).uniqueResult();
		  
		  if(seatAvailable!=null && !seatAvailable.isEmpty()){
			  totalSit=new BigDecimal(seatAvailable);
			}
			
			if(totalSit==null){
			str=" select sum(hl_room_type.no_of_occupants) as seats" +
					" from hl_hostel " +
					" inner join hl_room_type on hl_room_type.hl_hostel_id = hl_hostel.id" +
					" and hl_room_type.is_active=1" +
					" and hl_hostel.is_active=1" +
					" inner join hl_room on hl_room.hl_hostel_id = hl_hostel.id" +
					" and hl_room.hl_room_type_id = hl_room_type.id" +
					" and hl_room.is_active=1" +
				    " where  hl_room_type.id="+hlAdmissionForm.getRoomTypeName() +
				    " and hl_hostel.id="+hlAdmissionForm.getHostelName()+
				    " and hl_room_type.room_category='Student'"+
				    " group by hl_hostel.id,hl_room_type.id";
			totalSit=(BigDecimal) session.createSQLQuery(str).uniqueResult();
			        }
			}catch (Exception e) {
				if (session != null){
					session.flush();
					//session.close();
				}	
				throw  new ApplicationException(e);
			}
			return totalSit;
	}


	
	
	@Override
	public boolean duplicateApplNo(HlStudentChInEditForm hlAdmissionForm,	ActionErrors errors) throws Exception {
		Session session=null;
		boolean flag=false;
		HlAdmissionBo admissionBo;
		try{
			session=HibernateUtil.getSession();
			String quer=" from HlAdmissionBo admissionBo where admissionBo.isCancelled=0"
				+" and admissionBo.applicationNo='"+hlAdmissionForm.getHlApplicationNo()+"'";
			
			Query query=session.createQuery(quer);
			admissionBo=(HlAdmissionBo)query.uniqueResult();
			if(admissionBo!=null && !admissionBo.toString().isEmpty()){
				if(hlAdmissionForm.getId()!=0){
			      if(admissionBo.getId()==hlAdmissionForm.getId()){
				    flag=false;
			      }else if(!admissionBo.getIsCancelled()){
				     flag=true;
				     errors.add("error", new ActionError("knowledgepro.hostel.applicationno.exit"));
			       }
			      else{
					   flag=true;
					   hlAdmissionForm.setId(admissionBo.getId());
					   throw new ReActivateException(admissionBo.getId());
				   }
				}else if(!admissionBo.getIsCancelled()){
					flag=true;
					errors.add("error", new ActionError("knowledgepro.hostel.applicationno.exit"));
				}
				else{
					  flag=false;
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				 log.error("Error in getHostelDeatils...",e);
				 session.flush();
				 session.close();
				 throw  new ApplicationException(e);
			 }
		}
		return flag;
	}

	@Override
	public boolean deletehlAdmission(int id,
			HlStudentChInEditForm hlAdmissionForm) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getStudentNameClass(String regNo, String applNo, String academicYear, String hostelApplNo ,HttpServletRequest request) throws Exception {
	    Session session = null;
		List name = null;
		try {
			session = HibernateUtil.getSession();
			String applino="select hl.studentId.admAppln.personalData.firstName, "+ 
					 "hl.id, hl.roomId.id, hl.bedId.id, hl.roomId.hlBlock.id, hl.roomId.floorNo, "+
					 "hl.roomId.hlUnit.id,hl.hostelId.id, hl.applicationNo, "+
					 "hl.admittedDate, hl.biometricId, hl.checkInDate, hl.isCheckedIn, hl.roomTypeId.id, " +
					 " hl.studentId.admAppln.personalData.gender "+
			         "from HlAdmissionBo hl "+
			         "where hl.studentId.isActive=1 "+
			         "and hl.studentId.isAdmitted=1 "+
			         "and hl.studentId.isHide=0 "+
			         "and hl.studentId.admAppln.isCancelled=0";
			if(regNo!=null && !regNo.isEmpty() && StringUtils.isNumeric(regNo)){
				applino=applino+"and hl.studentId.registerNo='"+regNo+"'";
			}if(applNo!=null && !applNo.isEmpty() && StringUtils.isNumeric(applNo)){
				applino=applino+" and hl.studentId.admAppln.applnNo="+applNo;
			}if(academicYear!=null && !academicYear.isEmpty() && StringUtils.isNumeric(academicYear)){
				applino=applino+" and hl.academicYear="+academicYear;
			}if(hostelApplNo!=null && !hostelApplNo.isEmpty()){
				applino=applino+" hl.applicationNo="+hostelApplNo;
			}
			Query query = session.createQuery(applino);
			name = query.list();
		} catch (Exception e) {
			throw new ApplicationException();
		} finally {
			if (session != null) {
				session.flush();
		 	                  }
		          } 
		  return name;
		   }
	
	public List<HlFacility> getAllFacility() throws Exception {
		log.info("Inside of getAllFacility of CheckinTransactionImpl");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<HlFacility> facilityList = session.createQuery("from HlFacility h where h.isActive = 1 order by h.id").list();
			log.info("End of getAllFacility of CheckinTransactionImpl");
			return facilityList;
		} catch (Exception e) {
			log.error("Error occured in getAllFacility of CheckinTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	public List<HlStudentFacilityAllotted> getHlStudentFacilityAllotted(String HlAdmissionId) throws Exception
	{
	log.info("Inside of getAllFacility of CheckinTransactionImpl");
	Session session = null;
	try {
		session = HibernateUtil.getSession();
		List<HlStudentFacilityAllotted> facilityList = session.createQuery("from HlStudentFacilityAllotted h where h.hlAdmissionId.id=" +HlAdmissionId+" order by h.id").list();
		log.info("End of getAllFacility of CheckinTransactionImpl");
		return facilityList;
	} catch (Exception e) {
		log.error("Error occured in getAllFacility of CheckinTransactionImpl");
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
}
	
	public List<HlRoomTypeFacility> getHlRoomTypeFacility(String RoomTypeName) throws Exception{
		log.info("Inside of getAllFacility of CheckinTransactionImpl");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<HlRoomTypeFacility> facilityList = session.createQuery("from HlRoomTypeFacility h where h.isActive = 1 and h.hlRoomType.id=" + RoomTypeName + " order by h.id").list();
			log.info("End of getAllFacility of CheckinTransactionImpl");
			return facilityList;
		} catch (Exception e) {
			log.error("Error occured in getAllFacility of CheckinTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}

	}
	
	@SuppressWarnings("null")
	public Map<Integer, String> getRoomsAvailable(int hstlName,int RoomType,String academicYear,int block,int unit,String floor) throws Exception {
		try {
			StringBuffer query = new StringBuffer("from HlRoom r where r.isActive=1");
			if (hstlName>0) 
				query = query.append(" and r.hlHostel='"+ hstlName+"'");
			if (RoomType>0)
				query = query.append(" and r.hlRoomType='"+ RoomType+"'");
			if (block>0) 
				query = query.append(" and r.hlBlock='"+ block+"'");
			if (unit>0) 
				query = query.append(" and r.hlUnit='"+ unit+"'");
		    if (floor != null && !StringUtils.isEmpty(floor.trim())) 
				query = query.append(" and r.floorNo='"+ floor+"'");
			
			Session session = null;
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query.toString());
					
				
			List<HlRoom> roomList = query1.list();
			Map<Integer, String> roomMap = new HashMap<Integer, String>();
			Iterator<HlRoom> itr = roomList.iterator();
			HlRoom room;
			while (itr.hasNext()) {
				room = (HlRoom) itr.next();
				roomMap.put(room.getId(), room.getName());
				Map<Integer, String> beds=getBedsAvailable(room.getId(),Integer.parseInt(academicYear));
				if(beds.size()<=0){
					roomMap.remove(room.getId());
				}
			}
			session.flush();
			// session.close();
			roomMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomMap);
			return roomMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	public Map<Integer, String> getBedsAvailable(int roomId,int academicYear) throws Exception
	{
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlBeds b where b.hlRoom.id= "+roomId+" and b.id not in (select a.bedId from HlAdmissionBo a where a.isCancelled=0 and a.isActive=1 and (a.checkOut is null or a.checkOut=0) and  a.roomId="+roomId+" and a.academicYear="+academicYear+")");
			List<HlBeds> roomList = query.list();
			Map<Integer, String> bedMap = new HashMap<Integer, String>();
			Iterator<HlBeds> itr = roomList.iterator();
			HlBeds bed;

			while (itr.hasNext()) {
				bed = (HlBeds) itr.next();
				bedMap.put(bed.getId(), bed.getBedNo());
			}
			session.flush();
			// session.close();
			bedMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(bedMap);

			return bedMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
			}

	@Override
	public HlAdmissionBo gethlAdmission(HlStudentChInEditForm hlAdmissionForm,
			String mode) throws Exception {
		Session session = null;
		HlAdmissionBo bo = null;
		try{
			session = HibernateUtil.getSession();
			String applino="from HlAdmissionBo hl "+
			         "where hl.studentId.isActive=1 "+
			         "and hl.studentId.isAdmitted=1 "+
			         "and (hl.studentId.isHide=0 or hl.studentId.isHide is null) "+
			         "and hl.studentId.admAppln.isCancelled=0 and hl.isCancelled=0 and hl.isActive=1 and (hl.checkOut is null or hl.checkOut=0)";
			if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty() && StringUtils.isNumeric(hlAdmissionForm.getRegNo())){
				applino=applino+"and hl.studentId.registerNo='"+hlAdmissionForm.getRegNo()+"'";
			}if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty() && StringUtils.isNumeric(hlAdmissionForm.getApplNo())){
				applino=applino+" and hl.studentId.admAppln.applnNo='" +hlAdmissionForm.getApplNo()+"'";
			}if(hlAdmissionForm.getAcademicYear()!=null && !hlAdmissionForm.getAcademicYear().isEmpty() && StringUtils.isNumeric(hlAdmissionForm.getAcademicYear())){
				applino=applino+" and hl.academicYear="+hlAdmissionForm.getAcademicYear();
			}if(hlAdmissionForm.getHlApplicationNo()!=null && !hlAdmissionForm.getHlApplicationNo().isEmpty()){
				applino=applino+" and hl.applicationNo='"+hlAdmissionForm.getHlApplicationNo()+"'";
			}
			Query query = session.createQuery(applino);
			bo = (HlAdmissionBo) query.uniqueResult();
		
		}catch (Exception e) {
			session.close();
			throw new Exception(e);
		}finally{
			if(session != null){
				session.flush();
			}
		}
		return bo;
	}

	@Override
	public List<HostelTO> getHostelDetails() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlHostel h where h.isActive = 1 order by h.name asc");
			List<HlHostel> list = query.list();
			List<HostelTO> hostelTOList = HostelEntryHelper.getInstance().copyHostelBosToTos(list);
			session.flush();
			session.close();
			return hostelTOList;
		 } catch (Exception e) {
			 log.error("Error in getHostelDeatils...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	@Override
	public Map<Integer, String> getRoomTypeByHostelBYstudent(int hostelId)throws Exception {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlRoomType h where h.isActive = 1 and h.roomCategory='Student' and h.hlHostel.id = '"+ hostelId + "'");
			List<HlRoomType> roomTypeList = query.list();
			Map<Integer, String> roomTypeMap = new HashMap<Integer, String>();
			Iterator<HlRoomType> itr = roomTypeList.iterator();
			HlRoomType hlRoomType;

			while (itr.hasNext()) {
				hlRoomType = (HlRoomType) itr.next();
				roomTypeMap.put(hlRoomType.getId(), hlRoomType.getName());
			}
			session.flush();
			 session.close();
			roomTypeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomTypeMap);

			return roomTypeMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelEntryTransactions#getBlocks(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getBlocks(String hostelId) throws Exception {
		log.debug("inside getBlocks");
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
				session = HibernateUtil.getSession();
				if(hostelId!=null && !hostelId.trim().isEmpty()){
				Query query = session.createQuery("from HlBlocks h where h.isActive = 1 and h.hlHostel.id="+hostelId+" order by h.name asc");
				List<HlBlocks> list = query.list();
				if(list != null){
					Iterator<HlBlocks> iterator = list.iterator();
					while (iterator.hasNext()) {
						HlBlocks bo = (HlBlocks) iterator.next();
						if(bo.getId() != 0 && bo.getName() != null)
							map.put(bo.getId(), bo.getName());
					}
				}
				session.flush();
				session.close();
				log.debug("leaving getBlocks");
			}
			return map;
		 } catch (Exception e) {
			 log.error("Error in getBlocks...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHlStudentCheckInTransaction#getUnits(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getUnits(String blockId) throws Exception {
		log.debug("inside getUnits");
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			if(blockId!=null && !blockId.trim().isEmpty()){
				Query query = session.createQuery("from HlUnits h where h.isActive = 1 and h.blocks.id="+blockId+" order by h.name asc");
				List<HlUnits> list = query.list();
				if(list != null){
					Iterator<HlUnits> iterator = list.iterator();
					while (iterator.hasNext()) {
						HlUnits bo = (HlUnits) iterator.next();
						if(bo.getId() != 0 && bo.getName() != null)
							map.put(bo.getId(), bo.getName());
					}
				}
				session.flush();
				session.close();
			}
			log.debug("leaving getUnits");
			return map;
		 } catch (Exception e) {
			 log.error("Error in getUnits...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHlStudentCheckInTransaction#getFloorsByHostel(java.lang.String)
	 */
	public Map<Integer, String> getFloorsByHostel(String unitId) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlUnits h where h.isActive = 1 and  h.id = "+ unitId);
			HlUnits unit = (HlUnits)query.uniqueResult();
			
			Map<Integer, String> floorMap = new HashMap<Integer, String>();
			if(unit != null){
				int floorNo = unit.getFloorNo();
				for (int i = 1; i <= floorNo; i++) {
					floorMap.put(i, Integer.toString(i));
				}
			}
			session.flush();
			session.close();
			floorMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(floorMap);
			return floorMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.flush();
			session.close();
		}
		return new HashMap<Integer, String>();
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHlStudentCheckInTransaction#getRoomsByBlock(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Object[]> getRoomsByBlock(String hostelId, String block,
			String unit, String floorNo, String year, String roomTypeId, String studentId)
			throws Exception {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String query = "select hl_room.id as roomId,hl_room.name as roomName, hl_room_beds.id as bedId, hl_room_beds.bed_no as bedNo" +
							" from hl_room_beds " +
							" inner join hl_room on hl_room_beds.hl_room_id = hl_room.id " +
							" inner join hl_room_type ON hl_room.hl_room_type_id = hl_room_type.id " +
							" inner join hl_units ON hl_room.hl_unit_id = hl_units.id " +
							" inner join hl_blocks ON hl_units.hl_block_id = hl_blocks.id " +
							" inner join hl_hostel ON hl_blocks.hl_hostel_id = hl_hostel.id " +
							" left join hl_admission on hl_admission.bed_id=hl_room_beds.id " +
							" and hl_admission.is_active=1 " +
							" and hl_admission.is_cancelled=0 " +
							" and (is_checked_out=0 or is_checked_out is null) " +
							" and hl_admission.academic_year= " +year+
							" and hl_admission.student_id <> " +studentId+
							" where hl_hostel.id= " +hostelId+
							" and hl_room_beds.is_active=1 "+
							" and hl_room.is_active=1 " +
							" and hl_admission.id is null";
				if(block != null && !block.isEmpty())
					query = query + " and hl_blocks.id= "+block;
				if(unit != null && !unit.isEmpty())
					query = query +	" and hl_units.id= "+unit;
				if(roomTypeId != null && !roomTypeId.isEmpty())
					query = query + " and hl_room_type.id= "+roomTypeId; 
				if(floorNo != null && !floorNo.isEmpty())
					query = query + " and hl_room.floor_no= "+floorNo; 
			
				List<Object[]> list = session.createSQLQuery(query).list();
			session.flush();
			session.close();
			return list;
		}catch (Exception e) {
			throw new Exception(e);
		}
	}
}
