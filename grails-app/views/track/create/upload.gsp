<%@ page import="dukebox.Track" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<title><g:message code="track.create" default="Create Track"/></title>
	</head>
	<body>
		<g:applyLayout name="menu">
			<li><g:link class="list" action="list"><g:message code="track.list" default="Track List"/></g:link></li>
		</g:applyLayout>
		<div class="body">
			<h1><g:message code="track.create" default="Create Track"/></h1>
			<g:if test="${flash.message}">
				<div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}"/></div>
			</g:if>
			<g:hasErrors bean="${command}">
				<div class="errors">
					<g:renderErrors bean="${command}" as="list"/>
				</div>
			</g:hasErrors>
			<g:form action="create" method="post" enctype="multipart/form-data">
				<div class="dialog">
					<fieldset>
						<legend><g:message code="track.create.legend" default="Enter Track Details"/></legend>

						<div class="prop ${hasErrors(bean: command, field: 'file', 'error')}">
							<label for="file">
								<g:message code="dukebox.FileUploadCommand.file" default="File"/>
							</label>
							<input type="file" id="file" name="file"/>
						</div>

					</fieldset>
				</div>
				<div class="buttons">
					<span class="button"><g:submitButton name="next" class="save" value="${message(code: 'create', 'default': 'Create')}"/></span>
				</div>
			</g:form>
		</div>
	</body>
</html>
