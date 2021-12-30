package com.aubenoire.luckyblocks.listeners;

import com.aubenoire.luckyblocks.LuckyBlocks;
import com.aubenoire.luckyblocks.configuration.Options;
import com.aubenoire.luckyblocks.object.Reward;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public record Mining(LuckyBlocks instance) implements Listener {

    /**
     * When a player breaks a luckyblock
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if(event.getBlock().getType() == Options.MATERIAL) {
            event.setDropItems(false);
            event.setExpToDrop(0);
            Player player = event.getPlayer();
            int rand = new Random().nextInt(Options.REWARDS.size());
            Reward reward = Options.REWARDS.get(rand);

            if(reward.getCoins() > 0) {
                instance.getVaultEconomy().depositPlayer(player, reward.getCoins());
            }

            if(reward.getCommands() != null)
                for (String key : reward.getCommands())
                    instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), key.replaceAll("%player%", player.getName()));

            if(reward.getMessage().length() > 0)
                player.sendMessage(reward.getMessage());

            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 10, 10);
        }

    }

}
