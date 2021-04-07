package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.hostel.HlAdmissionBookingWaitingBo;
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.to.hostel.HostelWaitingListViewTo;
import com.kp.cms.utilities.CommonUtil;

public class HostelWaitingListHelper {
	
	
	private static final Log log=LogFactory.getLog(HostelWaitingListHelper.class);
	public static volatile HostelWaitingListHelper hostelWaitingListHelper=null;
	public static HostelWaitingListHelper getInstance()
	{
		if(hostelWaitingListHelper==null)
		{
			hostelWaitingListHelper=new HostelWaitingListHelper();
			return hostelWaitingListHelper;
		}
		return hostelWaitingListHelper;
	}

	
	public List<HostelWaitingListViewTo> convertBoTOTos(List<HlAdmissionBookingWaitingBo> hlAdmissionBookingWaitingBoList,HlAdmissionForm hlAdmissionForm)
	{
		
		List<HostelWaitingListViewTo> hostelWaitingListViewToList=new ArrayList<HostelWaitingListViewTo>();
		HostelWaitingListViewTo hostelWaitingListViewTo=null;
		Iterator iterator=hlAdmissionBookingWaitingBoList.iterator();
		while (iterator.hasNext()) {
			hostelWaitingListViewTo=new HostelWaitingListViewTo();
			HlAdmissionBookingWaitingBo bookingWaitingBo = (HlAdmissionBookingWaitingBo) iterator.next();
			
			hostelWaitingListViewTo.setId(bookingWaitingBo.getId());
			if(bookingWaitingBo.getStudentId().getRegisterNo()!=null )
			{
			hostelWaitingListViewTo.setRegisterNo(bookingWaitingBo.getStudentId().getRegisterNo());
			}
			if(bookingWaitingBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null)
			{
				hostelWaitingListViewTo.setStudentName(bookingWaitingBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			}
			if(bookingWaitingBo.getStudentId().getAdmAppln().getCourse().getName()!=null)
			{
				hostelWaitingListViewTo.setCourseName(bookingWaitingBo.getStudentId().getAdmAppln().getCourse().getName());
			}
			if(bookingWaitingBo.getRoomTypeId().getName()!=null)
			{
			hostelWaitingListViewTo.setRoomTypeName(bookingWaitingBo.getRoomTypeId().getName());	
			}
			if(bookingWaitingBo.getWaitingListPriorityNo()!=0)
			{
			hostelWaitingListViewTo.setWaitingListPriorityNo(bookingWaitingBo.getWaitingListPriorityNo());	
			}
			if(bookingWaitingBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1()!=null)
			{
				hostelWaitingListViewTo.setMobileNo1(bookingWaitingBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1());
			}
			if(bookingWaitingBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null)
			{
				hostelWaitingListViewTo.setMobileNo2(bookingWaitingBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2());
			}
			if(bookingWaitingBo.getStudentId().getAdmAppln().getPersonalData().getEmail()!=null)
			{
				hostelWaitingListViewTo.setEmailId(bookingWaitingBo.getStudentId().getAdmAppln().getPersonalData().getEmail());	
			}
			if(bookingWaitingBo.getApplicationNo()!=null && !bookingWaitingBo.getApplicationNo().isEmpty())
			{
				hostelWaitingListViewTo.setHostelApplicationNo(bookingWaitingBo.getApplicationNo());
			}
			if(String.valueOf(bookingWaitingBo.getStudentId().getAdmAppln().getApplnNo())!=null)
			{
				hostelWaitingListViewTo.setApplicationNo(String.valueOf(bookingWaitingBo.getStudentId().getAdmAppln().getApplnNo()));
			}
			if(bookingWaitingBo.getHostelId().getName()!=null)
			{
				hostelWaitingListViewTo.setHostelName(bookingWaitingBo.getHostelId().getName());
			}
			if(bookingWaitingBo.getIntimatedDate()!=null)
			{
				hostelWaitingListViewTo.setTempChecked("on");
				hostelWaitingListViewTo.setIntimateDate(CommonUtil.formatDates(bookingWaitingBo.getIntimatedDate()));
			}
			hostelWaitingListViewToList.add(hostelWaitingListViewTo);
		}
		return hostelWaitingListViewToList;
		
	}
}
