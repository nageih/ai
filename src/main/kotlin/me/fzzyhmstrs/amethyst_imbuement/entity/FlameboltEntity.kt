package me.fzzyhmstrs.amethyst_imbuement.entity

import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.projectile.SmallFireballEntity
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.world.World

class FlameboltEntity(entityType: EntityType<FlameboltEntity>, world: World): MissileEntity(entityType,world) {

    constructor(world: World,owner: LivingEntity, speed: Float, divergence: Float, x: Double, y: Double, z: Double) : this(RegisterEntity.FLAMEBOLT_ENTITY,world){
        this.owner = owner
        this.setVelocity(owner,
            owner.pitch,
            owner.yaw,
            0.0f,
            speed,
            divergence)
        this.setPosition(x,y,z)
        this.setRotation(owner.yaw, owner.pitch)
    }

    override fun initDataTracker() {}

    override fun onEntityHit(entityHitResult: EntityHitResult) {
        super.onEntityHit(entityHitResult)
        val entity2 = owner
        if (entity2 is LivingEntity) {
            val entity = entityHitResult.entity
            val fbe = SmallFireballEntity(EntityType.SMALL_FIREBALL,world)
            if (!entity.isFireImmune) {
                val i = entity.fireTicks
                entity.setOnFireFor(5)
                val bl = entity.damage(
                    DamageSource.fireball(fbe,owner),
                    6.0f
                )
                if (!bl) {
                    entity.fireTicks = i
                }
                applyDamageEffects(entity2 as LivingEntity?, entity)
            }
        }
        discard()
    }

    override fun isBurning(): Boolean {
        return true
    }

    override fun getParticleType(): ParticleEffect? {
        return ParticleTypes.FLAME
    }

    override fun onSpawnPacket(packet: EntitySpawnS2CPacket) {
        super.onSpawnPacket(packet)
        val d = packet.velocityX
        val e = packet.velocityY
        val f = packet.velocityZ
        /*for (i in 0..6) {
            val g = 0.95-0.02*i
            world.addParticle(ParticleTypes.CRIT, this.x, this.y, this.z, d * g, e, f * g)
            println("adding particles!")
        }*/
        this.setVelocity(d, e, f)
    }

}