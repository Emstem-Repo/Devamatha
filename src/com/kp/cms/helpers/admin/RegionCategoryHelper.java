package com.kp.cms.helpers.admin;
/**
 * 
 * @author Date Created : 19 Jan 2009 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Region;
import com.kp.cms.forms.admin.RegionCategoryForm;
import com.kp.cms.to.admin.RegionTO;

public class RegionCategoryHelper {
	public static volatile RegionCategoryHelper regionCategoryHelper = null;

	public static RegionCategoryHelper getInstance() {
		if (regionCategoryHelper == null) {
			regionCategoryHelper = new RegionCategoryHelper();
			return regionCategoryHelper;
		}
		return regionCategoryHelper;
	}
	
	public List copyRegionCategoryBosToTos(List regionCategoryList) {
		List regionCategoryTOList = new ArrayList();
		Iterator iterator = regionCategoryList.iterator();
		Region region;
		RegionTO regionCategoryTO;
		while (iterator.hasNext()) {
			regionCategoryTO = new RegionTO();
			region = (Region) iterator.next();
			regionCategoryTO.setId(region.getId());
			regionCategoryTO.setName(region.getName());
			regionCategoryTOList.add(regionCategoryTO);
		}
		return regionCategoryTOList;
	}

	
	
	/**
	 * 
	 * @param  RegionCategory creates BO from RegionCategoryForm 
	 *            
	 * @return Region BO object
	 */

	public Region populateRegionCategoryDataFormForm(
			RegionCategoryForm regionCategoryForm, String mode ) throws Exception {
		Region region = new Region();
		region.setName(regionCategoryForm.getName());
		region.setIsActive(true);
		if (!mode.equalsIgnoreCase("Add")) {
			region.setId(Integer.parseInt(regionCategoryForm.getRegionId()));
		}
		return region;
	}
	
	
}
