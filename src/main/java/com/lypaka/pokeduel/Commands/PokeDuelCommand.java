package com.lypaka.pokeduel.Commands;

import com.lypaka.pokeduel.Config.ConfigChecker;
import com.lypaka.pokeduel.Config.ConfigSetter;
import com.lypaka.pokeduel.PokeDuel;
import com.lypaka.pokeduel.Utils.BattleStarter;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.math.BigDecimal;
import java.util.Optional;

public class PokeDuelCommand {



    public static void registerDuelCommand() {

        EventContext eventContext = EventContext.builder().add(EventContextKeys.PLUGIN, PokeDuel.getContainer()).build();
        Optional<EconomyService> econ = Sponge.getServiceManager().provide(EconomyService.class);

        CommandSpec sendRequest = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.optional(GenericArguments.integer(Text.of("betAmount")))
                )
                .executor((sender, context) -> {
                    Player challenger = (Player) sender;
                    Player receiver = (Player) context.getOne("player").get();
                    Player receiver2 = Sponge.getServer().getPlayer(receiver.getName()).get();

                    if (context.getOne("betAmount").isPresent()) {
                        int bet = (int) context.getOne("betAmount").get();

                        /**
                         * Checks sender's account balance to ensure they have at least the amount of money they want to put up for a bet
                         * Makes a new pending battle request in the config, to store the values of the player names and the bet amount
                         * Sends the player being challenged a message, stating that the challenger player has challenged them to a battle with a bet of money being in place, prompting them with clickable text options to accept or deny the challenge
                         */


                        if (!ConfigChecker.betChallengeIssued(challenger.getName(), receiver.getName())) {
                            if (econ.isPresent()) {

                                Optional<UniqueAccount> a = econ.get().getOrCreateAccount(challenger.getUniqueId());
                                Currency defaultCur = econ.get().getDefaultCurrency();

                                if (a.get().getBalance(defaultCur).intValue() >= bet) {

                                    a.get().withdraw(defaultCur, BigDecimal.valueOf(bet), Cause.of(eventContext, PokeDuel.getContainer()));
                                    ConfigSetter.addBetBattleRequest(challenger.getName(), receiver2.getName(), bet);
                                    receiver2.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, challenger.getName() + " challenged you to a battle with a bet of " + bet + " money! Do you accept?"));
                                    Text yes = Text.builder("Yes").onClick(TextActions.runCommand("/pokeduel accept " + challenger.getName() + " " + bet)).build();
                                    Text no = Text.builder("No").onClick(TextActions.runCommand("/pokeduel deny " + challenger.getName() + " " + bet)).build();
                                    receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.DARK_RED, yes, TextColors.WHITE, " or ", TextColors.DARK_RED, no));

                                } else {

                                    challenger.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You don't have enough money to place the bet!"));

                                }
                            }
                        } else {

                            challenger.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You currently have a pending battle request with this player!"));

                        }

                        /**

                            No bet was specified, so none was applied

                         */
                    } else {
                        if (!ConfigChecker.challengeIssued(challenger.getName(), receiver2.getName())) {

                            ConfigSetter.addBattleRequest(challenger.getName(), receiver2.getName());
                            receiver2.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, challenger.getName() + " challenged you to a battle! Do you accept?"));
                            Text yes = Text.builder("Yes").onClick(TextActions.runCommand("/pokeduel accept " + challenger.getName())).build();
                            Text no = Text.builder("No").onClick(TextActions.runCommand("/pokeduel deny " + challenger.getName())).build();
                            receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.DARK_RED, yes, TextColors.WHITE, " or ", TextColors.DARK_RED, no));

                        } else {

                            challenger.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You currently have a pending battle request with this player!"));

                        }
                    }

                return CommandResult.success();
                })
                .build();


        CommandSpec acceptRequest = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.optional(GenericArguments.integer(Text.of("betAmount")))
                )
                .executor((sender, context) -> {

                    Player receiver = (Player) sender;
                    Player challenger = (Player) context.getOne("player").get();
                    Player challenger2 = Sponge.getServer().getPlayer(challenger.getName()).get();

                    if (context.getOne("betAmount").isPresent()) {
                        int bet = (int) context.getOne("betAmount").get();
                        if (ConfigChecker.betChallengeIssued(challenger2.getName(), receiver.getName())) {
                            if (econ.isPresent()) {

                                Optional<UniqueAccount> a = econ.get().getOrCreateAccount(receiver.getUniqueId());
                                Currency defaultCur = econ.get().getDefaultCurrency();

                                if (a.get().getBalance(defaultCur).intValue() >= bet) {

                                    a.get().withdraw(defaultCur, BigDecimal.valueOf(bet), Cause.of(eventContext, PokeDuel.getContainer()));
                                    ConfigSetter.removeBetBattleRequest(challenger2.getName(), receiver.getName(), bet);
                                    ConfigSetter.addBetBattle(challenger2.getName(), receiver.getName(), bet);
                                    receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You accepted " + challenger2.getName() + "'s request!"));
                                    challenger2.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, receiver.getName() + " accepted your request!"));
                                    BattleStarter.startBattle(challenger2, receiver);

                                } else {

                                    receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You don't have enough money for the bet!"));

                                }
                            }


                        } else {

                            receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You don't have a battle request from " + challenger2.getName() + "!"));

                        }
                    } else {

                        if (ConfigChecker.challengeIssued(challenger2.getName(), receiver.getName())) {

                            ConfigSetter.removeBattleRequest(challenger2.getName(), receiver.getName());
                            ConfigSetter.addBattle(challenger2.getName(), receiver.getName());
                            receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You accepted " + challenger2.getName() + "'s request!"));
                            challenger2.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, receiver.getName() + " accepted your request!"));
                            BattleStarter.startBattle(challenger2, receiver);

                        } else {

                            receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You don't have a battle request from " + challenger2.getName() + "!"));

                        }

                    }
                    return CommandResult.success();
                })
                .build();


        CommandSpec denyRequest = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.optional(GenericArguments.integer(Text.of("betAmount")))
                )
                .executor((sender, context) -> {

                    Player receiver = (Player) sender;
                    Player challenger = (Player) context.getOne("player").get();
                    Player challenger2 = Sponge.getServer().getPlayer(receiver.getName()).get();


                    if (context.getOne("betAmount").isPresent()) {
                        int bet = (int) context.getOne("betAmount").get();
                        if (ConfigChecker.betChallengeIssued(challenger2.getName(), receiver.getName())) {

                            ConfigSetter.removeBetBattleRequest(challenger2.getName(), receiver.getName(), bet);
                            receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You denied " + challenger2.getName() + "'s request!"));
                            challenger2.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, receiver.getName() + " denied your request!"));

                        } else {

                            receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You don't have a battle request from " + challenger2.getName() + "!"));

                        }
                    } else {

                        if (ConfigChecker.challengeIssued(challenger2.getName(), receiver.getName())) {

                            ConfigSetter.removeBattleRequest(challenger2.getName(), receiver.getName());
                            receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You denied " + challenger2.getName() + "'s request!"));
                            challenger2.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, receiver.getName() + " denied your request!"));

                        } else {

                            receiver.sendMessage(Text.of(TextColors.GOLD, "[", TextColors.DARK_RED, "PokeDuel", TextColors.GOLD, "] ", TextColors.WHITE, "You don't have a battle request from " + challenger2.getName() + "!"));

                        }

                    }
                    return CommandResult.success();
                })
                .build();

        CommandSpec main = CommandSpec.builder()
                .child(sendRequest, "challenge")
                .child(acceptRequest, "accept")
                .child(denyRequest, "deny")
                .executor((sender, context) -> {
                    if (sender instanceof ConsoleSource) {
                        sender.sendMessage(Text.of("This command is only usable by players!"));
                    }
                return CommandResult.success();
                })
                .build();

        Sponge.getCommandManager().register(PokeDuel.instance, main, "pokeduel");
    }

}
