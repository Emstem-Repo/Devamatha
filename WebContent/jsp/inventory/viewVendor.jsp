<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title>View Vendor </title>
</head>
<body>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"></span></td>
  </tr>
  <tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.inventory.vendor.master"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
							<td width="19%" height="25"> &nbsp;</td>
						</tr>						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.extra.cur.act.entry.org.name"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							 <bean:write name="vendorForm" property="vendorName"/>
							</td>
							<td width="10%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.inventory.vendor.master.contact.person"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							 <bean:write name="vendorForm" property="contactPerson"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="admissionForm.studentinfo.addrs1.label"/></div>
							</td>
							<td>			
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td width="19%" height="25" class="row-even">
							 <bean:write name="vendorForm" property="addressLine1"/>
							</td>
							</tr>
							<c:if test="${vendorForm.addressLine2 != null && vendorForm.addressLine2 != ''}">
							<tr>
							<td width="19%" height="25" class="row-even">
							 <bean:write name="vendorForm" property="addressLine2"/>
							</td>
							</tr>
							</c:if>
							</table>
							</td>
						</tr>
						<tr>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.admin.country"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="countryName"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right">
								<bean:message key="knowledgepro.admin.state"/>
								</div>
							</td>
							<td width="19%" height="25" class="row-even">
								<c:choose>
								<c:when test="${vendorForm.stateOthers != null && vendorForm.stateOthers != ''}">
									 <bean:write name="vendorForm" property="stateOthers"/>
								</c:when>
								<c:otherwise>
									 <bean:write name="vendorForm" property="stateName"/>
								</c:otherwise>
								</c:choose>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.admin.city"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="city"/>
							</td>
						</tr>
						<tr>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.admission.zipCode"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="zipCode"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="admissionForm.studentinfo.phone.label"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="phone"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="admissionForm.studentinfo.mobile.label"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="mobile"/>
							</td>
						</tr>
						<tr>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="admissionForm.studentinfo.email.label"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="emailId"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.fax"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="fax"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.tan"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="tan"/>
							</td>
						</tr>
						<tr>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.pan"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="pan"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.pan.pin"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="pin"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.vat"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="vat"/>
							</td>
						</tr>
						<tr>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.bank.ac.no"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="bankAcNo"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.bank.branch"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="bankBranch"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.payment.terms"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="paymentTerms"/>
							</td>
						</tr>
						<tr>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.delivery.schedule"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="deliverySchedule"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.payment.mode"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								 <bean:write name="vendorForm" property="paymentMode"/>
							</td>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.authorised.vendor"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
								<c:if test="${vendorForm.authorisedVendor == 'true'}">
								Yes
								</c:if>
								<c:if test="${vendorForm.authorisedVendor == 'false'}">
								No
								</c:if>
							</td>
						</tr>
						<tr>
							<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.inventory.vendor.category.item"/></div>
							</td>
							<td class="row-even">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<logic:iterate id="categoryList" name = "categoryList">
								<tr>
									<td width="19%" height="25" class="row-even">
										 <bean:write name="categoryList"/>
									</td>
								</tr>
							</logic:iterate>
							</table>
							</td>
							<td width="19%" height="25" class="row-odd"> <div align="right"> <bean:message key="knowledgepro.hostel.reservation.remarks"/></div> </td>
							<td width="19%" height="25" class="row-even">  <bean:write name="vendorForm" property="remarks"/> </td>
							<td width="19%" height="25" class="row-odd"> &nbsp;</td>
							<td width="19%" height="25" class="row-even"> &nbsp;</td>
						</tr>
						<tr>
							<td width="19%" height="25"> &nbsp;</td>
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
					</table>
					</td>

					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
</table>
</body>
</html>