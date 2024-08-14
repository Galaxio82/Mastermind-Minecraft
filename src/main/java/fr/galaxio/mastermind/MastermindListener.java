package fr.galaxio.mastermind;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class MastermindListener implements Listener {

    private final Main plugin;

    public MastermindListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block placedBlock = event.getBlock();
        Block blockBelow = placedBlock.getRelative(0, -1, 0);

        if (plugin.isPlayerInGame(player)) {
            if (isWool(placedBlock) && blockBelow.getType() == Material.QUARTZ_BLOCK) {
                MastermindGame game = plugin.getGameForPlayer(player);

                game.addGuess(placedBlock.getType());

                if(game.isGuessComplete()) {
                    game.checkGuess();
                }
            } else {
                player.sendMessage(ChatColor.AQUA + "Veuillez placer des blocs de laine sur des blocs de quartz !");
            }
        }
    }

    private boolean isWool(Block block) {
        return block.getType() == Material.WHITE_WOOL ||
                block.getType() == Material.BLACK_WOOL ||
                block.getType() == Material.RED_WOOL ||
                block.getType() == Material.BLUE_WOOL ||
                block.getType() == Material.YELLOW_WOOL ||
                block.getType() == Material.GREEN_WOOL;
    }
}