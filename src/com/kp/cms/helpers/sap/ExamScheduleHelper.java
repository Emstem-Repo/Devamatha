package com.kp.cms.helpers.sap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EventScheduleStudentAttendanceBo;
import com.kp.cms.bo.sap.ExamScheduleDate;
import com.kp.cms.bo.sap.ExamScheduleUsers;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.sap.ExamScheduleForm;
import com.kp.cms.handlers.sap.ExamScheduleHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.sap.ExamScheduleTo;
import com.kp.cms.transactions.sap.IExamScheduleTransaction;
import com.kp.cms.transactionsimpl.sap.ExamScheduleTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamScheduleHelper {

	private static volatile ExamScheduleHelper examScheduleHelper = null;
	private static final Log log = LogFactory.getLog(ExamScheduleHelper.class);
	
	/**
	 * return singleton object of TimeTableForClassHandler.
	 * @return
	 */
	public static ExamScheduleHelper getInstance() {
		if (examScheduleHelper == null) {
			examScheduleHelper = new ExamScheduleHelper();
		}
		return examScheduleHelper;
	}
	
	/**
	 * @param examschList
	 * @param examScheduleForm
	 * @return
	 */
	public ExamScheduleDate convertTotoBo(List<ExamScheduleTo> examschList,ExamScheduleForm examScheduleForm,HttpServletRequest request)throws Exception{
		ExamScheduleDate examSchDate=null;
		Map<Integer,String> venueMap=null;
		examSchDate=new ExamScheduleDate();
		 Iterator<ExamScheduleTo> iterator = examschList.iterator();
		 Set<ExamScheduleVenue> examSchVenue = new HashSet<ExamScheduleVenue>();
		 while (iterator.hasNext()) {
			 Set<ExamScheduleUsers> examSchUser = new HashSet<ExamScheduleUsers>();
			 ExamScheduleVenue venueBo=new ExamScheduleVenue();
			 ExamScheduleTo to = (ExamScheduleTo) iterator .next();
			 if(examSchVenue!=null && !examSchVenue.isEmpty()){
				 Iterator<ExamScheduleVenue> itr=examSchVenue.iterator();
					while (itr.hasNext()) {
						ExamScheduleVenue examVenue= itr.next();
						if(to.getWorkLocation().equalsIgnoreCase(examVenue.getWorkLocation())){
							if(Integer.parseInt(to.getVenue())==(examVenue.getVenue().getId())){
								request.setAttribute("venue",to.getVenue());
								request.setAttribute("isVenue", "true");
								venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
								to.setVenueMap(venueMap);
								throw new DuplicateException();
							}
						
						if(Integer.parseInt(to.getPriority())==examVenue.getPriority()){
							venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
							to.setVenueMap(venueMap);
							request.setAttribute("isPriority","true");
							throw new DuplicateException();
							}
						}
					}
			 }
			 
			 
			 	if(to.getTeachesTo()!=null){	
					String[] str1 = to.getTeachesTo();
					for (int x = 0; x < str1.length; x++) {
						if (str1[x] != null && str1[x].length() > 0) {
							ExamScheduleUsers userBo=new ExamScheduleUsers();
							Users user=new Users();
							user.setId(Integer.parseInt(str1[x]));
							userBo.setUsers(user);
							userBo.setCreatedBy(examScheduleForm.getUserId());
							userBo.setCreatedDate(new Date());
							userBo.setIsActive(true);
							userBo.setModifiedBy(examScheduleForm.getUserId());
							userBo.setLastModifiedDate(new Date());
							examSchUser.add(userBo);
						}
					}
				}
			 	venueBo.setPriority(Integer.parseInt(to.getPriority()));
			 	SapVenue venue=new SapVenue();
			 	venue.setId(Integer.parseInt(to.getVenue()));
			 	venueBo.setVenue(venue);
			 	venueBo.setWorkLocation(to.getWorkLocation());
			 	venueBo.setCreatedBy(examScheduleForm.getUserId());
			 	venueBo.setCreatedDate(new Date());
			 	venueBo.setModifiedBy(examScheduleForm.getUserId());
			 	venueBo.setLastModifiedDate(new Date());
			 	venueBo.setIsActive(true);
			 	venueBo.setExamScheduleUsers(examSchUser);
			 	examSchVenue.add(venueBo);
		 }
		 examSchDate.setExamDate(CommonUtil.ConvertStringToSQLDate(examScheduleForm.getExamDate()));
		 examSchDate.setSession(examScheduleForm.getSession());
		 examSchDate.setSessionOrder(Integer.parseInt(examScheduleForm.getSessionOrder()));
		 examSchDate.setCreatedBy(examScheduleForm.getUserId());
		 examSchDate.setCreatedDate(new Date());
		 examSchDate.setModifiedBy(examScheduleForm.getUserId());
		 examSchDate.setLastModifiedDate(new Date());
		 examSchDate.setIsActive(true);
		 examSchDate.setExamScheduleVenue(examSchVenue);
		
	return examSchDate;	
	}

	/**
	 * @param boList
	 * @return
	 */
	public List<ExamScheduleTo> convertBotoTo(List<ExamScheduleDate> boList){
		List<ExamScheduleTo> toList=new ArrayList<ExamScheduleTo>();
		if(boList!=null && !boList.isEmpty()){
			Iterator<ExamScheduleDate> iterator = boList.iterator();
			 while (iterator.hasNext()) {
				 ExamScheduleDate bo = (ExamScheduleDate) iterator .next();
				 ExamScheduleTo to=new ExamScheduleTo();
				 to.setId(bo.getId());
				 to.setExamDate((bo.getExamDate()).toString());
				 to.setSession(bo.getSession());
				 to.setSessionOrder(String.valueOf(bo.getSessionOrder()));
				 toList.add(to);
			 }
		}
		return toList;
	}
	
	
	/**
	 * @param bo
	 * @param examScheduleForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamScheduleTo> convertBotoToInEditMode(ExamScheduleDate bo,ExamScheduleForm examScheduleForm)throws Exception{
		List<ExamScheduleTo> toList=new ArrayList<ExamScheduleTo>();
		Map<Integer,String> workLocationMap=null;
		Map<Integer,String> venueMap=null;
		Map<Integer,String> mainMap=examScheduleForm.getMainTeachersMap();
		Map<Integer,String> unselectedTeachersMap=new HashMap<Integer,String>();
		unselectedTeachersMap.putAll(mainMap);
		Set<ExamScheduleVenue> venueBoSet=bo.getExamScheduleVenue();
		int size=0;
		int count=0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		String text = df.format(bo.getExamDate());  
		
		examScheduleForm.setExamDate(CommonUtil.formatSqlDate1(text));
		examScheduleForm.setSession(bo.getSession());
		examScheduleForm.setSessionOrder(String.valueOf(bo.getSessionOrder()));
		
		if(venueBoSet!=null && !venueBoSet.isEmpty() ){
		Iterator<ExamScheduleVenue> venueItr=venueBoSet.iterator();
		while (venueItr.hasNext()) {
			
			ExamScheduleTo to=new ExamScheduleTo();
			ExamScheduleVenue venueBo= venueItr .next();
		if(venueBo.getIsActive()){
			size=size+1;
			count=count+1;
			Map<Integer,String> selectedTeachersMap=new HashMap<Integer,String>();
			Set<ExamScheduleUsers> userBoSet=venueBo.getExamScheduleUsers();
			Map<Integer,Integer> usersBoIdMap=new HashMap<Integer,Integer>();
			Map<Integer,Integer> inActiveUsersBoIdMap=new HashMap<Integer,Integer>();
			
			if(userBoSet!=null && !userBoSet.isEmpty()){
				Iterator<ExamScheduleUsers> userItr=userBoSet.iterator();
				while (userItr.hasNext()) {
					ExamScheduleUsers userBo= userItr .next();
					if(userBo.getIsActive()){
					if(mainMap.containsKey(userBo.getUsers().getId())){
						String name=mainMap.get(userBo.getUsers().getId());
						selectedTeachersMap.put(userBo.getUsers().getId(),name);
						unselectedTeachersMap.remove(userBo.getUsers().getId());
						usersBoIdMap.put(userBo.getUsers().getId(), userBo.getId());
						}
					}else{
						inActiveUsersBoIdMap.put(userBo.getId(),userBo.getUsers().getId());
					}
				}
			}
			selectedTeachersMap=CommonUtil.sortMapByValue(selectedTeachersMap);
			unselectedTeachersMap=CommonUtil.sortMapByValue(unselectedTeachersMap);
			int workLocationId=venueBo.getVenue().getWorkLocationId().getId();
			int venueId=venueBo.getVenue().getId();
			 workLocationMap=ExamScheduleHandler.getInstance().getWorkLocation();
			 
				to.setExamSchVenueId(venueBo.getId());
				to.setWorkLocationMap(examScheduleForm.getWorkLocationMap());
				to.setWorkLocation(String.valueOf(workLocationId));
				venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(workLocationId);
				to.setVenueMap(venueMap);
				to.setVenue(String.valueOf(venueId));
				to.setPriority(String.valueOf(venueBo.getPriority()));
				to.setTeachersMap(unselectedTeachersMap);
				to.setSelectedTeachersMap(selectedTeachersMap);
				to.setIsActive(true);
				to.setUsersBOIdMap(usersBoIdMap);
				to.setInActiveUsersBOIdMap(inActiveUsersBoIdMap);
				to.setDeleteCount(count);
				toList.add(to);
				}
			}
		}
		examScheduleForm.setExamScheduleToListSize(String.valueOf(size));
		return toList;
	}
	
	
	/**
	 * @param examschList
	 * @param examScheduleForm
	 * @param venueList
	 * @return
	 */
	public ExamScheduleDate convertTotoBoInUpdateMode(List<ExamScheduleTo> examschList,ExamScheduleForm examScheduleForm,
			Set<ExamScheduleVenue> venueList,HttpServletRequest request)throws Exception{
		IExamScheduleTransaction transaction=ExamScheduleTransactionImpl.getInstance();
		ExamScheduleDate bo=transaction.getVenueAndInvigilatorDetails(examScheduleForm);
		Map<Integer,String> venueMap=null;
		
		 Iterator<ExamScheduleTo> iterator = examschList.iterator();
		 Set<ExamScheduleVenue> examSchVenueSet = new HashSet<ExamScheduleVenue>();
		 while (iterator.hasNext()) {
			 Set<ExamScheduleUsers> examSchUser = new HashSet<ExamScheduleUsers>();
			 ExamScheduleVenue venueBo=new ExamScheduleVenue();
			 ExamScheduleTo to = (ExamScheduleTo) iterator .next();
			 Map<Integer,Integer> usersBoIdMap=to.getUsersBOIdMap();
			 Map<Integer,Integer> inActiveUsersIdsMap=to.getInActiveUsersBOIdMap();
			 List<Integer> currentUsersIds=new ArrayList<Integer>();
			 if(examSchVenueSet!=null && !examSchVenueSet.isEmpty()){
				 Iterator<ExamScheduleVenue> itr=examSchVenueSet.iterator();
					while (itr.hasNext()) {
						ExamScheduleVenue examVenue= itr.next();
						if(to.getWorkLocation().equalsIgnoreCase(examVenue.getWorkLocation())){
							if(Integer.parseInt(to.getVenue())==(examVenue.getVenue().getId())){
								request.setAttribute("venue",to.getVenue());
								request.setAttribute("isVenueInUpdateMode", "true");
								venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
								to.setVenueMap(venueMap);
								throw new DuplicateException();
							}
						
						if(Integer.parseInt(to.getPriority())==examVenue.getPriority()){
							if(to.getWorkLocation()!=null && !to.getWorkLocation().isEmpty()){
								request.setAttribute("isPriorityInUpdate", "true");
								venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
								to.setVenueMap(venueMap);
							}
							throw new DuplicateException();
							}
						}
					}
			 }
			 
			 // /*  for add the users to ExamScheduleUsers
			 	if(to.getTeachesTo()!=null){	
					String[] str1 = to.getTeachesTo();
					for (int x = 0; x < str1.length; x++) {
						if (str1[x] != null && str1[x].length() > 0) {
							ExamScheduleUsers userBo=new ExamScheduleUsers();
							currentUsersIds.add(Integer.valueOf(str1[x]));
							if(usersBoIdMap!=null && !usersBoIdMap.isEmpty()){
							
								if(usersBoIdMap.containsKey(Integer.parseInt(str1[x]))){
									userBo.setId(usersBoIdMap.get(Integer.parseInt(str1[x])));
								}else{
									userBo.setCreatedBy(examScheduleForm.getUserId());
									userBo.setCreatedDate(new Date());
									}
							}else{
								userBo.setCreatedBy(examScheduleForm.getUserId());
								userBo.setCreatedDate(new Date());
							}
									Users user=new Users();
									user.setId(Integer.parseInt(str1[x]));
								userBo.setUsers(user);
								userBo.setIsActive(true);
								userBo.setModifiedBy(examScheduleForm.getUserId());
								userBo.setLastModifiedDate(new Date());
								examSchUser.add(userBo);
						}
					}
				}
			 	//delete un selected users data
			 	if(usersBoIdMap!=null && !usersBoIdMap.isEmpty()){
			 	for (Integer key : usersBoIdMap.keySet()) {
					if(!currentUsersIds.contains(key)){
						ExamScheduleUsers usersBo=transaction.getexamScheduleUsersData(usersBoIdMap.get(key));
						if(usersBo!=null){
							usersBo.setLastModifiedDate(new Date());
							usersBo.setModifiedBy(examScheduleForm.getUserId());
							usersBo.setIsActive(false);
							examSchUser.add(usersBo);
						}
					}
				}
			 	}
			 	// update inActive users data
			 	if(inActiveUsersIdsMap!=null && !inActiveUsersIdsMap.isEmpty()){
			 	for (Integer key : inActiveUsersIdsMap.keySet()) {
						ExamScheduleUsers usersBo=transaction.getInActiveExamScheduleUsersData(key);
						if(usersBo!=null){
							examSchUser.add(usersBo);
						}
					}
			 	}
			 //  */
			 	
			 // /*  for add the venue to ExamScheduleVenue
			 	
			 	if(to.getExamSchVenueId()!=0){
			 		venueBo.setId(to.getExamSchVenueId());
			 	}else{
				 	venueBo.setCreatedBy(examScheduleForm.getUserId());
				 	venueBo.setCreatedDate(new Date());
			 	}
			 	
			 	venueBo.setIsActive(true);
			 	venueBo.setPriority(Integer.parseInt(to.getPriority()));
		 			SapVenue venue=new SapVenue();
		 			venue.setId(Integer.parseInt(to.getVenue()));
	 			venueBo.setVenue(venue);
	 			venueBo.setWorkLocation(to.getWorkLocation());
			 	venueBo.setModifiedBy(examScheduleForm.getUserId());
			 	venueBo.setLastModifiedDate(new Date());
			 	if(examSchUser!=null && !examSchUser.isEmpty())
			 		venueBo.setExamScheduleUsers(examSchUser);
			 	examSchVenueSet.add(venueBo);
			 // */	
		 }
		 //add the deleted ExamVenueScheduleVenue details for updating the id other wise the deleted id going to update null
		 examSchVenueSet.addAll(venueList);
		 
		 /*if(examScheduleForm.getExamScheduleDateId()!=0){
			 bo.setId(examScheduleForm.getExamScheduleDateId());
		 }*/
		 bo.setExamDate(CommonUtil.ConvertStringToSQLDate(examScheduleForm.getExamDate()));
		 bo.setSession(examScheduleForm.getSession());
		 bo.setSessionOrder(Integer.parseInt(examScheduleForm.getSessionOrder()));
		 bo.setCreatedBy(examScheduleForm.getUserId());
		 bo.setCreatedDate(new Date());
		 bo.setModifiedBy(examScheduleForm.getUserId());
		 bo.setLastModifiedDate(new Date());
		 bo.setIsActive(true);
		 bo.setExamScheduleVenue(examSchVenueSet);
		
	return bo;	
	}

	public void checkingDuplicateVenueAndInvigilator(ExamScheduleDate bo,HttpServletRequest request,String str,List<ExamScheduleTo> examschList)throws Exception{
		Map<Integer,String> venueMap=null;
		Iterator<ExamScheduleTo> iterator = examschList.iterator();
			if(bo!=null && str.equalsIgnoreCase("sesion")){
				request.setAttribute("isSession", "true");
				while (iterator.hasNext()) {
					 ExamScheduleTo to = (ExamScheduleTo) iterator .next();
						venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
						to.setVenueMap(venueMap);
				 }
				throw new DuplicateException();
			}else if(bo!=null && str.equalsIgnoreCase("sesionorder")){
				 request.setAttribute("isSessionOrder", "true");
				 while (iterator.hasNext()) {
					 ExamScheduleTo to = (ExamScheduleTo) iterator .next();
						venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
						to.setVenueMap(venueMap);
				 }
				 throw new DuplicateException();
			}
	}
}
