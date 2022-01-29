package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlGroup;
import com.kp.cms.bo.admin.HlGroupStudent;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.forms.hostel.HostelGroupForm;
import com.kp.cms.handlers.hostel.HostelGroupHandler;
import com.kp.cms.to.hostel.ApplicationFormTO;
import com.kp.cms.to.hostel.HostelGroupStudentTO;
import com.kp.cms.to.hostel.HostelGroupTO;
import com.kp.cms.to.hostel.HostelTO;


public class HostelGroupHelper {
	public static Log log = LogFactory.getLog(HostelGroupHelper.class);
	public static volatile HostelGroupHelper hostelGroupHelper;
	
	public static HostelGroupHelper getInstance(){
		if(hostelGroupHelper == null){
			hostelGroupHelper = new HostelGroupHelper();
			return hostelGroupHelper;
		}
		return hostelGroupHelper;
	}
	
	/**
	 * copying bo's to to's
	 * @param hostelTOList
	 * @return
	 */
	
	public List<ApplicationFormTO> copyHostelGroupBosToTos(List<HlApplicationForm> studList) {
		log.debug("inside copyHostelGroupBosToTos");
		List<ApplicationFormTO> studTOList = new ArrayList<ApplicationFormTO>();
		Iterator<HlApplicationForm> iterator = studList.iterator();
		HlApplicationForm hlApplicationForm;
		ApplicationFormTO appFormTO;
		StringBuffer name = null;
		while (iterator.hasNext()) {
			appFormTO = new ApplicationFormTO();
			hlApplicationForm = (HlApplicationForm) iterator.next();
			if(hlApplicationForm.getIsStaff())
			{
				name = new StringBuffer();
				name.append(hlApplicationForm.getEmployee().getFirstName());
			/*	if(hlApplicationForm.getEmployee().getMiddleName()!= null && !hlApplicationForm.getEmployee().getMiddleName().trim().isEmpty()){
					name.append(" " + hlApplicationForm.getEmployee().getMiddleName());
				}
				if(hlApplicationForm.getEmployee().getLastName()!= null && !hlApplicationForm.getEmployee().getLastName().trim().isEmpty()){
					name.append(" " + hlApplicationForm.getEmployee().getLastName());
				}*/
			}
			else
			{	
				name = new StringBuffer();
				name.append(hlApplicationForm.getAdmAppln().getPersonalData().getFirstName());
				if(hlApplicationForm.getAdmAppln().getPersonalData().getMiddleName()!= null && !hlApplicationForm.getAdmAppln().getPersonalData().getMiddleName().trim().isEmpty()){
					name.append(" " + hlApplicationForm.getAdmAppln().getPersonalData().getMiddleName());
				}
				if(hlApplicationForm.getAdmAppln().getPersonalData().getLastName()!= null && !hlApplicationForm.getAdmAppln().getPersonalData().getLastName().trim().isEmpty()){
					name.append(" " + hlApplicationForm.getAdmAppln().getPersonalData().getLastName());
				}
				
			}
			appFormTO.setName(name.toString());
			appFormTO.setId(hlApplicationForm.getId());
			appFormTO.setSelected(false);
			appFormTO.setDummySelected(false);
			studTOList.add(appFormTO);
		}
		log.debug("leaving copyHostelGroupBosToTos");
		return studTOList;
	}
	
	/**
	 * copying form values to bo for save
	 * @param hlForm
	 * @return
	 * @throws Exception
	 */
	public HlGroup copyDataFromFormToBO(HostelGroupForm hlForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		HlGroup hlGroup = new HlGroup();
		HlHostel hostel = new HlHostel();
		if(hlForm.getId() != 0) {
			hlGroup.setId(hlForm.getId());
		}
		if(hlForm.getHostelId()!= null && !hlForm.getHostelId().trim().isEmpty()){
			hostel.setId(Integer.parseInt(hlForm.getHostelId()));
		}
		hlGroup.setHlHostel(hostel);
		hlGroup.setFloorNo(hlForm.getFloorNo());
		hlGroup.setName(hlForm.getGroupName());
		Iterator<ApplicationFormTO> studItr = hlForm.getStudList().iterator();
		ApplicationFormTO appFormTO;
		HlGroupStudent hlGroupStudent;
		HlApplicationForm hlApplicationForm;
		HashSet<HlGroupStudent> hlGroupStudentSet = new HashSet<HlGroupStudent>();
		while (studItr.hasNext()){
			hlApplicationForm = new HlApplicationForm();
			hlGroupStudent = new HlGroupStudent();
			appFormTO = studItr.next();
			if(!appFormTO.isSelected()){
				continue;
			}
			hlApplicationForm.setId(appFormTO.getId());
			hlGroupStudent.setHlApplicationForm(hlApplicationForm);
			hlGroupStudent.setIsActive(true);
			hlGroupStudent.setCreatedBy(hlForm.getUserId());
			hlGroupStudent.setModifiedBy(hlForm.getUserId());
			hlGroupStudent.setCreatedDate(new Date());
			hlGroupStudentSet.add(hlGroupStudent);
			hlGroup.setLastModifiedDate(new Date());
		}
		hlGroup.setHlGroupStudents(hlGroupStudentSet);
		hlGroup.setIsActive(true);
		hlGroup.setCreatedBy(hlForm.getUserId());
		hlGroup.setModifiedBy(hlForm.getUserId());
		hlGroup.setCreatedDate(new Date());
		hlGroup.setLastModifiedDate(new Date());
		log.debug("leaving copyDataFromFormToBO");
		return hlGroup;
		}

