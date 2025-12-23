@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;   // ✅ ADD THIS

    private String phoneNumber;

    private Boolean verified = false;

    private Boolean active = true;

    private String role;

    private Timestamp createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // getters & setters
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
