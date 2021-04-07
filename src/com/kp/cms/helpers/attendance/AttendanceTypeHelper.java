package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.AttendanceTypeMandatory;
import com.kp.cms.to.attendance.AttendanceTypeMandatoryTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;

public class AttendanceTypeHelper {
	
private static final Log log = LogFactory.getLog(AttendanceTypeHelper.class);
	
	private static volatile AttendanceTypeHelper attendanceTypeHelper = null;
	
	private AttendanceTypeHelper() {
	}
	/**
	 * 
	 * @returns a single instance when called (Singleton implementation)
	 */
	public static AttendanceTypeHelper getInstance() {
		if (attendanceTypeHelper == null) {
			attendanceTypeHelper = new AttendanceTypeHelper();
		}
		return attendanceTypeHelper;
	}
	
	/**
	 * Used while viewing all the attendanceTypes
	 * 
	 */
	
	public List<AttendanceTypeTO> copyAttendanceTypeBOtoTO(List<AttendanceType> attendanceTypeBOList)throws Exception{
		log.info("Inside of copyAttendanceTypeBOtoTO of AttendanceTypeHelper");
		//Coverts the list of BO to corresponding To class object
		List<AttendanceTypeTO> attendanceTypeTOList=null;		
		Set<AttendanceTypeMandatory> attMandatorySet=null;
		
		AttendanceType attendanceType;
		AttendanceTypeMandatory attendanceTypeMandatory;
		AttendanceTypeTO attendanceTypeTO;
		AttendanceTypeMandatoryTO attendanceTypeMandatoryTO = null;	
		if(attendanceTypeBOList != null && !attendanceTypeBOList.isEmpty()){
			Iterator<AttendanceType> iterator = attendanceTypeBOList.iterator();
			attendanceTypeTOList = new ArrayList<AttendanceTypeTO>();
			while (iterator.hasNext()) {
				attendanceType = iterator.next();				
				attendanceTypeTO = new AttendanceTypeTO();
				attendanceTypeTO.setId(attendanceType.getId()!=0 ? attendanceType.getId():0);
				attendanceTypeTO.setAttendanceTypeName(attendanceType.getName()!=null ? attendanceType.getName():null);
				if(attendanceType.getIsDefault()==true){
					//If default is true then set Yes else set No
					attendanceTypeTO.setDefaultValue("Yes");	
				}
				else{
					attendanceTypeTO.setDefaultValue("No");
				}				
				attMandatorySet = attendanceType.getAttendanceTypeMandatories();
				List<AttendanceTypeMandatory> mandatoryList=new ArrayList<AttendanceTypeMandatory>();
			
				if(attMandatorySet != null && !attMandatorySet.isEmpty()){
					List<AttendanceTypeMandatoryTO> attendanceTypeMandatoryTOList = new ArrayList<AttendanceTypeMandatoryTO>();
					//Sort the mandatory names available in the Set based on the mandatory Ids
					mandatoryList.addAll(attMandatorySet);
					Collections.sort(mandatoryList);
					
					Iterator<AttendanceTypeMandatory> it = mandatoryList.iterator();
					StringBuilder appendedString =new StringBuilder();
					String temp = "";
					int count = 0;
						while (it.hasNext()) {
							attendanceTypeMandatory = it.next();
							if(attendanceTypeMandatory.getIsActive()==true){
							attendanceTypeMandatoryTO = new AttendanceTypeMandatoryTO();
							attendanceTypeMandatoryTO.setId(attendanceTypeMandatory.getId()!=0 ? attendanceTypeMandatory.getId():0);
							//Below conditions used to put comma after one name
								if (count < 3) {
									appendedString.append(attendanceTypeMandatory.getName()).append(",");
								} else {
									appendedString.append("\n").append(attendanceTypeMandatory.getName()).append(",");
									count = 0;
								}
							count++;
								if (appendedString.toString().endsWith(",") == true) {
									temp = StringUtils.chop(appendedString.toString());
								}
							}
						}
					attendanceTypeMandatoryTO.setName(temp);						
					attendanceTypeMandatoryTOList.add(attendanceTypeMandatoryTO);
					attendanceTypeTO.setAttendanceTypeMandatoryTOList(attendanceTypeMandatoryTOList);
				}
				attendanceTypeTOList.add(attendanceTypeTO);
			}	
		}
		log.info("End of copyAttendanceTypeBOtoTO of AttendanceTypeHelper");
		return attendanceTypeTOList;
	}

