package com.kp.cms.utilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.to.admin.CountryTO;

public class RenderCountryListTag extends TagSupport {
	
	/**
	 * Represents the log statement for the class.
	 */
	private Log log = LogFactory.getLog(RenderCasteCategoryList.class);

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() {
		CountryHandler countryHandler = CountryHandler.getInstance();
		List<CountryTO> countryList = countryHandler.getCountries();
		
		
		try {
				JspWriter out = pageContext.getOut();
					Iterator<CountryTO> iterator= countryList.iterator();
					while (iterator.hasNext()) {
						CountryTO countryTO = (CountryTO) iterator
								.next();
						out.println("<option value="+countryTO.getId()+">"+countryTO.getName()+"</option>");
					}
					
			} catch (IOException io) {
				log.error("Error while renderring Country list tag " ,io);
			}
		return SKIP_BODY;

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	public int doEndTag() {
		return EVAL_PAGE;
	}


}
