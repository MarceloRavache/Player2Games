package dadm.quixada.ufc.player2games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

public class Home extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        verifyAuthentication();

        RecyclerView rv = findViewById(R.id.recycleHome);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        HandleGetPosts getPosts = new HandleGetPosts();
        getPosts.start();

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(Home.this, PostDetails.class);

                PostItem post = (PostItem) item;
                intent.putExtra("post", post.post);

                startActivity(intent);
            }
        });

    }
    private void verifyAuthentication() {
        if(FirebaseAuth.getInstance().getUid() == null){
            Intent intent = new Intent(Home.this,MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.seguindo:
                Intent intent1 = new Intent(Home.this,Seguindo.class);
                startActivity(intent1);
                break;
            case R.id.location:
                Intent intent2 = new Intent(Home.this,Location.class);
                startActivity(intent2);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                verifyAuthentication();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchPosts() {

        FirebaseFirestore.getInstance().collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Post post = new Post(
                                        document.getData().toString().replace("{","").replace("}","").split(",")[2].split("=")[1],
                                        document.getData().toString().replace("{","").replace("}","").split(",")[1].split("=")[1],
                                        document.getData().toString().replace("{","").replace("}","").split(",")[3].split("=")[1],
                                        document.getData().toString().replace("{","").replace("}","").split(",")[0].split("=")[1]
                                );
                                adapter.add(new PostItem(post));
                            }
                        } else {
                        }
                    }
                });
    }
    private class PostItem extends Item<ViewHolder> {

        private final Post post;

        private PostItem(Post post){
            this.post = post;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {

            TextView titulo = viewHolder.itemView.findViewById(R.id.textViewTitlePost);

            ImageView imgPost = viewHolder.itemView.findViewById(R.id.imageViewPost);

            Picasso.get()
                    .load(post.getImage())
                    .into(imgPost);

            titulo.setText(post.getTitulo());

        }

        @Override
        public int getLayout() {
            return R.layout.post_item;
        }

        public Post getUser() {
            return post;
        }
    }

    class HandleGetPosts extends Thread{

        HandleGetPosts(){
        }
        public void run(){
            fetchPosts();
        }
    }
}