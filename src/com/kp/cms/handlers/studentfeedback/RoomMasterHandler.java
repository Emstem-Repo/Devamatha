package com.kp.cms.handlers.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.forms.studentfeedback.RoomMasterForm;
import com.kp.cms.helpers.studentfeedback.RoomMasterHelpers;
import com.kp.cms.to.studentfeedback.BlockBoTo;
import com.kp.cms.to.studentfeedback.RoomMasterTo;
import com.kp.cms.transactions.studentfeedback.IRoomMasterTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.RoomMasterImpl;

public class RoomMasterHandler
{

	private static final Log log=LogFactory.getLog(RoomMasterHandler.class);
	public static volatile RoomMasterHandler roomMasterHandler=null;
	
	public static RoomMasterHandler getInstance()
    {
        if(roomMasterHandler == null)
        {
        	roomMasterHandler = new RoomMasterHandler();
            return roomMasterHandler;
        } else
        {
            return roomMasterHandler;
        }
    }
	IRoomMasterTransaction transaction = RoomMasterImpl.getInstance();
    
    /**
     * @return
     * @throws Exception
     */
    public List<BlockBoTo> getEmpLocation()throws Exception
    {
        List<EmployeeWorkLocationBO> feedBackGroup = transaction.getEmpLocation();
        List<BlockBoTo> group = RoomMasterHelpers.getInstance().convertBosToTOs(feedBackGroup);
        return group;
    }
    
    /**
     * @param roomMasterForm 
     * @return
     * @throws Exception
     */
    public List<RoomMasterTo> getRoomMasterList(RoomMasterForm roomMasterForm) throws Exception
    {
        List<RoomMaster> roomMaster = transaction.getRoomMasterList(roomMasterForm);
        List<RoomMasterTo> roomMasterList = RoomMasterHelpers.getInstance().convertBoToTos(roomMaster);
        return roomMasterList;
    }
    /**
     * @param roomMasterForm
     * @param errors
     * @param session
     * @return
     * @throws Exception
     */
    public boolean duplicateCheck(RoomMasterForm roomMasterForm, ActionErrors errors, HttpSession session)
        throws Exception
    {
        boolean duplicate = transaction.duplicateCheck(roomMasterForm, errors, session);
        return duplicate;
    }
    /**
     * @param roomMasterForm
     * @return
     * @throws Exception
     */
    public boolean addRoomMaster(RoomMasterForm roomMasterForm) throws Exception
    {
    	RoomMaster roomMaster = RoomMasterHelpers.getInstance().convertFormToBos(roomMasterForm);
        boolean isAdded = transaction.addRoomMaster(roomMaster);
        return isAdded;
    }
    /**
     * @param studentFeedBackQuestionForm
     * @throws Exception
     */
    public void editRoomMaster(RoomMasterForm roomMasterForm)
        throws Exception
    {
    	RoomMaster roomMaster = transaction.getRoomMasterById(roomMasterForm.getId());
    	RoomMasterHelpers.getInstance().setDataBoToForm(roomMasterForm, roomMaster);
    }

    /**
     * @param roomMasterForm
     * @return
     * @throws Exception
     */
    public boolean updateRoomMaster(RoomMasterForm roomMasterForm)
        throws Exception
    {
    	RoomMaster roomMaster = RoomMasterHelpers.getInstance().convertFormToBos(roomMasterForm);
        boolean isUpdated = transaction.updateRoomMaster(roomMaster);
        return isUpdated;
    }

    /**
     * @param roomMasterForm
     * @return
     * @throws Exception
     */
    public boolean deleteRoomMaster(RoomMasterForm roomMasterForm)
        throws Exception
    {
        boolean isDeleted = transaction.deleteRoomMaster(roomMasterForm.getId());
        return isDeleted;
    }
}
