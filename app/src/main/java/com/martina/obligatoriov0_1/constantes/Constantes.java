package com.martina.obligatoriov0_1.constantes;

import android.app.PendingIntent;

public class Constantes {
    public static final String URL_REGISTRO = "https://bios-fletes-api.herokuapp.com/api/student/53729203/users/register";
    public static final String URL_LOGIN = "https://bios-fletes-api.herokuapp.com/api/student/53729203/users/login";
    public static final String URL_GET_TRANSPORTATIONS = "https://bios-fletes-api.herokuapp.com/api/student/53729203/transportations";
    public static final String ERROR_JSON = "ERROR_JSON";
    public static final String EXITO = "EXITO";
    public static final String SHARED_PREFERENCES_NAME = "com.martina.obligatoriov0_1.sharedpreferences";
    public static final String MANTENER_SESION_INICIADA = "com.martina.obligatoriov0_1.sharedpreferences.MANTENER_SESION_INICIADA";
    public static final String EMAIL_SESION_INICIADA = "com.martina.obligatoriov0_1.sharedpreferences.ID_SESION_INICIADA";
    public static final String INFORMACION = "INFORMACION";
    public static final String BROADCAST_SESION_INICIADA_CON_EXITO = "com.martina.obligatoriov0_1.BROADCAST_SESION_INICIADA_CON_EXITO";
    public static final String EMAIL_EXTRA_INTENT = "EMAIL_EXTRA_INTENT";
    public static final String EXTRA_INTENT_SIMPLE_TRANSPORTATION_LIST = "EXTRA_INTENT_SIMPLE_TRANSPORTATION_LIST";
    public static final String FILTRO_INTENT_SIMPLE_TRANSPORTATION_LIST_BROADCAST = "com.martina.obligatoriov0_1.FILTRO_INTENT_SIMPLE_TRANSPORTATION_LIST_BROADCAST";
    public static final String ID_SELECCIONADA ="ID_SELECCIONADA";
    public static final String FULL_TRANSPORTATION_WITH_DETAILS = "com.martina.obligatoriov0_1.FULL_TRANSPORTATION_WITH_DETAILS";
    public static final String EXTRA_INTENT_FULL_TRANSPORTATION_SERIALIZED = "com.martina.obligatoriov0_1.EXTRA_INTENT_FULL_TRANSPORTATION_SERIALIZED";
    public static final String ERROR_OTHERS = "ERROR_OTHERS";
    public static final String BROADCAST_RETRY_HUB = "com.martina.obligatoriov0_1.BROADCAST_RETRY_HUB";
    public static final String ID_LIST_EXTRA_INTENT = "com.martina.obligatoriov0_1.ID_LIST_EXTRA_INTENT ";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT = "com.martina.obligatoriov0_1.TRANSPORTATION_DETALLADA_EXTRA_INTENT";
    public static final String FILTRO_INTENT_TRANSPORTATION_DETALLADA = "com.martina.obligatoriov0_1.FILTRO_INTENT_TRANSPORTATION_DETALLADA";
    public static final String EXTRA_INTENT_TRANSPORTATION_DETALLADA = "com.martina.obligatoriov0_1.EXTRA_INTENT_TRANSPORTATION_DETALLADA";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_DD = "com.martina.obligatoriov0_1.destino_direccion";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID = "com.martina.obligatoriov0_1.id";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLAT = "com.martina.obligatoriov0_1.destino_lat";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLONG = "com.martina.obligatoriov0_1.destino_long;";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_STATUS = "com.martina.obligatoriov0_1.ESTADO";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_DATE = "com.martina.obligatoriov0_1.FECHA";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_OD = "com.martina.obligatoriov0_1.ORIGEN_DIRECCION";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLAT = "com.martina.obligatoriov0_1.ORIGEN_LAT";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLONG = "com.martina.obligatoriov0_1.ORIGEN_LONG";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_VC = "com.martina.obligatoriov0_1.VEHICULO_CHOFER";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAR = "com.martina.obligatoriov0_1.VEHICULO_MARCA";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAT = "com.martina.obligatoriov0_1.VECHICULO_MATRICULA";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMODEL = "com.martina.obligatoriov0_1.VEHICULO_MODELO";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_RDATE = "com.martina.obligatoriov0_1.RECEPCION_FECHA";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_RLAT = "com.martina.obligatoriov0_1.RECEPCION_LATITUD";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_RLONG ="com.martina.obligatoriov0_1.RECEPCION_LONGITUD";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_RNAME = "com.martina.obligatoriov0_1.RECEPCION_NOMBRE_RECEPTOR";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_ROBS = "com.martina.obligatoriov0_1.RECEPCION_OBSERVACIONES";
    public static final String ORIGEN_LAT_ARRAY_EXTRA_INTENT = "com.martina.obligatoriov0_1.ORIGEN_LAT_ARRAY_EXTRA_INTENT";
    public static final String ORIGEN_LONG_ARRAY_EXTRA_INTENT = "com.martina.obligatoriov0_1.ORIGEN_LONG_ARRAY_EXTRA_INTENT";
    public static final String ID_ARRAY_EXTRA_INTENT = "com.martina.obligatoriov0_1.ID_ARRAY_EXTRA_INTENT";
    public static final String ESTADO_ARRAY_EXTRA_INTENT = "com.martina.obligatoriov0_1.ESTADO_ARRAY_EXTRA_INTENT";
    public static final String CONEXION = "com.martina.obligatoriov0_1.conexion";
    public static final String TRANSPORTATION_UPDATE_CURRENT_STATUS = "com.martina.obligatoriov0_1.TRANSPORTATION_UPDATE_CURRENT_STATUS";
    public static final String TRANSPORTATION_UPDATE_CURRENT_ID = "com.martina.obligatoriov0_1.TRANSPORTATION_UPDATE_CURRENT_ID";
    public static final String ESTADO_1 = "pendiente";
    public static final String ESTADO_2 = "iniciado";
    public static final String ESTADO_3 = "cargando";
    public static final String ESTADO_4 = "viajando";
    public static final String ESTADO_5 = "descargando";
    public static final String ESTADO_6 = "finalizado";
    public static final String URL_UPDATE = "https://bios-fletes-api.herokuapp.com/api/student/53729203/transportations/";
    public static final String URL_LOCATION = "https://bios-fletes-api.herokuapp.com/api/student/53729203/transportations/";
    public static final String INTENT_LOCATION_ID_TRANSPORTATION = "com.martina.obligatoriov0_1.INTENT_LOCATION_ID_TRANSPORTATION";
    public static final String TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID_NEW = "com.martina.obligatoriov0_1.id_new";;
    public static final String JSON_BODY = "com.martina.obligatoriov0_1.JSON_BODY";
    public static final String JSON_BODY_EXTRA = "com.martina.obligatoriov0_1.JSON_BODY_EXTRA";
    public static final String FILTRO_INTENT_LOCATION = "FILTRO INTENT LOCATION com.martina.obligatorio";
    public static final String DETALLE_LONG_DESTINO = "com.martina.obligatoriov0_1.DETALLE_LONG_DESTINO";
    public static final String DETALLE_LONG_ORIGEN = "com.martina.obligatoriov0_1.DETALLE_LONG_ORIGEN";
    public static final String DETALLE_LAT_DESTINO = "com.martina.obligatoriov0_1.DETALLE_LAT_DESTINO";
    public static final String DETALLE_LAT_ORIGEN = "com.martina.obligatoriov0_1.DETALLE_LAT_ORIGEN";
    public static final String DETALLE_MAPA_ID_EXTRA = "com.martina.obligatoriov0_1.DETALLE_MAPA_ID_EXTRA";
    public static final String BUNDLE_EXTRA_LOCATION_INTENT = "BUNDLE_EXTRA_LOCATION_INTENT";
    public static final String JSON_BODY_EXTRA_EXTRA = "EXTRA JSON BODY EXTRA INTENT";
    public static final String DETALLE_MAPA_OLAT = "com.martina.obligatoriov0_1.OLAT";
    public static final String DETALLE_MAPA_OLONG = "com.martina.obligatoriov0_1.OLONG";
    public static final String DETALLE_MAPA_DLAT = "com.martina.obligatoriov0_1.DLAT";
    public static final String DETALLE_MAPA_DLONG = "com.martina.obligatoriov0_1.DLONG";
    public static final String CANAL = "Pedidos";
    public static final int NOTIFICATION_ID = 1891;
    public static final String TAG_ALARMA = "TAG ALARMA DETALLE";
    public static final String INICIO_BROADCAST_ALARMA ="com.martina.obligatoriov0_1.ALARMA";
    public static final int RQ = 117741;
    public static final String  NOTIFICATION_ID_PEDIDO = "com.martina.obligatoriov0_1.not_id_pedido";


    public static int CURRENT_DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "mydb";
}
