package portfolio.services;

import java.util.List;

import portfolio.dto.ContactDto;
import portfolio.entities.ContactEntity;

public interface ContactService {

	

	ContactEntity savecontact(ContactDto contactDto);
	boolean isContactEmailExist(String email);
	List<ContactEntity> readAllContacts();
      void deleteContactById(int id);
}
