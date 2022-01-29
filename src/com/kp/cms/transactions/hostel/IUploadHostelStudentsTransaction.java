package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlRoomTransaction;

public interface IUploadHostelStudentsTransaction {
	Map<String, Integer> getAdmMap(String query) throws Exception;
	Map<Integer, Integer> getMaxCountForRoomType(String query) throws Exception;
	boolean addUploadData(List<HlRoomTransaction> results) throws Exception;
}
