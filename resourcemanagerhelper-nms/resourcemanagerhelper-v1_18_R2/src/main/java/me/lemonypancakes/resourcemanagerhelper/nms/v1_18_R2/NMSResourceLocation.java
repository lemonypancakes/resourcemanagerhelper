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

import me.lemonypancakes.resourcemanagerhelper.ResourceLocation;

public record NMSResourceLocation(net.minecraft.resources.ResourceLocation handle) implements ResourceLocation {
    @Override
    public String getPath() {
        return this.handle.getPath();
    }

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
}
