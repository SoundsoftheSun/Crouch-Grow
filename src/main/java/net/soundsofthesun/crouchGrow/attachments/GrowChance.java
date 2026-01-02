package net.soundsofthesun.crouchGrow.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record GrowChance(Integer n) {
    public static Codec<GrowChance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("n").forGetter(GrowChance::n)
    ).apply(instance, GrowChance::new));

    public static StreamCodec<ByteBuf, GrowChance> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static GrowChance DEFAULT = new GrowChance(8);

    public GrowChance clear() {
        return DEFAULT;
    }
}