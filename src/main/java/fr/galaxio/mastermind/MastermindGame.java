package fr.galaxio.mastermind;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MastermindGame {

    private Main plugin;

    private final List<Material> secretCode;
    private final List<Material> playerGuess;
    private final Player player;
    private final Player target;

    public MastermindGame(Main plugin, Player player, Player target, List<Material> secretCode) {
        this.plugin = plugin;
        this.player = player;
        this.target = target;
        this.secretCode = secretCode;
        this.playerGuess = new ArrayList<>();

        player.sendMessage(ChatColor.AQUA + "Le code que " + target.getName() + " doit retrouver est " + secretCode);
    }

    private List<Material> generateSecretCode() {
        List<Material> colors = new ArrayList<>();
        colors.add(Material.RED_WOOL);
        colors.add(Material.BLUE_WOOL);
        colors.add(Material.GREEN_WOOL);
        colors.add(Material.YELLOW_WOOL);
        colors.add(Material.WHITE_WOOL);
        colors.add(Material.BLACK_WOOL);
        Collections.shuffle(colors);
        return colors.subList(0, 4);
    }

    public void addGuess(Material wool) {
        if (playerGuess.size() < 4) {
            playerGuess.add(wool);
        }
    }

    public boolean isGuessComplete() {
        return playerGuess.size() == 4;
    }

    public void checkGuess() {
        int correctPosition = 0;
        int correctColor = 0;

        List<Material> remainingSecretCode = new ArrayList<>(secretCode);
        List<Material> remainingPlayerGuess = new ArrayList<>();

        for (int i = 0; i < playerGuess.size(); i++) {
            Material guessedColor = playerGuess.get(i);

            if (guessedColor.equals(secretCode.get(i))) {
                correctPosition++;
                remainingSecretCode.set(i, null);
            } else {
                remainingPlayerGuess.add(guessedColor);
            }
        }

        for (Material guessedColor : remainingPlayerGuess) {
            if (remainingSecretCode.contains(guessedColor)) {
                correctColor++;
                remainingSecretCode.set(remainingSecretCode.indexOf(guessedColor), null);
            }
        }

        target.sendMessage(ChatColor.AQUA + "Positions correctes: " + correctPosition + ", Couleurs correctes: " + correctColor);

        if(correctPosition == 4) {
            target.sendMessage(ChatColor.AQUA + "Vous avez trouvé la bonne composition de couleur. Bravo !");
            player.sendMessage(ChatColor.AQUA + target.getName() + " à trouvé la solution au Mastermind");
            plugin.getActiveGames().remove(target);
        }

        playerGuess.clear();
    }
}