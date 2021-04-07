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
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.transactionsimpl.fee.InstallmentPaymentTransactionImpl;

public class RenderPCFinancialYearList extends TagSupport {
	private static final Log log = LogFactory.getLog(RenderFinancialYearList.class);
	private String normalYear="false";
	
	public String getNormalYear() {
		return normalYear;
	}
	public void setNormalYear(String normalYear) {
		this.normalYear = normalYear;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public int doStartTag() {
			/*InputStream propStream=RenderFinancialYearList.class.getResourceAsStream("/resources/application.properties");
			Properties prop= new Properties();*/
			 try {
			InstallmentPaymentTransactionImpl impl = new InstallmentPaymentTransactionImpl();
			List<PcFinancialYear> yearList = impl.getAllPCFinancialYear();
			
	        JspWriter out = pageContext.getOut();
	        if(yearList!=null){
	        Iterator<PcFinancialYear> iterator = yearList.iterator();
		        while (iterator.hasNext()) {
		        	PcFinancialYear pcFinancialYear = iterator.next();
					String financialYear = "";
					if(pcFinancialYear.getFinancialYear()!=null){
						financialYear = pcFinancialYear.getFinancialYear();
					}
					if(pcFinancialYear.getIsCurrent()!=null && pcFinancialYear.getIsCurrent()){
						out.println("<option value="+pcFinancialYear.getId()+" selected='selected'>"+financialYear+"</option>");
					}
					else{
						 out.println("<option value="+pcFinancialYear.getId()+">"+financialYear+"</option>");
					}
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
