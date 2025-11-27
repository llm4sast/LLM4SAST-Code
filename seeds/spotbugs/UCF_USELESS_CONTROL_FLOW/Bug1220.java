import sfBugsNew.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import edu.umd.cs.findbugs.annotations.DesireWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug1220 {
    @interface GET { }
    @interface PUT { }
    @interface POST { }
    @interface HEAD { }
    @DesireWarning("UCF")
    Annotation getEndpoint(Method targetMethod) {
        Annotation endpoint = null;
        if (null != (endpoint = targetMethod.getAnnotation(GET.class))) {
        } else if (null != (endpoint = targetMethod.getAnnotation(PUT.class))) {
        } else if (null != (endpoint = targetMethod.getAnnotation(POST.class))) { } else if (null != (endpoint = targetMethod.getAnnotation(HEAD.class))) { }
        return endpoint;
    }
    @NoWarning("UCF")
    Annotation getEndpoint2(Method targetMethod) {
        Annotation endpoint = null;
        if (null != (endpoint = targetMethod.getAnnotation(GET.class))) {
        } else if (null != (endpoint = targetMethod.getAnnotation(PUT.class))) {
        } else if (null != (endpoint = targetMethod.getAnnotation(POST.class))) {
        } else { endpoint = targetMethod.getAnnotation(HEAD.class); }
        return endpoint;
    }
}