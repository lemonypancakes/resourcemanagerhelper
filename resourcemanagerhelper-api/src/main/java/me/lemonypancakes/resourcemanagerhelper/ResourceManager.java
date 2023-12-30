package me.lemonypancakes.resourcemanagerhelper;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Predicate;

public interface ResourceManager {
    @Nonnull
    Map<ResourceLocation, Resource> listResources(@Nonnull String resourceFolder, @Nonnull Predicate<ResourceLocation> resourceLocationPredicate);
}
