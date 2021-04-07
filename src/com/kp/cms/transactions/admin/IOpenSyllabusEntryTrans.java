package com.kp.cms.transactions.admin;


import java.util.List;

import com.kp.cms.bo.admin.OpenSyllabusEntry;
import com.kp.cms.forms.admin.OpenSyllabusEntryForm;

public interface IOpenSyllabusEntryTrans {

	boolean checkDuplicate(OpenSyllabusEntryForm openEntryForm, String mode)throws Exception;

	boolean save(OpenSyllabusEntry openSyllabusEntry)throws Exception;

	OpenSyllabusEntry getOpenSyllabusEntry(int id)throws Exception;

	boolean update(OpenSyllabusEntry openSyllabusEntry)throws Exception;

	List<OpenSyllabusEntry> getAllOpenSyllabusEntries()throws Exception;

}
