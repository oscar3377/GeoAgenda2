package es.upm.etsisi.geoagenda2;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static es.upm.etsisi.geoagenda2.Contacto.listaContactos;

/**
 * Created by Oscar on 05/11/2016.
 */

public class DataManager extends RecyclerView.Adapter<DataManager.RecyclerViewHolder> {
    public static int adapterPosition;
    public static ActionBar myToolbar;
    private static View vistaSel;
    private static boolean itemSelec = false;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mApellido1, mApellido2, mPhone;
        View mCircle;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.CONTACT_name);
            mApellido1 = (TextView) itemView.findViewById(R.id.CONTACT_apellido1);
            mApellido2 = (TextView) itemView.findViewById(R.id.CONTACT_apellido2);
            mPhone = (TextView) itemView.findViewById(R.id.CONTACT_phone);
            mCircle = itemView.findViewById(R.id.CONTACT_circle);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterPosition = getAdapterPosition();
                    vistaSel = v;
                    v.setBackgroundColor(Color.parseColor("#FFFFC107"));
                    myToolbar.show();
                    itemSelec = true;
                    return false;
                }
            });
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int i) {
        // get the single element from the main array
        final Contacto contact = getItem(i);
        // Set the values
        String sNombre = contact.get(Contacto.Field.NOMBRE);
        viewHolder.mName.setText(sNombre);
        viewHolder.mApellido1.setText(contact.get(Contacto.Field.APELLIDO1));
        viewHolder.mApellido2.setText(contact.get(Contacto.Field.APELLIDO2));
        viewHolder.mPhone.setText(contact.get(Contacto.Field.TELEFONO));
        // Set the color of the shape
        GradientDrawable bgShape = (GradientDrawable) viewHolder.mCircle.getBackground();
        bgShape.setColor(Integer.parseInt(contact.get(Contacto.Field.COLOR)));
    }

    @Override
    public int getItemCount() {
        if(listaContactos == null){
            Log.d("Items", "#:0 zero");
            return 0;}
        Log.d("Items", "#:" + listaContactos.size());
        return listaContactos.size();
    }

    public Contacto getItem(int i) {
        return listaContactos.get(i);
    }

    public boolean itemSelec() {
        return itemSelec;
    }

    public void restauraItem() {
        vistaSel.setBackgroundColor(Color.TRANSPARENT);
        itemSelec = false;
        myToolbar.hide();
    }
}
