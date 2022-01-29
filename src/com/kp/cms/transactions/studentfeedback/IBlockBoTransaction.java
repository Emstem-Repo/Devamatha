package com.kp.cms.transactions.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.studentfeedback.BlockBo;
import com.kp.cms.forms.studentfeedback.BlockBoForm;

public interface IBlockBoTransaction
{

    public List<EmployeeWorkLocationBO> getEmpLocation()throws Exception;

    public List<BlockBo> getBlockBoList()throws Exception;

    public  boolean duplicateCheck(BlockBoForm blockBoForm, ActionErrors actionerrors, HttpSession httpsession)throws Exception;

    public  boolean addBlockBo(BlockBo blockBo)throws Exception;

    public  BlockBo getBlockBoById(int i)throws Exception;
    
    public boolean updateBlockBo(BlockBo blockBo) throws Exception;

    public  boolean deleteBlockBo(int i)throws Exception;

}
