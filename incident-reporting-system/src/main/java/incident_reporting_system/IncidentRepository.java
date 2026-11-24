
package incident_reporting_system;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository
        extends JpaRepository<Incident, Long> {
}
