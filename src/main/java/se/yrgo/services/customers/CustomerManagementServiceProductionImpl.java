package se.yrgo.services.customers;

import org.springframework.jdbc.core.RowMapper;
import se.yrgo.dataaccess.CustomerDao;
import se.yrgo.dataaccess.RecordNotFoundException;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerManagementServiceProductionImpl implements CustomerManagementService {
    private CustomerDao customerDao;

    public CustomerManagementServiceProductionImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void newCustomer(Customer newCustomer) {
        customerDao.create(newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) {
        try {
            customerDao.update(changedCustomer);
        } catch (RecordNotFoundException e) {
            throw new RuntimeException("Customer not found during update", e);
        }
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) throws RecordNotFoundException {
        customerDao.delete(oldCustomer);
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        try {
            return customerDao.getById(customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerDao.getByName(name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        return customerDao.getFullCustomerDetail(customerId);
    }



    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        try {
            customerDao.addCall(callDetails, customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException();
        }
    }

    class CallMapper implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            String customerId = rs.getString("customer_id");
            String companyName = rs.getString("company_name");
            String email = rs.getString("email");
            String telephone = rs.getString("telephone");
            String notes = rs.getString("notes");
            Customer customer = new Customer(customerId, companyName, email, telephone, notes);
            return customer;
        }

    }


}
