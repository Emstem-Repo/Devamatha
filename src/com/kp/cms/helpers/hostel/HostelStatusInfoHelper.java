package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.forms.hostel.HostelStatusInfoForm;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HlBedsTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;


public class HostelStatusInfoHelper {
	private static volatile HostelStatusInfoHelper hostelStatusInfoHelper = null;

	public static HostelStatusInfoHelper getInstance() throws Exception {
		if (hostelStatusInfoHelper == null) {
			hostelStatusInfoHelper = new HostelStatusInfoHelper();
		}
		return hostelStatusInfoHelper;
	}

	public HostelStatusInfoHelper() {

	}

	public List<RoomTypeTO> convertBOtoToList(List<HlRoomType> roomType,HostelStatusInfoForm hostelStatusInfoForm) throws Exception {
		List<RoomTypeTO> list=new ArrayList<RoomTypeTO>();
		String[] room= hostelStatusInfoForm.getRoomType();
		List<Integer> roomTypeId=new ArrayList<Integer>();
		boolean roomTypeSelectecd=false;
		if(room!=null && room.length>0){
			roomTypeSelectecd=true;
			for(int i=0;i<room.length;i++){
				 roomTypeId.add(Integer.parseInt(room[i]));
			}
		}
		if(!roomType.isEmpty()){
			Iterator<HlRoomType> itr=roomType.iterator();
			while (itr.hasNext()) {
				HlRoomType hlRoomType = (HlRoomType) itr.next();
				RoomTypeTO roomTypeTO=new RoomTypeTO();
				roomTypeTO.setId(Integer.toString(hlRoomType.getId()));
				roomTypeTO.setName(hlRoomType.getName());
				roomTypeTO.setMaxCount(hlRoomType.getNoOfOccupants());
				if(roomTypeSelectecd){
					if(roomTypeId.contains(hlRoomType.getId())){
						list.add(roomTypeTO);
					}
				}else{
					list.add(roomTypeTO);
				}
			}
		}
		return list;
	}
	/**
	 * @author venkat
	 * @param admMap
	 * @param hlBedList
	 * @return Map<String, Map<String, List<HlAdmissionTo>>>
	 */
	public Map<String, Map<String, Map<Integer,List<HlAdmissionTo>>>> checkHostelDetails(Map<String,HlAdmissionBo> admMap,List<HlBeds> hlBedList)
	{
		Map<String, Map<String, Map<Integer,List<HlAdmissionTo>>>> hlAdmissionMap=new TreeMap<String, Map<String, Map<Integer,List<HlAdmissionTo>>>>();
		
	   	if((admMap!=null && !admMap.isEmpty()) || (hlBedList!=null && !hlBedList.isEmpty()))
	   	{
		for(HlBeds hlBeds : hlBedList) {
			if(admMap.containsKey(String.valueOf(hlBeds.getId()))){      //filled or not 
				HlAdmissionBo admissionBo = admMap.get(String.valueOf(hlBeds.getId()));
				//HlAdmissionBo hlAdmissionBo = admMap.get(hlBeds.getId());
        		  if(hlAdmissionMap.containsKey(admissionBo.getRoomId().getHlBlock().getName())){   //block 
        			  Map<String,Map<Integer,List<HlAdmissionTo>>> toListMap = hlAdmissionMap.get(admissionBo.getRoomId().getHlBlock().getName());
      				if(toListMap.containsKey(admissionBo.getRoomId().getHlUnit().getName())){
      					//Get List
      					 Map<Integer,List<HlAdmissionTo>> toMap = toListMap.get(hlBeds.getHlRoom().getHlUnit().getName());
	      					//Add To to List 
						 List<HlAdmissionTo> admToList;
						 HlAdmissionTo hlAdmissionTo;
						 if(toMap.containsKey(Integer.parseInt(hlBeds.getHlRoom().getFloorNo()))){
							 admToList = toMap.get(Integer.parseInt(hlBeds.getHlRoom().getFloorNo()));
							 hlAdmissionTo=processUnavailableBeds(admissionBo);
							 admToList.add(hlAdmissionTo);
						 }else{
							 admToList=new ArrayList<HlAdmissionTo>();
							 hlAdmissionTo=processUnavailableBeds(admissionBo);
		      			     admToList.add(hlAdmissionTo);
						 }
						 Collections.sort(admToList);
	      					//Add List To Map
						 toMap.put(Integer.parseInt(admissionBo.getRoomId().getFloorNo()), admToList);
						 toListMap.put(admissionBo.getRoomId().getHlUnit().getName(), toMap);
      					
      				}else{
      					Map<Integer,List<HlAdmissionTo>> toMap = new TreeMap<Integer, List<HlAdmissionTo>>();
      					List<HlAdmissionTo> admToList=new ArrayList<HlAdmissionTo>();
      					//Add To to list
      					HlAdmissionTo hlAdmissionTo=processUnavailableBeds(admissionBo);;
      					admToList.add(hlAdmissionTo);
      					Collections.sort(admToList);
      					toMap.put(Integer.parseInt(admissionBo.getRoomId().getFloorNo()), admToList);
      					toListMap.put(admissionBo.getRoomId().getHlUnit().getName(), toMap);
      				}
      				hlAdmissionMap.put(admissionBo.getRoomId().getHlBlock().getName(), toListMap);
      			  }
      			  else{
      				Map<String,Map<Integer,List<HlAdmissionTo>>> toListMap = new TreeMap<String, Map<Integer,List<HlAdmissionTo>>>();
      				Map<Integer, List<HlAdmissionTo>> toMap=new HashMap<Integer, List<HlAdmissionTo>>();
      				List<HlAdmissionTo> admToList=new ArrayList<HlAdmissionTo>();
      				HlAdmissionTo hlAdmissionTo=processUnavailableBeds(admissionBo);;
      				admToList.add(hlAdmissionTo);
      				toMap.put(Integer.parseInt(admissionBo.getRoomId().getFloorNo()), admToList);
      				toListMap.put(admissionBo.getRoomId().getHlUnit().getName(), toMap);
      				hlAdmissionMap.put(admissionBo.getRoomId().getHlBlock().getName(), toListMap);
      			  }
        		  
			}else{
				 if(hlAdmissionMap.containsKey(hlBeds.getHlRoom().getHlBlock().getName())){  //
					 Map<String,Map<Integer,List<HlAdmissionTo>>> toListMap = hlAdmissionMap.get(hlBeds.getHlRoom().getHlBlock().getName());
					 if(toListMap.containsKey(hlBeds.getHlRoom().getHlUnit().getName())){    //
	      					//Get List
						 Map<Integer,List<HlAdmissionTo>> toMap = toListMap.get(hlBeds.getHlRoom().getHlUnit().getName());
	      					//Add To to List 
						 List<HlAdmissionTo> admToList;
						 if(toMap.containsKey(Integer.parseInt(hlBeds.getHlRoom().getFloorNo()))){
							 admToList = toMap.get(Integer.parseInt(hlBeds.getHlRoom().getFloorNo()));
							 HlAdmissionTo hlAdmissionTo=processAvailableBeds(hlBeds);
							 admToList.add(hlAdmissionTo);
							 
						 }else{
							 admToList=new ArrayList<HlAdmissionTo>();
							 HlAdmissionTo hlAdmissionTo=processAvailableBeds(hlBeds);
		      			     admToList.add(hlAdmissionTo);
						 }
						 Collections.sort(admToList);
	      					//Add List To Map
						 toMap.put(Integer.parseInt(hlBeds.getHlRoom().getFloorNo()), admToList);
						 toListMap.put(hlBeds.getHlRoom().getHlUnit().getName(), toMap);
	      				}else{
	      					Map<Integer,List<HlAdmissionTo>> toMap = new TreeMap<Integer, List<HlAdmissionTo>>();
	      					List<HlAdmissionTo> admToList=new ArrayList<HlAdmissionTo>();
	      					//Add To to list
	      					HlAdmissionTo hlAdmissionTo=processAvailableBeds(hlBeds);
	      					admToList.add(hlAdmissionTo);
	      					Collections.sort(admToList);
	      					toMap.put(Integer.parseInt(hlBeds.getHlRoom().getFloorNo()), admToList);
	      					toListMap.put(hlBeds.getHlRoom().getHlUnit().getName(), toMap);
	      				}
	      				hlAdmissionMap.put(hlBeds.getHlRoom().getHlBlock().getName(), toListMap);
				 }else{
					 Map<String,Map<Integer,List<HlAdmissionTo>>> toListMap = new TreeMap<String, Map<Integer,List<HlAdmissionTo>>>();
				     Map<Integer, List<HlAdmissionTo>> toMap=new TreeMap<Integer, List<HlAdmissionTo>>();
		  			 List<HlAdmissionTo> admToList=new ArrayList<HlAdmissionTo>();
		  			 HlAdmissionTo hlAdmissionTo=processAvailableBeds(hlBeds);
		  			 admToList.add(hlAdmissionTo);
		  			 toMap.put(Integer.parseInt(hlBeds.getHlRoom().getFloorNo()), admToList);
		  			 toListMap.put(hlBeds.getHlRoom().getHlUnit().getName(), toMap);
		  			 hlAdmissionMap.put(hlBeds.getHlRoom().getHlBlock().getName(), toListMap);
				 }
			}
		}
	   	}
	   	
		return hlAdmissionMap;
	}
	/**
	 * @author venkat
	 * @param hlBeds
	 * @param isAdmitted
	 * @return HlAdmissionTo
	 */
	public HlAdmissionTo processAvailableBeds(HlBeds hlBeds){
		HlAdmissionTo hlAdmissionTo =new HlAdmissionTo();
		hlAdmissionTo.setBedNo(hlBeds.getBedNo());
		hlAdmissionTo.setCheckingStudent(false);
		if(hlBeds.getHlRoom()!=null)
		   hlAdmissionTo.setRoomName(hlBeds.getHlRoom().getName());
		if(hlBeds.getHlRoom().getHlRoomType().getName()!=null){
			hlAdmissionTo.setRoomTypeName(hlBeds.getHlRoom().getHlRoomType().getName());
		}
		return hlAdmissionTo;
	}
	public HlAdmissionTo processUnavailableBeds(HlAdmissionBo admissionBo){
		HlAdmissionTo hlAdmissionTo=new HlAdmissionTo();
		hlAdmissionTo.setBedNo(admissionBo.getBedId().getBedNo());
		hlAdmissionTo.setCheckingStudent(true);
		hlAdmissionTo.setRoomName(admissionBo.getRoomId().getName());
		hlAdmissionTo.setRegNo(admissionBo.getStudentId().getRegisterNo());
		if(admissionBo.getRoomTypeId().getName()!=null)
			 hlAdmissionTo.setRoomTypeName(admissionBo.getRoomTypeId().getName());
		return hlAdmissionTo;
	}
}
