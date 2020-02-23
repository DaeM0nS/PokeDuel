package com.lypaka.pokeduel.Utils;

import com.lypaka.pokeduel.Config.ConfigSetter;
import com.pixelmonmod.pixelmon.battles.BattleQuery;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.comm.packetHandlers.battles.EnumBattleQueryResponse;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class BattleStarter {

    public static void startBattle (Player player1, Player player2) {
        EntityPlayerMP play1 = (EntityPlayerMP) player1;
        EntityPlayerMP play2 = (EntityPlayerMP) player2;


        /**
         *
         * Checks if both players are not in a battle, then forces the battle between them to start
         *
         */

        if (BattleRegistry.getBattle(player1.getName()) == null && BattleRegistry.getBattle(player2.getName()) == null) {
            EntityPixelmon challengerFirstPokemon = PixelmonStorage.pokeBallManager.getPlayerStorage(play1).get().getFirstAblePokemon((World) player1.getWorld());
            PixelmonStorage.pokeBallManager.getPlayerStorage(play1).get().sendOutFromPosition(challengerFirstPokemon.getPartyPosition(), (World) player1.getWorld());
            EntityPixelmon receiverFirstPokemon = PixelmonStorage.pokeBallManager.getPlayerStorage(play2).get().getFirstAblePokemon((World) player2.getWorld());
            PixelmonStorage.pokeBallManager.getPlayerStorage(play2).get().sendOutFromPosition(receiverFirstPokemon.getPartyPosition(), (World) player2.getWorld());
            BattleQuery battle = new BattleQuery(play1, challengerFirstPokemon, play2, receiverFirstPokemon);
            battle.acceptQuery(play1, EnumBattleQueryResponse.Accept);
            battle.acceptQuery(play2, EnumBattleQueryResponse.Accept);
        } else {


            player1.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "The code tried to start a battle, but one of you is in a battle and the challenge was cancelled!"));
            player2.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "The code tried to start a battle, but one of you is in a battle and the challenge was cancelled!"));
        }

    }

}
