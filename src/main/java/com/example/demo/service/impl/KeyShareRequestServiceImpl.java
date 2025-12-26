package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository keyShareRequestRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;

    @Autowired
    public KeyShareRequestServiceImpl(KeyShareRequestRepository keyShareRequestRepository, DigitalKeyRepository digitalKeyRepository, GuestRepository guestRepository) {
        this.keyShareRequestRepository = keyShareRequestRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest request) {
        DigitalKey key = digitalKeyRepository.findById(request.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Digital key not found"));
        Guest sharedBy = guestRepository.findById(request.getSharedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shared by guest not found"));
        Guest sharedWith = guestRepository.findById(request.getSharedWith().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shared with guest not found"));
        if (sharedBy.getId().equals(sharedWith.getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be the same");
        }
        if (!sharedWith.getVerified() || !sharedWith.getActive()) {
            throw new IllegalArgumentException("Shared with guest must be verified and active");
        }
        if (request.getShareEnd().isBefore(request.getShareStart())) {
            throw new IllegalArgumentException("Share end must be after share start");
        }
        request.setDigitalKey(key);
        request.setSharedBy(sharedBy);
        request.setSharedWith(sharedWith);
        return keyShareRequestRepository.save(request);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long sharedById) {
        return keyShareRequestRepository.findBySharedById(sharedById);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long sharedWithId) {
        return keyShareRequestRepository.findBySharedWithId(sharedWithId);
    }

}