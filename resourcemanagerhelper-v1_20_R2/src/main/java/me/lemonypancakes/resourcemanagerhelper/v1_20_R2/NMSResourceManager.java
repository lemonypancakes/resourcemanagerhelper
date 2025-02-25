package me.lemonypancakes.resourcemanagerhelper.v1_20_R2;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import me.lemonypancakes.resourcemanagerhelper.Resource;
import me.lemonypancakes.resourcemanagerhelper.ResourceLocation;
import me.lemonypancakes.resourcemanagerhelper.ResourceManager;
import net.minecraft.server.dedicated.DedicatedServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;

public final class NMSResourceManager implements ResourceManager {
  @Nonnull private final net.minecraft.server.packs.resources.ResourceManager handle;

  public NMSResourceManager() {
    CraftServer craftServer = (CraftServer) Bukkit.getServer();
    DedicatedServer dedicatedServer = craftServer.getServer();
    this.handle = dedicatedServer.getResourceManager();
  }

  @Nonnull
  @Override
  public Map<ResourceLocation, Resource> listResources(
      @Nonnull String resourceFolder,
      @Nonnull Predicate<ResourceLocation> resourceLocationPredicate) {
    return this.handle
        .listResources(
            resourceFolder,
            resourceLocation ->
                resourceLocationPredicate.test(new NMSResourceLocation(resourceLocation)))
        .entrySet()
        .stream()
        .collect(
            Collectors.toMap(
                entry -> new NMSResourceLocation(entry.getKey()),
                entry -> new NMSResource(entry.getValue())));
  }

  @Nonnull
  public net.minecraft.server.packs.resources.ResourceManager getHandle() {
    return this.handle;
  }
}
