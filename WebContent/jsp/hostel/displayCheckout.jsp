<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" language="JavaScript">
	function cancelAction() {
				document.location.href = "hostelCheckout.do?method=initCheckout";
		}
	function displayFineDetails(){
		var url ="hostelCheckout.do?method=displayFineDetails";
		myRef = window.open(url,"hostelCheckout","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	    }
</script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<body>
<html:form action="/hostelCheckout">
<html:hidden property="formName" value="hostelCheckoutForm" />
<html:hidden property="method" styleId="method" value="submitHostelCheckoutDetails"/>
<html:hidden property="formId" name="hostelCheckoutForm"	styleId="formId" />
<html:hidden property="statusId" name="hostelCheckoutForm"
		styleId="statusId" />
<!--<html:hidden property="formId" name="hostelCheckoutForm" styleId="formId"/>
<html:hidden property="pageType" value="1" />
-->

<table width="98%" border="0">
  
  <tr>
    <td><span class="heading"><a href="#" class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/></a> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.checkout"/>  &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" ></td>
        <td colspan="2" background="images/Tcenter.gif" class="body" ><div align="left" class="heading_white"><bean:message key="knowledgepro.hostel.checkout"/></div></td>
        <td width="12" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="17" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="heading"><bean:message key="knowledgepro.hostel.checkout.hostlerDetails"/></td>
        <td width="499" height="17" class="heading"><bean:message key="knowledgepro.hostel.checkout"/></td>
        <td height="17" valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" colspan="2">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="68" valign="top" class="heading"><table width="98%" border="0" cellpadding="0" cellspacing="0">
        
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="29" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="47%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.checkout.name"/></div></td>
                  <td width="53%" height="25" class="row-even" ><bean:write name="hostelCheckoutForm" property="applicantName"/></td>
                  </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.checkin.checkinDate"/></div></td>
                  <td height="25" class="row-even" ><bean:write name="hostelCheckoutForm" property="txnDate"/></td>
                </tr>
            </table></td>
            <td  background="images/right.gif" width="10" height="29"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td  valign="top" class="heading"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="10" ><img src="images/01.gif" width="5" height="5"></td>
 		    <td width="427" background="images/02.gif"></td>
            <td width="9"><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td  background="images/left.gif"></td>
            <td width="100%" height="55" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="29%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.checkout.roomNo"/></div></td>
                  <td width="29%" height="25" class="row-even" ><bean:write name="hostelCheckoutForm" property="roomName"/></td>
                  <td width="23%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.checkout.roomType"/></div></td>
                  <td width="19%" height="25" class="row-even" ><bean:write name="hostelCheckoutForm" property="roomType" /></td>
                </tr>
                <tr >
                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.checkout.checkoutDate"/></div></td>
                  <td height="25" class="row-even" ><html:text name="hostelCheckoutForm" property="checkoutDate" styleId="checkoutDate" size="10" maxlength="10" /> 
                      <script language="JavaScript">
							new tcal ({
										// form name
											'formname': 'hostelCheckoutForm',
										// input name
											'controlname': 'checkoutDate'
									});
						</script>
				</td>
				<td colspan="2" class="row-even" >
                <a href="#" onclick="displayFineDetails()">View Fine Details</a>   
				</td>
              </tr>
            </table></td>
            <td  background="images/right.gif"></td>
          </tr>
          <tr>
 		     <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td width="433" height="20" class="heading"><bean:message key="knowledgepro.hostel.checkout.fecility"/></td>
        <td height="20" class="heading"><div align="left"><bean:message key="knowledgepro.hostel.checkout.remarks"/></div></td>
        <td height="20" valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="98%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="443" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td width="100%" height="29" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr>			
			<td width="100%" height="29" valign="top">
				<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
						<logic:notEmpty property="checkoutList" name="hostelCheckoutForm">
				   			<nested:iterate id="checkinCheckoutFacilityListId" name="hostelCheckoutForm"	property="checkoutList" indexId="count">
								<c:if test="${count%3 == 0}">
									<tr>
								</c:if>
						<td width="20%" class="row-odd" align="right">
								<nested:write name="checkinCheckoutFacilityListId" property="name" /> :
						</td>
						<td width="7%" height="25" class="row-even">
								<div align="left">
									<nested:checkbox property="selected" name="checkinCheckoutFacilityListId"></nested:checkbox>
								 </div>
								</td>
							</nested:iterate>
 						</logic:notEmpty>
					</tr>
			
		</table>			
			</td>
			
		</tr>
            </table></td>
            <td  background="images/right.gif" width="10" height="29"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" ><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="29" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="7%" valign="top" class="row-even" ><label>
                      <textarea name="textarea" id="textarea" cols="30" rows="2"></textarea>
                    </label></td>
                </tr>
            </table></td>
            <td  background="images/right.gif" width="10" height="29"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>

      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" >&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="49%" align="right"><html:submit property="" styleClass="formbutton" value="Submit" /></td>
              <td width="2%" align="center">&nbsp;</td>
              <td width="49%"><input type="button" class="formbutton"
								value="Cancel" onclick="cancelAction()" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" >&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td colspan="2"  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html>
