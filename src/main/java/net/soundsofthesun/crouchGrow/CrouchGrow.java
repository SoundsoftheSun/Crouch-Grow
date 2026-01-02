package net.soundsofthesun.crouchGrow;

import net.fabricmc.api.ModInitializer;
import net.soundsofthesun.crouchGrow.attachments.SAttachments;
import net.soundsofthesun.crouchGrow.commands.SCommands;

public class CrouchGrow implements ModInitializer {

    public static final String MOD_ID = "crouch_grow";

    @Override
    public void onInitialize() {
        SAttachments.init();
        SCommands.init();
    }
}
