<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Generic Notification</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" href="<c:url value="/resources/images/sudarshan-logo.png"/>"/>
<link rel="shortcut icon" href="<c:url value="/resources/images/ico/favicon.ico"/>" type="image/x-icon">

</head>
<body>
	<div id="fullpage">
		<div class="header">

			<%@include file="../views/common/welcome-header.jspf"%>

		</div>
		
		<div>
			<%@include file="../views/common/message.jspf" %>
		</div>


		<div id="star-wrapper">
			<div class="container">

				<h4>Generic Example for Sending Email Notification</h4>

				<div>
					<form:form
						action="${pageContext.request.contextPath}/sendNotification"
						method="post" modelAttribute="sendNotification">
						
						<div class="col-sm-12">
							<form:label path="name">Name</form:label>
							<form:input path="name" required="true" disabled="${not empty isDisabled && isDisabled?'true':'false' }"/>
						</div>
						
						<div class="col-sm-12">
							<form:label path="email">Email</form:label>
							<form:input path="email" required="true" disabled="${not empty isDisabled && isDisabled?'true':'false' }"/>
						</div>
						
						<div class="col-sm-6" style="padding: 17px;margin-left: 54px;">
						<c:choose>
							<c:when test="${isDisabled}">
								<input type="submit" value="Submit" disabled="disabled"/>
								<a href="${pageContext.request.contextPath}/">Back</a>
							</c:when>
							<c:otherwise>
								<input type="submit" value="Submit"/>
							</c:otherwise>
						</c:choose>
							
						</div>

					</form:form>
				</div>
			</div>
		</div>
	</div>


	<div id="star-body-form">
		<div class="col-lg-12 col-sm-12 col-xs-12 copyrights">
			<%@include file="../views/login-footer.jsp"%>
		</div>
	</div>
</body>
</html>