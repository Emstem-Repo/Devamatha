package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.ApplnDoc;

public interface IDownloadHostelImagesTransaction {

	public int getImages(int year, int hostelId, int blockId, int unitId)throws Exception;

	public List<ApplnDoc> getImages(int year, int hostelId, int blockId, int unitId, int page, int pAGESIZE)throws Exception;

}
