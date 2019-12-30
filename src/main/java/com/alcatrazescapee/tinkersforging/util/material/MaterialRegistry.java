package com.alcatrazescapee.tinkersforging.util.material;

import com.alcatrazescapee.tinkersforging.ModConfig;
import com.alcatrazescapee.tinkersforging.TinkersForging;
import com.alcatrazescapee.tinkersforging.integration.AdvToolboxIntegration;
import com.alcatrazescapee.tinkersforging.integration.TinkersIntegration;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.*;

@ParametersAreNonnullByDefault
public final class MaterialRegistry {
    private static final Map<String, MaterialType> MATERIALS = new HashMap<>();
    private static final Set<String> TINKERS_MATERIALS = new HashSet<>();
    private static final Set<String> NTP_MATERIALS = new HashSet<>();
    private static final Set<String> TOOLBOX_MATERIALS = new HashSet<>();

    public static void preInit() {
        // Vanilla Materials
        // Common Modded Materials
        addMaterial(new MaterialType("copper", new Color(184, 115, 51).getRGB(), 1, 600f, 1000f));
        addMaterial(new MaterialType("bismuth bronze", new Color(120, 143, 149).getRGB(), 2, 150f, 300f));
        addMaterial(new MaterialType("wrought iron", new Color(128, 128, 128).getRGB(), 3, 950f, 1350f));
        addMaterial(new MaterialType("steel", new Color(50, 48, 51).getRGB(), 4, 200f, 350f));
        addMaterial(new MaterialType("black steel", new Color(2, 2, 3).getRGB(), 5, 700f, 950f));
        addMaterial(new MaterialType("red steel", new Color(137, 0, 4).getRGB(), 6, 450f, 700f));
        addMaterial(new MaterialType("blue steel", new Color(0, 5, 137).getRGB(), 6, 450f, 700f));

        // NTP Compat Materials
        NTP_MATERIALS.addAll(Arrays.asList("iron", "gold", "tin", "copper", "bronze", "steel"));

        // Adventurer's Toolbox Materials
        if (Loader.isModLoaded("toolbox")) {
            AdvToolboxIntegration.addAllMaterials();
        }

        // Tinker's Construct Materials
        if (Loader.isModLoaded("tconstruct")) {
            TinkersIntegration.addAllMaterials();
        }

        // Force Enable materials
        for (String s : ModConfig.GENERAL.forceEnabledMetals) {
            if (MATERIALS.containsKey(s)) {
                MATERIALS.get(s).setEnabled();
            }
        }
    }

    public static void addMaterial(MaterialType material) {
        if (MATERIALS.containsKey(material.getName())) {
            TinkersForging.getLog().debug("Material {} was overriden!", material.getName());
        }
        MATERIALS.put(material.getName(), material);
    }

    public static void addTinkersMaterial(MaterialType material) {
        if (MATERIALS.containsKey(material.getName())) {
            TINKERS_MATERIALS.add(material.getName());
        }
    }

    public static void addToolboxMaterial(MaterialType material) {
        if (MATERIALS.containsKey(material.getName())) {
            TOOLBOX_MATERIALS.add(material.getName());
        }
    }

    @Nonnull
    public static Collection<MaterialType> getAllMaterials() {
        return MATERIALS.values();
    }

    @Nullable
    public static MaterialType getMaterial(String name) {
        return MATERIALS.get(name);
    }

    public static boolean isTinkersMaterial(MaterialType material) {
        return TINKERS_MATERIALS.contains(material.getName());
    }

    public static boolean isNTPMaterial(MaterialType material) {
        return NTP_MATERIALS.contains(material.getName());
    }

    public static boolean isToolboxMaterial(MaterialType material) {
        return TOOLBOX_MATERIALS.contains(material.getName());
    }
}
