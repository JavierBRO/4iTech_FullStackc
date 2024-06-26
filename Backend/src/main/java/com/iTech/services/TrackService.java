package com.iTech.services;

import com.iTech.models.Track;

import java.util.List;

public interface TrackService {

    List<Track> findTrackVisibleTrue();

    List<Track> findTracks();

    Track findById(Long id);

    Track createTrack(Track track);

    Track updateTrack(Long id, Track track);

    boolean deleteTrack(Long id);
}
