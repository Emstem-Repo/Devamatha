<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.inventory.amc.details.history"/> </title>
</head>
<body>
<table width="99%" border="0">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.inventory.amc.details.history"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td height="25" colspan="7">&nbsp;</td>
		</tr>
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >

                  <td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.amc.details.item.category"/></div></td>
                  <td width="25%" class="row-even" ><bean:write name="amcDetailsForm" property="itemCategoryName"/></td>
                  <td width="25%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.amc.details.item.name"/></div></td>
                  <td width="25%" class="row-even" ><bean:write name="amcDetailsForm" property="itemName"/></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.amc.details.item.no"/></div></td>
                  <td class="row-even" ><bean:write name="amcDetailsForm" property="selectedItemNo"/></td>
                  <td class="row-odd" >&nbsp;</td>
                  <td class="row-even" >&nbsp;</td>
                </tr>
              </table>
              </td>

            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>

      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			<tr>
  				<td width="33" height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td class="row-odd" ><bean:message key="knowledgepro.inventory.amc.details.amc.start.date"/></td>
                 <td class="row-odd" ><bean:message key="knowledgepro.inventory.amc.details.amc.end.date"/></td>
                 <td class="row-odd" ><bean:message key="admissionFormForm.comments.required"/></td>
               </tr>
             <logic:iterate id="amcHistoryList" name="amcHistoryList" indexId="count">
					<c:choose>
						<c:when test="${count%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-white">
						</c:otherwise>
					</c:choose>
                  <td height="25"><div align="center"><c:out value="${count +1}"/></div></td>

                  <td width="120"><bean:write name = "amcHistoryList" property="warrantyStartDate"/></td>
                  <td width="88"><bean:write name = "amcHistoryList" property="warrantyEndDate"/></td>
                  <td width="88"><bean:write name = "amcHistoryList" property="comments"/></td>
                </tr>
			</logic:iterate>
              </table>
               </td>

              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
          </table>
        <td valign="top" background="images/Tright_3_3.gif" ></td>

      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        </table>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>

        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
		<tr>
		<td height="25" colspan="7">&nbsp;</td>
		</tr>
	<tr>
		<td height="25" colspan="7">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="3" align="center">
				<input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/>
					</td>
				</tr>
			</table>
			</td>
		</tr>
    </table></td>
  </tr>
</table>
</html>