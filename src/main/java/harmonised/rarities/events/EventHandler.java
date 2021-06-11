package harmonised.rarities.events;

import harmonised.rarities.config.Rarities;
import harmonised.rarities.network.MessageRarities;
import harmonised.rarities.network.NetworkHandler;
import harmonised.rarities.util.Reference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber( modid = Reference.MOD_ID )
public class EventHandler
{
    @SubscribeEvent
    public static void handlePlayerConnected( PlayerEvent.PlayerLoggedInEvent event )
    {
        PlayerEntity player = event.getPlayer();
        if( !player.world.isRemote() )
            NetworkHandler.sendToPlayer( new MessageRarities( Rarities.localRarities ), (ServerPlayerEntity) player );
    }
}