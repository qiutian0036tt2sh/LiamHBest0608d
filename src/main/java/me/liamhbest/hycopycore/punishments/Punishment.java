package me.liamhbest.hycopycore.punishments;

import lombok.Getter;
import lombok.Setter;

public class Punishment {

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private int days;

    @Getter
    @Setter
    private int minutes;

    @Getter
    @Setter
    private int seconds;

    @Getter
    @Setter
    private boolean permanent;

    @Getter
    @Setter
    private boolean connectDiscord;

    @Getter
    @Setter
    private PunishmentType punishmentType;

    public Punishment(PunishmentType punishmentType){
        this.punishmentType = punishmentType;
    }


}
