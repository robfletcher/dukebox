<%@ page import="dukebox.Track" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<title><g:message code="track.show" args="[trackInstance.title, trackInstance.artist]" default="{0} by {1}"/></title>
	</head>
	<body>
		<g:applyLayout name="menu">
			<li><g:link class="list" action="list"><g:message code="track.list" default="Track List"/></g:link></li>
			<li><g:link class="create" action="create"><g:message code="track.create" default="New Track"/></g:link></li>
		</g:applyLayout>
		<div class="body">
			<g:if test="${message}">
				<div class="message"><g:message code="${message}" args="${args}" default="${defaultMessage}"/></div>
			</g:if>
			<g:form>
				<g:hiddenField name="id" value="${trackInstance?.id}"/>
				<g:render template="trackDetails" bean="${trackInstance}"/>
				<div class="buttons">
					<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'edit', 'default': 'Edit')}"/></span>
					<span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'delete', 'default': 'Delete')}" onclick="return confirm('${message(code: 'delete.confirm', 'default': 'Are you sure?')}');"/></span>
				</div>
			</g:form>
		</div>
	</body>
</html>
