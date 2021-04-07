package com.kp.cms.helpers.hostel;

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
import com.kp.cms.forms.hostel.ReadmissionSelectionUploadForm;
import com.kp.cms.to.hostel.UploadTheOfflineApplicationDetailsToSystemTo;

public class ReadmissionSelectionUploadHelper {
	private static final Log log = LogFactory.getLog(ReadmissionSelectionUploadHelper.class);
	private static volatile ReadmissionSelectionUploadHelper readmissionUploadHelper = null;
	
	private ReadmissionSelectionUploadHelper() {
	}
	
	/**
	 * @return
	 */
	public static ReadmissionSelectionUploadHelper getInstance() {
		if (readmissionUploadHelper == null) {
			readmissionUploadHelper = new ReadmissionSelectionUploadHelper();
		}
		return readmissionUploadHelper;
	}
	
	
	/**
	 * @param results
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,HostelOnlineApplication> covertToToBo(List<UploadTheOfflineApplicationDetailsToSystemTo> results,ReadmissionSelectionUploadForm objform)throws Exception{
		HostelOnlineApplication bo=null;
		log.info("entered into covertToToBo method");
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
					hlRoomType.setName(to.getRoomTypeName());
					bo.setSelectedRoomType(hlRoomType);
					map.put(to.getStudentId(), bo);
				}
		}
		return map;
	}

}
