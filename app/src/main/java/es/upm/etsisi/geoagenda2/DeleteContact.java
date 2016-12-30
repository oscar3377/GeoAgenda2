package es.upm.etsisi.geoagenda2;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;

import static es.upm.etsisi.geoagenda2.Contacto.listaContactos;

/**
 * Created by Oscar on 12/11/2016.
 */

public class DeleteContact {

    private static final Uri URI_CONTENT_PROVIDER = Uri.parse("content://es.upm.etsisi.geoagendacontentprovider/contacto");

    public static boolean Delete(Context context, int ID) {
        ContentResolver cr = context.getContentResolver();
        Uri uriDelete = ContentUris.withAppendedId(URI_CONTENT_PROVIDER,ID);
        int res = cr.delete(uriDelete, null, null);
        if (res==1) {
            int index = Contacto.getIndex(ID);
            listaContactos.remove(index);
            MainActivity.dm.notifyItemRemoved(index);
            return true;
        }
        else
            return false;
    }
}
