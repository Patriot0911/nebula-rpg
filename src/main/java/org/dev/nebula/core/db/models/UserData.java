package org.dev.nebula.core.db.models;

public class UserData {

    private final String uuid;
    private final String nickName;
    private int level;
    private int exp;

    public UserData(String uuid, String nickName, int level, int exp) {
        this.uuid = uuid;
        this.nickName = nickName;
        this.level = level;
        this.exp = exp;
    }

    public String getUuid() {
        return uuid;
    }
    public String getNickName() {
        return nickName;
    }
    public int getLevel() {
        return level;
    }
    public int getExp() {
        return exp;
    }

    public void addExp(int amount) {
        exp += amount;
        // while (exp >= 100) {
        //     exp -= 100;
        //     level++;
        // }
    }
}
