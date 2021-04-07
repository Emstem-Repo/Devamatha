package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.hostel.HlStudentFacilityAllotted;
import com.kp.cms.forms.hostel.HlStudentChInEditForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.hostel.HlStudentCheckInHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHlStudentCheckInTransaction;
import com.kp.cms.transactionsimpl.hostel.HlStudentCheckInImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class HlStudentCheckInHelper {
	
	private static final Log log=LogFactory.getLog(HlStudentCheckInHelper.class);
	public static volatile HlStudentCheckInHelper hlStudentCheckInHelper = null;
	IHlStudentCheckInTransaction txImpl = new HlStudentCheckInImpl();

	public static HlStudentCheckInHelper getInstance() {
		if (hlStudentCheckInHelper == null) {
			hlStudentCheckInHelper = new HlStudentCheckInHelper();
			return hlStudentCheckInHelper;
		}
		return hlStudentCheckInHelper;
	}
	
	IHlStudentCheckInTransaction transaction = new HlStudentCheckInImpl();
	/**
	 * @param hlAdmissionBoList
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("null")
	public List<HlAdmissionTo> convertBosToTOs(List<HlAdmissionBo> hlAdmissionBoList,HlStudentChInEditForm hlStudentChInEditForm) throws Exception {
		List<HlAdmissionTo> HlAdmissionToList=new ArrayList<HlAdmissionTo>();
		String academicYear =hlStudentChInEditForm.getYear();
//		String checkIndate=CommonUtil.formatDates(new java.util.Date());
//		Map<Integer, String> map = new HashMap<Integer, String>();
		//Map<Integer, String> HostelMap = new HashMap<Integer, String>();
		Map<Integer, String> RoomMap = new HashMap<Integer, String>();
		Map<Integer, String> BedMap = new HashMap<Integer, String>();
		List<HlStudentFacilityAllotted> allotted=null;
		List<HlRoomTypeFacility> roomFacility=null;
		Iterator<HlAdmissionBo> itr=hlAdmissionBoList.iterator();
		while (itr.hasNext()) {
			HlAdmissionBo  hlAdmissionBo = (HlAdmissionBo) itr.next();
//			HlAdmissionTo hlAdmissionTo=new HlAdmissionTo();
			/* useless map 
			 * if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getGender()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getGender().isEmpty())
				HostelMap = CommonAjaxHandler.getInstance().getHostelBygender(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getGender());
			//hlStudentChInEditForm.setHostelList(HostelMap)
*/			if(hlAdmissionBo.getHostelId()!=null && hlAdmissionBo.getHostelId().getId()>0){
				Map<Integer, String> RoomTypeMap = CommonAjaxHandler.getInstance().getRoomTypeByHostelBYstudent(hlAdmissionBo.getHostelId().getId());
				Map<Integer, String> BlockMap = HostelEntryHandler.getInstance().getBlocks(String.valueOf(hlAdmissionBo.getHostelId().getId()));
				Map<Integer, String> FloorMap = CommonAjaxHandler.getInstance().getFloorsByHostel(hlAdmissionBo.getHostelId().getId());
				hlStudentChInEditForm.setRoomTypeMap(RoomTypeMap);
				hlStudentChInEditForm.setBlockMap(BlockMap);
				hlStudentChInEditForm.setFloorMap(FloorMap);
			}
			if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlBlock()!=null && hlAdmissionBo.getRoomId().getHlBlock().getId()>0){
				Map<Integer, String> UnitMap = HostelEntryHandler.getInstance().getUnits(String.valueOf(hlAdmissionBo.getRoomId().getHlBlock().getId()));
				hlStudentChInEditForm.setUnitMap(UnitMap);
			}
			/*if(object[2]!=null && !object[2].toString().isEmpty()){
				BedMap = CommonAjaxHandler.getInstance().getBedsAvailable(object[2].toString());
				hlStudentChInEditForm.setBedMap(BedMap);
			}*/
			
				if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty())
					hlStudentChInEditForm.setStudentName(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				if(hlAdmissionBo.getId()>0)
					hlStudentChInEditForm.setHostelAdmissionId(String.valueOf(hlAdmissionBo.getId()));	
				if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlBlock()!=null && hlAdmissionBo.getRoomId().getHlBlock().getId()>0)
					hlStudentChInEditForm.setBlock(String.valueOf(hlAdmissionBo.getRoomId().getHlBlock().getId()));
				else
					hlStudentChInEditForm.setBlock("");
				if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getFloorNo()!=null && !hlAdmissionBo.getRoomId().getFloorNo().isEmpty())
					hlStudentChInEditForm.setFloorNo(hlAdmissionBo.getRoomId().getFloorNo());
				else
					hlStudentChInEditForm.setFloorNo("");
				if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlUnit()!=null && hlAdmissionBo.getRoomId().getHlUnit().getId()>0)
					hlStudentChInEditForm.setUnit(String.valueOf(hlAdmissionBo.getRoomId().getHlUnit().getId()));
				else
					hlStudentChInEditForm.setUnit("");
				if(hlAdmissionBo.getHostelId()!=null && hlAdmissionBo.getHostelId().getId()>0)
					hlStudentChInEditForm.setHostelId(String.valueOf(hlAdmissionBo.getHostelId().getId()));
				else
					hlStudentChInEditForm.setHostelId("");
				if(hlAdmissionBo.getApplicationNo()!=null && !hlAdmissionBo.getApplicationNo().isEmpty())
					hlStudentChInEditForm.setHlApplicationNo(hlAdmissionBo.getApplicationNo());
				if(hlAdmissionBo.getAdmittedDate()!=null && !hlAdmissionBo.getAdmittedDate().toString().isEmpty()){
					String dateString = hlAdmissionBo.getAdmittedDate().toString().substring(0, 10);
					String inputDateFormat = "yyyy-mm-dd";
					String outPutdateFormat = "dd/mm/yyyy";
					hlStudentChInEditForm.setAdmittedDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
				}
				else
				{
					hlStudentChInEditForm.setAdmittedDate(CommonUtil.formatDates(new java.util.Date()));
				}
				if(hlAdmissionBo.getBiometricId()!=null && !hlAdmissionBo.getBiometricId().isEmpty())
					hlStudentChInEditForm.setBiometricId(hlAdmissionBo.getBiometricId());
				if(hlAdmissionBo.getCheckInDate()!=null && !hlAdmissionBo.getCheckInDate().toString().isEmpty()){
					String dateString1 = hlAdmissionBo.getCheckInDate().toString().substring(0, 10);
					String inputDateFormat = "yyyy-mm-dd";
					String outPutdateFormat = "dd/mm/yyyy";
					hlStudentChInEditForm.setCheckInDate(CommonUtil.ConvertStringToDateFormat(dateString1, inputDateFormat, outPutdateFormat));
				}else
					hlStudentChInEditForm.setCheckInDate(CommonUtil.formatDates(new java.util.Date()));
				if(hlAdmissionBo.getIsCheckedIn()!=null && !hlAdmissionBo.getIsCheckedIn().toString().isEmpty()){
					if(hlAdmissionBo.getIsCheckedIn().toString().equalsIgnoreCase("true"))
						hlStudentChInEditForm.setIsCheckIn("1");
					else
						hlStudentChInEditForm.setIsCheckIn("0");
				}
				if(hlAdmissionBo.getRoomTypeId()!=null && hlAdmissionBo.getRoomTypeId().getId()>0)
					hlStudentChInEditForm.setRoomTypeName(String.valueOf(hlAdmissionBo.getRoomTypeId().getId()));
				if(hlAdmissionBo.getStudentId()!=null && 
						hlAdmissionBo.getStudentId().getAdmAppln()!=null && 
						hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData()!=null && 
						hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getGender()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getGender().isEmpty())
					hlStudentChInEditForm.setGender(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getGender());
				if(hlAdmissionBo.getStudentId()!=null && hlAdmissionBo.getStudentId().getRegisterNo()!=null && !hlAdmissionBo.getStudentId().getRegisterNo().isEmpty())
					hlStudentChInEditForm.setRegNo(hlAdmissionBo.getStudentId().getRegisterNo());
				if(hlAdmissionBo.getCourseId()!=null && hlAdmissionBo.getCourseId().getId()>0)
					hlStudentChInEditForm.setCourseId(String.valueOf(hlAdmissionBo.getCourseId().getId()));
					hlStudentChInEditForm.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(hlAdmissionBo.getCourseId().getId(),"Course",true,"code"));
				if(hlAdmissionBo.getStudentId()!=null && hlAdmissionBo.getStudentId().getId()>0)
					hlStudentChInEditForm.setStudentId(String.valueOf(hlAdmissionBo.getStudentId().getId()));
				
				//Null Checking 
				int hostelId=0;
				int Roomtype=0;
				int blockId=0;
				int unitId=0;
				int roomId=0;
				String floor=null;
				if(hlAdmissionBo.getHostelId().getId()>0)
				     hostelId=hlAdmissionBo.getHostelId().getId();
				if(hlAdmissionBo.getRoomTypeId().getId()>0)
					Roomtype=hlAdmissionBo.getRoomTypeId().getId();
				if(hlAdmissionBo.getRoomId()!=null)
				{
					if(hlAdmissionBo.getRoomId().getId()>0)
					roomId=hlAdmissionBo.getRoomId().getId();
					if(hlAdmissionBo.getRoomId().getHlBlock().getId()>0  )
						blockId=hlAdmissionBo.getRoomId().getHlBlock().getId();
					if(hlAdmissionBo.getRoomId().getHlUnit().getId()>0)
						unitId=hlAdmissionBo.getRoomId().getHlUnit().getId();
					if(hlAdmissionBo.getRoomId().getFloorNo()!=null)
						floor=hlAdmissionBo.getRoomId().getFloorNo();
				}
				//
				
				if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getId()>0){
					if((hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlBlock()!=null && hlAdmissionBo.getRoomId().getHlBlock().getId()>0) 
							|| (hlAdmissionBo.getHostelId()!=null && hlAdmissionBo.getHostelId().getId()>0) 
							|| (hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getFloorNo()!=null && !hlAdmissionBo.getRoomId().getFloorNo().isEmpty()) 
							|| (hlAdmissionBo.getRoomTypeId()!=null && hlAdmissionBo.getRoomTypeId().getId()>0))
					RoomMap = CommonAjaxHandler.getInstance().getRoomsByFloorsCheckIn(hlAdmissionBo.getHostelId().getId(),Integer.parseInt(hlAdmissionBo.getRoomId().getFloorNo()),hlAdmissionBo.getRoomTypeId().getId(),hlAdmissionBo.getRoomId().getId(),Integer.parseInt(academicYear));
					hlStudentChInEditForm.setRoomMap(RoomMap);
					hlStudentChInEditForm.setRoomName(String.valueOf(hlAdmissionBo.getRoomId().getId()));
				}else{
					RoomMap = HlStudentCheckInHandler.getInstance().getRoomsAvailable(hostelId, Roomtype,academicYear, blockId, unitId, floor);
					hlStudentChInEditForm.setRoomMap(RoomMap);
					hlStudentChInEditForm.setRoomName("");
				}
				if(hlAdmissionBo.getBedId()!=null && hlAdmissionBo.getBedId().getId()>0){
					if((hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getId()>0))
					  BedMap = CommonAjaxHandler.getInstance().getBedByRoomCheckIn(hlAdmissionBo.getRoomId().getId(), hlAdmissionBo.getBedId().getId(),Integer.parseInt(academicYear));
					  hlStudentChInEditForm.setBedMap(BedMap);
					  hlStudentChInEditForm.setBedNo(String.valueOf(hlAdmissionBo.getBedId().getId()));
				}else{
					 BedMap = HlStudentCheckInHandler.getInstance().getBedsAvailable(roomId, Integer.parseInt(academicYear));
					 hlStudentChInEditForm.setBedMap(BedMap);
         			 hlStudentChInEditForm.setBedNo("");
		
				}
				if(hlStudentChInEditForm.getHostelAdmissionId()!=null && !hlStudentChInEditForm.getHostelAdmissionId().isEmpty()){
					allotted = null;
					 allotted = transaction.getHlStudentFacilityAllotted(hlStudentChInEditForm.getHostelAdmissionId());
					 
					 if(hlStudentChInEditForm.getRoomTypeName()!=null && !hlStudentChInEditForm.getRoomTypeName().isEmpty())
					   roomFacility = transaction.getHlRoomTypeFacility(hlStudentChInEditForm.getRoomTypeName());
				}
			if(hlStudentChInEditForm.getFacilityList()!=null && !hlStudentChInEditForm.getFacilityList().isEmpty()){
				Iterator<FacilityTO> facItr=hlStudentChInEditForm.getFacilityList().iterator();
				List<FacilityTO> facilityTo=new ArrayList<FacilityTO>();
				while (facItr.hasNext()) {
					FacilityTO to =facItr.next();
					if(allotted!=null && !allotted.isEmpty()){
					Iterator<HlStudentFacilityAllotted> itr1=allotted.iterator();
					while (itr1.hasNext()) {
						HlStudentFacilityAllotted bo =itr1.next();
						
						if(bo.getHlFacilityId().getId()>0)
						{
							if(bo.getHlFacilityId().getId()==to.getId())
							{
								to.setId(bo.getHlFacilityId().getId());
								if(bo.getIsActive()){
									to.setSelected(true);
									to.setDummySelected(true);
								}
								if(bo.getId()>0){
									to.setHlStudAllotFacId(bo.getId());
								}
								if(bo.getDescription()!=null && !bo.getDescription().isEmpty())
								{
									to.setDescription(bo.getDescription());
								}
								to.setName(to.getName());
							}
						}
						}
				}
				else if(allotted==null || allotted.isEmpty())
				{	
					if (roomFacility != null && !roomFacility.isEmpty()) {
						Iterator<HlRoomTypeFacility> itr2=roomFacility.iterator();
						while (itr2.hasNext()) {
							HlRoomTypeFacility bo =itr2.next();
							if(bo.getHlRoomType().getId()>0)
							{
							if(bo.getHlFacility().getId()>0){
								if(bo.getHlFacility().getId()==to.getId()){
										to.setId(bo.getHlFacility().getId());
										to.setHlRoomTypeId(bo.getHlRoomType().getId());
										to.setSelected(true);
										to.setDummySelected(true);
								}
							}
							}
						}
					}
				}
				facilityTo.add(to);
			}
			hlStudentChInEditForm.setFacilityList(facilityTo);
			}
			hlStudentChInEditForm.setId(hlAdmissionBo.getId());
		}
		HibernateUtil.closeSession();
		return HlAdmissionToList;
	}

	/**
	 * @param hlAdmissionBoList
	 * @param hlAdmissionForm 
	 */
	public void setStudentDetails(List<Object[]> hlAdmissionBoList, HlStudentChInEditForm hlAdmissionForm) {
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
	 * @param HlStudentChInEditForm
	 * @return
	 */
	public HlAdmissionBo convertFormTOBO(HlStudentChInEditForm hlAdmissionForm) {
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
	public void setBotoForm(HlStudentChInEditForm hlAdmissionForm,Object[] hlAdmissionBo) {
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
			hlAdmissionForm.setRoomTypeName(hlAdmissionBo[10].toString());
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
			hlAdmissionForm.setCheckInDate(hlAdmissionBo[17].toString());
			}if(hlAdmissionBo[18]!=null){
				hlAdmissionForm.setRoomName(hlAdmissionBo[18].toString());
			}if(hlAdmissionBo[19]!=null){
				hlAdmissionForm.setBedNo(hlAdmissionBo[19].toString());
				}
			
		}
		
	}
	/**
	 * @param hlAdmissionForm
	 * @return
	 */
	public HlAdmissionBo convertFormToBO(HlStudentChInEditForm hlAdmissionForm) {
       HlAdmissionBo hlAdmissionBo=new HlAdmissionBo();
       Set<HlStudentFacilityAllotted> roomTypeFacilitySet = new HashSet<HlStudentFacilityAllotted>();
	   HlFacility facility = null;
	   HlStudentFacilityAllotted studFacilityAllot = null;
		
        hlAdmissionBo.setId(Integer.parseInt(hlAdmissionForm.getHostelAdmissionId()));
       
        Student student=new Student();
		Course course= new Course();
		HlHostel hostel=new HlHostel();
		HlRoomType roomType=new HlRoomType();
		HlRoom room=new HlRoom();
		HlBeds bed=new HlBeds();
		
		student.setId(Integer.parseInt(hlAdmissionForm.getStudentId()));
		course.setId(Integer.parseInt(hlAdmissionForm.getCourseId()));
		hostel.setId(Integer.parseInt(hlAdmissionForm.getHostelId()));
		roomType.setId(Integer.parseInt(hlAdmissionForm.getRoomTypeName()));
		
		if(hlAdmissionForm.getRoomName()!=null && !hlAdmissionForm.getRoomName().isEmpty()){
		room.setId(Integer.parseInt(hlAdmissionForm.getRoomName()));
		hlAdmissionBo.setRoomId(room);
		}
		if(hlAdmissionForm.getBedNo()!=null && !hlAdmissionForm.getBedNo().isEmpty()){
		bed.setId(Integer.parseInt(hlAdmissionForm.getBedNo()));
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
		
			
		if(hlAdmissionForm.getIsCheckIn()!=null){
			if(hlAdmissionForm.getIsCheckIn().equalsIgnoreCase("1")){
				hlAdmissionBo.setIsCheckedIn(true);
				if(hlAdmissionForm.getCheckInDate()!=null && !hlAdmissionForm.getCheckInDate().isEmpty())
					hlAdmissionBo.setCheckInDate(CommonUtil.ConvertStringToDate(hlAdmissionForm.getCheckInDate()));
				
			}else{
				hlAdmissionBo.setIsCheckedIn(false);}
		}
		
		
		if(hlAdmissionForm.getFacilityList() != null && !hlAdmissionForm.getFacilityList().isEmpty()){
			Iterator<FacilityTO> iterator = hlAdmissionForm.getFacilityList().iterator();
			while (iterator.hasNext()) {
				FacilityTO facilityTO = iterator.next();
				//  /*condition added by chandra
				if(facilityTO.getChecked()!=null && facilityTO.getChecked().equalsIgnoreCase("on")){
					// */
					studFacilityAllot = new HlStudentFacilityAllotted();
					facility = new HlFacility();
					if(facilityTO.getHlStudAllotFacId() >0)
						studFacilityAllot.setId(facilityTO.getHlStudAllotFacId());
					hlAdmissionBo.setId(hlAdmissionForm.getId());
					studFacilityAllot.setHlAdmissionId(hlAdmissionBo);
					facility.setId(facilityTO.getId());
					studFacilityAllot.setHlFacilityId(facility);
					if(facilityTO.getDescription() != null && !facilityTO.getDescription().isEmpty()){
						studFacilityAllot.setDescription(facilityTO.getDescription());
					}
					studFacilityAllot.setCreatedBy(hlAdmissionForm.getUserId());
					studFacilityAllot.setCreatedDate(new Date());
					studFacilityAllot.setModifiedBy(hlAdmissionForm.getUserId());
					studFacilityAllot.setLastModifiedDate(new Date());
					studFacilityAllot.setIsActive(true);
					roomTypeFacilitySet.add(studFacilityAllot);
				}
				else if((facilityTO.getChecked()==null && facilityTO.isDummySelected()) || facilityTO.getHlStudAllotFacId() >0){
					if(facilityTO.getHlStudAllotFacId() >0){
					studFacilityAllot = new HlStudentFacilityAllotted();
					facility = new HlFacility();
					if(facilityTO.getHlStudAllotFacId() >0){
						studFacilityAllot.setId(facilityTO.getHlStudAllotFacId());
					}
					hlAdmissionBo.setId(hlAdmissionForm.getId());
					studFacilityAllot.setHlAdmissionId(hlAdmissionBo);
					facility.setId(facilityTO.getId());
					studFacilityAllot.setHlFacilityId(facility);
					if(facilityTO.getDescription() != null && !facilityTO.getDescription().isEmpty()){
						studFacilityAllot.setDescription(facilityTO.getDescription());
					}
					studFacilityAllot.setCreatedBy(hlAdmissionForm.getUserId());
					studFacilityAllot.setCreatedDate(new Date());
					studFacilityAllot.setModifiedBy(hlAdmissionForm.getUserId());
					studFacilityAllot.setLastModifiedDate(new Date());
					studFacilityAllot.setIsActive(false);
					roomTypeFacilitySet.add(studFacilityAllot);
					}
				}
			}
			hlAdmissionBo.setHlFacilitiesAllotted(roomTypeFacilitySet);
		}
		hlAdmissionBo.setLastModifiedDate(new Date());
		hlAdmissionBo.setModifiedBy(hlAdmissionForm.getUserId());
		hlAdmissionBo.setIsCancelled(false);
		hlAdmissionBo.setIsActive(true);
		return hlAdmissionBo;
	}

	public List<HlAdmissionTo> convertTosToTOs(HlStudentChInEditForm hlAdmissionForm) {
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
   
	public List<HlAdmissionTo> backTosToTOs(HlStudentChInEditForm hlAdmissionForm) {
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
	
	public List<FacilityTO> copyFacilityBOToTO(List<HlFacility> facilityBOList)throws Exception{
		log.info("Entering into copyFacilityBOToTO of CheckInHelper");
		List<FacilityTO> facilityList = new ArrayList<FacilityTO>();
		HlFacility facility;
		FacilityTO facilityTO = null;
		if(facilityBOList!=null && !facilityBOList.isEmpty()){
			Iterator<HlFacility> iterator = facilityBOList.iterator();
			while (iterator.hasNext()) {
				facility = iterator.next();
				facilityTO = new FacilityTO();
				facilityTO.setId(facility.getId());
				if(facility.getName() != null){
					facilityTO.setName(facility.getName());
				}
				facilityTO.setDummySelected(false);
				facilityTO.setSelected(false);
				facilityList.add(facilityTO);
				facilityTO.setFacilityCount(facilityList.size());
			}
		}
		log.info("Leaving into copyFacilityBOToTO of CheckInHelper");
		return facilityList;
	}
	/**
	 * @param hlAdmissionBoList
	 * @return
	 * @throws Exception 
	 */
	public List<HlAdmissionTo> convertBosToTOsNew(HlAdmissionBo hlAdmissionBo,HlStudentChInEditForm hlStudentChInEditForm) throws Exception {
		List<HlAdmissionTo> HlAdmissionToList=new ArrayList<HlAdmissionTo>();
		if(hlAdmissionBo != null){
			// add admission details to form 
			
			addDetailsToForm(hlStudentChInEditForm,hlAdmissionBo);
			List<HostelTO> hostelList = txImpl.getHostelDetails();
			hlStudentChInEditForm.setHostelList(hostelList);

			if(hlStudentChInEditForm.getHostelId() != null && !hlStudentChInEditForm.getHostelId().isEmpty()){
				
				if(hlStudentChInEditForm.getRoomTypeName() != null && !hlStudentChInEditForm.getRoomTypeName().isEmpty()){
					Map<Integer, String> roomTypeMap = txImpl.getRoomTypeByHostelBYstudent(Integer.parseInt(hlStudentChInEditForm.getHostelId()));
					hlStudentChInEditForm.setRoomTypeMap(roomTypeMap);
				}
				
				if(hlStudentChInEditForm.getHostelId() != null && !hlStudentChInEditForm.getHostelId().isEmpty()){
					Map<Integer, String> blockMap = txImpl.getBlocks(hlStudentChInEditForm.getHostelId());
					hlStudentChInEditForm.setBlockMap(blockMap);
				}
				
				if(hlStudentChInEditForm.getBlock() != null && !hlStudentChInEditForm.getBlock().isEmpty()){
					Map<Integer, String> unitMap = txImpl.getUnits(hlStudentChInEditForm.getBlock());
					hlStudentChInEditForm.setUnitMap(unitMap);
				}
				
				
				if(hlStudentChInEditForm.getUnit() != null && !hlStudentChInEditForm.getUnit().isEmpty()){
					Map<Integer, String> floorMap = txImpl.getFloorsByHostel(hlStudentChInEditForm.getUnit());
					hlStudentChInEditForm.setFloorMap(floorMap);
				}
				
				if(hlStudentChInEditForm.getRoomName() != null && !hlStudentChInEditForm.getRoomName().isEmpty()){
					List<Object[]> listOfRooms = txImpl.getRoomsByBlock(hlStudentChInEditForm.getHostelId(),hlStudentChInEditForm.getBlock(),hlStudentChInEditForm.getUnit(),hlStudentChInEditForm.getFloorNo(),
							hlStudentChInEditForm.getYear(),hlStudentChInEditForm.getRoomTypeName(),hlStudentChInEditForm.getStudentId());
					if(listOfRooms != null){
						Map<Integer, String> roomMap = new HashMap<Integer, String>();
						Map<Integer, String> bedMap = new HashMap<Integer, String>();
						Iterator<Object[]> iterator = listOfRooms.iterator();
						while (iterator.hasNext()) {
							Object[] objects = (Object[]) iterator.next();
							if(objects[0] != null && objects[0].toString() != null && objects[1] != null){
								roomMap.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
								if(Integer.parseInt(hlStudentChInEditForm.getRoomName())==Integer.parseInt(objects[0].toString())
										&& objects[2] != null && objects[2].toString() != null && objects[3] != null){
									bedMap.put(Integer.parseInt(objects[2].toString()), objects[3].toString());
								}
							}
						}
						hlStudentChInEditForm.setRoomMap(roomMap);
						hlStudentChInEditForm.setBedMap(bedMap);
					}
				}
					
				setFacilitiesListToForm(hlStudentChInEditForm);
			}
			
		}
		return HlAdmissionToList;
	}

	private void setFacilitiesListToForm(HlStudentChInEditForm hlStudentChInEditForm) throws Exception{
		
		List<HlRoomTypeFacility> roomFacility = new ArrayList<HlRoomTypeFacility>();
		List<HlStudentFacilityAllotted> allotted = new ArrayList<HlStudentFacilityAllotted>();
		if(hlStudentChInEditForm.getHostelAdmissionId()!=null && !hlStudentChInEditForm.getHostelAdmissionId().isEmpty()){
			 allotted = transaction.getHlStudentFacilityAllotted(hlStudentChInEditForm.getHostelAdmissionId());
			 
			 if(hlStudentChInEditForm.getRoomTypeName()!=null && !hlStudentChInEditForm.getRoomTypeName().isEmpty())
				 roomFacility = transaction.getHlRoomTypeFacility(hlStudentChInEditForm.getRoomTypeName());
		}
		if(hlStudentChInEditForm.getFacilityList()!=null && !hlStudentChInEditForm.getFacilityList().isEmpty()){
			Iterator<FacilityTO> facItr=hlStudentChInEditForm.getFacilityList().iterator();
			List<FacilityTO> facilityTo=new ArrayList<FacilityTO>();
			while (facItr.hasNext()) {
				FacilityTO to =facItr.next();
				if(allotted!=null && !allotted.isEmpty()){
				Iterator<HlStudentFacilityAllotted> itr1=allotted.iterator();
				while (itr1.hasNext()) {
					HlStudentFacilityAllotted bo =itr1.next();
					
					if(bo.getHlFacilityId().getId()>0)
					{
						if(bo.getHlFacilityId().getId()==to.getId())
						{
							to.setId(bo.getHlFacilityId().getId());
							if(bo.getIsActive()){
								to.setSelected(true);
								to.setDummySelected(true);
							}
							if(bo.getId()>0){
								to.setHlStudAllotFacId(bo.getId());
							}
							if(bo.getDescription()!=null && !bo.getDescription().isEmpty())
							{
								to.setDescription(bo.getDescription());
							}
							to.setName(to.getName());
						}
					}
					}
			}
			else if(allotted==null || allotted.isEmpty())
			{	
				if (roomFacility != null && !roomFacility.isEmpty()) {
					Iterator<HlRoomTypeFacility> itr2=roomFacility.iterator();
					while (itr2.hasNext()) {
						HlRoomTypeFacility bo =itr2.next();
						if(bo.getHlRoomType().getId()>0)
						{
						if(bo.getHlFacility().getId()>0){
							if(bo.getHlFacility().getId()==to.getId()){
									to.setId(bo.getHlFacility().getId());
									to.setHlRoomTypeId(bo.getHlRoomType().getId());
									to.setSelected(true);
									to.setDummySelected(true);
							}
						}
						}
					}
				}
			}
			facilityTo.add(to);
		}
			hlStudentChInEditForm.setFacilityList(facilityTo);
			HibernateUtil.closeSession();
		}
		
	}

	/**
	 * @param hlStudentChInEditForm
	 * @param hlAdmissionBo
	 * @throws Exception
	 */
	private void addDetailsToForm(HlStudentChInEditForm hlStudentChInEditForm,HlAdmissionBo hlAdmissionBo) throws Exception{
		if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty())
			hlStudentChInEditForm.setStudentName(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		if(hlAdmissionBo.getId()>0)
			hlStudentChInEditForm.setHostelAdmissionId(String.valueOf(hlAdmissionBo.getId()));	
		if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlBlock()!=null && hlAdmissionBo.getRoomId().getHlBlock().getId()>0)
			hlStudentChInEditForm.setBlock(String.valueOf(hlAdmissionBo.getRoomId().getHlBlock().getId()));
		else
			hlStudentChInEditForm.setBlock("");
		if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getFloorNo()!=null && !hlAdmissionBo.getRoomId().getFloorNo().isEmpty())
			hlStudentChInEditForm.setFloorNo(hlAdmissionBo.getRoomId().getFloorNo());
		else
			hlStudentChInEditForm.setFloorNo("");
		if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlUnit()!=null && hlAdmissionBo.getRoomId().getHlUnit().getId()>0)
			hlStudentChInEditForm.setUnit(String.valueOf(hlAdmissionBo.getRoomId().getHlUnit().getId()));
		else
			hlStudentChInEditForm.setUnit("");
		if(hlAdmissionBo.getHostelId()!=null && hlAdmissionBo.getHostelId().getId()>0)
			hlStudentChInEditForm.setHostelId(String.valueOf(hlAdmissionBo.getHostelId().getId()));
		else
			hlStudentChInEditForm.setHostelId("");
		
		if(hlAdmissionBo.getRoomTypeId() != null && hlAdmissionBo.getRoomTypeId().getId() != 0){
			hlStudentChInEditForm.setRoomTypeName(String.valueOf(hlAdmissionBo.getRoomTypeId().getId()));
		}
		if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getId() != 0 ){
			hlStudentChInEditForm.setRoomName(String.valueOf(hlAdmissionBo.getRoomId().getId()));
		}
		if(hlAdmissionBo.getApplicationNo()!=null && !hlAdmissionBo.getApplicationNo().isEmpty())
			hlStudentChInEditForm.setHlApplicationNo(hlAdmissionBo.getApplicationNo());
		if(hlAdmissionBo.getAdmittedDate()!=null && !hlAdmissionBo.getAdmittedDate().toString().isEmpty()){
			String dateString = hlAdmissionBo.getAdmittedDate().toString().substring(0, 10);
			String inputDateFormat = "yyyy-mm-dd";
			String outPutdateFormat = "dd/mm/yyyy";
			hlStudentChInEditForm.setAdmittedDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
		}
		else
		{
			hlStudentChInEditForm.setAdmittedDate(CommonUtil.formatDates(new java.util.Date()));
		}
		if(hlAdmissionBo.getBiometricId()!=null && !hlAdmissionBo.getBiometricId().isEmpty())
			hlStudentChInEditForm.setBiometricId(hlAdmissionBo.getBiometricId());
		if(hlAdmissionBo.getCheckInDate()!=null && !hlAdmissionBo.getCheckInDate().toString().isEmpty()){
			String dateString1 = hlAdmissionBo.getCheckInDate().toString().substring(0, 10);
			String inputDateFormat = "yyyy-mm-dd";
			String outPutdateFormat = "dd/mm/yyyy";
			hlStudentChInEditForm.setCheckInDate(CommonUtil.ConvertStringToDateFormat(dateString1, inputDateFormat, outPutdateFormat));
		}else
			hlStudentChInEditForm.setCheckInDate(CommonUtil.formatDates(new java.util.Date()));
		if(hlAdmissionBo.getIsCheckedIn()!=null && !hlAdmissionBo.getIsCheckedIn().toString().isEmpty()){
			if(hlAdmissionBo.getIsCheckedIn().toString().equalsIgnoreCase("true"))
				hlStudentChInEditForm.setIsCheckIn("1");
			else
				hlStudentChInEditForm.setIsCheckIn("0");
		}
		if(hlAdmissionBo.getStudentId()!=null && hlAdmissionBo.getStudentId().getRegisterNo()!=null && !hlAdmissionBo.getStudentId().getRegisterNo().isEmpty())
			hlStudentChInEditForm.setRegNo(hlAdmissionBo.getStudentId().getRegisterNo());
		if(hlAdmissionBo.getCourseId()!=null && hlAdmissionBo.getCourseId().getId()>0)
			hlStudentChInEditForm.setCourseId(String.valueOf(hlAdmissionBo.getCourseId().getId()));
			hlStudentChInEditForm.setCourseName(hlAdmissionBo.getCourseId().getCode());
		if(hlAdmissionBo.getStudentId()!=null && hlAdmissionBo.getStudentId().getId()>0)
			hlStudentChInEditForm.setStudentId(String.valueOf(hlAdmissionBo.getStudentId().getId()));
		hlStudentChInEditForm.setId(hlAdmissionBo.getId());
		if(hlAdmissionBo.getBedId()!=null && hlAdmissionBo.getBedId().getId()>0){
			hlStudentChInEditForm.setBedNo(String.valueOf(hlAdmissionBo.getBedId().getId()));
		}
	}
}
