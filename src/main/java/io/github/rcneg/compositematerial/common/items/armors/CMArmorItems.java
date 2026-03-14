package io.github.rcneg.compositematerial.common.items.armors;

import io.github.rcneg.compositematerial.CompositeMaterial;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CMArmorItems extends ArmorItem{
    public CMArmorItems(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }
}