package me.liamhbest.hycopycore.utility;

import lombok.Getter;
import me.liamhbest.hycopycore.punishments.PunishmentManager;
import me.liamhbest.hycopycore.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class HycopyPlayer {

    private final OfflinePlayer offlinePlayer;

    @Getter
    private final RankManager rankManager;

    @Getter
    private final PunishmentManager punishmentManager;

    @Getter
    private final MetadataManager metadataManager;

    public HycopyPlayer(OfflinePlayer offlinePlayer){
        this.offlinePlayer = offlinePlayer;
        this.rankManager = new RankManager(offlinePlayer);
        this.punishmentManager = new PunishmentManager(offlinePlayer);
        this.metadataManager = new MetadataManager(offlinePlayer);
    }

    public Player getPlayer(){
        if (offlinePlayer.isOnline()){
            return Bukkit.getPlayer(offlinePlayer.getUniqueId());
        } else {
            return null;
        }
    }

}





