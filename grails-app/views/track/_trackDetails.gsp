<div class="trackDetails">
	<amazon:albumArt artist="${it.artist}" album="${it.album}"/>
	<h1 class="title">${fieldValue(bean: it, field: "title")}</h1>
	<h2 class="artits">${fieldValue(bean: it, field: "artist")}</h2>
	<h3 class="album">${fieldValue(bean: it, field: "album")}</h3>
	<p><span class="label"><g:message code="track.trackNo" default="Track No"/></span> ${fieldValue(bean: it, field: "trackNo")}</p>
	<p><span class="label"><g:message code="track.year" default="Year"/></span> ${fieldValue(bean: it, field: "year")}</p>
	<p><span class="label"><g:message code="track.lastPlayed" default="Last Played"/></span> ${fieldValue(bean: it, field: "lastPlayed")}</p>
	<p><span class="label"><g:message code="track.dateCreated" default="Date Created"/></span> ${fieldValue(bean: it, field: "dateCreated")}</p>
	<p><span class="label"><g:message code="track.lastUpdated" default="Last Updated"/></span> ${fieldValue(bean: it, field: "lastUpdated")}</p>
	<p><span class="label"><g:message code="track.playCount" default="Play Count"/></span> ${fieldValue(bean: it, field: "playCount")}</p>
</div>
