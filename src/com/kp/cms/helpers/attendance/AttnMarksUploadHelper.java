package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.PucAttnInternalMarks;
import com.kp.cms.bo.admin.PucttnAttendance;
import com.kp.cms.to.attendance.AttnMarksUploadTo;
import com.kp.cms.to.attendance.PucAttnInternalMarksTo;
import com.kp.cms.to.attendance.PucttnAttendanceTo;

public class AttnMarksUploadHelper {
	private static volatile AttnMarksUploadHelper attnMarksUploadHelper = null;
	public static AttnMarksUploadHelper getInstance(){
		if(attnMarksUploadHelper == null){
			attnMarksUploadHelper = new AttnMarksUploadHelper();
			return attnMarksUploadHelper;
		}
		return attnMarksUploadHelper;
	}
	/**
	 * @param attnMarksUploadToList
	 * @return
	 * @throws Exception
	 */
	public List<AttnMarksUpload> populateTOToBO( List<AttnMarksUploadTo> attnMarksUploadToList)throws Exception {
		List<AttnMarksUpload> uploadsList=new ArrayList<AttnMarksUpload>();
		if(attnMarksUploadToList!=null){
			Iterator<AttnMarksUploadTo> iterator = attnMarksUploadToList.iterator();
			while (iterator.hasNext()) {
				AttnMarksUploadTo attnMarksUploadTo = (AttnMarksUploadTo) iterator .next();
				AttnMarksUpload attnMarksUpload = new AttnMarksUpload();
				if(attnMarksUploadTo.getRegNo()!=null && !attnMarksUploadTo.getRegNo().isEmpty()){
					attnMarksUpload.setRegNo(attnMarksUploadTo.getRegNo());
				}
				if(attnMarksUploadTo.getClasses()!=null & !attnMarksUploadTo.getClasses().isEmpty()){
					attnMarksUpload.setClasses(attnMarksUploadTo.getClasses());
				}
				if(attnMarksUploadTo.getTestIdent()!=null && !attnMarksUploadTo.getTestIdent().isEmpty()){
					attnMarksUpload.setTestIdent(attnMarksUploadTo.getTestIdent());
				}
				if(attnMarksUploadTo.getMrkLang()!=null && !attnMarksUploadTo.getMrkLang().isEmpty()){
					attnMarksUpload.setMrkLang(attnMarksUploadTo.getMrkLang());
				}
				if(attnMarksUploadTo.getMrkPrac1()!=null && !attnMarksUploadTo.getMrkPrac1().isEmpty()){
					attnMarksUpload.setMrkPrac1(attnMarksUploadTo.getMrkPrac1());
				}
				if(attnMarksUploadTo.getMrkPrac2()!=null && !attnMarksUploadTo.getMrkPrac2().isEmpty()){
					attnMarksUpload.setMrkPrac2(attnMarksUploadTo.getMrkPrac2());
				}
				if(attnMarksUploadTo.getMrkPrac3()!=null && !attnMarksUploadTo.getMrkPrac3().isEmpty()){
					attnMarksUpload.setMrkPrac3(attnMarksUploadTo.getMrkPrac3());
				}
				if(attnMarksUploadTo.getMrkPrac4()!=null && !attnMarksUploadTo.getMrkPrac4().isEmpty()){
					attnMarksUpload.setMrkPrac4(attnMarksUploadTo.getMrkPrac4());
				}
				if(attnMarksUploadTo.getMrkSub1()!=null && !attnMarksUploadTo.getMrkSub1().isEmpty()){
					attnMarksUpload.setMrkSub1(attnMarksUploadTo.getMrkSub1());
				}
				if(attnMarksUploadTo.getMrkSub2()!=null && !attnMarksUploadTo.getMrkSub2().isEmpty()){
					attnMarksUpload.setMrkSub2(attnMarksUploadTo.getMrkSub2());
				}
				if(attnMarksUploadTo.getMrkSub3()!=null && !attnMarksUploadTo.getMrkSub3().isEmpty()){
					attnMarksUpload.setMrkSub3(attnMarksUploadTo.getMrkSub3());
				}
				if(attnMarksUploadTo.getMrkSub4()!=null && !attnMarksUploadTo.getMrkSub4().isEmpty()){
					attnMarksUpload.setMrkSub4(attnMarksUploadTo.getMrkSub4());
				}
				if(attnMarksUploadTo.getMrkSub5()!=null && !attnMarksUploadTo.getMrkSub5().isEmpty()){
					attnMarksUpload.setMrkSub5(attnMarksUploadTo.getMrkSub5());
				}
				if(attnMarksUploadTo.getMrkSub6()!=null && !attnMarksUploadTo.getMrkSub6().isEmpty()){
					attnMarksUpload.setMrkSub6(attnMarksUploadTo.getMrkSub6());
				}
				if(attnMarksUploadTo.getMrkSub7()!=null && !attnMarksUploadTo.getMrkSub7().isEmpty()){
					attnMarksUpload.setMrkSub7(attnMarksUploadTo.getMrkSub7());
				}
				if(attnMarksUploadTo.getMrkSub8()!=null && !attnMarksUploadTo.getMrkSub8().isEmpty()){
					attnMarksUpload.setMrkSub8(attnMarksUploadTo.getMrkSub8());
				}
				if(attnMarksUploadTo.getMrkSub9()!=null && !attnMarksUploadTo.getMrkSub9().isEmpty()){
					attnMarksUpload.setMrkSub9(attnMarksUploadTo.getMrkSub9());
				}
				if(attnMarksUploadTo.getMrkSub10()!=null && !attnMarksUploadTo.getMrkSub10().isEmpty()){
					attnMarksUpload.setMrkSub10(attnMarksUploadTo.getMrkSub10());
				}
				if(attnMarksUploadTo.getUserCode()!=null && !attnMarksUploadTo.getUserCode().isEmpty()){
					attnMarksUpload.setUserCode(attnMarksUploadTo.getUserCode());
				}
				if(attnMarksUploadTo.getAcademicYear()!=null && !attnMarksUploadTo.getAcademicYear().isEmpty()){
					attnMarksUpload.setAcademicYear(attnMarksUploadTo.getAcademicYear());
				}
				uploadsList.add(attnMarksUpload);
			}
		}
		return uploadsList;
	}
	
