package harmonised.rarities.commands;

import com.mojang.brigadier.context.CommandContext;
import harmonised.rarities.config.Rarities;
import harmonised.rarities.network.MessageRarities;
import harmonised.rarities.network.NetworkHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

public class ReloadRaritiesCommand
{
    public static int execute( CommandContext<CommandSource> context ) throws CommandException
    {
        Rarities.init();
        context.getSource().getServer().getPlayerList().getPlayers().forEach( player ->
        {
            NetworkHandler.sendToPlayer( new MessageRarities( Rarities.localRarities ), player );
            player.sendStatusMessage( new TranslationTextComponent( "rarities.raritiesReloaded" ).setStyle(Style.EMPTY.setColor(Color.fromInt( 0x00ff00 ) ) ), false );
        });

        System.out.println( "Rarities Reloaded" );

        return 1;
    }
}
