package harmonised.rarities.client;

import harmonised.rarities.config.Rarities;
import harmonised.rarities.config.Rarity;
import harmonised.rarities.util.Reference;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber( value = Dist.CLIENT, modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ClientHandler
{
    @SubscribeEvent( priority = EventPriority.LOWEST )
    public static void renderTooltip( ItemTooltipEvent event )
    {
        Rarity rarity = Rarities.getRarity( event.getItemStack() );
        if( rarity != null )
            event.getToolTip().add( 1, new TranslationTextComponent( Reference.MOD_ID + "." + rarity.name.toLowerCase() ).setStyle( Style.EMPTY.setColor( Color.fromInt( rarity.color ) ) ) );

    }
}