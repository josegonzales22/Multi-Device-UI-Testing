package annotations.browsers;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Tag("remote")
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Remote {
}
