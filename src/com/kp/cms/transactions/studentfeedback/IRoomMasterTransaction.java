package com.kp.cms.transactions.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.forms.studentfeedback.RoomMasterForm;

public interface IRoomMasterTransaction
{

    public List<EmployeeWorkLocationBO> getEmpLocation()throws Exception;

    public  boolean duplicateCheck(RoomMasterForm roomMasterForm, ActionErrors actionerrors, HttpSession httpsession)throws Exception;

    public  boolean addRoomMaster(RoomMaster roomMaster)throws Exception;
    
    public List<RoomMaster> getRoomMasterList(RoomMasterForm roomMasterForm)throws Exception;

    public  RoomMaster getRoomMasterById(int i)throws Exception;
    
    public boolean updateRoomMaster(RoomMaster roomMaster) throws Exception;

    public  boolean deleteRoomMaster(int i)throws Exception;

}
