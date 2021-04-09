package dadm.quixada.ufc.player2games;

public class PontoMapa {

    private String idUser;
    private double latitude;
    private double longitude;

    public PontoMapa(String idUser, double latitude, double longitude) {
        this.idUser = idUser;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
