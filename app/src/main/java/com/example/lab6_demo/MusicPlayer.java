package com.example.lab6_demo;

import android.media.MediaPlayer;

public class MusicPlayer implements MediaPlayer.OnCompletionListener {

    MediaPlayer player;
    int currentPosition = 0;
    int musicIndex = 0;
    private int musicStatus = 0;//0: before playing, 1 playing, 2 paused
    private MusicService musicService;

    static final int[] MUSICPATH = new int[]{
            R.raw.mario,
            R.raw.tetris,
            R.raw.gotechgo
    };

    static final String[] MUSICNAME = new String[]{
            "Super Mario Brothers",
            "Tetris",
            "Go Tech Go"
    };

    public MusicPlayer(MusicService service) {

        this.musicService = service;
    }


    public int getMusicStatus() {

        return musicStatus;
    }

    public String getMusicName() {

        return MUSICNAME[musicIndex];
    }

    public void playMusic() {
        player= MediaPlayer.create(this.musicService, MUSICPATH[musicIndex]);
        player.start();
        player.setOnCompletionListener(this);
        musicService.onUpdateMusicName(getMusicName());
        musicStatus = 1;
    }

    public void pauseMusic() {
        if(player!= null && player.isPlaying()){
            player.pause();
            currentPosition= player.getCurrentPosition();
            musicStatus= 2;
        }
    }

    public void resumeMusic() {
        if(player!= null){
            player.seekTo(currentPosition);
            player.start();
            musicStatus=1;
        }
    }

    public void prevSong(){
        musicIndex--;
        if (musicIndex == -1)
        {
            musicIndex = 2;
        }
        musicIndex = (musicIndex) % MUSICNAME.length;
        player.release();
        player= null;
        playMusic();
    }

    public void nextSong(){
        musicIndex = (musicIndex +1) % MUSICNAME.length;
        player.release();
        player= null;
        playMusic();
    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        musicIndex = (musicIndex +1) % MUSICNAME.length;
        player.release();
        player= null;
        playMusic();
    }

    public void stopMusic() {
        if(player!= null && player.isPlaying()){
            player.stop();
            currentPosition= 0;
            musicStatus= 0;
        }
    }
}