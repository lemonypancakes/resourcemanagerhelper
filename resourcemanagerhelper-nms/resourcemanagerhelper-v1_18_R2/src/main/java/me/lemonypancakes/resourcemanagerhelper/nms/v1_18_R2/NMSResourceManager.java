/*
 * ResourceManagerHelper - Gives access to minecraft's resource manager.
 * Copyright (C) 2023 Teofilo Jr. V. Daquipil (LemonyPancakes) <jiboyjune@gmail.com>
 * Copyright (C) 2023 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.lemonypancakes.resourcemanagerhelper.nms.v1_18_R2;

import me.lemonypancakes.resourcemanagerhelper.Resource;
import me.lemonypancakes.resourcemanagerhelper.ResourceLocation;
import me.lemonypancakes.resourcemanagerhelper.ResourceManager;
import net.minecraft.server.dedicated.DedicatedServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class NMSResourceManager implements ResourceManager {
    @Nonnull
    private final net.minecraft.server.packs.resources.ResourceManager handle;

    public NMSResourceManager() {
        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        DedicatedServer dedicatedServer = craftServer.getServer();
        this.handle = dedicatedServer.getResourceManager();
    }

    @Nonnull
    @Override
    public Map<ResourceLocation, Resource> listResources(@Nonnull String resourceFolder, @Nonnull Predicate<ResourceLocation> resourceLocationPredicate) {
        return this.handle.listResources(resourceFolder, resourceLocation -> resourceLocationPredicate.test(new NMSResourceLocation(new net.minecraft.resources.ResourceLocation(resourceLocation))))
                .stream()
                .collect(Collectors.toMap(NMSResourceLocation::new, resourceLocation -> {
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
