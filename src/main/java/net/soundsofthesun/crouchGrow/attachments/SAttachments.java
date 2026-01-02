package net.soundsofthesun.crouchGrow.attachments;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;
import net.soundsofthesun.crouchGrow.CrouchGrow;

public class SAttachments {

    public static void init() {}

    public static final AttachmentType<GrowChance> CHANCE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(CrouchGrow.MOD_ID, "grow_chance"),
            builder->builder
                    .initializer(()->GrowChance.DEFAULT)
                    .persistent(GrowChance.CODEC)
    );

    public static final AttachmentType<GrowRadius> RADIUS = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(CrouchGrow.MOD_ID, "grow_radius"),
            builder->builder
                    .initializer(()->GrowRadius.DEFAULT)
                    .persistent(GrowRadius.CODEC)
    );

    public static final AttachmentType<DoGrow> DO_GROW = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(CrouchGrow.MOD_ID, "do_grow"),
            builder->builder
                    .initializer(()->DoGrow.DEFAULT)
                    .persistent(DoGrow.CODEC)
    );

}
