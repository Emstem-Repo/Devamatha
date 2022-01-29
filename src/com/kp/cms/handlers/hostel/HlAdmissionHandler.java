package com.kp.cms.handlers.hostel;

import java.math.BigDecimal;
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
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.hostel.HlAdmissionBookingWaitingBo;
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.helpers.hostel.HlAdmissionHelper;
import com.kp.cms.helpers.hostel.HostelEntryHelper;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.hostel.IHlStudentCheckInTransaction;
import com.kp.cms.transactions.hostel.IHladmissionTransaction;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.hostel.HlAdmissionImpl;
import com.kp.cms.transactionsimpl.hostel.HlStudentCheckInImpl;
import com.kp.cms.utilities.CommonUtil;

public class HlAdmissionHandler {
	private static final Log log=LogFactory.getLog(HlAdmissionHandler.class);
	public static volatile HlAdmissionHandler hlAdmissionHandler=null;
	public static HlAdmissionHandler getInstance()
	{
		if(hlAdmissionHandler==null)
		{
			hlAdmissionHandler=new HlAdmissionHandler();
			return hlAdmissionHandler;
		}
		return hlAdmissionHandler;
	}
	
	IHladmissionTransaction transaction = new HlAdmissionImpl();
	
	
	/**
	 * @param hlAdmissionForm
	 * @param errors 
	 * @param request 
	 * @param mode 
	 * @return
	 * @throws Exception
	 */
	public List<HlAdmissionTo> gethlAdmissionList(HlAdmissionForm hlAdmissionForm, ActionErrors errors, HttpServletRequest request, String mode) throws Exception{
		   List<Object[]> hlAdmissionBoList=transaction.gethlAdmissionList(hlAdmissionForm,mode);
		   if(hlAdmissionBoList==null || hlAdmissionBoList.isEmpty()){
			   if(errors==null || errors.isEmpty()){
			   errors.add("error", new ActionError( "knowledgepro.norecords"));
			   return null;
			   }
			   return null;
		   }else{
		   List<HlAdmissionTo> hlAdmissionToList=HlAdmissionHelper.getInstance().convertBosToTOs(hlAdmissionBoList);
		   if(mode.equalsIgnoreCase("add")){
		   request.getSession().setAttribute("SelectedData", hlAdmissionToList);
		   }
			return hlAdmissionToList;
		   }
		}

	/**
	 * @param HlAdmissionForm
	 * @param errors
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck(HlAdmissionForm HlAdmissionForm,ActionErrors errors, HttpSession session) throws Exception{
		boolean duplicate=transaction.duplicateCheck(HlAdmissionForm,errors,session);
		boolean duplicateAppli=transaction.duplicateApplNo(HlAdmissionForm,errors);
		if(duplicate==false && duplicateAppli==false){
		return false;
		}else{
			return true;
		}
	}
	/**
	 * @param HlAdmissionForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addhlAdmission(HlAdmissionForm hlAdmissionForm,String mode)throws Exception{
		 List<Object[]> hlAdmissionBoList=transaction.getStudentDetails(hlAdmissionForm);
		 if(hlAdmissionBoList!=null && !hlAdmissionBoList.isEmpty()){
		HlAdmissionHelper.getInstance().setStudentDetails(hlAdmissionBoList,hlAdmissionForm);
		HlAdmissionBo hlAdmissionBo=HlAdmissionHelper.getInstance().convertFormTOBO(hlAdmissionForm);
		boolean isAdded=transaction.addhlAdmission(hlAdmissionBo,mode,hlAdmissionForm);
		return isAdded; 
		}else return false;
	}

	/**
	 * @param errors 
	 * @param HlAdmissionForm
	 * @throws Exception
	 */
	public void edithlAdmission(HlAdmissionForm hlAdmissionForm, ActionErrors errors)throws Exception {
		Object[] hlAdmissionBo=transaction.gethlAdmissionById(hlAdmissionForm.getId());
		if(hlAdmissionBo!=null){
		HlAdmissionHelper.getInstance().setBotoForm(hlAdmissionForm, hlAdmissionBo);
		}else{
			errors.add("error", new ActionError( "knowledgepro.hlAdmission.student.checkedin",hlAdmissionForm.gettRegNo()));
		}
	}

