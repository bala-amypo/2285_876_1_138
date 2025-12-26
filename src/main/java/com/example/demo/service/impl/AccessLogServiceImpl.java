@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository repo;

    public AccessLogServiceImpl(AccessLogRepository repo) {
        this.repo = repo;
    }

    public AccessLog logAccess(AccessLog log) {
        return repo.save(log);
    }

    public List<AccessLog> logsByGuest(Long guestId) {
        return repo.findByGuestId(guestId);
    }
}
