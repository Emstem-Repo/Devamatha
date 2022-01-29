<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript">
	checked = false;
    function checkAll () {
		if (checked == false) {
			checked = true;
		} else {
			checked = false;
		}
		
		for (var i=0;i<document.forms[0].elements.length;i++)
		{	
			var e=document.forms[0].elements[i];
			if ((e.type=='checkbox'))
			{
				e.checked=checked;
			}
		}
		
    }

    function getDetails(applicationNumber,appliedYear) {
       	var url  = "ApplicantDetails.do?method=getApplicantDetails&applicationNumber="+applicationNumber+"&applicationYear="+appliedYear;
    	myRef = window.open(url,"ViewApplicantDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
   }

    function cancelAction() {
    	document.location.href = "candidatesearch.do?method=submitCandidateSearch";
    }

</SCRIPT>
<html:form action="/candidatesearch" focus="selectall">

	<html:hidden property="method" styleId="method"
		value="removeSelectedCandidates" />
			<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="candidateSearchForm" />
	<table width="98%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> Admission Report Result</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>

							<td height="15" colspan="10" class="row-white">
							<div align="left"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>

							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">



									
									<c:set var="temp" value="0" />
										<display:table export="true" name="sessionScope.studentSearch" requestURI="" defaultorder="descending" pagesize="10">
										<display:setProperty name="export.xls.filename" value="applicationreport.xls"/>
										<display:setProperty name="export.xml.filename" value="applicationreport.xml"/>
										<display:setProperty name="export.csv.filename" value="applicationreport.csv"/>
										<c:choose>
										<c:when test="${temp == 0}">											
											<display:column style="text-align: center;" property="applnNo" sortable="true" title="Application Number" class="row-even" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="name" sortable="true" title="Name" class="row-even" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="totalWeightage" sortable="true" title="Total Weightage" class="row-even" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="dateOfBirth" sortable="true" title="Date of Birth" class="row-even" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="email" sortable="true" title="Email" class="row-even" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="gender" sortable="true" title="Gender" class="row-even" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="bloodGroup" sortable="true" title="Blood Group" class="row-even" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="challanNo" sortable="true" title="Challan Number" class="row-even" headerClass="row-odd"/>
																						
											<display:column style="padding-left: 35px;" property="programTypeName" media="csv excel xml pdf" title="Program Type Name" />
											<display:column style="padding-left: 35px;" property="programName" media="csv excel xml pdf" title="Program Name" />
											<display:column style="padding-left: 35px;" property="courseName" media="csv excel xml pdf" title="Course Name" />
											<display:column style="padding-left: 35px;" property="journalNo" media="csv excel xml pdf" title="Journal No." />
											<display:column style="padding-left: 35px;" property="appliedYear" media="csv excel xml pdf" title="Acedamic Year" />
											<display:column style="padding-left: 35px;" property="admittedThrough" media="csv excel xml pdf" title="Admitted Through" />

											<display:column style="padding-left: 35px;" property="residentCategory" media="csv excel xml pdf" title="Resident Category" />
											<display:column style="padding-left: 35px;" property="religion" media="csv excel xml pdf" title="Religion" />
											<display:column style="padding-left: 35px;" property="religionOther" media="csv excel xml pdf" title="Religion Other" />
											<display:column style="padding-left: 35px;" property="subReligion" media="csv excel xml pdf" title="Sub Religion" />
											<display:column style="padding-left: 35px;" property="subReligionOther" media="csv excel xml pdf" title="Sub Religion Other" />
											<display:column style="padding-left: 35px;" property="castCategory" media="csv excel xml pdf" title="Cast Category" />
											<display:column style="padding-left: 35px;" property="castCategoryOther" media="csv excel xml pdf" title="Cast Category Other" />
											<display:column style="padding-left: 35px;" property="placeOfBirth" media="csv excel xml pdf" title="Place Of Birth" />
											<display:column style="padding-left: 35px;" property="stateOfBirth" media="csv excel xml pdf" title="State Of Birth" />
											<display:column style="padding-left: 35px;" property="stateOfBirthOther" media="csv excel xml pdf" title="State Of Birth Other" />
											<display:column style="padding-left: 35px;" property="countryOfBirth" media="csv excel xml pdf" title="Country Of Birth" />
											<display:column style="padding-left: 35px;" property="belongsTo" media="csv excel xml pdf" title="Belongs To" />
											<display:column style="padding-left: 35px;" property="nationality" media="csv excel xml pdf" title="Nationality" />
											<display:column style="padding-left: 35px;" property="studentPhoneNo" media="csv excel xml pdf" title="Student Phone Number" />
											<display:column style="padding-left: 35px;" property="studentMobileNo" media="csv excel xml pdf" title="Student Mobile Number" />
											<display:column style="padding-left: 35px;" property="passportNo" media="csv excel xml pdf" title="Passport Number" />
											<display:column style="padding-left: 35px;" property="passportIssuingCountry" media="csv excel xml pdf" title="Passport Issuing Country" />
											<display:column style="padding-left: 35px;" property="passportValidUpTo" media="csv excel xml pdf" title="Passport Valid Up To" />

											<display:column style="padding-left: 35px;" property="firstPrePT" media="csv excel xml pdf" title="First Preference Program Type" />
											<display:column style="padding-left: 35px;" property="firstPreProgram" media="csv excel xml pdf" title="First Preference Program" />
											<display:column style="padding-left: 35px;" property="firstCourse" media="csv excel xml pdf" title="First Preference Course" />
											<display:column style="padding-left: 35px;" property="secondPrePT" media="csv excel xml pdf" title="Second Preference Program Type" />
											<display:column style="padding-left: 35px;" property="secondPreProgram" media="csv excel xml pdf" title="Second Preference Program" />
											<display:column style="padding-left: 35px;" property="secondCourse" media="csv excel xml pdf" title="Second Preference Course" />
											<display:column style="padding-left: 35px;" property="thirdPrePT" media="csv excel xml pdf" title="Third Preference Program Type" />
											<display:column style="padding-left: 35px;" property="thirdPreProgram" media="csv excel xml pdf" title="Third Preference Program" />
											<display:column style="padding-left: 35px;" property="thirdCourse" media="csv excel xml pdf" title="Third Preference Course" />


											<display:column style="padding-left: 35px;" property="firstNameOfOra" media="csv excel xml pdf" title="First Name of Organisation" />
											<display:column style="padding-left: 35px;" property="firstDesignation" media="csv excel xml pdf" title="First Designation" />
											<display:column style="padding-left: 35px;" property="firstFromDate" media="csv excel xml pdf" title="First From Date" />
											<display:column style="padding-left: 35px;" property="firstTODate" media="csv excel xml pdf" title="First To Date" />
											<display:column style="padding-left: 35px;" property="secondNameOfOra" media="csv excel xml pdf" title="Second Name of Organisation" />
											<display:column style="padding-left: 35px;" property="secondDesignation" media="csv excel xml pdf" title="Second Designation" />
											<display:column style="padding-left: 35px;" property="secondFromDate" media="csv excel xml pdf" title="Second From Date" />
											<display:column style="padding-left: 35px;" property="secondTODate" media="csv excel xml pdf" title="Second To Date" />
											<display:column style="padding-left: 35px;" property="thirdNameOfOra" media="csv excel xml pdf" title="Third Name of Organisation" />
											<display:column style="padding-left: 35px;" property="thirdDesignation" media="csv excel xml pdf" title="Third Designation" />
											<display:column style="padding-left: 35px;" property="thirdFromDate" media="csv excel xml pdf" title="Third From Date" />
											<display:column style="padding-left: 35px;" property="thirdTODate" media="csv excel xml pdf" title="Third To Date" />

											<display:column style="padding-left: 35px;" property="permanentAddressline1" media="csv excel xml pdf" title="Permanent Address Line1" />
											<display:column style="padding-left: 35px;" property="permanentAddressline2" media="csv excel xml pdf" title="Permanent Address Line2" />
											<display:column style="padding-left: 35px;" property="permanentState" media="csv excel xml pdf" title="Permanent State" />
											<display:column style="padding-left: 35px;" property="permanentStateOther" media="csv excel xml pdf" title="Permanent State Others" />
											<display:column style="padding-left: 35px;" property="permanentCity" media="csv excel xml pdf" title="Permanent City" />
											<display:column style="padding-left: 35px;" property="permanentCountry" media="csv excel xml pdf" title="Permanent Country" />
											<display:column style="padding-left: 35px;" property="permanentZipCode" media="csv excel xml pdf" title="Permanent Zip Code" />

											<display:column style="padding-left: 35px;" property="currentAddressline1" media="csv excel xml pdf" title="Current Address Line1" />
											<display:column style="padding-left: 35px;" property="currentAddressline2" media="csv excel xml pdf" title="Current Address Line2" />
											<display:column style="padding-left: 35px;" property="currentState" media="csv excel xml pdf" title="Current State" />
											<display:column style="padding-left: 35px;" property="currentStateOther" media="csv excel xml pdf" title="Current State Others" />
											<display:column style="padding-left: 35px;" property="currentCity" media="csv excel xml pdf" title="Current City" />
											<display:column style="padding-left: 35px;" property="currentCountry" media="csv excel xml pdf" title="Current Country" />
											<display:column style="padding-left: 35px;" property="currentZipCode" media="csv excel xml pdf" title="Current Zip Code" />

											<display:column style="padding-left: 35px;" property="fatherName" media="csv excel xml pdf" title="Father's Name" />
											<display:column style="padding-left: 35px;" property="fatherEducation" media="csv excel xml pdf" title="Father's Education" />
											<display:column style="padding-left: 35px;" property="fatherIncome" media="csv excel xml pdf" title="Father's Income" />
											<display:column style="padding-left: 35px;" property="fatherIncomeCurrency" media="csv excel xml pdf" title="Father's Currency" />
											<display:column style="padding-left: 35px;" property="fatherOccupation" media="csv excel xml pdf" title="Father's Occupation" />
											<display:column style="padding-left: 35px;" property="fatherEmail" media="csv excel xml pdf" title="Father's Email" />
											
											
											
											<display:column style="padding-left: 35px;" property="motherName" media="csv excel xml pdf" title="Mother Name" />
											<display:column style="padding-left: 35px;" property="motherEducation" media="csv excel xml pdf" title="Mother Education" />
											<display:column style="padding-left: 35px;" property="motherIncome" media="csv excel xml pdf" title="Mother's Income" />
											<display:column style="padding-left: 35px;" property="motherIncomeCurrency" media="csv excel xml pdf" title="Mother's Currency" />
											<display:column style="padding-left: 35px;" property="motherOccupation" media="csv excel xml pdf" title="Mother's Occupation" />
											<display:column style="padding-left: 35px;" property="motherEmail" media="csv excel xml pdf" title="Mother's Email" />
											
											
											<display:column style="padding-left: 35px;" property="parentAdressLine1" media="csv excel xml pdf" title="Parent Adress Line1 " />
											<display:column style="padding-left: 35px;" property="parentAdressLine2" media="csv excel xml pdf" title="Parent Adress Line2" />
											<display:column style="padding-left: 35px;" property="parentAdressLine3" media="csv excel xml pdf" title="Parent Adress Line3" />
											<display:column style="padding-left: 35px;" property="parentCity" media="csv excel xml pdf" title="Parent City" />
											<display:column style="padding-left: 35px;" property="parentState" media="csv excel xml pdf" title="Parent State" />
											<display:column style="padding-left: 35px;" property="parentStateOther" media="csv excel xml pdf" title="Parent State Other" />
											<display:column style="padding-left: 35px;" property="parentCountry" media="csv excel xml pdf" title="Parent Country" />
											<display:column style="padding-left: 35px;" property="parentZipCode" media="csv excel xml pdf" title="Parent Zip Code" />
											<display:column style="padding-left: 35px;" property="parentPhone" media="csv excel xml pdf" title="Parent Phone" />
											<display:column style="padding-left: 35px;" property="parentMobileNo" media="csv excel xml pdf" title="Parent Mobile No" />
											
											<display:column style="padding-left: 35px;" property="appliedDate" media="csv excel xml pdf" title="Applied Date" />
											<display:column style="padding-left: 35px;" property="challanPaymentDate" media="csv excel xml pdf" title="Challan Payment Date (Application)" />
											<display:column style="padding-left: 35px;" property="isHandicaped" media="csv excel xml pdf" title="Is Handicaped" />
											<display:column style="padding-left: 35px;" property="handicapDetails" media="csv excel xml pdf" title="Handicap details" />
											<display:column style="padding-left: 35px;" property="feeChallanDate" media="csv excel xml pdf" title="Fee challan Date" />
											<display:column style="padding-left: 35px;" property="totalFeePaid" media="csv excel xml pdf" title="Total Fee Paid " />
											<display:column style="padding-left: 35px;" property="feeChallanNo" media="csv excel xml pdf" title="Fee Challan No" />
												<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<display:column style="text-align: center;" property="applnNo" sortable="true" title="Application Number" class="row-white" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="name" sortable="true" title="Name" class="row-white" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="totalWeightage" sortable="true" title="Total Weightage" class="row-white" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="dateOfBirth" sortable="true" title="Date of Birth" class="row-white" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="email" sortable="true" title="Email" class="row-white" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="gender" sortable="true" title="Gender" class="row-white" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="bloodGroup" sortable="true" title="Blood Group" class="row-white" headerClass="row-odd"/>
											<display:column style="text-align: center;" property="challanNo" sortable="true" title="Challan Number" class="row-white" headerClass="row-odd"/>

											<display:column style="padding-left: 35px;" property="programTypeName" media="csv excel xml pdf" title="Program Type Name" />
											<display:column style="padding-left: 35px;" property="programName" media="csv excel xml pdf" title="Program Name" />
											<display:column style="padding-left: 35px;" property="courseName" media="csv excel xml pdf" title="Course Name" />
											<display:column style="padding-left: 35px;" property="journalNo" media="csv excel xml pdf" title="Journal No." />
											<display:column style="padding-left: 35px;" property="appliedYear" media="csv excel xml pdf" title="Acedamic Year" />
											<display:column style="padding-left: 35px;" property="admittedThrough" media="csv excel xml pdf" title="Admitted Through" />

											<display:column style="padding-left: 35px;" property="residentCategory" media="csv excel xml pdf" title="Resident Category" />
											<display:column style="padding-left: 35px;" property="religion" media="csv excel xml pdf" title="Religion" />
											<display:column style="padding-left: 35px;" property="religionOther" media="csv excel xml pdf" title="Religion Other" />
											<display:column style="padding-left: 35px;" property="subReligion" media="csv excel xml pdf" title="Sub Religion" />
											<display:column style="padding-left: 35px;" property="subReligionOther" media="csv excel xml pdf" title="Sub Religion Other" />
											<display:column style="padding-left: 35px;" property="castCategory" media="csv excel xml pdf" title="Cast Category" />
											<display:column style="padding-left: 35px;" property="castCategoryOther" media="csv excel xml pdf" title="Cast Category Other" />
											<display:column style="padding-left: 35px;" property="placeOfBirth" media="csv excel xml pdf" title="Place Of Birth" />
											<display:column style="padding-left: 35px;" property="stateOfBirth" media="csv excel xml pdf" title="State Of Birth" />
											<display:column style="padding-left: 35px;" property="stateOfBirthOther" media="csv excel xml pdf" title="State Of Birth Other" />
											<display:column style="padding-left: 35px;" property="countryOfBirth" media="csv excel xml pdf" title="Country Of Birth" />
											<display:column style="padding-left: 35px;" property="belongsTo" media="csv excel xml pdf" title="Belongs To" />
											<display:column style="padding-left: 35px;" property="nationality" media="csv excel xml pdf" title="Nationality" />
											<display:column style="padding-left: 35px;" property="studentPhoneNo" media="csv excel xml pdf" title="Student Phone Number" />
											<display:column style="padding-left: 35px;" property="studentMobileNo" media="csv excel xml pdf" title="Student Mobile Number" />
											<display:column style="padding-left: 35px;" property="passportNo" media="csv excel xml pdf" title="Passport Number" />
											<display:column style="padding-left: 35px;" property="passportIssuingCountry" media="csv excel xml pdf" title="Passport Issuing Country" />
											<display:column style="padding-left: 35px;" property="passportValidUpTo" media="csv excel xml pdf" title="Passport Valid Up To" />

											<display:column style="padding-left: 35px;" property="firstPrePT" media="csv excel xml pdf" title="First Preference Program Type" />
											<display:column style="padding-left: 35px;" property="firstPreProgram" media="csv excel xml pdf" title="First Preference Program" />
											<display:column style="padding-left: 35px;" property="firstCourse" media="csv excel xml pdf" title="First Preference Course" />
											<display:column style="padding-left: 35px;" property="secondPrePT" media="csv excel xml pdf" title="Second Preference Program Type" />
											<display:column style="padding-left: 35px;" property="secondPreProgram" media="csv excel xml pdf" title="Second Preference Program" />
											<display:column style="padding-left: 35px;" property="secondCourse" media="csv excel xml pdf" title="Second Preference Course" />
											<display:column style="padding-left: 35px;" property="thirdPrePT" media="csv excel xml pdf" title="Third Preference Program Type" />
											<display:column style="padding-left: 35px;" property="thirdPreProgram" media="csv excel xml pdf" title="Third Preference Program" />
											<display:column style="padding-left: 35px;" property="thirdCourse" media="csv excel xml pdf" title="Third Preference Course" />

											<display:column style="padding-left: 35px;" property="firstNameOfOra" media="csv excel xml pdf" title="First Name of Organisation" />
											<display:column style="padding-left: 35px;" property="firstDesignation" media="csv excel xml pdf" title="First Designation" />
											<display:column style="padding-left: 35px;" property="firstFromDate" media="csv excel xml pdf" title="First From Date" />
											<display:column style="padding-left: 35px;" property="firstTODate" media="csv excel xml pdf" title="First To Date" />
											<display:column style="padding-left: 35px;" property="secondNameOfOra" media="csv excel xml pdf" title="Second Name of Organisation" />
											<display:column style="padding-left: 35px;" property="secondDesignation" media="csv excel xml pdf" title="Second Designation" />
											<display:column style="padding-left: 35px;" property="secondFromDate" media="csv excel xml pdf" title="Second From Date" />
											<display:column style="padding-left: 35px;" property="secondTODate" media="csv excel xml pdf" title="Second To Date" />
											<display:column style="padding-left: 35px;" property="thirdNameOfOra" media="csv excel xml pdf" title="Third Name of Organisation" />
											<display:column style="padding-left: 35px;" property="thirdDesignation" media="csv excel xml pdf" title="Third Designation" />
											<display:column style="padding-left: 35px;" property="thirdFromDate" media="csv excel xml pdf" title="Third From Date" />
											<display:column style="padding-left: 35px;" property="thirdTODate" media="csv excel xml pdf" title="Third To Date" />


											<display:column style="padding-left: 35px;" property="permanentAddressline1" media="csv excel xml pdf" title="Permanent Address Line1" />
											<display:column style="padding-left: 35px;" property="permanentAddressline2" media="csv excel xml pdf" title="Permanent Address Line2" />
											<display:column style="padding-left: 35px;" property="permanentState" media="csv excel xml pdf" title="Permanent State" />
											<display:column style="padding-left: 35px;" property="permanentStateOther" media="csv excel xml pdf" title="Permanent State Others" />
											<display:column style="padding-left: 35px;" property="permanentCity" media="csv excel xml pdf" title="Permanent City" />
											<display:column style="padding-left: 35px;" property="permanentCountry" media="csv excel xml pdf" title="Permanent Country" />
											<display:column style="padding-left: 35px;" property="permanentZipCode" media="csv excel xml pdf" title="Permanent Zip Code" />

											<display:column style="padding-left: 35px;" property="currentAddressline1" media="csv excel xml pdf" title="Current Address Line1" />
											<display:column style="padding-left: 35px;" property="currentAddressline2" media="csv excel xml pdf" title="Current Address Line2" />
											<display:column style="padding-left: 35px;" property="currentState" media="csv excel xml pdf" title="Current State" />
											<display:column style="padding-left: 35px;" property="currentStateOther" media="csv excel xml pdf" title="Current State Others" />
											<display:column style="padding-left: 35px;" property="currentCity" media="csv excel xml pdf" title="Current City" />
											<display:column style="padding-left: 35px;" property="currentCountry" media="csv excel xml pdf" title="Current Country" />
											<display:column style="padding-left: 35px;" property="currentZipCode" media="csv excel xml pdf" title="Current Zip Code" />

											<display:column style="padding-left: 35px;" property="fatherName" media="csv excel xml pdf" title="Father's Name" />
											<display:column style="padding-left: 35px;" property="fatherEducation" media="csv excel xml pdf" title="Father's Education" />
											<display:column style="padding-left: 35px;" property="fatherIncome" media="csv excel xml pdf" title="Father's Income" />
											<display:column style="padding-left: 35px;" property="fatherIncomeCurrency" media="csv excel xml pdf" title="Father's Currency" />
											<display:column style="padding-left: 35px;" property="fatherOccupation" media="csv excel xml pdf" title="Father's Occupation" />
											<display:column style="padding-left: 35px;" property="fatherEmail" media="csv excel xml pdf" title="Father's Email" />
											
											
											
											<display:column style="padding-left: 35px;" property="motherName" media="csv excel xml pdf" title="Mother Name" />
											<display:column style="padding-left: 35px;" property="motherEducation" media="csv excel xml pdf" title="Mother Education" />
											<display:column style="padding-left: 35px;" property="motherIncome" media="csv excel xml pdf" title="Mother's Income" />
											<display:column style="padding-left: 35px;" property="motherIncomeCurrency" media="csv excel xml pdf" title="Mother's Currency" />
											<display:column style="padding-left: 35px;" property="motherOccupation" media="csv excel xml pdf" title="Mother's Occupation" />
											<display:column style="padding-left: 35px;" property="motherEmail" media="csv excel xml pdf" title="Mother's Email" />
											
											
											<display:column style="padding-left: 35px;" property="parentAdressLine1" media="csv excel xml pdf" title="Parent Adress Line1 " />
											<display:column style="padding-left: 35px;" property="parentAdressLine2" media="csv excel xml pdf" title="Parent Adress Line2" />
											<display:column style="padding-left: 35px;" property="parentAdressLine3" media="csv excel xml pdf" title="Parent Adress Line3" />
											<display:column style="padding-left: 35px;" property="parentCity" media="csv excel xml pdf" title="Parent City" />
											<display:column style="padding-left: 35px;" property="parentState" media="csv excel xml pdf" title="Parent State" />
											<display:column style="padding-left: 35px;" property="parentStateOther" media="csv excel xml pdf" title="Parent State Other" />
											<display:column style="padding-left: 35px;" property="parentCountry" media="csv excel xml pdf" title="Parent Country" />
											<display:column style="padding-left: 35px;" property="parentZipCode" media="csv excel xml pdf" title="Parent Zip Code" />
											<display:column style="padding-left: 35px;" property="parentPhone" media="csv excel xml pdf" title="Parent Phone" />
											<display:column style="padding-left: 35px;" property="parentMobileNo" media="csv excel xml pdf" title="Parent Mobile No" />
											
											<display:column style="padding-left: 35px;" property="appliedDate" media="csv excel xml pdf" title="Applied Date" />
											<display:column style="padding-left: 35px;" property="challanPaymentDate" media="csv excel xml pdf" title="Challan Payment Date (Application)" />
											<display:column style="padding-left: 35px;" property="isHandicaped" media="csv excel xml pdf" title="Is Handicaped" />
											<display:column style="padding-left: 35px;" property="handicapDetails" media="csv excel xml pdf" title="Handicap details" />
											<display:column style="padding-left: 35px;" property="feeChallanDate" media="csv excel xml pdf" title="Fee challan Date" />
											<display:column style="padding-left: 35px;" property="totalFeePaid" media="csv excel xml pdf" title="Total Fee Paid" />
											<display:column style="padding-left: 35px;" property="feeChallanNo" media="csv excel xml pdf" title="Fee Challan No" />

												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>										
										</display:table>

						
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" height="54" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="50" class="heading">
							<div align="center">
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="0">

								<tr>
									<td>
									<div align="center">
									<table width="100%" height="27" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="52%" height="45">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="45%" height="35">
													<div align="right"><html:button property="cancel"
														onclick="cancelAction()" styleClass="formbutton">
														<bean:message key="knowledgepro.cancel" />
													</html:button></div>
													</td>
													<td width="2%"></td>
													<td width="53"></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
							</table>
							</div>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				 <tr>
      <td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
        <td background="images/TcenterD.gif" width="100%"></td>
        <td><img src="images/Tright_02.gif" height="29" width="9"></td>        
      </tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
