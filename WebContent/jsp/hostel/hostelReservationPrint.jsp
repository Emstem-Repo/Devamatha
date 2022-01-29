<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function cancelAction(){
		document.location.href = "HostelReservation.do?method=initHostelReservation";
	}
</script>
<body>
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs">
				<bean:message  key="knowledgepro.hostel" /></span> <span class="Bredcrumbs">&gt;&gt;
				Print Slip &gt;&gt;</span></td>
		</tr>
		


  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.hostel.reservation.printslip" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
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
                    <td width="24%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.slipNo" />: </div></td>
                    <td height="25" colspan="3" class="row-even" ><bean:write name="hostelReservationForm" property="billNo" /></td>
                  </tr>
                  <tr >
                    <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.applicationorregno" /></div></td>
                    <td width="25%" height="25" class="row-even" ><bean:write name="hostelReservationForm" property="applicantHostelDetails.applicationNumber" /></td>
                    <td width="21%" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentedit.name.label" /></div></td>
                    <td width="30%" class="row-even" ><bean:write name="hostelReservationForm" property="applicantHostelDetails.applicantName" /></td>
                  </tr>
                  <tr >
                    <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.course.with.col" /></div></td>
                    <td height="25" class="row-even" ><bean:write name="hostelReservationForm" property="applicantHostelDetails.course" /></td>
                    <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.requisitionNo" />:</div></td>
                    <td height="25" class="row-even" ><bean:write name="hostelReservationForm" property="applicantHostelDetails.requisitionNo" /> </td>
                  </tr>
                  <tr >
                    <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.reservation.reservationDate" />:</div></td>
                    <td class="row-even" ><bean:write name="hostelReservationForm" property="reservationDate" /></td>
                    <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.roomtype" /></div></td>
                    <td height="25" class="row-even" ><bean:write name="hostelReservationForm" property="applicantHostelDetails.roomTypeName" /></td>
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
        <td height="36" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="48%" height="35"><div align="right">
                  <input type="button" class="formbutton" value="Print" onClick="window.print()" />
              </div></td>
              <td width="3%"></td>
              <td width="49%"><input type="submit" class="formbutton" value="Cancel" onClick="cancelAction()"/></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="931" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>

