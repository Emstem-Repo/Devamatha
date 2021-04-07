package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Region;

public interface IRegionCategoryTransaction {
	public List<Region> getRegionCategory() throws Exception;
	public boolean addRegionCategory(Region region, String mode)throws Exception;
	public boolean deleteRegion(int regionId, Boolean activate) throws Exception;
	public Region isRegionDuplcated(Region oldRegion) throws Exception;
}
