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
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.bo.admin.HostelApplicationNumber;
import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.bo.hostel.HlAdmissionBookingWaitingBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.transactions.hostel.IHladmissionTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class HlAdmissionImpl implements IHladmissionTransaction{
	

	private static final Log log=LogFactory.getLog(HlAdmissionImpl.class);
	public static volatile HlAdmissionImpl invSubCategoryImpl=null;
	public static HlAdmissionImpl getInstance()
	{
		if(invSubCategoryImpl==null)
		{
			invSubCategoryImpl=new HlAdmissionImpl();
			return invSubCategoryImpl;
		}
		return invSubCategoryImpl;

    }
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#gethlAdmissionList(com.kp.cms.forms.hostel.HlAdmissionForm)
	 */
	@Override
	public List<Object[]> gethlAdmissionList(HlAdmissionForm hlAdmissionForm,String mode) throws Exception {
		Session session=null;
		String str="";
		List<Object[]> list=null;
		try {
			session=HibernateUtil.getSession();

			str=" select student.id,student.register_no,adm_appln.appln_no,personal_data.first_name,personal_data.last_name,course.id as id1,course.name," +
					" hl_hostel.name as name1,hl_hostel.id as id2,hl_room_type.name as name2,hl_room_type.id as id3,hl_admission.id as id4,hl_admission.academic_year,hl_admission.application_no" +
					" from student" +
					" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
					" and student.is_admitted=1 " +
					" and adm_appln.is_cancelled=0 " +
					" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
					" inner join course ON adm_appln.selected_course_id = course.id" +
					" and course.is_active=1" +
					" left join hl_admission on hl_admission.student_id = student.id" +
					" and hl_admission.course_id = course.id" +
					" and hl_admission.is_cancelled=0" +
					" and hl_admission.academic_year="+hlAdmissionForm.getYear()+
					" left join hl_hostel ON hl_admission.hostel_id = hl_hostel.id and hl_hostel.is_active=1" +
					" left join hl_room_type ON hl_admission.roomtype_id = hl_room_type.id" +
					" and hl_room_type.is_active=1" +
					" where (student.is_hide=0 or student.is_hide is null) ";
			if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()){
				str=str+" and student.register_no='"+hlAdmissionForm.getRegNo()+"'";
			}if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty()){
				str=str+" and adm_appln.appln_no='"+hlAdmissionForm.getApplNo()+"'";
			}if(!mode.equalsIgnoreCase("update")){
				if(hlAdmissionForm.getHostelName()!=null && !hlAdmissionForm.getHostelName().isEmpty()){
				str=str+" and hl_admission.hostel_id="+hlAdmissionForm.getHostelName();
			}if(hlAdmissionForm.getRoomTypeName()!=null && !hlAdmissionForm.getRoomTypeName().isEmpty()){
				str=str+" and hl_admission.roomtype_id="+hlAdmissionForm.getRoomTypeName();
			}	
		       }
			list=session.createSQLQuery(str).list();
						
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
			
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#duplicateCheck(com.kp.cms.forms.hostel.HlAdmissionForm, org.apache.struts.action.ActionErrors, javax.servlet.http.HttpSession)
	 */
	@Override
	public boolean duplicateCheck(HlAdmissionForm hlAdmissionForm,ActionErrors errors, HttpSession sessions) {
		Session session=null;
		boolean flag=false;
		HlAdmissionBo admissionBo;
		try{
			session=HibernateUtil.getSession();
			if(hlAdmissionForm.getAdmissionType().equalsIgnoreCase("New")){
				String quer=" from HlAdmissionBo admissionBo where admissionBo.isCancelled=0" ;
				if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()){
					quer=quer+" and admissionBo.studentId.registerNo='"+hlAdmissionForm.getRegNo()+"'";
				}if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty()){
					quer=quer+" and admissionBo.studentId.admAppln.applnNo='"+hlAdmissionForm.getApplNo()+"'";
				}if(hlAdmissionForm.getYear()!=null && !hlAdmissionForm.getYear().isEmpty()){
					quer=quer+" and admissionBo.academicYear='"+(Integer.parseInt(hlAdmissionForm.getYear())-1)+"'";
				}
				Query query=session.createQuery(quer);
				HlAdmissionBo admissionBo1 = (HlAdmissionBo)query.uniqueResult();
				if(admissionBo1 != null){
					if(hlAdmissionForm.getId()!=0){
					      if(admissionBo1.getId()==hlAdmissionForm.getId()){
						    flag=false;
					      }else{
						     flag=true;
						     errors.add("error", new ActionError("knowledgepro.hostel.student.exist"));
					       }
						}else {
							flag=true;
							errors.add("error", new ActionError("knowledgepro.hostel.student.exist"));
						}
				}
			}
			if(!flag){
				String quer=" from HlAdmissionBo admissionBo where admissionBo.isCancelled=0" ;
				if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()){
					quer=quer+" and admissionBo.studentId.registerNo='"+hlAdmissionForm.getRegNo()+"'";
				}if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty()){
					quer=quer+" and admissionBo.studentId.admAppln.applnNo='"+hlAdmissionForm.getApplNo()+"'";
				}if(hlAdmissionForm.getYear()!=null && !hlAdmissionForm.getYear().isEmpty()){
					quer=quer+" and admissionBo.academicYear='"+hlAdmissionForm.getYear()+"'";
				}
				Query query=session.createQuery(quer);
				admissionBo=(HlAdmissionBo)query.uniqueResult();
				if(admissionBo!=null && !admissionBo.toString().isEmpty()){
					if(hlAdmissionForm.getId()!=0){
						if(admissionBo.getId()==hlAdmissionForm.getId()){
							flag=false;
						}else if(!admissionBo.getIsCancelled()){
							flag=true;
							errors.add("error", new ActionError("knowledgepro.inventory.subcategory.nameexit"));
						}
						else{
							flag=true;
							throw new ReActivateException(admissionBo.getId());
						}
					}else if(!admissionBo.getIsCancelled()){
						flag=true;
						errors.add("error", new ActionError("knowledgepro.inventory.subcategory.nameexit"));
					}
					else{
						flag=false;
					} 
				}else
					flag=false;
			}
			
				
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

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#getStudentDetails(com.kp.cms.forms.hostel.HlAdmissionForm)
	 */
	@Override
	public List<Object[]> getStudentDetails(HlAdmissionForm hlAdmissionForm)throws Exception {
		Session session=null;
		String str="";
		List<Object[]> list=null;
		try {
			session=HibernateUtil.getSession();
			str=" select student.id,personal_data.first_name,personal_data.last_name,course.id as id1,course.name" +
					" from student " +
					" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
					" and (student.is_hide=0 or student.is_hide is null) " +
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

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#addhlAdmission(com.kp.cms.bo.admin.HlAdmissionBo, java.lang.String)
	 */
	@Override
	public boolean addhlAdmission(HlAdmissionBo hlAdmissionBo, String mode, HlAdmissionForm hlAdmissionForm)throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				if(hlAdmissionBo.getApplicationNo() == null || hlAdmissionBo.getApplicationNo().isEmpty()){
					HostelApplicationNumber number = (HostelApplicationNumber)session.createQuery("from HostelApplicationNumber h where h.isActive=1 and h.hlHostel.id="+hlAdmissionBo.getHostelId().getId()).uniqueResult();
					if(number.getLastNumber() == null){
						hlAdmissionBo.setApplicationNo(number.getPrefix()+number.getStartingNumber());
						number.setLastNumber(number.getStartingNumber()+1);
					}else{
						hlAdmissionBo.setApplicationNo(number.getPrefix()+number.getLastNumber());
						number.setLastNumber(number.getLastNumber()+1);
					}
				}
				session.save(hlAdmissionBo);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.merge(hlAdmissionBo);
			}
			hlAdmissionForm.setId(hlAdmissionBo.getId());
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
			throw new Exception(exception);
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
			" and (student.is_hide=0 or student.is_hide is null) " +
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
			" where hl_admission.id="+id+
			" and hl_admission.is_checked_in!=1";
			
			admissionBo=(Object[]) session.createSQLQuery(str).uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			session.flush();
			session.close();
		}
		return admissionBo;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#deletehlAdmission(int)
	 */
	@Override
	public boolean deletehlAdmission(int id,HlAdmissionForm hlAdmissionForm) throws Exception {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from HlAdmissionBo admission where admission.id="+id;
      	    HlAdmissionBo subcategoryBo=(HlAdmissionBo)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	    subcategoryBo.setIsCancelled(true);
      	    subcategoryBo.setCancelReason(hlAdmissionForm.getCancelReason());
      	    session.update(subcategoryBo);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting ValuatorCharges data...", e);
      	   }
       return true;
		}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#getoccupaySeats(com.kp.cms.forms.hostel.HlAdmissionForm)
	 */
	@Override
	public BigDecimal getoccupaySeats(HlAdmissionForm hlAdmissionForm)	throws Exception {
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
	 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#getnumberOfSeat(com.kp.cms.forms.hostel.HlAdmissionForm)
	 */
	@Override
	public BigDecimal getnumberOfSeat(HlAdmissionForm hlAdmissionForm)	throws Exception {
		Session session=null;
		String str="";
		BigDecimal totalSit=null;
		String seatAvailable="";
		try {
			session=HibernateUtil.getSession();
			
		str=" select hl_available_seats.num_of_available_seats " +
			" from hl_available_seats " +
		    " where  hl_available_seats.hl_roomtype_id='"+hlAdmissionForm.getRoomTypeName() +"'"+
		    " and hl_available_seats.hl_hostel_id='"+hlAdmissionForm.getHostelName()+"'"+
		    " and hl_available_seats.academic_year='"+hlAdmissionForm.getYear()+"'"+
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
				    " where  hl_room_type.id='"+hlAdmissionForm.getRoomTypeName() +"'"+
				    " and hl_hostel.id='"+hlAdmissionForm.getHostelName()+"'"+
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
	public String getGenderHostel(HlAdmissionForm hlAdmissionForm) throws Exception {
		Session session=null;
		String str="";
		String gender=null;
		try {
			session=HibernateUtil.getSession();
			str=" select hl_hostel.hostel_for_gender" +
					" from hl_hostel " +
					" where hl_hostel.is_active=1"+
					" and hl_hostel.id='"+hlAdmissionForm.getHostelName()+"'";
			
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
	public String getGenderPersonaldata(HlAdmissionForm hlAdmissionForm) throws Exception {
		Session session=null;
		String str="";
		String gender=null;
		try {
			session=HibernateUtil.getSession();
			str=" select personal_data.gender" +
					" from student " +
					" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
					" and (student.is_hide=0 or student.is_hide is null) " +
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

	@Override
	public boolean duplicateApplNo(HlAdmissionForm hlAdmissionForm,	ActionErrors errors) throws Exception {
		Session session=null;
		boolean flag=false;
		HlAdmissionBo admissionBo;
		try{
			if(hlAdmissionForm.getHlApplicationNo() != null && !hlAdmissionForm.getHlApplicationNo().isEmpty()){
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
			}
				
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
	public boolean duplicateCheckWaitingList(HlAdmissionForm hlAdmissionForm,
			ActionErrors errors, HttpSession httpSession) throws Exception {
		
		Session session=null;
		boolean flag=false;
		HlAdmissionBookingWaitingBo bookingWaitingBo = null;
		try{
			session=HibernateUtil.getSession();
			if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty() && hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty() ){
				String quer=" from HlAdmissionBookingWaitingBo waiting where" ;
				if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()){
					quer=quer+" waiting.studentId.registerNo='"+hlAdmissionForm.getRegNo()+"'";
				}if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty()){
					quer=quer+" and waiting.studentId.admAppln.applnNo='"+hlAdmissionForm.getApplNo()+"'";
				}if(hlAdmissionForm.getYear()!=null && !hlAdmissionForm.getYear().isEmpty()){
					quer=quer+" and waiting.academicYear='"+hlAdmissionForm.getYear()+"'";
				}
				if(hlAdmissionForm.getHlApplicationNo()!=null && !hlAdmissionForm.getHlApplicationNo().isEmpty()){
					quer=quer+" or waiting.applicationNo='"+hlAdmissionForm.getHlApplicationNo()+"'";
				}
				Query query=session.createQuery(quer);
				bookingWaitingBo=(HlAdmissionBookingWaitingBo) query.uniqueResult();
				flag=duplicateCheckErrorMsg(bookingWaitingBo,hlAdmissionForm,errors);
			}else if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()){
				String quer=" from HlAdmissionBookingWaitingBo waiting where" ;
				if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()){
					quer=quer+" waiting.studentId.registerNo='"+hlAdmissionForm.getRegNo()+"'";
				}
				if(hlAdmissionForm.getYear()!=null && !hlAdmissionForm.getYear().isEmpty()){
					quer=quer+" and waiting.academicYear='"+hlAdmissionForm.getYear()+"'";
				}
				if(hlAdmissionForm.getHlApplicationNo()!=null && !hlAdmissionForm.getHlApplicationNo().isEmpty()){
					quer=quer+" or waiting.applicationNo='"+hlAdmissionForm.getHlApplicationNo()+"'";
				}
				Query query=session.createQuery(quer);
				bookingWaitingBo=(HlAdmissionBookingWaitingBo) query.uniqueResult();
				flag=duplicateCheckErrorMsg(bookingWaitingBo,hlAdmissionForm,errors);
			}else if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty() ){
				String quer=" from HlAdmissionBookingWaitingBo waiting where" ;
				if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty()){
					quer=quer+" waiting.studentId.admAppln.applnNo='"+hlAdmissionForm.getApplNo()+"'";
				}if(hlAdmissionForm.getYear()!=null && !hlAdmissionForm.getYear().isEmpty()){
					quer=quer+" and waiting.academicYear='"+hlAdmissionForm.getYear()+"'";
				}
				if(hlAdmissionForm.getHlApplicationNo()!=null && !hlAdmissionForm.getHlApplicationNo().isEmpty()){
					quer=quer+" or waiting.applicationNo='"+hlAdmissionForm.getHlApplicationNo()+"'";
				}
				Query query=session.createQuery(quer);
				bookingWaitingBo=(HlAdmissionBookingWaitingBo) query.uniqueResult();
				flag=duplicateCheckErrorMsg(bookingWaitingBo,hlAdmissionForm,errors);
			}
		}catch(Exception e){
			if (session != null){
				session.flush();
				//session.close();
			}	
		}
		return flag;
	}

	public boolean duplicateCheckErrorMsg(HlAdmissionBookingWaitingBo bookingWaitingBo,HlAdmissionForm hlAdmissionForm,ActionErrors errors)
	{
		boolean flag=false;
		if(bookingWaitingBo!=null && !bookingWaitingBo.toString().isEmpty()){
			if(hlAdmissionForm.getId()!=0){
		      if(bookingWaitingBo.getId()==hlAdmissionForm.getId()){
			    flag=false;
		      }else if(!bookingWaitingBo.getIsActive()){
			     flag=true;
			     errors.add("error", new ActionError("knowledgepro.inventory.subcategory.nameexit"));
		       }
		      else{
				   flag=true;
				   hlAdmissionForm.setId(bookingWaitingBo.getId());
				   errors.add("error", new ActionError("knowledgepro.inventory.subcategory.nameexit"));
			   }
			}else if(!bookingWaitingBo.getIsActive()){
				flag=true;
				errors.add("error", new ActionError("knowledgepro.inventory.subcategory.nameexit"));
			}
			else{
				 flag=true;
				   hlAdmissionForm.setId(bookingWaitingBo.getId());
				   errors.add("error", new ActionError("knowledgepro.inventory.subcategory.nameexit"));
			 } 
		}else
			flag=false;
		
		return flag;
			
		
	}	
	

	@Override
	public boolean addhlAdmissionWaiting(
			HlAdmissionBookingWaitingBo hlAdmissionBo, String mode)
			throws Exception {
		
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

	@Override
	public long getWaitingListPriorityNo(HlAdmissionForm hlAdmissionForm) throws Exception {
		Session session=null;
		Long count;
		HlAdmissionBookingWaitingBo admissionBo;
		try{
			session=HibernateUtil.getSession();
			String quer="select count(bo.waitingListPriorityNo) from HlAdmissionBookingWaitingBo bo where bo.academicYear='"+hlAdmissionForm.getAcademicYear()+"' and bo.hostelId='"+hlAdmissionForm.getHostelId()+"' and bo.roomTypeId="+hlAdmissionForm.getHostelroomTypeId()+" and bo.isActive=1";
			Query query=session.createQuery(quer);
			count= (Long) query.uniqueResult();
				
		}catch(Exception e){
				 log.error("Error in getHostelDeatils...",e);
				 session.flush();
				 session.close();
				 throw  new ApplicationException(e);
			 }
		return count;
	
	}

	@Override
	public HlAdmissionBo checkStudentInHlAdmission(HlAdmissionForm admissionForm) throws Exception {
		Session session=null;
		Long count;
		HlAdmissionBo admissionBo;
		String quer;
		try{
			session=HibernateUtil.getSession();
			if(admissionForm.getRegNo()!=null && !admissionForm.getRegNo().isEmpty())
			{
			 quer="from HlAdmissionBo bo where bo.isCancelled=0 and bo.academicYear='"+admissionForm.getAcademicYear()+"' and bo.studentId.registerNo='"+admissionForm.getRegNo()+"' and bo.hostelId='"+admissionForm.getHostelId()+"' and bo.roomTypeId='"+admissionForm.getHostelroomTypeId()+"'";
			}
			else
			{
			 quer="from HlAdmissionBo bo where bo.isCancelled=0 and bo.academicYear='"+admissionForm.getAcademicYear()+"' and bo.studentId.admAppln.applnNo='"+admissionForm.getApplNo()+"' and bo.hostelId='"+admissionForm.getHostelId()+"' and bo.roomTypeId="+admissionForm.getHostelroomTypeId()+"";	
			}
			Query query=session.createQuery(quer);
			admissionBo= (HlAdmissionBo) query.uniqueResult();
				
		}catch(Exception e){
				 log.error("Error in getHostelDeatils...",e);
				 session.flush();
				 session.close();
				 throw  new ApplicationException(e);
			 }
		return admissionBo;
	}

	@Override
		public HlAdmissionBo getAdmissionId(String hostelId, String regNo,String academicYear) throws Exception {
			Session session=null;
			int studentId=0;
			HlAdmissionBo hlAdmissionBo=null;
			try{ 
				session=HibernateUtil.getSession();
				Query query=session.createQuery("select s.id from Student s where s.isActive=1 and (s.registerNo='"+regNo+"' or s.admAppln.applnNo="+Integer.parseInt(regNo)+")");
				studentId=(Integer)query.uniqueResult();
				Query query1=session.createQuery("from HlAdmissionBo h where h.isActive=1 and h.studentId.id="+studentId+"and h.hostelId.id="+hostelId+"and h.academicYear='"+academicYear+"'" +
						" and (h.isCancelled=0 or h.isCancelled is null) and (h.checkOut=0 or h.checkOut is null)");
				hlAdmissionBo=(HlAdmissionBo)query1.uniqueResult();
				
			}catch(Exception e){
				log.debug("Reactivate Exception", e);
				if(e instanceof ReActivateException){
					 log.error("Error in getHostelDeatils...",e);
					 session.flush();
					 session.close();
					 throw  new ApplicationException(e);
				 }
			}
			return hlAdmissionBo;
		}

		@Override
		public HlRoom getRoomIdAndRoomTypeId(String hostelId,int blockId,int unitId,String roomNo) throws Exception {
			Session session=null;
			HlRoom hlRoom=null;
			try{ 
				session=HibernateUtil.getSession();
				String str="from HlRoom r where r.isActive=1 and r.name='"+roomNo+"' and r.hlBlock.id="+blockId+
					"and r.hlUnit.id="+unitId+"and r.hlHostel.id='"+hostelId+"'";
				Query query2=session.createQuery(str);
				hlRoom=(HlRoom)query2.uniqueResult();
			}catch(Exception e){
				log.debug("Reactivate Exception", e);
				if(e instanceof ReActivateException){
					 log.error("Error in getHostelDeatils...",e);
					 session.flush();
					 session.close();
					 throw  new ApplicationException(e);
				 }
			}
			return hlRoom;
		}

		@Override
		public	HlBeds getRoomBedId(int roomId, String bedNo,int rTypeId,String hostelId) throws Exception {
			Session session=null;
			HlBeds hlbeds=null;
			HlAdmissionBo hlAdmissionBo=null;
			try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from HlBeds s where s.isActive=1 and s.bedNo='"+bedNo+"' and s.hlRoom.id="+roomId);
				hlbeds=(HlBeds)query.uniqueResult();
				//duplicate check
				if(hlbeds!=null){
				int bedId=hlbeds.getId();
				Query query1=session.createQuery("from HlAdmissionBo h where h.isActive=1 and (h.isCancelled=0 or h.isCancelled is null) and (h.checkOut=0 or h.checkOut is null)" +
						" and h.roomTypeId.id="+rTypeId+" and h.hostelId.id="+hostelId+"and h.roomId.id="+roomId+"and h.bedId.id="+bedId);
				hlAdmissionBo=(HlAdmissionBo)query1.uniqueResult();
				if(hlAdmissionBo!=null){
					hlbeds=null; 
				}
				}
				
			}catch(Exception e){
				log.debug("Reactivate Exception", e);
				if(e instanceof ReActivateException){
					 log.error("Error in getHostelDeatils...",e);
					 session.flush();
					 session.close();
					 throw  new ApplicationException(e);
				 }
			}
			return hlbeds;
		}

		@Override
		public boolean addUploadRoomDetails(List<HlAdmissionBo> results)throws Exception {
			Session session=null;
			Transaction transaction = null;
			boolean isAdded = false; 
			try{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				transaction.begin();
				if(results!=null && !results.isEmpty()){
					Iterator<HlAdmissionBo> iterator=results.iterator();
					while (iterator.hasNext()) {
						HlAdmissionBo hlAdmissionBo=iterator.next();
						 session.merge(hlAdmissionBo);
					}
				}
				transaction.commit();
				isAdded = true;
			}catch (Exception e) {
				if(transaction != null)
					transaction.rollback();
				isAdded = false;
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return isAdded;
		}

		@Override
		public int getStudentInWaitingListWithPriorityNo(
				HlAdmissionForm hlAdmissionForm,HttpSession httpSession) throws Exception {
			Session session=null;
			Integer count=null;
			List list=null;
			try{
				session=HibernateUtil.getSession();
				if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty() && hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty())
				{
					String quer="select bo.id,bo.waitingListPriorityNo from HlAdmissionBookingWaitingBo bo where  bo.isActive=1";
					if(hlAdmissionForm.getAcademicYear()!=null && !hlAdmissionForm.getAcademicYear().isEmpty())
					{
						quer=quer+"and bo.academicYear="+hlAdmissionForm.getAcademicYear();
					}
					if(hlAdmissionForm.getHostelId()!=null && !hlAdmissionForm.getHostelId().isEmpty())
					{
						quer=quer+"and bo.hostelId="+hlAdmissionForm.getHostelId();
					}
                	 quer=quer+"and bo.roomTypeId="+hlAdmissionForm.getHostelroomTypeId();
                    if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty())
                    {
                    	quer=quer+"and bo.studentId.registerNo='"+hlAdmissionForm.getRegNo()+"'";
                    }
                    if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty())
                    {
                    	quer=quer+"and bo.studentId.admAppln.applnNo='"+hlAdmissionForm.getApplNo()+"'";
                    }
				
				Query query=session.createQuery(quer);
				list= query.list();
				if(list!=null && !list.isEmpty())
				{
					Iterator iterator=list.iterator();
					while (iterator.hasNext()) {
						Object[] object = (Object[]) iterator.next();
                        httpSession.setAttribute("StudentInWaitingListId", object[0].toString());
                        count=Integer.parseInt(object[1].toString());  
					}
				}
				else
				{
				  count=0;
				  httpSession.setAttribute("StudentInWaitingListId", null);
				}
				
				}
				else if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty())
				{
					String quer="select bo.id,bo.waitingListPriorityNo from HlAdmissionBookingWaitingBo bo where  bo.isActive=1 ";
					if(hlAdmissionForm.getAcademicYear()!=null && !hlAdmissionForm.getAcademicYear().isEmpty())
					{
						quer=quer+"and bo.academicYear="+hlAdmissionForm.getAcademicYear();
					}
					if(hlAdmissionForm.getHostelId()!=null && !hlAdmissionForm.getHostelId().isEmpty())
					{
						quer=quer+"and bo.hostelId="+hlAdmissionForm.getHostelId();
					}
                	 quer=quer+"and bo.roomTypeId="+hlAdmissionForm.getHostelroomTypeId();
                    if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty())
                    {
                    	quer=quer+"and bo.studentId.registerNo='"+hlAdmissionForm.getRegNo()+"'";
                    }
				Query query=session.createQuery(quer);
				list= query.list();
				if(list!=null && !list.isEmpty())
				{
					Iterator iterator=list.iterator();
					while (iterator.hasNext()) {
						Object[] object = (Object[]) iterator.next();
                        httpSession.setAttribute("StudentInWaitingListId", object[0].toString());
                        count=Integer.parseInt(object[1].toString());  
					}
				}
				else
				{
				  count=0;
				  httpSession.setAttribute("StudentInWaitingListId", null);
				}
				}
			else if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty())
			{
					String quer="select bo.id,bo.waitingListPriorityNo from HlAdmissionBookingWaitingBo bo where  bo.isActive=1";
					if(hlAdmissionForm.getAcademicYear()!=null && !hlAdmissionForm.getAcademicYear().isEmpty())
					{
						quer=quer+"and bo.academicYear="+hlAdmissionForm.getAcademicYear();
					}
					if(hlAdmissionForm.getHostelId()!=null && !hlAdmissionForm.getHostelId().isEmpty())
					{
						quer=quer+"and bo.hostelId="+hlAdmissionForm.getHostelId();
					}
                	 quer=quer+"and bo.roomTypeId="+hlAdmissionForm.getHostelroomTypeId();
                	 
                	 if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty())
                     {
                     	quer=quer+"and bo.studentId.admAppln.applnNo='"+hlAdmissionForm.getApplNo()+"'";
                     }
				Query query=session.createQuery(quer);
				list= query.list();
				if(list!=null && !list.isEmpty())
				{
					Iterator iterator=list.iterator();
					while (iterator.hasNext()) {
						Object[] object = (Object[]) iterator.next();
                        httpSession.setAttribute("StudentInWaitingListId", object[0].toString());
                        count=Integer.parseInt(object[1].toString());  
					}
				}
				else
				{
				  count=0;
				  httpSession.setAttribute("StudentInWaitingListId", null);
				}
			}
			}catch(Exception e){
					 log.error("Error in getHostelDeatils...",e);
					 session.flush();
					 session.close();
					 throw  new ApplicationException(e);
				 }
		return count;
		}

		@Override
		public boolean resetStudentInWaitingList(int waitingId) throws Exception {
			Session session=null;
			Transaction transaction=null;
			boolean updateWaitingList=false;
			HlAdmissionBookingWaitingBo bookingWaitingBo;
			try{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				Query query=session.createQuery("from HlAdmissionBookingWaitingBo bo where bo.id="+waitingId+" and bo.isActive=1");
				bookingWaitingBo=(HlAdmissionBookingWaitingBo) query.uniqueResult();
				if(bookingWaitingBo!=null)
				{
				 bookingWaitingBo.setIsActive(false);
				 session.merge(bookingWaitingBo);
				 transaction.commit();
				}
				updateWaitingList=true;
			}catch(Exception e){
				 log.error("Error in resetStudentInWaitingList...",e);
					 session.flush();
					 session.close();
					 throw  new ApplicationException(e);
			}
			return updateWaitingList;
		}

		@Override
		public List<HlAdmissionBookingWaitingBo> getStudentPriorityNumberInWaitingList(
				HlAdmissionForm hlAdmissionForm) throws Exception {
			Session session=null;
			List<HlAdmissionBookingWaitingBo> list=null;
			try{
				session=HibernateUtil.getSession();
					String quer="select bo from HlAdmissionBookingWaitingBo bo where  bo.isActive=1";
					if(hlAdmissionForm.getYear()!=null && !hlAdmissionForm.getYear().isEmpty())
					{
						quer=quer+"and bo.academicYear="+hlAdmissionForm.getYear();
					}
					if(hlAdmissionForm.getHostelName()!=null && !hlAdmissionForm.getHostelName().isEmpty())
					{
						quer=quer+"and bo.hostelId="+hlAdmissionForm.getHostelName();
					}
                	quer=quer+"and bo.roomTypeId="+hlAdmissionForm.getRoomTypeName();
				Query query=session.createQuery(quer);
				list= query.list();
				session.close();
			}catch(Exception e){
					 log.error("Error in getHostelDeatils...",e);
					 session.flush();
					 session.close();
					 throw  new ApplicationException(e);
					 
				 }
		return list;
		}

		@Override
		public void updateWaitingList(
				HlAdmissionBookingWaitingBo bookingWaitingBo) throws Exception {
			Session session=null;
			Transaction transaction=null;
			try{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				 session.merge(bookingWaitingBo);
				 transaction.commit();
			}catch(Exception e){
				 log.error("Error in resetStudentInWaitingList...",e);
					 session.flush();
					 session.close();
					 throw  new ApplicationException(e);
			}
		}
		@Override
		public List<HlAdmissionBookingWaitingBo> checkStudentsAreThereInWaitingList(HlAdmissionForm hlAdmissionForm) throws Exception {
			Session session=null;
			Long count;
			HlAdmissionBookingWaitingBo admissionBo;
			List<HlAdmissionBookingWaitingBo> list=null;
			try{
				session=HibernateUtil.getSession();
				String quer="select bo from HlAdmissionBookingWaitingBo bo where  bo.isActive=1 ";
				if(hlAdmissionForm.getAcademicYear()!=null && !hlAdmissionForm.getAcademicYear().isEmpty())
				{
					quer=quer+"and bo.academicYear="+hlAdmissionForm.getAcademicYear();
				}
				if(hlAdmissionForm.getHostelId()!=null && !hlAdmissionForm.getHostelId().isEmpty())
				{
					quer=quer+"and bo.hostelId="+hlAdmissionForm.getHostelId();
				}
            	quer=quer+"and bo.roomTypeId="+hlAdmissionForm.getRoomTypeName();
			Query query=session.createQuery(quer);
			list= query.list();
			session.close();
					
			}catch(Exception e){
					 log.error("Error in getHostelDeatils...",e);
					 session.flush();
					 session.close();
					 throw  new ApplicationException(e);
				 }
			return list;
		
		}
		public Map<String,Integer> getHlBlocksMap(String hostelId)throws Exception{
			Session session=null;
			Map<String,Integer> map=new HashMap<String, Integer>();
			try{
				session=HibernateUtil.getSession();
				Query query = session.createQuery("from HlBlocks a where a.isActive=1 and a.hlHostel.id="+hostelId);
				List<HlBlocks> list=query.list();
				if(list!=null){
					Iterator<HlBlocks> iterator=list.iterator();
					while(iterator.hasNext()){
						HlBlocks hlBlocks=iterator.next();
						map.put(hlBlocks.getName(),hlBlocks.getId());
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

		@Override
		public Map<String, Map<String,Integer>> getHlUnitsMap(String hostelId)
				throws Exception {
			Session session=null;
			Map<String,Map<String,Integer>> map=new HashMap<String, Map<String,Integer>>();
			try{
				session=HibernateUtil.getSession();
				Query query = session.createQuery("from HlBlocks a where a.isActive=1 and a.hlHostel.id="+hostelId);
				List<HlBlocks> list=query.list();
				if(list!=null){
					Iterator<HlBlocks> iterator=list.iterator();
					while(iterator.hasNext()){
						HlBlocks hlBlocks=iterator.next();
						Query query1 = session.createQuery("from HlUnits a where a.isActive=1 and a.blocks.id="+hlBlocks.getId());
						List<HlUnits> list1=query1.list();
						Map<String,Integer> unitsMap=new HashMap<String, Integer>();
						if(list1!=null){
							Iterator<HlUnits> iterator2=list1.iterator();
							while (iterator2.hasNext()) {
								HlUnits hlUnits = (HlUnits) iterator2.next();
								unitsMap.put(hlUnits.getName(),hlUnits.getId());
							}
						}
						map.put(hlBlocks.getName(),unitsMap);
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
		 * @see com.kp.cms.transactions.hostel.IHladmissionTransaction#getAdmissionDetailsForOrint(int)
		 */
		@Override
		public HlAdmissionBo getAdmissionDetailsForOrint(int admId)
				throws Exception {
			Session session=null;
			HlAdmissionBo bo = null;
			try{
				session=HibernateUtil.getSession();
				Query query = session.createQuery("from HlAdmissionBo a where a.isActive=1 and a.id="+admId);
				bo=(HlAdmissionBo) query.uniqueResult();
			}catch (Exception exception) {
				// TODO: handle exception
				throw new ApplicationException();
			}
			return bo;
		}

		@Override
		public BigDecimal getNumberOfSeatsAvailable(String hostel, String roomtype,String year, HttpServletRequest request) throws Exception {
			// TODO Auto-generated method stub
	    Session session = null;
	    BigDecimal totalseat =null;
	    BigInteger reserved =null;
	    BigDecimal available =null;
	    String seatTotal="";
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			

			String  str=" select count(hl_admission.student_id) as seats" +
			" from hl_admission " +
			" where hl_admission.is_active=1" +
			" and hl_admission.is_cancelled=0 " +
			" and (hl_admission.is_checked_out is null or hl_admission.is_checked_out=0 )" +
		    " and hl_admission.roomtype_id="+roomtype+
		    " and hl_admission.hostel_id="+hostel+
		    " and hl_admission.academic_year="+year+
		    " group by hl_admission.hostel_id,hl_admission.roomtype_id";
			
			Query query = session.createSQLQuery(str);
			reserved = (BigInteger) query.uniqueResult();
			
			
			String  strt=" select hl_available_seats.num_of_available_seats" +
			" from hl_available_seats " +
			" where hl_available_seats.is_active=1" +
		    " and hl_available_seats.hl_roomtype_id="+roomtype+
		    " and hl_available_seats.hl_hostel_id="+hostel+
		    " and hl_available_seats.academic_year="+year;
			
			Query que = session.createSQLQuery(strt);
			seatTotal =(String) que.uniqueResult();
			
			
			if(seatTotal!=null && !seatTotal.isEmpty()){
				totalseat=new BigDecimal(seatTotal);
			}
			if(totalseat==null){
			String strs=" select sum(hl_room_type.no_of_occupants) as seats" +
			" from hl_hostel " +
			" inner join hl_room_type on hl_room_type.hl_hostel_id = hl_hostel.id" +
			" and hl_room_type.is_active=1" +
			" and hl_hostel.is_active=1" +
			" inner join hl_room on hl_room.hl_hostel_id = hl_hostel.id" +
			" and hl_room.hl_room_type_id = hl_room_type.id" +
			" and hl_room.is_active=1" +
		    " where  hl_room_type.id="+roomtype +
		    " and hl_hostel.id="+hostel+
		    " and hl_room_type.room_category='Student'"+
		    " group by hl_hostel.id,hl_room_type.id";
			
			Query queryy = session.createSQLQuery(strs);
			totalseat = (BigDecimal) queryy.uniqueResult();
			}
			if(reserved!=null){
			available=totalseat.subtract(new BigDecimal(reserved));
			}else{
				available=totalseat;
			}
			
		} catch (Exception e) {
			throw new ApplicationException();
		} finally {
			if (session != null) {
				session.flush();
		 	                  }
		          } 
		  return available;
		}
		
}
