package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.StuCocurrLeaveDetails;
import com.kp.cms.forms.attendance.CocurricularLeaveApproveForm;
import com.kp.cms.to.attendance.ApproveCocurricularLeaveTO;
import com.kp.cms.utilities.CommonUtil;

public class CocurricularLeaveApproveHelper {
	private static volatile CocurricularLeaveApproveHelper cocurricularLeaveApproveHelper = null;
	private static final Log log = LogFactory.getLog(CocurricularLeaveApproveHelper.class);
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	public static CocurricularLeaveApproveHelper getInstance()
	{
		if(cocurricularLeaveApproveHelper== null)
		{
			cocurricularLeaveApproveHelper = new CocurricularLeaveApproveHelper();
		}
		return cocurricularLeaveApproveHelper;
	}
	public List<ApproveCocurricularLeaveTO> convertBOListToTOList(List<StuCocurrLeave> cocurrLeavesBo) throws Exception{
		log.debug("call of convertBOListToTOList method in CocurricularLeaveApproveHelper.class");
		List<ApproveCocurricularLeaveTO> cocurricularLeaveTOs = new ArrayList<ApproveCocurricularLeaveTO>();
		if(cocurrLeavesBo!= null && cocurrLeavesBo.size()>0)
		{
			Iterator<StuCocurrLeave> iterator = cocurrLeavesBo.iterator();
			while(iterator.hasNext())
			{
				StuCocurrLeave leaveBo = iterator.next();
				ApproveCocurricularLeaveTO leaveTO = new ApproveCocurricularLeaveTO();
				leaveTO.setId(leaveBo.getId());
				if(leaveBo.getSubject()!=null)
				{
					leaveTO.setSubjectId(Integer.toString(leaveBo.getSubject().getId()));
					leaveTO.setSubjectName(leaveBo.getSubject().getName());
				}
				if(leaveBo.getActivity()!=null)
				{
					leaveTO.setActivityId(Integer.toString(leaveBo.getActivity().getId()));
					leaveTO.setActivityName(leaveBo.getActivity().getName());
				}
				if(leaveBo.getClassSchemewise()!=null)
				{
					leaveTO.setClassId(Integer.toString(leaveBo.getClassSchemewise().getId()));
					leaveTO.setClassName(leaveBo.getClassSchemewise().getClasses().getName());
				}
				if(leaveBo.getStartPeriod()!=null)
				{
					leaveTO.setPeriodId(Integer.toString(leaveBo.getStartPeriod().getId()));
					leaveTO.setPeriodName(leaveBo.getStartPeriod().getPeriodName());
				}
				if(leaveBo.getStartDate()!=null)
				{
					leaveTO.setAttendanceDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(leaveBo.getStartDate()),
							CocurricularLeaveApproveHelper.SQL_DATEFORMAT,CocurricularLeaveApproveHelper.FROM_DATEFORMAT));
				}
				
				else
				{
					leaveTO.setApprovedByAuthoriser(Boolean.FALSE);
				}
				if(leaveBo.getApprovedByTeacher()!=null && leaveBo.getApprovedByTeacher())
				{
					leaveTO.setApprovedByTeacher(leaveBo.getApprovedByTeacher());
					leaveTO.setTempChecked(Boolean.TRUE);
				}
				else
				{
					leaveTO.setApprovedByTeacher(Boolean.FALSE);
					leaveTO.setTempChecked(Boolean.FALSE);
				}
				
				Set<StuCocurrLeaveDetails> details = new HashSet<StuCocurrLeaveDetails>();
				details = leaveBo.getStuCocurrLeaveDetailses();
				for(StuCocurrLeaveDetails  leaveDetails : details)
				{
					leaveTO.setStudentId(Integer.toString(leaveDetails.getStudent().getId()));
					
					if(leaveDetails.getStudent().getRegisterNo()!=null)
					{
						
						leaveTO.setRegisterNumber(leaveDetails.getStudent().getRegisterNo());
					}
					StringBuffer studentName = new StringBuffer();
					if(leaveDetails.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null)
					{
						studentName.append(leaveDetails.getStudent().getAdmAppln().getPersonalData().getFirstName()).append( " ");
					}
					if(leaveDetails.getStudent().getAdmAppln().getPersonalData().getMiddleName()!=null)
					{
						studentName.append(leaveDetails.getStudent().getAdmAppln().getPersonalData().getMiddleName()).append( " ");
					}
					if(leaveDetails.getStudent().getAdmAppln().getPersonalData().getLastName()!=null)
					{
						studentName.append(leaveDetails.getStudent().getAdmAppln().getPersonalData().getLastName());
					}
					leaveTO.setStudentName(studentName.toString());
				}
				
				cocurricularLeaveTOs.add(leaveTO);
				
			}
		}
		log.debug("end of convertBOListToTOList method in CocurricularLeaveApproveHelper.class");
		return cocurricularLeaveTOs;
	}
	public List<StuCocurrLeave> approveCocurricularApplicationsByTeacher(CocurricularLeaveApproveForm cocurricularLeaveApproveForm) throws Exception {
		log.debug("call of approveCocurricularApplicationsByTeacher method in CocurricularLeaveApproveHelper.class");
		List<StuCocurrLeave> applicationList = new ArrayList<StuCocurrLeave>();
		Iterator<ApproveCocurricularLeaveTO> iterator = cocurricularLeaveApproveForm.getList().iterator();
		while(iterator.hasNext())
		{
			ApproveCocurricularLeaveTO leaveTO = iterator.next();
			if(leaveTO.isChecked())
			{
				StuCocurrLeave cocurrLeave = new StuCocurrLeave();
				cocurrLeave.setId(leaveTO.getId());
				cocurrLeave.setApproverTeacher(cocurricularLeaveApproveForm.getUserId());
				cocurrLeave.setApprovedByTeacher(Boolean.TRUE);
				cocurrLeave.setModifiedBy(cocurricularLeaveApproveForm.getUserId());
				cocurrLeave.setLastModifiedDate(new Date());
				cocurrLeave.setCancelledByTeacher(Boolean.FALSE);
				applicationList.add(cocurrLeave);
			}
		}
		log.debug("end of approveCocurricularApplicationsByTeacher method in CocurricularLeaveApproveHelper.class");
		return applicationList;
	}
	public List<ApproveCocurricularLeaveTO> convertBOtoTOListForAuthorize(List<StuCocurrLeave> bolist, Map<Integer, String> userMap) {
		log.debug("call of convertBOtoTOListForAuthorize method in CocurricularLeaveApproveHelper.class");
		List<ApproveCocurricularLeaveTO> cocurricularLeaveTOs = new ArrayList<ApproveCocurricularLeaveTO>();
		if(bolist!= null && bolist.size()>0)
		{
			Iterator<StuCocurrLeave> iterator = bolist.iterator();
			while(iterator.hasNext())
			{
				StuCocurrLeave leaveBo = iterator.next();
				ApproveCocurricularLeaveTO leaveTO = new ApproveCocurricularLeaveTO();
				
				leaveTO.setId(leaveBo.getId());
				if(leaveBo.getSubject()!=null)
				{
					leaveTO.setSubjectId(Integer.toString(leaveBo.getSubject().getId()));
					leaveTO.setSubjectName(leaveBo.getSubject().getName());
				}
				if(leaveBo.getActivity()!=null)
				{
					leaveTO.setActivityId(Integer.toString(leaveBo.getActivity().getId()));
					leaveTO.setActivityName(leaveBo.getActivity().getName());
				}
				if(leaveBo.getClassSchemewise()!=null)
				{
					leaveTO.setClassId(Integer.toString(leaveBo.getClassSchemewise().getId()));
					leaveTO.setClassName(leaveBo.getClassSchemewise().getClasses().getName());
				}
				if(leaveBo.getStartPeriod()!=null)
				{
					leaveTO.setPeriodId(Integer.toString(leaveBo.getStartPeriod().getId()));
					leaveTO.setPeriodName(leaveBo.getStartPeriod().getPeriodName());
				}
				if(leaveBo.getStartDate()!=null)
				{
					leaveTO.setAttendanceDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(leaveBo.getStartDate()),
							CocurricularLeaveApproveHelper.SQL_DATEFORMAT,CocurricularLeaveApproveHelper.FROM_DATEFORMAT));
				}
				
				else
				{
					leaveTO.setApprovedByAuthoriser(Boolean.FALSE);
				}
				if(leaveBo.getApprovedByTeacher()!=null && leaveBo.getApprovedByTeacher())
				{
					leaveTO.setApprovedByTeacher(leaveBo.getApprovedByTeacher());
					leaveTO.setTempChecked(Boolean.TRUE);
				}
				else
				{
					leaveTO.setApprovedByTeacher(Boolean.FALSE);
					leaveTO.setTempChecked(Boolean.FALSE);
				}
				Set<StuCocurrLeaveDetails> details = new HashSet<StuCocurrLeaveDetails>();
				details = leaveBo.getStuCocurrLeaveDetailses();
				for(StuCocurrLeaveDetails  leaveDetails : details)
				{
					leaveTO.setStudentId(Integer.toString(leaveDetails.getStudent().getId()));
					
					if(leaveDetails.getStudent().getRegisterNo()!=null)
					{
						
						leaveTO.setRegisterNumber(leaveDetails.getStudent().getRegisterNo());
					}
					StringBuffer studentName = new StringBuffer();
					if(leaveDetails.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null)
					{
						studentName.append(leaveDetails.getStudent().getAdmAppln().getPersonalData().getFirstName()).append( " ");
					}
					if(leaveDetails.getStudent().getAdmAppln().getPersonalData().getMiddleName()!=null)
					{
						studentName.append(leaveDetails.getStudent().getAdmAppln().getPersonalData().getMiddleName()).append( " ");
					}
					if(leaveDetails.getStudent().getAdmAppln().getPersonalData().getLastName()!=null)
					{
						studentName.append(leaveDetails.getStudent().getAdmAppln().getPersonalData().getLastName());
					}
					leaveTO.setStudentName(studentName.toString());
				}
				if(leaveBo.getApproverTeacher()!=null )
				{
					leaveTO.setApproveTeacherName(userMap.get(Integer.parseInt(leaveBo.getApproverTeacher())));
				}
				cocurricularLeaveTOs.add(leaveTO);
				
			}
		}
		log.debug("end of convertBOtoTOListForAuthorize method in CocurricularLeaveApproveHelper.class");
		return cocurricularLeaveTOs;
	}
	public List<StuCocurrLeave> saveCocurricularAttendaceAndUpdateAttendace(CocurricularLeaveApproveForm cocurricularLeaveApproveForm) throws Exception{
		log.debug("call of saveCocurricularAttendaceAndUpdateAttendace method in CocurricularLeaveApproveHelper.class");
		List<StuCocurrLeave> applicationList = new ArrayList<StuCocurrLeave>();
		Iterator<ApproveCocurricularLeaveTO> iterator = cocurricularLeaveApproveForm.getList().iterator();
		while(iterator.hasNext())
		{
			ApproveCocurricularLeaveTO leaveTO = iterator.next();
			StuCocurrLeave cocurrLeave = new StuCocurrLeave();
			cocurrLeave.setId(leaveTO.getId());
			if(leaveTO.isChecked())
			{
				cocurrLeave.setModifiedBy(cocurricularLeaveApproveForm.getUserId());
				cocurrLeave.setLastModifiedDate(new Date());
			}
			else
			{
				cocurrLeave.setModifiedBy(cocurricularLeaveApproveForm.getUserId());
				cocurrLeave.setLastModifiedDate(new Date());
			}
			
			applicationList.add(cocurrLeave);
			
		}
		
		log.debug("call of saveCocurricularAttendaceAndUpdateAttendace method in CocurricularLeaveApproveHelper.class");
		return applicationList;
	}
	public List<StuCocurrLeave> cancelCocurricularApplicationsByTeacher(CocurricularLeaveApproveForm cocurricularLeaveApproveForm) throws Exception {
		log.debug("call of cancelCocurricularApplicationsByTeacher method in CocurricularLeaveApproveHelper.class");
		List<StuCocurrLeave> applicationList = new ArrayList<StuCocurrLeave>();
		Iterator<ApproveCocurricularLeaveTO> iterator = cocurricularLeaveApproveForm.getList().iterator();
		while(iterator.hasNext())
		{
			ApproveCocurricularLeaveTO leaveTO = iterator.next();
			
			if(leaveTO.isChecked())
			{
				StuCocurrLeave cocurrLeave = new StuCocurrLeave();
				cocurrLeave.setId(leaveTO.getId());
				cocurrLeave.setApproverTeacher(cocurricularLeaveApproveForm.getUserId());
				cocurrLeave.setApprovedByTeacher(Boolean.FALSE);
				cocurrLeave.setCancelledByTeacher(Boolean.TRUE);
				cocurrLeave.setModifiedBy(cocurricularLeaveApproveForm.getUserId());
				cocurrLeave.setLastModifiedDate(new Date());
				applicationList.add(cocurrLeave);
			}
			
			
			
			
		}
		log.debug("end of cancelCocurricularApplicationsByTeacher method in CocurricularLeaveApproveHelper.class");
		return applicationList;
	}
	

}
