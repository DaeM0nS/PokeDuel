package com.lypaka.pokeduel.Config;

public class ConfigSetter {

    public static void addBetBattleRequest (String player1, String player2, int bet) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Number-Of").getInt();
        for (int i = 1; i <= number + 1; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Request-" + i).isVirtual()) {
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Request-" + i, "Bet-Amount").setValue(bet);
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Request-" + i, "Challenger").setValue(player1);
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Request-" + i, "Receiver").setValue(player2);
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Number-Of").setValue(number + 1);
                ConfigManager.save();
                break;
            }
        }
    }

    public static void addBattleRequest (String player1, String player2) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Number-Of").getInt();
        for (int i = 1; i <= number + 1; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Request-" + i).isVirtual()) {
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Request-" + i, "Challenger").setValue(player1);
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Request-" + i, "Receiver").setValue(player2);
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Number-Of").setValue(number + 1);
                ConfigManager.save();
                break;

            }
        }
    }


    public static void removeBetBattleRequest (String player1, String player2, int bet) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Number-Of").getInt();
        for (int i = 1; i <= number; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Request-" + i, "Challenger").getString().equals(player1) && ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Request-" + i, "Receiver").getString().equals(player2)) {
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Request-" + i).setValue(null);
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Number-Of").setValue(number - 1);
                ConfigManager.save();
                break;
            }
        }
    }

    public static void removeBattleRequest (String player1, String player2) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Number-Of").getInt();
        for (int i = 1; i <= number; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Request-" + i, "Challenger").getString().equals(player1) && ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Request-" + i, "Receiver").getString().equals(player2)) {
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Request-" + i).setValue(null);
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Number-Of").setValue(number - 1);
                ConfigManager.save();
                break;

            }
        }
    }


    public static void addBattle (String player1, String player2) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Battles", "Number-Of").getInt();
        for (int i = 1; i <= number + 1; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Battles", "Battle-" + i).isVirtual()) {
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Battle-" + i, "Challenger").setValue(player1);
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Battle-" + i, "Receiver").setValue(player2);
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Number-Of").setValue(number + 1);
                ConfigManager.save();
                break;

            }
        }
    }

    public static void addBetBattle (String player1, String player2, int bet) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Number-Of").getInt();
        for (int i = 1; i <= number + 1; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battle-" + i).isVirtual()) {
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battle-" + i, "Challenger").setValue(player1);
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battle-" + i, "Receiver").setValue(player2);
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battle-" + i, "Bet-Amount").setValue(bet);
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Number-Of").setValue(number + 1);
                ConfigManager.save();
                break;

            }
        }
    }


    public static void removeBattle (String player1, String player2) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Battles", "Number-Of").getInt();
        for (int i = 1; i <= number + 1; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Battles", "Battle-" + i, "Challenger").getString().equals(player1) && ConfigManager.getConfigNode("PokeDuel", "Battles", "Battle-" + i, "Receiver").getString().equals(player2) || ConfigManager.getConfigNode("PokeDuel", "Battles", "Battle-" + i, "Challenger").getString().equals(player2) && ConfigManager.getConfigNode("PokeDuel", "Battles", "Battle-" + i, "Receiver").getString().equals(player1)) {
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Battle-" + i).setValue(null);
                ConfigManager.getConfigNode("PokeDuel", "Battles", "Number-Of").setValue(number - 1);
                ConfigManager.save();
                break;

            }
        }
    }

    public static void removeBetBattle (String player1, String player2) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Number-Of").getInt();
        for (int i = 1; i <= number + 1; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battle-" + i, "Challenger").getString().equals(player1) && ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battle-" + i, "Receiver").getString().equals(player2) || ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battle-" + i, "Challenger").getString().equals(player2) && ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battle-" + i, "Receiver").getString().equals(player1)) {
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Battle-" + i).setValue(null);
                ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Number-Of").setValue(number - 1);
                ConfigManager.save();
                break;

            }
        }
    }

}
