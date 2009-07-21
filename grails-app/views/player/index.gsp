<%@ page import="dukebox.Track" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<title><g:message code="player.index.title" default="Dukebox Player"/></title>
	</head>
	<body>
		<g:applyLayout name="menu"/>
		<div class="body">
			<g:if test="${currentTrack}">
				<p><g:message code="player.playing.now" args="[currentTrack.title, currentTrack.artist]" default="Now playing: {0} by {1}"/></p>
				<p><g:link action="stop"><g:message code="player.stop.title" default="Stop"/></g:link></p>
			</g:if>
			<g:else>
				<p><g:message code="player.playing.nothing" args="[Track.count()]" default="Nothing playing right now. {0} tracks in the database."/></p>
				<p><g:link action="play"><g:message code="player.play.title" default="Play"/></g:link></p>
			</g:else>
		</div>
	</body>
</html>