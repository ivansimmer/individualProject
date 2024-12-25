package com.example.istyleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPass;
    private Button buttonLogin, buttonCancel, buttonCreateAccount, buttonForgetPassword;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db; // Firestore instance

    private static final int GOOGLE_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private ImageButton buttonGoogleSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        initGoogleSignInClient();
        initComponents();
        setListeners();
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Agrega tu ID del cliente web de Firebase
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void loginWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Log.w("LoginActivity", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        if (idToken == null) {
            Log.e("LoginActivity", "El token de Google es nulo.");
            return;
        }

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveSession(user);
                            navigateToMainActivity();
                        }
                    } else {
                        Log.e("LoginActivity", "Error en la autenticación con Firebase.", task.getException());
                        Toast.makeText(LoginActivity.this, "Error al iniciar sesión con Google.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void initComponents() {
        etEmail = findViewById(R.id.editTextEmail);
        etPass = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonForgetPassword = findViewById(R.id.buttonForgetPassword);
        buttonGoogleSignIn = findViewById(R.id.btnGoogle);

        Log.d("LoginActivity", "Componentes inicializados correctamente.");
    }

    private void setListeners() {
        buttonCancel.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SessionActivity.class);
            startActivity(intent);
            finish();
        });

        buttonCreateAccount.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPass.getText().toString().trim();

            if (!validateInputs(email, password)) {
                return;
            }
            loginUser(email, password);
        });

        buttonForgetPassword.setOnClickListener(view -> resetPassword());

        buttonGoogleSignIn.setOnClickListener(view -> loginWithGoogle());
    }

    private void resetPassword() {
        String email = etEmail.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Correo electrónico no válido.");
            etEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Revisa tu correo", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Correo electrónico no válido.");
            etEmail.requestFocus();
            return false;
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 12) {
            etPass.setError("La contraseña debe tener entre 6 y 12 caracteres.");
            etPass.requestFocus();
            return false;
        }

        return true;
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            if (!user.isEmailVerified()) {
                                showAlert("Error", "Por favor verifica tu correo antes de iniciar sesión.");
                            } else {
                                saveSession(user);
                                navigateToMainActivity();
                            }
                        }
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Error desconocido";
                        Toast.makeText(LoginActivity.this, "Error al iniciar sesión. Revisa tus credenciales.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveSession(FirebaseUser user) {
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", user.getEmail());
        editor.apply();

        Log.d("LoginActivity", "Sesión guardada para el usuario: " + user.getEmail());
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

        Log.d("LoginActivity", "Redirigiendo a MainActivity.");
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            if (currentUser.isEmailVerified()) {
                // Proceed with verified user flow
            } else {
                // Notify the user to verify their email
            }
        } else {
            // Handle case where no user is logged in
            Log.e("LoginActivity", "No user is logged in.");
        }
    }
}