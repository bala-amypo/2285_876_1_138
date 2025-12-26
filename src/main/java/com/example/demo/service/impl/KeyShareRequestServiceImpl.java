@Service
public class KeyShareServiceImpl implements KeyShareService {

    private final KeyShareRequestRepository repo;

    public KeyShareServiceImpl(KeyShareRequestRepository repo) {
        this.repo = repo;
    }

    public KeyShareRequest shareKey(KeyShareRequest request) {
        return repo.save(request);
    }

    public List<KeyShareRequest> receivedRequests(Long guestId) {
        return repo.findBySharedWithId(guestId);
    }
}
