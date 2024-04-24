package com.example.parcial2dama;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModificarContacto extends AppCompatActivity {
    EditText nombre, telefono, genero;
    Button botonModificar, botonRegresar, botonEliminar;
    String id_contacto1, nombre2, telefono3, genero4;
    Configuraciones objConfiguracion = new Configuraciones();
    String URL = objConfiguracion.urlWebServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificar_contacto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.baseline_book_24);

        nombre = findViewById(R.id.txtNombreCompletoEditar);
        telefono = findViewById(R.id.txtTelefonoEditar);
        genero = findViewById(R.id.editTextText3);

        botonModificar = findViewById(R.id.modificar);
        botonRegresar = findViewById(R.id.btnRegresarEditar);
        botonEliminar = findViewById(R.id.btnEliminar);

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();
            }
        });

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
    }

    private void modificar(){
        try{
            RequestQueue objetoPeticion = Volley.newRequestQueue(ModificarContacto.this);
            StringRequest peticion = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject objJSONResultado = new JSONObject(response.toString());
                        String estado = objJSONResultado.getString("estado");
                        if(estado.equals("1")){
                            Toast.makeText(ModificarContacto.this, "Contacto Modificado con exito", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ModificarContacto.this, "Error: "+ estado, Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ModificarContacto.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("accion","modificar");
                    Log.d("id_contacto", "Valor de id_contacto: " + id_contacto1);
                    params.put("id_contacto",id_contacto1);
                    params.put("nombre",nombre.getText().toString());
                    params.put("telefono",telefono.getText().toString());
                    params.put("genero",genero.getText().toString());
                    return params;
                }
            };

            objetoPeticion.add(peticion);
        }catch (Exception error){
            Toast.makeText(ModificarContacto.this, "Error en tiempo de ejecucion: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void regresar(){
        Intent actividad = new Intent(ModificarContacto.this, MainActivity.class);
        startActivity(actividad);
        ModificarContacto.this.finish();
    }

    private void eliminar(){
        try{
            RequestQueue objetoPeticion = Volley.newRequestQueue(ModificarContacto.this);
            StringRequest peticion = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject objJSONResultado = new JSONObject(response.toString());
                        String estado = objJSONResultado.getString("estado");
                        if(estado.equals("1")){
                            Toast.makeText(ModificarContacto.this, "Contacto Eliminado con exito", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ModificarContacto.this, "Error: "+ estado, Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ModificarContacto.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("accion","eliminar");
                    Log.d("id_contacto", "Valor de id_contacto: " + id_contacto1);
                    params.put("id_contacto",id_contacto1);
                    return params;
                }
            };

            objetoPeticion.add(peticion);
        }catch (Exception error){
            Toast.makeText(ModificarContacto.this, "Error en tiempo de ejecucion: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle valoresAdicionales = getIntent().getExtras();
        if(valoresAdicionales==null){
            Toast.makeText(ModificarContacto.this, "Debe enviar un ID de contacto", Toast.LENGTH_SHORT).show();
            id_contacto1 = "";
            regresar();
        }else{
            id_contacto1 = valoresAdicionales.getString("id_contacto");
            Log.d("id_contacto", "Valor de id_contacto: " + id_contacto1);
            nombre2 = valoresAdicionales.getString("nombre");
            telefono3 = valoresAdicionales.getString("telefono");
            genero4 = valoresAdicionales.getString("genero");
            verContacto();
        }
    }

    private void verContacto(){

        nombre.setText(nombre2);
        telefono.setText(telefono3);
        genero.setText(genero4);
        Log.d("id_contacto", "Valor de id_contacto: " + id_contacto1);
    }
}
