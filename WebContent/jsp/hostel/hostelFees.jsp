<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">

	function viewFees(hostelId,roomTypeId) {
		var url = "hostelFees.do?method=viewFeeDetails&hostelId="+hostelId+"&roomId="+roomTypeId;
		window.open(url,'ViewPersonalDetails','left=20,top=20,width=1000,height=1000,toolbar=1,resizable=0,scrollbars=1');
	}
	function resetMessages() {
		document.getElementById("hostelId").value = "";
		document.getElementById("roomType").value = "";
		document.getElementsByName('name').value="";
		resetFieldAndErrMsgs();
	}	
	function getRoomTypes(hostelId){
		getRoomTypeByHostel("roomTypeMap",hostelId,"roomType",updateRoomType);
	}

	function updateRoomType(req) {
		updateOptionsFromMap(req, "roomType", "- Select -");
	}
	function deleteFeeDetails(hostelId,roomTypeId) 
	{

		deleteConfirm = confirm("Are you sure want to delete this entry?");
		if (deleteConfirm) {
			document.location.href="hostelFees.do?method=deleteFeeDetails&hostelId="+hostelId+"&roomId="+roomTypeId;
		}
	}

	function submitFeeDetails(){

		var hostelId = document.getElementById("hostelId").value;
		var roomTypeId = document.getElementById("roomType").value;
			if(document.getElementsByName)
			{
				elements = new Array();
				elements = document.getElementsByName('name');
				
				 values = new Array(elements.length);
				
				for(var j =0;j<elements.length;j++)
				{
					var element = elements[j].value;
					values[j] = element;
				}
				
				document.location.href ="hostelFees.do?elements="+values+"&hostelId="+hostelId+"&roomType="+roomTypeId;
				//document.hostelFeesForm.submit();
			}
		
		
		}

	function reActivate(hostelId,roomTypeId) {
		document.location.href = "hostelFees.do?method=reActivateFeeDetails&hostelId="+hostelId+"&roomType="+roomTypeId;
	}
	
	
	function getTotal() {
		var total = 0;
		var size = parseInt(document.getElementById("length").value);
		for ( var count = 0; count <= size - 1; count++) {
			var curValue = parseFloat(document
					.getElementById("feeList["+count+"].amount").value);
			if (isNaN(curValue) || (curValue == null)) {
				curValue = 0;
			}
			total = total + curValue;
		}
		document.getElementById("total").value = total;
	}
	

	function editFeeDetails(hostelId,roomTypeId){
		//document.getElementById("methodId").value="editHostelFeesTypeDetails";//wont work bcoz we are not submitting
		document.location.href="hostelFees.do?method=editFeeDetails&hostelId="+hostelId+"&roomType="+roomTypeId;
	}

	function updateFeeDetails()
	{
		var hostelId = document.getElementById("hostelId").value;
		var roomTypeId = document.getElementById("roomType").value;
			if(document.getElementsByName)
			{
				elements = new Array();
				elements = document.getElementsByName('name');
				
				 values = new Array(elements.length);
				
				for(var j =0;j<elements.length;j++)
				{
					var element = elements[j].value;
					values[j] = element;
				}
				
				document.location.href ="hostelFees.do?elements="+values+"&hostelId="+hostelId+"&roomType="+roomTypeId;
				
			}
	}
		
	var tempAmt="0";
</script>
</head>
<body>

