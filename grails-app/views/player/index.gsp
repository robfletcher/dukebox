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
					id: '${track.id}',
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
				playNextTrack();
			}

			var currentTrack = null;

			function playNextTrack() {
				currentTrack = playlist.shift();
				if (currentTrack == null) {
					console.warn("Playlist is empty");
				} else {
					console.info("Playing " + currentTrack.title + ' by ' + currentTrack.artist);
					var sound = soundManager.createSound({
						id: currentTrack.id,
						url: streamUrl + currentTrack.path,
						onfinish: function() {
							console.info("Finished playing");
							currentTrack = null;
							setTimeout('playNextTrack()', 15);
						}
					});
					sound.play();
				}
			}
		</g:javascript>
	</head>
	<body>
		<g:applyLayout name="menu"/>
		<div class="body">
		</div>
	</body>
</html>