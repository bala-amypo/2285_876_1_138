package com.example.demo.service.impl;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository repo;

    public KeyShareRequestServiceImpl(KeyShareRequestRepository repo) {
        this.repo = repo;
    }

    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest request) {
        return repo.save(request);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long guestId) {
        return repo.findBySharedById(guestId);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long guestId) {
        return repo.findBySharedWithId(guestId);
    }
}
