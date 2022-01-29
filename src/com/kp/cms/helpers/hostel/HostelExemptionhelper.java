package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.forms.hostel.HostelExemptionForm;
import com.kp.cms.to.hostel.HostelExemptionTo;


public class HostelExemptionhelper {
	private static final Log log = LogFactory.getLog(HostelExemptionhelper.class);
	public static volatile HostelExemptionhelper hostelExemptionhelper=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static HostelExemptionhelper getInstance(){
		if(hostelExemptionhelper==null){
			hostelExemptionhelper= new HostelExemptionhelper();
			}
		return hostelExemptionhelper;
	}
	
	public List<HostelExemptionTo>  convertBotoTo(List<HlAdmissionBo> BOList,Map<Integer,Integer> alreadySavedDataMap,HostelExemptionForm hostelExemptionForm) {
		List<HostelExemptionTo> htlist=new ArrayList<HostelExemptionTo>();
		if(BOList !=null && !BOList.isEmpty()){
		Iterator<HlAdmissionBo> itr=BOList.iterator();
		while(itr.hasNext()){
			HlAdmissionBo bo=(HlAdmissionBo)itr.next();
			HostelExemptionTo to=new HostelExemptionTo();
			if(bo.getCourseId()!=null && bo.getStudentId()!=null && bo.getRoomId()!=null){
				to.setHlAdmitionId(bo.getId());
				if(bo.getCourseId().getName()!=null && !bo.getCourseId().getName().isEmpty())
				to.setCourseName(bo.getCourseId().getName());
				if( bo.getStudentId().getRegisterNo()!=null &&  !bo.getStudentId().getRegisterNo().isEmpty())
				to.setRegisterNo(bo.getStudentId().getRegisterNo());
				if(bo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty())
				to.setName(bo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				if(bo.getRoomId().getHlBlock().getName()!=null && !bo.getRoomId().getHlBlock().getName().isEmpty())
				to.setBlock(bo.getRoomId().getHlBlock().getName());
				if(bo.getRoomId().getHlUnit().getName()!=null && !bo.getRoomId().getHlUnit().getName().isEmpty())
				to.setUnit(bo.getRoomId().getHlUnit().getName());
				if(alreadySavedDataMap !=null && !alreadySavedDataMap.isEmpty()){
				if(alreadySavedDataMap.containsKey(bo.getId()))
					to.setSelected(true);
					hostelExemptionForm.setStudentExist(true);
				}
			htlist.add(to);
			}	
		}
		}
			return htlist;
		}
	}


