@RestController
@RequestMapping("/auth")
public class AuthController {

    private final GuestService guestService;

    public AuthController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        Guest guest = new Guest();
        guest.setEmail(request.getEmail());
        guest.setPassword(request.getPassword()); // raw → encoded in service
        guest.setFullName(request.getFullName());
        guest.setPhoneNumber(request.getPhoneNumber());
        guest.setRole(request.getRole());
        guest.setActive(true);
        guest.setVerified(false);

        guestService.createGuest(guest);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "User registered successfully"
        ));
    }
}
