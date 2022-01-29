package com.kp.cms.helpers.employee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadEmpAttendanceHelper {
	/**
	 * Singleton object of UploadEmpAttendanceHelper
	 */
	private static volatile UploadEmpAttendanceHelper uploadEmpAttendanceHelper = null;
	private static final Log log = LogFactory.getLog(UploadEmpAttendanceHelper.class);
	private UploadEmpAttendanceHelper() {
		
	}
	/**
	 * return singleton object of UploadEmpAttendanceHelper.
	 * @return
	 */
	public static UploadEmpAttendanceHelper getInstance() {
		if (uploadEmpAttendanceHelper == null) {
			uploadEmpAttendanceHelper = new UploadEmpAttendanceHelper();
		}
		return uploadEmpAttendanceHelper;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> convertBotoMap(List list) throws Exception{
		Map<Integer, String> map=new HashMap<Integer, String>();
		if(list!=null && !list.isEmpty()){
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				Object[] obj= (Object[]) itr.next();
				int typeId=0;
				if(obj[2]!=null && !obj[2].toString().trim().isEmpty())
					typeId=Integer.parseInt(obj[2].toString());
				if(!obj[0].toString().trim().isEmpty() && StringUtils.isNumeric(obj[0].toString()))
				map.put(Integer.parseInt(obj[0].toString()),obj[1].toString()+"_"+typeId);
			}
		}
		return map;
	}
}
