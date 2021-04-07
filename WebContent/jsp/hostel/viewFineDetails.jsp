<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">

</script>
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.damageEntry"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.hostel.damageEntry"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								 <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.damgeentry.slno"/></div></td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.damgeentry.amount"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.damgeentry.date"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.damgeentry.description"/></div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.damgeentry.time"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:notEmpty name="hostelCheckoutForm" property="hlDamageToList">
                <logic:iterate id="damage" name="hostelCheckoutForm" property="hlDamageToList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="6%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="41%" height="25" class="row-even" align="center"><bean:write name="damage" property="amount"/></td>
                   		<td width="41%" class="row-even" align="center"><bean:write name="damage" property="date"/></td>
			            <td width="6%" height="25" class="row-even" ><bean:write name="damage" property="description"/></td>
                   		<td width="6%" height="25" class="row-even" ><bean:write name="damage" property="time"/></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="6%" height="25" class="row-odd" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="41%" height="25" class="row-odd" align="center"><bean:write name="damage" property="amount"/></td>
                   		<td width="41%" class="row-odd" align="center"><bean:write name="damage" property="date"/></td>
			            <td width="6%" height="25" class="row-odd" ><bean:write name="damage" property="description"/></td>
                   		<td width="6%" height="25" class="row-odd" ><bean:write name="damage" property="time"/></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
                </logic:iterate>
                
                </logic:notEmpty>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
						</tr>

						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>

