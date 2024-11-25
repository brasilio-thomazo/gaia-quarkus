package br.dev.optimus.gaia.repository;

import br.dev.optimus.gaia.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    private final GroupRepository groupRepository;

    public UserRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<User> list() {
        return find("visible and deletedAt = 0").list();
    }

    public User get(Long id) {
        return find("id = ?1 and deletedAt = 0", id)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

    public Optional<User> findByUsername(String username) {
        return find("username = ?1 and deletedAt = 0", username)
                .firstResultOptional();
    }

    public Optional<User> findByEmail(String email) {
        return find("email = ?1 and deletedAt = 0", email)
                .firstResultOptional();
    }

    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return find("username = ?1 or email = ?2 and deletedAt = 0", username, email)
                .firstResultOptional();
    }

    public boolean existsByUsername(String username) {
        return find("username = ?1 and deletedAt = 0", username)
                .firstResultOptional()
                .isPresent();
    }

    public boolean existsByUsername(String username, Long id) {
        return find("username = ?1 and deletedAt = 0 and id != ?2", username, id)
                .firstResultOptional()
                .isPresent();
    }

    public boolean existsByEmail(String email) {
        return find("email = ?1 and deletedAt = 0", email)
                .firstResultOptional()
                .isPresent();
    }

    public boolean existsByEmail(String email, Long id) {
        return find("email = ?1 and deletedAt = 0 and id != ?2", email, id)
                .firstResultOptional()
                .isPresent();
    }

    private void validate(User data) {
        if (data.getName() == null || data.getName().isBlank()) {
            throw new BadRequestException("user name is required");
        }
        if (data.getUsername() == null || data.getUsername().isBlank()) {
            throw new BadRequestException("user username is required");
        }
        if (data.getEmail() == null || data.getEmail().isBlank()) {
            throw new BadRequestException("user email is required");
        }
        if (data.getId() == null) {
            if (existsByUsername(data.getUsername())) {
                throw new BadRequestException("user username already exists");
            }
            if (existsByEmail(data.getEmail())) {
                throw new BadRequestException("user email already exists");
            }
        } else {
            if (existsByUsername(data.getUsername(), data.getId())) {
                throw new BadRequestException("user username already exists");
            }
            if (existsByEmail(data.getEmail(), data.getId())) {
                throw new BadRequestException("user email already exists");
            }
        }
    }

    public void create(User user) {
        validate(user);
        var now = Instant.now().getEpochSecond();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        persist(user);
    }

    public void update(User user) {
        validate(user);
        var now = Instant.now().getEpochSecond();
        user.setUpdatedAt(now);
        persist(user);
    }

    public User create(User.DTO dto) {
        if (dto.groupId() == null) {
            throw new BadRequestException("user group is required");
        }
        if (dto.password() == null || dto.password().isBlank()) {
            throw new BadRequestException("user password is required");
        }
        if (dto.password().length() < 6) {
            throw new BadRequestException("user password must be at least 6 characters");
        }
        if (!dto.password().equals(dto.passwordConfirm())) {
            throw new BadRequestException("user passwords do not match");
        }
        var group = groupRepository.get(dto.groupId());
        var data = User.builder()
                .group(group)
                .name(dto.name())
                .phone(dto.phone())
                .jobTitle(dto.jobTitle())
                .email(dto.email())
                .username(dto.username())
                .password(dto.password())
                .locked(dto.locked()).build();
        create(data);
        return data;
    }

    public User update(Long id, User.DTO dto) {
        if (dto.groupId() == null) {
            throw new BadRequestException("user group is required");
        }
        if (dto.password() != null && dto.password().length() < 6) {
            throw new BadRequestException("user password must be at least 6 characters");
        }

        if (dto.password() != null && !dto.password().equals(dto.passwordConfirm())) {
            throw new BadRequestException("user passwords do not match");
        }
        var group = groupRepository.get(dto.groupId());
        var data = get(id);
        data.setGroup(group);
        data.setName(dto.name());
        data.setPhone(dto.phone());
        data.setJobTitle(dto.jobTitle());
        data.setEmail(dto.email());
        data.setUsername(dto.username());
        if (dto.password() != null) data.setPassword(dto.password());
        data.setLocked(dto.locked());
        update(data);
        return data;
    }

    public void delete(Long id) {
        var data = get(id);
        data.setDeletedAt(Instant.now().getEpochSecond());
        update(data);
    }

    public User restore(Long id) {
        var data = get(id);
        data.setDeletedAt(0L);
        update(data);
        return data;
    }

}
