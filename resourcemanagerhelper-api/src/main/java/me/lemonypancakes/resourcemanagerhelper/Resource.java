package me.lemonypancakes.resourcemanagerhelper;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nonnull;

public interface Resource {
  @Nonnull
  String sourcePackId();

  @Nonnull
  InputStream open() throws IOException;
}
