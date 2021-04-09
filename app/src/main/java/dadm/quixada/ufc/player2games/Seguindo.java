package dadm.quixada.ufc.player2games;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class Seguindo extends AppCompatActivity {

    private GroupAdapter adapter;
    private EditText name;
    private Button btnSeguir;

    private String idSeguindo = "";
    private String idSeguir = "";

    private String nomeSeguir = "";
    private String nomeSeguindo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguindo);

        RecyclerView rv = findViewById(R.id.recyclerSeguindo);
        name = findViewById(R.id.editTextSeguirName);
        btnSeguir = findViewById(R.id.buttonSeguir);

        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        HandleGetUsers getUsers = new HandleGetUsers();
        getUsers.start();

        btnSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nomeSeguir = name.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                idSeguindo = user.getUid();

                FirebaseFirestore.getInstance().collection("users").whereEqualTo("uuid",idSeguindo)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        User userCurrent = document.toObject(User.class);

                                        nomeSeguindo = userCurrent.getUsername();
                                    }
                                }
                            }
                        });
                FirebaseFirestore.getInstance().collection("users").whereEqualTo("username",nomeSeguir)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        User userFollow = document.toObject(User.class);

                                        idSeguir = userFollow.getUuid();

                                        Follow follow = new Follow(idSeguindo,nomeSeguindo,idSeguir,nomeSeguir);

                                        FirebaseFirestore.getInstance().collection("follow").document().set(follow);

                                        finish();
                                        startActivity(getIntent());
                                    }
                                }
                            }
                        });
            }
        });
    }

    private void fetchUsers() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore.getInstance().collection("follow").whereEqualTo("idSeguidor",user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Follow follow = new Follow(
                                        document.getData().toString().replace("{","").replace("}","").split(",")[3].split("=")[1],
                                        document.getData().toString().replace("{","").replace("}","").split(",")[1].split("=")[1],
                                        document.getData().toString().replace("{","").replace("}","").split(",")[2].split("=")[1],
                                        document.getData().toString().replace("{","").replace("}","").split(",")[0].split("=")[1]
                                );
                                adapter.add(new UserItem(follow));
                            }
                        } else {
                        }
                    }
                });
    }
    private class UserItem extends Item<ViewHolder> {

        private final Follow user;

        private UserItem(Follow user){
            this.user = user;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView txtUsername = viewHolder.itemView.findViewById(R.id.textView2);
            Button btnUnFollow = viewHolder.itemView.findViewById(R.id.buttonUnFollow);

            btnUnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CollectionReference itemsRef = FirebaseFirestore.getInstance().collection("follow");
                    Query query = itemsRef.whereEqualTo("idSeguidor", user.getIdSeguidor()).whereEqualTo("idSeguindo", user.getIdSeguindo());
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    itemsRef.document(document.getId()).delete();
                                }
                                finish();
                                startActivity(getIntent());
                            } else {
                                Toast.makeText(Seguindo.this, "Não foi possivel deixar de seguir",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

            txtUsername.setText(user.getNomeSeguindo());
        }

        @Override
        public int getLayout() {
            return R.layout.item_user;
        }

        public Follow getUser() {
            return user;
        }
    }

    class HandleGetUsers extends Thread{

        HandleGetUsers(){
        }
        public void run(){
            fetchUsers();
        }
    }
}