	/**
	 * @param attnUploadToList
	 * @return
	 * @throws Exception
	 */
	public List<PucttnAttendance> convertTOToBO( List<PucttnAttendanceTo> attnUploadToList)throws Exception {
		List<PucttnAttendance> list = new ArrayList<PucttnAttendance>();
		if(attnUploadToList!=null){
			Iterator<PucttnAttendanceTo> iterator = attnUploadToList.iterator();
			while (iterator.hasNext()) {
				PucttnAttendanceTo pucttnAttendanceTo = (PucttnAttendanceTo) iterator .next();
				PucttnAttendance attendance = new PucttnAttendance();
				if(pucttnAttendanceTo.getRegNo()!=null && !pucttnAttendanceTo.getRegNo().isEmpty()){
					attendance.setRegNo(pucttnAttendanceTo.getRegNo());
				}
				if(pucttnAttendanceTo.getClasses()!=null && !pucttnAttendanceTo.getClasses().isEmpty()){
					attendance.setClasses(pucttnAttendanceTo.getClasses());
				}
				if(pucttnAttendanceTo.getAbsSub1()!=null && !pucttnAttendanceTo.getAbsSub1().isEmpty()){
					attendance.setAbsSub1(Integer.parseInt(pucttnAttendanceTo.getAbsSub1()));
				}
				if(pucttnAttendanceTo.getAbsSub2()!=null && !pucttnAttendanceTo.getAbsSub2().isEmpty()){
					attendance.setAbsSub2(Integer.parseInt(pucttnAttendanceTo.getAbsSub2()));
				}
				if(pucttnAttendanceTo.getAbsSub3()!=null && !pucttnAttendanceTo.getAbsSub3().isEmpty()){
					attendance.setAbsSub3(Integer.parseInt(pucttnAttendanceTo.getAbsSub3()));
				}
				if(pucttnAttendanceTo.getAbsSub4()!=null && !pucttnAttendanceTo.getAbsSub4().isEmpty()){
					attendance.setAbsSub4(Integer.parseInt(pucttnAttendanceTo.getAbsSub4()));
				}
				if(pucttnAttendanceTo.getAbsSub5()!=null && !pucttnAttendanceTo.getAbsSub5().isEmpty()){
					attendance.setAbsSub5(Integer.parseInt(pucttnAttendanceTo.getAbsSub5()));
				}
				if(pucttnAttendanceTo.getAbsSub6()!=null && !pucttnAttendanceTo.getAbsSub6().isEmpty()){
					attendance.setAbsSub6(Integer.parseInt(pucttnAttendanceTo.getAbsSub6()));
				}
				if(pucttnAttendanceTo.getAbsSub7()!=null && !pucttnAttendanceTo.getAbsSub7().isEmpty()){
					attendance.setAbsSub7(Integer.parseInt(pucttnAttendanceTo.getAbsSub7()));
				}
				if(pucttnAttendanceTo.getAbsSub8()!=null && !pucttnAttendanceTo.getAbsSub8().isEmpty()){
					attendance.setAbsSub8(Integer.parseInt(pucttnAttendanceTo.getAbsSub8()));
				}
				if(pucttnAttendanceTo.getAbsSub9()!=null && !pucttnAttendanceTo.getAbsSub9().isEmpty()){
					attendance.setAbsSub9(Integer.parseInt(pucttnAttendanceTo.getAbsSub9()));
				}
				if(pucttnAttendanceTo.getAbsSub10()!=null && !pucttnAttendanceTo.getAbsSub10().isEmpty()){
					attendance.setAbsSub10(Integer.parseInt(pucttnAttendanceTo.getAbsSub10()));
				}
				if(pucttnAttendanceTo.getAbsLang()!=null && !pucttnAttendanceTo.getAbsLang().isEmpty()){
					attendance.setAbsLang(Integer.parseInt(pucttnAttendanceTo.getAbsLang()));
				}
				if(pucttnAttendanceTo.getAbsPra1()!=null && !pucttnAttendanceTo.getAbsPra1().isEmpty()){
					attendance.setAbsPra1(Integer.parseInt(pucttnAttendanceTo.getAbsPra1()));
				}
				if(pucttnAttendanceTo.getAbsPra2()!=null && !pucttnAttendanceTo.getAbsPra2().isEmpty()){
					attendance.setAbsPra2(Integer.parseInt(pucttnAttendanceTo.getAbsPra2()));
				}
				if(pucttnAttendanceTo.getAbsPra3()!=null && !pucttnAttendanceTo.getAbsPra3().isEmpty()){
					attendance.setAbsPra3(Integer.parseInt(pucttnAttendanceTo.getAbsPra3()));
				}
				if(pucttnAttendanceTo.getAbsPra4()!=null && !pucttnAttendanceTo.getAbsPra4().isEmpty()){
					attendance.setAbsPra4(Integer.parseInt(pucttnAttendanceTo.getAbsPra4()));
				}
				if(pucttnAttendanceTo.getSplAchvmt()!=null && !pucttnAttendanceTo.getSplAchvmt().isEmpty()){
					attendance.setSplAchvmt(pucttnAttendanceTo.getSplAchvmt());
				}
				if(pucttnAttendanceTo.getPrnRemarks()!=null && !pucttnAttendanceTo.getPrnRemarks().isEmpty()){
					attendance.setPrnRemarks(pucttnAttendanceTo.getPrnRemarks());
				}
				if(pucttnAttendanceTo.getUserCode()!=null && !pucttnAttendanceTo.getUserCode().isEmpty()){
					attendance.setUserCode(pucttnAttendanceTo.getUserCode());
				}
				if(pucttnAttendanceTo.getLastUpdte()!=null && !pucttnAttendanceTo.getLastUpdte().toString().isEmpty()){
					attendance.setLastUpdte(pucttnAttendanceTo.getLastUpdte());
				}
				if(pucttnAttendanceTo.getAcademicYear()!=null && !pucttnAttendanceTo.getAcademicYear().isEmpty()){
					attendance.setAcademicYear(Integer.parseInt(pucttnAttendanceTo.getAcademicYear()));
				}
				list.add(attendance);
			}
		}
		return list;
	}
	
