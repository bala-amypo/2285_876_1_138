package com.example.demo.service.impl;

import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository repository;
    private final DigitalKeyRepository keyRepository;
    private final GuestRepository guestRepository;

    public KeyShareRequestServiceImpl(KeyShareRequestRepository repository,
                                      DigitalKeyRepository keyRepository,
                                      GuestRepository guestRepository) {
        this.repository = repository;
        this.keyRepository = keyRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest req) {

        if (req.getShareEnd().isBefore(req.getShareStart())) {
            throw new IllegalArgumentException("Share end before start");
        }

        if (req.getSharedBy().getId().equals(req.getSharedWith().getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be same");
        }

        DigitalKey key = keyRepository.findById(req.getDigitalKey().getId()).orElseThrow();
        Guest sharedWith = guestRepository.findById(req.getSharedWith().getId()).orElseThrow();

        if (!key.getActive() || !sharedWith.getActive() || !sharedWith.getVerified()) {
            throw new IllegalStateException("Invalid share");
        }

        return repository.save(req);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long guestId) {
        return repository.findBySharedById(guestId);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long guestId) {
        return repository.findBySharedWithId(guestId);
    }
}
