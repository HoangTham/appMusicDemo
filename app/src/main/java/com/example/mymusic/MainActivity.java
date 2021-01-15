package com.example.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtBegin, txtEnd;
    ImageView stop, back, next, next_next, img;
    SeekBar sb;
    ArrayList<Song> dataSong;
    MediaPlayer mediaPlayer;
    Animation animation2;
    int position = 0;
    ArrayList<Integer> dataImg;
    ArrayList<Animation> dataAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataImg = new ArrayList<>();
        dataAnim = new ArrayList<>();
        AnhXa();
        AddSong();
        createMediaPlay();
        setImg();
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    stop.setImageResource(R.drawable.play);
                }
                else{
                    mediaPlayer.start();
                    stop.setImageResource(R.drawable.stop);
                }
                setTimeStart();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position== dataSong.size()-1){
                    position = 0;
                }
                else{
                    position++;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                createMediaPlay();
                mediaPlayer.start();
                setImg();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0 ){
                    position = dataSong.size()-1;
                }
                else{
                    position--;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                createMediaPlay();
                mediaPlayer.start();
                setImg();
            }
        });
        next_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setProgress(sb.getProgress()+10000);
                mediaPlayer.seekTo(sb.getProgress());
            }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sb.getProgress());
            }
        });
    }
    private void setTimeStart(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                txtBegin.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                // update thoi gian cho seekbar
                sb.setProgress(mediaPlayer.getCurrentPosition());
                // kiem tra bai hat ket thuc thi chuyen bai khac
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(position== dataSong.size()-1){
                            position = 0;
                        }
                        else{
                            position++;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        createMediaPlay();
                        mediaPlayer.start();
                        setImg();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }
    private  void addImg(){
        dataImg.add(R.drawable.animal3_cat);
        dataImg.add(R.drawable.bell);
        dataImg.add(R.drawable.anime_emoji);
        dataImg.add(R.drawable.heart1);
        dataImg.add(R.drawable.certificate);
        dataAnim.add(AnimationUtils.loadAnimation(this, R.anim.ring_bell));
        dataAnim.add(AnimationUtils.loadAnimation(this, R.anim.ribbon));
        dataAnim.add(AnimationUtils.loadAnimation(this, R.anim.anpha));
        dataAnim.add(AnimationUtils.loadAnimation(this, R.anim.round));
        dataAnim.add(AnimationUtils.loadAnimation(this, R.anim.scale));

    }
    private void setImg(){
        addImg();
        Random random = new Random();
        int index1 = random.nextInt(dataImg.size()-1);
        int x = random.nextInt(dataAnim.size()-1);
        Animation k = dataAnim.get(x);
        int so = dataImg.get(index1);
        img.setImageResource(so);
        img.startAnimation(k);
    }

    private void setTimeEnd(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        int time = mediaPlayer.getDuration();
        txtEnd.setText(simpleDateFormat.format(time));
        sb.setMax(time);
    }

    private void createMediaPlay(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, dataSong.get(position).getFile());
        txtTitle.setText(dataSong.get(position).getTitle());
        animation2 = AnimationUtils.loadAnimation(this, R.anim.text);
        txtTitle.startAnimation(animation2);
        setTimeEnd();
    }

    private void AddSong(){
        dataSong = new ArrayList<>();
        // add bai hat
        dataSong.add(new Song("Anh là của em", R.raw.anh_la_cua_em));
        dataSong.add(new Song("Anh nói em nghe đi", R.raw.anh_noi_em_nghe_di));
        dataSong.add(new Song("Anh phải đi", R.raw.anh_phai_di));
        dataSong.add(new Song("Mãi yêu mình vợ", R.raw.mai_yeu_minh_vo));
        dataSong.add(new Song("Nếu không phải là em", R.raw.neu_khong_phai_la_em));
        dataSong.add(new Song("Ngủ ngoan nhé vợ tương lai", R.raw.ngu_ngoan_nhe_vo_tuong_lai));
        dataSong.add(new Song("Người âm phủ", R.raw.nguoi_am_phu));
        dataSong.add(new Song("Nói có sẽ khó nhưng vui", R.raw.noi_co_se_kho_nhung_vui));
        dataSong.add(new Song("Quan trọng là thần thái", R.raw.quan_trong_la_than_thai));
        dataSong.add(new Song("Rất là em", R.raw.rat_la_em));
        dataSong.add(new Song("Sai người sai thời điểm", R.raw.sai_nguoi_sai_thoi_diem));
        dataSong.add(new Song("Sao mình chưa nắm lấy tay nhau", R.raw.sao_minh_chua_nam_lay_tay_nhau));
        dataSong.add(new Song("What are words", R.raw.what_are_words));
        dataSong.add(new Song("Yêu thật đấy", R.raw.yeu_that_dat));
        dataSong.add(new Song("Yêu thật rồi", R.raw.yeu_that_roi));
        dataSong.add(new Song("Đúng người đúng thời điểm", R.raw.dung_nguoi_dung_thoi_diem));
    }

    private void AnhXa() {
        txtTitle = (TextView) findViewById(R.id.textView_Title);
        txtBegin = (TextView) findViewById(R.id.textView2_timebegin);
        txtEnd = (TextView) findViewById(R.id.textView3_timeend);
        stop = (ImageView) findViewById(R.id.imageView);
        back = (ImageView) findViewById(R.id.imageView4);
        next = (ImageView) findViewById(R.id.imageView2);
        next_next = (ImageView) findViewById(R.id.imageView3);
        sb = (SeekBar) findViewById(R.id.seekBar);
        img = (ImageView)findViewById(R.id.imageView5);

    }
}
