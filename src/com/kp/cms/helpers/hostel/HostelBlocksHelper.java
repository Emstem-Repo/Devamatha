package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.helpers.hostel.HostelBlocksHelper;
import com.kp.cms.to.hostel.HostelBlocksTO;
import com.kp.cms.to.hostel.HostelTO;

public class HostelBlocksHelper {
	
	private static final Log log = LogFactory.getLog(HostelBlocksHelper.class);
	private static volatile HostelBlocksHelper hostelBlocksHelper = null;
	
	private HostelBlocksHelper() {
	}
	
	public static HostelBlocksHelper getInstance() {

		if (hostelBlocksHelper == null) {
			hostelBlocksHelper = new HostelBlocksHelper();
		}
		return hostelBlocksHelper;
	}
	
	public HlBlocks populateTOtoBO(HostelBlocksTO hostelBlocksTO) throws Exception
	{
		log.info("Inside populateTOtoBO of HostelBlocksHelper");
		HlBlocks hlBlocks = null;
		
		if (hostelBlocksTO != null) {
			hlBlocks = new HlBlocks();
			if(hostelBlocksTO.getId()>0){
				hlBlocks.setId(hostelBlocksTO.getId());
			}
			if(hostelBlocksTO.getName()!=null){
				hlBlocks.setName(hostelBlocksTO.getName());
			}
			if (hostelBlocksTO.getHostelTO()!= null) {
				HlHostel hlHostel = new HlHostel();
				hlHostel.setId(hostelBlocksTO.getHostelTO().getId());
				hlBlocks.setHlHostel(hlHostel);
			}
			hlBlocks.setCreatedBy(hostelBlocksTO.getCreatedBy());
			hlBlocks.setModifiedBy(hostelBlocksTO.getModifiedBy());
			hlBlocks.setCreatedDate(new Date());
			hlBlocks.setLastModifiedDate(new Date());
			hlBlocks.setIsActive(true);
		}
		log.info("Leaving from populateTOtoBO of HostelBlocksHelper");
		return hlBlocks;
	}
	
	public List<HostelBlocksTO> pupulateHlBlocksBOtoTO(List<HlBlocks> blocksList)throws Exception 
	{
		log.info("Entering into pupulateExamValuatorsBOtoTO of ExternalEvaluatorHelper");
		HostelBlocksTO hostelBlocksTO = null;
		HostelTO hostelTO = null;

		List<HostelBlocksTO> newBlocksList = new ArrayList<HostelBlocksTO>();
		if (blocksList != null && !blocksList.isEmpty()) {
			Iterator<HlBlocks> iterator = blocksList.iterator();
			while (iterator.hasNext()) {
				HlBlocks hlBlocks = iterator.next();
				hostelBlocksTO = new HostelBlocksTO();
				hostelTO = new HostelTO();
				if (hlBlocks.getId() != 0 && hlBlocks.getName() != null) {
					hostelBlocksTO.setId(hlBlocks.getId());
					hostelBlocksTO.setName(hlBlocks.getName());
					if (hlBlocks.getHlHostel()!= null) {
						hostelBlocksTO.setHostelName(hlBlocks.getHlHostel().getName());
					}
					hostelBlocksTO.setHostelTO(hostelTO);
					newBlocksList.add(hostelBlocksTO);
				}
			}
		}
		log.info("Leaving from pupulateExamValuatorsBOtoTO of ExternalEvaluatorHelper");
		return newBlocksList;
	}
	
	public HlBlocks populateTotoBoUpdate(HostelBlocksTO byTO) throws Exception
	{
		log.info("Entering into populateTotoBoUpdate of HostelBlocksHelper");
		HlHostel hlHostel = null;
		if (byTO != null) {
			HlBlocks hlBlocks = new HlBlocks();
			hlBlocks.setId(byTO.getId());
			hlBlocks.setName(byTO.getName());
			
			if (byTO.getHostelTO() != null) {
				hlHostel = new HlHostel();
				hlHostel.setId(byTO.getHostelTO().getId());
				hlBlocks.setHlHostel(hlHostel);
			}
			hlBlocks.setIsActive(true);
			hlBlocks.setLastModifiedDate(new Date());
			hlBlocks.setModifiedBy(byTO.getModifiedBy());
			if (hlBlocks != null) {
				return hlBlocks;
			}
		}
		log.info("Leaving from populateTotoBoUpdate of HostelBlocksHelper");
		return null;
	}
	
	public HostelBlocksTO populateBotoToEdit(HlBlocks hlBlocks)throws Exception 
	{
		log.info("Entering into populateBotoToEdit of HostelBlocksHelper");
		HostelBlocksTO hostelBlocksTO = new HostelBlocksTO();
		if (hlBlocks != null) {
			hostelBlocksTO.setId(hlBlocks.getId());
			if (hlBlocks.getName() != null && !hlBlocks.getName().isEmpty()) {
				hostelBlocksTO.setName(hlBlocks.getName());
			}
			HostelTO hostelTO = new HostelTO();
			if(hlBlocks.getHlHostel()!=null){
				if (hlBlocks.getHlHostel().getId()>0){
					hostelTO.setName(hlBlocks.getHlHostel().getName());
					hostelTO.setId(hlBlocks.getHlHostel().getId());
					hostelBlocksTO.setHostelTO(hostelTO);
				}
			}
		}
		if (hostelBlocksTO != null) {
			return hostelBlocksTO;
		}
		log.info("Leaving from populateBotoToEdit of HostelBlocksHelper");
		return null;
	}
	
}
