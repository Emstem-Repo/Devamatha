package com.kp.cms.helpers.studentfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.studentfeedback.BlockBo;
import com.kp.cms.forms.studentfeedback.BlockBoForm;
import com.kp.cms.to.studentfeedback.BlockBoTo;

public class BlockBoHelpers
{

	private static final Log log=LogFactory.getLog(BlockBoHelpers.class);
	public static volatile BlockBoHelpers blockBoHelpers = null;
   

    public static BlockBoHelpers getInstance()
    {
        if(blockBoHelpers == null)
        {
        	blockBoHelpers = new BlockBoHelpers();
            return blockBoHelpers;
        } else
        {
            return blockBoHelpers;
        }
    }

    public List<BlockBoTo> convertBosToTOs(List<EmployeeWorkLocationBO> locationBos)
    {
        List<BlockBoTo> locationList = new ArrayList<BlockBoTo>();
        if(locationBos != null)
        {
        	Iterator <EmployeeWorkLocationBO> iterator=locationBos.iterator();
			while(iterator.hasNext())
			{
				EmployeeWorkLocationBO locationBo = (EmployeeWorkLocationBO)iterator.next();
				BlockBoTo locationTo = new BlockBoTo();
				locationTo.setEmpLocationId(locationBo.getId());
				locationTo.setEmpLocationName(locationBo.getName());
				locationList.add(locationTo);
            }

        }
        return locationList;
    }

    public List<BlockBoTo> convertBoToTos(List<BlockBo> blockList)
    {
        List<BlockBoTo> blockToListList = new ArrayList<BlockBoTo>();
        if(blockList != null)
        {
        	Iterator itr=blockList.iterator();
    		while (itr.hasNext()) {
    			BlockBo blockBo = (BlockBo)itr.next();
    			BlockBoTo blockBoTo= new BlockBoTo();
    			blockBoTo.setId(blockBo.getId());
    			blockBoTo.setLocationId(String.valueOf(blockBo.getLocationId().getId()));
    			blockBoTo.setLocationName(blockBo.getLocationId().getName());
    			blockBoTo.setBlockName(blockBo.getBlockName());
    			blockBoTo.setBlockOrderNO(String.valueOf(blockBo.getBlockOrder()));
                blockToListList.add(blockBoTo);
            }

        }
        return blockToListList;
    }

    public BlockBo convertFormToBos(BlockBoForm blockBoForm)
    {
    	BlockBo blockBo = new BlockBo();
        EmployeeWorkLocationBO type = new EmployeeWorkLocationBO();
        type.setId(Integer.parseInt(blockBoForm.getLocationId()));
        blockBo.setLocationId(type);
        blockBo.setBlockName(blockBoForm.getBlockName());
        blockBo.setCreatedBy(blockBoForm.getUserId());
        blockBo.setCreatedDate(new Date());
        blockBo.setLastModifiedDate(new Date());
        blockBo.setModifiedBy(blockBoForm.getUserId());
        blockBo.setIsActive(Boolean.valueOf(true));
        blockBo.setBlockOrder(Integer.parseInt(blockBoForm.getAllotmentOrderNo()));
        return blockBo;
    }

    public void setDataBoToForm(BlockBoForm blockBoForm, BlockBo blockBo)
    {
        if(blockBo != null)
        {
        	blockBoForm.setId(blockBo.getId());
            blockBoForm.setLocationId(String.valueOf(blockBo.getLocationId().getId()));
            blockBoForm.setBlockName(blockBo.getBlockName());
            blockBoForm.setAllotmentOrderNo(String.valueOf(blockBo.getBlockOrder()));
        }
    }

    public BlockBo updateFormToBo(BlockBoForm blockBoForm)
    {
    	BlockBo blockBo = new BlockBo();
    	EmployeeWorkLocationBO type = new EmployeeWorkLocationBO();
        type.setId(Integer.parseInt(blockBoForm.getLocationId()));
        blockBo.setLocationId(type);
        blockBo.setBlockName(blockBoForm.getBlockName());
        blockBo.setId(blockBoForm.getId());
        blockBo.setLastModifiedDate(new Date());
        blockBo.setModifiedBy(blockBoForm.getUserId());
        blockBo.setIsActive(Boolean.valueOf(true));
        blockBo.setBlockOrder(Integer.parseInt(blockBoForm.getAllotmentOrderNo()));
        return blockBo;
    }
}
