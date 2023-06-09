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
