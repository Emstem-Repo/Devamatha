package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.forms.admin.SubReligionForm;

public interface ISubReligionTransaction {
	public boolean addSubReligion(ReligionSection religionSection, String mode) throws Exception;
	public List<ReligionSection> getSubReligion() throws Exception;
	public boolean deleteSubReligion(int relId, Boolean activate, SubReligionForm subReligionForm) throws Exception;
	public ReligionSection isSubReligionDuplicated(ReligionSection duplReligionSection) throws Exception;
	/**
	 * checks sub religion exists for that religion or not
	 * @param religionId
	 * @return
	 * @throws Exception
	 */
	public boolean checkSubReligionExists(int religionId) throws Exception;
}