	/**
	 * @param HlAdmissionForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean updatehlAdmission(HlAdmissionForm hlAdmissionForm,String mode)throws Exception{
		HlAdmissionBo hlAdmissionBo=HlAdmissionHelper.getInstance().convertFormToBO(hlAdmissionForm);
		boolean isUpdated=transaction.addhlAdmission(hlAdmissionBo,mode,hlAdmissionForm);
		return isUpdated;
	}

	/**
	 * @param request 
	 * @param HlAdmissionForm
	 * @return
	 * @throws Exception
	 */
	public boolean deletehlAdmission(HlAdmissionForm hlAdmissionForm, HttpServletRequest request) throws Exception{
		boolean isDeleted=transaction.deletehlAdmission(hlAdmissionForm.getId(),hlAdmissionForm);
		 List<HlAdmissionTo> hlAdmissionToList=HlAdmissionHelper.getInstance().convertTosToTOs(hlAdmissionForm);
		 hlAdmissionForm.setHlAdmissionList(hlAdmissionToList);
		return isDeleted;
	}

	/**
	 * @param hlAdmissionForm
	 * @param errors
	 * @throws Exception
	 */
	public void getRoomAvailability(HlAdmissionForm hlAdmissionForm,ActionErrors errors) throws Exception{
		BigDecimal numberOfSeat=transaction.getnumberOfSeat(hlAdmissionForm);
		BigDecimal occupaySeats=transaction.getoccupaySeats(hlAdmissionForm);
		  HlAdmissionBo hlAdmissionBo=transaction.checkStudentInHlAdmission(hlAdmissionForm);
		   if(hlAdmissionBo==null){
		if(numberOfSeat!=null && occupaySeats!=null){
			if(occupaySeats.intValue()>=numberOfSeat.intValue()){
				 if(errors==null || errors.isEmpty()){
					   errors.add("error", new ActionError( "knowledgepro.Hostel.Admission.seatNotAvailable"));
					   }
			}
		}else if(numberOfSeat==null){
			 if(errors==null || errors.isEmpty()){
				   errors.add("error", new ActionError( "knowledgepro.Hostel.Admission.seatNotAvailable"));
				   }
		}
		   }else{
			   
			   errors.add("error", new ActionError( "knowledgepro.Hostel.Admission.student.exist"));
		   }
	}

	public void printHostelAdmission(HlAdmissionForm hlAdmissionForm, HttpServletRequest request) throws Exception{
		Object[] hlAdmissionBoList=transaction.gethlAdmissionById(hlAdmissionForm.getId());
		List<HlAdmissionTo> hlAdmissionToList=HlAdmissionHelper.getInstance().printBosToTOs(hlAdmissionBoList);
		request.getSession().setAttribute("SelectedData", hlAdmissionToList);
	}

	public String genderCheck(HlAdmissionForm hlAdmissionForm,ActionErrors errors, HttpSession session) throws Exception{
		 String hlAdmissionBoList=transaction.getGenderPersonaldata(hlAdmissionForm);
		 String gender=transaction.getGenderHostel(hlAdmissionForm);
		 if(hlAdmissionBoList!=null && !hlAdmissionBoList.isEmpty()){
		 if(hlAdmissionBoList.equalsIgnoreCase(gender)){
	     	return null;
		 }else {
			 if(hlAdmissionBoList.equalsIgnoreCase("MALE")){
			 return "Men";
			 }else{
				 return "Women";
			 }
			 
		 }
		 }else{
			 return "NoData";
		 }
	}

	public List<HostelTO> getHostelDetails(String gender) throws Exception{
		log.debug("inside getHostelDetails");
		List<HlHostel> hostelList = transaction.getHostelDeatils(gender);
		List<HostelTO> hostelTOList = HostelEntryHelper.getInstance().copyHostelBosToTos(hostelList);
		log.debug("leaving getHostelDetails");
		return hostelTOList;
	}
	
	public boolean duplicateCheckWaitingList(HlAdmissionForm HlAdmissionForm,ActionErrors errors, HttpSession session) throws Exception{
		boolean duplicate=transaction.duplicateCheckWaitingList(HlAdmissionForm,errors,session);
		if(duplicate==false){
		return false;
		}else{
			return true;
		}
	}
	
	public boolean addhlAdmissionWaiting(HlAdmissionForm hlAdmissionForm,String mode)throws Exception{
		 List<Object[]> hlAdmissionBoList=transaction.getStudentDetails(hlAdmissionForm);
		 if(hlAdmissionBoList!=null && !hlAdmissionBoList.isEmpty()){
		HlAdmissionHelper.getInstance().setStudentDetails(hlAdmissionBoList,hlAdmissionForm);
		HlAdmissionBookingWaitingBo bookingWaitingBo=HlAdmissionHelper.getInstance().convertFormTOWaitingBo(hlAdmissionForm);
		boolean isAdded=transaction.addhlAdmissionWaiting(bookingWaitingBo,mode);
		return isAdded; 
		}else return false;
	}
	
