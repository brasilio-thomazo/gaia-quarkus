package br.dev.optimus.gaia.repository;

import java.time.Instant;
import java.util.List;

import br.dev.optimus.gaia.model.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    /**
     * Retrieves a list of all customers that have not been deleted.
     *
     * @return a list of customers
     */
    public List<Customer> list() {
        return find("deletedAt = 0").list();
    }

    /**
     * Retrieves a customer by ID, if it has not been deleted.
     *
     * @param id the ID of the customer to retrieve
     * @return the customer with the given ID, or a 404 if it has been deleted
     * or does not exist
     */
    public Customer get(Long id) {
        return find("id = ?1 and deletedAt = 0", id)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("customer not found"));
    }

    /**
     * Retrieves a customer by ID, regardless of its deletion status.
     *
     * @param id the ID of the customer to retrieve
     * @return the customer with the specified ID
     * @throws NotFoundException if no customer with the given ID is found
     */
    public Customer getDeleted(Long id) {
        return find("id = ?1", id)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("customer not found"));
    }

    /**
     * Checks if a customer with the given name already exists in the database.
     *
     * @param name the name to search for
     * @return true if a customer with the given name already exists, false
     * otherwise
     */
    public boolean existsByName(String name) {
        return find("name = ?1", name)
                .firstResultOptional()
                .isPresent();
    }

    /**
     * Checks if a customer with the given name already exists in the database,
     * excluding the one with the given id.
     *
     * @param name the name to search for
     * @param id the id to exclude
     * @return true if a customer with the given name already exists, false
     * otherwise
     */
    public boolean existsByName(String name, Long id) {
        return find("name = ?1 and id != ?2", name, id)
                .firstResultOptional()
                .isPresent();
    }

    /**
     * Validates a customer object
     *
     * Verifies that the customer's name, phone, email, document, and address
     * are not null and not empty. Verifies that the customer's email is a valid
     * email address. Verifies that the customer's document is a valid document.
     * Verifies that the customer's name does not already exist in the database.
     *
     * If any of the above conditions are not met, a BadRequestException is
     * thrown.
     *
     * @param customer the customer object to validate
     */
    private void validate(Customer customer) {
        var documentRegex = "^\\d+";
        var emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new BadRequestException("name is required");
        }
        if (customer.getPhone() == null || customer.getPhone().isBlank()) {
            throw new BadRequestException("phone is required");
        }
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new BadRequestException("email is required");
        }
        if (!customer.getEmail().matches(emailRegex)) {
            throw new BadRequestException("email is invalid");
        }
        if (customer.getDocument() == null || customer.getDocument().isBlank()) {
            throw new BadRequestException("document is required");
        }
        if (!customer.getDocument().matches(documentRegex)) {
            throw new BadRequestException("document is invalid");
        }
        if (customer.getAddress() == null || customer.getAddress().isBlank()) {
            throw new BadRequestException("address is required");
        }
        if (customer.getContacts() == null || customer.getContacts().isEmpty()) {
            throw new BadRequestException("contacts is required");
        }
        if (customer.getId() == null) {
            if (existsByName(customer.getName())) {
                throw new BadRequestException("name already exists");
            }
        } else {
            if (existsByName(customer.getName(), customer.getId())) {
                throw new BadRequestException("name already exists");
            }
        }
    }

    /**
     * Creates a new customer record in the database.
     *
     * Validates the customer object to ensure all required fields are present
     * and valid. Sets the created and updated timestamps to the current epoch
     * second. Persists the customer object to the database.
     *
     * @param customer the customer object to be created
     * @throws BadRequestException if validation fails
     */
    public void create(Customer customer) {
        validate(customer);
        var now = Instant.now().getEpochSecond();
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        persist(customer);
    }

    /**
     * Creates a new customer record in the database.
     *
     * Creates a new customer object from the provided DTO and calls
     * {@link #create(Customer)} to persist it.
     *
     * @param dto the customer DTO to be created
     * @return the created customer object
     * @throws BadRequestException if validation fails
     */
    public Customer create(Customer.DTO dto) {
        var data = Customer.builder()
                .name(dto.name())
                .phone(dto.phone())
                .email(dto.email())
                .document(dto.document())
                .address(dto.address())
                .contacts(dto.contacts())
                .build();
        create(data);
        return data;
    }

    /**
     * Updates an existing customer record in the database.
     *
     * Validates the customer object to ensure all required fields are present
     * and valid. Sets the updated timestamp to the current epoch second.
     * Persists the customer object to the database.
     *
     * @param customer the customer object to be updated
     * @throws BadRequestException if validation fails
     */
    public void update(Customer customer) {
        validate(customer);
        var now = Instant.now().getEpochSecond();
        customer.setUpdatedAt(now);
        persist(customer);
    }

    /**
     * Updates an existing customer record in the database.
     *
     * Retrieves the customer object with the provided ID, updates its fields
     * according to the provided DTO, and calls {@link #update(Customer)} to
     * persist the changes.
     *
     * @param id the ID of the customer to be updated
     * @param dto the customer DTO to be used for updating
     * @return the updated customer object
     * @throws BadRequestException if validation fails
     */
    public Customer update(Long id, Customer.DTO dto) {
        var customer = get(id);
        customer.setName(dto.name());
        customer.setPhone(dto.phone());
        customer.setEmail(dto.email());
        customer.setDocument(dto.document());
        customer.setAddress(dto.address());
        customer.setContacts(dto.contacts());
        update(customer);
        return customer;
    }

    /**
     * Deletes a customer record in the database.
     *
     * Retrieves the customer object with the provided ID, sets its deletion
     * timestamp to the current epoch second, and persists the changes.
     *
     * @param id the ID of the customer to be deleted
     */
    public void delete(Long id) {
        var customer = get(id);
        customer.setDeletedAt(Instant.now().getEpochSecond());
        persist(customer);
    }

    /**
     * Restores a deleted customer record in the database.
     *
     * Retrieves the customer object with the provided ID from the deleted
     * records, resets its deletion timestamp to 0, and persists the changes.
     *
     * @param id the ID of the customer to be restored
     * @return the restored customer object
     */
    public Customer restore(Long id) {
        var customer = getDeleted(id);
        customer.setDeletedAt(0);
        persist(customer);
        return customer;
    }
}
