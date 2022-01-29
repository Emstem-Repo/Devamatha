<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${msg==null}">
	<studentDetails>
			<studentName><bean:write name="studentName"/></studentName>
			<hostelAdmissionId><bean:write name="hostelAdmissionId"/></hostelAdmissionId>
			<studentRoomNo><bean:write name="studentRoomNo"/></studentRoomNo>
			<studentBedNo><bean:write name="studentBedNo"/></studentBedNo>
			<studentBlock><bean:write name="studentBlock"/></studentBlock>
			<studentUnit><bean:write name="studentUnit"/></studentUnit>
			<studentFloor><bean:write name="studentFloor"/></studentFloor>
			<studentHostel><bean:write name="studentHostel"/></studentHostel>
			<studentHostelAppNo><bean:write name="studentHostelAppNo"/></studentHostelAppNo>
			<admittedDate><bean:write name="admittedDate"/></admittedDate>
			<biometricId><bean:write name="biometricId"/></biometricId>
			<checkInDate><bean:write name="checkInDate"/></checkInDate>
			<isCheckedIn><bean:write name="isCheckedIn"/></isCheckedIn>
			<roomTypeId><bean:write name="roomTypeId"/></roomTypeId>
			<studentGender><bean:write name="studentGender"/></studentGender>
	</studentDetails>
	<logic:iterate id="map" name="HostelMap">
              <Hostel>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
              </Hostel>
   	</logic:iterate>
	
	<logic:iterate id="map" name="RoomTypeMap">
               <RoomType>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
                </RoomType>
   	</logic:iterate>
   	<logic:iterate id="map" name="BlockMap">
               <Block>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
               </Block>
   	</logic:iterate>
   	<logic:iterate id="map" name="UnitMap">
              <Unit>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
              </Unit>
   	</logic:iterate>
   	<logic:iterate id="map" name="FloorMap">
               <Floor>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
               </Floor>
   	</logic:iterate>
   	<logic:iterate id="map" name="RoomMap">
              <Room>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
              </Room>
   	</logic:iterate>
   	<logic:iterate id="map" name="BedMap">
              <Bed>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
              </Bed>
   	</logic:iterate>
	
	
	
	
	
	</c:if>
	<c:if test="${msg!=null}">
		<value><bean:write name="msg"/> </value>
	</c:if>
</response>