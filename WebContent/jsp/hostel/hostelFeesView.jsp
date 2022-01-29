<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
</script>
<title>Hostel Fees View</title>
<body>

<html:form action="/hostelFees" method="post" >
	<html:hidden property="formName" value="hostelFeesForm" />
	<html:hidden property="pageType" value="2" />
	
	
	<table width="99%" border="0">
  
  
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.hostel.fees"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      
      <tr> </tr>
			<tr> </tr>
      
      <tr>
        <td height="43" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="21%" height="24" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.name"/></div></td>
                  <td width="30%" class="row-even"><bean:write name="hostelFeesForm" property="hostelName"/></td>
                  <td width="17%" height="24" class="row-odd"><div align="right"><bean:message key="knowledgepro.hoste.roomtype.col"/></div></td>
                  <td width="32%" class="row-even"><bean:write name="hostelFeesForm" property="roomType"/></td>
                </tr>
                <tr>
                         			<c:set var="temp" value="0" />
										<logic:notEmpty name="hostelFeesForm" property="feeDetailedListToView">
											<logic:iterate id="details" name="hostelFeesForm" property="feeDetailedListToView" indexId="count">
												
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															<td class="row-odd"><div align="right"><bean:write name="details" property="feeType" /></div></td>
               												 <td class="row-even" colspan="3"><bean:write name="details" property="amount" /></td>				 
														</tr>
	
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
															<td class="row-odd"><div align="right"><bean:write name="details" property="feeType" /></div></td>
               												 <td class="row-even" colspan="3"><bean:write name="details" property="amount" /></td>
														</tr>
														
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:notEmpty>
										
                    </tr>
                    <tr >
					<td class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.total"/></div></td>
               		 <td class="row-even" colspan="3"><bean:write name="hostelFeesForm" property="total" /></td>							 
                    </tr>
                
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
<script type="text/javascript">
</script>
