<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link href="css/styles.css" rel="stylesheet" type="text/css">

<html:form action="HostelReservation" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="hostelReservationForm" />
<html:hidden property="pageType" value="1"/>
	<table width="98%" border="0" cellpadding="2" cellspacing="1">
	  <tr>
	    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td height="40" valign="top" background="images/Tright_03_03.gif"></td>
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
	                    <td width="24%" height="25" class="row-odd" ><div align="right"> Slip No.: </div></td>
	                    <td height="25" colspan="3" class="row-even" >123</td>
	                  </tr>
	                  <tr >
	
	                    <td height="25" class="row-odd" ><div align="right">Application No./Register No.:</div></td>
	                    <td width="25%" height="25" class="row-even" >1001</td>
	                    <td width="21%" class="row-odd" ><div align="right">Name:</div></td>
	                    <td width="30%" class="row-even" ><bean:write name="hostelReservationForm" property="applicantHostelDetails.applicantName"/> </td>
	                  </tr>
	                  <tr >
	                    <td height="25" class="row-odd" ><div align="right">Course:</div></td>
	
	                    <td height="25" class="row-even" ><bean:write name="hostelReservationForm" property="applicantHostelDetails.course"/></td>
	                    <td class="row-odd" ><div align="right">Scheme:</div></td>
	                    <td class="row-even" >VI </td>
	                  </tr>
	                  <tr >
	                    <td height="25" class="row-odd" ><div align="right">Requisition No.:</div></td>
	                    <td height="25" class="row-even" ><bean:write name="hostelReservationForm" property="applicantHostelDetails.requisitionNo"/> </td>
	
	                    <td class="row-odd" ><div align="right">Reservation Date:</div></td>
	                    <td class="row-even" ><bean:write name="hostelReservationForm" property="reservationDate"/> </td>
	                  </tr>
	                  <tr >
	                    <td height="25" class="row-odd" ><div align="right">Room Type:</div></td>
	                    <td height="25" class="row-even" ><bean:write name="hostelReservationForm" property="roomTypeName"/> </td>
	                    <td class="row-odd" ><div align="right">Amount:</div></td>
	
	                    <td class="row-even" >15,000</td>
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
	    </table></td>
	  </tr>
	</table>
</html:form>
<script type="text/javascript">
	window.print();
</script>
