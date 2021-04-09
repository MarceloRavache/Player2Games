package dadm.quixada.ufc.player2games;

import android.os.Parcel;
import android.os.Parcelable;

public class Follow implements Parcelable {

    private String idSeguidor;
    private String nomeSeguidor;
    private String idSeguindo;
    private String nomeSeguindo;

    public Follow(String idSeguidor, String nomeSeguidor, String idSeguindo, String nomeSeguindo) {
        this.idSeguidor = idSeguidor;
        this.nomeSeguidor = nomeSeguidor;
        this.idSeguindo = idSeguindo;
        this.nomeSeguindo = nomeSeguindo;
    }

    protected Follow(Parcel in) {
        idSeguidor = in.readString();
        nomeSeguidor = in.readString();
        idSeguindo = in.readString();
        nomeSeguindo = in.readString();
    }

    public static final Creator<Follow> CREATOR = new Creator<Follow>() {
        @Override
        public Follow createFromParcel(Parcel in) {
            return new Follow(in);
        }

        @Override
        public Follow[] newArray(int size) {
            return new Follow[size];
        }
    };

    public String getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(String idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    public String getNomeSeguidor() {
        return nomeSeguidor;
    }

    public void setNomeSeguidor(String nomeSeguidor) {
        this.nomeSeguidor = nomeSeguidor;
    }

    public String getIdSeguindo() {
        return idSeguindo;
    }

    public void setIdSeguindo(String idSeguindo) {
        this.idSeguindo = idSeguindo;
    }

    public String getNomeSeguindo() {
        return nomeSeguindo;
    }

    public void setNomeSeguindo(String nomeSeguindo) {
        this.nomeSeguindo = nomeSeguindo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idSeguidor);
        dest.writeString(nomeSeguidor);
        dest.writeString(idSeguindo);
        dest.writeString(nomeSeguindo);
    }
}
