package br.dev.optimus.gaia.config;

import br.dev.optimus.gaia.model.Group;
import br.dev.optimus.gaia.model.User;
import br.dev.optimus.gaia.repository.GroupRepository;
import br.dev.optimus.gaia.repository.UserRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class InitConfig {
    private static final Logger log = LoggerFactory.getLogger(InitConfig.class);
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;


    public InitConfig(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void init(@Observes StartupEvent event) {
        log.info("init groups and users");
        if (groupRepository.list().isEmpty()) {
            createInitialGroups();
            createInitialUsers();
        }
    }

    private void createInitialGroups() {
        groupRepository.create(Group.builder()
                .name("root")
                .visible(false)
                .editable(false)
                .locked(true)
                .permissions("root")
                .build());

        groupRepository.create(Group.builder()
                .name("nobody")
                .visible(false)
                .editable(false)
                .locked(true)
                .permissions("nobody")
                .build());

        groupRepository.create(Group.builder()
                .name("admin")
                .visible(true)
                .editable(false)
                .locked(true)
                .permissions("admin")
                .build());
    }

    private void createInitialUsers() {
        var root = groupRepository.findByName("root");
        var admin = groupRepository.findByName("admin");
        log.info("create root user");
        userRepository.create(User.builder()
                .group(root)
                .name("root")
                .email("root@change.me")
                .username("root")
                .password("root")
                .editable(false)
                .visible(false)
                .locked(true)
                .build());
        log.info("create admin user");
        userRepository.create(User.builder()
                .group(admin)
                .name("admin")
                .email("admin@change.me")
                .username("admin")
                .password("admin")
                .editable(false)
                .visible(true)
                .locked(true)
                .build());

    }
}
