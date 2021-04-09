package dadm.quixada.ufc.player2games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class PostDetails extends AppCompatActivity {

    private TextView titulo;
    private TextView descricao;
    private TextView numberLike;
    private Button btnLike;
    private ImageView img;
    private Boolean loadData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Post post = getIntent().getExtras().getParcelable("post");

        titulo = findViewById(R.id.textViewTitlePostDetails);
        descricao = findViewById(R.id.textViewDescPostDetails);
        numberLike = findViewById(R.id.textViewNumberLike);
        btnLike = findViewById(R.id.buttonLike);
        img = findViewById(R.id.imageViewPostDetails);

        titulo.setText(post.getTitulo());
        descricao.setText(post.getDescricao());
        btnLike.setText("LIKE");
        Picasso.get()
                .load(post.getImage())
                .into(img);

        String idUserCurrent = FirebaseAuth.getInstance().getUid();


        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Like like = new Like(idUserCurrent,post.getUuid());
                FirebaseFirestore.getInstance().collection("likes").document().set(like);

                finish();
                startActivity(getIntent());
            }
        });
        FirebaseFirestore.getInstance().collection("likes").whereEqualTo("idPostLike",post.getUuid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            numberLike.setText(task.getResult().size()+"");

                        } else{
                            Toast.makeText(PostDetails.this, "Não foi possivel encontrar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}