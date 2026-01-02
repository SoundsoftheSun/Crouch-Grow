package net.soundsofthesun.crouchGrow.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.soundsofthesun.crouchGrow.attachments.DoGrow;
import net.soundsofthesun.crouchGrow.attachments.GrowChance;
import net.soundsofthesun.crouchGrow.attachments.GrowRadius;
import net.soundsofthesun.crouchGrow.attachments.SAttachments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.jar.JarEntry;

@Mixin(Player.class)
public class PlayerMixin {

    @WrapOperation(method = "updatePlayerPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setPose(Lnet/minecraft/world/entity/Pose;)V"))
    void fs$updatePlayerPose(Player player, Pose pose, Operation<Void> original) {
        Pose oldPose = player.getPose();
        original.call(player, pose);
        if (!(player.level() instanceof ServerLevel serverLevel)) return;
        if (!(serverLevel.getAttachedOrElse(SAttachments.DO_GROW, DoGrow.DEFAULT).b())) return;
        Pose newPose = player.getPose();
        if (oldPose == Pose.STANDING && newPose == Pose.CROUCHING) {
            double radius = serverLevel.getAttachedOrElse(SAttachments.RADIUS, GrowRadius.DEFAULT).n();
            for (BlockPos blockpos : BlockPos.betweenClosed(player.getBoundingBox().inflate(radius, radius, radius))) {
                BlockState bs = serverLevel.getBlockState(blockpos);
                Block block = bs.getBlock();
                if (block instanceof BonemealableBlock bonemealableBlock && block instanceof VegetationBlock) {
                    int chance = serverLevel.getAttachedOrElse(SAttachments.CHANCE, GrowChance.DEFAULT).n();
                    if (serverLevel.getRandom().nextInt(chance) == 0) {
                        if (bonemealableBlock.isValidBonemealTarget(serverLevel, blockpos, bs)) {
                            bonemealableBlock.performBonemeal(serverLevel, serverLevel.random, blockpos, bs);
                            break;
                        }
                    }
                    serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, blockpos.getX()+0.5, blockpos.getY()+0.25, blockpos.getZ()+0.5, 3, 0.25, 0.25, 0.25, 1);
                } else if (block instanceof NetherWartBlock) {
                    int age = bs.getValue(NetherWartBlock.AGE);
                    int chance = serverLevel.getAttachedOrElse(SAttachments.CHANCE, GrowChance.DEFAULT).n();
                    if (serverLevel.getRandom().nextInt(chance) == 0 && age < NetherWartBlock.MAX_AGE) {
                        serverLevel.setBlockAndUpdate(blockpos, bs.setValue(NetherWartBlock.AGE, ++age));
                        break;
                    }
                    serverLevel.sendParticles(ParticleTypes.WAX_ON, blockpos.getX()+0.5, blockpos.getY()+0.25, blockpos.getZ()+0.5, 3, 0.25, 0.25, 0.25, 1);
                }
            }
        }
    }
}
