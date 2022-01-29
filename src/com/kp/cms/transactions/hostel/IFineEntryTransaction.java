package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;
import com.kp.cms.bo.hostel.FineEntryBo;
import com.kp.cms.forms.hostel.FineEntryForm;

public interface IFineEntryTransaction {
public Map<String, String> getFineCategoryList()throws Exception;
public boolean addFineEntry(FineEntryBo fineEntryBo,String add)throws Exception;
public List<FineEntryBo> getFineEntry()throws Exception;
public boolean deleteFineEntry(int id)throws Exception;
public FineEntryBo getFineEntryById(int id)throws Exception;
public boolean updateWhenPaidTheFine(int id,String paid)throws Exception;
public FineEntryBo getLastFineEntryBo(FineEntryForm fineEntryForm,String add)throws Exception;
public List<FineEntryBo> getSearchFineEntryListForRegNo(FineEntryForm fineEntryForm)throws Exception;
}
