package com.lypaka.pokeduel.Listeners;

import com.lypaka.pokeduel.Config.ConfigManager;
import com.lypaka.pokeduel.Config.ConfigSetter;
import com.lypaka.pokeduel.PokeDuel;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import java.math.BigDecimal;
import java.util.Optional;

public class BattleEndListener {

    public int bet;

    @SubscribeEvent
    public void onBattleEnd (BattleEndEvent event) {
        if (event.bc.participants.get(0).getEntity() instanceof EntityPlayer && event.bc.participants.get(1).getEntity() instanceof EntityPlayer) {
            EventContext eventContext = EventContext.builder().add(EventContextKeys.PLUGIN, PokeDuel.getContainer()).build();
            Optional<EconomyService> econ = Sponge.getServiceManager().provide(EconomyService.class);
            Player player1 = (Player) event.bc.participants.get(0).getEntity();
            Player player2 = (Player) event.bc.participants.get(1).getEntity();

            /**
             *
             * Checks both possibilities of order of player names, in case the event switches the order
             *
             */

            if (isBetBattle(player1.getName(), player2.getName()) || isBetBattle(player2.getName(), player1.getName())) {
                ConfigSetter.removeBetBattle(player1.getName(), player2.getName());
                if (event.bc.participants.get(0).isDefeated) {
                    if (econ.isPresent()) {

                        Optional<UniqueAccount> a = econ.get().getOrCreateAccount(player2.getUniqueId());
                        Currency defaultCur = econ.get().getDefaultCurrency();

                        setBet(player1.getName(), player2.getName());
                        a.get().deposit(defaultCur, BigDecimal.valueOf(getBet()), Cause.of(eventContext, PokeDuel.getContainer()));
                    }
                } else if (event.bc.participants.get(1).isDefeated) {
                    if (econ.isPresent()) {

                        Optional<UniqueAccount> a = econ.get().getOrCreateAccount(player1.getUniqueId());
                        Currency defaultCur = econ.get().getDefaultCurrency();

                        setBet(player1.getName(), player2.getName());
                        a.get().deposit(defaultCur, BigDecimal.valueOf(getBet()), Cause.of(eventContext, PokeDuel.getContainer()));
                    }
                }
            } else {
                ConfigSetter.removeBattle(player1.getName(), player2.getName());
            }

        }
    }


    private boolean isBetBattle (String player1, String player2) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Number-Of").getInt();
        for (int i = 1; i <= number; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Challenger").getString().contains(player1) && ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Receiver").getString().contains(player2) || ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Challenger").getString().contains(player2) && ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Receiver").getString().contains(player1)) {
                return true;
            }
        }
        return false;
    }

    private void setBet(String player1, String player2) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Number-Of").getInt();
        for (int i = 1; i <= number; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Challenger").getString().contains(player1) && ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Receiver").getString().contains(player2)) {
                bet = ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Bet-Amount").getInt();
            } else if (ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Challenger").getString().contains(player2) && ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Receiver").getString().contains(player1)) {
                bet = ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battles", "Battle-" + i, "Bet-Amount").getInt();
            }
        }
    }

    private int getBet () {
        return bet;
    }

}
