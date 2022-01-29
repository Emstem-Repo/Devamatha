package com.kp.cms.transactions.hostel;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlVisitorInfo;
import java.util.List;

public interface IVisitorInfoTransaction
{

    public abstract List<HlRoomTransaction> getListOfAppDetails(String s)  throws Exception;

    public abstract boolean submitVisitorDetails(HlVisitorInfo hlvisitorinfo) throws Exception;
    public List<HlHostel> getHostelNames() throws Exception;
}
