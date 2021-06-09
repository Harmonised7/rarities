package harmonised.rarities.network;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class MessageConfig
{
    public static final Logger LOGGER = LogManager.getLogger();

    public String configKey;
    public CompoundNBT config;

    public MessageConfig( CompoundNBT config )
    {
        this.config = config;
    }

    public MessageConfig()
    {
    }

    public static MessageConfig decode(PacketBuffer buf )
    {
        MessageConfig packet = new MessageConfig();

        packet.config = buf.readCompoundTag();

        return packet;
    }

    public static void encode(MessageConfig packet, PacketBuffer buf )
    {
        buf.writeCompoundTag( packet.config );
    }

    public static void handlePacket( MessageConfig packet, Supplier<NetworkEvent.Context> ctx )
    {
        ctx.get().enqueueWork(() ->
        {
            //Update Player's Rarities
        });
        ctx.get().setPacketHandled( true );
    }
}