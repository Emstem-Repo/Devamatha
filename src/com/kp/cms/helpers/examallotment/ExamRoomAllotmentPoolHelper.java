package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentPool;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentSettingsPoolWise;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm;
import com.kp.cms.to.examallotment.ExamRoomAllotSettingPoolWiseTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentPoolTo;

public class ExamRoomAllotmentPoolHelper {

	
	private static volatile ExamRoomAllotmentPoolHelper examRoomAllotmentPoolHelper=null;
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentPoolHelper.class);
	 
	/**
	 * @return
	 */
	public static ExamRoomAllotmentPoolHelper getInstance(){
		if(examRoomAllotmentPoolHelper == null){
			examRoomAllotmentPoolHelper=new ExamRoomAllotmentPoolHelper();
		}
		return examRoomAllotmentPoolHelper;
	}
	
	public ExamRoomAllotmentPoolHelper() {

	}

	/**
	 * @param allotmentPoolForm
	 * @param mode
	 * @return
	 */
	public ExamRoomAllotmentPool convertFormToBO(ExamRoomAllotmentPoolForm allotmentPoolForm,String mode) {
		ExamRoomAllotmentPool examRoomAllotmentPool=new ExamRoomAllotmentPool();
		examRoomAllotmentPool.setPoolName(allotmentPoolForm.getPoolName());
		log.info("enterd into convertFormToBO ExamRoomAllotmentPoolHelper");
		examRoomAllotmentPool.setIsActive(true);
		if(mode.equalsIgnoreCase("Add")){
			examRoomAllotmentPool.setCreatedBy(allotmentPoolForm.getUserId());
			examRoomAllotmentPool.setCreatedDate(new Date());
		}else{
			examRoomAllotmentPool.setModifiedBy(allotmentPoolForm.getUserId());
			examRoomAllotmentPool.setLastModifiedDate(new Date());
			examRoomAllotmentPool.setId(allotmentPoolForm.getId());
		}
		return examRoomAllotmentPool;
		
	}

	/**
	 * @param roomAllotmentPoolsList
	 * @return
	 */
	public List<ExamRoomAllotmentPoolTo> convertBOtoTO(List<ExamRoomAllotmentPool> roomAllotmentPoolsList) {
		List<ExamRoomAllotmentPoolTo> roomAllotmentPoolToList=new ArrayList<ExamRoomAllotmentPoolTo>();
		log.info("enterd into convertBOtoTO ExamRoomAllotmentPoolHelper");
		if(roomAllotmentPoolsList!=null && !roomAllotmentPoolsList.isEmpty()){
		   for (ExamRoomAllotmentPool examRoomAllotmentPool : roomAllotmentPoolsList) {
			   ExamRoomAllotmentPoolTo examRoomAllotmentPoolTo=new ExamRoomAllotmentPoolTo();
			   examRoomAllotmentPoolTo.setId(examRoomAllotmentPool.getId());
			   if(examRoomAllotmentPool.getPoolName()!=null && !examRoomAllotmentPool.getPoolName().isEmpty()){
				   examRoomAllotmentPoolTo.setPoolName(examRoomAllotmentPool.getPoolName());
			   }
			   roomAllotmentPoolToList.add(examRoomAllotmentPoolTo);
		}	
		}
		return roomAllotmentPoolToList;
	}

	/**
	 * @param allotmentPoolForm
	 * @param allotmentPool
	 */
	public void setBOtoForm(ExamRoomAllotmentPoolForm allotmentPoolForm,ExamRoomAllotmentPool allotmentPool) {
		  if(allotmentPool!=null){
			  log.info("enterd into setBOtoForm ExamRoomAllotmentPoolHelper");
			allotmentPoolForm.setPoolName(allotmentPool.getPoolName());
			//allotmentPoolForm.setOrigPoolName(allotmentPool.getPoolName());
		  }
	}

	/**
	 * @param examRoomAllotmentPools
	 */
	public Map<Integer, String> convertAllotRoomPoolListToMap(List<ExamRoomAllotmentPool> examRoomAllotmentPools) {
		Map<Integer, String> examAllotmentPoolMap=new HashMap<Integer, String>();
           		if(examRoomAllotmentPools!=null && !examRoomAllotmentPools.isEmpty()){
           		 for (ExamRoomAllotmentPool examRoomAllotmentPool : examRoomAllotmentPools) {
           			examAllotmentPoolMap.put(examRoomAllotmentPool.getId(), examRoomAllotmentPool.getPoolName());
				}
           		}
				return examAllotmentPoolMap;
	}

	/**
	 * @param courseMap
	 * @param courseIdOfSpecialization
	 * @param courseIdOfPoolWise
	 * @return
	 */
	public Map<Integer, String> getFinalCourseMap(Map<Integer, String> courseMap,List<Integer> courseIdOfSpecialization,List<Integer> courseIdOfPoolWise) {
		
		if(courseMap!=null && !courseMap.isEmpty()){
			if(courseIdOfSpecialization!=null && !courseIdOfSpecialization.isEmpty()){
			for (Integer integer : courseIdOfSpecialization) {
				if(courseMap.containsKey(integer)){
					courseMap.remove(integer);
				}
			}
			}if(courseIdOfPoolWise!=null && !courseIdOfPoolWise.isEmpty()){
			  for (Integer integer : courseIdOfPoolWise) {
				if(courseMap.containsKey(integer)){
					courseMap.remove(integer);
				}
			}	
			}
		}
		return courseMap;
	}

	/**
	 * @param allotmentPoolForm
	 * @return
	 */
	public List<ExamRoomAllotmentSettingsPoolWise> convertPoolWiseFormToBO(ExamRoomAllotmentPoolForm allotmentPoolForm) {
		List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWiseList=new ArrayList<ExamRoomAllotmentSettingsPoolWise>();
			String[] courseIdList=allotmentPoolForm.getSelectedCourses().split(",");
			for (int i = 0; i < courseIdList.length; i++) {
				ExamRoomAllotmentSettingsPoolWise poolWise=new ExamRoomAllotmentSettingsPoolWise();
				if(courseIdList[i]!=null && !courseIdList[i].isEmpty()){
					Course course=new Course();
					course.setId(Integer.parseInt(courseIdList[i]));
					poolWise.setCourse(course); 
				}
				ExamRoomAllotmentPool allotmentPool=new ExamRoomAllotmentPool();
				allotmentPool.setId(Integer.parseInt(allotmentPoolForm.getPoolName()));
				poolWise.setAllotmentPool(allotmentPool);
				poolWise.setMidOrEndSem(allotmentPoolForm.getMidOrEndSem());
				poolWise.setCreatedBy(allotmentPoolForm.getUserId());
				poolWise.setCreatedDate(new Date());
				poolWise.setIsActive(true);
				poolWise.setSchemeNo(Integer.parseInt(allotmentPoolForm.getSemesterNo()));
				allotmentSettingsPoolWiseList.add(poolWise);
			}
		return allotmentSettingsPoolWiseList;
	}

	/**
	 * @param allotmentSettingsPoolWises
	 * @return
	 */
	public Map<String, Map<Integer, Map<String, ExamRoomAllotSettingPoolWiseTO>>> convertAllotRoomPoolWiseBOtoTO(List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWises) {
		Map<String, Map<Integer, Map<String, ExamRoomAllotSettingPoolWiseTO>>> midOrEndMap=new HashMap<String, Map<Integer,Map<String,ExamRoomAllotSettingPoolWiseTO>>>();
		if(allotmentSettingsPoolWises!=null && !allotmentSettingsPoolWises.isEmpty()){
			
			
		   for (ExamRoomAllotmentSettingsPoolWise examRoomAllotmentSettingsPoolWise : allotmentSettingsPoolWises) {
			   
			    if(midOrEndMap.containsKey(examRoomAllotmentSettingsPoolWise.getMidOrEndSem())){
			    	
			    	Map<Integer, Map<String, ExamRoomAllotSettingPoolWiseTO>> schemeMap1=midOrEndMap.get(examRoomAllotmentSettingsPoolWise.getMidOrEndSem());
			    	if(schemeMap1.containsKey(examRoomAllotmentSettingsPoolWise.getSchemeNo())){
			    		
			    		 Map<String, ExamRoomAllotSettingPoolWiseTO> poolMap1=schemeMap1.get(examRoomAllotmentSettingsPoolWise.getSchemeNo());
			    		if(poolMap1.containsKey(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getPoolName())){
			    			
			    			if(examRoomAllotmentSettingsPoolWise.getCourse()!=null){
			    				ExamRoomAllotSettingPoolWiseTO to = poolMap1.get(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getPoolName());	
			    			List<Integer> courseIds=to.getCourseIds();
			    			List<String> courseList=to.getCourseList();
			    			courseList.add(examRoomAllotmentSettingsPoolWise.getCourse().getName());
			    			to.setCourseList(courseList);
			    			courseIds.add(examRoomAllotmentSettingsPoolWise.getCourse().getId());
			    			to.setCourseIds(courseIds);
			    			poolMap1.put(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getPoolName(), to);
			    		}
			    			schemeMap1.put(examRoomAllotmentSettingsPoolWise.getSchemeNo(), poolMap1);
			    			midOrEndMap.put(examRoomAllotmentSettingsPoolWise.getMidOrEndSem(), schemeMap1);
			    		
			    		}else{
			    			
			    			ExamRoomAllotSettingPoolWiseTO allotSettingPoolWiseTO=new ExamRoomAllotSettingPoolWiseTO();
			    			if(examRoomAllotmentSettingsPoolWise.getMidOrEndSem().equalsIgnoreCase("M")){
					    	     allotSettingPoolWiseTO.setMidOrEndSem("Mid Sem");
					    	 }else{
					    		 allotSettingPoolWiseTO.setMidOrEndSem("End Sem");
					    	 }
					    	 if(examRoomAllotmentSettingsPoolWise.getCourse()!=null){
					    		 List<Integer> courseIds=new ArrayList<Integer>();
						    	 courseIds.add(examRoomAllotmentSettingsPoolWise.getCourse().getId());
					    		 if(examRoomAllotmentSettingsPoolWise.getCourse().getName()!=null){
					    			 List<String> courseList=new ArrayList<String>();
					    			 courseList.add(examRoomAllotmentSettingsPoolWise.getCourse().getName());
					    			 allotSettingPoolWiseTO.setCourseList(courseList);	
					    		 }
					    		 allotSettingPoolWiseTO.setCourseIds(courseIds);
					    	 }
					    		 allotSettingPoolWiseTO.setSchemeNo(examRoomAllotmentSettingsPoolWise.getSchemeNo());
					    		 allotSettingPoolWiseTO.setPoolName(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getPoolName());
					    		 allotSettingPoolWiseTO.setPoolId(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getId());
					    		 allotSettingPoolWiseTO.setId(examRoomAllotmentSettingsPoolWise.getId());
					    		 poolMap1.put(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getPoolName(), allotSettingPoolWiseTO);
					    		 schemeMap1.put(examRoomAllotmentSettingsPoolWise.getSchemeNo(), poolMap1);
					    		 midOrEndMap.put(examRoomAllotmentSettingsPoolWise.getMidOrEndSem(), schemeMap1);
			    		}
			    		
			    	}else{
			    		
			    		Map<String, ExamRoomAllotSettingPoolWiseTO> poolMap=new HashMap<String, ExamRoomAllotSettingPoolWiseTO>();
			    		   ExamRoomAllotSettingPoolWiseTO  allotSettingPoolWiseTO=new ExamRoomAllotSettingPoolWiseTO();
			    		   if(examRoomAllotmentSettingsPoolWise.getMidOrEndSem().equalsIgnoreCase("M")){
					    	     allotSettingPoolWiseTO.setMidOrEndSem("Mid Sem");
					    	 }else{
					    		 allotSettingPoolWiseTO.setMidOrEndSem("End Sem");
					    	 }
				    	 if(examRoomAllotmentSettingsPoolWise.getCourse()!=null){
				    		 List<Integer> courseIds=new ArrayList<Integer>();
					    	 courseIds.add(examRoomAllotmentSettingsPoolWise.getCourse().getId());
				    		 if(examRoomAllotmentSettingsPoolWise.getCourse().getName()!=null){
				    			 List<String> courseList=new ArrayList<String>();
				    			 courseList.add(examRoomAllotmentSettingsPoolWise.getCourse().getName());
				    			 allotSettingPoolWiseTO.setCourseList(courseList);			 
				    		 }
				    		 allotSettingPoolWiseTO.setCourseIds(courseIds);
				    	 }
				    		 allotSettingPoolWiseTO.setSchemeNo(examRoomAllotmentSettingsPoolWise.getSchemeNo());
				    		 allotSettingPoolWiseTO.setPoolName(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getPoolName());
				    		 allotSettingPoolWiseTO.setPoolId(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getId());
				    		 allotSettingPoolWiseTO.setId(examRoomAllotmentSettingsPoolWise.getId());
				    		 poolMap.put(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getPoolName(), allotSettingPoolWiseTO);
				    		 schemeMap1.put(examRoomAllotmentSettingsPoolWise.getSchemeNo(), poolMap);
				    		 midOrEndMap.put(examRoomAllotmentSettingsPoolWise.getMidOrEndSem(), schemeMap1);
			    		
			    	}
			    	
			    	
			    }else{
			    	Map<Integer, Map<String, ExamRoomAllotSettingPoolWiseTO>> schemeMap=new HashMap<Integer, Map<String,ExamRoomAllotSettingPoolWiseTO>>();
			    	Map<String, ExamRoomAllotSettingPoolWiseTO> poolMap=new HashMap<String, ExamRoomAllotSettingPoolWiseTO>();
			    	 ExamRoomAllotSettingPoolWiseTO allotSettingPoolWiseTO=new ExamRoomAllotSettingPoolWiseTO();
			    	 if(examRoomAllotmentSettingsPoolWise.getMidOrEndSem().equalsIgnoreCase("M")){
			    	     allotSettingPoolWiseTO.setMidOrEndSem("Mid Sem");
			    	 }else{
			    		 allotSettingPoolWiseTO.setMidOrEndSem("End Sem");
			    	 }
			    	 if(examRoomAllotmentSettingsPoolWise.getCourse()!=null){
			    		 List<Integer> courseIds=new ArrayList<Integer>();
				    	 courseIds.add(examRoomAllotmentSettingsPoolWise.getCourse().getId());
			    		 if(examRoomAllotmentSettingsPoolWise.getCourse().getName()!=null){
			    			 List<String> courseList=new ArrayList<String>();
			    			 courseList.add(examRoomAllotmentSettingsPoolWise.getCourse().getName());
			    			 allotSettingPoolWiseTO.setCourseList(courseList);		
			    		 }
			    		 allotSettingPoolWiseTO.setCourseIds(courseIds);
			    	 }
			    		 allotSettingPoolWiseTO.setSchemeNo(examRoomAllotmentSettingsPoolWise.getSchemeNo());
			    		 allotSettingPoolWiseTO.setPoolName(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getPoolName());
			    		 allotSettingPoolWiseTO.setPoolId(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getId());
			    		 allotSettingPoolWiseTO.setId(examRoomAllotmentSettingsPoolWise.getId());
			    		 poolMap.put(examRoomAllotmentSettingsPoolWise.getAllotmentPool().getPoolName(), allotSettingPoolWiseTO);
			    		 schemeMap.put(examRoomAllotmentSettingsPoolWise.getSchemeNo(), poolMap);
			    		 midOrEndMap.put(examRoomAllotmentSettingsPoolWise.getMidOrEndSem(), schemeMap);
			    }
		}	
		}
		return midOrEndMap;
		
	}

	

	/**
	 * @param settingsPoolWiseList
	 * @param allotmentPoolForm
	 * @param mode
	 * @return
	 */
	public List<ExamRoomAllotmentSettingsPoolWise> updateConvertFormToBo(List<ExamRoomAllotmentSettingsPoolWise> settingsPoolWiseList,
			ExamRoomAllotmentPoolForm allotmentPoolForm,String mode) {
		List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWiseSetBoList=new ArrayList<ExamRoomAllotmentSettingsPoolWise>();
		
		if (settingsPoolWiseList!=null && !settingsPoolWiseList.isEmpty()) {
		    if(mode.equalsIgnoreCase("Update")){
		    	if(allotmentPoolForm.getSelectedCourses()!=null && !allotmentPoolForm.getSelectedCourses().isEmpty()){
					String[] strings=allotmentPoolForm.getSelectedCourses().split(",");
						List<String> courseList=new ArrayList<String>(Arrays.asList(strings));
						for (ExamRoomAllotmentSettingsPoolWise examRoomAllotmentSettingsPoolWise : settingsPoolWiseList) {
							if(!courseList.contains(String.valueOf(examRoomAllotmentSettingsPoolWise.getCourse().getId()))){
								examRoomAllotmentSettingsPoolWise.setModifiedBy(allotmentPoolForm.getUserId());
								examRoomAllotmentSettingsPoolWise.setLastModifiedDate(new Date());
								examRoomAllotmentSettingsPoolWise.setIsActive(false);
							}else{
								examRoomAllotmentSettingsPoolWise.setModifiedBy(allotmentPoolForm.getUserId());
								examRoomAllotmentSettingsPoolWise.setLastModifiedDate(new Date());
								courseList.remove(String.valueOf(examRoomAllotmentSettingsPoolWise.getCourse().getId()));
							}
							allotmentSettingsPoolWiseSetBoList.add(examRoomAllotmentSettingsPoolWise);
						}
						if(courseList!=null && !courseList.isEmpty()){
							for (String courseId : courseList) {
								ExamRoomAllotmentSettingsPoolWise poolWise=new ExamRoomAllotmentSettingsPoolWise();
								Course course=new Course();
								course.setId(Integer.parseInt(courseId));
								poolWise.setCourse(course); 
								ExamRoomAllotmentPool allotmentPool=new ExamRoomAllotmentPool();
								allotmentPool.setId(Integer.parseInt(allotmentPoolForm.getPoolName()));
								poolWise.setAllotmentPool(allotmentPool);
								poolWise.setMidOrEndSem(allotmentPoolForm.getMidOrEndSem());
								poolWise.setCreatedBy(allotmentPoolForm.getUserId());
								poolWise.setCreatedDate(new Date());
								poolWise.setIsActive(true);
								poolWise.setSchemeNo(Integer.parseInt(allotmentPoolForm.getSemesterNo()));
								allotmentSettingsPoolWiseSetBoList.add(poolWise);
							}
						}
				}else{
					for (ExamRoomAllotmentSettingsPoolWise examRoomAllotmentSettingsPoolWise : allotmentSettingsPoolWiseSetBoList) {
						examRoomAllotmentSettingsPoolWise.setModifiedBy(allotmentPoolForm.getUserId());
						examRoomAllotmentSettingsPoolWise.setLastModifiedDate(new Date());
						examRoomAllotmentSettingsPoolWise.setIsActive(false);
						allotmentSettingsPoolWiseSetBoList.add(examRoomAllotmentSettingsPoolWise);
					}
				}
		    }else if(mode.equalsIgnoreCase("Delete")){
		    	  for (ExamRoomAllotmentSettingsPoolWise examRoomAllotmentSettingsPoolWise : settingsPoolWiseList) {
					  examRoomAllotmentSettingsPoolWise.setModifiedBy(allotmentPoolForm.getUserId());
					  examRoomAllotmentSettingsPoolWise.setLastModifiedDate(new Date());
					  examRoomAllotmentSettingsPoolWise.setIsActive(false);
					  allotmentSettingsPoolWiseSetBoList.add(examRoomAllotmentSettingsPoolWise);
				}
		     }
			
		}
		return allotmentSettingsPoolWiseSetBoList;
		
	}

}
