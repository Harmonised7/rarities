package harmonised.rarities.commands;

import com.mojang.brigadier.CommandDispatcher;
import harmonised.rarities.util.Reference;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class RaritiesCommand
{
    public static void register( CommandDispatcher<CommandSource> dispatcher )
    {
        dispatcher.register( Commands.literal( Reference.MOD_ID )
                //RELOAD
                .requires( player -> player.hasPermissionLevel( 2 ) )
                .executes( ReloadRaritiesCommand::execute )
                .then( Commands.literal( "reload" )
                .executes( ReloadRaritiesCommand::execute )
                )
        );
    }
}
