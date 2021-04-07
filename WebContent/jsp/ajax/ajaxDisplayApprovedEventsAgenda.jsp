<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${audiBookingMap!=null}">
			<logic:iterate id="audiBooking" name="audiBookingMap">
			<fields>
				<value> <bean:write name="audiBooking" property="key"/> </value>
					<logic:iterate id="audiBookingTo" name="audiBooking" property="value" indexId="count">
				<audiBookingToDetails>
						<siNo> <c:out value="${count + 1}" /></siNo> 
						<block><bean:write name="audiBookingTo" property="blockName"/></block>
						<venue><bean:write name="audiBookingTo" property="venueName"/></venue>
						<startTime><bean:write name="audiBookingTo" property="startTime"/></startTime>
						<endTime><bean:write name="audiBookingTo" property="endTime"/></endTime>
						<bookedBy><bean:write name="audiBookingTo" property="bookedBy"/></bookedBy>
						<department><bean:write name="audiBookingTo" property="department"/></department>
						<requirements><bean:write name="audiBookingTo" property="bookingRequirements"/></requirements>
					</audiBookingToDetails>
					</logic:iterate>
					
				</fields>
			</logic:iterate>
	</c:if>
</response>