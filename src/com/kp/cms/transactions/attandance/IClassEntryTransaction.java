package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;

/**
 * Interface for IClassEntryTransactionImpl
 */
public interface IClassEntryTransaction {
	
	public List<ClassSchemewise> getAllClass(int currentYear) throws Exception;

	public boolean addClass(Classes classes) throws Exception;

	public boolean deleteClass(Classes classes) throws Exception;

	public boolean actiavateClass(Classes classes) throws Exception;

	public boolean updateClass(Classes classes) throws Exception;

	public ClassSchemewise getClassById(int id) throws Exception;

	public List<ClassSchemewise> getClassesByIds() throws Exception;

	public List<ClassSchemewise> getClassesByClassName(String className,String year)throws Exception;

	public ClassSchemewise checkDuplicateThroughQuery(String duplicateQuery)throws Exception;
}
