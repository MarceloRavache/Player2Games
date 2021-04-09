package dadm.quixada.ufc.player2games;

public class Like {

    private String idUserLike;
    private String idPostLike;

    public Like(String idUserLike, String idPostLike) {
        this.idUserLike = idUserLike;
        this.idPostLike = idPostLike;
    }

    public String getIdUserLike() {
        return idUserLike;
    }

    public void setIdUserLike(String idUserLike) {
        this.idUserLike = idUserLike;
    }

    public String getIdPostLike() {
        return idPostLike;
    }

    public void setIdPostLike(String idPostLike) {
        this.idPostLike = idPostLike;
    }
}
