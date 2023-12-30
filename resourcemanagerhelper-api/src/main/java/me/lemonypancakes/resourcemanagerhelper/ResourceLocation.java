package me.lemonypancakes.resourcemanagerhelper;

import javax.annotation.Nonnull;

public interface ResourceLocation {
    @Nonnull
    String getPath();

    @Nonnull
    String getNamespace();
}
