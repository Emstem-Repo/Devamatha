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
import com.kp.cms.transactionsimpl.fee.InstallmentPaymentTransactionImpl;

public class RenderFinancialYearForFeePaymentList extends TagSupport{
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
			InputStream propStream=RenderFinancialYearList.class.getResourceAsStream("/resources/application.properties");
			Properties prop= new Properties();
			 try {
				/* final Calendar cal = new GregorianCalendar();
				 final int year = cal.get(Calendar.YEAR);   	 
			prop.load(propStream);
			int yearMaxRange=Integer.parseInt(prop.getProperty(CMSConstants.YEAR_MAXRANGE_KEY));
			int yearMinRange=Integer.parseInt(prop.getProperty(CMSConstants.YEAR_MINRANGE_KEY));*/
			
			InstallmentPaymentTransactionImpl impl = new InstallmentPaymentTransactionImpl();
			List<FeeFinancialYear> yearList = impl.getAllFinancialYear();
			
	        JspWriter out = pageContext.getOut();
	        if(yearList!=null){
	        Iterator<FeeFinancialYear> iterator = yearList.iterator();
		        while (iterator.hasNext()) {
					FeeFinancialYear feeFinancialYear = iterator.next();
					String financialYear = "";
					if(feeFinancialYear.getYear()!=null){
						financialYear = feeFinancialYear.getYear();
					}
//					if(feeFinancialYear.getIsCurrent()!=null && feeFinancialYear.getIsCurrent()){
//						out.println("<option value="+feeFinancialYear.getId()+" >"+financialYear+"</option>");
//					}
//					else{
						 out.println("<option value="+feeFinancialYear.getId()+">"+financialYear+"</option>");
//					}
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
