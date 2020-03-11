package com.lypaka.pokeduel.Utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.battles.BattleQuery;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.comm.packetHandlers.battles.EnumBattleQueryResponse;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;

import net.minecraft.entity.player.EntityPlayerMP;


public class BattleStarter {

    public static void startBattle (Player player1, Player player2) {
        EntityPlayerMP play1 = (EntityPlayerMP) player1;
        EntityPlayerMP play2 = (EntityPlayerMP) player2;


        /**
         *
         * Checks if both players are not in a battle, then forces the battle between them to start
         *
         */

        if (BattleRegistry.getBattle(play1).isPvP() == false && BattleRegistry.getBattle(play2).isPvP() == false) {
            EntityPixelmon challengerFirstPokemon = Pixelmon.storageManager.getParty(play1).getAndSendOutFirstAblePokemon(play1);
            EntityPixelmon receiverFirstPokemon = Pixelmon.storageManager.getParty(play2).getAndSendOutFirstAblePokemon(play2);
            BattleQuery battle = new BattleQuery(play1, challengerFirstPokemon, play2, receiverFirstPokemon);
            battle.acceptQuery(play1, EnumBattleQueryResponse.Accept);
            battle.acceptQuery(play2, EnumBattleQueryResponse.Accept);
            battle.battleRules.fullHeal=true;
        } else {


            player1.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "The code tried to start a battle, but one of you is in a battle and the challenge was cancelled!"));
            player2.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "The code tried to start a battle, but one of you is in a battle and the challenge was cancelled!"));
        }

    }

}
