package fr.galaxio.mastermind;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {

    private static Main instance;
    private Map<Player, MastermindGame> activeGames = new HashMap<>();

    @Override
    public void onEnable() {
        this.instance = instance;

        getLogger().info("Plugin Mastermind activé !");
        getCommand("mastermind").setExecutor(new MastermindCommand(this));
        getServer().getPluginManager().registerEvents(new MastermindListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Mastermind désactivé !");
    }

    public static Main getInstance() { return instance; }

    public Map<Player, MastermindGame> getActiveGames() { return activeGames; }

    public void startNewGame(Player player, Player target, List<Material> secretCode) {
        MastermindGame game = new MastermindGame(this, player, target, secretCode);
        activeGames.put(player, game);
    }

    public void StopNowGame(Player player) {
        activeGames.remove(player);
    }

    public boolean isPlayerInGame(Player player) {
        return activeGames.containsKey(player);
    }

    public MastermindGame getGameForPlayer(Player player) {
        return activeGames.get(player);
    }
}