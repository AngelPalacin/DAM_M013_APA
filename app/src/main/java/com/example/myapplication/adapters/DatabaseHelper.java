package com.example.myapplication.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database";
    private static final int DATABASE_VERSION = 1;

    // Tabla de usuarios
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Sentencia SQL para crear la tabla de usuarios
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL);";

    // Columnas y sentencias SQL para la tabla del banco
    public static final String TABLE_NAME = "datos_bancarios";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_NUMERO_CUENTA = "numero_cuenta";
    public static final String COLUMN_DNI = "dni";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String COLUMN_DIRECCION = "direccion";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_CORREO = "correo";

    private static final String TABLE_CREATE =
            "create table " + TABLE_NAME + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NOMBRE + " text not null, " +
                    COLUMN_NUMERO_CUENTA + " text not null, " +
                    COLUMN_DNI + " text, " +
                    COLUMN_APELLIDOS + " text, " +
                    COLUMN_DIRECCION + " text, " +
                    COLUMN_TELEFONO + " text, " +
                    COLUMN_CORREO + " text);";

    // Nueva tabla para el calendario y actividades del gimnasio
    public static final String CALENDAR_TABLE_NAME = "calendario";
    public static final String COLUMN_CALENDAR_ID = "_id";
    public static final String COLUMN_FECHA_HORA = "fecha_hora";
    public static final String COLUMN_ACTIVIDAD = "actividad";

    private static final String CALENDAR_TABLE_CREATE =
            "create table " + CALENDAR_TABLE_NAME + "(" +
                    COLUMN_CALENDAR_ID + " integer primary key autoincrement, " +
                    COLUMN_FECHA_HORA + " text not null, " +
                    COLUMN_ACTIVIDAD + " text not null);";

    // Nueva tabla para los ejercicios
    public static final String EXERCISES_TABLE_NAME = "exercises";
    public static final String COLUMN_EXERCISE_ID = "_id";
    public static final String COLUMN_EXERCISE = "exercise";
    public String getUserPassword(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_PASSWORD};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        String storedPassword = null;

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            if (columnIndex != -1) {
                storedPassword = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close();
        return storedPassword;
    }
    private static final String EXERCISES_TABLE_CREATE =
            "create table " + EXERCISES_TABLE_NAME + "(" +
                    COLUMN_EXERCISE_ID + " integer primary key autoincrement, " +
                    COLUMN_EXERCISE + " text not null);";

    // Nueva tabla para el registro de salud
    public static final String SALUD_TABLE_NAME = "registro_salud";
    public static final String COLUMN_SALUD_ID = "_id";
    public static final String COLUMN_IMC = "imc";
    public static final String COLUMN_FECHA = "fecha";

    private static final String SALUD_TABLE_CREATE =
            "create table " + SALUD_TABLE_NAME + "(" +
                    COLUMN_SALUD_ID + " integer primary key autoincrement, " +
                    COLUMN_IMC + " real not null, " +
                    COLUMN_FECHA + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_USERS);
        database.execSQL(TABLE_CREATE);
        database.execSQL(CALENDAR_TABLE_CREATE);
        database.execSQL(EXERCISES_TABLE_CREATE);
        database.execSQL(SALUD_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(CALENDAR_TABLE_CREATE);
            db.execSQL(EXERCISES_TABLE_CREATE);
            db.execSQL(SALUD_TABLE_CREATE);
        }
    }

    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long userId = db.insert(TABLE_USERS, null, values);
        db.close();
        return userId;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public long insertDatosBancarios(String nombre, String numeroCuenta, String dni, String apellidos, String direccion, String telefono, String correo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_NUMERO_CUENTA, numeroCuenta);
        values.put(COLUMN_DNI, dni);
        values.put(COLUMN_APELLIDOS, apellidos);
        values.put(COLUMN_DIRECCION, direccion);
        values.put(COLUMN_TELEFONO, telefono);
        values.put(COLUMN_CORREO, correo);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }
}
