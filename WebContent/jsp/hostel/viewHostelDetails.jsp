<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<html:form action="/HostelApplication" method="post">
<html:hidden property="method" styleId="method" />
<html:hidden property="formName" value="hostelApplicationForm" />
<table width="99%" border="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.details"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
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
                <td width="19%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.name.col"/></div></td>
                <logic:notEmpty name="hostelApplicationForm" property="hostelTO">
                <td width="21%" height="25" class="row-even" ><bean:write name="hostelApplicationForm" property="hostelTO.name"/> </td>
                </logic:notEmpty>
                <td width="21%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.address.col"/></div></td>
                <logic:notEmpty name="hostelApplicationForm" property="hostelTO">
                <td width="21%" class="row-even" >
                <bean:write name="hostelApplicationForm" property="hostelTO.addressLine1"/>
                <bean:write name="hostelApplicationForm" property="hostelTO.addressLine2"/>
                </td>
                </logic:notEmpty>
                </tr>
            </table></td>
            <td width="5" background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
     <logic:notEmpty name="hostelApplicationForm" property="roomTypeNameList">
      <nested:iterate id="roomtype" name="hostelApplicationForm" property="roomTypeNameList">
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="25" class="heading"><bean:message key="knowledgepro.hoste.roomtype.col"/>&nbsp;<bean:write name="roomtype" property="name" /></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
    <tr>
        <td height="66" valign="top" background="images/Tright_03_03.gif"></td>
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
                  <td width="17%" height="25" class="row-odd" ><bean:message key="knowledgepro.fee"/> </td>
                  <td width="83%" height="25" class="row-even" >10,000</td>
                  </tr>
                <tr >
                  <td height="25" class="row-odd" width="20%"><bean:message key="knowledgepro.facilities"/></td>
                  <td class="row-even" width="80%" >
                  <logic:notEmpty name="roomtype" property="facilityList">
                  <nested:iterate name="roomtype" id="facility" property="facilityList">
                  <bean:write name="facility" property="facilityTO.name"/>
                  </nested:iterate>
                  </logic:notEmpty>
                  </td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><bean:message key="knowledgepro.hostel.photo"/> </td>
                <logic:notEmpty name="roomtype" property="imageList">
                  <td class="row-even" >
                  <logic:iterate id="photo" property="imageList" name="roomtype">
                   <bean:define id="reqroomTypeId" name="photo" property="roomTypeId"></bean:define>
					<bean:define id="count" name="photo" property="countId"></bean:define>										
					<img src='<%=request.getContextPath()+"/HostelRoomTypeImageServlet?countID="+count+"&reqroomTypeId="+reqroomTypeId %>'height="90Px" width="90Px" />
					</logic:iterate> 
                  </td>
                  </logic:notEmpty>
                </tr>
              </table></td>
              <td width="5" height="55"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      </nested:iterate>
      </logic:notEmpty>
 	 <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center">&nbsp;</td>
          </tr>
        </table></td>
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