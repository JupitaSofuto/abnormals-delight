package com.minecraftabnormals.abnormals_delight.common.item;

import com.minecraftabnormals.abnormals_delight.core.registry.ADItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class CakeSliceItem extends Item {
	public Supplier<EffectInstance> effect;

	public CakeSliceItem(Supplier<EffectInstance> effect, Properties properties) {
		super(properties);
		this.effect = effect;
	}

	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		if (!worldIn.isRemote && effect != null && effect.get().getPotion() != null)
			entityLiving.addPotionEffect(new EffectInstance(effect.get().getPotion(), effect.get().getDuration(), effect.get().getAmplifier()));
		if (this == ADItems.STRAWBERRY_CAKE_SLICE.get())
			applyHealing(1.0F, worldIn, entityLiving);
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

	public static void applyHealing(float healAmount, IWorld world, LivingEntity entity) {
		entity.heal(healAmount);
		Random rand = entity.getRNG();
		if (world.isRemote()) {
			int times = 2 * Math.round(healAmount);
			for (int i = 0; i < times; ++i) {
				double d0 = rand.nextGaussian() * 0.02D;
				double d1 = rand.nextGaussian() * 0.02D;
				double d2 = rand.nextGaussian() * 0.02D;
				world.addParticle(ParticleTypes.HEART, entity.getPosXRandom(1.0D), entity.getPosYRandom() + 0.5D, entity.getPosZRandom(1.0D), d0, d1, d2);
			}
		}
	}
}
