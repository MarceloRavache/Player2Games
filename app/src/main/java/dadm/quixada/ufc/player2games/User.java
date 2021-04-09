package dadm.quixada.ufc.player2games;

public class User {
    private String uuid;
    private String username;

    public User(){}

    public User(String uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }
}
