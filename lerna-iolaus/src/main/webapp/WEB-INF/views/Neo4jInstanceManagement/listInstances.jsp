
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<header>
	<h2 class="dataTableHeading">Instance Management</h2>
	<h4 class="dataTableByline">Instances you could edit, delete and add.</h4>
	<br/>
</header>




<style type="text/css">
.submit {
	background-color: #474E69;
	color: #FFFFFF;
	width: 130px;
	height: 30px;
	weight: bold;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	margin-bottom: 3px;
}

.dataTableHeading{
	font-weight: "900";
	display:block;
	color:#72587F;
	margin-top: 25px;
}

.dataTableByline{
	weight: bold; 
	color:#2E0854;
	font-size: 20 px;
}

.dataTableSubHeading{
	weight: bold; 
	color:#2E0854;
	font-size: 25 px;
	margin-left:50%;
}

.dataTable{
}

.response{
color:red;
margin-left:40%;
}
</style>

<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		$('#selectall').click(function() {
			$('.selected').prop('checked', isChecked('selectall'));
		});
	});
	function isChecked(checkboxId) {
		var id = '#' + checkboxId;
		return $(id).is(":checked");
	}
	function resetSelectAll() {
		// if all checkbox are selected, check the selectall checkbox
		// and viceversa
		if ($(".selected").length == $(".selected:checked").length) {
			$("#selectall").attr("checked", "checked");
		} else {
			$("#selectall").removeAttr("checked");
		}

		if ($(".selected:checked").length > 0) {
			$('#edit').attr("disabled", false);
		} else {
			$('#edit').attr("disabled", true);
		}
	}
	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {

		});
	});
	$(document).ready(function() {
		$("input[type=a]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		$("ul.pagination1").quickPagination({
			pageSize : "10"
		});
		$("ul.pagination2").quickPagination({
			pageSize : "10"
		});

	});
</script>
<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		$('#listinstance').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false,
			"aoColumns" : [ {
				"bSortable" : false
			}, null, null, null, null, null, null ]
		});
	});
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>


<header>
	<h3 class="dataTables">List of Instances.</h3>
</header>

<div class="container">	
	<c:if test="${not empty instanceId}">
	<h4 class="response">Instance id is ${instanceId}</h4>
	</c:if>
	<c:choose>
		<c:when test="${not empty instanceList}">

			<form method="POST">
				<input type=button class="submit"
					onClick="location.href='${pageContext.servletContext.contextPath}/auth/addInstance'"
					value='Add a Instance'> <input type="submit" class="submit"
					value="Delete Instances"
					onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/deleteInstances'" />
				<hr />
				<table style="width: 100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable" id="listinstance" class="dataTable">
					<thead>
						<tr>
							<th align="left"><input type="checkbox" id="selectall">All</th>
							<th>Id</th>
							<th>Port Number</th>
							<th>Host</th>
							<th>Description</th>
							<th>Status</th>
							<th></th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="instance" items="${instanceList}">
							<tr>
								<td width="10%"><input type="checkbox" class="selected"
									name="selected" value='<c:out value="${instance.id}"></c:out>' /></td>
								<td width="5%" align="center"><c:out
										value="${instance.id}"></c:out></td>
								<td width="10%" align="center"><c:out
										value="${instance.port}"></c:out></td>
								<td width="15%" align="center"><c:out
										value="${instance.host}"></c:out></td>
								<td width="40%" align="center"><c:out
										value="${instance.description}"></c:out></td>
								<c:choose>
									<c:when test="${instance.active}">
										<td width="10%" align="center"><c:out value="Active"></c:out></td>
									</c:when>
									<c:otherwise>
										<td width="10%" align="center"><c:out value="Inactive"></c:out></td>
									</c:otherwise>
								</c:choose>
								<td width="10%" align="center"><input type=button
									class="submit"
									onClick="location.href='${pageContext.servletContext.contextPath}/auth/editInstance/${instance.id}'"
									value='Edit'></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br/>
			</form>
		</c:when>
		<c:otherwise>
			<input type=button class="submit"
				onClick="location.href='${pageContext.servletContext.contextPath}/auth/addInstance'"
				value='Add Instance'>
			<hr />
			<br />
			Empty List
		</c:otherwise>
	</c:choose>
</div>
