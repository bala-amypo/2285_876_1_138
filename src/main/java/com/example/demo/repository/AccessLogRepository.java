@RestController
@RequestMapping("/api/logs")
public class AccessLogController {

    private final AccessLogService service;

    public AccessLogController(AccessLogService service) {
        this.service = service;
    }

    @GetMapping("/guest/{id}")
    public List<AccessLog> logs(@PathVariable Long id) {
        return service.getLogsForGuest(id);
    }
}
