package me.lemonypancakes.resourcemanagerhelper;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;

public interface Resource {
    @Nonnull
    String sourcePackId();

    @Nonnull
    InputStream open() throws IOException;
}
