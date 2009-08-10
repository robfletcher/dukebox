<%@ page import="dukebox.Track" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<title><g:message code="player.index.title" default="Dukebox Player"/></title>
		<sm:soundManagerAPI/>
		<g:javascript>
			var playlist = new Array();
			<g:each var="track" in="${playlist}" status="i">
			playlist[${i}] = {
				id: '${track.id}',
				title: '${track.title}',
				artist: '${track.artist}',
				album: '${track.album}', 
				url: '<g:createLink action="stream" id="${track.filepath}"/>'
			};
			</g:each>
			soundManager.debugMode = false;
			soundManager.useConsole = true;
			soundManager.url = '<g:resource dir="/plugins/sound-manager-0.4/media"/>';
			soundManager.onload = function() {
				var track = playlist[0];
				soundManager.play(track.id, track.url);
				$('#nowplaying').text(track.title + ' by ' + track.artist);
			}
		</g:javascript>
	</head>
	<body>
		<g:applyLayout name="menu"/>
		<div class="body">
			<div id="nowplaying"></div>
			%{--<sm:playlist>--}%
			%{--<g:each var="track" in="${playlist}">--}%
			%{--<g:link action="stream" id="${track.filepath}">--}%
			%{--<g:message code="player.playing.now" args="[track.title, track.artist]" default="{0} by {1}"/>--}%
			%{--</g:link>--}%
			%{--</g:each>--}%
			%{--</sm:playlist>--}%
		</div>
	</body>
</html>