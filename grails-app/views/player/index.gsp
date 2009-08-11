<%@ page import="grails.converters.JSON; dukebox.Track" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<title><g:message code="player.index.title" default="Dukebox Player"/></title>
		<sm:soundManagerAPI/>
		<g:javascript>
			var playlist = [];
			<g:each var="track" in="${playlist}" status="i">
				playlist[${i}] = {
					title: '${track.title.encodeAsJavaScript()}', 
					artist: '${track.artist.encodeAsJavaScript()}',
					album: '${track.album.encodeAsJavaScript()}',
					path: '${track.filepath}'
				};
			</g:each>
			var streamUrl = '<g:createLink action="stream"/>/';
			soundManager.debugMode = false;
			soundManager.useConsole = false;
			soundManager.url = '<g:resource dir="/plugins/sound-manager-0.4/media" />';
			soundManager.onload = function() {
				console.log("O HAI");
				playNextTrack();
			}

			var nextIndex = 0;
			var track = null;

			function playNextTrack() {
				console.log("Playing " + playlist[nextIndex].title + ' by ' + playlist[nextIndex].artist);
				var track = soundManager.createSound({
					id: '' + nextIndex,
					url: streamUrl + playlist[nextIndex].path,
					onfinish: function() {
						console.log("Finished playing");
						track = null;
						nextIndex++;
						setTimeout('playNextTrack()', 15);
					}
				});
				track.play();
			}
		</g:javascript>
	</head>
	<body>
		<g:applyLayout name="menu"/>
		<div class="body">
		</div>
	</body>
</html>