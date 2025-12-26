@Service
public class RoomBookingServiceImpl implements RoomBookingService {

    private final RoomBookingRepository repo;

    public RoomBookingServiceImpl(RoomBookingRepository repo) {
        this.repo = repo;
    }

    public RoomBooking createBooking(RoomBooking booking) {
        return repo.save(booking);
    }

    public List<RoomBooking> getBookingsByGuest(Long guestId) {
        return repo.findByGuestId(guestId);
    }
}
