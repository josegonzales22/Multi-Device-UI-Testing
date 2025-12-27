package annotations.devices;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Tag("desktop")
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Desktop {
}
