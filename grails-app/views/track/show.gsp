<%@ page import="dukebox.Track" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<title><g:message code="track.show" default="Show Track"/></title>
	</head>
	<body>
		<g:applyLayout name="menu">
			<li><g:link class="list" action="list"><g:message code="track.list" default="Track List"/></g:link></li>
			<li><g:link class="create" action="create"><g:message code="track.create" default="New Track"/></g:link></li>
		</g:applyLayout>
		<div class="body">
			<h1><g:message code="track.show" default="Show Track"/></h1>
			<g:if test="${flash.message}">
				<div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}"/></div>
			</g:if>
			<g:form>
				<g:hiddenField name="id" value="${trackInstance?.id}"/>
				<div class="dialog">
					<table>
						<tbody>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="track.title" default="Title"/>:</td>
								<td valign="top" class="value">${fieldValue(bean: trackInstance, field: "title")}</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="track.artist" default="Artist"/>:</td>
								<td valign="top" class="value">${fieldValue(bean: trackInstance, field: "artist")}</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="track.album" default="Album"/>:</td>
								<td valign="top" class="value">${fieldValue(bean: trackInstance, field: "album")}</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="track.trackNo" default="Track No"/>:</td>
								<td valign="top" class="value">${fieldValue(bean: trackInstance, field: "trackNo")}</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="track.year" default="Year"/>:</td>
								<td valign="top" class="value">${fieldValue(bean: trackInstance, field: "year")}</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="track.lastPlayed" default="Last Played"/>:</td>
								<td valign="top" class="value">${fieldValue(bean: trackInstance, field: "lastPlayed")}</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="track.dateCreated" default="Date Created"/>:</td>
								<td valign="top" class="value">${fieldValue(bean: trackInstance, field: "dateCreated")}</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="track.lastUpdated" default="Last Updated"/>:</td>
								<td valign="top" class="value">${fieldValue(bean: trackInstance, field: "lastUpdated")}</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="track.playCount" default="Play Count"/>:</td>
								<td valign="top" class="value">${fieldValue(bean: trackInstance, field: "playCount")}</td>
							</tr>
							<tr><td colspan="2"><amazon:albumArt artist="${trackInstance.artist}" album="${trackInstance.album}" /></td></tr>
						</tbody>
					</table>
				</div>
				<div class="buttons">
					<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'edit', 'default': 'Edit')}"/></span>
					<span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'delete', 'default': 'Delete')}" onclick="return confirm('${message(code: 'delete.confirm', 'default': 'Are you sure?')}');"/></span>
				</div>
			</g:form>
		</div>
	</body>
</html>
