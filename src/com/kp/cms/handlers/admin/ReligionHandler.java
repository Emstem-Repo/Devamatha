package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.helpers.admin.ReligionHelper;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.transactions.admin.IReligionTransaction;
import com.kp.cms.transactionsimpl.admin.ReligionTransactionImpl;

/**
 * Manages the operations of add, edit, delete of Religion.
 * @author
 * 
 */
public class ReligionHandler {
	private static volatile ReligionHandler religionHandler = null;

	public static ReligionHandler getInstance() {
		if (religionHandler == null) {
			religionHandler = new ReligionHandler();
		}
		return religionHandler;
	}

	/**
	 * Add Religion to the database.
	 * @param programTypeName - Religion Name to be added.
	 * @return - true for successful addition, false otherwise.
	 */
	public boolean addReligion(String religionName) throws Exception {
		IReligionTransaction religionImpl = new ReligionTransactionImpl();
		boolean isreligionadded = false;
		Religion religion=ReligionHelper.getInstance().createReligionObject(0,religionName,"add");
		if (religion != null) {
			isreligionadded = religionImpl.addReligion(religion);
		}
		return isreligionadded;
	}

	/**
	 * Get all Religion list from the database.
	 * @return List - Religion transaction List object
	 */
	public List<ReligionTO> getReligion() {
		IReligionTransaction religionIntf = new ReligionTransactionImpl();
		List<Religion> programBoList = religionIntf.getReligion();
		List<ReligionTO> getProgramList = ReligionHelper
				.convertBOstoTos(programBoList);
		return getProgramList;
	}

	/**
	 * Updates the Religion.
	 * @param programTypeId - Religion Id to be edited.
	 * @param programTypeName - Religion Name to be edited.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean editReligion(int religionId, String religionName)throws Exception {
		IReligionTransaction religionImpl = new ReligionTransactionImpl();
		boolean isProgramEdited = false;
		Religion religion=ReligionHelper.getInstance().createReligionObject(religionId,religionName,"edit");
		if (religion != null) {
			isProgramEdited = religionImpl.editReligion(religion);
		}
		return isProgramEdited;
	}

	/**
	 * Deletes the Religion from the database.
	 * @param programTypeId - Religion Id to be deleted.
	 * @return - true, if the Religion is deleted successfully, false otherwise.
	 */
	public boolean deleteReligion(int religionId,String religionName)throws Exception {
		IReligionTransaction religionImpl = new ReligionTransactionImpl();
		Religion religion=ReligionHelper.getInstance().createReligionObject(religionId,religionName,"delete");
		boolean isProgramDeleted = false;
		if (religion != null) {
			isProgramDeleted = religionImpl.editReligion(religion);
		}
		return isProgramDeleted;
	}
	/**
	 * Searching new religion in database .
	 * @param religion name - Religion Id to be deleted.
	 * @return - true, if the Religion is already there, false otherwise.
	 */
	public boolean existanceCheck(String name) throws Exception
	{
		IReligionTransaction religionImpl = new ReligionTransactionImpl();
		boolean isExisting = false;
		Religion religion=ReligionHelper.getInstance().createReligionObject(0,name,"");
		if (religion != null) {
			isExisting = religionImpl.existanceCheck(religion);
		}
		return isExisting;
	}
	
	public List<ReligionSectionTO> getReligionSection() throws Exception {
		IReligionTransaction religionIntf = new ReligionTransactionImpl();
		List<ReligionSection> programBoList = religionIntf.getReligionSection();
		List<ReligionSectionTO> getProgramList = ReligionHelper
				.getReligionSectionTos(programBoList);
		return getProgramList;
	}
}
