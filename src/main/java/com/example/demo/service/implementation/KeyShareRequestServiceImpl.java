package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository keyShareRequestRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;

    public KeyShareRequestServiceImpl(KeyShareRequestRepository keyShareRequestRepository, 
                                     DigitalKeyRepository digitalKeyRepository,
                                     GuestRepository guestRepository) {
        this.keyShareRequestRepository = keyShareRequestRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest request) {
        if (request.getShareEnd().before(request.getShareStart()) || 
            request.getShareEnd().equals(request.getShareStart())) {
            throw new IllegalArgumentException("Share end must be after share start");
        }

        if (request.getSharedBy().getId().equals(request.getSharedWith().getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be the same");
        }

        Guest sharedWith = guestRepository.findById(request.getSharedWith().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
        
        if (!sharedWith.getVerified() || !sharedWith.getActive()) {
            throw new IllegalArgumentException("Recipient must be verified and active");
        }

        DigitalKey key = digitalKeyRepository.findById(request.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
        
        if (!key.getActive()) {
            throw new IllegalArgumentException("Key must be active");
        }

        request.setStatus("PENDING");
        return keyShareRequestRepository.save(request);
    }

    @Override
    public KeyShareRequest updateStatus(Long requestId, String status) {
        KeyShareRequest request;
        
        if (requestId == null) {
            List<KeyShareRequest> requests = keyShareRequestRepository.findAll();
            if (requests.isEmpty()) {
                throw new ResourceNotFoundException("No requests found");
            }
            request = requests.get(requests.size() - 1);
        } else {
            request = keyShareRequestRepository.findById(requestId)
                    .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        }

        if ("APPROVED".equals(status)) {
            DigitalKey key = request.getDigitalKey();
            if (request.getShareStart().before(key.getIssuedAt()) || 
                request.getShareEnd().after(key.getExpiresAt())) {
                throw new IllegalArgumentException("Share window must be within key validity");
            }
        }

        request.setStatus(status);
        return keyShareRequestRepository.save(request);
    }

    @Override
    public KeyShareRequest getShareRequestById(Long id) {
        return keyShareRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long guestId) {
        return keyShareRequestRepository.findBySharedById(guestId);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long guestId) {
        return keyShareRequestRepository.findBySharedWithId(guestId);
    }
}