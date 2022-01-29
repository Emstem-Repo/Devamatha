package com.kp.cms.handlers.hostel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.forms.hostel.HlStudentChInEditForm;
import com.kp.cms.helpers.hostel.HlStudentCheckInHelper;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.hostel.IHlStudentCheckInTransaction;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.hostel.HlStudentCheckInImpl;
import com.kp.cms.utilities.CommonUtil;

public class HlStudentCheckInHandler {
	
	private static final Log log=LogFactory.getLog(HlStudentCheckInHandler.class);
	public static volatile HlStudentCheckInHandler hlAdmissionHandler=null;
	public static HlStudentCheckInHandler getInstance()
	{
		if(hlAdmissionHandler==null)
		{
			hlAdmissionHandler=new HlStudentCheckInHandler();
			return hlAdmissionHandler;
		}
		return hlAdmissionHandler;
	}
	
	IHlStudentCheckInTransaction transaction = new HlStudentCheckInImpl();
	
	
	public List<HlAdmissionTo> gethlAdmissionList(HlStudentChInEditForm HlStudentChInEditForm, ActionErrors errors, HttpServletRequest request, String mode) throws Exception{
		   List<HlAdmissionBo> hlAdmissionBoList=transaction.gethlAdmissionList(HlStudentChInEditForm,mode);
		   if(hlAdmissionBoList==null || hlAdmissionBoList.isEmpty()){
			   if(errors==null || errors.isEmpty()){
			   errors.add("error", new ActionError( "knowledgepro.norecords"));
			   return null;
			   }
			   return null;
		   }else{
		   List<HlAdmissionTo> hlAdmissionToList=HlStudentCheckInHelper.getInstance().convertBosToTOs(hlAdmissionBoList,HlStudentChInEditForm);
		   if(mode.equalsIgnoreCase("add")){
		   request.getSession().setAttribute("SelectedData", hlAdmissionToList);
		   }
			return hlAdmissionToList;
		   }
		}
	
	
	public boolean addhlAdmission(HlStudentChInEditForm HlStudentChInEditForm,String mode)throws Exception{
		 List<Object[]> hlAdmissionBoList=transaction.getStudentDetails(HlStudentChInEditForm);
		 if(hlAdmissionBoList!=null && !hlAdmissionBoList.isEmpty()){
		HlStudentCheckInHelper.getInstance().setStudentDetails(hlAdmissionBoList,HlStudentChInEditForm);
		HlAdmissionBo hlAdmissionBo=HlStudentCheckInHelper.getInstance().convertFormTOBO(HlStudentChInEditForm);
		boolean isAdded=transaction.addhlAdmission(hlAdmissionBo,mode);
		return isAdded; 
		}else return false;
	}

	/**
	 * @param HlStudentChInEditForm
	 * @throws Exception
	 */
	public void edithlAdmission(HlStudentChInEditForm HlStudentChInEditForm)throws Exception {
		Object[] hlAdmissionBo=transaction.gethlAdmissionById(HlStudentChInEditForm.getId());
		HlStudentCheckInHelper.getInstance().setBotoForm(HlStudentChInEditForm, hlAdmissionBo);
	}

	/**
	 * @param HlStudentChInEditForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean updatehlAdmission(HlStudentChInEditForm HlStudentChInEditForm,String mode)throws Exception{
		HlAdmissionBo hlAdmissionBo=HlStudentCheckInHelper.getInstance().convertFormToBO(HlStudentChInEditForm);
		boolean isUpdated=transaction.addhlAdmission(hlAdmissionBo,mode);
		return isUpdated;
	}
	public String genderCheck(HlStudentChInEditForm hlAdmissionForm,ActionErrors errors, HttpSession session) throws Exception{
		 String hlAdmissionBoList=transaction.getGenderPersonaldata(hlAdmissionForm);
		 String gender=transaction.getGenderHostel(hlAdmissionForm);
		 if(hlAdmissionBoList!=null && !hlAdmissionBoList.isEmpty()){
		 if(hlAdmissionForm.getGender().equalsIgnoreCase(gender)){
	     	return null;
		 }else {
			 if(hlAdmissionForm.getGender().equalsIgnoreCase("MALE")){
				 return "Men";
			 }else{
				 return "Women";
			 }
		 }
		 }else{
			 return "NoData";
		 }
	}
	
	public boolean duplicateCheck(HlStudentChInEditForm HlAdmissionForm,ActionErrors errors, HttpSession session) throws Exception{
		boolean duplicate=transaction.duplicateCheck(HlAdmissionForm,errors,session);
		//boolean duplicateAppli=transaction.duplicateApplNo(HlAdmissionForm,errors);
		if(duplicate==false /*&& duplicateAppli==false*/){
		return false;
		}else{
			return true;
		}
	}
	
	public List<FacilityTO> getAllFacility()throws Exception{
		log.info("Entering into getAllFacility of CheckinHandler");
		//IRoomTypeTransaction transaction = new RoomTypeTransactionImpl();
		List<HlFacility> facilityBOList = transaction.getAllFacility();
		log.info("Leaving into getAllFacility of CheckinHandler");
		return HlStudentCheckInHelper.getInstance().copyFacilityBOToTO(facilityBOList);
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomsAvailable(int hstlName,int RoomType,String academicYear,int block,int unit,String floor) throws Exception
	{
		Map<Integer, String> hostelmap = transaction.getRoomsAvailable(hstlName, RoomType,academicYear, block, unit,floor);
		hostelmap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelmap);
        return hostelmap;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBedsAvailable(int roomId, int academicYear) throws Exception
	{
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		Map<Integer, String> hostelmap = iCommonAjax.getBedsAvailable(roomId, academicYear);
		hostelmap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelmap);
        return hostelmap;
	}
	/**
	 * @param HlStudentChInEditForm
	 * @param errors
	 * @param request
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<HlAdmissionTo> gethlAdmissionListNew(HlStudentChInEditForm HlStudentChInEditForm, ActionErrors errors, 
			HttpServletRequest request, String mode) throws Exception{

		   HlAdmissionBo hlAdmissionBo=transaction.gethlAdmission(HlStudentChInEditForm,mode);
		   if(hlAdmissionBo==null){
			   if(errors==null || errors.isEmpty()){
				   errors.add("error", new ActionError( "knowledgepro.norecords"));
				   return null;
			   }
			   return null;
		   }else{
			   List<HlAdmissionTo> hlAdmissionToList=HlStudentCheckInHelper.getInstance().convertBosToTOsNew(hlAdmissionBo,HlStudentChInEditForm);
			   if(mode.equalsIgnoreCase("add")){
				   request.getSession().setAttribute("SelectedData", hlAdmissionToList);
			   }
				return hlAdmissionToList;
		   }
	}


	/**
	 * @param hostelId
	 * @param hostelroomType
	 * @param academicYear
	 * @param blockId
	 * @param unitId
	 * @param floorNo
	 * @param studentId 
	 * @param roomMap 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getRooms(String hostelId, String hostelroomType, String academicYear, String blockId,
			String unitId, String floorNo, String studentId, Map<Integer, String> roomMap) throws Exception{
		List<Object[]> listOfRooms = transaction.getRoomsByBlock(hostelId,blockId,unitId,floorNo,
				academicYear,hostelroomType,studentId);
		if(listOfRooms != null){
			Iterator<Object[]> iterator = listOfRooms.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				if(objects[0] != null && objects[0].toString() != null && objects[1] != null){
					roomMap.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
				}
			}
		}
		return roomMap;
	}
}
