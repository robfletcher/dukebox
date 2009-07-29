<%@ page import="dukebox.Track" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<title><g:message code="track.list" default="Track List"/></title>
	</head>
	<body>
		<g:applyLayout name="menu">
			<li><g:link class="create" action="create"><g:message code="track.create" default="New Track"/></g:link></li>
		</g:applyLayout>
		<div class="body">
			<h1><g:message code="track.list" default="Track List"/></h1>
			<g:if test="${flash.message}">
				<div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}"/></div>
			</g:if>
			<div class="list">
				<table>
					<thead>
						<tr>
							<g:sortableColumn property="title" title="Title" titleKey="track.title"/>
							<g:sortableColumn property="artist" title="Artist" titleKey="track.artist"/>
							<g:sortableColumn property="album" title="Album" titleKey="track.album"/>
							<g:sortableColumn property="trackNo" title="Track No" titleKey="track.trackNo"/>
							<g:sortableColumn property="year" title="Year" titleKey="track.year"/>
							<th><g:message code="track.image" default="Album Cover"/></th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${trackInstanceList}" status="i" var="trackInstance">
							<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
								<td><g:link action="show" id="${trackInstance.id}">${fieldValue(bean: trackInstance, field: "title")}</g:link></td>
								<td>${fieldValue(bean: trackInstance, field: "artist")}</td>
								<td>${fieldValue(bean: trackInstance, field: "album")}</td>
								<td>${fieldValue(bean: trackInstance, field: "trackNo")}</td>
								<td>${fieldValue(bean: trackInstance, field: "year")}</td>
								<amazon:albumArtCell trackList="${trackInstanceList}" index="${i}"/>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
			<div class="paginateButtons">
				<g:paginate total="${trackInstanceTotal}"/>
			</div>
		</div>
	</body>
</html>
