package com.example.pruebapaises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask, AdapterView.OnItemClickListener{

    private ListView lstOpciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LLamado al WebService
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("http://www.geognos.com/api/en/countries/info/all.json", datos, MainActivity.this, MainActivity.this);
        ws.execute("");
        lstOpciones = (ListView)findViewById(R.id.lblprincipal);
        lstOpciones.setOnItemClickListener(this);
        getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void processFinish(String result) throws JSONException {

        //Lista de paises a parcear
        ArrayList<Paises> paises = new ArrayList<>();
        JSONObject ob = new JSONObject(result);
        JSONObject resultadosp = ob.getJSONObject("Results");
        Iterator<?> ite = resultadosp.keys();
        while (ite.hasNext())
        {
            String a =(String)ite.next();
            JSONObject jpais = resultadosp.getJSONObject(a);
            Paises banderita = new Paises();
            banderita.setNombre(jpais.getString("Name"));
            JSONObject objetopais = jpais.getJSONObject("CountryCodes");
            banderita.setImagen("http://www.geognos.com/api/en/countries/flag/"+objetopais.getString("iso2")+".png");
            paises.add(banderita);
        }
        AdaptadorPaises adaptadorpais = new AdaptadorPaises(this, paises);
        lstOpciones.setAdapter(adaptadorpais);
    }
    public void getPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1)
        {
            Toast.makeText(this.getApplicationContext(),"OK", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this.getApplicationContext(),((Paises)parent.getItemAtPosition(position)).getImagen(),Toast.LENGTH_LONG).show();
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(((Paises)parent.getItemAtPosition(position)).getImagen()));
        request.setDescription("PDF de los paises");
        request.setTitle("Pdf Imagen pais");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "filedownload.pdf");
        DownloadManager manager = (DownloadManager) this.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            manager.enqueue(request);
        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
