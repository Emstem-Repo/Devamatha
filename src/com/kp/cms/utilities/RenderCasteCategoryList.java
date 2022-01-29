package com.kp.cms.utilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.to.admin.CasteTO;

/**
 * Renders CasteCategory List tag.
 */
public class RenderCasteCategoryList extends TagSupport {	
	/**
	 * Represents the log statement for the class.
	 */
	private Log log = LogFactory.getLog(RenderCasteCategoryList.class);

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() {
		CasteHandler casteHandler = CasteHandler.getInstance();
		List<CasteTO> casteList = casteHandler.getCastes();
		
		try {
				JspWriter out = pageContext.getOut();
					Iterator<CasteTO> iterator= casteList.iterator();
					while (iterator.hasNext()) {
						CasteTO casteTO = (CasteTO) iterator
								.next();
						out.println("<option value="+casteTO.getCasteId()+">"+casteTO.getCasteName()+"</option>");
					}
					
			} catch (IOException io) {
				log.error("Error while renderring Caste category list tag " ,io);
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
