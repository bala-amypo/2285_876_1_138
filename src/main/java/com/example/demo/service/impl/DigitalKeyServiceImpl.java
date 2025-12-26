@Service
public class DigitalKeyServiceImpl implements DigitalKeyService {

    private final DigitalKeyRepository repo;
    private final RoomBookingRepository bookingRepo;

    public DigitalKeyServiceImpl(DigitalKeyRepository repo, RoomBookingRepository bookingRepo) {
        this.repo = repo;
        this.bookingRepo = bookingRepo;
    }

    public DigitalKey generateKey(Long bookingId) {
        DigitalKey key = new DigitalKey();
        key.setBooking(bookingRepo.findById(bookingId).orElseThrow());
        return repo.save(key);
    }

    public List<DigitalKey> getKeysByGuest(Long guestId) {
        return repo.findByBookingGuestId(guestId);
    }
}
