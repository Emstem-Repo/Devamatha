package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.forms.admission.ApplicationNumberForm;
import com.kp.cms.to.admin.CourseApplicationNoTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.ApplicationNumberTO;
import com.kp.cms.transactions.admission.IApplicationNumberTransaction;
import com.kp.cms.transactionsimpl.admission.ApplicationNumberTransactionImpl;
/**
 * 
 * @author
 *
 */
public class ApplicationNumberHelper {
	private static final Log log = LogFactory.getLog(ApplicationNumberHelper.class);
	public static volatile ApplicationNumberHelper applicationNumberHelper = null;

	/**
	 * 
	 * @return
	 */
	public static ApplicationNumberHelper getInstance() {
		if (applicationNumberHelper == null) {
			applicationNumberHelper = new ApplicationNumberHelper();
		}
		return applicationNumberHelper;
	}

	/**
	 * copying form data to BO
	 * @param applicationNumberForm
	 * @return
	 * @throws Exception
	 */
	
	public ApplicationNumber populateApplicationNumber(ApplicationNumberForm applicationNumberForm,String mode) throws Exception {
		log.debug("inside populateApplicationNumber in Helper");
		ApplicationNumber applicationNumber = new ApplicationNumber();
		String selectedCourse[] = applicationNumberForm.getSelectedCourse();
		
		applicationNumber.setOfflineApplnNoFrom(applicationNumberForm.getOfflineAppNoFrom());
		applicationNumber.setOfflineApplnNoTo(applicationNumberForm.getOfflineAppNoTill());
		applicationNumber.setOnlineApplnNoFrom(applicationNumberForm.getOnlineAppNoFrom());
		applicationNumber.setOnlineApplnNoTo(applicationNumberForm.getOnlineAppNoTill());
		applicationNumber.setYear(Integer.parseInt(applicationNumberForm.getYear()));
		applicationNumber.setIsActive(true);
		applicationNumber.setCreatedDate(new Date());
		applicationNumber.setLastModifiedDate(new Date());
		applicationNumber.setCreatedBy(applicationNumberForm.getUserId());
		applicationNumber.setModifiedBy(applicationNumberForm.getUserId());
		if(mode.equalsIgnoreCase("Add")){
			applicationNumber.setCurrentOnlineApplicationNo(applicationNumberForm.getOnlineAppNoFrom());
			applicationNumber.setCurrentOfflineApplicationNo(applicationNumberForm.getOfflineAppNoFrom());
		}
		Set<CourseApplicationNumber> courseAppNoSet = new HashSet<CourseApplicationNumber>();

		
		for (int x = 0; x < selectedCourse.length; x++){
			Course course = new Course();
			CourseApplicationNumber courseApplicationNo = new CourseApplicationNumber();
			if(selectedCourse[x]!= null && !selectedCourse[x].isEmpty()){
				course.setId(Integer.parseInt(selectedCourse[x]));
			}
			
			courseApplicationNo.setCourse(course);
			courseApplicationNo.setIsActive(true);
			courseAppNoSet.add(courseApplicationNo);
		}
		applicationNumber.setCourseApplicationNumbers(courseAppNoSet);
		log.debug("leaving populateApplicationNumber in Helper");
		return applicationNumber;
	}

