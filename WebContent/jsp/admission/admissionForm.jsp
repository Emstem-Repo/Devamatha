<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="knowledgepro.title"/> </title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<link rel="stylesheet" href="css/calendar.css">


<link href="css/styles.css" rel="stylesheet"
	type="text/css">

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

</head>
<body>
<html:form action="/admissionFormSubmit" enctype="multipart/form-data">
<table width="98%" border="0">
	<tr>
		<td><span class="heading"><a href="main.html"
			class="Bredcrumbs">Admission</a> <span class="Bredcrumbs">&gt;&gt;
		Admission Form &gt;&gt;</span></span></td>
	</tr>
	<tr>
		<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="10"><img src="images/Tright_03_01.gif"></td>
				<td width="100%" background="images/Tcenter.gif" class="body">
				<div align="left"><strong class="boxheader">
				Admission Form</strong></div>
				</td>
				<td width="10"><img src="images/Tright_1_01.gif"
					width="9" height="29"></td>
			</tr>
			<tr>
				<td valign="top" background="images/Tright_03_03.gif"></td>
				<td valign="top" class="news">
				<table width="100%" cellspacing="1" cellpadding="2">
					<tr bgcolor="#FFFFFF">
						<td height="20" colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td width="21%" height="25" class="row-odd">
						<div align="right">Application Number:</div>
						</td>
						<td width="26%" height="25" class="row-even"><label>
						<html:text property="applicationNumber" size="15" maxlength="15"></html:text>
						 </label></td>
						<td width="27%" class="row-odd">
						</td>
						<td width="26%" class="row-even"></td>
					</tr>
					<tr>
						<td height="25" class="row-odd">
						<div align="right">First Name:</div>
						</td>
						<td height="25" class="row-even"><html:text property="firstName" size="15" maxlength="15"></html:text></td>
						<td class="row-odd">
						<div align="right">Religion:</div>
						</td>
						<td class="row-even">
							<html:select property="religion" styleClass="body">
									<html:option value="0">- Select -</html:option>
									<html:option value="hindu">hindu</html:option>
									<html:option value="muslim">muslim</html:option>
							</html:select>
						
						</td>
					</tr>
					<tr>
						<td height="25" class="row-odd">
						<div align="right">Middle Name:</div>
						</td>
						<td height="25" class="row-even"><html:text property="middleName" size="15" maxlength="15"></html:text></td>
						<td class="row-odd">
						<div align="right">Sub Religion:</div>
						</td>
						<td class="row-even"><html:select property="subReligion" styleClass="body">
									<html:option value="0">- Select -</html:option>
									<html:option value="catholic">catholic</html:option>
									<html:option value="protestant">protestant</html:option>
							</html:select></td>
					</tr>
					<tr>
						<td height="25" class="row-odd">
						<div align="right">Last Name:</div>
						</td>
						<td height="25" class="row-even"><html:text property="lastName" size="15" maxlength="15"></html:text></td>
						<td class="row-odd">
						<div align="right">Date of Birth:</div>
						</td>
						<td class="row-even"><!-- calendar attaches to existing form element -->
						<html:text property="dateOfBirth" size="10" maxlength="10"></html:text> <script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'dateOfBirth'
							});
						</script></td>
					</tr>
					<tr>
						<td height="25" class="row-odd">
						<div align="right">Gender:</div>
						</td>
						<td height="25" class="row-even">
							<html:radio property="gender" value="Male">Male</html:radio>
							<html:radio property="gender" value="Female">Female</html:radio>
							 </td>
						<td class="row-odd">
						<div align="right">Place of Birth:</div>
						</td>
						<td class="row-even"><html:text property="birthPlace" size="15" maxlength="15"></html:text></td>
					</tr>
					<tr>
						<td class="row-odd">
						<div align="right">Cast Category:</div>
						</td>
						<td class="row-even"><html:select property="castCategory" styleClass="row-even">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="casteList" name="admissionFormForm" label="casteName" value="casteId"/>
							</html:select></td>
						<td class="row-odd">
						<div align="right">Nationality:</div>
						</td>
						<td class="row-even"><html:text property="nationality" size="15" maxlength="15"></html:text></td>
					</tr>
					<tr>
						<td class="row-odd">
						<div align="right">Country of Birth:</div>
						</td>
						
						<td class="row-even"><html:select property="country" styleClass="row-even" onchange="getStates(this,'birthState')">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="countries" name="admissionFormForm" label="name" value="id" />
							</html:select></td>
						<td class="row-odd">
						<div align="right">e-mail:</div>
						</td>
						<td class="row-even"><span class=" heading"> <html:text property="emailId" size="15" maxlength="15"></html:text> </span></td>
					</tr>
					<tr>
						<td class="row-odd">
						<div align="right">State of Birth:</div>
						</td>
						<td class="row-even"><html:select property="birthState" styleId="birthState" styleClass="row-even">
									<html:option value="0">- Select -</html:option>
									
							</html:select></td>
						<td class="row-odd">
						<div align="right">Belongs to :</div>
						</td>
						<td class="row-even">
								<html:radio property="areaType" value="Rural">Rural</html:radio>
								<html:radio property="areaType" value="Urban">Urban</html:radio></td>
					</tr>
					<tr>
						<td class="row-odd">
						<div align="right">Mother tongue:</div>
						</td>
						<td class="row-even"><html:select property="motherTongue" styleClass="row-even">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="mothertongues" name="admissionFormForm" label="name" value="id"/>
							</html:select></td>
						<td class="row-odd">
						<div align="right">Blood Group:</div>
						</td>
						<td class="row-even"><html:select property="bloodGroup" styleClass="body">
									<html:option value="0">- Select -</html:option>
									<html:option value="O+ve">O+ve</html:option>
									<html:option value="O+ve">O+ve</html:option>
									<html:option value="O+ve">O+ve</html:option>
									<html:option value="O+ve">O+ve</html:option>
							</html:select></td>
					</tr>
					<tr>
						<td class="row-odd">
						<div align="right">Region:</div>
						</td>
						<td class="row-even"><html:select property="region" styleClass="row-even">
									<html:option value="0">- Select -</html:option>
									<html:option value="KA">Kannada</html:option>
									<html:option value="TL">telugu</html:option>
									<html:option value="OR">Oriya</html:option>
									<html:option value="OT">Other</html:option>
							</html:select></td>
						<td class="row-odd">
						<div align="right">Phone:</div>
						</td>
						<td class="row-even"><html:text property="phone1" size="4" maxlength="4"></html:text>
						 <html:text property="phone2" size="4" maxlength="15"></html:text> 
						<html:text property="phone3" size="10" maxlength="15"></html:text> </td>
					</tr>

					<tr>
						<td class="row-odd">
						<div align="right">Resident Category:</div>
						</td>
						<td class="row-even"><html:radio property="nriResident" value="true" styleClass="row-even">NRI</html:radio>
									<html:radio property="nriResident" value="false" styleClass="row-even">RI</html:radio>				
									</td>
						<td class="row-odd">&nbsp;</td>
						<td class="row-even">&nbsp;</td>
					</tr>
					<tr>
						<td height="25" colspan="4" class=" heading">
						<table width="100%" height="90" border="0" cellpadding="0"
							cellspacing="1">

							<tr class="row-white">
								<td width="194" height="20" class="row-odd">
								<div align="right"><span class="row-odd">Preferences</span></div>
								</td>
								<td width="241" class="row-odd">
								<div align="center">Program Type</div>
								</td>
								<td width="232" class="row-odd">
								<div align="center">Program</div>
								</td>
								<td width="255" class="row-odd">
								<div align="center">Course</div>
								</td>
							</tr>
							<tr class="row-even">
								<td height="20" class="row-odd">
								<div align="right">First Preference:</div>
								</td>
								<td class="bodytext">
								<div align="center"><html:select property="progTypePref1" styleClass="row-even" onchange="getPrograms(this,'progPref1')">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="programtypeList" name="admissionFormForm" label="programTypeName" value="programTypeId"/>
							</html:select></div>
								</td>
								<td class="bodytext">
								<div align="center"><html:select property="progPref1" styleClass="row-even" styleId="progPref1" onchange="getCourse(this,'coursePref1')">
									<html:option value="0">- Select -</html:option>
									
							</html:select></div>
								</td>
								<td class="bodytext">
								<div align="center"><html:select property="coursePref1" styleClass="row-even" styleId="coursePref1">
									<html:option value="0">- Select -</html:option>
									
							</html:select></div>
								</td>
							</tr>
							<tr class="row-even">
								<td height="20" class="row-odd">
								<div align="right">Second Preference:</div>
								</td>
								<td class="row-white">
								<div align="center"><span class="bodytext"> <html:select property="progTypePref2" styleClass="row-even" onchange="getPrograms(this,'progPref2')">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="programtypeList" name="admissionFormForm" label="programTypeName" value="programTypeId"/>
							</html:select> </span></div>
								</td>
								<td class="row-white">
								<div align="center"><span class="bodytext"> <html:select property="progPref2" styleClass="row-even" styleId="progPref2" onchange="getCourse(this,'coursePref2')">
									<html:option value="0">- Select -</html:option>
									
							</html:select></span></div>
								</td>
								<td class="row-white">
								<div align="center"><span class="bodytext"> <html:select property="coursePref2" styleClass="row-even" styleId="coursePref2">
									<html:option value="0">- Select -</html:option>
									
							</html:select></span></div>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="4" class=" heading">Address Details:</td>
					</tr>
					<tr>
						<td height="25" colspan="4">
						<table width="100%" height="90" border="0" cellpadding="0"
							cellspacing="1">
							<tr class="row-odd">
								<td width="290">
								<div align="right">Permanent Address:</div>
								</td>
								<td height="25" class="row-even"><label></label></td>
								<td width="176" valign="top" class="row-even">
								<div align="right"></div>
								</td>
								<td valign="top" class="row-even"><label></label></td>
							</tr>
							<tr class="row-white">
								<td height="20" class="row-odd">
								<div align="right">Address. 1</div>
								</td>
								<td width="290" height="20" class="bodytext"><html:text property="permAddr.address1" size="15" maxlength="15" styleId="permAddraddress1"></html:text></td>
								<td class="row-odd">
								<div align="right">
								<div align="right">Address 2</div>
								</div>
								</td>
								<td width="166" class="bodytext"><html:text property="permAddr.address2" size="15" maxlength="15" styleId="permAddraddress2"></html:text></td>
							</tr>
							<tr class="row-even">
								<td height="20" class="row-odd">
								<div align="right">City:</div>
								</td>
								<td height="20" class="bodytext"><html:select property="permAddr.city" styleClass="row-even" styleId="permAddrcity">
									<html:option value="0">- Select -</html:option>
									
							</html:select></td>
								<td class="row-odd">
								<div align="right">State:</div>
								</td>
								<td class="bodytext"><html:select property="permAddr.state" styleClass="row-even" styleId="permAddrstate" onchange="getCities(this,'permAddrcity')">
									<html:option value="0">- Select -</html:option>
									
							</html:select></td>
							</tr>
							<tr class="row-even">
								<td height="20" class="row-odd">
								<div align="right">Country:</div>
								</td>
								<td height="20" class="row-white"><html:select property="permAddr.country" styleClass="row-even" styleId="permAddrcountry" onchange="getStates(this,'permAddrstate')">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="countries" name="admissionFormForm" label="name" value="id"/>
							</html:select></td>
								<td class="row-odd">&nbsp;</td>
								<td class="row-white">&nbsp;</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="4" class="body">Is present address
						same as permanent address ? <html:radio property="sameTempAddr" value="true" onclick="copyAddresstoTemp()">Yes</html:radio>
  						<html:radio property="sameTempAddr" value="false" onclick="cleartempAddress()">No</html:radio></td>
					</tr>
					<tr>
						<td height="25" colspan="4">
						<table width="100%" height="90" border="0" cellpadding="0"
							cellspacing="1">
							<tr class="row-odd">
								<td width="278">
								<div align="right">Current Address:</div>
								</td>
								<td height="25" class="row-even"><label></label></td>
								<td width="176" valign="top" class="row-even">
								<div align="right"></div>
								</td>
								<td valign="top" class="row-even"><label></label></td>
							</tr>
							<tr class="row-white">
								<td height="20" class="row-odd">
								<div align="right">Address. 1</div>
								</td>
								<td width="302" height="20" class="bodytext"><html:text property="tempAddr.address1" size="15" maxlength="15" styleId="tempAddraddress1"></html:text></td>
								<td class="row-odd">
								<div align="right">
								<div align="right">Address 2</div>
								</div>
								</td>
								<td width="166" class="bodytext"><html:text property="tempAddr.address2" size="15" maxlength="15" styleId="tempAddraddress2"></html:text></td>
							</tr>
							<tr class="row-even">
								<td height="20" class="row-odd">
								<div align="right">City:</div>
								</td>
								<td height="20" class="bodytext"><html:select property="tempAddr.city" styleClass="row-even" styleId="tempAddrcity">
									<html:option value="0">- Select -</html:option>
									
							</html:select></td>
								<td class="row-odd">
								<div align="right">State:</div>
								</td>
								<td class="bodytext"><html:select property="tempAddr.state" styleClass="row-even" styleId="tempAddrstate" onchange="getCities(this,'tempAddrcity')">
									<html:option value="0">- Select -</html:option>
									</html:select></td>
							</tr>
							<tr class="row-even">
								<td height="20" class="row-odd">
								<div align="right">Country:</div>
								</td>
								<td height="20" class="row-white"><html:select property="tempAddr.country" styleClass="row-even" styleId="tempAddrcountry" onchange="getStates(this,'tempAddrstate')">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="countries" name="admissionFormForm" label="name" value="id"/>
							</html:select></td>
								<td class="row-odd">&nbsp;</td>
								<td class="row-white">&nbsp;</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="4"><span class="heading">Parent's
						Details:</span></td>
					</tr>
					<tr>
						<td height="25" colspan="4">
						<table width="100%" height="111" border="0" cellpadding="0"
							cellspacing="1">
							<tr class="row-odd">
								<td width="277">
								<div align="right">Father's Name::</div>
								</td>
								<td height="25" class="row-even"><label><html:text property="fatherName" size="15" maxlength="15"></html:text> </label></td>
								<td width="135" class="row-odd">
								<div align="right">Education:</div>
								</td>
								<td valign="top" class="row-even"><label> <html:text  styleClass="row-even" property="prntEducation" size="15" maxlength="15"></html:text> </label></td>
								<td class="row-odd">
								<div align="right">Occupation:</div>
								</td>
								<td valign="top" class="row-even"><span class="row-even">
								<html:select property="prntOccupation" styleClass="row-even">
									<html:option value="0">- Select -</html:option>
									<html:option value="KA">Kannada</html:option>
									<html:option value="TL">telugu</html:option>
									<html:option value="OR">Oriya</html:option>
									<html:option value="OT">Other</html:option>
							</html:select> </span></td>
							</tr>
							<tr class="row-white">
								<td height="20" class="row-odd">
								<div align="right">Mother's Name:</div>
								</td>
								<td width="152" height="20" class="row-even"><html:text property="motherName" size="15" maxlength="15"></html:text></td>
								<td class="row-odd">
								<div align="right">
								<div align="right">Phone No.</div>
								</div>
								</td>
								<td width="132" class="row-even"><html:text property="parentPhone" size="15" maxlength="15"></html:text></td>
								<td width="104" class="row-odd">
								<div align="right">Income:</div>
								</td>
								<td width="120" class="row-even"><html:text property="parentIncome" size="11" maxlength="15"></html:text></td>
							</tr>
							<tr class="row-even">
								<td height="20" class="row-odd">
								<div align="right">email:</div>
								</td>
								<td height="20" class="row-even"><html:text property="parentEmail" size="11" maxlength="15"></html:text></td>
								
								<td valign="top" class="row-odd">
								<div align="right"> Income Currency type:</div>
								</td>
								<td valign="top" class="row-even"><span class="row-white">
								<html:select property="currencyId" styleClass="row-even">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="currencies" name="admissionFormForm" label="name" value="id"/>
							</html:select> </span></td>
							</tr>
							<tr class="row-even">
								<td height="20" valign="top" class="row-odd">
								<div align="right">Permanent Address:</div>
								</td>
								<td height="20" class="row-even"><html:textarea
									property="parentAddress.address1" cols="15" rows="3"></html:textarea></td>
								<td valign="top" class="row-odd">
								<div align="right">Country:</div>
								</td>
								<td valign="top" class="row-even"><span class="row-white">
								<html:select property="parentAddress.country" styleClass="row-even" onchange="getStates(this,'parentState')">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="countries" name="admissionFormForm" label="name" value="id"/>
							</html:select> </span></td>
								<td valign="top" class="row-odd">
								<div align="right">State:</div>
								</td>
								<td valign="top" class="row-even"><span class="row-white">
								<html:select property="parentAddress.state" styleClass="row-even" styleId="parentState" onchange="getCities(this,'parentCity')">
									<html:option value="0">- Select -</html:option>
									
							</html:select> </span></td>
							</tr>
							<tr class="row-even">
								<td height="20" class="row-odd">&nbsp;</td>
								<td height="20" class="row-even">&nbsp;</td>
								<td class="row-odd">
								<div align="right">City:</div>
								</td>
								<td class="row-even"><span class="row-white"> <html:select property="parentAddress.city" styleClass="row-even" styleId="parentCity">
									<html:option value="0">- Select -</html:option>
									
							</html:select></span></td>
								<td class="row-odd">
								<div align="right">Zip Code:</div>
								</td>
								<td class="row-even"><html:text property="parentAddress.zipcode" size="11" maxlength="15"></html:text></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				<div align="center">
				<table width="100%" height="27" border="0" cellpadding="1"
					cellspacing="2">
					<tr>
						<td class="heading">Upload Documents</td>
					</tr>
					<tr>
						<td>
						<table width="100%" height="90" border="0" cellpadding="0"
							cellspacing="1">
							<tr class="row-odd">
								<td width="434">
								<div align="right">10 th Marks Card:</div>
								</td>
								<td height="25" class="row-even"><label>
								 <html:file property="hscMark"></html:file> </label></td>
							</tr>
							<tr class="row-white">
								<td height="20" class="row-odd">
								<div align="right">10+ 2 Marks Card:</div>
								</td>
								<td width="492" height="20" class="row-even"><html:file property="pucMark"></html:file></td>
							</tr>
							<tr class="row-even">
								<td height="20" class="row-odd">
								<div align="right">Photograph:</div>
								</td>
								<td height="20" class="row-even"><html:file property="photo"></html:file></td>
							</tr>

						</table>
						</td>
					</tr>
				<html:hidden property="method" value=""/>
				<tr>
              <td height="25" colspan="4" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="46%" height="35"><div align="right"><html:link href="#" styleClass="button" onclick="submitAdmissionForm('submitAdmissionForm')" >Submit</html:link> </div></td>
                    <td width="1%"></td>
                    <td width="53%"><a href="#" class="button">Cancel</a></td>
                  </tr>
              </table></td>
            </tr>
				</table>
				

				</div>
				</td>
				

				<td width="10" valign="top" background="images/Tright_3_3.gif"
					class="news"></td>
			</tr>
			<tr>
				<td><img src="images/Tright_03_05.gif"></td>
				<td background="images/Tcenter.gif"></td>
				<td><img src="images/Tright_02.gif" width="9"
					height="29"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</html:form>
</body>
</html:html>