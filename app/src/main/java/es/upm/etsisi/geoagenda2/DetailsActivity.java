package es.upm.etsisi.geoagenda2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;

/**
 * Created by Oscar on 06/11/2016.
 */

public class DetailsActivity extends AppCompatActivity {
    // Before the onCreate
    public Contacto mContact;

    private TextView mName;
    private TextView mApellido1;
    private TextView mApellido2;
    private TextView mPhone;
    private TextView mEmail;
    private TextView mAdress;
    private View mColor;

    private boolean Modificar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        // In the onCreate, after the setContentView method
        mContact = Contacto.getItem(getIntent().getIntExtra("ID",0));

        ActionBar myToolbarDet = getSupportActionBar();
        myToolbarDet.setTitle("GeoAgenda");

        CargaDatosContacto();

        FloatingActionButton bEdit = (FloatingActionButton)findViewById(R.id.edit);
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the corky is clicked
                ModifyContact();
            }
        });

        FloatingActionButton bDelete = (FloatingActionButton)findViewById(R.id.del);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the corky is clicked
                ConfirmDeleteContact();
            }
        });

    }

        private void CargaDatosContacto(){

            mName = (TextView) findViewById(R.id.DETAILS_name);
            mName.setText(mContact.get(Contacto.Field.NOMBRE));

            mApellido1 = (TextView) findViewById(R.id.DETAILS_apellido1);
            mApellido1.setText(mContact.get(Contacto.Field.APELLIDO1));

            mApellido2 = (TextView) findViewById(R.id.DETAILS_apellido2);
            mApellido2.setText(mContact.get(Contacto.Field.APELLIDO2));

            mPhone = (TextView) findViewById(R.id.DETAILS_phone);
            mPhone.setText(mContact.get(Contacto.Field.TELEFONO));

            mEmail = (TextView) findViewById(R.id.DETAILS_email);
            mEmail.setText(mContact.get(Contacto.Field.EMAIL));

            mAdress = (TextView) findViewById(R.id.DETAILS_address);
            mAdress.setText(mContact.get(Contacto.Field.DIRECCION));

            mColor = findViewById(R.id.DETAILS_circle);
            mColor.setBackgroundColor(Integer.parseInt(mContact.get(Contacto.Field.COLOR)));

        }

        private void ModifyContact() {
            Intent i = new Intent(this,AddActivity.class);
            i.putExtra("Modificar",true);
            i.putExtra("ID", Integer.valueOf(mContact.get(Contacto.Field.ID)));
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    // the context of the activity
                    DetailsActivity.this,

                    // For each shared element, add to this method a new Pair item,
                    // which contains the reference of the view we are transitioning *from*,
                    // and the value of the transitionName attribute
                    new Pair<View, String>(findViewById(R.id.DETAILS_circle),
                            getString(R.string.transition_name_circle)),
                    new Pair<View, String>(findViewById(R.id.DETAILS_name),
                            getString(R.string.transition_name_name)),
                    new Pair<View, String>(findViewById(R.id.DETAILS_apellido1),
                            getString(R.string.transition_name_apellido1)),
                    new Pair<View, String>(findViewById(R.id.DETAILS_apellido2),
                            getString(R.string.transition_name_apellido2)),
                    new Pair<View, String>(findViewById(R.id.DETAILS_phone),
                            getString(R.string.transition_name_phone))
            );

            ActivityCompat.startActivityForResult(DetailsActivity.this, i, 12, options.toBundle());

        }

        private void ConfirmDeleteContact() {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Confirme borrado");
            dialogo1.setMessage("¿Desea eliminar este contacto?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                   DeleteContact();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    return;
                }
            });
            dialogo1.show();
        }

        private void DeleteContact() {
            boolean res = DeleteContact.Delete(getApplicationContext(),Integer.parseInt(mContact.get(Contacto.Field.ID)));
            if (res == true) {
                Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_LONG).show();

                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "No se ha podido eliminar el contacto", Toast.LENGTH_LONG).show();
            }
        }

        protected void onActivityResult(int resultado, int codigo,Intent data) {

            if (resultado == 12 && codigo == RESULT_OK) {

                Modificar = data.getBooleanExtra("Modificar",true);

                if (Modificar) {

                    mContact = Contacto.getItem(data.getIntExtra("ID",0));

                    Collections.sort(Contacto.listaContactos, new ContactsCompare());

                    CargaDatosContacto();

                }

            }
        }

}