	/**
	 * @param attnInternalMarksToList
	 * @return
	 * @throws Exception
	 */
	public List<PucAttnInternalMarks> populateTOToBOForInternalMarks(List<PucAttnInternalMarksTo> attnInternalMarksToList)throws Exception {
		List<PucAttnInternalMarks> uploadsList=new ArrayList<PucAttnInternalMarks>();
		if(attnInternalMarksToList!=null){
			Iterator<PucAttnInternalMarksTo> iterator = attnInternalMarksToList.iterator();
			while (iterator.hasNext()) {
				PucAttnInternalMarksTo attnInternalMarksTo = (PucAttnInternalMarksTo) iterator .next();
				PucAttnInternalMarks attnInternalMarks = new PucAttnInternalMarks();
				if(attnInternalMarksTo.getRegNo()!=null && !attnInternalMarksTo.getRegNo().isEmpty()){
					attnInternalMarks.setRegNo(attnInternalMarksTo.getRegNo());
				}
				if(attnInternalMarksTo.getClasses()!=null & !attnInternalMarksTo.getClasses().isEmpty()){
					attnInternalMarks.setClasses(attnInternalMarksTo.getClasses());
				}
				if(attnInternalMarksTo.getTestIdent()!=null && !attnInternalMarksTo.getTestIdent().isEmpty()){
					attnInternalMarks.setTestIdent(attnInternalMarksTo.getTestIdent());
				}
				if(attnInternalMarksTo.getMrkLang()!=null && !attnInternalMarksTo.getMrkLang().isEmpty()){
					attnInternalMarks.setMrkLang(attnInternalMarksTo.getMrkLang());
				}
				if(attnInternalMarksTo.getMrkSub1()!=null && !attnInternalMarksTo.getMrkSub1().isEmpty()){
					attnInternalMarks.setMrkSub1(attnInternalMarksTo.getMrkSub1());
				}
				if(attnInternalMarksTo.getMrkSub2()!=null && !attnInternalMarksTo.getMrkSub2().isEmpty()){
					attnInternalMarks.setMrkSub2(attnInternalMarksTo.getMrkSub2());
				}
				if(attnInternalMarksTo.getMrkSub3()!=null && !attnInternalMarksTo.getMrkSub3().isEmpty()){
					attnInternalMarks.setMrkSub3(attnInternalMarksTo.getMrkSub3());
				}
				if(attnInternalMarksTo.getMrkSub4()!=null && !attnInternalMarksTo.getMrkSub4().isEmpty()){
					attnInternalMarks.setMrkSub4(attnInternalMarksTo.getMrkSub4());
				}
				if(attnInternalMarksTo.getMrkSub5()!=null && !attnInternalMarksTo.getMrkSub5().isEmpty()){
					attnInternalMarks.setMrkSub5(attnInternalMarksTo.getMrkSub5());
				}
				if(attnInternalMarksTo.getMrkSub6()!=null && !attnInternalMarksTo.getMrkSub6().isEmpty()){
					attnInternalMarks.setMrkSub6(attnInternalMarksTo.getMrkSub6());
				}
				if(attnInternalMarksTo.getMrkSub7()!=null && !attnInternalMarksTo.getMrkSub7().isEmpty()){
					attnInternalMarks.setMrkSub7(attnInternalMarksTo.getMrkSub7());
				}
				if(attnInternalMarksTo.getMrkSub8()!=null && !attnInternalMarksTo.getMrkSub8().isEmpty()){
					attnInternalMarks.setMrkSub8(attnInternalMarksTo.getMrkSub8());
				}
				if(attnInternalMarksTo.getMrkSub9()!=null && !attnInternalMarksTo.getMrkSub9().isEmpty()){
					attnInternalMarks.setMrkSub9(attnInternalMarksTo.getMrkSub9());
				}
				if(attnInternalMarksTo.getMrkSub10()!=null && !attnInternalMarksTo.getMrkSub10().isEmpty()){
					attnInternalMarks.setMrkSub10(attnInternalMarksTo.getMrkSub10());
				}
				if(attnInternalMarksTo.getUserCode()!=null && !attnInternalMarksTo.getUserCode().isEmpty()){
					attnInternalMarks.setUserCode(attnInternalMarksTo.getUserCode());
				}
				if(attnInternalMarksTo.getAcademicYear()!=null && !attnInternalMarksTo.getAcademicYear().isEmpty()){
					attnInternalMarks.setAcademicYear(attnInternalMarksTo.getAcademicYear());
				}
				uploadsList.add(attnInternalMarks);
			}
		}
		return uploadsList;
	}
}
