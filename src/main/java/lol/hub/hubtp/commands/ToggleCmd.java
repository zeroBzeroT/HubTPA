package lol.hub.hubtp.commands;

import lol.hub.hubtp.Plugin;
import lol.hub.hubtp.RequestManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import static lol.hub.hubtp.Plugin.BLOCKED_PREFIX;

// tpt (tptoggle)
public class ToggleCmd extends TpCommand {
    public ToggleCmd(Plugin plugin, PluginCommand pluginCommand) {
        super(plugin, pluginCommand, 0);
    }

    @Override
    public void run(Player commandSender, String ignored) {
        if (plugin.isRequestBlock(commandSender)) {
            // if toggle is getting turned off, we delete instead of setting false
            plugin.getConfig().set(BLOCKED_PREFIX + commandSender.getUniqueId(), null);
            commandSender.sendMessage(
                Component.text("Request are now ", NamedTextColor.GOLD)
                    .append(Component.text("enabled", NamedTextColor.GREEN))
                    .append(Component.text("!", NamedTextColor.GOLD))
            );
        } else {
            plugin.getConfig().set(BLOCKED_PREFIX + commandSender.getUniqueId(), true);
            RequestManager.cancelRequestsByTarget(commandSender);
            commandSender.sendMessage(
                Component.text("Request are now ", NamedTextColor.GOLD)
                    .append(Component.text("disabled", NamedTextColor.RED))
                    .append(Component.text("!", NamedTextColor.GOLD))
            );
        }

        plugin.saveConfig();
    }
}
