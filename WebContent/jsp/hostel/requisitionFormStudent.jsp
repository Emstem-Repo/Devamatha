<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function submitHostelApplicationStudent(){
	document.getElementById("method").value = "submitHostelApplicationStudent";
	document.hostelApplicationForm.submit();	
}
function setRoomTypeId(roomTypeId){
	document.getElementById("roomTypeCheck").value = roomTypeId;
}
function redirectToHomePage(){
	document.getElementById("method").value = "initHostelApplicationStudent";
	document.hostelApplicationForm.submit();
}
function viewTermsCondition(hostelId){
	document.location.href = "HostelApplicationTermsCondition.do?hostelId="
		+ hostelId;
}
function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 200;
	return (Object.value.length < MaxLen);
}

</script>
<html:form action="/HostelApplication" method="post">
<html:hidden property="method" styleId="method" />
<html:hidden property="roomTypeCheck" styleId="roomTypeCheck" />
<html:hidden property="formName" value="hostelApplicationForm" />
<table width="99%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/> &gt;&gt; <bean:message key="knowledgepro.hostel.app.student"/> </span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td  background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.hostel.app.student"/> </strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="10" valign="top">
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
        <div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr >
            <td width="100%" height="25" class="row-white" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img src="images/01.gif" width="5" height="5"></td>
                  <td  background="images/02.gif"></td>
                  <td width="10" ><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="0" height="28"  background="images/left.gif"></td>
                  <td width="100%" height="28" valign="top"><table width="100%" height="51" border="0" cellpadding="2" cellspacing="1">
                      
                      <tr class="row-white">
                        <td width="29%"  height="24" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.preference.roomtype"/> </div></td>
                        
                        <logic:notEmpty name="hostelApplicationForm" property="roomTypeNameList">
                        <nested:iterate id="room" name="hostelApplicationForm" property="roomTypeNameList" indexId="count">
                        <td  height="24" class="row-even">
                        
                        <bean:define id="roomId" property="id" name="room"></bean:define>
                  	    <input type="radio" name="roomTypeId" id="roomTypeId" onclick="setRoomTypeId('<%=roomId %>')">
                          <nested:write name="room" property="name"/>
                   
                          </td>
                          </nested:iterate>
                          </logic:notEmpty>
                        </tr>
                      <tr class="row-white">
                        <td width="29%"  height="24" class="row-odd"><div align="right"> <bean:message key="knowledgepro.hostel.clinicalremarks"/> </div></td>
                        <td width="23%"  height="24" class="row-even">
                        <html:textarea property="clinicalRemarks" cols="15" rows="2" onkeypress="return imposeMaxLength(event,this)"></html:textarea>
						</td>
                        <td width="24%" class="row-odd"><div align="right"> <bean:message key="knowledgepro.hostel.sicknessinfo"/> </div></td>
                        <td width="24%" class="row-even"><html:textarea property="sicknessRelatedInfo" cols="15" rows="2" onkeypress="return imposeMaxLength(event,this)"></html:textarea></td>
                      </tr>
                      
                  </table></td>
                  <td  background="images/right.gif" width="10" height="28"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
          </tr>
		<logic:notEmpty  name="hostelApplicationForm" property="roomTypeWithAmountList">
          <tr >
            <td height="150" valign="top" class="row-white" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5" /></td>
                  <td background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5" /></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
					<logic:iterate id="feeHead" name="hostelApplicationForm" property="roomTypeWithAmountList" type="com.kp.cms.to.hostel.RoomTypeWithAmountTO" indexId="count">
					<c:if test="${count == 0}">
	                      <tr>
	                        <td height="25" class="row-odd" >Room Type</td>
							<logic:notEmpty name = "feeHead" property="amountList" >
							<logic:iterate name = "feeHead" property="amountList" id = "head">
		                        <td height="25" class="row-odd" ><bean:write name="head" property="feeTypeName"/> </td>
							</logic:iterate>
							</logic:notEmpty>
 							<td height="25" class="row-odd" >Total Amount</td>
	                      </tr>
					</c:if>
					</logic:iterate>
					<logic:iterate id="feeRoomType" name="hostelApplicationForm" property="roomTypeWithAmountList" type="com.kp.cms.to.hostel.RoomTypeWithAmountTO" indexId="count">
						<%Double totalAmt = 0.0;%> 
                      <tr>
                        <td width="20%" height="25" class="row-even"><div align="left"><bean:write name="feeRoomType" property="roomType"/></div></td>
						<logic:notEmpty name = "feeRoomType" property="amountList" >
							<logic:iterate name = "feeRoomType" id = "feeAmt" property="amountList" type = "com.kp.cms.to.hostel.HlApplicationFeeTO">
	                        	<td width="18%" height="25" class="row-even" ><bean:write name="feeAmt" property="amount"/></td>
								<%
								if(feeAmt.getAmount()!=null && feeAmt.getAmount()!="")
								totalAmt = totalAmt + Double.parseDouble(feeAmt.getAmount()); %>
							</logic:iterate>
						</logic:notEmpty>
						<td width="18%" height="25" class="row-even" ><%=totalAmt%></td>
                      </tr>
                    </logic:iterate>  
                  </table></td>
                  <td width="5" height="30"  background="images/right.gif"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" /></td>
                </tr>
            </table></td>
          </tr>
		</logic:notEmpty>
          <tr >
            <td height="42" valign="top" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img src="images/01.gif" width="5" height="5"></td>
                  <td  background="images/02.gif"></td>
                  <td width="10" ><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="0" height="28"  background="images/left.gif"></td>
                  <td width="100%" height="28" valign="top"><table width="100%" height="30" border="0" cellpadding="2" cellspacing="1">
                      <tr class="row-white">
                        <td width="25%"  height="28" class="row-even"><a href="#"
							onclick="viewTermsCondition('<bean:write name="hostelApplicationForm" property="hostelId"/>')"><bean:message key="knowledgepro.view.termscondition"/> </a></td>
                        <td width="75%" class="row-even"> 
                        <html:radio property="accepted" styleId="accept" value="true">
						  <bean:message key="knowledgepro.i.accept"/> </html:radio> 
                       <html:radio property="accepted" styleId="decline" value="false">
						  <bean:message key="knowledgepro.decline"/> </html:radio> 
                        </td>
                      </tr>
                  </table></td>
                  <td  background="images/right.gif" width="10" height="28"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35"><div align="right">
            <html:button property="" value="Submit" styleClass="formbutton" onclick="submitHostelApplicationStudent()"></html:button>
            </div></td>
            <td width="2%"></td>
            <td width="49%"><html:button property="" value="Cancel" styleClass="formbutton" onclick="redirectToHomePage()"></html:button></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
	document.getElementById("decline").checked  = false;
	document.getElementById("accept").checked  = false;
</script>