package com.uaa.basesdedatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText txtId;
    private EditText txtNombre;
    private TextView txtResultado;
    private SQLiteDatabase db;
    private Button[] botones;
    private ContentValues nuevoRegistro;
    private ContentValues valores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.inicializaElementos();
        this.abrirBase();
        this.inicializarListeners();
    }

    private void inicializaElementos(){
        txtId = (EditText) findViewById(R.id.txtReg);
        txtNombre = (EditText) findViewById(R.id.txtVal);
        txtResultado = (EditText) findViewById(R.id.txtResultado);

        botones = new Button[]{
                (Button) findViewById(R.id.btnInsertar),
                (Button) findViewById(R.id.btnActualizar),
                (Button) findViewById(R.id.btnEliminar),
                (Button) findViewById(R.id.btnConsultar)
        };
    }

    private void abrirBase() {
        //abrimos la base de datos 'usuarios.db' en modo escritura
        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "usuarios.db", null, 1);

        db = usdbh.getWritableDatabase();
    }

    private void inicializarListeners(){
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnInsertar:
                        insertarBD();
                        break;
                    case R.id.btnActualizar:
                        actualizarBD();
                        break;
                    case R.id.btnEliminar:
                        eliminarBD();
                        break;
                    case R.id.btnConsultar:
                        consultarBD();
                        break;
                }
            }
        };

        for(int i=0; i<botones.length ; i++){
            botones[i].setOnClickListener(l);
        }
    }

    private void insertarBD(){
        String id = txtId.getText().toString();
        String nom = txtNombre.getText().toString();

        //alternativa 1: método sqlExec()
        String sql = "INSERT INTO Usuarios (id, nombre) Values('"+id+"','"+nom+"');";
        db.execSQL(sql);

        //alternativa 2: metodo insert()
        /*nuevoRegistro = null;
        nuevoRegistro = new ContentValues();
        nuevoRegistro.put("id", id);
        nuevoRegistro.put("nombre",nom);
        db.insert("Usuarios",null,nuevoRegistro);
         */
    }

    private void actualizarBD(){
        //recuperamos los valores de los campos de texto
        String id = txtId.getText().toString();
        String nom = txtNombre.getText().toString();

        //alternativa 1: metodo sqlExec()
        String sql = "UPDATE Usuarios SET nombre='"+nom+"' WHERE id="+id;
        db.execSQL(sql);

        //alternativa 2: metodo update
        /*valores = null;
        valores = new ContentValues();
        valores.put("nombre", nom);
        db.update("Usuarios", valores,"id=?",new String{id});
        */


    }

    private void eliminarBD(){
        //recuper...
        String id = txtId.getText().toString();
        String nom = txtNombre.getText().toString();

        //alternativa 1: metodo sqlExec()
        String sql = "DELETE FROM Usuarios WHERE id="+id;
        db.execSQL(sql);

        //alternativa 2: metodo update
        /*valores = null;
        db.delete("Usuarios", valores,"id=?",new String{id});
        */

    }
	
	private void consultarBD(){
		Cursor cursor1 = db.rawQuery("select id, nombre from Usuarios", null);

        //alternativa 2
        String[] campos = new String[]{"id", "nombre"};
        Cursor c = db.query("Usuarios", campos, null,null,null,null,null);

        //Recorremos los resultados para mostrarlos
        txtResultado.setText("");
        if(c.moveToFirst()){
            do{
                int idIndex = c.getColumnIndex("id");
                int nombreIndex = c.getColumnIndex("nombre");
                String cod = c.getString(idIndex);
                String nom = c.getString(nombreIndex);
                txtResultado.append(" "+cod+"-"+nom+"\n");
            }while (c.moveToNext());
        }
	}

}
