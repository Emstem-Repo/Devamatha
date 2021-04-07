package com.kp.cms.handlers.admin;

/**
 * 
 * @author Date Created : 19 Jan 2009 
 */

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.Region;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.RegionCategoryForm;
import com.kp.cms.helpers.admin.RegionCategoryHelper;
import com.kp.cms.transactions.admin.IRegionCategoryTransaction;
import com.kp.cms.transactionsimpl.admin.RegionCategoryTransactionImpl;

public class RegionCategoryHandler {
	public static volatile RegionCategoryHandler regionCategoryHandler = null;

	public static RegionCategoryHandler getInstance() {
		if (regionCategoryHandler == null) {
			regionCategoryHandler = new RegionCategoryHandler();
			return regionCategoryHandler;
		}
		return regionCategoryHandler;
	}

	/**
	 * 
	 * @return list of regionCategoryTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 * @throws Exception
	 * @throws Exception
	 */
	public List getRegionCategory() throws Exception {
		IRegionCategoryTransaction iRegionCategoryTransaction = RegionCategoryTransactionImpl
				.getInstance();
		List<Region> regionCategoryList = iRegionCategoryTransaction
				.getRegionCategory();
		List regionCategoryTOList = RegionCategoryHelper.getInstance()
				.copyRegionCategoryBosToTos(regionCategoryList);
		return regionCategoryTOList;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */
	public boolean addRegionCategory(RegionCategoryForm regionCategoryForm,
			String mode) throws Exception {
		IRegionCategoryTransaction iRegionCategoryTransaction = RegionCategoryTransactionImpl
				.getInstance();
		boolean isAdded = false;
		
		Boolean originalNotChanged = false;

		String regionName = regionCategoryForm.getName();
		String origName = regionCategoryForm.getOrigName();

		if (regionName.equalsIgnoreCase(origName)) {
			originalNotChanged = true;
		}

		if ("Add".equalsIgnoreCase(mode)) {
			originalNotChanged = false; // for add no need to check original
										// changed
		}

		if (!originalNotChanged) {
			Region duplregion = RegionCategoryHelper.getInstance()
			.populateRegionCategoryDataFormForm(regionCategoryForm, mode);

			duplregion = iRegionCategoryTransaction.isRegionDuplcated(duplregion);
			if (duplregion != null
					&& duplregion.getIsActive()) {
				throw new DuplicateException();
			} else if (duplregion != null
					&& !duplregion.getIsActive()) {
				regionCategoryForm.setRegionId(Integer.toString(duplregion.getId()));
				throw new ReActivateException();
			}
		}

		 Region region = RegionCategoryHelper.getInstance()
				.populateRegionCategoryDataFormForm(regionCategoryForm, mode);
	
		if ("Add".equalsIgnoreCase(mode)) {
			region.setCreatedDate(new Date());
			region.setLastModifiedDate(new Date());
		} else // edit
		{
			region.setLastModifiedDate(new Date());

		}

		isAdded = iRegionCategoryTransaction.addRegionCategory(region, mode);
		return isAdded;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */

	public boolean deleteRegion(int id, Boolean activate) throws Exception {
		IRegionCategoryTransaction iRegionCategoryTransaction = RegionCategoryTransactionImpl
				.getInstance();
		 return iRegionCategoryTransaction.deleteRegion(id, activate);
	}

}
