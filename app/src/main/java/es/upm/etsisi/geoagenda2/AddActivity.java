package es.upm.etsisi.geoagenda2;

/**
 * Created by androidstudio on 10/11/16.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity {

    private boolean Modificar = false;

    public Contacto mContact;

    private static final Uri URI_CONTENT_PROVIDER = Uri.parse("content://es.upm.etsisi.geoagendacontentprovider/contacto");

    private TextView mName;
    private TextInputLayout tilNombre;
    private TextView mApellido1;
    private TextInputLayout tilApellido1;
    private TextView mApellido2;
    private TextInputLayout tilApellido2;
    private TextView mPhone;
    private TextInputLayout tilPhone;
    private TextView mEmail;
    private TextInputLayout tilEmail;
    private TextView mAdress;
    private TextInputLayout tilAddress;
    private View mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        ActionBar myToolbarAdd = getSupportActionBar();
        myToolbarAdd.hide();

        cargaElementos();

        Modificar = getIntent().getBooleanExtra("Modificar",false);

        if (Modificar) {
            mContact = Contacto.getItem(getIntent().getIntExtra("ID",0));
            cargaDatosContacto();
        }

        FloatingActionButton bSave = (FloatingActionButton)findViewById(R.id.save);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the corky is clicked
                SaveContact();
            }
        });

        FloatingActionButton bCancel = (FloatingActionButton)findViewById(R.id.cancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the corky is clicked
                Cancel();
            }
        });

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mColor.setBackgroundColor(Color.parseColor("#FAFAFA"));
                } else if (s.length() >= 1) {
                    if (!validarTexto(String.valueOf(s),tilNombre,30)) {
                        tilNombre.setError("Formato de Nombre incorrecto");
                        mColor.setBackgroundColor(Color.parseColor("#FAFAFA"));
                    }
                    else {
                        mColor.setBackgroundColor(Integer.parseInt(Contacto.getColor(getApplicationContext(), s.toString().toUpperCase())));
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mApellido1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    if (!validarTexto(String.valueOf(s),tilApellido1,15)) {
                        tilApellido1.setError("Formato de Apellido 1 incorrecto");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mApellido2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    if (!validarTexto(String.valueOf(s),tilApellido2,15)) {
                        tilApellido2.setError("Formato de Apellido 2 incorrecto");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mAdress.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    validarDireccion(String.valueOf(s));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    tilNombre.setCounterEnabled(false);
                }
                else
                    tilNombre.setCounterEnabled(true);

            }
        });

        mApellido1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    tilApellido1.setCounterEnabled(false);
                }
                else
                    tilApellido1.setCounterEnabled(true);

            }
        });

        mApellido2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    tilApellido2.setCounterEnabled(false);
                }
                else
                    tilApellido2.setCounterEnabled(true);

            }
        });

        mAdress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    tilAddress.setCounterEnabled(false);
                }
                else
                    tilAddress.setCounterEnabled(true);

            }
        });

        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    if (!String.valueOf(mEmail.getText()).equals("")) {
                        validarEmail(String.valueOf(mEmail.getText()));
                    }
                }
            }
        });

        mPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    mPhone.setText(formateaTelefono());
                    validarTelefono(String.valueOf(mPhone.getText()));
                }
            }
        });

        LinearLayout mLayout=(LinearLayout)findViewById(R.id.activity_alta_contacto);
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

    }

    private void cargaElementos() {
        mName = (TextView) findViewById(R.id.ADD_name);
        tilNombre = (TextInputLayout) findViewById(R.id.til_nombre);
        tilNombre.setCounterMaxLength(30);

        mApellido1 = (TextView) findViewById(R.id.ADD_apellido1);
        tilApellido1 = (TextInputLayout) findViewById(R.id.til_apellido1);
        tilApellido1.setCounterMaxLength(15);

        mApellido2 = (TextView) findViewById(R.id.ADD_apellido2);
        tilApellido2 = (TextInputLayout) findViewById(R.id.til_apellido2);
        tilApellido2.setCounterMaxLength(15);

        mPhone = (TextView) findViewById(R.id.ADD_phone);
        tilPhone = (TextInputLayout) findViewById(R.id.til_phone);

        mEmail = (TextView) findViewById(R.id.ADD_email);
        tilEmail = (TextInputLayout) findViewById(R.id.til_email);

        mAdress = (TextView) findViewById(R.id.ADD_address);
        tilAddress = (TextInputLayout) findViewById(R.id.til_address);
        tilAddress.setCounterMaxLength(50);

        mColor = findViewById(R.id.ADD_icono);
    }

    private void cargaDatosContacto() {

        mName.setText(mContact.get(Contacto.Field.NOMBRE));
        mApellido1.setText(mContact.get(Contacto.Field.APELLIDO1));
        mApellido2.setText(mContact.get(Contacto.Field.APELLIDO2));
        mPhone.setText(mContact.get(Contacto.Field.TELEFONO));
        mEmail.setText(mContact.get(Contacto.Field.EMAIL));
        mAdress.setText(mContact.get(Contacto.Field.DIRECCION));
        mColor.setBackgroundColor(Integer.parseInt(mContact.get(Contacto.Field.COLOR)));

    }

    public void SaveContact(){

        mName = (TextView) findViewById(R.id.ADD_name);
        String nombre = String.valueOf(mName.getText());
        if (!validarTexto(nombre,tilNombre,30)) {
            tilNombre.setError("Formato de Nombre incorrecto");
            return;
        }

        mApellido1 = (TextView) findViewById(R.id.ADD_apellido1);
        if (!validarTexto(String.valueOf(mApellido1.getText()),tilApellido1,15)) {
            tilApellido1.setError("Formato de Apellido 1 incorrecto");
            return;
        }

        mApellido2 = (TextView) findViewById(R.id.ADD_apellido2);
        if (!String.valueOf(mApellido2.getText()).equals("")) {
            if (!validarTexto(String.valueOf(mApellido2.getText()), tilApellido2,15)) {
                tilApellido2.setError("Formato de Apellido 2 incorrecto");
                return;
            }
        }

        mAdress = (TextView) findViewById(R.id.ADD_address);
        if (!String.valueOf(mAdress.getText()).equals("")) {
            if (!validarDireccion(String.valueOf(mAdress.getText()))) {
                return;
            }
        }

        mPhone = (TextView) findViewById(R.id.ADD_phone);
        if (!validarTelefono(String.valueOf(mPhone.getText()))) {
            return;
        }

        mEmail = (TextView) findViewById(R.id.ADD_email);
        if (!String.valueOf(mEmail.getText()).equals("")) {
            if (!validarEmail(String.valueOf(mEmail.getText()))) {
            return;
            }
        }

        String color = Contacto.getColor(getApplicationContext(),nombre.toUpperCase());

        if (mContact == null)
            mContact = new Contacto(0,nombre,String.valueOf(mApellido1.getText()),String.valueOf(mApellido2.getText()),
                    String.valueOf(mAdress.getText()),String.valueOf(mEmail.getText()),String.valueOf(mPhone.getText()),"",color);
        else {
            mContact.nombre = nombre;
            mContact.apellido1 = String.valueOf(mApellido1.getText());
            mContact.apellido2 = String.valueOf(mApellido2.getText());
            mContact.telefono = String.valueOf(mPhone.getText());
            mContact.email = String.valueOf(mEmail.getText());
            mContact.direccion = String.valueOf(mAdress.getText());
            mContact.color = color;
        }

        try {

            ContentResolver cr = getContentResolver();

            if (Modificar) {
                Uri uriUpdate = ContentUris.withAppendedId(URI_CONTENT_PROVIDER,mContact.id);

                int res = cr.update(uriUpdate, mContact.toContentValues(), null, null);

                Log.i("UPDATE", "Num Rows:" + res);
                if (res == 1) {
                    Toast.makeText(getApplicationContext(), "Contacto actualizado", Toast.LENGTH_LONG).show();
                    devolverResultado(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Error al modificar el contacto", Toast.LENGTH_LONG).show();
                    devolverResultado(false);
                }
            } else {
                //Hacemos la inserción
                Uri uri = cr.insert(URI_CONTENT_PROVIDER, mContact.toContentValues());
                Toast.makeText(getApplicationContext(), "Contacto añadido", Toast.LENGTH_LONG).show();
                mContact.id = Integer.parseInt(uri.getLastPathSegment());
                Contacto.listaContactos.add(mContact);
                Collections.sort(Contacto.listaContactos, new ContactsCompare());
                devolverResultado(true);
            }
        }
        catch (Exception ex)
        {
            Log.e("BASE DE DATOS", "Error al insertar/actualizar en la base de datos:" + ex.getMessage());
            Toast.makeText(getApplicationContext(), "Error al insertar/modificar el contacto", Toast.LENGTH_LONG).show();
            devolverResultado(false);
        }
    }

    private boolean validarTexto(String texto, TextInputLayout til, int longitud){
        if (texto.trim().length()==0) {
            til.setErrorEnabled(true);
            return false;
        }
        Pattern patron = Pattern.compile("^[a-zA-Zá-úÁ-Ú ]+$");
        if (!patron.matcher(texto).matches() || texto.length() > longitud) {
            til.setErrorEnabled(true);
            return false;
        } else {
            til.setError(null);
            til.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validarDireccion(String direccion){
        if (direccion.length() > 50) {
            tilAddress.setError("Formato de Dirección incorrecto");
            tilAddress.setErrorEnabled(true);
            return false;
        } else {
            tilAddress.setError(null);
            tilAddress.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validarTelefono(String telefono){
        telefono = telefono.replaceAll("\\s","");
        if (!Patterns.PHONE.matcher(telefono).matches() || telefono.length() < 9) {
            tilPhone.setError("Formato de Teléfono incorrecto");
            tilPhone.setErrorEnabled(true);
            return false;
        } else {
            tilPhone.setError(null);
            tilPhone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validarEmail(String correo){
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilEmail.setError("Formato de Email incorrecto");
            tilEmail.setErrorEnabled(true);
            return false;
        } else {
            tilEmail.setError(null);
            tilEmail.setErrorEnabled(false);
            return true;
        }
    }

    private String formateaTelefono() {
        String cadPhone = String.valueOf(mPhone.getText()).replaceAll("\\s","");
        if (cadPhone.length()==0) {
            return "";
        }
        String resultado = "";
        if (cadPhone.substring(0,2).equals("00"))
            cadPhone = "+" + cadPhone.substring(2);
        do {
            resultado = resultado + cadPhone.substring(0,3) + " ";
            cadPhone = cadPhone.substring(3);
        } while (cadPhone.length()>3);
        return((resultado+cadPhone).trim());
    }

    public void Cancel(){
        devolverResultado(false);
    }

    public void devolverResultado (boolean correcto)
    {
        Intent i= new Intent();
        if (correcto) {
            i.putExtra("ID", Integer.valueOf(mContact.get(Contacto.Field.ID)));
            i.putExtra("Modificar",Modificar);

            setResult(RESULT_OK, i);
        }
        else
            setResult(RESULT_CANCELED,i);
        finish();
    }
}
