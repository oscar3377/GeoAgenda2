package es.upm.etsisi.geoagenda2;

/**
 * Created by Oscar on 06/11/2016.
 */


import android.provider.BaseColumns;

public class ContactosSchema {
    public static abstract class ContactosEntry implements BaseColumns {
        public static final String TABLE_NAME =  "contacto";

        public static final String NOMBRE = "nombre";
        public static final String APELLIDO1 = "apellido1";
        public static final String APELLIDO2 = "apellido2";
        public static final String DIRECCION = "direccion";
        public static final String EMAIL = "email";
        public static final String TELEFONO = "telefono";
        public static final String AVATAR_URI = "avatarUri";
        public static final String COLOR = "color";
    }
}