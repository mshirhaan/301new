package com.eatza.customer.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.customer.model.Customer;
import com.eatza.customer.repository.CustomerRepository;

@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

	@TestConfiguration
    static class CustomerServiceImpTest {
  
        @Bean
        public CustomerService employeeService() {
            return new CustomerServiceImpl();
        }
    }
	
	@Autowired
    private CustomerService customerService;
 
    @MockBean
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        Customer alex = new Customer(1,"alex", "lastname", "usern", "pass");
     
        Mockito.when(customerRepository.findById(alex.getId()).get())
          .thenReturn(alex);
    }
    
    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        customerService.deactivateCustomer(1);
      
         assertThat(found.getName())
          .isEqualTo(name);
     }
}
