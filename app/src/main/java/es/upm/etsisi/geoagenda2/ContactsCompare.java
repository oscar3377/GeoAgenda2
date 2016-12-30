package es.upm.etsisi.geoagenda2;

import java.util.Comparator;

/**
 * Created by Oscar on 12/11/2016.
 */

public class ContactsCompare implements Comparator<Contacto> {
    @Override
    public int compare(Contacto o1, Contacto o2) {
        return o1.nombre.compareToIgnoreCase(o2.nombre);
    }
}
