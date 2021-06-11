package harmonised.rarities.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import harmonised.rarities.RaritiesMod;
import harmonised.rarities.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Rarities
{
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String hardDataPath = "/assets/rarities/util/";
    public static final String defaultConfigName = "defaultConfig.json";
    public static final Gson gson = new Gson();
    public static final Type dummyTypeToken = new TypeToken<Map<String, DummyRarity>>(){}.getType();

    public static Map<String, Rarity> localRarities = new HashMap<>();
    public static Map<String, Rarity> rarities = new HashMap<>();

    public static void init()
    {
        File configFile = FMLPaths.CONFIGDIR.get().resolve( Reference.MOD_ID + ".json" ).toFile();
        if( !configFile.exists() )
            createDefaultConfig( configFile );
        try
        (
            InputStream input = new FileInputStream( configFile.getPath() );
            Reader reader = new BufferedReader( new InputStreamReader( input ) )
        )
        {
            Map<String, DummyRarity> dummyRarities = gson.fromJson(reader, dummyTypeToken);
            localRarities.clear();
            loadRarities( dummyRarities );
            localRarities = rarities;
        }
        catch( Exception e )
        {
            LOGGER.error( "ERROR READING RARITIES MOD CONFIG", e );
        }
    }

    public static void loadRarities( Map<String, DummyRarity> dummyRarities )
    {
        rarities.clear();
        for( Map.Entry<String, DummyRarity> entry : dummyRarities.entrySet() )
        {
            rarities.put( entry.getKey().toLowerCase(), new Rarity( entry.getKey(), entry.getValue().color, entry.getValue().items ) );
        }
        if( !rarities.containsKey( "common" ) )
            rarities.put( "common", new Rarity( "Common", 0xa8a8a8, new HashSet<>() ) );
    }

    private static void createDefaultConfig( File configFile )
    {
        try
        (
            InputStream inputStream = RaritiesMod.class.getResourceAsStream( hardDataPath + defaultConfigName );
            FileOutputStream outputStream = new FileOutputStream( configFile );
        )
        {
            LOGGER.debug( "Copying over " + defaultConfigName + " json config to " + configFile.getPath(), configFile.getPath() );
            IOUtils.copy( inputStream, outputStream );
        }
        catch( IOException e )
        {
            LOGGER.error( "Error copying over " + defaultConfigName + " json config to " + configFile.getPath(), configFile.getPath(), e );
        }
    }

    public static Rarity getRarity( ItemStack itemStack )
    {
        return getRarity( itemStack.getItem() );
    }

    public static Rarity getRarity( Item item )
    {
        return getRarity( item.getRegistryName() );
    }

    public static Rarity getRarity( ResourceLocation resLoc )
    {
        for( Map.Entry<String, Rarity> entry : Rarities.rarities.entrySet() )
        {
            if( entry.getValue().contains( resLoc ) )
                return entry.getValue();
        }
        return getRarity( "common" );
    }

    public static Rarity getRarity( String name )
    {
        return rarities.get( name.toLowerCase() );
    }
}
