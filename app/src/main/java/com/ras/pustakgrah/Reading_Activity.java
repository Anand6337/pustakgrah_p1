package com.ras.pustakgrah;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.ras.pustakgrah.databinding.ActivityReadingBinding;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

public class Reading_Activity extends AppCompatActivity implements DownloadFile.Listener {

    ActivityReadingBinding binding;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

            progress = new ProgressDialog(this);
            progress.setTitle("Loading");
            progress.setMessage("Wait While Loading....");
            progress.show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String url = getIntent().getStringExtra("url");
        RemotePDFViewPager remotePDFViewPager =
                new RemotePDFViewPager(Reading_Activity.this, url ,this);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {

        progress.dismiss();
      PDFPagerAdapter adapter = new PDFPagerAdapter(this,  extractFileNamefromURL(url));
        binding.pdfViewPager.setAdapter(adapter);


    }
    public static String extractFileNamefromURL(String url)
    {
        return url.substring(url.lastIndexOf('/') +1);
    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(Reading_Activity.this, "Something went wrong ! Please try Again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}