<html:form action="/hostelFees">
	<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateFeeDetails" />
		</c:when>
		<c:otherwise>
		<html:hidden property="method" styleId="method" value="submitFeeDetails" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="hostelFeesForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="oldHostelId"/>
	<html:hidden property="oldRoomTypeId"/>
	<table width="99%" border="0">
	  <tr>
	    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.fees"/> &gt;&gt;</span> </span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.hostel.fees"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      
	      
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out><br>
						</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
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
	                  <td width="21%" height="24" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.name"/></div></td>
	                  <td width="30%" class="row-even"><html:select property="hostelId" styleClass="combo"
											styleId="hostelId" onchange="getRoomTypes(this.value)">
											<html:option value="">-<bean:message
													key="knowledgepro.select" />-</html:option>
													<logic:notEmpty property="hostelList" name="hostelFeesForm">
											<html:optionsCollection property="hostelList" name="hostelFeesForm" label="name" value="id"/>
											</logic:notEmpty>
										</html:select></td>
	                  <td width="17%" height="24" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hoste.roomtype.col"/></div></td>
	                  <td width="32%" class="row-even"><html:select property="roomType" styleId="roomType" styleClass="combo">
							<html:option value="">- Select -</html:option>
							<c:if test="${roomTypeMap != null}">
							<html:optionsCollection name="roomTypeMap" label="value" value="key"/>
							</c:if>
						</html:select></td>
	                </tr>
	               
	                
	                 <tr>
	                 <c:set var="temp1" value="0" />
                      			<c:set var="temp" value="0" />
							<logic:notEmpty name="hostelFeesForm" property="feeList">
								<nested:iterate id="details" name="hostelFeesForm" property="feeList" indexId="count">
									<nested:hidden property="feeId"></nested:hidden>
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td class="row-odd"><div align="right"><bean:write name="details" property="feesname" /></div></td>
            												 <td class="row-even" colspan="3">
            												<div id="dynaText">
            											 	 <input type="text" name="feeList[<c:out value="${count}"/>].amount" 
            											 	 value="<bean:write name='details' property="amount"/>"
            											 	  id='feeList[<c:out value="${count}"/>].amount' maxlength="9" onkeypress="return isNumberKey(event)" onblur="getTotal()" />
            												</div>
            												 </td>
											</tr>
											<c:set var="temp" value="1" />
											<c:set var="temp1" value="${count+1}" />
										</c:when>
										<c:otherwise>
											<tr>
												<td class="row-odd"><div align="right"><bean:write name="details" property="feesname" /></div></td>
     												 <td class="row-even" colspan="3">
     												 <div id="dynaText">
     													<input type="text" name="feeList[<c:out value="${count}"/>].amount"
     													 value="<bean:write name='details' property="amount"/>"
     													 id='feeList[<c:out value="${count}"/>].amount' maxlength="9" onkeypress="return isNumberKey(event)" onblur="getTotal()" /> 
     												 </div>
     												 </td>
											</tr>
											<c:set var="temp" value="0" />
											<c:set var="temp1" value="${count+1}" />
										</c:otherwise>
									</c:choose>
								</nested:iterate>
							</logic:notEmpty>
											
	                    </tr>
	                     <tr >
						<td class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.total"/></div></td>
	               		 <td class="row-even" colspan="3">
	               		 <input type="hidden" name="length" id="length" value="<c:out value="${temp1}"/>" />
	               		 <html:text name="hostelFeesForm" property="total" styleId="total" maxlength="9" readonly="true"/></td>							 
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
        <td height="37" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td width="38%" height="35">&nbsp;</td>
            <td width="10%">
            <c:choose>
            	<c:when test="${operation == 'edit'}">
            		<html:submit property="" styleClass="formbutton" onclick="updateFeeDetails()">
						<bean:message key="knowledgepro.update"/>
					</html:submit>
            	</c:when>
            	<c:otherwise>
            		<html:submit property="" styleClass="formbutton" onclick="submitFeeDetails()">
						<bean:message key="knowledgepro.submit"/>
					</html:submit>
            	</c:otherwise>
            </c:choose>
            </td>
            <td width="8%">
            <c:choose>
	            <c:when test="${operation == 'edit'}">
					<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
	            </c:when>
	            <c:otherwise>
	            	<html:button property="" styleClass="formbutton" onclick="resetMessages()">
						<bean:message key="knowledgepro.admin.reset"/>
					</html:button>
	            </c:otherwise>
            </c:choose>
            </td>
            <td width="44%" height="35">&nbsp;</td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr>
            <td height="45" colspan="2" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top">
                
               
                <table width="100%" cellspacing="1" cellpadding="2">
                	<tr>
                	<td width="10%" class="row-odd" align="center">Sl No</td>
                     <td width="28%" height="25" class="row-odd" align="center">Hostel Name</td>
                      <td width="28%" class="row-odd" align="center">Room Type</td>
                      <td width="24%" class="row-odd" align="center">View Details </td>
                      <td width="10%" class="row-odd" align="center">Edit</td>
                      <td width="10%" class="row-odd" align="center">Delete</td>
                    </tr>
                   <tr>
                   			
                   										<c:set var="temp" value="0" />
										<logic:notEmpty name="hostelFeesForm" property="feeAllDetails">
											<logic:iterate id="details" name="hostelFeesForm" property="feeAllDetails" indexId="count">
												
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															<td width="10%" height="25" class="row-even"
																align="center"><c:out value="${count + 1}" /></td>
															<td width="28%" height="25" class="row-even"
																align="center"><bean:write name="details" property="hostelName" /></td>
															<td width="28%" class="row-even" align="center"><bean:write
																name="details" property="roomType" /></td>
															<td width="24%" class="row-even" align="center">
															<a href="javascript:viewFees('<bean:write name = "details" property="hostelId" />','<bean:write name = "details" property="roomTypeId"/>');">
															View Details </a></td>
															<td width="10%" height="25" class="row-even">
															<div align="center"><img
																src="images/edit_icon.gif" width="16" height="16" style="cursor:pointer"
																onclick="editFeeDetails('<bean:write name="details" property="hostelId" />','<bean:write name = "details" property="roomTypeId"/>')"></div>
															</td>
															<td width="10%" height="25" class="row-even">
															<div align="center"><img
																src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
																onclick="deleteFeeDetails('<bean:write name="details" property="hostelId" />','<bean:write name = "details" property="roomTypeId"/>')"></div>
															</td>
														</tr>
	
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
															<td width="10%" height="25" class="row-even"
																align="center"><c:out value="${count + 1}" /></td>
															<td width="28%" height="25" class="row-even"
																align="center"><bean:write name="details" property="hostelName" /></td>
															<td width="28%" class="row-even" align="center"><bean:write
																name="details" property="roomType" /></td>
															<td width="24%" class="row-even" align="center">
															<a href="javascript:viewFees('<bean:write name = "details" property="hostelId" />','<bean:write name = "details" property="roomTypeId"/>');">
															View Details </a></td>
															<td width="10%" height="25" class="row-even">
															<div align="center"><img
																src="images/edit_icon.gif" width="16" height="16" style="cursor:pointer"
																onclick="editFeeDetails('<bean:write name="details" property="hostelId" />','<bean:write name = "details" property="roomTypeId"/>')"></div>
															</td>
															<td width="10%" height="25" class="row-even">
															<div align="center"><img
																src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
																onclick="deleteFeeDetails('<bean:write name = "details" property="hostelId" />','<bean:write name = "details" property="roomTypeId"/>')"></div>
															</td>
														</tr>
														
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:notEmpty>
										
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
          </tr>
          
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
