package com.aubenoire.luckyblocks.command;

import com.aubenoire.luckyblocks.LuckyBlocks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    private final LuckyBlocks instance;

    public MainCommand(LuckyBlocks instance) {
        this.instance = instance;
        instance.getCommand("luckyblocks").setExecutor(this);
    }

    /**
     * Reload command
     * @param commandSender
     * @param command
     * @param s
     * @param strings
     * @return
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender.hasPermission("luckyblocks.admin")) {
            long start = System.currentTimeMillis();
            instance.reloadConfig();
            instance.loadOptions();
            instance.loadRewards();
            commandSender.sendMessage("§aLuckyBlocks configuration reloaded in " + (System.currentTimeMillis() - start) + "ms!");
        } else {
            commandSender.sendMessage("§cQuesto comando è riservato agli amministratori.");
        }
        return false;
    }
}
