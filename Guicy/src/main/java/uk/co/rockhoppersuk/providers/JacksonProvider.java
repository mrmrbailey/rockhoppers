/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.providers;

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
import uk.co.rockhoppersuk.bean.Bean;
import uk.co.rockhoppersuk.feeds.PublicBeanView;

/**
 *
 * @author mxbailey
 */
@Provider
public class JacksonProvider extends JacksonJsonProvider {

    @Override
    public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        ObjectMapper mapper = locateMapper(type, mediaType);
        //suppress nulls
        mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

        //add mix in annotations
        mapper.getSerializationConfig().addMixInAnnotations(Bean.class, PublicBeanView.class);

        super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }
    
    
}
