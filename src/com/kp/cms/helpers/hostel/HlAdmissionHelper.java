package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.hostel.HlAdmissionBookingWaitingBo;
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.transactions.hostel.IHladmissionTransaction;
import com.kp.cms.transactionsimpl.hostel.HlAdmissionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HlAdmissionHelper {

	private static final Log log=LogFactory.getLog(HlAdmissionHelper.class);
	public static volatile HlAdmissionHelper hlAdmissionHelper = null;

	public static HlAdmissionHelper getInstance() {
		if (hlAdmissionHelper == null) {
			hlAdmissionHelper = new HlAdmissionHelper();
			return hlAdmissionHelper;
		}
		return hlAdmissionHelper;
	}
	IHladmissionTransaction transaction = new HlAdmissionImpl();
	
	
	/**
	 * @param hlAdmissionBoList
	 * @return
	 */
	public List<HlAdmissionTo> convertBosToTOs(List<Object[]> hlAdmissionBoList) {
		List<HlAdmissionTo> HlAdmissionToList=new ArrayList<HlAdmissionTo>();
		Iterator itr=hlAdmissionBoList.iterator();
		while (itr.hasNext()) {
			Object[] subject=(Object[])itr.next();
			HlAdmissionTo hlAdmissionTo=new HlAdmissionTo();
			
			if(subject[0]!=null && !subject[0].toString().isEmpty()){
			hlAdmissionTo.setStudentId(subject[0].toString());
			}if(subject[1]!=null && !subject[1].toString().isEmpty()){
			hlAdmissionTo.setRegNo(subject[1].toString());
			}if(subject[2]!=null && !subject[2].toString().isEmpty()){
			hlAdmissionTo.setApplNo(subject[2].toString());
			}if(subject[3]!=null && !subject[3].toString().isEmpty() && subject[4]!=null){
			hlAdmissionTo.setStudentName(subject[3].toString()+""+subject[4].toString());
			}else{
				hlAdmissionTo.setStudentName(subject[3].toString());
			}
			if(subject[5]!=null && !subject[5].toString().isEmpty()){
			hlAdmissionTo.setCourseId(subject[5].toString());
			}if(subject[6]!=null && !subject[6].toString().isEmpty()){
			hlAdmissionTo.setCourseName(subject[6].toString());
			}if(subject[7]!=null && !subject[7].toString().isEmpty()){
			hlAdmissionTo.setHostelName(subject[7].toString());
			}if(subject[8]!=null && !subject[8].toString().isEmpty()){
			hlAdmissionTo.setHostelId(subject[8].toString());
			}if(subject[9]!=null && !subject[9].toString().isEmpty()){
			hlAdmissionTo.setRoomTypeName(subject[9].toString());
			}if(subject[10]!=null && !subject[10].toString().isEmpty()){
			hlAdmissionTo.setRoomTypeId(subject[10].toString());
			}if(subject[11]!=null && !subject[11].toString().isEmpty()){
				hlAdmissionTo.setId(Integer.parseInt(subject[11].toString()));
			}else{
				hlAdmissionTo.setId(0);
			}if(subject[12]!=null && !subject[12].toString().isEmpty()){
				hlAdmissionTo.setYear(subject[12].toString());
		    }if(subject[13]!=null && !subject[13].toString().isEmpty()){
				hlAdmissionTo.setHlApplicationNo(subject[13].toString());
		    }
			HlAdmissionToList.add(hlAdmissionTo);
		}
		return HlAdmissionToList;
	}

	/**
	 * @param hlAdmissionBoList
	 * @param hlAdmissionForm 
	 */
	public void setStudentDetails(List<Object[]> hlAdmissionBoList, HlAdmissionForm hlAdmissionForm) {
		Iterator itr=hlAdmissionBoList.iterator();
		while (itr.hasNext()) {
			Object[] subject=(Object[])itr.next();
			
			if(subject[0]!=null && !subject[0].toString().isEmpty()){
				hlAdmissionForm.setStudentId(subject[0].toString());
			}if(subject[1]!=null && !subject[1].toString().isEmpty() && subject[2]!=null && !subject[2].toString().isEmpty()){
				hlAdmissionForm.setStudentName(subject[1].toString()+""+subject[2].toString());
			}else{
				hlAdmissionForm.setStudentName(subject[1].toString());
			}if(subject[3]!=null && !subject[3].toString().isEmpty()){
				hlAdmissionForm.setCourseId(subject[3].toString());
			}if(subject[4]!=null && !subject[4].toString().isEmpty()){
				hlAdmissionForm.setCourseName(subject[4].toString());
			}
		}
	}
	/**
	 * @param HlAdmissionForm
	 * @return
	 */
	public HlAdmissionBo convertFormTOBO(HlAdmissionForm hlAdmissionForm) {
		HlAdmissionBo hlAdmissionBo=new HlAdmissionBo();
		
		
		Student student=new Student();
		Course course= new Course();
		HlHostel hostel=new HlHostel();
		HlRoomType roomType=new HlRoomType();
		
		student.setId(Integer.parseInt(hlAdmissionForm.getStudentId()));
		course.setId(Integer.parseInt(hlAdmissionForm.getCourseId()));
		hostel.setId(Integer.parseInt(hlAdmissionForm.getHostelName()));
		roomType.setId(Integer.parseInt(hlAdmissionForm.getRoomTypeName()));

		hlAdmissionBo.setStudentId(student);
		hlAdmissionBo.setCourseId(course);
		hlAdmissionBo.setHostelId(hostel);
		hlAdmissionBo.setRoomTypeId(roomType);
		hlAdmissionBo.setAcademicYear(hlAdmissionForm.getYear());
		hlAdmissionBo.setAdmittedDate(CommonUtil.ConvertStringToDate(hlAdmissionForm.getAdmittedDate()));
		
		hlAdmissionBo.setApplicationNo(hlAdmissionForm.getHlApplicationNo());
		hlAdmissionBo.setBiometricId(null);
		hlAdmissionBo.setCheckInDate(null);
		hlAdmissionBo.setIsCheckedIn(false);
		
		hlAdmissionBo.setCreatedBy(hlAdmissionForm.getUserId());
		hlAdmissionBo.setCreatedDate(new Date());
		hlAdmissionBo.setLastModifiedDate(new Date());
		hlAdmissionBo.setModifiedBy(hlAdmissionForm.getUserId());
		hlAdmissionBo.setIsCancelled(false);
		hlAdmissionBo.setIsActive(true);
		return hlAdmissionBo;
	}
	/**
	 * @param hlAdmissionForm
	 * @param hlAdmissionBo
	 */
	public void setBotoForm(HlAdmissionForm hlAdmissionForm,Object[] hlAdmissionBo) {
		if(hlAdmissionBo!=null)
		{
			if(hlAdmissionBo[0]!=null){
			hlAdmissionForm.setStudentId(hlAdmissionBo[0].toString());
			}if(hlAdmissionBo[1]!=null){
			hlAdmissionForm.setRegNo(hlAdmissionBo[1].toString());
			}if(hlAdmissionBo[2]!=null){
			hlAdmissionForm.setApplNo(hlAdmissionBo[2].toString());
			}if(hlAdmissionBo[3]!=null && hlAdmissionBo[4]!=null){
			hlAdmissionForm.setStudentName(hlAdmissionBo[3].toString()+""+hlAdmissionBo[4].toString());
			hlAdmissionForm.setTempStudentName(hlAdmissionBo[3].toString()+""+hlAdmissionBo[4].toString());
			}else{
				hlAdmissionForm.setStudentName(hlAdmissionBo[3].toString());
				hlAdmissionForm.setTempStudentName(hlAdmissionBo[3].toString());
			}
			if(hlAdmissionBo[5]!=null){
			hlAdmissionForm.setCourseId(hlAdmissionBo[5].toString());
			}if(hlAdmissionBo[6]!=null){
			hlAdmissionForm.setCourseName(hlAdmissionBo[6].toString());
			}if(hlAdmissionBo[8]!=null){
			hlAdmissionForm.setHostelName(hlAdmissionBo[8].toString());
			}if(hlAdmissionBo[8]!=null){
			hlAdmissionForm.setHostelId(hlAdmissionBo[8].toString());
			}if(hlAdmissionBo[10]!=null){
			hlAdmissionForm.setRoomTypeName(hlAdmissionBo[10].toString());
			}if(hlAdmissionBo[10]!=null){
			hlAdmissionForm.setRoomTypeId(Integer.parseInt(hlAdmissionBo[10].toString()));
			}if(hlAdmissionBo[11]!=null){
			hlAdmissionForm.setId(Integer.parseInt(hlAdmissionBo[11].toString()));
			}if(hlAdmissionBo[12]!=null){
			hlAdmissionForm.setYear(hlAdmissionBo[12].toString());
			}if(hlAdmissionBo[7]!=null){
			hlAdmissionForm.setHostelTempName(hlAdmissionBo[7].toString());
			}if(hlAdmissionBo[9]!=null){
			hlAdmissionForm.setRoomTypeTempName(hlAdmissionBo[9].toString());
			}if(hlAdmissionBo[13]!=null){
			hlAdmissionForm.setAdmittedDate(hlAdmissionBo[13].toString());
			}if(hlAdmissionBo[14]!=null){
			hlAdmissionForm.setHlApplicationNo(hlAdmissionBo[14].toString());
			}if(hlAdmissionBo[15]!=null){
			hlAdmissionForm.setBiometricId(hlAdmissionBo[15].toString());
			}if(hlAdmissionBo[16]!=null){
			hlAdmissionForm.setIsCheckIn(hlAdmissionBo[16].toString());
			}if(hlAdmissionBo[17]!=null){
			hlAdmissionForm.setCheckedInDate(hlAdmissionBo[17].toString());
			}if(hlAdmissionBo[18]!=null){
				hlAdmissionForm.setRoomId(hlAdmissionBo[18].toString());
			}if(hlAdmissionBo[19]!=null){
				hlAdmissionForm.setBedId(hlAdmissionBo[19].toString());
				}
			
		}
		
	}
	/**
	 * @param hlAdmissionForm
	 * @return
	 */
	public HlAdmissionBo convertFormToBO(HlAdmissionForm hlAdmissionForm) {
       HlAdmissionBo hlAdmissionBo=new HlAdmissionBo();
		
        hlAdmissionBo.setId(hlAdmissionForm.getId());
       
        Student student=new Student();
		Course course= new Course();
		HlHostel hostel=new HlHostel();
		HlRoomType roomType=new HlRoomType();
		HlRoom room=new HlRoom();
		HlBeds bed=new HlBeds();
		
		student.setId(Integer.parseInt(hlAdmissionForm.getStudentId()));
		course.setId(Integer.parseInt(hlAdmissionForm.getCourseId()));
		hostel.setId(Integer.parseInt(hlAdmissionForm.getHostelName()));
		roomType.setId(Integer.parseInt(hlAdmissionForm.getRoomTypeName()));
		if(hlAdmissionForm.getRoomId()!=null && !hlAdmissionForm.getRoomId().isEmpty()){
		room.setId(Integer.parseInt(hlAdmissionForm.getRoomId()));
		hlAdmissionBo.setRoomId(room);
		}if(hlAdmissionForm.getBedId()!=null && !hlAdmissionForm.getBedId().isEmpty()){
		bed.setId(Integer.parseInt(hlAdmissionForm.getBedId()));
		hlAdmissionBo.setBedId(bed);
		}
		
		hlAdmissionBo.setStudentId(student);
		hlAdmissionBo.setCourseId(course);
		hlAdmissionBo.setHostelId(hostel);
		hlAdmissionBo.setRoomTypeId(roomType);
		hlAdmissionBo.setAcademicYear(hlAdmissionForm.getYear());
		hlAdmissionBo.setAdmittedDate(CommonUtil.ConvertStringToDate(hlAdmissionForm.getAdmittedDate()));
		
		hlAdmissionBo.setApplicationNo(hlAdmissionForm.getHlApplicationNo());
		if(hlAdmissionForm.getBiometricId()!=null && !hlAdmissionForm.getBiometricId().isEmpty())
		hlAdmissionBo.setBiometricId(hlAdmissionForm.getBiometricId());
		if(hlAdmissionForm.getCheckedInDate()!=null && !hlAdmissionForm.getCheckedInDate().isEmpty())
		hlAdmissionBo.setCheckInDate(CommonUtil.ConvertStringToDate(hlAdmissionForm.getCheckedInDate()));
		if(hlAdmissionForm.getIsCheckIn()!=null){
			if(hlAdmissionForm.getIsCheckIn().equalsIgnoreCase("true"))
				hlAdmissionBo.setIsCheckedIn(true);
			else
				hlAdmissionBo.setIsCheckedIn(false);
		}
		
		
		hlAdmissionBo.setLastModifiedDate(new Date());
		hlAdmissionBo.setModifiedBy(hlAdmissionForm.getUserId());
		hlAdmissionBo.setIsCancelled(false);
		hlAdmissionBo.setIsActive(true);
		
		return hlAdmissionBo;
	}

	public List<HlAdmissionTo> convertTosToTOs(HlAdmissionForm hlAdmissionForm) {
		List<HlAdmissionTo> HlAdmissionToList=new ArrayList<HlAdmissionTo>();
		Iterator itr=hlAdmissionForm.getHlAdmissionList().iterator();
		while (itr.hasNext()) {
			HlAdmissionTo student=(HlAdmissionTo)itr.next();
			HlAdmissionTo hlAdmissionTo=new HlAdmissionTo();
			
			if(student.getId()!=hlAdmissionForm.getId()){
				
			if(student.getStudentId()!=null && !student.getStudentId().isEmpty()){
			hlAdmissionTo.setStudentId(student.getStudentId());
			}if(student.getRegNo()!=null && !student.getRegNo().isEmpty()){
			hlAdmissionTo.setRegNo(student.getRegNo());
			}if(student.getApplNo()!=null && !student.getApplNo().isEmpty()){
			hlAdmissionTo.setApplNo(student.getApplNo());
			}if(student.getStudentName()!=null && !student.getStudentName().isEmpty()){
			hlAdmissionTo.setStudentName(student.getStudentName());
			}if(student.getCourseId()!=null && !student.getCourseId().isEmpty()){
			hlAdmissionTo.setCourseId(student.getCourseId());
			}if(student.getCourseName()!=null && !student.getCourseName().isEmpty()){
			hlAdmissionTo.setCourseName(student.getCourseName());
			}if(student.getHostelName()!=null && !student.getHostelName().isEmpty()){
			hlAdmissionTo.setHostelName(student.getHostelName());
			}if(student.getHostelId()!=null && !student.getHostelId().isEmpty()){
			hlAdmissionTo.setHostelId(student.getHostelId());
			}if(student.getRoomTypeName()!=null && !student.getRoomTypeName().isEmpty()){
			hlAdmissionTo.setRoomTypeName(student.getRoomTypeName());
			}if(student.getRoomTypeId()!=null && !student.getRoomTypeId().isEmpty()){
			hlAdmissionTo.setRoomTypeId(student.getRoomTypeId());
			}hlAdmissionTo.setId(student.getId());
			if(student.getYear()!=null && !student.getYear().isEmpty()){
				hlAdmissionTo.setYear(student.getYear());
				}if(student.getHlApplicationNo()!=null && !student.getHlApplicationNo().isEmpty()){
				hlAdmissionTo.setHlApplicationNo(student.getHlApplicationNo());
				}
			HlAdmissionToList.add(hlAdmissionTo);
		}
	}
		return HlAdmissionToList;
	}
   
	public List<HlAdmissionTo> backTosToTOs(HlAdmissionForm hlAdmissionForm) {
		List<HlAdmissionTo> HlAdmissionToList=new ArrayList<HlAdmissionTo>();
		Iterator itr=hlAdmissionForm.getHlAdmissionList().iterator();
		while (itr.hasNext()) {
			HlAdmissionTo student=(HlAdmissionTo)itr.next();
			HlAdmissionTo hlAdmissionTo=new HlAdmissionTo();
			if(student.getStudentId()!=null && !student.getStudentId().isEmpty()){
			hlAdmissionTo.setStudentId(student.getStudentId());
			}if(student.getRegNo()!=null && !student.getRegNo().isEmpty()){
			hlAdmissionTo.setRegNo(student.getRegNo());
			}if(student.getApplNo()!=null && !student.getApplNo().isEmpty()){
			hlAdmissionTo.setApplNo(student.getApplNo());
			}if(student.getStudentName()!=null && !student.getStudentName().isEmpty()){
			hlAdmissionTo.setStudentName(student.getStudentName());
			}if(student.getCourseId()!=null && !student.getCourseId().isEmpty()){
			hlAdmissionTo.setCourseId(student.getCourseId());
			}if(student.getCourseName()!=null && !student.getCourseName().isEmpty()){
			hlAdmissionTo.setCourseName(student.getCourseName());
			}if(student.getHostelName()!=null && !student.getHostelName().isEmpty()){
			hlAdmissionTo.setHostelName(student.getHostelName());
			}if(student.getHostelId()!=null && !student.getHostelId().isEmpty()){
			hlAdmissionTo.setHostelId(student.getHostelId());
			}if(student.getRoomTypeName()!=null && !student.getRoomTypeName().isEmpty()){
			hlAdmissionTo.setRoomTypeName(student.getRoomTypeName());
			}if(student.getRoomTypeId()!=null && !student.getRoomTypeId().isEmpty()){
			hlAdmissionTo.setRoomTypeId(student.getRoomTypeId());
			}hlAdmissionTo.setId(student.getId());
			if(student.getYear()!=null && !student.getYear().isEmpty()){
				hlAdmissionTo.setYear(student.getYear());
		   }if(student.getHlApplicationNo()!=null && !student.getHlApplicationNo().isEmpty()){
				hlAdmissionTo.setHlApplicationNo(student.getHlApplicationNo());
				}
			HlAdmissionToList.add(hlAdmissionTo);
		
	}
		return HlAdmissionToList;
	}

	public List<HlAdmissionTo> printBosToTOs(Object[] subject) {
		List<HlAdmissionTo> HlAdmissionToList=new ArrayList<HlAdmissionTo>();
			HlAdmissionTo hlAdmissionTo=new HlAdmissionTo();
			
			if(subject[0]!=null && !subject[0].toString().isEmpty()){
			hlAdmissionTo.setStudentId(subject[0].toString());
			}if(subject[1]!=null && !subject[1].toString().isEmpty()){
			hlAdmissionTo.setRegNo(subject[1].toString());
			}if(subject[2]!=null && !subject[2].toString().isEmpty()){
			hlAdmissionTo.setApplNo(subject[2].toString());
			}if((subject[1]==null || subject[1].toString().isEmpty()) && (subject[2]!=null && !subject[2].toString().isEmpty())){
			hlAdmissionTo.setRegNo(subject[2].toString());
			}if(subject[3]!=null && !subject[3].toString().isEmpty() && subject[4]!=null){
			hlAdmissionTo.setStudentName(subject[3].toString()+""+subject[4].toString());
			}else{
				hlAdmissionTo.setStudentName(subject[3].toString());
			}
			if(subject[5]!=null && !subject[5].toString().isEmpty()){
			hlAdmissionTo.setCourseId(subject[5].toString());
			}if(subject[6]!=null && !subject[6].toString().isEmpty()){
			hlAdmissionTo.setCourseName(subject[6].toString());
			}if(subject[7]!=null && !subject[7].toString().isEmpty()){
			hlAdmissionTo.setHostelName(subject[7].toString());
			}if(subject[8]!=null && !subject[8].toString().isEmpty()){
			hlAdmissionTo.setHostelId(subject[8].toString());
			}if(subject[9]!=null && !subject[9].toString().isEmpty()){
			hlAdmissionTo.setRoomTypeName(subject[9].toString());
			}if(subject[10]!=null && !subject[10].toString().isEmpty()){
			hlAdmissionTo.setRoomTypeId(subject[10].toString());
			}if(subject[11]!=null && !subject[11].toString().isEmpty()){
				hlAdmissionTo.setId(Integer.parseInt(subject[11].toString()));
			}else{
				hlAdmissionTo.setId(0);
			}
			if(subject[12]!=null && !subject[12].toString().isEmpty()){
				hlAdmissionTo.setYear(subject[12].toString());
			}if(subject[13]!=null && !subject[13].toString().isEmpty()){
				hlAdmissionTo.setHlApplicationNo(subject[13].toString());
				}
			HlAdmissionToList.add(hlAdmissionTo);
		return HlAdmissionToList;
	}
	
	public HlAdmissionBookingWaitingBo convertFormTOWaitingBo(HlAdmissionForm hlAdmissionForm) throws Exception {
	       HlAdmissionBookingWaitingBo waitingBo=new HlAdmissionBookingWaitingBo();
			
	       waitingBo.setId(hlAdmissionForm.getId());
	       
	        Student student=new Student();
			HlHostel hostel=new HlHostel();
			HlRoomType roomType=new HlRoomType();
			
			student.setId(Integer.parseInt(hlAdmissionForm.getStudentId()));
			hostel.setId(Integer.parseInt(hlAdmissionForm.getHostelName()));
			roomType.setId(Integer.parseInt(hlAdmissionForm.getRoomTypeName()));
			waitingBo.setStudentId(student);
			waitingBo.setHostelId(hostel);
			waitingBo.setRoomTypeId(roomType);
			waitingBo.setAcademicYear(hlAdmissionForm.getYear());
			long count=transaction.getWaitingListPriorityNo(hlAdmissionForm);
			int i=(int) count;
			if(i>0)
			{
				i=i+1;
				waitingBo.setWaitingListPriorityNo(i);
			}
			else
			{
				waitingBo.setWaitingListPriorityNo(1);
			}
			waitingBo.setCreatedBy(hlAdmissionForm.getUserId());
			waitingBo.setCreatedDate(new Date());
			waitingBo.setApplicationNo(hlAdmissionForm.getHlApplicationNo());
			waitingBo.setDateOfBooking(new Date());
			waitingBo.setLastModifiedDate(new Date());
			waitingBo.setModifiedBy(hlAdmissionForm.getUserId());
			waitingBo.setIsActive(true);
			return waitingBo;
		}

	/**
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public HlAdmissionTo printBosToTO(HlAdmissionBo bo) throws Exception{
		HlAdmissionTo to = new HlAdmissionTo();
		if(bo != null){
			if(bo.getHostelId() != null && bo.getHostelId().getName() != null){
				to.setHostelName(bo.getHostelId().getName());	
			}
			if(bo.getApplicationNo() != null){
				to.setApplNo(bo.getApplicationNo());
			}
			if(bo.getStudentId() != null && bo.getStudentId().getAdmAppln() != null && bo.getStudentId().getAdmAppln().getPersonalData() != null){
				if(bo.getStudentId().getRegisterNo()!= null && !bo.getStudentId().getRegisterNo().isEmpty())
					to.setRegNo(bo.getStudentId().getRegisterNo());
				else
					to.setRegNo(String.valueOf(bo.getStudentId().getAdmAppln().getApplnNo()));
				to.setStudentName(bo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				to.setDateOfBirth(CommonUtil.getStringDate(bo.getStudentId().getAdmAppln().getPersonalData().getDateOfBirth()));
				if(bo.getStudentId().getAdmAppln().getPersonalData().getReligion() != null)
					to.setReligion(bo.getStudentId().getAdmAppln().getPersonalData().getReligion().getName());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2() != null)
					to.setMobNO(bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getPermanentAddressLine1() != null)
					to.setParentAdd1(bo.getStudentId().getAdmAppln().getPersonalData().getPermanentAddressLine1());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getPermanentAddressLine2() != null)
					to.setParentAdd2(bo.getStudentId().getAdmAppln().getPersonalData().getPermanentAddressLine2());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getCityByPermanentAddressCityId() != null 
						&&	bo.getStudentId().getAdmAppln().getPersonalData().getPermanentAddressZipCode() != null)
					to.setParentAdd3(bo.getStudentId().getAdmAppln().getPersonalData().getCityByPermanentAddressCityId() +"-" +bo.getStudentId().getAdmAppln().getPersonalData().getPermanentAddressZipCode());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId() != null)
					to.setParentAdd4(bo.getStudentId().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getParentMob2() != null)
					to.setParentMobNO(bo.getStudentId().getAdmAppln().getPersonalData().getParentMob2());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getParentPh2() != null)
					to.setParentTelNO(bo.getStudentId().getAdmAppln().getPersonalData().getParentPh2());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getGuardianAddressLine1() != null 
						&& bo.getStudentId().getAdmAppln().getPersonalData().getGuardianAddressLine2() != null 
						&& bo.getStudentId().getAdmAppln().getPersonalData().getGuardianAddressLine3() != null){
					to.setCurrentAdd1(bo.getStudentId().getAdmAppln().getPersonalData().getGuardianAddressLine1());
					to.setCurrentAdd2(bo.getStudentId().getAdmAppln().getPersonalData().getGuardianAddressLine2()+","+bo.getStudentId().getAdmAppln().getPersonalData().getGuardianAddressLine3());
					if(bo.getStudentId().getAdmAppln().getPersonalData().getCityByGuardianAddressCityId() != null
							&& bo.getStudentId().getAdmAppln().getPersonalData().getGuardianAddressZipCode() != null)
						to.setCurrentAdd3(bo.getStudentId().getAdmAppln().getPersonalData().getCityByGuardianAddressCityId()+"-"+bo.getStudentId().getAdmAppln().getPersonalData().getGuardianAddressZipCode());
					if(bo.getStudentId().getAdmAppln().getPersonalData().getStateByGuardianAddressStateId() != null)
						to.setCurrentAdd4(bo.getStudentId().getAdmAppln().getPersonalData().getStateByGuardianAddressStateId().getName());
				}
				if(bo.getStudentId().getAdmAppln().getPersonalData().getGuardianMob2() != null)
					to.setCurrentMobNO(bo.getStudentId().getAdmAppln().getPersonalData().getGuardianMob2());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getGuardianPh2() != null)
					to.setCurrentTelNO(bo.getStudentId().getAdmAppln().getPersonalData().getGuardianPh2());	
				if(bo.getStudentId().getAdmAppln().getPersonalData().getEmail() != null)
					to.setEmail(bo.getStudentId().getAdmAppln().getPersonalData().getEmail());
				if(bo.getRoomTypeId() != null && bo.getRoomTypeId().getName() != null)
					to.setRoomTypeName(bo.getRoomTypeId().getName());
			}
			if(bo.getStudentId() != null && bo.getStudentId().getAdmAppln() != null && bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId() != null 
					&& bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getProgram() != null){
				to.setProgram(bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getProgram().getName());
			}
			if(bo.getStudentId() != null)
				to.setStudentId(String.valueOf(bo.getStudentId().getId()));
		}
		return to;
	}
	

}
