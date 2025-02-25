package me.lemonypancakes.resourcemanagerhelper.v1_18_R1;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nonnull;
import me.lemonypancakes.resourcemanagerhelper.Resource;

public class NMSResource implements Resource {
  @Nonnull private final net.minecraft.server.packs.resources.Resource handle;

  public NMSResource(@Nonnull net.minecraft.server.packs.resources.Resource handle) {
    this.handle = handle;
  }

  @Nonnull
  @Override
  public String sourcePackId() {
    return this.handle.getSourceName();
  }

  @Nonnull
  @Override
  public InputStream open() throws IOException {
    return this.handle.getInputStream();
  }

  @Nonnull
  public net.minecraft.server.packs.resources.Resource getHandle() {
    return this.handle;
  }
}
