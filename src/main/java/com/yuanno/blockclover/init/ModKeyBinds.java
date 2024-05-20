package com.yuanno.blockclover.init;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.client.screens.PlayerOverviewScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * Class that handles all the keys pressed;
 * Key pressed (-> event) -> something happens
 *
 * inspired by mineminenomi @Wynd
 */
@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class ModKeyBinds {
    public static KeyBinding infocard;

    public static void init()
    {
        infocard = new KeyBinding("keys.blockclover.info_card", GLFW.GLFW_KEY_P, "category.blockclover.gui");
        ClientRegistry.registerKeyBinding(infocard);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player == null)
            return;

        checkKeyBindings(player);
    }

    private static void checkKeyBindings(PlayerEntity player)
    {
        if (infocard.isDown())
        {
            if (Minecraft.getInstance().screen != null)
                return;

            PlayerOverviewScreen.open();
        }
    }
}
