package annotations.browsers.safari;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Tag("safari_cloud_tablet")
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SafariCloudTablet {
}