package me.lemonypancakes.resourcemanagerhelper.impl.v1_20_R3;

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
        return this.handle.sourcePackId();
    }

    @Nonnull
    @Override
    public InputStream open() throws IOException {
        return this.handle.open();
    }

    @Nonnull
    public net.minecraft.server.packs.resources.Resource getHandle() {
        return this.handle;
    }
}
