package olivermakesco.de.bmc.testmod;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TestEnchantment extends Enchantment {
    protected TestEnchantment() {
        super(Rarity.COMMON, EnchantmentCategory.BREAKABLE, new EquipmentSlot[0]);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return true;
    }
}
