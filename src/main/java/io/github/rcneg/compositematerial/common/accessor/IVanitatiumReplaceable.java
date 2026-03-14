package io.github.rcneg.compositematerial.common.accessor;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public interface IVanitatiumReplaceable {
    default Item getReplaceItem(){
        return Items.AIR;
    }
}
