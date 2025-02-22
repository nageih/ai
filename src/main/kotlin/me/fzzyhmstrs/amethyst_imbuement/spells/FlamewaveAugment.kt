package me.fzzyhmstrs.amethyst_imbuement.spells

import me.fzzyhmstrs.amethyst_core.modifier_util.AugmentEffect
import me.fzzyhmstrs.amethyst_core.scepter_util.LoreTier
import me.fzzyhmstrs.amethyst_core.scepter_util.ScepterTier
import me.fzzyhmstrs.amethyst_core.scepter_util.SpellType
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.AugmentDatapoint
import me.fzzyhmstrs.amethyst_core.scepter_util.augments.SummonProjectileAugment
import me.fzzyhmstrs.amethyst_imbuement.entity.spell.FlameboltEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class FlamewaveAugment: SummonProjectileAugment(ScepterTier.THREE,11){

    override val baseEffect: AugmentEffect
        get() = super.baseEffect
            .withDamage(5.85F,0.15F,0.0F)
            .withDuration(76,4)

    override fun augmentStat(imbueLevel: Int): AugmentDatapoint {
        return AugmentDatapoint(SpellType.FURY,2,2,
            24,imbueLevel,1, LoreTier.HIGH_TIER, Items.FIRE_CHARGE)
    }

    override fun entityClass(world: World, user: LivingEntity, level: Int, effects: AugmentEffect): ProjectileEntity {
        val speed = 1.5F
        val div = 1.25F
        return FlameboltEntity.createFlamebolt(world, user, speed, div, effects, level,this)
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.ENTITY_BLAZE_SHOOT
    }
}
