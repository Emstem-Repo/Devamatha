package com.kp.cms.helpers.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.fee.SlipRegisterForm;
import com.kp.cms.to.fee.SlipRegisterTO;

public class SlipRegisterHelper {
	private static final Log log = LogFactory.getLog(SlipRegisterHelper.class);
	
	
	public static String buildQuery(SlipRegisterForm slipRegisterForm) {
	log.info("entered buildQuery..");
		String statusCriteria = commonSearch(slipRegisterForm);
		String selectQuery = queryBuildForSelect(slipRegisterForm);
		String searchCriteria= "";

		//		String ednJoin = "";
		searchCriteria = "select students.registerNo, "+selectQuery +
		" from AdmAppln admAppln inner join admAppln.students students "
			+ "where" + statusCriteria;					
		log.info("exit buildQuery..");
		return searchCriteria;
}


	private static String queryBuildForSelect(SlipRegisterForm slipRegisterForm) {
		return null;
	}


	private static String commonSearch(SlipRegisterForm slipRegisterForm) {
		return null;
	}


	public static List<SlipRegisterTO> copyStudentAddressBOToTO(List<Object[]> slipRegisterBo) {
		
		
		
		
		
		return null;
	}
	
	

}
