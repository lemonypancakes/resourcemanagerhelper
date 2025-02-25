package me.lemonypancakes.resourcemanagerhelper;

import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

public interface ResourceManager {
  @Nonnull
  Map<ResourceLocation, Resource> listResources(
      @Nonnull String resourceFolder,
      @Nonnull Predicate<ResourceLocation> resourceLocationPredicate);
}
