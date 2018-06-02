/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pikycz.novamobs.task;

import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.PluginTask;
import com.pikycz.novamobs.NovaMobsX;

/**
 *
 * @author PikyCZ
 */
public class ItemCleanTask extends PluginTask<NovaMobsX> {

    public static NovaMobsX plugin;

    public ItemCleanTask(NovaMobsX owner) {
        super(owner);
        this.plugin = owner;
    }

    @Override
    public void onRun(int i) {
        int count = 0;
        for (Level level : Server.getInstance().getLevels().values()) {
            for (Entity entity : level.getEntities()) {
                if (entity instanceof EntityItem && entity.isOnGround()) {
                    entity.close();
                    count++;
                    Server.getInstance().getLogger().info(plugin.PluginPrefix + " Removed " + count + " items on ground from all levels.");
                }
            }
        }
    }

}
