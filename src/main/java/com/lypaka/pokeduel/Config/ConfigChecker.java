package com.lypaka.pokeduel.Config;

public class ConfigChecker {

    public static boolean betChallengeIssued(String player1, String player2) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Number-Of").getInt();
        for (int i = 1; i <= number; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Request-" + i).getString().contains(player1) && ConfigManager.getConfigNode("PokeDuel", "Bet-Battles", "Pending-Requests", "Request-" + i).getString().contains(player2)) {
                return true;
            }

        }
        return false;
    }


    public static boolean challengeIssued (String player1, String player2) {
        int number = ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Number-Of").getInt();
        for (int i = 1; i <= number; i++) {
            if (ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Request-" + i).getString().contains(player1) && ConfigManager.getConfigNode("PokeDuel", "Battles", "Pending-Requests", "Request-" + i).getString().contains(player2)) {
                return true;
            }
        }
        return false;
    }

}
