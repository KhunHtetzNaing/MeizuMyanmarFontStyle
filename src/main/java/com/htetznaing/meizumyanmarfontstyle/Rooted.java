package com.htetznaing.meizumyanmarfontstyle;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

public class Rooted extends AppCompatActivity implements View.OnClickListener {
    Button btnInstall,btnAll;
    ImageButton btnRestore;
    String font,name,path;
    Fucker my;
    ProgressDialog progressDialog;
    AdRequest adRequest;
    AdView banner;
    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooted);
        getSupportActionBar().setElevation(0);

        font = getIntent().getStringExtra("font");
        name = getIntent().getStringExtra("name");
        path = Environment.getExternalStorageDirectory()+"/Android/data/"+getPackageName()+"/";
        setTitle(name);

        btnInstall = findViewById(R.id.btnInstall);
        btnAll = findViewById(R.id.btnAll);
        btnRestore = findViewById(R.id.btnRestore);
        btnInstall.setOnClickListener(this);
        btnAll.setOnClickListener(this);
        btnRestore.setOnClickListener(this);
        my = new Fucker();
        creDir();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("ATTENTION!");
        progressDialog.setMessage("WORKING...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        adRequest = new AdRequest.Builder().build();
        banner = findViewById(R.id.adView);
        banner.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2780984156359274/4418826003");
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                loadAD();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                loadAD();
            }

            @Override
            public void onAdOpened() {
                loadAD();
            }
        });
    }

    public void loadAD(){
        if (!interstitialAd.isLoaded()){
            interstitialAd.loadAd(adRequest);
        }
    }

    public void showAD(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }else{
            interstitialAd.loadAd(adRequest);
        }
    }

    public boolean creDir(){
        boolean b =false;
        File file = new File(path);
        if (file.exists()){
            b=true;
        }else{
            if (file.mkdirs()){
                b=true;
            }
        }
        return b;
    }

    @Override
    public void onClick(View view) {
        showAD();
        switch (view.getId()){
            case R.id.btnInstall:
                install(font);
                break;
            case R.id.btnAll:
                installAll(font);
                break;
            case R.id.btnRestore:
                break;
        }
    }

    public void install(String font){
        try {
            my.GetRoot ();
            if (my.HaveRoot==true){
                if (creDir()==true) {
                    if (new File(path+font).exists()==true){
                        goWork(font);
                    }else{
                        if (my.assets2SD(this,"zzz.cache",path,"z.zip")){
                            if (my.unZip(path+"z.zip",path)){
                                new File(path+"z.zip").delete();
                                goWork(font);
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Please Root First :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void goWork(String font){
        new install().execute(font);
    }

    public void installAll(String font){
            try {
                my.GetRoot ();
                if (my.HaveRoot==true){
                    if (creDir()==true) {
                        if (new File(path+font).exists()==true){
                            goWorkAll(font);
                        }else{
                            if (my.assets2SD(this,"zzz.cache",path,"z.zip")){
                                if (my.unZip(path+"z.zip",path)){
                                    new File(path+"z.zip").delete();
                                    goWorkAll(font);
                                }
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(this, "Please Root First :(", Toast.LENGTH_SHORT).show();
            }
    }

    public void goWorkAll(String font){
        new installAll().execute(font);
    }

    class install extends AsyncTask<String,Integer,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String font = strings[0];
            boolean b = false;
            if (all(font)==true){
                b=true;
            }
            return b;
        }

        public boolean all(String font){
            Log.d("FontPath",path+font);
            my.RootCmd("mount -o remount ,rw /system", "", null, null, false);
            my.RootCmd("mount -o rw,remount -t auto /system", "", null, null, false);
            my.RootCmd("mount -o rw,remount /system", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmar-Bold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmar-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmarUI-Bold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmarUI-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/DroidSansMyanmar.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SamsungMyanmar.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SamsungMyanmarUI-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SamsungMyanmarZawgyiUI-Bold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SamsungMyanmarZawgyiUI-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SmartZawgyi.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/Padauk-book.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/Padauk-bookbold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/Padauk.ttf", "", null, null, false);
            my.RootCmd("chmod 644 /system/fonts/*.ttf", "", null, null, false);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            if (aBoolean==true){
                Toast.makeText(Rooted.this, "Install Finished!\n Rebooting...", Toast.LENGTH_SHORT).show();
                my.RootCmd("reboot","",null,null,false);
            }else{
                Toast.makeText(Rooted.this, "Install Failed :(", Toast.LENGTH_SHORT).show();
            }
        }
    }


    class installAll extends AsyncTask<String,Integer,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String font = strings[0];
            boolean b = false;
            if (all(font)==true){
                b=true;
            }
            return b;
        }

        public boolean all(String font){
            Log.d("FontPath",path+font);
            my.RootCmd("mount -o remount ,rw /system", "", null, null, false);
            my.RootCmd("mount -o rw,remount -t auto /system", "", null, null, false);
            my.RootCmd("mount -o rw,remount /system", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmar-Bold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmar-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmarUI-Bold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmarUI-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmarZawgyi-Bold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/NotoSansMyanmarZawgyi-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/DroidSansMyanmar.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SamsungMyanmar.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SamsungMyanmarUI-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SamsungMyanmarZawgyiUI-Bold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SamsungMyanmarZawgyiUI-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/SmartZawgyi.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/Padauk-book.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/Padauk-bookbold.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/DroidSans.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/DroidSans-Regular.ttf", "", null, null, false);
            my.RootCmd("dd if=" + path + font + " of=/system/fonts/Roboto-Regular.ttf", "", null, null, false);
            my.RootCmd("chmod 644 /system/fonts/*.ttf", "", null, null, false);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            if (aBoolean==true){
                Toast.makeText(Rooted.this, "Install Finished!\n Rebooting...", Toast.LENGTH_SHORT).show();
                my.RootCmd("reboot","",null,null,false);
            }else{
                Toast.makeText(Rooted.this, "Install Failed :(", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
