package ch.unifr.diva.dia.webservices;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marcel Würsch on 18.06.2014.
 */
public class DivaWebservices extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register resources and features
        classes.add(MultiPartFeature.class);
        classes.add(LoggingFilter.class);
        classes.add(SegmentationService.class);
        classes.add(SampleService.class);
        return classes;
    }
}