	/**
	 * 
	 * @param hlGroupList
	 * @return
	 */
	public List<HostelGroupTO> copyHlGroupBosToTos(List<HlGroup> hlGroupList) {
		log.info("Start of copyHostelGroupBosToTos of Helper");
		List<HostelGroupTO> hostelGroupToList = new ArrayList<HostelGroupTO>();
		Iterator<HlGroup> iterator = hlGroupList.iterator();
		HlGroup hlGroup;
		HostelGroupTO hostelGroupTO;
		HostelTO hostelTO;
		StringBuffer name = new StringBuffer();
		HlGroupStudent hlGroupStudent;
		while (iterator.hasNext()) {
			hostelGroupTO = new HostelGroupTO();
			hostelTO = new HostelTO();
			hlGroup = (HlGroup) iterator.next();
			hostelGroupTO.setId(hlGroup.getId());
			hostelGroupTO.setName(hlGroup.getName());
			hostelTO.setId(hlGroup.getId());
			hostelTO.setName(hlGroup.getHlHostel().getName());
			hostelGroupTO.setHostelTO(hostelTO);
			hostelGroupTO.setFloorNo(hlGroup.getFloorNo());
			
			
			Set<HlGroupStudent> hlGroupSet =hlGroup.getHlGroupStudents();
			Iterator<HlGroupStudent> it = hlGroupSet.iterator();
			List<HostelGroupStudentTO> hostelGroupList = new ArrayList<HostelGroupStudentTO>();
			int size = 0;
			if(hlGroupSet!= null){
				size = hlGroupSet.size();
			}
			int count = 1;
			String appendedString = "";
			String tempName = "";
			
			while (it.hasNext()) {
				HostelGroupStudentTO hostelGroupStudentTO = new HostelGroupStudentTO();
				hlGroupStudent = it.next();
				if(!hlGroupStudent.getIsActive()){
					size--;
					continue;
				}
				hostelGroupStudentTO.setId(hlGroupStudent.getId());
				hostelGroupStudentTO.setAppFormId(hlGroupStudent.getHlApplicationForm().getId());
				hostelGroupStudentTO.setHlGroupId(hlGroupStudent.getHlGroup().getId());
				name = new StringBuffer();
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
					if(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getFirstName()!= null &&
							!hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getFirstName().isEmpty()){
						name.append(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getFirstName());
					}
					if(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getMiddleName()!= null &&
							!hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
						if(name.toString()!= null){
							name.append(" ");
						}
						name.append(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getMiddleName());
					}
					if(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getLastName()!= null &&
							!hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getLastName().isEmpty()){
						name.append(" " + hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getLastName());
					}
				}	

				appendedString = name.toString() + ",";
				if(size == count){
					if (appendedString.endsWith(",") == true) {
						tempName = StringUtils.chop(appendedString);
					}
				}
				else
				{
					tempName = appendedString;
				}
				count++;
				hostelGroupStudentTO.setName(tempName);
				hostelGroupList.add(hostelGroupStudentTO);
			}
			hostelGroupTO.setHlGroupStudentList(hostelGroupList);
			hostelGroupToList.add(hostelGroupTO);
		}
		log.info("End of  copyHostelGroupBosToTos of Helper");
		return hostelGroupToList;
	}
	/**
	 * @throws Exception 
	*/
	public void setRequiredDatatoForm(HlGroup hlGroup, HostelGroupForm hForm) throws Exception {
		hForm.setGroupName(hlGroup.getName());
		if(hlGroup.getFloorNo()!= null){
			hForm.setFloorNo(hlGroup.getFloorNo());		
		}
		if(hlGroup.getHlHostel()!= null){
			hForm.setHostelId(Integer.toString(hlGroup.getHlHostel().getId()));
		}
		if(hlGroup.getHlGroupStudents()!= null && !hlGroup.getHlGroupStudents().isEmpty()){
			Set<HlGroupStudent> hlGroupStudentBOSet = hlGroup.getHlGroupStudents();
			setStudentsToForm(hlGroupStudentBOSet, hForm);
		
		}
	}
	
	/**
	 * setting students to form
	 */
	public void setStudentsToForm(Set<HlGroupStudent> hlGroupStudentBOSet,HostelGroupForm hForm)throws Exception{
		log.info("Entering into setStudentsToForm");
		List<ApplicationFormTO> studList = HostelGroupHandler.getInstance().getStudentDetails(hForm);
		Map<Integer, String> studentMap = new HashMap<Integer, String>();
		Map<Integer, HostelGroupStudentTO>  hostelGroupStudentMap = new HashMap<Integer, HostelGroupStudentTO>();
	
		if(hlGroupStudentBOSet!=null){
			Iterator<HlGroupStudent> it = hlGroupStudentBOSet.iterator();
			while (it.hasNext()) {
				HostelGroupStudentTO groupStudentTO = new HostelGroupStudentTO(); 
				HlGroupStudent hlGroupStudent = it.next();
				if(!hlGroupStudent.getIsActive()){
					continue;
				}
				int appFormId = 0;
				StringBuffer name = new StringBuffer();
				appFormId = hlGroupStudent.getHlApplicationForm().getId();
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
					if(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getFirstName()!= null &&
							!hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getFirstName().isEmpty()){
						name.append(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getFirstName());
					}
					if(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getMiddleName()!= null &&
							!hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
						if(name.toString()!= null){
							name.append(" ");
						}
						name.append(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getMiddleName());
					}
					if(hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getLastName()!= null &&
							!hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getLastName().isEmpty()){
						name.append(" " + hlGroupStudent.getHlApplicationForm().getAdmAppln().getPersonalData().getLastName());
					}	
				}		
				studentMap.put(appFormId, name.toString());
				groupStudentTO.setId(hlGroupStudent.getId());
				groupStudentTO.setHlGroupId(hlGroupStudent.getHlGroup().getId());
				hostelGroupStudentMap.put(appFormId, groupStudentTO);
			}
		}
		hForm.setHostelGroupStudentMap(hostelGroupStudentMap);
		if(studList!=null && !studList.isEmpty()){
			Iterator<ApplicationFormTO>itr = studList.iterator();
			while (itr.hasNext()) {
				ApplicationFormTO applicationFormTO = itr.next();
				if(studentMap!=null && studentMap.containsKey(applicationFormTO.getId())){
					applicationFormTO.setName(String.valueOf(studentMap.get(applicationFormTO.getId())));
					applicationFormTO.setDummySelected(true);
				}
			}
		}
		log.info("Leaving setStudentsToForm");
		hForm.setStudList(studList);
	}
		
	/**
	 * 
	 * @param hlForm
	 * @return
	 * @throws Exception
	 */
	public HlGroup copyDataFromFormToBOForUpdate(HostelGroupForm hlForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		HlGroup hlGroup = new HlGroup();
		HlHostel hostel = new HlHostel();
		if(hlForm.getId() != 0) {
			hlGroup.setId(hlForm.getId());
		}
		if(hlForm.getHostelId()!= null && !hlForm.getHostelId().trim().isEmpty()){
			hostel.setId(Integer.parseInt(hlForm.getHostelId()));
		}
		hlGroup.setHlHostel(hostel);
		hlGroup.setFloorNo(hlForm.getFloorNo());
		hlGroup.setName(hlForm.getGroupName());
		HlGroupStudent hlGroupStudent;
		HlApplicationForm hlApplicationForm;
		Map<Integer, HostelGroupStudentTO> hsotelStudentMap = hlForm.getHostelGroupStudentMap();
		
		HlGroup hlGroup1;
		Set<HlGroupStudent> hlGroupStudentSet = new HashSet<HlGroupStudent>();
		
		if(hlForm.getStudList()!=null && !hlForm.getStudList().isEmpty()){
			Iterator<ApplicationFormTO> iterator = hlForm.getStudList().iterator();
			while (iterator.hasNext()) {
				ApplicationFormTO applicationFormTO = (ApplicationFormTO) iterator.next();					
				if(applicationFormTO.isSelected() &&hsotelStudentMap!=null &&
						hsotelStudentMap.containsKey(applicationFormTO.getId())){			
						//Gets the old TO
						HostelGroupStudentTO hosStudentTO = hsotelStudentMap.get(applicationFormTO.getId());						
						hlGroupStudent = new HlGroupStudent();						
						hlGroupStudent.setId(hosStudentTO.getId());	
						
						if(hosStudentTO!=null){
							hlGroup1 = new HlGroup();
							hlGroup1.setId(hosStudentTO.getHlGroupId());
							hlGroupStudent.setHlGroup(hlGroup1);
						}							
						hlApplicationForm = new HlApplicationForm();
						hlApplicationForm.setId(applicationFormTO.getId());
						hlGroupStudent.setHlApplicationForm(hlApplicationForm);
						hlGroupStudent.setModifiedBy(hlForm.getUserId());
						hlGroupStudent.setLastModifiedDate(new Date());
						hlGroupStudent.setCreatedBy(hlForm.getUserId());
						hlGroupStudent.setCreatedDate(new Date());
						hlGroupStudent.setIsActive(true);							
						hlGroupStudentSet.add(hlGroupStudent);
					}				
				else if(applicationFormTO.isSelected() && hsotelStudentMap!=null &&
						!hsotelStudentMap.containsKey(applicationFormTO.getId())){	
						hlGroupStudent = new HlGroupStudent();
						hlApplicationForm = new HlApplicationForm();
						hlApplicationForm.setId(applicationFormTO.getId());
						hlGroupStudent.setHlApplicationForm(hlApplicationForm);
						hlGroupStudent.setModifiedBy(hlForm.getUserId());
						hlGroupStudent.setLastModifiedDate(new Date());
						hlGroupStudent.setCreatedBy(hlForm.getUserId());
						hlGroupStudent.setCreatedDate(new Date());
						hlGroupStudent.setIsActive(true);
						
						hlGroupStudentSet.add(hlGroupStudent);
					}				
				//Works when previously present and now not at all selected. Make the records inactive
				else if(!applicationFormTO.isSelected() && hsotelStudentMap!=null &&
						hsotelStudentMap.containsKey(applicationFormTO.getId())){						
						HostelGroupStudentTO hosStudentTO = hsotelStudentMap.get(applicationFormTO.getId());						
						hlGroupStudent = new HlGroupStudent();						
						hlGroupStudent.setId(hosStudentTO.getId());						
						if(hosStudentTO!=null){
							hlGroup1 = new HlGroup();
							hlGroup1.setId(hosStudentTO.getHlGroupId());
							hlGroupStudent.setHlGroup(hlGroup1);
						}		
						hlApplicationForm = new HlApplicationForm();
						hlApplicationForm.setId(applicationFormTO.getId());
						hlGroupStudent.setHlApplicationForm(hlApplicationForm);
						
						hlGroupStudent.setModifiedBy(hlForm.getUserId());
						hlGroupStudent.setLastModifiedDate(new Date());
						hlGroupStudent.setCreatedBy(hlForm.getUserId());
						hlGroupStudent.setCreatedDate(new Date());
						hlGroupStudent.setIsActive(false);							
						hlGroupStudentSet.add(hlGroupStudent);
				}
				//Works when previously no students added. But adding in update mode. (new records)
				else if(hsotelStudentMap == null && applicationFormTO.isSelected()){
					hlGroupStudent = new HlGroupStudent();
					hlApplicationForm = new HlApplicationForm();
					hlApplicationForm.setId(applicationFormTO.getId());
					hlGroupStudent.setHlApplicationForm(hlApplicationForm);
					hlGroupStudent.setModifiedBy(hlForm.getUserId());
					hlGroupStudent.setLastModifiedDate(new Date());
					hlGroupStudent.setCreatedBy(hlForm.getUserId());
					hlGroupStudent.setCreatedDate(new Date());
					hlGroupStudent.setIsActive(true);						
					hlGroupStudentSet.add(hlGroupStudent);
				}					
				}			
			}		
		
		
		
		hlGroup.setHlGroupStudents(hlGroupStudentSet);
		hlGroup.setIsActive(true);
		hlGroup.setCreatedBy(hlForm.getUserId());
		hlGroup.setModifiedBy(hlForm.getUserId());
		hlGroup.setCreatedDate(new Date());
		hlGroup.setLastModifiedDate(new Date());
		log.debug("leaving copyDataFromFormToBO");
		return hlGroup;
		}

	/**
	 * @param hForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQuery(HostelGroupForm hForm) throws Exception {
		String query="select h.hlApplicationForm from HlRoomTransaction h where h.hlApplicationForm.hlHostelByHlApprovedHostelId.id="+hForm.getHostelId()+" " +
				"and h.hlApplicationForm.isActive=1 " +
				"and h.hlApplicationForm.hlStatus.id=2" ;
		if(hForm.getFloorNo()!=null && !hForm.getFloorNo().isEmpty()){
			query=query+" and h.hlRoom.floorNo="+hForm.getFloorNo();
		}
		query=query+" group by h.hlApplicationForm.id";
		return query;
	}

	public String getSearchQueryForHostelGroup(HostelGroupForm hForm) throws Exception {
		String query="from HlGroup h where h.isActive = 1 and h.hlHostel.id="+hForm.getHostelId();
		return query;
	}

	
}
