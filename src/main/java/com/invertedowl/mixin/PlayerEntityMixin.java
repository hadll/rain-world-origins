package com.invertedowl.mixin;

import net.minecraft.entity.TntEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.text.JTextComponent;

@Mixin(ServerPlayerEntity.class)
public class PlayerEntityMixin {
    private static JTextComponent.KeyBinding jumpKeyBinding;

    @Inject(at = @At("HEAD"), method = "tick")
    private void explode(CallbackInfo ci) {
        jumpKeyBinding = KEy.registerKeyBinding(new JTextComponent.KeyBinding(
                "key.examplemod.jump",
                GLFW.GLFW_KEY_SPACE,
                "key.categories.examplemod"
        ));

        JTextComponent.KeyBinding spacebarBinding = new JTextComponent.KeyBinding("key.spacebar", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "key.categories.gameplay");

    }
}
