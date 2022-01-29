package com.kp.cms.actions.studentfeedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.studentfeedback.RoomMasterForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.studentfeedback.RoomMasterHandler;
import com.kp.cms.to.studentfeedback.BlockBoTo;
import com.kp.cms.to.studentfeedback.RoomEndMidSemRowsTo;
import com.kp.cms.to.studentfeedback.RoomMasterTo;

public class RoomMasterAction extends BaseDispatchAction
{

	private static final Log log=LogFactory.getLog(RoomMasterAction.class);

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	   public ActionForward initRoomMasterSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
       throws Exception{
       RoomMasterForm roomMasterForm = (RoomMasterForm)form;
       String formName = mapping.getName();
       HttpSession sessions=request.getSession();
	   sessions.setAttribute("roomUpdate",null);
       request.getSession().setAttribute("formName", formName);
       roomMasterForm.resetClear1();
       setEmpLocation(request);
       setBlockName(request,roomMasterForm);
       return mapping.findForward(CMSConstants.ROOM_MASTER_SEARCH);
   }
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward searchRoomMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		   throws Exception{
			RoomMasterForm roomMasterForm = (RoomMasterForm)form;
			ActionErrors errors = roomMasterForm.validate(mapping, request);
			HttpSession sessions=request.getSession();
			sessions.setAttribute("roomUpdate",null);
			 if(errors.isEmpty())
		        {
		        try {
		        	setEmpLocation(request);
				    setBlockName(request,roomMasterForm);
			        setRequestedDataToForm(roomMasterForm,errors);
			      } catch (Exception exception) {
					if (exception instanceof ApplicationException) {
						String msg = super.handleApplicationException(exception);
						roomMasterForm.setErrorMessage(msg);
						roomMasterForm.setErrorStack(exception.getMessage());
						return mapping.findForward(CMSConstants.ERROR_PAGE);
				   }else
					throw exception;
				    }
		      saveErrors(request, errors);
		      return mapping.findForward(CMSConstants.ROOM_MASTER_SEARCH);
		    } else
		    {
		     saveErrors(request, errors);
		     setEmpLocation(request);
		     setBlockName(request,roomMasterForm);
		     return mapping.findForward(CMSConstants.ROOM_MASTER_SEARCH);
		    }
		}
		/**
	     * @param roomMasterForm
		 * @param errors 
		 * @param errors 
	     * @throws Exception
	     */
	    private void setRequestedDataToForm(RoomMasterForm roomMasterForm, ActionErrors errors)throws Exception{
	    	List<RoomMasterTo> blockList = RoomMasterHandler.getInstance().getRoomMasterList(roomMasterForm);
	    	if(blockList.isEmpty()){
	    		 errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
	    	}
	        roomMasterForm.setRoomMasterList(blockList);
	    }
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initRoomMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        RoomMasterForm roomMasterForm = (RoomMasterForm)form;
        String formName = mapping.getName();
        ActionErrors errors = new ActionErrors();
        HttpSession session=request.getSession();
		session.setAttribute("roomUpdate",null);
        request.getSession().setAttribute("formName", formName);
        setEmpLocation(request);
        setBlockName(request,roomMasterForm);
       if(roomMasterForm.getLocationId()!=null && !roomMasterForm.getLocationId().isEmpty()
    	&& roomMasterForm.getBlockId()!=null && !roomMasterForm.getBlockId().isEmpty() &&
    	roomMasterForm.getRoomNo()!=null && !roomMasterForm.getRoomNo().isEmpty() &&
    	roomMasterForm.getFloor()!=null && !roomMasterForm.getFloor().isEmpty() &&
    	roomMasterForm.getFloorName()!=null && !roomMasterForm.getFloorName().isEmpty()){
          boolean isDuplicate = RoomMasterHandler.getInstance().duplicateCheck(roomMasterForm, errors, session);
            if(isDuplicate)
              {
              addErrors(request, errors);
              return mapping.findForward(CMSConstants.ROOM_MASTER_SEARCH);
             }
       }
        return mapping.findForward(CMSConstants.ROOM_MASTER);
    }
	/**
	 * @param request
	 * @param roomMasterForm
	 * @throws Exception
	 */
	private void setBlockName(HttpServletRequest request, RoomMasterForm roomMasterForm) throws Exception{
		if(roomMasterForm.getLocationId()!=null && !roomMasterForm.getLocationId().isEmpty()){
		int locationId=Integer.parseInt(roomMasterForm.getLocationId());
		Map<Integer,String> blockMap = CommonAjaxHandler.getInstance().getBlockByLocation(locationId);
		roomMasterForm.setBlockMap(blockMap);
            }
		}
	/**
     * @param request
     * @throws Exception
     */
    private void setEmpLocation(HttpServletRequest request)throws Exception {
        List<BlockBoTo> locationList = RoomMasterHandler.getInstance().getEmpLocation();
        request.getSession().setAttribute("locationList", locationList);
    }
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward setColumnAsEndSem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        RoomMasterForm roomMasterForm = (RoomMasterForm)form;
        setEndMidSemList(roomMasterForm);
        setBlockName(request,roomMasterForm);
        
        HttpSession sessions=request.getSession();
		if(sessions.getAttribute("roomUpdate")!=null && !sessions.getAttribute("roomUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("roomUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("roomMaster", "edit");
		  }
		}
       return mapping.findForward(CMSConstants.ROOM_MASTER);
}
/**
 * @param roomMasterForm
 * @throws Exception
 */
    private void setEndMidSemList(RoomMasterForm roomMasterForm)throws Exception{
    	
    	if(roomMasterForm.getEndSemTotalColumn()!=null && !roomMasterForm.getEndSemTotalColumn().isEmpty()){
    	  if(roomMasterForm.getEndSemList()==null || roomMasterForm.getEndSemList().isEmpty()){
        	int endValue=Integer.parseInt(roomMasterForm.getEndSemTotalColumn());
        	List<RoomEndMidSemRowsTo> endList=new ArrayList<RoomEndMidSemRowsTo>();
        	for(int i=1;i<=endValue;i++){
        		RoomEndMidSemRowsTo RoomMasterTo=new RoomEndMidSemRowsTo();
        		RoomMasterTo.setEndColumnNumber(i);
        		RoomMasterTo.setEndSemNoOfRows("");
        		endList.add(RoomMasterTo);
        		
        	  }
        	  roomMasterForm.setEndSemList(endList);
            }
    	  if(roomMasterForm.getEndSemList().size() > Integer.parseInt(roomMasterForm.getEndSemTotalColumn())){
    		  for(int i=(roomMasterForm.getEndSemList().size());i>Integer.parseInt(roomMasterForm.getEndSemTotalColumn());i--){
    		  List<RoomEndMidSemRowsTo> moreList=roomMasterForm.getEndSemList();
    		  moreList.remove(i-1);
    	      }
    	  }
    	  if(roomMasterForm.getEndSemList().size() < Integer.parseInt(roomMasterForm.getEndSemTotalColumn())){
    		  List<RoomEndMidSemRowsTo> moreList=roomMasterForm.getEndSemList();
    		  for(int i=(roomMasterForm.getEndSemList().size()+1);i<=Integer.parseInt(roomMasterForm.getEndSemTotalColumn());i++){
    		     RoomEndMidSemRowsTo RoomMasterTo=new RoomEndMidSemRowsTo();
    		      RoomMasterTo.setEndColumnNumber(i);
    		       RoomMasterTo.setEndSemNoOfRows("");
    		     moreList.add(RoomMasterTo);
    		  }
    	  }
     }
    	if(roomMasterForm.getMidSemTotalColumn()!=null && !roomMasterForm.getMidSemTotalColumn().isEmpty()){
    	 if(roomMasterForm.getMidSemList()==null || roomMasterForm.getMidSemList().isEmpty()){
        	int midValue=Integer.parseInt(roomMasterForm.getMidSemTotalColumn());
        	List<RoomEndMidSemRowsTo> midList=new ArrayList<RoomEndMidSemRowsTo>();
        	for(int i=1;i<=midValue;i++){
        		RoomEndMidSemRowsTo RoomMasterTo=new RoomEndMidSemRowsTo();
        		RoomMasterTo.setMidColumnNumber(i);
        		RoomMasterTo.setMidSemNoOfRows("");
        		midList.add(RoomMasterTo);
        	 }
        	 roomMasterForm.setMidSemList(midList);
           }
    	  if(roomMasterForm.getMidSemList().size() > Integer.parseInt(roomMasterForm.getMidSemTotalColumn())){
    		  for(int i=(roomMasterForm.getMidSemList().size());i>Integer.parseInt(roomMasterForm.getMidSemTotalColumn());i--){
    		  List<RoomEndMidSemRowsTo> moreList=roomMasterForm.getMidSemList();
    		  moreList.remove(i-1);
    	      }
    	  }
    	  if(roomMasterForm.getMidSemList().size() < Integer.parseInt(roomMasterForm.getMidSemTotalColumn())){
    		  List<RoomEndMidSemRowsTo> moreList=roomMasterForm.getMidSemList();
    		  for(int i=(roomMasterForm.getMidSemList().size()+1);i<=Integer.parseInt(roomMasterForm.getMidSemTotalColumn());i++){
    		     RoomEndMidSemRowsTo RoomMasterTo=new RoomEndMidSemRowsTo();
    		      RoomMasterTo.setMidColumnNumber(i);
    		      RoomMasterTo.setMidSemNoOfRows("");
    		      moreList.add(RoomMasterTo);
    		  }
    	  }
    	}
    }
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addRoomMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        RoomMasterForm roomMasterForm = (RoomMasterForm)form;
        HttpSession session=request.getSession();
		session.setAttribute("roomUpdate",null);
        setUserId(request, roomMasterForm);
        ActionMessages messages = new ActionMessages();
         ActionErrors errors = roomMasterForm.validate(mapping, request);
        String Mode="Add";
        validateNooFRows(roomMasterForm,errors,Mode);
        if(errors.isEmpty())
        {
            try
            {
                boolean isAdded = false;
                boolean isDuplicate = RoomMasterHandler.getInstance().duplicateCheck(roomMasterForm, errors, session);
                if(!isDuplicate)
                {
                    isAdded = RoomMasterHandler.getInstance().addRoomMaster(roomMasterForm);
                    if(isAdded)
                    {
                        messages.add("messages", new ActionMessage("knowledgepro.hlAdmission.entry.added.success"));
                        saveMessages(request, messages);
                        setRequestedDataToForm(roomMasterForm,errors);
                        roomMasterForm.resetClear();
                    } else
                    {
                        errors.add("error", new ActionError("knowledgepro.hlAdmission.entry.added.failure"));
                        addErrors(request, errors);
                        roomMasterForm.resetClear();
                    }
                } else
                {
                    addErrors(request, errors);
                }
            }
            catch(Exception exception)
            {
                log.error("Error occured in caste Entry Action", exception);
                String msg = super.handleApplicationException(exception);
                roomMasterForm.setErrorMessage(msg);
                roomMasterForm.setErrorStack(exception.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } else
        {
            saveErrors(request, errors);
            return mapping.findForward(CMSConstants.ROOM_MASTER);
        }
        log.info("end of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.ROOM_MASTER_SEARCH);
    }
   
    /**
     * @param roomMasterForm
     * @param errors
     * @param mode 
     */
    private void validateNooFRows(RoomMasterForm roomMasterForm,ActionErrors errors, String mode) {
    	int end=0;
    	int mid=0;
    	if(mode.equalsIgnoreCase("Update")){
    	   if(roomMasterForm.getEndSemCapacity()!=null && !roomMasterForm.getEndSemCapacity().isEmpty())
    		 end=Integer.parseInt(roomMasterForm.getEndSemCapacity());
    	   if(roomMasterForm.getMidSemCapacity()!=null && !roomMasterForm.getMidSemCapacity().isEmpty())
    		 mid=Integer.parseInt(roomMasterForm.getMidSemCapacity());
    	}
    	if(roomMasterForm.getTotalEndSem()!=null && !roomMasterForm.getTotalEndSem().isEmpty())
    	end=Integer.parseInt(roomMasterForm.getTotalEndSem());
    	if(roomMasterForm.getTotalMidSem()!=null && !roomMasterForm.getTotalMidSem().isEmpty())
		mid=Integer.parseInt(roomMasterForm.getTotalMidSem());
    	
    if(roomMasterForm.getEndSemList()!=null && !roomMasterForm.getEndSemList().isEmpty()){
		if(end==0){
			   errors.add("error", new ActionError("knowledgepro.hlAdmission.end.NoofRows"));
			}
		   if(roomMasterForm.getEndSemCapacity()!=null && !roomMasterForm.getEndSemCapacity().isEmpty() && end >0 && Integer.parseInt(roomMasterForm.getEndSemCapacity())>0 && end!=Integer.parseInt(roomMasterForm.getEndSemCapacity())){
				errors.add("error", new ActionError("knowledgepro.hlAdmission.end.seat.notmatch"));
			}
			if(roomMasterForm.getEndSemTotalColumn()==null || roomMasterForm.getEndSemTotalColumn().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.hlAdmission.endmid.total.Column"));
			}
			if(roomMasterForm.getEndSemCapacity()==null || roomMasterForm.getEndSemCapacity().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.hlAdmission.end.capacity"));
			}
    }
	if(roomMasterForm.getMidSemList()!=null && !roomMasterForm.getMidSemList().isEmpty()){
		if(mid==0){
		 errors.add("error", new ActionError("knowledgepro.hlAdmission.mid.NoofRows"));
		 }
		if(roomMasterForm.getMidSemCapacity()!=null && !roomMasterForm.getMidSemCapacity().isEmpty() && mid >0 && Integer.parseInt(roomMasterForm.getMidSemCapacity())>0 &&  mid!=Integer.parseInt(roomMasterForm.getMidSemCapacity())){
			errors.add("error", new ActionError("knowledgepro.hlAdmission.mid.seat.notmatch"));
		}
		if(roomMasterForm.getMidSemTotalColumn()==null || roomMasterForm.getMidSemTotalColumn().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.hlAdmission.endmid.total.Column"));
		}
		if(roomMasterForm.getMidSemCapacity()==null || roomMasterForm.getMidSemCapacity().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.hlAdmission.mid.capacity"));
		}
    }
	}
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editRoomMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	RoomMasterForm roomMasterForm = (RoomMasterForm)form;
    	HttpSession sessions=request.getSession();
        try
        {
            RoomMasterHandler.getInstance().editRoomMaster(roomMasterForm);
            request.setAttribute("roomMaster", "edit");
            sessions.setAttribute("roomUpdate", "edit");
        }
        catch(Exception e)
        {
            log.error("error in editing FeedBackQuestion...", e);
            String msg = super.handleApplicationException(e);
            roomMasterForm.setErrorMessage(msg);
            roomMasterForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward(CMSConstants.ROOM_MASTER);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateRoomMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	RoomMasterForm roomMasterForm = (RoomMasterForm)form;
        HttpSession session=request.getSession();
		session.setAttribute("roomUpdate",null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = roomMasterForm.validate(mapping, request);
		String Mode="Update";
		validateNooFRows(roomMasterForm,errors,Mode);
		boolean isUpdated = false;
		if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					roomMasterForm.resetClear();
			        String formName = mapping.getName();
			        request.getSession().setAttribute("formName", formName);
			        RoomMasterHandler.getInstance().editRoomMaster(roomMasterForm);
		            request.setAttribute("roomMaster", "edit");
			        return mapping.findForward(CMSConstants.BLOCK_BO);
				}
				setUserId(request, roomMasterForm);
				boolean isDuplicate = RoomMasterHandler.getInstance().duplicateCheck(roomMasterForm, errors, session);
				if(!isDuplicate){
					isUpdated = RoomMasterHandler.getInstance().updateRoomMaster(roomMasterForm);
				if (isUpdated) {
                    ActionMessage message = new ActionMessage("knowledgepro.hlAdmission.entry.update.success");
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    roomMasterForm.resetClear();
                } else {
                    errors.add("error", new ActionError("knowledgepro.hlAdmission.entry.update.failure"));
                    addErrors(request, errors);
                    roomMasterForm.resetClear();
                }}
				else{
	                request.setAttribute("roomMaster", "edit");
	                addErrors(request, errors);
	            }
			} catch (Exception e) {
	            log.error("Error occured in edit valuatorcharges", e);
	            String msg = super.handleApplicationException(e);
	            roomMasterForm.setErrorMessage(msg);
	            roomMasterForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }}else{
	        	setRequestedDataToForm(roomMasterForm,errors);
				saveErrors(request, errors);
		        request.setAttribute("roomMaster", "edit");
				return mapping.findForward(CMSConstants.ROOM_MASTER);
			}
		 setRequestedDataToForm(roomMasterForm,errors);
		 saveErrors(request, errors);
        log.debug("Exit: action class updateFeedBackQuestion");
        return mapping.findForward(CMSConstants.ROOM_MASTER_SEARCH);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteRoomMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.debug("Action class. Delete valuatorCharges ");
    	RoomMasterForm roomMasterForm = (RoomMasterForm)form;
    	HttpSession session=request.getSession();
		session.setAttribute("roomUpdate",null);
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        try
        {
            boolean isDeleted = RoomMasterHandler.getInstance().deleteRoomMaster(roomMasterForm);
            if(isDeleted)
            {
                ActionMessage message = new ActionMessage("knowledgepro.hlAdmission.entry.delete.success");
                messages.add("messages", message);
                saveMessages(request, messages);
            } else
            {
                ActionMessage message = new ActionMessage("knowledgepro.hlAdmission.entry.delete.failure");
                messages.add("messages", message);
                saveMessages(request, messages);
            }
            roomMasterForm.resetClear();
            setRequestedDataToForm(roomMasterForm,errors);
            saveErrors(request, errors);
        }
        catch(Exception e)
        {
            log.error("error submit valuatorCharges...", e);
            if(e instanceof ApplicationException)
            {
                String msg = super.handleApplicationException(e);
                roomMasterForm.setErrorMessage(msg);
                roomMasterForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            } else
            {
                String msg = super.handleApplicationException(e);
                roomMasterForm.setErrorMessage(msg);
                roomMasterForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        }
        log.debug("Action class. Delete valuatorCharges ");
        return mapping.findForward(CMSConstants.ROOM_MASTER);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward calculateEndSemTotalSeat (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        RoomMasterForm roomMasterForm = (RoomMasterForm)form;
        HttpSession sessions=request.getSession();
		if(sessions.getAttribute("roomUpdate")!=null && !sessions.getAttribute("roomUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("roomUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("roomMaster", "edit");
		  }
		}
    	int end=0;
		if(roomMasterForm.getEndSemList()!=null && !roomMasterForm.getEndSemList().isEmpty()){
			java.util.Iterator<RoomEndMidSemRowsTo> itr=roomMasterForm.getEndSemList().iterator();
			while (itr.hasNext()) {
				RoomEndMidSemRowsTo roomEndMidSemRowsTo = (RoomEndMidSemRowsTo) itr.next();
				if((roomEndMidSemRowsTo.getEndSemNoOfRows()!=null && !roomEndMidSemRowsTo.getEndSemNoOfRows().isEmpty()) &&
				  (roomEndMidSemRowsTo.getEndSemSeatInDesk()!=null && !roomEndMidSemRowsTo.getEndSemSeatInDesk().isEmpty())){
					end=end+(Integer.parseInt(roomEndMidSemRowsTo.getEndSemNoOfRows())* Integer.parseInt(roomEndMidSemRowsTo.getEndSemSeatInDesk()));
				}
			}
			if(end>0){
			roomMasterForm.setTotalEndSem(Integer.toString(end));
			}else{
				roomMasterForm.setTotalEndSem(null);
			}
		}
       return mapping.findForward(CMSConstants.ROOM_MASTER);
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward calculateMidSemTotalSeat (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        RoomMasterForm roomMasterForm = (RoomMasterForm)form;
        HttpSession sessions=request.getSession();
		if(sessions.getAttribute("roomUpdate")!=null && !sessions.getAttribute("roomUpdate").toString().isEmpty()){
		String test=sessions.getAttribute("roomUpdate").toString();
		if(test!=null && !test.isEmpty()){
			request.setAttribute("roomMaster", "edit");
		  }
		}
		int mid=0;
		if(roomMasterForm.getMidSemList()!=null && !roomMasterForm.getMidSemList().isEmpty()){
			java.util.Iterator<RoomEndMidSemRowsTo> itr=roomMasterForm.getMidSemList().iterator();
			while (itr.hasNext()) {
				RoomEndMidSemRowsTo roomEndMidSemRowsTo = (RoomEndMidSemRowsTo) itr.next();
				if((roomEndMidSemRowsTo.getMidSemNoOfRows()!=null && !roomEndMidSemRowsTo.getMidSemNoOfRows().isEmpty()) && 
				  (roomEndMidSemRowsTo.getMidSemSeatInDesk()!=null && !roomEndMidSemRowsTo.getMidSemSeatInDesk().isEmpty())){
					mid=mid+(Integer.parseInt(roomEndMidSemRowsTo.getMidSemNoOfRows())* (Integer.parseInt(roomEndMidSemRowsTo.getMidSemSeatInDesk())));
				}
			}
			if(mid>0){
				roomMasterForm.setTotalMidSem(Integer.toString(mid));
			}else{
				roomMasterForm.setTotalMidSem(null);
			}
		}
       return mapping.findForward(CMSConstants.ROOM_MASTER);
  }

}
