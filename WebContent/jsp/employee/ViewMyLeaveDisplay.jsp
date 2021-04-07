<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>

</head>
<html:form action="/ViewMyLeave" method="post">
<html:hidden property="pageType" value="2" />
<html:hidden property="formName" value="ViewMyLeaveForm" />

<table width="100%" border="0">
  <tr>
    <td><span class="heading">Faculty on Leave</span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> Faculty on Leave</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      <tr>
        <td height="107" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
            
              <tr >
                <td height="25" class="row-odd" >Sl.No</td>
                <td class="row-odd" >Name</td>
                <td class="row-odd" >Department</td>
                <td class="row-odd" >Type of Leave</td>
                <td class="row-odd" >From Date</td>
                <td class="row-odd" >To Date</td>
                <td class="row-odd" >Reason</td>
                </tr>
              
              
              
              <nested:iterate property="listOfEmployeeLeave" indexId="count" >
				<c:choose>
					<c:when test="${count%2 == 0}">
						<tr class="row-even">
					</c:when>
					<c:otherwise>
						<tr class="row-white">
					</c:otherwise>
				</c:choose>
				<td class="bodytext">
				<div align="center"><c:out value="${count + 1}" /></div>
				</td>
				<td align="center" class="bodytext"><nested:write property="name" /></td>
				<td align="center" class="bodytext"><nested:write property="department" /></td>
				<td align="center" class="bodytext"><nested:write property="leaveType" /></td>
				<td align="center" class="bodytext"><nested:write property="fromDate" /></td>
				<td align="center" class="bodytext"><nested:write property="toDate" /></td>
				<td align="center" class="bodytext"><nested:write property="reason" /></td>
              </tr>
			</nested:iterate>
              
              
              
                
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      
          
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
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