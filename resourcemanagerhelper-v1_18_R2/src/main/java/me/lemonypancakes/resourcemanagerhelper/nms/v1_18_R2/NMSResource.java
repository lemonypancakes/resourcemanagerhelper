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

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;

public class NMSResource implements Resource {
    @Nonnull
    private final net.minecraft.server.packs.resources.Resource handle;

    public NMSResource(@Nonnull net.minecraft.server.packs.resources.Resource handle) {
        this.handle = handle;
    }

    @Nonnull
    @Override
    public String sourcePackId() {
        return this.handle.getSourceName();
    }

    @Nonnull
    @Override
    public InputStream open() throws IOException {
        return this.handle.getInputStream();
    }

    @Nonnull
    public net.minecraft.server.packs.resources.Resource getHandle() {
        return this.handle;
    }
}
