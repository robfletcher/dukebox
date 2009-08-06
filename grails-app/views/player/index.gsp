<%@ page import="dukebox.Track" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<title><g:message code="player.index.title" default="Dukebox Player"/></title>
		<sm:pagePlayer autoStart="true"/>
	</head>
	<body>
		<g:applyLayout name="menu"/>
		<div class="body">
			<sm:playlist>
				<g:each var="track" in="${playlist}">
					<g:link action="stream" id="${track.filepath}">
						<g:message code="player.playing.now" args="[track.title, track.artist]" default="{0} by {1}"/>
					</g:link>
				</g:each>
			</sm:playlist>
		</div>
	</body>
</html>