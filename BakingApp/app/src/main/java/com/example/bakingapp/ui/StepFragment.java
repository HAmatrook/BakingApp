package com.example.bakingapp.ui;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static android.content.Context.NOTIFICATION_SERVICE;

//import android.support.v7.app.NotificationCompat;

public class StepFragment extends Fragment implements ExoPlayer.EventListener {

    @BindView(R.id.description_tv)
    TextView description_tv;
    Unbinder unbinder;

    public StepFragment() {
    }

    public Context context;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;
    private static MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private NotificationManager notificationManager;
    Dialog fullScreenDialog;
    String videoUri;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Timber.plant(new Timber.DebugTree());
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        view = rootView;
        Bundle bundle = this.getArguments();

        int position = bundle.getInt(DetailsFragment.POSITION, -1);

        playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ButterKnife.bind(this, rootView);
            description_tv.setText(DetailsFragment.recipe.getDescription().get(position));
        }
        context = getActivity();

        videoUri = DetailsFragment.recipe.getVideoURL().get(position);
        if (!videoUri.isEmpty()) {
            playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(videoUri));
            initializeMediaSession();
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (!videoUri.isEmpty()) {
            fullScreenDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            // Checking the orientation of the screen
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                // isExoPlayerFullscreen = true;
                openFullscreenDialog();

            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                closeFullscreenDialog();
            }
        }

    }


    private void openFullscreenDialog() {
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        fullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreenDialog.show();
    }

    private void closeFullscreenDialog() {
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        ((FrameLayout) view.findViewById(R.id.main_media_frame)).addView(playerView);
        fullScreenDialog.dismiss();
    }

    private void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(context, getString(R.string.app_name));

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                //      PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());

        mediaSession.setCallback(new MySessionCallback());
        mediaSession.setActive(true);

    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            //TODO exoPlayer.addListener;

            String userAgent = Util.getUserAgent(context, getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }


    private void releasePlayer() {
        if (exoPlayer != null) {
            // notificationManager.cancelAll();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        unbinder.unbind();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
        //testMediaSession(stateBuilder.build());
    }

    private void testMediaSession(PlaybackStateCompat stateCompat) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        int icon;
        String play_pause;
        if (stateCompat.getState() == PlaybackStateCompat.STATE_PLAYING) {
            icon = R.drawable.exo_controls_pause;
            play_pause = getString((R.string.pause));
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = getString(R.string.play);
        }

        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action restartAction = new NotificationCompat
                .Action(R.drawable.exo_controls_previous, getString(R.string.restart),
                MediaButtonReceiver.buildMediaButtonPendingIntent
                        (context, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, 0, new Intent(context, StepFragment.class), 0);

        builder.setContentTitle("Test")
                .setContentText("Testing the mediaSession")
                .setContentIntent(contentPendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(restartAction)
                .addAction(playPauseAction)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken())
                        .setShowActionsInCompactView(0, 1));


        notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }
}
