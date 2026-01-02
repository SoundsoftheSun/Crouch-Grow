package net.soundsofthesun.crouchGrow.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record DoGrow(Boolean b) {
    public static Codec<DoGrow> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("b").forGetter(DoGrow::b)
    ).apply(instance, DoGrow::new));

    public static StreamCodec<ByteBuf, DoGrow> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static DoGrow DEFAULT = new DoGrow(true);

    public DoGrow clear() {
        return DEFAULT;
    }
}
