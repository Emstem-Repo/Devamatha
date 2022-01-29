package com.kp.cms.handlers.reports;

import com.kp.cms.forms.reports.AddressReportForm;
import com.kp.cms.helpers.reports.AddressReportHelper;
import com.kp.cms.to.reports.StudentAddressTo;
import com.kp.cms.transactions.reports.IAddressTransaction;
import com.kp.cms.transactionsimpl.reports.AddressTransactionImpl;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddressReportHandler
{

	private static final Log log = LogFactory.getLog(AddressReportHandler.class);
    private static volatile AddressReportHandler addressReportHandler = null;

    private AddressReportHandler()
    {
    }

    public static AddressReportHandler getInstance()
    {
        if(addressReportHandler == null)
        {
            addressReportHandler = new AddressReportHandler();
        }
        return addressReportHandler;
    }

    public List<StudentAddressTo> getAddressDetails(AddressReportForm addressForm)throws Exception{
		log.info("Inside handler getAddressDetails");
		IAddressTransaction transaction=new AddressTransactionImpl();
		String dynamicQuery = AddressReportHelper.buildQuery(addressForm);
		List<Object[]> addressBo = transaction.getAddressDetails(dynamicQuery);
		List<StudentAddressTo> studentaddressList =AddressReportHelper.copyStudentAddressBOToTO(addressBo,addressForm);
		log.info("Leaving into getAddres Details of of AddressReportHandler");
		return  studentaddressList;
	}

    public List<StudentAddressTo> printAddressDetails(AddressReportForm addressForm)throws Exception{
		log.info("Inside handler getAddressDetails");
		IAddressTransaction transaction=new AddressTransactionImpl();
		String dynamicQuery = AddressReportHelper.buildQuery(addressForm);
			List<Object[]> addressBo = transaction.getAddressDetails(dynamicQuery);
				
			List<StudentAddressTo> studentaddressList =AddressReportHelper.copyStudentAddressBOToTOtoPrint(addressBo,addressForm);
			
			log.info("Leaving into getAddres Details of of AddressReportHandler");
			return  studentaddressList;
			
		
	
	}

    public void printAddressDetails(List<StudentAddressTo> list)throws Exception{
		log.info("Inside handler printAddressDetails");
				
			
		AddressReportHelper.printAddressReport(list);
			
			log.info("Leaving into getAddres Details of of AddressReportHandler");
		
			
		
	
	}

}
