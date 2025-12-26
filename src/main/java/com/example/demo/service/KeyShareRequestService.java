package com.example.demo.service;

import com.example.demo.model.KeyShareRequest;
import java.util.List;

public interface KeyShareService {
    KeyShareRequest shareKey(KeyShareRequest request);
    List<KeyShareRequest> receivedRequests(Long guestId);
}
