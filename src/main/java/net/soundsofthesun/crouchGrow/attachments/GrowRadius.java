package net.soundsofthesun.crouchGrow.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record GrowRadius(Double n) {
    public static Codec<GrowRadius> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("n").forGetter(GrowRadius::n)
    ).apply(instance, GrowRadius::new));

    public static StreamCodec<ByteBuf, GrowRadius> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static GrowRadius DEFAULT = new GrowRadius(1D);

    public GrowRadius clear() {
        return DEFAULT;
    }
}
