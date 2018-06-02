/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pikycz.novamobs.entities.monster.flying;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import com.pikycz.novamobs.entities.monster.FlyingMonster;

/**
 *
 * @author Jaroslav Pikart
 */
public class Phantom extends FlyingMonster {

    private float Size;//??

    public static final int NETWORK_ID = 100;//??

    public Phantom(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.setMaxHealth(14);

        this.setDamage(new int[]{0, 2, 3, 4});
    }

    @Override
    public float getDrag() {
        return 0.2f;
    }

    @Override
    public float getGravity() {
        return 0.04f;
    }

    @Override
    public float getWidth() {
        return 0.9f + Size * 0.2f;
    }

    @Override
    public float getHeight() {
        return 0.5f + Size * 0f;
    }

    @Override
    public String getName() {
        return "Phantom";
    }

    @Override
    public int getKillExperience() {
        return 5;
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public void attackEntity(Entity player) {
        if (this.attackDelay > 10 && player.distanceSquared(this) <= 1) {
            this.attackDelay = 0;
            player.attack(new EntityDamageByEntityEvent(this, player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, getDamage()));
        }
    }

}
