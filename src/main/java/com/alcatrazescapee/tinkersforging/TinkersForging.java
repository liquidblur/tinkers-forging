/*
 * Part of the Tinkers Forging Mod by alcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.tinkersforging;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.alcatrazescapee.tinkersforging.client.ModGuiHandler;
import com.alcatrazescapee.tinkersforging.common.blocks.ModBlocks;
import com.alcatrazescapee.tinkersforging.common.capability.CapabilityForgeItem;
import com.alcatrazescapee.tinkersforging.common.items.ModItems;
import com.alcatrazescapee.tinkersforging.common.network.PacketTinkersAnvilButtonPress;
import com.alcatrazescapee.tinkersforging.common.recipe.ModRecipes;

import static com.alcatrazescapee.tinkersforging.ModConstants.MOD_ID;

@Mod(modid = MOD_ID, version = ModConstants.VERSION, dependencies = ModConstants.DEPENDENCIES, useMetadata = true)
public final class TinkersForging
{
    @Mod.Instance
    private static TinkersForging instance;
    private static Logger logger;
    private static SimpleNetworkWrapper network;

    public static TinkersForging getInstance()
    {
        return instance;
    }

    public static Logger getLog()
    {
        return logger;
    }

    public static SimpleNetworkWrapper getNetwork()
    {
        return network;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        logger.debug("If you can see this, debug logging is working :)");

        int id = -1;
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        network.registerMessage(PacketTinkersAnvilButtonPress.Handler.class, PacketTinkersAnvilButtonPress.class, ++id, Side.SERVER);


        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());

        // Pre-Init Managers
        CapabilityForgeItem.preInit();
        ModBlocks.preInit();
        ModItems.preInit();
        //ModSounds.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // Init Managers
        ModRecipes.init();
        ModItems.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Post-Init Managers
        ModRecipes.postInit();
    }
}
