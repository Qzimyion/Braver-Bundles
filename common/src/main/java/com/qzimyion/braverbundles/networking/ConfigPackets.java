package com.qzimyion.braverbundles.networking;

import com.qzimyion.braverbundles.CommonModConfig;
import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.minecraft.network.FriendlyByteBuf;

public class ConfigPackets  {

    private final double fraction;

    public ConfigPackets(double fraction) {
        this.fraction = fraction;
    }

    public static ConfigPackets decode(FriendlyByteBuf buf) {
        return new ConfigPackets(buf.readDouble());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(fraction);
    }

    public void handle(NetworkManager.PacketContext context) {
        context.queue(() -> {
            if (context.getEnvironment().toPlatform()== EnvType.CLIENT) {
                CommonModConfig.UNSTACKING_FRACTIONS = fraction;
            }
        });
    }
}
