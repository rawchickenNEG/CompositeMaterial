package io.github.rcneg.compositematerial.common.items.disctools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class DiscAxe extends AxeItem {

    public DiscAxe(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_) {
        super(p_40521_, p_40522_, p_40523_, p_40524_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("68024817-4632-8753-1084-684203410895"), "atk_range", 1D, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    /*
    //拿来测试便携末影箱用的
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        PlayerEnderChestContainer playerenderchestcontainer = player.getEnderChestInventory();
        player.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
            return ChestMenu.threeRows(p_53124_, p_53125_, playerenderchestcontainer);
        }, Component.translatable("container.enderchest")));
        player.playSound(SoundEvents.ENDER_CHEST_OPEN);
        player.awardStat(Stats.OPEN_ENDERCHEST);

        return InteractionResultHolder.pass(itemstack);
    }

     */

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        //拿来测试shift显示额外内容用的
        //if(Screen.hasShiftDown()){
            tooltip.add(Component.translatable("tooltip.composite_material.disc_axe").withStyle(ChatFormatting.DARK_AQUA));
            tooltip.add(Component.translatable("tooltip.composite_material.disc_axe_2").withStyle(ChatFormatting.DARK_AQUA));
        //} else {
        //    tooltip.add(Component.translatable("tooltip.composite_material.disc_axe_cover").withStyle(ChatFormatting.YELLOW));
        //    tooltip.add(Component.translatable("tooltip.composite_material.shift_cover").withStyle(ChatFormatting.GRAY));
        //}

    }
}
