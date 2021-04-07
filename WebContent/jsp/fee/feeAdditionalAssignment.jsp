<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="java.util.Map,java.util.HashMap" %>

<script type="text/javascript">
function addFee() {
	document.getElementById("method").value = "initAddFeeAdditional";
	document.getElementById("feeGroupName").value = document.getElementById("feeGroupId").options[document.getElementById("feeGroupId").selectedIndex].text;
 	document.feeAdditionalForm.submit();	
}

function updateFee() {
	document.getElementById("method").value = "initEditFeeAdditional";
	document.feeAdditionalForm.submit();
}

function deleteFee(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href="FeeAdditional.do?method=deleteFeeAdditional&id="+id;
	}
}

function viewFee(id) {
	var url ="FeeAdditional.do?method=viewFeeAdditional&id="+id;
	myRef = window.open(url,"viewFees","left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	//document.location.href="FeeAssignment.do?method=viewFeeAssignment&id="+id;
}
function editFee(id) {
	document.location.href="FeeAdditional.do?method=editFeeAdditional&id="+id;
}

function reActivate() {
	var id = document.getElementById("feeAdditionalId").value;
	document.location.href="FeeAdditional.do?method=activateFeeAdditional&id="+id;
}

</script>

<html:form action="/FeeAdditional">

<html:hidden property="method" styleId="method" value="addFeeAssignment"/>
<html:hidden property="formName" value="feeAdditionalForm"/>
<html:hidden property="feeGroupName" styleId="feeGroupName" value=""/>

<html:hidden property="pageType" value="1"/>

<input type="hidden" id="feeAdditionalId" name="id" value="<bean:write name="feeAdditionalForm" property="id"/>"/>  <!-- usefull while edit -->
<input type="hidden" id="operation" name="operation" value="<c:out value='${feeOperation}'/>"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.feeadditional.feeadditional"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.feeadditional.feeadditional"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 <tr>
                    <td height="35" colspan="6" class="body" >
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
		                           <td height="25" valign="top" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.feegroup"/>:</div></td>
		                           <td height="25" class="row-even" align="left">
		                            <html:select property="feeGroupId" styleId="feeGroupId" styleClass="combo" >
                         				   <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                         				  <html:optionsCollection name="feeAdditionalForm" property="feeGroupMap" label="value" value="key"/>
                       				</html:select>
                                   <br></td>
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
                   </table>
                   </td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
                     <tr>
                       <td width="45%"><div align="right">
                        <c:choose>
                           	 <c:when test="${feeOperation == 'edit'}">
                           	 		<html:button property="" styleClass="formbutton" value="UpdateFee" onclick="updateFee();hideButton(this)"></html:button>
                           	 </c:when>
                           	 <c:otherwise>
                           	 		<html:button property="" styleClass="formbutton" value="Add Fee" onclick="addFee();hideButton(this)"></html:button>
                           	 </c:otherwise>
                        </c:choose>   	 
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                       <c:choose>
                           	 <c:when test="${feeOperation == 'edit'}">
                           	 		<html:reset property="" styleClass="formbutton" value="Reset"></html:reset>
                           	 </c:when>
                           	 <c:otherwise>
                           	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                           	 </c:otherwise>
                        </c:choose>  
                      </td>
                     </tr>                     
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>                     
                   </table>
                   </td>
                </tr>
                <tr>
                   <td height="35" colspan="6" >
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
		                       <td width="5%" height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
		                       <td width="12%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.fee.feegroup"/></div></td>
		                       <td width="6%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.view"/></div></td>
		                       <td width="6%" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
		                       <td width="6%" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	                       </tr>
	                     
	                       <c:set var="temp" value="0"/>
	                       
	                       <logic:iterate id="fee" name="feeAdditionalForm" property="feeList" type="com.kp.cms.to.fee.FeeAdditionalTO" indexId="count">
		                       <c:choose>
	                           	 <c:when test="${temp == 0}">
	                           		<tr>
				                       <td height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
				                       <td class="row-even" ><div align="center"><bean:write name="fee" property="feeGroupTO.name"/></div></td>
				                       <td class="row-even" ><div align="center"><img src="images/View_icon.gif" width="16" height="18" style="cursor:pointer" onclick="viewFee('<bean:write name="fee" property="id"/>')"></div></td>
				                       <td height="25" class="row-even" ><div align="center"><img src="images/edit_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editFee('<bean:write name="fee" property="id"/>')"></div></td>
				                       <td height="25" class="row-even" ><div align="center"><img src="images/delete_icon.gif" width="16" style="cursor:pointer" height="16" onclick="deleteFee('<bean:write name="fee" property="id"/>')"></div></td>
	                               </tr>
	                      		   <c:set var="temp" value="1"/>
	                   		 	</c:when>
	                    	    <c:otherwise>
			                     <tr >
			                       <td height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
				                   <td class="row-white" ><div align="center"><bean:write name="fee" property="feeGroupTO.name"/></div></td>
			                       <td class="row-white" ><div align="center"><div align="center"><img src="images/View_icon.gif" width="16" height="18" style="cursor:pointer" onclick="viewFee('<bean:write name="fee" property="id"/>')"></div></td>
			                       <td height="25" class="row-white" ><div align="center"><img src="images/edit_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editFee('<bean:write name="fee" property="id"/>')"></div></td>
			                       <td height="25" class="row-white" ><div align="center"><img src="images/delete_icon.gif" width="16" style="cursor:pointer" height="16" onclick="deleteFee('<bean:write name="fee" property="id"/>')"></div></td>
			                     </tr>
	                     		 <c:set var="temp" value="0"/>
					  	       </c:otherwise>
	                        </c:choose>
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
                   </table></td>
                 </tr>
               
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>


</html:form>
