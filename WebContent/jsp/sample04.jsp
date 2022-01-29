<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>

<html >
	<head>
		<title>FCKeditor - JSP Sample</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="robots" content="noindex, nofollow" />
		<link href="css/fck/sample.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="images/fckeditor.gif"
				type="image/x-icon" />
	</head>
	<body>
		<h1>FCKeditor - JSP - Sample 4</h1>
		This sample shows how to change the editor toolbar.
		<hr />
		<br />
		<html:form action="/CreateTemplate" method="post">
		<html:hidden property="method" styleId="method"	value="createTemplate" />
			<FCK:editor instanceName="EditorDefault"  toolbarSet="Basic">
				<jsp:attribute name="value">This is some <strong>sample text
					</strong>. You are using FCK
				</jsp:attribute>
			</FCK:editor>
			<br />
			<input type="submit" value="Submit" />
		</html:form>
	</body>
</html>