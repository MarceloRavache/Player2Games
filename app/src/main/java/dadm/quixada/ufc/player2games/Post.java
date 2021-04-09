package dadm.quixada.ufc.player2games;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {

    private String uuid;
    private String titulo;
    private String descricao;
    private String image;

    public Post(String uuid, String titulo, String descricao, String image) {
        this.uuid = uuid;
        this.titulo = titulo;
        this.descricao = descricao;
        this.image = image;
    }

    protected Post(Parcel in) {
        uuid = in.readString();
        titulo = in.readString();
        descricao = in.readString();
        image = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(image);
    }
}
