package br.com.uchat.messenger;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import br.com.uchat.audio_recorder.AudioListener;
import br.com.uchat.audio_recorder.AudioRecordButton;
import br.com.uchat.audio_recorder.AudioRecording;
import br.com.uchat.audio_recorder.RecordingItem;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AudioMessageActivity extends AppCompatActivity {

    private AudioRecordButton mAudioRecordButton;
    private AudioRecording audioRecording;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioRecording = new AudioRecording(getBaseContext());
        this.mAudioRecordButton = (AudioRecordButton) findViewById(R.id.audio_record_button);

        //Asking Read,Write External Storage and Record Audio Permissions
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, READ_EXTERNAL_STORAGE},0);

        //Audio Button OnClickListener
        this.mAudioRecordButton.setOnAudioListener(new AudioListener() {
            @Override
            public void onStop(RecordingItem recordingItem) {
                Toast.makeText(getBaseContext(), "Sending..", Toast.LENGTH_SHORT).show();
                audioRecording.play(recordingItem);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Log.d("MainActivity", "Error: " + e.getMessage());
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted

                } else {
                    //Permission denied
                }
                break;
            }
        }
    }
}
