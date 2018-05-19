package com.pikycz.novamobs.entities.animal.flying;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import com.pikycz.novamobs.entities.animal.FlyingAnimal;
import com.pikycz.novamobs.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PikyCZ
 */
public class Parrot extends FlyingAnimal {

    public static final int NETWORK_ID = 105;

    public Parrot(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public String getName() {
        return "Parrot";
    }

    @Override
    public float getDrag() {
        return 0.2f;
    }

    @Override
    public float getGravity() {
        return 0.3f;
    }

    @Override
    public float getWidth() {
        return 0.5f;
    }

    @Override
    public float getHeight() {
        return 0.9f;
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(6);
    }

    @Override
    public boolean targetOption(EntityCreature creature, double distance) {
        if (creature instanceof Player) {
            Player player = (Player) creature;
            return player.isAlive() && !player.closed && (player.getInventory().getItemInHand().getId() == Item.SEEDS) && distance <= 49;
        }
        return creature.isAlive() && creature.closed && distance <= 50;
    }

    @Override
    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();
        if (this.lastDamageCause instanceof EntityDamageByEntityEvent) {
            int featherDrop = Utils.rand(1, 3); // drops 1-2 feathers
            for (int i = 0; i < featherDrop; i++) {
                drops.add(Item.get(Item.FEATHER, 0, 1));
            }
        }
        return drops.toArray(new Item[drops.size()]);
    }

    @Override
    public int getKillExperience() {
        return Utils.rand(1, 3);
    }

}
