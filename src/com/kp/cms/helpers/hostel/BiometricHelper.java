package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.set.CompositeSet.SetMutator;

import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.hostel.BiometricBo;
import com.kp.cms.forms.employee.BiometricDetailsForm;
import com.kp.cms.forms.hostel.BiometricForm;
import com.kp.cms.to.employee.BiometricDetailsTO;

public class BiometricHelper {
	/**
	 * instance()
	 */
	public static volatile BiometricHelper biometricHelper = null;

	public static BiometricHelper getInstance() {
		if (biometricHelper == null) {
			biometricHelper = new BiometricHelper();
		}
		return biometricHelper;
	}
	/**
	 * copy Form to Bo
	 * @param availableSeatsForm
	 * @return
	 * @throws Exception
	 */
	public BiometricBo copyFormToBiometricBo(BiometricForm biometricForm) throws Exception{
		BiometricBo biometricBo=null;
		if(biometricForm!=null){
			biometricBo=new BiometricBo();
			HlHostel hlHostel=null;
			if(biometricForm.getHostelId()!=null && !biometricForm.getHostelId().isEmpty()){
				hlHostel=new HlHostel();
				hlHostel.setId(Integer.parseInt(biometricForm.getHostelId()));
			}
			biometricBo.setHlHostel(hlHostel);
			HlBlocks hlBlocks=null;
			if(biometricForm.getBlock()!=null && !biometricForm.getBlock().isEmpty()){
				hlBlocks=new HlBlocks();
				hlBlocks.setId(Integer.parseInt(biometricForm.getBlock()));
			}
			biometricBo.setHlBlock(hlBlocks);
			HlUnits hlUnits=null;
			if(biometricForm.getUnit()!=null && !biometricForm.getUnit().isEmpty()){
				hlUnits=new HlUnits();
				hlUnits.setId(Integer.parseInt(biometricForm.getUnit()));
			}
			biometricBo.setHlUnit(hlUnits);
			biometricBo.setMachineId(biometricForm.getMachineId());
			biometricBo.setMachineIp(biometricForm.getMachineIp());
			biometricBo.setMachineName(biometricForm.getMachineName());
			biometricBo.setCreatedBy(biometricForm.getUserId());
			biometricBo.setModifiedBy(biometricForm.getUserId());
			biometricBo.setCreatedDate(new Date());
			biometricBo.setLastModifiedDate(new Date());
			biometricBo.setIsActive(true);
			biometricBo.setWorklocation(biometricForm.getWorklocation());
			
		}
	return biometricBo;
	}
	/** convert boList to toList
	 * @param biometric
	 * @return
	 */
	public List<BiometricDetailsTO> convertBosToTOs(List<BiometricBo> biometric){
    	List<BiometricDetailsTO> biometricTOs=new ArrayList<BiometricDetailsTO>();
		Iterator itr=biometric.iterator();
		while(itr.hasNext()){
			BiometricBo biometricBo=(BiometricBo)itr.next();
			BiometricDetailsTO biometricTo=new BiometricDetailsTO();
			biometricTo.setId(biometricBo.getId());
			biometricTo.setMachineId(biometricBo.getMachineId());
			biometricTo.setMachineIp(biometricBo.getMachineIp());
			if(biometricBo.getMachineName()!=null)
			biometricTo.setMachinName(biometricBo.getMachineName());
			biometricTo.setHostelName(biometricBo.getHlHostel().getName());
			biometricTo.setBlockName(biometricBo.getHlBlock().getName());
			biometricTo.setUnitName(biometricBo.getHlUnit().getName());
			biometricTo.setWorkLocation(biometricBo.getWorklocation());
			biometricTOs.add(biometricTo);
		}
		return biometricTOs;
	}
	/**
	 * @param biometricDetailsForm
	 * @param biometric
	 */
	public void setBotoForm(BiometricForm biometricForm,BiometricBo biometricBo){
    	if(biometricBo!=null){
    		biometricForm.setId(biometricBo.getId());
    		biometricForm.setHostelId(String.valueOf(biometricBo.getHlHostel().getId()));
    		biometricForm.setBlock(String.valueOf(biometricBo.getHlBlock().getId()));
    		biometricForm.setUnit(String.valueOf(biometricBo.getHlUnit().getId()));
    		biometricForm.setMachineId(biometricBo.getMachineId());
    		biometricForm.setMachineIp(biometricBo.getMachineIp());
    		if(biometricBo.getMachineName()!=null){
    			biometricForm.setMachineName(biometricBo.getMachineName());
    		}
    		biometricForm.setFlag(true);
    		biometricForm.setWorklocation(biometricBo.getWorklocation());
    		
    	}
    }
	/**
	 * @param convert EditForm to BiometricBo
	 * @return
	 */
	public BiometricBo convertEditFormToBo(BiometricForm biometricForm){
		BiometricBo biometricBo=new BiometricBo();
		HlHostel hlHostel=null;
		if(biometricForm.getHostelId()!=null && !biometricForm.getHostelId().isEmpty()){
			hlHostel=new HlHostel();
			hlHostel.setId(Integer.parseInt(biometricForm.getHostelId()));
		}
		biometricBo.setHlHostel(hlHostel);
		HlBlocks hlBlocks=null;
		if(biometricForm.getBlock()!=null && !biometricForm.getBlock().isEmpty()){
			hlBlocks=new HlBlocks();
			hlBlocks.setId(Integer.parseInt(biometricForm.getBlock()));
		}
		biometricBo.setHlBlock(hlBlocks);
		HlUnits hlUnits=null;
		if(biometricForm.getUnit()!=null && !biometricForm.getUnit().isEmpty()){
			hlUnits=new HlUnits();
			hlUnits.setId(Integer.parseInt(biometricForm.getUnit()));
		}
		biometricBo.setHlUnit(hlUnits);
		biometricBo.setId(biometricForm.getId());
		biometricBo.setMachineId(biometricForm.getMachineId());
		biometricBo.setMachineIp(biometricForm.getMachineIp());
		biometricBo.setMachineName(biometricForm.getMachineName());
		biometricBo.setWorklocation(biometricForm.getWorklocation());
		biometricBo.setModifiedBy(biometricForm.getUserId());
		biometricBo.setLastModifiedDate(new Date());
		biometricBo.setIsActive(true);
		return biometricBo;
	}
}
