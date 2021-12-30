package com.aubenoire.luckyblocks;

import com.aubenoire.luckyblocks.command.MainCommand;
import com.aubenoire.luckyblocks.configuration.Options;
import com.aubenoire.luckyblocks.listeners.Mining;
import com.aubenoire.luckyblocks.object.Reward;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class LuckyBlocks extends JavaPlugin {

    @Getter
    private Economy vaultEconomy = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadOptions();
        loadRewards();
        getServer().getPluginManager().registerEvents(new Mining(this), this);

        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        new MainCommand(this);
    }

    @Override
    public void onDisable() {}

    /**
     * Load config options
     */
    public void loadOptions() {
        Options.MATERIAL = Material.getMaterial(getConfig().getString("material").toUpperCase());
    }

    /**
     * Load rewards from config.yml
     */
    public void loadRewards() {
        List<Reward> rewards = new ArrayList<>();
        getConfig().getConfigurationSection("rewards").getKeys(false).forEach(key -> {
            rewards.add(new Reward(getConfig().getStringList("rewards." + key + ".commands"), getConfig().getString("rewards." + key + ".message").replaceAll("&", "§"), (float)getConfig().getDouble("rewards." + key + ".coins")));
        });
        Options.REWARDS = rewards;
    }

    /**
     * Setup Vault Economy - https://www.spigotmc.org/threads/hook-into-vault.68252/
     * @return setup successful
     */
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        vaultEconomy = rsp.getProvider();
        return vaultEconomy != null;
    }

}
