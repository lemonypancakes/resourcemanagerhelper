package me.lemonypancakes.resourcemanagerhelper.v1_18_R1;

import me.lemonypancakes.resourcemanagerhelper.Resource;
import me.lemonypancakes.resourcemanagerhelper.ResourceLocation;
import me.lemonypancakes.resourcemanagerhelper.ResourceManager;
import net.minecraft.server.dedicated.DedicatedServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
                resourceLocationPredicate.test(
                    new NMSResourceLocation(
                        new net.minecraft.resources.ResourceLocation(resourceLocation))))
        .stream()
        .collect(
            Collectors.toMap(
                NMSResourceLocation::new,
                resourceLocation -> {
                  try {
                    return new NMSResource(this.handle.getResource(resourceLocation));
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                }));
  }

  @Nonnull
  public net.minecraft.server.packs.resources.ResourceManager getHandle() {
    return this.handle;
  }
}
