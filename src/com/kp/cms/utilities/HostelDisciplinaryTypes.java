package com.kp.cms.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.transactions.hostel.IHostelDisciplinaryDetailsTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelDisciplinaryDetailsTransactionImpl;


public class HostelDisciplinaryTypes extends TagSupport  {
	public static final Log log=LogFactory.getLog(HostelDisciplinaryTypes.class);
	
	IHostelDisciplinaryDetailsTransaction iDetailsTransaction=HostelDisciplinaryDetailsTransactionImpl.getInstance();
	List<HlDisciplinaryType> disciplineList=new ArrayList<HlDisciplinaryType>();
	HlDisciplinaryType disciplineType=null;
	public int doStartTag(){
		JspWriter out=pageContext.getOut();
		try {
			disciplineList=iDetailsTransaction.getHostelDisciplinesList();
			if(disciplineList!=null){
				Iterator<HlDisciplinaryType> disciplineIter=disciplineList.iterator();
				while(disciplineIter.hasNext()){
					disciplineType=new HlDisciplinaryType();
					disciplineType=disciplineIter.next();
					if(disciplineType!=null){
						if(disciplineType.getName()!=null){					
							out.println("<option value="+disciplineType.getId()+">"+disciplineType.getName()+"</option>");
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error occured in Disciplinary Types List---->"+e.getMessage());
		}
		return SKIP_BODY;
	}
	public int doEndTag(){
		return EVAL_PAGE;
	}

}
