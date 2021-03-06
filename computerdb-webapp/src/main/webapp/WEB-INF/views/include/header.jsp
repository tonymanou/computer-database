<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<!-- Bootstrap -->
	<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
	<link href="<c:url value="/css/font-awesome.css" />" rel="stylesheet" media="screen">
	<link href="<c:url value="/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<c:url value="/" />"><fmt:message key="title.home" /></a>
			<a class="pull-right locale-flag" href="<c:url value="?lang=en" />">
				<img alt="en" src="<c:url value="/images/flag-en.png" />">
			</a>
			<a class="pull-right locale-flag" href="<c:url value="?lang=fr" />">
				<img alt="fr" src="<c:url value="/images/flag-fr.png" />">
			</a>
		</div>
	</header>
