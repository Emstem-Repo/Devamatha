package com.kp.cms.handlers.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.studentfeedback.BlockBo;
import com.kp.cms.forms.studentfeedback.BlockBoForm;
import com.kp.cms.helpers.studentfeedback.BlockBoHelpers;
import com.kp.cms.to.studentfeedback.BlockBoTo;
import com.kp.cms.transactions.studentfeedback.IBlockBoTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.BlockBoImpl;

public class BlockBoHandler
{

	private static final Log log=LogFactory.getLog(BlockBoHandler.class);
	public static volatile BlockBoHandler blockBoHandler=null;
	
	public static BlockBoHandler getInstance()
    {
        if(blockBoHandler == null)
        {
        	blockBoHandler = new BlockBoHandler();
            return blockBoHandler;
        } else
        {
            return blockBoHandler;
        }
    }
	IBlockBoTransaction transaction = BlockBoImpl.getInstance();
    
    public List<BlockBoTo> getEmpLocation()throws Exception
    {
        List<EmployeeWorkLocationBO> locationBo = transaction.getEmpLocation();
        List<BlockBoTo> locationTo = BlockBoHelpers.getInstance().convertBosToTOs(locationBo);
        return locationTo;
    }

    public List<BlockBoTo> getBlockBoList()throws Exception
    {
        List<BlockBo> blockList = transaction.getBlockBoList();
        List<BlockBoTo> blockToList = BlockBoHelpers.getInstance().convertBoToTos(blockList);
        return blockToList;
    }

    public boolean duplicateCheck(BlockBoForm blockBoForm, ActionErrors errors, HttpSession session) throws Exception
    {
        boolean duplicate = transaction.duplicateCheck(blockBoForm, errors, session);
        return duplicate;
    }

    public boolean addBlockBo(BlockBoForm blockBoForm)throws Exception
    {
    	BlockBo blockBo = BlockBoHelpers.getInstance().convertFormToBos(blockBoForm);
        boolean isAdded = transaction.addBlockBo(blockBo);
        return isAdded;
    }

    public void editBlockBo(BlockBoForm blockBoForm)throws Exception
    {
    	BlockBo blockBo = transaction.getBlockBoById(blockBoForm.getId());
    	BlockBoHelpers.getInstance().setDataBoToForm(blockBoForm, blockBo);
    }

    public boolean updateBlockBo(BlockBoForm blockBoForm) throws Exception
    {
    	BlockBo blockBo = BlockBoHelpers.getInstance().updateFormToBo(blockBoForm);
        boolean isUpdated = transaction.updateBlockBo(blockBo);
        return isUpdated;
    }

    public boolean deleteBlockBo(BlockBoForm blockBoForm) throws Exception
    {
        boolean isDeleted = transaction.deleteBlockBo(blockBoForm.getId());
        return isDeleted;
    }


}
