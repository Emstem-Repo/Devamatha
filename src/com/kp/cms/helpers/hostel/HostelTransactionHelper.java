package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.handlers.hostel.HostelTransactionHandler;
import com.kp.cms.to.hostel.HostelTransactionTo;

public class HostelTransactionHelper {
	private static final Log log=LogFactory.getLog(HostelTransactionHelper.class);
	public static volatile HostelTransactionHelper hostelTransactionHelper=null;
	public static HostelTransactionHelper getInstance()
	{
		if(hostelTransactionHelper==null)
		{
			hostelTransactionHelper=new HostelTransactionHelper();
			return hostelTransactionHelper;
		}
		return hostelTransactionHelper;
	}
	public List<HostelTransactionTo>  convertBotoTo(List<HlAdmissionBo> BOList) {
		List<HostelTransactionTo> htlist=new ArrayList<HostelTransactionTo>();
		Iterator itr=BOList.iterator();
		while(itr.hasNext()){
			HlAdmissionBo bo=(HlAdmissionBo)itr.next();
			if(bo.getRoomId()!=null &&  bo.getStudentId()!=null){
			if( bo.getStudentId().getRegisterNo()!=null){
			if(bo.getRoomId().getName() != null && !bo.getRoomId().getName().isEmpty()){
			HostelTransactionTo to=new HostelTransactionTo();
			to.setCourseId(bo.getCourseId().getId());
			to.setCourseName(bo.getCourseId().getName());
			to.setHlRoomId(bo.getRoomId().getId());
			to.setRoomNo((bo.getRoomId().getName()));
			to.setRegisterNo(bo.getStudentId().getRegisterNo());
			to.setStudentId(bo.getStudentId().getId());
			to.setStudentName(bo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			htlist.add(to);
					}
				}	
			}
		}
		return htlist;
	}
}
