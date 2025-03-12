package it.infocert.adapter.message.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.jackson.PojoCloudEventDataMapper;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

import static io.cloudevents.core.CloudEventUtils.mapData;

public class CloudEventWrapper {

    private static final Logger logger = LoggerFactory.getLogger(CloudEventWrapper.class);

    private final static ObjectMapper mapper = new ObjectMapper();

    static  {
        // To manage java.time dates
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        // To use ISO-8501 date format
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
    }

    public static CloudEvent wrap(Object domainEvent) {
        CloudEvent cloudEvent = null;
        try {
            byte[] domainEventBytes =  mapper.writeValueAsString(domainEvent).getBytes(StandardCharsets.UTF_8);
            cloudEvent = toCloudEvent(domainEventBytes);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Json error", e);
        }

        return cloudEvent;
    }

    private static CloudEvent toCloudEvent(byte[] eventBytes) {

        Span currentSpan = Span.current();
        SpanContext spanContext = currentSpan.getSpanContext();
        logger.info("traceparent : {}-{}", spanContext.getTraceId(), spanContext.getSpanId());
        return CloudEventBuilder.v1()
                .withId("2")
                .withSource(URI.create("/jdriven/blog"))
                .withType("com.jdriven.blog.1.0.0")
                .withDataContentType("application/json")
                .withDataSchema(URI.create("schemas.jdriven.com/blog"))
                .withSubject("Second CloudEvent blog")
                .withTime(OffsetDateTime.now())
                .withExtension("traceparent", spanContext.getTraceId() + '-' + spanContext.getSpanId())
                .withData(eventBytes)
                .build();
    }

    public static <E> E unwrap(CloudEvent tenantEvent, Class<E> clazz) {
        PojoCloudEventData<E> cloudEventData = mapData(
                tenantEvent,
                PojoCloudEventDataMapper.from(mapper, clazz)
        );
        return cloudEventData.getValue();
    }


}
