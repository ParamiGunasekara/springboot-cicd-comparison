
package incident_reporting_system;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentRepository repository;

    public IncidentController(IncidentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Incident create(@Valid @RequestBody Incident incident) {
        incident.setStatus(Incident.Status.OPEN);

        if (incident.getSeverity() == null) {
            incident.setSeverity(Incident.Severity.LOW);
        }

        return repository.save(incident);
    }

    @GetMapping
    public List<Incident> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Incident getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Incident not found"));
    }

    @PatchMapping("/{id}/status")
    public Incident updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequest request) {

        Incident incident = getById(id);
        incident.setStatus(request.status());
        return repository.save(incident);
    }

    @PatchMapping("/{id}/severity")
    public Incident updateSeverity(
            @PathVariable Long id,
            @Valid @RequestBody SeverityRequest request) {

        Incident incident = getById(id);
        incident.setSeverity(request.severity());
        return repository.save(incident);
    }

    public record StatusRequest(
            @NotNull Incident.Status status) {}

    public record SeverityRequest(
            @NotNull Incident.Severity severity) {}
}
