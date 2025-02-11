package com.qzimyion.braverbundles.networking;

import com.qzimyion.braverbundles.BraverBundlesConstants;
import com.qzimyion.braverbundles.CommonModConfig;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@SuppressWarnings("removal")
public class ModNetworkHandler {
    public static final ResourceLocation UNSTACKING_ITEM_PACKET = ResourceLocation.fromNamespaceAndPath(BraverBundlesConstants.MOD_ID, "unstacking_item_packet");

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, UNSTACKING_ITEM_PACKET, (buf, context) -> {
            ConfigPackets packet = ConfigPackets.decode(buf);
            packet.handle(context);
        });
    }

    public static void sendToClient(ServerPlayer player) {
        RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), player.registryAccess());
        new ConfigPackets(CommonModConfig.UNSTACKING_FRACTIONS).encode(buf);
        NetworkManager.sendToPlayer(player, UNSTACKING_ITEM_PACKET, buf);
    }
}
