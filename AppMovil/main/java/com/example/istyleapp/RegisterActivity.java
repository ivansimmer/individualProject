// Register.java
package com.example.istyleapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etPassword;
    private EditText etEmail;
    private EditText etConfirmPassword;
    Button buttonRegister;
    Button buttonCancel;
    private EditText etTelefono;
    private EditText etDireccion;

    private FirebaseFirestore db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        initComponents();
        setListeners();
    }

    private void initComponents(){

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonCancel = findViewById(R.id.button2);
        etPassword = findViewById(R.id.editTextPassword);
        etEmail = findViewById(R.id.editTextEmail);
        etConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        etTelefono = findViewById(R.id.editTextTelefono);
        etDireccion = findViewById(R.id.editTextDireccion);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setListeners(){

        buttonCancel.setOnClickListener(view -> {

            Intent intent = new Intent(RegisterActivity.this, SessionActivity.class);
            startActivity(intent);
        });

        buttonRegister.setOnClickListener(view -> {

            String password = etPassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();

            if (!validateInputs(password, email, confirmPassword, telefono, direccion)) {
                return;
            }

            registerUserInFirebase(email, password);
        });
    }

    private boolean validateInputs(String password, String email, String confirmPassword, String telefono, String direccion) {
        if (password.isEmpty() || password.length() < 6 || password.length() > 12) {
            etPassword.setError("La contraseña debe tener entre 6 y 12 caracteres.");
            etPassword.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("El correo electrónico no es válido.");
            etEmail.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Las contraseñas no coinciden.");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (telefono.length() != 9) {
            etTelefono.setError("El telefono debe contener 9 caracteres.");
            etTelefono.requestFocus();
            return false;
        }
        if (direccion.isEmpty()) {
            etDireccion.setError("La direccion no puede estar vacia.");
            etDireccion.requestFocus();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerUserInFirebase(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            sendVerificationEmail(user); // Enviar correo de verificación

                            // Guardar datos en Firebase Database
                            saveUserData(user.getUid(), email);

                            Toast.makeText(RegisterActivity.this, "Usuario registrado exitosamente. Por favor verifica tu correo.", Toast.LENGTH_LONG).show();

                            // Redirigir a la pantalla de inicio de sesión
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Error desconocido";
                        showAlert("Error", "No se pudo registrar el usuario: " + errorMessage);
                    }
                });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("RegisterActivity", "Correo de verificación enviado a: " + user.getEmail());
                    } else {
                        Log.e("RegisterActivity", "Error al enviar el correo de verificación", task.getException());
                        Toast.makeText(RegisterActivity.this, "No se pudo enviar el correo de verificación. Intenta nuevamente.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveUserData(String userId, String email) {
        String password = String.valueOf(etPassword.getText());
        String telefono = String.valueOf(etTelefono.getText());
        String direccion = String.valueOf(etDireccion.getText());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);

        // Guardar datos del usuario en Firestore
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", password);
        userData.put("telefono", telefono);
        userData.put("direccion", direccion);
        userData.put("fecha_registro", formattedDate);

        db.collection("usuarios").document(userId).set(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Datos de usuario guardados correctamente.", Toast.LENGTH_SHORT).show();
                    Log.d("FirestoreDB", "Datos guardados correctamente en Firestore.");
                    sendDataToMySQL(email, password, telefono, direccion, formattedDate);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al guardar los datos del usuario.", Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreDB", "Error al guardar los datos en Firestore", e);
                });
    }

    // Método para enviar datos a la tabla `usuarios` de MySQL
    private void sendDataToMySQL(String email, String password, String telefono, String direccion, String fechaRegistro) {
        Log.d("RetrofitDebug", "Enviando email: " + email);
        Log.d("RetrofitDebug", "Enviando password: " + password);
        Log.d("RetrofitDebug", "Enviando telefono: " + telefono);
        Log.d("RetrofitDebug", "Enviando direccion: " + direccion);
        Log.d("RetrofitDebug", "Enviando fechaRegistro: " + fechaRegistro);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<ResponseBody> call = apiService.insertUser(email, password, fechaRegistro);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("RetrofitDebug", "Respuesta: " + response.body().toString());
                } else {
                    Log.e("RetrofitDebug", "Error en la llamada Retrofit.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("RetrofitDebug", "Error en Retrofit: ", t);
            }
        });
    }

    // Método para mostrar alertas
    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}