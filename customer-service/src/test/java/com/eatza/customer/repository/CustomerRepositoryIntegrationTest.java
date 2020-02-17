package com.eatza.customer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.customer.model.Customer;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired CustomerRepository customerRepository;
	@Test
	public void whenFindByName_thenReturnEmployee() {
	    // given
	    Customer alex = new Customer("alex", "shir" , "username", "pass");
	    entityManager.persist(alex);
	    entityManager.flush();
	 
	    // when
	    Customer found = customerRepository.findByUsernameAndIsActiveIsTrue(alex.getUsername());
	 
	    // then
	    assertThat(found.getUsername())
	      .isEqualTo(alex.getUsername());
	}
}
