package com.kp.cms.handlers.hostel;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlVisitorInfo;
import com.kp.cms.forms.hostel.VisitorInfoForm;
import com.kp.cms.helpers.hostel.HostelCheckinHelper;
import com.kp.cms.helpers.hostel.VisitorInfoHelper;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.VisitorInfoTO;
import com.kp.cms.transactions.hostel.IVisitorInfoTransaction;
import com.kp.cms.transactionsimpl.hostel.VisitorInfoTransactionImpl;

public class VisitorInfoHandler
{

    private static volatile VisitorInfoHandler visitorInfoHandler = null;
    private static final Log log = LogFactory.getLog(VisitorInfoHandler.class);
    VisitorInfoHelper helper= VisitorInfoHelper.getInstance();
    IVisitorInfoTransaction transaction= new VisitorInfoTransactionImpl();

    public static VisitorInfoHandler getInstance()
    {
        if(visitorInfoHandler == null)
        {
            visitorInfoHandler = new VisitorInfoHandler();
        }
        return visitorInfoHandler;
    }

    public List<VisitorInfoTO> getHostelerDetails(VisitorInfoForm visitorInfoForm)
        throws Exception
    {
        String query = "";
        List<HlRoomTransaction> appList = null;
        Properties prop = new Properties();
        InputStream in = VisitorInfoHandler.class.getClassLoader().getResourceAsStream("resources/application.properties");
        prop.load(in);
        String roomStatusId = prop.getProperty("knowledgepro.hostel.visitor.room.CheckInstatus");
        if(roomStatusId != null)
        {
            if(visitorInfoForm.getVisitorFor().equals("1"))
            {
                query = helper.getSearchQuery(visitorInfoForm, roomStatusId);
            } else
            if(visitorInfoForm.getVisitorFor().equals("2"))
            {
                query = helper.getSearchQueryForEmployee(visitorInfoForm, roomStatusId);
            }
            if(query != null)
            {
                appList = transaction.getListOfAppDetails(query);
            }
        }
        List<VisitorInfoTO> detailList = null;
        if(visitorInfoForm.getVisitorFor().equals("1"))
        {
            detailList = helper.convertListOfBOtoTO(appList);
        } else
        if(visitorInfoForm.getVisitorFor().equals("2"))
        {
            detailList = helper.convertListOfBOtoTOForEmployee(appList);
        }
        return detailList;
    }

    public boolean submitVisitorDetails(VisitorInfoForm visitorInfoForm)
        throws Exception
    {
        HlVisitorInfo hlVisitorInfo = VisitorInfoHelper.getInstance().getBOFromForm(visitorInfoForm);
        boolean isAdded = false;
        IVisitorInfoTransaction transaction = new VisitorInfoTransactionImpl();
        if(hlVisitorInfo != null)
        {
            isAdded = transaction.submitVisitorDetails(hlVisitorInfo);
        }
        return isAdded;
    }
    
    public List<HostelTO> getHostelDetails() throws Exception {
		log.info("Entering getHostelDetails of HostelCheckinHandler");

		List<HlHostel> hostelList = transaction.getHostelNames();
		List<HostelTO> hostelTOList = HostelCheckinHelper.getInstance()
				.copyHostelBosToTos(hostelList);
		log.info("Exitinging getHostelDetails of HostelCheckinHandler");
		return hostelTOList;
	}

}