    public int getWaitingListPriorityNo(HlAdmissionForm admissionForm) throws Exception
    {
    	 long priorityNo=transaction.getWaitingListPriorityNo(admissionForm);
		  priorityNo=priorityNo+1;
    	 return (int) priorityNo;
    	 
    	
    }
    public HlAdmissionBo getAdmissionId(String hostelId,String regNo,String academicYear)throws Exception{
    	HlAdmissionBo hlAdmissionBo= transaction.getAdmissionId(hostelId,regNo,academicYear);
    	return hlAdmissionBo;
    }
    public HlRoom getRoomIdAndRoomTypeId(String hostelId,int blockId,int unitId,String roomNo)throws Exception{
    	HlRoom object=transaction.getRoomIdAndRoomTypeId(hostelId,blockId,unitId,roomNo);
    	return object;
    }
    public HlBeds getRoomBedId(int roomId,String bedNo,int rTypeId,String hostelId)throws Exception{
    	HlBeds hlbeds=transaction.getRoomBedId(roomId,bedNo,rTypeId,hostelId);
    	return hlbeds;
    }
    public boolean addUploadRoomDetails(List<HlAdmissionBo> results) throws Exception {
    	log.info("call of addUploadChildrenDetails method in ChildrenDetailsUploadHandler class.");
    	boolean isAdd = false;
    	isAdd=transaction.addUploadRoomDetails(results);
    	log.info("end of addUploadChildrenDetails method in ChildrenDetailsUploadHandler class.");
    	return isAdd;
    }
    
    public int getStudentInWaitingListWithPriorityNo(HlAdmissionForm hlAdmissionForm,HttpSession session) throws Exception
    {
    	 return transaction.getStudentInWaitingListWithPriorityNo(hlAdmissionForm,session);
    }
    
    public void resetStudentInWaitingList(int waitingId,HlAdmissionForm admissionForm) throws Exception
    {
    	boolean updateWaitingList=transaction.resetStudentInWaitingList(waitingId);
    	if(updateWaitingList)
    	{
    		int k=0;
        	List<HlAdmissionBookingWaitingBo> bookingWaitingBoList=transaction.getStudentPriorityNumberInWaitingList(admissionForm);
        	Iterator<HlAdmissionBookingWaitingBo> iterator=bookingWaitingBoList.iterator();
        	while (iterator.hasNext()) {
                 HlAdmissionBookingWaitingBo bookingWaitingBo= (HlAdmissionBookingWaitingBo) iterator.next();
                 int j=bookingWaitingBo.getWaitingListPriorityNo();
                 if(j!=1)
                 {
                	 int m=0;
                	 if(k==1 && j==2)
                	 {
                		m=j; 
                	 }
                	 else
                	  m=j-1;
                	 bookingWaitingBo.setWaitingListPriorityNo(m);
                	 bookingWaitingBo.setId(bookingWaitingBo.getId());
                	 transaction.updateWaitingList(bookingWaitingBo);
                 }
                 k++;
			}
    	}
    }

	/**
	 * @param hlAdmissionForm
	 * @param request
	 * @throws Exception
	 */
	public void printHlAdmission(HlAdmissionForm hlAdmissionForm,
			HttpServletRequest request) throws Exception{
		HlAdmissionBo bo = transaction.getAdmissionDetailsForOrint(hlAdmissionForm.getId()); 
		HlAdmissionTo to = HlAdmissionHelper.getInstance().printBosToTO(bo);
		hlAdmissionForm.setPrintData(to);
	}

	public void setHostelApplicationValuesToForm(HlAdmissionForm hlAdmissionForm) throws Exception {
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
		String hostel_roomType = iCommonAjax.checkStudentApplication(hlAdmissionForm.getRegNo(),hlAdmissionForm.getApplNo(),hlAdmissionForm.getYear());
		if(!hostel_roomType.isEmpty()){
			hlAdmissionForm.setHostelName(hostel_roomType.split("%")[0]);
			hlAdmissionForm.setRoomTypeName(hostel_roomType.split("%")[1]);
			hlAdmissionForm.setHlApplicationNo( hostel_roomType.split("%")[2]);
		}
		
	}
}
