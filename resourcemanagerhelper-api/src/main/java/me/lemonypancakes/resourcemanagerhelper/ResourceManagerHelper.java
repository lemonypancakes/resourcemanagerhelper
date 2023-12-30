package me.lemonypancakes.resourcemanagerhelper;

import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class ResourceManagerHelper {
    @Nullable
    private static ResourceManager resourceManager;

    static {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        try {
            Class<?> clazz = Class.forName("me.lemonypancakes.resourcemanagerhelper.nms." + version + ".NMSResourceManager");

            if (ResourceManager.class.isAssignableFrom(clazz)) {
                ResourceManagerHelper.resourceManager = (ResourceManager) clazz.getConstructor().newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ResourceManagerHelper() {
    }

    @Nullable
    public static ResourceManager getResourceManager() {
        return resourceManager;
    }

    @Nonnull
    public static Map<ResourceLocation, Resource> listResources(@Nonnull String resourceFolder, @Nonnull Predicate<ResourceLocation> resourceLocationPredicate) {
        return resourceManager != null ? resourceManager.listResources(resourceFolder, resourceLocationPredicate) : new HashMap<>();
    }
}
