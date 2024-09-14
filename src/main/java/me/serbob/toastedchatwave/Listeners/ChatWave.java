package me.serbob.toastedchatwave.Listeners;

import me.serbob.toastedchatwave.ToastedChatWave;
import me.serbob.toastedchatwave.Util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static me.serbob.toastedchatwave.Managers.WaveManager.*;

public class ChatWave implements Listener {
    public EventPriority currentEventPriority;
    public ChatWave(EventPriority currentEventPriority) {
        this.currentEventPriority=currentEventPriority;
    }

/*    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        executeChatWave(event);
    }*/

    public static void executeChatWave(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if(!isAvailable(event.getMessage()))
            return;

        if(receiveRewards(player)) {
            sendRewardMessages(player);
            sendRewards(player);
            manageAftermath(player);
        }

        if(ToastedChatWave.instance.getConfig().getString("show_in_chat_method").equalsIgnoreCase("message_reformat")) {
            event.setFormat(ChatUtil.c(formatFinalMessage(player, ChatColor.stripColor(event.getMessage()))));
        } else {
            Bukkit.broadcastMessage(ChatUtil.c(formatFinalMessage(player, ChatColor.stripColor(event.getMessage()))));
            event.setCancelled(true);
        }
    }
}
