package com.kp.cms.helpers.hostel;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.hostel.UploadTheOfflineApplicationDetailsToSystemForm;
import com.kp.cms.to.hostel.UploadTheOfflineApplicationDetailsToSystemTo;

public class UploadTheOfflineApplicationDetailsToSystemHelper {
	private static final Log log = LogFactory.getLog(UploadTheOfflineApplicationDetailsToSystemHelper.class);
	private static volatile UploadTheOfflineApplicationDetailsToSystemHelper offlineAppDetailsTHelper = null;
	
	private UploadTheOfflineApplicationDetailsToSystemHelper() {
	}
	
	public static UploadTheOfflineApplicationDetailsToSystemHelper getInstance() {
		if (offlineAppDetailsTHelper == null) {
			offlineAppDetailsTHelper = new UploadTheOfflineApplicationDetailsToSystemHelper();
		}
		return offlineAppDetailsTHelper;
	}
	
	public Map<Integer,HostelOnlineApplication> covertToToBo(List<UploadTheOfflineApplicationDetailsToSystemTo> results,UploadTheOfflineApplicationDetailsToSystemForm objform)throws Exception{
		HostelOnlineApplication bo=null;
		Map<Integer, HostelOnlineApplication> map =new HashMap<Integer, HostelOnlineApplication>();
		Iterator<UploadTheOfflineApplicationDetailsToSystemTo> itr=results.iterator();
		while(itr.hasNext()) {
			bo=new HostelOnlineApplication();
			UploadTheOfflineApplicationDetailsToSystemTo to=itr.next();
				if(!map.containsKey(to.getStudentId()) ){
					Student student=new Student();
					student.setId(to.getStudentId());
					bo.setStudent(student);
					HlHostel hostel=new HlHostel();
					hostel.setId(Integer.parseInt(objform.getHostelId()));
					bo.setHlHostel(hostel);
					HlRoomType hlRoomType=new HlRoomType();
					hlRoomType.setId(to.getRoomTypeId());
					bo.setRoomType(hlRoomType);
					bo.setCreatedBy(objform.getUserId());
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(objform.getUserId());
					bo.setLastModifiedDate(new Date());
					bo.setIsActive(true);
					bo.setAcademicYear(Integer.parseInt(objform.getAcademicYear1()));
					bo.setApplicationNo(to.getApplicationNo());
					map.put(to.getStudentId(), bo);
				}
		}
		return map;
	}
	
	

}
