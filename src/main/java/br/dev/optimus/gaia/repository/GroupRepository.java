package br.dev.optimus.gaia.repository;

import br.dev.optimus.gaia.model.Group;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class GroupRepository implements PanacheRepositoryBase<Group, Integer> {
    private void validate(Group.DTO dto, Integer id) {
        if (dto.name() == null || dto.name().isBlank()) {
            throw new BadRequestException("group name is required");
        }
        if (id != null && nameExists(dto.name(), id)) {
            throw new BadRequestException("group name already exists");
        }
        else if (nameExists(dto.name())) {
            throw new BadRequestException("group name already exists");
        }
    }

    private boolean nameExists(String name) {
        return find("name = ?1", name)
                .firstResultOptional()
                .isPresent();
    }

    private boolean nameExists(String name, Integer id) {
        return find("name = ?1 and id != ?2", name, id)
                .firstResultOptional()
                .isPresent();
    }

    public List<Group> list() {
        return find("visible and deletedAt = 0").list();
    }

    public Group get(Integer id) {
        return find("id = ?1 and deletedAt = 0", id)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("group not found"));
    }

    public Group findByName(String name) {
        return find("name = ?1 and deletedAt = 0", name)
                .firstResultOptional()
                .orElse(null);
    }

    public void create(Group group) {
        var now = Instant.now().getEpochSecond();
        group.setCreatedAt(now);
        group.setUpdatedAt(now);
        persist(group);
    }

    public void update(Group group) {
        var now = Instant.now().getEpochSecond();
        group.setUpdatedAt(now);
        persist(group);
    }

    public Group create(Group.DTO dto) {
        validate(dto, null);
        var data = Group.builder()
                .name(dto.name())
                .permissions(dto.permissions())
                .locked(dto.locked()).build();
        create(data);
        return data;
    }
    
    public Group update(Integer id, Group.DTO dto) {
        validate(dto, id);
        var data = get(id);
        data.setName(dto.name());
        data.setPermissions(dto.permissions());
        data.setLocked(dto.locked());
        update(data);
        return data;
    }
    
    public void delete(Integer id) {
        var group = get(id);
        group.setDeletedAt(Instant.now().getEpochSecond());
        update(group);
    }
    
    public Group restore(Integer id) {
        var data = get(id);
        data.setDeletedAt(0);
        update(data);
        return data;
    }
}
