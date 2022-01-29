package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.to.hostel.HlAdmissionTo;

public class HostelStudentDetailsHelper {
	
	public List<HlAdmissionTo> convertHostelStudentBOtoTO(List<HlAdmissionBo> hlAdmissionBoList,Map<String, ExamStudentDetentionRejoinDetails> detaintionRejoinMap)
	{
		String name="";
		List<HlAdmissionTo> hlAdmissionToList =new ArrayList<HlAdmissionTo>();
		if(hlAdmissionBoList!=null)
		{
			Iterator iterator=hlAdmissionBoList.iterator();
			while (iterator.hasNext()) {
				name="";
				HlAdmissionBo hlAdmissionBo = (HlAdmissionBo) iterator.next();
				HlAdmissionTo hlAdmissionTo=new HlAdmissionTo();
				if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null)
				{
				name=name+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName();
				}
				if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName()!=null)
				{
				name=name+" "+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName();
				}
				if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getLastName()!=null)
				{
				name=name+" "+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getLastName();
				}
				hlAdmissionTo.setStudentName(name);
				if(hlAdmissionBo.getStudentId().getRegisterNo()!=null)
				{
				hlAdmissionTo.setRegNo(hlAdmissionBo.getStudentId().getRegisterNo());
				}
				if(hlAdmissionBo.getStudentId().getClassSchemewise()!=null)
				{
				if(hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName()!=null)
				{
				hlAdmissionTo.setStudentClass(hlAdmissionBo.getStudentId().getClassSchemewise().getClasses().getName());
				}
				}
				if(hlAdmissionBo.getHostelId().getName()!=null)
				{
				hlAdmissionTo.setHostelName(hlAdmissionBo.getHostelId().getName());
				}
				if(hlAdmissionBo.getRoomId()!=null)
				{
				if(hlAdmissionBo.getRoomId().getHlUnit().getName()!=null)
				{
				hlAdmissionTo.setUnitId(hlAdmissionBo.getRoomId().getHlUnit().getName());
				}
				if(hlAdmissionBo.getRoomId().getName()!=null)
				{
				hlAdmissionTo.setRoomName(hlAdmissionBo.getRoomId().getName());
				}
				}
				if(hlAdmissionBo.getBedId()!=null)
				{
				if(hlAdmissionBo.getBedId().getBedNo()!=null)
				{
				hlAdmissionTo.setBedNo(hlAdmissionBo.getBedId().getBedNo());
				}
				}
				//if(studentPhotoList.contains(hlAdmissionBo.getStudentId().getId()))
				//	hlAdmissionTo.setIsPhoto(true);

				if(hlAdmissionBo.getStudentId().getIsHide()!=null && hlAdmissionBo.getStudentId().getIsHide()==true )
				{
					hlAdmissionTo.setStatus(CMSConstants.ADMISSION_STUDENT_HIDDEN);	
				}
				else{
					if(detaintionRejoinMap.containsKey(String.valueOf(hlAdmissionBo.getStudentId().getId()))){
						ExamStudentDetentionRejoinDetails examStudentDetentionRejoinDetails=detaintionRejoinMap.get(String.valueOf(hlAdmissionBo.getStudentId().getId()));
					if(examStudentDetentionRejoinDetails!=null){
						if(examStudentDetentionRejoinDetails.getDetain()!=null && examStudentDetentionRejoinDetails.getDetain()==true){
							if( examStudentDetentionRejoinDetails.getRejoin()!=null){
								if(examStudentDetentionRejoinDetails.getRejoin()!=true)
								hlAdmissionTo.setStatus(CMSConstants.ADMISSION_STUDENT_DETAINED);
								}else 
								hlAdmissionTo.setStatus(CMSConstants.ADMISSION_STUDENT_DETAINED);
							}else if(examStudentDetentionRejoinDetails.getDiscontinued()!=null && examStudentDetentionRejoinDetails.getDiscontinued()==true){ 
								if( examStudentDetentionRejoinDetails.getRejoin()!=null){
									if(examStudentDetentionRejoinDetails.getRejoin()!=true)
										hlAdmissionTo.setStatus(CMSConstants.ADMISSION_STUDENT_DISCONTINUED);
									}else 
										hlAdmissionTo.setStatus(CMSConstants.ADMISSION_STUDENT_DISCONTINUED);
								}else{
									hlAdmissionTo.setStatus("");
								}
					}else{
						hlAdmissionTo.setStatus("");
					}
					}else
						hlAdmissionTo.setStatus("");
					
					
				}
				hlAdmissionToList.add(hlAdmissionTo);
			}
		}
		
		return hlAdmissionToList;
		
	}

}
