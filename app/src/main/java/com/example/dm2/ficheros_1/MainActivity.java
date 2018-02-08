package com.example.dm2.ficheros_1;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {

    private EditText txtEscrito;

    private TextView txtEnseñar;

    private Button btnañadirint;
    private Button btnañadirExt;
    private Button btnleerint;
    private Button btnleerExt;
    private Button btnleerRecurso;
    private Button btnborrarInt;
    private Button btnborrarExt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEscrito= (EditText) findViewById(R.id.txt);

        txtEnseñar=(TextView) findViewById(R.id.txtfichero);

        btnañadirint=(Button) findViewById(R.id.btnanadirInt);
        btnañadirExt=(Button) findViewById(R.id.btnanadirExt);
        btnleerint=(Button) findViewById(R.id.btnleerInt);
        btnleerExt=(Button) findViewById(R.id.btnleerExt);
        btnleerRecurso=(Button) findViewById(R.id.btnleerRecurso);
        btnborrarInt=(Button) findViewById(R.id.btnanadirInt);
        btnborrarExt=(Button) findViewById(R.id.btnborrarExt);

    }

    public void escribirInterno(View view){

            try {
                OutputStreamWriter osw=new OutputStreamWriter(openFileOutput("interno.txt", Context.MODE_APPEND));

                osw.write(txtEscrito.getText().toString());

                osw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void EscribirExterno(View  view){

        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));

            osw.write(txtEscrito.getText().toString());
            //Toast.makeText(getApplicationContext(),"se ha escrito "+txtEscrito,Toast.LENGTH_SHORT).show();
            osw.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void leerSD(View view){
        boolean sDdisponible= false;
        boolean sDaccesoEscritura = false;

        //comprobamos el estado de la memoria externa
        String estado = Environment.getExternalStorageState();
        Log.i("MEMORIA",estado);

        if (estado.equals(Environment.MEDIA_MOUNTED)){
            sDdisponible= true;
            sDaccesoEscritura= true;
        }
        else {
            if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
                sDdisponible= true;
                sDaccesoEscritura= false;
            }
            else {
                sDdisponible= false;
                sDaccesoEscritura= false;
            }
        }

        if (sDdisponible){
            Toast.makeText(getApplicationContext(),"tarjeta SD disponible",Toast.LENGTH_SHORT).show();
        }

        if (sDaccesoEscritura){
            Toast.makeText(getApplicationContext(),"la tarjeta SD es escribible",Toast.LENGTH_SHORT).show();
        }

        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");

            BufferedReader bfr= new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            String texto="";
            String linea= bfr.readLine();
            while(linea!=null){
                texto= linea+"\n"+texto;
                linea=bfr.readLine();
            }
            bfr.close();
            txtEnseñar.setText(texto);
            //Toast.makeText(getApplicationContext(),texto,Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void leerInterno (View view){

        try{
            BufferedReader bfr= new BufferedReader(new InputStreamReader(openFileInput("interno.txt")));

            String texto="";
            String linea= bfr.readLine();
            while(linea!=null){
                texto= linea+"\n"+texto;
                linea=bfr.readLine();
            }
            bfr.close();
            txtEnseñar.setText(texto);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void leerRecurso(View view) {

        try {
            InputStream fraw = getResources().openRawResource(R.raw.pruba_raw);
            BufferedReader brin = new BufferedReader(new InputStreamReader(fraw));

            String texto = "";
            String linea = brin.readLine();
            while (linea != null) {
                texto = linea + "\n" + texto;
                linea = brin.readLine();
            }
            brin.close();
            fraw.close();

            //why no cierra el bufferedreader
            txtEnseñar.setText(txtEnseñar.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrarInterno(View view){

        /*
       File[] ficheros= new File("/data/data/com.example.dm2.ficheros_1/files").listFiles(new FileFilter() {
           public boolean accept(File archivo) {
               if (archivo.isFile())
               return archivo.getName().endsWith(".txt");
               return false;
           }
       });

        for (File fichero : ficheros) {

            fichero.delete();

        }*/

        File f = new File("/data/data/com.example.dm2.ficheros_1/files/interno.txt");

        f.delete();

    }

    public void borrarSD(View view){

        File ruta_sd = Environment.getExternalStorageDirectory();
        File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");

        f.delete();

    }

}
