package me.liamhbest.hycopycore.utility;

import me.liamhbest.hycopycore.ranks.PlayerRank;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public class MetadataManager {

    private OfflinePlayer offlinePlayer;
    public MetadataManager(OfflinePlayer offlinePlayer){
        this.offlinePlayer = offlinePlayer;
    }

    public String getPlus(){
        HycopyPlayer hycopyPlayer = new HycopyPlayer(this.offlinePlayer);
        int amount = 0;

        if (hycopyPlayer.getRankManager().getRank() == PlayerRank.MVP_PLUS_PLUS){
            amount = 2;
        }

        if (hycopyPlayer.getRankManager().getRank() == PlayerRank.MVP_PLUS){
            amount = 1;
        }

        if (amount == 1){
            return CC.translate("&3+");

        } else if (amount == 2){
            return CC.translate("&3++");

        } else {
            return "";
        }
    }

    public ChatColor getPlusColor(){
        return ChatColor.AQUA;
    }

}