	/**
	 * copying Bo to TO
	 * @param applicationNoList
	 * @param applicationNumberForm
	 * @param id
	 * @return
	 */
	public List<ApplicationNumberTO> copyApplicationNoBosToTos(List<ApplicationNumber> applicationNoList, ApplicationNumberForm applicationNumberForm, int id) {
		log.debug("inside copyApplicationNoBosToTos in Helper");
		List<ApplicationNumberTO> applicationNoTOList = new ArrayList<ApplicationNumberTO>();
		Iterator<ApplicationNumber> i = applicationNoList.iterator();
		ApplicationNumber applicationNumber;
		ApplicationNumberTO applicationNumberTO;
		CourseTO courseTo;
		ProgramTypeTO programTypeTo;
		ProgramTO programTo;
		ProgramTO selectedPgmTo;
		
		while (i.hasNext()) {
			applicationNumberTO = new ApplicationNumberTO();

			applicationNumber = i.next();

			applicationNumberTO.setId(applicationNumber.getId());
			applicationNumberTO.setOnlineAppNoFrom(applicationNumber.getOnlineApplnNoFrom());
			applicationNumberTO.setOnlineAppNoTill(applicationNumber.getOnlineApplnNoTo());
			applicationNumberTO.setOfflineAppNoFrom(applicationNumber.getOfflineApplnNoFrom());
			applicationNumberTO.setOfflineAppNoTill(applicationNumber.getOfflineApplnNoTo());
			applicationNumberTO.setYear(applicationNumber.getYear());
			applicationNumberTO.setEndYear(applicationNumber.getYear() + 1);
			if(applicationNumber.getCurrentOnlineApplicationNo()!=null){
				applicationNumberTO.setCurrentOnlineApplnNo(applicationNumber.getCurrentOnlineApplicationNo());
			}
			if(applicationNumber.getCurrentOfflineApplicationNo()!=null){
				applicationNumberTO.setCurrentOfflineApplnNo(applicationNumber.getCurrentOfflineApplicationNo());
			}
			Set<CourseApplicationNumber> courseAppNoSet = applicationNumber.getCourseApplicationNumbers();
			Iterator<CourseApplicationNumber> itr = courseAppNoSet.iterator();
			List<CourseApplicationNoTO> tempList = new ArrayList<CourseApplicationNoTO>();
			int count = 0; 
			String courseApplcationNoIdNoArray[] = new String[applicationNumber.getCourseApplicationNumbers().size()];
			while (itr.hasNext()){
				CourseApplicationNoTO courseApplicationNoTO = new CourseApplicationNoTO();
				programTo = new ProgramTO();
				selectedPgmTo = new ProgramTO();
				programTypeTo = new ProgramTypeTO();
				courseTo = new CourseTO();
				CourseApplicationNumber courseApplicationNumber = itr.next();
				programTypeTo.setProgramTypeName(courseApplicationNumber.getCourse().getName());
				programTypeTo.setProgramTypeId(courseApplicationNumber.getCourse().getProgram().getProgramType().getId());
				programTo.setId(courseApplicationNumber.getCourse().getProgram().getId());
				programTo.setName(courseApplicationNumber.getCourse().getProgram().getName());
				programTo.setProgramTypeTo(programTypeTo);
				courseTo.setName(courseApplicationNumber.getCourse().getName());
				courseTo.setId(courseApplicationNumber.getCourse().getId());
				courseTo.setProgramTo(programTo);
				courseApplicationNoTO.setId(courseApplicationNumber.getId());
				courseApplicationNoTO.setCourseTO(courseTo);
				courseApplicationNoTO.setApplNoId(courseApplicationNumber.getApplicationNumber().getId());
				selectedPgmTo.setId(courseApplicationNumber.getCourse().getProgram().getId());
				selectedPgmTo.setName(courseApplicationNumber.getCourse().getProgram().getName());
				courseApplicationNoTO.setSelectedProgram(selectedPgmTo);
				courseApplicationNoTO.setProgramTypeId(courseApplicationNumber.getCourse().getProgram().getProgramType().getId());
				if(courseApplicationNumber.getIsActive()){ 
					tempList.add(courseApplicationNoTO);
				}
				courseApplcationNoIdNoArray[count] = Integer.toString(courseApplicationNumber.getId());
				count = count + 1;
			}
			if(id != 0){
				applicationNumberForm.setSelectedCourseIdNos(courseApplcationNoIdNoArray);
			}
			applicationNumberTO.setCourseApplicationNoTO(tempList);
			applicationNoTOList.add(applicationNumberTO);

		}
		log.debug("leaving copyApplicationNoBosToTos in Helper");
		return applicationNoTOList;
	}
	
	/**
	 * creating Bo object from form
	 * @param applicationNumberForm
	 * @return
	 * @throws Exception
	 */

