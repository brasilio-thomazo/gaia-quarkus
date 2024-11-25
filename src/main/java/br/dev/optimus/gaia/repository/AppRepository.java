package br.dev.optimus.gaia.repository;

import java.time.Instant;
import java.util.UUID;

import br.dev.optimus.gaia.model.App;
import br.dev.optimus.gaia.request.DockerRequest;
import br.dev.optimus.gaia.service.DockerService;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppRepository implements PanacheRepositoryBase<App, UUID> {

    public void create(App app) {
        var now = Instant.now().getEpochSecond();
        app.setCreatedAt(now);
        app.setUpdatedAt(now);
        persist(app);
    }

    public void create(App.DTO dto, DockerService service) {
        var request = new DockerRequest.ContainerCreate(dto.image(), null, dto.environments(), dto.volumes(), null);
        var response = service.createContainer(dto.name(), request);
        var data = App.builder()
                .name(dto.name())
                .image(dto.image())
                .container(response.id())
                .environments(dto.environments())
                .volumes(dto.volumes())
                .build();
    }
    
}
