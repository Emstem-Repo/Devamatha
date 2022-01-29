package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlAttendance;
import com.kp.cms.bo.admin.HlGroupStudent;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.forms.hostel.AttendanceForm;
import com.kp.cms.handlers.hostel.AttendanceHandler;
import com.kp.cms.to.hostel.HostelGroupStudentTO;
import com.kp.cms.utilities.CommonUtil;

public class AttendanceHelper {
	public static Log log = LogFactory.getLog(AttendanceHandler.class);
	public static volatile AttendanceHelper attendanceHelper;
	
	public static AttendanceHelper getInstance(){
		if(attendanceHelper == null){
			attendanceHelper = new AttendanceHelper();
			return attendanceHelper;
		}
		return attendanceHelper;
	}	
	/**
	 * setting Bo values to TO for display
	 * @param studList
	 * @return
	 */
	
	public List<HostelGroupStudentTO> copyHostelGroupBosToTos(List<HlGroupStudent> studList) {
		log.debug("inside copyHostelGroupBosToTos");
		List<HostelGroupStudentTO> studTOList = new ArrayList<HostelGroupStudentTO>();
		Iterator<HlGroupStudent> iterator = studList.iterator();
		HlGroupStudent hlGroupStudent;
		HostelGroupStudentTO hStudentTO;
		while (iterator.hasNext()) {
			hStudentTO = new HostelGroupStudentTO();
			hlGroupStudent = (HlGroupStudent) iterator.next();
			StringBuffer name = new StringBuffer();
			if(hlGroupStudent.getHlApplicationForm().getIsStaff())
			{
				if(hlGroupStudent.getHlApplicationForm().getEmployee().getFirstName()!= null &&
						!hlGroupStudent.getHlApplicationForm().getEmployee().getFirstName().isEmpty()){
					name.append(hlGroupStudent.getHlApplicationForm().getEmployee().getFirstName());
				}
				/*if(hlGroupStudent.getHlApplicationForm().getEmployee().getMiddleName()!= null &&
						!hlGroupStudent.getHlApplicationForm().getEmployee().getMiddleName().isEmpty()){
					if(name.toString()!= null){
						name.append(" ");
					}
					name.append(hlGroupStudent.getHlApplicationForm().getEmployee().getMiddleName());
				}
				if(hlGroupStudent.getHlApplicationForm().getEmployee().getLastName()!= null &&
						!hlGroupStudent.getHlApplicationForm().getEmployee().getLastName().isEmpty()){
					name.append(" " + hlGroupStudent.getHlApplicationForm().getEmployee().getLastName());
				}*/
			}
			else
			{	
				name.append(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getFirstName());
				if(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getMiddleName()!= null && !hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getMiddleName().trim().isEmpty()){
					name.append(" " + hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getMiddleName());
				}
				if(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getLastName()!= null && !hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getLastName().trim().isEmpty()){
					name.append(" " + hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getLastName());
				}
			}	
			hStudentTO.setName(name.toString());
			hStudentTO.setId(hlGroupStudent.getId());
			hStudentTO.setSelected(false);
			hStudentTO.setDummySelected(false);
			studTOList.add(hStudentTO);
		}
		log.debug("leaving copyHostelGroupBosToTos");
		return studTOList;
	}

	/**
	 * setting form values to BO
	 * @param attForm
	 * @return
	 * @throws Exception
	 */
	public List<HlAttendance> populateAttendanceDetails(AttendanceForm attForm) throws Exception {
		log.debug("populateAttendanceDetails");
		List<HlAttendance> attBoList = new ArrayList<HlAttendance>();
		Iterator<HostelGroupStudentTO> attIter = attForm.getHostelGroupStudentList().iterator(); 
		HlHostel hlHostel = new HlHostel();
		if(attForm.getHostelId()!= null && !attForm.getHostelId().trim().isEmpty()){
			hlHostel.setId(Integer.parseInt(attForm.getHostelId()));
		}
		while(attIter.hasNext()){
			HlAttendance hlAttendance = new HlAttendance();
			HlGroupStudent hlGroupStudent = new HlGroupStudent();
			HostelGroupStudentTO hlGroupStudentTO = attIter.next();
			hlGroupStudent.setId(hlGroupStudentTO.getId());
			hlAttendance.setHlHostel(hlHostel);
			hlAttendance.setHlGroupStudent(hlGroupStudent);
			hlAttendance.setAttendanceDate(CommonUtil.ConvertStringToSQLDate(attForm.getAttendanceDate()));
			if(hlGroupStudentTO.isSelected()){
				hlAttendance.setIsPresent(false);
			}
			else
			{
				hlAttendance.setIsPresent(true);
			}
			hlAttendance.setCreatedDate(new Date());
			hlAttendance.setLastModifiedDate(new Date());
			hlAttendance.setCreatedBy(attForm.getUserId());
			hlAttendance.setModifiedBy(attForm.getUserId());
			hlAttendance.setIsActive(true);
			attBoList.add(hlAttendance);
		}
		log.debug("exit populateAttendanceDetails");	
		return attBoList;
		
	}	
}
