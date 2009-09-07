package com.energizedwork.grails.plugins.jodatime

import grails.converters.JSON
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException
import org.codehaus.groovy.grails.web.converters.marshaller.ObjectMarshaller
import org.codehaus.groovy.grails.web.json.JSONException
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class DateTimeMarshaller implements ObjectMarshaller<JSON> {

	private final DateTimeFormatter JSON_DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(DateTimeZone.UTC).withLocale(Locale.US);

	public boolean supports(Object object) {
		return object instanceof DateTime;
	}

	public void marshalObject(Object object, JSON converter) throws ConverterException {
		try {
			converter.getWriter().value(JSON_DATE_TIME_FORMAT.print((DateTime) object));
		} catch (JSONException e) {
			throw new ConverterException(e);
		}
	}
}