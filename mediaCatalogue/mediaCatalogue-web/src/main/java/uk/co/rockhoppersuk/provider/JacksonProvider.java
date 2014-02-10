package uk.co.rockhoppersuk.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Custom provider.
 * <p>
 * Only used so far to:
 * <ul>
 * <li>Surpress Nulls.</li>
 * <li>add mixin annotations to the object mapper.</li>
 * </ul>
 *
 */
@Provider
public class JacksonProvider  extends JacksonJsonProvider {

    @Override
    public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        ObjectMapper mapper = locateMapper(type, mediaType);

        //suppress nulls
        mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

//        mapper.getSerializationConfig().addMixInAnnotations(DrawGame.class, MatrixDrawGamePublicView.class);
//        mapper.getSerializationConfig().addMixInAnnotations(DrawGame.class, DrawGamePublicView.class);
//        mapper.getSerializationConfig().addMixInAnnotations(Point.class, MixIn.class);

        super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }}
