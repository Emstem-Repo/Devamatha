package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.List;

public class HlRoomTO implements Serializable,Comparable<HlRoomTO> {
	private String name;
	private int roomTypeId;
	private int roomId;
	private String floorNo;
	private String roomNo;
	private List<HlBedsTO> hlBeds;
	
	/**
	 * @return the floorNo
	 */
	public String getFloorNo() {
		return floorNo;
	}
	/**
	 * @param floorNo the floorNo to set
	 */
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	/**
	 * @return the roomNo
	 */
	public String getRoomNo() {
		return roomNo;
	}
	/**
	 * @param roomNo the roomNo to set
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getName() {
		return name;
	}
	public int getRoomTypeId() {
		return roomTypeId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	@Override
	public int compareTo(HlRoomTO arg0) {
        String firstString = this.getName();
        String secondString = arg0.getName();
 
        if (secondString == null || firstString == null) {
            return 0;
        }
 
        int lengthFirstStr = firstString.length();
        int lengthSecondStr = secondString.length();
 
        int index1 = 0;
        int index2 = 0;
 
        while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
            char ch1 = firstString.charAt(index1);
            char ch2 = secondString.charAt(index2);
 
            char[] space1 = new char[lengthFirstStr];
            char[] space2 = new char[lengthSecondStr];
 
            int loc1 = 0;
            int loc2 = 0;
 
            do {
                space1[loc1++] = ch1;
                index1++;
 
                if (index1 < lengthFirstStr) {
                    ch1 = firstString.charAt(index1);
                } else {
                    break;
                }
            } while (Character.isDigit(ch1) == Character.isDigit(space1[0]));
 
            do {
                space2[loc2++] = ch2;
                index2++;
 
                if (index2 < lengthSecondStr) {
                    ch2 = secondString.charAt(index2);
                } else {
                    break;
                }
            } while (Character.isDigit(ch2) == Character.isDigit(space2[0]));
 
            String str1 = new String(space1);
            String str2 = new String(space2);
 
            int result;
 
            if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
                Integer firstNumberToCompare = Integer.valueOf(Integer
                        .parseInt(str1.trim()));
                Integer secondNumberToCompare = Integer.valueOf(Integer
                        .parseInt(str2.trim()));
                result = firstNumberToCompare.compareTo(secondNumberToCompare);
            } else {
                result = str1.compareTo(str2);
            }
 
            if (result != 0) {
                return result;
            }
        }
        return lengthFirstStr - lengthSecondStr;
    }
	public List<HlBedsTO> getHlBeds() {
		return hlBeds;
	}
	public void setHlBeds(List<HlBedsTO> hlBeds) {
		this.hlBeds = hlBeds;
	}

}
