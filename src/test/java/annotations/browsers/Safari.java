package annotations.browsers;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Tag("safari")
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Safari {
}
