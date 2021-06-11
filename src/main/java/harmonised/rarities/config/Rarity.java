package harmonised.rarities.config;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class Rarity
{
    public static final Logger LOGGER = LogManager.getLogger();

    public final String name;
    public final int color;
    public final Set<ResourceLocation> items;

    public Rarity( String name, int color, Set<String> items )
    {
        this.name = name;
        this.color = color;
        this.items = new HashSet<>();
        for( String item : items )
        {
            if( item == null )
                continue;
            ResourceLocation resLoc = new ResourceLocation( item );
            if( ForgeRegistries.ITEMS.containsKey( resLoc ) )
                this.items.add( resLoc );
            else
                LOGGER.warn( "Skipping inexistant item \"" + item + " \" from rarity \"" + name + "\"" );
        }
    }

    public boolean contains( ResourceLocation resLoc )
    {
        return items.contains( resLoc );
    }
}
