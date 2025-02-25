package me.lemonypancakes.resourcemanagerhelper.v1_19_R3;

import javax.annotation.Nonnull;
import me.lemonypancakes.resourcemanagerhelper.ResourceLocation;

public class NMSResourceLocation implements ResourceLocation {
  @Nonnull private final net.minecraft.resources.ResourceLocation handle;

  public NMSResourceLocation(@Nonnull net.minecraft.resources.ResourceLocation handle) {
    this.handle = handle;
  }

  @Nonnull
  @Override
  public String getPath() {
    return this.handle.getPath();
  }

  @Nonnull
  @Override
  public String getNamespace() {
    return this.handle.getNamespace();
  }

  @Override
  public String toString() {
    return this.handle.toString();
  }

  @Override
  public int hashCode() {
    return 31 * this.handle.getNamespace().hashCode() + this.handle.getPath().hashCode();
  }

  @Nonnull
  public net.minecraft.resources.ResourceLocation getHandle() {
    return this.handle;
  }
}
