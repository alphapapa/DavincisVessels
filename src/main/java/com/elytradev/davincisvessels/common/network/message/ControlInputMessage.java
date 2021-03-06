package com.elytradev.davincisvessels.common.network.message;

import com.elytradev.davincisvessels.common.entity.EntityShip;
import com.elytradev.davincisvessels.common.network.DavincisVesselsNetworking;
import com.elytradev.concrete.Message;
import com.elytradev.concrete.NetworkContext;
import com.elytradev.concrete.annotation.field.MarshalledAs;
import com.elytradev.concrete.annotation.type.ReceivedOn;
import com.elytradev.movingworld.common.network.marshallers.EntityMarshaller;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by darkevilmac on 2/2/2017.
 */
@ReceivedOn(Side.SERVER)
public class ControlInputMessage extends Message {

    @MarshalledAs(EntityMarshaller.MARSHALLER_NAME)
    public EntityShip ship;
    @MarshalledAs("i8")
    public int control;

    public ControlInputMessage(EntityShip ship, int control) {
        super(DavincisVesselsNetworking.NETWORK);
        this.ship = ship;
        this.control = control;
    }

    public ControlInputMessage(NetworkContext ctx) {
        super(ctx);
    }

    @Override
    protected void handle(EntityPlayer sender) {
        if (ship == null)
            return;

        ship.getController().updateControl(ship, sender, control);
    }
}
