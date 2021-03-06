package com.elytradev.davincisvessels.common.handler;


import com.elytradev.davincisvessels.common.tileentity.TileCrate;
import com.elytradev.davincisvessels.common.tileentity.TileEntitySecuredBed;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonHookContainer {
    @SubscribeEvent
    public void onInteractWithEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntityPlayer() != null) {
            int x = MathHelper.floor(event.getTarget().posX);
            int y = MathHelper.floor(event.getTarget().posY);
            int z = MathHelper.floor(event.getTarget().posZ);

            TileEntity te = event.getEntity().world.getTileEntity(new BlockPos(x, y, z));
            if (te instanceof TileCrate && ((TileCrate) te).getContainedEntity() == event.getTarget()) {
                ((TileCrate) te).releaseEntity();
                event.setCanceled(true);
            }
        }
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerSpawnSet(PlayerSetSpawnEvent e) {
        if (e.isCanceled())
            return;

        if (e.getEntityPlayer().getGameProfile() != null && e.getEntityPlayer().getGameProfile().getId() != null &&
                ConnectionHandler.playerBedMap.containsKey(e.getEntityPlayer().getGameProfile().getId())) {
            //Spawn for the player is changing and they use a secured bed, clear the map of the player.

            TileEntitySecuredBed bed = ConnectionHandler.playerBedMap.get(e.getEntityPlayer().getGameProfile().getId());

            if (bed.getPos().equals(e.getNewSpawn()))
                return;

            ConnectionHandler.playerBedMap.remove(e.getEntityPlayer().getGameProfile().getId());
        }
    }

}
