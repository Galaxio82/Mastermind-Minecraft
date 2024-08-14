package fr.galaxio.mastermind;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MastermindCommand implements CommandExecutor {

    private final Main plugin;

    public MastermindCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("start")) {
                    if (args.length < 2) {
                        player.sendMessage(ChatColor.AQUA + "Usage: /mastermind start <targetPlayer> [color1 color2 color3 color4]");
                        return false;
                    }

                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null || !target.isOnline()) {
                        player.sendMessage(ChatColor.AQUA + "Le joueur cible n'est pas en ligne.");
                        return false;
                    }

                    List<Material> secretCode = new ArrayList<>();
                    if (args.length >= 6) {
                        for (int i = 2; i < args.length; i++) {
                            Material color = getMaterialFromString(args[i]);
                            if (color == null || !isWool(color)) {
                                player.sendMessage(ChatColor.AQUA + "Couleur invalide : " + args[i]);
                                return false;
                            }
                            secretCode.add(color);
                        }
                        if (secretCode.size() != 4) {
                            player.sendMessage(ChatColor.AQUA + "Il doit y avoir exactement 4 couleurs.");
                            return false;
                        }
                    } else {
                        secretCode = generateRandomColors();
                    }

                    plugin.startNewGame(player, target, secretCode);
                    player.sendMessage(ChatColor.AQUA + "Le jeu Mastermind a commencé avec " + target.getName() + " !");
                    target.sendMessage(ChatColor.AQUA + "Le jeu Mastermind a commencé avec " + player.getName() + " !");
                    return true;
                } else if (args[0].equalsIgnoreCase("stop")) {

                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null || !target.isOnline()) {
                        player.sendMessage(ChatColor.AQUA + "Le joueur cible n'est pas en ligne");
                        return false;
                    }

                    plugin.StopNowGame(target);
                    player.sendMessage(ChatColor.AQUA + "Le jeu Mastermind vient de finir ! Merci d'avoir joué");
                    return true;
                }
            } else {
                player.sendMessage(ChatColor.AQUA + "Usage: /mastermind <start / stop> <targetPlayer> [color1 color2 color3 color4]");
                return false;
            }
        }
        return false;
    }

    private Material getMaterialFromString(String colorName) {
        switch (colorName.toLowerCase()) {
            case "white":
                return Material.WHITE_WOOL;
            case "black":
                return Material.BLACK_WOOL;
            case "red":
                return Material.RED_WOOL;
            case "blue":
                return Material.BLUE_WOOL;
            case "yellow":
                return Material.YELLOW_WOOL;
            case "green":
                return Material.GREEN_WOOL;
            default:
                return null;
        }
    }

    private boolean isWool(Material material) {
        return material == Material.WHITE_WOOL ||
                material == Material.BLACK_WOOL ||
                material == Material.RED_WOOL ||
                material == Material.BLUE_WOOL ||
                material == Material.YELLOW_WOOL ||
                material == Material.GREEN_WOOL;
    }

    private List<Material> generateRandomColors() {
        List<Material> colors = Arrays.asList(
                Material.WHITE_WOOL,
                Material.BLACK_WOOL,
                Material.RED_WOOL,
                Material.BLUE_WOOL,
                Material.YELLOW_WOOL,
                Material.GREEN_WOOL
        );

        Collections.shuffle(colors);
        return colors.subList(0, 4);
    }
}