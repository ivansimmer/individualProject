package com.example.istyleapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imgProfile;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    EditText etEmail;
    Button btnLogout;
    ImageButton buttonAtras;
    ImageButton buttonChangeImage;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inicializar componentes
        etEmail = findViewById(R.id.etEmail);
        btnLogout = findViewById(R.id.btnLogout);
        buttonAtras = findViewById(R.id.imageButtonAtras);
        imgProfile = findViewById(R.id.ivProfileImage);
        buttonChangeImage = findViewById(R.id.imageButtonEdit);
        auth = FirebaseAuth.getInstance();
        String email = "";

        if (auth.getCurrentUser() != null) {
            email = auth.getCurrentUser().getEmail(); // Obtiene el correo electrónico del usuario actual
        }
        etEmail.setText(email);

        // Botón para cerrar sesión
        btnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileActivity.this, SessionActivity.class);
            startActivity(intent);
            finish();
        });

        buttonAtras.setOnClickListener(view -> {

            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        imgProfile.setImageURI(imageUri);
                    }
                }
        );

        buttonChangeImage.setOnClickListener(view -> openImagePicker());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }
}

