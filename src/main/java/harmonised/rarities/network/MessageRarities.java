package harmonised.rarities.network;

import harmonised.rarities.config.Rarities;
import harmonised.rarities.config.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MessageRarities
{
    public static final Logger LOGGER = LogManager.getLogger();

    public CompoundNBT rarities;

    public MessageRarities(Map<String, Rarity> rarities )
    {
        this.rarities = serializeRarities( rarities );
    }

    public MessageRarities()
    {
    }

    public static MessageRarities decode(PacketBuffer buf )
    {
        MessageRarities packet = new MessageRarities();

        packet.rarities = buf.readCompoundTag();

        return packet;
    }

    public static void encode( MessageRarities packet, PacketBuffer buf )
    {
        buf.writeCompoundTag( packet.rarities );
    }

    public static void handlePacket(MessageRarities packet, Supplier<NetworkEvent.Context> ctx )
    {
        ctx.get().enqueueWork(() ->
        {
            Rarities.rarities = deserializeRarities( packet.rarities );
        });
        ctx.get().setPacketHandled( true );
    }

    public static CompoundNBT serializeRarities( Map<String, Rarity> input )
    {
        CompoundNBT output = new CompoundNBT();

        for( String key : input.keySet() )
        {
            output.put( key, serializeRarity( input.get( key ) ) );
        }

        return output;
    }

    public static CompoundNBT serializeRarity( Rarity rarity )
    {
        CompoundNBT output = new CompoundNBT();

        output.putString( "name", rarity.name );
        output.putInt( "color", rarity.color );
        CompoundNBT items = new CompoundNBT();
        int i = 0;
        for( ResourceLocation item : rarity.items )
        {
            items.putString( "" + i++, item.toString() );
        }
        output.put( "items", items );

        return output;
    }

    public static Map<String, Rarity> deserializeRarities( CompoundNBT input )
    {
        Map<String, Rarity> rarities = new HashMap<>();

        for( String rarityName : input.keySet() )
        {
            rarities.put( rarityName, deserializeRarity( (CompoundNBT) input.get( rarityName ) ) );
        }

        return rarities;
    }

    public static Rarity deserializeRarity( CompoundNBT input )
    {
        Set<String> items = new HashSet<>();

        for( String key : input.getCompound( "items" ).keySet() )
        {
            items.add( input.getCompound( "items" ).getString( key ) );
        }

        return new Rarity( input.getString( "name" ), input.getInt( "color" ), items );
    }
}