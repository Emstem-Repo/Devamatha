package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentGroupWise;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentGroupWiseForm;
import com.kp.cms.to.examallotment.ExamRoomAllotmentGroupWiseTo;


public class ExamRoomAllotmentGroupWiseHelper {

	
public static volatile ExamRoomAllotmentGroupWiseHelper allotmentGroupWiseHelper=null;
	
	/**
	 * @return
	 */
	public static ExamRoomAllotmentGroupWiseHelper getInstance(){
		if(allotmentGroupWiseHelper==null){
			allotmentGroupWiseHelper=new ExamRoomAllotmentGroupWiseHelper();
		}
		return allotmentGroupWiseHelper;
	}
	public ExamRoomAllotmentGroupWiseHelper() {
	}
	
	
	/**
	 * @param allotmentGroupWiseForm
	 * @return
	 */
	public List<ExamRoomAllotmentGroupWise> convertGroupWiseFormToBo(ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm) {
		List<ExamRoomAllotmentGroupWise> allotmentGroupWiseList=new ArrayList<ExamRoomAllotmentGroupWise>();
		String[] courseIds=allotmentGroupWiseForm.getSelectedCourses().split(",");
		for (int i = 0; i < courseIds.length; i++) {
			ExamRoomAllotmentGroupWise groupWise=new ExamRoomAllotmentGroupWise();
			groupWise.setCreatedBy(allotmentGroupWiseForm.getUserId());
			groupWise.setCreatedDate(new Date());
			Course course=new Course();
			course.setId(Integer.parseInt(courseIds[i]));
			groupWise.setCourse(course);
			groupWise.setIsActive(true);
			groupWise.setSchemeNo(Integer.parseInt(allotmentGroupWiseForm.getSchemeNo()));
			groupWise.setMidOrEndSem(allotmentGroupWiseForm.getMidOrEndSem());
			allotmentGroupWiseList.add(groupWise);
		}
		return allotmentGroupWiseList;
		
	}
	/**
	 * @param groupWiseList
	 * @return
	 */
	public Map<String, Map<Integer, ExamRoomAllotmentGroupWiseTo>> convertGroupWiseBoToTo(List<ExamRoomAllotmentGroupWise> groupWiseList) {
      Map<String, Map<Integer, ExamRoomAllotmentGroupWiseTo>> groupWiseMidMap=new HashMap<String, Map<Integer,ExamRoomAllotmentGroupWiseTo>>();
      if(groupWiseList!=null && !groupWiseList.isEmpty()){
    	  for (ExamRoomAllotmentGroupWise groupWise : groupWiseList) {
			if(groupWiseMidMap.containsKey(groupWise.getMidOrEndSem())){
				Map<Integer, ExamRoomAllotmentGroupWiseTo> schemeMap=groupWiseMidMap.get(groupWise.getMidOrEndSem());
				if(schemeMap.containsKey(groupWise.getSchemeNo())){
					ExamRoomAllotmentGroupWiseTo allotmentGroupWiseTo=schemeMap.get(groupWise.getSchemeNo());
					List<Integer> courseIds=allotmentGroupWiseTo.getCourseIds();
					List<String> courseNameList=allotmentGroupWiseTo.getCourseNames();
					courseNameList.add(groupWise.getCourse().getName());
					allotmentGroupWiseTo.setCourseIds(courseIds);
					courseIds.add(groupWise.getCourse().getId());
					allotmentGroupWiseTo.setCourseIds(courseIds);
					schemeMap.put(groupWise.getSchemeNo(), allotmentGroupWiseTo);
					groupWiseMidMap.put(groupWise.getMidOrEndSem(), schemeMap);
				}else{
					ExamRoomAllotmentGroupWiseTo groupWiseTo=new ExamRoomAllotmentGroupWiseTo();
					groupWiseTo.setSchemeNo(groupWise.getSchemeNo());
				   if(groupWise.getMidOrEndSem().equalsIgnoreCase("M")){
					   groupWiseTo.setMidEndSem("Mid Sem");
				   }else{
					   groupWiseTo.setMidEndSem("End Sem");
				   }
				   if(groupWise.getCourse()!=null){
					   if(groupWise.getCourse().getId()!=0){
						   List<Integer> courseIdList=new ArrayList<Integer>();
						   courseIdList.add(groupWise.getCourse().getId());
						   groupWiseTo.setCourseIds(courseIdList);
					   }
					   if(groupWise.getCourse().getName()!=null && !groupWise.getCourse().getName().isEmpty()){
						   List<String> courseNameList=new ArrayList<String>();
						   courseNameList.add(groupWise.getCourse().getName());
						   groupWiseTo.setCourseNames(courseNameList);
					   }
				   }
				   schemeMap.put(groupWise.getSchemeNo(), groupWiseTo);
				   groupWiseMidMap.put(groupWise.getMidOrEndSem(), schemeMap);
				}
			}else{
				Map<Integer, ExamRoomAllotmentGroupWiseTo> schemeGroupMap=new HashMap<Integer, ExamRoomAllotmentGroupWiseTo>();
				ExamRoomAllotmentGroupWiseTo groupWiseTo=new ExamRoomAllotmentGroupWiseTo();
				if(groupWise.getMidOrEndSem().equalsIgnoreCase("M")){
					groupWiseTo.setMidEndSem("Mid Sem");
				}else{
					groupWiseTo.setMidEndSem("End Sem");
				}
				groupWiseTo.setSchemeNo(groupWise.getSchemeNo());
				if(groupWise.getCourse()!=null){
					List<Integer> courseIdList=new ArrayList<Integer>();
					if(groupWise.getCourse().getId()!=0){
						courseIdList.add(groupWise.getCourse().getId());
					}
					if(groupWise.getCourse().getName()!=null && !groupWise.getCourse().getName().isEmpty()){
						List<String> courseNameList=new ArrayList<String>();
						courseNameList.add(groupWise.getCourse().getName());
						groupWiseTo.setCourseNames(courseNameList);
					}
					groupWiseTo.setCourseIds(courseIdList);
				}
				schemeGroupMap.put(groupWise.getSchemeNo(), groupWiseTo);
				groupWiseMidMap.put(groupWise.getMidOrEndSem(), schemeGroupMap);
			}
    		  
    		  
    		  
		}
      }
	return groupWiseMidMap;
      
	}
	public List<ExamRoomAllotmentGroupWise> updateConvertFormToBO(List<ExamRoomAllotmentGroupWise> groupWiseList,
			ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm, String mode) {
     List<ExamRoomAllotmentGroupWise> allotmentGroupWiseList=new ArrayList<ExamRoomAllotmentGroupWise>();
     if(groupWiseList!=null && !groupWiseList.isEmpty()){
    	 if(mode.equalsIgnoreCase("Update")){
    		 if(allotmentGroupWiseForm.getSelectedCourses()!=null && !allotmentGroupWiseForm.getSelectedCourses().isEmpty()){
    			 String[] strings=allotmentGroupWiseForm.getSelectedCourses().split(",");
    			 List<String> courseList=new ArrayList<String>(Arrays.asList(strings));
    			 for (ExamRoomAllotmentGroupWise allotmentGroupWise : groupWiseList) {
    				 if(courseList.contains(String.valueOf(allotmentGroupWise.getCourse().getId()))){
    					 allotmentGroupWise.setModifiedBy(allotmentGroupWiseForm.getUserId());
    					 allotmentGroupWise.setLastModifiedDate(new Date());
    					 courseList.remove(String.valueOf(allotmentGroupWise.getCourse().getId()));
    				 }else{
    					 allotmentGroupWise.setModifiedBy(allotmentGroupWiseForm.getUserId());
    					 allotmentGroupWise.setLastModifiedDate(new Date());
    					 allotmentGroupWise.setIsActive(false);
    				 }
    				 allotmentGroupWiseList.add(allotmentGroupWise);
    			 }
    			 
    			 if(courseList!=null && !courseList.isEmpty()){
    				 for (String courseId : courseList) {
    					 ExamRoomAllotmentGroupWise allotmentGroupWise=new ExamRoomAllotmentGroupWise();
    					 allotmentGroupWise.setMidOrEndSem(allotmentGroupWiseForm.getMidOrEndSem());
    					 Course course=new Course();
    					 course.setId(Integer.parseInt(courseId));
    					 allotmentGroupWise.setCourse(course);
    					 allotmentGroupWise.setSchemeNo(Integer.parseInt(allotmentGroupWiseForm.getSchemeNo()));
    					 allotmentGroupWise.setModifiedBy(allotmentGroupWiseForm.getUserId());
    					 allotmentGroupWise.setLastModifiedDate(new Date());
    					 allotmentGroupWise.setIsActive(true);
    					 allotmentGroupWiseList.add(allotmentGroupWise);  
    				 }
    				 
    			 }
    		 }else{
    			 for (ExamRoomAllotmentGroupWise examRoomAllotmentGroupWise : groupWiseList) {
    				 examRoomAllotmentGroupWise.setModifiedBy(allotmentGroupWiseForm.getUserId());
    				 examRoomAllotmentGroupWise.setLastModifiedDate(new Date());
    				 examRoomAllotmentGroupWise.setIsActive(false);
    				 allotmentGroupWiseList.add(examRoomAllotmentGroupWise);
    			 }
    		 }
    	 }else if(mode.equalsIgnoreCase("Delete")){
    		 for (ExamRoomAllotmentGroupWise examRoomAllotmentGroupWise : groupWiseList) {
				examRoomAllotmentGroupWise.setModifiedBy(allotmentGroupWiseForm.getUserId());
				examRoomAllotmentGroupWise.setLastModifiedDate(new Date());
				examRoomAllotmentGroupWise.setIsActive(false);
				allotmentGroupWiseList.add(examRoomAllotmentGroupWise);
			}
    	 }
    	 
     }
	return allotmentGroupWiseList;
		
	}
}
