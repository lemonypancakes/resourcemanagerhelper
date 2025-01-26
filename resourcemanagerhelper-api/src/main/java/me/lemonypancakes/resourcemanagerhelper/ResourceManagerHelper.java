package me.lemonypancakes.resourcemanagerhelper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ResourceManagerHelper {
    @Nullable
    private static ResourceManager resourceManager;

    static {
        try {
            String nmsVersion = null;
            HttpURLConnection conn = (HttpURLConnection) new URL("https://raw.githubusercontent.com/Rapha149/NMSVersions/main/nms-versions.json").openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                JsonObject json = new Gson().fromJson(br.lines().collect(Collectors.joining()), JsonObject.class);

                if (json.has(Bukkit.getBukkitVersion())) {
                    nmsVersion = json.get(Bukkit.getBukkitVersion()).getAsString();
                }
            }
            Class<?> clazz = Class.forName("me.lemonypancakes.resourcemanagerhelper.impl.v" + nmsVersion + ".NMSResourceManager");

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
