<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<style type="text/css">
.response {
	color: red;
	margin-left: -60px;
}

.info {
	color: green;
	font-weight: 500;
	margin-top: -30px;
	margin-left: 170px;
}
</style>


<script type="text/javascript">
<!--
	// Form validation code will come here.
	function validate() {

		var hostName = new RegExp(
				"^([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]{0,61}[a-zA-Z0-9])(\.([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]{0,61}[a-zA-Z0-9]))*$");
		if (document.addInstanceForm.port.value == ""
				|| isNaN(document.addInstanceForm.port.value)
				|| document.addInstanceForm.port.value > 99999) {
			alert("Please provide valid Port Number!");
			document.addInstanceForm.port.focus();
			return false;
		}
		if (document.addInstanceForm.host.value == ""
				|| !document.addInstanceForm.host.value.match(hostName)) {
			alert("Please provide valid address of host machine!");
			document.addInstanceForm.host.focus();
			return false;
		}
		if (document.addInstanceForm.description.value == "") {
			alert("Please provide description!");
			document.addInstanceForm.description.focus();
			return false;
		}
		return (true);
	}
//-->
</script>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/form.css" />
<form name="addInstanceForm" class="form" autocomplete="off"
	action="addInstance" method="POST" onsubmit="return(validate());">
	<div id="form">
		<table>
			<c:if test="${failure}">
				<tr>
					<td colspan="2" align="center">
						<h5 class="response">**Failure</h5>
					</td>
				</tr>
			</c:if>
			<c:if test="${noConnectivity}">
				<tr>
					<td colspan="2" align="center">
						<h5 class="response">
							**Could not connect to Port number ${instance.port} on
							${instance.host}.<br /> For adding this instance, please uncheck
							the Activate Now checkbox or start the server.
						</h5>
					</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="2" align="left"><h1>Add a Neo4j Instance</h1></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><c:if
						test="${not empty instance and not noConnectivity and not unableToCreateIndex}">
						<h5 class="response">**Port ${instance.port} is already in
							used on host machine ${instance.host}</h5>
					</c:if></td>
			</tr>
			
			<tr>
				<td colspan="2" align="center"><c:if
						test="${not empty instance and not noConnectivity and unableToCreateIndex}">
						<h5 class="response">**Unable to create Index name</h5>
					</c:if></td>
			</tr>
			
			<tr>
				<td><label>Port Number:</label></td>
				<td><input class="text" type="text" name="port"
					value="${instance.port}" /></td>

			</tr>
			<tr>
				<td><label>Host:</label></td>
				<td><input class="text" type="text" name="host"
					value="${instance.host}" /></td>
			</tr>

			<tr>
				<td><label>Protocol:</label></td>
				<td><input class="text" type="text" name="protocol"
					value="${instance.protocol}" />
				<div class="info">(e.g. Http, Https)</div></td>
			</tr>

			<tr>
				<td><label>Node Index Name:</label></td>
				<td><input class="text" type="text" name="nodeIndex"
					value="${instance.nodeIndex}" /></td>
			</tr>

			<tr>
				<td><label>Relation Index Name:</label></td>
				<td><input class="text" type="text" name="relationIndex"
					value="${instance.relationIndex}" /></td>
			</tr>

			<tr>
				<td><label>Database Path:</label></td>
				<c:choose>
					<c:when test="${not empty instance}">
						<td><input class="text" type="text" name="dbPath"
							value="${instance.dbPath}" />
						<div class="info">Default path is db/data</div></td>
					</c:when>
					<c:otherwise>
						<td><input class="text" type="text" name="dbPath"
							value="db/data" />
						<div class="info">Default path is db/data</div></td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td><label>Description:</label></td>
				<td><textarea class="description" name="description">${instance.description}</textarea></td>
			</tr>
			<tr>
				<td><label>Activate Now:</label></td>
				<c:choose>
					<c:when test="${instance.active}">
						<td><input class="checkbox" type="checkbox" name="active"
							checked /></td>
					</c:when>
					<c:otherwise>
						<td><input class="checkbox" type="checkbox" name="active" /></td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td colspan="2" align="left"><input class="submit"
					type="submit" value="Add" /> <input type=button class="cancel"
					onClick="location.href='${pageContext.servletContext.contextPath}/auth/listInstances'"
					value='Cancel' /></td>
			</tr>
		</table>
	</div>
</form>