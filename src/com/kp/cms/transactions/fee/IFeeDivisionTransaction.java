package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
/**
 * 
 * Date 10/feb/2009 
 */
public interface IFeeDivisionTransaction {
	public List<FeeDivision> getFeeDivisions() throws Exception;
	public FeeDivision getFeeDivision(int divisionId) throws Exception;
	public void addFeeDivisionEntry(String feeDivisionname, String userName) throws Exception;
	public void reActivateFeeDivisionEntry(String feeDivisionname) throws Exception;
	public void deleteFeeDivisionEntry(int feeDivisionId) throws Exception;
	public void updateFeeDivisionEntry(int feeDivisionId, String feeDivisionName, String originalDivisionName, String userName) throws DuplicateException,ReActivateException,Exception;
}