	/**
	 * 
	 * @param attendanceTypeList
	 * @return
	 * Used for Activity Master entry
	 */
	public List<AttendanceTypeTO> copyAttendanceTypeBosToTos(List<AttendanceType> attendanceTypeList)throws Exception {
		log.debug("inside copyAttendanceTypeBosToTos");
		List<AttendanceTypeTO> attendanceTypeTOList = new ArrayList<AttendanceTypeTO>();
		Iterator<AttendanceType> i = attendanceTypeList.iterator();
		AttendanceType attendanceType;
		AttendanceTypeTO attendanceTypeTO;
		while (i.hasNext()) {
			attendanceTypeTO = new AttendanceTypeTO();
			attendanceType = (AttendanceType) i.next();
			attendanceTypeTO.setId(attendanceType.getId());
			attendanceTypeTO.setAttendanceTypeName(attendanceType.getName());
			attendanceTypeTOList.add(attendanceTypeTO);
		}
		log.debug("leaving copyAttendanceTypeBosToTos");
		return attendanceTypeTOList;
	}
	
	
	/**
	 * 
	 * @param attendanceTypeList
	 * @return
	 * Used for Activity Master entry
	 */
	public List<AttendanceTypeTO> copyAttendanceTypeBosToTosWithMandatory(List<AttendanceType> attendanceTypeList)throws Exception {
		log.debug("inside copyAttendanceTypeBosToTos");
		List<AttendanceTypeTO> attendanceTypeTOList = new ArrayList<AttendanceTypeTO>();
		Iterator<AttendanceType> i = attendanceTypeList.iterator();
		AttendanceType attendanceType;
		AttendanceTypeTO attendanceTypeTO;
		while (i.hasNext()) {
			attendanceTypeTO = new AttendanceTypeTO();
			attendanceType = (AttendanceType) i.next();
			attendanceTypeTO.setId(attendanceType.getId());
			attendanceTypeTO.setAttendanceTypeName(attendanceType.getName());
			if(attendanceType.getIsDefault() == true)
				attendanceTypeTO.setDefaultValue("yes");
			else 
				attendanceTypeTO.setDefaultValue("no");
			
			List<AttendanceTypeMandatoryTO> mandatoryTOList = new ArrayList<AttendanceTypeMandatoryTO>();
			Iterator<AttendanceTypeMandatory> itr = attendanceType.getAttendanceTypeMandatories().iterator();
			AttendanceTypeMandatoryTO attendanceTypeMandatoryTO;
			AttendanceTypeMandatory attendanceTypeMandatory;
			while(itr.hasNext()) {
				attendanceTypeMandatoryTO = new AttendanceTypeMandatoryTO();
				attendanceTypeMandatory = itr.next();
				attendanceTypeMandatoryTO.setId(attendanceTypeMandatory.getId());
				attendanceTypeMandatoryTO.setName(attendanceTypeMandatory.getName());
				attendanceTypeMandatoryTO.setActive(attendanceTypeMandatory.getIsActive());
				mandatoryTOList.add(attendanceTypeMandatoryTO);
			}
			attendanceTypeTO.setAttendanceTypeMandatoryTOList(mandatoryTOList);	
			attendanceTypeTOList.add(attendanceTypeTO);
		}
		log.debug("leaving copyAttendanceTypeBosToTos");
		return attendanceTypeTOList;
	}	
}
