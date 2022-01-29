package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.transactions.hostel.IHostelAllocationTransactions;
import com.kp.cms.transactionsimpl.fee.InstallmentPaymentTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelAllocationTransactionImpl;

public class RenderHostelNames extends TagSupport {
	private static final Log log = LogFactory.getLog(RenderHostelNames.class);
	
	private String name="false";
	
	
	public String getName() {
	return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**Method to render all the hostel names
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public int doStartTag() {
			
		/*InputStream propStream=RenderHostelNames.class.getResourceAsStream("/resources/application.properties");
			Properties prop= new Properties();*/
			 try {
				 IHostelAllocationTransactions hostelAllocationTraxn = HostelAllocationTransactionImpl.getInstance();
			
			List<HlHostel> hostelList = hostelAllocationTraxn.getHostelNames();
			
	        JspWriter out = pageContext.getOut();
	        if(hostelList!=null){
	        Iterator<HlHostel> iterator = hostelList.iterator();
		        while (iterator.hasNext()) {
		        	HlHostel hostel = iterator.next();
					String hostelName = "";
					if(hostel.getName()!=null){
						hostelName =hostel.getName();
					}
						out.println("<option value="+hostel.getId()+">"+hostelName+"</option>");
						 //out.println("<option value="+hostelName+"></option>");
					
				}
	        }
	    
		  } catch (Exception e) {
			  log.error("Error occured at RenderYearList" + e.getMessage());
	    }
		 return SKIP_BODY;
	}
	/**
	 * doEndTag is called by the JSP container when the tag is closed
	 */
		public int doEndTag(){
		  
		return EVAL_PAGE;
		}
}
