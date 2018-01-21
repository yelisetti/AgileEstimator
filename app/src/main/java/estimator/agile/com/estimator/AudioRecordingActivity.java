package estimator.agile.com.estimator;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class AudioRecordingActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton bigRecordingBtn;
    private Button recordingButton;
    private Button stopButton;
    private Button playButton;

    private MediaRecorder mediaRecorder;
    String voiceStoragePath;

    static final String AB = "abcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiorecordingactivity);

        hasSDCard();

        voiceStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File audioVoice = new File(voiceStoragePath + File.separator + "voices");
        if (!audioVoice.exists()) {
            audioVoice.mkdir();
        }
        voiceStoragePath = voiceStoragePath + File.separator + "voices/" + generateVoiceFilename(6) + ".3gpp";
        System.out.println("Audio path : " + voiceStoragePath);

        bigRecordingBtn = (ImageButton) findViewById(R.id.recording_image);
        bigRecordingBtn.setOnClickListener(this);
        recordingButton = (Button) findViewById(R.id.recording_button);
        recordingButton.setOnClickListener(this);
        stopButton = (Button) findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(this);

        bigRecordingBtn.setEnabled(true);
        stopButton.setEnabled(false);
        playButton.setEnabled(false);

        initializeMediaRecord();

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.recording_image:
            case R.id.recording_button:
                if(mediaRecorder == null){
                    initializeMediaRecord();
                }
                startAudioRecording();
                break;
            case R.id.stop_button:
                stopAudioRecording();
                break;
            case R.id.play_button:
                playLastStoredAudioMusic();
                mediaPlayerPlaying();
                break;
            default:
                break;
        }
    }


    private String generateVoiceFilename( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    private void startAudioRecording(){
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bigRecordingBtn.setEnabled(false);
        recordingButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    private void stopAudioRecording(){
        if(mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        stopButton.setEnabled(false);
        playButton.setEnabled(true);
    }

    private void playLastStoredAudioMusic(){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(voiceStoragePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        bigRecordingBtn.setEnabled(true);
        recordingButton.setEnabled(true);
        playButton.setEnabled(false);
    }

    private void stopAudioPlay(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void hasSDCard(){
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if(isSDPresent)        {
            System.out.println("There is SDCard");
        }
        else{
            System.out.println("There is no SDCard");
        }
    }

    private void mediaPlayerPlaying(){
        if(!mediaPlayer.isPlaying()){
            stopAudioPlay();
        }
    }

    private void initializeMediaRecord(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(voiceStoragePath);
    }

}