	public ApplicationNumber populateApplicationNumberinUpdate(ApplicationNumberForm applicationNumberForm,String mode) throws Exception {
		log.debug("inside populateApplicationNumberinUpdate in Helper");
		IApplicationNumberTransaction iappApplicationNumberTransaction = ApplicationNumberTransactionImpl.getInstance();
		Course course;
		CourseApplicationNumber courseApplicationNo;
		ApplicationNumber applicationNumber = new ApplicationNumber();
		String selectedCourse[] = applicationNumberForm.getSelectedCourse();
		String selectedCourseAppNoIds[] = applicationNumberForm.getSelectedCourseIdNos();
		List<Integer> SelectedCourseList = new ArrayList<Integer>();
		
		
		if(selectedCourse != null) {
			for(int i=0; i<selectedCourse.length; i++){
				SelectedCourseList.add(Integer.parseInt(selectedCourse[i]));
			}
		}

		applicationNumber.setOfflineApplnNoFrom(applicationNumberForm.getOfflineAppNoFrom());
		applicationNumber.setOfflineApplnNoTo(applicationNumberForm.getOfflineAppNoTill());
		applicationNumber.setOnlineApplnNoFrom(applicationNumberForm.getOnlineAppNoFrom());
		applicationNumber.setOnlineApplnNoTo(applicationNumberForm.getOnlineAppNoTill());
		applicationNumber.setYear(Integer.parseInt(applicationNumberForm.getYear()));
		applicationNumber.setIsActive(true);
		applicationNumber.setCreatedDate(new Date());
		applicationNumber.setLastModifiedDate(new Date());
		applicationNumber.setCreatedBy(applicationNumberForm.getUserId());
		applicationNumber.setModifiedBy(applicationNumberForm.getUserId());
		if(mode.equalsIgnoreCase("Add")){
		applicationNumber.setCurrentOnlineApplicationNo(applicationNumberForm.getOnlineAppNoFrom());
		applicationNumber.setCurrentOfflineApplicationNo(applicationNumberForm.getOfflineAppNoFrom());
		}else{
			if(applicationNumberForm.getCurrentOnlineApplnNo()!=null)
			applicationNumber.setCurrentOnlineApplicationNo(applicationNumberForm.getCurrentOnlineApplnNo());
			if(applicationNumberForm.getCurrentOfflineApplnNo()!=null)
				applicationNumber.setCurrentOfflineApplicationNo(applicationNumberForm.getCurrentOfflineApplnNo());
		}
		if(applicationNumberForm.getId()!= 0){
			applicationNumber.setId(applicationNumberForm.getId());
		}
		CourseApplicationNoTO courseApplicationNoTO;
		Set<CourseApplicationNumber> courseAppNoSet = new HashSet<CourseApplicationNumber>();

		List<CourseApplicationNoTO> tempList = new ArrayList<CourseApplicationNoTO>();
		
		int courseId  = 0;
		int count = 0;
		Iterator<CourseApplicationNoTO> itr = applicationNumberForm.getCourseApplicationNumberList().iterator();
		while (itr.hasNext()){
			courseApplicationNoTO = itr.next();
			courseId = courseApplicationNoTO.getCourseTO().getId();
			if(!SelectedCourseList.contains(courseId)){
				tempList.add(courseApplicationNoTO);
			}
			count = count + 1;
		}
		
		String deletedCourseAppNoIds[] = new String[tempList.size()];
		count = 0;
		Iterator<CourseApplicationNoTO> iter =  tempList.iterator();
		while(iter.hasNext()){
			courseApplicationNo = new CourseApplicationNumber();
			ApplicationNumber delApplicationNumber = new ApplicationNumber();
			course = new Course();
			courseApplicationNoTO = iter.next();
			courseApplicationNo.setId(courseApplicationNoTO.getId());
			course.setId(courseApplicationNoTO.getCourseTO().getId());
			courseApplicationNo.setCourse(course);
			delApplicationNumber.setId(courseApplicationNoTO.getApplNoId());
			courseApplicationNo.setApplicationNumber(delApplicationNumber);
			courseApplicationNo.setIsActive(false);
			courseAppNoSet.add(courseApplicationNo);
			deletedCourseAppNoIds[count] = Integer.toString(courseApplicationNo.getId());
			count = count + 1;
		}
		
		count = 0;
		String tempOldAppNoSelectedArray[] =  new String[selectedCourseAppNoIds.length - deletedCourseAppNoIds.length];
		Boolean found = false;
		for (int n = 0; n < selectedCourseAppNoIds.length; n++){
			found = false;
			for (int m = 0; m < deletedCourseAppNoIds.length; m++){
				if(Integer.parseInt(deletedCourseAppNoIds[m]) == Integer.parseInt(selectedCourseAppNoIds[n])){
					found = true;
				}
			}
			if(!found){
				tempOldAppNoSelectedArray[count] = selectedCourseAppNoIds[n];
				count = count + 1;
			}
		}
		
		
		ApplicationNumber tempApplicationNumber = new ApplicationNumber();
		int oldAssignedNos = 0;
		for (int x = 0; x < selectedCourse.length; x++){
			course = new Course();
			courseApplicationNo = new CourseApplicationNumber();
			if(selectedCourse[x]!= null && !selectedCourse[x].isEmpty()){
				course.setId(Integer.parseInt(selectedCourse[x]));
				courseId = Integer.parseInt(selectedCourse[x]);
			}
			
			tempApplicationNumber.setId(applicationNumberForm.getId());
			courseApplicationNo.setApplicationNumber(tempApplicationNumber);
			if(x<tempOldAppNoSelectedArray.length)	{
				if(tempOldAppNoSelectedArray[x]!=null){
					courseApplicationNo.setId(Integer.parseInt(tempOldAppNoSelectedArray[x]));
					oldAssignedNos++; 
				}
			}
			
			courseApplicationNo.setCourse(course);
			courseApplicationNo.setIsActive(true);
			courseAppNoSet.add(courseApplicationNo);
		}

		for (int p = oldAssignedNos; p < tempOldAppNoSelectedArray.length; p++){
			course = new Course();
			courseApplicationNo = new CourseApplicationNumber();
			courseApplicationNo.setId(Integer.parseInt(tempOldAppNoSelectedArray[p]));
			CourseApplicationNumber tempCourseApplNo = iappApplicationNumberTransaction.getCourseApplNo(courseApplicationNo.getId());
			course.setId(tempCourseApplNo.getCourse().getId());
			courseApplicationNo.setCourse(course);
			courseApplicationNo.setIsActive(false);
			courseAppNoSet.add(courseApplicationNo);
		}
		
		applicationNumber.setCourseApplicationNumbers(courseAppNoSet);
		log.debug("leaving populateApplicationNumberinUpdate in Helper");
		return applicationNumber;
	}
	/**
	 * method for duplication checking
	 * @param applicationNumberForm
	 * @param origYearNotChanged
	 * @return
	 */
	public List<Integer> assignDuplicationCheckItemsToList(ApplicationNumberForm applicationNumberForm, Boolean origYearNotChanged ){
		log.debug("inside assignDuplicationCheckItemsToList in Helper");
		List<CourseApplicationNoTO> courseApplNoList = applicationNumberForm.getCourseApplicationNumberList();
		Iterator<CourseApplicationNoTO> it = courseApplNoList.iterator();
		List<Integer> origIdArray = new ArrayList<Integer>();
		List<Integer> duplArray = new ArrayList<Integer>();
		String[] selectedCourse = applicationNumberForm.getSelectedCourse();
		while (it.hasNext()){
			CourseApplicationNoTO courseApplicationNoTO = it.next();
			origIdArray.add(courseApplicationNoTO.getCourseTO().getId());
		}

		for (int x = 0; x < selectedCourse.length; x++) {
			if(origYearNotChanged){
				if(!origIdArray.contains(Integer.parseInt(selectedCourse[x]))){
					duplArray.add(Integer.parseInt(selectedCourse[x]));
				}
			}
			else
			{
				duplArray.add(Integer.parseInt(selectedCourse[x]));
			}
		}
		log.debug("leaving assignDuplicationCheckItemsToList in Helper");
		return duplArray;
	}
	
}
